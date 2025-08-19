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
import org.clas.element.Hit;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;
import org.clas.utilities.Constants;

/**
 *
 * @author Tongtong Cao
 */
public class HitBg extends BaseAnalysis {      
    @Override
    public void createHistoGroupMap() { 
        //// BST  
        HistoGroup histoGroupOverViewBST2 = new HistoGroup("overViewBST2", 3, 2);
        H1F h1_numTotalHitsBST2 = new H1F("numTotalHitsBST2", "# of total hits for S2 BST", 100, 0, 1000);
        h1_numTotalHitsBST2.setTitleX("Is matched hit found");
        h1_numTotalHitsBST2.setTitleY("Counts");
        h1_numTotalHitsBST2.setLineColor(1);
        histoGroupOverViewBST2.addDataSet(h1_numTotalHitsBST2, 0);
        histoGroupMap.put(histoGroupOverViewBST2.getName(), histoGroupOverViewBST2); 
            
        
        HistoGroup histoGroupHitMatchStatusBST1 = new HistoGroup("hitMatchStatusBST1", 3, 2);
        HistoGroup histoGroupHitMatchStatusBST2 = new HistoGroup("hitMatchStatusBST2", 3, 2);
        for (int i = 0; i < 6; i++) {            
            H1F h1_matchStatusBST1 = new H1F("hit match status for S1 BST L" + Integer.toString(i + 1),
                    "hit match status for S1 BST L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h1_matchStatusBST1.setTitleX("Is matched hit found");
            h1_matchStatusBST1.setTitleY("Counts");
            h1_matchStatusBST1.setLineColor(1);
            histoGroupHitMatchStatusBST1.addDataSet(h1_matchStatusBST1, i);
            
            H1F h_matchStatusBST2 = new H1F("hit match status for S2 BST L" + Integer.toString(i + 1),
                    "hit match status for S2 BST L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h_matchStatusBST2.setTitleX("Is matched hit found");
            h_matchStatusBST2.setTitleY("Counts");
            h_matchStatusBST2.setLineColor(1);
            histoGroupHitMatchStatusBST2.addDataSet(h_matchStatusBST2, i); 
        }
        histoGroupMap.put(histoGroupHitMatchStatusBST1.getName(), histoGroupHitMatchStatusBST1);        
        histoGroupMap.put(histoGroupHitMatchStatusBST2.getName(), histoGroupHitMatchStatusBST2);   
        
        HistoGroup histoGroupHitsBST = new HistoGroup("hitsBST", 3, 2);
        H1F h1_numHitsBST1 = new H1F("numHitsBST1", "# of hits for S1 BST", 6, 0.5, 6.5);
        h1_numHitsBST1.setTitleX("layer");
        h1_numHitsBST1.setTitleY("Counts");
        h1_numHitsBST1.setLineColor(1);
        histoGroupHitsBST.addDataSet(h1_numHitsBST1, 0);  
        H1F h1_numLostHitsBST1 = new H1F("numLostHitsBST1", "# of lost hits for S1 BST", 6, 0.5, 6.5);
        h1_numLostHitsBST1.setTitleX("layer");
        h1_numLostHitsBST1.setTitleY("Counts");
        h1_numLostHitsBST1.setLineColor(1);
        histoGroupHitsBST.addDataSet(h1_numLostHitsBST1, 1);             
        H1F h1_numHitsBST2 = new H1F("numHitsBST2", "# of hits for S2 BST", 6, 0.5, 6.5);
        h1_numHitsBST2.setTitleX("layer");
        h1_numHitsBST2.setTitleY("Counts");
        h1_numHitsBST2.setLineColor(1);
        histoGroupHitsBST.addDataSet(h1_numHitsBST2, 3);                
        histoGroupMap.put(histoGroupHitsBST.getName(), histoGroupHitsBST);
        H1F h1_numNomatchedHitsBST2 = new H1F("numNomatchedHitsBST2", "# of nomatched hits for S2 BST", 6, 0.5, 6.5);
        h1_numNomatchedHitsBST2.setTitleX("layer");
        h1_numNomatchedHitsBST2.setTitleY("Counts");
        h1_numNomatchedHitsBST2.setLineColor(1);
        histoGroupHitsBST.addDataSet(h1_numNomatchedHitsBST2, 4);                
        histoGroupMap.put(histoGroupHitsBST.getName(), histoGroupHitsBST);
        
        HistoGroup histoGroupRemainedHitsBST1 = new HistoGroup("remainedHitsBST1", 3, 2);
        H1F h1_energyRemainedHitsBST1 = new H1F("energyRemainedHitsBST1", "energy of remained hits for S1 BST", 100, 0, 800);
        h1_energyRemainedHitsBST1.setTitleX("energy");
        h1_energyRemainedHitsBST1.setTitleY("Counts");
        h1_energyRemainedHitsBST1.setLineColor(1);
        histoGroupRemainedHitsBST1.addDataSet(h1_energyRemainedHitsBST1, 0);        
        H1F h1_timeRemainedHitsBST1 = new H1F("timeRemainedHitsBST1", "time energy of remained hits for S1 BST", 100, 0, 550);
        h1_timeRemainedHitsBST1.setTitleX("time");
        h1_timeRemainedHitsBST1.setTitleY("Counts");
        h1_timeRemainedHitsBST1.setLineColor(1);
        histoGroupRemainedHitsBST1.addDataSet(h1_timeRemainedHitsBST1, 1);        
        H1F h1_sectorRemainedHitsBST1 = new H1F("sectorRemainedHitsBST1", "sector of remained hits for S1 BST", 18, 0.5, 18.5);
        h1_sectorRemainedHitsBST1.setTitleX("sector");
        h1_sectorRemainedHitsBST1.setTitleY("Counts");
        h1_sectorRemainedHitsBST1.setLineColor(1);
        histoGroupRemainedHitsBST1.addDataSet(h1_sectorRemainedHitsBST1, 2);                
        H2F h2_stripVsLayerRemainedHitsBST1 = new H2F("stripVsLayerRemainedHitsBST1", "strip vs layer of remained hits for S1 BST", 6, 0.5, 6.5, 256, 0.5, 256.5);
        h2_stripVsLayerRemainedHitsBST1.setTitleX("layer");
        h2_stripVsLayerRemainedHitsBST1.setTitleY("strip");
        histoGroupRemainedHitsBST1.addDataSet(h2_stripVsLayerRemainedHitsBST1, 3);
        histoGroupMap.put(histoGroupRemainedHitsBST1.getName(), histoGroupRemainedHitsBST1);         
        
        HistoGroup histoGroupLostHitsBST1 = new HistoGroup("lostHitsBST1", 3, 2);
        H1F h1_energyLostHitsBST1 = new H1F("energyLostHitsBST1", "energy of lost hits for S1 BST", 100, 0, 800);
        h1_energyLostHitsBST1.setTitleX("energy");
        h1_energyLostHitsBST1.setTitleY("Counts");
        h1_energyLostHitsBST1.setLineColor(1);
        histoGroupLostHitsBST1.addDataSet(h1_energyLostHitsBST1, 0);        
        H1F h1_timeLostHitsBST1 = new H1F("timeLostHitsBST1", "time energy of lost hits for S1 BST", 100, 0, 550);
        h1_timeLostHitsBST1.setTitleX("time");
        h1_timeLostHitsBST1.setTitleY("Counts");
        h1_timeLostHitsBST1.setLineColor(1);
        histoGroupLostHitsBST1.addDataSet(h1_timeLostHitsBST1, 1);        
        H1F h1_sectorLostHitsBST1 = new H1F("sectorLostHitsBST1", "sector of lost hits for S1 BST", 18, 0.5, 18.5);
        h1_sectorLostHitsBST1.setTitleX("sector");
        h1_sectorLostHitsBST1.setTitleY("Counts");
        h1_sectorLostHitsBST1.setLineColor(1);
        histoGroupLostHitsBST1.addDataSet(h1_sectorLostHitsBST1, 2);                
        H2F h2_stripVsLayerLostHitsBST1 = new H2F("stripVsLayerLostHitsBST1", "strip vs layer of lost hits for S1 BST", 6, 0.5, 6.5, 256, 0.5, 256.5);
        h2_stripVsLayerLostHitsBST1.setTitleX("layer");
        h2_stripVsLayerLostHitsBST1.setTitleY("strip");
        histoGroupLostHitsBST1.addDataSet(h2_stripVsLayerLostHitsBST1, 3);
        histoGroupMap.put(histoGroupLostHitsBST1.getName(), histoGroupLostHitsBST1);                
                
        //// BMT 
        HistoGroup histoGroupOverViewBMT2 = new HistoGroup("overViewBMT2", 3, 2);
        H1F h1_numTotalHitsBMT2 = new H1F("numTotalHitsBMT2", "# of total hits for S2 BMT", 100, 0, 1000);
        h1_numTotalHitsBMT2.setTitleX("Is matched hit found");
        h1_numTotalHitsBMT2.setTitleY("Counts");
        h1_numTotalHitsBMT2.setLineColor(1);
        histoGroupOverViewBMT2.addDataSet(h1_numTotalHitsBMT2, 0);
        histoGroupMap.put(histoGroupOverViewBMT2.getName(), histoGroupOverViewBMT2);
        
        HistoGroup histoGroupHitMatchStatusBMT1 = new HistoGroup("hitMatchStatusBMT1", 3, 2);
        HistoGroup histoGroupHitMatchStatusBMT2 = new HistoGroup("hitMatchStatusBMT2", 3, 2);
        for (int i = 0; i < 6; i++) {                     
            H1F h1_matchStatusBMT1 = new H1F("hit match status for S1 BMT L" + Integer.toString(i + 1),
                    "hit match status for S1 BMT L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h1_matchStatusBMT1.setTitleX("Is matched hit found");
            h1_matchStatusBMT1.setTitleY("Counts");
            h1_matchStatusBMT1.setLineColor(1);
            histoGroupHitMatchStatusBMT1.addDataSet(h1_matchStatusBMT1, i);
            
            H1F h2_matchStatusBMT2 = new H1F("hit match status for S2 BMT L" + Integer.toString(i + 1),
                    "hit match status for S2 BMT L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h2_matchStatusBMT2.setTitleX("Is matched hit found");
            h2_matchStatusBMT2.setTitleY("Counts");
            h2_matchStatusBMT2.setLineColor(1);
            histoGroupHitMatchStatusBMT2.addDataSet(h2_matchStatusBMT2, i);             
        }
        histoGroupMap.put(histoGroupHitMatchStatusBMT1.getName(), histoGroupHitMatchStatusBMT1);        
        histoGroupMap.put(histoGroupHitMatchStatusBMT2.getName(), histoGroupHitMatchStatusBMT2);   
        
        HistoGroup histoGroupHitsBMT = new HistoGroup("hitsBMT", 3, 2);
        H1F h1_numHitsBMT1 = new H1F("numHitsBMT1", "# of hits for S1 BMT", 6, 0.5, 6.5);
        h1_numHitsBMT1.setTitleX("layer");
        h1_numHitsBMT1.setTitleY("Counts");
        h1_numHitsBMT1.setLineColor(1);
        histoGroupHitsBMT.addDataSet(h1_numHitsBMT1, 0);  
        H1F h1_numLostHitsBMT1 = new H1F("numLostHitsBMT1", "# of lost hits for S1 BMT", 6, 0.5, 6.5);
        h1_numLostHitsBMT1.setTitleX("layer");
        h1_numLostHitsBMT1.setTitleY("Counts");
        h1_numLostHitsBMT1.setLineColor(1);
        histoGroupHitsBMT.addDataSet(h1_numLostHitsBMT1, 1);             
        H1F h1_numHitsBMT2 = new H1F("numHitsBMT2", "# of hits for S2 BMT", 6, 0.5, 6.5);
        h1_numHitsBMT2.setTitleX("layer");
        h1_numHitsBMT2.setTitleY("Counts");
        h1_numHitsBMT2.setLineColor(1);
        histoGroupHitsBMT.addDataSet(h1_numHitsBMT2, 3);                
        histoGroupMap.put(histoGroupHitsBMT.getName(), histoGroupHitsBMT);
        H1F h1_numNomatchedHitsBMT2 = new H1F("numNomatchedHitsBMT2", "# of nomatched hits for S2 BMT", 6, 0.5, 6.5);
        h1_numNomatchedHitsBMT2.setTitleX("layer");
        h1_numNomatchedHitsBMT2.setTitleY("Counts");
        h1_numNomatchedHitsBMT2.setLineColor(1);
        histoGroupHitsBMT.addDataSet(h1_numNomatchedHitsBMT2, 4);                
        histoGroupMap.put(histoGroupHitsBMT.getName(), histoGroupHitsBMT);
        
        HistoGroup histoGroupRemainedHitsBMT1 = new HistoGroup("remainedHitsBMT1", 3, 2);
        H1F h1_energyRemainedHitsBMT1 = new H1F("energyRemainedHitsBMT1", "energy of remained hits for S1 BMT", 100, 0, 800);
        h1_energyRemainedHitsBMT1.setTitleX("energy");
        h1_energyRemainedHitsBMT1.setTitleY("Counts");
        h1_energyRemainedHitsBMT1.setLineColor(1);
        histoGroupRemainedHitsBMT1.addDataSet(h1_energyRemainedHitsBMT1, 0);        
        H1F h1_timeRemainedHitsBMT1 = new H1F("timeRemainedHitsBMT1", "time of remained hits for S1 BMT", 100, 0, 550);
        h1_timeRemainedHitsBMT1.setTitleX("time");
        h1_timeRemainedHitsBMT1.setTitleY("Counts");
        h1_timeRemainedHitsBMT1.setLineColor(1);
        histoGroupRemainedHitsBMT1.addDataSet(h1_timeRemainedHitsBMT1, 1);        
        H1F h1_sectorRemainedHitsBMT1 = new H1F("sectorRemainedHitsBMT1", "sector of remained hits for S1 BMT", 3, 0.5, 3.5);
        h1_sectorRemainedHitsBMT1.setTitleX("sector");
        h1_sectorRemainedHitsBMT1.setTitleY("Counts");
        h1_sectorRemainedHitsBMT1.setLineColor(1);
        histoGroupRemainedHitsBMT1.addDataSet(h1_sectorRemainedHitsBMT1, 2);                
        for(int i = 1; i <= 3; i++){
            H2F h2_stripVsLayerRemainedHitsBMT1 = new H2F("stripVsLayerRemainedHitsBMT1 for sector" + Integer.toString(i), "strip vs layer of remained hits for S1 BMT sector" + Integer.toString(i), 6, 0.5, 6.5, 1200, 0.5, 1200.5);
            h2_stripVsLayerRemainedHitsBMT1.setTitleX("layer");
            h2_stripVsLayerRemainedHitsBMT1.setTitleY("strip");
            histoGroupRemainedHitsBMT1.addDataSet(h2_stripVsLayerRemainedHitsBMT1, i+2);        
        }
        histoGroupMap.put(histoGroupRemainedHitsBMT1.getName(), histoGroupRemainedHitsBMT1);           
        
        HistoGroup histoGroupLostHitsBMT1 = new HistoGroup("lostHitsBMT1", 3, 2);
        H1F h1_energyLostHitsBMT1 = new H1F("energyLostHitsBMT1", "energy of lost hits for S1 BMT", 100, 0, 800);
        h1_energyLostHitsBMT1.setTitleX("energy");
        h1_energyLostHitsBMT1.setTitleY("Counts");
        h1_energyLostHitsBMT1.setLineColor(1);
        histoGroupLostHitsBMT1.addDataSet(h1_energyLostHitsBMT1, 0);        
        H1F h1_timeLostHitsBMT1 = new H1F("timeLostHitsBMT1", "time of lost hits for S1 BMT", 100, 0, 550);
        h1_timeLostHitsBMT1.setTitleX("time");
        h1_timeLostHitsBMT1.setTitleY("Counts");
        h1_timeLostHitsBMT1.setLineColor(1);
        histoGroupLostHitsBMT1.addDataSet(h1_timeLostHitsBMT1, 1);        
        H1F h1_sectorLostHitsBMT1 = new H1F("sectorLostHitsBMT1", "sector of lost hits for S1 BMT", 3, 0.5, 3.5);
        h1_sectorLostHitsBMT1.setTitleX("sector");
        h1_sectorLostHitsBMT1.setTitleY("Counts");
        h1_sectorLostHitsBMT1.setLineColor(1);
        histoGroupLostHitsBMT1.addDataSet(h1_sectorLostHitsBMT1, 2);                
        for(int i = 1; i <= 3; i++){
            H2F h2_stripVsLayerLostHitsBMT1 = new H2F("stripVsLayerLostHitsBMT1 for sector" + Integer.toString(i), "strip vs layer of lost hits for S1 BMT sector" + Integer.toString(i), 6, 0.5, 6.5, 1200, 0.5, 1200.5);
            h2_stripVsLayerLostHitsBMT1.setTitleX("layer");
            h2_stripVsLayerLostHitsBMT1.setTitleY("strip");
            histoGroupLostHitsBMT1.addDataSet(h2_stripVsLayerLostHitsBMT1, i+2);        
        }
        histoGroupMap.put(histoGroupLostHitsBMT1.getName(), histoGroupLostHitsBMT1);        
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
        List<Hit> hitsBST1 = localEvent1.getBSTHits(Constants.PASS);        
        List<Hit> hitsBST2 = localEvent2.getBSTHits(Constants.PASS);        
        List<Hit> hitsBMT1 = localEvent1.getBMTHits(Constants.PASS);
        List<Hit> hitsBMT2 = localEvent2.getBMTHits(Constants.PASS);
        
        processEvent(hitsBST1, hitsBST2, hitsBMT1, hitsBMT2, match);
    }
    
    public void processEvent(List<Hit> hitsBST1, List<Hit> hitsBST2, List<Hit> hitsBMT1, List<Hit> hitsBMT2, Match match) {                        
        match.hitMatch(hitsBST1, hitsBST2, hitsBMT1, hitsBMT2);
        
        //// BST
        HistoGroup histoGroupOverViewBST2 = histoGroupMap.get("overViewBST2");
        histoGroupOverViewBST2.getH1F("numTotalHitsBST2").fill(hitsBST2.size());
                
        List<Hit> matchedClsList1BST = new ArrayList<>(match.get_map_hit1_hit2_BST().keySet());
        List<Hit> lostClsList1BST = new ArrayList();
        lostClsList1BST.addAll(hitsBST1);
        lostClsList1BST.removeAll(matchedClsList1BST);
        List<Hit> matchedClsList2BST = new ArrayList<>(match.get_map_hit1_hit2_BST().values());
        List<Hit> nomatchedClsList2BST = new ArrayList();
        nomatchedClsList2BST.addAll(hitsBST2);
        nomatchedClsList2BST.removeAll(matchedClsList2BST);
        
        HistoGroup histoGroupMatchStatusBST1 = histoGroupMap.get("hitMatchStatusBST1");
        HistoGroup histoGroupMatchStatusBST2 = histoGroupMap.get("hitMatchStatusBST2");  
        HistoGroup histoGroupLostHitsBST1 = histoGroupMap.get("lostHitsBST1");
        HistoGroup histoGroupRemainedHitsBST1 = histoGroupMap.get("remainedHitsBST1");
        for(Hit hit : matchedClsList1BST){
            histoGroupMatchStatusBST1.getH1F("hit match status for S1 BST L" + Integer.toString(hit.layer())).fill(1);
            histoGroupMatchStatusBST1.getH1F("hit match status for S1 BST L" + Integer.toString(hit.layer())).fill(0);
            histoGroupRemainedHitsBST1.getH1F("energyRemainedHitsBST1").fill(hit.energy());
            histoGroupRemainedHitsBST1.getH1F("timeRemainedHitsBST1").fill(hit.time());
            histoGroupRemainedHitsBST1.getH1F("sectorRemainedHitsBST1").fill(hit.sector());
            histoGroupRemainedHitsBST1.getH2F("stripVsLayerRemainedHitsBST1").fill(hit.layer(), hit.strip()); 
        }
        for(Hit hit : lostClsList1BST){
            histoGroupMatchStatusBST1.getH1F("hit match status for S1 BST L" + Integer.toString(hit.layer())).fill(0);
            histoGroupLostHitsBST1.getH1F("energyLostHitsBST1").fill(hit.energy());
            histoGroupLostHitsBST1.getH1F("timeLostHitsBST1").fill(hit.time());
            histoGroupLostHitsBST1.getH1F("sectorLostHitsBST1").fill(hit.sector());
            histoGroupLostHitsBST1.getH2F("stripVsLayerLostHitsBST1").fill(hit.layer(), hit.strip());            
        }
        for(Hit hit : matchedClsList2BST){
            histoGroupMatchStatusBST2.getH1F("hit match status for S2 BST L" + Integer.toString(hit.layer())).fill(1);
        }
        for(Hit hit : nomatchedClsList2BST){
            histoGroupMatchStatusBST2.getH1F("hit match status for S2 BST L" + Integer.toString(hit.layer())).fill(0);
        }
        
        HistoGroup histoGroupHitsBST = histoGroupMap.get("hitsBST");
        for(Hit hit : hitsBST1){
            histoGroupHitsBST.getH1F("numHitsBST1").fill(hit.layer());
        }
        for(Hit hit : lostClsList1BST){
            histoGroupHitsBST.getH1F("numLostHitsBST1").fill(hit.layer());
        }
        
        for(Hit hit : hitsBST2){
            histoGroupHitsBST.getH1F("numHitsBST2").fill(hit.layer());
        }
        for(Hit hit : nomatchedClsList2BST){
            histoGroupHitsBST.getH1F("numNomatchedHitsBST2").fill(hit.layer());
        }        
        
        
        //// BMT
        HistoGroup histoGroupOverViewBMT2 = histoGroupMap.get("overViewBMT2");
        histoGroupOverViewBMT2.getH1F("numTotalHitsBMT2").fill(hitsBMT2.size());
        
        List<Hit> matchedClsList1BMT = new ArrayList<>(match.get_map_hit1_hit2_BMT().keySet());
        List<Hit> lostClsList1BMT = new ArrayList();
        lostClsList1BMT.addAll(hitsBMT1);
        lostClsList1BMT.removeAll(matchedClsList1BMT);
        List<Hit> matchedClsList2BMT = new ArrayList<>(match.get_map_hit1_hit2_BMT().values());
        List<Hit> nomatchedClsList2BMT = new ArrayList();
        nomatchedClsList2BMT.addAll(hitsBMT2);
        nomatchedClsList2BMT.removeAll(matchedClsList2BMT);
        
        HistoGroup histoGroupMatchStatusBMT1 = histoGroupMap.get("hitMatchStatusBMT1");
        HistoGroup histoGroupMatchStatusBMT2 = histoGroupMap.get("hitMatchStatusBMT2");
        HistoGroup histoGroupLostHitsBMT1 = histoGroupMap.get("lostHitsBMT1"); 
        HistoGroup histoGroupRemainedHitsBMT1 = histoGroupMap.get("remainedHitsBMT1"); 
        for(Hit hit : matchedClsList1BMT){
            histoGroupMatchStatusBMT1.getH1F("hit match status for S1 BMT L" + Integer.toString(hit.layer())).fill(1);            
            histoGroupMatchStatusBMT1.getH1F("hit match status for S1 BMT L" + Integer.toString(hit.layer())).fill(0);
            histoGroupRemainedHitsBMT1.getH1F("energyRemainedHitsBMT1").fill(hit.energy());
            histoGroupRemainedHitsBMT1.getH1F("timeRemainedHitsBMT1").fill(hit.time());
            histoGroupRemainedHitsBMT1.getH1F("sectorRemainedHitsBMT1").fill(hit.sector());
            histoGroupRemainedHitsBMT1.getH2F("stripVsLayerRemainedHitsBMT1 for sector"+Integer.toString(hit.sector())).fill(hit.layer(), hit.strip());             
        }
        for(Hit hit : lostClsList1BMT){
            histoGroupMatchStatusBMT1.getH1F("hit match status for S1 BMT L" + Integer.toString(hit.layer())).fill(0);
            histoGroupLostHitsBMT1.getH1F("energyLostHitsBMT1").fill(hit.energy());
            histoGroupLostHitsBMT1.getH1F("timeLostHitsBMT1").fill(hit.time());
            histoGroupLostHitsBMT1.getH1F("sectorLostHitsBMT1").fill(hit.sector());
            histoGroupLostHitsBMT1.getH2F("stripVsLayerLostHitsBMT1 for sector"+Integer.toString(hit.sector())).fill(hit.layer(), hit.strip()); 
        }
        for(Hit hit : matchedClsList2BMT){
            histoGroupMatchStatusBMT2.getH1F("hit match status for S2 BMT L" + Integer.toString(hit.layer())).fill(1);
        }
        for(Hit hit : nomatchedClsList2BMT){
            histoGroupMatchStatusBMT2.getH1F("hit match status for S2 BMT L" + Integer.toString(hit.layer())).fill(0);
        } 
        
        HistoGroup histoGroupHitsBMT = histoGroupMap.get("hitsBMT");
        for(Hit hit : hitsBMT1){
            histoGroupHitsBMT.getH1F("numHitsBMT1").fill(hit.layer());
        }
        for(Hit hit : lostClsList1BMT){
            histoGroupHitsBMT.getH1F("numLostHitsBMT1").fill(hit.layer());
        }
        
        for(Hit hit : hitsBMT2){
            histoGroupHitsBMT.getH1F("numHitsBMT2").fill(hit.layer());
        }
        for(Hit hit : nomatchedClsList2BMT){
            histoGroupHitsBMT.getH1F("numNomatchedHitsBMT2").fill(hit.layer());
        }         
    }
    
    public void postEventProcess(){ 
        //// BST        
        HistoGroup histoGroupHitsBST = histoGroupMap.get("hitsBST");             
        H1F h1_ratioLostHitsBST1 = new H1F("ratioLostHitsBST1", "ratio of lost hits for S1 BST", 6, 0.5, 6.5);
        h1_ratioLostHitsBST1.setTitleX("layer");
        h1_ratioLostHitsBST1.setTitleY("Counts");
        h1_ratioLostHitsBST1.setLineColor(1);        
        for(int i = 0; i < 6; i++){
            double ratio = histoGroupHitsBST.getH1F("numLostHitsBST1").getBinContent(i)/(double)histoGroupHitsBST.getH1F("numHitsBST1").getBinContent(i);
            h1_ratioLostHitsBST1.setBinContent(i, ratio);
        }
        histoGroupHitsBST.addDataSet(h1_ratioLostHitsBST1, 2); 
        
        H1F h1_ratioNomatchedHitsBST2 = new H1F("ratioNomatchedHitsBST2", "ratio of nomatched hits for S2 BST", 6, 0.5, 6.5);
        h1_ratioNomatchedHitsBST2.setTitleX("layer");
        h1_ratioNomatchedHitsBST2.setTitleY("Counts");
        h1_ratioNomatchedHitsBST2.setLineColor(1);        
        for(int i = 0; i < 6; i++){
            double ratio = histoGroupHitsBST.getH1F("numNomatchedHitsBST2").getBinContent(i)/(double)histoGroupHitsBST.getH1F("numHitsBST2").getBinContent(i);
            h1_ratioNomatchedHitsBST2.setBinContent(i, ratio);
        }
        histoGroupHitsBST.addDataSet(h1_ratioNomatchedHitsBST2, 5);
        
        //// BMT
        HistoGroup histoGroupHitsBMT = histoGroupMap.get("hitsBMT");             
        H1F h1_ratioLostHitsBMT1 = new H1F("ratioLostHitsBMT1", "ratio of lost hits for S1 BMT", 6, 0.5, 6.5);
        h1_ratioLostHitsBMT1.setTitleX("layer");
        h1_ratioLostHitsBMT1.setTitleY("Counts");
        h1_ratioLostHitsBMT1.setLineColor(1);        
        for(int i = 0; i < 6; i++){
            double ratio = histoGroupHitsBMT.getH1F("numLostHitsBMT1").getBinContent(i)/(double)histoGroupHitsBMT.getH1F("numHitsBMT1").getBinContent(i);
            h1_ratioLostHitsBMT1.setBinContent(i, ratio);
        }
        histoGroupHitsBMT.addDataSet(h1_ratioLostHitsBMT1, 2); 
        
        H1F h1_ratioNomatchedHitsBMT2 = new H1F("ratioNomatchedHitsBMT2", "ratio of nomatched hits for S2 BMT", 6, 0.5, 6.5);
        h1_ratioNomatchedHitsBMT2.setTitleX("layer");
        h1_ratioNomatchedHitsBMT2.setTitleY("Counts");
        h1_ratioNomatchedHitsBMT2.setLineColor(1);        
        for(int i = 0; i < 6; i++){
            double ratio = histoGroupHitsBMT.getH1F("numNomatchedHitsBMT2").getBinContent(i)/(double)histoGroupHitsBMT.getH1F("numHitsBMT2").getBinContent(i);
            h1_ratioNomatchedHitsBMT2.setBinContent(i, ratio);
        }
        histoGroupHitsBMT.addDataSet(h1_ratioNomatchedHitsBMT2, 5);  
    }                
    
    
    public static void main(String[] args) {
        OptionParser parser = new OptionParser("bgEffectsonHits");
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
        HitBg analysis = new HitBg();
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