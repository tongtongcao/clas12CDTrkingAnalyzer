package org.clas.analysis.studyBg;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javafx.util.Pair;

import org.clas.reader.LocalEvent;
import org.clas.element.Hit;
import org.clas.element.Cluster;
import org.clas.element.Cross;
import org.clas.element.Seed;
import org.clas.element.Track;
import org.clas.utilities.Constants;

/**
 * Match between pure and bg samples
 *      1: pure sample
 *      2: sample with bg
 * Make hit match map
 * Make match maps step by step:
 *      1st: make match map between clusters based on seed strips of clusters
 *      2nd: make match map between crosses based on matched clusters
 *      3rd: make match map between seeds with most matched crosses and highest purity
 * @author Tongtong Cao
 */
public class Match {
    static final double CLUSTERCENTROIDDIFFCUT = Double.POSITIVE_INFINITY;
    private LocalEvent localEvent1;
    private LocalEvent localEvent2; 
    private Map<Hit, Hit> map_hit1_hit2_BST= new HashMap();
    private Map<Hit, Hit> map_hit1_hit2_BMT = new HashMap();    
    private Map<Cluster, Cluster> map_cls1_cls2_BST= new HashMap();
    private Map<Cluster, Cluster> map_cls1_cls2_BMT = new HashMap();
    private Map<Cross, Cross> map_crs1_crs2_BST= new HashMap();
    private Map<Cross, Cross> map_crs1_crs2_BMT= new HashMap();    
    private Map<Seed, Seed> map_seed1_seed2= new HashMap();
    private Map<Track, Track> map_track1_track2= new HashMap();
    private Map<Track, Track> map_track1_pseudoTrack2= new HashMap();
    
    public Match(){}
        
    public Match(LocalEvent localEvent1, LocalEvent localEvent2){
        this.localEvent1 = localEvent1;
        this.localEvent2 = localEvent2;
    }
    
    public void hitMatch(){
        List<Hit> hitsBST1 = localEvent1.getBSTHits(Constants.PASS);        
        List<Hit> hitsBST2 = localEvent2.getBSTHits(Constants.PASS);                
        List<Hit> hitsBMT1 = localEvent1.getBMTHits(Constants.PASS);
        List<Hit> hitsBMT2 = localEvent2.getBMTHits(Constants.PASS);
        
        hitMatchBST(hitsBST1, hitsBST2);
        hitMatchBMT(hitsBMT1, hitsBMT2);
    }
    
    public void hitMatch(List<Hit> hitsBST1, List<Hit> hitsBST2, List<Hit> hitsBMT1, List<Hit> hitsBMT2){        
        hitMatchBST(hitsBST1, hitsBST2);
        hitMatchBMT(hitsBMT1, hitsBMT2);
    }
    
    public void hitMatchBST(List<Hit> hits1, List<Hit> hits2){
        makeHitMatchMap(hits1, hits2, map_hit1_hit2_BST);
    }
    
    public void hitMatchBMT(List<Hit> hits1, List<Hit> hits2){
        makeHitMatchMap(hits1, hits2, map_hit1_hit2_BMT);
    }
    
    public Map<Hit, Hit> get_map_hit1_hit2_BST(){
        return map_hit1_hit2_BST;
    }        
    
    public Map<Hit, Hit> get_map_hit1_hit2_BMT(){
        return map_hit1_hit2_BMT;
    }         
           
    ////// Make hit map between pure and bg samples
    public void makeHitMatchMap(List<Hit> hits1, List<Hit> hits2, Map<Hit, Hit> map_hit1_hit2) {  
        for(Hit hit1 : hits1){
            for(Hit hit2 : hits2){
                if(hit2.isMatchedHit(hit1)){
                    map_hit1_hit2.put(hit1, hit2);
                    break;
                }
            }
        }                
    }    
    
