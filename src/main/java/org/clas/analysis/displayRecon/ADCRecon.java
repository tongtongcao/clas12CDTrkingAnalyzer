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
import org.clas.element.BSTADC;
import org.clas.element.BMTADC;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;

/**
 *
 * @author Tongtong Cao
 */
public class ADCRecon extends BaseAnalysis{ 
    
    public ADCRecon(){}
    
    @Override
    public void createHistoGroupMap(){
        HistoGroup histoGroupBSTSector = new HistoGroup("sector for BST ADC", 3, 2);
        HistoGroup histoGroupBSTStrip= new HistoGroup("strip for BST ADC", 3, 2);
        HistoGroup histoGroupBSTADC = new HistoGroup("ADC for BST ADC", 3, 2);
        HistoGroup histoGroupBSTTime = new HistoGroup("time for BST ADC", 3, 2);
        HistoGroup histoGroupBSTOrder = new HistoGroup("order for BST ADC", 3, 2);
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
            
            H1F h1_adc = new H1F("BST ADC for L" + Integer.toString(i + 1),
                    "BST ADC for L" + Integer.toString(i + 1), 100, 0, 20);
            h1_adc.setTitleX("ADC");
            h1_adc.setTitleY("Counts");
            h1_adc.setLineColor(1);
            histoGroupBSTADC.addDataSet(h1_adc, i);  
            
            H1F h1_time = new H1F("BST time for L" + Integer.toString(i + 1),
                    "BST time for L" + Integer.toString(i + 1), 100, 0, 550);
            h1_time.setTitleX("time");
            h1_time.setTitleY("Counts");
            h1_time.setLineColor(1);
            histoGroupBSTTime.addDataSet(h1_time, i); 
            
            H1F h1_order = new H1F("BST order for L" + Integer.toString(i + 1),
                    "BST order for L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h1_order.setTitleX("order");
            h1_order.setTitleY("Counts");
            h1_order.setLineColor(1);
            histoGroupBSTOrder.addDataSet(h1_order, i);              
        }
        histoGroupMap.put(histoGroupBSTSector.getName(), histoGroupBSTSector);
        histoGroupMap.put(histoGroupBSTStrip.getName(), histoGroupBSTStrip);
        histoGroupMap.put(histoGroupBSTADC.getName(), histoGroupBSTADC);
        histoGroupMap.put(histoGroupBSTTime.getName(), histoGroupBSTTime);
        histoGroupMap.put(histoGroupBSTOrder.getName(), histoGroupBSTOrder);
        
        if(Constants.BG){
            HistoGroup histoGroupBSTSectorNormal = new HistoGroup("sector for normal BST ADC", 3, 2);
            HistoGroup histoGroupBSTStripNormal= new HistoGroup("strip for normal BST ADC", 3, 2);
            HistoGroup histoGroupBSTADCNormal = new HistoGroup("ADC for normal BST ADC", 3, 2);
            HistoGroup histoGroupBSTTimeNormal = new HistoGroup("time for normal BST ADC", 3, 2);
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

                H1F h1_adc = new H1F("BST ADC for L" + Integer.toString(i + 1),
                        "BST ADC for L" + Integer.toString(i + 1), 100, 0, 20);
                h1_adc.setTitleX("ADC");
                h1_adc.setTitleY("Counts");
                h1_adc.setLineColor(1);
                histoGroupBSTADCNormal.addDataSet(h1_adc, i);  

                H1F h1_time = new H1F("BST time for L" + Integer.toString(i + 1),
                        "BST time for L" + Integer.toString(i + 1), 100, 0, 550);
                h1_time.setTitleX("time");
                h1_time.setTitleY("Counts");
                h1_time.setLineColor(1);
                histoGroupBSTTimeNormal.addDataSet(h1_time, i);            
            }
            histoGroupMap.put(histoGroupBSTSectorNormal.getName(), histoGroupBSTSectorNormal);
            histoGroupMap.put(histoGroupBSTStripNormal.getName(), histoGroupBSTStripNormal);
            histoGroupMap.put(histoGroupBSTADCNormal.getName(), histoGroupBSTADCNormal);
            histoGroupMap.put(histoGroupBSTTimeNormal.getName(), histoGroupBSTTimeNormal); 
            
            HistoGroup histoGroupBSTSectorBg = new HistoGroup("sector for bg BST ADC", 3, 2);
            HistoGroup histoGroupBSTStripBg= new HistoGroup("strip for bg BST ADC", 3, 2);
            HistoGroup histoGroupBSTADCBg = new HistoGroup("ADC for bg BST ADC", 3, 2);
            HistoGroup histoGroupBSTTimeBg = new HistoGroup("time for bg BST ADC", 3, 2);
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

                H1F h1_adc = new H1F("BST ADC for L" + Integer.toString(i + 1),
                        "BST ADC for L" + Integer.toString(i + 1), 100, 0, 20);
                h1_adc.setTitleX("ADC");
                h1_adc.setTitleY("Counts");
                h1_adc.setLineColor(1);
                histoGroupBSTADCBg.addDataSet(h1_adc, i);  

                H1F h1_time = new H1F("BST time for L" + Integer.toString(i + 1),
                        "BST time for L" + Integer.toString(i + 1), 100, 0, 550);
                h1_time.setTitleX("time");
                h1_time.setTitleY("Counts");
                h1_time.setLineColor(1);
                histoGroupBSTTimeBg.addDataSet(h1_time, i);            
            }
            histoGroupMap.put(histoGroupBSTSectorBg.getName(), histoGroupBSTSectorBg);
            histoGroupMap.put(histoGroupBSTStripBg.getName(), histoGroupBSTStripBg);
            histoGroupMap.put(histoGroupBSTADCBg.getName(), histoGroupBSTADCBg);
            histoGroupMap.put(histoGroupBSTTimeBg.getName(), histoGroupBSTTimeBg);              
        }
        
        HistoGroup histoGroupBMTSector = new HistoGroup("sector for BMT ADC", 3, 2);
        HistoGroup histoGroupBMTStrip= new HistoGroup("strip for BMT ADC", 3, 2);
        HistoGroup histoGroupBMTADC = new HistoGroup("ADC for BMT ADC", 3, 2);
        HistoGroup histoGroupBMTTime = new HistoGroup("time for BMT ADC", 3, 2);
        HistoGroup histoGroupBMTOrder = new HistoGroup("order for BMT ADC", 3, 2);
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
            
            H1F h1_adc = new H1F("BMT ADC for L" + Integer.toString(i + 1),
                    "BMT ADC for L" + Integer.toString(i + 1), 100, 0, 2000);
            h1_adc.setTitleX("ADC");
            h1_adc.setTitleY("Counts");
            h1_adc.setLineColor(1);
            histoGroupBMTADC.addDataSet(h1_adc, i);  
            
            H1F h1_time = new H1F("BMT time for L" + Integer.toString(i + 1),
                    "BMT time for L" + Integer.toString(i + 1), 100, 0, 450);
            h1_time.setTitleX("time");
            h1_time.setTitleY("Counts");
            h1_time.setLineColor(1);
            histoGroupBMTTime.addDataSet(h1_time, i); 
            
            H1F h1_order = new H1F("BMT order for L" + Integer.toString(i + 1),
                    "BMT order for L" + Integer.toString(i + 1), 2, -0.5, 1.5);
            h1_order.setTitleX("order");
            h1_order.setTitleY("Counts");
            h1_order.setLineColor(1);
            histoGroupBMTOrder.addDataSet(h1_order, i);              
        }
        histoGroupMap.put(histoGroupBMTSector.getName(), histoGroupBMTSector);
        histoGroupMap.put(histoGroupBMTStrip.getName(), histoGroupBMTStrip);
        histoGroupMap.put(histoGroupBMTADC.getName(), histoGroupBMTADC);
        histoGroupMap.put(histoGroupBMTTime.getName(), histoGroupBMTTime);
        histoGroupMap.put(histoGroupBMTOrder.getName(), histoGroupBMTOrder);
        
        if(Constants.BG){
            HistoGroup histoGroupBMTSectorNormal = new HistoGroup("sector for normal BMT ADC", 3, 2);
            HistoGroup histoGroupBMTStripNormal= new HistoGroup("strip for normal BMT ADC", 3, 2);
            HistoGroup histoGroupBMTADCNormal = new HistoGroup("ADC for normal BMT ADC", 3, 2);
            HistoGroup histoGroupBMTTimeNormal = new HistoGroup("time for normal BMT ADC", 3, 2);
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

                H1F h1_adc = new H1F("BMT ADC for L" + Integer.toString(i + 1),
                        "BMT ADC for L" + Integer.toString(i + 1), 100, 0, 2000);
                h1_adc.setTitleX("ADC");
                h1_adc.setTitleY("Counts");
                h1_adc.setLineColor(1);
                histoGroupBMTADCNormal.addDataSet(h1_adc, i);  

                H1F h1_time = new H1F("BMT time for L" + Integer.toString(i + 1),
                        "BMT time for L" + Integer.toString(i + 1), 100, 0, 450);
                h1_time.setTitleX("time");
                h1_time.setTitleY("Counts");
                h1_time.setLineColor(1);
                histoGroupBMTTimeNormal.addDataSet(h1_time, i);            
            }
            histoGroupMap.put(histoGroupBMTSectorNormal.getName(), histoGroupBMTSectorNormal);
            histoGroupMap.put(histoGroupBMTStripNormal.getName(), histoGroupBMTStripNormal);
            histoGroupMap.put(histoGroupBMTADCNormal.getName(), histoGroupBMTADCNormal);
            histoGroupMap.put(histoGroupBMTTimeNormal.getName(), histoGroupBMTTimeNormal); 
            
            HistoGroup histoGroupBMTSectorBg = new HistoGroup("sector for bg BMT ADC", 3, 2);
            HistoGroup histoGroupBMTStripBg= new HistoGroup("strip for bg BMT ADC", 3, 2);
            HistoGroup histoGroupBMTADCBg = new HistoGroup("ADC for bg BMT ADC", 3, 2);
            HistoGroup histoGroupBMTTimeBg = new HistoGroup("time for bg BMT ADC", 3, 2);
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

                H1F h1_adc = new H1F("BMT ADC for L" + Integer.toString(i + 1),
                        "BMT ADC for L" + Integer.toString(i + 1), 100, 0, 2000);
                h1_adc.setTitleX("ADC");
                h1_adc.setTitleY("Counts");
                h1_adc.setLineColor(1);
                histoGroupBMTADCBg.addDataSet(h1_adc, i);  

                H1F h1_time = new H1F("BMT time for L" + Integer.toString(i + 1),
                        "BMT time for L" + Integer.toString(i + 1), 100, 0, 450);
                h1_time.setTitleX("time");
                h1_time.setTitleY("Counts");
                h1_time.setLineColor(1);
                histoGroupBMTTimeBg.addDataSet(h1_time, i);            
            }
            histoGroupMap.put(histoGroupBMTSectorBg.getName(), histoGroupBMTSectorBg);
            histoGroupMap.put(histoGroupBMTStripBg.getName(), histoGroupBMTStripBg);
            histoGroupMap.put(histoGroupBMTADCBg.getName(), histoGroupBMTADCBg);
            histoGroupMap.put(histoGroupBMTTimeBg.getName(), histoGroupBMTTimeBg);              
        }        
    }
             
    public void processEvent(Event event){        
        //Read banks
         //////// Read banks
        LocalEvent localEvent = new LocalEvent(reader, event);
        processEvent(localEvent);
    }
    
    public void processEvent(LocalEvent localEvent){
        List<BSTADC> bstADCs = localEvent.getBSTADCs();
        List<BMTADC> bmtADCs = localEvent.getBMTADCs();
        
        HistoGroup histoGroupBSTSector = histoGroupMap.get("sector for BST ADC");
        HistoGroup histoGroupBSTStrip = histoGroupMap.get("strip for BST ADC");
        HistoGroup histoGroupBSTADC = histoGroupMap.get("ADC for BST ADC");
        HistoGroup histoGroupBSTTime = histoGroupMap.get("time for BST ADC");
        HistoGroup histoGroupBSTOrder = histoGroupMap.get("order for BST ADC");
        for(BSTADC adc : bstADCs){
            histoGroupBSTSector.getH1F("BST sector for L" + Integer.toString(adc.layer())).fill(adc.sector());
            histoGroupBSTStrip.getH1F("BST strip for L" + Integer.toString(adc.layer())).fill(adc.component());
            histoGroupBSTADC.getH1F("BST ADC for L" + Integer.toString(adc.layer())).fill(adc.ADC());
            histoGroupBSTTime.getH1F("BST time for L" + Integer.toString(adc.layer())).fill(adc.time());
            histoGroupBSTOrder.getH1F("BST order for L" + Integer.toString(adc.layer())).fill(adc.order());
        }
        
        if(Constants.BG){
            HistoGroup histoGroupBSTSectorNormal = histoGroupMap.get("sector for normal BST ADC");
            HistoGroup histoGroupBSTStripNormal = histoGroupMap.get("strip for normal BST ADC");
            HistoGroup histoGroupBSTADCNormal = histoGroupMap.get("ADC for normal BST ADC");
            HistoGroup histoGroupBSTTimeNormal = histoGroupMap.get("time for normal BST ADC");
            HistoGroup histoGroupBSTSectorBg = histoGroupMap.get("sector for bg BST ADC");
            HistoGroup histoGroupBSTStripBg = histoGroupMap.get("strip for bg BST ADC");
            HistoGroup histoGroupBSTADCBg = histoGroupMap.get("ADC for bg BST ADC");
            HistoGroup histoGroupBSTTimeBg = histoGroupMap.get("time for bg BST ADC");
            for(BSTADC adc : bstADCs){
                if(adc.isNormalHit()){
                    histoGroupBSTSectorNormal.getH1F("BST sector for L" + Integer.toString(adc.layer())).fill(adc.sector());
                    histoGroupBSTStripNormal.getH1F("BST strip for L" + Integer.toString(adc.layer())).fill(adc.component());
                    histoGroupBSTADCNormal.getH1F("BST ADC for L" + Integer.toString(adc.layer())).fill(adc.ADC());
                    histoGroupBSTTimeNormal.getH1F("BST time for L" + Integer.toString(adc.layer())).fill(adc.time());
                }
                else{
                    histoGroupBSTSectorBg.getH1F("BST sector for L" + Integer.toString(adc.layer())).fill(adc.sector());
                    histoGroupBSTStripBg.getH1F("BST strip for L" + Integer.toString(adc.layer())).fill(adc.component());
                    histoGroupBSTADCBg.getH1F("BST ADC for L" + Integer.toString(adc.layer())).fill(adc.ADC());
                    histoGroupBSTTimeBg.getH1F("BST time for L" + Integer.toString(adc.layer())).fill(adc.time());
                }
            }             
        }
        
        HistoGroup histoGroupBMTSector = histoGroupMap.get("sector for BMT ADC");
        HistoGroup histoGroupBMTStrip = histoGroupMap.get("strip for BMT ADC");
        HistoGroup histoGroupBMTADC = histoGroupMap.get("ADC for BMT ADC");
        HistoGroup histoGroupBMTTime = histoGroupMap.get("time for BMT ADC");
        HistoGroup histoGroupBMTOrder = histoGroupMap.get("order for BMT ADC");
        for(BMTADC adc : bmtADCs){
            histoGroupBMTSector.getH1F("BMT sector for L" + Integer.toString(adc.layer())).fill(adc.sector());
            histoGroupBMTStrip.getH1F("BMT strip for L" + Integer.toString(adc.layer())).fill(adc.component());
            histoGroupBMTADC.getH1F("BMT ADC for L" + Integer.toString(adc.layer())).fill(adc.ADC());
            histoGroupBMTTime.getH1F("BMT time for L" + Integer.toString(adc.layer())).fill(adc.time());
            histoGroupBMTOrder.getH1F("BMT order for L" + Integer.toString(adc.layer())).fill(adc.order());
        }
        
        if(Constants.BG){
            HistoGroup histoGroupBMTSectorNormal = histoGroupMap.get("sector for normal BMT ADC");
            HistoGroup histoGroupBMTStripNormal = histoGroupMap.get("strip for normal BMT ADC");
            HistoGroup histoGroupBMTADCNormal = histoGroupMap.get("ADC for normal BMT ADC");
            HistoGroup histoGroupBMTTimeNormal = histoGroupMap.get("time for normal BMT ADC");
            HistoGroup histoGroupBMTSectorBg = histoGroupMap.get("sector for bg BMT ADC");
            HistoGroup histoGroupBMTStripBg = histoGroupMap.get("strip for bg BMT ADC");
            HistoGroup histoGroupBMTADCBg = histoGroupMap.get("ADC for bg BMT ADC");
            HistoGroup histoGroupBMTTimeBg = histoGroupMap.get("time for bg BMT ADC");
            for(BMTADC adc : bmtADCs){
                if(adc.isNormalHit()){
                    histoGroupBMTSectorNormal.getH1F("BMT sector for L" + Integer.toString(adc.layer())).fill(adc.sector());
                    histoGroupBMTStripNormal.getH1F("BMT strip for L" + Integer.toString(adc.layer())).fill(adc.component());
                    histoGroupBMTADCNormal.getH1F("BMT ADC for L" + Integer.toString(adc.layer())).fill(adc.ADC());
                    histoGroupBMTTimeNormal.getH1F("BMT time for L" + Integer.toString(adc.layer())).fill(adc.time());
                }
                else{
                    histoGroupBMTSectorBg.getH1F("BMT sector for L" + Integer.toString(adc.layer())).fill(adc.sector());
                    histoGroupBMTStripBg.getH1F("BMT strip for L" + Integer.toString(adc.layer())).fill(adc.component());
                    histoGroupBMTADCBg.getH1F("BMT ADC for L" + Integer.toString(adc.layer())).fill(adc.ADC());
                    histoGroupBMTTimeBg.getH1F("BMT time for L" + Integer.toString(adc.layer())).fill(adc.time());
                }
            }             
        }        
        

        

    }
    
    public void postEventProcess() {

                      
    }            
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("ADCRecon");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o"          ,"",     "output file name prefix");
        parser.addOption("-n"          ,"-1",   "maximum number of events to process");        
        parser.addOption("-plot"       ,"1",    "display histograms (0/1)");
        parser.addOption("-bg", "0", "if bg (0/1)");
                
        // histogram based analysis
        parser.addOption("-histo"      ,"0",    "read histogram file (0/1)");
        
        parser.parse(args);
        
        String namePrefix  = parser.getOption("-o").stringValue(); 
        int maxEvents  = parser.getOption("-n").intValue();    
        boolean displayPlots   = (parser.getOption("-plot").intValue()!=0);
        boolean readHistos   = (parser.getOption("-histo").intValue()!=0); 
        boolean bg = (parser.getOption("-bg").intValue() != 0);
        Constants.BG = bg;
        
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
        
        ADCRecon analysis = new ADCRecon();
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
