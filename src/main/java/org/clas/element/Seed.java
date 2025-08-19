package org.clas.element;

import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;

import org.jlab.clas.physics.Vector3;

import org.clas.utilities.Constants;

/**
 *
 * @author Tongtong
 */

public class Seed implements Comparable<Seed> {
    private int trackingPass = Constants.TRACKINGPASS2;   
    private int id;
    private int fittingMethod;        
    private int q;
    private double p;
    private double pt;
    private double phi0;    
    private double tandip;
    private double z0;
    private double d0; 
    private double circlefit_chi2_per_ndf;
    private double linefit_chi2_per_ndf;
    private double chi2;
    private int ndf;
    private double xb;    
    private double yb;
    private double fracmctru;
    private double fracmcmatch;  
    
    private Vector3 vertex = new Vector3(0.0,0.0,0.0);
    private Vector3 momentum = new Vector3(0.0,0.0,0.0);
    
    private int[] crossIds = new int[9];
    
    private List<Cross> bstCrosses = new ArrayList();
    private List<Cross> bmtCrosses = new ArrayList();
    private List<Cross> crosses = new ArrayList();
    
    private List<Cross> normalSeedStripsBSTCrosses = new ArrayList();
    private List<Cross> nonnormalSeedStripsBSTCrosses = new ArrayList();
    private int numNormalSeedStripsBSTCrosses = -1;
    private int numNonnormalSeedStripsBSTCrosses = -1;
    private double ratioNormalSeedStripsBSTCrosses = -1;    
    private List<Cross> normalSeedStripsBMTCrosses = new ArrayList();
    private List<Cross> nonnormalSeedStripsBMTCrosses = new ArrayList();
    private int numNormalSeedStripsBMTCrosses = -1;
    private int numNonnormalSeedStripsBMTCrosses = -1;
    private double ratioNormalSeedStripsBMTCrosses = -1;        
    private List<Cross> normalSeedStripsCrosses = new ArrayList();
    private List<Cross> nonnormalSeedStripsCrosses = new ArrayList();
    private int numNormalSeedStripsCrosses = -1;
    private int numNonnormalSeedStripsCrosses = -1;
    private double ratioNormalSeedStripsCrosses = -1;
    
    private List<Cluster> bstClusters = new ArrayList();
    private List<Cluster> bmtClusters = new ArrayList();
    private List<Cluster> clusters = new ArrayList();
    
    private List<Cluster> normalSeedStripBSTClusters = new ArrayList();
    private List<Cluster> nonnormalSeedStripBSTClusters = new ArrayList();
    private int numNormalSeedStripBSTClusters = -1;
    private int numNonnormalSeedStripBSTClusters = -1;
    private double ratioNormalSeedStripBSTClusters = -1;    
    private List<Cluster> normalSeedStripBMTClusters = new ArrayList();
    private List<Cluster> nonnormalSeedStripBMTClusters = new ArrayList();
    private int numNormalSeedStripBMTClusters = -1;
    private int numNonnormalSeedStripBMTClusters = -1;
    private double ratioNormalSeedStripBMTClusters = -1;        
    private List<Cluster> normalSeedStripClusters = new ArrayList();
    private List<Cluster> nonnormalSeedStripClusters = new ArrayList();
    private int numNormalSeedStripClusters = -1;
    private int numNonnormalSeedStripClusters = -1;
    private double ratioNormalSeedStripClusters = -1;
    
    private List<Hit> bstHits = new ArrayList();
    private List<Hit> bmtHits = new ArrayList();
    private List<Hit> hits = new ArrayList();        
    List<Hit> normalBSTHits = new ArrayList();
    List<Hit> bgBSTHits = new ArrayList();
    private int numNormalBSTHits = -1;
    private int numBgBSTHits = -1;
    private double ratioNormalBSTHits = -1;    
    List<Hit> normalBMTHits = new ArrayList();
    List<Hit> bgBMTHits = new ArrayList();
    private int numNormalBMTHits = -1;
    private int numBgBMTHits = -1;
    private double ratioNormalBMTHits = -1;         
    List<Hit> normalHits = new ArrayList();
    List<Hit> bgHits = new ArrayList();
    private int numNormalHits = -1;
    private int numBgHits = -1;
    private double ratioNormalHits = -1; 
    
