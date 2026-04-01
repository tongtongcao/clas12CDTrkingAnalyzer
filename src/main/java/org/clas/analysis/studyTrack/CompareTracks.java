package org.clas.analysis.studyTrack;

import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;
import javax.swing.JFrame;

import org.jlab.groot.graphics.EmbeddedCanvasTabbed;
import org.jlab.jnp.hipo4.data.Event;
import org.jlab.jnp.hipo4.data.SchemaFactory;
import org.jlab.jnp.hipo4.io.HipoReader;
import org.jlab.utils.benchmark.ProgressPrintout;
import org.jlab.utils.options.OptionParser;

import org.clas.analysis.BaseAnalysis;
import org.clas.utilities.Constants;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;
import org.clas.element.Hit;
import org.clas.element.Cluster;
import org.clas.element.Cross;
import org.clas.element.Seed;
import org.clas.element.Track;
import org.clas.graph.HistoGroup;
import org.clas.graph.TrackHistoGroup;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;

/**
 * Compare tracks
 * 
 * @author Tongtong Cao
 */
public class CompareTracks extends BaseAnalysis{ 
       
    public CompareTracks(){}
    
    @Override
    public void createHistoGroupMap(){ 
        TrackHistoGroup histoGroupTrackPass1 = new TrackHistoGroup("trackPass1", 4, 2);
        histoGroupTrackPass1.addTrackHistos("evt1", 1, 0);
        histoGroupTrackPass1.addTrackHistos("evt2", 2, 0);
        histoGroupMap.put(histoGroupTrackPass1.getName(), histoGroupTrackPass1);      
        
        TrackHistoGroup histoGroupTrackPass2 = new TrackHistoGroup("trackPass2", 4, 2);
        histoGroupTrackPass2.addTrackHistos("evt1", 1, 0);
        histoGroupTrackPass2.addTrackHistos("evt2", 2, 0);
        histoGroupMap.put(histoGroupTrackPass2.getName(), histoGroupTrackPass2);   
                
        TrackHistoGroup histoGroupUTrackPass1 = new TrackHistoGroup("uTrackPass1", 4, 2);
        histoGroupUTrackPass1.addTrackHistos("evt1", 1, 0);
        histoGroupUTrackPass1.addTrackHistos("evt2", 2, 0);
        histoGroupMap.put(histoGroupUTrackPass1.getName(), histoGroupUTrackPass1);      
        
        TrackHistoGroup histoGroupUTrackPass2 = new TrackHistoGroup("uTrackPass2", 4, 2);
        histoGroupUTrackPass2.addTrackHistos("evt1", 1, 0);
        histoGroupUTrackPass2.addTrackHistos("evt2", 2, 0);
        histoGroupMap.put(histoGroupUTrackPass2.getName(), histoGroupUTrackPass2);         
    }
             
