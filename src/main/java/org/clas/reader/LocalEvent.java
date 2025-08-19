package org.clas.reader;

import java.util.*;
import org.jlab.jnp.hipo4.data.Event;

import org.clas.element.RunConfig;
import org.clas.element.MCParticle;
import org.clas.element.MCTrue;
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
public class LocalEvent {
    private Reader reader;
    private Event event;
    private int trkType = 0;
    private boolean readURWell = false;
    
    
    private RunConfig runConfig = null;
    private List<MCParticle> mcParticles = new ArrayList();
    private MCTrue mcTrue = null;
        
    private List<BSTADC> bstADCs = new ArrayList();
    private List<BMTADC> bmtADCs = new ArrayList();
        
    private List<Hit> bstHits = new ArrayList();
    private List<Hit> bmtHits = new ArrayList(); 
    private List<Hit> bstRecHits = new ArrayList();
    private List<Hit> bmtRecHits = new ArrayList(); 
        
    private List<Cluster> bstClusters = new ArrayList();
    private List<Cluster> bmtClusters = new ArrayList(); 
    private List<Cluster> bstRecClusters = new ArrayList();
    private List<Cluster> bmtRecClusters = new ArrayList();   
    
    private List<Cross> bstCrosses = new ArrayList();
    private List<Cross> bmtCrosses = new ArrayList(); 
    private List<Cross> bstRecCrosses = new ArrayList();
    private List<Cross> bmtRecCrosses = new ArrayList();     
    
    private List<Seed> seeds = new ArrayList();
    private List<Seed> recSeeds = new ArrayList();
    
    private List<SeedClusters> seedClusterses = new ArrayList();
    private List<SeedClusters> recSeedClusterses = new ArrayList();
    
    private List<Track> tracks = new ArrayList();
    private List<Track> recTracks = new ArrayList();
    private List<Track> uTracks = new ArrayList();
    private List<Track> recUTracks = new ArrayList();
    
    public LocalEvent(Reader reader, Event event){
        this.reader = reader;
        this.event = event;
        readBanks();
    }
    
