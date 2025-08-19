package org.clas.analysis.studySeed;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
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
import org.clas.utilities.Constants;
import org.clas.element.Seed;
import org.clas.element.Track;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;

/**
 *
 * @author Tongtong Cao
 */
public class CompareAllSeedsTracks extends BaseAnalysis{ 
    final static double PURITYCUT = 0.95;
    private double totalTrackWithPurityCut = 0;
    private double tracksLostbySeedRoutine = 0;
    
    
    public CompareAllSeedsTracks(){}
    
    @Override
    public void createHistoGroupMap(){
        
        HistoGroup histoGroupOverview = new HistoGroup("overview", 3, 2);
        H2F h2_ndfComp = new H2F("ndfComp", "comparision of ndf", 9, 0.5, 9.5, 9, 0.5, 9.5);
        h2_ndfComp.setTitleX("ndf for seed");
        h2_ndfComp.setTitleY("ndf for track");
        histoGroupOverview.addDataSet(h2_ndfComp, 0);
        
        H2F h2_chi2Comp = new H2F("chi2Comp", "comparision of chi2", 100, 0, 500, 100, 0, 500);
        h2_chi2Comp.setTitleX("chi2 for seed");
        h2_chi2Comp.setTitleY("chi2 for track");
        histoGroupOverview.addDataSet(h2_chi2Comp, 1);                
        H1F h1_clusterOverlappingTrackListSize = new H1F("clusterOverlappingTrackListSize","# of cluster-overlapping tracks/seeds", 100, 0.5, 100.5);
        h1_clusterOverlappingTrackListSize.setTitleX("# of cluster-overlapping tracks");
        h1_clusterOverlappingTrackListSize.setTitleY("Counts");
        h1_clusterOverlappingTrackListSize.setLineColor(1);
        histoGroupOverview.addDataSet(h1_clusterOverlappingTrackListSize, 2);
        
        H1F h1_ifSameBestTrackbyTwoRoutines= new H1F("ifSameBestTrackbyTwoRoutines","if same best track by two routines", 2, -0.5, 1.5);
        h1_ifSameBestTrackbyTwoRoutines.setTitleX("if same best track by two routines");
        h1_ifSameBestTrackbyTwoRoutines.setTitleY("Counts");
        h1_ifSameBestTrackbyTwoRoutines.setLineColor(1);
        histoGroupOverview.addDataSet(h1_ifSameBestTrackbyTwoRoutines, 3);
        
        H2F h2_ratioMatchedClusters = new H2F("ratioMatchedClusters", "ratio of matched clusters", 30, 0, 1.01, 30, 0, 1.01);
        h2_ratioMatchedClusters.setTitleX("ratio of matched clusters for track by best seed routine");
        h2_ratioMatchedClusters.setTitleY("ratio of matched clusters for track by best track routine");   
        histoGroupOverview.addDataSet(h2_ratioMatchedClusters, 4); 

        H1F h1_ifSameBestTrackOrNoOverlappingCluster= new H1F("ifSameBestTrackOrNoOverlappingCluster","if same best track or no overlapping cluster", 3, -1.5, 1.5);
        h1_ifSameBestTrackOrNoOverlappingCluster.setTitleX("if same best track by two routines or no overlapping cluster");
        h1_ifSameBestTrackOrNoOverlappingCluster.setTitleY("Counts");
        h1_ifSameBestTrackOrNoOverlappingCluster.setLineColor(1);
        histoGroupOverview.addDataSet(h1_ifSameBestTrackOrNoOverlappingCluster, 5);                
        histoGroupMap.put(histoGroupOverview.getName(), histoGroupOverview);
        
        HistoGroup histoGroupSameBestTrack = new HistoGroup("sameBestTrack", 3, 2);
        H1F h1_purityHitSameBestTrack = new H1F("purityHitSameBestTrack", "purity for hits", 30, 0, 1.01);
        h1_purityHitSameBestTrack.setTitleX("purity");
        h1_purityHitSameBestTrack.setTitleY("efficiency");
        histoGroupSameBestTrack.addDataSet(h1_purityHitSameBestTrack, 0);
        
        H1F h1_purityClusterSameBestTrack = new H1F("purityClusterSameBestTrack", "purity for clusters", 30, 0, 1.01);
        h1_purityClusterSameBestTrack.setTitleX("purity");
        h1_purityClusterSameBestTrack.setTitleY("counts");
        histoGroupSameBestTrack.addDataSet(h1_purityClusterSameBestTrack, 1);
        histoGroupMap.put(histoGroupSameBestTrack.getName(), histoGroupSameBestTrack);
        
        
        HistoGroup histoGroupDiffBestTrackNoSharedCluster = new HistoGroup("diffBestTrackNoSharedCluster", 3, 2);
        H2F h2_purityCompHitDiffBestTrackNoSharedCluster = new H2F("purityCompHitDiffBestTrackNoSharedCluster", "purity comparision for hits", 30, 0, 1.01, 30, 0, 1.01);
        h2_purityCompHitDiffBestTrackNoSharedCluster.setTitleX("purity for track with best seed");
        h2_purityCompHitDiffBestTrackNoSharedCluster.setTitleY("purity for track with best track");
        histoGroupDiffBestTrackNoSharedCluster.addDataSet(h2_purityCompHitDiffBestTrackNoSharedCluster, 0);
        
        H2F h2_purityCompClusterDiffBestTrackNoSharedCluster = new H2F("purityCompClusterDiffBestTrackNoSharedCluster", "purity comparision for clusters", 30, 0, 1.01, 30, 0, 1.01);
        h2_purityCompClusterDiffBestTrackNoSharedCluster.setTitleX("purity for track with best seed");
        h2_purityCompClusterDiffBestTrackNoSharedCluster.setTitleY("purity for track with best track");
        histoGroupDiffBestTrackNoSharedCluster.addDataSet(h2_purityCompClusterDiffBestTrackNoSharedCluster, 1);                     
        histoGroupMap.put(histoGroupDiffBestTrackNoSharedCluster.getName(), histoGroupDiffBestTrackNoSharedCluster);
        
        HistoGroup histoGroupDiffBestTrackSharedCluster = new HistoGroup("diffBestTrackSharedCluster", 3, 2);
        H2F h2_purityCompHitDiffBestTrackSharedCluster = new H2F("purityCompHitDiffBestTrackSharedCluster", "purity comparision for hits", 30, 0, 1.01, 30, 0, 1.01);
        h2_purityCompHitDiffBestTrackSharedCluster.setTitleX("purity for track with best seed");
        h2_purityCompHitDiffBestTrackSharedCluster.setTitleY("purity for track with best track");
        histoGroupDiffBestTrackSharedCluster.addDataSet(h2_purityCompHitDiffBestTrackSharedCluster, 0);
        
        H2F h2_purityCompClusterDiffBestTrackSharedCluster = new H2F("purityCompClusterDiffBestTrackSharedCluster", "purity comparision for clusters", 30, 0, 1.01, 30, 0, 1.01);
        h2_purityCompClusterDiffBestTrackSharedCluster.setTitleX("purity for track with best seed");
        h2_purityCompClusterDiffBestTrackSharedCluster.setTitleY("purity for track with best track");
        histoGroupDiffBestTrackSharedCluster.addDataSet(h2_purityCompClusterDiffBestTrackSharedCluster, 1);                     
        histoGroupMap.put(histoGroupDiffBestTrackSharedCluster.getName(), histoGroupDiffBestTrackSharedCluster);        
        
    }
             
