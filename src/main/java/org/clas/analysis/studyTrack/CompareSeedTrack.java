package org.clas.analysis.studyTrack;

import java.util.List;
import java.util.ArrayList;
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
public class CompareSeedTrack extends BaseAnalysis{ 
    
       
    public CompareSeedTrack(){}
    
    @Override
    public void createHistoGroupMap(){         
        TrackHistoGroup histoGroupCompare = new TrackHistoGroup("compare", 3, 5);
        histoGroupCompare.addTrackHistos("trackPass1", 1, 0);
        histoGroupCompare.addTrackHistos("trackPass2", 2, 0);
        histoGroupCompare.addTrackHistos("seed", 3, 0);        
        histoGroupMap.put(histoGroupCompare.getName(), histoGroupCompare);  
        
        TrackHistoGroup histoGroupSeedTrackPass1Diff = new TrackHistoGroup("seedTrackPass1Diff", 3, 5);
        histoGroupSeedTrackPass1Diff.addTrackDiffHistos(1, 0);
        histoGroupMap.put(histoGroupSeedTrackPass1Diff.getName(), histoGroupSeedTrackPass1Diff);  
        
        TrackHistoGroup histoGroupSeedTrackPass2Diff = new TrackHistoGroup("seedTrackPass2Diff", 3, 5);
        histoGroupSeedTrackPass2Diff.addTrackDiffHistos(1, 0);
        histoGroupMap.put(histoGroupSeedTrackPass2Diff.getName(), histoGroupSeedTrackPass2Diff);          
    }
             
