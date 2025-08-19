package org.clas.element;

import org.clas.utilities.Constants;
        
/**
 *
 * @author Tongtong
 */

public class BSTADC {
    private int sector;
    private int layer;
    private int component;
    private int order;
    private int ADC;
    private double time;
    private int ped;
    private double timestamp;
        
    public BSTADC(int sector, int layer, int component, int order, int ADC, double time, int ped, double timestamp){
        this.sector = sector;
        this.layer = layer;
        this.component = component;
        this.order = order;  
        this.ADC = ADC;
        this.time = time;
        this.ped = ped;
        this.timestamp = timestamp;
    }
    
    public int sector(){
        return sector;
    }
    
    public int layer(){
        return layer;
    }
    
    public int component(){
        return component;
    }

    public int order(){
        return order;
    } 
    
    public int ADC(){
       return ADC;
    }
    
    public double time(){
        return time;
    }
    
    public int ped(){
        return ped;
    }
    
    public double timestamp(){
        return timestamp;
    } 
    
    public boolean isNormalHit(){
        return order == 0;
    }     
    
    public boolean matchADC(BSTADC adc){
        return this.sector == adc.sector() && this.layer() == adc.layer() && this.component == adc.component() && this.ADC == adc.ADC();
    }    
}