    public void clusterMatch(){
        List<Cluster> clustersBST1 = localEvent1.getBSTClusters(Constants.PASS);        
        List<Cluster> clustersBST2 = localEvent2.getBSTClusters(Constants.PASS);                
        List<Cluster> clustersBMT1 = localEvent1.getBMTClusters(Constants.PASS);
        List<Cluster> clustersBMT2 = localEvent2.getBMTClusters(Constants.PASS);
        
        clusterMatchBST(clustersBST1, clustersBST2);
        clusterMatchBMT(clustersBMT1, clustersBMT2);
    }
    
    public void clusterMatch(List<Cluster> clustersBST1, List<Cluster> clustersBST2, List<Cluster> clustersBMT1, List<Cluster> clustersBMT2){        
        clusterMatchBST(clustersBST1, clustersBST2);
        clusterMatchBMT(clustersBMT1, clustersBMT2);
    }
    
    public void clusterMatchBST(List<Cluster> clusters1, List<Cluster> clusters2){
        makeClusterMatchMap(clusters1, clusters2, map_cls1_cls2_BST);
    }
    
    public void clusterMatchBMT(List<Cluster> clusters1, List<Cluster> clusters2){
        makeClusterMatchMap(clusters1, clusters2, map_cls1_cls2_BMT);
    }
    
    public Map<Cluster, Cluster> get_map_cls1_cls2_BST(){
        return map_cls1_cls2_BST;
    }        
    
    public Map<Cluster, Cluster> get_map_cls1_cls2_BMT(){
        return map_cls1_cls2_BMT;
    }         
           
    ////// Make cluster map with matched hits between pure and bg samples
    // Priorities for map: seed strip (priority 1), purity = truth hits / hits on cluster in S2 (priority 2) and efficiency = truth hits / all truth hits in S1 (priority 3)
    // Matched clusters could have no matched seed strip
    // CLUSTERCENTROIDDIFFCUT: parameter for centrod difference cut; positive infinity as default 
    public void makeClusterMatchMap(List<Cluster> clusters1, List<Cluster> clusters2, Map<Cluster, Cluster> map_cls1_cls2) {                         
        for(Cluster cls1 : clusters1){
            List<Cluster> matchedClsListWithMatchedHits = new ArrayList();
            for(Cluster cls2 : clusters2){
                if(cls1.clusterMatchedHits(cls2) > 0 && Math.abs(cls2.centroid() - cls1.centroid()) < CLUSTERCENTROIDDIFFCUT) matchedClsListWithMatchedHits.add(cls2);
            }
            
            if(!matchedClsListWithMatchedHits.isEmpty()){
            
                Hit seedHit1 = cls1.getSeedHit();
                List<Cluster> matchedClsListWithSameSeedStrip = new ArrayList();
                for(Cluster cls2 : matchedClsListWithMatchedHits){                
                    Hit seedHit2 = cls2.getSeedHit();
                    if(seedHit2.isMatchedHit(seedHit1)) matchedClsListWithSameSeedStrip.add(cls2);                      
                }
                
                List<Cluster> matchedClsList = new ArrayList();
                if(!matchedClsListWithSameSeedStrip.isEmpty()) matchedClsList.addAll(matchedClsListWithSameSeedStrip);
                else matchedClsList.addAll(matchedClsListWithMatchedHits);
                
                List<Cluster> matchedClsListWithLargetPurity = new ArrayList();
                double maxPurity = -1;  
                for(Cluster cls2 : matchedClsList){
                    double normalRatio = cls2.getRatioNormalHits();
                    if(normalRatio > maxPurity) {
                        maxPurity = normalRatio;
                        matchedClsListWithLargetPurity.clear();
                        matchedClsListWithLargetPurity.add(cls2);                    
                    }
                    else if(normalRatio == maxPurity){
                        matchedClsListWithLargetPurity.add(cls2);
                    }
                }                
                if(!matchedClsListWithLargetPurity.isEmpty()){
                    double maxEfficiency = -1;
                    Cluster matchedCls2 = null;
                    for(Cluster cls2 : matchedClsListWithLargetPurity){
                        double efficency = cls2.getNumNormalHits()/(double) cls1.size();                        
                        if(efficency > maxEfficiency) {
                            maxEfficiency = efficency;
                            matchedCls2 = cls2;
                        }                        
                    }
                    if(matchedCls2 != null) {
                        cls1.setFoundMatchedCluster(true);
                        matchedCls2.setFoundMatchedCluster(true);
                        cls1.setMatchedClusterId(matchedCls2.id());
                        matchedCls2.setMatchedClusterId(cls1.id());
                        matchedCls2.setTotalTruthHits(cls1.size());
                        matchedCls2.setEfficiency(maxEfficiency);
                        map_cls1_cls2.put(cls1, matchedCls2);                            
                    }
                }
                
            }
        }               
    }
    
