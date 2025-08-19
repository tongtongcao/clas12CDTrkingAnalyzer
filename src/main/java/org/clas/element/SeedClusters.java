package org.clas.element;

import org.clas.utilities.Constants;
/**
 *
 * @author Tongtong
 */

public class SeedClusters implements Comparable<SeedClusters> {
    private int trackingPass = Constants.TRACKINGPASS2;   
    private int id;
    private double prob;    
    
    private int[] clusterIds = new int[12];
            
    public SeedClusters(int trackingPass, int id, double prob){
        this.trackingPass = trackingPass;
        this.id = id;
        this.prob = prob;                             
    }
    
    public int trackingPass(){
        return trackingPass;
    }    
    
    public int id(){
        return id;
    }
    
    public double prob(){
        return prob;
    }
        
    public void clusterIds(int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12){
        this.clusterIds[0] = i1;
        this.clusterIds[1] = i2;
        this.clusterIds[2] = i3;
        this.clusterIds[3] = i4;
        this.clusterIds[4] = i5;
        this.clusterIds[5] = i6;
        this.clusterIds[6] = i7;
        this.clusterIds[7] = i8;
        this.clusterIds[8] = i9;
        this.clusterIds[9] = i10;
        this.clusterIds[10] = i11;
        this.clusterIds[11] = i12;         
    }

    public int[] clusterIds(){
        return clusterIds;
    }    
                      
    @Override
    public int compareTo(SeedClusters o) {
        return this.id()<o.id() ? -1 : 1;
    }         
}