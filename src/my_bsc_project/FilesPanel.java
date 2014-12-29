/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author comapq presario
 */
public class FilesPanel extends JPanel{

    /**
     * @param args the command line arguments
     */
    JTable table;
    static JButton refresh_btn;
    JButton restore_btn;
    JButton remove_btn;
    JLabel status_label;
    ResultSet result;
    static long[] files_id = null;
    static JFrame f;
    public FilesPanel()
    {
        
        //this.add(new JLabel("Local Folder panel"));
        //FlowLayout flayout = new FlowLayout();
        setLayout(null);
        setOpaque(false);
        String[] header = null;
        Object[][] data = null;
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        
        try
        {
            //File file = new File("file_list.dll");
            
            
            if(Connect.connected)
            {
                result = Connect.statement.executeQuery("SELECT * FROM user_files WHERE user = '"+LoginPanel.username +"'");
                result.last();
                header = new String[]{"Availability","File","Size","Uploaded"};
                data = new Object[result.getRow()][4];
                files_id = new long[result.getRow()];
                
                result.beforeFirst();
                int i = 0;
                ImageIcon yes = new ImageIcon("images/true_icon.png");
                while(result.next())
                {
                    String nextPath = result.getString(3);
                    nextPath = nextPath.replace("\\", "\\\\");
                    File checkFile = new File(nextPath);
                    if(checkFile.exists())
                    {
                        data[i][0] = Boolean.TRUE;
                    }
                    else
                    {
                        data[i][0] = Boolean.FALSE;
                    }
                    
                    data[i][1] = result.getString(3).substring(result.getString(3).lastIndexOf('\\')+1);
                    data[i][2] = Math.round(result.getLong(4)/1024) + " KB";
                    data[i][3] = result.getDate(5) +" "+ result.getTime(5);
                    
                    files_id[i] = result.getLong(2);
                    
                    i++;
                }
            }
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"SQLException ::: could not retrive from the DB :: "+ex.getMessage());
        } 
       
        DefaultTableModel model = new DefaultTableModel(data, header);
        table = new JTable(model);
        
        table.setOpaque(false);
        ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(0,0));
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setRowSelectionAllowed(true);
        table.setSelectionBackground(Color.LIGHT_GRAY);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setShowGrid(true);
        
        
        
        JScrollPane spane = new JScrollPane(table);
        spane.setOpaque(false);
        spane.getViewport().setOpaque(false);
        spane.setViewportBorder(null);
        spane.setBounds(10, 0, 650, 490);
        add(spane);

        restore_btn = new JButton("Restore");
        restore_btn.setBackground(Color.GREEN);
        restore_btn.addActionListener(Main.eventhandler);
        restore_btn.setBounds(100,495,150,25);
        add(restore_btn);
        
        refresh_btn = new JButton("Refresh");
        refresh_btn.setBackground(Color.GRAY);
        refresh_btn.setBounds(280, 495, 150, 25);
        refresh_btn.addActionListener(Main.eventhandler);
        add(refresh_btn);
        
        remove_btn = new JButton("Remove");
        remove_btn.setBackground(Color.red);
        remove_btn.addActionListener(Main.eventhandler);
        remove_btn.setBounds(450,495,150,25);
        add(remove_btn);
        
        if(table.getRowCount() == 0)
        {
            restore_btn.setEnabled(false);
            remove_btn.setEnabled(false);
        }else
        {
            table.setRowSelectionInterval(0, 0);
        }
    }
    public int countLines(File file2) throws FileNotFoundException
    {
        Scanner scan2 = new Scanner(file2);
        String nxt;
        int lines = 1;
        while(scan2.hasNextLine())
        {
            nxt = scan2.nextLine();
            lines ++;
        }
        return lines;
    }
    
}