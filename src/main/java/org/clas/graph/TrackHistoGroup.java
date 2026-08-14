package org.clas.graph;

import java.util.LinkedHashMap;
import java.util.List;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.data.IDataSet;
import org.jlab.groot.data.TDirectory;
import org.jlab.groot.group.DataGroup;

/**
 *
 * @author Tongtong
 */
public class TrackHistoGroup extends HistoGroup{
    
    private LinkedHashMap<String,H1F> h1_nKFIters_map;
    private LinkedHashMap<String,H1F> h1_chi2overndf_map;
    private LinkedHashMap<String,H1F> h1_ndf_map;
    private LinkedHashMap<String,H1F> h1_p_map;
    private LinkedHashMap<String,H1F> h1_theta_map;
    private LinkedHashMap<String,H1F> h1_phi_map;
    private LinkedHashMap<String,H1F> h1_vx_map;
    private LinkedHashMap<String,H1F> h1_vy_map;
    private LinkedHashMap<String,H1F> h1_vz_map;
    private LinkedHashMap<String,H1F> h1_q_map;
    private LinkedHashMap<String,H1F> h1_tandip_map;
    private LinkedHashMap<String,H1F> h1_z0_map;
    private LinkedHashMap<String,H1F> h1_pt_map;
    private LinkedHashMap<String,H1F> h1_phi0_map;
    private LinkedHashMap<String,H1F> h1_d0_map;
    
    private H1F h1_nKFIters_diff;
    private H1F h1_chi2overndf_diff;
    private H1F h1_ndf_diff;
    private H1F h1_p_diff;
    private H1F h1_theta_diff;
    private H1F h1_phi_diff;
    private H1F h1_vx_diff;
    private H1F h1_vy_diff;
    private H1F h1_vz_diff;  
    private H1F h1_q_diff; 
    private H1F h1_tandip_diff;
    private H1F h1_z0_diff;
    private H1F h1_pt_diff;
    private H1F h1_phi0_diff;
    private H1F h1_d0_diff;
    
        
    public TrackHistoGroup(String str) {
        super(str);
        h1_nKFIters_map = new LinkedHashMap<String,H1F>();
        h1_chi2overndf_map = new LinkedHashMap<String,H1F>();
        h1_ndf_map = new LinkedHashMap<String,H1F>();
        h1_p_map = new LinkedHashMap<String,H1F>();
        h1_theta_map = new LinkedHashMap<String,H1F>();
        h1_phi_map = new LinkedHashMap<String,H1F>();
        h1_vx_map = new LinkedHashMap<String,H1F>();
        h1_vy_map = new LinkedHashMap<String,H1F>();
        h1_vz_map = new LinkedHashMap<String,H1F>();
        h1_q_map = new LinkedHashMap<String,H1F>();
        h1_tandip_map = new LinkedHashMap<String,H1F>();
        h1_z0_map = new LinkedHashMap<String,H1F>();
        h1_pt_map = new LinkedHashMap<String,H1F>();
        h1_phi0_map = new LinkedHashMap<String,H1F>();
        h1_d0_map = new LinkedHashMap<String,H1F>();
    }
    
    public TrackHistoGroup(String str, int ncols, int nrows) {
        super(str, ncols, nrows);
        h1_nKFIters_map = new LinkedHashMap<String,H1F>();
        h1_chi2overndf_map = new LinkedHashMap<String,H1F>();
        h1_ndf_map = new LinkedHashMap<String,H1F>();
        h1_p_map = new LinkedHashMap<String,H1F>();
        h1_theta_map = new LinkedHashMap<String,H1F>();
        h1_phi_map = new LinkedHashMap<String,H1F>();
        h1_vx_map = new LinkedHashMap<String,H1F>();
        h1_vy_map = new LinkedHashMap<String,H1F>();
        h1_vz_map = new LinkedHashMap<String,H1F>();
        h1_q_map = new LinkedHashMap<String,H1F>();
        h1_tandip_map = new LinkedHashMap<String,H1F>();
        h1_z0_map = new LinkedHashMap<String,H1F>();
        h1_pt_map = new LinkedHashMap<String,H1F>();
        h1_phi0_map = new LinkedHashMap<String,H1F>();
        h1_d0_map = new LinkedHashMap<String,H1F>();        
    } 
    
