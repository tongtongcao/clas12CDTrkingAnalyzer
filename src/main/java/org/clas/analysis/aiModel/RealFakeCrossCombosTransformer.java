package org.clas.analysis.aiModel;

import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
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
import org.clas.utilities.CommonFunctions;
import org.jlab.geom.prim.Point3D;

/**
 * Cross combos for valid tracks are real.
 * A fake combo is constructed by randomly replacing a cross on a real combo 
 * with a random cross at the same sector and the same layer.
 * @author Tongtong
 */

public class RealFakeCrossCombosTransformer{   
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
        
        String outputName = "realFakeCrossCombos.csv";
        if (!namePrefix.isEmpty()) {
            outputName = namePrefix + "_" + outputName;
        }
        
        Random random = new Random();

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
                    List<Cross> allCrossesBST = localEvent.getBSTCrosses();
                    List<Cluster> allClustersBMT = localEvent.getBMTClusters();
                    
                    Map<Integer, Map<Integer, List<Cross>>> map_section_region_allCrossesBST = new HashMap();
                    for(int i = 1; i <= 3; i++){
                        Map<Integer, List<Cross>> map_region_allCrossesBST = new HashMap();                        
                        for(int j = 1; j <= 3; j++){                            
                            map_region_allCrossesBST.put(j, new ArrayList<Cross>());                            
                        }                        
                        map_section_region_allCrossesBST.put(i, map_region_allCrossesBST);
                    }
                    for(Cross crs : allCrossesBST){
                        List<Integer> sectionList = CommonFunctions.getSectionList(crs.getCluster1().layer(), crs.getCluster1().sector()); 
                        for(int s : sectionList){
                            map_section_region_allCrossesBST.get(s).get(crs.region()).add(crs);
                        }
                    }
                    
                    Map<Integer, Map<Integer, List<Cluster>>> map_section_layer_allClustersBMT = new HashMap();
                    for(int i = 1; i <= 3; i++){
                        Map<Integer, List<Cluster>> map_region_allClustersBMT = new HashMap();
                        for(int j = 1; j <= 6; j++){                            
                            map_region_allClustersBMT.put(j, new ArrayList<Cluster>());                            
                        }
                        map_section_layer_allClustersBMT.put(i, map_region_allClustersBMT);
                    }
                    for(Cluster cls : allClustersBMT){
                        map_section_layer_allClustersBMT.get(cls.sector()).get(cls.layer()).add(cls);
                    }                    
                    
                    
                    
