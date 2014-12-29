/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author comapq presario
 */
public class MenuBar extends JMenuBar{
    JMenu file = new JMenu("File");
    JMenu view = new JMenu("View");
    JMenu window = new JMenu("Window");
    JMenu help = new JMenu("?");
    
    JMenuItem exit = new JMenuItem("Exit");
    
    JMenuItem uploads = new JMenuItem("Uploads");
    JMenuItem myfiles = new JMenuItem("My Files");
    //JMenuItem synch = new JMenuItem("Synchronization");
    
    
    JMenuItem summary = new JMenuItem("Summary");
        
    JMenuItem help2 = new JMenuItem("Help");
    JMenuItem about = new JMenuItem("About");
    
    public MenuBar()
    {
        file.add(exit);
        
        view.add(summary);
        view.add(uploads);
        view.add(myfiles);
        //view.add(synch);
              
        help.add(help2);
        help.add(about);
        
        this.add(file);
        this.add(view);
        this.add(window);
        this.add(help);
        
        //configuration of the menus
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        
        uploads.addActionListener(Main.eventhandler);
        myfiles.addActionListener(Main.eventhandler);
        
        //synch.addActionListener(Main.eventhandler);
        summary.addActionListener(Main.eventhandler);
        
        uploads.setEnabled(false);
        myfiles.setEnabled(false);
        
        //synch.setEnabled(false);
        summary.setEnabled(false);
        
    }
}
