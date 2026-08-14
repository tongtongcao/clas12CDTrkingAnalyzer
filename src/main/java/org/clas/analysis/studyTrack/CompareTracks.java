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
public class CompareTracks extends BaseAnalysis{ 
    
    private static int trackPass1Sp1 = 0;
    private static int trackPass1Sp2 = 0;        
    private static int trackPass2Sp1 = 0;
    private static int trackPass2Sp2 = 0;    
    private static int uTrackPass2Sp1 = 0;
    private static int uTrackPass2Sp2 = 0;   
    
    private static int trackPass1_sameSeedPair = 0;
    private static int trackPass2_sameSeedPair = 0;
    private static int uTrackPass2_sameSeedPair = 0;
    
    private boolean mcSingle = false;
       
    public CompareTracks(){}
    
    @Override
    public void createHistoGroupMap(){         
        // tracks for pass1
        TrackHistoGroup histoGroupTrackPass1 = new TrackHistoGroup("trackPass1", 3, 3);
        histoGroupTrackPass1.addTrackHistos("evt1", 1, 0);
        histoGroupTrackPass1.addTrackHistos("evt2", 2, 0);
        histoGroupMap.put(histoGroupTrackPass1.getName(), histoGroupTrackPass1); 
        
        TrackHistoGroup histoGroupTrackPass1DiffSameSeed = new TrackHistoGroup("trackPass1DiffSameSeed", 3, 3);
        histoGroupTrackPass1DiffSameSeed.addTrackDiffHistos(1, 0);
        histoGroupMap.put(histoGroupTrackPass1DiffSameSeed.getName(), histoGroupTrackPass1DiffSameSeed); 
        
        TrackHistoGroup histoGroupTrackPass1ExcludeSameSeed = new TrackHistoGroup("trackPass1ExcludeSameSeed", 3, 3);
        histoGroupTrackPass1ExcludeSameSeed.addTrackHistos("evt1", 1, 0);
        histoGroupTrackPass1ExcludeSameSeed.addTrackHistos("evt2", 2, 0);
        histoGroupMap.put(histoGroupTrackPass1ExcludeSameSeed.getName(), histoGroupTrackPass1ExcludeSameSeed);         
        
        // tracks for pass2
        TrackHistoGroup histoGroupTrackPass2 = new TrackHistoGroup("trackPass2", 3, 3);
        histoGroupTrackPass2.addTrackHistos("evt1", 1, 0);
        histoGroupTrackPass2.addTrackHistos("evt2", 2, 0);
        histoGroupMap.put(histoGroupTrackPass2.getName(), histoGroupTrackPass2);  
        
        TrackHistoGroup histoGroupTrackPass2DiffSameSeed = new TrackHistoGroup("trackPass2DiffSameSeed", 3, 3);
        histoGroupTrackPass2DiffSameSeed.addTrackDiffHistos(1, 0);
        histoGroupMap.put(histoGroupTrackPass2DiffSameSeed.getName(), histoGroupTrackPass2DiffSameSeed);  
        
        TrackHistoGroup histoGroupTrackPass2ExcludeSameSeed = new TrackHistoGroup("trackPass2ExcludeSameSeed", 3, 3);
        histoGroupTrackPass2ExcludeSameSeed.addTrackHistos("evt1", 1, 0);
        histoGroupTrackPass2ExcludeSameSeed.addTrackHistos("evt2", 2, 0);
        histoGroupMap.put(histoGroupTrackPass2ExcludeSameSeed.getName(), histoGroupTrackPass2ExcludeSameSeed); 
        
        HistoGroup histoTracksPass2ReconTruthDiffExcludeSameSeedGroup = new HistoGroup("tracksPass2ReconTruthDiffExcludeSameSeed", 3, 2);
        H1F h1_p_tracksPass2ReconTruthDiffExcludeSameSeed_evt1 = new H1F("p_tracksPass2ReconTruthDiffExcludeSameSeed_evt1", "#Deltap/p", 100, -0.5, 0.5);
        h1_p_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setTitleX("#Deltap/p");
        h1_p_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setTitleY("Counts");
        h1_p_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setLineColor(1);
        H1F h1_theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt1 = new H1F("theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt1", "#Delta#theta", 100, -5, 5);
        h1_theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setTitleX("#Delta#theta (deg)");
        h1_theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setTitleY("Counts");
        h1_theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setLineColor(1);
        H1F h1_phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt1 = new H1F("phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt1", "#Delta#phi", 100, -2, 2);
        h1_phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setTitleX("#Delta#phi (deg)");
        h1_phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setTitleY("Counts");
        h1_phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setLineColor(1);
        H1F h1_vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt1 = new H1F("vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt1", "#DeltaV_x", 100, -0.1, 0.1);
        h1_vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setTitleX("#DeltaV_x (cm)");
        h1_vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setTitleY("Counts");  
        h1_vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setLineColor(1);
        H1F h1_vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt1 = new H1F("vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt1", "#DeltaV_y", 100, -0.1, 0.1);
        h1_vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setTitleX("#DeltaV_y (cm)");
        h1_vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setTitleY("Counts");  
        h1_vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setLineColor(1);
        H1F h1_vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt1 = new H1F("vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt1", "#DeltaV_z", 100, -1, 1);
        h1_vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setTitleX("#DeltaV_z (cm)");
        h1_vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setTitleY("Counts");   
        h1_vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt1.setLineColor(1);
        histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.addDataSet(h1_p_tracksPass2ReconTruthDiffExcludeSameSeed_evt1, 0);
        histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.addDataSet(h1_theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt1, 1);
        histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.addDataSet(h1_phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt1, 2);
        histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.addDataSet(h1_vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt1, 3);
        histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.addDataSet(h1_vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt1, 4);
        histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.addDataSet(h1_vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt1, 5);          
        H1F h1_p_tracksPass2ReconTruthDiffExcludeSameSeed_evt2 = new H1F("p_tracksPass2ReconTruthDiffExcludeSameSeed_evt2", "#Deltap/p", 100, -0.5, 0.5);
        h1_p_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setTitleX("#Deltap/p");
        h1_p_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setTitleY("Counts");
        h1_p_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setLineColor(2);
        H1F h1_theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt2 = new H1F("theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt2", "#Delta#theta", 100, -5, 5);
        h1_theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setTitleX("#Delta#theta (deg)");
        h1_theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setTitleY("Counts");
        h1_theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setLineColor(2);
        H1F h1_phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt2 = new H1F("phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt2", "#Delta#phi", 100, -2, 2);
        h1_phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setTitleX("#Delta#phi (deg)");
        h1_phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setTitleY("Counts");
        h1_phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setLineColor(2);
        H1F h1_vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt2 = new H1F("vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt2", "#DeltaV_x", 100, -0.1, 0.1);
        h1_vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setTitleX("#DeltaV_x (cm)");
        h1_vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setTitleY("Counts");  
        h1_vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setLineColor(2);
        H1F h1_vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt2 = new H1F("vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt2", "#DeltaV_y", 100, -0.1, 0.1);
        h1_vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setTitleX("#DeltaV_y (cm)");
        h1_vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setTitleY("Counts");  
        h1_vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setLineColor(2);
        H1F h1_vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt2 = new H1F("vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt2", "#DeltaV_z", 100, -1, 1);
        h1_vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setTitleX("#DeltaV_z (cm)");
        h1_vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setTitleY("Counts");   
        h1_vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt2.setLineColor(2);
        histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.addDataSet(h1_p_tracksPass2ReconTruthDiffExcludeSameSeed_evt2, 0);
        histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.addDataSet(h1_theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt2, 1);
        histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.addDataSet(h1_phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt2, 2);
        histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.addDataSet(h1_vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt2, 3);
        histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.addDataSet(h1_vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt2, 4);
        histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.addDataSet(h1_vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt2, 5);                 
        histoGroupMap.put(histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getName(), histoTracksPass2ReconTruthDiffExcludeSameSeedGroup);         
        
        // utracks for pass2
        TrackHistoGroup histoGroupUTrackPass2 = new TrackHistoGroup("uTrackPass2", 3, 3);
        histoGroupUTrackPass2.addTrackHistos("evt1", 1, 0);
        histoGroupUTrackPass2.addTrackHistos("evt2", 2, 0);
        histoGroupMap.put(histoGroupUTrackPass2.getName(), histoGroupUTrackPass2); 
        
        TrackHistoGroup histoGroupUTrackPass2DiffSameSeed = new TrackHistoGroup("uTrackPass2DiffSameSeed", 3, 3);
        histoGroupUTrackPass2DiffSameSeed.addTrackDiffHistos(1, 0);
        histoGroupMap.put(histoGroupUTrackPass2DiffSameSeed.getName(), histoGroupUTrackPass2DiffSameSeed);  
        
        TrackHistoGroup histoGroupUTrackPass2ExcludeSameSeed = new TrackHistoGroup("uTrackPass2ExcludeSameSeed", 3, 3);
        histoGroupUTrackPass2ExcludeSameSeed.addTrackHistos("evt1", 1, 0);
        histoGroupUTrackPass2ExcludeSameSeed.addTrackHistos("evt2", 2, 0);
        histoGroupMap.put(histoGroupUTrackPass2ExcludeSameSeed.getName(), histoGroupUTrackPass2ExcludeSameSeed);                                                                                         
    }
             