    public void addTrackHistos(int color, int startOrder){ 
        addTrackHistos("", color, startOrder);      
    }
    
    public void addTrackHistos(String postflix, int color, int startOrder){
        H1F h1_nKFIters= new H1F("nKFIters"+postflix, "nKFIters", 5, 0.5, 5.5);
        h1_nKFIters.setTitleX("nKFIters");
        h1_nKFIters.setTitleY("Counts");
        h1_nKFIters.setLineColor(color);        
        H1F h1_chi2overndf= new H1F("chi2overndf"+postflix, "#Chi^2/ndf", 100, 0, 40);
        h1_chi2overndf.setTitleX("#Chi^2/ndf");
        h1_chi2overndf.setTitleY("Counts");
        h1_chi2overndf.setLineColor(color);
        H1F h1_ndf= new H1F("ndf"+postflix, "ndf", 12, 0.5, 12.5);
        h1_ndf.setTitleX("ndf");
        h1_ndf.setTitleY("Counts");
        h1_ndf.setLineColor(color);
        H1F h1_p = new H1F("p"+postflix, "p", 100, 0, 5);
        h1_p.setTitleX("p (GeV/c)");
        h1_p.setTitleY("Counts");
        h1_p.setLineColor(color);
        H1F h1_theta = new H1F("theta"+postflix, "#theta", 100, 0, Math.PI);
        h1_theta.setTitleX("#theta (rad)");
        h1_theta.setTitleY("Counts");
        h1_theta.setLineColor(color);
        H1F h1_phi = new H1F("phi"+postflix, "#phi", 100, -Math.PI, Math.PI);
        h1_phi.setTitleX("#phi (rad)");
        h1_phi.setTitleY("Counts");
        h1_phi.setLineColor(color);
        H1F h1_vx = new H1F("vx"+postflix, "vx", 100, -5, 5);
        h1_vx.setTitleX("vx (cm)");
        h1_vx.setTitleY("Counts");
        h1_vx.setLineColor(color);        
        H1F h1_vy = new H1F("vy"+postflix, "vy", 100, -5, 5);
        h1_vy.setTitleX("vy (cm)");
        h1_vy.setTitleY("Counts");
        h1_vy.setLineColor(color);                
        H1F h1_vz = new H1F("vz"+postflix, "vz", 100, -20, 20);
        h1_vz.setTitleX("vz (cm)");
        h1_vz.setTitleY("Counts");
        h1_vz.setLineColor(color);           
        H1F h1_q = new H1F("q"+postflix, "q", 3, -1.5, 1.5);
        h1_q.setTitleX("q");
        h1_q.setTitleY("Counts");
        h1_q.setLineColor(color); 
        H1F h1_tandip = new H1F("tandip"+postflix, "tandip", 100, -5, 5);
        h1_tandip.setTitleX("tandip");
        h1_tandip.setTitleY("Counts");
        h1_tandip.setLineColor(color);          
        H1F h1_z0 = new H1F("tandip"+postflix, "tandip", 100, -20, 20);
        h1_z0.setTitleX("z0 (cm)");
        h1_z0.setTitleY("Counts");
        h1_z0.setLineColor(color);  
        H1F h1_pt = new H1F("pt"+postflix, "pt", 100, 0, 5);
        h1_pt.setTitleX("pt (GeV/c)");
        h1_pt.setTitleY("Counts");
        h1_pt.setLineColor(color);         
        H1F h1_phi0 = new H1F("phi0"+postflix, "phi0", 100, -Math.PI, Math.PI);
        h1_phi0.setTitleX("phi0 (rad)");
        h1_phi0.setTitleY("Counts");
        h1_phi0.setLineColor(color);          
        H1F h1_d0 = new H1F("d0"+postflix, "d0", 100, -1, 1);
        h1_d0.setTitleX("d0 (cm)");
        h1_d0.setTitleY("Counts");
        h1_d0.setLineColor(color);         
                
        addDataSet(h1_nKFIters, startOrder);
        addDataSet(h1_chi2overndf, startOrder+1);
        addDataSet(h1_ndf, startOrder+2);
        addDataSet(h1_p, startOrder+3);
        addDataSet(h1_theta, startOrder+4);
        addDataSet(h1_phi, startOrder+5);
        addDataSet(h1_vx, startOrder+6);
        addDataSet(h1_vy, startOrder+7);
        addDataSet(h1_vz, startOrder+8);         
        addDataSet(h1_q, startOrder+9); 
        addDataSet(h1_tandip, startOrder+10); 
        addDataSet(h1_z0, startOrder+11); 
        addDataSet(h1_pt, startOrder+12); 
        addDataSet(h1_phi0, startOrder+13);
        addDataSet(h1_d0, startOrder+14); 
        
        h1_nKFIters_map.put(postflix, h1_nKFIters);
        h1_chi2overndf_map.put(postflix, h1_chi2overndf);
        h1_ndf_map.put(postflix, h1_ndf);
        h1_p_map.put(postflix, h1_p);
        h1_theta_map.put(postflix, h1_theta);
        h1_phi_map.put(postflix, h1_phi);
        h1_vx_map.put(postflix, h1_vx);
        h1_vy_map.put(postflix, h1_vy);
        h1_vz_map.put(postflix, h1_vz);        
        h1_q_map.put(postflix, h1_q);
        h1_tandip_map.put(postflix, h1_tandip);
        h1_z0_map.put(postflix, h1_z0);
        h1_pt_map.put(postflix, h1_pt);
        h1_phi0_map.put(postflix, h1_phi0);
        h1_d0_map.put(postflix, h1_d0);        
    }
    
