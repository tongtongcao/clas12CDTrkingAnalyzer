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
import org.clas.element.Seed;
import org.clas.graph.HistoGroup;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;

/**
 *
 * @author Tongtong Cao
 */
public class SeedRecon extends BaseAnalysis{ 
    
    public SeedRecon(){}
    
    @Override
    public void createHistoGroupMap(){
        HistoGroup histoGroupFittingParameters = new HistoGroup("fittingParameters for Seed", 4, 3);         
        H1F h1_q = new H1F("q", "q", 3, -1.5, 1.5);
        h1_q.setTitleX("q");
        h1_q.setTitleY("Counts");
        h1_q.setLineColor(1);
        histoGroupFittingParameters.addDataSet(h1_q, 0);           
        H1F h1_pt = new H1F("pt", "pt", 100, 0, 5);
        h1_pt.setTitleX("pt (GeV/c)");
        h1_pt.setTitleY("Counts");
        h1_pt.setLineColor(1);
        histoGroupFittingParameters.addDataSet(h1_pt, 1);          
        H1F h1_phi0 = new H1F("phi0", "phi0", 100, -Math.PI, Math.PI);
        h1_phi0.setTitleX("phi0 (rad)");
        h1_phi0.setTitleY("Counts");
        h1_phi0.setLineColor(1);
        histoGroupFittingParameters.addDataSet(h1_phi0, 2);  
        H1F h1_d0 = new H1F("d0", "d0", 100, -0.1, 0.1);
        h1_d0.setTitleX("d0 (cm)");
        h1_d0.setTitleY("Counts");
        h1_d0.setLineColor(1);
        histoGroupFittingParameters.addDataSet(h1_d0, 3);         
        H1F h1_tandip = new H1F("tandip", "tandip", 100, -5, 5);
        h1_tandip.setTitleX("tandip");
        h1_tandip.setTitleY("Counts");
        h1_tandip.setLineColor(1);
        histoGroupFittingParameters.addDataSet(h1_tandip, 4);        
        H1F h1_z0 = new H1F("z0", "z0", 100, -15, 10);
        h1_z0.setTitleX("z0 (cm)");
        h1_z0.setTitleY("Counts");
        h1_z0.setLineColor(1);
        histoGroupFittingParameters.addDataSet(h1_z0, 5);          
        H1F h1_xb = new H1F("xb", "xb", 100, -1, 1);
        h1_xb.setTitleX("xb (cm)");
        h1_xb.setTitleY("Counts");
        h1_xb.setLineColor(1);
        histoGroupFittingParameters.addDataSet(h1_xb, 6);         
        H1F h1_yb = new H1F("yb", "yb", 100, -1, 1);
        h1_yb.setTitleX("yb (cm)");
        h1_yb.setTitleY("Counts");
        h1_yb.setLineColor(1);
        histoGroupFittingParameters.addDataSet(h1_yb, 7);         
        H1F h1_chi2_per_ndf = new H1F("chi2_per_ndf", "chi2/ndf", 100, 0, 50);
        h1_chi2_per_ndf.setTitleX("chi2/ndf");
        h1_chi2_per_ndf.setTitleY("Counts");
        h1_chi2_per_ndf.setLineColor(1);
        histoGroupFittingParameters.addDataSet(h1_chi2_per_ndf, 8); 
        H1F h1_circlefit_chi2_per_ndf = new H1F("circlefit_chi2_per_ndf", "chi2/ndf for circlefit", 100, 0, 50);
        h1_circlefit_chi2_per_ndf.setTitleX("chi2/ndf for circlefit");
        h1_circlefit_chi2_per_ndf.setTitleY("Counts");
        h1_circlefit_chi2_per_ndf.setLineColor(1);
        histoGroupFittingParameters.addDataSet(h1_circlefit_chi2_per_ndf, 9); 
        H1F h1_linefit_chi2_per_ndf = new H1F("linefit_chi2_per_ndf", "chi2/ndf for linefit", 100, 0, 50);
        h1_linefit_chi2_per_ndf.setTitleX("chi2/ndf for linefit");
        h1_linefit_chi2_per_ndf.setTitleY("Counts");
        h1_linefit_chi2_per_ndf.setLineColor(1);
        histoGroupFittingParameters.addDataSet(h1_linefit_chi2_per_ndf, 10);                         
        histoGroupMap.put(histoGroupFittingParameters.getName(), histoGroupFittingParameters);
      
        HistoGroup histoGroupKinematics = new HistoGroup("kinematics for Seed", 3, 2);         
        H1F h1_p = new H1F("p", "p", 100, 0, 5);
        h1_p.setTitleX("p (GeV/c)");
        h1_p.setTitleY("Counts");
        h1_p.setLineColor(1);   
        histoGroupKinematics.addDataSet(h1_p, 0);        
        H1F h1_theta = new H1F("theta", "#theta", 100, 0, Math.PI);
        h1_theta.setTitleX("#theta (rad)");
        h1_theta.setTitleY("Counts");
        h1_theta.setLineColor(1);   
        histoGroupKinematics.addDataSet(h1_theta, 1);          
        H1F h1_phi = new H1F("phi", "#phi", 100, -Math.PI, Math.PI);
        h1_phi.setTitleX("#phi (rad)");
        h1_phi.setTitleY("Counts");
        h1_phi.setLineColor(1);   
        histoGroupKinematics.addDataSet(h1_phi, 2);         
        H1F h1_x = new H1F("x", "x", 100, -1, 1);
        h1_x.setTitleX("x (cm)");
        h1_x.setTitleY("Counts");
        h1_x.setLineColor(1);   
        histoGroupKinematics.addDataSet(h1_x, 3);           
        H1F h1_y = new H1F("y", "y", 100, -1, 1);
        h1_y.setTitleX("y (cm)");
        h1_y.setTitleY("Counts");
        h1_y.setLineColor(1);   
        histoGroupKinematics.addDataSet(h1_y, 4); 
        H1F h1_z = new H1F("z", "z", 100, -15, 10);
        h1_z.setTitleX("z (cm)");
        h1_z.setTitleY("Counts");
        h1_z.setLineColor(1);   
        histoGroupKinematics.addDataSet(h1_z, 5);                 
        histoGroupMap.put(histoGroupKinematics.getName(), histoGroupKinematics);
        
        if(Constants.BG){
            HistoGroup histoGroupCrossStatus = new HistoGroup("cross status for Seed", 3, 2); 
            H2F h2_crossStatus = new H2F("cross status", "cross status", 10, -0.5, 9.5, 10, -0.5, 9.5);
            h2_crossStatus.setTitleX("# of normal-seed-strips crosses");
            h2_crossStatus.setTitleY("# of nonnormal-seed-strips crosses");
            histoGroupCrossStatus.addDataSet(h2_crossStatus, 0);             
            H2F h2_bstCrossStatus = new H2F("BST cross status", "BST cross status", 10, -0.5, 9.5, 10, -0.5, 9.5);
            h2_bstCrossStatus.setTitleX("# of normal-seed-strips crosses");
            h2_bstCrossStatus.setTitleY("# of nonnormal-seed-strips crosses");
            histoGroupCrossStatus.addDataSet(h2_bstCrossStatus, 1);            
            H2F h2_bmtCrossStatus = new H2F("BMT cross status", "BMT cross status", 10, -0.5, 9.5, 10, -0.5, 9.5);
            h2_bmtCrossStatus.setTitleX("# of normal-seed-strips crosses");
            h2_bmtCrossStatus.setTitleY("# of nonnormal-seed-strips crosses");
            histoGroupCrossStatus.addDataSet(h2_bmtCrossStatus, 2);            
            H1F h1_ratioNoramlSeedStripsCrosses = new H1F("ratio of noraml-seed-strips crosses", "ratio of noraml-seed-strips crosses", 30, 0, 1.01);
            h1_ratioNoramlSeedStripsCrosses.setTitleX("ratio of noraml-seed-strips crosses");
            h1_ratioNoramlSeedStripsCrosses.setTitleY("Counts");
            histoGroupCrossStatus.addDataSet(h1_ratioNoramlSeedStripsCrosses, 3);             
            H1F h1_ratioNoramlSeedStripsBSTCrosses = new H1F("ratio of noraml-seed-strips BST crosses", "ratio of noraml-seed-strips BST crosses", 30, 0, 1.01);
            h1_ratioNoramlSeedStripsBSTCrosses.setTitleX("ratio of noraml-seed-strips crosses");
            h1_ratioNoramlSeedStripsBSTCrosses.setTitleY("Counts");
            histoGroupCrossStatus.addDataSet(h1_ratioNoramlSeedStripsBSTCrosses, 4); 
            H1F h1_ratioNoramlSeedStripsBMTCrosses = new H1F("ratio of noraml-seed-strips BMT crosses", "ratio of noraml-seed-strips BMT crosses", 30, 0, 1.01);
            h1_ratioNoramlSeedStripsBMTCrosses.setTitleX("ratio of noraml-seed-strips crosses");
            h1_ratioNoramlSeedStripsBMTCrosses.setTitleY("Counts");
            histoGroupCrossStatus.addDataSet(h1_ratioNoramlSeedStripsBMTCrosses, 5);            
            histoGroupMap.put(histoGroupCrossStatus.getName(), histoGroupCrossStatus);
            
            HistoGroup histoGroupClusterStatus = new HistoGroup("cluster status for Seed", 3, 2); 
            H2F h2_clusterStatus = new H2F("cluster status", "cluster status", 13, -0.5, 12.5, 13, -0.5, 12.5);
            h2_clusterStatus.setTitleX("# of normal-seed-strip clusters");
            h2_clusterStatus.setTitleY("# of nonnormal-seed-strip clusters");
            histoGroupClusterStatus.addDataSet(h2_clusterStatus, 0);             
            H2F h2_bstClusterStatus = new H2F("BST cluster status", "BST cluster status", 13, -0.5, 12.5, 13, -0.5, 12.5);
            h2_bstClusterStatus.setTitleX("# of normal-seed-strip clusters");
            h2_bstClusterStatus.setTitleY("# of nonnormal-seed-strip clusters");
            histoGroupClusterStatus.addDataSet(h2_bstClusterStatus, 1);            
            H2F h2_bmtClusterStatus = new H2F("BMT cluster status", "BMT cluster status", 13, -0.5, 12.5, 13, -0.5, 12.5);
            h2_bmtClusterStatus.setTitleX("# of normal-seed-strip clusters");
            h2_bmtClusterStatus.setTitleY("# of nonnormal-seed-strip clusters");
            histoGroupClusterStatus.addDataSet(h2_bmtClusterStatus, 2);            
            H1F h1_ratioNoramlSeedStripsClusters = new H1F("ratio of noraml-seed-strip clusters", "ratio of noraml-seed-strip clusters", 30, 0, 1.01);
            h1_ratioNoramlSeedStripsClusters.setTitleX("ratio of noraml-seed-strip clusters");
            h1_ratioNoramlSeedStripsClusters.setTitleY("Counts");
            histoGroupClusterStatus.addDataSet(h1_ratioNoramlSeedStripsClusters, 3);             
            H1F h1_ratioNoramlSeedStripsBSTClusters = new H1F("ratio of noraml-seed-strip BST clusters", "ratio of noraml-seed-strip BST clusters", 30, 0, 1.01);
            h1_ratioNoramlSeedStripsBSTClusters.setTitleX("ratio of noraml-seed-strip clusters");
            h1_ratioNoramlSeedStripsBSTClusters.setTitleY("Counts");
            histoGroupClusterStatus.addDataSet(h1_ratioNoramlSeedStripsBSTClusters, 4); 
            H1F h1_ratioNoramlSeedStripsBMTClusters = new H1F("ratio of noraml-seed-strip BMT clusters", "ratio of noraml-seed-strip BMT clusters", 30, 0, 1.01);
            h1_ratioNoramlSeedStripsBMTClusters.setTitleX("ratio of noraml-seed-strip clusters");
            h1_ratioNoramlSeedStripsBMTClusters.setTitleY("Counts");
            histoGroupClusterStatus.addDataSet(h1_ratioNoramlSeedStripsBMTClusters, 5);            
            histoGroupMap.put(histoGroupClusterStatus.getName(), histoGroupClusterStatus);

            HistoGroup histoGroupHitStatus = new HistoGroup("hit status for Seed", 3, 2); 
            H2F h2_hitStatus = new H2F("hit status", "hit status", 101, -0.5, 100.5, 101, -0.5, 100.5);
            h2_hitStatus.setTitleX("# of normal hits");
            h2_hitStatus.setTitleY("# of bg hits");
            histoGroupHitStatus.addDataSet(h2_hitStatus, 0);             
            H2F h2_bstHitStatus = new H2F("BST hit status", "BST hit status", 101, -0.5, 100.5, 101, -0.5, 100.5);
            h2_bstHitStatus.setTitleX("# of normal hits");
            h2_bstHitStatus.setTitleY("# of bg hits");
            histoGroupHitStatus.addDataSet(h2_bstHitStatus, 1);            
            H2F h2_bmtHitStatus = new H2F("BMT hit status", "BMT hit status", 101, -0.5, 100.5, 101, -0.5, 100.5);
            h2_bmtHitStatus.setTitleX("# of normal hits");
            h2_bmtHitStatus.setTitleY("# of bg hits");
            histoGroupHitStatus.addDataSet(h2_bmtHitStatus, 2);            
            H1F h1_ratioNoramlHits = new H1F("ratio of noraml hits", "ratio of noraml hits", 30, 0, 1.01);
            h1_ratioNoramlHits.setTitleX("ratio of noraml-seed-strip hits");
            h1_ratioNoramlHits.setTitleY("Counts");
            histoGroupHitStatus.addDataSet(h1_ratioNoramlHits, 3);             
            H1F h1_ratioNoramlBSTHits = new H1F("ratio of noraml BST hits", "ratio of noraml BST hits", 30, 0, 1.01);
            h1_ratioNoramlBSTHits.setTitleX("ratio of noraml hits");
            h1_ratioNoramlBSTHits.setTitleY("Counts");
            histoGroupHitStatus.addDataSet(h1_ratioNoramlBSTHits, 4); 
            H1F h1_ratioNoramlBMTHits = new H1F("ratio of noraml BMT hits", "ratio of noraml BMT hits", 30, 0, 1.01);
            h1_ratioNoramlBMTHits.setTitleX("ratio of noraml hits");
            h1_ratioNoramlBMTHits.setTitleY("Counts");
            histoGroupHitStatus.addDataSet(h1_ratioNoramlBMTHits, 5);            
            histoGroupMap.put(histoGroupHitStatus.getName(), histoGroupHitStatus);
            
            HistoGroup histoGroupChi2overndfVsRatioNoraml = new HistoGroup("chi2overndfVsRatioNoraml", 3, 3); 
            H2F h2_chi2overndfVsRatioNoramlSeedStripsCrosses = new H2F("chi2overndfVsRatioNoramlSeedStripsCrosses", "chi2/ndf vs ratio of normal-seed-strips crosses", 30, 0, 1.01, 100, 0, 50);
            h2_chi2overndfVsRatioNoramlSeedStripsCrosses.setTitleX("ratio of normal-seed-strips crosses");
            h2_chi2overndfVsRatioNoramlSeedStripsCrosses.setTitleY("chi2/ndf");
            histoGroupChi2overndfVsRatioNoraml.addDataSet(h2_chi2overndfVsRatioNoramlSeedStripsCrosses, 0);             
            H2F h2_chi2overndfVsRatioNoramlSeedStripsBSTCrosses = new H2F("chi2overndfVsRatioNoramlSeedStripsBSTCrosses", "chi2/ndf vs ratio of normal-seed-strips BST crosses", 30, 0, 1.01, 100, 0, 50);
            h2_chi2overndfVsRatioNoramlSeedStripsBSTCrosses.setTitleX("ratio of normal-seed-strips crosses");
            h2_chi2overndfVsRatioNoramlSeedStripsBSTCrosses.setTitleY("chi2/ndf");
            histoGroupChi2overndfVsRatioNoraml.addDataSet(h2_chi2overndfVsRatioNoramlSeedStripsBSTCrosses, 1);             
            H2F h2_chi2overndfVsRatioNoramlSeedStripsBMTCrosses = new H2F("chi2overndfVsRatioNoramlSeedStripsBMTCrosses", "chi2/ndf vs ratio of normal-seed-strips BMT crosses", 30, 0, 1.01, 100, 0, 50);
            h2_chi2overndfVsRatioNoramlSeedStripsBMTCrosses.setTitleX("ratio of normal-seed-strips crosses");
            h2_chi2overndfVsRatioNoramlSeedStripsBMTCrosses.setTitleY("chi2/ndf");
            histoGroupChi2overndfVsRatioNoraml.addDataSet(h2_chi2overndfVsRatioNoramlSeedStripsBMTCrosses, 2);  
            
            H2F h2_chi2overndfVsRatioNoramlSeedStripClusters = new H2F("chi2overndfVsRatioNoramlSeedStripClusters", "chi2/ndf vs ratio of normal-seed-strip clusters", 30, 0, 1.01, 100, 0, 50);
            h2_chi2overndfVsRatioNoramlSeedStripClusters.setTitleX("ratio of normal-seed-strip clusters");
            h2_chi2overndfVsRatioNoramlSeedStripClusters.setTitleY("chi2/ndf");
            histoGroupChi2overndfVsRatioNoraml.addDataSet(h2_chi2overndfVsRatioNoramlSeedStripClusters, 3);             
            H2F h2_chi2overndfVsRatioNoramlSeedStripBSTClusters = new H2F("chi2overndfVsRatioNoramlSeedStripBSTClusters", "chi2/ndf vs ratio of normal-seed-strip BST clusters", 30, 0, 1.01, 100, 0, 50);
            h2_chi2overndfVsRatioNoramlSeedStripBSTClusters.setTitleX("ratio of normal-seed-strip clusters");
            h2_chi2overndfVsRatioNoramlSeedStripBSTClusters.setTitleY("chi2/ndf");
            histoGroupChi2overndfVsRatioNoraml.addDataSet(h2_chi2overndfVsRatioNoramlSeedStripBSTClusters, 4);             
            H2F h2_chi2overndfVsRatioNoramlSeedStripBMTClusters = new H2F("chi2overndfVsRatioNoramlSeedStripBMTClusters", "chi2/ndf vs ratio of normal-seed-strip BMT clusters", 30, 0, 1.01, 100, 0, 50);
            h2_chi2overndfVsRatioNoramlSeedStripBMTClusters.setTitleX("ratio of normal-seed-strip clusters");
            h2_chi2overndfVsRatioNoramlSeedStripBMTClusters.setTitleY("chi2/ndf");
            histoGroupChi2overndfVsRatioNoraml.addDataSet(h2_chi2overndfVsRatioNoramlSeedStripBMTClusters, 5);               
            
            H2F h2_chi2overndfVsRatioNoramlHits = new H2F("chi2overndfVsRatioNoramlHits", "chi2/ndf vs ratio of normal hits", 30, 0, 1.01, 100, 0, 50);
            h2_chi2overndfVsRatioNoramlHits.setTitleX("ratio of normal hits");
            h2_chi2overndfVsRatioNoramlHits.setTitleY("chi2/ndf");
            histoGroupChi2overndfVsRatioNoraml.addDataSet(h2_chi2overndfVsRatioNoramlHits, 6);             
            H2F h2_chi2overndfVsRatioNoramlBSTHits = new H2F("chi2overndfVsRatioNoramlBSTHits", "chi2/ndf vs ratio of normal BST hits", 30, 0, 1.01, 100, 0, 50);
            h2_chi2overndfVsRatioNoramlBSTHits.setTitleX("ratio of normal hits");
            h2_chi2overndfVsRatioNoramlBSTHits.setTitleY("chi2/ndf");
            histoGroupChi2overndfVsRatioNoraml.addDataSet(h2_chi2overndfVsRatioNoramlBSTHits, 7);             
            H2F h2_chi2overndfVsRatioNoramlBMTHits = new H2F("chi2overndfVsRatioNoramlBMTHits", "chi2/ndf vs ratio of normal BMT hits", 30, 0, 1.01, 100, 0, 50);
            h2_chi2overndfVsRatioNoramlBMTHits.setTitleX("ratio of normal hits");
            h2_chi2overndfVsRatioNoramlBMTHits.setTitleY("chi2/ndf");
            histoGroupChi2overndfVsRatioNoraml.addDataSet(h2_chi2overndfVsRatioNoramlBMTHits, 8);             
            
            histoGroupMap.put(histoGroupChi2overndfVsRatioNoraml.getName(), histoGroupChi2overndfVsRatioNoraml);
            
        }
        
    }
             