    public void processEvent(Event event){        
        //Read banks
        LocalEvent localEvent = new LocalEvent(reader, event); 
        
        List<Seed> seeds = localEvent.getSeeds();
        List<Track> tracksPass1 = localEvent.getTracks(1, false);              
        List<Track> tracksPass2 = localEvent.getTracks(2, false);                                      
        
        // tracks for pass1
        TrackHistoGroup histoGroupCompare = (TrackHistoGroup) histoGroupMap.get("compare");  
        for(Seed seed : seeds){
            histoGroupCompare.getHistoP("seed").fill(seed.momentum().mag());
            histoGroupCompare.getHistoTheta("seed").fill(seed.momentum().theta());
            histoGroupCompare.getHistoPhi("seed").fill(seed.momentum().phi());            
            histoGroupCompare.getHistoVx("seed").fill(seed.vertex().x());
            histoGroupCompare.getHistoVy("seed").fill(seed.vertex().y());
            histoGroupCompare.getHistoVz("seed").fill(seed.vertex().z());            
            histoGroupCompare.getHistoQ("seed").fill(seed.q());
            histoGroupCompare.getHistoTandip("seed").fill(seed.tandip());
            histoGroupCompare.getHistoZ0("seed").fill(seed.z0());
            histoGroupCompare.getHistoPt("seed").fill(seed.pt());
            histoGroupCompare.getHistoPhi0("seed").fill(seed.phi0());
            histoGroupCompare.getHistoD0("seed").fill(seed.d0());
        }         
        
        for(Track trk : tracksPass1){
            histoGroupCompare.getHistoNKFIters("trackPass1").fill(trk.nKFIters());
            histoGroupCompare.getHistoChi2overndf("trackPass1").fill(trk.chi2()/trk.ndf());
            histoGroupCompare.getHistoNDF("trackPass1").fill(trk.ndf());
            histoGroupCompare.getHistoP("trackPass1").fill(trk.momentum().mag());
            histoGroupCompare.getHistoTheta("trackPass1").fill(trk.momentum().theta());
            histoGroupCompare.getHistoPhi("trackPass1").fill(trk.momentum().phi());            
            histoGroupCompare.getHistoVx("trackPass1").fill(trk.vertex().x());
            histoGroupCompare.getHistoVy("trackPass1").fill(trk.vertex().y());
            histoGroupCompare.getHistoVz("trackPass1").fill(trk.vertex().z());            
            histoGroupCompare.getHistoQ("trackPass1").fill(trk.q());
            histoGroupCompare.getHistoTandip("trackPass1").fill(trk.tandip());
            histoGroupCompare.getHistoZ0("trackPass1").fill(trk.z0());
            histoGroupCompare.getHistoPt("trackPass1").fill(trk.pt());
            histoGroupCompare.getHistoPhi0("trackPass1").fill(trk.phi0());
            histoGroupCompare.getHistoD0("trackPass1").fill(trk.d0());            
        }        
        for(Track trk : tracksPass2){
            histoGroupCompare.getHistoNKFIters("trackPass2").fill(trk.nKFIters());
            histoGroupCompare.getHistoChi2overndf("trackPass2").fill(trk.chi2()/trk.ndf());
            histoGroupCompare.getHistoNDF("trackPass2").fill(trk.ndf());
            histoGroupCompare.getHistoP("trackPass2").fill(trk.momentum().mag());
            histoGroupCompare.getHistoTheta("trackPass2").fill(trk.momentum().theta());
            histoGroupCompare.getHistoPhi("trackPass2").fill(trk.momentum().phi());            
            histoGroupCompare.getHistoVx("trackPass2").fill(trk.vertex().x());
            histoGroupCompare.getHistoVy("trackPass2").fill(trk.vertex().y());
            histoGroupCompare.getHistoVz("trackPass2").fill(trk.vertex().z());            
            histoGroupCompare.getHistoQ("trackPass2").fill(trk.q());
            histoGroupCompare.getHistoTandip("trackPass2").fill(trk.tandip());
            histoGroupCompare.getHistoZ0("trackPass2").fill(trk.z0());
            histoGroupCompare.getHistoPt("trackPass2").fill(trk.pt());
            histoGroupCompare.getHistoPhi0("trackPass2").fill(trk.phi0());
            histoGroupCompare.getHistoD0("trackPass2").fill(trk.d0());             
        } 
        
        TrackHistoGroup histoGroupSeedTrackPass1Diff = (TrackHistoGroup) histoGroupMap.get("seedTrackPass1Diff");  
        for(Seed seed : seeds){
            for(Track trk : tracksPass1){
                if(seed.isSameClusterswithTrack(trk)){
                    histoGroupSeedTrackPass1Diff.getHistoPDiff().fill(trk.momentum().mag() - seed.momentum().mag());
                    histoGroupSeedTrackPass1Diff.getHistoThetaDiff().fill(trk.momentum().theta() - seed.momentum().theta());
                    histoGroupSeedTrackPass1Diff.getHistoPhiDiff().fill(trk.momentum().phi() - seed.momentum().phi());            
                    histoGroupSeedTrackPass1Diff.getHistoVxDiff().fill(trk.vertex().x() - seed.vertex().x());
                    histoGroupSeedTrackPass1Diff.getHistoVyDiff().fill(trk.vertex().y() - seed.vertex().y());
                    histoGroupSeedTrackPass1Diff.getHistoVzDiff().fill(trk.vertex().z() - seed.vertex().z());                    
                    histoGroupSeedTrackPass1Diff.getHistoQDiff().fill(trk.q() - seed.q());  
                    histoGroupSeedTrackPass1Diff.getHistoTandipDiff().fill(trk.tandip() - seed.tandip());   
                    histoGroupSeedTrackPass1Diff.getHistoZ0Diff().fill(trk.z0() - seed.z0());   
                    histoGroupSeedTrackPass1Diff.getHistoPtDiff().fill(trk.pt() - seed.pt());   
                    histoGroupSeedTrackPass1Diff.getHistoPhi0Diff().fill(trk.phi0() - seed.phi0()); 
                    histoGroupSeedTrackPass1Diff.getHistoD0Diff().fill(trk.d0() - seed.d0());  
                }
            }
        }
        
        TrackHistoGroup histoGroupSeedTrackPass2Diff = (TrackHistoGroup) histoGroupMap.get("seedTrackPass2Diff");  
        for(Seed seed : seeds){
            for(Track trk : tracksPass2){
                if(seed.isSameClusterswithTrack(trk)){
                    histoGroupSeedTrackPass2Diff.getHistoPDiff().fill(trk.momentum().mag() - seed.momentum().mag());
                    histoGroupSeedTrackPass2Diff.getHistoThetaDiff().fill(trk.momentum().theta() - seed.momentum().theta());
                    histoGroupSeedTrackPass2Diff.getHistoPhiDiff().fill(trk.momentum().phi() - seed.momentum().phi());            
                    histoGroupSeedTrackPass2Diff.getHistoVxDiff().fill(trk.vertex().x() - seed.vertex().x());
                    histoGroupSeedTrackPass2Diff.getHistoVyDiff().fill(trk.vertex().y() - seed.vertex().y());
                    histoGroupSeedTrackPass2Diff.getHistoVzDiff().fill(trk.vertex().z() - seed.vertex().z());                    
                    histoGroupSeedTrackPass2Diff.getHistoQDiff().fill(trk.q() - seed.q());  
                    histoGroupSeedTrackPass2Diff.getHistoTandipDiff().fill(trk.tandip() - seed.tandip());   
                    histoGroupSeedTrackPass2Diff.getHistoZ0Diff().fill(trk.z0() - seed.z0());   
                    histoGroupSeedTrackPass2Diff.getHistoPtDiff().fill(trk.pt() - seed.pt());   
                    histoGroupSeedTrackPass2Diff.getHistoPhi0Diff().fill(trk.phi0() - seed.phi0()); 
                    histoGroupSeedTrackPass2Diff.getHistoD0Diff().fill(trk.d0() - seed.d0());  
                }
            }
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
        CompareSeedTrack analysis = new CompareSeedTrack();
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
            
            analysis.postEventProcess();
            
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
