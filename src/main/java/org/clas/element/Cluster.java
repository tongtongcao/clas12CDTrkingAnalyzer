package org.clas.element;

import java.util.List;
import java.util.ArrayList;

import org.jlab.geom.prim.Point3D;
import org.jlab.geom.prim.Vector3D;

import org.clas.utilities.Constants;
/**
 *
 * @author Tongtong
 */

public class Cluster implements Comparable<Cluster> {
    private int detectorType = Constants.DETECTORBST;
    private int trackingPass = Constants.TRACKINGPASS2; 
    private int id;
    private int sector;
    private int layer;
    private int size;        
    private double ETot;
    private double time;
    private double seedE;
    private int seedStrip;
    private double centroid;
    private double centroidError;
    private double centroidResidual;
    private double seedResidual;    
    private int trkID;
    private Point3D originPoint = null;
    private Point3D endPoint = null;
    private Point3D centerPoint = null;
    private Vector3D paraDir = null;
    private Vector3D perpDir = null;
    private Vector3D normDir = null;
    private int[] hitIds = new int[5];
   
    private Hit seedHit = null;
    
    List<Hit> hits = new ArrayList();
    List<Hit> normalHits = new ArrayList();
    List<Hit> bgHits = new ArrayList();
    private int numNormalHits = -1;
    private int numBgHits = -1;
    private double ratioNormalHits = -1; 
    
    //category 1: all signal strips; category 2: mixed with signal seed strip; category 3: mixed with bg seed strip; category 4: all bg hits
    private int category = 1;
    
    private boolean isNormalSeedStrip = true;
    
    //// Match between pure and bg samples    
    private boolean foundMatchedCluster = false; // if matched cluster found
    private int matchedClusterId = -1; // ID of matched cluster
    private int totalTruthHits = 0; // total truth hits on matched cluster in pure sample
    private double efficiency = 0; // # of truth hits / # of total truth hits on matched cluster in pure sample
    
    private double dafWeight = -1;
    
    public Cluster(Cluster cls){
        this.copy(cls);
    }
            
    public Cluster(int detectorType, int trackingPass, int id, int sector, int layer, int size, 
            double ETot, double time, double seedE, int seedStrip, double centroid, double centroidError, double centroidResidual, double seedResidual, 
            int trkID, double x1, double y1, double z1, double x2, double y2, double z2, double cx, double cy, double cz,
            double lx, double ly, double lz, double sx, double sy, double sz, double nx, double ny, double nz){
        this.detectorType = detectorType;        
        this.trackingPass = trackingPass;
        this.id = id;
        this.sector = sector;
        this.layer = layer;
        this.size = size;        
        this.ETot = ETot;
        this.time = time;
        this.seedE = seedE;
        this.seedStrip = seedStrip;
        this.centroid = centroid;
        this.centroidError = centroidError;         
        this.centroidResidual = centroidResidual;         
        this.seedResidual = seedResidual;         
        this.trkID = trkID; 
        originPoint = new Point3D(x1, y1, z1);
        endPoint = new Point3D(x2, y2, z2);
        centerPoint = new Point3D(cx, cy, cz);
        paraDir = new Vector3D(lx, ly, lz);
        perpDir = new Vector3D(sx, sy, sz);
        normDir = new Vector3D(nx, ny, nz);
    }

