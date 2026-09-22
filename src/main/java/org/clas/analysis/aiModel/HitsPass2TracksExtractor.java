package org.clas.analysis.aiModel;

import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jlab.utils.options.OptionParser;
import org.jlab.jnp.hipo4.io.HipoReader;
import org.jlab.jnp.hipo4.data.Event;
import org.jlab.jnp.hipo4.data.Bank;
import org.jlab.jnp.hipo4.data.SchemaFactory;
import org.jlab.jnp.hipo4.io.HipoWriterSorted;
import org.jlab.utils.benchmark.ProgressPrintout;

import org.clas.element.RunConfig;
import org.clas.element.Track;
import org.clas.element.Cluster;
import org.clas.element.Cross;
import org.clas.element.Hit;
import org.clas.reader.Reader;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;
import org.clas.utilities.Constants;
import org.jlab.geom.prim.Point3D;

/**
 * For each valid pass2 track, store all clusters in the track, and track parameters
 * Samples are used to train a model for estimation of track state with inputs of all clusters
 * @author Tongtong
 */

public class HitsPass2TracksExtractor{   
    private static final Logger LOGGER = Logger.getLogger(Reader.class.getName()); 
    
    // Set sections based on layer and sector of hits
    // Some SVT hits are shared by sections1&2 or sections2&3
    private static List<Integer> getSectionList(int layer, int sector) {
        List<Integer> sectionList = new ArrayList<>();

        if (layer >= 1 && layer <= 2) {
            if (sector >= 1 && sector <= 4) sectionList.add(1);
            if (sector >= 4 && sector <= 8) sectionList.add(2);
            if (sector >= 8 && sector <= 10) sectionList.add(3);
        }
        else if (layer >= 3 && layer <= 4) {
            if (sector >= 1 && sector <= 6) sectionList.add(1);
            if (sector >= 6 && sector <= 10) sectionList.add(2);
            if (sector >= 10 && sector <= 14) sectionList.add(3);
        }
        else if (layer >= 5 && layer <= 6) {
            if (sector >= 1 && sector <= 7) sectionList.add(1);
            if (sector >= 7 && sector <= 13) sectionList.add(2);
            if (sector >= 14 && sector <= 18) sectionList.add(3);
        }
        else {
            if (sector == 1) sectionList.add(1);
            if (sector == 2) sectionList.add(2);
            if (sector == 3) sectionList.add(3);
        }

        return sectionList;
    }    
       
