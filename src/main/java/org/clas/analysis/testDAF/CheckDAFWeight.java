package org.clas.analysis.testDAF;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JFrame;

import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.graphics.EmbeddedCanvasTabbed;
import org.jlab.jnp.hipo4.data.Event;
import org.jlab.jnp.hipo4.data.SchemaFactory;
import org.jlab.jnp.hipo4.io.HipoReader;
import org.jlab.utils.benchmark.ProgressPrintout;
import org.jlab.utils.options.OptionParser;

import org.clas.analysis.BaseAnalysis;
import org.clas.utilities.Constants;
import org.clas.element.Cluster;
import org.clas.element.Track;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;

/**
 *
 * @author Tongtong Cao
 */
public class CheckDAFWeight extends BaseAnalysis{ 
    final static double PURITYCUT = 0.8;   
    
    public CheckDAFWeight(){}
    
    @Override
    public void createHistoGroupMap(){
        
        HistoGroup histoGroupDAFWeight = new HistoGroup("DAFWeight", 2, 2);       
        H1F h1_dafWeightNormalSeedStripCluster = new H1F("dafWeightNormalSeedStripCluster", "DAF weight for clusters with noraml seed strip", 30, 0, 1.01);
        h1_dafWeightNormalSeedStripCluster.setTitleX("DAF weight");
        h1_dafWeightNormalSeedStripCluster.setTitleY("counts");
        histoGroupDAFWeight.addDataSet(h1_dafWeightNormalSeedStripCluster, 0);        
        H1F h1_dafWeightNoiseSeedStripCluster = new H1F("dafWeightNoiseSeedStripCluster", "DAF weight for clusters with noise seed strip", 30, 0, 1.01);
        h1_dafWeightNoiseSeedStripCluster.setTitleX("DAF weight");
        h1_dafWeightNoiseSeedStripCluster.setTitleY("counts");
        histoGroupDAFWeight.addDataSet(h1_dafWeightNoiseSeedStripCluster, 1); 
        H2F h2_dafWeightVsPurityNormalSeedStripCluster = new H2F("dafWeightVsPurityNormalSeedStripCluster", "DAF weight vs purity for clusters with noraml seed strip", 30, 0, 1.01, 30, 0, 1.01);
        h2_dafWeightVsPurityNormalSeedStripCluster.setTitleX("purity");
        h2_dafWeightVsPurityNormalSeedStripCluster.setTitleY("DAF weight");
        histoGroupDAFWeight.addDataSet(h2_dafWeightVsPurityNormalSeedStripCluster, 2); 
        H2F h2_dafWeightVsPurityNoiseSeedStripCluster = new H2F("dafWeightVsPurityNoiseSeedStripCluster", "DAF weight vs purity for clusters with noise seed strip", 30, 0, 1.01, 30, 0, 1.01);
        h2_dafWeightVsPurityNoiseSeedStripCluster.setTitleX("purity");
        h2_dafWeightVsPurityNoiseSeedStripCluster.setTitleY("DAF weight");
        histoGroupDAFWeight.addDataSet(h2_dafWeightVsPurityNoiseSeedStripCluster, 3);                 
        histoGroupMap.put(histoGroupDAFWeight.getName(), histoGroupDAFWeight);    
        
        HistoGroup histoGroupDAFWeightBST = new HistoGroup("DAFWeightBST", 2, 2);       
        H1F h1_dafWeightNormalSeedStripClusterBST = new H1F("dafWeightNormalSeedStripClusterBST", "DAF weight for clusters with noraml seed strip BST", 30, 0, 1.01);
        h1_dafWeightNormalSeedStripClusterBST.setTitleX("DAF weight");
        h1_dafWeightNormalSeedStripClusterBST.setTitleY("counts");
        histoGroupDAFWeightBST.addDataSet(h1_dafWeightNormalSeedStripClusterBST, 0);        
        H1F h1_dafWeightNoiseSeedStripClusterBST = new H1F("dafWeightNoiseSeedStripClusterBST", "DAF weight for clusters with noise seed strip BST", 30, 0, 1.01);
        h1_dafWeightNoiseSeedStripClusterBST.setTitleX("DAF weight");
        h1_dafWeightNoiseSeedStripClusterBST.setTitleY("counts");
        histoGroupDAFWeightBST.addDataSet(h1_dafWeightNoiseSeedStripClusterBST, 1);
        H2F h2_dafWeightVsPurityNormalSeedStripClusterBST = new H2F("dafWeightVsPurityNormalSeedStripClusterBST", "DAF weight vs purity BST for clusters with noraml seed strip BST", 30, 0, 1.01, 30, 0, 1.01);
        h2_dafWeightVsPurityNormalSeedStripClusterBST.setTitleX("purity");
        h2_dafWeightVsPurityNormalSeedStripClusterBST.setTitleY("DAF weight");
        histoGroupDAFWeightBST.addDataSet(h2_dafWeightVsPurityNormalSeedStripClusterBST, 2);
        H2F h2_dafWeightVsPurityNoiseSeedStripClusterBST = new H2F("dafWeightVsPurityNoiseSeedStripClusterBST", "DAF weight vs purity BST for clusters with noise seed strip BST", 30, 0, 1.01, 30, 0, 1.01);
        h2_dafWeightVsPurityNoiseSeedStripClusterBST.setTitleX("purity");
        h2_dafWeightVsPurityNoiseSeedStripClusterBST.setTitleY("DAF weight");
        histoGroupDAFWeightBST.addDataSet(h2_dafWeightVsPurityNoiseSeedStripClusterBST, 3);         
        histoGroupMap.put(histoGroupDAFWeightBST.getName(), histoGroupDAFWeightBST);  
        
        HistoGroup histoGroupDAFWeightBMT = new HistoGroup("DAFWeightBMT", 2, 2);       
        H1F h1_dafWeightNormalSeedStripClusterBMT = new H1F("dafWeightNormalSeedStripClusterBMT", "DAF weight for clusters with noraml seed strip BMT", 30, 0, 1.01);
        h1_dafWeightNormalSeedStripClusterBMT.setTitleX("DAF weight");
        h1_dafWeightNormalSeedStripClusterBMT.setTitleY("counts");
        histoGroupDAFWeightBMT.addDataSet(h1_dafWeightNormalSeedStripClusterBMT, 0);        
        H1F h1_dafWeightNoiseSeedStripClusterBMT = new H1F("dafWeightNoiseSeedStripClusterBMT", "DAF weight for clusters with noise seed strip BMT", 30, 0, 1.01);
        h1_dafWeightNoiseSeedStripClusterBMT.setTitleX("DAF weight");
        h1_dafWeightNoiseSeedStripClusterBMT.setTitleY("counts");
        histoGroupDAFWeightBMT.addDataSet(h1_dafWeightNoiseSeedStripClusterBMT, 1);
        H2F h2_dafWeightVsPurityNormalSeedStripClusterBMT = new H2F("dafWeightVsPurityNormalSeedStripClusterBMT", "DAF weight vs purity BMT for clusters with noraml seed strip BMT", 30, 0, 1.01, 30, 0, 1.01);
        h2_dafWeightVsPurityNormalSeedStripClusterBMT.setTitleX("purity");
        h2_dafWeightVsPurityNormalSeedStripClusterBMT.setTitleY("DAF weight");
        histoGroupDAFWeightBMT.addDataSet(h2_dafWeightVsPurityNormalSeedStripClusterBMT, 2);
        H2F h2_dafWeightVsPurityNoiseSeedStripClusterBMT = new H2F("dafWeightVsPurityNoiseSeedStripClusterBMT", "DAF weight vs purity BMT for clusters with noise seed strip BMT", 30, 0, 1.01, 30, 0, 1.01);
        h2_dafWeightVsPurityNoiseSeedStripClusterBMT.setTitleX("purity");
        h2_dafWeightVsPurityNoiseSeedStripClusterBMT.setTitleY("DAF weight");
        histoGroupDAFWeightBMT.addDataSet(h2_dafWeightVsPurityNoiseSeedStripClusterBMT, 3);         
        histoGroupMap.put(histoGroupDAFWeightBMT.getName(), histoGroupDAFWeightBMT);         
        
    }
             
