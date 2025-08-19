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
import org.clas.element.Cross;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;
import org.clas.utilities.Constants;


/**
 *
 * @author Tongtong Cao
 */
public class CrossBg extends BaseAnalysis {      
    @Override
    public void createHistoGroupMap() { 
        //// BST
        HistoGroup histoGroupCrossMatchStatusBST1 = new HistoGroup("crossMatchStatusBST1", 3, 2);
        HistoGroup histoGroupCrossMatchStatusBST2 = new HistoGroup("crossMatchStatusBST2", 3, 2);
        HistoGroup histoGroupCrossEfficiencyVsPurityBST = new HistoGroup("crossEfficiencyVsPurityBST", 3, 2);
        for (int i = 0; i < 3; i++) {            
            H1F h1_matchStatusBST1 = new H1F("cross match status for S1 BST R" + Integer.toString(i + 1),
                    "cross match status for S1 BST R" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h1_matchStatusBST1.setTitleX("Is matched cross found");
            h1_matchStatusBST1.setTitleY("Counts");
            h1_matchStatusBST1.setLineColor(1);
            histoGroupCrossMatchStatusBST1.addDataSet(h1_matchStatusBST1, i);
            
            H1F h_matchStatusBST2 = new H1F("cross match status for S2 BST R" + Integer.toString(i + 1),
                    "cross match status for S2 BST R" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h_matchStatusBST2.setTitleX("Is matched cross found");
            h_matchStatusBST2.setTitleY("Counts");
            h_matchStatusBST2.setLineColor(1);
            histoGroupCrossMatchStatusBST2.addDataSet(h_matchStatusBST2, i); 
            
            H2F h2_efficiencyVsPurityBST = new H2F("efficiency vs purity for cross of BST R" + Integer.toString(i + 1),
                    "efficiency vs purity for cross of BST R" + Integer.toString(i + 1), 30, 0, 1.01, 30, 0, 1.01);
            h2_efficiencyVsPurityBST.setTitleX("purity");
            h2_efficiencyVsPurityBST.setTitleY("efficiency");
            histoGroupCrossEfficiencyVsPurityBST.addDataSet(h2_efficiencyVsPurityBST, i);  
        }
        histoGroupMap.put(histoGroupCrossMatchStatusBST1.getName(), histoGroupCrossMatchStatusBST1);        
        histoGroupMap.put(histoGroupCrossMatchStatusBST2.getName(), histoGroupCrossMatchStatusBST2);   
        histoGroupMap.put(histoGroupCrossEfficiencyVsPurityBST.getName(), histoGroupCrossEfficiencyVsPurityBST); 
        
        HistoGroup histoGroupCrossesBST = new HistoGroup("crossesBST", 3, 2);
        H1F h1_numCrossesBST1 = new H1F("numCrossesBST1", "# of crosses for S1 BST", 3, 0.5, 3.5);
        h1_numCrossesBST1.setTitleX("layer");
        h1_numCrossesBST1.setTitleY("Counts");
        h1_numCrossesBST1.setLineColor(1);
        histoGroupCrossesBST.addDataSet(h1_numCrossesBST1, 0);  
        H1F h1_numLostCrossesBST1 = new H1F("numLostCrossesBST1", "# of lost crosses for S1 BST", 3, 0.5, 3.5);
        h1_numLostCrossesBST1.setTitleX("layer");
        h1_numLostCrossesBST1.setTitleY("Counts");
        h1_numLostCrossesBST1.setLineColor(1);
        histoGroupCrossesBST.addDataSet(h1_numLostCrossesBST1, 1);             
        H1F h1_numCrossesBST2 = new H1F("numCrossesBST2", "# of crosses for S2 BST", 3, 0.5, 3.5);
        h1_numCrossesBST2.setTitleX("layer");
        h1_numCrossesBST2.setTitleY("Counts");
        h1_numCrossesBST2.setLineColor(1);
        histoGroupCrossesBST.addDataSet(h1_numCrossesBST2, 3);                
        histoGroupMap.put(histoGroupCrossesBST.getName(), histoGroupCrossesBST);
        H1F h1_numNomatchedCrossesBST2 = new H1F("numNomatchedCrossesBST2", "# of nomatched crosses for S2 BST", 3, 0.5, 3.5);
        h1_numNomatchedCrossesBST2.setTitleX("layer");
        h1_numNomatchedCrossesBST2.setTitleY("Counts");
        h1_numNomatchedCrossesBST2.setLineColor(1);
        histoGroupCrossesBST.addDataSet(h1_numNomatchedCrossesBST2, 4);                
        histoGroupMap.put(histoGroupCrossesBST.getName(), histoGroupCrossesBST); 
        
        
        //// BMT
        HistoGroup histoGroupCrossMatchStatusBMT1 = new HistoGroup("crossMatchStatusBMT1", 3, 2);
        HistoGroup histoGroupCrossMatchStatusBMT2 = new HistoGroup("crossMatchStatusBMT2", 3, 2);
        HistoGroup histoGroupCrossEfficiencyVsPurityBMT = new HistoGroup("crossEfficiencyVsPurityBMT", 3, 2);
        for (int i = 0; i < 6; i++) {            
            H1F h1_matchStatusBMT1 = new H1F("cross match status for S1 BMT L" + Integer.toString(i + 1),
                    "cross match status for S1 BMT L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h1_matchStatusBMT1.setTitleX("Is matched cross found");
            h1_matchStatusBMT1.setTitleY("Counts");
            h1_matchStatusBMT1.setLineColor(1);
            histoGroupCrossMatchStatusBMT1.addDataSet(h1_matchStatusBMT1, i);
            
            H1F h2_matchStatusBMT2 = new H1F("cross match status for S2 BMT L" + Integer.toString(i + 1),
                    "cross match status for S2 BMT L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h2_matchStatusBMT2.setTitleX("Is matched cross found");
            h2_matchStatusBMT2.setTitleY("Counts");
            h2_matchStatusBMT2.setLineColor(1);
            histoGroupCrossMatchStatusBMT2.addDataSet(h2_matchStatusBMT2, i); 
            
            H2F h_efficiencyVsPurityBMT = new H2F("efficiency vs purity for cross of BMT L" + Integer.toString(i + 1),
                    "efficiency vs purity for cross of BMT L" + Integer.toString(i + 1), 30, 0, 1.01, 30, 0, 1.01);
            h_efficiencyVsPurityBMT.setTitleX("purity");
            h_efficiencyVsPurityBMT.setTitleY("efficiency");
            histoGroupCrossEfficiencyVsPurityBMT.addDataSet(h_efficiencyVsPurityBMT, i);  
        }
        histoGroupMap.put(histoGroupCrossMatchStatusBMT1.getName(), histoGroupCrossMatchStatusBMT1);        
        histoGroupMap.put(histoGroupCrossMatchStatusBMT2.getName(), histoGroupCrossMatchStatusBMT2);   
        histoGroupMap.put(histoGroupCrossEfficiencyVsPurityBMT.getName(), histoGroupCrossEfficiencyVsPurityBMT); 
        
        HistoGroup histoGroupCrossesBMT = new HistoGroup("crossesBMT", 3, 2);
        H1F h1_numCrossesBMT1 = new H1F("numCrossesBMT1", "# of crosses for S1 BMT", 6, 0.5, 6.5);
        h1_numCrossesBMT1.setTitleX("layer");
        h1_numCrossesBMT1.setTitleY("Counts");
        h1_numCrossesBMT1.setLineColor(1);
        histoGroupCrossesBMT.addDataSet(h1_numCrossesBMT1, 0);  
        H1F h1_numLostCrossesBMT1 = new H1F("numLostCrossesBMT1", "# of lost crosses for S1 BMT", 6, 0.5, 6.5);
        h1_numLostCrossesBMT1.setTitleX("layer");
        h1_numLostCrossesBMT1.setTitleY("Counts");
        h1_numLostCrossesBMT1.setLineColor(1);
        histoGroupCrossesBMT.addDataSet(h1_numLostCrossesBMT1, 1);             
        H1F h1_numCrossesBMT2 = new H1F("numCrossesBMT2", "# of crosses for S2 BMT", 6, 0.5, 6.5);
        h1_numCrossesBMT2.setTitleX("layer");
        h1_numCrossesBMT2.setTitleY("Counts");
        h1_numCrossesBMT2.setLineColor(1);
        histoGroupCrossesBMT.addDataSet(h1_numCrossesBMT2, 3);                
        histoGroupMap.put(histoGroupCrossesBMT.getName(), histoGroupCrossesBMT);
        H1F h1_numNomatchedCrossesBMT2 = new H1F("numNomatchedCrossesBMT2", "# of nomatched crosses for S2 BMT", 6, 0.5, 6.5);
        h1_numNomatchedCrossesBMT2.setTitleX("layer");
        h1_numNomatchedCrossesBMT2.setTitleY("Counts");
        h1_numNomatchedCrossesBMT2.setLineColor(1);
        histoGroupCrossesBMT.addDataSet(h1_numNomatchedCrossesBMT2, 4);                
        histoGroupMap.put(histoGroupCrossesBMT.getName(), histoGroupCrossesBMT); 

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
        List<Cross> crossesBST1 = localEvent1.getBSTCrosses(Constants.PASS);        
        List<Cross> crossesBST2 = localEvent2.getBSTCrosses(Constants.PASS);        
        List<Cross> crossesBMT1 = localEvent1.getBMTCrosses(Constants.PASS);
        List<Cross> crossesBMT2 = localEvent2.getBMTCrosses(Constants.PASS); 
        
        processEvent(crossesBST1, crossesBST2, crossesBMT1, crossesBMT2, match);
    }
    
    public void processEvent(List<Cross> crossesBST1, List<Cross> crossesBST2, List<Cross> crossesBMT1, List<Cross> crossesBMT2, Match match) {     
        match.crossMatch(crossesBST1, crossesBST2, crossesBMT1, crossesBMT2);
        
        //// BST
        List<Cross> matchedCrsList1BST = new ArrayList<>(match.get_map_crs1_crs2_BST().keySet());
        List<Cross> lostCrsList1BST = new ArrayList();
        lostCrsList1BST.addAll(crossesBST1);
        lostCrsList1BST.removeAll(matchedCrsList1BST);
        List<Cross> matchedCrsList2BST = new ArrayList<>(match.get_map_crs1_crs2_BST().values());
        List<Cross> nomatchedCrsList2BST = new ArrayList();
        nomatchedCrsList2BST.addAll(crossesBST2);
        nomatchedCrsList2BST.removeAll(matchedCrsList2BST);
        
        HistoGroup histoGroupMatchStatusBST1 = histoGroupMap.get("crossMatchStatusBST1");
        HistoGroup histoGroupMatchStatusBST2 = histoGroupMap.get("crossMatchStatusBST2");        
        HistoGroup histoGroupCrossEfficiencyVsPurityBST = histoGroupMap.get("crossEfficiencyVsPurityBST");
        for(Cross crs : matchedCrsList1BST){
            histoGroupMatchStatusBST1.getH1F("cross match status for S1 BST R" + Integer.toString(crs.region())).fill(1);
        }
        for(Cross crs : lostCrsList1BST){
            histoGroupMatchStatusBST1.getH1F("cross match status for S1 BST R" + Integer.toString(crs.region())).fill(0);
        }
        for(Cross crs : matchedCrsList2BST){
            histoGroupMatchStatusBST2.getH1F("cross match status for S2 BST R" + Integer.toString(crs.region())).fill(1);
            histoGroupCrossEfficiencyVsPurityBST.getH2F("efficiency vs purity for cross of BST R" + Integer.toString(crs.region())).fill(crs.getRatioNormalHits(), crs.getEfficiency());
        }
        for(Cross crs : nomatchedCrsList2BST){
            histoGroupMatchStatusBST2.getH1F("cross match status for S2 BST R" + Integer.toString(crs.region())).fill(0);
        }
        
        HistoGroup histoGroupCrossesBST = histoGroupMap.get("crossesBST");
        for(Cross crs : crossesBST1){
            histoGroupCrossesBST.getH1F("numCrossesBST1").fill(crs.region());
        }
        for(Cross crs : lostCrsList1BST){
            histoGroupCrossesBST.getH1F("numLostCrossesBST1").fill(crs.region());
        }
        
        for(Cross crs : crossesBST2){
            histoGroupCrossesBST.getH1F("numCrossesBST2").fill(crs.region());
        }
        for(Cross crs : nomatchedCrsList2BST){
            histoGroupCrossesBST.getH1F("numNomatchedCrossesBST2").fill(crs.region());
        }
        
        //// BMT
        List<Cross> matchedCrsList1BMT = new ArrayList<>(match.get_map_crs1_crs2_BMT().keySet());
        List<Cross> lostCrsList1BMT = new ArrayList();
        lostCrsList1BMT.addAll(crossesBMT1);
        lostCrsList1BMT.removeAll(matchedCrsList1BMT);
        List<Cross> matchedCrsList2BMT = new ArrayList<>(match.get_map_crs1_crs2_BMT().values());
        List<Cross> nomatchedCrsList2BMT = new ArrayList();
        nomatchedCrsList2BMT.addAll(crossesBMT2);
        nomatchedCrsList2BMT.removeAll(matchedCrsList2BMT);
        
        HistoGroup histoGroupMatchStatusBMT1 = histoGroupMap.get("crossMatchStatusBMT1");
        HistoGroup histoGroupMatchStatusBMT2 = histoGroupMap.get("crossMatchStatusBMT2");        
        HistoGroup histoGroupCrossEfficiencyVsPurityBMT = histoGroupMap.get("crossEfficiencyVsPurityBMT");
        for(Cross crs : matchedCrsList1BMT){
            histoGroupMatchStatusBMT1.getH1F("cross match status for S1 BMT L" + Integer.toString(crs.layer())).fill(1);
        }
        for(Cross crs : lostCrsList1BMT){
            histoGroupMatchStatusBMT1.getH1F("cross match status for S1 BMT L" + Integer.toString(crs.layer())).fill(0);
        }
        for(Cross crs : matchedCrsList2BMT){
            histoGroupMatchStatusBMT2.getH1F("cross match status for S2 BMT L" + Integer.toString(crs.layer())).fill(1);
            histoGroupCrossEfficiencyVsPurityBMT.getH2F("efficiency vs purity for cross of BMT L" + Integer.toString(crs.layer())).fill(crs.getRatioNormalHits(), crs.getEfficiency());
        }
        for(Cross crs : nomatchedCrsList2BMT){
            histoGroupMatchStatusBMT2.getH1F("cross match status for S2 BMT L" + Integer.toString(crs.layer())).fill(0);
        }
        
        HistoGroup histoGroupCrossesBMT = histoGroupMap.get("crossesBMT");
        for(Cross crs : crossesBMT1){
            histoGroupCrossesBMT.getH1F("numCrossesBMT1").fill(crs.layer());
        }
        for(Cross crs : lostCrsList1BMT){
            histoGroupCrossesBMT.getH1F("numLostCrossesBMT1").fill(crs.layer());
        }
        
        for(Cross crs : crossesBMT2){
            histoGroupCrossesBMT.getH1F("numCrossesBMT2").fill(crs.layer());
        }
        for(Cross crs : nomatchedCrsList2BMT){
            histoGroupCrossesBMT.getH1F("numNomatchedCrossesBMT2").fill(crs.layer());
        }        
        
        
    }
    
    public void postEventProcess(){ 
        //// BST        
        HistoGroup histoGroupCrossesBST = histoGroupMap.get("crossesBST");             
        H1F h1_ratioLostCrossesBST1 = new H1F("ratioLostCrossesBST1", "ratio of lost crosses for S1 BST", 3, 0.5, 3.5);
        h1_ratioLostCrossesBST1.setTitleX("layer");
        h1_ratioLostCrossesBST1.setTitleY("Counts");
        h1_ratioLostCrossesBST1.setLineColor(1);        
        for(int i = 0; i < 6; i++){
            double ratio = histoGroupCrossesBST.getH1F("numLostCrossesBST1").getBinContent(i)/(double)histoGroupCrossesBST.getH1F("numCrossesBST1").getBinContent(i);
            h1_ratioLostCrossesBST1.setBinContent(i, ratio);
        }
        histoGroupCrossesBST.addDataSet(h1_ratioLostCrossesBST1, 2); 
        
        H1F h1_ratioNomatchedCrossesBST2 = new H1F("ratioNomatchedCrossesBST2", "ratio of nomatched crosses for S2 BST", 3, 0.5, 3.5);
        h1_ratioNomatchedCrossesBST2.setTitleX("layer");
        h1_ratioNomatchedCrossesBST2.setTitleY("Counts");
        h1_ratioNomatchedCrossesBST2.setLineColor(1);        
        for(int i = 0; i < 6; i++){
            double ratio = histoGroupCrossesBST.getH1F("numNomatchedCrossesBST2").getBinContent(i)/(double)histoGroupCrossesBST.getH1F("numCrossesBST2").getBinContent(i);
            h1_ratioNomatchedCrossesBST2.setBinContent(i, ratio);
        }
        histoGroupCrossesBST.addDataSet(h1_ratioNomatchedCrossesBST2, 5);
        
        //// BMT        
        HistoGroup histoGroupCrossesBMT = histoGroupMap.get("crossesBMT");             
        H1F h1_ratioLostCrossesBMT1 = new H1F("ratioLostCrossesBMT1", "ratio of lost crosses for S1 BMT", 6, 0.5, 6.5);
        h1_ratioLostCrossesBMT1.setTitleX("layer");
        h1_ratioLostCrossesBMT1.setTitleY("Counts");
        h1_ratioLostCrossesBMT1.setLineColor(1);        
        for(int i = 0; i < 6; i++){
            double ratio = histoGroupCrossesBMT.getH1F("numLostCrossesBMT1").getBinContent(i)/(double)histoGroupCrossesBMT.getH1F("numCrossesBMT1").getBinContent(i);
            h1_ratioLostCrossesBMT1.setBinContent(i, ratio);
        }
        histoGroupCrossesBMT.addDataSet(h1_ratioLostCrossesBMT1, 2); 
        
        H1F h1_ratioNomatchedCrossesBMT2 = new H1F("ratioNomatchedCrossesBMT2", "ratio of nomatched crosses for S2 BMT", 6, 0.5, 6.5);
        h1_ratioNomatchedCrossesBMT2.setTitleX("layer");
        h1_ratioNomatchedCrossesBMT2.setTitleY("Counts");
        h1_ratioNomatchedCrossesBMT2.setLineColor(1);        
        for(int i = 0; i < 6; i++){
            double ratio = histoGroupCrossesBMT.getH1F("numNomatchedCrossesBMT2").getBinContent(i)/(double)histoGroupCrossesBMT.getH1F("numCrossesBMT2").getBinContent(i);
            h1_ratioNomatchedCrossesBMT2.setBinContent(i, ratio);
        }
        histoGroupCrossesBMT.addDataSet(h1_ratioNomatchedCrossesBMT2, 5);        
    }                
    
    public static void main(String[] args) {
        OptionParser parser = new OptionParser("bgEffectsonCrosses");
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
        CrossBg analysis = new CrossBg();
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