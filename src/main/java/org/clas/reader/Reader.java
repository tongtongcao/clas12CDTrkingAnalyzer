package org.clas.reader;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jlab.jnp.hipo4.data.Bank;
import org.jlab.jnp.hipo4.data.Event;

import org.clas.element.RunConfig;
import org.clas.element.MCParticle;
import org.clas.element.MCTrue;
import org.clas.element.MCTrueHit;
import org.clas.element.BSTADC;
import org.clas.element.BMTADC;
import org.clas.element.Hit;
import org.clas.element.Cluster;
import org.clas.element.Cross;
import org.clas.element.Seed;
import org.clas.element.SeedClusters;
import org.clas.element.Track;
import org.clas.utilities.Constants;

/**
 *
 * @author Tongtong
 */
public class Reader {    
    Banks banks = null;
    
    public Reader(Banks banks) {
        this.banks = banks;
    }
    
    private static final Logger LOGGER = Logger.getLogger(Reader.class.getName());    
    
    public RunConfig readRunConfig(Event event) {
        Bank runConfigBank = banks.getRunConfig();
        if(runConfigBank != null){
            event.read(runConfigBank);           
            RunConfig runconfig = new RunConfig(
                    runConfigBank.getInt("run", 0),
                    runConfigBank.getInt("event", 0),
                    runConfigBank.getInt("unixtime", 0),
                    runConfigBank.getLong("trigger", 0),
                    runConfigBank.getLong("timestamp", 0),
                    runConfigBank.getInt("type", 0),
                    runConfigBank.getInt("mode", 0),
                    runConfigBank.getFloat("torus", 0),
                    runConfigBank.getFloat("solenoid", 0)
            );                            
            return runconfig;
        }               
        else return null;   
    }
        
    public List<Track> readTracks(Event event) {
        return readTracks(event, Constants.TRACKINGPASS2, Constants.TRACKINGMODE2);
    }
    
    public List<Track> readTracks(Event event, int trackingPass, int trackingMode) {        
        List<Track> tracks = new ArrayList();
        
        Bank trackBank      = banks.getTrackBank(trackingPass, trackingMode);           
        
        if(trackBank!=null) {
            event.read(trackBank);
            // create tracks list from track bank
            for(int it = 0; it < trackBank.getRows(); it++){
                Track track = new Track(trackingPass, trackingMode,
                                        trackBank.getInt("ID", it),
                                        trackBank.getByte("nKFIters", it),
                                        trackBank.getByte("q", it),
                                        trackBank.getFloat("p", it),
                                        trackBank.getFloat("pt", it),
                                        trackBank.getFloat("phi0", it),
                                        trackBank.getFloat("tandip", it),
                                        trackBank.getFloat("z0", it),
                                        trackBank.getFloat("d0", it),
                                        trackBank.getFloat("chi2", it),
                                        trackBank.getInt("ndf", it),
                                        trackBank.getInt("pid", it),
                                        trackBank.getInt("seedID", it),
                                        trackBank.getFloat("xb", it),
                                        trackBank.getFloat("yb", it),
                                        trackBank.getInt("status", it));
                
                if(trackingMode == Constants.TRACKINGMODE2){
                    track.crossIds(trackBank.getInt("Cross1_ID", it),
                                    trackBank.getInt("Cross2_ID", it),
                                    trackBank.getInt("Cross3_ID", it),
                                    trackBank.getInt("Cross4_ID", it),
                                    trackBank.getInt("Cross5_ID", it),
                                    trackBank.getInt("Cross6_ID", it),
                                    trackBank.getInt("Cross7_ID", it),
                                    trackBank.getInt("Cross8_ID", it),
                                    trackBank.getInt("Cross9_ID", it));
                }

                tracks.add(track);
            }
        }
        return tracks;
    }
    
    public List<Seed> readSeeds(Event event) {
        return readSeeds(event, Constants.TRACKINGPASS2);
    }
    