    public void crossMatch(){
        if(map_cls1_cls2_BST.isEmpty() && map_cls1_cls2_BMT.isEmpty() && localEvent1 != null && localEvent2 != null) clusterMatch();
        
        List<Cross> crossesBST1 = localEvent1.getBSTCrosses(Constants.PASS);        
        List<Cross> crossesBST2 = localEvent2.getBSTCrosses(Constants.PASS);                
        List<Cross> crossesBMT1 = localEvent1.getBMTCrosses(Constants.PASS);
        List<Cross> crossesBMT2 = localEvent2.getBMTCrosses(Constants.PASS);
        
        makeCrossMatchMapBST(crossesBST1, crossesBST2);
        makeCrossMatchMapBMT(crossesBMT1, crossesBMT2);
    }    
    
    public void crossMatch(List<Cross> crossesBST1, List<Cross> crossesBST2, List<Cross> crossesBMT1, List<Cross> crossesBMT2){   
        if(map_cls1_cls2_BST.isEmpty() && map_cls1_cls2_BMT.isEmpty() && localEvent1 != null && localEvent2 != null) clusterMatch();
        
        makeCrossMatchMapBST(crossesBST1, crossesBST2);
        makeCrossMatchMapBMT(crossesBMT1, crossesBMT2);
    }
    

    
    public Map<Cross, Cross> get_map_crs1_crs2_BST(){
        return map_crs1_crs2_BST;
    }        
    
    public Map<Cross, Cross> get_map_crs1_crs2_BMT(){
        return map_crs1_crs2_BMT;
    }
    
    public void makeCrossMatchMapBST(List<Cross> crosses1, List<Cross> crosses2){
        for(Cross crs1 : crosses1){
            int matchedCluster1Id = -1;
            int matchedCluster2Id = -1;
            if(crs1.getCluster1() != null) matchedCluster1Id = crs1.getCluster1().getMatchedClusterId();
            if(crs1.getCluster2() != null) matchedCluster2Id = crs1.getCluster2().getMatchedClusterId();
            
            if(matchedCluster1Id != -1 && matchedCluster2Id != -1){
                for(Cross crs2 : crosses2){
                    int cluster1Id = -1;
                    int cluster2Id = -1;
                    if(crs1.getCluster1() != null) cluster1Id = crs2.getCluster1().id();
                    if(crs1.getCluster2() != null) cluster2Id = crs2.getCluster2().id();
                    if(matchedCluster1Id == cluster1Id && matchedCluster2Id == cluster2Id){
                        crs1.setFoundMatchedCross(true);
                        crs2.setFoundMatchedCross(true);
                        crs1.setMatchedCrossId(crs2.id());
                        crs2.setMatchedCrossId(crs1.id());
                        int totalTruthHits = crs1.getHits().size();
                        crs2.setTotalTruthHits(totalTruthHits);
                        double efficency = crs2.getNumNormalHits()/(double)totalTruthHits;
                        crs2.setEfficiency(efficency);
                        map_crs1_crs2_BST.put(crs1, crs2);
                        break;
                    }
                }
            }
        }
    }
    