    //// Match between pure and bg samples    
    private boolean foundMatchedSeed = false; // if matched seed found
    private int matchedSeedId = -1; // ID of matched seed
    private int totalTruthHits = 0; // total truth hits on matched seed in pure sample
    private double efficiencyHitLevel = 0; // # of truth hits / # of total truth hits on matched seed in pure sample
    private List<Pair<Integer, Integer>> matchedCrossPairList = new ArrayList(); // List of matched cross pair
    private List<Pair<Integer, Integer>> matchedClusterPairList = new ArrayList(); // List of matched cluster pair
    private int totalTruthClusters = 0; // total truth clusters on matched seed in pure sample
    private double purityClusterLevel = 0; // # of matched clusters / # of total clusters
    private double efficiencyClusterLevel = 0; // # of matched clusters / # of total truth clusters on matched seed in pure sample
    private int totalTruthCrosses = 0; // total truth crosses on matched seed in pure sample
    private double purityCrossLevel = 0; // # of matched crosses / # of total crosses
    private double efficiencyCrossLevel = 0; // # of matched crosses / # of total truth crosses on matched seed in pure sample    
            
    public Seed(int trackingPass, int id, int fittingMethod, 
            int q, double p, double pt, double phi0, double tandip, double z0, double d0, double circlefit_chi2_per_ndf, double linefit_chi2_per_ndf, double chi2, int ndf,
            double xb, double yb, double fracmctru, double fracmcmatch){
        this.trackingPass = trackingPass;
        this.id = id;
        this.fittingMethod = fittingMethod;                
        this.q = q;        
        this.p = p;
        this.pt = pt;
        this.phi0 = phi0;
        this.tandip = tandip;
        this.z0 = z0;
        this.d0 = d0;
        this.circlefit_chi2_per_ndf = circlefit_chi2_per_ndf;
        this.linefit_chi2_per_ndf = linefit_chi2_per_ndf;
        this.chi2 = chi2;
        this.ndf = ndf;
        this.xb = xb;
        this.yb = yb;
        this.fracmctru = fracmctru;
        this.fracmcmatch = fracmcmatch;
        
        vertex.setXYZ(xb - d0 * Math.sin(phi0), yb + d0 * Math.cos(phi0), z0);
        momentum.setXYZ(pt * Math.cos(phi0), pt * Math.sin(phi0), pt * tandip);
    }
    
    public int trackingPass(){
        return trackingPass;
    }    
    
    public int id(){
        return id;
    }
    
    public int fittingMethod(){
        return fittingMethod;
    }
    
    public int q(){
        return q;
    }
    
    public double p(){
        return p;
    }
    
    public double pt(){
        return pt;
    }
    
    public double phi0(){
        return phi0;
    }
    
    public double tandip(){
        return tandip;
    }
    
    public double z0(){
        return z0;
    }
    
    public double d0(){
        return d0;
    } 
    
    public double circlefit_chi2_per_ndf(){
        return circlefit_chi2_per_ndf;
    }
    
    public double linefit_chi2_per_ndf(){
        return linefit_chi2_per_ndf;
    }
    
    public double chi2(){
        return chi2;
    }
    
    public int ndf(){
        return ndf;
    }
    
    public double xb(){
        return xb;
    }
    
    public double yb(){
        return yb;
    }
    
    public double fracmctru(){
        return fracmctru;
    }
    
    public double fracmcmatch(){
        return fracmcmatch;
    } 
    
    public Vector3 vertex(){
        return vertex;
    }
    
    public Vector3 momentum(){
        return momentum;
    }    
    
    public void crossIds(int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9){
        this.crossIds[0] = i1;
        this.crossIds[1] = i2;
        this.crossIds[2] = i3;
        this.crossIds[3] = i4;
        this.crossIds[4] = i5;
        this.crossIds[5] = i6;
        this.crossIds[6] = i7;
        this.crossIds[7] = i8;
        this.crossIds[8] = i9;        
    }

    public int[] crossIds(){
        return crossIds;
    }

