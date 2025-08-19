package org.clas.element;

import java.util.ArrayList;
import java.util.List;

import org.jlab.geom.prim.Point3D;
import org.jlab.geom.prim.Vector3D;

import org.clas.utilities.Constants;
/**
 *
 * @author Tongtong
 */

public class Cross implements Comparable<Cross> {
    private int detectorType = Constants.DETECTORBST;
    private int trackingPass = Constants.TRACKINGPASS2;   
    private int id;
    private int sector;
    private int region;
    private int layer = -1;
    private Point3D point = null; // Updated with tracking
    private Point3D pointErr = null;
    private Point3D pointOrigin = null;
    private Point3D pointOriginErr = null;
    private Vector3D dir = null; // Direction along track      
    private int cluster1Id;
    private int cluster2Id = 0;
    private int trkID;   
    
    private Cluster cluster1 = null;
    private Cluster cluster2 = null; 
    
    List<Hit> hits = new ArrayList();
    List<Hit> normalHits = new ArrayList();
    List<Hit> bgHits = new ArrayList();
    private int numNormalHits = -1;
    private int numBgHits = -1;
    private double ratioNormalHits = -1; 
        
    // BMT: category of cross equals to category of cluster
    //     category 1: all signal strips
    //     category 2: mixed with signal seed strip
    //     category 3: mixed with bg seed strip
    //      category 4: all bg hits
    // BST: 
    //     category 1: both clusters with normal seed strip 
    //     category 2: inner cluster with normal seed strip, while outer cluster with bg seed strip
    //     category 3: inner cluster with bg seed strip, while outer cluster with normal seed strip
    //     category 4: both clusters with bg seed strip
    private int category = 1;
    
    // BMT: seed strip for cluster1 is normal or not
    // BST: seed strips for both clusters are normal or not
    private boolean isNormalSeedStrips = true;
    
    //// Match between pure and bg samples    
    private boolean foundMatchedCross = false; // if matched cross found
    private int matchedCrossId = -1; // ID of matched cross
    private int totalTruthHits = 0; // total truth hits on matched cross in pure sample
    private double efficiency = 0; // # of truth hits / # of total truth hits on matched cross in pure sample
    
    public Cross(int detectorType, int trackingPass, int id, int sector, int region, double x, double y, double z, double xErr, double yErr, double zErr, 
            double x0, double y0, double z0, double x0Err, double y0Err, double z0Err, double ux, double uy, double uz,
            int cluster1Id, int cluster2Id, int trkID){
        this.detectorType = detectorType;        
        this.trackingPass = trackingPass;
        this.id = id;
        this.sector = sector;
        this.region = region;  
        this.point = new Point3D(x, y, z);
        this.pointErr = new Point3D(xErr, yErr, zErr);
        this.pointOrigin = new Point3D(x0, y0, z0);
        this.pointOriginErr = new Point3D(x0Err, y0Err, z0Err);
        this.dir = new Vector3D(ux, uy, uz);    
        this.cluster1Id = cluster1Id;
        this.cluster2Id = cluster2Id;
    }    
            
    public Cross(int detectorType, int trackingPass, int id, int sector, int region, int layer, double x, double y, double z, double xErr, double yErr, double zErr, 
            double x0, double y0, double z0, double x0Err, double y0Err, double z0Err, double ux, double uy, double uz,
            int cluster1Id, int cluster2Id, int trkID){
        this.detectorType = detectorType;        
        this.trackingPass = trackingPass;
        this.id = id;
        this.sector = sector;
        this.region = region;  
        this.layer = layer;
        this.point = new Point3D(x, y, z);
        this.pointErr = new Point3D(xErr, yErr, zErr);
        this.pointOrigin = new Point3D(x0, y0, z0);
        this.pointOriginErr = new Point3D(x0Err, y0Err, z0Err);
        this.dir = new Vector3D(ux, uy, uz);    
        this.cluster1Id = cluster1Id;
        this.cluster2Id = cluster2Id;
    }
    