    private void readBanks(){
        //// Read banks        
        // Config
        runConfig = reader.readRunConfig(event);
        
        mcParticles = reader.readMCParticles(event);  
        mcTrue = reader.readMCTrue(event);
                        
        // ADC
        bstADCs = reader.readBSTADCs(event);
        bmtADCs = reader.readBMTADCs(event);
              
        // Hits
        bstHits = reader.readHits(event, Constants.DETECTORBST, Constants.TRACKINGPASS1);
        bmtHits = reader.readHits(event, Constants.DETECTORBMT, Constants.TRACKINGPASS1);
        bstRecHits = reader.readHits(event, Constants.DETECTORBST, Constants.TRACKINGPASS2);
        bmtRecHits = reader.readHits(event, Constants.DETECTORBMT, Constants.TRACKINGPASS2);
                               
        Map<List<Integer>, Integer> mapBSTADCs = new HashMap<>();
        for(BSTADC adc : bstADCs){
            if(adc.component() >= 0){
                List<Integer> key = Arrays.asList(adc.sector(), adc.layer(), adc.component());
                mapBSTADCs.put(key, adc.order());                    
            }
        }
        for(Hit hit : bstHits){
            List<Integer> key = Arrays.asList(hit.sector(), hit.layer(), hit.strip());
            hit.setOrder(mapBSTADCs.get(key));
        }        
        for(Hit hit : bstRecHits){
            List<Integer> key = Arrays.asList(hit.sector(), hit.layer(), hit.strip());
            hit.setOrder(mapBSTADCs.get(key));
        } 
        
        Map<List<Integer>, Integer> mapBMTADCs = new HashMap<>();
        for(BMTADC adc : bmtADCs){
            if(adc.component() >= 0){
                List<Integer> key = Arrays.asList(adc.sector(), adc.layer(), adc.component());
                mapBMTADCs.put(key, adc.order());                    
            }
        }
        for(Hit hit : bmtHits){
            List<Integer> key = Arrays.asList(hit.sector(), hit.layer(), hit.strip());
            hit.setOrder(mapBMTADCs.get(key));
        }        
        for(Hit hit : bmtRecHits){
            List<Integer> key = Arrays.asList(hit.sector(), hit.layer(), hit.strip());
            hit.setOrder(mapBMTADCs.get(key));
        }                 
        
        // Clusters
        bstClusters = reader.readClusters(event, Constants.DETECTORBST, Constants.TRACKINGPASS1);
        bmtClusters = reader.readClusters(event, Constants.DETECTORBMT, Constants.TRACKINGPASS1);
        bstRecClusters = reader.readClusters(event, Constants.DETECTORBST, Constants.TRACKINGPASS2);
        bmtRecClusters = reader.readClusters(event, Constants.DETECTORBMT, Constants.TRACKINGPASS2);
        
        for(Cluster cls : bstClusters) cls.setHits(bstHits);
        for(Cluster cls : bmtClusters) cls.setHits(bmtHits);
        for(Cluster cls : bstRecClusters) cls.setHits(bstRecHits);
        for(Cluster cls : bmtRecClusters) cls.setHits(bmtRecHits);
        
        // Crosses
        bstCrosses = reader.readCrosses(event, Constants.DETECTORBST, Constants.TRACKINGPASS1);
        bmtCrosses = reader.readCrosses(event, Constants.DETECTORBMT, Constants.TRACKINGPASS1);
        bstRecCrosses = reader.readCrosses(event, Constants.DETECTORBST, Constants.TRACKINGPASS2);
        bmtRecCrosses = reader.readCrosses(event, Constants.DETECTORBMT, Constants.TRACKINGPASS2);   
        
        for(Cross crs : bstCrosses) crs.setHitsClusters(bstClusters);
        for(Cross crs : bmtCrosses) crs.setHitsClusters(bmtClusters);
        for(Cross crs : bstRecCrosses) crs.setHitsClusters(bstRecClusters);
        for(Cross crs : bmtRecCrosses) crs.setHitsClusters(bmtRecClusters);        
        
        // Seed
        seeds = reader.readSeeds(event, Constants.TRACKINGPASS1);
        recSeeds = reader.readSeeds(event, Constants.TRACKINGPASS2);
        
        for(Seed seed : seeds) seed.setHitsClustersCrosses(bstCrosses, bmtCrosses);
        for(Seed seed : recSeeds) seed.setHitsClustersCrosses(bstRecCrosses, bmtRecCrosses);
        
        // SeedClusters
        seedClusterses = reader.readSeedClusterses(event, Constants.TRACKINGPASS1);
        recSeedClusterses = reader.readSeedClusterses(event, Constants.TRACKINGPASS2);
        
        // Tracks
        tracks = reader.readTracks(event, Constants.TRACKINGPASS1, Constants.TRACKINGMODE2);
        uTracks = reader.readTracks(event, Constants.TRACKINGPASS1, Constants.TRACKINGMODE1);
        recTracks = reader.readTracks(event, Constants.TRACKINGPASS2, Constants.TRACKINGMODE2);
        recUTracks = reader.readTracks(event, Constants.TRACKINGPASS2, Constants.TRACKINGMODE1);
        
        for(Track trk : tracks) {            
            trk.setHitsClustersCrosses(bstCrosses, bmtCrosses);
            trk.setSeed(seeds);
        }
        for(Track trk : uTracks) {
            trk.setSeed(seeds);
            for(Track tt : tracks){
                if(trk.id() == tt.id()){
                    trk.setCrossIds(tt.crossIds());
                    break;
                }
            }
            trk.setHitsClustersCrosses(bstCrosses, bmtCrosses);
        }
        for(Track trk : recTracks) {
            trk.setHitsClustersCrosses(bstRecCrosses, bmtRecCrosses);
            trk.setSeed(recSeeds);
        }
        for(Track trk : recUTracks) {
            trk.setSeed(recSeeds);
            for(Track tt : recTracks){
                if(trk.id() == tt.id()){
                    trk.setCrossIds(tt.crossIds());
                    break;
                }
            }
            trk.setHitsClustersCrosses(bstRecCrosses, bmtRecCrosses);
        }                
    }
    
    public RunConfig getRunConfig(){
        if(runConfig != null) return runConfig;
        else{
            System.err.println("Error: run config is empty!");
            return null;
        }
    }
    
    public List<MCParticle> getMCParticles(){
        return mcParticles;
    }