    public void processEvent(Event event1, Event event2){        
        //Read banks
        LocalEvent localEvent1 = new LocalEvent(reader1, event1); 
        LocalEvent localEvent2 = new LocalEvent(reader2, event2);
        
        List<Track> tracksPass1_evt1 = localEvent1.getTracks(1, false);              
        List<Track> tracksPass2_evt1 = localEvent1.getTracks(2, false); 
        List<Track> uTracksPass1_evt1 = localEvent1.getTracks(1, true);              
        List<Track> uTracksPass2_evt1 = localEvent1.getTracks(2, true); 
        
        List<Track> tracksPass1_evt2 = localEvent2.getTracks(1, false);              
        List<Track> tracksPass2_evt2 = localEvent2.getTracks(2, false); 
        List<Track> uTracksPass1_evt2 = localEvent2.getTracks(1, true);              
        List<Track> uTracksPass2_evt2 = localEvent2.getTracks(2, true); 
        
        TrackHistoGroup histoGroupTrackPass1 = (TrackHistoGroup) histoGroupMap.get("trackPass1");        
        for(Track trk : tracksPass1_evt1){
            histoGroupTrackPass1.getHistoChi2overndf("evt1").fill(trk.chi2()/trk.ndf());
            histoGroupTrackPass1.getHistoP("evt1").fill(trk.momentum().mag());
            histoGroupTrackPass1.getHistoTheta("evt1").fill(trk.momentum().theta());
            histoGroupTrackPass1.getHistoPhi("evt1").fill(trk.momentum().phi());            
            histoGroupTrackPass1.getHistoVx("evt1").fill(trk.vertex().x());
            histoGroupTrackPass1.getHistoVy("evt1").fill(trk.vertex().y());
            histoGroupTrackPass1.getHistoVz("evt1").fill(trk.vertex().z());
        }        
        for(Track trk : tracksPass1_evt2){
            histoGroupTrackPass1.getHistoChi2overndf("evt2").fill(trk.chi2()/trk.ndf());
            histoGroupTrackPass1.getHistoP("evt2").fill(trk.momentum().mag());
            histoGroupTrackPass1.getHistoTheta("evt2").fill(trk.momentum().theta());
            histoGroupTrackPass1.getHistoPhi("evt2").fill(trk.momentum().phi());
            histoGroupTrackPass1.getHistoVx("evt2").fill(trk.vertex().x());
            histoGroupTrackPass1.getHistoVy("evt2").fill(trk.vertex().y());
            histoGroupTrackPass1.getHistoVz("evt2").fill(trk.vertex().z());            
        }
        
        TrackHistoGroup histoGroupTrackPass2 = (TrackHistoGroup) histoGroupMap.get("trackPass2");        
        for(Track trk : tracksPass2_evt1){
            histoGroupTrackPass2.getHistoChi2overndf("evt1").fill(trk.chi2()/trk.ndf());
            histoGroupTrackPass2.getHistoP("evt1").fill(trk.momentum().mag());
            histoGroupTrackPass2.getHistoTheta("evt1").fill(trk.momentum().theta());
            histoGroupTrackPass2.getHistoPhi("evt1").fill(trk.momentum().phi());            
            histoGroupTrackPass2.getHistoVx("evt1").fill(trk.vertex().x());
            histoGroupTrackPass2.getHistoVy("evt1").fill(trk.vertex().y());
            histoGroupTrackPass2.getHistoVz("evt1").fill(trk.vertex().z());
        }        
        for(Track trk : tracksPass2_evt2){
            histoGroupTrackPass2.getHistoChi2overndf("evt2").fill(trk.chi2()/trk.ndf());
            histoGroupTrackPass2.getHistoP("evt2").fill(trk.momentum().mag());
            histoGroupTrackPass2.getHistoTheta("evt2").fill(trk.momentum().theta());
            histoGroupTrackPass2.getHistoPhi("evt2").fill(trk.momentum().phi());
            histoGroupTrackPass2.getHistoVx("evt2").fill(trk.vertex().x());
            histoGroupTrackPass2.getHistoVy("evt2").fill(trk.vertex().y());
            histoGroupTrackPass2.getHistoVz("evt2").fill(trk.vertex().z());            
        }  
        
        TrackHistoGroup histoGroupUTrackPass1 = (TrackHistoGroup) histoGroupMap.get("uTrackPass1");        
        for(Track trk : uTracksPass1_evt1){
            histoGroupUTrackPass1.getHistoChi2overndf("evt1").fill(trk.chi2()/trk.ndf());
            histoGroupUTrackPass1.getHistoP("evt1").fill(trk.momentum().mag());
            histoGroupUTrackPass1.getHistoTheta("evt1").fill(trk.momentum().theta());
            histoGroupUTrackPass1.getHistoPhi("evt1").fill(trk.momentum().phi());            
            histoGroupUTrackPass1.getHistoVx("evt1").fill(trk.vertex().x());
            histoGroupUTrackPass1.getHistoVy("evt1").fill(trk.vertex().y());
            histoGroupUTrackPass1.getHistoVz("evt1").fill(trk.vertex().z());
        }        
        for(Track trk : uTracksPass1_evt2){
            histoGroupUTrackPass1.getHistoChi2overndf("evt2").fill(trk.chi2()/trk.ndf());
            histoGroupUTrackPass1.getHistoP("evt2").fill(trk.momentum().mag());
            histoGroupUTrackPass1.getHistoTheta("evt2").fill(trk.momentum().theta());
            histoGroupUTrackPass1.getHistoPhi("evt2").fill(trk.momentum().phi());
            histoGroupUTrackPass1.getHistoVx("evt2").fill(trk.vertex().x());
            histoGroupUTrackPass1.getHistoVy("evt2").fill(trk.vertex().y());
            histoGroupUTrackPass1.getHistoVz("evt2").fill(trk.vertex().z());            
        }
        
        TrackHistoGroup histoGroupUTrackPass2 = (TrackHistoGroup) histoGroupMap.get("uTrackPass2");        
        for(Track trk : uTracksPass2_evt1){
            histoGroupUTrackPass2.getHistoChi2overndf("evt1").fill(trk.chi2()/trk.ndf());
            histoGroupUTrackPass2.getHistoP("evt1").fill(trk.momentum().mag());
            histoGroupUTrackPass2.getHistoTheta("evt1").fill(trk.momentum().theta());
            histoGroupUTrackPass2.getHistoPhi("evt1").fill(trk.momentum().phi());            
            histoGroupUTrackPass2.getHistoVx("evt1").fill(trk.vertex().x());
            histoGroupUTrackPass2.getHistoVy("evt1").fill(trk.vertex().y());
            histoGroupUTrackPass2.getHistoVz("evt1").fill(trk.vertex().z());
        }        
        for(Track trk : uTracksPass2_evt2){
            histoGroupUTrackPass2.getHistoChi2overndf("evt2").fill(trk.chi2()/trk.ndf());
            histoGroupUTrackPass2.getHistoP("evt2").fill(trk.momentum().mag());
            histoGroupUTrackPass2.getHistoTheta("evt2").fill(trk.momentum().theta());
            histoGroupUTrackPass2.getHistoPhi("evt2").fill(trk.momentum().phi());
            histoGroupUTrackPass2.getHistoVx("evt2").fill(trk.vertex().x());
            histoGroupUTrackPass2.getHistoVy("evt2").fill(trk.vertex().y());
            histoGroupUTrackPass2.getHistoVz("evt2").fill(trk.vertex().z());            
        }        
                
        
    }
    
