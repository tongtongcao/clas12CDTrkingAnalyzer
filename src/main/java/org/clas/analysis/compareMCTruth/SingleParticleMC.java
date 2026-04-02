package org.clas.analysis.compareMCTruth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JFrame;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.graphics.EmbeddedCanvasTabbed;
import org.jlab.jnp.hipo4.data.Event;
import org.jlab.jnp.hipo4.data.SchemaFactory;
import org.jlab.jnp.hipo4.io.HipoReader;
import org.jlab.utils.benchmark.ProgressPrintout;
import org.jlab.utils.options.OptionParser;
import org.jlab.groot.math.F1D;

import org.clas.utilities.Constants;
import org.clas.element.Track;
import org.clas.element.MCParticle;
import org.clas.graph.HistoGroup;
import org.clas.analysis.BaseAnalysis;
import org.clas.reader.Banks;
import org.clas.reader.LocalEvent;

/**
 *
 * @author Tongtong Cao
 */
public class SingleParticleMC extends BaseAnalysis{    
    private String outputPrefix = "";
    
    public SingleParticleMC(){}

    public SingleParticleMC(Banks banks){
        super(banks);
    }
    
    @Override
    public void createHistoGroupMap(){                   
        HistoGroup histoGroupMCParticle = new HistoGroup("MCParticle", 3, 2);
        H1F h1_pMCParticle = new H1F("pMCParticle", "p", 100, 0, 11);
        h1_pMCParticle.setTitleX("p (GeV/c)");
        h1_pMCParticle.setTitleY("Counts");
        H1F h1_thetaMCParticle = new H1F("thetaMCParticle", "#theta", 100, 0, 180);
        h1_thetaMCParticle.setTitleX("#theta (deg)");
        h1_thetaMCParticle.setTitleY("Counts");
        H1F h1_phiMCParticle = new H1F("phiMCParticle", "#phi", 100, -180, 180);
        h1_phiMCParticle.setTitleX("#phi (deg)");
        h1_phiMCParticle.setTitleY("Counts");
        H1F h1_vxMCParticle = new H1F("vxMCParticle", "vx", 100, -5, 5);
        h1_vxMCParticle.setTitleX("vx (cm)");
        h1_vxMCParticle.setTitleY("Counts");     
        H1F h1_vyMCParticle = new H1F("vyMCParticle", "vy", 100, -5, 5);
        h1_vyMCParticle.setTitleX("vy (cm)");
        h1_vyMCParticle.setTitleY("Counts");                
        H1F h1_vzMCParticle = new H1F("vzMCParticle", "vz", 100, -15, 10);
        h1_vzMCParticle.setTitleX("vz (cm)");
        h1_vzMCParticle.setTitleY("Counts");
        histoGroupMCParticle.addDataSet(h1_pMCParticle, 0);
        histoGroupMCParticle.addDataSet(h1_thetaMCParticle, 1);
        histoGroupMCParticle.addDataSet(h1_phiMCParticle, 2);
        histoGroupMCParticle.addDataSet(h1_vxMCParticle, 3);
        histoGroupMCParticle.addDataSet(h1_vyMCParticle, 4);
        histoGroupMCParticle.addDataSet(h1_vzMCParticle, 5);        
        histoGroupMap.put(histoGroupMCParticle.getName(), histoGroupMCParticle);  
        
        HistoGroup histoGroupMapping = new HistoGroup("Mapping", 2, 1);
        H2F h2_trkMCPartMap = new H2F("trkMCPartMap", "trkMCPartMap", 100, 0, 1, 100, 0, 10);
        h2_trkMCPartMap.setTitleX("dist. for mom. (GeV)");
        h2_trkMCPartMap.setTitleY("dist. for vtx (cm)");
        histoGroupMapping.addDataSet(h2_trkMCPartMap, 0);                 
        histoGroupMap.put(histoGroupMapping.getName(), histoGroupMapping);
        
        HistoGroup histoDiffGroup = new HistoGroup("Diff", 3, 2);
        H1F h1_p_diff = new H1F("pDiff", "#Deltap/p", 100, -0.5, 0.5);
        h1_p_diff.setTitleX("#Deltap/p");
        h1_p_diff.setTitleY("Counts");
        H1F h1_theta_diff = new H1F("thetaDiff", "#Delta#theta", 100, -5, 5);
        h1_theta_diff.setTitleX("#Delta#theta (deg)");
        h1_theta_diff.setTitleY("Counts");
        H1F h1_phi_diff = new H1F("phiDiff", "#Delta#phi", 100, -2, 2);
        h1_phi_diff.setTitleX("#Delta#phi (deg)");
        h1_phi_diff.setTitleY("Counts");
        H1F h1_vx_diff = new H1F("vxDiff", "#DeltaV_x", 100, -0.1, 0.1);
        h1_vx_diff.setTitleX("#DeltaV_x (cm)");
        h1_vx_diff.setTitleY("Counts");     
        H1F h1_vy_diff = new H1F("vyDiff", "#DeltaV_y", 100, -0.1, 0.1);
        h1_vy_diff.setTitleX("#DeltaV_y (cm)");
        h1_vy_diff.setTitleY("Counts");                
        H1F h1_vz_diff = new H1F("vzDiff", "#DeltaV_z", 100, -1, 1);
        h1_vz_diff.setTitleX("#DeltaV_z (cm)");
        h1_vz_diff.setTitleY("Counts");        
        histoDiffGroup.addDataSet(h1_p_diff, 0);
        histoDiffGroup.addDataSet(h1_theta_diff, 1);
        histoDiffGroup.addDataSet(h1_phi_diff, 2);
        histoDiffGroup.addDataSet(h1_vx_diff, 3);
        histoDiffGroup.addDataSet(h1_vy_diff, 4);
        histoDiffGroup.addDataSet(h1_vz_diff, 5);        
        histoGroupMap.put(histoDiffGroup.getName(), histoDiffGroup);         
        
        
        HistoGroup histoGroupUTrackMapping = new HistoGroup("uTrackMapping", 2, 1);
        H2F h2_utrkMCPartMap = new H2F("utrkMCPartMap", "utrkMCPartMap", 100, 0, 1, 100, 0, 10);
        h2_utrkMCPartMap.setTitleX("dist. for mom. (GeV)");
        h2_utrkMCPartMap.setTitleY("dist. for vtx (cm)");
        histoGroupUTrackMapping.addDataSet(h2_utrkMCPartMap, 0);                 
        histoGroupMap.put(histoGroupUTrackMapping.getName(), histoGroupUTrackMapping);
        
        HistoGroup histoUTrackDiffGroup = new HistoGroup("uTrackDiff", 3, 2);
        H1F h1_p_diff_uTrack = new H1F("pDiff", "#Deltap/p", 100, -0.5, 0.5);
        h1_p_diff_uTrack.setTitleX("#Deltap/p");
        h1_p_diff_uTrack.setTitleY("Counts");
        H1F h1_theta_diff_uTrack = new H1F("thetaDiff", "#Delta#theta", 100, -5, 5);
        h1_theta_diff_uTrack.setTitleX("#Delta#theta (deg)");
        h1_theta_diff_uTrack.setTitleY("Counts");
        H1F h1_phi_diff_uTrack = new H1F("phiDiff", "#Delta#phi", 100, -5, 5);
        h1_phi_diff_uTrack.setTitleX("#Delta#phi (deg)");
        h1_phi_diff_uTrack.setTitleY("Counts");
        H1F h1_vx_diff_uTrack = new H1F("vxDiff", "#DeltaV_x", 100, -0.3, 0.3);
        h1_vx_diff_uTrack.setTitleX("#DeltaV_x (cm)");
        h1_vx_diff.setTitleY("Counts");     
        H1F h1_vy_diff_uTrack = new H1F("vyDiff", "#DeltaV_y", 100, -0.3, 0.3);
        h1_vy_diff_uTrack.setTitleX("#DeltaV_y (cm)");
        h1_vy_diff_uTrack.setTitleY("Counts");                
        H1F h1_vz_diff_uTrack = new H1F("vzDiff", "#DeltaV_z", 100, -1, 1);
        h1_vz_diff_uTrack.setTitleX("#DeltaV_z (cm)");
        h1_vz_diff_uTrack.setTitleY("Counts");        
        histoUTrackDiffGroup.addDataSet(h1_p_diff_uTrack, 0);
        histoUTrackDiffGroup.addDataSet(h1_theta_diff_uTrack, 1);
        histoUTrackDiffGroup.addDataSet(h1_phi_diff_uTrack, 2);
        histoUTrackDiffGroup.addDataSet(h1_vx_diff_uTrack, 3);
        histoUTrackDiffGroup.addDataSet(h1_vy_diff_uTrack, 4);
        histoUTrackDiffGroup.addDataSet(h1_vz_diff_uTrack, 5);        
        histoGroupMap.put(histoUTrackDiffGroup.getName(), histoUTrackDiffGroup);        
    }
             