    public static void main(String[] args) throws IOException {
        
        OptionParser parser = new OptionParser("extractEvents");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o", "", "output file name prefix");
        parser.addOption("-n", "-1", "maximum number of events to process");
        parser.addOption("-m", "1000000", "maximum output entries");
        parser.parse(args);

        String namePrefix = parser.getOption("-o").stringValue();
        int maxEvents = parser.getOption("-n").intValue();
        int maxOutputEntries = parser.getOption("-m").intValue();

        List<String> inputList = parser.getInputList();
        if (inputList.isEmpty() == true) {
            parser.printUsage();
            System.out.println("\n >>>> error: no input file is specified....\n");
            System.exit(0);
        }
        
        String outputName = "labeledHits.csv";
        if (!namePrefix.isEmpty()) {
            outputName = namePrefix + "_" + outputName;
        }
        
                // Prepare FileWriters for all 6 sectors
        Map<Integer, FileWriter> sectionWriters = new HashMap<>();
        Map<Integer, Integer> sectionCounters = new HashMap();
        for (int section = 1; section <= 3; section++) {
            String sectionOutputName = outputName.replace(".csv", "_section" + section + ".csv");
            FileWriter writer = new FileWriter(sectionOutputName);
            sectionWriters.put(section, writer);  
            sectionCounters.put(section, 0);
        }        

        ProgressPrintout progress = new ProgressPrintout();
        int counter = 0;
        for(String input : inputList){
            HipoReader reader = new HipoReader();
            reader.open(input);
            SchemaFactory schema = reader.getSchemaFactory();

            Reader localReader = new Reader(new Banks(schema));
            Event event = new Event();
            while(reader.hasNext()){
                counter++;
                reader.nextEvent(event);

                LocalEvent localEvent = new LocalEvent(localReader, event);

                List<Hit> bstHitsOnTracksPass2 = new ArrayList();
                List<Hit> bmtHitsOnTracksPass2 = new ArrayList();
                for(Track trk : localEvent.getRecTracks()){  
                    if(trk.isValidLooseCuts(false)){
                        bstHitsOnTracksPass2.addAll(trk.getBSTHits());
                        bmtHitsOnTracksPass2.addAll(trk.getBMTHits());
                    }
                }

                List<Hit> bstHits = localEvent.getBSTHits();
                List<Hit> bmtHits = localEvent.getBMTHits();                 

                List<Hit> bstHitsOnTracks = new ArrayList();
                List<Hit> bstHitsNotOnTracks = new ArrayList();                
                List<Hit> bmtHitsOnTracks = new ArrayList();
                List<Hit> bmtHitsNotOnTracks = new ArrayList();

                for(Hit hit : bstHits){
                    boolean flag = false;
                    for(Hit hitOnTrk : bstHitsOnTracksPass2){
                        if(hit.isSameHitOnAllAttributes(hitOnTrk)) {
                            bstHitsOnTracks.add(hit);
                            flag = true;
                            break;
                        }
                    }
                    if(!flag) bstHitsNotOnTracks.add(hit);
                }

                for(Hit hit : bmtHits){
                    boolean flag = false;
                    for(Hit hitOnTrk : bmtHitsOnTracksPass2){
                        if(hit.isSameHitOnAllAttributes(hitOnTrk)) {
                            bmtHitsOnTracks.add(hit);
                            flag = true;
                            break;
                        }
                    }
                    if(!flag) bmtHitsNotOnTracks.add(hit);
                }                    

                Map<Integer, List<Hit>> map_section_bstHitsOnTracks = new HashMap();
                Map<Integer, List<Hit>> map_section_bstHitsNotOnTracks = new HashMap();
                Map<Integer, List<Hit>> map_section_bmtHitsOnTracks = new HashMap();
                Map<Integer, List<Hit>> map_section_bmtHitsNotOnTracks = new HashMap();
                for(int section = 1; section <= 3; section++){
                    map_section_bstHitsOnTracks.put(section, new ArrayList());
                    map_section_bstHitsNotOnTracks.put(section, new ArrayList());
                    map_section_bmtHitsOnTracks.put(section, new ArrayList());
                    map_section_bmtHitsNotOnTracks.put(section, new ArrayList());
                }

                for(Hit hit : bstHitsOnTracks){
                    List<Integer> sectionList = getSectionList(hit.layer(), hit.sector());
                    for(int section : sectionList){
                        map_section_bstHitsOnTracks.get(section).add(hit);
                    }
                }

                for(Hit hit : bstHitsNotOnTracks){
                    List<Integer> sectionList = getSectionList(hit.layer(), hit.sector());
                    for(int section : sectionList){
                        map_section_bstHitsNotOnTracks.get(section).add(hit);
                    }
                }

                for(Hit hit : bmtHitsOnTracks){
                    map_section_bmtHitsOnTracks.get(hit.sector()).add(hit);
                }

                for(Hit hit : bmtHitsNotOnTracks){
                    map_section_bmtHitsNotOnTracks.get(hit.sector()).add(hit);
                }                    

                for(int section = 1; section <= 3; section++){
                    boolean flag = false;
                    for(Hit hit : map_section_bstHitsOnTracks.get(section)){
                        String info = String.format("%d,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%d,%d,%d\n", 
                                        hit.strip(), hit.getOriginPoint().x(), hit.getEndPoint().x(),
                                        hit.getOriginPoint().y(), hit.getEndPoint().y(),
                                        hit.getOriginPoint().z(), hit.getEndPoint().z(),
                                        hit.sector(), hit.layer(), 1);
                        sectionWriters.get(section).write(info);
                        flag = true;
                    }

                    for(Hit hit : map_section_bstHitsNotOnTracks.get(section)){
                        String info = String.format("%d,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%d,%d,%d\n", 
                                        hit.strip(), hit.getOriginPoint().x(), hit.getEndPoint().x(),
                                        hit.getOriginPoint().y(), hit.getEndPoint().y(),
                                        hit.getOriginPoint().z(), hit.getEndPoint().z(),
                                        hit.sector(), hit.layer(), 0);
                        sectionWriters.get(section).write(info);
                        flag = true;
                    } 
                    for(Hit hit : map_section_bmtHitsOnTracks.get(section)){
                        String info = String.format("%d,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%d,%d,%d\n", 
                                        hit.strip(), hit.getOriginPoint().x(), hit.getEndPoint().x(),
                                        hit.getOriginPoint().y(), hit.getEndPoint().y(),
                                        hit.getOriginPoint().z(), hit.getEndPoint().z(),
                                        hit.sector(), hit.layer()+6, 1);
                        sectionWriters.get(section).write(info);
                        flag = true;
                    }                        
                    for(Hit hit : map_section_bmtHitsNotOnTracks.get(section)){
                        String info = String.format("%d,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%d,%d,%d\n", 
                                        hit.strip(), hit.getOriginPoint().x(), hit.getEndPoint().x(),
                                        hit.getOriginPoint().y(), hit.getEndPoint().y(),
                                        hit.getOriginPoint().z(), hit.getEndPoint().z(),
                                        hit.sector(), hit.layer()+6, 0);
                        sectionWriters.get(section).write(info);
                        flag = true;
                    } 
                    if(flag) {                        
                        sectionWriters.get(section).write("\n");
                        
                        int counterSection = sectionCounters.get(section) + 1;
                        sectionCounters.put(section, counterSection);
                    }                                                
                }

                progress.updateStatus();
                
                if((maxEvents > 0 && counter >= maxEvents) || (maxOutputEntries > 0 && sectionCounters.containsValue(maxOutputEntries))) break;
            }

            progress.showStatus();
            reader.close(); 
        }
        
        for (FileWriter writer : sectionWriters.values()) {
            writer.flush();
            writer.close();
        }

    }
}