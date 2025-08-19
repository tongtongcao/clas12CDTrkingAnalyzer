package org.clas.analysis.displayRecon;

import java.util.List;
import javax.swing.JFrame;

import org.jlab.groot.data.H1F;
import org.jlab.groot.graphics.EmbeddedCanvasTabbed;
import org.jlab.jnp.hipo4.data.Event;
import org.jlab.jnp.hipo4.data.SchemaFactory;
import org.jlab.jnp.hipo4.io.HipoReader;
import org.jlab.utils.benchmark.ProgressPrintout;
import org.jlab.utils.options.OptionParser;

import org.clas.analysis.BaseAnalysis;
import org.clas.utilities.Constants;
import org.clas.element.Hit;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;

/**
 *
 * @author Tongtong Cao
 */
public class HitRecon extends BaseAnalysis{ 
    
    public HitRecon(){}
    
    @Override
    public void createHistoGroupMap(){
        HistoGroup histoGroupBSTSector = new HistoGroup("sector for BST Hit", 3, 2);
        HistoGroup histoGroupBSTStrip= new HistoGroup("strip for BST Hit", 3, 2);
        HistoGroup histoGroupBSTEnergy = new HistoGroup("energy for BST Hit", 3, 2);
        HistoGroup histoGroupBSTTime = new HistoGroup("time for BST Hit", 3, 2);
        HistoGroup histoGroupBSTFitResidual = new HistoGroup("fitResidual for BST Hit", 3, 2);
        HistoGroup histoGroupBSTOrder = new HistoGroup("order for BST Hit", 3, 2);
        for (int i = 0; i < 6; i++) {
            H1F h1_sector = new H1F("BST sector for L" + Integer.toString(i + 1),
                    "BST sector for L" + Integer.toString(i + 1), 18, 0.5, 18.5);
            h1_sector.setTitleX("sector");
            h1_sector.setTitleY("Counts");
            h1_sector.setLineColor(1);
            histoGroupBSTSector.addDataSet(h1_sector, i);
            
            H1F h1_strip = new H1F("BST strip for L" + Integer.toString(i + 1),
                    "BST strip for L" + Integer.toString(i + 1), 256, 0.5, 256.5);
            h1_strip.setTitleX("strip");
            h1_strip.setTitleY("Counts");
            h1_strip.setLineColor(1);
            histoGroupBSTStrip.addDataSet(h1_strip, i);            
            
            H1F h1_energy = new H1F("BST energy for L" + Integer.toString(i + 1),
                    "BST energy for L" + Integer.toString(i + 1), 100, 0, 800);
            h1_energy.setTitleX("energy");
            h1_energy.setTitleY("Counts");
            h1_energy.setLineColor(1);
            histoGroupBSTEnergy.addDataSet(h1_energy, i);  
            
            H1F h1_time = new H1F("BST time for L" + Integer.toString(i + 1),
                    "BST time for L" + Integer.toString(i + 1), 100, 0, 550);
            h1_time.setTitleX("time");
            h1_time.setTitleY("Counts");
            h1_time.setLineColor(1);
            histoGroupBSTTime.addDataSet(h1_time, i); 
            
            H1F h1_fitResidual = new H1F("BST fitResidual for L" + Integer.toString(i + 1),
                    "BST fitResidual for L" + Integer.toString(i + 1), 100, -0.1, 0.1);
            h1_fitResidual.setTitleX("fitResidual");
            h1_fitResidual.setTitleY("Counts");
            h1_fitResidual.setLineColor(1);
            histoGroupBSTFitResidual.addDataSet(h1_fitResidual, i);             
            
            H1F h1_order = new H1F("BST order for L" + Integer.toString(i + 1),
                    "BST order for L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h1_order.setTitleX("order");
            h1_order.setTitleY("Counts");
            h1_order.setLineColor(1);
            histoGroupBSTOrder.addDataSet(h1_order, i);              
        }
        histoGroupMap.put(histoGroupBSTSector.getName(), histoGroupBSTSector);
        histoGroupMap.put(histoGroupBSTStrip.getName(), histoGroupBSTStrip);
        histoGroupMap.put(histoGroupBSTEnergy.getName(), histoGroupBSTEnergy);
        histoGroupMap.put(histoGroupBSTTime.getName(), histoGroupBSTTime);
        histoGroupMap.put(histoGroupBSTFitResidual.getName(), histoGroupBSTFitResidual);        
        histoGroupMap.put(histoGroupBSTOrder.getName(), histoGroupBSTOrder);
        
        if(Constants.BG){
            HistoGroup histoGroupBSTSectorNormal = new HistoGroup("sector for normal BST Hit", 3, 2);
            HistoGroup histoGroupBSTStripNormal= new HistoGroup("strip for normal BST Hit", 3, 2);
            HistoGroup histoGroupBSTEnergyNormal = new HistoGroup("energy for normal BST Hit", 3, 2);
            HistoGroup histoGroupBSTTimeNormal = new HistoGroup("time for normal BST Hit", 3, 2);
            HistoGroup histoGroupBSTFitResidualNormal = new HistoGroup("fitResidual for normal BST Hit", 3, 2);
            for (int i = 0; i < 6; i++) {
                H1F h1_sector = new H1F("BST sector for L" + Integer.toString(i + 1),
                        "BST sector for L" + Integer.toString(i + 1), 18, 0.5, 18.5);
                h1_sector.setTitleX("sector");
                h1_sector.setTitleY("Counts");
                h1_sector.setLineColor(1);
                histoGroupBSTSectorNormal.addDataSet(h1_sector, i);

                H1F h1_strip = new H1F("BST strip for L" + Integer.toString(i + 1),
                        "BST strip for L" + Integer.toString(i + 1), 256, 0.5, 256.5);
                h1_strip.setTitleX("strip");
                h1_strip.setTitleY("Counts");
                h1_strip.setLineColor(1);
                histoGroupBSTStripNormal.addDataSet(h1_strip, i);            

                H1F h1_energy = new H1F("BST energy for L" + Integer.toString(i + 1),
                        "BST energy for L" + Integer.toString(i + 1), 100, 0, 800);
                h1_energy.setTitleX("energy");
                h1_energy.setTitleY("Counts");
                h1_energy.setLineColor(1);
                histoGroupBSTEnergyNormal.addDataSet(h1_energy, i);  

                H1F h1_time = new H1F("BST time for L" + Integer.toString(i + 1),
                        "BST time for L" + Integer.toString(i + 1), 100, 0, 550);
                h1_time.setTitleX("time");
                h1_time.setTitleY("Counts");
                h1_time.setLineColor(1);
                histoGroupBSTTimeNormal.addDataSet(h1_time, i);  
                
                H1F h1_fitResidual = new H1F("BST fitResidual for L" + Integer.toString(i + 1),
                        "BST fitResidual for L" + Integer.toString(i + 1), 100, -0.1, 0.1);
                h1_fitResidual.setTitleX("fitResidual");
                h1_fitResidual.setTitleY("Counts");
                h1_fitResidual.setLineColor(1);
                histoGroupBSTFitResidualNormal.addDataSet(h1_fitResidual, i);                 
            }
            histoGroupMap.put(histoGroupBSTSectorNormal.getName(), histoGroupBSTSectorNormal);
            histoGroupMap.put(histoGroupBSTStripNormal.getName(), histoGroupBSTStripNormal);
            histoGroupMap.put(histoGroupBSTEnergyNormal.getName(), histoGroupBSTEnergyNormal);
            histoGroupMap.put(histoGroupBSTTimeNormal.getName(), histoGroupBSTTimeNormal); 
            histoGroupMap.put(histoGroupBSTFitResidualNormal.getName(), histoGroupBSTFitResidualNormal); 
            
            HistoGroup histoGroupBSTSectorBg = new HistoGroup("sector for bg BST Hit", 3, 2);
            HistoGroup histoGroupBSTStripBg= new HistoGroup("strip for bg BST Hit", 3, 2);
            HistoGroup histoGroupBSTEnergyBg = new HistoGroup("energy for bg BST Hit", 3, 2);
            HistoGroup histoGroupBSTTimeBg = new HistoGroup("time for bg BST Hit", 3, 2);
            HistoGroup histoGroupBSTFitResidualBg = new HistoGroup("fitResidual for bg BST Hit", 3, 2);
            for (int i = 0; i < 6; i++) {
                H1F h1_sector = new H1F("BST sector for L" + Integer.toString(i + 1),
                        "BST sector for L" + Integer.toString(i + 1), 18, 0.5, 18.5);
                h1_sector.setTitleX("sector");
                h1_sector.setTitleY("Counts");
                h1_sector.setLineColor(1);
                histoGroupBSTSectorBg.addDataSet(h1_sector, i);

                H1F h1_strip = new H1F("BST strip for L" + Integer.toString(i + 1),
                        "BST strip for L" + Integer.toString(i + 1), 256, 0.5, 256.5);
                h1_strip.setTitleX("strip");
                h1_strip.setTitleY("Counts");
                h1_strip.setLineColor(1);
                histoGroupBSTStripBg.addDataSet(h1_strip, i);            

                H1F h1_energy = new H1F("BST energy for L" + Integer.toString(i + 1),
                        "BST energy for L" + Integer.toString(i + 1), 100, 0, 800);
                h1_energy.setTitleX("energy");
                h1_energy.setTitleY("Counts");
                h1_energy.setLineColor(1);
                histoGroupBSTEnergyBg.addDataSet(h1_energy, i);  

                H1F h1_time = new H1F("BST time for L" + Integer.toString(i + 1),
                        "BST time for L" + Integer.toString(i + 1), 100, 0, 550);
                h1_time.setTitleX("time");
                h1_time.setTitleY("Counts");
                h1_time.setLineColor(1);
                histoGroupBSTTimeBg.addDataSet(h1_time, i);  
                
                H1F h1_fitResidual = new H1F("BST fitResidual for L" + Integer.toString(i + 1),
                        "BST fitResidual for L" + Integer.toString(i + 1), 100, -0.1, 0.1);
                h1_fitResidual.setTitleX("fitResidual");
                h1_fitResidual.setTitleY("Counts");
                h1_fitResidual.setLineColor(1);
                histoGroupBSTFitResidualBg.addDataSet(h1_fitResidual, i);  
            }
            histoGroupMap.put(histoGroupBSTSectorBg.getName(), histoGroupBSTSectorBg);
            histoGroupMap.put(histoGroupBSTStripBg.getName(), histoGroupBSTStripBg);
            histoGroupMap.put(histoGroupBSTEnergyBg.getName(), histoGroupBSTEnergyBg);
            histoGroupMap.put(histoGroupBSTTimeBg.getName(), histoGroupBSTTimeBg);  
            histoGroupMap.put(histoGroupBSTFitResidualBg.getName(), histoGroupBSTFitResidualBg);            
        }
        
        HistoGroup histoGroupBMTSector = new HistoGroup("sector for BMT Hit", 3, 2);
        HistoGroup histoGroupBMTStrip= new HistoGroup("strip for BMT Hit", 3, 2);
        HistoGroup histoGroupBMTEnergy = new HistoGroup("energy for BMT Hit", 3, 2);
        HistoGroup histoGroupBMTTime = new HistoGroup("time for BMT Hit", 3, 2);
        HistoGroup histoGroupBMTFitResidual = new HistoGroup("fitResidual for BMT Hit", 3, 2);        
        HistoGroup histoGroupBMTOrder = new HistoGroup("order for BMT Hit", 3, 2);
        for (int i = 0; i < 6; i++) {
            H1F h1_sector = new H1F("BMT sector for L" + Integer.toString(i + 1),
                    "BMT sector for L" + Integer.toString(i + 1), 3, 0.5, 3.5);
            h1_sector.setTitleX("sector");
            h1_sector.setTitleY("Counts");
            h1_sector.setLineColor(1);
            histoGroupBMTSector.addDataSet(h1_sector, i);
            
            H1F h1_strip = new H1F("BMT strip for L" + Integer.toString(i + 1),
                    "BMT strip for L" + Integer.toString(i + 1), 1200, 0.5, 1200.5);
            h1_strip.setTitleX("strip");
            h1_strip.setTitleY("Counts");
            h1_strip.setLineColor(1);
            histoGroupBMTStrip.addDataSet(h1_strip, i);            
            
            H1F h1_energy = new H1F("BMT energy for L" + Integer.toString(i + 1),
                    "BMT energy for L" + Integer.toString(i + 1), 100, 0, 2000);
            h1_energy.setTitleX("energy");
            h1_energy.setTitleY("Counts");
            h1_energy.setLineColor(1);
            histoGroupBMTEnergy.addDataSet(h1_energy, i);  
            
            H1F h1_time = new H1F("BMT time for L" + Integer.toString(i + 1),
                    "BMT time for L" + Integer.toString(i + 1), 100, 0, 450);
            h1_time.setTitleX("time");
            h1_time.setTitleY("Counts");
            h1_time.setLineColor(1);
            histoGroupBMTTime.addDataSet(h1_time, i); 
            
            H1F h1_fitResidual = new H1F("BMT fitResidual for L" + Integer.toString(i + 1),
                    "BMT fitResidual for L" + Integer.toString(i + 1), 100, -0.1, 0.1);
            h1_fitResidual.setTitleX("fitResidual");
            h1_fitResidual.setTitleY("Counts");
            h1_fitResidual.setLineColor(1);
            histoGroupBMTFitResidual.addDataSet(h1_fitResidual, i);              
            
            H1F h1_order = new H1F("BMT order for L" + Integer.toString(i + 1),
                    "BMT order for L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h1_order.setTitleX("order");
            h1_order.setTitleY("Counts");
            h1_order.setLineColor(1);
            histoGroupBMTOrder.addDataSet(h1_order, i);              
        }
        histoGroupMap.put(histoGroupBMTSector.getName(), histoGroupBMTSector);
        histoGroupMap.put(histoGroupBMTStrip.getName(), histoGroupBMTStrip);
        histoGroupMap.put(histoGroupBMTEnergy.getName(), histoGroupBMTEnergy);
        histoGroupMap.put(histoGroupBMTTime.getName(), histoGroupBMTTime);
        histoGroupMap.put(histoGroupBMTFitResidual.getName(), histoGroupBMTFitResidual);
        histoGroupMap.put(histoGroupBMTOrder.getName(), histoGroupBMTOrder);
        
        if(Constants.BG){
            HistoGroup histoGroupBMTSectorNormal = new HistoGroup("sector for normal BMT Hit", 3, 2);
            HistoGroup histoGroupBMTStripNormal= new HistoGroup("strip for normal BMT Hit", 3, 2);
            HistoGroup histoGroupBMTEnergyNormal = new HistoGroup("energy for normal BMT Hit", 3, 2);
            HistoGroup histoGroupBMTTimeNormal = new HistoGroup("time for normal BMT Hit", 3, 2);
            HistoGroup histoGroupBMTFitResidualNormal = new HistoGroup("fitResidual for normal BMT Hit", 3, 2);   
            for (int i = 0; i < 6; i++) {
                H1F h1_sector = new H1F("BMT sector for L" + Integer.toString(i + 1),
                        "BMT sector for L" + Integer.toString(i + 1), 3, 0.5, 3.5);
                h1_sector.setTitleX("sector");
                h1_sector.setTitleY("Counts");
                h1_sector.setLineColor(1);
                histoGroupBMTSectorNormal.addDataSet(h1_sector, i);

                H1F h1_strip = new H1F("BMT strip for L" + Integer.toString(i + 1),
                        "BMT strip for L" + Integer.toString(i + 1), 1200, 0.5, 1200.5);
                h1_strip.setTitleX("strip");
                h1_strip.setTitleY("Counts");
                h1_strip.setLineColor(1);
                histoGroupBMTStripNormal.addDataSet(h1_strip, i);            

                H1F h1_energy = new H1F("BMT energy for L" + Integer.toString(i + 1),
                        "BMT energy for L" + Integer.toString(i + 1), 100, 0, 2000);
                h1_energy.setTitleX("energy");
                h1_energy.setTitleY("Counts");
                h1_energy.setLineColor(1);
                histoGroupBMTEnergyNormal.addDataSet(h1_energy, i);  

                H1F h1_time = new H1F("BMT time for L" + Integer.toString(i + 1),
                        "BMT time for L" + Integer.toString(i + 1), 100, 0, 450);
                h1_time.setTitleX("time");
                h1_time.setTitleY("Counts");
                h1_time.setLineColor(1);
                histoGroupBMTTimeNormal.addDataSet(h1_time, i);
                
                H1F h1_fitResidual = new H1F("BMT fitResidual for L" + Integer.toString(i + 1),
                        "BMT fitResidual for L" + Integer.toString(i + 1), 100, -0.1, 0.1);
                h1_fitResidual.setTitleX("fitResidual");
                h1_fitResidual.setTitleY("Counts");
                h1_fitResidual.setLineColor(1);
                histoGroupBMTFitResidualNormal.addDataSet(h1_fitResidual, i);  
            }
            histoGroupMap.put(histoGroupBMTSectorNormal.getName(), histoGroupBMTSectorNormal);
            histoGroupMap.put(histoGroupBMTStripNormal.getName(), histoGroupBMTStripNormal);
            histoGroupMap.put(histoGroupBMTEnergyNormal.getName(), histoGroupBMTEnergyNormal);
            histoGroupMap.put(histoGroupBMTTimeNormal.getName(), histoGroupBMTTimeNormal); 
            histoGroupMap.put(histoGroupBMTFitResidualNormal.getName(), histoGroupBMTFitResidualNormal); 
            
            HistoGroup histoGroupBMTSectorBg = new HistoGroup("sector for bg BMT Hit", 3, 2);
            HistoGroup histoGroupBMTStripBg= new HistoGroup("strip for bg BMT Hit", 3, 2);
            HistoGroup histoGroupBMTEnergyBg = new HistoGroup("energy for bg BMT Hit", 3, 2);
            HistoGroup histoGroupBMTTimeBg = new HistoGroup("time for bg BMT Hit", 3, 2);
            HistoGroup histoGroupBMTFitResidualBg = new HistoGroup("fitResidual for bg BMT Hit", 3, 2);   
            for (int i = 0; i < 6; i++) {
                H1F h1_sector = new H1F("BMT sector for L" + Integer.toString(i + 1),
                        "BMT sector for L" + Integer.toString(i + 1), 3, 0.5, 3.5);
                h1_sector.setTitleX("sector");
                h1_sector.setTitleY("Counts");
                h1_sector.setLineColor(1);
                histoGroupBMTSectorBg.addDataSet(h1_sector, i);

                H1F h1_strip = new H1F("BMT strip for L" + Integer.toString(i + 1),
                        "BMT strip for L" + Integer.toString(i + 1), 1200, 0.5, 1200.5);
                h1_strip.setTitleX("strip");
                h1_strip.setTitleY("Counts");
                h1_strip.setLineColor(1);
                histoGroupBMTStripBg.addDataSet(h1_strip, i);            

                H1F h1_energy = new H1F("BMT energy for L" + Integer.toString(i + 1),
                        "BMT energy for L" + Integer.toString(i + 1), 100, 0, 2000);
                h1_energy.setTitleX("energy");
                h1_energy.setTitleY("Counts");
                h1_energy.setLineColor(1);
                histoGroupBMTEnergyBg.addDataSet(h1_energy, i);  

                H1F h1_time = new H1F("BMT time for L" + Integer.toString(i + 1),
                        "BMT time for L" + Integer.toString(i + 1), 100, 0, 450);
                h1_time.setTitleX("time");
                h1_time.setTitleY("Counts");
                h1_time.setLineColor(1);
                histoGroupBMTTimeBg.addDataSet(h1_time, i); 
                
                H1F h1_fitResidual = new H1F("BMT fitResidual for L" + Integer.toString(i + 1),
                        "BMT fitResidual for L" + Integer.toString(i + 1), 100, -0.1, 0.1);
                h1_fitResidual.setTitleX("fitResidual");
                h1_fitResidual.setTitleY("Counts");
                h1_fitResidual.setLineColor(1);
                histoGroupBMTFitResidualBg.addDataSet(h1_fitResidual, i);                 
            }
            histoGroupMap.put(histoGroupBMTSectorBg.getName(), histoGroupBMTSectorBg);
            histoGroupMap.put(histoGroupBMTStripBg.getName(), histoGroupBMTStripBg);
            histoGroupMap.put(histoGroupBMTEnergyBg.getName(), histoGroupBMTEnergyBg);
            histoGroupMap.put(histoGroupBMTTimeBg.getName(), histoGroupBMTTimeBg);
            histoGroupMap.put(histoGroupBMTFitResidualBg.getName(), histoGroupBMTFitResidualBg);
        }        
    }
             
    public void processEvent(Event event){        
        //Read banks
         //////// Read banks
        LocalEvent localEvent = new LocalEvent(reader, event);
        processEvent(localEvent);
    }
    
    public void processEvent(LocalEvent localEvent){   
        List<Hit> bstHits = localEvent.getBSTHits(Constants.PASS);
        List<Hit> bmtHits = localEvent.getBMTHits(Constants.PASS);
        
        HistoGroup histoGroupBSTSector = histoGroupMap.get("sector for BST Hit");
        HistoGroup histoGroupBSTStrip = histoGroupMap.get("strip for BST Hit");
        HistoGroup histoGroupBSTEnergy = histoGroupMap.get("energy for BST Hit");
        HistoGroup histoGroupBSTTime = histoGroupMap.get("time for BST Hit");
        HistoGroup histoGroupBSTFitResidual = histoGroupMap.get("fitResidual for BST Hit");
        HistoGroup histoGroupBSTOrder = histoGroupMap.get("order for BST Hit");
        for(Hit hit : bstHits){
            histoGroupBSTSector.getH1F("BST sector for L" + Integer.toString(hit.layer())).fill(hit.sector());
            histoGroupBSTStrip.getH1F("BST strip for L" + Integer.toString(hit.layer())).fill(hit.strip());
            histoGroupBSTEnergy.getH1F("BST energy for L" + Integer.toString(hit.layer())).fill(hit.energy());
            histoGroupBSTTime.getH1F("BST time for L" + Integer.toString(hit.layer())).fill(hit.time());
            histoGroupBSTFitResidual.getH1F("BST fitResidual for L" + Integer.toString(hit.layer())).fill(hit.fitResidual());
            histoGroupBSTOrder.getH1F("BST order for L" + Integer.toString(hit.layer())).fill(hit.order());
        }
        
        if(Constants.BG){
            HistoGroup histoGroupBSTSectorNormal = histoGroupMap.get("sector for normal BST Hit");
            HistoGroup histoGroupBSTStripNormal = histoGroupMap.get("strip for normal BST Hit");
            HistoGroup histoGroupBSTEnergyNormal = histoGroupMap.get("energy for normal BST Hit");
            HistoGroup histoGroupBSTTimeNormal = histoGroupMap.get("time for normal BST Hit");
            HistoGroup histoGroupBSTFitResidualNormal = histoGroupMap.get("fitResidual for normal BST Hit");
            HistoGroup histoGroupBSTSectorBg = histoGroupMap.get("sector for bg BST Hit");
            HistoGroup histoGroupBSTStripBg = histoGroupMap.get("strip for bg BST Hit");
            HistoGroup histoGroupBSTEnergyBg = histoGroupMap.get("energy for bg BST Hit");
            HistoGroup histoGroupBSTTimeBg = histoGroupMap.get("time for bg BST Hit");
            HistoGroup histoGroupBSTFitResidualBg = histoGroupMap.get("fitResidual for bg BST Hit");
            for(Hit hit : bstHits){
                if(hit.isNormalHit()){
                    histoGroupBSTSectorNormal.getH1F("BST sector for L" + Integer.toString(hit.layer())).fill(hit.sector());
                    histoGroupBSTStripNormal.getH1F("BST strip for L" + Integer.toString(hit.layer())).fill(hit.strip());
                    histoGroupBSTEnergyNormal.getH1F("BST energy for L" + Integer.toString(hit.layer())).fill(hit.energy());
                    histoGroupBSTTimeNormal.getH1F("BST time for L" + Integer.toString(hit.layer())).fill(hit.time());
                    histoGroupBSTFitResidualNormal.getH1F("BST fitResidual for L" + Integer.toString(hit.layer())).fill(hit.fitResidual());
                }
                else{
                    histoGroupBSTSectorBg.getH1F("BST sector for L" + Integer.toString(hit.layer())).fill(hit.sector());
                    histoGroupBSTStripBg.getH1F("BST strip for L" + Integer.toString(hit.layer())).fill(hit.strip());
                    histoGroupBSTEnergyBg.getH1F("BST energy for L" + Integer.toString(hit.layer())).fill(hit.energy());
                    histoGroupBSTTimeBg.getH1F("BST time for L" + Integer.toString(hit.layer())).fill(hit.time());
                    histoGroupBSTFitResidualBg.getH1F("BST fitResidual for L" + Integer.toString(hit.layer())).fill(hit.fitResidual());
                }
            }             
        }
        
        HistoGroup histoGroupBMTSector = histoGroupMap.get("sector for BMT Hit");
        HistoGroup histoGroupBMTStrip = histoGroupMap.get("strip for BMT Hit");
        HistoGroup histoGroupBMTEnergy = histoGroupMap.get("energy for BMT Hit");
        HistoGroup histoGroupBMTTime = histoGroupMap.get("time for BMT Hit");
        HistoGroup histoGroupBMTFitResidual = histoGroupMap.get("fitResidual for BMT Hit");
        HistoGroup histoGroupBMTOrder = histoGroupMap.get("order for BMT Hit");
        for(Hit hit : bmtHits){
            histoGroupBMTSector.getH1F("BMT sector for L" + Integer.toString(hit.layer())).fill(hit.sector());
            histoGroupBMTStrip.getH1F("BMT strip for L" + Integer.toString(hit.layer())).fill(hit.strip());
            histoGroupBMTEnergy.getH1F("BMT energy for L" + Integer.toString(hit.layer())).fill(hit.energy());
            histoGroupBMTTime.getH1F("BMT time for L" + Integer.toString(hit.layer())).fill(hit.time());
            histoGroupBMTFitResidual.getH1F("BMT fitResidual for L" + Integer.toString(hit.layer())).fill(hit.fitResidual());
            histoGroupBMTOrder.getH1F("BMT order for L" + Integer.toString(hit.layer())).fill(hit.order());
        }
        
        if(Constants.BG){
            HistoGroup histoGroupBMTSectorNormal = histoGroupMap.get("sector for normal BMT Hit");
            HistoGroup histoGroupBMTStripNormal = histoGroupMap.get("strip for normal BMT Hit");
            HistoGroup histoGroupBMTEnergyNormal = histoGroupMap.get("energy for normal BMT Hit");
            HistoGroup histoGroupBMTTimeNormal = histoGroupMap.get("time for normal BMT Hit");
            HistoGroup histoGroupBMTFitResidualNormal = histoGroupMap.get("fitResidual for normal BMT Hit");
            HistoGroup histoGroupBMTSectorBg = histoGroupMap.get("sector for bg BMT Hit");
            HistoGroup histoGroupBMTStripBg = histoGroupMap.get("strip for bg BMT Hit");
            HistoGroup histoGroupBMTEnergyBg = histoGroupMap.get("energy for bg BMT Hit");
            HistoGroup histoGroupBMTTimeBg = histoGroupMap.get("time for bg BMT Hit");
            HistoGroup histoGroupBMTFitResidualBg = histoGroupMap.get("fitResidual for bg BMT Hit");
            for(Hit hit : bmtHits){
                if(hit.isNormalHit()){
                    histoGroupBMTSectorNormal.getH1F("BMT sector for L" + Integer.toString(hit.layer())).fill(hit.sector());
                    histoGroupBMTStripNormal.getH1F("BMT strip for L" + Integer.toString(hit.layer())).fill(hit.strip());
                    histoGroupBMTEnergyNormal.getH1F("BMT energy for L" + Integer.toString(hit.layer())).fill(hit.energy());
                    histoGroupBMTTimeNormal.getH1F("BMT time for L" + Integer.toString(hit.layer())).fill(hit.time());
                    histoGroupBMTFitResidualNormal.getH1F("BMT fitResidual for L" + Integer.toString(hit.layer())).fill(hit.fitResidual());                    
                }
                else{
                    histoGroupBMTSectorBg.getH1F("BMT sector for L" + Integer.toString(hit.layer())).fill(hit.sector());
                    histoGroupBMTStripBg.getH1F("BMT strip for L" + Integer.toString(hit.layer())).fill(hit.strip());
                    histoGroupBMTEnergyBg.getH1F("BMT energy for L" + Integer.toString(hit.layer())).fill(hit.energy());
                    histoGroupBMTTimeBg.getH1F("BMT time for L" + Integer.toString(hit.layer())).fill(hit.time());
                    histoGroupBMTFitResidualBg.getH1F("BMT fitResidual for L" + Integer.toString(hit.layer())).fill(hit.fitResidual());
                }
            }             
        }         

    }
    
    public void postEventProcess() {

                      
    }            
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("HitRecon");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o", "", "output file name prefix");
        parser.addOption("-n", "-1", "maximum number of events to process");
        parser.addOption("-plot", "1", "display histograms (0/1)");
        parser.addOption("-bg", "0", "if bg (0/1)");
        parser.addOption("-pass", "2", "if bg (1/2)");                
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
        
        HitRecon analysis = new HitRecon();
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