    public void processEvent(Event event1, Event event2){        
        //Read banks
        LocalEvent localEvent1 = new LocalEvent(reader1, event1); 
        LocalEvent localEvent2 = new LocalEvent(reader2, event2);
        
        List<Track> tracksPass1_evt1 = localEvent1.getTracks(1, false);              
        List<Track> tracksPass2_evt1 = localEvent1.getTracks(2, false);              
        List<Track> uTracksPass2_evt1 = localEvent1.getTracks(2, true); 
        
        List<Track> tracksPass1_evt2 = localEvent2.getTracks(1, false);              
        List<Track> tracksPass2_evt2 = localEvent2.getTracks(2, false);             
        List<Track> uTracksPass2_evt2 = localEvent2.getTracks(2, true); 
                        
        trackPass1Sp1 += tracksPass1_evt1.size();
        trackPass1Sp2 += tracksPass1_evt2.size();        
        trackPass2Sp1 += tracksPass2_evt1.size();
        trackPass2Sp2 += tracksPass2_evt2.size();
        uTrackPass2Sp1 += uTracksPass2_evt1.size();
        uTrackPass2Sp2 += uTracksPass2_evt2.size();  
        
        List<MCParticle> mcParts1 = null;
        List<MCParticle> mcParts2 = null;
        if(mcSingle){
            mcParts1 = localEvent1.getMCParticles();
            mcParts2 = localEvent2.getMCParticles();
        }
        
        // tracks for pass1
        TrackHistoGroup histoGroupTrackPass1 = (TrackHistoGroup) histoGroupMap.get("trackPass1");        
        for(Track trk : tracksPass1_evt1){
            histoGroupTrackPass1.getHistoNKFIters("evt1").fill(trk.nKFIters());
            histoGroupTrackPass1.getHistoChi2overndf("evt1").fill(trk.chi2()/trk.ndf());
            histoGroupTrackPass1.getHistoNDF("evt1").fill(trk.ndf());
            histoGroupTrackPass1.getHistoP("evt1").fill(trk.momentum().mag());
            histoGroupTrackPass1.getHistoTheta("evt1").fill(trk.momentum().theta());
            histoGroupTrackPass1.getHistoPhi("evt1").fill(trk.momentum().phi());            
            histoGroupTrackPass1.getHistoVx("evt1").fill(trk.vertex().x());
            histoGroupTrackPass1.getHistoVy("evt1").fill(trk.vertex().y());
            histoGroupTrackPass1.getHistoVz("evt1").fill(trk.vertex().z());
        }        
        for(Track trk : tracksPass1_evt2){
            histoGroupTrackPass1.getHistoNKFIters("evt2").fill(trk.nKFIters());
            histoGroupTrackPass1.getHistoChi2overndf("evt2").fill(trk.chi2()/trk.ndf());
            histoGroupTrackPass1.getHistoNDF("evt2").fill(trk.ndf());
            histoGroupTrackPass1.getHistoP("evt2").fill(trk.momentum().mag());
            histoGroupTrackPass1.getHistoTheta("evt2").fill(trk.momentum().theta());
            histoGroupTrackPass1.getHistoPhi("evt2").fill(trk.momentum().phi());
            histoGroupTrackPass1.getHistoVx("evt2").fill(trk.vertex().x());
            histoGroupTrackPass1.getHistoVy("evt2").fill(trk.vertex().y());
            histoGroupTrackPass1.getHistoVz("evt2").fill(trk.vertex().z());            
        }
        
        Map<Track, Track> map_trackPass1PairSameSeed = new HashMap();
        TrackHistoGroup histoGroupTrackPass1DiffSameSeed = (TrackHistoGroup) histoGroupMap.get("trackPass1DiffSameSeed");  
        for(Track trk1 : tracksPass1_evt1){
            for(Track trk2 : tracksPass1_evt2){
                if(trk1.isSameClusterswithTrack(trk2)){
                    histoGroupTrackPass1DiffSameSeed.getHistoNKFItersDiff().fill(trk2.nKFIters() - trk1.nKFIters());
                    histoGroupTrackPass1DiffSameSeed.getHistoChi2overndfDiff().fill(trk2.chi2()/trk2.ndf() - trk1.chi2()/trk1.ndf());
                    histoGroupTrackPass1DiffSameSeed.getHistoNDFDiff().fill(trk2.ndf() - trk1.ndf());
                    histoGroupTrackPass1DiffSameSeed.getHistoPDiff().fill(trk2.momentum().mag() - trk1.momentum().mag());
                    histoGroupTrackPass1DiffSameSeed.getHistoThetaDiff().fill(trk2.momentum().theta() - trk1.momentum().theta());
                    histoGroupTrackPass1DiffSameSeed.getHistoPhiDiff().fill(trk2.momentum().phi() - trk1.momentum().phi());            
                    histoGroupTrackPass1DiffSameSeed.getHistoVxDiff().fill(trk2.vertex().x() - trk1.vertex().x());
                    histoGroupTrackPass1DiffSameSeed.getHistoVyDiff().fill(trk2.vertex().y() - trk1.vertex().y());
                    histoGroupTrackPass1DiffSameSeed.getHistoVzDiff().fill(trk2.vertex().z() - trk1.vertex().z());
                    
                    map_trackPass1PairSameSeed.put(trk1, trk2);
                    trackPass1_sameSeedPair++;
                    break;
                }
            }
        }
        
        List<Track> tracksPass1_excludeSameSeedTracks_evt1 = new ArrayList();
        tracksPass1_excludeSameSeedTracks_evt1.addAll(tracksPass1_evt1);
        tracksPass1_excludeSameSeedTracks_evt1.removeAll(map_trackPass1PairSameSeed.keySet());
        
        List<Track> tracksPass1_excludeSameSeedTracks_evt2 = new ArrayList();
        tracksPass1_excludeSameSeedTracks_evt2.addAll(tracksPass1_evt2);
        tracksPass1_excludeSameSeedTracks_evt2.removeAll(map_trackPass1PairSameSeed.values());
        
        TrackHistoGroup histoGroupTrackPass1ExcludeSameSeed = (TrackHistoGroup) histoGroupMap.get("trackPass1ExcludeSameSeed");        
        for(Track trk : tracksPass1_excludeSameSeedTracks_evt1){
            histoGroupTrackPass1ExcludeSameSeed.getHistoNKFIters("evt1").fill(trk.nKFIters());
            histoGroupTrackPass1ExcludeSameSeed.getHistoChi2overndf("evt1").fill(trk.chi2()/trk.ndf());
            histoGroupTrackPass1ExcludeSameSeed.getHistoNDF("evt1").fill(trk.ndf());
            histoGroupTrackPass1ExcludeSameSeed.getHistoP("evt1").fill(trk.momentum().mag());
            histoGroupTrackPass1ExcludeSameSeed.getHistoTheta("evt1").fill(trk.momentum().theta());
            histoGroupTrackPass1ExcludeSameSeed.getHistoPhi("evt1").fill(trk.momentum().phi());            
            histoGroupTrackPass1ExcludeSameSeed.getHistoVx("evt1").fill(trk.vertex().x());
            histoGroupTrackPass1ExcludeSameSeed.getHistoVy("evt1").fill(trk.vertex().y());
            histoGroupTrackPass1ExcludeSameSeed.getHistoVz("evt1").fill(trk.vertex().z());
        }        
        for(Track trk : tracksPass1_excludeSameSeedTracks_evt2){
            histoGroupTrackPass1ExcludeSameSeed.getHistoNKFIters("evt2").fill(trk.nKFIters());
            histoGroupTrackPass1ExcludeSameSeed.getHistoChi2overndf("evt2").fill(trk.chi2()/trk.ndf());
            histoGroupTrackPass1ExcludeSameSeed.getHistoNDF("evt2").fill(trk.ndf());
            histoGroupTrackPass1ExcludeSameSeed.getHistoP("evt2").fill(trk.momentum().mag());
            histoGroupTrackPass1ExcludeSameSeed.getHistoTheta("evt2").fill(trk.momentum().theta());
            histoGroupTrackPass1ExcludeSameSeed.getHistoPhi("evt2").fill(trk.momentum().phi());
            histoGroupTrackPass1ExcludeSameSeed.getHistoVx("evt2").fill(trk.vertex().x());
            histoGroupTrackPass1ExcludeSameSeed.getHistoVy("evt2").fill(trk.vertex().y());
            histoGroupTrackPass1ExcludeSameSeed.getHistoVz("evt2").fill(trk.vertex().z());            
        }        
        
        // tracks for pass2                
        TrackHistoGroup histoGroupTrackPass2 = (TrackHistoGroup) histoGroupMap.get("trackPass2");        
        for(Track trk : tracksPass2_evt1){
            histoGroupTrackPass2.getHistoNKFIters("evt1").fill(trk.nKFIters());
            histoGroupTrackPass2.getHistoChi2overndf("evt1").fill(trk.chi2()/trk.ndf());
            histoGroupTrackPass2.getHistoNDF("evt1").fill(trk.ndf());
            histoGroupTrackPass2.getHistoP("evt1").fill(trk.momentum().mag());
            histoGroupTrackPass2.getHistoTheta("evt1").fill(trk.momentum().theta());
            histoGroupTrackPass2.getHistoPhi("evt1").fill(trk.momentum().phi());            
            histoGroupTrackPass2.getHistoVx("evt1").fill(trk.vertex().x());
            histoGroupTrackPass2.getHistoVy("evt1").fill(trk.vertex().y());
            histoGroupTrackPass2.getHistoVz("evt1").fill(trk.vertex().z());
        }        
        for(Track trk : tracksPass2_evt2){
            histoGroupTrackPass2.getHistoNKFIters("evt2").fill(trk.nKFIters());
            histoGroupTrackPass2.getHistoChi2overndf("evt2").fill(trk.chi2()/trk.ndf());
            histoGroupTrackPass2.getHistoNDF("evt2").fill(trk.ndf());
            histoGroupTrackPass2.getHistoP("evt2").fill(trk.momentum().mag());
            histoGroupTrackPass2.getHistoTheta("evt2").fill(trk.momentum().theta());
            histoGroupTrackPass2.getHistoPhi("evt2").fill(trk.momentum().phi());
            histoGroupTrackPass2.getHistoVx("evt2").fill(trk.vertex().x());
            histoGroupTrackPass2.getHistoVy("evt2").fill(trk.vertex().y());
            histoGroupTrackPass2.getHistoVz("evt2").fill(trk.vertex().z());            
        }
        
        Map<Track, Track> map_trackPass2PairSameSeed = new HashMap();
        TrackHistoGroup histoGroupTrackPass2DiffSameSeed = (TrackHistoGroup) histoGroupMap.get("trackPass2DiffSameSeed");  
        for(Track trk1 : tracksPass2_evt1){
            for(Track trk2 : tracksPass2_evt2){
                if(trk1.isSameClusterswithTrack(trk2)){
                    histoGroupTrackPass2DiffSameSeed.getHistoNKFItersDiff().fill(trk2.nKFIters() - trk1.nKFIters());
                    histoGroupTrackPass2DiffSameSeed.getHistoChi2overndfDiff().fill(trk2.chi2()/trk2.ndf() - trk1.chi2()/trk1.ndf());
                    histoGroupTrackPass2DiffSameSeed.getHistoNDFDiff().fill(trk2.ndf() - trk1.ndf());
                    histoGroupTrackPass2DiffSameSeed.getHistoPDiff().fill(trk2.momentum().mag() - trk1.momentum().mag());
                    histoGroupTrackPass2DiffSameSeed.getHistoThetaDiff().fill(trk2.momentum().theta() - trk1.momentum().theta());
                    histoGroupTrackPass2DiffSameSeed.getHistoPhiDiff().fill(trk2.momentum().phi() - trk1.momentum().phi());            
                    histoGroupTrackPass2DiffSameSeed.getHistoVxDiff().fill(trk2.vertex().x() - trk1.vertex().x());
                    histoGroupTrackPass2DiffSameSeed.getHistoVyDiff().fill(trk2.vertex().y() - trk1.vertex().y());
                    histoGroupTrackPass2DiffSameSeed.getHistoVzDiff().fill(trk2.vertex().z() - trk1.vertex().z());
                    
                    map_trackPass2PairSameSeed.put(trk1, trk2);
                    trackPass2_sameSeedPair++;
                    break;
                }
            }
        }  
        
        List<Track> tracksPass2_excludeSameSeedTracks_evt1 = new ArrayList();
        tracksPass2_excludeSameSeedTracks_evt1.addAll(tracksPass2_evt1);
        tracksPass2_excludeSameSeedTracks_evt1.removeAll(map_trackPass2PairSameSeed.keySet());
        
        List<Track> tracksPass2_excludeSameSeedTracks_evt2 = new ArrayList();
        tracksPass2_excludeSameSeedTracks_evt2.addAll(tracksPass2_evt2);
        tracksPass2_excludeSameSeedTracks_evt2.removeAll(map_trackPass2PairSameSeed.values());
        
        TrackHistoGroup histoGroupTrackPass2ExcludeSameSeed = (TrackHistoGroup) histoGroupMap.get("trackPass2ExcludeSameSeed");  
        HistoGroup histoTracksPass2ReconTruthDiffExcludeSameSeedGroup = histoGroupMap.get("tracksPass2ReconTruthDiffExcludeSameSeed");
        for(Track trk : tracksPass2_excludeSameSeedTracks_evt1){
            histoGroupTrackPass2ExcludeSameSeed.getHistoNKFIters("evt1").fill(trk.nKFIters());
            histoGroupTrackPass2ExcludeSameSeed.getHistoChi2overndf("evt1").fill(trk.chi2()/trk.ndf());
            histoGroupTrackPass2ExcludeSameSeed.getHistoNDF("evt1").fill(trk.ndf());
            histoGroupTrackPass2ExcludeSameSeed.getHistoP("evt1").fill(trk.momentum().mag());
            histoGroupTrackPass2ExcludeSameSeed.getHistoTheta("evt1").fill(trk.momentum().theta());
            histoGroupTrackPass2ExcludeSameSeed.getHistoPhi("evt1").fill(trk.momentum().phi());            
            histoGroupTrackPass2ExcludeSameSeed.getHistoVx("evt1").fill(trk.vertex().x());
            histoGroupTrackPass2ExcludeSameSeed.getHistoVy("evt1").fill(trk.vertex().y());
            histoGroupTrackPass2ExcludeSameSeed.getHistoVz("evt1").fill(trk.vertex().z());
            
            if(mcSingle){
                if(mcParts1 != null && mcParts1.size() == 1){
                    MCParticle mcPart = mcParts1.get(0);
                    histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getH1F("p_tracksPass2ReconTruthDiffExcludeSameSeed_evt1").fill((mcPart.mom().mag() - trk.momentum().mag())/mcPart.mom().mag());
                    histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getH1F("theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt1").fill((mcPart.mom().theta() - trk.momentum().theta())/Math.PI*180.);
                    histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getH1F("phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt1").fill((mcPart.mom().phi() - trk.momentum().phi())/Math.PI*180.);
                    histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getH1F("vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt1").fill(mcPart.vertex().x() - trk.vertex().x());
                    histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getH1F("vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt1").fill(mcPart.vertex().y() - trk.vertex().y());
                    histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getH1F("vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt1").fill(mcPart.vertex().z() - trk.vertex().z());                     
                    
                }
            }
        }        
        for(Track trk : tracksPass2_excludeSameSeedTracks_evt2){
            histoGroupTrackPass2ExcludeSameSeed.getHistoNKFIters("evt2").fill(trk.nKFIters());
            histoGroupTrackPass2ExcludeSameSeed.getHistoChi2overndf("evt2").fill(trk.chi2()/trk.ndf());
            histoGroupTrackPass2ExcludeSameSeed.getHistoNDF("evt2").fill(trk.ndf());
            histoGroupTrackPass2ExcludeSameSeed.getHistoP("evt2").fill(trk.momentum().mag());
            histoGroupTrackPass2ExcludeSameSeed.getHistoTheta("evt2").fill(trk.momentum().theta());
            histoGroupTrackPass2ExcludeSameSeed.getHistoPhi("evt2").fill(trk.momentum().phi());
            histoGroupTrackPass2ExcludeSameSeed.getHistoVx("evt2").fill(trk.vertex().x());
            histoGroupTrackPass2ExcludeSameSeed.getHistoVy("evt2").fill(trk.vertex().y());
            histoGroupTrackPass2ExcludeSameSeed.getHistoVz("evt2").fill(trk.vertex().z()); 
            
            if(mcSingle){
                if(mcParts1 != null && mcParts1.size() == 1){
                    MCParticle mcPart = mcParts1.get(0);
                    histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getH1F("p_tracksPass2ReconTruthDiffExcludeSameSeed_evt2").fill((mcPart.mom().mag() - trk.momentum().mag())/mcPart.mom().mag());
                    histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getH1F("theta_tracksPass2ReconTruthDiffExcludeSameSeed_evt2").fill((mcPart.mom().theta() - trk.momentum().theta())/Math.PI*180.);
                    histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getH1F("phi_tracksPass2ReconTruthDiffExcludeSameSeed_evt2").fill((mcPart.mom().phi() - trk.momentum().phi())/Math.PI*180.);
                    histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getH1F("vx_tracksPass2ReconTruthDiffExcludeSameSeed_evt2").fill(mcPart.vertex().x() - trk.vertex().x());
                    histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getH1F("vy_tracksPass2ReconTruthDiffExcludeSameSeed_evt2").fill(mcPart.vertex().y() - trk.vertex().y());
                    histoTracksPass2ReconTruthDiffExcludeSameSeedGroup.getH1F("vz_tracksPass2ReconTruthDiffExcludeSameSeed_evt2").fill(mcPart.vertex().z() - trk.vertex().z());                                         
                }
            }            
        }        
        
        
        // utracks for pass2
        TrackHistoGroup histoGroupUTrackPass2 = (TrackHistoGroup) histoGroupMap.get("uTrackPass2");        
        for(Track trk : uTracksPass2_evt1){
            histoGroupUTrackPass2.getHistoNKFIters("evt1").fill(trk.nKFIters());
            histoGroupUTrackPass2.getHistoChi2overndf("evt1").fill(trk.chi2()/trk.ndf());
            histoGroupUTrackPass2.getHistoNDF("evt1").fill(trk.ndf());
            histoGroupUTrackPass2.getHistoP("evt1").fill(trk.momentum().mag());
            histoGroupUTrackPass2.getHistoTheta("evt1").fill(trk.momentum().theta());
            histoGroupUTrackPass2.getHistoPhi("evt1").fill(trk.momentum().phi());            
            histoGroupUTrackPass2.getHistoVx("evt1").fill(trk.vertex().x());
            histoGroupUTrackPass2.getHistoVy("evt1").fill(trk.vertex().y());
            histoGroupUTrackPass2.getHistoVz("evt1").fill(trk.vertex().z());
        }        
        for(Track trk : uTracksPass2_evt2){
            histoGroupUTrackPass2.getHistoNKFIters("evt2").fill(trk.nKFIters());
            histoGroupUTrackPass2.getHistoChi2overndf("evt2").fill(trk.chi2()/trk.ndf());
            histoGroupUTrackPass2.getHistoNDF("evt2").fill(trk.ndf());
            histoGroupUTrackPass2.getHistoP("evt2").fill(trk.momentum().mag());
            histoGroupUTrackPass2.getHistoTheta("evt2").fill(trk.momentum().theta());
            histoGroupUTrackPass2.getHistoPhi("evt2").fill(trk.momentum().phi());
            histoGroupUTrackPass2.getHistoVx("evt2").fill(trk.vertex().x());
            histoGroupUTrackPass2.getHistoVy("evt2").fill(trk.vertex().y());
            histoGroupUTrackPass2.getHistoVz("evt2").fill(trk.vertex().z());            
        } 
        
        Map<Track, Track> map_uTrackPass2PairSameSeed = new HashMap();
        TrackHistoGroup histoGroupUTrackPass2DiffSameSeed = (TrackHistoGroup) histoGroupMap.get("uTrackPass2DiffSameSeed");  
        for(Track trk1 : uTracksPass2_evt1){
            for(Track trk2 : uTracksPass2_evt2){
                if(trk1.isSameClusterswithTrack(trk2)){
                    histoGroupUTrackPass2DiffSameSeed.getHistoNKFItersDiff().fill(trk2.nKFIters() - trk1.nKFIters());
                    histoGroupUTrackPass2DiffSameSeed.getHistoChi2overndfDiff().fill(trk2.chi2()/trk2.ndf() - trk1.chi2()/trk1.ndf());
                    histoGroupUTrackPass2DiffSameSeed.getHistoNDFDiff().fill(trk2.ndf() - trk1.ndf());
                    histoGroupUTrackPass2DiffSameSeed.getHistoPDiff().fill(trk2.momentum().mag() - trk1.momentum().mag());
                    histoGroupUTrackPass2DiffSameSeed.getHistoThetaDiff().fill(trk2.momentum().theta() - trk1.momentum().theta());
                    histoGroupUTrackPass2DiffSameSeed.getHistoPhiDiff().fill(trk2.momentum().phi() - trk1.momentum().phi());            
                    histoGroupUTrackPass2DiffSameSeed.getHistoVxDiff().fill(trk2.vertex().x() - trk1.vertex().x());
                    histoGroupUTrackPass2DiffSameSeed.getHistoVyDiff().fill(trk2.vertex().y() - trk1.vertex().y());
                    histoGroupUTrackPass2DiffSameSeed.getHistoVzDiff().fill(trk2.vertex().z() - trk1.vertex().z());
                    
                    map_uTrackPass2PairSameSeed.put(trk1, trk2);
                    uTrackPass2_sameSeedPair++;
                    break;
                }
            }
        }          
                
        List<Track> uTracksPass2_excludeSameSeedTracks_evt1 = new ArrayList();
        uTracksPass2_excludeSameSeedTracks_evt1.addAll(uTracksPass2_evt1);
        uTracksPass2_excludeSameSeedTracks_evt1.removeAll(map_uTrackPass2PairSameSeed.keySet());
        
        List<Track> uTracksPass2_excludeSameSeedTracks_evt2 = new ArrayList();
        uTracksPass2_excludeSameSeedTracks_evt2.addAll(uTracksPass2_evt2);
        uTracksPass2_excludeSameSeedTracks_evt2.removeAll(map_uTrackPass2PairSameSeed.values());
        
        TrackHistoGroup histoGroupUTrackPass2ExcludeSameSeed = (TrackHistoGroup) histoGroupMap.get("uTrackPass2ExcludeSameSeed");        
        for(Track trk : uTracksPass2_excludeSameSeedTracks_evt1){
            histoGroupUTrackPass2ExcludeSameSeed.getHistoNKFIters("evt1").fill(trk.nKFIters());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoChi2overndf("evt1").fill(trk.chi2()/trk.ndf());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoNDF("evt1").fill(trk.ndf());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoP("evt1").fill(trk.momentum().mag());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoTheta("evt1").fill(trk.momentum().theta());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoPhi("evt1").fill(trk.momentum().phi());            
            histoGroupUTrackPass2ExcludeSameSeed.getHistoVx("evt1").fill(trk.vertex().x());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoVy("evt1").fill(trk.vertex().y());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoVz("evt1").fill(trk.vertex().z());
        }        
        for(Track trk : uTracksPass2_excludeSameSeedTracks_evt2){
            histoGroupUTrackPass2ExcludeSameSeed.getHistoNKFIters("evt2").fill(trk.nKFIters());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoChi2overndf("evt2").fill(trk.chi2()/trk.ndf());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoNDF("evt2").fill(trk.ndf());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoP("evt2").fill(trk.momentum().mag());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoTheta("evt2").fill(trk.momentum().theta());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoPhi("evt2").fill(trk.momentum().phi());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoVx("evt2").fill(trk.vertex().x());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoVy("evt2").fill(trk.vertex().y());
            histoGroupUTrackPass2ExcludeSameSeed.getHistoVz("evt2").fill(trk.vertex().z());            
        }        
        
    }
    