    public List<Seed> readSeeds(Event event, int trackingPass) {        
        List<Seed> seeds = new ArrayList();
        
        Bank seedBank = banks.getSeedBank(trackingPass);           
        
        if(seedBank!=null) {
            event.read(seedBank);
            // create tracks list from track bank
            for(int it = 0; it < seedBank.getRows(); it++){
                Seed seed = new Seed(trackingPass,                                
                                        seedBank.getInt("ID", it),
                                seedBank.getInt("fittingMethod", it),
                                        seedBank.getByte("q", it),
                                        seedBank.getFloat("p", it),
                                        seedBank.getFloat("pt", it),
                                        seedBank.getFloat("phi0", it),
                                        seedBank.getFloat("tandip", it),
                                        seedBank.getFloat("z0", it),
                                        seedBank.getFloat("d0", it),
                                        seedBank.getFloat("circlefit_chi2_per_ndf", it),
                                        seedBank.getFloat("linefit_chi2_per_ndf", it),
                                        seedBank.getFloat("chi2", it),
                                        seedBank.getInt("ndf", it),
                                        seedBank.getFloat("xb", it),
                                        seedBank.getFloat("yb", it),
                                        seedBank.getFloat("fracmctru", it),
                                        seedBank.getFloat("fracmcmatch", it));

                seed.crossIds(seedBank.getInt("Cross1_ID", it),
                                seedBank.getInt("Cross2_ID", it),
                                seedBank.getInt("Cross3_ID", it),
                                seedBank.getInt("Cross4_ID", it),
                                seedBank.getInt("Cross5_ID", it),
                                seedBank.getInt("Cross6_ID", it),
                                seedBank.getInt("Cross7_ID", it),
                                seedBank.getInt("Cross8_ID", it),
                                seedBank.getInt("Cross9_ID", it));

                seeds.add(seed);
            }
        }
        return seeds;
    }
    

    public List<SeedClusters> readSeedClusterses(Event event) {
        return readSeedClusterses(event, Constants.TRACKINGPASS2);
    }
    
    public List<SeedClusters> readSeedClusterses(Event event, int trackingPass) {        
        List<SeedClusters> seedClusterses = new ArrayList();
        
        Bank seedClustersBank = banks.getSeedClustersBank(trackingPass);           
        
        if(seedClustersBank!=null) {
            event.read(seedClustersBank);
            // create tracks list from track bank
            for(int it = 0; it < seedClustersBank.getRows(); it++){
                SeedClusters seedClusters = new SeedClusters(trackingPass,
                                        seedClustersBank.getInt("id", it),                                        
                                        seedClustersBank.getFloat("prob", it));

                seedClusters.clusterIds(seedClustersBank.getInt("Clus1_ID", it),
                                        seedClustersBank.getInt("Clus2_ID", it),
                                        seedClustersBank.getInt("Clus3_ID", it),
                                        seedClustersBank.getInt("Clus4_ID", it),
                                        seedClustersBank.getInt("Clus5_ID", it),
                                        seedClustersBank.getInt("Clus6_ID", it),
                                        seedClustersBank.getInt("Clus7_ID", it),
                                        seedClustersBank.getInt("Clus8_ID", it),
                                        seedClustersBank.getInt("Clus9_ID", it),
                                        seedClustersBank.getInt("Clus10_ID", it),
                                        seedClustersBank.getInt("Clus11_ID", it),
                                        seedClustersBank.getInt("Clus12_ID", it));

                seedClusterses.add(seedClusters);
            }
        }
        return seedClusterses;
    }
    

    public List<Cross> readCrosses(Event event) {
        return readCrosses(event, Constants.DETECTORBST, Constants.TRACKINGPASS2);
    }
    
    public List<Cross> readCrosses(Event event, int detectorType, int trackingPass) {
        Bank crossBank = banks.getCrossBank(detectorType, trackingPass);
        
        ArrayList<Cross> crosses = new ArrayList();
        
        if(crossBank != null){
            event.read(crossBank);
            
            for(int loop = 0; loop < crossBank.getRows(); loop++){
                if(detectorType == Constants.DETECTORBST){
                    Cross crs = new Cross(detectorType, trackingPass,
                            crossBank.getInt("ID", loop),
                            crossBank.getByte("sector", loop),
                            crossBank.getByte("region", loop),                        
                            crossBank.getFloat("x", loop),
                            crossBank.getFloat("y", loop),
                            crossBank.getFloat("z", loop),
                            crossBank.getFloat("err_x", loop),
                            crossBank.getFloat("err_y", loop),
                            crossBank.getFloat("err_z", loop),
                            crossBank.getFloat("x0", loop),
                            crossBank.getFloat("y0", loop),
                            crossBank.getFloat("z0", loop),
                            crossBank.getFloat("err_x0", loop),
                            crossBank.getFloat("err_y0", loop),
                            crossBank.getFloat("err_z0", loop),                        
                            crossBank.getFloat("ux", loop),
                            crossBank.getFloat("uy", loop),
                            crossBank.getFloat("uz", loop),
                            crossBank.getInt("Cluster1_ID", loop), 
                            crossBank.getInt("Cluster2_ID", loop), 
                            crossBank.getInt("trkID", loop)
                            );    
                    crosses.add(crs);
                }
                else if(detectorType == Constants.DETECTORBMT){
                    Cross crs = new Cross(detectorType, trackingPass,
                            crossBank.getInt("ID", loop),
                            crossBank.getByte("sector", loop),
                            crossBank.getByte("region", loop), 
                            crossBank.getByte("layer", loop),
                            crossBank.getFloat("x", loop),
                            crossBank.getFloat("y", loop),
                            crossBank.getFloat("z", loop),
                            crossBank.getFloat("err_x", loop),
                            crossBank.getFloat("err_y", loop),
                            crossBank.getFloat("err_z", loop),
                            crossBank.getFloat("x0", loop),
                            crossBank.getFloat("y0", loop),
                            crossBank.getFloat("z0", loop),
                            crossBank.getFloat("err_x0", loop),
                            crossBank.getFloat("err_y0", loop),
                            crossBank.getFloat("err_z0", loop),                        
                            crossBank.getFloat("ux", loop),
                            crossBank.getFloat("uy", loop),
                            crossBank.getFloat("uz", loop),
                            crossBank.getInt("Cluster1_ID", loop), 
                            crossBank.getInt("Cluster2_ID", loop), 
                            crossBank.getInt("trkID", loop)
                            );    
                    crosses.add(crs);
                }
            }
        }        
        
        return crosses;
    }
      
