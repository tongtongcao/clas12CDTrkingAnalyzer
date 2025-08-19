package org.clas.analysis.displayRecon;

import java.util.List;
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
import org.clas.element.Cross;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;

/**
 *
 * @author Tongtong Cao
 */
public class CrossRecon extends BaseAnalysis{ 
    
    public CrossRecon(){}
    
    @Override
    public void createHistoGroupMap(){
        HistoGroup histoGroupBSTSector = new HistoGroup("sector for BST Cross", 3, 2);       
        HistoGroup histoGroupBSTXY = new HistoGroup("xy for BST Cross", 3, 2);
        HistoGroup histoGroupBSTZ = new HistoGroup("z for BST Cross", 3, 2);
        HistoGroup histoGroupBSTX0Y0 = new HistoGroup("x0y0 for BST Cross", 3, 2);
        HistoGroup histoGroupBSTZ0 = new HistoGroup("z0 for BST Cross", 3, 2);        
        for (int i = 0; i < 3; i++) {
            H1F h1_sector = new H1F("BST sector for R" + Integer.toString(i + 1),
                    "BST sector for R" + Integer.toString(i + 1), 18, 0.5, 18.5);
            h1_sector.setTitleX("sector");
            h1_sector.setTitleY("Counts");
            h1_sector.setLineColor(1);
            histoGroupBSTSector.addDataSet(h1_sector, i);
            
            H2F h2_xy = new H2F("BST xy for R" + Integer.toString(i + 1), 
                    "BST xy for R" + Integer.toString(i + 1), 100, -25, 25, 100, -25, 25);
            h2_xy.setTitleX("x (cm)");
            h2_xy.setTitleY("y (cm)");
            histoGroupBSTXY.addDataSet(h2_xy, i); 
            
            H1F h1_z = new H1F("BST z for R" + Integer.toString(i + 1), 
                    "BST z for R" + Integer.toString(i + 1), 100, -30, 30);
            h1_z.setTitleX("z (cm)");
            h1_z.setTitleY("Counts");
            h1_z.setLineColor(1);
            histoGroupBSTZ.addDataSet(h1_z, i); 
            
            H2F h2_x0y0 = new H2F("BST x0y0 for R" + Integer.toString(i + 1), 
                    "BST x0y0 for R" + Integer.toString(i + 1), 100, -25, 25, 100, -25, 25);
            h2_x0y0.setTitleX("x0 (cm)");
            h2_x0y0.setTitleY("y0 (cm)");
            histoGroupBSTX0Y0.addDataSet(h2_x0y0, i); 
            
            H1F h1_z0 = new H1F("BST z0 for R" + Integer.toString(i + 1), 
                    "BST z0 for R" + Integer.toString(i + 1), 100, -30, 30);
            h1_z0.setTitleX("z (cm)");
            h1_z0.setTitleY("Counts");
            h1_z0.setLineColor(1);
            histoGroupBSTZ0.addDataSet(h1_z0, i);             
        }
        histoGroupMap.put(histoGroupBSTSector.getName(), histoGroupBSTSector);
        histoGroupMap.put(histoGroupBSTXY.getName(), histoGroupBSTXY); 
        histoGroupMap.put(histoGroupBSTZ.getName(), histoGroupBSTZ);
        histoGroupMap.put(histoGroupBSTX0Y0.getName(), histoGroupBSTX0Y0); 
        histoGroupMap.put(histoGroupBSTZ0.getName(), histoGroupBSTZ0);        
        
        if(Constants.BG){
            HistoGroup histoGroupBSTNumNormalBgHits = new HistoGroup("nums of normal and bg hits for BST Cross", 3, 2);
            HistoGroup histoGroupBSTRatioNormalHits = new HistoGroup("ratio of normal hits for BST Cross", 3, 2);            
            HistoGroup histoGroupBSTCategory = new HistoGroup("category for BST Cross", 3, 2);
            HistoGroup histoGroupBSTSeedStrips = new HistoGroup("seedStrips for BST Cross", 3, 2);
            HistoGroup histoGroupBSTRatiosNormalHits = new HistoGroup("ratios of normal hits for BST Cross", 3, 2);
            for (int i = 0; i < 3; i++) {
                H2F h2_numNormalBgHits = new H2F("normal vs bg hits for R" + Integer.toString(i + 1), 
                        "normal vs bg hits for R" + Integer.toString(i + 1), 31, -0.5, 30.5, 31, -0.5, 30.5);
                h2_numNormalBgHits.setTitleX("# of normal hits");
                h2_numNormalBgHits.setTitleY("# of bg hits");
                histoGroupBSTNumNormalBgHits.addDataSet(h2_numNormalBgHits, i); 
                
                H1F h1_ratioNormalHits = new H1F("ratio of normal hits for R" + Integer.toString(i + 1), 
                        "ratio of normal hits for R" + Integer.toString(i + 1), 30, 0, 1.01);
                h1_ratioNormalHits.setTitleX("ratio of normal hits");
                h1_ratioNormalHits.setTitleY("Counts");
                h1_ratioNormalHits.setLineColor(1);
                histoGroupBSTRatioNormalHits.addDataSet(h1_ratioNormalHits, i);
                
                H1F h1_category = new H1F("category for R" + Integer.toString(i + 1), 
                        "category for R" + Integer.toString(i + 1), 4, 0.5, 4.5);
                h1_category.setTitleX("category");
                h1_category.setTitleY("Counts");
                h1_category.setLineColor(1);
                histoGroupBSTCategory.addDataSet(h1_category, i); 

                H2F h2_seedStrips = new H2F("if noraml seed strips for R" + Integer.toString(i + 1), 
                        "if noraml seed strips for R" + Integer.toString(i + 1), 2, -0.5, 1.5, 2, -0.5, 1.5);
                h2_seedStrips.setTitleX("if noraml seed strip for cls1");
                h2_seedStrips.setTitleY("if noraml seed strip for cls2");
                histoGroupBSTSeedStrips.addDataSet(h2_seedStrips, i);
                
                H2F h2_ratiosNormalHits = new H2F("ratios of normal hits for R" + Integer.toString(i + 1), 
                        "ratios of normal hits for R" + Integer.toString(i + 1), 30, 0, 1.01, 30, 0, 1.01);
                h2_ratiosNormalHits.setTitleX("ratio of normal hits for cls1");
                h2_ratiosNormalHits.setTitleY("ratio of normal hits for cls2");
                histoGroupBSTRatiosNormalHits.addDataSet(h2_ratiosNormalHits, i); 
            }
            histoGroupMap.put(histoGroupBSTNumNormalBgHits.getName(), histoGroupBSTNumNormalBgHits);
            histoGroupMap.put(histoGroupBSTRatioNormalHits.getName(), histoGroupBSTRatioNormalHits);
            histoGroupMap.put(histoGroupBSTCategory.getName(), histoGroupBSTCategory);
            histoGroupMap.put(histoGroupBSTSeedStrips.getName(), histoGroupBSTSeedStrips);
            histoGroupMap.put(histoGroupBSTRatiosNormalHits.getName(), histoGroupBSTRatiosNormalHits);
        }
        

        HistoGroup histoGroupBMTSector = new HistoGroup("sector for BMT Cross", 3, 2);
        HistoGroup histoGroupBMTXY = new HistoGroup("xy for BMT Cross", 3, 2);
        HistoGroup histoGroupBMTZ = new HistoGroup("z for BMT Cross", 3, 2);
        HistoGroup histoGroupBMTX0Y0 = new HistoGroup("x0y0 for BMT Cross", 3, 2);
        HistoGroup histoGroupBMTZ0 = new HistoGroup("z0 for BMT Cross", 3, 2);          
        for (int i = 0; i < 6; i++) {
            H1F h1_sector = new H1F("BMT sector for L" + Integer.toString(i + 1),
                    "BMT sector for L" + Integer.toString(i + 1), 3, 0.5, 3.5);
            h1_sector.setTitleX("sector");
            h1_sector.setTitleY("Counts");
            h1_sector.setLineColor(1);
            histoGroupBMTSector.addDataSet(h1_sector, i);      

            H2F h2_xy = new H2F("BMT xy for L" + Integer.toString(i + 1), 
                    "BMT xy for L" + Integer.toString(i + 1), 100, -25, 25, 100, -25, 25);
            h2_xy.setTitleX("x (cm)");
            h2_xy.setTitleY("y (cm)");
            histoGroupBMTXY.addDataSet(h2_xy, i); 
            
            H1F h1_z = new H1F("BMT z for L" + Integer.toString(i + 1), 
                    "BMT z for L" + Integer.toString(i + 1), 100, -30, 30);
            h1_z.setTitleX("z (cm)");
            h1_z.setTitleY("Counts");
            h1_z.setLineColor(1);
            histoGroupBMTZ.addDataSet(h1_z, i); 

            H2F h2_x0y0 = new H2F("BMT x0y0 for L" + Integer.toString(i + 1), 
                    "BMT x0y0 for L" + Integer.toString(i + 1), 100, -25, 25, 100, -25, 25);
            h2_x0y0.setTitleX("x0 (cm)");
            h2_x0y0.setTitleY("y0 (cm)");
            histoGroupBMTX0Y0.addDataSet(h2_x0y0, i); 
            
            H1F h1_z0 = new H1F("BMT z0 for L" + Integer.toString(i + 1), 
                    "BMT z0 for L" + Integer.toString(i + 1), 100, -30, 30);
            h1_z0.setTitleX("z (cm)");
            h1_z0.setTitleY("Counts");
            h1_z0.setLineColor(1);
            histoGroupBMTZ0.addDataSet(h1_z0, i);             
        }
        histoGroupMap.put(histoGroupBMTSector.getName(), histoGroupBMTSector); 
        histoGroupMap.put(histoGroupBMTXY.getName(), histoGroupBMTXY); 
        histoGroupMap.put(histoGroupBMTZ.getName(), histoGroupBMTZ);
        histoGroupMap.put(histoGroupBMTX0Y0.getName(), histoGroupBMTX0Y0); 
        histoGroupMap.put(histoGroupBMTZ0.getName(), histoGroupBMTZ0);         
        
        if(Constants.BG){
            HistoGroup histoGroupBMTNumNormalBgHits = new HistoGroup("nums of normal and bg hits for BMT Cross", 3, 2);
            HistoGroup histoGroupBMTRatioNormalHits = new HistoGroup("ratio of normal hits for BMT Cross", 3, 2);
            HistoGroup histoGroupBMTCategory = new HistoGroup("category for BMT Cross", 3, 2);
            for (int i = 0; i < 6; i++) {
                H2F h2_numNormalBgHits = new H2F("normal vs bg hits for L" + Integer.toString(i + 1), 
                        "normal vs bg hits for L" + Integer.toString(i + 1), 41, -0.5, 40.5, 41, -0.5, 40.5);
                h2_numNormalBgHits.setTitleX("# of normal hits");
                h2_numNormalBgHits.setTitleY("# of bg hits");
                histoGroupBMTNumNormalBgHits.addDataSet(h2_numNormalBgHits, i); 
                
                H1F h1_ratioNormalHits = new H1F("ratio of normal hits for L" + Integer.toString(i + 1), 
                        "ratio of normal hits for L" + Integer.toString(i + 1), 30, 0, 1.01);
                h1_ratioNormalHits.setTitleX("ratio of normal hits");
                h1_ratioNormalHits.setTitleY("Counts");
                h1_ratioNormalHits.setLineColor(1);
                histoGroupBMTRatioNormalHits.addDataSet(h1_ratioNormalHits, i);
                
                H1F h1_category = new H1F("category for L" + Integer.toString(i + 1), 
                        "category for L" + Integer.toString(i + 1), 4, 0.5, 4.5);
                h1_category.setTitleX("category");
                h1_category.setTitleY("Counts");
                h1_category.setLineColor(1);
                histoGroupBMTCategory.addDataSet(h1_category, i);                 
            }
            histoGroupMap.put(histoGroupBMTNumNormalBgHits.getName(), histoGroupBMTNumNormalBgHits);
            histoGroupMap.put(histoGroupBMTRatioNormalHits.getName(), histoGroupBMTRatioNormalHits);
            histoGroupMap.put(histoGroupBMTCategory.getName(), histoGroupBMTCategory);
        }
        
        
    }
             
