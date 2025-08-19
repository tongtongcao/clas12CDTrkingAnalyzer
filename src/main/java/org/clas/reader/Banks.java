package org.clas.reader;

import org.jlab.jnp.hipo4.data.Bank;
import org.jlab.jnp.hipo4.data.SchemaFactory;

import org.clas.utilities.Constants;

/**
 *
 * @author Tongtong
 */
public class Banks {    
    private Bank runConfig;
    
    // MC Particle and true
    private Bank mcParticleBank;
    private Bank mcTrueBank;
    
    // ADC banks
    private Bank bstADCBank;    
    private Bank bmtADCBank;
    
    // Hit banks
    private Bank bstHitBank;    
    private Bank bmtHitBank;
    private Bank bstRecHitBank;        
    private Bank bmtRecHitBank;
    
    // Cluster banks
    private Bank bstClusterBank;    
    private Bank bmtClusterBank;
    private Bank bstRecClusterBank;     
    private Bank bmtRecClusterBank;
    
    // Cross banks
    private Bank bstCrossBank;    
    private Bank bmtCrossBank;
    private Bank bstRecCrossBank;    
    private Bank bmtRecCrossBank;
    
    // Seed banks
    private Bank seedBank;
    private Bank recSeedBank;
    private Bank seedClustersBank;
    private Bank recSeedClustersBank;

    // Track banks
    private Bank trackBank;
    private Bank uTrackBank;
    private Bank recTrackBank;
    private Bank recUTrackBank;        
    
    

    public Banks(SchemaFactory schema) {
        if(schema.hasSchema("RUN::config"))
            this.runConfig        = new Bank(schema.getSchema("RUN::config"));

        // MC banks
        if(schema.hasSchema("MC::Particle"))
            this.mcParticleBank        = new Bank(schema.getSchema("MC::Particle"));        
        if(schema.hasSchema("MC::True"))
            this.mcTrueBank        = new Bank(schema.getSchema("MC::True"));
                
        // ADC banks
        if(schema.hasSchema("BST::adc"))
            this.bstADCBank        = new Bank(schema.getSchema("BST::adc"));        
        if(schema.hasSchema("BMT::adc"))
            this.bmtADCBank        = new Bank(schema.getSchema("BMT::adc"));
        
        // Hit banks
        if(schema.hasSchema("BST::Hits"))
            this.bstHitBank    = new Bank(schema.getSchema("BST::Hits"));            
        if(schema.hasSchema("BMT::Hits"))
            this.bmtHitBank    = new Bank(schema.getSchema("BMT::Hits"));
        if(schema.hasSchema("BSTRec::Hits"))
            this.bstRecHitBank    = new Bank(schema.getSchema("BSTRec::Hits"));              
        if(schema.hasSchema("BMTRec::Hits"))
            this.bmtRecHitBank    = new Bank(schema.getSchema("BMTRec::Hits"));   
        
        
        // Cluster banks
        if(schema.hasSchema("BST::Clusters"))
            this.bstClusterBank    = new Bank(schema.getSchema("BST::Clusters"));            
        if(schema.hasSchema("BMT::Clusters"))
            this.bmtClusterBank    = new Bank(schema.getSchema("BMT::Clusters"));
        if(schema.hasSchema("BSTRec::Clusters"))
            this.bstRecClusterBank    = new Bank(schema.getSchema("BSTRec::Clusters"));              
        if(schema.hasSchema("BMTRec::Clusters"))
            this.bmtRecClusterBank    = new Bank(schema.getSchema("BMTRec::Clusters")); 
        
        // Cross banks
        if(schema.hasSchema("BST::Crosses"))
            this.bstCrossBank    = new Bank(schema.getSchema("BST::Crosses"));            
        if(schema.hasSchema("BMT::Crosses"))
            this.bmtCrossBank    = new Bank(schema.getSchema("BMT::Crosses"));
        if(schema.hasSchema("BSTRec::Crosses"))
            this.bstRecCrossBank    = new Bank(schema.getSchema("BSTRec::Crosses"));              
        if(schema.hasSchema("BMTRec::Crosses"))
            this.bmtRecCrossBank    = new Bank(schema.getSchema("BMTRec::Crosses"));
        
        // Seed banks
        if(schema.hasSchema("CVT::Seeds"))
            this.seedBank    = new Bank(schema.getSchema("CVT::Seeds")); 
        if(schema.hasSchema("CVTRec::Seeds"))
            this.recSeedBank    = new Bank(schema.getSchema("CVTRec::Seeds"));  
        if(schema.hasSchema("CVT::SeedClusters"))
            this.seedClustersBank    = new Bank(schema.getSchema("CVT::SeedClusters"));         
        if(schema.hasSchema("CVTRec::SeedClusters"))
            this.recSeedClustersBank    = new Bank(schema.getSchema("CVTRec::SeedClusters")); 
        
        // Track banks
        if(schema.hasSchema("CVT::Tracks"))
            this.trackBank    = new Bank(schema.getSchema("CVT::Tracks"));   
        if(schema.hasSchema("CVT::UTracks"))
            this.uTrackBank    = new Bank(schema.getSchema("CVT::UTracks"));    
        if(schema.hasSchema("CVTRec::Tracks"))
            this.recTrackBank    = new Bank(schema.getSchema("CVTRec::Tracks"));   
        if(schema.hasSchema("CVTRec::UTracks"))
            this.recUTrackBank    = new Bank(schema.getSchema("CVTRec::UTracks"));                           
    }

