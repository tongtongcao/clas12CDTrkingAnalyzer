package org.clas.element;

import java.util.List;
import java.util.ArrayList;

import org.clas.utilities.Constants;

/**
 *
 * @author Tongtong
 */

public class Hit implements Comparable<Hit> {    
    private int detectorType = Constants.DETECTORBST;
    private int trackingPass = Constants.TRACKINGPASS2;
    
    private int id;
    private int sector;
    private int layer;
    private int strip;
    private double energy;
    private double time;
    private double fitResidual;            
    private int trkingStat;    
    private int clusterID;
    private int trkID;
    private int status;
    private int order = -1;
              
    public Hit(int detectorType, int trackingPass, int id, int sector, int layer, int strip, double energy, double time, double fitResidual, int trkingStat, int clusterID, int trkID, int status){
        this.detectorType = detectorType;
        this.trackingPass = trackingPass;
        this.id = id;
        this.sector = sector;
        this.layer = layer;
        this.strip = strip;
        this.energy = energy; 
        this.time = time;
        this.fitResidual = fitResidual;
        this.trkingStat = trkingStat;
        this.clusterID = clusterID;
        this.trkID = trkID;                
        this.status = status;
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
    
    public int strip(){
        return strip;
    }    
    
    public double energy(){
        return energy;
    }
    
    public double time(){
        return time;
    }    
    
    public double fitResidual(){
        return fitResidual;
    }
    
    public int trkingStat(){
        return trkingStat;
    }

    public int clusterID(){
        return clusterID;
    }

    public int trkID(){
        return trkID;
    }    
    
    public int status(){
        return status;
    } 
    
    public void setOrder(int order){
        this.order = order;
    }
    
    public int order(){
        return order;
    }
    
    public boolean isNormalHit(){
        return order == 0;
    } 
    
    public boolean isMatchedHit(Hit thatHit){
        return this.sector() == thatHit.sector() && this.layer() == thatHit.layer() && this.strip() == thatHit.strip() && this.energy() == thatHit.energy();        
    }
    
    @Override
    public int compareTo(Hit o) {
        return this.id()<o.id() ? -1 : 1;
    }         
}