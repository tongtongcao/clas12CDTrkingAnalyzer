package org.clas.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tongtong
 */
public class CommonFunctions {  
     // Set SVT sections based on layer and sector of hits
    public static List<Integer> getSectionList(int layer, int sector) {
        List<Integer> sectionList = new ArrayList<>();

        if (layer >= 1 && layer <= 2) {
            if (sector >= 1 && sector <= 4) sectionList.add(1);
            if (sector >= 4 && sector <= 8) sectionList.add(2);
            if ((sector >= 8 && sector <= 10) || sector == 1) sectionList.add(3);
        }
        else if (layer >= 3 && layer <= 4) {
            if (sector >= 1 && sector <= 6) sectionList.add(1);
            if (sector >= 6 && sector <= 10) sectionList.add(2);
            if ((sector >= 10 && sector <= 14) || sector == 1) sectionList.add(3);
        }
        else if (layer >= 5 && layer <= 6) {
            if (sector >= 1 && sector <= 7) sectionList.add(1);
            if (sector >= 7 && sector <= 13) sectionList.add(2);
            if ((sector >= 14 && sector <= 18) || sector == 1) sectionList.add(3);
        }

        return sectionList;
    }
}