    public void makeCrossMatchMapBMT(List<Cross> crosses1, List<Cross> crosses2){
        for(Cross crs1 : crosses1){
            int matchedCluster1Id = -1;
            if(crs1.getCluster1() != null) matchedCluster1Id = crs1.getCluster1().getMatchedClusterId();
            
            if(matchedCluster1Id != -1){
                for(Cross crs2 : crosses2){
                    int cluster1Id = -1;
                    if(crs1.getCluster1() != null) cluster1Id = crs2.getCluster1().id();
                    if(matchedCluster1Id == cluster1Id){
                        crs1.setFoundMatchedCross(true);
                        crs2.setFoundMatchedCross(true);
                        crs1.setMatchedCrossId(crs2.id());
                        crs2.setMatchedCrossId(crs1.id());
                        int totalTruthHits = crs1.getHits().size();
                        crs2.setTotalTruthHits(totalTruthHits);
                        double efficency = crs2.getNumNormalHits()/(double)totalTruthHits;
                        crs2.setEfficiency(efficency);                        
                        map_crs1_crs2_BMT.put(crs1, crs2);
                        break;
                    }
                }
            }
        }
    } 
    
    public void seedMatch(){
        if(map_cls1_cls2_BST.isEmpty() && map_cls1_cls2_BMT.isEmpty() && localEvent1 != null && localEvent2 != null) clusterMatch();
        if(map_crs1_crs2_BST.isEmpty() && map_crs1_crs2_BMT.isEmpty() && localEvent1 != null && localEvent2 != null) crossMatch();
        
        List<Seed> seeds1 = localEvent1.getSeeds(Constants.PASS);        
        List<Seed> seeds2 = localEvent2.getSeeds(Constants.PASS);                
        
        makeSeedMatchMap(seeds1, seeds2);
    }    
    
    public void seedMatch(List<Seed> seeds1, List<Seed> seeds2){   
        if(map_cls1_cls2_BST.isEmpty() && map_cls1_cls2_BMT.isEmpty() && localEvent1 != null && localEvent2 != null) clusterMatch();
        if(map_crs1_crs2_BST.isEmpty() && map_crs1_crs2_BMT.isEmpty() && localEvent1 != null && localEvent2 != null) crossMatch();
        
        makeSeedMatchMap(seeds1, seeds2);
    }

    public Map<Seed, Seed> get_map_seed1_seed2(){
        return map_seed1_seed2;
    } 
    
