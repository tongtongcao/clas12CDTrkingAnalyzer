package org.clas.analysis.aiModel;

import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
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

public class ClustersPass2TracksExtractor{   
    private static final Logger LOGGER = Logger.getLogger(Reader.class.getName()); 
       
    public static void main(String[] args) throws IOException {
        
        OptionParser parser = new OptionParser("extractEvents");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o", "", "output file name prefix");
        parser.addOption("-n", "1000000", "maximum output entries");
        parser.addOption("-mc", "0", "if mc (0/1)");
        
        parser.parse(args);

        String namePrefix = parser.getOption("-o").stringValue();
        int maxOutputEntries = parser.getOption("-n").intValue();
        boolean mc = (parser.getOption("-mc").intValue() != 0);
        Constants.MC = mc;       

        List<String> inputList = parser.getInputList();
        if (inputList.isEmpty() == true) {
            parser.printUsage();
            System.out.println("\n >>>> error: no input file is specified....\n");
            System.exit(0);
        }
        
        String outputName = "clustersTracks.csv";
        if (!namePrefix.isEmpty()) {
            outputName = namePrefix + "_" + outputName;
        }
        

        ProgressPrintout progress = new ProgressPrintout();
        int counter = 0;
        try(FileWriter writer = new FileWriter(outputName)){
            for(String input : inputList){
                HipoReader reader = new HipoReader();
                reader.open(input);
                SchemaFactory schema = reader.getSchemaFactory();

                Reader localReader = new Reader(new Banks(schema));
                Event event = new Event();
                while(reader.hasNext()){
                    boolean flag = false;
                    
                    reader.nextEvent(event);

                    LocalEvent localEvent = new LocalEvent(localReader, event);
                    for(Track trk : localEvent.getRecTracks()){  
                        if(trk.isValid()){
                            Cluster[] bstClusters = new Cluster[6];
                            Cluster[] bmtClusters = new Cluster[6];
                            
                            List<Cluster> clustersBST = trk.getBSTClusters();
                            List<Cluster> clustersBMT = trk.getBMTClusters();
                            for (Cluster cls : clustersBST) {
                                int layer = cls.layer();

                                if (layer >= 1 && layer <= 6) {
                                    bstClusters[layer - 1] = cls;
                                }
                            }

                            for (Cluster cls : clustersBMT) {
                                int layer = cls.layer();

                                if (layer >= 1 && layer <= 6) {
                                    bmtClusters[layer - 1] = cls;
                                }
                            }
                            
                            for(int i = 0; i < 6; i++){
                                String clsInfo;                               
                                if(bstClusters[i] != null){
                                    clsInfo = String.format("%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%d,%f,%d", 
                                            bstClusters[i].originPoint().x(), bstClusters[i].originPoint().y(), bstClusters[i].originPoint().z(),
                                            bstClusters[i].endPoint().x(), bstClusters[i].endPoint().y(), bstClusters[i].endPoint().z(), i+1, bstClusters[i].centroid(),
                                            1);
                                } else {
                                    clsInfo = String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d", 
                                            0, 0, 0,
                                            0, 0, 0, 
                                            0, 0,
                                            0);
                                }                                
                                writer.write(clsInfo + ",");                                                                 
                            }
                            
                            for(int i = 0; i < 6; i++){
                                String clsInfo;
                                if(bmtClusters[i] != null) {
                                    clsInfo = String.format("%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%d,%f,%d", 
                                            bmtClusters[i].originPoint().x(), bmtClusters[i].originPoint().y(), bmtClusters[i].originPoint().z(),
                                            bmtClusters[i].endPoint().x(), bmtClusters[i].endPoint().y(), bmtClusters[i].endPoint().z(), i+7, bmtClusters[i].centroid(),
                                            1);
                                } else {
                                    clsInfo = String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d", 
                                            0, 0, 0,
                                            0, 0, 0, 
                                            0, 0,
                                            0);                                                                
                                }
                                
                                writer.write(clsInfo + ",");                                                                
                            }                                                        
                            
                            String trackParameters = String.format("%.4f,%.4f,%.4f,%.4f,%.4f", 
                                     trk.d0(), trk.phi0(), trk.q()/trk.pt(), trk.z0(), trk.tandip());
                            writer.write(trackParameters + "\n");

                            counter++;
                            if ((maxOutputEntries > 0 && counter >= maxOutputEntries)) {
                                flag = true;
                                break;
                            }                                                                                                                                        
                        }
                    }
                    
                    if(flag) break;
                    progress.updateStatus();
                                        
                }
                progress.showStatus();
                reader.close(); 
            }
        }
    }
}