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
import javax.swing.border.LineBorder;

/**
 *
 * @author comapq presario
 */
public class TransferAnimation extends JPanel{
    Toolkit kit = Toolkit.getDefaultToolkit();
    Image img = kit.createImage("images/filecopy.gif");
    public TransferAnimation()
    {
        EmptyBorder border = new EmptyBorder(5,10,15,10);
        this.setBorder(border);
        this.setSize(500,100);
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
