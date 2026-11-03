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

public class CrossesPass2TracksExtractorTransformer{   
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
        
        String outputName = "crossesTracks.csv";
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
                            List<Cross> crossesBST = trk.getBSTCrosses();
                            List<Cluster> clustersBMT = trk.getBMTClusters();
                            
                            int numCrossesBST = crossesBST.size();
                            for(int i = 0; i < numCrossesBST; i++){   
                                Cross crs = crossesBST.get(i);
                                String crsInfo = String.format("%.4f,%.4f,%.4f,%d", 
                                        crs.point().x(), crs.point().y(), crs.point().z(), crs.region());
                                if(i < numCrossesBST - 1) writer.write(crsInfo + ",");
                                else{
                                    if(!clustersBMT.isEmpty()) writer.write(crsInfo + ",");
                                    else writer.write(crsInfo + "\n");
                                }
                            }
                            
                            int numClustersBMT = clustersBMT.size();
                            for(int i = 0; i < numClustersBMT; i++){
                                Cluster cls = clustersBMT.get(i);
                                String clsInfo;

                                // Average radius of the two endpoints
                                double r1 = Math.hypot(cls.originPoint().x(), cls.originPoint().y());
                                double r2 = Math.hypot(cls.endPoint().x(), cls.endPoint().y());
                                double r = 0.5 * (r1 + r2);

                                if(cls.bmtType() == Constants.BMTC){
                                    double z = (cls.originPoint().z() + cls.endPoint().z()) / 2;
                                    clsInfo = String.format("%.4f,%.4f,%d,%d", 
                                        r, z, 0, cls.layer()+3);
                                }
                                else{
                                    // Average phi of the two endpoints
                                    double phi1 = Math.atan2(cls.originPoint().y(), cls.originPoint().x());
                                    double phi2 = Math.atan2(cls.endPoint().y(), cls.endPoint().x());
                                    double phi = circularMean(phi1, phi2);
                                    clsInfo = String.format("%.4f,%.4f,%d,%d", 
                                        r, phi, 0, cls.layer()+3);                                    
                                }

                                if(i < numClustersBMT - 1) writer.write(clsInfo + ",");
                                else writer.write(clsInfo + "\n");
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
    
    private static double circularMean(double phi1, double phi2) {
        double sinSum = Math.sin(phi1) + Math.sin(phi2);
        double cosSum = Math.cos(phi1) + Math.cos(phi2);
        return Math.atan2(sinSum, cosSum);
    }
}