    public void processEvent(Event event){
        //Read banks
        LocalEvent localEvent = new LocalEvent(reader, event);        
        
        List<MCParticle> mcParts = localEvent.getMCParticles();
             
        List<Track> tracks = localEvent.getTracks(Constants.PASS, false);  
        List<Track> utracks = localEvent.getTracks(Constants.PASS, true);  
                        
        HistoGroup histoGroupMCParticle = histoGroupMap.get("MCParticle");
        for(MCParticle mcPart : mcParts){
            histoGroupMCParticle.getH1F("pMCParticle").fill(mcPart.mom().mag());
            histoGroupMCParticle.getH1F("thetaMCParticle").fill(mcPart.mom().theta()/Math.PI*180.);
            histoGroupMCParticle.getH1F("phiMCParticle").fill(mcPart.mom().phi()/Math.PI*180.);
            histoGroupMCParticle.getH1F("vxMCParticle").fill(mcPart.vertex().x());
            histoGroupMCParticle.getH1F("vyMCParticle").fill(mcPart.vertex().y());
            histoGroupMCParticle.getH1F("vzMCParticle").fill(mcPart.vertex().z()); 
        }
        
        HistoGroup histoGroupMapping = histoGroupMap.get("Mapping");         
        Map<MCParticle, List<Track>> map_mcPart_trkList = new HashMap();
        for(MCParticle mcPart : mcParts){
            for(Track trk : tracks){                                
                    double distMom = mcPart.euclideanDistanceMom(trk);
                    double distVtx = mcPart.euclideanDistanceVertex(trk);
                    histoGroupMapping.getH2F("trkMCPartMap").fill(distMom, distVtx);
                    if(map_mcPart_trkList.get(mcPart) == null){
                        List<Track> trkList = new ArrayList();
                        trkList.add(trk);
                        map_mcPart_trkList.put(mcPart, trkList);
                    }
                    else {
                        map_mcPart_trkList.get(mcPart).add(trk);
                    }
            }
        }
                
        Map<MCParticle, Track> map_mcPart_trk = new HashMap();
        for(MCParticle mcPart : map_mcPart_trkList.keySet()){
            double minDistMom = 9999;
            Track cloestTrk = null;            
            for(Track trk : map_mcPart_trkList.get(mcPart)){
                double distMom = mcPart.euclideanDistanceMom(trk);
                if(distMom < minDistMom){
                    minDistMom = distMom;
                    cloestTrk = trk;
                }
            }
            map_mcPart_trk.put(mcPart, cloestTrk);
        }
        
        HistoGroup histoDiffGroup = histoGroupMap.get("Diff");
        for(MCParticle mcPart : map_mcPart_trk.keySet()){
            Track trk = map_mcPart_trk.get(mcPart);
            
            histoDiffGroup.getH1F("pDiff").fill((mcPart.mom().mag() - trk.momentum().mag())/mcPart.mom().mag());
            histoDiffGroup.getH1F("thetaDiff").fill((mcPart.mom().theta() - trk.momentum().theta())/Math.PI*180.);
            histoDiffGroup.getH1F("phiDiff").fill((mcPart.mom().phi() - trk.momentum().phi())/Math.PI*180.);
            histoDiffGroup.getH1F("vxDiff").fill(mcPart.vertex().x() - trk.vertex().x());
            histoDiffGroup.getH1F("vyDiff").fill(mcPart.vertex().y() - trk.vertex().y());
            histoDiffGroup.getH1F("vzDiff").fill(mcPart.vertex().z() - trk.vertex().z()); 
        }
        
        HistoGroup histoGroupUTrackMapping = histoGroupMap.get("uTrackMapping");         
        Map<MCParticle, List<Track>> map_mcPart_utrkList = new HashMap();
        for(MCParticle mcPart : mcParts){
            for(Track trk : utracks){                                
                    double distMom = mcPart.euclideanDistanceMom(trk);
                    double distVtx = mcPart.euclideanDistanceVertex(trk);
                    histoGroupUTrackMapping.getH2F("utrkMCPartMap").fill(distMom, distVtx);
                    if(map_mcPart_utrkList.get(mcPart) == null){
                        List<Track> trkList = new ArrayList();
                        trkList.add(trk);
                        map_mcPart_utrkList.put(mcPart, trkList);
                    }
                    else {
                        map_mcPart_utrkList.get(mcPart).add(trk);
                    }
            }
        }        
        
        
        Map<MCParticle, Track> map_mcPart_utrk = new HashMap();
        for(MCParticle mcPart : map_mcPart_utrkList.keySet()){
            double minDistMom = 9999;
            Track cloestTrk = null;            
            for(Track trk : map_mcPart_utrkList.get(mcPart)){
                double distMom = mcPart.euclideanDistanceMom(trk);
                if(distMom < minDistMom){
                    minDistMom = distMom;
                    cloestTrk = trk;
                }
            }
            map_mcPart_utrk.put(mcPart, cloestTrk);
        }
        
        HistoGroup histoUTrackDiffGroup = histoGroupMap.get("uTrackDiff");
        for(MCParticle mcPart : map_mcPart_utrk.keySet()){
            Track trk = map_mcPart_utrk.get(mcPart);
            
            histoUTrackDiffGroup.getH1F("pDiff").fill((mcPart.mom().mag() - trk.momentum().mag())/mcPart.mom().mag());
            histoUTrackDiffGroup.getH1F("thetaDiff").fill((mcPart.mom().theta() - trk.momentum().theta())/Math.PI*180.);
            histoUTrackDiffGroup.getH1F("phiDiff").fill((mcPart.mom().phi() - trk.momentum().phi())/Math.PI*180.);
            histoUTrackDiffGroup.getH1F("vxDiff").fill(mcPart.vertex().x() - trk.vertex().x());
            histoUTrackDiffGroup.getH1F("vyDiff").fill(mcPart.vertex().y() - trk.vertex().y());
            histoUTrackDiffGroup.getH1F("vzDiff").fill(mcPart.vertex().z() - trk.vertex().z()); 
        }        
    }
    
