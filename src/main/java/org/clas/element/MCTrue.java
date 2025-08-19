package org.clas.element;

import java.util.List;

/**
 *
 * @author Tongtong
 */

public class MCTrue {    
    private List<MCTrueHit> hitsBST;
    private List<MCTrueHit> hitsBMT;
    
        
    public MCTrue(List<MCTrueHit> hitsBST, List<MCTrueHit> hitsBMT){
        this.hitsBST = hitsBST;
        this.hitsBMT = hitsBMT;
    }
    
    public List<MCTrueHit> getHitsBST(){
        return hitsBST;
    }    
    
    public List<MCTrueHit> getHitsBMT(){
        return hitsBMT;
    }       
}