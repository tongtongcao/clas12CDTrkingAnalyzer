package org.clas.analysis.displayRecon;

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
public class Recon extends BaseAnalysis{
    ADCRecon adcRecon = new ADCRecon();
    HitRecon hitRecon = new HitRecon();
    ClusterRecon clusterRecon = new ClusterRecon();
    CrossRecon crossRecon = new CrossRecon();
    SeedRecon seedRecon = new SeedRecon();
    TrackRecon trackRecon = new TrackRecon();
    
    public Recon(){}
    
    @Override
    public void createHistoGroupMap(){
        adcRecon.createHistoGroupMap();
        for(String key : adcRecon.getHistoGroupMap().keySet()){
            histoGroupMap.put(key, adcRecon.getHistoGroupMap().get(key));
        }
        
        hitRecon.createHistoGroupMap();
        for(String key : hitRecon.getHistoGroupMap().keySet()){
            histoGroupMap.put(key, hitRecon.getHistoGroupMap().get(key));
        }
        
        clusterRecon.createHistoGroupMap();
        for(String key : clusterRecon.getHistoGroupMap().keySet()){
            histoGroupMap.put(key, clusterRecon.getHistoGroupMap().get(key));
        }
        
        crossRecon.createHistoGroupMap();
        for(String key : crossRecon.getHistoGroupMap().keySet()){
            histoGroupMap.put(key, crossRecon.getHistoGroupMap().get(key));
        }

        seedRecon.createHistoGroupMap();
        for(String key : seedRecon.getHistoGroupMap().keySet()){
            histoGroupMap.put(key, seedRecon.getHistoGroupMap().get(key));
        }

        trackRecon.createHistoGroupMap();
        for(String key : trackRecon.getHistoGroupMap().keySet()){
            histoGroupMap.put(key, trackRecon.getHistoGroupMap().get(key));
        }        
    }
             
    public void processEvent(Event event, boolean uTrack){        
        //Read banks
        LocalEvent localEvent = new LocalEvent(reader, event); 
        
        adcRecon.processEvent(localEvent);
        hitRecon.processEvent(localEvent);
        clusterRecon.processEvent(localEvent);
        crossRecon.processEvent(localEvent);
        seedRecon.processEvent(localEvent);
        trackRecon.processEvent(localEvent, uTrack);
    }
    
    public void postEventProcess() {  
        adcRecon.postEventProcess();
        hitRecon.postEventProcess();
        clusterRecon.postEventProcess();
        crossRecon.postEventProcess();
        seedRecon.postEventProcess();
        trackRecon.postEventProcess();
        
                      
    }            
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("uRWellReconstruction");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o"          ,"",     "output file name prefix");
        parser.addOption("-n"          ,"-1",   "maximum number of events to process");        
        parser.addOption("-plot"       ,"1",    "display histograms (0/1)");
        parser.addOption("-bg", "0", "if bg (0/1)");
        parser.addOption("-pass", "2", "if bg (1/2)");
        parser.addOption("-uTrack", "0", "if bg (0/1)");
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
        boolean uTrack = (parser.getOption("-uTrack").intValue() != 0);
        
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
        
        Recon analysis = new Recon();
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
                analysis.processEvent(event, uTrack);
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