    public int detectorType(){
        return detectorType;
    }     
    
    public int trackingPass(){
        return trackingPass;
    }
    
    public int id(){
        return id;
    }
    
    public int sector(){
        return sector;
    }
    
    public int region(){
        return region;
    }
    
    public int layer(){
        return layer;
    }
    
    public Point3D point(){
        return point;
    }
    
    public Point3D pointErr(){
        return pointErr;
    }

    public Point3D pointOrigin(){
        return pointOrigin;
    }
    
    public Point3D pointOriginErr(){
        return pointOriginErr;
    } 
    
    public Vector3D dir(){
        return dir;
    }    
    
    public int cluster1Id(){
        return cluster1Id;
    }
    
    public int cluster2Id(){
        return cluster2Id;
    }

    public void setHits(List<Hit> allHits){
        if(detectorType == Constants.DETECTORBMT){
            if(cluster1 != null){
                cluster1.setHits(allHits);
                hits.addAll(cluster1.getHits());
            }
        }
        if(detectorType == Constants.DETECTORBST) {
            if(cluster1 != null) {
                cluster1.setHits(allHits);
                hits.addAll(cluster1.getHits());
            }
            if(cluster2 != null){
                cluster2.setHits(allHits);
                hits.addAll(cluster2.getHits());
            }
        }
        
        if(Constants.BG) separateNormalBgHits();
    }
        
    public void setHitsClusters(List<Cluster> allClusters){
        if(detectorType == Constants.DETECTORBMT) {
            setCluster1(allClusters);
            if(cluster1 != null) {
                category = cluster1.getCategory();
                isNormalSeedStrips = cluster1.isNormalSeedStrip();
                hits.addAll(cluster1.getHits());
            }
        }
        if(detectorType == Constants.DETECTORBST) {
            setCluster1(allClusters);
            setCluster2(allClusters);
            if(cluster1 != null && cluster2 != null){
                if(cluster1.isNormalSeedStrip() && cluster2.isNormalSeedStrip()) category = 1;
                else if(cluster1.isNormalSeedStrip() && !cluster2.isNormalSeedStrip()) category = 2;
                else if(!cluster1.isNormalSeedStrip() && cluster2.isNormalSeedStrip()) category = 3;
                else category = 4;
            }
            if(category == 1) isNormalSeedStrips = true;
            else isNormalSeedStrips = false;
            hits.addAll(cluster1.getHits());
            hits.addAll(cluster2.getHits());
        }
        
        if(Constants.BG) separateNormalBgHits();
    } 
    
    public void setHitsClusters(List<Cluster> allClusters, List<Hit> allHits){
        if(detectorType == Constants.DETECTORBMT) {
            setCluster1(allClusters);
            if(cluster1 != null) {
                category = cluster1.getCategory();
                isNormalSeedStrips = cluster1.isNormalSeedStrip();
                cluster1.setHits(allHits);
                hits.addAll(cluster1.getHits());
            }
        }
        if(detectorType == Constants.DETECTORBST) {
            setCluster1(allClusters);
            setCluster2(allClusters);
            if(cluster1 != null && cluster2 != null){
                if(cluster1.isNormalSeedStrip() && cluster2.isNormalSeedStrip()) category = 1;
                else if(cluster1.isNormalSeedStrip() && !cluster2.isNormalSeedStrip()) category = 2;
                else if(!cluster1.isNormalSeedStrip() && cluster2.isNormalSeedStrip()) category = 3;
                else category = 4;
            }
            if(category == 1) isNormalSeedStrips = true;
            else isNormalSeedStrips = false;
            cluster1.setHits(allHits);
            cluster2.setHits(allHits);
            hits.addAll(cluster1.getHits());
            hits.addAll(cluster2.getHits());
        }
        
        if(Constants.BG) separateNormalBgHits();
    }
        
