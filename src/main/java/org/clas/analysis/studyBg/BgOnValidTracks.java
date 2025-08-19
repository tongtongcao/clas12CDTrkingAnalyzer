package org.clas.analysis.studyBg;

import java.util.List;
import java.util.ArrayList;
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
import org.clas.element.Seed;
import org.clas.element.Track;
import org.clas.graph.HistoGroup;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;

/**
 * Investigate background effects level by level in reconstruction
 * 
 * @author Tongtong Cao
 */
public class BgOnValidTracks extends BaseAnalysis{ 
    
    final static int CUTOPTIONS = 6;
    final static double EFFICIENCYCUT[] = {0.5, 0.6, 0.7, 0.8, 0.9, 0.95};
    final static double PURITYCUT[] = {0.5, 0.6, 0.7, 0.8, 0.9, 0.95};
    
    private int numTotalTracks1 = 0;
    
    private int numHitMatchedTracksHitLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numHitMatchedTracksClusterLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numHitMatchedTracksCrossLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numHitMatchedTracksSeedLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numHitMatchedTracksTrackLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numHitMatchedTracksValidTrackLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    
    private int numClusterMatchedTracksHitLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numClusterMatchedTracksClusterLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numClusterMatchedTracksCrossLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numClusterMatchedTracksSeedLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numClusterMatchedTracksTrackLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numClusterMatchedTracksValidTrackLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};

    private int numCrossMatchedTracksHitLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numCrossMatchedTracksClusterLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numCrossMatchedTracksCrossLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numCrossMatchedTracksSeedLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numCrossMatchedTracksTrackLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
    private int numCrossMatchedTracksValidTrackLevel[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0},
                                                    {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};    

    public BgOnValidTracks(){}
    
    @Override
    public void createHistoGroupMap(){ 
        HistoGroup histoGroupKinematicsDiff = new HistoGroup("Track kinematics diff", 3, 3);         
        H1F h1_pDiff = new H1F("pDiff", "p diff", 100, -0.1, 0.1);
        h1_pDiff.setTitleX("p diff (GeV/c)");
        h1_pDiff.setTitleY("Counts");
        h1_pDiff.setLineColor(1);   
        histoGroupKinematicsDiff.addDataSet(h1_pDiff, 0);        
        H1F h1_thetaDiff = new H1F("thetaDiff", "#theta diff", 100, -0.1, 0.1);
        h1_thetaDiff.setTitleX("#theta diff (rad)");
        h1_thetaDiff.setTitleY("Counts");
        h1_thetaDiff.setLineColor(1);   
        histoGroupKinematicsDiff.addDataSet(h1_thetaDiff, 1);          
        H1F h1_phiDiff = new H1F("phiDiff", "#phi diff", 100, -0.1, 0.1);
        h1_phiDiff.setTitleX("#phi diff (rad)");
        h1_phiDiff.setTitleY("Counts");
        h1_phiDiff.setLineColor(1);   
        histoGroupKinematicsDiff.addDataSet(h1_phiDiff, 2);         
        H1F h1_xDiff = new H1F("xDiff", "x diff", 100, -0.1, 0.1);
        h1_xDiff.setTitleX("x diff (cm)");
        h1_xDiff.setTitleY("Counts");
        h1_xDiff.setLineColor(1);   
        histoGroupKinematicsDiff.addDataSet(h1_xDiff, 3);           
        H1F h1_yDiff = new H1F("yDiff", "y diff", 100, -0.1, 0.1);
        h1_yDiff.setTitleX("y diff (cm)");
        h1_yDiff.setTitleY("Counts");
        h1_yDiff.setLineColor(1);   
        histoGroupKinematicsDiff.addDataSet(h1_yDiff, 4); 
        H1F h1_zDiff = new H1F("zDiff", "z diff", 100, -0.1, 0.1);
        h1_zDiff.setTitleX("z diff(cm)");
        h1_zDiff.setTitleY("Counts");
        h1_zDiff.setLineColor(1);   
        histoGroupKinematicsDiff.addDataSet(h1_zDiff, 5);  
        H1F h1_chi2_per_ndfDiff = new H1F("chi2_per_ndfDiff", "chi2/ndf diff", 100, -1, 1);
        h1_chi2_per_ndfDiff.setTitleX("chi2/ndf diff");
        h1_chi2_per_ndfDiff.setTitleY("Counts");
        h1_chi2_per_ndfDiff.setLineColor(1);
        histoGroupKinematicsDiff.addDataSet(h1_chi2_per_ndfDiff, 6);           
        histoGroupMap.put(histoGroupKinematicsDiff.getName(), histoGroupKinematicsDiff); 
                
        HistoGroup histoGroupKinematicsDiffVsHitEfficiency = new HistoGroup("Track kinematics diff vs hit efficiency", 3, 3);  
        H2F h2_pDiffVsHitEfficiency = new H2F("pDiffVsHitEfficiency", "p diff vs hit efficiency", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_pDiffVsHitEfficiency.setTitleX("hit efficiency"); 
        h2_pDiffVsHitEfficiency.setTitleY("p diff (GeV/c)");        
        histoGroupKinematicsDiffVsHitEfficiency.addDataSet(h2_pDiffVsHitEfficiency, 0);        
        H2F h2_thetaDiffVsHitEfficiency = new H2F("thetaDiffVsHitEfficiency", "#theta diff vs hit efficiency", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_thetaDiffVsHitEfficiency.setTitleX("hit efficiency"); 
        h2_thetaDiffVsHitEfficiency.setTitleY("#theta diff (rad)");        
        histoGroupKinematicsDiffVsHitEfficiency.addDataSet(h2_thetaDiffVsHitEfficiency, 1);          
        H2F h2_phiDiffVsHitEfficiency = new H2F("phiDiffVsHitEfficiency", "#phi diff vs hit efficiency", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_phiDiffVsHitEfficiency.setTitleX("hit efficiency");
        h2_phiDiffVsHitEfficiency.setTitleY("#phi diff (rad)");        
        histoGroupKinematicsDiffVsHitEfficiency.addDataSet(h2_phiDiffVsHitEfficiency, 2);         
        H2F h2_xDiffVsHitEfficiency = new H2F("xDiffVsHitEfficiency", "x diff vs hit efficiency", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_xDiffVsHitEfficiency.setTitleX("hit efficiency"); 
        h2_xDiffVsHitEfficiency.setTitleY("x diff (cm)");          
        histoGroupKinematicsDiffVsHitEfficiency.addDataSet(h2_xDiffVsHitEfficiency, 3);           
        H2F h2_yDiffVsHitEfficiency = new H2F("yDiffVsHitEfficiency", "y diff vs hit efficiency", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_yDiffVsHitEfficiency.setTitleX("hit efficiency");
        h2_yDiffVsHitEfficiency.setTitleY("y diff (cm)");        
        histoGroupKinematicsDiffVsHitEfficiency.addDataSet(h2_yDiffVsHitEfficiency, 4); 
        H2F h2_zDiffVsHitEfficiency = new H2F("zDiffVsHitEfficiency", "z diff vs hit efficiency", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_zDiffVsHitEfficiency.setTitleX("hit efficiency"); 
        h2_zDiffVsHitEfficiency.setTitleY("z diff(cm)");        
        histoGroupKinematicsDiffVsHitEfficiency.addDataSet(h2_zDiffVsHitEfficiency, 5);  
        H2F h2_chi2_per_ndfDiffVsHitEfficiency = new H2F("chi2_per_ndfDiffVsHitEfficiency", "chi2/ndf diff vs hit efficiency", 30, 0, 1.01, 100, -1, 1);
        h2_chi2_per_ndfDiffVsHitEfficiency.setTitleX("hit efficiency");
        h2_chi2_per_ndfDiffVsHitEfficiency.setTitleY("chi2/ndf diff");        
        histoGroupKinematicsDiffVsHitEfficiency.addDataSet(h2_chi2_per_ndfDiffVsHitEfficiency, 6); 
        histoGroupMap.put(histoGroupKinematicsDiffVsHitEfficiency.getName(), histoGroupKinematicsDiffVsHitEfficiency);
        
        HistoGroup histoGroupKinematicsDiffVsHitPurity = new HistoGroup("Track kinematics diff vs hit purity", 3, 3);  
        H2F h2_pDiffVsHitPurity = new H2F("pDiffVsHitPurity", "p diff vs hit purity", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_pDiffVsHitPurity.setTitleX("hit purity"); 
        h2_pDiffVsHitPurity.setTitleY("p diff (GeV/c)");        
        histoGroupKinematicsDiffVsHitPurity.addDataSet(h2_pDiffVsHitPurity, 0);        
        H2F h2_thetaDiffVsHitPurity = new H2F("thetaDiffVsHitPurity", "#theta diff vs hit purity", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_thetaDiffVsHitPurity.setTitleX("hit purity"); 
        h2_thetaDiffVsHitPurity.setTitleY("#theta diff (rad)");        
        histoGroupKinematicsDiffVsHitPurity.addDataSet(h2_thetaDiffVsHitPurity, 1);          
        H2F h2_phiDiffVsHitPurity = new H2F("phiDiffVsHitPurity", "#phi diff vs hit purity", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_phiDiffVsHitPurity.setTitleX("hit purity");
        h2_phiDiffVsHitPurity.setTitleY("#phi diff (rad)");        
        histoGroupKinematicsDiffVsHitPurity.addDataSet(h2_phiDiffVsHitPurity, 2);         
        H2F h2_xDiffVsHitPurity = new H2F("xDiffVsHitPurity", "x diff vs hit purity", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_xDiffVsHitPurity.setTitleX("hit purity"); 
        h2_xDiffVsHitPurity.setTitleY("x diff (cm)");          
        histoGroupKinematicsDiffVsHitPurity.addDataSet(h2_xDiffVsHitPurity, 3);           
        H2F h2_yDiffVsHitPurity = new H2F("yDiffVsHitPurity", "y diff vs hit purity", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_yDiffVsHitPurity.setTitleX("hit purity");
        h2_yDiffVsHitPurity.setTitleY("y diff (cm)");        
        histoGroupKinematicsDiffVsHitPurity.addDataSet(h2_yDiffVsHitPurity, 4); 
        H2F h2_zDiffVsHitPurity = new H2F("zDiffVsHitPurity", "z diff vs hit purity", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_zDiffVsHitPurity.setTitleX("hit purity"); 
        h2_zDiffVsHitPurity.setTitleY("z diff(cm)");        
        histoGroupKinematicsDiffVsHitPurity.addDataSet(h2_zDiffVsHitPurity, 5);  
        H2F h2_chi2_per_ndfDiffVsHitPurity = new H2F("chi2_per_ndfDiffVsHitPurity", "chi2/ndf diff vs hit purity", 30, 0, 1.01, 100, -1, 1);
        h2_chi2_per_ndfDiffVsHitPurity.setTitleX("hit purity");
        h2_chi2_per_ndfDiffVsHitPurity.setTitleY("chi2/ndf diff");        
        histoGroupKinematicsDiffVsHitPurity.addDataSet(h2_chi2_per_ndfDiffVsHitPurity, 6); 
        histoGroupMap.put(histoGroupKinematicsDiffVsHitPurity.getName(), histoGroupKinematicsDiffVsHitPurity);                 

        HistoGroup histoGroupKinematicsDiffVsClusterEfficiency = new HistoGroup("Track kinematics diff vs cluster efficiency", 3, 3);  
        H2F h2_pDiffVsClusterEfficiency = new H2F("pDiffVsClusterEfficiency", "p diff vs cluster efficiency", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_pDiffVsClusterEfficiency.setTitleX("cluster efficiency"); 
        h2_pDiffVsClusterEfficiency.setTitleY("p diff (GeV/c)");        
        histoGroupKinematicsDiffVsClusterEfficiency.addDataSet(h2_pDiffVsClusterEfficiency, 0);        
        H2F h2_thetaDiffVsClusterEfficiency = new H2F("thetaDiffVsClusterEfficiency", "#theta diff vs cluster efficiency", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_thetaDiffVsClusterEfficiency.setTitleX("cluster efficiency"); 
        h2_thetaDiffVsClusterEfficiency.setTitleY("#theta diff (rad)");        
        histoGroupKinematicsDiffVsClusterEfficiency.addDataSet(h2_thetaDiffVsClusterEfficiency, 1);          
        H2F h2_phiDiffVsClusterEfficiency = new H2F("phiDiffVsClusterEfficiency", "#phi diff vs cluster efficiency", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_phiDiffVsClusterEfficiency.setTitleX("cluster efficiency");
        h2_phiDiffVsClusterEfficiency.setTitleY("#phi diff (rad)");        
        histoGroupKinematicsDiffVsClusterEfficiency.addDataSet(h2_phiDiffVsClusterEfficiency, 2);         
        H2F h2_xDiffVsClusterEfficiency = new H2F("xDiffVsClusterEfficiency", "x diff vs cluster efficiency", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_xDiffVsClusterEfficiency.setTitleX("cluster efficiency"); 
        h2_xDiffVsClusterEfficiency.setTitleY("x diff (cm)");          
        histoGroupKinematicsDiffVsClusterEfficiency.addDataSet(h2_xDiffVsClusterEfficiency, 3);           
        H2F h2_yDiffVsClusterEfficiency = new H2F("yDiffVsClusterEfficiency", "y diff vs cluster efficiency", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_yDiffVsClusterEfficiency.setTitleX("cluster efficiency");
        h2_yDiffVsClusterEfficiency.setTitleY("y diff (cm)");        
        histoGroupKinematicsDiffVsClusterEfficiency.addDataSet(h2_yDiffVsClusterEfficiency, 4); 
        H2F h2_zDiffVsClusterEfficiency = new H2F("zDiffVsClusterEfficiency", "z diff vs cluster efficiency", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_zDiffVsClusterEfficiency.setTitleX("cluster efficiency"); 
        h2_zDiffVsClusterEfficiency.setTitleY("z diff(cm)");        
        histoGroupKinematicsDiffVsClusterEfficiency.addDataSet(h2_zDiffVsClusterEfficiency, 5);  
        H2F h2_chi2_per_ndfDiffVsClusterEfficiency = new H2F("chi2_per_ndfDiffVsClusterEfficiency", "chi2/ndf diff vs cluster efficiency", 30, 0, 1.01, 100, -1, 1);
        h2_chi2_per_ndfDiffVsClusterEfficiency.setTitleX("cluster efficiency");
        h2_chi2_per_ndfDiffVsClusterEfficiency.setTitleY("chi2/ndf diff");        
        histoGroupKinematicsDiffVsClusterEfficiency.addDataSet(h2_chi2_per_ndfDiffVsClusterEfficiency, 6); 
        histoGroupMap.put(histoGroupKinematicsDiffVsClusterEfficiency.getName(), histoGroupKinematicsDiffVsClusterEfficiency);
        
        HistoGroup histoGroupKinematicsDiffVsClusterPurity = new HistoGroup("Track kinematics diff vs cluster purity", 3, 3);  
        H2F h2_pDiffVsClusterPurity = new H2F("pDiffVsClusterPurity", "p diff vs cluster purity", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_pDiffVsClusterPurity.setTitleX("cluster purity"); 
        h2_pDiffVsClusterPurity.setTitleY("p diff (GeV/c)");        
        histoGroupKinematicsDiffVsClusterPurity.addDataSet(h2_pDiffVsClusterPurity, 0);        
        H2F h2_thetaDiffVsClusterPurity = new H2F("thetaDiffVsClusterPurity", "#theta diff vs cluster purity", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_thetaDiffVsClusterPurity.setTitleX("cluster purity"); 
        h2_thetaDiffVsClusterPurity.setTitleY("#theta diff (rad)");        
        histoGroupKinematicsDiffVsClusterPurity.addDataSet(h2_thetaDiffVsClusterPurity, 1);          
        H2F h2_phiDiffVsClusterPurity = new H2F("phiDiffVsClusterPurity", "#phi diff vs cluster purity", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_phiDiffVsClusterPurity.setTitleX("cluster purity");
        h2_phiDiffVsClusterPurity.setTitleY("#phi diff (rad)");        
        histoGroupKinematicsDiffVsClusterPurity.addDataSet(h2_phiDiffVsClusterPurity, 2);         
        H2F h2_xDiffVsClusterPurity = new H2F("xDiffVsClusterPurity", "x diff vs cluster purity", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_xDiffVsClusterPurity.setTitleX("cluster purity"); 
        h2_xDiffVsClusterPurity.setTitleY("x diff (cm)");          
        histoGroupKinematicsDiffVsClusterPurity.addDataSet(h2_xDiffVsClusterPurity, 3);           
        H2F h2_yDiffVsClusterPurity = new H2F("yDiffVsClusterPurity", "y diff vs cluster purity", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_yDiffVsClusterPurity.setTitleX("cluster purity");
        h2_yDiffVsClusterPurity.setTitleY("y diff (cm)");        
        histoGroupKinematicsDiffVsClusterPurity.addDataSet(h2_yDiffVsClusterPurity, 4); 
        H2F h2_zDiffVsClusterPurity = new H2F("zDiffVsClusterPurity", "z diff vs cluster purity", 30, 0, 1.01, 100, -0.1, 0.1);
        h2_zDiffVsClusterPurity.setTitleX("cluster purity"); 
        h2_zDiffVsClusterPurity.setTitleY("z diff(cm)");        
        histoGroupKinematicsDiffVsClusterPurity.addDataSet(h2_zDiffVsClusterPurity, 5);  
        H2F h2_chi2_per_ndfDiffVsClusterPurity = new H2F("chi2_per_ndfDiffVsClusterPurity", "chi2/ndf diff vs cluster purity", 30, 0, 1.01, 100, -1, 1);
        h2_chi2_per_ndfDiffVsClusterPurity.setTitleX("cluster purity");
        h2_chi2_per_ndfDiffVsClusterPurity.setTitleY("chi2/ndf diff");        
        histoGroupKinematicsDiffVsClusterPurity.addDataSet(h2_chi2_per_ndfDiffVsClusterPurity, 6); 
        histoGroupMap.put(histoGroupKinematicsDiffVsClusterPurity.getName(), histoGroupKinematicsDiffVsClusterPurity);          
        
        HistoGroup histoGroupEfficiencyBSTHit = new HistoGroup("efficiencyBSTHit", 3, 2);
        H1F h1_efficiencyBSTHitHitLevel = new H1F("efficiencyBSTHitHitLevel", "efficiencyBSTHit at hit level", 30, 0, 1.01);
        h1_efficiencyBSTHitHitLevel.setTitleX("efficiency");
        h1_efficiencyBSTHitHitLevel.setTitleY("Counts");
        h1_efficiencyBSTHitHitLevel.setLineColor(1);
        histoGroupEfficiencyBSTHit.addDataSet(h1_efficiencyBSTHitHitLevel, 0);
        //histoGroupEfficiencyBSTHit.addDataSet(h1_efficiencyBSTHitHitLevel, 5);
        H1F h1_efficiencyBSTHitClusterLevel = new H1F("efficiencyBSTHitClusterLevel", "efficiencyBSTHit at cluster level", 30, 0, 1.01);
        h1_efficiencyBSTHitClusterLevel.setTitleX("efficiency");
        h1_efficiencyBSTHitClusterLevel.setTitleY("Counts");
        h1_efficiencyBSTHitClusterLevel.setLineColor(2);
        histoGroupEfficiencyBSTHit.addDataSet(h1_efficiencyBSTHitClusterLevel, 1);   
        //histoGroupEfficiencyBSTHit.addDataSet(h1_efficiencyBSTHitClusterLevel, 5);
        H1F h1_efficiencyBSTHitCrossLevel = new H1F("efficiencyBSTHitCrossLevel", "efficiencyBSTHit at cross level", 30, 0, 1.01);
        h1_efficiencyBSTHitCrossLevel.setTitleX("efficiency");
        h1_efficiencyBSTHitCrossLevel.setTitleY("Counts");
        h1_efficiencyBSTHitCrossLevel.setLineColor(3);
        histoGroupEfficiencyBSTHit.addDataSet(h1_efficiencyBSTHitCrossLevel, 2);   
        //histoGroupEfficiencyBSTHit.addDataSet(h1_efficiencyBSTHitCrossLevel, 5); 
        H1F h1_efficiencyBSTHitSeedLevel = new H1F("efficiencyBSTHitSeedLevel", "efficiencyBSTHit at seed level", 30, 0, 1.01);
        h1_efficiencyBSTHitSeedLevel.setTitleX("efficiency");
        h1_efficiencyBSTHitSeedLevel.setTitleY("Counts");
        h1_efficiencyBSTHitSeedLevel.setLineColor(4);
        histoGroupEfficiencyBSTHit.addDataSet(h1_efficiencyBSTHitSeedLevel, 3);   
        //histoGroupEfficiencyBSTHit.addDataSet(h1_efficiencyBSTHitSeedLevel, 5);
        H1F h1_efficiencyBSTHitTrackLevel = new H1F("efficiencyBSTHitTrackLevel", "efficiencyBSTHit at track level", 30, 0, 1.01);
        h1_efficiencyBSTHitTrackLevel.setTitleX("efficiency");
        h1_efficiencyBSTHitTrackLevel.setTitleY("Counts");
        h1_efficiencyBSTHitTrackLevel.setLineColor(5);
        histoGroupEfficiencyBSTHit.addDataSet(h1_efficiencyBSTHitTrackLevel, 4);
        //histoGroupEfficiencyBSTHit.addDataSet(h1_efficiencyBSTHitTrackLevel, 5);
        H1F h1_efficiencyBSTHitValidTrackLevel = new H1F("efficiencyBSTHitValidTrackLevel", "efficiencyBSTHit at valid track level", 30, 0, 1.01);
        h1_efficiencyBSTHitValidTrackLevel.setTitleX("efficiency");
        h1_efficiencyBSTHitValidTrackLevel.setTitleY("Counts");
        h1_efficiencyBSTHitValidTrackLevel.setLineColor(6);
        histoGroupEfficiencyBSTHit.addDataSet(h1_efficiencyBSTHitValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupEfficiencyBSTHit.getName(), histoGroupEfficiencyBSTHit);        
        HistoGroup histoGroupEfficiencyBSTCluster = new HistoGroup("efficiencyBSTCluster", 3, 2);
        H1F h1_efficiencyBSTClusterHitLevel = new H1F("efficiencyBSTClusterHitLevel", "efficiencyBSTCluster at hit level", 30, 0, 1.01);
        h1_efficiencyBSTClusterHitLevel.setTitleX("efficiency");
        h1_efficiencyBSTClusterHitLevel.setTitleY("Counts");
        h1_efficiencyBSTClusterHitLevel.setLineColor(1);
        histoGroupEfficiencyBSTCluster.addDataSet(h1_efficiencyBSTClusterHitLevel, 0); 
        //histoGroupEfficiencyBSTCluster.addDataSet(h1_efficiencyBSTClusterHitLevel, 5);   
        H1F h1_efficiencyBSTClusterClusterLevel = new H1F("efficiencyBSTClusterClusterLevel", "efficiencyBSTCluster at cluster level", 30, 0, 1.01);
        h1_efficiencyBSTClusterClusterLevel.setTitleX("efficiency");
        h1_efficiencyBSTClusterClusterLevel.setTitleY("Counts");
        h1_efficiencyBSTClusterClusterLevel.setLineColor(2);
        histoGroupEfficiencyBSTCluster.addDataSet(h1_efficiencyBSTClusterClusterLevel, 1);         
        //histoGroupEfficiencyBSTCluster.addDataSet(h1_efficiencyBSTClusterClusterLevel, 5); 
        H1F h1_efficiencyBSTClusterCrossLevel = new H1F("efficiencyBSTClusterCrossLevel", "efficiencyBSTCluster at cross level", 30, 0, 1.01);
        h1_efficiencyBSTClusterCrossLevel.setTitleX("efficiency");
        h1_efficiencyBSTClusterCrossLevel.setTitleY("Counts");
        h1_efficiencyBSTClusterCrossLevel.setLineColor(3);
        histoGroupEfficiencyBSTCluster.addDataSet(h1_efficiencyBSTClusterCrossLevel, 2);  
        //histoGroupEfficiencyBSTCluster.addDataSet(h1_efficiencyBSTClusterCrossLevel, 5); 
        H1F h1_efficiencyBSTClusterSeedLevel = new H1F("efficiencyBSTClusterSeedLevel", "efficiencyBSTCluster at seed level", 30, 0, 1.01);
        h1_efficiencyBSTClusterSeedLevel.setTitleX("efficiency");
        h1_efficiencyBSTClusterSeedLevel.setTitleY("Counts");
        h1_efficiencyBSTClusterSeedLevel.setLineColor(4);
        histoGroupEfficiencyBSTCluster.addDataSet(h1_efficiencyBSTClusterSeedLevel, 3); 
        //histoGroupEfficiencyBSTCluster.addDataSet(h1_efficiencyBSTClusterSeedLevel, 5); 
        H1F h1_efficiencyBSTClusterTrackLevel = new H1F("efficiencyBSTClusterTrackLevel", "efficiencyBSTCluster at track level", 30, 0, 1.01);
        h1_efficiencyBSTClusterTrackLevel.setTitleX("efficiency");
        h1_efficiencyBSTClusterTrackLevel.setTitleY("Counts");
        h1_efficiencyBSTClusterTrackLevel.setLineColor(5);
        histoGroupEfficiencyBSTCluster.addDataSet(h1_efficiencyBSTClusterTrackLevel, 4); 
        //histoGroupEfficiencyBSTCluster.addDataSet(h1_efficiencyBSTClusterTrackLevel, 5); 
        H1F h1_efficiencyBSTClusterValidTrackLevel = new H1F("efficiencyBSTClusterValidTrackLevel", "efficiencyBSTCluster at valid track level", 30, 0, 1.01);
        h1_efficiencyBSTClusterValidTrackLevel.setTitleX("efficiency");
        h1_efficiencyBSTClusterValidTrackLevel.setTitleY("Counts");
        h1_efficiencyBSTClusterValidTrackLevel.setLineColor(6);
        histoGroupEfficiencyBSTCluster.addDataSet(h1_efficiencyBSTClusterValidTrackLevel, 5);         
        histoGroupMap.put(histoGroupEfficiencyBSTCluster.getName(), histoGroupEfficiencyBSTCluster);          
        HistoGroup histoGroupEfficiencyBSTCross = new HistoGroup("efficiencyBSTCross", 3, 2);
        H1F h1_efficiencyBSTCrossHitLevel = new H1F("efficiencyBSTCrossHitLevel", "efficiencyBSTCross at hit level", 30, 0, 1.01);
        h1_efficiencyBSTCrossHitLevel.setTitleX("efficiency");
        h1_efficiencyBSTCrossHitLevel.setTitleY("Counts");
        h1_efficiencyBSTCrossHitLevel.setLineColor(1);
        histoGroupEfficiencyBSTCross.addDataSet(h1_efficiencyBSTCrossHitLevel, 0);  
        //histoGroupEfficiencyBSTCross.addDataSet(h1_efficiencyBSTCrossHitLevel, 5); 
        H1F h1_efficiencyBSTCrossClusterLevel = new H1F("efficiencyBSTCrossClusterLevel", "efficiencyBSTCross at cluster level", 30, 0, 1.01);
        h1_efficiencyBSTCrossClusterLevel.setTitleX("efficiency");
        h1_efficiencyBSTCrossClusterLevel.setTitleY("Counts");
        h1_efficiencyBSTCrossClusterLevel.setLineColor(2);
        histoGroupEfficiencyBSTCross.addDataSet(h1_efficiencyBSTCrossClusterLevel, 1);
        //histoGroupEfficiencyBSTCross.addDataSet(h1_efficiencyBSTCrossClusterLevel, 5); 
        H1F h1_efficiencyBSTCrossCrossLevel = new H1F("efficiencyBSTCrossCrossLevel", "efficiencyBSTCross at cross level", 30, 0, 1.01);
        h1_efficiencyBSTCrossCrossLevel.setTitleX("efficiency");
        h1_efficiencyBSTCrossCrossLevel.setTitleY("Counts");
        h1_efficiencyBSTCrossCrossLevel.setLineColor(3);
        histoGroupEfficiencyBSTCross.addDataSet(h1_efficiencyBSTCrossCrossLevel, 2);  
        //histoGroupEfficiencyBSTCross.addDataSet(h1_efficiencyBSTCrossCrossLevel, 5); 
        H1F h1_efficiencyBSTCrossSeedLevel = new H1F("efficiencyBSTCrossSeedLevel", "efficiencyBSTCross at seed level", 30, 0, 1.01);
        h1_efficiencyBSTCrossSeedLevel.setTitleX("efficiency");
        h1_efficiencyBSTCrossSeedLevel.setTitleY("Counts");
        h1_efficiencyBSTCrossSeedLevel.setLineColor(4);
        histoGroupEfficiencyBSTCross.addDataSet(h1_efficiencyBSTCrossSeedLevel, 3); 
        //histoGroupEfficiencyBSTCross.addDataSet(h1_efficiencyBSTCrossSeedLevel, 5); 
        H1F h1_efficiencyBSTCrossTrackLevel = new H1F("efficiencyBSTCrossTrackLevel", "efficiencyBSTCross at track level", 30, 0, 1.01);
        h1_efficiencyBSTCrossTrackLevel.setTitleX("efficiency");
        h1_efficiencyBSTCrossTrackLevel.setTitleY("Counts");
        h1_efficiencyBSTCrossTrackLevel.setLineColor(5);
        histoGroupEfficiencyBSTCross.addDataSet(h1_efficiencyBSTCrossTrackLevel, 4); 
        //histoGroupEfficiencyBSTCross.addDataSet(h1_efficiencyBSTCrossTrackLevel, 5); 
        H1F h1_efficiencyBSTCrossValidTrackLevel = new H1F("efficiencyBSTCrossValidTrackLevel", "efficiencyBSTCross at valid track level", 30, 0, 1.01);
        h1_efficiencyBSTCrossValidTrackLevel.setTitleX("efficiency");
        h1_efficiencyBSTCrossValidTrackLevel.setTitleY("Counts");
        h1_efficiencyBSTCrossValidTrackLevel.setLineColor(6);
        histoGroupEfficiencyBSTCross.addDataSet(h1_efficiencyBSTCrossValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupEfficiencyBSTCross.getName(), histoGroupEfficiencyBSTCross);  
        HistoGroup histoGroupPurityBSTHit = new HistoGroup("purityBSTHit", 3, 2);
        H1F h1_purityBSTHitHitLevel = new H1F("purityBSTHitHitLevel", "purityBSTHit at hit level", 30, 0, 1.01);
        h1_purityBSTHitHitLevel.setTitleX("purity");
        h1_purityBSTHitHitLevel.setTitleY("Counts");
        h1_purityBSTHitHitLevel.setLineColor(1);
        histoGroupPurityBSTHit.addDataSet(h1_purityBSTHitHitLevel, 0); 
        //histoGroupPurityBSTHit.addDataSet(h1_purityBSTHitHitLevel, 5);
        H1F h1_purityBSTHitClusterLevel = new H1F("purityBSTHitClusterLevel", "purityBSTHit at cluster level", 30, 0, 1.01);
        h1_purityBSTHitClusterLevel.setTitleX("purity");
        h1_purityBSTHitClusterLevel.setTitleY("Counts");
        h1_purityBSTHitClusterLevel.setLineColor(2);
        histoGroupPurityBSTHit.addDataSet(h1_purityBSTHitClusterLevel, 1); 
        //histoGroupPurityBSTHit.addDataSet(h1_purityBSTHitClusterLevel, 5);        
        H1F h1_purityBSTHitCrossLevel = new H1F("purityBSTHitCrossLevel", "purityBSTHit at cross level", 30, 0, 1.01);
        h1_purityBSTHitCrossLevel.setTitleX("purity");
        h1_purityBSTHitCrossLevel.setTitleY("Counts");
        h1_purityBSTHitCrossLevel.setLineColor(3);
        histoGroupPurityBSTHit.addDataSet(h1_purityBSTHitCrossLevel, 2); 
        //histoGroupPurityBSTHit.addDataSet(h1_purityBSTHitCrossLevel, 5);
        H1F h1_purityBSTHitSeedLevel = new H1F("purityBSTHitSeedLevel", "purityBSTHit at seed level", 30, 0, 1.01);
        h1_purityBSTHitSeedLevel.setTitleX("purity");
        h1_purityBSTHitSeedLevel.setTitleY("Counts");
        h1_purityBSTHitSeedLevel.setLineColor(4);
        histoGroupPurityBSTHit.addDataSet(h1_purityBSTHitSeedLevel, 3); 
        //histoGroupPurityBSTHit.addDataSet(h1_purityBSTHitSeedLevel, 5);
        H1F h1_purityBSTHitTrackLevel = new H1F("purityBSTHitTrackLevel", "purityBSTHit at track level", 30, 0, 1.01);
        h1_purityBSTHitTrackLevel.setTitleX("purity");
        h1_purityBSTHitTrackLevel.setTitleY("Counts");
        h1_purityBSTHitTrackLevel.setLineColor(5);
        histoGroupPurityBSTHit.addDataSet(h1_purityBSTHitTrackLevel, 4);
        //histoGroupPurityBSTHit.addDataSet(h1_purityBSTHitTrackLevel, 5);
        H1F h1_purityBSTHitValidTrackLevel = new H1F("purityBSTHitValidTrackLevel", "purityBSTHit at valid track level", 30, 0, 1.01);
        h1_purityBSTHitValidTrackLevel.setTitleX("purity");
        h1_purityBSTHitValidTrackLevel.setTitleY("Counts");
        h1_purityBSTHitValidTrackLevel.setLineColor(6);
        histoGroupPurityBSTHit.addDataSet(h1_purityBSTHitValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupPurityBSTHit.getName(), histoGroupPurityBSTHit);        
        HistoGroup histoGroupPurityBSTCluster = new HistoGroup("purityBSTCluster", 3, 2);
        H1F h1_purityBSTClusterHitLevel = new H1F("purityBSTClusterHitLevel", "purityBSTCluster at hit level", 30, 0, 1.01);
        h1_purityBSTClusterHitLevel.setTitleX("purity");
        h1_purityBSTClusterHitLevel.setTitleY("Counts");
        h1_purityBSTClusterHitLevel.setLineColor(1);
        histoGroupPurityBSTCluster.addDataSet(h1_purityBSTClusterHitLevel, 0);
        //histoGroupPurityBSTCluster.addDataSet(h1_purityBSTClusterHitLevel, 5);
        H1F h1_purityBSTClusterClusterLevel = new H1F("purityBSTClusterClusterLevel", "purityBSTCluster at cluster level", 30, 0, 1.01);
        h1_purityBSTClusterClusterLevel.setTitleX("purity");
        h1_purityBSTClusterClusterLevel.setTitleY("Counts");
        h1_purityBSTClusterClusterLevel.setLineColor(2);
        histoGroupPurityBSTCluster.addDataSet(h1_purityBSTClusterClusterLevel, 1);
        //histoGroupPurityBSTCluster.addDataSet(h1_purityBSTClusterClusterLevel, 5);
        H1F h1_purityBSTClusterCrossLevel = new H1F("purityBSTClusterCrossLevel", "purityBSTCluster at cross level", 30, 0, 1.01);
        h1_purityBSTClusterCrossLevel.setTitleX("purity");
        h1_purityBSTClusterCrossLevel.setTitleY("Counts");
        h1_purityBSTClusterCrossLevel.setLineColor(3);
        histoGroupPurityBSTCluster.addDataSet(h1_purityBSTClusterCrossLevel, 2); 
        //histoGroupPurityBSTCluster.addDataSet(h1_purityBSTClusterCrossLevel, 5);
        H1F h1_purityBSTClusterSeedLevel = new H1F("purityBSTClusterSeedLevel", "purityBSTCluster at seed level", 30, 0, 1.01);
        h1_purityBSTClusterSeedLevel.setTitleX("purity");
        h1_purityBSTClusterSeedLevel.setTitleY("Counts");
        h1_purityBSTClusterSeedLevel.setLineColor(4);
        histoGroupPurityBSTCluster.addDataSet(h1_purityBSTClusterSeedLevel, 3); 
        //histoGroupPurityBSTCluster.addDataSet(h1_purityBSTClusterSeedLevel, 5);
        H1F h1_purityBSTClusterTrackLevel = new H1F("purityBSTClusterTrackLevel", "purityBSTCluster at track level", 30, 0, 1.01);
        h1_purityBSTClusterTrackLevel.setTitleX("purity");
        h1_purityBSTClusterTrackLevel.setTitleY("Counts");
        h1_purityBSTClusterTrackLevel.setLineColor(5);
        histoGroupPurityBSTCluster.addDataSet(h1_purityBSTClusterTrackLevel, 4); 
        //histoGroupPurityBSTCluster.addDataSet(h1_purityBSTClusterTrackLevel, 5);  
        H1F h1_purityBSTClusterValidTrackLevel = new H1F("purityBSTClusterValidTrackLevel", "purityBSTCluster at valid track level", 30, 0, 1.01);
        h1_purityBSTClusterValidTrackLevel.setTitleX("purity");
        h1_purityBSTClusterValidTrackLevel.setTitleY("Counts");
        h1_purityBSTClusterValidTrackLevel.setLineColor(6);
        histoGroupPurityBSTCluster.addDataSet(h1_purityBSTClusterValidTrackLevel, 5);         
        histoGroupMap.put(histoGroupPurityBSTCluster.getName(), histoGroupPurityBSTCluster);          
        HistoGroup histoGroupPurityBSTCross = new HistoGroup("purityBSTCross", 3, 2);
        H1F h1_purityBSTCrossHitLevel = new H1F("purityBSTCrossHitLevel", "purityBSTCross at hit level", 30, 0, 1.01);
        h1_purityBSTCrossHitLevel.setTitleX("purity");
        h1_purityBSTCrossHitLevel.setTitleY("Counts");
        h1_purityBSTCrossHitLevel.setLineColor(1);
        histoGroupPurityBSTCross.addDataSet(h1_purityBSTCrossHitLevel, 0);
        //histoGroupPurityBSTCross.addDataSet(h1_purityBSTCrossHitLevel, 5); 
        H1F h1_purityBSTCrossClusterLevel = new H1F("purityBSTCrossClusterLevel", "purityBSTCross at cluster level", 30, 0, 1.01);
        h1_purityBSTCrossClusterLevel.setTitleX("purity");
        h1_purityBSTCrossClusterLevel.setTitleY("Counts");
        h1_purityBSTCrossClusterLevel.setLineColor(2);
        histoGroupPurityBSTCross.addDataSet(h1_purityBSTCrossClusterLevel, 1); 
        //histoGroupPurityBSTCross.addDataSet(h1_purityBSTCrossClusterLevel, 5);
        H1F h1_purityBSTCrossCrossLevel = new H1F("purityBSTCrossCrossLevel", "purityBSTCross at cross level", 30, 0, 1.01);
        h1_purityBSTCrossCrossLevel.setTitleX("purity");
        h1_purityBSTCrossCrossLevel.setTitleY("Counts");
        h1_purityBSTCrossCrossLevel.setLineColor(3);
        histoGroupPurityBSTCross.addDataSet(h1_purityBSTCrossCrossLevel, 2); 
        //histoGroupPurityBSTCross.addDataSet(h1_purityBSTCrossCrossLevel, 5);
        H1F h1_purityBSTCrossSeedLevel = new H1F("purityBSTCrossSeedLevel", "purityBSTCross at seed level", 30, 0, 1.01);
        h1_purityBSTCrossSeedLevel.setTitleX("purity");
        h1_purityBSTCrossSeedLevel.setTitleY("Counts");
        h1_purityBSTCrossSeedLevel.setLineColor(4);
        histoGroupPurityBSTCross.addDataSet(h1_purityBSTCrossSeedLevel, 3);
        //histoGroupPurityBSTCross.addDataSet(h1_purityBSTCrossSeedLevel, 5);
        H1F h1_purityBSTCrossTrackLevel = new H1F("purityBSTCrossTrackLevel", "purityBSTCross at track level", 30, 0, 1.01);
        h1_purityBSTCrossTrackLevel.setTitleX("purity");
        h1_purityBSTCrossTrackLevel.setTitleY("Counts");
        h1_purityBSTCrossTrackLevel.setLineColor(5);
        histoGroupPurityBSTCross.addDataSet(h1_purityBSTCrossTrackLevel, 4); 
        //histoGroupPurityBSTCross.addDataSet(h1_purityBSTCrossTrackLevel, 5); 
        H1F h1_purityBSTCrossValidTrackLevel = new H1F("purityBSTCrossValidTrackLevel", "purityBSTCross at valid track level", 30, 0, 1.01);
        h1_purityBSTCrossValidTrackLevel.setTitleX("purity");
        h1_purityBSTCrossValidTrackLevel.setTitleY("Counts");
        h1_purityBSTCrossValidTrackLevel.setLineColor(6);
        histoGroupPurityBSTCross.addDataSet(h1_purityBSTCrossValidTrackLevel, 5);
        histoGroupMap.put(histoGroupPurityBSTCross.getName(), histoGroupPurityBSTCross);                 
        HistoGroup histoGroupEfficiencyVsPurityBSTHit = new HistoGroup("efficiencyVsPurityBSTHit", 3, 2);
        H2F h2_efficiencyVsPurityBSTHitHitLevel = new H2F("efficiencyVsPurityBSTHitHitLevel", "efficiency vs purity BSTHit at hit level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTHitHitLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTHitHitLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTHit.addDataSet(h2_efficiencyVsPurityBSTHitHitLevel, 0);
        H2F h2_efficiencyVsPurityBSTHitClusterLevel = new H2F("efficiencyVsPurityBSTHitClusterLevel", "efficiency vs purity BSTHit at cluster level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTHitClusterLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTHitClusterLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTHit.addDataSet(h2_efficiencyVsPurityBSTHitClusterLevel, 1);   
        H2F h2_efficiencyVsPurityBSTHitCrossLevel = new H2F("efficiencyVsPurityBSTHitCrossLevel", "efficiency vs purity BSTHit at cross level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTHitCrossLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTHitCrossLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTHit.addDataSet(h2_efficiencyVsPurityBSTHitCrossLevel, 2);   
        H2F h2_efficiencyVsPurityBSTHitSeedLevel = new H2F("efficiencyVsPurityBSTHitSeedLevel", "efficiency vs purity BSTHit at seed level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTHitSeedLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTHitSeedLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTHit.addDataSet(h2_efficiencyVsPurityBSTHitSeedLevel, 3);   
        H2F h2_efficiencyVsPurityBSTHitTrackLevel = new H2F("efficiencyVsPurityBSTHitTrackLevel", "efficiency vs purity BSTHit at track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTHitTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTHitTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTHit.addDataSet(h2_efficiencyVsPurityBSTHitTrackLevel, 4);
        H2F h2_efficiencyVsPurityBSTHitValidTrackLevel = new H2F("efficiencyVsPurityBSTHitValidTrackLevel", "efficiency vs purity BSTHit at valid track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTHitValidTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTHitValidTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTHit.addDataSet(h2_efficiencyVsPurityBSTHitValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupEfficiencyVsPurityBSTHit.getName(), histoGroupEfficiencyVsPurityBSTHit);         
        HistoGroup histoGroupEfficiencyVsPurityBSTCluster = new HistoGroup("efficiencyVsPurityBSTCluster", 3, 2);
        H2F h2_efficiencyVsPurityBSTClusterHitLevel = new H2F("efficiencyVsPurityBSTClusterHitLevel", "efficiency vs purity BSTCluster at hit level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTClusterHitLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTClusterHitLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTCluster.addDataSet(h2_efficiencyVsPurityBSTClusterHitLevel, 0);
        H2F h2_efficiencyVsPurityBSTClusterClusterLevel = new H2F("efficiencyVsPurityBSTClusterClusterLevel", "efficiency vs purity BSTCluster at cluster level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTClusterClusterLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTClusterClusterLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTCluster.addDataSet(h2_efficiencyVsPurityBSTClusterClusterLevel, 1);   
        H2F h2_efficiencyVsPurityBSTClusterCrossLevel = new H2F("efficiencyVsPurityBSTClusterCrossLevel", "efficiency vs purity BSTCluster at cross level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTClusterCrossLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTClusterCrossLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTCluster.addDataSet(h2_efficiencyVsPurityBSTClusterCrossLevel, 2);   
        H2F h2_efficiencyVsPurityBSTClusterSeedLevel = new H2F("efficiencyVsPurityBSTClusterSeedLevel", "efficiency vs purity BSTCluster at seed level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTClusterSeedLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTClusterSeedLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTCluster.addDataSet(h2_efficiencyVsPurityBSTClusterSeedLevel, 3);   
        H2F h2_efficiencyVsPurityBSTClusterTrackLevel = new H2F("efficiencyVsPurityBSTClusterTrackLevel", "efficiency vs purity BSTCluster at track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTClusterTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTClusterTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTCluster.addDataSet(h2_efficiencyVsPurityBSTClusterTrackLevel, 4);
        H2F h2_efficiencyVsPurityBSTClusterValidTrackLevel = new H2F("efficiencyVsPurityBSTClusterValidTrackLevel", "efficiency vs purity BSTCluster at valid track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTClusterValidTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTClusterValidTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTCluster.addDataSet(h2_efficiencyVsPurityBSTClusterValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupEfficiencyVsPurityBSTCluster.getName(), histoGroupEfficiencyVsPurityBSTCluster);         
        HistoGroup histoGroupEfficiencyVsPurityBSTCross = new HistoGroup("efficiencyVsPurityBSTCross", 3, 2);
        H2F h2_efficiencyVsPurityBSTCrossHitLevel = new H2F("efficiencyVsPurityBSTCrossHitLevel", "efficiency vs purity BSTCross at hit level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTCrossHitLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTCrossHitLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTCross.addDataSet(h2_efficiencyVsPurityBSTCrossHitLevel, 0);
        H2F h2_efficiencyVsPurityBSTCrossClusterLevel = new H2F("efficiencyVsPurityBSTCrossClusterLevel", "efficiency vs purity BSTCross at cluster level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTCrossClusterLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTCrossClusterLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTCross.addDataSet(h2_efficiencyVsPurityBSTCrossClusterLevel, 1);   
        H2F h2_efficiencyVsPurityBSTCrossCrossLevel = new H2F("efficiencyVsPurityBSTCrossCrossLevel", "efficiency vs purity BSTCross at cross level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTCrossCrossLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTCrossCrossLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTCross.addDataSet(h2_efficiencyVsPurityBSTCrossCrossLevel, 2);   
        H2F h2_efficiencyVsPurityBSTCrossSeedLevel = new H2F("efficiencyVsPurityBSTCrossSeedLevel", "efficiency vs purity BSTCross at seed level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTCrossSeedLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTCrossSeedLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTCross.addDataSet(h2_efficiencyVsPurityBSTCrossSeedLevel, 3);   
        H2F h2_efficiencyVsPurityBSTCrossTrackLevel = new H2F("efficiencyVsPurityBSTCrossTrackLevel", "efficiency vs purity BSTCross at track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTCrossTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTCrossTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTCross.addDataSet(h2_efficiencyVsPurityBSTCrossTrackLevel, 4);
        H2F h2_efficiencyVsPurityBSTCrossValidTrackLevel = new H2F("efficiencyVsPurityBSTCrossValidTrackLevel", "efficiency vs purity BSTCross at valid track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBSTCrossValidTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityBSTCrossValidTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBSTCross.addDataSet(h2_efficiencyVsPurityBSTCrossValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupEfficiencyVsPurityBSTCross.getName(), histoGroupEfficiencyVsPurityBSTCross);                                          

        HistoGroup histoGroupEfficiencyBMTHit = new HistoGroup("efficiencyBMTHit", 3, 2);
        H1F h1_efficiencyBMTHitHitLevel = new H1F("efficiencyBMTHitHitLevel", "efficiencyBMTHit at hit level", 30, 0, 1.01);
        h1_efficiencyBMTHitHitLevel.setTitleX("efficiency");
        h1_efficiencyBMTHitHitLevel.setTitleY("Counts");
        h1_efficiencyBMTHitHitLevel.setLineColor(1);
        histoGroupEfficiencyBMTHit.addDataSet(h1_efficiencyBMTHitHitLevel, 0);
        //histoGroupEfficiencyBMTHit.addDataSet(h1_efficiencyBMTHitHitLevel, 5);
        H1F h1_efficiencyBMTHitClusterLevel = new H1F("efficiencyBMTHitClusterLevel", "efficiencyBMTHit at cluster level", 30, 0, 1.01);
        h1_efficiencyBMTHitClusterLevel.setTitleX("efficiency");
        h1_efficiencyBMTHitClusterLevel.setTitleY("Counts");
        h1_efficiencyBMTHitClusterLevel.setLineColor(2);
        histoGroupEfficiencyBMTHit.addDataSet(h1_efficiencyBMTHitClusterLevel, 1);   
        //histoGroupEfficiencyBMTHit.addDataSet(h1_efficiencyBMTHitClusterLevel, 5);
        H1F h1_efficiencyBMTHitCrossLevel = new H1F("efficiencyBMTHitCrossLevel", "efficiencyBMTHit at cross level", 30, 0, 1.01);
        h1_efficiencyBMTHitCrossLevel.setTitleX("efficiency");
        h1_efficiencyBMTHitCrossLevel.setTitleY("Counts");
        h1_efficiencyBMTHitCrossLevel.setLineColor(3);
        histoGroupEfficiencyBMTHit.addDataSet(h1_efficiencyBMTHitCrossLevel, 2);   
        //histoGroupEfficiencyBMTHit.addDataSet(h1_efficiencyBMTHitCrossLevel, 5); 
        H1F h1_efficiencyBMTHitSeedLevel = new H1F("efficiencyBMTHitSeedLevel", "efficiencyBMTHit at seed level", 30, 0, 1.01);
        h1_efficiencyBMTHitSeedLevel.setTitleX("efficiency");
        h1_efficiencyBMTHitSeedLevel.setTitleY("Counts");
        h1_efficiencyBMTHitSeedLevel.setLineColor(4);
        histoGroupEfficiencyBMTHit.addDataSet(h1_efficiencyBMTHitSeedLevel, 3);   
        //histoGroupEfficiencyBMTHit.addDataSet(h1_efficiencyBMTHitSeedLevel, 5);
        H1F h1_efficiencyBMTHitTrackLevel = new H1F("efficiencyBMTHitTrackLevel", "efficiencyBMTHit at track level", 30, 0, 1.01);
        h1_efficiencyBMTHitTrackLevel.setTitleX("efficiency");
        h1_efficiencyBMTHitTrackLevel.setTitleY("Counts");
        h1_efficiencyBMTHitTrackLevel.setLineColor(5);
        histoGroupEfficiencyBMTHit.addDataSet(h1_efficiencyBMTHitTrackLevel, 4);
        //histoGroupEfficiencyBMTHit.addDataSet(h1_efficiencyBMTHitTrackLevel, 5);
        H1F h1_efficiencyBMTHitValidTrackLevel = new H1F("efficiencyBMTHitValidTrackLevel", "efficiencyBMTHit at valid track level", 30, 0, 1.01);
        h1_efficiencyBMTHitValidTrackLevel.setTitleX("efficiency");
        h1_efficiencyBMTHitValidTrackLevel.setTitleY("Counts");
        h1_efficiencyBMTHitValidTrackLevel.setLineColor(6);
        histoGroupEfficiencyBMTHit.addDataSet(h1_efficiencyBMTHitValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupEfficiencyBMTHit.getName(), histoGroupEfficiencyBMTHit);        
        HistoGroup histoGroupEfficiencyBMTCluster = new HistoGroup("efficiencyBMTCluster", 3, 2);
        H1F h1_efficiencyBMTClusterHitLevel = new H1F("efficiencyBMTClusterHitLevel", "efficiencyBMTCluster at hit level", 30, 0, 1.01);
        h1_efficiencyBMTClusterHitLevel.setTitleX("efficiency");
        h1_efficiencyBMTClusterHitLevel.setTitleY("Counts");
        h1_efficiencyBMTClusterHitLevel.setLineColor(1);
        histoGroupEfficiencyBMTCluster.addDataSet(h1_efficiencyBMTClusterHitLevel, 0); 
        //histoGroupEfficiencyBMTCluster.addDataSet(h1_efficiencyBMTClusterHitLevel, 5);   
        H1F h1_efficiencyBMTClusterClusterLevel = new H1F("efficiencyBMTClusterClusterLevel", "efficiencyBMTCluster at cluster level", 30, 0, 1.01);
        h1_efficiencyBMTClusterClusterLevel.setTitleX("efficiency");
        h1_efficiencyBMTClusterClusterLevel.setTitleY("Counts");
        h1_efficiencyBMTClusterClusterLevel.setLineColor(2);
        histoGroupEfficiencyBMTCluster.addDataSet(h1_efficiencyBMTClusterClusterLevel, 1);         
        //histoGroupEfficiencyBMTCluster.addDataSet(h1_efficiencyBMTClusterClusterLevel, 5); 
        H1F h1_efficiencyBMTClusterCrossLevel = new H1F("efficiencyBMTClusterCrossLevel", "efficiencyBMTCluster at cross level", 30, 0, 1.01);
        h1_efficiencyBMTClusterCrossLevel.setTitleX("efficiency");
        h1_efficiencyBMTClusterCrossLevel.setTitleY("Counts");
        h1_efficiencyBMTClusterCrossLevel.setLineColor(3);
        histoGroupEfficiencyBMTCluster.addDataSet(h1_efficiencyBMTClusterCrossLevel, 2);  
        //histoGroupEfficiencyBMTCluster.addDataSet(h1_efficiencyBMTClusterCrossLevel, 5); 
        H1F h1_efficiencyBMTClusterSeedLevel = new H1F("efficiencyBMTClusterSeedLevel", "efficiencyBMTCluster at seed level", 30, 0, 1.01);
        h1_efficiencyBMTClusterSeedLevel.setTitleX("efficiency");
        h1_efficiencyBMTClusterSeedLevel.setTitleY("Counts");
        h1_efficiencyBMTClusterSeedLevel.setLineColor(4);
        histoGroupEfficiencyBMTCluster.addDataSet(h1_efficiencyBMTClusterSeedLevel, 3); 
        //histoGroupEfficiencyBMTCluster.addDataSet(h1_efficiencyBMTClusterSeedLevel, 5); 
        H1F h1_efficiencyBMTClusterTrackLevel = new H1F("efficiencyBMTClusterTrackLevel", "efficiencyBMTCluster at track level", 30, 0, 1.01);
        h1_efficiencyBMTClusterTrackLevel.setTitleX("efficiency");
        h1_efficiencyBMTClusterTrackLevel.setTitleY("Counts");
        h1_efficiencyBMTClusterTrackLevel.setLineColor(5);
        histoGroupEfficiencyBMTCluster.addDataSet(h1_efficiencyBMTClusterTrackLevel, 4); 
        //histoGroupEfficiencyBMTCluster.addDataSet(h1_efficiencyBMTClusterTrackLevel, 5);  
        H1F h1_efficiencyBMTClusterValidTrackLevel = new H1F("efficiencyBMTClusterValidTrackLevel", "efficiencyBMTCluster at valid track level", 30, 0, 1.01);
        h1_efficiencyBMTClusterValidTrackLevel.setTitleX("efficiency");
        h1_efficiencyBMTClusterValidTrackLevel.setTitleY("Counts");
        h1_efficiencyBMTClusterValidTrackLevel.setLineColor(6);
        histoGroupEfficiencyBMTCluster.addDataSet(h1_efficiencyBMTClusterValidTrackLevel, 5);         
        histoGroupMap.put(histoGroupEfficiencyBMTCluster.getName(), histoGroupEfficiencyBMTCluster);          
        HistoGroup histoGroupEfficiencyBMTCross = new HistoGroup("efficiencyBMTCross", 3, 2);
        H1F h1_efficiencyBMTCrossHitLevel = new H1F("efficiencyBMTCrossHitLevel", "efficiencyBMTCross at hit level", 30, 0, 1.01);
        h1_efficiencyBMTCrossHitLevel.setTitleX("efficiency");
        h1_efficiencyBMTCrossHitLevel.setTitleY("Counts");
        h1_efficiencyBMTCrossHitLevel.setLineColor(1);
        histoGroupEfficiencyBMTCross.addDataSet(h1_efficiencyBMTCrossHitLevel, 0);  
        //histoGroupEfficiencyBMTCross.addDataSet(h1_efficiencyBMTCrossHitLevel, 5); 
        H1F h1_efficiencyBMTCrossClusterLevel = new H1F("efficiencyBMTCrossClusterLevel", "efficiencyBMTCross at cluster level", 30, 0, 1.01);
        h1_efficiencyBMTCrossClusterLevel.setTitleX("efficiency");
        h1_efficiencyBMTCrossClusterLevel.setTitleY("Counts");
        h1_efficiencyBMTCrossClusterLevel.setLineColor(2);
        histoGroupEfficiencyBMTCross.addDataSet(h1_efficiencyBMTCrossClusterLevel, 1);
        //histoGroupEfficiencyBMTCross.addDataSet(h1_efficiencyBMTCrossClusterLevel, 5); 
        H1F h1_efficiencyBMTCrossCrossLevel = new H1F("efficiencyBMTCrossCrossLevel", "efficiencyBMTCross at cross level", 30, 0, 1.01);
        h1_efficiencyBMTCrossCrossLevel.setTitleX("efficiency");
        h1_efficiencyBMTCrossCrossLevel.setTitleY("Counts");
        h1_efficiencyBMTCrossCrossLevel.setLineColor(3);
        histoGroupEfficiencyBMTCross.addDataSet(h1_efficiencyBMTCrossCrossLevel, 2);  
        //histoGroupEfficiencyBMTCross.addDataSet(h1_efficiencyBMTCrossCrossLevel, 5); 
        H1F h1_efficiencyBMTCrossSeedLevel = new H1F("efficiencyBMTCrossSeedLevel", "efficiencyBMTCross at seed level", 30, 0, 1.01);
        h1_efficiencyBMTCrossSeedLevel.setTitleX("efficiency");
        h1_efficiencyBMTCrossSeedLevel.setTitleY("Counts");
        h1_efficiencyBMTCrossSeedLevel.setLineColor(4);
        histoGroupEfficiencyBMTCross.addDataSet(h1_efficiencyBMTCrossSeedLevel, 3); 
        //histoGroupEfficiencyBMTCross.addDataSet(h1_efficiencyBMTCrossSeedLevel, 5); 
        H1F h1_efficiencyBMTCrossTrackLevel = new H1F("efficiencyBMTCrossTrackLevel", "efficiencyBMTCross at track level", 30, 0, 1.01);
        h1_efficiencyBMTCrossTrackLevel.setTitleX("efficiency");
        h1_efficiencyBMTCrossTrackLevel.setTitleY("Counts");
        h1_efficiencyBMTCrossTrackLevel.setLineColor(5);
        histoGroupEfficiencyBMTCross.addDataSet(h1_efficiencyBMTCrossTrackLevel, 4); 
        //histoGroupEfficiencyBMTCross.addDataSet(h1_efficiencyBMTCrossTrackLevel, 5);  
        H1F h1_efficiencyBMTCrossValidTrackLevel = new H1F("efficiencyBMTCrossValidTrackLevel", "efficiencyBMTCross at valid track level", 30, 0, 1.01);
        h1_efficiencyBMTCrossValidTrackLevel.setTitleX("efficiency");
        h1_efficiencyBMTCrossValidTrackLevel.setTitleY("Counts");
        h1_efficiencyBMTCrossValidTrackLevel.setLineColor(6);
        histoGroupEfficiencyBMTCross.addDataSet(h1_efficiencyBMTCrossValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupEfficiencyBMTCross.getName(), histoGroupEfficiencyBMTCross);         
        HistoGroup histoGroupPurityBMTHit = new HistoGroup("purityBMTHit", 3, 2);
        H1F h1_purityBMTHitHitLevel = new H1F("purityBMTHitHitLevel", "purityBMTHit at hit level", 30, 0, 1.01);
        h1_purityBMTHitHitLevel.setTitleX("purity");
        h1_purityBMTHitHitLevel.setTitleY("Counts");
        h1_purityBMTHitHitLevel.setLineColor(1);
        histoGroupPurityBMTHit.addDataSet(h1_purityBMTHitHitLevel, 0); 
        //histoGroupPurityBMTHit.addDataSet(h1_purityBMTHitHitLevel, 5);
        H1F h1_purityBMTHitClusterLevel = new H1F("purityBMTHitClusterLevel", "purityBMTHit at cluster level", 30, 0, 1.01);
        h1_purityBMTHitClusterLevel.setTitleX("purity");
        h1_purityBMTHitClusterLevel.setTitleY("Counts");
        h1_purityBMTHitClusterLevel.setLineColor(2);
        histoGroupPurityBMTHit.addDataSet(h1_purityBMTHitClusterLevel, 1); 
        //histoGroupPurityBMTHit.addDataSet(h1_purityBMTHitClusterLevel, 5);        
        H1F h1_purityBMTHitCrossLevel = new H1F("purityBMTHitCrossLevel", "purityBMTHit at cross level", 30, 0, 1.01);
        h1_purityBMTHitCrossLevel.setTitleX("purity");
        h1_purityBMTHitCrossLevel.setTitleY("Counts");
        h1_purityBMTHitCrossLevel.setLineColor(3);
        histoGroupPurityBMTHit.addDataSet(h1_purityBMTHitCrossLevel, 2); 
        //histoGroupPurityBMTHit.addDataSet(h1_purityBMTHitCrossLevel, 5);
        H1F h1_purityBMTHitSeedLevel = new H1F("purityBMTHitSeedLevel", "purityBMTHit at seed level", 30, 0, 1.01);
        h1_purityBMTHitSeedLevel.setTitleX("purity");
        h1_purityBMTHitSeedLevel.setTitleY("Counts");
        h1_purityBMTHitSeedLevel.setLineColor(4);
        histoGroupPurityBMTHit.addDataSet(h1_purityBMTHitSeedLevel, 3); 
        //histoGroupPurityBMTHit.addDataSet(h1_purityBMTHitSeedLevel, 5);
        H1F h1_purityBMTHitTrackLevel = new H1F("purityBMTHitTrackLevel", "purityBMTHit at track level", 30, 0, 1.01);
        h1_purityBMTHitTrackLevel.setTitleX("purity");
        h1_purityBMTHitTrackLevel.setTitleY("Counts");
        h1_purityBMTHitTrackLevel.setLineColor(5);
        histoGroupPurityBMTHit.addDataSet(h1_purityBMTHitTrackLevel, 4);
        //histoGroupPurityBMTHit.addDataSet(h1_purityBMTHitTrackLevel, 5);
        H1F h1_purityBMTHitValidTrackLevel = new H1F("purityBMTHitValidTrackLevel", "purityBMTHit at valid track level", 30, 0, 1.01);
        h1_purityBMTHitValidTrackLevel.setTitleX("purity");
        h1_purityBMTHitValidTrackLevel.setTitleY("Counts");
        h1_purityBMTHitValidTrackLevel.setLineColor(6);
        histoGroupPurityBMTHit.addDataSet(h1_purityBMTHitValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupPurityBMTHit.getName(), histoGroupPurityBMTHit);        
        HistoGroup histoGroupPurityBMTCluster = new HistoGroup("purityBMTCluster", 3, 2);
        H1F h1_purityBMTClusterHitLevel = new H1F("purityBMTClusterHitLevel", "purityBMTCluster at hit level", 30, 0, 1.01);
        h1_purityBMTClusterHitLevel.setTitleX("purity");
        h1_purityBMTClusterHitLevel.setTitleY("Counts");
        h1_purityBMTClusterHitLevel.setLineColor(1);
        histoGroupPurityBMTCluster.addDataSet(h1_purityBMTClusterHitLevel, 0);
        //histoGroupPurityBMTCluster.addDataSet(h1_purityBMTClusterHitLevel, 5);
        H1F h1_purityBMTClusterClusterLevel = new H1F("purityBMTClusterClusterLevel", "purityBMTCluster at cluster level", 30, 0, 1.01);
        h1_purityBMTClusterClusterLevel.setTitleX("purity");
        h1_purityBMTClusterClusterLevel.setTitleY("Counts");
        h1_purityBMTClusterClusterLevel.setLineColor(2);
        histoGroupPurityBMTCluster.addDataSet(h1_purityBMTClusterClusterLevel, 1);
        //histoGroupPurityBMTCluster.addDataSet(h1_purityBMTClusterClusterLevel, 5);
        H1F h1_purityBMTClusterCrossLevel = new H1F("purityBMTClusterCrossLevel", "purityBMTCluster at cross level", 30, 0, 1.01);
        h1_purityBMTClusterCrossLevel.setTitleX("purity");
        h1_purityBMTClusterCrossLevel.setTitleY("Counts");
        h1_purityBMTClusterCrossLevel.setLineColor(3);
        histoGroupPurityBMTCluster.addDataSet(h1_purityBMTClusterCrossLevel, 2); 
        //histoGroupPurityBMTCluster.addDataSet(h1_purityBMTClusterCrossLevel, 5);
        H1F h1_purityBMTClusterSeedLevel = new H1F("purityBMTClusterSeedLevel", "purityBMTCluster at seed level", 30, 0, 1.01);
        h1_purityBMTClusterSeedLevel.setTitleX("purity");
        h1_purityBMTClusterSeedLevel.setTitleY("Counts");
        h1_purityBMTClusterSeedLevel.setLineColor(4);
        histoGroupPurityBMTCluster.addDataSet(h1_purityBMTClusterSeedLevel, 3); 
        //histoGroupPurityBMTCluster.addDataSet(h1_purityBMTClusterSeedLevel, 5);
        H1F h1_purityBMTClusterTrackLevel = new H1F("purityBMTClusterTrackLevel", "purityBMTCluster at track level", 30, 0, 1.01);
        h1_purityBMTClusterTrackLevel.setTitleX("purity");
        h1_purityBMTClusterTrackLevel.setTitleY("Counts");
        h1_purityBMTClusterTrackLevel.setLineColor(5);
        histoGroupPurityBMTCluster.addDataSet(h1_purityBMTClusterTrackLevel, 4); 
        //histoGroupPurityBMTCluster.addDataSet(h1_purityBMTClusterTrackLevel, 5); 
        H1F h1_purityBMTClusterValidTrackLevel = new H1F("purityBMTClusterValidTrackLevel", "purityBMTCluster at valid track level", 30, 0, 1.01);
        h1_purityBMTClusterValidTrackLevel.setTitleX("purity");
        h1_purityBMTClusterValidTrackLevel.setTitleY("Counts");
        h1_purityBMTClusterValidTrackLevel.setLineColor(6);
        histoGroupPurityBMTCluster.addDataSet(h1_purityBMTClusterValidTrackLevel, 5);          
        histoGroupMap.put(histoGroupPurityBMTCluster.getName(), histoGroupPurityBMTCluster);          
        HistoGroup histoGroupPurityBMTCross = new HistoGroup("purityBMTCross", 3, 2);
        H1F h1_purityBMTCrossHitLevel = new H1F("purityBMTCrossHitLevel", "purityBMTCross at hit level", 30, 0, 1.01);
        h1_purityBMTCrossHitLevel.setTitleX("purity");
        h1_purityBMTCrossHitLevel.setTitleY("Counts");
        h1_purityBMTCrossHitLevel.setLineColor(1);
        histoGroupPurityBMTCross.addDataSet(h1_purityBMTCrossHitLevel, 0);
        //histoGroupPurityBMTCross.addDataSet(h1_purityBMTCrossHitLevel, 5); 
        H1F h1_purityBMTCrossClusterLevel = new H1F("purityBMTCrossClusterLevel", "purityBMTCross at cluster level", 30, 0, 1.01);
        h1_purityBMTCrossClusterLevel.setTitleX("purity");
        h1_purityBMTCrossClusterLevel.setTitleY("Counts");
        h1_purityBMTCrossClusterLevel.setLineColor(2);
        histoGroupPurityBMTCross.addDataSet(h1_purityBMTCrossClusterLevel, 1); 
        //histoGroupPurityBMTCross.addDataSet(h1_purityBMTCrossClusterLevel, 5);
        H1F h1_purityBMTCrossCrossLevel = new H1F("purityBMTCrossCrossLevel", "purityBMTCross at cross level", 30, 0, 1.01);
        h1_purityBMTCrossCrossLevel.setTitleX("purity");
        h1_purityBMTCrossCrossLevel.setTitleY("Counts");
        h1_purityBMTCrossCrossLevel.setLineColor(3);
        histoGroupPurityBMTCross.addDataSet(h1_purityBMTCrossCrossLevel, 2); 
        //histoGroupPurityBMTCross.addDataSet(h1_purityBMTCrossCrossLevel, 5);
        H1F h1_purityBMTCrossSeedLevel = new H1F("purityBMTCrossSeedLevel", "purityBMTCross at seed level", 30, 0, 1.01);
        h1_purityBMTCrossSeedLevel.setTitleX("purity");
        h1_purityBMTCrossSeedLevel.setTitleY("Counts");
        h1_purityBMTCrossSeedLevel.setLineColor(4);
        histoGroupPurityBMTCross.addDataSet(h1_purityBMTCrossSeedLevel, 3);
        //histoGroupPurityBMTCross.addDataSet(h1_purityBMTCrossSeedLevel, 5);
        H1F h1_purityBMTCrossTrackLevel = new H1F("purityBMTCrossTrackLevel", "purityBMTCross at track level", 30, 0, 1.01);
        h1_purityBMTCrossTrackLevel.setTitleX("purity");
        h1_purityBMTCrossTrackLevel.setTitleY("Counts");
        h1_purityBMTCrossTrackLevel.setLineColor(5);
        histoGroupPurityBMTCross.addDataSet(h1_purityBMTCrossTrackLevel, 4); 
        //histoGroupPurityBMTCross.addDataSet(h1_purityBMTCrossTrackLevel, 5);         
        H1F h1_purityBMTCrossValidTrackLevel = new H1F("purityBMTCrossValidTrackLevel", "purityBMTCross at valid track level", 30, 0, 1.01);
        h1_purityBMTCrossValidTrackLevel.setTitleX("purity");
        h1_purityBMTCrossValidTrackLevel.setTitleY("Counts");
        h1_purityBMTCrossValidTrackLevel.setLineColor(6);
        histoGroupPurityBMTCross.addDataSet(h1_purityBMTCrossValidTrackLevel, 5);           
        histoGroupMap.put(histoGroupPurityBMTCross.getName(), histoGroupPurityBMTCross); 
        HistoGroup histoGroupEfficiencyVsPurityBMTHit = new HistoGroup("efficiencyVsPurityBMTHit", 3, 2);
        H2F h2_efficiencyVsPurityBMTHitHitLevel = new H2F("efficiencyVsPurityBMTHitHitLevel", "efficiency vs purity BMTHit at hit level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTHitHitLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTHitHitLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTHit.addDataSet(h2_efficiencyVsPurityBMTHitHitLevel, 0);
        H2F h2_efficiencyVsPurityBMTHitClusterLevel = new H2F("efficiencyVsPurityBMTHitClusterLevel", "efficiency vs purity BMTHit at cluster level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTHitClusterLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTHitClusterLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTHit.addDataSet(h2_efficiencyVsPurityBMTHitClusterLevel, 1);   
        H2F h2_efficiencyVsPurityBMTHitCrossLevel = new H2F("efficiencyVsPurityBMTHitCrossLevel", "efficiency vs purity BMTHit at cross level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTHitCrossLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTHitCrossLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTHit.addDataSet(h2_efficiencyVsPurityBMTHitCrossLevel, 2);   
        H2F h2_efficiencyVsPurityBMTHitSeedLevel = new H2F("efficiencyVsPurityBMTHitSeedLevel", "efficiency vs purity BMTHit at seed level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTHitSeedLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTHitSeedLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTHit.addDataSet(h2_efficiencyVsPurityBMTHitSeedLevel, 3);   
        H2F h2_efficiencyVsPurityBMTHitTrackLevel = new H2F("efficiencyVsPurityBMTHitTrackLevel", "efficiency vs purity BMTHit at track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTHitTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTHitTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTHit.addDataSet(h2_efficiencyVsPurityBMTHitTrackLevel, 4);
        H2F h2_efficiencyVsPurityBMTHitValidTrackLevel = new H2F("efficiencyVsPurityBMTHitValidTrackLevel", "efficiency vs purity BMTHit at valid track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTHitValidTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTHitValidTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTHit.addDataSet(h2_efficiencyVsPurityBMTHitValidTrackLevel, 5);
        histoGroupMap.put(histoGroupEfficiencyVsPurityBMTHit.getName(), histoGroupEfficiencyVsPurityBMTHit);         
        HistoGroup histoGroupEfficiencyVsPurityBMTCluster = new HistoGroup("efficiencyVsPurityBMTCluster", 3, 2);
        H2F h2_efficiencyVsPurityBMTClusterHitLevel = new H2F("efficiencyVsPurityBMTClusterHitLevel", "efficiency vs purity BMTCluster at hit level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTClusterHitLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTClusterHitLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTCluster.addDataSet(h2_efficiencyVsPurityBMTClusterHitLevel, 0);
        H2F h2_efficiencyVsPurityBMTClusterClusterLevel = new H2F("efficiencyVsPurityBMTClusterClusterLevel", "efficiency vs purity BMTCluster at cluster level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTClusterClusterLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTClusterClusterLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTCluster.addDataSet(h2_efficiencyVsPurityBMTClusterClusterLevel, 1);   
        H2F h2_efficiencyVsPurityBMTClusterCrossLevel = new H2F("efficiencyVsPurityBMTClusterCrossLevel", "efficiency vs purity BMTCluster at cross level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTClusterCrossLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTClusterCrossLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTCluster.addDataSet(h2_efficiencyVsPurityBMTClusterCrossLevel, 2);   
        H2F h2_efficiencyVsPurityBMTClusterSeedLevel = new H2F("efficiencyVsPurityBMTClusterSeedLevel", "efficiency vs purity BMTCluster at seed level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTClusterSeedLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTClusterSeedLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTCluster.addDataSet(h2_efficiencyVsPurityBMTClusterSeedLevel, 3);   
        H2F h2_efficiencyVsPurityBMTClusterTrackLevel = new H2F("efficiencyVsPurityBMTClusterTrackLevel", "efficiency vs purity BMTCluster at track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTClusterTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTClusterTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTCluster.addDataSet(h2_efficiencyVsPurityBMTClusterTrackLevel, 4);
        H2F h2_efficiencyVsPurityBMTClusterValidTrackLevel = new H2F("efficiencyVsPurityBMTClusterValidTrackLevel", "efficiency vs purity BMTCluster at valid track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTClusterValidTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTClusterValidTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTCluster.addDataSet(h2_efficiencyVsPurityBMTClusterValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupEfficiencyVsPurityBMTCluster.getName(), histoGroupEfficiencyVsPurityBMTCluster);         
        HistoGroup histoGroupEfficiencyVsPurityBMTCross = new HistoGroup("efficiencyVsPurityBMTCross", 3, 2);
        H2F h2_efficiencyVsPurityBMTCrossHitLevel = new H2F("efficiencyVsPurityBMTCrossHitLevel", "efficiency vs purity BMTCross at hit level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTCrossHitLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTCrossHitLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTCross.addDataSet(h2_efficiencyVsPurityBMTCrossHitLevel, 0);
        H2F h2_efficiencyVsPurityBMTCrossClusterLevel = new H2F("efficiencyVsPurityBMTCrossClusterLevel", "efficiency vs purity BMTCross at cluster level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTCrossClusterLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTCrossClusterLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTCross.addDataSet(h2_efficiencyVsPurityBMTCrossClusterLevel, 1);   
        H2F h2_efficiencyVsPurityBMTCrossCrossLevel = new H2F("efficiencyVsPurityBMTCrossCrossLevel", "efficiency vs purity BMTCross at cross level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTCrossCrossLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTCrossCrossLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTCross.addDataSet(h2_efficiencyVsPurityBMTCrossCrossLevel, 2);   
        H2F h2_efficiencyVsPurityBMTCrossSeedLevel = new H2F("efficiencyVsPurityBMTCrossSeedLevel", "efficiency vs purity BMTCross at seed level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTCrossSeedLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTCrossSeedLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTCross.addDataSet(h2_efficiencyVsPurityBMTCrossSeedLevel, 3);   
        H2F h2_efficiencyVsPurityBMTCrossTrackLevel = new H2F("efficiencyVsPurityBMTCrossTrackLevel", "efficiency vs purity BMTCross at track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTCrossTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTCrossTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTCross.addDataSet(h2_efficiencyVsPurityBMTCrossTrackLevel, 4);
        H2F h2_efficiencyVsPurityBMTCrossValidTrackLevel = new H2F("efficiencyVsPurityBMTCrossValidTrackLevel", "efficiency vs purity BMTCross at valid track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityBMTCrossValidTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityBMTCrossValidTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityBMTCross.addDataSet(h2_efficiencyVsPurityBMTCrossValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupEfficiencyVsPurityBMTCross.getName(), histoGroupEfficiencyVsPurityBMTCross);         
        
        HistoGroup histoGroupEfficiencyHit = new HistoGroup("efficiencyHit", 3, 2);
        H1F h1_efficiencyHitHitLevel = new H1F("efficiencyHitHitLevel", "efficiencyHit at hit level", 30, 0, 1.01);
        h1_efficiencyHitHitLevel.setTitleX("efficiency");
        h1_efficiencyHitHitLevel.setTitleY("Counts");
        h1_efficiencyHitHitLevel.setLineColor(1);
        histoGroupEfficiencyHit.addDataSet(h1_efficiencyHitHitLevel, 0);
        //histoGroupEfficiencyHit.addDataSet(h1_efficiencyHitHitLevel, 5);
        H1F h1_efficiencyHitClusterLevel = new H1F("efficiencyHitClusterLevel", "efficiencyHit at cluster level", 30, 0, 1.01);
        h1_efficiencyHitClusterLevel.setTitleX("efficiency");
        h1_efficiencyHitClusterLevel.setTitleY("Counts");
        h1_efficiencyHitClusterLevel.setLineColor(2);
        histoGroupEfficiencyHit.addDataSet(h1_efficiencyHitClusterLevel, 1);   
        //histoGroupEfficiencyHit.addDataSet(h1_efficiencyHitClusterLevel, 5);
        H1F h1_efficiencyHitCrossLevel = new H1F("efficiencyHitCrossLevel", "efficiencyHit at cross level", 30, 0, 1.01);
        h1_efficiencyHitCrossLevel.setTitleX("efficiency");
        h1_efficiencyHitCrossLevel.setTitleY("Counts");
        h1_efficiencyHitCrossLevel.setLineColor(3);
        histoGroupEfficiencyHit.addDataSet(h1_efficiencyHitCrossLevel, 2);   
        //histoGroupEfficiencyHit.addDataSet(h1_efficiencyHitCrossLevel, 5); 
        H1F h1_efficiencyHitSeedLevel = new H1F("efficiencyHitSeedLevel", "efficiencyHit at seed level", 30, 0, 1.01);
        h1_efficiencyHitSeedLevel.setTitleX("efficiency");
        h1_efficiencyHitSeedLevel.setTitleY("Counts");
        h1_efficiencyHitSeedLevel.setLineColor(4);
        histoGroupEfficiencyHit.addDataSet(h1_efficiencyHitSeedLevel, 3);   
        //histoGroupEfficiencyHit.addDataSet(h1_efficiencyHitSeedLevel, 5);
        H1F h1_efficiencyHitTrackLevel = new H1F("efficiencyHitTrackLevel", "efficiencyHit at track level", 30, 0, 1.01);
        h1_efficiencyHitTrackLevel.setTitleX("efficiency");
        h1_efficiencyHitTrackLevel.setTitleY("Counts");
        h1_efficiencyHitTrackLevel.setLineColor(5);
        histoGroupEfficiencyHit.addDataSet(h1_efficiencyHitTrackLevel, 4);
        //histoGroupEfficiencyHit.addDataSet(h1_efficiencyHitTrackLevel, 5);
        H1F h1_efficiencyHitValidTrackLevel = new H1F("efficiencyHitValidTrackLevel", "efficiencyHit at valid track level", 30, 0, 1.01);
        h1_efficiencyHitValidTrackLevel.setTitleX("efficiency");
        h1_efficiencyHitValidTrackLevel.setTitleY("Counts");
        h1_efficiencyHitValidTrackLevel.setLineColor(6);
        histoGroupEfficiencyHit.addDataSet(h1_efficiencyHitValidTrackLevel, 5);
        histoGroupMap.put(histoGroupEfficiencyHit.getName(), histoGroupEfficiencyHit);        
        HistoGroup histoGroupEfficiencyCluster = new HistoGroup("efficiencyCluster", 3, 2);
        H1F h1_efficiencyClusterHitLevel = new H1F("efficiencyClusterHitLevel", "efficiencyCluster at hit level", 30, 0, 1.01);
        h1_efficiencyClusterHitLevel.setTitleX("efficiency");
        h1_efficiencyClusterHitLevel.setTitleY("Counts");
        h1_efficiencyClusterHitLevel.setLineColor(1);
        histoGroupEfficiencyCluster.addDataSet(h1_efficiencyClusterHitLevel, 0); 
        //histoGroupEfficiencyCluster.addDataSet(h1_efficiencyClusterHitLevel, 5);   
        H1F h1_efficiencyClusterClusterLevel = new H1F("efficiencyClusterClusterLevel", "efficiencyCluster at cluster level", 30, 0, 1.01);
        h1_efficiencyClusterClusterLevel.setTitleX("efficiency");
        h1_efficiencyClusterClusterLevel.setTitleY("Counts");
        h1_efficiencyClusterClusterLevel.setLineColor(2);
        histoGroupEfficiencyCluster.addDataSet(h1_efficiencyClusterClusterLevel, 1);         
        //histoGroupEfficiencyCluster.addDataSet(h1_efficiencyClusterClusterLevel, 5); 
        H1F h1_efficiencyClusterCrossLevel = new H1F("efficiencyClusterCrossLevel", "efficiencyCluster at cross level", 30, 0, 1.01);
        h1_efficiencyClusterCrossLevel.setTitleX("efficiency");
        h1_efficiencyClusterCrossLevel.setTitleY("Counts");
        h1_efficiencyClusterCrossLevel.setLineColor(3);
        histoGroupEfficiencyCluster.addDataSet(h1_efficiencyClusterCrossLevel, 2);  
        //histoGroupEfficiencyCluster.addDataSet(h1_efficiencyClusterCrossLevel, 5); 
        H1F h1_efficiencyClusterSeedLevel = new H1F("efficiencyClusterSeedLevel", "efficiencyCluster at seed level", 30, 0, 1.01);
        h1_efficiencyClusterSeedLevel.setTitleX("efficiency");
        h1_efficiencyClusterSeedLevel.setTitleY("Counts");
        h1_efficiencyClusterSeedLevel.setLineColor(4);
        histoGroupEfficiencyCluster.addDataSet(h1_efficiencyClusterSeedLevel, 3); 
        //histoGroupEfficiencyCluster.addDataSet(h1_efficiencyClusterSeedLevel, 5); 
        H1F h1_efficiencyClusterTrackLevel = new H1F("efficiencyClusterTrackLevel", "efficiencyCluster at track level", 30, 0, 1.01);
        h1_efficiencyClusterTrackLevel.setTitleX("efficiency");
        h1_efficiencyClusterTrackLevel.setTitleY("Counts");
        h1_efficiencyClusterTrackLevel.setLineColor(5);
        histoGroupEfficiencyCluster.addDataSet(h1_efficiencyClusterTrackLevel, 4); 
        //histoGroupEfficiencyCluster.addDataSet(h1_efficiencyClusterTrackLevel, 5);  
        H1F h1_efficiencyClusterValidTrackLevel = new H1F("efficiencyClustervalidTrackLevel", "efficiencyCluster at valid track level", 30, 0, 1.01);
        h1_efficiencyClusterValidTrackLevel.setTitleX("efficiency");
        h1_efficiencyClusterValidTrackLevel.setTitleY("Counts");
        h1_efficiencyClusterValidTrackLevel.setLineColor(6);
        histoGroupEfficiencyCluster.addDataSet(h1_efficiencyClusterValidTrackLevel, 5);         
        histoGroupMap.put(histoGroupEfficiencyCluster.getName(), histoGroupEfficiencyCluster);          
        HistoGroup histoGroupEfficiencyCross = new HistoGroup("efficiencyCross", 3, 2);
        H1F h1_efficiencyCrossHitLevel = new H1F("efficiencyCrossHitLevel", "efficiencyCross at hit level", 30, 0, 1.01);
        h1_efficiencyCrossHitLevel.setTitleX("efficiency");
        h1_efficiencyCrossHitLevel.setTitleY("Counts");
        h1_efficiencyCrossHitLevel.setLineColor(1);
        histoGroupEfficiencyCross.addDataSet(h1_efficiencyCrossHitLevel, 0);  
        //histoGroupEfficiencyCross.addDataSet(h1_efficiencyCrossHitLevel, 5); 
        H1F h1_efficiencyCrossClusterLevel = new H1F("efficiencyCrossClusterLevel", "efficiencyCross at cluster level", 30, 0, 1.01);
        h1_efficiencyCrossClusterLevel.setTitleX("efficiency");
        h1_efficiencyCrossClusterLevel.setTitleY("Counts");
        h1_efficiencyCrossClusterLevel.setLineColor(2);
        histoGroupEfficiencyCross.addDataSet(h1_efficiencyCrossClusterLevel, 1);
        //histoGroupEfficiencyCross.addDataSet(h1_efficiencyCrossClusterLevel, 5); 
        H1F h1_efficiencyCrossCrossLevel = new H1F("efficiencyCrossCrossLevel", "efficiencyCross at cross level", 30, 0, 1.01);
        h1_efficiencyCrossCrossLevel.setTitleX("efficiency");
        h1_efficiencyCrossCrossLevel.setTitleY("Counts");
        h1_efficiencyCrossCrossLevel.setLineColor(3);
        histoGroupEfficiencyCross.addDataSet(h1_efficiencyCrossCrossLevel, 2);  
        //histoGroupEfficiencyCross.addDataSet(h1_efficiencyCrossCrossLevel, 5); 
        H1F h1_efficiencyCrossSeedLevel = new H1F("efficiencyCrossSeedLevel", "efficiencyCross at seed level", 30, 0, 1.01);
        h1_efficiencyCrossSeedLevel.setTitleX("efficiency");
        h1_efficiencyCrossSeedLevel.setTitleY("Counts");
        h1_efficiencyCrossSeedLevel.setLineColor(4);
        histoGroupEfficiencyCross.addDataSet(h1_efficiencyCrossSeedLevel, 3); 
        //histoGroupEfficiencyCross.addDataSet(h1_efficiencyCrossSeedLevel, 5); 
        H1F h1_efficiencyCrossTrackLevel = new H1F("efficiencyCrossTrackLevel", "efficiencyCross at track level", 30, 0, 1.01);
        h1_efficiencyCrossTrackLevel.setTitleX("efficiency");
        h1_efficiencyCrossTrackLevel.setTitleY("Counts");
        h1_efficiencyCrossTrackLevel.setLineColor(5);
        histoGroupEfficiencyCross.addDataSet(h1_efficiencyCrossTrackLevel, 4); 
        //histoGroupEfficiencyCross.addDataSet(h1_efficiencyCrossTrackLevel, 5);  
        H1F h1_efficiencyCrossValidTrackLevel = new H1F("efficiencyCrossValidTrackLevel", "efficiencyCross at valid track level", 30, 0, 1.01);
        h1_efficiencyCrossValidTrackLevel.setTitleX("efficiency");
        h1_efficiencyCrossValidTrackLevel.setTitleY("Counts");
        h1_efficiencyCrossValidTrackLevel.setLineColor(6);
        histoGroupEfficiencyCross.addDataSet(h1_efficiencyCrossValidTrackLevel, 5);          
        histoGroupMap.put(histoGroupEfficiencyCross.getName(), histoGroupEfficiencyCross);                         
        HistoGroup histoGroupPurityHit = new HistoGroup("purityHit", 3, 2);
        H1F h1_purityHitHitLevel = new H1F("purityHitHitLevel", "purityHit at hit level", 30, 0, 1.01);
        h1_purityHitHitLevel.setTitleX("purity");
        h1_purityHitHitLevel.setTitleY("Counts");
        h1_purityHitHitLevel.setLineColor(1);
        histoGroupPurityHit.addDataSet(h1_purityHitHitLevel, 0); 
        //histoGroupPurityHit.addDataSet(h1_purityHitHitLevel, 5);
        H1F h1_purityHitClusterLevel = new H1F("purityHitClusterLevel", "purityHit at cluster level", 30, 0, 1.01);
        h1_purityHitClusterLevel.setTitleX("purity");
        h1_purityHitClusterLevel.setTitleY("Counts");
        h1_purityHitClusterLevel.setLineColor(2);
        histoGroupPurityHit.addDataSet(h1_purityHitClusterLevel, 1); 
        //histoGroupPurityHit.addDataSet(h1_purityHitClusterLevel, 5);        
        H1F h1_purityHitCrossLevel = new H1F("purityHitCrossLevel", "purityHit at cross level", 30, 0, 1.01);
        h1_purityHitCrossLevel.setTitleX("purity");
        h1_purityHitCrossLevel.setTitleY("Counts");
        h1_purityHitCrossLevel.setLineColor(3);
        histoGroupPurityHit.addDataSet(h1_purityHitCrossLevel, 2); 
        //histoGroupPurityHit.addDataSet(h1_purityHitCrossLevel, 5);
        H1F h1_purityHitSeedLevel = new H1F("purityHitSeedLevel", "purityHit at seed level", 30, 0, 1.01);
        h1_purityHitSeedLevel.setTitleX("purity");
        h1_purityHitSeedLevel.setTitleY("Counts");
        h1_purityHitSeedLevel.setLineColor(4);
        histoGroupPurityHit.addDataSet(h1_purityHitSeedLevel, 3); 
        //histoGroupPurityHit.addDataSet(h1_purityHitSeedLevel, 5);
        H1F h1_purityHitTrackLevel = new H1F("purityHitTrackLevel", "purityHit at track level", 30, 0, 1.01);
        h1_purityHitTrackLevel.setTitleX("purity");
        h1_purityHitTrackLevel.setTitleY("Counts");
        h1_purityHitTrackLevel.setLineColor(5);
        histoGroupPurityHit.addDataSet(h1_purityHitTrackLevel, 4);
        //histoGroupPurityHit.addDataSet(h1_purityHitTrackLevel, 5);
        H1F h1_purityHitValidTrackLevel = new H1F("purityHitValidTrackLevel", "purityHit at valid track level", 30, 0, 1.01);
        h1_purityHitValidTrackLevel.setTitleX("purity");
        h1_purityHitValidTrackLevel.setTitleY("Counts");
        h1_purityHitValidTrackLevel.setLineColor(6);
        histoGroupPurityHit.addDataSet(h1_purityHitValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupPurityHit.getName(), histoGroupPurityHit);        
        HistoGroup histoGroupPurityCluster = new HistoGroup("purityCluster", 3, 2);
        H1F h1_purityClusterHitLevel = new H1F("purityClusterHitLevel", "purityCluster at hit level", 30, 0, 1.01);
        h1_purityClusterHitLevel.setTitleX("purity");
        h1_purityClusterHitLevel.setTitleY("Counts");
        h1_purityClusterHitLevel.setLineColor(1);
        histoGroupPurityCluster.addDataSet(h1_purityClusterHitLevel, 0);
        //histoGroupPurityCluster.addDataSet(h1_purityClusterHitLevel, 5);
        H1F h1_purityClusterClusterLevel = new H1F("purityClusterClusterLevel", "purityCluster at cluster level", 30, 0, 1.01);
        h1_purityClusterClusterLevel.setTitleX("purity");
        h1_purityClusterClusterLevel.setTitleY("Counts");
        h1_purityClusterClusterLevel.setLineColor(2);
        histoGroupPurityCluster.addDataSet(h1_purityClusterClusterLevel, 1);
        //histoGroupPurityCluster.addDataSet(h1_purityClusterClusterLevel, 5);
        H1F h1_purityClusterCrossLevel = new H1F("purityClusterCrossLevel", "purityCluster at cross level", 30, 0, 1.01);
        h1_purityClusterCrossLevel.setTitleX("purity");
        h1_purityClusterCrossLevel.setTitleY("Counts");
        h1_purityClusterCrossLevel.setLineColor(3);
        histoGroupPurityCluster.addDataSet(h1_purityClusterCrossLevel, 2); 
        //histoGroupPurityCluster.addDataSet(h1_purityClusterCrossLevel, 5);
        H1F h1_purityClusterSeedLevel = new H1F("purityClusterSeedLevel", "purityCluster at seed level", 30, 0, 1.01);
        h1_purityClusterSeedLevel.setTitleX("purity");
        h1_purityClusterSeedLevel.setTitleY("Counts");
        h1_purityClusterSeedLevel.setLineColor(4);
        histoGroupPurityCluster.addDataSet(h1_purityClusterSeedLevel, 3); 
        //histoGroupPurityCluster.addDataSet(h1_purityClusterSeedLevel, 5);
        H1F h1_purityClusterTrackLevel = new H1F("purityClusterTrackLevel", "purityCluster at track level", 30, 0, 1.01);
        h1_purityClusterTrackLevel.setTitleX("purity");
        h1_purityClusterTrackLevel.setTitleY("Counts");
        h1_purityClusterTrackLevel.setLineColor(5);
        histoGroupPurityCluster.addDataSet(h1_purityClusterTrackLevel, 4); 
        //histoGroupPurityCluster.addDataSet(h1_purityClusterTrackLevel, 5);  
        H1F h1_purityClusterValidTrackLevel = new H1F("purityClusterValidTrackLevel", "purityCluster at valid track level", 30, 0, 1.01);
        h1_purityClusterValidTrackLevel.setTitleX("purity");
        h1_purityClusterValidTrackLevel.setTitleY("Counts");
        h1_purityClusterValidTrackLevel.setLineColor(6);
        histoGroupPurityCluster.addDataSet(h1_purityClusterValidTrackLevel, 5);          
        histoGroupMap.put(histoGroupPurityCluster.getName(), histoGroupPurityCluster);          
        HistoGroup histoGroupPurityCross = new HistoGroup("purityCross", 3, 2);
        H1F h1_purityCrossHitLevel = new H1F("purityCrossHitLevel", "purityCross at hit level", 30, 0, 1.01);
        h1_purityCrossHitLevel.setTitleX("purity");
        h1_purityCrossHitLevel.setTitleY("Counts");
        h1_purityCrossHitLevel.setLineColor(1);
        histoGroupPurityCross.addDataSet(h1_purityCrossHitLevel, 0);
        //histoGroupPurityCross.addDataSet(h1_purityCrossHitLevel, 5); 
        H1F h1_purityCrossClusterLevel = new H1F("purityCrossClusterLevel", "purityCross at cluster level", 30, 0, 1.01);
        h1_purityCrossClusterLevel.setTitleX("purity");
        h1_purityCrossClusterLevel.setTitleY("Counts");
        h1_purityCrossClusterLevel.setLineColor(2);
        histoGroupPurityCross.addDataSet(h1_purityCrossClusterLevel, 1); 
        //histoGroupPurityCross.addDataSet(h1_purityCrossClusterLevel, 5);
        H1F h1_purityCrossCrossLevel = new H1F("purityCrossCrossLevel", "purityCross at cross level", 30, 0, 1.01);
        h1_purityCrossCrossLevel.setTitleX("purity");
        h1_purityCrossCrossLevel.setTitleY("Counts");
        h1_purityCrossCrossLevel.setLineColor(3);
        histoGroupPurityCross.addDataSet(h1_purityCrossCrossLevel, 2); 
        //histoGroupPurityCross.addDataSet(h1_purityCrossCrossLevel, 5);
        H1F h1_purityCrossSeedLevel = new H1F("purityCrossSeedLevel", "purityCross at seed level", 30, 0, 1.01);
        h1_purityCrossSeedLevel.setTitleX("purity");
        h1_purityCrossSeedLevel.setTitleY("Counts");
        h1_purityCrossSeedLevel.setLineColor(4);
        histoGroupPurityCross.addDataSet(h1_purityCrossSeedLevel, 3);
        //histoGroupPurityCross.addDataSet(h1_purityCrossSeedLevel, 5);
        H1F h1_purityCrossTrackLevel = new H1F("purityCrossTrackLevel", "purityCross at track level", 30, 0, 1.01);
        h1_purityCrossTrackLevel.setTitleX("purity");
        h1_purityCrossTrackLevel.setTitleY("Counts");
        h1_purityCrossTrackLevel.setLineColor(5);
        histoGroupPurityCross.addDataSet(h1_purityCrossTrackLevel, 4); 
        //histoGroupPurityCross.addDataSet(h1_purityCrossTrackLevel, 5); 
        H1F h1_purityCrossValidTrackLevel = new H1F("purityCrossValidTrackLevel", "purityCross at valid track level", 30, 0, 1.01);
        h1_purityCrossValidTrackLevel.setTitleX("purity");
        h1_purityCrossValidTrackLevel.setTitleY("Counts");
        h1_purityCrossValidTrackLevel.setLineColor(6);
        histoGroupPurityCross.addDataSet(h1_purityCrossValidTrackLevel, 5);         
        histoGroupMap.put(histoGroupPurityCross.getName(), histoGroupPurityCross);
        HistoGroup histoGroupEfficiencyVsPurityHit = new HistoGroup("efficiencyVsPurityHit", 3, 2);
        H2F h2_efficiencyVsPurityHitHitLevel = new H2F("efficiencyVsPurityHitHitLevel", "efficiency vs purity Hit at hit level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityHitHitLevel.setTitleX("purity");
        h2_efficiencyVsPurityHitHitLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityHit.addDataSet(h2_efficiencyVsPurityHitHitLevel, 0);
        H2F h2_efficiencyVsPurityHitClusterLevel = new H2F("efficiencyVsPurityHitClusterLevel", "efficiency vs purity Hit at cluster level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityHitClusterLevel.setTitleX("purity");
        h2_efficiencyVsPurityHitClusterLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityHit.addDataSet(h2_efficiencyVsPurityHitClusterLevel, 1);   
        H2F h2_efficiencyVsPurityHitCrossLevel = new H2F("efficiencyVsPurityHitCrossLevel", "efficiency vs purity Hit at cross level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityHitCrossLevel.setTitleX("purity");
        h2_efficiencyVsPurityHitCrossLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityHit.addDataSet(h2_efficiencyVsPurityHitCrossLevel, 2);   
        H2F h2_efficiencyVsPurityHitSeedLevel = new H2F("efficiencyVsPurityHitSeedLevel", "efficiency vs purity Hit at seed level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityHitSeedLevel.setTitleX("purity");
        h2_efficiencyVsPurityHitSeedLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityHit.addDataSet(h2_efficiencyVsPurityHitSeedLevel, 3);   
        H2F h2_efficiencyVsPurityHitTrackLevel = new H2F("efficiencyVsPurityHitTrackLevel", "efficiency vs purity Hit at track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityHitTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityHitTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityHit.addDataSet(h2_efficiencyVsPurityHitTrackLevel, 4);
        H2F h2_efficiencyVsPurityHitValidTrackLevel = new H2F("efficiencyVsPurityHitValidTrackLevel", "efficiency vs purity Hit at valid track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityHitValidTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityHitValidTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityHit.addDataSet(h2_efficiencyVsPurityHitValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupEfficiencyVsPurityHit.getName(), histoGroupEfficiencyVsPurityHit);         
        HistoGroup histoGroupEfficiencyVsPurityCluster = new HistoGroup("efficiencyVsPurityCluster", 3, 2);
        H2F h2_efficiencyVsPurityClusterHitLevel = new H2F("efficiencyVsPurityClusterHitLevel", "efficiency vs purity Cluster at hit level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityClusterHitLevel.setTitleX("purity");
        h2_efficiencyVsPurityClusterHitLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityCluster.addDataSet(h2_efficiencyVsPurityClusterHitLevel, 0);
        H2F h2_efficiencyVsPurityClusterClusterLevel = new H2F("efficiencyVsPurityClusterClusterLevel", "efficiency vs purity Cluster at cluster level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityClusterClusterLevel.setTitleX("purity");
        h2_efficiencyVsPurityClusterClusterLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityCluster.addDataSet(h2_efficiencyVsPurityClusterClusterLevel, 1);   
        H2F h2_efficiencyVsPurityClusterCrossLevel = new H2F("efficiencyVsPurityClusterCrossLevel", "efficiency vs purity Cluster at cross level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityClusterCrossLevel.setTitleX("purity");
        h2_efficiencyVsPurityClusterCrossLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityCluster.addDataSet(h2_efficiencyVsPurityClusterCrossLevel, 2);   
        H2F h2_efficiencyVsPurityClusterSeedLevel = new H2F("efficiencyVsPurityClusterSeedLevel", "efficiency vs purity Cluster at seed level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityClusterSeedLevel.setTitleX("purity");
        h2_efficiencyVsPurityClusterSeedLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityCluster.addDataSet(h2_efficiencyVsPurityClusterSeedLevel, 3);   
        H2F h2_efficiencyVsPurityClusterTrackLevel = new H2F("efficiencyVsPurityClusterTrackLevel", "efficiency vs purity Cluster at track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityClusterTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityClusterTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityCluster.addDataSet(h2_efficiencyVsPurityClusterTrackLevel, 4);
        H2F h2_efficiencyVsPurityClusterValidTrackLevel = new H2F("efficiencyVsPurityClusterValidTrackLevel", "efficiency vs purity Cluster at valid track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityClusterValidTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityClusterValidTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityCluster.addDataSet(h2_efficiencyVsPurityClusterValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupEfficiencyVsPurityCluster.getName(), histoGroupEfficiencyVsPurityCluster);         
        HistoGroup histoGroupEfficiencyVsPurityCross = new HistoGroup("efficiencyVsPurityCross", 3, 2);
        H2F h2_efficiencyVsPurityCrossHitLevel = new H2F("efficiencyVsPurityCrossHitLevel", "efficiency vs purity Cross at hit level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityCrossHitLevel.setTitleX("purity");
        h2_efficiencyVsPurityCrossHitLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityCross.addDataSet(h2_efficiencyVsPurityCrossHitLevel, 0);
        H2F h2_efficiencyVsPurityCrossClusterLevel = new H2F("efficiencyVsPurityCrossClusterLevel", "efficiency vs purity Cross at cluster level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityCrossClusterLevel.setTitleX("purity");
        h2_efficiencyVsPurityCrossClusterLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityCross.addDataSet(h2_efficiencyVsPurityCrossClusterLevel, 1);   
        H2F h2_efficiencyVsPurityCrossCrossLevel = new H2F("efficiencyVsPurityCrossCrossLevel", "efficiency vs purity Cross at cross level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityCrossCrossLevel.setTitleX("purity");
        h2_efficiencyVsPurityCrossCrossLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityCross.addDataSet(h2_efficiencyVsPurityCrossCrossLevel, 2);   
        H2F h2_efficiencyVsPurityCrossSeedLevel = new H2F("efficiencyVsPurityCrossSeedLevel", "efficiency vs purity Cross at seed level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityCrossSeedLevel.setTitleX("purity");
        h2_efficiencyVsPurityCrossSeedLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityCross.addDataSet(h2_efficiencyVsPurityCrossSeedLevel, 3);   
        H2F h2_efficiencyVsPurityCrossTrackLevel = new H2F("efficiencyVsPurityCrossTrackLevel", "efficiency vs purity Cross at track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityCrossTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityCrossTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityCross.addDataSet(h2_efficiencyVsPurityCrossTrackLevel, 4);
        H2F h2_efficiencyVsPurityCrossValidTrackLevel = new H2F("efficiencyVsPurityCrossValidTrackLevel", "efficiency vs purity Cross at valid track level", 30, 0, 1.01, 30, 0, 1.01);
        h2_efficiencyVsPurityCrossValidTrackLevel.setTitleX("purity");
        h2_efficiencyVsPurityCrossValidTrackLevel.setTitleY("efficiency");
        histoGroupEfficiencyVsPurityCross.addDataSet(h2_efficiencyVsPurityCrossValidTrackLevel, 5);        
        histoGroupMap.put(histoGroupEfficiencyVsPurityCross.getName(), histoGroupEfficiencyVsPurityCross);                                         
    }
             
    public void processEvent(Event event1, Event event2, boolean uTrack){        
        //Read banks
        LocalEvent localEvent1 = new LocalEvent(reader1, event1); 
        LocalEvent localEvent2 = new LocalEvent(reader2, event2);
        
        List<Track> allTracks1;              
        List<Track> tracks2;
        if(!uTrack){
            allTracks1 = localEvent1.getTracks(Constants.PASS); 
            tracks2 = localEvent2.getTracks(Constants.PASS);
        }
        else{
            allTracks1 = localEvent1.getUTracks(Constants.PASS); 
            tracks2 = localEvent2.getUTracks(Constants.PASS);
        }        
                
        List<Track> tracks1 = new ArrayList();
        List<Seed> seeds1 = new ArrayList();
        List<Cross> crossesBST1 = new ArrayList();
        List<Cross> crossesBMT1 = new ArrayList();
        List<Cluster> clustersBST1 = new ArrayList();
        List<Cluster> clustersBMT1 = new ArrayList();
        List<Hit> hitsBST1 = new ArrayList();
        List<Hit> hitsBMT1 = new ArrayList();
        
        for(Track trk1 : allTracks1){
            if(trk1.isValid()) {
                numTotalTracks1 ++;
                tracks1.add(trk1);
                if(trk1.getSeed() != null) seeds1.add(trk1.getSeed());
                crossesBST1.addAll(trk1.getBSTCrosses());
                crossesBMT1.addAll(trk1.getBMTCrosses());
                clustersBST1.addAll(trk1.getBSTClusters());
                clustersBMT1.addAll(trk1.getBMTClusters());
                hitsBST1.addAll(trk1.getBSTHits());
                hitsBMT1.addAll(trk1.getBMTHits());
            }
        }
        
        List<Seed> seeds2 = localEvent2.getSeeds(Constants.PASS);
        List<Cross> crossesBST2 = localEvent2.getBSTCrosses(Constants.PASS);
        List<Cross> crossesBMT2 = localEvent2.getBMTCrosses(Constants.PASS);
        List<Cluster> clustersBST2 = localEvent2.getBSTClusters(Constants.PASS);
        List<Cluster> clustersBMT2 = localEvent2.getBMTClusters(Constants.PASS);
        List<Hit> hitsBST2 = localEvent2.getBSTHits(Constants.PASS);
        List<Hit> hitsBMT2 = localEvent2.getBMTHits(Constants.PASS);
        
        
        Match match = new Match();
        match.hitMatch(hitsBST1, hitsBST2, hitsBMT1, hitsBMT2);
        match.clusterMatch(clustersBST1, clustersBST2, clustersBMT1, clustersBMT2);
        match.crossMatch(crossesBST1, crossesBST2, crossesBMT1, crossesBMT2);
        match.seedMatch(seeds1, seeds2);
        match.trackMatch(tracks1, tracks2);
        
        for(Track trk1 : tracks1){
            int numBSTHitsOnTrk1 = trk1.getBSTHits().size();
            int numBMTHitsOnTrk1 = trk1.getBMTHits().size();
            int numHitsOnTrk1 = trk1.getHits().size();
            int numBSTClustersOnTrk1 = trk1.getBSTClusters().size();
            int numBMTClustersOnTrk1 = trk1.getBMTClusters().size();
            int numClustersOnTrk1 = trk1.getClusters().size();
            int numBSTCrossesOnTrk1 = trk1.getBSTCrosses().size();
            int numBMTCrossesOnTrk1 = trk1.getBMTCrosses().size();
            int numCrossesOnTrk1 = trk1.getCrosses().size();
            
            int numBSTHitsOnTrk2 = 0;
            int numBMTHitsOnTrk2 = 0;
            int numHitsOnTrk2 = 0;
            int numBSTClustersOnTrk2 = 0;
            int numBMTClustersOnTrk2 = 0;
            int numClustersOnTrk2 = 0;
            int numBSTCrossesOnTrk2 = 0;
            int numBMTCrossesOnTrk2 = 0;
            int numCrossesOnTrk2 = 0;
            if(match.get_map_track1_track2().containsKey(trk1)){
                Track trk2 = match.get_map_track1_track2().get(trk1);
                numBSTHitsOnTrk2 = trk2.getBSTHits().size();
                numBMTHitsOnTrk2 = trk2.getBMTHits().size();
                numHitsOnTrk2 = trk2.getHits().size();
                numBSTClustersOnTrk2 = trk2.getBSTClusters().size();
                numBMTClustersOnTrk2 = trk2.getBMTClusters().size();
                numClustersOnTrk2 = trk2.getClusters().size();
                numBSTCrossesOnTrk2 = trk2.getBSTCrosses().size();
                numBMTCrossesOnTrk2 = trk2.getBMTCrosses().size();
                numCrossesOnTrk2 = trk2.getCrosses().size();                
            }
                                               
            //// Hit level
            // Hit: for a hit on track of sample 1, check if ther is matched hit in sample 2
            // Cluster: for seed hit of a cluster on track of sample 1, check if there is matched hit in sample 2
            // Cross: for seed hits of a cross on track of sample 1, check if there are matched hits in sample 2
            int numMatchedBSTHitsHitLevel = 0;
            int numMatchedBMTHitsHitLevel = 0;
            int numMatchedHitsHitLevel = 0;
            int numMatchedBSTClustersHitLevel = 0;
            int numMatchedBMTClustersHitLevel = 0;
            int numMatchedClustersHitLevel = 0;
            int numMatchedBSTCrossesHitLevel = 0;
            int numMatchedBMTCrossesHitLevel = 0;
            int numMatchedCrossesHitLevel = 0;
            for(Hit hit1 : trk1.getBSTHits()){
                if(match.get_map_hit1_hit2_BST().keySet().contains(hit1)) numMatchedBSTHitsHitLevel++;                                
            }
            for(Hit hit1 : trk1.getBMTHits()){
                if(match.get_map_hit1_hit2_BMT().keySet().contains(hit1)) numMatchedBMTHitsHitLevel++; 
            }
            for(Cluster cls1 : trk1.getBSTClusters()){
                if(match.get_map_hit1_hit2_BST().keySet().contains(cls1.getSeedHit())) numMatchedBSTClustersHitLevel++;
            }
            for(Cluster cls1 : trk1.getBMTClusters()){
                if(match.get_map_hit1_hit2_BMT().keySet().contains(cls1.getSeedHit())) numMatchedBMTClustersHitLevel++;
            }
            for(Cross crs1 : trk1.getBSTCrosses()){
                if(match.get_map_hit1_hit2_BST().keySet().contains(crs1.getCluster1().getSeedHit()) 
                        && match.get_map_hit1_hit2_BST().keySet().contains(crs1.getCluster2().getSeedHit())) numMatchedBSTCrossesHitLevel++;
            }
            for(Cross crs1 : trk1.getBMTCrosses()){
                if(match.get_map_hit1_hit2_BMT().keySet().contains(crs1.getCluster1().getSeedHit())) numMatchedBMTCrossesHitLevel++;
            }                        
            numMatchedHitsHitLevel = numMatchedBSTHitsHitLevel + numMatchedBMTHitsHitLevel;
            numMatchedClustersHitLevel = numMatchedBSTClustersHitLevel + numMatchedBMTClustersHitLevel;
            numMatchedCrossesHitLevel = numMatchedBSTCrossesHitLevel + numMatchedBMTCrossesHitLevel;
            
            //// Cluster level            
            // Cluster: for a cluster on track of sample 1, check if there is matched cluster in sample 2
            // Hit: for matched clusters, check how many matched hits between clusters
            // Cross: for clusters of a cross on track of sample 1, check if both clusters for BST or single cluster for BMT have matched clusters in sample 2
            int numMatchedBSTHitsClusterLevel = 0;
            int numMatchedBMTHitsClusterLevel = 0;
            int numMatchedHitsClusterLevel = 0;
            int numMatchedBSTClustersClusterLevel = 0;
            int numMatchedBMTClustersClusterLevel = 0;
            int numMatchedClustersClusterLevel = 0;
            int numMatchedBSTCrossesClusterLevel = 0;
            int numMatchedBMTCrossesClusterLevel = 0;
            int numMatchedCrossesClusterLevel = 0;            
            for(Cluster cls1 : trk1.getBSTClusters()){
                if(match.get_map_cls1_cls2_BST().keySet().contains(cls1)){
                    numMatchedBSTClustersClusterLevel++;
                    numMatchedBSTHitsClusterLevel += cls1.clusterMatchedHits(match.get_map_cls1_cls2_BST().get(cls1));
                }
            }
            for(Cluster cls1 : trk1.getBMTClusters()){
                if(match.get_map_cls1_cls2_BMT().keySet().contains(cls1)){
                    numMatchedBMTClustersClusterLevel++;                    
                    numMatchedBMTHitsClusterLevel += cls1.clusterMatchedHits(match.get_map_cls1_cls2_BMT().get(cls1));
                }
            }
            for(Cross crs1 : trk1.getBSTCrosses()){
                if(match.get_map_cls1_cls2_BST().keySet().contains(crs1.getCluster1()) 
                        && match.get_map_cls1_cls2_BST().keySet().contains(crs1.getCluster2())) numMatchedBSTCrossesClusterLevel++;
            }
            for(Cross crs1 : trk1.getBMTCrosses()){
                if(match.get_map_cls1_cls2_BMT().keySet().contains(crs1.getCluster1())) numMatchedBMTCrossesClusterLevel++;
            }
            numMatchedHitsClusterLevel = numMatchedBSTHitsClusterLevel + numMatchedBMTHitsClusterLevel;
            numMatchedClustersClusterLevel = numMatchedBSTClustersClusterLevel + numMatchedBMTClustersClusterLevel;
            numMatchedCrossesClusterLevel = numMatchedBSTCrossesClusterLevel + numMatchedBMTCrossesClusterLevel;            
            
            //// Cross level 
            // Cross: for a cross on track of sample 1, check if there is a matched cross in sample 2
            // Cluster: 1 matched cross means 2 matched clusters for BST, and 1 matched cluster for BMT
            // Hit: for matched clusters of matched crosses, check how many matched hits between clusters                                
            int numMatchedBSTHitsCrossLevel = 0;
            int numMatchedBMTHitsCrossLevel = 0;
            int numMatchedHitsCrossLevel = 0;
            int numMatchedBSTClustersCrossLevel = 0;
            int numMatchedBMTClustersCrossLevel = 0;
            int numMatchedClustersCrossLevel = 0;
            int numMatchedBSTCrossesCrossLevel = 0;
            int numMatchedBMTCrossesCrossLevel = 0;
            int numMatchedCrossesCrossLevel = 0;
            for(Cross crs1 : trk1.getBSTCrosses()){
                if(match.get_map_crs1_crs2_BST().keySet().contains(crs1)){
                    numMatchedBSTCrossesCrossLevel++;
                    numMatchedBSTClustersCrossLevel += 2;
                    numMatchedBSTHitsCrossLevel += crs1.getCluster1().clusterMatchedHits(match.get_map_crs1_crs2_BST().get(crs1).getCluster1());
                    numMatchedBSTHitsCrossLevel += crs1.getCluster2().clusterMatchedHits(match.get_map_crs1_crs2_BST().get(crs1).getCluster2());
                }
            }
            for(Cross crs1 : trk1.getBMTCrosses()){
                if(match.get_map_crs1_crs2_BMT().keySet().contains(crs1)){
                    numMatchedBMTCrossesCrossLevel++;
                    numMatchedBMTClustersCrossLevel++;
                    numMatchedBMTHitsCrossLevel += crs1.getCluster1().clusterMatchedHits(match.get_map_crs1_crs2_BMT().get(crs1).getCluster1());
                }
            }            
            numMatchedHitsCrossLevel = numMatchedBSTHitsCrossLevel + numMatchedBMTHitsCrossLevel;
            numMatchedClustersCrossLevel = numMatchedBSTClustersCrossLevel + numMatchedBMTClustersCrossLevel;
            numMatchedCrossesCrossLevel = numMatchedBSTCrossesCrossLevel + numMatchedBMTCrossesCrossLevel;
            
            //// Seed level
            // Cross & its clusters: for matched seeds, check how many matched crosses & their clusters
            // Hit: for matched clusters of matched crosses, check how many matched hits
            int numMatchedBSTHitsSeedLevel = 0;
            int numMatchedBMTHitsSeedLevel = 0;
            int numMatchedHitsSeedLevel = 0;
            int numMatchedBSTClustersSeedLevel = 0;
            int numMatchedBMTClustersSeedLevel = 0;
            int numMatchedClustersSeedLevel = 0;
            int numMatchedBSTCrossesSeedLevel = 0;
            int numMatchedBMTCrossesSeedLevel = 0;
            int numMatchedCrossesSeedLevel = 0;            
            Seed seed1 = trk1.getSeed();
            if(seed1 != null && match.get_map_seed1_seed2().keySet().contains(seed1)){
                Seed seed2 = match.get_map_seed1_seed2().get(seed1);
                List<Pair<Integer, Integer>> matchedCrossPairList = seed1.getMatchedCrossPairList();
                for(Pair<Integer, Integer> crossIdPair : matchedCrossPairList){
                    Cross crossBST1 = null;
                    for(Cross crs1 : seed1.getBSTCrosses()){
                        if(crs1.id() == crossIdPair.getKey()){
                            crossBST1 = crs1;
                            break;
                        }
                    }
                    Cross crossBST2 = null;
                    for(Cross crs2 : seed2.getBSTCrosses()){
                        if(crs2.id() == crossIdPair.getValue()){
                            crossBST2 = crs2;
                            break;
                        }
                    }
                    if(crossBST1 != null && crossBST2 != null) {
                        numMatchedBSTCrossesSeedLevel++;
                        numMatchedBSTClustersSeedLevel += 2;
                        numMatchedBSTHitsSeedLevel += crossBST1.getCluster1().clusterMatchedHits(crossBST2.getCluster1());
                        numMatchedBSTHitsSeedLevel += crossBST1.getCluster2().clusterMatchedHits(crossBST2.getCluster2());
                    }
                    
                    Cross crossBMT1 = null;
                    for(Cross crs1 : seed1.getBMTCrosses()){
                        if(crs1.id() == crossIdPair.getKey()){
                            crossBMT1 = crs1;
                            break;
                        }
                    }
                    Cross crossBMT2 = null;
                    for(Cross crs2 : seed2.getBMTCrosses()){
                        if(crs2.id() == crossIdPair.getValue()){
                            crossBMT2 = crs2;
                            break;
                        }
                    }
                    if(crossBMT1 != null && crossBMT2 != null) {
                        numMatchedBMTCrossesSeedLevel++;
                        numMatchedBMTClustersSeedLevel++;
                        numMatchedBMTHitsSeedLevel += crossBMT1.getCluster1().clusterMatchedHits(crossBMT2.getCluster1());
                    }                 
                }
            }
            numMatchedHitsSeedLevel = numMatchedBSTHitsSeedLevel + numMatchedBMTHitsSeedLevel;
            numMatchedClustersSeedLevel = numMatchedBSTClustersSeedLevel + numMatchedBMTClustersSeedLevel;
            numMatchedCrossesSeedLevel = numMatchedBSTCrossesSeedLevel + numMatchedBMTCrossesSeedLevel;
                
            //// Track level
            // Cross & its clusters: for matched tracks, check how many matched crosses & their clusters
            // Hit: for matched clusters of matched crosses, check how many matched hits
            int numMatchedBSTHitsTrackLevel = 0;
            int numMatchedBMTHitsTrackLevel = 0;
            int numMatchedHitsTrackLevel = 0;
            int numMatchedBSTClustersTrackLevel = 0;
            int numMatchedBMTClustersTrackLevel = 0;
            int numMatchedClustersTrackLevel = 0;
            int numMatchedBSTCrossesTrackLevel = 0;
            int numMatchedBMTCrossesTrackLevel = 0;
            int numMatchedCrossesTrackLevel = 0; 
            int numMatchedBSTHitsValidTrackLevel = 0;
            int numMatchedBMTHitsValidTrackLevel = 0;
            int numMatchedHitsValidTrackLevel = 0;
            int numMatchedBSTClustersValidTrackLevel = 0;
            int numMatchedBMTClustersValidTrackLevel = 0;
            int numMatchedClustersValidTrackLevel = 0;
            int numMatchedBSTCrossesValidTrackLevel = 0;
            int numMatchedBMTCrossesValidTrackLevel = 0;
            int numMatchedCrossesValidTrackLevel = 0; 
            if(match.get_map_track1_track2().keySet().contains(trk1)){
                Track trk2 = match.get_map_track1_track2().get(trk1);
                List<Pair<Integer, Integer>> matchedCrossPairList = trk1.getMatchedCrossPairList();
                for(Pair<Integer, Integer> crossIdPair : matchedCrossPairList){
                    Cross crossBST1 = null;
                    for(Cross crs1 : trk1.getBSTCrosses()){
                        if(crs1.id() == crossIdPair.getKey()){
                            crossBST1 = crs1;
                            break;
                        }
                    }
                    Cross crossBST2 = null;
                    for(Cross crs2 : trk2.getBSTCrosses()){
                        if(crs2.id() == crossIdPair.getValue()){
                            crossBST2 = crs2;
                            break;
                        }
                    }
                    if(crossBST1 != null && crossBST2 != null) {
                        numMatchedBSTCrossesTrackLevel++;
                        numMatchedBSTClustersTrackLevel += 2;
                        numMatchedBSTHitsTrackLevel += crossBST1.getCluster1().clusterMatchedHits(crossBST2.getCluster1());
                        numMatchedBSTHitsTrackLevel += crossBST1.getCluster2().clusterMatchedHits(crossBST2.getCluster2());
                        if(trk2.isValid()) {
                            numMatchedBSTCrossesValidTrackLevel++;
                            numMatchedBSTClustersValidTrackLevel += 2;
                            numMatchedBSTHitsValidTrackLevel += crossBST1.getCluster1().clusterMatchedHits(crossBST2.getCluster1());
                            numMatchedBSTHitsValidTrackLevel += crossBST1.getCluster2().clusterMatchedHits(crossBST2.getCluster2());
                        }
                    }
                    
                    Cross crossBMT1 = null;
                    for(Cross crs1 : trk1.getBMTCrosses()){
                        if(crs1.id() == crossIdPair.getKey()){
                            crossBMT1 = crs1;
                            break;
                        }
                    }
                    Cross crossBMT2 = null;
                    for(Cross crs2 : trk2.getBMTCrosses()){
                        if(crs2.id() == crossIdPair.getValue()){
                            crossBMT2 = crs2;
                            break;
                        }
                    }
                    if(crossBMT1 != null && crossBMT2 != null) {
                        numMatchedBMTCrossesTrackLevel++;
                        numMatchedBMTClustersTrackLevel++;
                        numMatchedBMTHitsTrackLevel += crossBMT1.getCluster1().clusterMatchedHits(crossBMT2.getCluster1());
                        if(trk2.isValid()) {
                            numMatchedBMTCrossesValidTrackLevel++;
                            numMatchedBMTClustersValidTrackLevel++;
                            numMatchedBMTHitsValidTrackLevel += crossBMT1.getCluster1().clusterMatchedHits(crossBMT2.getCluster1()); 
                        }
                    }                 
                }                                                                
            }
            numMatchedHitsTrackLevel = numMatchedBSTHitsTrackLevel + numMatchedBMTHitsTrackLevel;
            numMatchedClustersTrackLevel = numMatchedBSTClustersTrackLevel + numMatchedBMTClustersTrackLevel;
            numMatchedCrossesTrackLevel = numMatchedBSTCrossesTrackLevel + numMatchedBMTCrossesTrackLevel; 
            
            numMatchedHitsValidTrackLevel = numMatchedBSTHitsValidTrackLevel + numMatchedBMTHitsValidTrackLevel;
            numMatchedClustersValidTrackLevel = numMatchedBSTClustersValidTrackLevel + numMatchedBMTClustersValidTrackLevel;
            numMatchedCrossesValidTrackLevel = numMatchedBSTCrossesValidTrackLevel + numMatchedBMTCrossesValidTrackLevel;
            
            double efficiencyBSTHitHitLevel = numMatchedBSTHitsHitLevel / (double) numBSTHitsOnTrk1;
            double efficiencyBSTHitClusterLevel = numMatchedBSTHitsClusterLevel / (double) numBSTHitsOnTrk1;
            double efficiencyBSTHitCrossLevel = numMatchedBSTHitsCrossLevel / (double) numBSTHitsOnTrk1;
            double efficiencyBSTHitSeedLevel = numMatchedBSTHitsSeedLevel / (double) numBSTHitsOnTrk1;
            double efficiencyBSTHitTrackLevel = numMatchedBSTHitsTrackLevel / (double) numBSTHitsOnTrk1; 
            double efficiencyBSTHitValidTrackLevel = numMatchedBSTHitsValidTrackLevel / (double) numBSTHitsOnTrk1; 
            double efficiencyBSTClusterHitLevel = numMatchedBSTClustersHitLevel / (double) numBSTClustersOnTrk1;
            double efficiencyBSTClusterClusterLevel = numMatchedBSTClustersClusterLevel / (double) numBSTClustersOnTrk1;
            double efficiencyBSTClusterCrossLevel = numMatchedBSTClustersCrossLevel / (double) numBSTClustersOnTrk1;
            double efficiencyBSTClusterSeedLevel = numMatchedBSTClustersSeedLevel / (double) numBSTClustersOnTrk1;
            double efficiencyBSTClusterTrackLevel = numMatchedBSTClustersTrackLevel / (double) numBSTClustersOnTrk1;
            double efficiencyBSTClusterValidTrackLevel = numMatchedBSTClustersValidTrackLevel / (double) numBSTClustersOnTrk1; 
            double efficiencyBSTCrossHitLevel = numMatchedBSTCrossesHitLevel / (double) numBSTCrossesOnTrk1;
            double efficiencyBSTCrossClusterLevel = numMatchedBSTCrossesClusterLevel / (double) numBSTCrossesOnTrk1;
            double efficiencyBSTCrossCrossLevel = numMatchedBSTCrossesCrossLevel / (double) numBSTCrossesOnTrk1;
            double efficiencyBSTCrossSeedLevel = numMatchedBSTCrossesSeedLevel / (double) numBSTCrossesOnTrk1;
            double efficiencyBSTCrossTrackLevel = numMatchedBSTCrossesTrackLevel / (double) numBSTCrossesOnTrk1; 
            double efficiencyBSTCrossValidTrackLevel = numMatchedBSTCrossesValidTrackLevel / (double) numBSTCrossesOnTrk1;
            
            double efficiencyBMTHitHitLevel = numMatchedBMTHitsHitLevel / (double) numBMTHitsOnTrk1;
            double efficiencyBMTHitClusterLevel = numMatchedBMTHitsClusterLevel / (double) numBMTHitsOnTrk1;
            double efficiencyBMTHitCrossLevel = numMatchedBMTHitsCrossLevel / (double) numBMTHitsOnTrk1;
            double efficiencyBMTHitSeedLevel = numMatchedBMTHitsSeedLevel / (double) numBMTHitsOnTrk1;
            double efficiencyBMTHitTrackLevel = numMatchedBMTHitsTrackLevel / (double) numBMTHitsOnTrk1;  
            double efficiencyBMTHitValidTrackLevel = numMatchedBMTHitsValidTrackLevel / (double) numBMTHitsOnTrk1; 
            double efficiencyBMTClusterHitLevel = numMatchedBMTClustersHitLevel / (double) numBMTClustersOnTrk1;
            double efficiencyBMTClusterClusterLevel = numMatchedBMTClustersClusterLevel / (double) numBMTClustersOnTrk1;
            double efficiencyBMTClusterCrossLevel = numMatchedBMTClustersCrossLevel / (double) numBMTClustersOnTrk1;
            double efficiencyBMTClusterSeedLevel = numMatchedBMTClustersSeedLevel / (double) numBMTClustersOnTrk1;
            double efficiencyBMTClusterTrackLevel = numMatchedBMTClustersTrackLevel / (double) numBMTClustersOnTrk1; 
            double efficiencyBMTClusterValidTrackLevel = numMatchedBMTClustersValidTrackLevel / (double) numBMTClustersOnTrk1;            
            double efficiencyBMTCrossHitLevel = numMatchedBMTCrossesHitLevel / (double) numBMTCrossesOnTrk1;
            double efficiencyBMTCrossClusterLevel = numMatchedBMTCrossesClusterLevel / (double) numBMTCrossesOnTrk1;
            double efficiencyBMTCrossCrossLevel = numMatchedBMTCrossesCrossLevel / (double) numBMTCrossesOnTrk1;
            double efficiencyBMTCrossSeedLevel = numMatchedBMTCrossesSeedLevel / (double) numBMTCrossesOnTrk1;
            double efficiencyBMTCrossTrackLevel = numMatchedBMTCrossesTrackLevel / (double) numBMTCrossesOnTrk1; 
            double efficiencyBMTCrossValidTrackLevel = numMatchedBMTCrossesValidTrackLevel / (double) numBMTCrossesOnTrk1;
            
            double efficiencyHitHitLevel = numMatchedHitsHitLevel / (double) numHitsOnTrk1;
            double efficiencyHitClusterLevel = numMatchedHitsClusterLevel / (double) numHitsOnTrk1;
            double efficiencyHitCrossLevel = numMatchedHitsCrossLevel / (double) numHitsOnTrk1;
            double efficiencyHitSeedLevel = numMatchedHitsSeedLevel / (double) numHitsOnTrk1;
            double efficiencyHitTrackLevel = numMatchedHitsTrackLevel / (double) numHitsOnTrk1; 
            double efficiencyHitValidTrackLevel = numMatchedHitsValidTrackLevel / (double) numHitsOnTrk1;
            double efficiencyClusterHitLevel = numMatchedClustersHitLevel / (double) numClustersOnTrk1;
            double efficiencyClusterClusterLevel = numMatchedClustersClusterLevel / (double) numClustersOnTrk1;
            double efficiencyClusterCrossLevel = numMatchedClustersCrossLevel / (double) numClustersOnTrk1;
            double efficiencyClusterSeedLevel = numMatchedClustersSeedLevel / (double) numClustersOnTrk1;
            double efficiencyClusterTrackLevel = numMatchedClustersTrackLevel / (double) numClustersOnTrk1; 
            double efficiencyClusterValidTrackLevel = numMatchedClustersValidTrackLevel / (double) numClustersOnTrk1;             
            double efficiencyCrossHitLevel = numMatchedCrossesHitLevel / (double) numCrossesOnTrk1;
            double efficiencyCrossClusterLevel = numMatchedCrossesClusterLevel / (double) numCrossesOnTrk1;
            double efficiencyCrossCrossLevel = numMatchedCrossesCrossLevel / (double) numCrossesOnTrk1;
            double efficiencyCrossSeedLevel = numMatchedCrossesSeedLevel / (double) numCrossesOnTrk1;
            double efficiencyCrossTrackLevel = numMatchedCrossesTrackLevel / (double) numCrossesOnTrk1; 
            double efficiencyCrossValidTrackLevel = numMatchedCrossesValidTrackLevel / (double) numCrossesOnTrk1;
            
            double purityBSTHitHitLevel = 1;
            double purityBSTHitClusterLevel = 1;
            double purityBSTHitCrossLevel = 1;
            double purityBSTHitSeedLevel = 0;
            double purityBSTHitTrackLevel = 0; 
            double purityBSTHitValidTrackLevel = 0;
            double purityBSTClusterHitLevel = 1;
            double purityBSTClusterClusterLevel = 1;
            double purityBSTClusterCrossLevel = 1;
            double purityBSTClusterSeedLevel = 0;
            double purityBSTClusterTrackLevel = 0; 
            double purityBSTClusterValidTrackLevel = 0;            
            double purityBSTCrossHitLevel = 1;
            double purityBSTCrossClusterLevel = 1;
            double purityBSTCrossCrossLevel = 1;
            double purityBSTCrossSeedLevel = 0;
            double purityBSTCrossTrackLevel = 0;
            double purityBSTCrossValidTrackLevel = 0;
            if(numBSTHitsOnTrk2 > 0){
                purityBSTHitHitLevel = numMatchedBSTHitsHitLevel / (double) numBSTHitsOnTrk2;
                purityBSTHitClusterLevel = numMatchedBSTHitsClusterLevel / (double) numBSTHitsOnTrk2;
                purityBSTHitCrossLevel = numMatchedBSTHitsCrossLevel / (double) numBSTHitsOnTrk2;
                purityBSTHitSeedLevel = numMatchedBSTHitsSeedLevel / (double) numBSTHitsOnTrk2;
                purityBSTHitTrackLevel = numMatchedBSTHitsTrackLevel / (double) numBSTHitsOnTrk2; 
                purityBSTHitValidTrackLevel = numMatchedBSTHitsValidTrackLevel / (double) numBSTHitsOnTrk2;
            }
            if(numBSTClustersOnTrk2 > 0){
                purityBSTClusterHitLevel = numMatchedBSTClustersHitLevel / (double) numBSTClustersOnTrk2;
                purityBSTClusterClusterLevel = numMatchedBSTClustersClusterLevel / (double) numBSTClustersOnTrk2;
                purityBSTClusterCrossLevel = numMatchedBSTClustersCrossLevel / (double) numBSTClustersOnTrk2;
                purityBSTClusterSeedLevel = numMatchedBSTClustersSeedLevel / (double) numBSTClustersOnTrk2;
                purityBSTClusterTrackLevel = numMatchedBSTClustersTrackLevel / (double) numBSTClustersOnTrk2;
                purityBSTClusterValidTrackLevel = numMatchedBSTClustersValidTrackLevel / (double) numBSTClustersOnTrk2;
            }
            if(numBSTCrossesOnTrk2 > 0){
                purityBSTCrossHitLevel = numMatchedBSTCrossesHitLevel / (double) numBSTCrossesOnTrk2;
                purityBSTCrossClusterLevel = numMatchedBSTCrossesClusterLevel / (double) numBSTCrossesOnTrk2;
                purityBSTCrossCrossLevel = numMatchedBSTCrossesCrossLevel / (double) numBSTCrossesOnTrk2;
                purityBSTCrossSeedLevel = numMatchedBSTCrossesSeedLevel / (double) numBSTCrossesOnTrk2;
                purityBSTCrossTrackLevel = numMatchedBSTCrossesTrackLevel / (double) numBSTCrossesOnTrk2;
                purityBSTCrossValidTrackLevel = numMatchedBSTCrossesValidTrackLevel / (double) numBSTCrossesOnTrk2;
            }
            
            double purityBMTHitHitLevel = 1;
            double purityBMTHitClusterLevel = 1;
            double purityBMTHitCrossLevel = 1;
            double purityBMTHitSeedLevel = 0;
            double purityBMTHitTrackLevel = 0; 
            double purityBMTHitValidTrackLevel = 0; 
            double purityBMTClusterHitLevel = 1;
            double purityBMTClusterClusterLevel = 1;
            double purityBMTClusterCrossLevel = 1;
            double purityBMTClusterSeedLevel = 0;
            double purityBMTClusterTrackLevel = 0; 
            double purityBMTClusterValidTrackLevel = 0;             
            double purityBMTCrossHitLevel = 1;
            double purityBMTCrossClusterLevel = 1;
            double purityBMTCrossCrossLevel = 1;
            double purityBMTCrossSeedLevel = 0;
            double purityBMTCrossTrackLevel = 0;
            double purityBMTCrossValidTrackLevel = 0;
            if(numBMTHitsOnTrk2 > 0){
                purityBMTHitHitLevel = numMatchedBMTHitsHitLevel / (double) numBMTHitsOnTrk2;
                purityBMTHitClusterLevel = numMatchedBMTHitsClusterLevel / (double) numBMTHitsOnTrk2;
                purityBMTHitCrossLevel = numMatchedBMTHitsCrossLevel / (double) numBMTHitsOnTrk2;
                purityBMTHitSeedLevel = numMatchedBMTHitsSeedLevel / (double) numBMTHitsOnTrk2;
                purityBMTHitTrackLevel = numMatchedBMTHitsTrackLevel / (double) numBMTHitsOnTrk2; 
                purityBMTHitValidTrackLevel = numMatchedBMTHitsValidTrackLevel / (double) numBMTHitsOnTrk2; 
            }
            if(numBMTClustersOnTrk2 > 0){
                purityBMTClusterHitLevel = numMatchedBMTClustersHitLevel / (double) numBMTClustersOnTrk2;
                purityBMTClusterClusterLevel = numMatchedBMTClustersClusterLevel / (double) numBMTClustersOnTrk2;
                purityBMTClusterCrossLevel = numMatchedBMTClustersCrossLevel / (double) numBMTClustersOnTrk2;
                purityBMTClusterSeedLevel = numMatchedBMTClustersSeedLevel / (double) numBMTClustersOnTrk2;
                purityBMTClusterTrackLevel = numMatchedBMTClustersTrackLevel / (double) numBMTClustersOnTrk2; 
                purityBMTClusterValidTrackLevel = numMatchedBMTClustersValidTrackLevel / (double) numBMTClustersOnTrk2; 
            }
            if(numBMTCrossesOnTrk2 > 0){
                purityBMTCrossHitLevel = numMatchedBMTCrossesHitLevel / (double) numBMTCrossesOnTrk2;
                purityBMTCrossClusterLevel = numMatchedBMTCrossesClusterLevel / (double) numBMTCrossesOnTrk2;
                purityBMTCrossCrossLevel = numMatchedBMTCrossesCrossLevel / (double) numBMTCrossesOnTrk2;
                purityBMTCrossSeedLevel = numMatchedBMTCrossesSeedLevel / (double) numBMTCrossesOnTrk2;
                purityBMTCrossTrackLevel = numMatchedBMTCrossesTrackLevel / (double) numBMTCrossesOnTrk2;
                purityBMTCrossValidTrackLevel = numMatchedBMTCrossesValidTrackLevel / (double) numBMTCrossesOnTrk2;
            } 
            
            double purityHitHitLevel = 1;
            double purityHitClusterLevel = 1;
            double purityHitCrossLevel = 1;
            double purityHitSeedLevel = 0;
            double purityHitTrackLevel = 0; 
            double purityHitValidTrackLevel = 0;
            double purityClusterHitLevel = 1;
            double purityClusterClusterLevel = 1;
            double purityClusterCrossLevel = 1;
            double purityClusterSeedLevel = 0;
            double purityClusterTrackLevel = 0;  
            double purityClusterValidTrackLevel = 0;
            double purityCrossHitLevel = 1;
            double purityCrossClusterLevel = 1;
            double purityCrossCrossLevel = 1;
            double purityCrossSeedLevel = 0;
            double purityCrossTrackLevel = 0;
            double purityCrossValidTrackLevel = 0;
            if(numHitsOnTrk2 > 0){
                purityHitHitLevel = numMatchedHitsHitLevel / (double) numHitsOnTrk2;
                purityHitClusterLevel = numMatchedHitsClusterLevel / (double) numHitsOnTrk2;
                purityHitCrossLevel = numMatchedHitsCrossLevel / (double) numHitsOnTrk2;
                purityHitSeedLevel = numMatchedHitsSeedLevel / (double) numHitsOnTrk2;
                purityHitTrackLevel = numMatchedHitsTrackLevel / (double) numHitsOnTrk2; 
                purityHitValidTrackLevel = numMatchedHitsValidTrackLevel / (double) numHitsOnTrk2;
            }
            if(numClustersOnTrk2 > 0){
                purityClusterHitLevel = numMatchedClustersHitLevel / (double) numClustersOnTrk2;
                purityClusterClusterLevel = numMatchedClustersClusterLevel / (double) numClustersOnTrk2;
                purityClusterCrossLevel = numMatchedClustersCrossLevel / (double) numClustersOnTrk2;
                purityClusterSeedLevel = numMatchedClustersSeedLevel / (double) numClustersOnTrk2;
                purityClusterTrackLevel = numMatchedClustersTrackLevel / (double) numClustersOnTrk2;  
                purityClusterValidTrackLevel = numMatchedClustersValidTrackLevel / (double) numClustersOnTrk2;
            }
            if(numCrossesOnTrk2 > 0){
                purityCrossHitLevel = numMatchedCrossesHitLevel / (double) numCrossesOnTrk2;
                purityCrossClusterLevel = numMatchedCrossesClusterLevel / (double) numCrossesOnTrk2;
                purityCrossCrossLevel = numMatchedCrossesCrossLevel / (double) numCrossesOnTrk2;
                purityCrossSeedLevel = numMatchedCrossesSeedLevel / (double) numCrossesOnTrk2;
                purityCrossTrackLevel = numMatchedCrossesTrackLevel / (double) numCrossesOnTrk2;
                purityCrossValidTrackLevel = numMatchedCrossesValidTrackLevel / (double) numCrossesOnTrk2;
            } 
            
            if(match.get_map_track1_track2().containsKey(trk1)){
                HistoGroup histoGroupKinematicsDiff = histoGroupMap.get("Track kinematics diff"); 
                HistoGroup histoGroupKinematicsDiffVsHitEfficiency = histoGroupMap.get("Track kinematics diff vs hit efficiency"); 
                HistoGroup histoGroupKinematicsDiffVsHitPurity = histoGroupMap.get("Track kinematics diff vs hit purity"); 
                HistoGroup histoGroupKinematicsDiffVsHitFBeta = histoGroupMap.get("Track kinematics diff vs hit fBeta"); 
                HistoGroup histoGroupKinematicsDiffVsClusterEfficiency = histoGroupMap.get("Track kinematics diff vs cluster efficiency"); 
                HistoGroup histoGroupKinematicsDiffVsClusterPurity = histoGroupMap.get("Track kinematics diff vs cluster purity"); 
                HistoGroup histoGroupKinematicsDiffVsClusterFBeta = histoGroupMap.get("Track kinematics diff vs cluster fBeta"); 
                
                Track trk2 = match.get_map_track1_track2().get(trk1);

                histoGroupKinematicsDiff.getH1F("pDiff" ).fill(trk2.momentum().mag() - trk1.momentum().mag()); 
                histoGroupKinematicsDiff.getH1F("thetaDiff" ).fill(trk2.momentum().theta() - trk1.momentum().theta()); 
                histoGroupKinematicsDiff.getH1F("phiDiff" ).fill(trk2.momentum().phi() - trk1.momentum().phi()); 
                histoGroupKinematicsDiff.getH1F("xDiff" ).fill(trk2.vertex().x() - trk1.vertex().x()); 
                histoGroupKinematicsDiff.getH1F("yDiff" ).fill(trk2.vertex().y() - trk1.vertex().y()); 
                histoGroupKinematicsDiff.getH1F("zDiff" ).fill(trk2.vertex().z() - trk1.vertex().z());
                histoGroupKinematicsDiff.getH1F("chi2_per_ndfDiff" ).fill(trk2.chi2()/trk2.ndf() - trk1.chi2()/trk1.ndf());
                
                histoGroupKinematicsDiffVsHitEfficiency.getH2F("pDiffVsHitEfficiency" ).fill(efficiencyHitTrackLevel, trk2.momentum().mag() - trk1.momentum().mag()); 
                histoGroupKinematicsDiffVsHitEfficiency.getH2F("thetaDiffVsHitEfficiency" ).fill(efficiencyHitTrackLevel, trk2.momentum().theta() - trk1.momentum().theta()); 
                histoGroupKinematicsDiffVsHitEfficiency.getH2F("phiDiffVsHitEfficiency" ).fill(efficiencyHitTrackLevel, trk2.momentum().phi() - trk1.momentum().phi()); 
                histoGroupKinematicsDiffVsHitEfficiency.getH2F("xDiffVsHitEfficiency" ).fill(efficiencyHitTrackLevel, trk2.vertex().x() - trk1.vertex().x()); 
                histoGroupKinematicsDiffVsHitEfficiency.getH2F("yDiffVsHitEfficiency" ).fill(efficiencyHitTrackLevel, trk2.vertex().y() - trk1.vertex().y()); 
                histoGroupKinematicsDiffVsHitEfficiency.getH2F("zDiffVsHitEfficiency" ).fill(efficiencyHitTrackLevel, trk2.vertex().z() - trk1.vertex().z());
                histoGroupKinematicsDiffVsHitEfficiency.getH2F("chi2_per_ndfDiffVsHitEfficiency" ).fill(efficiencyHitTrackLevel, trk2.chi2()/trk2.ndf() - trk1.chi2()/trk1.ndf()); 
                
                histoGroupKinematicsDiffVsHitPurity.getH2F("pDiffVsHitPurity" ).fill(purityHitTrackLevel, trk2.momentum().mag() - trk1.momentum().mag()); 
                histoGroupKinematicsDiffVsHitPurity.getH2F("thetaDiffVsHitPurity" ).fill(purityHitTrackLevel, trk2.momentum().theta() - trk1.momentum().theta()); 
                histoGroupKinematicsDiffVsHitPurity.getH2F("phiDiffVsHitPurity" ).fill(purityHitTrackLevel, trk2.momentum().phi() - trk1.momentum().phi()); 
                histoGroupKinematicsDiffVsHitPurity.getH2F("xDiffVsHitPurity" ).fill(purityHitTrackLevel, trk2.vertex().x() - trk1.vertex().x()); 
                histoGroupKinematicsDiffVsHitPurity.getH2F("yDiffVsHitPurity" ).fill(purityHitTrackLevel, trk2.vertex().y() - trk1.vertex().y()); 
                histoGroupKinematicsDiffVsHitPurity.getH2F("zDiffVsHitPurity" ).fill(purityHitTrackLevel, trk2.vertex().z() - trk1.vertex().z());
                histoGroupKinematicsDiffVsHitPurity.getH2F("chi2_per_ndfDiffVsHitPurity" ).fill(purityHitTrackLevel, trk2.chi2()/trk2.ndf() - trk1.chi2()/trk1.ndf());
                                                   
                histoGroupKinematicsDiffVsClusterEfficiency.getH2F("pDiffVsClusterEfficiency" ).fill(efficiencyClusterTrackLevel, trk2.momentum().mag() - trk1.momentum().mag()); 
                histoGroupKinematicsDiffVsClusterEfficiency.getH2F("thetaDiffVsClusterEfficiency" ).fill(efficiencyClusterTrackLevel, trk2.momentum().theta() - trk1.momentum().theta()); 
                histoGroupKinematicsDiffVsClusterEfficiency.getH2F("phiDiffVsClusterEfficiency" ).fill(efficiencyClusterTrackLevel, trk2.momentum().phi() - trk1.momentum().phi()); 
                histoGroupKinematicsDiffVsClusterEfficiency.getH2F("xDiffVsClusterEfficiency" ).fill(efficiencyClusterTrackLevel, trk2.vertex().x() - trk1.vertex().x()); 
                histoGroupKinematicsDiffVsClusterEfficiency.getH2F("yDiffVsClusterEfficiency" ).fill(efficiencyClusterTrackLevel, trk2.vertex().y() - trk1.vertex().y()); 
                histoGroupKinematicsDiffVsClusterEfficiency.getH2F("zDiffVsClusterEfficiency" ).fill(efficiencyClusterTrackLevel, trk2.vertex().z() - trk1.vertex().z());
                histoGroupKinematicsDiffVsClusterEfficiency.getH2F("chi2_per_ndfDiffVsClusterEfficiency" ).fill(efficiencyClusterTrackLevel, trk2.chi2()/trk2.ndf() - trk1.chi2()/trk1.ndf()); 
                
                histoGroupKinematicsDiffVsClusterPurity.getH2F("pDiffVsClusterPurity" ).fill(purityClusterTrackLevel, trk2.momentum().mag() - trk1.momentum().mag()); 
                histoGroupKinematicsDiffVsClusterPurity.getH2F("thetaDiffVsClusterPurity" ).fill(purityClusterTrackLevel, trk2.momentum().theta() - trk1.momentum().theta()); 
                histoGroupKinematicsDiffVsClusterPurity.getH2F("phiDiffVsClusterPurity" ).fill(purityClusterTrackLevel, trk2.momentum().phi() - trk1.momentum().phi()); 
                histoGroupKinematicsDiffVsClusterPurity.getH2F("xDiffVsClusterPurity" ).fill(purityClusterTrackLevel, trk2.vertex().x() - trk1.vertex().x()); 
                histoGroupKinematicsDiffVsClusterPurity.getH2F("yDiffVsClusterPurity" ).fill(purityClusterTrackLevel, trk2.vertex().y() - trk1.vertex().y()); 
                histoGroupKinematicsDiffVsClusterPurity.getH2F("zDiffVsClusterPurity" ).fill(purityClusterTrackLevel, trk2.vertex().z() - trk1.vertex().z());
                histoGroupKinematicsDiffVsClusterPurity.getH2F("chi2_per_ndfDiffVsClusterPurity" ).fill(purityClusterTrackLevel, trk2.chi2()/trk2.ndf() - trk1.chi2()/trk1.ndf());                
            }            
                        
            HistoGroup  histoGroupEfficiencyBSTHit = histoGroupMap.get("efficiencyBSTHit");
            histoGroupEfficiencyBSTHit.getH1F("efficiencyBSTHitHitLevel").fill(efficiencyBSTHitHitLevel);
            histoGroupEfficiencyBSTHit.getH1F("efficiencyBSTHitClusterLevel").fill(efficiencyBSTHitClusterLevel);
            histoGroupEfficiencyBSTHit.getH1F("efficiencyBSTHitCrossLevel").fill(efficiencyBSTHitCrossLevel);
            histoGroupEfficiencyBSTHit.getH1F("efficiencyBSTHitSeedLevel").fill(efficiencyBSTHitSeedLevel);
            histoGroupEfficiencyBSTHit.getH1F("efficiencyBSTHitTrackLevel").fill(efficiencyBSTHitTrackLevel);  
            histoGroupEfficiencyBSTHit.getH1F("efficiencyBSTHitValidTrackLevel").fill(efficiencyBSTHitValidTrackLevel); 
            HistoGroup  histoGroupEfficiencyBSTCluster = histoGroupMap.get("efficiencyBSTCluster");
            histoGroupEfficiencyBSTCluster.getH1F("efficiencyBSTClusterHitLevel").fill(efficiencyBSTClusterHitLevel);
            histoGroupEfficiencyBSTCluster.getH1F("efficiencyBSTClusterClusterLevel").fill(efficiencyBSTClusterClusterLevel);
            histoGroupEfficiencyBSTCluster.getH1F("efficiencyBSTClusterCrossLevel").fill(efficiencyBSTClusterCrossLevel);
            histoGroupEfficiencyBSTCluster.getH1F("efficiencyBSTClusterSeedLevel").fill(efficiencyBSTClusterSeedLevel);
            histoGroupEfficiencyBSTCluster.getH1F("efficiencyBSTClusterTrackLevel").fill(efficiencyBSTClusterTrackLevel);  
            histoGroupEfficiencyBSTCluster.getH1F("efficiencyBSTClusterValidTrackLevel").fill(efficiencyBSTClusterValidTrackLevel); 
            HistoGroup  histoGroupEfficiencyBSTCross = histoGroupMap.get("efficiencyBSTCross");
            histoGroupEfficiencyBSTCross.getH1F("efficiencyBSTCrossHitLevel").fill(efficiencyBSTCrossHitLevel);
            histoGroupEfficiencyBSTCross.getH1F("efficiencyBSTCrossClusterLevel").fill(efficiencyBSTCrossClusterLevel);
            histoGroupEfficiencyBSTCross.getH1F("efficiencyBSTCrossCrossLevel").fill(efficiencyBSTCrossCrossLevel);
            histoGroupEfficiencyBSTCross.getH1F("efficiencyBSTCrossSeedLevel").fill(efficiencyBSTCrossSeedLevel);
            histoGroupEfficiencyBSTCross.getH1F("efficiencyBSTCrossTrackLevel").fill(efficiencyBSTCrossTrackLevel); 
            histoGroupEfficiencyBSTCross.getH1F("efficiencyBSTCrossValidTrackLevel").fill(efficiencyBSTCrossValidTrackLevel); 
            HistoGroup  histoGroupPurityBSTHit = histoGroupMap.get("purityBSTHit");
            histoGroupPurityBSTHit.getH1F("purityBSTHitHitLevel").fill(purityBSTHitHitLevel);
            histoGroupPurityBSTHit.getH1F("purityBSTHitClusterLevel").fill(purityBSTHitClusterLevel);
            histoGroupPurityBSTHit.getH1F("purityBSTHitCrossLevel").fill(purityBSTHitCrossLevel);
            histoGroupPurityBSTHit.getH1F("purityBSTHitSeedLevel").fill(purityBSTHitSeedLevel);
            histoGroupPurityBSTHit.getH1F("purityBSTHitTrackLevel").fill(purityBSTHitTrackLevel);  
            histoGroupPurityBSTHit.getH1F("purityBSTHitValidTrackLevel").fill(purityBSTHitValidTrackLevel);
            HistoGroup  histoGroupPurityBSTCluster = histoGroupMap.get("purityBSTCluster");
            histoGroupPurityBSTCluster.getH1F("purityBSTClusterHitLevel").fill(purityBSTClusterHitLevel);
            histoGroupPurityBSTCluster.getH1F("purityBSTClusterClusterLevel").fill(purityBSTClusterClusterLevel);
            histoGroupPurityBSTCluster.getH1F("purityBSTClusterCrossLevel").fill(purityBSTClusterCrossLevel);
            histoGroupPurityBSTCluster.getH1F("purityBSTClusterSeedLevel").fill(purityBSTClusterSeedLevel);
            histoGroupPurityBSTCluster.getH1F("purityBSTClusterTrackLevel").fill(purityBSTClusterTrackLevel);  
            histoGroupPurityBSTCluster.getH1F("purityBSTClusterValidTrackLevel").fill(purityBSTClusterValidTrackLevel);            
            HistoGroup  histoGroupPurityBSTCross = histoGroupMap.get("purityBSTCross");
            histoGroupPurityBSTCross.getH1F("purityBSTCrossHitLevel").fill(purityBSTCrossHitLevel);
            histoGroupPurityBSTCross.getH1F("purityBSTCrossClusterLevel").fill(purityBSTCrossClusterLevel);
            histoGroupPurityBSTCross.getH1F("purityBSTCrossCrossLevel").fill(purityBSTCrossCrossLevel);
            histoGroupPurityBSTCross.getH1F("purityBSTCrossSeedLevel").fill(purityBSTCrossSeedLevel);
            histoGroupPurityBSTCross.getH1F("purityBSTCrossTrackLevel").fill(purityBSTCrossTrackLevel); 
            histoGroupPurityBSTCross.getH1F("purityBSTCrossValidTrackLevel").fill(purityBSTCrossValidTrackLevel); 
            HistoGroup  histoGroupEfficiencyVsPurityBSTHit = histoGroupMap.get("efficiencyVsPurityBSTHit");
            histoGroupEfficiencyVsPurityBSTHit.getH2F("efficiencyVsPurityBSTHitHitLevel").fill(purityBSTHitHitLevel, efficiencyBSTHitHitLevel);
            histoGroupEfficiencyVsPurityBSTHit.getH2F("efficiencyVsPurityBSTHitClusterLevel").fill(purityBSTHitClusterLevel,efficiencyBSTHitClusterLevel);
            histoGroupEfficiencyVsPurityBSTHit.getH2F("efficiencyVsPurityBSTHitCrossLevel").fill(purityBSTHitCrossLevel, efficiencyBSTHitCrossLevel);
            histoGroupEfficiencyVsPurityBSTHit.getH2F("efficiencyVsPurityBSTHitSeedLevel").fill(purityBSTHitSeedLevel, efficiencyBSTHitSeedLevel);
            histoGroupEfficiencyVsPurityBSTHit.getH2F("efficiencyVsPurityBSTHitTrackLevel").fill(purityBSTHitTrackLevel, efficiencyBSTHitTrackLevel);
            histoGroupEfficiencyVsPurityBSTHit.getH2F("efficiencyVsPurityBSTHitValidTrackLevel").fill(purityBSTHitValidTrackLevel, efficiencyBSTHitValidTrackLevel); 
            HistoGroup  histoGroupEfficiencyVsPurityBSTCluster = histoGroupMap.get("efficiencyVsPurityBSTCluster");
            histoGroupEfficiencyVsPurityBSTCluster.getH2F("efficiencyVsPurityBSTClusterHitLevel").fill(purityBSTClusterHitLevel, efficiencyBSTClusterHitLevel);
            histoGroupEfficiencyVsPurityBSTCluster.getH2F("efficiencyVsPurityBSTClusterClusterLevel").fill(purityBSTClusterClusterLevel,efficiencyBSTClusterClusterLevel);
            histoGroupEfficiencyVsPurityBSTCluster.getH2F("efficiencyVsPurityBSTClusterCrossLevel").fill(purityBSTClusterCrossLevel, efficiencyBSTClusterCrossLevel);
            histoGroupEfficiencyVsPurityBSTCluster.getH2F("efficiencyVsPurityBSTClusterSeedLevel").fill(purityBSTClusterSeedLevel, efficiencyBSTClusterSeedLevel);
            histoGroupEfficiencyVsPurityBSTCluster.getH2F("efficiencyVsPurityBSTClusterTrackLevel").fill(purityBSTClusterTrackLevel, efficiencyBSTClusterTrackLevel); 
            histoGroupEfficiencyVsPurityBSTCluster.getH2F("efficiencyVsPurityBSTClusterValidTrackLevel").fill(purityBSTClusterValidTrackLevel, efficiencyBSTClusterValidTrackLevel);
            HistoGroup  histoGroupEfficiencyVsPurityBSTCross = histoGroupMap.get("efficiencyVsPurityBSTCross");
            histoGroupEfficiencyVsPurityBSTCross.getH2F("efficiencyVsPurityBSTCrossHitLevel").fill(purityBSTCrossHitLevel, efficiencyBSTCrossHitLevel);
            histoGroupEfficiencyVsPurityBSTCross.getH2F("efficiencyVsPurityBSTCrossClusterLevel").fill(purityBSTCrossClusterLevel,efficiencyBSTCrossClusterLevel);
            histoGroupEfficiencyVsPurityBSTCross.getH2F("efficiencyVsPurityBSTCrossCrossLevel").fill(purityBSTCrossCrossLevel, efficiencyBSTCrossCrossLevel);
            histoGroupEfficiencyVsPurityBSTCross.getH2F("efficiencyVsPurityBSTCrossSeedLevel").fill(purityBSTCrossSeedLevel, efficiencyBSTCrossSeedLevel);
            histoGroupEfficiencyVsPurityBSTCross.getH2F("efficiencyVsPurityBSTCrossTrackLevel").fill(purityBSTCrossTrackLevel, efficiencyBSTCrossTrackLevel);
            histoGroupEfficiencyVsPurityBSTCross.getH2F("efficiencyVsPurityBSTCrossValidTrackLevel").fill(purityBSTCrossValidTrackLevel, efficiencyBSTCrossValidTrackLevel); 
            
            HistoGroup  histoGroupEfficiencyBMTHit = histoGroupMap.get("efficiencyBMTHit");
            histoGroupEfficiencyBMTHit.getH1F("efficiencyBMTHitHitLevel").fill(efficiencyBMTHitHitLevel);
            histoGroupEfficiencyBMTHit.getH1F("efficiencyBMTHitClusterLevel").fill(efficiencyBMTHitClusterLevel);
            histoGroupEfficiencyBMTHit.getH1F("efficiencyBMTHitCrossLevel").fill(efficiencyBMTHitCrossLevel);
            histoGroupEfficiencyBMTHit.getH1F("efficiencyBMTHitSeedLevel").fill(efficiencyBMTHitSeedLevel);
            histoGroupEfficiencyBMTHit.getH1F("efficiencyBMTHitTrackLevel").fill(efficiencyBMTHitTrackLevel);  
            histoGroupEfficiencyBMTHit.getH1F("efficiencyBMTHitValidTrackLevel").fill(efficiencyBMTHitValidTrackLevel); 
            HistoGroup  histoGroupEfficiencyBMTCluster = histoGroupMap.get("efficiencyBMTCluster");
            histoGroupEfficiencyBMTCluster.getH1F("efficiencyBMTClusterHitLevel").fill(efficiencyBMTClusterHitLevel);
            histoGroupEfficiencyBMTCluster.getH1F("efficiencyBMTClusterClusterLevel").fill(efficiencyBMTClusterClusterLevel);
            histoGroupEfficiencyBMTCluster.getH1F("efficiencyBMTClusterCrossLevel").fill(efficiencyBMTClusterCrossLevel);
            histoGroupEfficiencyBMTCluster.getH1F("efficiencyBMTClusterSeedLevel").fill(efficiencyBMTClusterSeedLevel);
            histoGroupEfficiencyBMTCluster.getH1F("efficiencyBMTClusterTrackLevel").fill(efficiencyBMTClusterTrackLevel); 
            histoGroupEfficiencyBMTCluster.getH1F("efficiencyBMTClusterValidTrackLevel").fill(efficiencyBMTClusterValidTrackLevel);            
            HistoGroup  histoGroupEfficiencyBMTCross = histoGroupMap.get("efficiencyBMTCross");
            histoGroupEfficiencyBMTCross.getH1F("efficiencyBMTCrossHitLevel").fill(efficiencyBMTCrossHitLevel);
            histoGroupEfficiencyBMTCross.getH1F("efficiencyBMTCrossClusterLevel").fill(efficiencyBMTCrossClusterLevel);
            histoGroupEfficiencyBMTCross.getH1F("efficiencyBMTCrossCrossLevel").fill(efficiencyBMTCrossCrossLevel);
            histoGroupEfficiencyBMTCross.getH1F("efficiencyBMTCrossSeedLevel").fill(efficiencyBMTCrossSeedLevel);
            histoGroupEfficiencyBMTCross.getH1F("efficiencyBMTCrossTrackLevel").fill(efficiencyBMTCrossTrackLevel);  
            histoGroupEfficiencyBMTCross.getH1F("efficiencyBMTCrossValidTrackLevel").fill(efficiencyBMTCrossValidTrackLevel);
            HistoGroup  histoGroupPurityBMTHit = histoGroupMap.get("purityBMTHit");
            histoGroupPurityBMTHit.getH1F("purityBMTHitHitLevel").fill(purityBMTHitHitLevel);
            histoGroupPurityBMTHit.getH1F("purityBMTHitClusterLevel").fill(purityBMTHitClusterLevel);
            histoGroupPurityBMTHit.getH1F("purityBMTHitCrossLevel").fill(purityBMTHitCrossLevel);
            histoGroupPurityBMTHit.getH1F("purityBMTHitSeedLevel").fill(purityBMTHitSeedLevel);
            histoGroupPurityBMTHit.getH1F("purityBMTHitTrackLevel").fill(purityBMTHitTrackLevel); 
            histoGroupPurityBMTHit.getH1F("purityBMTHitValidTrackLevel").fill(purityBMTHitValidTrackLevel); 
            HistoGroup  histoGroupPurityBMTCluster = histoGroupMap.get("purityBMTCluster");
            histoGroupPurityBMTCluster.getH1F("purityBMTClusterHitLevel").fill(purityBMTClusterHitLevel);
            histoGroupPurityBMTCluster.getH1F("purityBMTClusterClusterLevel").fill(purityBMTClusterClusterLevel);
            histoGroupPurityBMTCluster.getH1F("purityBMTClusterCrossLevel").fill(purityBMTClusterCrossLevel);
            histoGroupPurityBMTCluster.getH1F("purityBMTClusterSeedLevel").fill(purityBMTClusterSeedLevel);
            histoGroupPurityBMTCluster.getH1F("purityBMTClusterTrackLevel").fill(purityBMTClusterTrackLevel);
            histoGroupPurityBMTCluster.getH1F("purityBMTClusterValidTrackLevel").fill(purityBMTClusterValidTrackLevel);
            HistoGroup  histoGroupPurityBMTCross = histoGroupMap.get("purityBMTCross");
            histoGroupPurityBMTCross.getH1F("purityBMTCrossHitLevel").fill(purityBMTCrossHitLevel);
            histoGroupPurityBMTCross.getH1F("purityBMTCrossClusterLevel").fill(purityBMTCrossClusterLevel);
            histoGroupPurityBMTCross.getH1F("purityBMTCrossCrossLevel").fill(purityBMTCrossCrossLevel);
            histoGroupPurityBMTCross.getH1F("purityBMTCrossSeedLevel").fill(purityBMTCrossSeedLevel);
            histoGroupPurityBMTCross.getH1F("purityBMTCrossTrackLevel").fill(purityBMTCrossTrackLevel);
            histoGroupPurityBMTCross.getH1F("purityBMTCrossValidTrackLevel").fill(purityBMTCrossValidTrackLevel);
            HistoGroup  histoGroupEfficiencyVsPurityBMTHit = histoGroupMap.get("efficiencyVsPurityBMTHit");
            histoGroupEfficiencyVsPurityBMTHit.getH2F("efficiencyVsPurityBMTHitHitLevel").fill(purityBMTHitHitLevel, efficiencyBMTHitHitLevel);
            histoGroupEfficiencyVsPurityBMTHit.getH2F("efficiencyVsPurityBMTHitClusterLevel").fill(purityBMTHitClusterLevel,efficiencyBMTHitClusterLevel);
            histoGroupEfficiencyVsPurityBMTHit.getH2F("efficiencyVsPurityBMTHitCrossLevel").fill(purityBMTHitCrossLevel, efficiencyBMTHitCrossLevel);
            histoGroupEfficiencyVsPurityBMTHit.getH2F("efficiencyVsPurityBMTHitSeedLevel").fill(purityBMTHitSeedLevel, efficiencyBMTHitSeedLevel);
            histoGroupEfficiencyVsPurityBMTHit.getH2F("efficiencyVsPurityBMTHitTrackLevel").fill(purityBMTHitTrackLevel, efficiencyBMTHitTrackLevel); 
            histoGroupEfficiencyVsPurityBMTHit.getH2F("efficiencyVsPurityBMTHitValidTrackLevel").fill(purityBMTHitValidTrackLevel, efficiencyBMTHitValidTrackLevel);             
            HistoGroup  histoGroupEfficiencyVsPurityBMTCluster = histoGroupMap.get("efficiencyVsPurityBMTCluster");
            histoGroupEfficiencyVsPurityBMTCluster.getH2F("efficiencyVsPurityBMTClusterHitLevel").fill(purityBMTClusterHitLevel, efficiencyBMTClusterHitLevel);
            histoGroupEfficiencyVsPurityBMTCluster.getH2F("efficiencyVsPurityBMTClusterClusterLevel").fill(purityBMTClusterClusterLevel,efficiencyBMTClusterClusterLevel);
            histoGroupEfficiencyVsPurityBMTCluster.getH2F("efficiencyVsPurityBMTClusterCrossLevel").fill(purityBMTClusterCrossLevel, efficiencyBMTClusterCrossLevel);
            histoGroupEfficiencyVsPurityBMTCluster.getH2F("efficiencyVsPurityBMTClusterSeedLevel").fill(purityBMTClusterSeedLevel, efficiencyBMTClusterSeedLevel);
            histoGroupEfficiencyVsPurityBMTCluster.getH2F("efficiencyVsPurityBMTClusterTrackLevel").fill(purityBMTClusterTrackLevel, efficiencyBMTClusterTrackLevel);
            histoGroupEfficiencyVsPurityBMTCluster.getH2F("efficiencyVsPurityBMTClusterValidTrackLevel").fill(purityBMTClusterValidTrackLevel, efficiencyBMTClusterValidTrackLevel);
            HistoGroup  histoGroupEfficiencyVsPurityBMTCross = histoGroupMap.get("efficiencyVsPurityBMTCross");
            histoGroupEfficiencyVsPurityBMTCross.getH2F("efficiencyVsPurityBMTCrossHitLevel").fill(purityBMTCrossHitLevel, efficiencyBMTCrossHitLevel);
            histoGroupEfficiencyVsPurityBMTCross.getH2F("efficiencyVsPurityBMTCrossClusterLevel").fill(purityBMTCrossClusterLevel,efficiencyBMTCrossClusterLevel);
            histoGroupEfficiencyVsPurityBMTCross.getH2F("efficiencyVsPurityBMTCrossCrossLevel").fill(purityBMTCrossCrossLevel, efficiencyBMTCrossCrossLevel);
            histoGroupEfficiencyVsPurityBMTCross.getH2F("efficiencyVsPurityBMTCrossSeedLevel").fill(purityBMTCrossSeedLevel, efficiencyBMTCrossSeedLevel);
            histoGroupEfficiencyVsPurityBMTCross.getH2F("efficiencyVsPurityBMTCrossTrackLevel").fill(purityBMTCrossTrackLevel, efficiencyBMTCrossTrackLevel); 
            histoGroupEfficiencyVsPurityBMTCross.getH2F("efficiencyVsPurityBMTCrossValidTrackLevel").fill(purityBMTCrossValidTrackLevel, efficiencyBMTCrossValidTrackLevel); 

            HistoGroup  histoGroupEfficiencyHit = histoGroupMap.get("efficiencyHit");
            histoGroupEfficiencyHit.getH1F("efficiencyHitHitLevel").fill(efficiencyHitHitLevel);
            histoGroupEfficiencyHit.getH1F("efficiencyHitClusterLevel").fill(efficiencyHitClusterLevel);
            histoGroupEfficiencyHit.getH1F("efficiencyHitCrossLevel").fill(efficiencyHitCrossLevel);
            histoGroupEfficiencyHit.getH1F("efficiencyHitSeedLevel").fill(efficiencyHitSeedLevel);
            histoGroupEfficiencyHit.getH1F("efficiencyHitTrackLevel").fill(efficiencyHitTrackLevel); 
            histoGroupEfficiencyHit.getH1F("efficiencyHitValidTrackLevel").fill(efficiencyHitValidTrackLevel); 
            HistoGroup  histoGroupEfficiencyCluster = histoGroupMap.get("efficiencyCluster");
            histoGroupEfficiencyCluster.getH1F("efficiencyClusterHitLevel").fill(efficiencyClusterHitLevel);
            histoGroupEfficiencyCluster.getH1F("efficiencyClusterClusterLevel").fill(efficiencyClusterClusterLevel);
            histoGroupEfficiencyCluster.getH1F("efficiencyClusterCrossLevel").fill(efficiencyClusterCrossLevel);
            histoGroupEfficiencyCluster.getH1F("efficiencyClusterSeedLevel").fill(efficiencyClusterSeedLevel);
            histoGroupEfficiencyCluster.getH1F("efficiencyClusterTrackLevel").fill(efficiencyClusterTrackLevel); 
            histoGroupEfficiencyCluster.getH1F("efficiencyClustervalidTrackLevel").fill(efficiencyClusterValidTrackLevel);             
            HistoGroup  histoGroupEfficiencyCross = histoGroupMap.get("efficiencyCross");
            histoGroupEfficiencyCross.getH1F("efficiencyCrossHitLevel").fill(efficiencyCrossHitLevel);
            histoGroupEfficiencyCross.getH1F("efficiencyCrossClusterLevel").fill(efficiencyCrossClusterLevel);
            histoGroupEfficiencyCross.getH1F("efficiencyCrossCrossLevel").fill(efficiencyCrossCrossLevel);
            histoGroupEfficiencyCross.getH1F("efficiencyCrossSeedLevel").fill(efficiencyCrossSeedLevel);
            histoGroupEfficiencyCross.getH1F("efficiencyCrossTrackLevel").fill(efficiencyCrossTrackLevel); 
            histoGroupEfficiencyCross.getH1F("efficiencyCrossValidTrackLevel").fill(efficiencyCrossValidTrackLevel);
            HistoGroup  histoGroupPurityHit = histoGroupMap.get("purityHit");
            histoGroupPurityHit.getH1F("purityHitHitLevel").fill(purityHitHitLevel);
            histoGroupPurityHit.getH1F("purityHitClusterLevel").fill(purityHitClusterLevel);
            histoGroupPurityHit.getH1F("purityHitCrossLevel").fill(purityHitCrossLevel);
            histoGroupPurityHit.getH1F("purityHitSeedLevel").fill(purityHitSeedLevel);
            histoGroupPurityHit.getH1F("purityHitTrackLevel").fill(purityHitTrackLevel);
            histoGroupPurityHit.getH1F("purityHitValidTrackLevel").fill(purityHitValidTrackLevel);             
            HistoGroup  histoGroupPurityCluster = histoGroupMap.get("purityCluster");
            histoGroupPurityCluster.getH1F("purityClusterHitLevel").fill(purityClusterHitLevel);
            histoGroupPurityCluster.getH1F("purityClusterClusterLevel").fill(purityClusterClusterLevel);
            histoGroupPurityCluster.getH1F("purityClusterCrossLevel").fill(purityClusterCrossLevel);
            histoGroupPurityCluster.getH1F("purityClusterSeedLevel").fill(purityClusterSeedLevel);
            histoGroupPurityCluster.getH1F("purityClusterTrackLevel").fill(purityClusterTrackLevel);             
            histoGroupPurityCluster.getH1F("purityClusterValidTrackLevel").fill(purityClusterValidTrackLevel); 
            HistoGroup  histoGroupPurityCross = histoGroupMap.get("purityCross");
            histoGroupPurityCross.getH1F("purityCrossHitLevel").fill(purityCrossHitLevel);
            histoGroupPurityCross.getH1F("purityCrossClusterLevel").fill(purityCrossClusterLevel);
            histoGroupPurityCross.getH1F("purityCrossCrossLevel").fill(purityCrossCrossLevel);
            histoGroupPurityCross.getH1F("purityCrossSeedLevel").fill(purityCrossSeedLevel);
            histoGroupPurityCross.getH1F("purityCrossTrackLevel").fill(purityCrossTrackLevel); 
            histoGroupPurityCross.getH1F("purityCrossValidTrackLevel").fill(purityCrossValidTrackLevel); 
            HistoGroup  histoGroupEfficiencyVsPurityHit = histoGroupMap.get("efficiencyVsPurityHit");
            histoGroupEfficiencyVsPurityHit.getH2F("efficiencyVsPurityHitHitLevel").fill(purityHitHitLevel, efficiencyHitHitLevel);
            histoGroupEfficiencyVsPurityHit.getH2F("efficiencyVsPurityHitClusterLevel").fill(purityHitClusterLevel,efficiencyHitClusterLevel);
            histoGroupEfficiencyVsPurityHit.getH2F("efficiencyVsPurityHitCrossLevel").fill(purityHitCrossLevel, efficiencyHitCrossLevel);
            histoGroupEfficiencyVsPurityHit.getH2F("efficiencyVsPurityHitSeedLevel").fill(purityHitSeedLevel, efficiencyHitSeedLevel);
            histoGroupEfficiencyVsPurityHit.getH2F("efficiencyVsPurityHitTrackLevel").fill(purityHitTrackLevel, efficiencyHitTrackLevel);  
            histoGroupEfficiencyVsPurityHit.getH2F("efficiencyVsPurityHitValidTrackLevel").fill(purityHitValidTrackLevel, efficiencyHitValidTrackLevel); 
            HistoGroup  histoGroupEfficiencyVsPurityCluster = histoGroupMap.get("efficiencyVsPurityCluster");
            histoGroupEfficiencyVsPurityCluster.getH2F("efficiencyVsPurityClusterHitLevel").fill(purityClusterHitLevel, efficiencyClusterHitLevel);
            histoGroupEfficiencyVsPurityCluster.getH2F("efficiencyVsPurityClusterClusterLevel").fill(purityClusterClusterLevel,efficiencyClusterClusterLevel);
            histoGroupEfficiencyVsPurityCluster.getH2F("efficiencyVsPurityClusterCrossLevel").fill(purityClusterCrossLevel, efficiencyClusterCrossLevel);
            histoGroupEfficiencyVsPurityCluster.getH2F("efficiencyVsPurityClusterSeedLevel").fill(purityClusterSeedLevel, efficiencyClusterSeedLevel);
            histoGroupEfficiencyVsPurityCluster.getH2F("efficiencyVsPurityClusterTrackLevel").fill(purityClusterTrackLevel, efficiencyClusterTrackLevel);
            histoGroupEfficiencyVsPurityCluster.getH2F("efficiencyVsPurityClusterValidTrackLevel").fill(purityClusterValidTrackLevel, efficiencyClusterValidTrackLevel); 
            HistoGroup  histoGroupEfficiencyVsPurityCross = histoGroupMap.get("efficiencyVsPurityCross");
            histoGroupEfficiencyVsPurityCross.getH2F("efficiencyVsPurityCrossHitLevel").fill(purityCrossHitLevel, efficiencyCrossHitLevel);
            histoGroupEfficiencyVsPurityCross.getH2F("efficiencyVsPurityCrossClusterLevel").fill(purityCrossClusterLevel,efficiencyCrossClusterLevel);
            histoGroupEfficiencyVsPurityCross.getH2F("efficiencyVsPurityCrossCrossLevel").fill(purityCrossCrossLevel, efficiencyCrossCrossLevel);
            histoGroupEfficiencyVsPurityCross.getH2F("efficiencyVsPurityCrossSeedLevel").fill(purityCrossSeedLevel, efficiencyCrossSeedLevel);
            histoGroupEfficiencyVsPurityCross.getH2F("efficiencyVsPurityCrossTrackLevel").fill(purityCrossTrackLevel, efficiencyCrossTrackLevel); 
            histoGroupEfficiencyVsPurityCross.getH2F("efficiencyVsPurityCrossValidTrackLevel").fill(purityCrossValidTrackLevel, efficiencyCrossValidTrackLevel); 
                        
            for(int i = 0; i < CUTOPTIONS; i++){
                for(int j = 0; j < CUTOPTIONS; j++){
                    if(efficiencyHitHitLevel >= EFFICIENCYCUT[i] && purityHitHitLevel >= PURITYCUT[j]) numHitMatchedTracksHitLevel[i][j]++;
                    if(efficiencyHitClusterLevel >= EFFICIENCYCUT[i] && purityHitClusterLevel >= PURITYCUT[j]) numHitMatchedTracksClusterLevel[i][j]++;
                    if(efficiencyHitCrossLevel >= EFFICIENCYCUT[i] && purityHitCrossLevel >= PURITYCUT[j]) numHitMatchedTracksCrossLevel[i][j]++;
                    if(efficiencyHitSeedLevel >= EFFICIENCYCUT[i] && purityHitSeedLevel >= PURITYCUT[j]) numHitMatchedTracksSeedLevel[i][j]++;
                    if(efficiencyHitTrackLevel >= EFFICIENCYCUT[i] && purityHitTrackLevel >= PURITYCUT[j]) numHitMatchedTracksTrackLevel[i][j]++;
                    if(efficiencyHitValidTrackLevel >= EFFICIENCYCUT[i] && purityHitValidTrackLevel >= PURITYCUT[j]) numHitMatchedTracksValidTrackLevel[i][j]++; 

                    if(efficiencyClusterHitLevel >= EFFICIENCYCUT[i] && purityHitHitLevel >= PURITYCUT[j]) numClusterMatchedTracksHitLevel[i][j]++;
                    if(efficiencyClusterClusterLevel >= EFFICIENCYCUT[i] && purityHitClusterLevel >= PURITYCUT[j]) numClusterMatchedTracksClusterLevel[i][j]++;
                    if(efficiencyClusterCrossLevel >= EFFICIENCYCUT[i] && purityHitCrossLevel >= PURITYCUT[j]) numClusterMatchedTracksCrossLevel[i][j]++;
                    if(efficiencyClusterSeedLevel >= EFFICIENCYCUT[i] && purityHitSeedLevel >= PURITYCUT[j]) numClusterMatchedTracksSeedLevel[i][j]++;
                    if(efficiencyClusterTrackLevel >= EFFICIENCYCUT[i] && purityHitTrackLevel >= PURITYCUT[j]) numClusterMatchedTracksTrackLevel[i][j]++; 
                    if(efficiencyClusterValidTrackLevel >= EFFICIENCYCUT[i] && purityHitValidTrackLevel >= PURITYCUT[j]) numClusterMatchedTracksValidTrackLevel[i][j]++; 

                    if(efficiencyCrossHitLevel >= EFFICIENCYCUT[i] && purityHitHitLevel >= PURITYCUT[j]) numCrossMatchedTracksHitLevel[i][j]++;
                    if(efficiencyCrossClusterLevel >= EFFICIENCYCUT[i] && purityHitClusterLevel >= PURITYCUT[j]) numCrossMatchedTracksClusterLevel[i][j]++;
                    if(efficiencyCrossCrossLevel >= EFFICIENCYCUT[i] && purityHitCrossLevel >= PURITYCUT[j]) numCrossMatchedTracksCrossLevel[i][j]++;
                    if(efficiencyCrossSeedLevel >= EFFICIENCYCUT[i] && purityHitSeedLevel >= PURITYCUT[j]) numCrossMatchedTracksSeedLevel[i][j]++;
                    if(efficiencyCrossTrackLevel >= EFFICIENCYCUT[i] && purityHitTrackLevel >= PURITYCUT[j]) numCrossMatchedTracksTrackLevel[i][j]++; 
                    if(efficiencyCrossValidTrackLevel >= EFFICIENCYCUT[i] && purityHitValidTrackLevel >= PURITYCUT[j]) numCrossMatchedTracksValidTrackLevel[i][j]++; 
                }                                
            }
        }
    }
    
    public void postEventProcess() {                
        HistoGroup histoGroupTrackEfficiencyBasedHitMatch = new HistoGroup("trackEfficiencyBasedHitMatch", CUTOPTIONS, CUTOPTIONS);
        for(int i = 0; i < CUTOPTIONS; i++){
            for(int j = 0; j < CUTOPTIONS; j++){
                int numRemainingTracksBasedHitMatch[] = {numTotalTracks1, numHitMatchedTracksHitLevel[i][j], numHitMatchedTracksClusterLevel[i][j], 
            numHitMatchedTracksCrossLevel[i][j], numHitMatchedTracksSeedLevel[i][j], numHitMatchedTracksTrackLevel[i][j], numHitMatchedTracksValidTrackLevel[i][j]};
                H1F h1_ratioRemainingTracksBasedHitMatch = new H1F("ratioRemainingTracksBasedHitMatch", "ratio for hit eff >= " + Double.toString(EFFICIENCYCUT[i]) + " and pur >= " + Double.toString(PURITYCUT[j]), 7, -0.5, 6.5);
                for(int k = 0; k < numRemainingTracksBasedHitMatch.length; k++){
                    h1_ratioRemainingTracksBasedHitMatch.setBinContent(k, (double) numRemainingTracksBasedHitMatch[k]/numRemainingTracksBasedHitMatch[0]);
                }        
                h1_ratioRemainingTracksBasedHitMatch.setTitleX("reconstruction level");
                h1_ratioRemainingTracksBasedHitMatch.setTitleY("Counts");
                h1_ratioRemainingTracksBasedHitMatch.setLineColor(1);
                histoGroupTrackEfficiencyBasedHitMatch.addDataSet(h1_ratioRemainingTracksBasedHitMatch, i*6+j);
            }
        }
        histoGroupMap.put(histoGroupTrackEfficiencyBasedHitMatch.getName(), histoGroupTrackEfficiencyBasedHitMatch); 
        
        HistoGroup histoGroupTrackEfficiencyBasedClusterMatch = new HistoGroup("trackEfficiencyBasedClusterMatch", CUTOPTIONS, CUTOPTIONS);
        for(int i = 0; i < CUTOPTIONS; i++){
            for(int j = 0; j < CUTOPTIONS; j++){
                int numRemainingTracksBasedClusterMatch[] = {numTotalTracks1, numClusterMatchedTracksHitLevel[i][j], numClusterMatchedTracksClusterLevel[i][j], 
            numClusterMatchedTracksCrossLevel[i][j], numClusterMatchedTracksSeedLevel[i][j], numClusterMatchedTracksTrackLevel[i][j], numClusterMatchedTracksValidTrackLevel[i][j]};
                H1F h1_ratioRemainingTracksBasedClusterMatch = new H1F("ratioRemainingTracksBasedClusterMatch", "ratio for cluster eff >= " + Double.toString(EFFICIENCYCUT[i]) + " and pur >= " + Double.toString(PURITYCUT[j]), 7, -0.5, 6.5);
                for(int k = 0; k < numRemainingTracksBasedClusterMatch.length; k++){
                    h1_ratioRemainingTracksBasedClusterMatch.setBinContent(k, (double) numRemainingTracksBasedClusterMatch[k]/numRemainingTracksBasedClusterMatch[0]);
                }        
                h1_ratioRemainingTracksBasedClusterMatch.setTitleX("reconstruction level");
                h1_ratioRemainingTracksBasedClusterMatch.setTitleY("Counts");
                h1_ratioRemainingTracksBasedClusterMatch.setLineColor(1);
                histoGroupTrackEfficiencyBasedClusterMatch.addDataSet(h1_ratioRemainingTracksBasedClusterMatch, i*6+j);
            }
        }
        histoGroupMap.put(histoGroupTrackEfficiencyBasedClusterMatch.getName(), histoGroupTrackEfficiencyBasedClusterMatch); 

        HistoGroup histoGroupTrackEfficiencyBasedCrossMatch = new HistoGroup("trackEfficiencyBasedCrossMatch", CUTOPTIONS, CUTOPTIONS);
        for(int i = 0; i < CUTOPTIONS; i++){
            for(int j = 0; j < CUTOPTIONS; j++){
                int numRemainingTracksBasedCrossMatch[] = {numTotalTracks1, numCrossMatchedTracksHitLevel[i][j], numCrossMatchedTracksClusterLevel[i][j], 
            numCrossMatchedTracksCrossLevel[i][j], numCrossMatchedTracksSeedLevel[i][j], numCrossMatchedTracksTrackLevel[i][j], numCrossMatchedTracksValidTrackLevel[i][j]};
                H1F h1_ratioRemainingTracksBasedCrossMatch = new H1F("ratioRemainingTracksBasedCrossMatch", "ratio for cross eff >= " + Double.toString(EFFICIENCYCUT[i]) + " and pur >= " + Double.toString(PURITYCUT[j]), 7, -0.5, 6.5);
                for(int k = 0; k < numRemainingTracksBasedCrossMatch.length; k++){
                    h1_ratioRemainingTracksBasedCrossMatch.setBinContent(k, (double) numRemainingTracksBasedCrossMatch[k]/numRemainingTracksBasedCrossMatch[0]);
                }        
                h1_ratioRemainingTracksBasedCrossMatch.setTitleX("reconstruction level");
                h1_ratioRemainingTracksBasedCrossMatch.setTitleY("Counts");
                h1_ratioRemainingTracksBasedCrossMatch.setLineColor(1);
                histoGroupTrackEfficiencyBasedCrossMatch.addDataSet(h1_ratioRemainingTracksBasedCrossMatch, i*6+j);
            }
        }
        histoGroupMap.put(histoGroupTrackEfficiencyBasedCrossMatch.getName(), histoGroupTrackEfficiencyBasedCrossMatch);         
    }            
                            
    public static void main(String[] args){
        OptionParser parser = new OptionParser("bgEffectsOnValidTracks");
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
        BgOnValidTracks analysis = new BgOnValidTracks();
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
                frame.setSize(1800, 1200);
                frame.add(canvas);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }        
    }
    
}
