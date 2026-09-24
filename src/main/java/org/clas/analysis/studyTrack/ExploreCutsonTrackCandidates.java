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
        
        HistoGroup histoGroupSVTSection= new HistoGroup("svtSection", 3, 3);
        int[] maxSectors = {10, 14, 18};
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                H1F h1_svtSection = new H1F("svtSectors for R" + Integer.toString(j + 1) + "S" + Integer.toString(i+1), "svtSectors for R" + Integer.toString(j + 1) + "S" + Integer.toString(i+1), maxSectors[j], 0.5, maxSectors[j]+0.5);
                h1_svtSection.setTitleX("sector");
                h1_svtSection.setTitleY("counts"); 
                histoGroupSVTSection.addDataSet(h1_svtSection, i*3 + j);
            }
        }     
        histoGroupMap.put(histoGroupSVTSection.getName(), histoGroupSVTSection);     
        
        HistoGroup histoGroupPhi = new HistoGroup("phi", 1, 1);
        H1F h1_maxPhiDiff = new H1F("maxPhiDiff", "maxPhiDiff", 100, 0, 40);
        h1_maxPhiDiff.setTitleX("max of phi diff among crosses on tracks (deg)");
        h1_maxPhiDiff.setTitleY("counts"); 
        histoGroupPhi.addDataSet(h1_maxPhiDiff, 0);
        histoGroupMap.put(histoGroupPhi.getName(), histoGroupPhi);  
        
        HistoGroup histoGroupZ = new HistoGroup("z", 1, 1);
        H1F h1_maxZDiff = new H1F("maxZDiff", "maxZDiff", 100, 0, 40);
        h1_maxZDiff.setTitleX("max of z diff among crosses on tracks (cm)");
        h1_maxZDiff.setTitleY("counts");         
        histoGroupZ.addDataSet(h1_maxZDiff, 0);
        histoGroupMap.put(histoGroupZ.getName(), histoGroupZ);          
    }
             
    public void processEvent(Event event){        
        //Read banks
        LocalEvent localEvent = new LocalEvent(reader, event); 
        
        List<Seed> seeds = localEvent.getSeeds();
        List<Track> tracksPass1 = localEvent.getTracks(1, false);              
        List<Track> tracksPass2 = localEvent.getTracks(2, false);                                      
        
        HistoGroup histoGroupNumCrosses = histoGroupMap.get("numCrosses");
        HistoGroup histoGroupSVTSection = histoGroupMap.get("svtSection");  
        HistoGroup histoGroupPhi = histoGroupMap.get("phi");  
        HistoGroup histoGroupZ = histoGroupMap.get("z"); 
        for(Track trk : tracksPass2){
            if(trk.isValid()){
                if(!trk.getBMTClusters().isEmpty()){
                    for(Cluster cls : trk.getBSTClusters()){
                        histoGroupSVTSection.getH1F("svtSectors for R" + Integer.toString((cls.layer()+1)/2) + "S" + Integer.toString(trk.getBMTClusters().get(0).sector())).fill(cls.sector());
                    }                             
                }
                                
                List<Double> phiList = new ArrayList();
                List<Double> zList = new ArrayList();
                for(Cross crs : trk.getBSTCrosses()){
                    double phi = crs.point().toVector3D().phi()/Math.PI * 180;
                    phiList.add(phi);
                    zList.add(crs.point().z());
                }
                
                int numBMTZ = 0; 
                int numBMTC = 0;
                for(Cluster cls : trk.getBMTClusters()){
                    if(cls.bmtType() == Constants.BMTZ){
                        phiList.add(cls.centroidValue()/Math.PI * 180);
                        numBMTZ++;
                    }
                    else{
                        double z = (cls.originPoint().z() + cls.endPoint().z()) / 2.;
                        zList.add(z);
                        numBMTC++;
                    }
                }
                
                double minPhi = Collections.min(phiList);
                double maxPhi = Collections.max(phiList);
                histoGroupPhi.getH1F("maxPhiDiff").fill(maxPhi - minPhi);
                
                double minZ = Collections.min(zList);
                double maxZ = Collections.max(zList);
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