    public void processEvent(Event event){        
        //Read banks
         //////// Read banks
        LocalEvent localEvent = new LocalEvent(reader, event);
        List<Seed> seeds = localEvent.getSeeds(Constants.PASS);
        List<Track> tracks = localEvent.getTracks(Constants.PASS);
        
        HistoGroup histoGroupOverview = histoGroupMap.get("overview");  
        HistoGroup histoGroupSameBestTrack = histoGroupMap.get("sameBestTrack"); 
        HistoGroup histoGroupDiffBestTrackNoSharedCluster = histoGroupMap.get("diffBestTrackNoSharedCluster"); 
        HistoGroup histoGroupDiffBestTrackSharedCluster = histoGroupMap.get("diffBestTrackSharedCluster"); 
        
        for(Track trk : tracks){
            Seed seed = trk.getSeed();
            histoGroupOverview.getH2F("ndfComp").fill(seed.ndf(), trk.ndf());  
            histoGroupOverview.getH2F("chi2Comp").fill(seed.chi2(), trk.chi2());
            
            if((double)trk.getNumNormalHits()/trk.getHits().size() > PURITYCUT) totalTrackWithPurityCut++;
        }
        
        List<List<Track>> clusterOverlappingTracksList = clusterOverlappingTracksList(tracks);
        for(List<Track> clusterOverlappingTracks : clusterOverlappingTracksList){
            histoGroupOverview.getH1F("clusterOverlappingTrackListSize").fill(clusterOverlappingTracks.size());
            
            Track bestTrackByTrackRoutine = getBestTrackByTrackRoutine(clusterOverlappingTracks);
            Track bestTrackBySeedRoutine = getBestTrackBySeedRoutine(clusterOverlappingTracks);
            
            double purityHitBestTrackByTrackRoutine = (double)bestTrackByTrackRoutine.getNumNormalHits()/bestTrackByTrackRoutine.getHits().size();
            double purityHitBestTrackBySeedRoutine = (double)bestTrackBySeedRoutine.getNumNormalHits()/bestTrackBySeedRoutine.getHits().size();
            
            double purityClusterBestTrackByTrackRoutine = (double)bestTrackByTrackRoutine.getNumNormalSeedStripClusters()/bestTrackByTrackRoutine.getClusters().size();
            double purityClusterBestTrackBySeedRoutine = (double)bestTrackBySeedRoutine.getNumNormalSeedStripClusters()/bestTrackBySeedRoutine.getClusters().size();
            
            if(bestTrackByTrackRoutine.id() == bestTrackBySeedRoutine.id()) {
                histoGroupOverview.getH1F("ifSameBestTrackbyTwoRoutines").fill(1);
                histoGroupOverview.getH1F("ifSameBestTrackOrNoOverlappingCluster").fill(1);
                
                histoGroupSameBestTrack.getH1F("purityHitSameBestTrack").fill(purityHitBestTrackByTrackRoutine);
                histoGroupSameBestTrack.getH1F("purityClusterSameBestTrack").fill(purityClusterBestTrackByTrackRoutine);
            } 
            else{
                histoGroupOverview.getH1F("ifSameBestTrackbyTwoRoutines").fill(0);
                
                int numMatchedClusters = bestTrackByTrackRoutine.numMatchedClustersWithTrack(bestTrackBySeedRoutine);
                            
                histoGroupOverview.getH2F("ratioMatchedClusters").fill((double)numMatchedClusters/bestTrackBySeedRoutine.getClusters().size(), (double)numMatchedClusters/bestTrackByTrackRoutine.getClusters().size());
                
                if(numMatchedClusters == 0){
                    histoGroupOverview.getH1F("ifSameBestTrackOrNoOverlappingCluster").fill(-1);
                    histoGroupDiffBestTrackNoSharedCluster.getH2F("purityCompHitDiffBestTrackNoSharedCluster").fill(purityHitBestTrackBySeedRoutine, purityHitBestTrackByTrackRoutine);
                    histoGroupDiffBestTrackNoSharedCluster.getH2F("purityCompClusterDiffBestTrackNoSharedCluster").fill(purityClusterBestTrackBySeedRoutine, purityClusterBestTrackByTrackRoutine);
                }
                else{
                    histoGroupOverview.getH1F("ifSameBestTrackOrNoOverlappingCluster").fill(0);
                    histoGroupDiffBestTrackSharedCluster.getH2F("purityCompHitDiffBestTrackSharedCluster").fill(purityHitBestTrackBySeedRoutine, purityHitBestTrackByTrackRoutine);
                    histoGroupDiffBestTrackSharedCluster.getH2F("purityCompClusterDiffBestTrackSharedCluster").fill(purityClusterBestTrackBySeedRoutine, purityClusterBestTrackByTrackRoutine);               
                    
                    if(purityHitBestTrackByTrackRoutine > PURITYCUT && purityHitBestTrackBySeedRoutine < PURITYCUT) tracksLostbySeedRoutine++;
                }
                
                
            } 
        }
    }
    