    public void makeSeedMatchMap(List<Seed> seeds1, List<Seed> seeds2){
        for(Seed seed1 : seeds1){
            Map<Cross, Integer> map_cr1_crs2Id_BST = new HashMap();
            for(Cross crs1 : seed1.getBSTCrosses()){
                if(crs1.getMatchedCrossId() != -1) map_cr1_crs2Id_BST.put(crs1, crs1.getMatchedCrossId());
            }
            Map<Cross, Integer> map_cr1_crs2Id_BMT = new HashMap();
            for(Cross crs1 : seed1.getBMTCrosses()){
                if(crs1.getMatchedCrossId() != -1) map_cr1_crs2Id_BMT.put(crs1, crs1.getMatchedCrossId());
            }            
            if(!map_cr1_crs2Id_BST.isEmpty() || !map_cr1_crs2Id_BMT.isEmpty()){
                List<Seed> matchedSeedListWithMostMatchedCrosses2 = new ArrayList();
                List<List<Pair<Integer, Integer>>> matchedCrossPairListList = new ArrayList();
                List<List<Pair<Integer, Integer>>> matchedClusterPairListList = new ArrayList();
                int maxNumMatchedCrosses = 0;              
                for(Seed seed2 : seeds2){
                    List<Pair<Integer, Integer>> matchedCrossPairList = new ArrayList();
                    List<Pair<Integer, Integer>> matchedClusterPairList = new ArrayList();
                    for(Cross crs1 : map_cr1_crs2Id_BST.keySet()){
                        for(Cross crs2 : seed2.getBSTCrosses()){
                            if(crs2.id() == map_cr1_crs2Id_BST.get(crs1)){
                                matchedCrossPairList.add(new Pair<>(crs1.id(), crs2.id()));
                                matchedClusterPairList.add(new Pair<>(crs1.getCluster1().id(), crs2.getCluster1().id()));
                                matchedClusterPairList.add(new Pair<>(crs1.getCluster2().id(), crs2.getCluster2().id()));
                                break;
                            }
                        }
                    }
                    for(Cross crs1 : map_cr1_crs2Id_BMT.keySet()){
                        for(Cross crs2 : seed2.getBMTCrosses()){
                            if(crs2.id() == map_cr1_crs2Id_BMT.get(crs1)){
                                matchedCrossPairList.add(new Pair<>(crs1.id(), crs2.id()));
                                matchedClusterPairList.add(new Pair<>(crs1.getCluster1().id(), crs2.getCluster1().id()));
                                break;
                            }
                        }
                    }                                        
                    if(matchedCrossPairList.size() > maxNumMatchedCrosses){
                        maxNumMatchedCrosses = matchedCrossPairList.size();
                        matchedSeedListWithMostMatchedCrosses2.clear();
                        matchedSeedListWithMostMatchedCrosses2.add(seed2);
                        matchedCrossPairListList.add(matchedCrossPairList);
                        matchedClusterPairListList.add(matchedClusterPairList);
                    }
                    else if(!matchedCrossPairList.isEmpty() && matchedCrossPairList.size() == maxNumMatchedCrosses){
                        matchedSeedListWithMostMatchedCrosses2.add(seed2);
                        matchedCrossPairListList.add(matchedCrossPairList);
                        matchedClusterPairListList.add(matchedClusterPairList);
                    }
                }
                                
                double maxPurity = -1;
                int seedIndexWithMaxPurity = -1;
                for(int i = 0; i < matchedSeedListWithMostMatchedCrosses2.size(); i++){
                    Seed seed2 = matchedSeedListWithMostMatchedCrosses2.get(i);
                    if(seed2.getRatioNormalHits() > maxPurity){
                        maxPurity = seed2.getRatioNormalHits();
                        seedIndexWithMaxPurity = i;
                    }
                }
                
                if(seedIndexWithMaxPurity != -1){
                    Seed matchedSeed2 = matchedSeedListWithMostMatchedCrosses2.get(seedIndexWithMaxPurity);
                    List<Pair<Integer, Integer>> finalMatchedCrossPairList = matchedCrossPairListList.get(seedIndexWithMaxPurity);
                    List<Pair<Integer, Integer>> finalMatchedClusterPairList = matchedClusterPairListList.get(seedIndexWithMaxPurity);
                    seed1.setMatchedCrossPairList(finalMatchedCrossPairList);
                    matchedSeed2.setMatchedCrossPairList(finalMatchedCrossPairList);
                    seed1.setMatchedClusterPairList(finalMatchedClusterPairList);
                    matchedSeed2.setMatchedClusterPairList(finalMatchedClusterPairList);
                    
                    seed1.setFoundMatchedSeed(true);
                    matchedSeed2.setFoundMatchedSeed(true);
                    seed1.setMatchedSeedId(matchedSeed2.id());
                    matchedSeed2.setMatchedSeedId(seed1.id());
                    int totalTruthHits = seed1.getHits().size();
                    matchedSeed2.setTotalTruthHits(totalTruthHits);
                    matchedSeed2.setEfficiencyHitLevel(matchedSeed2.getNumNormalHits()/(double)totalTruthHits);
                    int totalTruthClusters = seed1.getClusters().size();
                    matchedSeed2.setTotalTruthClusters(totalTruthClusters);
                    matchedSeed2.setEfficiencyClusterLevel(finalMatchedClusterPairList.size()/(double)totalTruthClusters);
                    matchedSeed2.setPurityClusterLevel(finalMatchedClusterPairList.size()/(double)matchedSeed2.getClusters().size());
                    int totalTruthCrosses = seed1.getCrosses().size();
                    matchedSeed2.setTotalTruthCrosses(totalTruthCrosses);
                    matchedSeed2.setEfficiencyCrossLevel(finalMatchedCrossPairList.size()/(double)totalTruthCrosses);
                    matchedSeed2.setPurityCrossLevel(finalMatchedCrossPairList.size()/(double)matchedSeed2.getCrosses().size());                    
                    
                    map_seed1_seed2.put(seed1, matchedSeed2);
                }
            }
        }
    }
    