    public void addTrackDiffHistos(int color, int startOrder){
        h1_nKFIters_diff= new H1F("nKFIters_diff", "Diff. of nKFIters", 9, -4.5, 4.5);
        h1_nKFIters_diff.setTitleX("Diff. of nKFIters");
        h1_nKFIters_diff.setTitleY("Counts");
        h1_nKFIters_diff.setLineColor(color);          
        h1_chi2overndf_diff= new H1F("chi2overndfDiff", "Diff. of #Chi^2/ndf", 100, -10, 10);
        h1_chi2overndf_diff.setTitleX("Diff. of #Chi^2/ndf");
        h1_chi2overndf_diff.setTitleY("Counts");
        h1_chi2overndf_diff.setLineColor(color);   
        h1_ndf_diff= new H1F("ndfDiff", "Diff. of ndf", 19, -9.5, 9.5);
        h1_ndf_diff.setTitleX("Diff. of ndf");
        h1_ndf_diff.setTitleY("Counts");
        h1_ndf_diff.setLineColor(color);           
        h1_p_diff = new H1F("pDiff", "Diff. of p", 100, -1, 1);
        h1_p_diff.setTitleX("Diff. of p (GeV/c)");
        h1_p_diff.setTitleY("Counts");
        h1_p_diff.setLineColor(color);
        h1_theta_diff = new H1F("thetaDiff", "Diff. of #theta", 100, -0.1, 0.1);
        h1_theta_diff.setTitleX("Diff. of #theta (rad)");
        h1_theta_diff.setTitleY("Counts");
        h1_theta_diff.setLineColor(color);
        h1_phi_diff = new H1F("phiDiff", "Diff. of #phi", 100, -0.5, 0.5);
        h1_phi_diff.setTitleX("Diff. of #phi (rad)");
        h1_phi_diff.setTitleY("Counts");
        h1_phi_diff.setLineColor(color);
        h1_vx_diff = new H1F("vxDiff", "Diff. of vx", 100, -5, 5);
        h1_vx_diff.setTitleX("Diff. of vx (cm)");
        h1_vx_diff.setTitleY("Counts");
        h1_vx_diff.setLineColor(color);        
        h1_vy_diff = new H1F("vyDiff", "Diff. of vy", 100, -5, 5);
        h1_vy_diff.setTitleX("Diff. of vy (cm)");
        h1_vy_diff.setTitleY("Counts");
        h1_vy_diff.setLineColor(color);                
        h1_vz_diff = new H1F("vzDiff", "Diff. of vz", 100, -5, 5);
        h1_vz_diff.setTitleX("Diff. of vz (cm)");
        h1_vz_diff.setTitleY("Counts");
        h1_vz_diff.setLineColor(color);          
        h1_q_diff = new H1F("qDiff", "Diff. of q", 5, -2.5, 2.5);
        h1_q_diff.setTitleX("Diff. of q");
        h1_q_diff.setTitleY("Counts");
        h1_q_diff.setLineColor(color);           
        h1_tandip_diff = new H1F("tandipDiff", "Diff. of tandip", 100, -5, 5);
        h1_tandip_diff.setTitleX("Diff. of tandip");
        h1_tandip_diff.setTitleY("Counts");
        h1_tandip_diff.setLineColor(color); 
        h1_z0_diff = new H1F("z0Diff", "Diff. of z0", 100, -5, 5);
        h1_z0_diff.setTitleX("Diff. of z0 (cm)");
        h1_z0_diff.setTitleY("Counts");
        h1_z0_diff.setLineColor(color);          
        h1_pt_diff = new H1F("ptDiff", "Diff. of pt", 100, -1, 1);
        h1_pt_diff.setTitleX("Diff. of pt (GeV/c)");
        h1_pt_diff.setTitleY("Counts");
        h1_pt_diff.setLineColor(color); 
        h1_phi0_diff = new H1F("phi0Diff", "Diff. of phi0", 100, -Math.PI, Math.PI);
        h1_phi0_diff.setTitleX("Diff. of phi0 (rad)");
        h1_phi0_diff.setTitleY("Counts");
        h1_phi0_diff.setLineColor(color);        
        h1_d0_diff = new H1F("d0Diff", "Diff. of d0", 100, -1, 1);
        h1_d0_diff.setTitleX("Diff. of d0 (rad)");
        h1_d0_diff.setTitleY("Counts");
        h1_d0_diff.setLineColor(color);        
        
        addDataSet(h1_nKFIters_diff, startOrder);
        addDataSet(h1_chi2overndf_diff, startOrder+1);
        addDataSet(h1_ndf_diff, startOrder+2);
        addDataSet(h1_p_diff, startOrder+3);
        addDataSet(h1_theta_diff, startOrder+4);
        addDataSet(h1_phi_diff, startOrder+5);
        addDataSet(h1_vx_diff, startOrder+6);
        addDataSet(h1_vy_diff, startOrder+7);
        addDataSet(h1_vz_diff, startOrder+8);            
        addDataSet(h1_q_diff, startOrder+9);   
        addDataSet(h1_tandip_diff, startOrder+10);   
        addDataSet(h1_z0_diff, startOrder+11);   
        addDataSet(h1_pt_diff, startOrder+12);   
        addDataSet(h1_phi0_diff, startOrder+13);   
        addDataSet(h1_d0_diff, startOrder+14);           
    }
    
