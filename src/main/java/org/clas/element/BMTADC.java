package org.clas.element;

import org.clas.utilities.Constants;
        
/**
 *
 * @author Tongtong
 */

public class BMTADC {
    private int sector;
    private int layer;
    private int component;
    private int order;
    private int ADC;
    private double time;
    private int ped;
    private int integral;
    private double timestamp;
    private int bmtType = Constants.BMTNO;
        
    public BMTADC(int sector, int layer, int component, int order, int ADC, double time, int ped, int integral, double timestamp){
        this.sector = sector;
        this.layer = layer;
        this.component = component;
        this.order = order;  
        this.ADC = ADC;
        this.time = time;
        this.ped = ped;
        this.integral = integral;
        this.timestamp = timestamp;
        if(layer == 1 || layer == 4 || layer == 6){
            bmtType = Constants.BMTC;
        }
        else if(layer == 2 || layer == 3 || layer == 5){
            bmtType = Constants.BMTZ;
        }
        else bmtType = Constants.BMTNO;
    }
    
    public int bmtType(){
        return bmtType;
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
    
    public int integral(){
        return integral;
    }
    
    public double timestamp(){
        return timestamp;
    }
    
    public boolean isNormalHit(){
        return order == 0;
    }     
    
    public boolean isSameADC(BMTADC adc){
        return this.sector == adc.sector() && this.layer() == adc.layer() && this.component == adc.component() && this.ADC == adc.ADC() && this.integral == adc.integral();
    }    
}