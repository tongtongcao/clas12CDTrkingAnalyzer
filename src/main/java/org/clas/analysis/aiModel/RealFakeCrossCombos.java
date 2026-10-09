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

public class RealFakeCrossCombos{   
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
                            Map<Integer, List<Double>> map_realCombos = new HashMap();
                                                                                    
                            Cross[] bstCrosses = new Cross[3];
                            Cluster[] bmtClusters = new Cluster[6];
                            
                            List<Cross> crossesBST = trk.getBSTCrosses();
                            List<Cluster> clustersBMT = trk.getBMTClusters();
                            for (Cross crs : crossesBST) {
                                int region = crs.region();

                                if (region >= 1 && region <= 3) {
                                    bstCrosses[region - 1] = crs;
                                }
                            }

                            for (Cluster cls : clustersBMT) {
                                int layer = cls.layer();

                                if (layer >= 1 && layer <= 6) {
                                    bmtClusters[layer - 1] = cls;
                                }
                            }
                            
                            for(int i = 0; i < 3; i++){
                                String crsInfo;                               
                                if(bstCrosses[i] != null){
                                    crsInfo = String.format("%.4f,%.4f,%.4f,%d,%d", 
                                            bstCrosses[i].point().x(), bstCrosses[i].point().y(), bstCrosses[i].point().z(), i+1,1);
                                    List<Double> crsFeatures = new ArrayList<>(Arrays.asList(bstCrosses[i].point().x(), bstCrosses[i].point().y(), bstCrosses[i].point().z(), (double)(i+1), 1.0));
                                    map_realCombos.put(i+1, crsFeatures);
                                } else {
                                    crsInfo = String.format("%d,%d,%d,%d,%d", 
                                            0, 0, 0, i+1, 0);
                                     List<Double> crsFeatures = new ArrayList<>(Arrays.asList(0., 0., 0., (double)(i+1), 0.));
                                     map_realCombos.put(i+1, crsFeatures);
                                }                                                                                               
                            }
                            
