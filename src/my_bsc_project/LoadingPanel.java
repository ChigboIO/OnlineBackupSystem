/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 *
 * @author comapq presario
 */
public class LoadingPanel extends JPanel{
    JLabel loadinglabel;
    JLabel loadingtext;
    public LoadingPanel()
    {
        this.setLayout(null);
        this.setOpaque(false);
        loadinglabel = new JLabel(new ImageIcon("images/loadingAnimation.gif"));
        loadingtext = new JLabel("Loging in...");
        
        loadinglabel.setBounds(300,200,208,13);
        loadingtext.setBounds(370, 220, 100, 15);
        
        this.add(loadinglabel);
        this.add(loadingtext);
    }
}