    public H1F getHistoNKFIters(){
        return getHistoNKFIters("");
    }
    
    public H1F getHistoNKFIters(String postflix){
        return h1_nKFIters_map.get(postflix);
    }    
    
    public H1F getHistoChi2overndf(){
        return getHistoChi2overndf("");
    }
    
    public H1F getHistoChi2overndf(String postflix){
        return h1_chi2overndf_map.get(postflix);
    }
    
    public H1F getHistoNDF(){
        return getHistoNDF("");
    }
    
    public H1F getHistoNDF(String postflix){
        return h1_ndf_map.get(postflix);
    }    
    
    public H1F getHistoP(){
        return getHistoP("");
    }
    
    public H1F getHistoP(String postflix){
        return h1_p_map.get(postflix);
    }
    
    public H1F getHistoTheta(){
        return getHistoTheta("");
    }
    
    public H1F getHistoTheta(String postflix){
        return h1_theta_map.get(postflix);
    }
    
    public H1F getHistoPhi(){
        return getHistoPhi("");
    }
    
    public H1F getHistoPhi(String postflix){
        return h1_phi_map.get(postflix);
    }
    
    public H1F getHistoVx(){
        return getHistoVx("");
    }

    public H1F getHistoVx(String postflix){
        return h1_vx_map.get(postflix);
    }

