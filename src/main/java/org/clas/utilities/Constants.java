package org.clas.utilities;

import org.jlab.detector.base.DetectorType;

/**
 *
 * @author Tongtong
 */
public class Constants {       
        
    public static boolean MC = true;
    public static boolean BG = false;
    public static int PASS = 2;
    
    public static double CHI2MAX = Double.POSITIVE_INFINITY;
    public static double ZMIN = -15;
    public static double ZMAX = 5;
    public static double PMIN = 0.5;
    
    public static double BEAMENERGY = 10.6;
    public static int    TARGETPID = 2212;
    
    // Detector type
    public static final int DETECTORBMT = 1;
    public static final int DETECTORBST = 2;
                
    // Tracking pass
    public static final int TRACKINGPASS1 = 1;
    public static final int TRACKINGPASS2 = 2;
    
    // Tracking mode
    public static final int TRACKINGMODE1 = 1; // Transported
    public static final int TRACKINGMODE2 = 2; // Smoothed
    
    // Valid cuts
    public static final double CHI2OVERNDFMAX = 4.;
        
}