    public void trackMatch(boolean uTrack){
        if(map_cls1_cls2_BST.isEmpty() && map_cls1_cls2_BMT.isEmpty() && localEvent1 != null && localEvent2 != null) clusterMatch();
        if(map_crs1_crs2_BST.isEmpty() && map_crs1_crs2_BMT.isEmpty() && localEvent1 != null && localEvent2 != null) crossMatch();
        
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
        
        makeTrackMatchMap(tracks1, tracks2);
    }    
    
    public void trackMatch(List<Track> tracks1, List<Track> tracks2){   
        if(map_cls1_cls2_BST.isEmpty() && map_cls1_cls2_BMT.isEmpty() && localEvent1 != null && localEvent2 != null) clusterMatch();
        if(map_crs1_crs2_BST.isEmpty() && map_crs1_crs2_BMT.isEmpty() && localEvent1 != null && localEvent2 != null) crossMatch();
        
        makeTrackMatchMap(tracks1, tracks2);
    }

    public Map<Track, Track> get_map_track1_track2(){
        return map_track1_track2;
    } 
    
    // Make track map between pure and bg samples
    // If no mapped track in bg sample, build a pesudo-track to be mapped with track in pure sample
    public void makeTrackMatchMap(List<Track> tracks1, List<Track> tracks2){
        for(Track track1 : tracks1){
            Map<Cross, Integer> map_crs1_crs2Id_BST = new HashMap();
            for(Cross crs1 : track1.getBSTCrosses()){
                if(crs1.getMatchedCrossId() != -1) map_crs1_crs2Id_BST.put(crs1, crs1.getMatchedCrossId());
            }
            Map<Cross, Integer> map_crs1_crs2Id_BMT = new HashMap();
            for(Cross crs1 : track1.getBMTCrosses()){
                if(crs1.getMatchedCrossId() != -1) map_crs1_crs2Id_BMT.put(crs1, crs1.getMatchedCrossId());
            }            
            if(!map_crs1_crs2Id_BST.isEmpty() || !map_crs1_crs2Id_BMT.isEmpty()){
                List<Track> matchedTrackListWithMostMatchedCrosses2 = new ArrayList();
                List<List<Pair<Integer, Integer>>> matchedCrossPairListList = new ArrayList();
                List<List<Pair<Integer, Integer>>> matchedClusterPairListList = new ArrayList();
                int maxNumMatchedCrosses = 0;              
                for(Track track2 : tracks2){
                    List<Pair<Integer, Integer>> matchedCrossPairList = new ArrayList();
                    List<Pair<Integer, Integer>> matchedClusterPairList = new ArrayList();
                    for(Cross crs1 : map_crs1_crs2Id_BST.keySet()){
                        for(Cross crs2 : track2.getBSTCrosses()){
                            if(crs2.id() == map_crs1_crs2Id_BST.get(crs1)){
                                matchedCrossPairList.add(new Pair<>(crs1.id(), crs2.id()));
                                matchedClusterPairList.add(new Pair<>(crs1.getCluster1().id(), crs2.getCluster1().id()));
                                matchedClusterPairList.add(new Pair<>(crs1.getCluster2().id(), crs2.getCluster2().id()));
                                break;
                            }
                        }
                    }
                    for(Cross crs1 : map_crs1_crs2Id_BMT.keySet()){
                        for(Cross crs2 : track2.getBMTCrosses()){
                            if(crs2.id() == map_crs1_crs2Id_BMT.get(crs1)){
                                matchedCrossPairList.add(new Pair<>(crs1.id(), crs2.id()));
                                matchedClusterPairList.add(new Pair<>(crs1.getCluster1().id(), crs2.getCluster1().id()));
                                break;
                            }
                        }
                    }                                        
                    if(matchedCrossPairList.size() > maxNumMatchedCrosses){
                        maxNumMatchedCrosses = matchedCrossPairList.size();
                        matchedTrackListWithMostMatchedCrosses2.clear();
                        matchedTrackListWithMostMatchedCrosses2.add(track2);
                        matchedCrossPairListList.add(matchedCrossPairList);
                        matchedClusterPairListList.add(matchedClusterPairList);
                    }
                    else if(!matchedCrossPairList.isEmpty() && matchedCrossPairList.size() == maxNumMatchedCrosses){
                        matchedTrackListWithMostMatchedCrosses2.add(track2);
                        matchedCrossPairListList.add(matchedCrossPairList);
                        matchedClusterPairListList.add(matchedClusterPairList);
                    }
                }
                
                if(!matchedTrackListWithMostMatchedCrosses2.isEmpty()){
                    double maxPurity = -1;
                    int trackIndexWithMaxPurity = -1;
                    for(int i = 0; i < matchedTrackListWithMostMatchedCrosses2.size(); i++){
                        Track track2 = matchedTrackListWithMostMatchedCrosses2.get(i);
                        if(track2.getRatioNormalHits() > maxPurity){
                            maxPurity = track2.getRatioNormalHits();
                            trackIndexWithMaxPurity = i;
                        }
                    }

                    if(trackIndexWithMaxPurity != -1){
                        Track matchedTrack2 = matchedTrackListWithMostMatchedCrosses2.get(trackIndexWithMaxPurity);
                        List<Pair<Integer, Integer>> finalMatchedCrossPairList = matchedCrossPairListList.get(trackIndexWithMaxPurity);
                        List<Pair<Integer, Integer>> finalMatchedClusterPairList = matchedClusterPairListList.get(trackIndexWithMaxPurity);
                        track1.setMatchedCrossPairList(finalMatchedCrossPairList);
                        matchedTrack2.setMatchedCrossPairList(finalMatchedCrossPairList);
                        track1.setMatchedClusterPairList(finalMatchedClusterPairList);
                        matchedTrack2.setMatchedClusterPairList(finalMatchedClusterPairList);

                        track1.setFoundMatchedTrack(true);
                        matchedTrack2.setFoundMatchedTrack(true);
                        track1.setMatchedTrackId(matchedTrack2.id());
                        matchedTrack2.setMatchedTrackId(track1.id());
                        int totalTruthHits = track1.getHits().size();
                        matchedTrack2.setTotalTruthHits(totalTruthHits);
                        matchedTrack2.setEfficiencyHitLevel(matchedTrack2.getNumNormalHits()/(double)totalTruthHits);
                        int totalTruthClusters = track1.getClusters().size();
                        matchedTrack2.setTotalTruthClusters(totalTruthClusters);
                        matchedTrack2.setEfficiencyClusterLevel(finalMatchedClusterPairList.size()/(double)totalTruthClusters);
                        matchedTrack2.setPurityClusterLevel(finalMatchedClusterPairList.size()/(double)matchedTrack2.getClusters().size());
                        int totalTruthCrosses = track1.getCrosses().size();
                        matchedTrack2.setTotalTruthCrosses(totalTruthCrosses);
                        matchedTrack2.setEfficiencyCrossLevel(finalMatchedCrossPairList.size()/(double)totalTruthCrosses);
                        matchedTrack2.setPurityCrossLevel(finalMatchedCrossPairList.size()/(double)matchedTrack2.getCrosses().size());                    

                        map_track1_track2.put(track1, matchedTrack2);
                    }
                }                
            }            
        }
    }    
}