                            for(int i = 0; i < 6; i++){
                                String clsInfo;
                                if(bmtClusters[i] != null) {
                                    double r = (Math.sqrt(Math.pow(bmtClusters[i].originPoint().x(), 2) + Math.pow(bmtClusters[i].originPoint().y(), 2)) + 
                                            Math.sqrt(Math.pow(bmtClusters[i].endPoint().x(), 2) + Math.pow(bmtClusters[i].endPoint().y(), 2))) / 2;
                                    
                                    if(bmtClusters[i].bmtType() == Constants.BMTC){
                                        double z = (bmtClusters[i].originPoint().z() + bmtClusters[i].endPoint().z()) / 2;
                                        List<Double> clsFeatures = new ArrayList<>(Arrays.asList(r, z, (double)(i+4), 1.));
                                        map_realCombos.put(i+4, clsFeatures);
                                    }
                                    else{
                                        double phi = (Math.atan2(bmtClusters[i].originPoint().y(), bmtClusters[i].originPoint().x()) + 
                                                Math.atan2(bmtClusters[i].endPoint().y(), bmtClusters[i].endPoint().x())) / 2;
                                        List<Double> clsFeatures = new ArrayList<>(Arrays.asList(r, phi, (double)(i+4), 1.));
                                        map_realCombos.put(i+4, clsFeatures);
                                    }
                                }
                                else {
                                    List<Double> clsFeatures = new ArrayList<>(Arrays.asList(0., 0., (double)(i+4), 0.));
                                    map_realCombos.put(i+4, clsFeatures);
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
                            for (int l = 1; l <= 9; l++) {
                                List<Double> features = map_realCombos.get(l);
                                if(l <= 3){
                                    String crsInfo = String.format("%.4f,%.4f,%.4f,%d,%d", 
                                            features.get(0), features.get(1), features.get(2), features.get(3).intValue(),features.get(4).intValue());
                                    writer.write(crsInfo + ",");  
                                }
                                else{
                                    String clsInfo = String.format("%.4f,%.4f,%d,%d", 
                                            features.get(0), features.get(1), features.get(2).intValue(),features.get(3).intValue());
                                    writer.write(clsInfo + ",");  
                                }
                            }
                            writer.write(1 + "\n"); 
                            counter++;                                                        
                            
                            // Construct and write fake case with a fake BST cross
                            Map<Integer, List<Double>> map_fakeBSTCross = new HashMap(map_realCombos);  
                            int randomNumberBSTRegion = random.nextInt(3) + 1;
                            List<Cross> allCrossesBST_randomRegion = map_section_region_allCrossesBST.get(section).get(randomNumberBSTRegion);
                            List<Cross> candidatesBST = new ArrayList<>(allCrossesBST_randomRegion);
                            if (bstCrosses[randomNumberBSTRegion - 1] != null) {
                                int originalId = bstCrosses[randomNumberBSTRegion - 1].id();
                                candidatesBST.removeIf(crs -> crs.id() == originalId);
                            }
                            if(!candidatesBST.isEmpty()) {
                                Cross randomBSTCross = candidatesBST.get(random.nextInt(candidatesBST.size()));
                                List<Double> crsFeaturesFake = new ArrayList<>(Arrays.asList(randomBSTCross.point().x(), randomBSTCross.point().y(), randomBSTCross.point().z(), (double)randomNumberBSTRegion, 1.0));
                                map_fakeBSTCross.put(randomNumberBSTRegion, crsFeaturesFake);
                                
                                for (int l = 1; l <= 9; l++) {
                                    List<Double> features = map_fakeBSTCross.get(l);
                                    if(l <= 3){
                                        String crsInfo = String.format("%.4f,%.4f,%.4f,%d,%d", 
                                                features.get(0), features.get(1), features.get(2), features.get(3).intValue(),features.get(4).intValue());
                                        writer.write(crsInfo + ",");  
                                    }
                                    else{
                                        String clsInfo = String.format("%.4f,%.4f,%d,%d", 
                                                features.get(0), features.get(1), features.get(2).intValue(),features.get(3).intValue());
                                        writer.write(clsInfo + ",");  
                                    }
                                }
                                writer.write(0 + "\n");  
                                counter++;
                            }                            

                            // Construct and write fake case with a fake BMT cluster
                            Map<Integer, List<Double>> map_fakeBMTCluster = new HashMap(map_realCombos);
                            int randomNumberBMTLayer = random.nextInt(6) + 1;
                            List<Cluster> allClusterBMT_randomLayer = map_section_layer_allClustersBMT.get(section).get(randomNumberBMTLayer);
                            List<Cluster> candidatesBMT = new ArrayList<>(allClusterBMT_randomLayer);  
                            if(bmtClusters[randomNumberBMTLayer - 1] != null) {
                                int originalId = bmtClusters[randomNumberBMTLayer - 1].id();
                                candidatesBMT.removeIf(cls -> cls.id() == originalId);
                            }                            
                            
                            if (!candidatesBMT.isEmpty()) {
                                Cluster randomBMTCluster = candidatesBMT.get(random.nextInt(candidatesBMT.size()));
                                double r = (Math.sqrt(Math.pow(randomBMTCluster.originPoint().x(), 2) + Math.pow(randomBMTCluster.originPoint().y(), 2))
                                        + Math.sqrt(Math.pow(randomBMTCluster.endPoint().x(), 2) + Math.pow(randomBMTCluster.endPoint().y(), 2))) / 2;
                                if (randomBMTCluster.bmtType() == Constants.BMTC) {
                                    double z = (randomBMTCluster.originPoint().z() + randomBMTCluster.endPoint().z()) / 2;
                                    List<Double> clsFeatures = new ArrayList<>(Arrays.asList(r, z, (double) (randomNumberBMTLayer + 3), 1.));
                                    map_fakeBMTCluster.put(randomNumberBMTLayer + 3, clsFeatures);
                                } else {
                                    double phi = (Math.atan2(randomBMTCluster.originPoint().y(), randomBMTCluster.originPoint().x())
                                            + Math.atan2(randomBMTCluster.endPoint().y(), randomBMTCluster.endPoint().x())) / 2;
                                    List<Double> clsFeatures = new ArrayList<>(Arrays.asList(r, phi, (double) (randomNumberBMTLayer + 3), 1.));
                                    map_fakeBMTCluster.put(randomNumberBMTLayer + 3, clsFeatures);
                                }
                                
                                for (int l = 1; l <= 9; l++) {
                                    List<Double> features = map_fakeBMTCluster.get(l);
                                    if(l <= 3){
                                        String crsInfo = String.format("%.4f,%.4f,%.4f,%d,%d", 
                                                features.get(0), features.get(1), features.get(2), features.get(3).intValue(),features.get(4).intValue());
                                        writer.write(crsInfo + ",");  
                                    }
                                    else{
                                        String clsInfo = String.format("%.4f,%.4f,%d,%d", 
                                                features.get(0), features.get(1), features.get(2).intValue(),features.get(3).intValue());
                                        writer.write(clsInfo + ",");  
                                    }
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
}