    //// Routine: 
    // 1st priority: max NDF
    // 2st priority: min chi2    
    private Track getBestTrackByTrackRoutine(List<Track> tracks){
        int maxNDF = 0;
        List<Track> trackListWithMaxNDF = new ArrayList();
        for(Track trk : tracks){
            int ndf = trk.ndf();
            if(ndf > maxNDF){
                maxNDF = ndf;
                trackListWithMaxNDF.clear();
                trackListWithMaxNDF.add(trk);
            }
            else if(ndf == maxNDF) trackListWithMaxNDF.add(trk);
        }
        
        double minChi2 = 10000000;
        int bestTrackIndex = 0;
        for(int i = 0; i < trackListWithMaxNDF.size(); i++){
            double chi2 = trackListWithMaxNDF.get(i).chi2();
            if(chi2 < minChi2){
                minChi2 = chi2;
                bestTrackIndex = i;
            }
        }
        
        return trackListWithMaxNDF.get(bestTrackIndex);
    }
        /*
    public List<Track> getOverlapRemovedTracks(List<Track> tracks) { 
        List<Track> ovlrem = new ArrayList<>();
        ovlrem.addAll(tracks);
        while(ovlrem.size()!=getOverlapRemovedTracks1Pass(ovlrem).size()) {
            ovlrem = getOverlapRemovedTracks1Pass(ovlrem);
        }
        
        while(ovlrem.size()!=appendTrackList(tracks, ovlrem).size() ){
            ovlrem = appendTrackList(tracks, ovlrem);
        }
        
        return ovlrem;
    }
    
    
    public List<Track> getOverlapRemovedTracks1Pass(List<Track> tracks) { 
        List<List<Track>> clusterOverlappingTracksList = clusterOverlappingTracksList(tracks);
       
        Map<Double, Track> trackMap = new HashMap<>();
        for(List<Track> clusterOverlappingTracks : clusterOverlappingTracksList){                        
            Track bestTrackByTrackRoutine = getBestTrackByTrackRoutine(clusterOverlappingTracks);
            
            if(trackMap.containsKey(bestTrackByTrackRoutine.chi2())) {
                trackMap.replace(bestTrackByTrackRoutine.chi2(), bestTrackByTrackRoutine);
            } else {
                trackMap.put(bestTrackByTrackRoutine.chi2(), bestTrackByTrackRoutine);
            }
            
        }
        
        List<Track> goodTracks = new ArrayList<>();  
        goodTracks.addAll(new ArrayList<>(trackMap.values()));
        return goodTracks;
    }
    

    private  static List<Track> appendTrackList(List<Track> tracks, List<Track> ovlrem) {
        
        List<Track> overlappingSeeds =  new ArrayList<>();
        List<Track> ovlrem2 = new ArrayList<>();
        ovlrem2.addAll(tracks);
        ovlrem2.removeAll(ovlrem);
        for (Track s2 : ovlrem2) {
            for(Track s : ovlrem) {
                if(s2.overlapWithUsingClusters(s)) {
                    s2.setId(-99999);
                }
            }
        }
        for(Seed s2 :  ovlrem2) {
            if(s2.getId()==-99999) 
                overlappingSeeds.add(s2);
        }
        ovlrem2.removeAll(overlappingSeeds);
        //get the best seeds from what remains
        if(!ovlrem2.isEmpty()) {
            while(ovlrem2.size()!=getOverlapRemovedSeeds1Pass(ovlrem2).size()) {
                ovlrem2 = getOverlapRemovedSeeds1Pass(ovlrem2);
            }
        }
        ovlrem2.addAll(ovlrem);
        
        return ovlrem2;
    }
    */
    
    
    
    
    