    public void processEvent(Event event){        
        //Read banks
         //////// Read banks
        LocalEvent localEvent = new LocalEvent(reader, event); 
        processEvent(localEvent);
    }
    
    public void processEvent(LocalEvent localEvent){
        List<Cross> bstCrosses = localEvent.getBSTCrosses(Constants.PASS);
        List<Cross> bmtCrosses = localEvent.getBMTCrosses(Constants.PASS);
        
        HistoGroup histoGroupBSTSector = histoGroupMap.get("sector for BST Cross");
        HistoGroup histoGroupBSTXY = histoGroupMap.get("xy for BST Cross");
        HistoGroup histoGroupBSTZ = histoGroupMap.get("z for BST Cross");
        HistoGroup histoGroupBSTX0Y0 = histoGroupMap.get("x0y0 for BST Cross");
        HistoGroup histoGroupBSTZ0 = histoGroupMap.get("z0 for BST Cross");        
        for(Cross crs : bstCrosses){
            histoGroupBSTSector.getH1F("BST sector for R" + Integer.toString(crs.region())).fill(crs.sector());
            histoGroupBSTXY.getH2F("BST xy for R" + Integer.toString(crs.region())).fill(crs.point().x(), crs.point().y());
            histoGroupBSTZ.getH1F("BST z for R" + Integer.toString(crs.region())).fill(crs.point().z());
            histoGroupBSTX0Y0.getH2F("BST x0y0 for R" + Integer.toString(crs.region())).fill(crs.pointOrigin().x(), crs.pointOrigin().y());
            histoGroupBSTZ0.getH1F("BST z0 for R" + Integer.toString(crs.region())).fill(crs.pointOrigin().z());            
        }
        
        if(Constants.BG){
            HistoGroup histoGroupBSTNumNormalBgHits = histoGroupMap.get("nums of normal and bg hits for BST Cross");
            HistoGroup histoGroupBSTRatioNormalHits = histoGroupMap.get("ratio of normal hits for BST Cross");
            HistoGroup histoGroupBSTCategory = histoGroupMap.get("category for BST Cross");   
            HistoGroup histoGroupBSTSeedStrips = histoGroupMap.get("seedStrips for BST Cross"); 
            HistoGroup histoGroupBSTRatiosNormalHits = histoGroupMap.get("ratios of normal hits for BST Cross");             
            for(Cross crs : bstCrosses){
                histoGroupBSTNumNormalBgHits.getH2F("normal vs bg hits for R" + Integer.toString(crs.region())).fill(crs.getNumNormalHits(), crs.getNumBgHits());
                histoGroupBSTRatioNormalHits.getH1F("ratio of normal hits for R" + Integer.toString(crs.region())).fill(crs.getRatioNormalHits()); 
                histoGroupBSTCategory.getH1F("category for R" + Integer.toString(crs.region())).fill(crs.getCategory());                 
                histoGroupBSTSeedStrips.getH2F("if noraml seed strips for R" + Integer.toString(crs.region())).fill(crs.getCluster1().isNormalSeedStrip()?1:0, crs.getCluster2().isNormalSeedStrip()?1:0);
                histoGroupBSTRatiosNormalHits.getH2F("ratios of normal hits for R" + Integer.toString(crs.region())).fill(crs.getCluster1().getRatioNormalHits(), crs.getCluster2().getRatioNormalHits());
            }
        }
        
        HistoGroup histoGroupBMTSector = histoGroupMap.get("sector for BMT Cross");
        HistoGroup histoGroupBMTXY = histoGroupMap.get("xy for BMT Cross");
        HistoGroup histoGroupBMTZ = histoGroupMap.get("z for BMT Cross");
        HistoGroup histoGroupBMTX0Y0 = histoGroupMap.get("x0y0 for BMT Cross");
        HistoGroup histoGroupBMTZ0 = histoGroupMap.get("z0 for BMT Cross");          
        for(Cross crs : bmtCrosses){
            histoGroupBMTSector.getH1F("BMT sector for L" + Integer.toString(crs.layer())).fill(crs.sector());
            histoGroupBMTXY.getH2F("BMT xy for L" + Integer.toString(crs.layer())).fill(crs.point().x(), crs.point().y());
            histoGroupBMTZ.getH1F("BMT z for L" + Integer.toString(crs.layer())).fill(crs.point().z());
            histoGroupBMTX0Y0.getH2F("BMT x0y0 for L" + Integer.toString(crs.layer())).fill(crs.pointOrigin().x(), crs.pointOrigin().y());
            histoGroupBMTZ0.getH1F("BMT z0 for L" + Integer.toString(crs.layer())).fill(crs.pointOrigin().z());            
        }
        
        if(Constants.BG){
            HistoGroup histoGroupBMTNumNormalBgHits = histoGroupMap.get("nums of normal and bg hits for BMT Cross");
            HistoGroup histoGroupBMTRatioNormalHits = histoGroupMap.get("ratio of normal hits for BMT Cross");            
            HistoGroup histoGroupBMTCategory = histoGroupMap.get("category for BMT Cross");                        
            for(Cross crs : bmtCrosses){   
                histoGroupBMTNumNormalBgHits.getH2F("normal vs bg hits for L" + Integer.toString(crs.layer())).fill(crs.getNumNormalHits(), crs.getNumBgHits());
                histoGroupBMTRatioNormalHits.getH1F("ratio of normal hits for L" + Integer.toString(crs.layer())).fill(crs.getRatioNormalHits());
                histoGroupBMTCategory.getH1F("category for L" + Integer.toString(crs.layer())).fill(crs.getCategory());                      
            }
        }        
         

    }
    
    public void postEventProcess() {

                      
    }            
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("CrossRecon");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o"          ,"",     "output file name prefix");
        parser.addOption("-n"          ,"-1",   "maximum number of events to process");        
        parser.addOption("-plot"       ,"1",    "display histograms (0/1)");
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
        
        CrossRecon analysis = new CrossRecon();
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