                    for(Track trk : localEvent.getRecTracks()){                                                                                                 
                        if(trk.isValid()){
                            
                            // Construct real case and save info into a map
                            Map<Integer, List<Double>> map_crsLayer_crsFeatures_real = new HashMap();
                                                                                                                
                            List<Cross> crossesBST = trk.getBSTCrosses();
                            List<Cluster> clustersBMT = trk.getBMTClusters();

                            Map<Integer, Cross> map_region_crossBST = new HashMap();
                            for(Cross crs : crossesBST){   
                                map_region_crossBST.put(crs.region(), crs);
                                
                                List<Double> crsFeatures = new ArrayList<>(Arrays.asList(crs.point().x(), crs.point().y(), crs.point().z(), (double)(crs.region())));
                                map_crsLayer_crsFeatures_real.put(crs.region(), crsFeatures);                                                                                                                               
                            }
                            
                            Map<Integer, Cluster> map_region_clusterBMT = new HashMap();
                            for(Cluster cls : clustersBMT){ 
                                map_region_clusterBMT.put(cls.layer(), cls);
                                
                                // Average radius of the two endpoints
                                double r1 = Math.hypot(cls.originPoint().x(), cls.originPoint().y());
                                double r2 = Math.hypot(cls.endPoint().x(), cls.endPoint().y());
                                double r = 0.5 * (r1 + r2); 

                                if(cls.bmtType() == Constants.BMTC){
                                    double z = (cls.originPoint().z() + cls.endPoint().z()) / 2;
                                    List<Double> clsFeatures = new ArrayList<>(Arrays.asList(r, z, 0.0, cls.layer()+3.0));
                                    map_crsLayer_crsFeatures_real.put(cls.layer()+3, clsFeatures);
                                }
                                else{
                                    // Average phi of the two endpoints
                                    double phi1 = Math.atan2(cls.originPoint().y(), cls.originPoint().x());
                                    double phi2 = Math.atan2(cls.endPoint().y(), cls.endPoint().x());
                                    double phi = circularMean(phi1, phi2); 

                                    List<Double> clsFeatures = new ArrayList<>(Arrays.asList(r, phi, 0.0, cls.layer()+3.0));
                                    map_crsLayer_crsFeatures_real.put(cls.layer()+3, clsFeatures);
                                }                                                                    
                            }                        
                                                        
                            int section = -1;    
                            if(!clustersBMT.isEmpty()) section = clustersBMT.get(0).sector();
                            else {
                                int totalSection = 0;
                                int countSection = 0;
                                for(Cross crs : crossesBST){
                                    List<Integer> sectionList = CommonFunctions.getSectionList(crs.getCluster1().layer(), crs.getCluster1().sector()); 
                                    for(int s : sectionList){
                                        totalSection += s;
                                        countSection++;
                                    }                                   
                                }
                                section = (int) Math.round((double)totalSection/countSection);                                
                            }
                            
                            
                            // Write real case
                            for (List<Double> features : map_crsLayer_crsFeatures_real.values()) {
                                String clsInfo = String.format("%.4f,%.4f,%f,%d", 
                                        features.get(0), features.get(1), features.get(2),features.get(3).intValue());
                                writer.write(clsInfo + ",");  
                            }
                            writer.write(1 + "\n"); 
                            counter++;                                                        
                            
                            // Construct and write fake case with a fake BST cross
                            Map<Integer, List<Double>> map_crsLayer_crsFeatures_bstFake = new HashMap(map_crsLayer_crsFeatures_real);  
                            int randomNumberBSTRegion = random.nextInt(3) + 1;
                            List<Cross> allCrossesBST_randomRegion = map_section_region_allCrossesBST.get(section).get(randomNumberBSTRegion);
                            List<Cross> candidatesBST = new ArrayList<>(allCrossesBST_randomRegion);
                            if (map_region_crossBST.keySet().contains(randomNumberBSTRegion)) {
                                int originalId = map_region_crossBST.get(randomNumberBSTRegion).id();
                                candidatesBST.removeIf(crs -> crs.id() == originalId);
                            }
                            if(!candidatesBST.isEmpty()) {
                                Cross randomBSTCross = candidatesBST.get(random.nextInt(candidatesBST.size()));
                                List<Double> crsFeaturesFake = new ArrayList<>(Arrays.asList(randomBSTCross.point().x(), randomBSTCross.point().y(), randomBSTCross.point().z(), (double)randomNumberBSTRegion));
                                map_crsLayer_crsFeatures_bstFake.put(randomNumberBSTRegion, crsFeaturesFake);
                                
                                for (List<Double> features : map_crsLayer_crsFeatures_bstFake.values()) {
                                    String clsInfo = String.format("%.4f,%.4f,%f,%d", 
                                            features.get(0), features.get(1), features.get(2),features.get(3).intValue());
                                    writer.write(clsInfo + ",");  
                                }
                                
                                writer.write(0 + "\n");  
                                counter++;
                            }                            

                            // Construct and write fake case with a fake BMT cluster
                            Map<Integer, List<Double>> map_crsLayer_crsFeatures_bmtFake = new HashMap(map_crsLayer_crsFeatures_real);
                            int randomNumberBMTLayer = random.nextInt(6) + 1;
                            List<Cluster> allClusterBMT_randomLayer = map_section_layer_allClustersBMT.get(section).get(randomNumberBMTLayer);
                            List<Cluster> candidatesBMT = new ArrayList<>(allClusterBMT_randomLayer);  
                            if(map_region_clusterBMT.keySet().contains(randomNumberBMTLayer)) {
                                int originalId = map_region_clusterBMT.get(randomNumberBMTLayer).id();
                                candidatesBMT.removeIf(cls -> cls.id() == originalId);
                            }                            
                            
                            if (!candidatesBMT.isEmpty()) {
                                Cluster randomBMTCluster = candidatesBMT.get(random.nextInt(candidatesBMT.size()));
                                
                                // Average radius of the two endpoints
                                double r1 = Math.hypot(randomBMTCluster.originPoint().x(), randomBMTCluster.originPoint().y());
                                double r2 = Math.hypot(randomBMTCluster.endPoint().x(), randomBMTCluster.endPoint().y());
                                double r = 0.5 * (r1 + r2);                                  
                                
                                if (randomBMTCluster.bmtType() == Constants.BMTC) {
                                    double z = (randomBMTCluster.originPoint().z() + randomBMTCluster.endPoint().z()) / 2;
                                    List<Double> clsFeatures = new ArrayList<>(Arrays.asList(r, z, 0.0, randomNumberBMTLayer + 3.));
                                    map_crsLayer_crsFeatures_bmtFake.put(randomNumberBMTLayer + 3, clsFeatures);
                                } else {                                    
                                    // Average phi of the two endpoints
                                    double phi1 = Math.atan2(randomBMTCluster.originPoint().y(), randomBMTCluster.originPoint().x());
                                    double phi2 = Math.atan2(randomBMTCluster.endPoint().y(), randomBMTCluster.endPoint().x());
                                    double phi = circularMean(phi1, phi2); 
                                    
                                    List<Double> clsFeatures = new ArrayList<>(Arrays.asList(r, phi, 0., randomNumberBMTLayer + 3.));
                                    map_crsLayer_crsFeatures_bmtFake.put(randomNumberBMTLayer + 3, clsFeatures);
                                }
                                
                                for (List<Double> features : map_crsLayer_crsFeatures_bmtFake.values()) {
                                    String clsInfo = String.format("%.4f,%.4f,%f,%d", 
                                            features.get(0), features.get(1), features.get(2),features.get(3).intValue());
                                    writer.write(clsInfo + ",");  
                                }
                                                                
                                writer.write(0 + "\n"); 
                                counter++;
                            }                                                                                                                                                                                                                            
                                                        
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