    public H1F getHistoVy(){
        return getHistoVy("");
    }
    
    public H1F getHistoVy(String postflix){
        return h1_vy_map.get(postflix);
    }
    
    public H1F getHistoVz(){
        return getHistoVz("");
    }

    public H1F getHistoVz(String postflix){
        return h1_vz_map.get(postflix);
    } 
    
    public H1F getHistoQ(){
        return getHistoQ("");
    }

    public H1F getHistoQ(String postflix){
        return h1_q_map.get(postflix);
    }
    
    public H1F getHistoTandip(){
        return getHistoTandip("");
    }

    public H1F getHistoTandip(String postflix){
        return h1_tandip_map.get(postflix);
    } 
    
    public H1F getHistoZ0(){
        return getHistoZ0("");
    }

    public H1F getHistoZ0(String postflix){
        return h1_z0_map.get(postflix);
    } 
    
    public H1F getHistoPt(){
        return getHistoPt("");
    }

    public H1F getHistoPt(String postflix){
        return h1_pt_map.get(postflix);
    }
    
    public H1F getHistoPhi0(){
        return getHistoPhi0("");
    }

    public H1F getHistoPhi0(String postflix){
        return h1_phi0_map.get(postflix);
    }
    
    
    public H1F getHistoD0(){
        return getHistoD0("");
    }

    public H1F getHistoD0(String postflix){
        return h1_d0_map.get(postflix);
    }    
    
    public H1F getHistoNKFItersDiff(){
        return h1_nKFIters_diff;
    }      
        
    public H1F getHistoChi2overndfDiff(){
        return h1_chi2overndf_diff;
    }

    public H1F getHistoNDFDiff(){
        return h1_ndf_diff;
    }    
    
    public H1F getHistoPDiff(){
        return h1_p_diff;
    }
    
    public H1F getHistoThetaDiff(){
        return h1_theta_diff;
    }
    
    public H1F getHistoPhiDiff(){
        return h1_phi_diff;
    }

    public H1F getHistoVxDiff(){
        return h1_vx_diff;
    }

    public H1F getHistoVyDiff(){
        return h1_vy_diff;
    }

    public H1F getHistoVzDiff(){
        return h1_vz_diff;
    }     
    
    public H1F getHistoQDiff(){
        return h1_q_diff;
    }
    
    public H1F getHistoTandipDiff(){
        return h1_tandip_diff;
    }  

    public H1F getHistoZ0Diff(){
        return h1_z0_diff;
    }  

    public H1F getHistoPtDiff(){
        return h1_pt_diff;
    }  

    public H1F getHistoPhi0Diff(){
        return h1_phi0_diff;
    }  

    public H1F getHistoD0Diff(){
        return h1_d0_diff;
    }                  
}