    public void postEventProcess() {  
                
    }
    
    public void setMCSingle(boolean mcSingle){
        this.mcSingle = mcSingle;
    }
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("bgEffectsOnValidTracks");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o"          ,"",     "output file name prefix");
        parser.addOption("-n"          ,"-1",   "maximum number of events to process");        
        parser.addOption("-plot"       ,"1",    "display histograms (0/1)");
        parser.addOption("-histo"      ,"0",    "read histogram file (0/1)");  
        parser.addOption("-mcSingle"      ,"0",    "if MC for single particle");  
        parser.parse(args);
        
        String namePrefix  = parser.getOption("-o").stringValue(); 
        int maxEvents  = parser.getOption("-n").intValue();    
        boolean displayPlots   = (parser.getOption("-plot").intValue()!=0);
        boolean readHistos   = (parser.getOption("-histo").intValue()!=0); 
        boolean mcSingle = (parser.getOption("-mcSingle").intValue()!=0); 
        
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
        analysis.setMCSingle(mcSingle);
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
            
            System.out.println("Tracks for pass1 in sample1: " + Integer.toString(CompareTracks.trackPass1Sp1));
            System.out.println("Tracks for pass1 in sample2: " + Integer.toString(CompareTracks.trackPass1Sp2));
            System.out.println("Tracks for pass1 with the same seeds in two samples: " + Integer.toString(CompareTracks.trackPass1_sameSeedPair));  
            System.out.println("Tracks for pass2 in sample1: " + Integer.toString(CompareTracks.trackPass2Sp1));
            System.out.println("Tracks for pass2 in sample2: " + Integer.toString(CompareTracks.trackPass2Sp2));   
            System.out.println("Tracks for pass2 with the same seeds in two samples: " + Integer.toString(CompareTracks.trackPass2_sameSeedPair));             
            System.out.println("uTracks for pass2 in sample1: " + Integer.toString(CompareTracks.uTrackPass2Sp1));
            System.out.println("uTracks for pass2 in sample2: " + Integer.toString(CompareTracks.uTrackPass2Sp2));   
            System.out.println("uTracks for pass2 with the same seeds in two samples: " + Integer.toString(CompareTracks.uTrackPass2_sameSeedPair));               
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
