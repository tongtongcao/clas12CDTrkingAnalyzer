package org.clas.analysis.studyBg;

import java.util.List;
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

/**
 *
 * @author Tongtong Cao
 */
public class Bg extends BaseAnalysis{
    HitBg hitBg = new HitBg();
    ClusterBg clusterBg = new ClusterBg();
    CrossBg crossBg = new CrossBg();
    SeedBg seedBg = new SeedBg();
    TrackBg trackBg = new TrackBg();
    
    public Bg(){}
    
    @Override
    public void createHistoGroupMap(){  
        hitBg.createHistoGroupMap();
        for(String key : hitBg.getHistoGroupMap().keySet()){
            histoGroupMap.put(key, hitBg.getHistoGroupMap().get(key));
        }
        
        clusterBg.createHistoGroupMap();
        for(String key : clusterBg.getHistoGroupMap().keySet()){
            histoGroupMap.put(key, clusterBg.getHistoGroupMap().get(key));
        }

        crossBg.createHistoGroupMap();
        for(String key : crossBg.getHistoGroupMap().keySet()){
            histoGroupMap.put(key, crossBg.getHistoGroupMap().get(key));
        }

        seedBg.createHistoGroupMap();
        for(String key : seedBg.getHistoGroupMap().keySet()){
            histoGroupMap.put(key, seedBg.getHistoGroupMap().get(key));
        }

        trackBg.createHistoGroupMap();
        for(String key : trackBg.getHistoGroupMap().keySet()){
            histoGroupMap.put(key, trackBg.getHistoGroupMap().get(key));
        } 
    }
             
    public void processEvent(Event event1, Event event2, boolean uTrack){        
        //Read banks
        LocalEvent localEvent1 = new LocalEvent(reader1, event1); 
        LocalEvent localEvent2 = new LocalEvent(reader2, event2);
        Match match = new Match(localEvent1, localEvent2);
        
        hitBg.processEvent(localEvent1, localEvent2, match);
        clusterBg.processEvent(localEvent1, localEvent2, match);
        crossBg.processEvent(localEvent1, localEvent2, match);
        seedBg.processEvent(localEvent1, localEvent2, match);
        trackBg.processEvent(localEvent1, localEvent2, uTrack, match);
    }
    
    public void postEventProcess() {  
        hitBg.postEventProcess();
        clusterBg.postEventProcess();
        crossBg.postEventProcess();
        seedBg.postEventProcess();
        trackBg.postEventProcess();                              
    }            
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("bgEffects");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o"          ,"",     "output file name prefix");
        parser.addOption("-n"          ,"-1",   "maximum number of events to process");        
        parser.addOption("-plot"       ,"1",    "display histograms (0/1)");
        parser.addOption("-pass", "2", "if bg (1/2)");
        parser.addOption("-uTrack", "0", "if bg (0/1)");
        parser.addOption("-histo"      ,"0",    "read histogram file (0/1)");        
        parser.parse(args);
        
        String namePrefix  = parser.getOption("-o").stringValue(); 
        int maxEvents  = parser.getOption("-n").intValue();    
        boolean displayPlots   = (parser.getOption("-plot").intValue()!=0);
        boolean readHistos   = (parser.getOption("-histo").intValue()!=0);        
        int pass  = parser.getOption("-pass").intValue(); 
        Constants.PASS = pass;
        boolean uTrack = (parser.getOption("-uTrack").intValue() != 0);
        
        List<String> inputList = parser.getInputList();
        if(inputList.isEmpty()==true){
            parser.printUsage();
            inputList.add("/Users/caot/research/clas12/data/mc/uRWELL/upgradeTrackingWithuRWELL/nobg/applyCTDAF/pt3_1pt6/origin/recon_before_update.hipo");
            inputList.add("/Users/caot/research/clas12/data/mc/uRWELL/upgradeTrackingWithuRWELL/bg/applyCTDAF/pt3_1pt6/origin/recon_before_update_bg.hipo");
            maxEvents = 1000;
            //System.out.println("\n >>>> error: no input file is specified....\n");
            //System.exit(0);
        }

        String histoName   = "histo.hipo"; 
        if(!namePrefix.isEmpty()) {
            histoName  = namePrefix + "_" + histoName;
        }
        
        Constants.BG = true;         
        Bg analysis = new Bg();
        analysis.createHistoGroupMap();
        
        if(!readHistos) {                 
            HipoReader reader1 = new HipoReader();
            reader1.open(inputList.get(0));
            HipoReader reader2 = new HipoReader();
            reader2.open(inputList.get(1));

            SchemaFactory schema1 = reader1.getSchemaFactory();
            SchemaFactory schema2 = reader2.getSchemaFactory();
            analysis.initReader(new Banks(schema1), new Banks(schema2));

            int counter = 0;
            Event event1 = new Event();
            Event event2 = new Event();
        
            ProgressPrintout progress = new ProgressPrintout();
            while (reader1.hasNext() && reader2.hasNext()) {

                counter++;

                reader1.nextEvent(event1);
                reader2.nextEvent(event2);               
                analysis.processEvent(event1, event2, uTrack);
                progress.updateStatus();
                if(maxEvents>0){
                    if(counter>=maxEvents) break;
                }                    
            }
            
            analysis.postEventProcess();
            
            progress.showStatus();
            reader1.close(); 
            reader2.close();
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