    public void setCrosses(List<Cross> allBSTCrosses, List<Cross> allBMTCrosses){
        for(int i = 0; i < crossIds.length; i++){
            for(Cross crs: allBSTCrosses){            
                if(crossIds[i] == crs.id()) {
                    bstCrosses.add(crs);
                    break;
                }
            }
            
            for(Cross crs: allBMTCrosses){            
                if(crossIds[i] == crs.id()) {
                    bmtCrosses.add(crs);
                    break;
                }
            }
        }
        crosses.addAll(bstCrosses);
        crosses.addAll(bmtCrosses);        
    }
    
    public void setHitsClustersCrosses(List<Cross> allBSTCrosses, List<Cross> allBMTCrosses){
        for(int i = 0; i < crossIds.length; i++){
            for(Cross crs: allBSTCrosses){            
                if(crossIds[i] == crs.id()) {
                    bstCrosses.add(crs);
                    if(crs.getCluster1() != null) {
                        bstClusters.add(crs.getCluster1());
                        bstHits.addAll(crs.getCluster1().getHits());
                    }
                    if(crs.getCluster2() != null) {
                        bstClusters.add(crs.getCluster2());
                        bstHits.addAll(crs.getCluster2().getHits());
                    }
                    break;
                }
            }
            
            for(Cross crs: allBMTCrosses){            
                if(crossIds[i] == crs.id()) {
                    bmtCrosses.add(crs);
                    if(crs.getCluster1() != null) {
                        bmtClusters.add(crs.getCluster1());
                        bmtHits.addAll(crs.getCluster1().getHits());
                    }
                    break;
                }
            }
        }
        crosses.addAll(bstCrosses);
        crosses.addAll(bmtCrosses);
        clusters.addAll(bstClusters);
        clusters.addAll(bmtClusters);
        hits.addAll(bstHits);
        hits.addAll(bmtHits);
        
        if(Constants.BG) {
            separateCrossesBasedonSeedStrips();
            separateClustersBasedonSeedStrip();
            separateNormalBgHits();
        }
    } 
    
    public void setHitsClustersCrosses(List<Cross> allBSTCrosses, List<Cross> allBMTCrosses, List<Cluster> allBSTClusters, List<Cluster> allBMTClusters, List<Hit> allBSTHits, List<Hit> allBMTHits){
        for(int i = 0; i < crossIds.length; i++){
            for(Cross crs: allBSTCrosses){            
                if(crossIds[i] == crs.id()) {
                    bstCrosses.add(crs);
                    crs.setClusters(allBSTClusters);
                    if(crs.getCluster1() != null) {                        
                        bstClusters.add(crs.getCluster1());
                        crs.getCluster1().setHits(allBSTHits);
                        bstHits.addAll(crs.getCluster1().getHits());
                    }
                    if(crs.getCluster2() != null) {
                        bstClusters.add(crs.getCluster2());
                        crs.getCluster2().setHits(allBSTHits);
                        bstHits.addAll(crs.getCluster2().getHits());
                    }
                    break;
                }
            }
            
            for(Cross crs: allBMTCrosses){            
                if(crossIds[i] == crs.id()) {
                    bmtCrosses.add(crs);
                    crs.setClusters(allBMTClusters);
                    if(crs.getCluster1() != null) {
                        bmtClusters.add(crs.getCluster1());
                        crs.getCluster1().setHits(allBMTHits);
                        bmtHits.addAll(crs.getCluster1().getHits());
                    }
                    break;
                }
            }
        }
        crosses.addAll(bstCrosses);
        crosses.addAll(bmtCrosses);
        clusters.addAll(bstClusters);
        clusters.addAll(bmtClusters);
        hits.addAll(bstHits);
        hits.addAll(bmtHits);
        
        if(Constants.BG) {
            separateCrossesBasedonSeedStrips();
            separateClustersBasedonSeedStrip();
            separateNormalBgHits();
        }
    }
    