    //// Routine: 
    // 1st priority: max NDF
    // 2st priority: min chi2
    private Track getBestTrackBySeedRoutine(List<Track> tracks){
        int maxNDF = 0;
        List<Track> trackListWithMaxNDF = new ArrayList();
        for(Track trk : tracks){
            int ndf = trk.getSeed().ndf();
            if(ndf > maxNDF){
                maxNDF = ndf;
                trackListWithMaxNDF.clear();
                trackListWithMaxNDF.add(trk);
            }
            else if(ndf == maxNDF) trackListWithMaxNDF.add(trk);
        }
        
        double minChi2 = 10000000;
        int bestTrackIndex = 0;
        for(int i = 0; i < trackListWithMaxNDF.size(); i++){
            double chi2 = trackListWithMaxNDF.get(i).getSeed().chi2();
            if(chi2 < minChi2){
                minChi2 = chi2;
                bestTrackIndex = i;
            }
        }
                
        return trackListWithMaxNDF.get(bestTrackIndex);
    }    
    
    private List<List<Track>> clusterOverlappingTracksList(List<Track> trackList){  
        List<List<Track>> clusterOverlappingTracksList =  new ArrayList();
        for (int i = 0; i < trackList.size(); i++) {            
            Track t1 = trackList.get(i);
            List<Track> selectedTracks =  new ArrayList<>();  
            selectedTracks.add(t1);
            for(int j=0; j<trackList.size(); j++ ) {
                Track t2 = trackList.get(j);
                if(i!=j && t1.isClusterOverlapping(t2)) {
                    selectedTracks.add(t2);
                }
            }
            if(!isTrackListIncluded(clusterOverlappingTracksList, selectedTracks)) clusterOverlappingTracksList.add(selectedTracks);
        }
        
        return clusterOverlappingTracksList;
    }
    
