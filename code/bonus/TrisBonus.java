/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.io.*;
import java.util.*;

/**
 *
 * @author dario
 */
public class TrisBonus extends Bonus<Tris> {
    
    private static final Map<Tris, Integer> TRIS_BONUS = new HashMap<>();
    
    private TrisBonus() {}
    
    public static Integer getBonus(Tris tris) {
        Integer bonus = TRIS_BONUS.get(tris);
        if (bonus != null) {
            return bonus;
        } else {
            return 0;
        }
    }
    
    public static void initBonus() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("res/tris_bonus.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] splitted_line = line.split(",");
                TRIS_BONUS.put(Tris.getTris(splitted_line[0]), Integer.valueOf(splitted_line[1]));
                line = reader.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File tris.txt not found!");
        } catch (IOException ex) {
            System.out.println("IO error!");
        }
    }
    
}