    public List<Cluster> readClusters(Event event) {
        return readClusters(event, Constants.DETECTORBST, Constants.TRACKINGPASS2);
    }
    
    public List<Cluster> readClusters(Event event, int detectorType, int trackingPass) {
        Bank clusterBank = banks.getClusterBank(detectorType, trackingPass);
                
        ArrayList<Cluster> clusters = new ArrayList();
        
        if(clusterBank != null){
            event.read(clusterBank);            
            for(int loop = 0; loop < clusterBank.getRows(); loop++){
                Cluster cls = new Cluster(detectorType, trackingPass, 
                        clusterBank.getInt("ID", loop),
                        clusterBank.getByte("sector", loop),
                        clusterBank.getByte("layer", loop),
                        clusterBank.getInt("size", loop),
                        clusterBank.getFloat("ETot", loop),
                        clusterBank.getFloat("time", loop),
                        clusterBank.getFloat("seedE", loop),
                        clusterBank.getInt("seedStrip", loop),
                        clusterBank.getFloat("centroid", loop),
                        clusterBank.getFloat("centroidError", loop),
                        clusterBank.getFloat("centroidResidual", loop),
                        clusterBank.getFloat("seedResidual", loop),
                        clusterBank.getInt("trkID", loop),
                        clusterBank.getFloat("x1", loop),
                        clusterBank.getFloat("y1", loop),
                        clusterBank.getFloat("z1", loop),
                        clusterBank.getFloat("x2", loop),
                        clusterBank.getFloat("y2", loop),
                        clusterBank.getFloat("z2", loop),
                        clusterBank.getFloat("cx", loop),
                        clusterBank.getFloat("cy", loop),
                        clusterBank.getFloat("cz", loop),
                        clusterBank.getFloat("lx", loop),
                        clusterBank.getFloat("ly", loop),
                        clusterBank.getFloat("lz", loop),
                        clusterBank.getFloat("sx", loop),
                        clusterBank.getFloat("sy", loop),
                        clusterBank.getFloat("sz", loop),
                        clusterBank.getFloat("nx", loop),
                        clusterBank.getFloat("ny", loop),
                        clusterBank.getFloat("nz", loop)
                        );
                
                cls.hitIds(clusterBank.getInt("Hit1_ID", loop),
                            clusterBank.getInt("Hit2_ID", loop),
                            clusterBank.getInt("Hit3_ID", loop),
                            clusterBank.getInt("Hit4_ID", loop),
                            clusterBank.getInt("Hit5_ID", loop));
                
                /*
                try{
                    cls.dafWeight(clusterBank.getFloat("DAFWeight", loop));
                }              
                catch(Exception e){
                   LOGGER.log(Level.FINER, "no item DAFWeight in cluster bank!");
                }
                */
                
                clusters.add(cls); 
            }
        }        
        
        return clusters;
    }
        
    public List<Hit> readHits(Event event) {
        return readHits(event, Constants.DETECTORBST, Constants.TRACKINGPASS2);
    }
    
