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
import org.clas.element.Cluster;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;

/**
 *
 * @author Tongtong Cao
 */
public class ClusterRecon extends BaseAnalysis{ 
    
    public ClusterRecon(){}
    
    @Override
    public void createHistoGroupMap(){
        HistoGroup histoGroupBSTSector = new HistoGroup("sector for BST Cluster", 3, 2);
        HistoGroup histoGroupBSTSize = new HistoGroup("size for BST Cluster", 3, 2);        
        HistoGroup histoGroupBSTSeedStrip= new HistoGroup("seedStrip for BST Cluster", 3, 2);
        HistoGroup histoGroupBSTSeedE= new HistoGroup("seedE for BST Cluster", 3, 2);
        HistoGroup histoGroupBSTETot = new HistoGroup("ETot for BST Cluster", 3, 2);
        HistoGroup histoGroupBSTTime = new HistoGroup("time for BST Cluster", 3, 2);
        HistoGroup histoGroupBSTCentroid = new HistoGroup("centroid for BST Cluster", 3, 2);
        HistoGroup histoGroupBSTCentroidResidual = new HistoGroup("centroidResidual for BST Cluster", 3, 2);
        HistoGroup histoGroupBSTSeedResidual = new HistoGroup("seedResidual for BST Cluster", 3, 2);
        HistoGroup histoGroupBSTCenterXY = new HistoGroup("center xy for BST Cluster", 3, 2);
        HistoGroup histoGroupBSTCenterZ = new HistoGroup("center z for BST Cluster", 3, 2);
        for (int i = 0; i < 6; i++) {
            H1F h1_sector = new H1F("BST sector for L" + Integer.toString(i + 1),
                    "BST sector for L" + Integer.toString(i + 1), 18, 0.5, 18.5);
            h1_sector.setTitleX("sector");
            h1_sector.setTitleY("Counts");
            h1_sector.setLineColor(1);
            histoGroupBSTSector.addDataSet(h1_sector, i);
            
            H1F h1_size = new H1F("BST size for L" + Integer.toString(i + 1),
                    "BST size for L" + Integer.toString(i + 1), 10, 0.5, 10.5);
            h1_size.setTitleX("size");
            h1_size.setTitleY("Counts");
            h1_size.setLineColor(1);
            histoGroupBSTSize.addDataSet(h1_size, i);                                    
            
            H1F h1_seedStrip = new H1F("BST seedStrip for L" + Integer.toString(i + 1),
                    "BST seedStrip for L" + Integer.toString(i + 1), 256, 0.5, 256.5);
            h1_seedStrip.setTitleX("seedStrip");
            h1_seedStrip.setTitleY("Counts");
            h1_seedStrip.setLineColor(1);
            histoGroupBSTSeedStrip.addDataSet(h1_seedStrip, i);

            H1F h1_seedE = new H1F("BST seedE for L" + Integer.toString(i + 1),
                    "BST seedE for L" + Integer.toString(i + 1), 100, 0, 800);
            h1_seedE.setTitleX("seedE");
            h1_seedE.setTitleY("Counts");
            h1_seedE.setLineColor(1);
            histoGroupBSTSeedE.addDataSet(h1_seedE, i);              
            
            H1F h1_ETot = new H1F("BST ETot for L" + Integer.toString(i + 1),
                    "BST ETot for L" + Integer.toString(i + 1), 100, 0, 2000);
            h1_ETot.setTitleX("ETot");
            h1_ETot.setTitleY("Counts");
            h1_ETot.setLineColor(1);
            histoGroupBSTETot.addDataSet(h1_ETot, i);  
            
            H1F h1_time = new H1F("BST time for L" + Integer.toString(i + 1),
                    "BST time for L" + Integer.toString(i + 1), 100, 0, 550);
            h1_time.setTitleX("time");
            h1_time.setTitleY("Counts");
            h1_time.setLineColor(1);
            histoGroupBSTTime.addDataSet(h1_time, i);  
            
            H1F h1_centroid = new H1F("BST centroid for L" + Integer.toString(i + 1),
                    "BST centroid for L" + Integer.toString(i + 1), 256, 0.5, 256.5);
            h1_centroid.setTitleX("centroid");
            h1_centroid.setTitleY("Counts");
            h1_centroid.setLineColor(1);
            histoGroupBSTCentroid.addDataSet(h1_centroid, i);  
            
            H1F h1_centroidResidual = new H1F("BST centroidResidual for L" + Integer.toString(i + 1), 
                    "BST centroidResidual for L" + Integer.toString(i + 1), 100, -0.1, 0.1);
            h1_centroidResidual.setTitleX("centroidResidual");
            h1_centroidResidual.setTitleY("Counts");
            h1_centroidResidual.setLineColor(1);
            histoGroupBSTCentroidResidual.addDataSet(h1_centroidResidual, i);
            
            H1F h1_seedResidual = new H1F("BST seedResidual for L" + Integer.toString(i + 1), 
                    "BST seedResidual for L" + Integer.toString(i + 1), 100, -0.1, 0.1);
            h1_seedResidual.setTitleX("seedResidual");
            h1_seedResidual.setTitleY("Counts");
            h1_seedResidual.setLineColor(1);
            histoGroupBSTSeedResidual.addDataSet(h1_seedResidual, i); 
            
            H2F h2_centerxy = new H2F("BST center xy for L" + Integer.toString(i + 1), 
                    "BST center xy for L" + Integer.toString(i + 1), 100, -25, 25, 100, -25, 25);
            h2_centerxy.setTitleX("center x (cm)");
            h2_centerxy.setTitleY("center y (cm)");
            histoGroupBSTCenterXY.addDataSet(h2_centerxy, i); 
            
            H1F h1_centerz = new H1F("BST center z for L" + Integer.toString(i + 1), 
                    "BST center z for L" + Integer.toString(i + 1), 100, -30, 30);
            h1_centerz.setTitleX("center z (cm)");
            h1_centerz.setTitleY("Counts");
            h1_centerz.setLineColor(1);
            histoGroupBSTCenterZ.addDataSet(h1_centerz, i); 
        }
        histoGroupMap.put(histoGroupBSTSector.getName(), histoGroupBSTSector);
        histoGroupMap.put(histoGroupBSTSize.getName(), histoGroupBSTSize);               
        histoGroupMap.put(histoGroupBSTSeedStrip.getName(), histoGroupBSTSeedStrip);
        histoGroupMap.put(histoGroupBSTSeedE.getName(), histoGroupBSTSeedE); 
        histoGroupMap.put(histoGroupBSTETot.getName(), histoGroupBSTETot);
        histoGroupMap.put(histoGroupBSTTime.getName(), histoGroupBSTTime);
        histoGroupMap.put(histoGroupBSTCentroid.getName(), histoGroupBSTCentroid);
        histoGroupMap.put(histoGroupBSTCentroidResidual.getName(), histoGroupBSTCentroidResidual);
        histoGroupMap.put(histoGroupBSTSeedResidual.getName(), histoGroupBSTSeedResidual); 
        histoGroupMap.put(histoGroupBSTCenterXY.getName(), histoGroupBSTCenterXY); 
        histoGroupMap.put(histoGroupBSTCenterZ.getName(), histoGroupBSTCenterZ);
        
        if(Constants.BG){
            HistoGroup histoGroupBSTNumNormalBgHits = new HistoGroup("nums of normal and bg hits for BST Cluster", 3, 2);
            HistoGroup histoGroupBSTRatioNormalHits = new HistoGroup("ratio of normal hits for BST Cluster", 3, 2);
            HistoGroup histoGroupBSTCategory = new HistoGroup("category for BST Cluster", 3, 2);
            for (int i = 0; i < 6; i++) {
                H2F h2_numNormalBgHits = new H2F("normal vs bg hits for L" + Integer.toString(i + 1), 
                        "normal vs bg hits for L" + Integer.toString(i + 1), 16, -0.5, 15.5, 16, -0.5, 15.5);
                h2_numNormalBgHits.setTitleX("# of normal hits");
                h2_numNormalBgHits.setTitleY("# of bg hits");
                histoGroupBSTNumNormalBgHits.addDataSet(h2_numNormalBgHits, i); 
                
                H1F h1_ratioNormalHits = new H1F("ratio of normal hits for L" + Integer.toString(i + 1), 
                        "ratio of normal hits for L" + Integer.toString(i + 1), 30, 0, 1.01);
                h1_ratioNormalHits.setTitleX("ratio of normal hits");
                h1_ratioNormalHits.setTitleY("Counts");
                h1_ratioNormalHits.setLineColor(1);
                histoGroupBSTRatioNormalHits.addDataSet(h1_ratioNormalHits, i);

                H1F h1_category = new H1F("category for L" + Integer.toString(i + 1), 
                        "category for L" + Integer.toString(i + 1), 4, 0.5, 4.5);
                h1_category.setTitleX("category");
                h1_category.setTitleY("Counts");
                h1_category.setLineColor(1);
                histoGroupBSTCategory.addDataSet(h1_category, i);                 
            }
            
            histoGroupMap.put(histoGroupBSTNumNormalBgHits.getName(), histoGroupBSTNumNormalBgHits);
            histoGroupMap.put(histoGroupBSTRatioNormalHits.getName(), histoGroupBSTRatioNormalHits);
            histoGroupMap.put(histoGroupBSTCategory.getName(), histoGroupBSTCategory);
        }
        
        HistoGroup histoGroupBMTSector = new HistoGroup("sector for BMT Cluster", 3, 2);
        HistoGroup histoGroupBMTSize = new HistoGroup("size for BMT Cluster", 3, 2);
        HistoGroup histoGroupBMTSeedStrip= new HistoGroup("seedStrip for BMT Cluster", 3, 2);
        HistoGroup histoGroupBMTSeedE= new HistoGroup("seedE for BMT Cluster", 3, 2);        
        HistoGroup histoGroupBMTETot = new HistoGroup("ETot for BMT Cluster", 3, 2);
        HistoGroup histoGroupBMTTime = new HistoGroup("time for BMT Cluster", 3, 2);
        HistoGroup histoGroupBMTCentroid = new HistoGroup("centroid for BMT Cluster", 3, 2);
        HistoGroup histoGroupBMTCentroidResidual = new HistoGroup("centroidResidual for BMT Cluster", 3, 2);
        HistoGroup histoGroupBMTSeedResidual = new HistoGroup("seedResidual for BMT Cluster", 3, 2);
        HistoGroup histoGroupBMTCenterXY = new HistoGroup("center xy for BMT Cluster", 3, 2);
        HistoGroup histoGroupBMTCenterZ = new HistoGroup("center z for BMT Cluster", 3, 2);
        for (int i = 0; i < 6; i++) {
            H1F h1_sector = new H1F("BMT sector for L" + Integer.toString(i + 1),
                    "BMT sector for L" + Integer.toString(i + 1), 3, 0.5, 3.5);
            h1_sector.setTitleX("sector");
            h1_sector.setTitleY("Counts");
            h1_sector.setLineColor(1);
            histoGroupBMTSector.addDataSet(h1_sector, i);
            
            H1F h1_size = new H1F("BMT size for L" + Integer.toString(i + 1),
                    "BMT size for L" + Integer.toString(i + 1), 20, 0.5, 20.5);
            h1_size.setTitleX("size");
            h1_size.setTitleY("Counts");
            h1_size.setLineColor(1);
            histoGroupBMTSize.addDataSet(h1_size, i);                       
            
            H1F h1_seedStrip = new H1F("BMT seedStrip for L" + Integer.toString(i + 1),
                    "BMT seedStrip for L" + Integer.toString(i + 1), 1200, 0.5, 1200.5);
            h1_seedStrip.setTitleX("seedStrip");
            h1_seedStrip.setTitleY("Counts");
            h1_seedStrip.setLineColor(1);
            histoGroupBMTSeedStrip.addDataSet(h1_seedStrip, i);  
            
            H1F h1_seedE = new H1F("BMT seedE for L" + Integer.toString(i + 1),
                    "BMT seedE for L" + Integer.toString(i + 1), 100, 0, 2000);
            h1_seedE.setTitleX("seedE");
            h1_seedE.setTitleY("Counts");
            h1_seedE.setLineColor(1);
            histoGroupBMTSeedE.addDataSet(h1_seedE, i);              
            
            H1F h1_ETot = new H1F("BMT ETot for L" + Integer.toString(i + 1),
                    "BMT ETot for L" + Integer.toString(i + 1), 100, 0, 4000);
            h1_ETot.setTitleX("ETot");
            h1_ETot.setTitleY("Counts");
            h1_ETot.setLineColor(1);
            histoGroupBMTETot.addDataSet(h1_ETot, i);  
            
            H1F h1_time = new H1F("BMT time for L" + Integer.toString(i + 1),
                    "BMT time for L" + Integer.toString(i + 1), 100, 0, 450);
            h1_time.setTitleX("time");
            h1_time.setTitleY("Counts");
            h1_time.setLineColor(1);
            histoGroupBMTTime.addDataSet(h1_time, i);  
            
            H1F h1_centroid = new H1F("BMT centroid for L" + Integer.toString(i + 1),
                    "BMT centroid for L" + Integer.toString(i + 1), 1200, 0.5, 1200.5);
            h1_centroid.setTitleX("centroid");
            h1_centroid.setTitleY("Counts");
            h1_centroid.setLineColor(1);
            histoGroupBMTCentroid.addDataSet(h1_centroid, i);  
            
            H1F h1_centroidResidual = new H1F("BMT centroidResidual for L" + Integer.toString(i + 1), 
                    "BMT centroidResidual for L" + Integer.toString(i + 1), 100, -0.1, 0.1);
            h1_centroidResidual.setTitleX("centroidResidual");
            h1_centroidResidual.setTitleY("Counts");
            h1_centroidResidual.setLineColor(1);
            histoGroupBMTCentroidResidual.addDataSet(h1_centroidResidual, i);
            
            H1F h1_seedResidual = new H1F("BMT seedResidual for L" + Integer.toString(i + 1), 
                    "BMT seedResidual for L" + Integer.toString(i + 1), 100, -0.1, 0.1);
            h1_seedResidual.setTitleX("seedResidual");
            h1_seedResidual.setTitleY("Counts");
            h1_seedResidual.setLineColor(1);
            histoGroupBMTSeedResidual.addDataSet(h1_seedResidual, i); 

            H2F h2_centerxy = new H2F("BMT center xy for L" + Integer.toString(i + 1), 
                    "BMT center xy for L" + Integer.toString(i + 1), 100, -25, 25, 100, -25, 25);
            h2_centerxy.setTitleX("center x (cm)");
            h2_centerxy.setTitleY("center y (cm)");
            histoGroupBMTCenterXY.addDataSet(h2_centerxy, i); 
            
            H1F h1_centerz = new H1F("BMT center z for L" + Integer.toString(i + 1), 
                    "BMT center z for L" + Integer.toString(i + 1), 100, -30, 30);
            h1_centerz.setTitleX("center z (cm)");
            h1_centerz.setTitleY("Counts");
            h1_centerz.setLineColor(1);
            histoGroupBMTCenterZ.addDataSet(h1_centerz, i);             
        }
        histoGroupMap.put(histoGroupBMTSector.getName(), histoGroupBMTSector);
        histoGroupMap.put(histoGroupBMTSize.getName(), histoGroupBMTSize);
        histoGroupMap.put(histoGroupBMTSeedStrip.getName(), histoGroupBMTSeedStrip);
        histoGroupMap.put(histoGroupBMTSeedE.getName(), histoGroupBMTSeedE);
        histoGroupMap.put(histoGroupBMTETot.getName(), histoGroupBMTETot);
        histoGroupMap.put(histoGroupBMTTime.getName(), histoGroupBMTTime);
        histoGroupMap.put(histoGroupBMTCentroid.getName(), histoGroupBMTCentroid);
        histoGroupMap.put(histoGroupBMTCentroidResidual.getName(), histoGroupBMTCentroidResidual);
        histoGroupMap.put(histoGroupBMTSeedResidual.getName(), histoGroupBMTSeedResidual); 
        histoGroupMap.put(histoGroupBMTCenterXY.getName(), histoGroupBMTCenterXY); 
        histoGroupMap.put(histoGroupBMTCenterZ.getName(), histoGroupBMTCenterZ); 
        
        if(Constants.BG){
            HistoGroup histoGroupBMTNumNormalBgHits = new HistoGroup("nums of normal and bg hits for BMT Cluster", 3, 2);
            HistoGroup histoGroupBMTRatioNormalHits = new HistoGroup("ratio of normal hits for BMT Cluster", 3, 2);
            HistoGroup histoGroupBMTCategory = new HistoGroup("category for BMT Cluster", 3, 2);
            for (int i = 0; i < 6; i++) {
                H2F h2_numNormalBgHits = new H2F("normal vs bg hits for L" + Integer.toString(i + 1), 
                        "normal vs bg hits for L" + Integer.toString(i + 1), 21, -0.5, 20.5, 21, -0.5, 20.5);
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
        List<Cluster> bstClusters = localEvent.getBSTClusters(Constants.PASS);
        List<Cluster> bmtClusters = localEvent.getBMTClusters(Constants.PASS);
        
        HistoGroup histoGroupBSTSector = histoGroupMap.get("sector for BST Cluster");
        HistoGroup histoGroupBSTSize = histoGroupMap.get("size for BST Cluster");
        HistoGroup histoGroupBSTSeedStrip = histoGroupMap.get("seedStrip for BST Cluster");
        HistoGroup histoGroupBSTSeedE = histoGroupMap.get("seedE for BST Cluster");
        HistoGroup histoGroupBSTETot = histoGroupMap.get("ETot for BST Cluster");
        HistoGroup histoGroupBSTTime = histoGroupMap.get("time for BST Cluster");
        HistoGroup histoGroupBSTCentroid = histoGroupMap.get("centroid for BST Cluster");
        HistoGroup histoGroupBSTCentroidResidual = histoGroupMap.get("centroidResidual for BST Cluster");
        HistoGroup histoGroupBSTSeedResidual = histoGroupMap.get("seedResidual for BST Cluster");
        HistoGroup histoGroupBSTCenterXY = histoGroupMap.get("center xy for BST Cluster");
        HistoGroup histoGroupBSTCenterZ = histoGroupMap.get("center z for BST Cluster");
        for(Cluster cls : bstClusters){
            histoGroupBSTSector.getH1F("BST sector for L" + Integer.toString(cls.layer())).fill(cls.sector());
            histoGroupBSTSize.getH1F("BST size for L" + Integer.toString(cls.layer())).fill(cls.size());
            histoGroupBSTSeedStrip.getH1F("BST seedStrip for L" + Integer.toString(cls.layer())).fill(cls.seedStrip());
            histoGroupBSTSeedE.getH1F("BST seedE for L" + Integer.toString(cls.layer())).fill(cls.seedE());
            histoGroupBSTETot.getH1F("BST ETot for L" + Integer.toString(cls.layer())).fill(cls.ETot());
            histoGroupBSTTime.getH1F("BST time for L" + Integer.toString(cls.layer())).fill(cls.time());            
            histoGroupBSTCentroid.getH1F("BST centroid for L" + Integer.toString(cls.layer())).fill(cls.centroid());
            histoGroupBSTCentroidResidual.getH1F("BST centroidResidual for L" + Integer.toString(cls.layer())).fill(cls.centroidResidual());
            histoGroupBSTSeedResidual.getH1F("BST seedResidual for L" + Integer.toString(cls.layer())).fill(cls.seedResidual());
            histoGroupBSTCenterXY.getH2F("BST center xy for L" + Integer.toString(cls.layer())).fill(cls.centerPoint().x(), cls.centerPoint().y());
            histoGroupBSTCenterZ.getH1F("BST center z for L" + Integer.toString(cls.layer())).fill(cls.centerPoint().z());
        }
        
        if(Constants.BG){
            HistoGroup histoGroupBSTNumNormalBgHits = histoGroupMap.get("nums of normal and bg hits for BST Cluster");
            HistoGroup histoGroupBSTRatioNormalHits = histoGroupMap.get("ratio of normal hits for BST Cluster");
            HistoGroup histoGroupBSTCategory = histoGroupMap.get("category for BST Cluster");
            
            for(Cluster cls : bstClusters){
                histoGroupBSTNumNormalBgHits.getH2F("normal vs bg hits for L" + Integer.toString(cls.layer())).fill(cls.getNumNormalHits(), cls.getNumBgHits());
                histoGroupBSTRatioNormalHits.getH1F("ratio of normal hits for L" + Integer.toString(cls.layer())).fill(cls.getRatioNormalHits());                
                histoGroupBSTCategory.getH1F("category for L" + Integer.toString(cls.layer())).fill(cls.getCategory());
               
            }
        }

        HistoGroup histoGroupBMTSector = histoGroupMap.get("sector for BMT Cluster");
        HistoGroup histoGroupBMTSize = histoGroupMap.get("size for BMT Cluster");
        HistoGroup histoGroupBMTSeedStrip = histoGroupMap.get("seedStrip for BMT Cluster");
        HistoGroup histoGroupBMTSeedE = histoGroupMap.get("seedE for BMT Cluster");
        HistoGroup histoGroupBMTETot = histoGroupMap.get("ETot for BMT Cluster");
        HistoGroup histoGroupBMTTime = histoGroupMap.get("time for BMT Cluster");
        HistoGroup histoGroupBMTCentroid = histoGroupMap.get("centroid for BMT Cluster");
        HistoGroup histoGroupBMTCentroidResidual = histoGroupMap.get("centroidResidual for BMT Cluster");
        HistoGroup histoGroupBMTSeedResidual = histoGroupMap.get("seedResidual for BMT Cluster");
        HistoGroup histoGroupBMTCenterXY = histoGroupMap.get("center xy for BMT Cluster");
        HistoGroup histoGroupBMTCenterZ = histoGroupMap.get("center z for BMT Cluster");        
        for(Cluster cls : bmtClusters){
            histoGroupBMTSector.getH1F("BMT sector for L" + Integer.toString(cls.layer())).fill(cls.sector());
            histoGroupBMTSize.getH1F("BMT size for L" + Integer.toString(cls.layer())).fill(cls.size());
            histoGroupBMTSeedStrip.getH1F("BMT seedStrip for L" + Integer.toString(cls.layer())).fill(cls.seedStrip());
            histoGroupBMTSeedE.getH1F("BMT seedE for L" + Integer.toString(cls.layer())).fill(cls.seedE());
            histoGroupBMTETot.getH1F("BMT ETot for L" + Integer.toString(cls.layer())).fill(cls.ETot());
            histoGroupBMTTime.getH1F("BMT time for L" + Integer.toString(cls.layer())).fill(cls.time());
            histoGroupBMTCentroid.getH1F("BMT centroid for L" + Integer.toString(cls.layer())).fill(cls.centroid());
            histoGroupBMTCentroidResidual.getH1F("BMT centroidResidual for L" + Integer.toString(cls.layer())).fill(cls.centroidResidual());
            histoGroupBMTSeedResidual.getH1F("BMT seedResidual for L" + Integer.toString(cls.layer())).fill(cls.seedResidual());
            histoGroupBMTCenterXY.getH2F("BMT center xy for L" + Integer.toString(cls.layer())).fill(cls.centerPoint().x(), cls.centerPoint().y());
            histoGroupBMTCenterZ.getH1F("BMT center z for L" + Integer.toString(cls.layer())).fill(cls.centerPoint().z());
        }
        
        if(Constants.BG){
            HistoGroup histoGroupBMTNumNormalBgHits = histoGroupMap.get("nums of normal and bg hits for BMT Cluster");
            HistoGroup histoGroupBMTRatioNormalHits = histoGroupMap.get("ratio of normal hits for BMT Cluster");
            HistoGroup histoGroupBMTCategory = histoGroupMap.get("category for BMT Cluster");
            for(Cluster cls : bstClusters){
                histoGroupBMTNumNormalBgHits.getH2F("normal vs bg hits for L" + Integer.toString(cls.layer())).fill(cls.getNumNormalHits(), cls.getNumBgHits());
                histoGroupBMTRatioNormalHits.getH1F("ratio of normal hits for L" + Integer.toString(cls.layer())).fill(cls.getRatioNormalHits());
                histoGroupBMTCategory.getH1F("category for L" + Integer.toString(cls.layer())).fill(cls.getCategory());               
            }
        }        
         

    }
    
    public void postEventProcess() {

                      
    }            
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("ClusterRecon");
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
        
        ClusterRecon analysis = new ClusterRecon();
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
