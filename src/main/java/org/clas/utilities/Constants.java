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
    
    // Valid cuts 
    public static double CHI2PID = 3;
    public static double ZMIN = -7;
    public static double ZMAX = 0;
    public static double PMIN = 0.2;
           
    public static double BEAMENERGY = 10.6;
    public static int    TARGETPID = 2212;
    
    // Detector type
    public static final int DETECTORBMT = 1;
    public static final int DETECTORBST = 2;
    
    // BMT type
    public static final int BMTC = 1;
    public static final int BMTZ = 2;
    public static final int BMTNO = 3;
                
    // Tracking pass
    public static final int TRACKINGPASS1 = 1;
    public static final int TRACKINGPASS2 = 2;
    
    // Tracking mode
    public static final int TRACKINGMODE1 = 1; // Transported
    public static final int TRACKINGMODE2 = 2; // Smoothed   
        
}