    public void processEvent(Event event){        
        //Read banks
         //////// Read banks
        LocalEvent localEvent = new LocalEvent(reader, event);
        processEvent(localEvent);
    }
    
    public void processEvent(LocalEvent localEvent){ 
        List<Seed> seeds = localEvent.getSeeds(Constants.PASS);
        
        HistoGroup histoGroupFittingParameters = histoGroupMap.get("fittingParameters for Seed"); 
        HistoGroup histoGroupKinematics = histoGroupMap.get("kinematics for Seed"); 
        for(Seed seed : seeds){
            histoGroupFittingParameters.getH1F("q" ).fill(seed.q()); 
            histoGroupFittingParameters.getH1F("pt" ).fill(seed.pt()); 
            histoGroupFittingParameters.getH1F("phi0" ).fill(seed.phi0()); 
            histoGroupFittingParameters.getH1F("d0" ).fill(seed.d0()); 
            histoGroupFittingParameters.getH1F("tandip" ).fill(seed.tandip()); 
            histoGroupFittingParameters.getH1F("z0" ).fill(seed.z0());             
            histoGroupFittingParameters.getH1F("xb" ).fill(seed.xb()); 
            histoGroupFittingParameters.getH1F("yb" ).fill(seed.yb()); 
            histoGroupFittingParameters.getH1F("chi2_per_ndf" ).fill(seed.chi2()/seed.ndf()); 
            histoGroupFittingParameters.getH1F("circlefit_chi2_per_ndf" ).fill(seed.circlefit_chi2_per_ndf()); 
            histoGroupFittingParameters.getH1F("linefit_chi2_per_ndf" ).fill(seed.linefit_chi2_per_ndf()); 
            
            histoGroupKinematics.getH1F("p" ).fill(seed.momentum().mag()); 
            histoGroupKinematics.getH1F("theta" ).fill(seed.momentum().theta()); 
            histoGroupKinematics.getH1F("phi" ).fill(seed.momentum().phi()); 
            histoGroupKinematics.getH1F("x" ).fill(seed.vertex().x()); 
            histoGroupKinematics.getH1F("y" ).fill(seed.vertex().y()); 
            histoGroupKinematics.getH1F("z" ).fill(seed.vertex().z()); 
        }
        
        if(Constants.BG){
            HistoGroup histoGroupCrossStatus = histoGroupMap.get("cross status for Seed"); 
            HistoGroup histoGroupClusterStatus = histoGroupMap.get("cluster status for Seed"); 
            HistoGroup histoGroupHitStatus = histoGroupMap.get("hit status for Seed");
            HistoGroup histoGroupChi2overndfVsRatioNoraml = histoGroupMap.get("chi2overndfVsRatioNoraml");
            for(Seed seed : seeds){
                histoGroupCrossStatus.getH2F("cross status" ).fill(seed.getNumNormalSeedStripsCrosses(), seed.getNumNonnormalSeedStripsCrosses()); 
                histoGroupCrossStatus.getH2F("BST cross status" ).fill(seed.getNumNormalSeedStripsBSTCrosses(), seed.getNumNonnormalSeedStripsBSTCrosses()); 
                histoGroupCrossStatus.getH2F("BMT cross status" ).fill(seed.getNumNormalSeedStripsBMTCrosses(), seed.getNumNonnormalSeedStripsBMTCrosses());                 
                histoGroupCrossStatus.getH1F("ratio of noraml-seed-strips crosses" ).fill(seed.getRatioNormalSeedStripsCrosses());
                histoGroupCrossStatus.getH1F("ratio of noraml-seed-strips BST crosses" ).fill(seed.getRatioNormalSeedStripsBSTCrosses()); 
                histoGroupCrossStatus.getH1F("ratio of noraml-seed-strips BMT crosses" ).fill(seed.getRatioNormalSeedStripsBMTCrosses()); 
                
                histoGroupClusterStatus.getH2F("cluster status" ).fill(seed.getNumNormalSeedStripClusters(), seed.getNumNonnormalSeedStripClusters()); 
                histoGroupClusterStatus.getH2F("BST cluster status" ).fill(seed.getNumNormalSeedStripBSTClusters(), seed.getNumNonnormalSeedStripBSTClusters()); 
                histoGroupClusterStatus.getH2F("BMT cluster status" ).fill(seed.getNumNormalSeedStripBMTClusters(), seed.getNumNonnormalSeedStripBMTClusters());                 
                histoGroupClusterStatus.getH1F("ratio of noraml-seed-strip clusters" ).fill(seed.getRatioNormalSeedStripClusters());
                histoGroupClusterStatus.getH1F("ratio of noraml-seed-strip BST clusters" ).fill(seed.getRatioNormalSeedStripBSTClusters()); 
                histoGroupClusterStatus.getH1F("ratio of noraml-seed-strip BMT clusters" ).fill(seed.getRatioNormalSeedStripBMTClusters()); 
                
                histoGroupHitStatus.getH2F("hit status" ).fill(seed.getNumNormalHits(), seed.getNumBgHits()); 
                histoGroupHitStatus.getH2F("BST hit status" ).fill(seed.getNumNormalBSTHits(), seed.getNumBgBSTHits()); 
                histoGroupHitStatus.getH2F("BMT hit status" ).fill(seed.getNumNormalBMTHits(), seed.getNumBgBMTHits());                 
                histoGroupHitStatus.getH1F("ratio of noraml hits" ).fill(seed.getRatioNormalHits());
                histoGroupHitStatus.getH1F("ratio of noraml BST hits" ).fill(seed.getRatioNormalBSTHits()); 
                histoGroupHitStatus.getH1F("ratio of noraml BMT hits" ).fill(seed.getRatioNormalBMTHits()); 

                histoGroupChi2overndfVsRatioNoraml.getH2F("chi2overndfVsRatioNoramlSeedStripsCrosses").fill(seed.getRatioNormalSeedStripsCrosses(), seed.chi2()/seed.ndf());
                histoGroupChi2overndfVsRatioNoraml.getH2F("chi2overndfVsRatioNoramlSeedStripsBSTCrosses").fill(seed.getRatioNormalSeedStripsBSTCrosses(), seed.chi2()/seed.ndf());
                histoGroupChi2overndfVsRatioNoraml.getH2F("chi2overndfVsRatioNoramlSeedStripsBMTCrosses").fill(seed.getRatioNormalSeedStripsBMTCrosses(), seed.chi2()/seed.ndf());
                
                histoGroupChi2overndfVsRatioNoraml.getH2F("chi2overndfVsRatioNoramlSeedStripClusters").fill(seed.getRatioNormalSeedStripClusters(), seed.chi2()/seed.ndf());
                histoGroupChi2overndfVsRatioNoraml.getH2F("chi2overndfVsRatioNoramlSeedStripBSTClusters").fill(seed.getRatioNormalSeedStripBSTClusters(), seed.chi2()/seed.ndf());
                histoGroupChi2overndfVsRatioNoraml.getH2F("chi2overndfVsRatioNoramlSeedStripBMTClusters").fill(seed.getRatioNormalSeedStripBMTClusters(), seed.chi2()/seed.ndf()); 
                
                histoGroupChi2overndfVsRatioNoraml.getH2F("chi2overndfVsRatioNoramlHits").fill(seed.getRatioNormalHits(), seed.chi2()/seed.ndf());
                histoGroupChi2overndfVsRatioNoraml.getH2F("chi2overndfVsRatioNoramlBSTHits").fill(seed.getRatioNormalBSTHits(), seed.chi2()/seed.ndf());
                histoGroupChi2overndfVsRatioNoraml.getH2F("chi2overndfVsRatioNoramlBMTHits").fill(seed.getRatioNormalBMTHits(), seed.chi2()/seed.ndf());                 
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
        
        SeedRecon analysis = new SeedRecon();
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
