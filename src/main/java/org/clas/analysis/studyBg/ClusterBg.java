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
import org.clas.element.Cluster;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;
import org.clas.utilities.Constants;

/**
 *
 * @author Tongtong Cao
 */
public class ClusterBg extends BaseAnalysis {      
    @Override
    public void createHistoGroupMap() { 
        //// BST        
        HistoGroup histoGroupClusterCentroidDiffBST = new HistoGroup("clusterCentroidDiffBST", 3, 2);
        HistoGroup histoGroupClusterMatchStatusBST1 = new HistoGroup("clusterMatchStatusBST1", 3, 2);
        HistoGroup histoGroupClusterMatchStatusBST2 = new HistoGroup("clusterMatchStatusBST2", 3, 2);
        HistoGroup histoGroupClusterEfficiencyVsPurityBST = new HistoGroup("clusterEfficiencyVsPurityBST", 3, 2);
        for (int i = 0; i < 6; i++) {
            H1F h1_centroidDiffBST = new H1F("centroid diff between matched clusters for BST L" + Integer.toString(i + 1),
                    "centroid diff between same-seed-strip clusters for BST for L" + Integer.toString(i + 1), 100, -0.1, 0.1);
            h1_centroidDiffBST.setTitleX("centroid diff");
            h1_centroidDiffBST.setTitleY("Counts");
            h1_centroidDiffBST.setLineColor(1);
            histoGroupClusterCentroidDiffBST.addDataSet(h1_centroidDiffBST, i);
            
            H1F h1_matchStatusBST1 = new H1F("cluster match status for S1 BST L" + Integer.toString(i + 1),
                    "cluster match status for S1 BST L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h1_matchStatusBST1.setTitleX("Is matched cluster found");
            h1_matchStatusBST1.setTitleY("Counts");
            h1_matchStatusBST1.setLineColor(1);
            histoGroupClusterMatchStatusBST1.addDataSet(h1_matchStatusBST1, i);
            
            H1F h_matchStatusBST2 = new H1F("cluster match status for S2 BST L" + Integer.toString(i + 1),
                    "cluster match status for S2 BST L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h_matchStatusBST2.setTitleX("Is matched cluster found");
            h_matchStatusBST2.setTitleY("Counts");
            h_matchStatusBST2.setLineColor(1);
            histoGroupClusterMatchStatusBST2.addDataSet(h_matchStatusBST2, i); 
            
            H2F h2_efficiencyVsPurityBST = new H2F("efficiency vs purity for cluster of BST L" + Integer.toString(i + 1),
                    "efficiency vs purity for cluster of BST L" + Integer.toString(i + 1), 30, 0, 1.01, 30, 0, 1.01);
            h2_efficiencyVsPurityBST.setTitleX("purity");
            h2_efficiencyVsPurityBST.setTitleY("efficiency");
            histoGroupClusterEfficiencyVsPurityBST.addDataSet(h2_efficiencyVsPurityBST, i);  
        }
        histoGroupMap.put(histoGroupClusterCentroidDiffBST.getName(), histoGroupClusterCentroidDiffBST);
        histoGroupMap.put(histoGroupClusterMatchStatusBST1.getName(), histoGroupClusterMatchStatusBST1);        
        histoGroupMap.put(histoGroupClusterMatchStatusBST2.getName(), histoGroupClusterMatchStatusBST2);   
        histoGroupMap.put(histoGroupClusterEfficiencyVsPurityBST.getName(), histoGroupClusterEfficiencyVsPurityBST); 
        
        HistoGroup histoGroupClustersBST = new HistoGroup("clustersBST", 3, 2);
        H1F h1_numClustersBST1 = new H1F("numClustersBST1", "# of clusters for S1 BST", 6, 0.5, 6.5);
        h1_numClustersBST1.setTitleX("layer");
        h1_numClustersBST1.setTitleY("Counts");
        h1_numClustersBST1.setLineColor(1);
        histoGroupClustersBST.addDataSet(h1_numClustersBST1, 0);  
        H1F h1_numLostClustersBST1 = new H1F("numLostClustersBST1", "# of lost clusters for S1 BST", 6, 0.5, 6.5);
        h1_numLostClustersBST1.setTitleX("layer");
        h1_numLostClustersBST1.setTitleY("Counts");
        h1_numLostClustersBST1.setLineColor(1);
        histoGroupClustersBST.addDataSet(h1_numLostClustersBST1, 1);             
        H1F h1_numClustersBST2 = new H1F("numClustersBST2", "# of clusters for S2 BST", 6, 0.5, 6.5);
        h1_numClustersBST2.setTitleX("layer");
        h1_numClustersBST2.setTitleY("Counts");
        h1_numClustersBST2.setLineColor(1);
        histoGroupClustersBST.addDataSet(h1_numClustersBST2, 3);                
        histoGroupMap.put(histoGroupClustersBST.getName(), histoGroupClustersBST);
        H1F h1_numNomatchedClustersBST2 = new H1F("numNomatchedClustersBST2", "# of nomatched clusters for S2 BST", 6, 0.5, 6.5);
        h1_numNomatchedClustersBST2.setTitleX("layer");
        h1_numNomatchedClustersBST2.setTitleY("Counts");
        h1_numNomatchedClustersBST2.setLineColor(1);
        histoGroupClustersBST.addDataSet(h1_numNomatchedClustersBST2, 4);                
        histoGroupMap.put(histoGroupClustersBST.getName(), histoGroupClustersBST);        
                
        //// BMT           
        HistoGroup histoGroupClusterCentroidDiffBMT = new HistoGroup("clusterCentroidDiffBMT", 3, 2);
        HistoGroup histoGroupClusterMatchStatusBMT1 = new HistoGroup("clusterMatchStatusBMT1", 3, 2);
        HistoGroup histoGroupClusterMatchStatusBMT2 = new HistoGroup("clusterMatchStatusBMT2", 3, 2);
        HistoGroup histoGroupClusterEfficiencyVsPurityBMT = new HistoGroup("clusterEfficiencyVsPurityBMT", 3, 2);
        for (int i = 0; i < 6; i++) {
            H1F h1_centroidDiffBMT = new H1F("centroid diff between matched clusters for BMT L" + Integer.toString(i + 1),
                    "centroid diff between same-seed-strip clusters for BMT for L" + Integer.toString(i + 1), 100, -0.1, 0.1);
            h1_centroidDiffBMT.setTitleX("centroid diff");
            h1_centroidDiffBMT.setTitleY("Counts");
            h1_centroidDiffBMT.setLineColor(1);
            histoGroupClusterCentroidDiffBMT.addDataSet(h1_centroidDiffBMT, i);            
            
            H1F h1_matchStatusBMT1 = new H1F("cluster match status for S1 BMT L" + Integer.toString(i + 1),
                    "cluster match status for S1 BMT L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h1_matchStatusBMT1.setTitleX("Is matched cluster found");
            h1_matchStatusBMT1.setTitleY("Counts");
            h1_matchStatusBMT1.setLineColor(1);
            histoGroupClusterMatchStatusBMT1.addDataSet(h1_matchStatusBMT1, i);
            
            H1F h2_matchStatusBMT2 = new H1F("cluster match status for S2 BMT L" + Integer.toString(i + 1),
                    "cluster match status for S2 BMT L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h2_matchStatusBMT2.setTitleX("Is matched cluster found");
            h2_matchStatusBMT2.setTitleY("Counts");
            h2_matchStatusBMT2.setLineColor(1);
            histoGroupClusterMatchStatusBMT2.addDataSet(h2_matchStatusBMT2, i); 
            
            H2F h_efficiencyVsPurityBMT = new H2F("efficiency vs purity for cluster of BMT L" + Integer.toString(i + 1),
                    "efficiency vs purity for cluster of BMT L" + Integer.toString(i + 1), 30, 0, 1.01, 30, 0, 1.01);
            h_efficiencyVsPurityBMT.setTitleX("purity");
            h_efficiencyVsPurityBMT.setTitleY("efficiency");
            histoGroupClusterEfficiencyVsPurityBMT.addDataSet(h_efficiencyVsPurityBMT, i);  
        }
        histoGroupMap.put(histoGroupClusterCentroidDiffBMT.getName(), histoGroupClusterCentroidDiffBMT);
        histoGroupMap.put(histoGroupClusterMatchStatusBMT1.getName(), histoGroupClusterMatchStatusBMT1);        
        histoGroupMap.put(histoGroupClusterMatchStatusBMT2.getName(), histoGroupClusterMatchStatusBMT2);   
        histoGroupMap.put(histoGroupClusterEfficiencyVsPurityBMT.getName(), histoGroupClusterEfficiencyVsPurityBMT);
        
        HistoGroup histoGroupClustersBMT = new HistoGroup("clustersBMT", 3, 2);
        H1F h1_numClustersBMT1 = new H1F("numClustersBMT1", "# of clusters for S1 BMT", 6, 0.5, 6.5);
        h1_numClustersBMT1.setTitleX("layer");
        h1_numClustersBMT1.setTitleY("Counts");
        h1_numClustersBMT1.setLineColor(1);
        histoGroupClustersBMT.addDataSet(h1_numClustersBMT1, 0);  
        H1F h1_numLostClustersBMT1 = new H1F("numLostClustersBMT1", "# of lost clusters for S1 BMT", 6, 0.5, 6.5);
        h1_numLostClustersBMT1.setTitleX("layer");
        h1_numLostClustersBMT1.setTitleY("Counts");
        h1_numLostClustersBMT1.setLineColor(1);
        histoGroupClustersBMT.addDataSet(h1_numLostClustersBMT1, 1);             
        H1F h1_numClustersBMT2 = new H1F("numClustersBMT2", "# of clusters for S2 BMT", 6, 0.5, 6.5);
        h1_numClustersBMT2.setTitleX("layer");
        h1_numClustersBMT2.setTitleY("Counts");
        h1_numClustersBMT2.setLineColor(1);
        histoGroupClustersBMT.addDataSet(h1_numClustersBMT2, 3);                
        histoGroupMap.put(histoGroupClustersBMT.getName(), histoGroupClustersBMT);
        H1F h1_numNomatchedClustersBMT2 = new H1F("numNomatchedClustersBMT2", "# of nomatched clusters for S2 BMT", 6, 0.5, 6.5);
        h1_numNomatchedClustersBMT2.setTitleX("layer");
        h1_numNomatchedClustersBMT2.setTitleY("Counts");
        h1_numNomatchedClustersBMT2.setLineColor(1);
        histoGroupClustersBMT.addDataSet(h1_numNomatchedClustersBMT2, 4);                
        histoGroupMap.put(histoGroupClustersBMT.getName(), histoGroupClustersBMT); 
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
        List<Cluster> clustersBST1 = localEvent1.getBSTClusters(Constants.PASS);        
        List<Cluster> clustersBST2 = localEvent2.getBSTClusters(Constants.PASS);        
        List<Cluster> clustersBMT1 = localEvent1.getBMTClusters(Constants.PASS);
        List<Cluster> clustersBMT2 = localEvent2.getBMTClusters(Constants.PASS);
        
        processEvent(clustersBST1, clustersBST2, clustersBMT1, clustersBMT2, match);
    }
    
    public void processEvent(List<Cluster> clustersBST1, List<Cluster> clustersBST2, List<Cluster> clustersBMT1, List<Cluster> clustersBMT2, Match match) {          
        match.clusterMatch(clustersBST1, clustersBST2, clustersBMT1, clustersBMT2);
        
        //// BST                 
        HistoGroup histoGroupClusterCentroidBST = histoGroupMap.get("clusterCentroidDiffBST");
        for(Cluster cls1 : match.get_map_cls1_cls2_BST().keySet()){
            Cluster cls2 = match.get_map_cls1_cls2_BST().get(cls1);
            histoGroupClusterCentroidBST.getH1F("centroid diff between matched clusters for BST L" + Integer.toString(cls1.layer())).fill(cls2.centroid() - cls1.centroid());

        }        
        
        // Since cases for matched cluster with different seed strip are rare, and difference of centroid between such matched clusters are quite big,
        // so such matched clusters are regarded as unmatched                                        
        // Matched clusters must have the same seed strip
        List<Cluster> matchedClsList1BST = new ArrayList<>(match.get_map_cls1_cls2_BST().keySet());
        List<Cluster> lostClsList1BST = new ArrayList();
        lostClsList1BST.addAll(clustersBST1);
        lostClsList1BST.removeAll(matchedClsList1BST);
        List<Cluster> matchedClsList2BST = new ArrayList<>(match.get_map_cls1_cls2_BST().values());
        List<Cluster> nomatchedClsList2BST = new ArrayList();
        nomatchedClsList2BST.addAll(clustersBST2);
        nomatchedClsList2BST.removeAll(matchedClsList2BST);
        
        HistoGroup histoGroupMatchStatusBST1 = histoGroupMap.get("clusterMatchStatusBST1");
        HistoGroup histoGroupMatchStatusBST2 = histoGroupMap.get("clusterMatchStatusBST2");        
        HistoGroup histoGroupClusterEfficiencyVsPurityBST = histoGroupMap.get("clusterEfficiencyVsPurityBST");
        for(Cluster cls : matchedClsList1BST){
            histoGroupMatchStatusBST1.getH1F("cluster match status for S1 BST L" + Integer.toString(cls.layer())).fill(1);
        }
        for(Cluster cls : lostClsList1BST){
            histoGroupMatchStatusBST1.getH1F("cluster match status for S1 BST L" + Integer.toString(cls.layer())).fill(0);
        }
        for(Cluster cls : matchedClsList2BST){
            histoGroupMatchStatusBST2.getH1F("cluster match status for S2 BST L" + Integer.toString(cls.layer())).fill(1);
            histoGroupClusterEfficiencyVsPurityBST.getH2F("efficiency vs purity for cluster of BST L" + Integer.toString(cls.layer())).fill(cls.getRatioNormalHits(), cls.getEfficiency());
        }
        for(Cluster cls : nomatchedClsList2BST){
            histoGroupMatchStatusBST2.getH1F("cluster match status for S2 BST L" + Integer.toString(cls.layer())).fill(0);
        }
        
        HistoGroup histoGroupClustersBST = histoGroupMap.get("clustersBST");
        for(Cluster cls : clustersBST1){
            histoGroupClustersBST.getH1F("numClustersBST1").fill(cls.layer());
        }
        for(Cluster cls : lostClsList1BST){
            histoGroupClustersBST.getH1F("numLostClustersBST1").fill(cls.layer());
        }
        
        for(Cluster cls : clustersBST2){
            histoGroupClustersBST.getH1F("numClustersBST2").fill(cls.layer());
        }
        for(Cluster cls : nomatchedClsList2BST){
            histoGroupClustersBST.getH1F("numNomatchedClustersBST2").fill(cls.layer());
        }        
        
        
        //// BMT                 
        HistoGroup histoGroupClusterCentroidDiffBMT = histoGroupMap.get("clusterCentroidDiffBMT");
        for(Cluster cls1 : match.get_map_cls1_cls2_BMT().keySet()){
            Cluster cls2 = match.get_map_cls1_cls2_BMT().get(cls1);
            histoGroupClusterCentroidDiffBMT.getH1F("centroid diff between matched clusters for BMT L" + Integer.toString(cls1.layer())).fill(cls2.centroid() - cls1.centroid());

        }        
        
        // Since cases for matched cluster with different seed strip are rare, and difference of centroid between such matched clusters are quite big,
        // so such matched clusters are regarded as unmatched                                        
        // Matched clusters must have the same seed strip
        List<Cluster> matchedClsList1BMT = new ArrayList<>(match.get_map_cls1_cls2_BMT().keySet());
        List<Cluster> lostClsList1BMT = new ArrayList();
        lostClsList1BMT.addAll(clustersBMT1);
        lostClsList1BMT.removeAll(matchedClsList1BMT);
        List<Cluster> matchedClsList2BMT = new ArrayList<>(match.get_map_cls1_cls2_BMT().values());
        List<Cluster> nomatchedClsList2BMT = new ArrayList();
        nomatchedClsList2BMT.addAll(clustersBMT2);
        nomatchedClsList2BMT.removeAll(matchedClsList2BMT);
        
        HistoGroup histoGroupMatchStatusBMT1 = histoGroupMap.get("clusterMatchStatusBMT1");
        HistoGroup histoGroupMatchStatusBMT2 = histoGroupMap.get("clusterMatchStatusBMT2");        
        HistoGroup histoGroupClusterEfficiencyVsPurityBMT = histoGroupMap.get("clusterEfficiencyVsPurityBMT");
        for(Cluster cls : matchedClsList1BMT){
            histoGroupMatchStatusBMT1.getH1F("cluster match status for S1 BMT L" + Integer.toString(cls.layer())).fill(1);
        }
        for(Cluster cls : lostClsList1BMT){
            histoGroupMatchStatusBMT1.getH1F("cluster match status for S1 BMT L" + Integer.toString(cls.layer())).fill(0);
        }
        for(Cluster cls : matchedClsList2BMT){
            histoGroupMatchStatusBMT2.getH1F("cluster match status for S2 BMT L" + Integer.toString(cls.layer())).fill(1);
            histoGroupClusterEfficiencyVsPurityBMT.getH2F("efficiency vs purity for cluster of BMT L" + Integer.toString(cls.layer())).fill(cls.getRatioNormalHits(), cls.getEfficiency());
        }
        for(Cluster cls : nomatchedClsList2BMT){
            histoGroupMatchStatusBMT2.getH1F("cluster match status for S2 BMT L" + Integer.toString(cls.layer())).fill(0);
        } 
        
        HistoGroup histoGroupClustersBMT = histoGroupMap.get("clustersBMT");
        for(Cluster cls : clustersBMT1){
            histoGroupClustersBMT.getH1F("numClustersBMT1").fill(cls.layer());
        }
        for(Cluster cls : lostClsList1BMT){
            histoGroupClustersBMT.getH1F("numLostClustersBMT1").fill(cls.layer());
        }
        
        for(Cluster cls : clustersBMT2){
            histoGroupClustersBMT.getH1F("numClustersBMT2").fill(cls.layer());
        }
        for(Cluster cls : nomatchedClsList2BMT){
            histoGroupClustersBMT.getH1F("numNomatchedClustersBMT2").fill(cls.layer());
        }         
    }
    
    public void postEventProcess(){ 
        //// BST        
        HistoGroup histoGroupClustersBST = histoGroupMap.get("clustersBST");             
        H1F h1_ratioLostClustersBST1 = new H1F("ratioLostClustersBST1", "ratio of lost clusters for S1 BST", 6, 0.5, 6.5);
        h1_ratioLostClustersBST1.setTitleX("layer");
        h1_ratioLostClustersBST1.setTitleY("Counts");
        h1_ratioLostClustersBST1.setLineColor(1);        
        for(int i = 0; i < 6; i++){
            double ratio = histoGroupClustersBST.getH1F("numLostClustersBST1").getBinContent(i)/(double)histoGroupClustersBST.getH1F("numClustersBST1").getBinContent(i);
            h1_ratioLostClustersBST1.setBinContent(i, ratio);
        }
        histoGroupClustersBST.addDataSet(h1_ratioLostClustersBST1, 2); 
        
        H1F h1_ratioNomatchedClustersBST2 = new H1F("ratioNomatchedClustersBST2", "ratio of nomatched clusters for S2 BST", 6, 0.5, 6.5);
        h1_ratioNomatchedClustersBST2.setTitleX("layer");
        h1_ratioNomatchedClustersBST2.setTitleY("Counts");
        h1_ratioNomatchedClustersBST2.setLineColor(1);        
        for(int i = 0; i < 6; i++){
            double ratio = histoGroupClustersBST.getH1F("numNomatchedClustersBST2").getBinContent(i)/(double)histoGroupClustersBST.getH1F("numClustersBST2").getBinContent(i);
            h1_ratioNomatchedClustersBST2.setBinContent(i, ratio);
        }
        histoGroupClustersBST.addDataSet(h1_ratioNomatchedClustersBST2, 5);
        
        //// BMT
        HistoGroup histoGroupClustersBMT = histoGroupMap.get("clustersBMT");             
        H1F h1_ratioLostClustersBMT1 = new H1F("ratioLostClustersBMT1", "ratio of lost clusters for S1 BMT", 6, 0.5, 6.5);
        h1_ratioLostClustersBMT1.setTitleX("layer");
        h1_ratioLostClustersBMT1.setTitleY("Counts");
        h1_ratioLostClustersBMT1.setLineColor(1);        
        for(int i = 0; i < 6; i++){
            double ratio = histoGroupClustersBMT.getH1F("numLostClustersBMT1").getBinContent(i)/(double)histoGroupClustersBMT.getH1F("numClustersBMT1").getBinContent(i);
            h1_ratioLostClustersBMT1.setBinContent(i, ratio);
        }
        histoGroupClustersBMT.addDataSet(h1_ratioLostClustersBMT1, 2); 
        
        H1F h1_ratioNomatchedClustersBMT2 = new H1F("ratioNomatchedClustersBMT2", "ratio of nomatched clusters for S2 BMT", 6, 0.5, 6.5);
        h1_ratioNomatchedClustersBMT2.setTitleX("layer");
        h1_ratioNomatchedClustersBMT2.setTitleY("Counts");
        h1_ratioNomatchedClustersBMT2.setLineColor(1);        
        for(int i = 0; i < 6; i++){
            double ratio = histoGroupClustersBMT.getH1F("numNomatchedClustersBMT2").getBinContent(i)/(double)histoGroupClustersBMT.getH1F("numClustersBMT2").getBinContent(i);
            h1_ratioNomatchedClustersBMT2.setBinContent(i, ratio);
        }
        histoGroupClustersBMT.addDataSet(h1_ratioNomatchedClustersBMT2, 5);  
    }                
    
    
    public static void main(String[] args) {
        OptionParser parser = new OptionParser("bgEffectsonClusters");
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
        ClusterBg analysis = new ClusterBg();
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