    public void processEvent(Event event){        
        //Read banks
         //////// Read banks
        LocalEvent localEvent = new LocalEvent(reader, event);
        List<Track> tracks = localEvent.getTracks(Constants.PASS);
        
        HistoGroup histoGroupDAFWeight = histoGroupMap.get("DAFWeight"); 
        HistoGroup histoGroupDAFWeightBST = histoGroupMap.get("DAFWeightBST");
        HistoGroup histoGroupDAFWeightBMT = histoGroupMap.get("DAFWeightBMT");
        for(Track trk : tracks){            
            if(trk.getRatioNormalHits() > PURITYCUT) {
                for(Cluster cls : trk.getClusters()){                    
                    if(cls.isNormalSeedStrip()) {
                        histoGroupDAFWeight.getH1F("dafWeightNormalSeedStripCluster").fill(cls.getDAFWeight());
                        histoGroupDAFWeight.getH2F("dafWeightVsPurityNormalSeedStripCluster").fill((double)cls.getRatioNormalHits(), cls.getDAFWeight());
                    }
                    else {
                        histoGroupDAFWeight.getH1F("dafWeightNoiseSeedStripCluster").fill(cls.getDAFWeight());
                        histoGroupDAFWeight.getH2F("dafWeightVsPurityNoiseSeedStripCluster").fill((double)cls.getRatioNormalHits(), cls.getDAFWeight());
                    }
                }
                
                for(Cluster cls : trk.getBSTClusters()){                    
                    if(cls.isNormalSeedStrip()) {
                        histoGroupDAFWeightBST.getH1F("dafWeightNormalSeedStripClusterBST").fill(cls.getDAFWeight());
                        histoGroupDAFWeightBST.getH2F("dafWeightVsPurityNormalSeedStripClusterBST").fill((double)cls.getRatioNormalHits(), cls.getDAFWeight());
                    }
                    else {
                        histoGroupDAFWeightBST.getH1F("dafWeightNoiseSeedStripClusterBST").fill(cls.getDAFWeight());
                        histoGroupDAFWeightBST.getH2F("dafWeightVsPurityNoiseSeedStripClusterBST").fill((double)cls.getRatioNormalHits(), cls.getDAFWeight());
                    }
                }
                
                for(Cluster cls : trk.getBMTClusters()){                    
                    if(cls.isNormalSeedStrip()) {
                        histoGroupDAFWeightBMT.getH1F("dafWeightNormalSeedStripClusterBMT").fill(cls.getDAFWeight());
                        histoGroupDAFWeightBMT.getH2F("dafWeightVsPurityNormalSeedStripClusterBMT").fill((double)cls.getRatioNormalHits(), cls.getDAFWeight());
                    }
                    else {
                        histoGroupDAFWeightBMT.getH1F("dafWeightNoiseSeedStripClusterBMT").fill(cls.getDAFWeight());
                        histoGroupDAFWeightBMT.getH2F("dafWeightVsPurityNoiseSeedStripClusterBMT").fill((double)cls.getRatioNormalHits(), cls.getDAFWeight());
                    }
                }               
            }
        }
        
        
    }

    
    public void postEventProcess() {
                      
    }            
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("SeedRecon");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o"          ,"",     "output file name prefix");
        parser.addOption("-n"          ,"-1",   "maximum number of events to process");        
        parser.addOption("-plot"       ,"1",    "display histograms (0/1)");
        parser.addOption("-bg", "1", "if bg (0/1)");
        parser.addOption("-pass", "1", "if bg (1/2)");
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
        
        CheckDAFWeight analysis = new CheckDAFWeight();
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
                frame.setSize(1000, 1000);
                frame.add(canvas);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }        
    }
    
}
