/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 *
 * @author comapq presario
 */
public class LocalPanel extends ImagePanel{

    /**
     * @param args the command line arguments
     */
    JTable table;
    JButton refresh;
    static JFrame f;
    public LocalPanel()
    {
        
        //this.add(new JLabel("Local Folder panel"));
        //FlowLayout flayout = new FlowLayout();
        setLayout(null);
        String[] header = null;
        Object[][] data = null;
        
        try
        {
            File file = new File("file_list.dll");
            
            if(file.exists() && file.canRead() && file.length() > 0)
            {
                
                Scanner scan = new Scanner(file);
                header = new String[]{"File","Availability","Full Path","Last Modified"};
                data = new Object[countLines(file)][4];
;
                
                int i = 0;
                while(scan.hasNextLine())
                {
                    String nextPath = scan.nextLine();
                    nextPath = nextPath.replace("\\", "\\"+"\\");
                    File checkFile = new File(nextPath);
                    if(checkFile.exists())
                    {
                        data[i][0] = checkFile.getName();
                        data[i][1] = "Available";
                        data[i][2] = checkFile.getPath();
                        data[i][3] = new Date(checkFile.lastModified());
                    }
                    else
                    {
                        data[i][0] = "---";
                        data[i][1] = "Not Available";
                        data[i][2] = nextPath;
                        data[i][3] = "---";
                    }
                    i++;
                }
            }
        }
        catch(FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null,"File not found exception cought : "+ex.getMessage());
        } 
       
        table = new JTable(data, header);
        
        JScrollPane spane = new JScrollPane(table);
        spane.setBounds(20, 0, 750, 400);
        add(spane);
        
        refresh = new JButton("Refresh");
        refresh.setBounds(350, 420, 100, 30);
        refresh.addActionListener(Main.eventhandler);
        add(refresh);
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
    
    public static void main(String[] args) {
        // TODO code application logic here
        f = new JFrame("Testing frame");
        f.setLayout(new BorderLayout());
        f.add(new ToolBar(), BorderLayout.NORTH);
        f.setSize(800,600);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setIconImage(iconimage);
        f.add(new LocalPanel(), BorderLayout.CENTER);
        //checkUser();
        //this.setJMenuBar(menubar);
        f.setVisible(true);
    }
}
