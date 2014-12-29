/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author comapq presario
 */
public class ImagePanel extends JPanel{
    Toolkit kit = Toolkit.getDefaultToolkit();
    Image img = kit.createImage("images/onlinebackup1.jpg");
    public ImagePanel()
    {
        EmptyBorder border = new EmptyBorder(5,10,15,10);
        this.setBorder(border);
    }
    public void paintComponent(Graphics g2)
    {
        Graphics2D g = (Graphics2D)g2;
        super.setBackground(Color.red);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //g.setBackground(Color.BLUE);
        g.setColor(Color.red);
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