    public void separateCrossesBasedonSeedStrips(){
        for(Cross crs : bstCrosses){
            if(crs.isNormalSeedStrips()) normalSeedStripsBSTCrosses.add(crs);
            else nonnormalSeedStripsBSTCrosses.add(crs);                        
        }
        numNormalSeedStripsBSTCrosses = normalSeedStripsBSTCrosses.size();
        numNonnormalSeedStripsBSTCrosses = nonnormalSeedStripsBSTCrosses.size();
        if(!bstCrosses.isEmpty()) ratioNormalSeedStripsBSTCrosses = (double) numNormalSeedStripsBSTCrosses/bstCrosses.size();
        
        for(Cross crs : bmtCrosses){
            if(crs.isNormalSeedStrips()) normalSeedStripsBMTCrosses.add(crs);
            else nonnormalSeedStripsBMTCrosses.add(crs);                        
        }
        numNormalSeedStripsBMTCrosses = normalSeedStripsBMTCrosses.size();
        numNonnormalSeedStripsBMTCrosses = nonnormalSeedStripsBMTCrosses.size();
        if(!bmtCrosses.isEmpty()) ratioNormalSeedStripsBMTCrosses = (double) numNormalSeedStripsBMTCrosses/bmtCrosses.size();        
        
        for(Cross crs : crosses){
            if(crs.isNormalSeedStrips()) normalSeedStripsCrosses.add(crs);
            else nonnormalSeedStripsCrosses.add(crs);                        
        }
        numNormalSeedStripsCrosses = normalSeedStripsCrosses.size();
        numNonnormalSeedStripsCrosses = nonnormalSeedStripsCrosses.size();
        if(!crosses.isEmpty()) ratioNormalSeedStripsCrosses = (double) numNormalSeedStripsCrosses/crosses.size();
    }
    
    public void separateClustersBasedonSeedStrip(){
        for(Cluster cls : bstClusters){
            if(cls.isNormalSeedStrip()) normalSeedStripBSTClusters.add(cls);
            else nonnormalSeedStripBSTClusters.add(cls);                        
        }
        numNormalSeedStripBSTClusters = normalSeedStripBSTClusters.size();
        numNonnormalSeedStripBSTClusters = nonnormalSeedStripBSTClusters.size();
        if(!bstClusters.isEmpty()) ratioNormalSeedStripBSTClusters = (double) numNormalSeedStripBSTClusters/bstClusters.size();
        
        for(Cluster cls : bmtClusters){
            if(cls.isNormalSeedStrip()) normalSeedStripBMTClusters.add(cls);
            else nonnormalSeedStripBMTClusters.add(cls);                        
        }
        numNormalSeedStripBMTClusters = normalSeedStripBMTClusters.size();
        numNonnormalSeedStripBMTClusters = nonnormalSeedStripBMTClusters.size();
        if(!bmtClusters.isEmpty()) ratioNormalSeedStripBMTClusters = (double) numNormalSeedStripBMTClusters/bmtClusters.size();        
        
        for(Cluster cls : clusters){
            if(cls.isNormalSeedStrip()) normalSeedStripClusters.add(cls);
            else nonnormalSeedStripClusters.add(cls);                        
        }
        numNormalSeedStripClusters = normalSeedStripClusters.size();
        numNonnormalSeedStripClusters = nonnormalSeedStripClusters.size();
        if(!clusters.isEmpty()) ratioNormalSeedStripClusters = (double) numNormalSeedStripClusters/clusters.size();
    }    
    
    public void separateNormalBgHits(){
        for(Hit hit : bstHits){
            if(hit.isNormalHit()) normalBSTHits.add(hit);                
            else bgBSTHits.add(hit);
        }
        numNormalBSTHits = normalBSTHits.size();
        numBgBSTHits = bgBSTHits.size();
        if(!bstHits.isEmpty()) ratioNormalBSTHits = (double) numNormalBSTHits/bstHits.size();
        
        for(Hit hit : bmtHits){
            if(hit.isNormalHit()) normalBMTHits.add(hit);                
            else bgBMTHits.add(hit);
        }
        numNormalBMTHits = normalBMTHits.size();
        numBgBMTHits = bgBMTHits.size();
        if(!bmtHits.isEmpty()) ratioNormalBMTHits = (double) numNormalBMTHits/bmtHits.size();        
                
        for(Hit hit : hits){
            if(hit.isNormalHit()) normalHits.add(hit);                
            else bgHits.add(hit);
        }
        numNormalHits = normalHits.size();
        numBgHits = bgHits.size();
        if(!hits.isEmpty()) ratioNormalHits = (double) numNormalHits/hits.size();
    }
    
    
    public List<Cross> getBSTCrosses(){
        return bstCrosses;
    }

    public List<Cross> getBMTCrosses(){
        return bmtCrosses;
    }    
    
    public List<Cross> getCrosses(){
        return crosses;
    }         
    
