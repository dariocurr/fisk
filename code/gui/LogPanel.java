/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;


/**
 *
 * @author dario
 */
public class LogPanel extends JScrollPane {
    
    private final Integer WIDTH;
    private final Integer HEIGHT;
    private static final JTextArea TEXT_AREA = new JTextArea();
    
    public LogPanel(int width, int height) {
        super(TEXT_AREA, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        LogPanel.TEXT_AREA.setEditable(false);
        this.WIDTH = width;
        this.HEIGHT = height;
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
    }
    
}
