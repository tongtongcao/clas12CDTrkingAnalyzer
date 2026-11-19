package org.clas.analysis.studyHit;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import javafx.util.Pair;
import javax.swing.JFrame;

import org.jlab.groot.graphics.EmbeddedCanvasTabbed;
import org.jlab.jnp.hipo4.data.Event;
import org.jlab.jnp.hipo4.data.SchemaFactory;
import org.jlab.jnp.hipo4.io.HipoReader;
import org.jlab.utils.benchmark.ProgressPrintout;
import org.jlab.utils.options.OptionParser;

import org.clas.analysis.BaseAnalysis;
import org.clas.utilities.Constants;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;
import org.clas.element.Hit;
import org.clas.element.Cluster;
import org.clas.element.Cross;
import org.clas.element.MCParticle;
import org.clas.element.Seed;
import org.clas.element.Track;
import org.clas.graph.HistoGroup;
import org.clas.graph.TrackHistoGroup;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;

/**
 * Hit distributions
 * 
 * @author Tongtong Cao
 */
public class AnalysisHitDistributions extends BaseAnalysis{ 
    
       
    public AnalysisHitDistributions(){}
    
    @Override
    public void createHistoGroupMap(){   
        HistoGroup histoGroupBSTStripSector = new HistoGroup("bstStripSector", 3, 2);
        for(int i = 0; i < 6; i++){
            H2F h2_bstStripSector = new H2F("bstStripSector for L" + Integer.toString(i + 1), "bstStripSector for L" + Integer.toString(i + 1), Constants.BSTSECTORS[i], 0.5, Constants.BSTSECTORS[i]+0.5, 256, 0.5, 256.5);
            h2_bstStripSector.setTitleX("sector");
            h2_bstStripSector.setTitleY("strip"); 
            histoGroupBSTStripSector.addDataSet(h2_bstStripSector, i);
        }     
        histoGroupMap.put(histoGroupBSTStripSector.getName(), histoGroupBSTStripSector); 
        
        HistoGroup histoGroupBMTStripSector = new HistoGroup("bmtStripSector", 3, 2);
        for(int i = 0; i < 6; i++){
            H2F h2_bmtStripSector = new H2F("bmtStripSector for L" + Integer.toString(i + 1), "bmtStripSector for L" + Integer.toString(i + 1), 3, 0.5, 3.5, Constants.BMTSTRIPS[i], 0.5, Constants.BMTSTRIPS[i]+0.5);
            h2_bmtStripSector.setTitleX("sector");
            h2_bmtStripSector.setTitleY("strip"); 
            histoGroupBMTStripSector.addDataSet(h2_bmtStripSector, i);
        }     
        histoGroupMap.put(histoGroupBMTStripSector.getName(), histoGroupBMTStripSector);   
        
        HistoGroup histoGroupHits = new HistoGroup("hits", 3, 2);
        H2F h2_bstSectorVsLayer = new H2F("bstSectorVsLayer", "bstSectorVsLayer", 6, 0.5, 6.5, 18, 0.5, 18.5);
        h2_bstSectorVsLayer.setTitleX("layer");
        h2_bstSectorVsLayer.setTitleY("sector"); 
        histoGroupHits.addDataSet(h2_bstSectorVsLayer, 0);        
        H2F h2_bmtSectorVsLayer = new H2F("bmtSectorVsLayer", "bmtSectorVsLayer", 6, 0.5, 6.5, 3, 0.5, 3.5);
        h2_bmtSectorVsLayer.setTitleX("layer");
        h2_bmtSectorVsLayer.setTitleY("sector"); 
        histoGroupHits.addDataSet(h2_bmtSectorVsLayer, 1);                
        histoGroupMap.put(histoGroupHits.getName(), histoGroupHits); 
                 
    }
             
    public void processEvent(Event event){        
        //Read banks
        LocalEvent localEvent = new LocalEvent(reader, event); 
        
        List<Hit> bstHits = localEvent.getBSTHits();
        List<Hit> bmtHits = localEvent.getBMTHits();
        
        HistoGroup histoGroupBSTStripSector = histoGroupMap.get("bstStripSector");
        
        HistoGroup histoGroupHits = histoGroupMap.get("hits");
        for(Hit hit : bstHits){
            histoGroupBSTStripSector.getH2F("bstStripSector for L" + Integer.toString(hit.layer())).fill(hit.sector(), hit.strip());
            
            histoGroupHits.getH2F("bstSectorVsLayer").fill(hit.layer(), hit.sector());
        }
        
        HistoGroup histoGroupBMTStripSector = histoGroupMap.get("bmtStripSector");
        for(Hit hit : bmtHits){
            histoGroupBMTStripSector.getH2F("bmtStripSector for L" + Integer.toString(hit.layer())).fill(hit.sector(), hit.strip());
            
            histoGroupHits.getH2F("bmtSectorVsLayer").fill(hit.layer(), hit.sector());
        }        
        
        
                         
        

            
        
    }    
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("analysisHitDistributions");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o"          ,"",     "output file name prefix");
        parser.addOption("-n"          ,"-1",   "maximum number of events to process");        
        parser.addOption("-plot"       ,"1",    "display histograms (0/1)");
        parser.addOption("-histo"      ,"0",    "read histogram file (0/1)");  
        parser.parse(args);
        
        String namePrefix  = parser.getOption("-o").stringValue(); 
        int maxEvents  = parser.getOption("-n").intValue();    
        boolean displayPlots   = (parser.getOption("-plot").intValue()!=0);
        boolean readHistos   = (parser.getOption("-histo").intValue()!=0); 
        
        List<String> inputList = parser.getInputList();
        if(inputList.isEmpty()==true){
            parser.printUsage();
            inputList.add("/Users/caot/research/clas12/data/mc/uRWELL/upgradeTrackingWithuRWELL/nobg/applyCTDAF/pt3_1pt6/origin/recon_before_update.hipo");
            maxEvents = 1000;
            //System.out.println("\n >>>> error: no input file is specified....\n");
            //System.exit(0);
        }

        String histoName   = "histo.hipo"; 
        if(!namePrefix.isEmpty()) {
            histoName  = namePrefix + "_" + histoName;
        }
        
        Constants.BG = true;         
        AnalysisHitDistributions analysis = new AnalysisHitDistributions();
        analysis.createHistoGroupMap();        
        
        if(!readHistos) {                 
            HipoReader reader = new HipoReader();
            reader.open(inputList.get(0));

            SchemaFactory schema = reader.getSchemaFactory();
            analysis.initReader(new Banks(schema));

            int counter = 0;
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
                frame.setSize(1800, 1200);
                frame.add(canvas);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }        
    }
    
}