    public Bank getRunConfig() {
	return runConfig;
    }
    
    public Bank getMCParticle() {
	return mcParticleBank;
    }
    
    public Bank getMCTrue() {
	return mcTrueBank;
    }
    
    public Bank getBSTADCBank() {
	return bstADCBank;
    }
    
    public Bank getBMTADCBank() {
	return bmtADCBank;
    }    
    
    public Bank getHitBank(int detectType, int trackingPass) {
        if(detectType == Constants.DETECTORBST){
            if(trackingPass == Constants.TRACKINGPASS1) return bstHitBank;
            else if(trackingPass == Constants.TRACKINGPASS2) return bstRecHitBank;
            else return null;
        }
        else if(detectType == Constants.DETECTORBMT){
            if(trackingPass == Constants.TRACKINGPASS1) return bmtHitBank;
            else if(trackingPass == Constants.TRACKINGPASS2) return bmtRecHitBank;
            else return null;
        }
        else return null;	
    }
    
    public Bank getClusterBank(int detectType, int trackingPass) {
        if(detectType == Constants.DETECTORBST){
            if(trackingPass == Constants.TRACKINGPASS1) return bstClusterBank;
            else if(trackingPass == Constants.TRACKINGPASS2) return bstRecClusterBank;
            else return null;
        }
        else if(detectType == Constants.DETECTORBMT){
            if(trackingPass == Constants.TRACKINGPASS1) return bmtClusterBank;
            else if(trackingPass == Constants.TRACKINGPASS2) return bmtRecClusterBank;
            else return null;
        }
        else return null;	
    } 
    
    public Bank getCrossBank(int detectType, int trackingPass) {
        if(detectType == Constants.DETECTORBST){
            if(trackingPass == Constants.TRACKINGPASS1) return bstCrossBank;
            else if(trackingPass == Constants.TRACKINGPASS2) return bstRecCrossBank;
            else return null;
        }
        else if(detectType == Constants.DETECTORBMT){
            if(trackingPass == Constants.TRACKINGPASS1) return bmtCrossBank;
            else if(trackingPass == Constants.TRACKINGPASS2) return bmtRecCrossBank;
            else return null;
        }
        else return null;	
    }

    public Bank getSeedBank(int trackingPass) {
        if(trackingPass == Constants.TRACKINGPASS1) return seedBank;
        else if(trackingPass == Constants.TRACKINGPASS2) return recSeedBank;
        else return null;	
    }     
    
    public Bank getSeedClustersBank(int trackingPass) {
        if(trackingPass == Constants.TRACKINGPASS1) return seedClustersBank;
        else if(trackingPass == Constants.TRACKINGPASS2) return recSeedClustersBank;
        else return null;	
    } 
    
    public Bank getTrackBank(int trackingPass, int trackingMode) {
        if(trackingPass == Constants.TRACKINGPASS1){
            if(trackingMode == Constants.TRACKINGMODE1) return uTrackBank;
            else if(trackingMode == Constants.TRACKINGMODE2) return trackBank;
            else return null;
        }
        else if(trackingPass == Constants.TRACKINGPASS2){
            if(trackingMode == Constants.TRACKINGMODE1) return recUTrackBank;
            else if(trackingMode == Constants.TRACKINGMODE2) return recTrackBank;
            else return null;
        }
        else return null;	
    }

}