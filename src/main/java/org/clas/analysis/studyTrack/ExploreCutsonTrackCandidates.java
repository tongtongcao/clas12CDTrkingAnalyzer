package org.clas.analysis.studyTrack;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
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
import org.clas.element.MCParticle;
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
public class ExploreCutsonTrackCandidates extends BaseAnalysis{
    
    private double zMin = -7;
    private double zMax = -1;
    private double pMin = 0.3;
    private double thetaMin = 35; // degree
    private double chi2OverNDFMax = 10;
               
    public ExploreCutsonTrackCandidates(){}
    
    @Override
    public void createHistoGroupMap(){   
        HistoGroup histoGroupNumCrosses = new HistoGroup("numCrosses", 2, 3);
        H1F h1_numCrosses = new H1F("numCrosses", "numCrosses", 9, 0.5, 9.5);
        h1_numCrosses.setTitleX("# of crosses");
        h1_numCrosses.setTitleY("counts"); 
        histoGroupNumCrosses.addDataSet(h1_numCrosses, 0);
        
        H1F h1_numBSTCrosses = new H1F("numBSTCrosses", "numBSTCrosses", 4, -0.5, 3.5);
        h1_numBSTCrosses.setTitleX("# of crosses");
        h1_numBSTCrosses.setTitleY("counts"); 
        histoGroupNumCrosses.addDataSet(h1_numBSTCrosses, 1);
        
        H1F h1_numBMTCrosses = new H1F("numBMTCrosses", "numBMTCrosses", 7, -0.5, 6.5);
        h1_numBMTCrosses.setTitleX("# of crosses");
        h1_numBMTCrosses.setTitleY("counts"); 
        histoGroupNumCrosses.addDataSet(h1_numBMTCrosses, 2);   
        
        H1F h1_numBMTZCrosses = new H1F("numBMTZCrosses", "numBMTZCrosses", 4, -0.5, 3.5);
        h1_numBMTZCrosses.setTitleX("# of crosses");
        h1_numBMTZCrosses.setTitleY("counts"); 
        histoGroupNumCrosses.addDataSet(h1_numBMTZCrosses, 3);      
        
        H1F h1_numBMTCCrosses = new H1F("numBMTCCrosses", "numBMTCCrosses", 4, -0.5, 3.5);
        h1_numBMTCCrosses.setTitleX("# of crosses");
        h1_numBMTCCrosses.setTitleY("counts"); 
        histoGroupNumCrosses.addDataSet(h1_numBMTCCrosses, 4);                    
        histoGroupMap.put(histoGroupNumCrosses.getName(), histoGroupNumCrosses);  
        
        HistoGroup histoGroupBSTSection= new HistoGroup("bstSection", 3, 3);        
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                H1F h1_bstSection = new H1F("bstSectors for R" + Integer.toString(j + 1) + "S" + Integer.toString(i+1), "bstSectors for R" + Integer.toString(j + 1) + "S" + Integer.toString(i+1), Constants.BSTSECTORS[j*2], 0.5, Constants.BSTSECTORS[j*2]+0.5);
                h1_bstSection.setTitleX("sector");
                h1_bstSection.setTitleY("counts"); 
                histoGroupBSTSection.addDataSet(h1_bstSection, i*3 + j);
            }
        }     
        histoGroupMap.put(histoGroupBSTSection.getName(), histoGroupBSTSection);     
        
        HistoGroup histoGroupPhi = new HistoGroup("phi", 2, 3);
        H2F h2_phiDiffVsregionNoSkip = new H2F("phiDiffVsregionNoSkip", "phiDiffVsregionNoSkip", 5, 0.5, 5.5, 100, -45, 45);
        h2_phiDiffVsregionNoSkip.setTitleX("former region");         
        h2_phiDiffVsregionNoSkip.setTitleY("phi diff between neighbored region (deg)");
        histoGroupPhi.addDataSet(h2_phiDiffVsregionNoSkip, 0);        
        H2F h2_phiDiffVsregion1Skip = new H2F("phiDiffVsregion1Skip", "phiDiffVsregion1Skip", 4, 0.5, 4.5, 100, -45, 45);
        h2_phiDiffVsregion1Skip.setTitleX("former region"); 
        h2_phiDiffVsregion1Skip.setTitleY("phi diff between 1-skipped region (deg)");
        histoGroupPhi.addDataSet(h2_phiDiffVsregion1Skip, 1);        
        H2F h2_phiDiffVsregion2Skip = new H2F("phiDiffVsregion2Skip", "phiDiffVsregion2Skip", 3, 0.5, 3.5, 100, -45, 45);
        h2_phiDiffVsregion2Skip.setTitleX("former region");         
        h2_phiDiffVsregion2Skip.setTitleY("phi diff between 2-skipped region (deg)");
        histoGroupPhi.addDataSet(h2_phiDiffVsregion2Skip, 2);           
        H2F h2_phiDiffVsregion3Skip = new H2F("phiDiffVsregion3Skip", "phiDiffVsregion3Skip", 2, 0.5, 2.5, 100, -45, 45);
        h2_phiDiffVsregion3Skip.setTitleX("former region");         
        h2_phiDiffVsregion3Skip.setTitleY("phi diff between 3-skipped region (deg)");
        histoGroupPhi.addDataSet(h2_phiDiffVsregion3Skip, 3);    
        H2F h2_phiDiffVsregion4Skip = new H2F("phiDiffVsregion4Skip", "phiDiffVsregion4Skip", 1, 0.5, 1.5, 100, -45, 45);
        h2_phiDiffVsregion4Skip.setTitleX("former region");         
        h2_phiDiffVsregion4Skip.setTitleY("phi diff between 3-skipped region (deg)");
        histoGroupPhi.addDataSet(h2_phiDiffVsregion4Skip, 4);          
        H1F h1_maxPhiDiff = new H1F("maxPhiDiff", "maxPhiDiff", 100, 0, 45);
        h1_maxPhiDiff.setTitleX("max of phi diff among crosses on tracks (deg)");
        h1_maxPhiDiff.setTitleY("counts"); 
        histoGroupPhi.addDataSet(h1_maxPhiDiff, 5);
        histoGroupMap.put(histoGroupPhi.getName(), histoGroupPhi);  
        
        HistoGroup histoGroupZ = new HistoGroup("z", 2, 3);        
        H2F h2_zDiffVsregionNoSkip = new H2F("zDiffVsregionNoSkip", "zDiffVsregionNoSkip", 5, 0.5, 5.5, 100, -25, 25);
        h2_zDiffVsregionNoSkip.setTitleX("former region");         
        h2_zDiffVsregionNoSkip.setTitleY("z diff between neighbored region (cm)");
        histoGroupZ.addDataSet(h2_zDiffVsregionNoSkip, 0);        
        H2F h2_zDiffVsregion1Skip = new H2F("zDiffVsregion1Skip", "zDiffVsregion1Skip", 4, 0.5, 4.5, 100, -25, 25);
        h2_zDiffVsregion1Skip.setTitleX("former region"); 
        h2_zDiffVsregion1Skip.setTitleY("z diff between 1-skipped region (cm)");
        histoGroupZ.addDataSet(h2_zDiffVsregion1Skip, 1);        
        H2F h2_zDiffVsregion2Skip = new H2F("zDiffVsregion2Skip", "zDiffVsregion2Skip", 3, 0.5, 3.5, 100, -25, 25);
        h2_zDiffVsregion2Skip.setTitleX("former region");         
        h2_zDiffVsregion2Skip.setTitleY("z diff between 2-skipped region (cm)");
        histoGroupZ.addDataSet(h2_zDiffVsregion2Skip, 2);           
        H2F h2_zDiffVsregion3Skip = new H2F("zDiffVsregion3Skip", "zDiffVsregion3Skip", 2, 0.5, 2.5, 100, -25, 25);
        h2_zDiffVsregion3Skip.setTitleX("former region");         
        h2_zDiffVsregion3Skip.setTitleY("z diff between 3-skipped region (cm)");
        histoGroupZ.addDataSet(h2_zDiffVsregion3Skip, 3);   
        H2F h2_zDiffVsregion4Skip = new H2F("zDiffVsregion4Skip", "zDiffVsregion4Skip", 1, 0.5, 1.5, 100, -25, 25);
        h2_zDiffVsregion4Skip.setTitleX("former region");         
        h2_zDiffVsregion4Skip.setTitleY("z diff between 4-skipped region (cm)");
        histoGroupZ.addDataSet(h2_zDiffVsregion4Skip, 4);         
        H1F h1_maxZDiff = new H1F("maxZDiff", "maxZDiff", 100, 0, 25);
        h1_maxZDiff.setTitleX("max of z diff among crosses on tracks (cm)");
        h1_maxZDiff.setTitleY("counts");         
        histoGroupZ.addDataSet(h1_maxZDiff, 5);
        histoGroupMap.put(histoGroupZ.getName(), histoGroupZ);                         
    }
             
    public void processEvent(Event event){        
        //Read banks
        LocalEvent localEvent = new LocalEvent(reader, event); 
        
        List<Seed> seeds = localEvent.getSeeds();
        List<Track> tracksPass1 = localEvent.getTracks(1, false);              
        List<Track> tracksPass2 = localEvent.getTracks(2, false);                                      
        
        HistoGroup histoGroupNumCrosses = histoGroupMap.get("numCrosses");
        HistoGroup histoGroupBSTSection = histoGroupMap.get("bstSection");  
        HistoGroup histoGroupPhi = histoGroupMap.get("phi");  
        HistoGroup histoGroupZ = histoGroupMap.get("z"); 
        for(Track trk : tracksPass2){
            if(trk.isValid(zMin, zMax, pMin, thetaMin/180.*Math.PI, chi2OverNDFMax)){
                if(!trk.getBMTClusters().isEmpty()){
                    for(Cluster cls : trk.getBSTClusters()){
                        histoGroupBSTSection.getH1F("bstSectors for R" + Integer.toString((cls.layer()+1)/2) + "S" + Integer.toString(trk.getBMTClusters().get(0).sector())).fill(cls.sector());
                    }                             
                }
                                
                Map<Integer, Double> map_region_phi = new HashMap();
                Map<Integer, Double> map_region_z = new HashMap();
                for(Cross crs : trk.getBSTCrosses()){
                    double phi = crs.point().toVector3D().phi()/Math.PI * 180;
                    map_region_phi.put(crs.region(), phi);
                    map_region_z.put(crs.region(), crs.point().z());
                }
                
                int numBMTZ = 0; 
                int numBMTC = 0;
                for(Cluster cls : trk.getBMTClusters()){
                    if(cls.bmtType() == Constants.BMTZ){
                        map_region_phi.put((cls.layer()+1)/2 + 3, cls.centroidValue()/Math.PI * 180);
                        numBMTZ++;
                    }
                    else{
                        double z = (cls.originPoint().z() + cls.endPoint().z()) / 2.;
                        map_region_z.put((cls.layer()+1)/2 + 3, z);
                        numBMTC++;
                    }
                }
                
                List<Integer> keyListPhiMap = new ArrayList<>(map_region_phi.keySet());                
                for(int i = 0; i < keyListPhiMap.size() - 1; i++){
                    int region1 = keyListPhiMap.get(i);
                    double phi1 = map_region_phi.get(region1);
                    for(int j = i + 1; j < keyListPhiMap.size(); j++){
                        int region2 = keyListPhiMap.get(j);
                        double phi2 = map_region_phi.get(region2);
                        int deltaRegion = region2 - region1;
                        double deltaPhi = phi2 - phi1;
                        if(deltaRegion == 1) histoGroupPhi.getH2F("phiDiffVsregionNoSkip").fill(region1, deltaPhi);
                        else if(deltaRegion == 2) histoGroupPhi.getH2F("phiDiffVsregion1Skip").fill(region1, deltaPhi);
                        else if(deltaRegion == 3) histoGroupPhi.getH2F("phiDiffVsregion2Skip").fill(region1, deltaPhi);
                        else if(deltaRegion == 4) histoGroupPhi.getH2F("phiDiffVsregion3Skip").fill(region1, deltaPhi);
                        else if(deltaRegion == 5) histoGroupPhi.getH2F("phiDiffVsregion4Skip").fill(region1, deltaPhi);
                    }
                }
                
                double minPhi = Collections.min(map_region_phi.values());
                double maxPhi = Collections.max(map_region_phi.values());
                histoGroupPhi.getH1F("maxPhiDiff").fill(maxPhi - minPhi);
                
                List<Integer> keyListZMap = new ArrayList<>(map_region_z.keySet());                
                for(int i = 0; i < keyListZMap.size() - 1; i++){
                    int region1 = keyListZMap.get(i);
                    double z1 = map_region_z.get(region1);
                    for(int j = i + 1; j < keyListZMap.size(); j++){
                        int region2 = keyListZMap.get(j);
                        double z2 = map_region_z.get(region2);
                        int deltaRegion = region2 - region1;
                        double deltaZ = z2 - z1;
                        if(deltaRegion == 1) histoGroupZ.getH2F("zDiffVsregionNoSkip").fill(region1, deltaZ);
                        else if(deltaRegion == 2) histoGroupZ.getH2F("zDiffVsregion1Skip").fill(region1, deltaZ);
                        else if(deltaRegion == 3) histoGroupZ.getH2F("zDiffVsregion2Skip").fill(region1, deltaZ);
                        else if(deltaRegion == 4) histoGroupZ.getH2F("zDiffVsregion3Skip").fill(region1, deltaZ);
                        else if(deltaRegion == 5) histoGroupZ.getH2F("zDiffVsregion4Skip").fill(region1, deltaZ);
                    }
                }                
                
                double minZ = Collections.min(map_region_z.values());
                double maxZ = Collections.max(map_region_z.values());
                histoGroupZ.getH1F("maxZDiff").fill(maxZ - minZ);    
                
                histoGroupNumCrosses.getH1F("numCrosses").fill(trk.getBSTCrosses().size() + trk.getBMTClusters().size());

                histoGroupNumCrosses.getH1F("numBSTCrosses").fill(trk.getBSTCrosses().size());
                histoGroupNumCrosses.getH1F("numBMTCrosses").fill(trk.getBMTClusters().size());
                histoGroupNumCrosses.getH1F("numBMTZCrosses").fill(numBMTZ);
                histoGroupNumCrosses.getH1F("numBMTCCrosses").fill(numBMTC);
            }            
        }                 
        

            
        
    }    
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("exploreCutsonTrackCandidates");
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
        ExploreCutsonTrackCandidates analysis = new ExploreCutsonTrackCandidates();
        analysis.createHistoGroupMap();        
        
        if(!readHistos) {                 
            HipoReader reader = new HipoReader();
            reader.open(inputList.get(0));

            SchemaFactory schema = reader.getSchemaFactory();
            analysis.initReader(new Banks(schema));

            int counter = 0;
            Event event = new Event();
        
            ProgressPrintout progress = new ProgressPrintout();
            while (reader.hasNext()) {

                counter++;

                reader.nextEvent(event);              
                analysis.processEvent(event);
                progress.updateStatus();
                if(maxEvents>0){
                    if(counter>=maxEvents) break;
                }                    
            }           
            
            progress.showStatus();
            reader.close(); 
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
