/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author comapq presario
 */
public class ToolBar extends JPanel{
    
    static JLabel con_label = new JLabel("Waiting...");
    JButton uploads = new JButton(new ImageIcon("Upload2.png"));
    JButton local = new JButton(new ImageIcon("Desktop Folder.png"));
    JButton server = new JButton(new ImageIcon("server folder.png"));
    JButton synch = new JButton(new ImageIcon("synch1.png"));
    
    
    public ToolBar()
    {
        this.setBackground(Color.BLACK);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        this.add(con_label);
        this.add(uploads);
        this.add(local);
        this.add(server);
        this.add(synch);
        
        //configuration of the tools
        uploads.setContentAreaFilled(false);
        uploads.setBorder(null);
        uploads.setToolTipText("Upload Files");
        uploads.setEnabled(false);
        uploads.addActionListener(Main.eventhandler);
        
        local.setContentAreaFilled(false);
        local.setBorder(null);
        local.setToolTipText("Local Folder");
        local.setEnabled(false);
        local.addActionListener(Main.eventhandler);
        
        server.setContentAreaFilled(false);
        server.setBorder(null);
        server.setToolTipText("Server Folder");
        server.setEnabled(false);
        server.addActionListener(Main.eventhandler);
        
        
        
        synch.setContentAreaFilled(false);
        synch.setBorder(null);
        synch.setToolTipText("Synchronise");
        synch.setEnabled(false);
        synch.addActionListener(Main.eventhandler);
        
        con_label.setForeground(Color.YELLOW);
        
        
    }
}