    public List<Cross> getNormalSeedStripsBSTCrosses(){            
        return normalSeedStripsBSTCrosses;
    }
    
    public List<Cross> getNonnormalSeedStripsBSTCrosses(){            
        return nonnormalSeedStripsBSTCrosses;
    }
    
    public int getNumNormalSeedStripsBSTCrosses(){
        return numNormalSeedStripsBSTCrosses;
    }
    
    public int getNumNonnormalSeedStripsBSTCrosses(){
        return numNonnormalSeedStripsBSTCrosses;
    }  
    
    public double getRatioNormalSeedStripsBSTCrosses(){
        return ratioNormalSeedStripsBSTCrosses;
    }
    
    public List<Cross> getNormalSeedStripsBMTCrosses(){            
        return normalSeedStripsBMTCrosses;
    }
    
    public List<Cross> getNonnormalSeedStripsBMTCrosses(){            
        return nonnormalSeedStripsBMTCrosses;
    }
    
    public int getNumNormalSeedStripsBMTCrosses(){
        return numNormalSeedStripsBMTCrosses;
    }
    
    public int getNumNonnormalSeedStripsBMTCrosses(){
        return numNonnormalSeedStripsBMTCrosses;
    }  
    
    public double getRatioNormalSeedStripsBMTCrosses(){
        return ratioNormalSeedStripsBMTCrosses;
    }

    public List<Cross> getNormalSeedStripsCrosses(){            
        return normalSeedStripsCrosses;
    }
    
    public List<Cross> getNonnormalSeedStripsCrosses(){            
        return nonnormalSeedStripsCrosses;
    }
    
    public int getNumNormalSeedStripsCrosses(){
        return numNormalSeedStripsCrosses;
    }
    
    public int getNumNonnormalSeedStripsCrosses(){
        return numNonnormalSeedStripsBMTCrosses;
    }  
    
    public double getRatioNormalSeedStripsCrosses(){
        return ratioNormalSeedStripsCrosses;
    }          
            
    public List<Cluster> getBSTClusters(){
        return bstClusters;
    }

    public List<Cluster> getBMTClusters(){
        return bmtClusters;
    }    
    
    public List<Cluster> getClusters(){
        return clusters;
    }
    
    public List<Cluster> getNormalSeedStripBSTClusters(){            
        return normalSeedStripBSTClusters;
    }
    
    public List<Cluster> getNonnormalSeedStripBSTClusters(){            
        return nonnormalSeedStripBSTClusters;
    }
    
    public int getNumNormalSeedStripBSTClusters(){
        return numNormalSeedStripBSTClusters;
    }
    
    public int getNumNonnormalSeedStripBSTClusters(){
        return numNonnormalSeedStripBSTClusters;
    }  
    
    public double getRatioNormalSeedStripBSTClusters(){
        return ratioNormalSeedStripBSTClusters;
    }
    
    public List<Cluster> getNormalSeedStripBMTClusters(){            
        return normalSeedStripBMTClusters;
    }
    
    public List<Cluster> getNonnormalSeedStripBMTClusters(){            
        return nonnormalSeedStripBMTClusters;
    }
    
    public int getNumNormalSeedStripBMTClusters(){
        return numNormalSeedStripBMTClusters;
    }
    
    public int getNumNonnormalSeedStripBMTClusters(){
        return numNonnormalSeedStripBMTClusters;
    }  
    
    public double getRatioNormalSeedStripBMTClusters(){
        return ratioNormalSeedStripBMTClusters;
    }

    public List<Cluster> getNormalSeedStripClusters(){            
        return normalSeedStripClusters;
    }
    
    public List<Cluster> getNonnormalSeedStripClusters(){            
        return nonnormalSeedStripClusters;
    }
    
    public int getNumNormalSeedStripClusters(){
        return numNormalSeedStripClusters;
    }
    
    public int getNumNonnormalSeedStripClusters(){
        return numNonnormalSeedStripBMTClusters;
    }  
    
    public double getRatioNormalSeedStripClusters(){
        return ratioNormalSeedStripClusters;
    }  
    
    public List<Hit> getBSTHits(){
        return bstHits;
    }

    public List<Hit> getBMTHits(){
        return bmtHits;
    }    
    
    public List<Hit> getHits(){
        return hits;
    } 