    public void postEventProcess() {  
                
    }            
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("bgEffectsOnValidTracks");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o"          ,"",     "output file name prefix");
        parser.addOption("-n"          ,"-1",   "maximum number of events to process");        
        parser.addOption("-plot"       ,"1",    "display histograms (0/1)");
        parser.addOption("-histo"      ,"0",    "read histogram file (0/1)");        
        parser.parse(args);
        
        String namePrefix  = parser.getOption("-o").stringValue(); 
        int maxEvents  = parser.getOption("-n").intValue();    
        boolean displayPlots   = (parser.getOption("-plot").intValue()!=0);
        boolean readHistos   = (parser.getOption("-histo").intValue()!=0);        
        
        List<String> inputList = parser.getInputList();
        if(inputList.isEmpty()==true){
            parser.printUsage();
            inputList.add("/Users/caot/research/clas12/data/mc/uRWELL/upgradeTrackingWithuRWELL/nobg/applyCTDAF/pt3_1pt6/origin/recon_before_update.hipo");
            inputList.add("/Users/caot/research/clas12/data/mc/uRWELL/upgradeTrackingWithuRWELL/bg/applyCTDAF/pt3_1pt6/origin/recon_before_update_bg.hipo");
            maxEvents = 1000;
            //System.out.println("\n >>>> error: no input file is specified....\n");
            //System.exit(0);
        }

        String histoName   = "histo.hipo"; 
        if(!namePrefix.isEmpty()) {
            histoName  = namePrefix + "_" + histoName;
        }
        
        Constants.BG = true;         
        CompareTracks analysis = new CompareTracks();
        analysis.createHistoGroupMap();
        
        if(!readHistos) {                 
            HipoReader reader1 = new HipoReader();
            reader1.open(inputList.get(0));
            HipoReader reader2 = new HipoReader();
            reader2.open(inputList.get(1));

            SchemaFactory schema1 = reader1.getSchemaFactory();
            SchemaFactory schema2 = reader2.getSchemaFactory();
            analysis.initReader(new Banks(schema1), new Banks(schema2));

            int counter = 0;
            Event event1 = new Event();
            Event event2 = new Event();
        
            ProgressPrintout progress = new ProgressPrintout();
            while (reader1.hasNext() && reader2.hasNext()) {

                counter++;

                reader1.nextEvent(event1);
                reader2.nextEvent(event2);               
                analysis.processEvent(event1, event2);
                progress.updateStatus();
                if(maxEvents>0){
                    if(counter>=maxEvents) break;
                }                    
            }
            
            analysis.postEventProcess();
            
            progress.showStatus();
            reader1.close(); 
            reader2.close();
            analysis.saveHistos(histoName);
        }
        else{
            analysis.readHistos(inputList.get(0)); 
        }
        
        if(displayPlots) {
            JFrame frame = new JFrame();
            EmbeddedCanvasTabbed canvas = analysis.plotHistos();
            if(canvas != null){
                frame.setSize(1800, 1200);
                frame.add(canvas);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }        
    }
    
}