    public void hitIds(int i1, int i2, int i3, int i4, int i5){
        this.hitIds[0] = i1;
        this.hitIds[1] = i2;
        this.hitIds[2] = i3;
        this.hitIds[3] = i4;
        this.hitIds[4] = i5;
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
    
    public int layer(){
        return layer;
    }
    
    public int size(){
        return size;
    }
    
    public double ETot(){
        return ETot;
    }
    
    public double time(){
        return time;
    }

    public double seedE(){
        return seedE;
    }

    public int seedStrip(){
        return seedStrip;
    }

    public double centroid(){
        return centroid;
    } 
    
    public double centroidError(){
        return centroidError;
    }

    public double centroidResidual(){
        return centroidResidual;
    }

    public double seedResidual(){
        return seedResidual;
    }  
    
    public int trkID(){
        return trkID;
    }

    public Point3D originPoint(){
        return originPoint;
    }
    
    public Point3D endPoint(){
        return endPoint;
    }  
    
    public Point3D centerPoint(){
        return centerPoint;
    }
    
    public Vector3D paraDir(){
        return paraDir;
    }
    
    public Vector3D perpDir(){
        return perpDir;
    }

    public Vector3D normDir(){
        return normDir;
    }    
        
    public int[] hitIds(){
        return hitIds;
    }      
    
    public void setHits(List<Hit> allHits){
        for(Hit hit: allHits){
            if(hit.clusterID() == this.id) hits.add(hit);
        }
        
        for(Hit hit : hits){
            if(hit.strip() == seedStrip) seedHit = hit;
        }
        
        if(Constants.BG) separateNormalBgHits();
    }
    
    public List<Hit> getHits(){
        return hits;
    } 
    
    public Hit getSeedHit(){
        return seedHit;
    }
    
    public void separateNormalBgHits(){                
        for(Hit hit : hits){
            if(hit.isNormalHit()) normalHits.add(hit);                
            else bgHits.add(hit);
        }

        numNormalHits = normalHits.size();
        numBgHits = bgHits.size();
        if(!hits.isEmpty()) ratioNormalHits = (double) numNormalHits/hits.size();
        isNormalSeedStrip = isNormalSeedStrip(normalHits);

        if(ratioNormalHits == 1) category = 1;
        else if(ratioNormalHits == 0) category = 4;
        else{
            if(isNormalSeedStrip) category = 2;
            else category = 3;               
        }                          
    }

    public boolean isNormalSeedStrip (List<Hit> normalHitList){
        if(normalHitList != null){
            for(Hit hit : normalHitList){
                if(seedStrip == hit.strip()) return true;
            }
        }       
        return false;
    }
    
    public boolean isNormalSeedStrip(){
        return isNormalSeedStrip;
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
    
    public int clusterMatchedHits(Cluster otherCls){
        if(this.hits == null || otherCls.hits == null) return -999;
        int matchedHits = 0;
        for(Hit hitThisCluster : this.hits){
            for(Hit hitOtherCluster : otherCls.hits){
                if(hitThisCluster.sector() == hitOtherCluster.sector() && hitThisCluster.layer() == hitOtherCluster.layer() && hitThisCluster.strip() == hitOtherCluster.strip()
                        && hitThisCluster.order() == hitOtherCluster.order() && hitThisCluster.energy() == hitOtherCluster.energy()){
                    matchedHits++;
                    break;
                }
            }
        }
        return matchedHits;
    }
    
    public void setFoundMatchedCluster(boolean foundMatchedCluster){
        this.foundMatchedCluster = foundMatchedCluster;
    }
    
    public boolean getFoundMatchedCluster(){
        return foundMatchedCluster;
    }

    public void setTotalTruthHits(int totalTruthHits){
        this.totalTruthHits = totalTruthHits;
    }
               
    public int getTotalTruthHits(){
        return totalTruthHits;
    }    
    
    public void setMatchedClusterId(int matchedClusterId){
        this.matchedClusterId = matchedClusterId;
    }
               
    public int getMatchedClusterId(){
        return matchedClusterId;
    }    
    
    public void setEfficiency(double efficiency){
        this.efficiency = efficiency;
    }
               
    public double getEfficiency(){
        return efficiency;
    } 
    
    public void dafWeight(double dafWeight){
        this.dafWeight = dafWeight;
    }
    
    public double getDAFWeight(){
        return dafWeight;
    }
    
    public final void copy(Cluster cls) {
        this.detectorType = cls.detectorType;        
        this.trackingPass = cls.trackingPass;
        this.id = cls.id;
        this.sector = cls.sector;
        this.layer = cls.layer;
        this.size = cls.size;        
        this.ETot = cls.ETot;
        this.time = cls.time;
        this.seedE = cls.seedE;
        this.seedStrip = cls.seedStrip;
        this.centroid = cls.centroid;
        this.centroidError = cls.centroidError;         
        this.centroidResidual = cls.centroidResidual;         
        this.seedResidual = cls.seedResidual;         
        this.trkID = cls.trkID;          
        this.hitIds = cls.hitIds();
        this.hits = cls.getHits();
        this.seedHit = cls.getSeedHit();
        this.normalHits.addAll(cls.getNormalHits());
        this.bgHits.addAll(cls.getBgHits());
        this.numNormalHits = cls.getNumNormalHits();
        this.numBgHits = cls.getNumBgHits();
        this.ratioNormalHits = cls.getRatioNormalHits();
        this.category = cls.getCategory();
        this.isNormalSeedStrip = cls.isNormalSeedStrip();
        this.foundMatchedCluster = cls.getFoundMatchedCluster();
        this.matchedClusterId = cls.getMatchedClusterId();
        this.totalTruthHits = cls.getTotalTruthHits();
        this.efficiency = cls.getEfficiency();        
    }        
    
    @Override
    public int compareTo(Cluster o) {
        return this.id()<o.id() ? -1 : 1;
    }         
}