    public void postEventProcessing(){
        
        HistoGroup histoDiffGroup = histoGroupMap.get("Diff");        
        F1D func_p     = fitPeakGaussian(histoDiffGroup.getH1F("pDiff"), "func_p");
        F1D func_theta = fitPeakGaussian(histoDiffGroup.getH1F("thetaDiff"), "func_theta");
        F1D func_phi   = fitPeakGaussian(histoDiffGroup.getH1F("phiDiff"), "func_phi");
        F1D func_vx    = fitPeakGaussian(histoDiffGroup.getH1F("vxDiff"), "func_vx");
        F1D func_vy    = fitPeakGaussian(histoDiffGroup.getH1F("vyDiff"), "func_vy");
        F1D func_vz    = fitPeakGaussian(histoDiffGroup.getH1F("vzDiff"), "func_vz");
        
        HistoGroup histoUTrackDiffGroup = histoGroupMap.get("uTrackDiff");        
        F1D func_p_uTrack     = fitPeakGaussian(histoUTrackDiffGroup.getH1F("pDiff"), "func_p");
        F1D func_theta_uTrack = fitPeakGaussian(histoUTrackDiffGroup.getH1F("thetaDiff"), "func_theta");
        F1D func_phi_uTrack   = fitPeakGaussian(histoUTrackDiffGroup.getH1F("phiDiff"), "func_phi");
        F1D func_vx_uTrack    = fitPeakGaussian(histoUTrackDiffGroup.getH1F("vxDiff"), "func_vx");
        F1D func_vy_uTrack    = fitPeakGaussian(histoUTrackDiffGroup.getH1F("vyDiff"), "func_vy");
        F1D func_vz_uTrack    = fitPeakGaussian(histoUTrackDiffGroup.getH1F("vzDiff"), "func_vz");
        
        String fitFileName = (outputPrefix.isEmpty() ? "" : outputPrefix + "_") + "fit_results.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fitFileName))) {

            writer.write("Parameter\tMean\tSigma\n");

            writer.write(String.format("p\t%f\t%f\n",
                    func_p.getParameter(1), func_p.getParameter(2)));

            writer.write(String.format("theta\t%f\t%f\n",
                    func_theta.getParameter(1), func_theta.getParameter(2)));

            writer.write(String.format("phi\t%f\t%f\n",
                    func_phi.getParameter(1), func_phi.getParameter(2)));

            writer.write(String.format("vx\t%f\t%f\n",
                    func_vx.getParameter(1), func_vx.getParameter(2)));

            writer.write(String.format("vy\t%f\t%f\n",
                    func_vy.getParameter(1), func_vy.getParameter(2)));

            writer.write(String.format("vz\t%f\t%f\n",
                    func_vz.getParameter(1), func_vz.getParameter(2)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String fitFileName_uTrack = (outputPrefix.isEmpty() ? "" : outputPrefix + "_") + "fit_results_uTrack.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fitFileName_uTrack))) {

            writer.write("Parameter\tMean\tSigma\n");

            writer.write(String.format("p\t%f\t%f\n",
                    func_p_uTrack.getParameter(1), func_p_uTrack.getParameter(2)));

            writer.write(String.format("theta\t%f\t%f\n",
                    func_theta_uTrack.getParameter(1), func_theta_uTrack.getParameter(2)));

            writer.write(String.format("phi\t%f\t%f\n",
                    func_phi_uTrack.getParameter(1), func_phi_uTrack.getParameter(2)));

            writer.write(String.format("vx\t%f\t%f\n",
                    func_vx_uTrack.getParameter(1), func_vx_uTrack.getParameter(2)));

            writer.write(String.format("vy\t%f\t%f\n",
                    func_vy_uTrack.getParameter(1), func_vy_uTrack.getParameter(2)));

            writer.write(String.format("vz\t%f\t%f\n",
                    func_vz_uTrack.getParameter(1), func_vz_uTrack.getParameter(2)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static F1D fitPeakGaussian(H1F h, String funcName) {
        int maxBin = h.getMaximumBin();

        int binMin = Math.max(0, maxBin - 8);
        int binMax = Math.min(h.getDataSize(0) - 1, maxBin + 8);

        double xMin = h.getDataX(binMin);
        double xMax = h.getDataX(binMax);

        F1D func = new F1D(funcName, "[amp]*gaus(x,[mean],[sigma])", xMin, xMax);

        func.setParameter(0, h.getMax());
        func.setParameter(1, h.getDataX(maxBin));
        func.setParameter(2, (xMax - xMin) / 6.0);

        func.setParLimits(2, 0.0, (xMax - xMin));

        func.setLineColor(2);
        func.setOptStat(1110);
        
        h.fit(func);

        return func;  
    }    
    
    public void setOutputPrefix(String prefix) {
        this.outputPrefix = prefix;
    }
                        
    public static void main(String[] args){
        OptionParser parser = new OptionParser("studyBgEffectsDC");
        parser.setRequiresInputList(false);
        // valid options for event-base analysis
        parser.addOption("-o"          ,"",     "output file name prefix");
        parser.addOption("-n"          ,"-1",   "maximum number of events to process");
        parser.addOption("-energy"     ,"10.6", "beam energy");
        parser.addOption("-pass", "2", "pass 1 or 2");
        parser.addOption("-uTrack", "0", "if unconstrained track (0/1)");
        
        parser.addOption("-plot"       ,"1",    "display histograms (0/1)");
        
        // histogram based analysis
        parser.addOption("-histo"      ,"0",    "read histogram file (0/1)");
        
        parser.parse(args);
        
        String namePrefix  = parser.getOption("-o").stringValue(); 
        int   maxEvents  = parser.getOption("-n").intValue();      
        boolean openWindow   = (parser.getOption("-plot").intValue()!=0);
        boolean readHistos   = (parser.getOption("-histo").intValue()!=0);   
        
        int pass  = parser.getOption("-pass").intValue(); 
        Constants.PASS = pass;
        boolean uTrack = (parser.getOption("-uTrack").intValue() != 0);        
        
        List<String> inputList = parser.getInputList();
        if(inputList.isEmpty()==true){
            parser.printUsage();
            inputList.add("/Users/caot/research/clas12/data/mc/uRWELL/upgradeTrackingWithuRWELL/rga-sidis-uRWell-2R_denoise/0nA/reconBg/0000.hipo");
            maxEvents = 1000;
            //System.out.println("\n >>>> error: no input file is specified....\n");
            //System.exit(0);
        }

        String histoName   = "histo.hipo"; 
        if(!namePrefix.isEmpty()) {
            histoName  = namePrefix + "_" + histoName;
        }
        
        SingleParticleMC analysis = new SingleParticleMC();
        analysis.setOutputPrefix(namePrefix);
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
            
            analysis.postEventProcessing();
            progress.showStatus();
            reader.close();            
            analysis.saveHistos(histoName);            
        }
        else{
            analysis.readHistos(inputList.get(0)); 
        }
        
        if(openWindow) {
            JFrame frame = new JFrame();
            EmbeddedCanvasTabbed canvas = analysis.plotHistos();
            frame.setSize(1500, 750);
            frame.add(canvas);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
    
}
