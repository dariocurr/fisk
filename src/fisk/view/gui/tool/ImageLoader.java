package fisk.view.gui.tool;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {

    public static Image loadImage(String path) {
        Image temp = null;
        try {
            temp = ImageIO.read(new File(path));
        } catch (FileNotFoundException ex) {
            System.out.println("Image " + path + " not found!");
        } catch (IOException ex) {
            System.out.println("IO Error: " + path);
        }
        return temp;
    }

}
