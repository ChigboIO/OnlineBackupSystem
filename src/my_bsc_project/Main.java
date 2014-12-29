/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
/**
 *
 * @author comapq presario
 */
public class Main extends JFrame{
    
    /**
     * @param args the command line arguments
     */
    Thread thread;
    static EventClass eventhandler;
    static MenuBar menubar;
    static JPanel contentPane;
    static LoginPanel loginpanel;
    static RandomAccessFile username_file;
    static SummaryPanel summarypanel;
    static UploadsPanel uploadspanel;
    static FilesPanel myfilespanel;
    static JTabbedPane tabpane;
    static JLabel con_label;
    static JLabel status_label;
    
    public Main()
    {
        UIManager.put("TabbedPane.contentOpaque", false);
        UIManager.put("TabbedPane.selected", Color.WHITE);
        
        initComponents();
           
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image iconimage = kit.createImage("images/Upload.png");
        
        setTitle("Online - Back - Up - System");
        setSize(800,600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(iconimage);
        getContentPane().add(contentPane);
        //checkUser();
        this.setJMenuBar(menubar);
        setVisible(true);        
        this.setDefaultLookAndFeelDecorated(true);

        ImageIcon loginIcon = new ImageIcon("images/key10.png");
        ImageIcon uploadIcon = new ImageIcon("images/Upload2.png");
        ImageIcon localIcon = new ImageIcon("images/Desktop Folder.png");
        ImageIcon summary = new ImageIcon("images/summary.png");

        tabpane.addTab("", loginIcon, loginpanel, "Login / Lock");
        tabpane.addTab("", uploadIcon, null, "Upload");
        tabpane.addTab("", localIcon, null, "My Files");
        tabpane.addTab("", summary, null, "Summary");

        tabpane.setEnabledAt(1, false);
        tabpane.setEnabledAt(2, false);
        tabpane.setEnabledAt(3, false);
        tabpane.setBackground(Color.BLACK);
        tabpane.setOpaque(false);
        tabpane.setFocusable(false);
                
        contentPane.setLayout(null);
        
        //contentPane.add(toolbar, BorderLayout.NORTH); 
        tabpane.setBounds(10, 0, contentPane.getWidth(), contentPane.getHeight());
        contentPane.add(tabpane);
        
        con_label = new JLabel("Waiting...");
        con_label.setForeground(Color.YELLOW);
        con_label.setBounds(3, 520, 80, 15);
        contentPane.add(con_label);
        
        status_label = new JLabel("");
        status_label.setForeground(Color.WHITE);
        status_label.setBounds(130, 530, 650, 20);
        contentPane.add(status_label);
        //connect = new Connect();
        thread = new Connect();
        thread.start();
        
        LoginPanel.password_field.grabFocus();
        
    }
    public static void initComponents()
    {
        
        //connect = 
        eventhandler = new EventClass();
        menubar = new MenuBar();
        contentPane = new ImagePanel();
        //toolbar = new ToolBar();
        loginpanel = new LoginPanel();
        username_file = null;
        
        tabpane = new JTabbedPane(JTabbedPane.LEFT);
        
    }
    
    public static void main(String[] args){
        // TODO code application logic here
        //System.out.println("The program has started running");
        try {
            username_file = new RandomAccessFile("user.dll","rw");
            
            if(username_file.length() != 0)
            {
                //start the main program if the username file contatains some text(username)
                
                new Main(); 
                
            }
            else
            {
                //call configure panel to configure user's username if it has not been configured
                new Configure();
                
            }
        }
        catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Could not create or read file 'user.dll' ::: IOException : "+ex.getMessage());
        }
        
        //new NewUserDialog();
    }
}

