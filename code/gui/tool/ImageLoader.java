/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.*;
import java.io.*;
import javax.imageio.*;

/**
 *
 * @author dario
 */
public class ImageLoader {

    public static Image loadImage(String path) {
        Image temp = null;
        try {
            temp = ImageIO.read(new File(path));
        } catch (FileNotFoundException exx) {
            System.out.println("Image " + path + " not found!");
        } catch (IOException ex) {
            System.out.println("IO Error!");
        }
        return temp;
    }

}
