package org.clas.analysis;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jlab.groot.base.GStyle;
import org.jlab.groot.data.TDirectory;
import org.jlab.groot.graphics.EmbeddedCanvasTabbed;
import org.jlab.groot.data.IDataSet;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.data.DataLine;
import org.jlab.groot.group.DataGroup;

import org.clas.utilities.Constants;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;
import org.clas.reader.Reader;
import org.clas.element.Hit;
import org.clas.element.Cluster;

/**
 *
 * @author Tongtong Cao
 */
public abstract class BaseAnalysis { 
    private static final Logger LOGGER = Logger.getLogger(Reader.class.getName());    
    
    protected Reader reader = null;   
    protected Reader reader1 = null;   
    protected Reader reader2 = null;   
    protected LinkedHashMap<String, HistoGroup> histoGroupMap;    
    
    protected LinkedHashMap<String, DataGroup> demoGroupMap = new LinkedHashMap(); 
    protected int countDemoCases = 0;
    
    public BaseAnalysis() {
        histoGroupMap = new LinkedHashMap<>();
    }
    
    public void initReader(Banks banks){
        this.reader = new Reader(banks);
    }
    
    public void initReader(Banks banks1, Banks banks2){
        this.reader1 = new Reader(banks1);
        this.reader2 = new Reader(banks2);
    }
    
    public void initReader(Reader reader){
        this.reader = reader;
    }
    
    public void initReader(Reader reader1, Reader reader2){
        this.reader1 = reader1;
        this.reader2 = reader2;
    }
    
    public BaseAnalysis(Banks banks) {
        this.reader = new Reader(banks);
        histoGroupMap = new LinkedHashMap<>();
    }
    
    public BaseAnalysis(Banks banks1, Banks banks2) {
        this.reader1 = new Reader(banks1);
        this.reader2 = new Reader(banks2);
        histoGroupMap = new LinkedHashMap<>();
    }
    
    public BaseAnalysis(Reader reader) {
        this.reader = reader;
        histoGroupMap = new LinkedHashMap<>();
    }
    
    public BaseAnalysis(Reader reader1, Reader reader2) {
        this.reader1 = reader1;
        this.reader2 = reader2;
        histoGroupMap = new LinkedHashMap<>();
    }
    
    public LinkedHashMap<String, HistoGroup> getHistoGroupMap(){
        return histoGroupMap;
    }
    
    public LinkedHashMap<String, DataGroup> getDemoGroupMap(){
        return demoGroupMap;
    } 
    
    public abstract void createHistoGroupMap(); 
        
    public void saveHistos(String fileName) {
        TDirectory dir = new TDirectory();
        for(String key : histoGroupMap.keySet()) {
            writeHistoGroup(dir, key);
        }
        System.out.println("Saving histograms to file " + fileName);
        dir.writeFile(fileName);
    }
    
    public void writeHistoGroup(TDirectory dir, String key) {
        String folder = "/" + key;
        dir.mkdir(folder);
        dir.cd(folder); 
        int nrows = histoGroupMap.get(key).getRows();
        int ncols = histoGroupMap.get(key).getColumns();
        int nds   = nrows*ncols;
        for(int i = 0; i < nds; i++){
            List<IDataSet> dsList = histoGroupMap.get(key).getData(i);
            for(IDataSet ds : dsList){
                dir.addDataSet(ds);                
            }          
        }
    } 
    
    public void readHistos(String fileName) {
        TDirectory dir = new TDirectory();
        dir.readFile(fileName);
        for(String key : histoGroupMap.keySet()) {
            histoGroupMap.replace(key, readHistoGroup(dir, key));
        } 
    }
    
    public HistoGroup readHistoGroup(TDirectory dir, String key) {
        int nrows = histoGroupMap.get(key).getRows();
        int ncols = histoGroupMap.get(key).getColumns();
        int nds   = nrows*ncols;
        HistoGroup newGroup = new HistoGroup(key, ncols, nrows);
        for(int i = 0; i < nds; i++){
            List<IDataSet> dsList = histoGroupMap.get(key).getData(i);            
            for(IDataSet ds : dsList){
                if(dir.getObject(key, ds.getName())!=null) {
                    newGroup.addDataSet(dir.getObject(key, ds.getName()),i);
                }
            }
        }
        return newGroup;        
    }    
        
    public EmbeddedCanvasTabbed plotHistos() {
        setStyle();
        EmbeddedCanvasTabbed canvas  = null;
        for(String key : histoGroupMap.keySet()) {
            if(canvas==null) canvas = new EmbeddedCanvasTabbed(key);
            else             canvas.addCanvas(key);
            canvas.getCanvas(key).draw(histoGroupMap.get(key));
        }
                        
        return canvas;       
    }            
    
    public void setStyle(){      
        GStyle.setCanvasBackgroundColor(Color.lightGray);
        GStyle.getH1FAttributes().setOptStat("r");
        GStyle.getH1FAttributes().setLineWidth(2);
        //GStyle.getH2FAttributes().setDrawOptions("colz");
        GStyle.setGraphicsFrameLineWidth(1);
        GStyle.getAxisAttributesX().setTitleFontSize(18);
        GStyle.getAxisAttributesX().setLabelFontSize(14);
        GStyle.getAxisAttributesY().setTitleFontSize(18);
        GStyle.getAxisAttributesY().setLabelFontSize(14);
        GStyle.getAxisAttributesZ().setLabelFontSize(14);
        GStyle.getAxisAttributesX().setLabelFontName("Arial");
        GStyle.getAxisAttributesY().setLabelFontName("Arial");
        GStyle.getAxisAttributesZ().setLabelFontName("Arial");
        GStyle.getAxisAttributesX().setTitleFontName("Arial");
        GStyle.getAxisAttributesY().setTitleFontName("Arial");
        GStyle.getAxisAttributesZ().setTitleFontName("Arial");
        GStyle.getAxisAttributesZ().setLog(true);
    }    
} 