    public MCTrue getMCTrue(){
        return mcTrue;
    }         
        
    public List<BSTADC> getBSTADCs(){
        return bstADCs;
    }
    
    public List<BMTADC> getBMTADCs(){
        return bmtADCs;
    }    
    
    public List<Hit> getBSTHits(){
        return bstHits;
    }
    
    public List<Hit> getBMTHits(){
        return bmtHits;
    }    
    
    public List<Hit> getBSTRecHits(){
        return bstRecHits;
    }  
    
    public List<Hit> getBMTRecHits(){
        return bmtRecHits;
    }
    
    public List<Hit> getBSTHits(int pass){
        if(pass == Constants.TRACKINGPASS1) return bstHits;
        else if(pass == Constants.TRACKINGPASS2) return bstRecHits;
        else return null;
    }
    
    public List<Hit> getBMTHits(int pass){
        if(pass == Constants.TRACKINGPASS1) return bmtHits;
        else if(pass == Constants.TRACKINGPASS2) return bmtRecHits;
        else return null;
    }    
    
    public List<Cluster> getBSTClusters(){
        return bstClusters;
    }
    
    public List<Cluster> getBMTClusters(){
        return bmtClusters;
    }    
    
    public List<Cluster> getBSTRecClusters(){
        return bstRecClusters;
    }  
    
    public List<Cluster> getBMTRecClusters(){
        return bmtRecClusters;
    }
    
    public List<Cluster> getBSTClusters(int pass){
        if(pass == Constants.TRACKINGPASS1) return bstClusters;
        else if(pass == Constants.TRACKINGPASS2) return bstRecClusters;
        else return null;
    }
    
    public List<Cluster> getBMTClusters(int pass){
        if(pass == Constants.TRACKINGPASS1) return bmtClusters;
        else if(pass == Constants.TRACKINGPASS2) return bmtRecClusters;
        else return null;
    }     
    
    public List<Cross> getBSTCrosses(){
        return bstCrosses;
    }
    
    public List<Cross> getBMTCrosses(){
        return bmtCrosses;
    }    
    
    public List<Cross> getBSTRecCrosses(){
        return bstRecCrosses;
    }  
    
    public List<Cross> getBMTRecCrosses(){
        return bmtRecCrosses;
    }
    
    public List<Cross> getBSTCrosses(int pass){
        if(pass == Constants.TRACKINGPASS1) return bstCrosses;
        else if(pass == Constants.TRACKINGPASS2) return bstRecCrosses;
        else return null;
    }
    
    public List<Cross> getBMTCrosses(int pass){
        if(pass == Constants.TRACKINGPASS1) return bmtCrosses;
        else if(pass == Constants.TRACKINGPASS2) return bmtRecCrosses;
        else return null;
    }     
        
    public List<Seed> getSeeds(){
        return seeds;
    }
    
    public List<Seed> getRecSeeds(){
        return recSeeds;
    }
    
    public List<Seed> getSeeds(int pass){
        if(pass == Constants.TRACKINGPASS1) return seeds;
        else if(pass == Constants.TRACKINGPASS2) return recSeeds;
        else return null;
    }    
    
    public List<SeedClusters> getSeedClusterses(){
        return seedClusterses;
    }
    
    public List<SeedClusters> getRecSeedClusterses(){
        return recSeedClusterses;
    }
    
    public List<SeedClusters> getSeedClusterses(int pass){
        if(pass == Constants.TRACKINGPASS1) return seedClusterses;
        else if(pass == Constants.TRACKINGPASS2) return recSeedClusterses;
        else return null;
    }     

    public List<Track> getTracks(){
        return tracks;
    }

    public List<Track> getUTracks(){
        return uTracks;
    }  
    
    public List<Track> getRecTracks(){
        return recTracks;
    }

    public List<Track> getRecUTracks(){
        return recUTracks;
    }
    
    public List<Track> getTracks(int pass){
        if(pass == Constants.TRACKINGPASS1) return tracks;
        else if(pass == Constants.TRACKINGPASS2) return recTracks;
        else return null;
    } 
    
    public List<Track> getUTracks(int pass){
        if(pass == Constants.TRACKINGPASS1) return uTracks;
        else if(pass == Constants.TRACKINGPASS2) return recUTracks;
        else return null;
    }    
} 