/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
/**
 *
 * @author comapq presario
 */
public class SummaryPanel extends JPanel{
    //Connect connect;
    JPanel north_panel = new JPanel();
    JPanel progress_panel = new JPanel();
    JButton refresh;
    //JPanel west_panel = new JPanel();
    //JPanel south_panel = new JPanel();
    
    ResultSet result;
    double total_space = 0, used_space = 0, free_space;
    public SummaryPanel()
    {
        this.setOpaque(false);
        if(Connect.connected)
        {
            try
            {
                //connect = new Connect();
                result = Connect.statement.executeQuery("SELECT * FROM user_space WHERE username = '"+LoginPanel.username +"'");
                if(result.first())
                {
                    total_space = (double)result.getLong(2);
                    used_space = (double)result.getLong(3);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Could not execute query for space ::: SQLException : "+ex.getMessage());
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Sorry, you are not connected to the server,\r\n waiting for internet connection...",
                        "Server Connection Error",JOptionPane.ERROR_MESSAGE);
        }
        setLayout(null);
        String u_symbol, f_symbol;
        free_space = total_space - used_space;
        
        long byte_total = (long)total_space;
        long byte_used = (long)used_space;
        long byte_free = (long)free_space;
        
        total_space /= 1073741824;
        
        //converting the used space size to GB, MB, KB or B
        if(used_space >= 1073741824)
        {
            used_space /= 1073741824;
            u_symbol = "GB";
        }else if(used_space >= 1048576)
        {
            used_space /= 1048576;
            u_symbol = "MB";
        }else if(used_space >= 1024)
        {
            used_space /= 1024;
            u_symbol = "KB";
        }else
            u_symbol = "B";
        
        //converting the free space size to GB, MB, KB or B
        if(free_space >= 1073741824)
        {
            free_space /= 1073741824;
            f_symbol = "GB";
        }else if(free_space >= 1048576)
        {
            free_space /= 1048576;
            f_symbol = "MB";
        }else if(free_space >= 1024)
        {
            free_space /= 1024;
            f_symbol = "KB";
        }else
            f_symbol = "B";
        
        
        north_panel.setLayout(new GridLayout(5,1,5,1));
        
        north_panel.setOpaque(false);
        
        JProgressBar progress = new JProgressBar(0,100);
        progress.setStringPainted(true);
        progress.setMaximum((int)(100));
        progress.setValue((int)((byte_used/byte_total)*100));
        progress_panel.add(progress);
        progress_panel.setOpaque(false);
        
        refresh = new JButton("Refresh");
        refresh.addActionListener(Main.eventhandler);
        
        north_panel.add(new JLabel(String.format("Total Space \t ::: \t %.2f %s", total_space ," GB ")+ "("+byte_total+" bytes)"));
        north_panel.add(new JLabel(String.format("Used Space \t ::: \t %.2f %s", used_space ," "+u_symbol)+ " ("+byte_used+" bytes)"));
        north_panel.add(new JLabel(String.format("Free Space \t ::: \t %.2f %s", free_space ," "+f_symbol)+ " ("+byte_free+" bytes)"));
        north_panel.add(progress_panel);
        north_panel.add(refresh);
        
        north_panel.setBounds(200, 80, 250, 200);
        add(north_panel);
        //this.setSize(300, WIDTH);
        //add(getNorth_panel(), BorderLayout.NORTH);
    }
}  