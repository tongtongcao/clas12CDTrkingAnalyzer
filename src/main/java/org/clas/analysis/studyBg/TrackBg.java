package org.clas.analysis.studyBg;

import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;

import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.graphics.EmbeddedCanvasTabbed;
import org.jlab.jnp.hipo4.data.Event;
import org.jlab.jnp.hipo4.data.SchemaFactory;
import org.jlab.jnp.hipo4.io.HipoReader;
import org.jlab.utils.benchmark.ProgressPrintout;
import org.jlab.utils.options.OptionParser;

import org.clas.analysis.BaseAnalysis;
import org.clas.element.Track;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;
import org.clas.utilities.Constants;


/**
 *
 * @author Tongtong Cao
 */
public class TrackBg extends BaseAnalysis {      
    @Override
    public void createHistoGroupMap() { 
        HistoGroup histoGroupTrack = new HistoGroup("track", 3, 2);
        H1F h1_trackStatus1 = new H1F("trackStatus1", "track status for S1", 2, -0.5, 1.5);
        h1_trackStatus1.setTitleX("if matched track");
        h1_trackStatus1.setTitleY("Counts");
        h1_trackStatus1.setLineColor(1);
        histoGroupTrack.addDataSet(h1_trackStatus1, 0);          
        H1F h1_trackStatus2 = new H1F("trackStatus2", "track status for S2", 2, -0.5, 1.5);
        h1_trackStatus2.setTitleX("if matched track");
        h1_trackStatus2.setTitleY("Counts");
        h1_trackStatus2.setLineColor(1);
        histoGroupTrack.addDataSet(h1_trackStatus2, 1);         
        H2F h2_efficiencyVsPurityCrossLevel = new H2F("efficiencyVsPurityCrossLevel", "efficiency vs purity at cross level for S2", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityCrossLevel.setTitleX("purity");
        h2_efficiencyVsPurityCrossLevel.setTitleY("efficiency");
        histoGroupTrack.addDataSet(h2_efficiencyVsPurityCrossLevel, 3); 
        
        H2F h2_efficiencyVsPurityClusterLevel = new H2F("efficiencyVsPurityClusterLevel", "efficiency vs purity at cluster level for S2", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityClusterLevel.setTitleX("purity");
        h2_efficiencyVsPurityClusterLevel.setTitleY("efficiency");
        histoGroupTrack.addDataSet(h2_efficiencyVsPurityClusterLevel, 4);                  
        H2F h2_efficiencyVsPurityHitLevel = new H2F("efficiencyVsPurityHitLevel", "efficiency vs purity at hit level for S2", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityHitLevel.setTitleX("purity");
        h2_efficiencyVsPurityHitLevel.setTitleY("efficiency");
        histoGroupTrack.addDataSet(h2_efficiencyVsPurityHitLevel, 5);                
        histoGroupMap.put(histoGroupTrack.getName(), histoGroupTrack); 

        HistoGroup histoGroupFittingParametersDiff = new HistoGroup("fittingParametersDiff for Track", 4, 3);         
        H1F h1_qDiff = new H1F("qDiff", "q diff", 5, -2.5, 2.5);
        h1_qDiff.setTitleX("q diff");
        h1_qDiff.setTitleY("Counts");
        h1_qDiff.setLineColor(1);
        histoGroupFittingParametersDiff.addDataSet(h1_qDiff, 0);           
        H1F h1_ptDiff = new H1F("ptDiff", "pt diff", 100, -0.2, 0.2);
        h1_ptDiff.setTitleX("pt diff (GeV/c)");
        h1_ptDiff.setTitleY("Counts");
        h1_ptDiff.setLineColor(1);
        histoGroupFittingParametersDiff.addDataSet(h1_ptDiff, 1);          
        H1F h1_phi0Diff = new H1F("phi0Diff", "phi0 diff", 100, -0.1, 0.1);
        h1_phi0Diff.setTitleX("phi0 diff (rad)");
        h1_phi0Diff.setTitleY("Counts");
        h1_phi0Diff.setLineColor(1);
        histoGroupFittingParametersDiff.addDataSet(h1_phi0Diff, 2);  
        H1F h1_d0Diff = new H1F("d0Diff", "d0 diff", 100, -0.02, 0.02);
        h1_d0Diff.setTitleX("d0 diff (cm)");
        h1_d0Diff.setTitleY("Counts");
        h1_d0Diff.setLineColor(1);
        histoGroupFittingParametersDiff.addDataSet(h1_d0Diff, 3);         
        H1F h1_tandipDiff = new H1F("tandipDiff", "tandip diff", 100, -0.02, 0.02);
        h1_tandipDiff.setTitleX("tandip diff");
        h1_tandipDiff.setTitleY("Counts");
        h1_tandipDiff.setLineColor(1);
        histoGroupFittingParametersDiff.addDataSet(h1_tandipDiff, 4);        
        H1F h1_z0Diff = new H1F("z0Diff", "z0 diff", 100, -0.02, 0.02);
        h1_z0Diff.setTitleX("z0 diff (cm)");
        h1_z0Diff.setTitleY("Counts");
        h1_z0Diff.setLineColor(1);
        histoGroupFittingParametersDiff.addDataSet(h1_z0Diff, 5);          
        H1F h1_xbDiff = new H1F("xbDiff", "xb diff", 100, -0.1, 0.1);
        h1_xbDiff.setTitleX("xb diff (cm)");
        h1_xbDiff.setTitleY("Counts");
        h1_xbDiff.setLineColor(1);
        histoGroupFittingParametersDiff.addDataSet(h1_xbDiff, 6);         
        H1F h1_ybDiff = new H1F("ybDiff", "yb diff", 100, -0.1, 0.1);
        h1_ybDiff.setTitleX("yb diff (cm)");
        h1_ybDiff.setTitleY("Counts");
        h1_ybDiff.setLineColor(1);
        histoGroupFittingParametersDiff.addDataSet(h1_ybDiff, 7);         
        H1F h1_chi2_per_ndfDiff = new H1F("chi2_per_ndfDiff", "chi2/ndf diff", 100, -1, 1);
        h1_chi2_per_ndfDiff.setTitleX("chi2/ndf diff");
        h1_chi2_per_ndfDiff.setTitleY("Counts");
        h1_chi2_per_ndfDiff.setLineColor(1);
        histoGroupFittingParametersDiff.addDataSet(h1_chi2_per_ndfDiff, 8);                       
        histoGroupMap.put(histoGroupFittingParametersDiff.getName(), histoGroupFittingParametersDiff);
        
        HistoGroup histoGroupKinematicsDiff = new HistoGroup("kinematics diff for Track", 3, 2);         
        H1F h1_pDiff = new H1F("pDiff", "p diff", 100, -0.1, 0.1);
        h1_pDiff.setTitleX("p diff (GeV/c)");
        h1_pDiff.setTitleY("Counts");
        h1_pDiff.setLineColor(1);   
        histoGroupKinematicsDiff.addDataSet(h1_pDiff, 0);        
        H1F h1_thetaDiff = new H1F("thetaDiff", "#theta diff", 100, -0.1, 0.1);
        h1_thetaDiff.setTitleX("#theta diff (rad)");
        h1_thetaDiff.setTitleY("Counts");
        h1_thetaDiff.setLineColor(1);   
        histoGroupKinematicsDiff.addDataSet(h1_thetaDiff, 1);          
        H1F h1_phiDiff = new H1F("phiDiff", "#phi diff", 100, -0.1, 0.1);
        h1_phiDiff.setTitleX("#phi diff (rad)");
        h1_phiDiff.setTitleY("Counts");
        h1_phiDiff.setLineColor(1);   
        histoGroupKinematicsDiff.addDataSet(h1_phiDiff, 2);         
        H1F h1_xDiff = new H1F("xDiff", "x diff", 100, -0.1, 0.1);
        h1_xDiff.setTitleX("x diff (cm)");
        h1_xDiff.setTitleY("Counts");
        h1_xDiff.setLineColor(1);   
        histoGroupKinematicsDiff.addDataSet(h1_xDiff, 3);           
        H1F h1_yDiff = new H1F("yDiff", "y diff", 100, -0.1, 0.1);
        h1_yDiff.setTitleX("y diff (cm)");
        h1_yDiff.setTitleY("Counts");
        h1_yDiff.setLineColor(1);   
        histoGroupKinematicsDiff.addDataSet(h1_yDiff, 4); 
        H1F h1_zDiff = new H1F("zDiff", "z diff", 100, -0.1, 0.1);
        h1_zDiff.setTitleX("z diff(cm)");
        h1_zDiff.setTitleY("Counts");
        h1_zDiff.setLineColor(1);   
        histoGroupKinematicsDiff.addDataSet(h1_zDiff, 5);                 
        histoGroupMap.put(histoGroupKinematicsDiff.getName(), histoGroupKinematicsDiff);        
    }
    
    public void processEvent(Event event1, Event event2, boolean uTrack){        
        //Read banks
        //////// Read banks
        LocalEvent localEvent1 = new LocalEvent(reader1, event1);
        LocalEvent localEvent2 = new LocalEvent(reader2, event2);
        Match match = new Match(localEvent1, localEvent2);
        processEvent(localEvent1, localEvent2, uTrack, match);
    }    
    
    public void processEvent(LocalEvent localEvent1, LocalEvent localEvent2, boolean uTrack, Match match) {                        
        List<Track> tracks1;              
        List<Track> tracks2;
        if(!uTrack){
            tracks1 = localEvent1.getTracks(Constants.PASS); 
            tracks2 = localEvent2.getTracks(Constants.PASS);
        }
        else{
            tracks1 = localEvent1.getUTracks(Constants.PASS); 
            tracks2 = localEvent2.getUTracks(Constants.PASS);
        }
        
        processEvent(tracks1, tracks2, match);
    }
    
    public void processEvent(List<Track> tracks1, List<Track> tracks2, Match match) {         
        match.trackMatch(tracks1, tracks2);
        
        List<Track> matchedTrackList1 = new ArrayList<>(match.get_map_track1_track2().keySet());
        List<Track> lostTrackList1 = new ArrayList();
        lostTrackList1.addAll(tracks1);
        lostTrackList1.removeAll(matchedTrackList1);
        List<Track> matchedTrackList2 = new ArrayList<>(match.get_map_track1_track2().values());
        List<Track> nomatchedTrackList2 = new ArrayList();
        nomatchedTrackList2.addAll(tracks2);
        nomatchedTrackList2.removeAll(matchedTrackList2);  
        
        HistoGroup histoGroupTrack = histoGroupMap.get("track");
        for(Track track : matchedTrackList1){
            histoGroupTrack.getH1F("trackStatus1").fill(1);
        }
        for(Track track : lostTrackList1){
            histoGroupTrack.getH1F("trackStatus1").fill(0);
        }
        for(Track track : matchedTrackList2){
            histoGroupTrack.getH1F("trackStatus2").fill(1);
            histoGroupTrack.getH2F("efficiencyVsPurityCrossLevel").fill(track.getPurityCrossLevel(), track.getEfficiencyCrossLevel());
            histoGroupTrack.getH2F("efficiencyVsPurityClusterLevel").fill(track.getPurityClusterLevel(), track.getEfficiencyClusterLevel());
            histoGroupTrack.getH2F("efficiencyVsPurityHitLevel").fill(track.getRatioNormalHits(), track.getEfficiencyHitLevel());
        }
        for(Track track : nomatchedTrackList2){
            histoGroupTrack.getH1F("trackStatus2").fill(0);
        }
        
        
        HistoGroup histoGroupFittingParametersDiff = histoGroupMap.get("fittingParametersDiff for Track"); 
        HistoGroup histoGroupKinematicsDiff = histoGroupMap.get("kinematics diff for Track"); 
        for(Track track1 : match.get_map_track1_track2().keySet()){
            Track track2 = match.get_map_track1_track2().get(track1);
            histoGroupFittingParametersDiff.getH1F("qDiff" ).fill(track2.q() - track1.q()); 
            histoGroupFittingParametersDiff.getH1F("ptDiff" ).fill(track2.pt() - track1.pt()); 
            histoGroupFittingParametersDiff.getH1F("phi0Diff" ).fill(track2.phi0() - track1.phi0()); 
            histoGroupFittingParametersDiff.getH1F("d0Diff" ).fill(track2.d0() - track1.d0()); 
            histoGroupFittingParametersDiff.getH1F("tandipDiff" ).fill(track2.tandip() - track1.tandip()); 
            histoGroupFittingParametersDiff.getH1F("z0Diff" ).fill(track2.z0() - track1.z0());             
            histoGroupFittingParametersDiff.getH1F("xbDiff" ).fill(track2.xb() - track1.xb()); 
            histoGroupFittingParametersDiff.getH1F("ybDiff" ).fill(track2.yb() - track1.yb()); 
            histoGroupFittingParametersDiff.getH1F("chi2_per_ndfDiff" ).fill(track2.chi2()/track2.ndf() - track1.chi2()/track1.ndf()); 
            
            histoGroupKinematicsDiff.getH1F("pDiff" ).fill(track2.momentum().mag() - track1.momentum().mag()); 
            histoGroupKinematicsDiff.getH1F("thetaDiff" ).fill(track2.momentum().theta() - track1.momentum().theta()); 
            histoGroupKinematicsDiff.getH1F("phiDiff" ).fill(track2.momentum().phi() - track1.momentum().phi()); 
            histoGroupKinematicsDiff.getH1F("xDiff" ).fill(track2.vertex().x() - track1.vertex().x()); 
            histoGroupKinematicsDiff.getH1F("yDiff" ).fill(track2.vertex().y() - track1.vertex().y()); 
            histoGroupKinematicsDiff.getH1F("zDiff" ).fill(track2.vertex().z() - track1.vertex().z());             
        }
    }
    
    public void postEventProcess(){       
    }                
    
    public static void main(String[] args) {
        OptionParser parser = new OptionParser("bgEffectsonTracks");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o", "", "output file name prefix");
        parser.addOption("-n", "-1", "maximum number of events to process");
        parser.addOption("-plot", "1", "display histograms (0/1)");
        parser.addOption("-pass", "1", "if bg (1/2)"); 
        parser.addOption("-uTrack", "0", "if bg (0/1)");        
        parser.addOption("-histo"      ,"0",    "read histogram file (0/1)");  
        parser.parse(args);

        String namePrefix  = parser.getOption("-o").stringValue(); 
        int maxEvents  = parser.getOption("-n").intValue();    
        boolean displayPlots   = (parser.getOption("-plot").intValue()!=0);
        boolean readHistos   = (parser.getOption("-histo").intValue()!=0);         
        int pass  = parser.getOption("-pass").intValue(); 
        Constants.PASS = pass;
        boolean uTrack = (parser.getOption("-uTrack").intValue() != 0);
        
        List<String> inputList = parser.getInputList();
        if (inputList.isEmpty() == true) {
            parser.printUsage();
            inputList.add("/Users/caot/research/clas12/data/mc/uRWELL/upgradeTrackingWithuRWELL/nobg/applyCTDAF/pt3_1pt6/origin/recon_before_update.hipo");
            inputList.add("/Users/caot/research/clas12/data/mc/uRWELL/upgradeTrackingWithuRWELL/bg/applyCTDAF/pt3_1pt6/origin/recon_before_update_bg.hipo");
            maxEvents = 1000;
            //System.out.println("\n >>>> error: no input file is specified....\n");
            //System.exit(0);
        }

        String histoName = "histo.hipo";
        if (!namePrefix.isEmpty()) {
            histoName = namePrefix + "_" + histoName;
        }
        
        Constants.BG = true; 
        TrackBg analysis = new TrackBg();
        analysis.createHistoGroupMap();
        

        if (!readHistos) {
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

                analysis.processEvent(event1, event2, uTrack);

                progress.updateStatus();
                if (maxEvents > 0) {
                    if (counter >= maxEvents) {
                        break;
                    }
                }
            }

            analysis.postEventProcess();

            progress.showStatus();
            reader1.close();
            reader2.close();
            analysis.saveHistos(histoName);
        } else {
            analysis.readHistos(inputList.get(0));
        }

        if (displayPlots) {
            JFrame frame = new JFrame();
            EmbeddedCanvasTabbed canvas = analysis.plotHistos();
            frame.setSize(1500, 900);
            frame.add(canvas);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }       
    }
}