    public List<Hit> getNormalBSTHits(){
        return normalBSTHits;
    }
    
    public List<Hit> getBgBSTHits(){
        return bgBSTHits;
    }
    
    public int getNumNormalBSTHits(){
        return numNormalBSTHits;
    } 
    
    public int getNumBgBSTHits(){
        return numBgBSTHits;
    }
    
    public double getRatioNormalBSTHits(){
        return ratioNormalBSTHits;
    } 
    
    public List<Hit> getNormalBMTHits(){
        return normalBMTHits;
    }
    
    public List<Hit> getBgBMTHits(){
        return bgBMTHits;
    }
    
    public int getNumNormalBMTHits(){
        return numNormalBMTHits;
    } 
    
    public int getNumBgBMTHits(){
        return numBgBMTHits;
    }
    
    public double getRatioNormalBMTHits(){
        return ratioNormalBMTHits;
    }     

    public List<Hit> getNormalHits(){
        return normalHits;
    }
    
    public List<Hit> getBgHits(){
        return bgHits;
    }
    
    public int getNumNormalHits(){
        return numNormalHits;
    } 
    
    public int getNumBgHits(){
        return numBgHits;
    }
    
    public double getRatioNormalHits(){
        return ratioNormalHits;
    }
    
    public void setFoundMatchedSeed(boolean foundMatchedSeed){
        this.foundMatchedSeed = foundMatchedSeed;
    }
    
    public boolean getFoundMatchedSeed(){
        return foundMatchedSeed;
    }

    public void setTotalTruthHits(int totalTruthHits){
        this.totalTruthHits = totalTruthHits;
    }
               
    public int getTotalTruthHits(){
        return totalTruthHits;
    }    
    
    public void setMatchedSeedId(int matchedSeedId){
        this.matchedSeedId = matchedSeedId;
    }
               
    public int getMatchedSeedId(){
        return matchedSeedId;
    }    
    
    public void setEfficiencyHitLevel(double efficiencyHitLevel){
        this.efficiencyHitLevel = efficiencyHitLevel;
    }
               
    public double getEfficiencyHitLevel(){
        return efficiencyHitLevel;
    }
    
    public void setMatchedCrossPairList(List<Pair<Integer, Integer>> matchedCrossPairList){
        this.matchedCrossPairList = matchedCrossPairList;
    }
               
    public List<Pair<Integer, Integer>> getMatchedCrossPairList(){
        return matchedCrossPairList;
    } 
    
    public void setMatchedClusterPairList(List<Pair<Integer, Integer>> matchedClusterPairList){
        this.matchedClusterPairList = matchedClusterPairList;
    }
               
    public List<Pair<Integer, Integer>> getMatchedClusterPairList(){
        return matchedClusterPairList;
    }
    
    
    public void setTotalTruthClusters(int totalTruthClusters){
        this.totalTruthClusters = totalTruthClusters;
    }
               
    public int getTotalTruthClusters(){
        return totalTruthClusters;
    } 
    
    public void setPurityClusterLevel(double purityClusterLevel){
        this.purityClusterLevel = purityClusterLevel;
    }
               
    public double getPurityClusterLevel(){
        return purityClusterLevel;
    }    
    
    public void setEfficiencyClusterLevel(double efficiencyClusterLevel){
        this.efficiencyClusterLevel = efficiencyClusterLevel;
    }
               
    public double getEfficiencyClusterLevel(){
        return efficiencyClusterLevel;
    } 
    
    public void setTotalTruthCrosses(int totalTruthCrosses){
        this.totalTruthCrosses = totalTruthCrosses;
    }
               
    public int getTotalTruthCrosses(){
        return totalTruthCrosses;
    } 
    
    public void setPurityCrossLevel(double purityCrossLevel){
        this.purityCrossLevel = purityCrossLevel;
    }
               
    public double getPurityCrossLevel(){
        return purityCrossLevel;
    }    
    
    public void setEfficiencyCrossLevel(double efficiencyCrossLevel){
        this.efficiencyCrossLevel = efficiencyCrossLevel;
    }
               
    public double getEfficiencyCrossLevel(){
        return efficiencyCrossLevel;
    }         
                      
    @Override
    public int compareTo(Seed o) {
        return this.id()<o.id() ? -1 : 1;
    }         
}