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
import org.clas.element.Seed;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;
import org.clas.utilities.Constants;


/**
 *
 * @author Tongtong Cao
 */
public class SeedBg extends BaseAnalysis {      
    @Override
    public void createHistoGroupMap() { 
        HistoGroup histoGroupSeed = new HistoGroup("seed", 3, 2);
        H1F h1_seedStatus1 = new H1F("seedStatus1", "seed status for S1", 2, -0.5, 1.5);
        h1_seedStatus1.setTitleX("if matched seed");
        h1_seedStatus1.setTitleY("Counts");
        h1_seedStatus1.setLineColor(1);
        histoGroupSeed.addDataSet(h1_seedStatus1, 0);          
        H1F h1_seedStatus2 = new H1F("seedStatus2", "seed status for S2", 2, -0.5, 1.5);
        h1_seedStatus2.setTitleX("if matched seed");
        h1_seedStatus2.setTitleY("Counts");
        h1_seedStatus2.setLineColor(1);
        histoGroupSeed.addDataSet(h1_seedStatus2, 1);         
        H2F h2_efficiencyVsPurityCrossLevel = new H2F("efficiencyVsPurityCrossLevel", "efficiency vs purity at cross level for S2", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityCrossLevel.setTitleX("purity");
        h2_efficiencyVsPurityCrossLevel.setTitleY("efficiency");
        histoGroupSeed.addDataSet(h2_efficiencyVsPurityCrossLevel, 3); 
        
        H2F h2_efficiencyVsPurityClusterLevel = new H2F("efficiencyVsPurityClusterLevel", "efficiency vs purity at cluster level for S2", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityClusterLevel.setTitleX("purity");
        h2_efficiencyVsPurityClusterLevel.setTitleY("efficiency");
        histoGroupSeed.addDataSet(h2_efficiencyVsPurityClusterLevel, 4);                  
        H2F h2_efficiencyVsPurityHitLevel = new H2F("efficiencyVsPurityHitLevel", "efficiency vs purity at hit level for S2", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityHitLevel.setTitleX("purity");
        h2_efficiencyVsPurityHitLevel.setTitleY("efficiency");
        histoGroupSeed.addDataSet(h2_efficiencyVsPurityHitLevel, 5);                
        histoGroupMap.put(histoGroupSeed.getName(), histoGroupSeed); 

        HistoGroup histoGroupFittingParametersDiff = new HistoGroup("fittingParametersDiff for Seed", 4, 3);         
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
        H1F h1_circlefit_chi2_per_ndfDiff = new H1F("circlefit_chi2_per_ndfDiff", "chi2/ndf for circlefit diff", 100, -1, 1);
        h1_circlefit_chi2_per_ndfDiff.setTitleX("chi2/ndf for circlefit diff");
        h1_circlefit_chi2_per_ndfDiff.setTitleY("Counts");
        h1_circlefit_chi2_per_ndfDiff.setLineColor(1);
        histoGroupFittingParametersDiff.addDataSet(h1_circlefit_chi2_per_ndfDiff, 9); 
        H1F h1_linefit_chi2_per_ndfDiff = new H1F("linefit_chi2_per_ndfDiff", "chi2/ndf for linefit diff", 100, -1, 1);
        h1_linefit_chi2_per_ndfDiff.setTitleX("chi2/ndf for linefit diff");
        h1_linefit_chi2_per_ndfDiff.setTitleY("Counts");
        h1_linefit_chi2_per_ndfDiff.setLineColor(1);
        histoGroupFittingParametersDiff.addDataSet(h1_linefit_chi2_per_ndfDiff, 10);                         
        histoGroupMap.put(histoGroupFittingParametersDiff.getName(), histoGroupFittingParametersDiff);
        
        HistoGroup histoGroupKinematicsDiff = new HistoGroup("kinematics diff for Seed", 3, 2);         
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
    
    public void processEvent(Event event1, Event event2){        
        //Read banks
        //////// Read banks
        LocalEvent localEvent1 = new LocalEvent(reader1, event1);
        LocalEvent localEvent2 = new LocalEvent(reader2, event2);
        Match match = new Match(localEvent1, localEvent2);
        processEvent(localEvent1, localEvent2, match);
    }    
    
    public void processEvent(LocalEvent localEvent1, LocalEvent localEvent2, Match match) {                        
        List<Seed> seeds1 = localEvent1.getSeeds(Constants.PASS);               
        List<Seed> seeds2 = localEvent2.getSeeds(Constants.PASS);
        
        processEvent(seeds1, seeds2, match);
    }
    
    public void processEvent(List<Seed> seeds1, List<Seed> seeds2, Match match) {          
        match.seedMatch(seeds1, seeds2);
        
        List<Seed> matchedSeedList1 = new ArrayList<>(match.get_map_seed1_seed2().keySet());
        List<Seed> lostSeedList1 = new ArrayList();
        lostSeedList1.addAll(seeds1);
        lostSeedList1.removeAll(matchedSeedList1);
        List<Seed> matchedSeedList2 = new ArrayList<>(match.get_map_seed1_seed2().values());
        List<Seed> nomatchedSeedList2 = new ArrayList();
        nomatchedSeedList2.addAll(seeds2);
        nomatchedSeedList2.removeAll(matchedSeedList2);  
        
        HistoGroup histoGroupSeed = histoGroupMap.get("seed");
        for(Seed seed : matchedSeedList1){
            histoGroupSeed.getH1F("seedStatus1").fill(1);
        }
        for(Seed seed : lostSeedList1){
            histoGroupSeed.getH1F("seedStatus1").fill(0);
        }
        for(Seed seed : matchedSeedList2){
            histoGroupSeed.getH1F("seedStatus2").fill(1);
            histoGroupSeed.getH2F("efficiencyVsPurityCrossLevel").fill(seed.getPurityCrossLevel(), seed.getEfficiencyCrossLevel());
            histoGroupSeed.getH2F("efficiencyVsPurityClusterLevel").fill(seed.getPurityClusterLevel(), seed.getEfficiencyClusterLevel());
            histoGroupSeed.getH2F("efficiencyVsPurityHitLevel").fill(seed.getRatioNormalHits(), seed.getEfficiencyHitLevel());
        }
        for(Seed seed : nomatchedSeedList2){
            histoGroupSeed.getH1F("seedStatus2").fill(0);
        }
        
        
        HistoGroup histoGroupFittingParametersDiff = histoGroupMap.get("fittingParametersDiff for Seed"); 
        HistoGroup histoGroupKinematicsDiff = histoGroupMap.get("kinematics diff for Seed"); 
        for(Seed seed1 : match.get_map_seed1_seed2().keySet()){
            Seed seed2 = match.get_map_seed1_seed2().get(seed1);
            histoGroupFittingParametersDiff.getH1F("qDiff" ).fill(seed2.q() - seed1.q()); 
            histoGroupFittingParametersDiff.getH1F("ptDiff" ).fill(seed2.pt() - seed1.pt()); 
            histoGroupFittingParametersDiff.getH1F("phi0Diff" ).fill(seed2.phi0() - seed1.phi0()); 
            histoGroupFittingParametersDiff.getH1F("d0Diff" ).fill(seed2.d0() - seed1.d0()); 
            histoGroupFittingParametersDiff.getH1F("tandipDiff" ).fill(seed2.tandip() - seed1.tandip()); 
            histoGroupFittingParametersDiff.getH1F("z0Diff" ).fill(seed2.z0() - seed1.z0());             
            histoGroupFittingParametersDiff.getH1F("xbDiff" ).fill(seed2.xb() - seed1.xb()); 
            histoGroupFittingParametersDiff.getH1F("ybDiff" ).fill(seed2.yb() - seed1.yb()); 
            histoGroupFittingParametersDiff.getH1F("chi2_per_ndfDiff" ).fill(seed2.chi2()/seed2.ndf() - seed1.chi2()/seed1.ndf()); 
            histoGroupFittingParametersDiff.getH1F("circlefit_chi2_per_ndfDiff" ).fill(seed2.circlefit_chi2_per_ndf() - seed1.circlefit_chi2_per_ndf()); 
            histoGroupFittingParametersDiff.getH1F("linefit_chi2_per_ndfDiff" ).fill(seed2.linefit_chi2_per_ndf() - seed1.linefit_chi2_per_ndf());
            
            histoGroupKinematicsDiff.getH1F("pDiff" ).fill(seed2.momentum().mag() - seed1.momentum().mag()); 
            histoGroupKinematicsDiff.getH1F("thetaDiff" ).fill(seed2.momentum().theta() - seed1.momentum().theta()); 
            histoGroupKinematicsDiff.getH1F("phiDiff" ).fill(seed2.momentum().phi() - seed1.momentum().phi()); 
            histoGroupKinematicsDiff.getH1F("xDiff" ).fill(seed2.vertex().x() - seed1.vertex().x()); 
            histoGroupKinematicsDiff.getH1F("yDiff" ).fill(seed2.vertex().y() - seed1.vertex().y()); 
            histoGroupKinematicsDiff.getH1F("zDiff" ).fill(seed2.vertex().z() - seed1.vertex().z());             
        }
    }
    
    public void postEventProcess(){       
    }                
    
    public static void main(String[] args) {
        OptionParser parser = new OptionParser("bgEffectsonSeeds");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o", "", "output file name prefix");
        parser.addOption("-n", "-1", "maximum number of events to process");
        parser.addOption("-plot", "1", "display histograms (0/1)");
        parser.addOption("-pass", "1", "if bg (1/2)");                
        parser.addOption("-histo"      ,"0",    "read histogram file (0/1)");  
        parser.parse(args);

        String namePrefix  = parser.getOption("-o").stringValue(); 
        int maxEvents  = parser.getOption("-n").intValue();    
        boolean displayPlots   = (parser.getOption("-plot").intValue()!=0);
        boolean readHistos   = (parser.getOption("-histo").intValue()!=0); 
        int pass  = parser.getOption("-pass").intValue(); 
        Constants.PASS = pass;
        
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
        SeedBg analysis = new SeedBg();
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

                analysis.processEvent(event1, event2);

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