    public List<Hit> readHits(Event event, int detectorType, int trackingPass) {
        Bank hitBank = banks.getHitBank(detectorType, trackingPass);
        
        List<Hit> hits = new ArrayList();
        
        if(hitBank != null){
            event.read(hitBank);
            
            for(int loop = 0; loop < hitBank.getRows(); loop++){
                Hit hit = new Hit(detectorType, trackingPass, 
                        hitBank.getInt("ID", loop),
                        hitBank.getByte("sector", loop),
                        hitBank.getByte("layer", loop),
                        hitBank.getInt("strip", loop),
                        hitBank.getFloat("energy", loop),
                        hitBank.getFloat("time", loop),
                        hitBank.getFloat("fitResidual", loop),
                        hitBank.getByte("trkingStat", loop),
                        hitBank.getInt("clusterID", loop),
                        hitBank.getInt("trkID", loop),
                        hitBank.getByte("status", loop)
                        );
                
                hits.add(hit);
            }
        }
                
        return hits;
    }  
    
    public List<BSTADC> readBSTADCs(Event event) {
        Bank bstADCBank = banks.getBSTADCBank();
        List<BSTADC> adcs = new ArrayList();
        if(bstADCBank != null){
            event.read(bstADCBank);
            
            for(int loop = 0; loop < bstADCBank.getRows(); loop++){
                BSTADC adc = new BSTADC(
                        bstADCBank.getByte("sector", loop),
                        bstADCBank.getByte("layer", loop),
                        bstADCBank.getInt("component", loop),
                        bstADCBank.getByte("order", loop),
                        bstADCBank.getInt("ADC", loop),
                        bstADCBank.getFloat("time", loop),
                        bstADCBank.getInt("ped", loop),
                        bstADCBank.getLong("timestamp", loop)
                );
                
                adcs.add(adc);
            }
        }         
                
        return adcs;
    } 
    
    public List<BMTADC> readBMTADCs(Event event) {
        Bank bmtADCBank = banks.getBMTADCBank();
        List<BMTADC> adcs = new ArrayList();
        if(bmtADCBank != null){
            event.read(bmtADCBank);
            
            for(int loop = 0; loop < bmtADCBank.getRows(); loop++){
                BMTADC adc = new BMTADC(
                        bmtADCBank.getByte("sector", loop),
                        bmtADCBank.getByte("layer", loop),
                        bmtADCBank.getInt("component", loop),
                        bmtADCBank.getByte("order", loop),
                        bmtADCBank.getInt("ADC", loop),
                        bmtADCBank.getFloat("time", loop),
                        bmtADCBank.getInt("ped", loop),
                        bmtADCBank.getInt("integral", loop),
                        bmtADCBank.getLong("timestamp", loop)
                );
                
                adcs.add(adc);
            }
        }         
                
        return adcs;
    } 
    
    
    
    public ArrayList<MCParticle> readMCParticles(Event event){
        Bank mcParticleBank = banks.getMCParticle();
        
        ArrayList<MCParticle> mcParticles = new ArrayList();
        
        if(mcParticleBank != null){
            event.read(mcParticleBank);
            
            for(int loop = 0; loop < mcParticleBank.getRows(); loop++){

                MCParticle particle = new MCParticle( 
                        mcParticleBank.getInt("pid", loop),
                        mcParticleBank.getFloat("px", loop),
                        mcParticleBank.getFloat("py", loop),
                        mcParticleBank.getFloat("pz", loop),
                        mcParticleBank.getFloat("vx", loop),
                        mcParticleBank.getFloat("vy", loop),
                        mcParticleBank.getFloat("vz", loop),
                        mcParticleBank.getFloat("vt", loop)
                        );
                              
                mcParticles.add(particle);
            }
        }
        
        return mcParticles;
    }
    
    public MCTrue readMCTrue(Event event){
        Bank mcTrueBank = banks.getMCTrue();
        
        List<MCTrueHit> hitsBST = new ArrayList();
        List<MCTrueHit> hitsBMT = new ArrayList();
        
        if(mcTrueBank != null){
            event.read(mcTrueBank);
            
            for(int loop = 0; loop < mcTrueBank.getRows(); loop++){
                int detector = mcTrueBank.getInt("detector", loop);
                MCTrueHit hit = new MCTrueHit(                            
                            mcTrueBank.getInt("pid", loop),
                            detector,
                            mcTrueBank.getInt("hitn", loop),
                            mcTrueBank.getFloat("avgX", loop),
                            mcTrueBank.getFloat("avgY", loop),
                            mcTrueBank.getFloat("avgZ", loop)
                            );
                if(detector == 2) hitsBST.add(hit);
                else if(detector == 1) hitsBMT.add(hit);
            }
        }
        
        return new MCTrue(hitsBST, hitsBMT);
    }
}