    private boolean isTrackListIncluded(List<List<Track>> trackListList, List<Track> currentTrackList){
        for(List<Track> trackList : trackListList){
            int numSameTracks = 0;
            for(Track trk : trackList){
                for(Track currentTrk : currentTrackList){
                    if(currentTrk.isSameClusterswithTrack(trk)) {
                        numSameTracks++;
                        break;
                    }
                }
            }
            if(currentTrackList.size() == numSameTracks) return true;
        }
        
        return false;
    }
    
    public void postEventProcess() {
        System.out.println(totalTrackWithPurityCut + "  " + tracksLostbySeedRoutine);
                      
    }            
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("SeedRecon");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o"          ,"",     "output file name prefix");
        parser.addOption("-n"          ,"-1",   "maximum number of events to process");        
        parser.addOption("-plot"       ,"1",    "display histograms (0/1)");
        parser.addOption("-bg", "1", "if bg (0/1)");
        parser.addOption("-pass", "1", "if bg (1/2)");
        parser.addOption("-histo"      ,"0",    "read histogram file (0/1)");        
        parser.parse(args);
        
        String namePrefix  = parser.getOption("-o").stringValue(); 
        int maxEvents  = parser.getOption("-n").intValue();    
        boolean displayPlots   = (parser.getOption("-plot").intValue()!=0);
        boolean readHistos   = (parser.getOption("-histo").intValue()!=0); 
        boolean bg = (parser.getOption("-bg").intValue() != 0);
        Constants.BG = bg;
        int pass  = parser.getOption("-pass").intValue(); 
        Constants.PASS = pass;
        
        List<String> inputList = parser.getInputList();
        if(inputList.isEmpty()==true){
            parser.printUsage();
            inputList.add("/Users/caot/research/clas12/data/mc/uRWELL/upgradeTrackingWithuRWELL/bg/applyCTDAF/pt3_1pt6/origin/recon_before_update_bg.hipo");
            maxEvents = 1000;
            //System.out.println("\n >>>> error: no input file is specified....\n");
            //System.exit(0);
        }

        String histoName   = "histo.hipo"; 
        if(!namePrefix.isEmpty()) {
            histoName  = namePrefix + "_" + histoName;
        }
        
        CompareAllSeedsTracks analysis = new CompareAllSeedsTracks();
        analysis.createHistoGroupMap();
        
        if(!readHistos) {                 
            HipoReader reader = new HipoReader();
            reader.open(inputList.get(0));        

            SchemaFactory schema = reader.getSchemaFactory();
            analysis.initReader(new Banks(schema));

            int counter=0;
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
                frame.setSize(1500, 1000);
                frame.add(canvas);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }        
    }
    
}