    public void separateNormalBgHits(){
        for(Hit hit : hits){
            if(hit.isNormalHit()) normalHits.add(hit);                
            else bgHits.add(hit);
        }

        numNormalHits = normalHits.size();
        numBgHits = bgHits.size();
        if(!hits.isEmpty()) ratioNormalHits = (double) numNormalHits/hits.size();
    }
    
    public void setClusters(List<Cluster> allClusters){
        if(detectorType == Constants.DETECTORBMT) {
            setCluster1(allClusters);
            if(cluster1 != null) {
                category = cluster1.getCategory();
                isNormalSeedStrips = cluster1.isNormalSeedStrip();
            }
        }
        if(detectorType == Constants.DETECTORBST) {
            setCluster1(allClusters);
            setCluster2(allClusters);
            if(cluster1 != null && cluster2 != null){
                if(cluster1.isNormalSeedStrip() && cluster2.isNormalSeedStrip()) category = 1;
                else if(cluster1.isNormalSeedStrip() && !cluster2.isNormalSeedStrip()) category = 2;
                else if(!cluster1.isNormalSeedStrip() && cluster2.isNormalSeedStrip()) category = 3;
                else category = 4;
            }
            if(category == 1) isNormalSeedStrips = true;
            else isNormalSeedStrips = false;
        }
    }
    
    public boolean isNormalSeedStrips(){
        return isNormalSeedStrips;
    }
    
    public void setCluster1(List<Cluster> allClusters){
        for(Cluster cls : allClusters){
            if(cls.id() == cluster1Id){
                cluster1 = cls;
                break;
            }
        }
    }
    
    public void setCluster2(List<Cluster> allClusters){
        for(Cluster cls : allClusters){
            if(cls.id() == cluster2Id){
                cluster2 = cls;
                break;
            }
        }
    }    
    
    public Cluster getCluster1(){
        return cluster1;
    }
            
    public Cluster getCluster2(){
        return cluster2;
    }

    public List<Hit> getHits(){
        return hits;
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
    
    public int getCategory(){
        return category;
    }

    public void setFoundMatchedCross(boolean foundMatchedCross){
        this.foundMatchedCross = foundMatchedCross;
    }
    
    public boolean getFoundMatchedCross(){
        return foundMatchedCross;
    }

    public void setTotalTruthHits(int totalTruthHits){
        this.totalTruthHits = totalTruthHits;
    }
               
    public int getTotalTruthHits(){
        return totalTruthHits;
    }    
    
    public void setMatchedCrossId(int matchedCrossId){
        this.matchedCrossId = matchedCrossId;
    }
               
    public int getMatchedCrossId(){
        return matchedCrossId;
    }    
    
    public void setEfficiency(double efficiency){
        this.efficiency = efficiency;
    }
               
    public double getEfficiency(){
        return efficiency;
    } 
    
    // Map to sector of BMT 
    private List<Integer> getSectorListMapToBMTSector() {
        List<Integer> secList = new ArrayList<>();
        if(region == 1) {
            if(sector>0 && sector<5)
                secList.add(1);
            if(sector>3 && sector<9)
                secList.add(2);
            if(sector>7 && sector<11)
                secList.add(3);
            if(sector==1)
                secList.add(3);
        }
        if(region == 2) {
            if(sector>0 && sector<7)
                secList.add(1);
            if(sector>5 && sector<11)
                secList.add(2);
            if(sector>9 && sector<15)
                secList.add(3);
            if(sector==1)
                secList.add(3);
        }        
        if(region == 3) {
            if(sector>0 && sector<8)
                secList.add(1);
            if(sector>6 && sector<14)
                secList.add(2);
            if(sector>13 && sector<19)
                secList.add(3);
            if(sector==1)
                secList.add(3);
        }   
               
        return secList;
    }    
                      
    @Override
    public int compareTo(Cross o) {
        return this.id()<o.id() ? -1 : 1;
    }         
}