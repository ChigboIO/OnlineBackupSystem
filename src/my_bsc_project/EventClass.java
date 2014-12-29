/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

/**
 *
 * @author comapq presario
 */
public class EventClass  implements KeyListener, ActionListener, FocusListener{
    ExecutorService threads;
    public EventClass()
    {
        threads = Executors.newCachedThreadPool();
    }
    
    @Override public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 10)
        {
            //threads.execute(new LoadingLogin());
            String username = LoginPanel.username;
            String password = String.copyValueOf(LoginPanel.password_field.getPassword());
            //System.out.println("Connecting...");
            /*
            try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, "InterruptedException occured ::: "+ex.getMessage());
                }
            */
            if(Connect.connected)//call here a method that checks if user has internet access 
            {
                /*Main.loginpanel.setVisible(false);
                Main.contentPane.remove(0);
                Main.contentPane.add(new LoadingPanel(), BorderLayout.CENTER, 0);
                */
                Main.loginpanel.updateLoadingText("Authenticating...");
                
                //System.out.println("Authenticating...");
                /*
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, "InterruptedException occured ::: "+ex.getMessage());
                }
                */
                if(Main.loginpanel.doLogin(username, password))
                {  

                    Main.loginpanel.updateLoadingText("Loging in...");
                    
                    //System.out.println("Loging in...");
                    /*
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        JOptionPane.showMessageDialog(null, "InterruptedException occured ::: "+ex.getMessage());
                    }
                    */
                    Main.summarypanel = new SummaryPanel();
                    Main.uploadspanel = new UploadsPanel();
                    Main.myfilespanel = new FilesPanel();
                    //Main.serverpanel = new ServerPanel();
                    //Main.synchpanel = new SynchPanel();

                    Main.tabpane.setComponentAt(1, Main.uploadspanel);
                    Main.tabpane.setComponentAt(2, Main.myfilespanel);
                    //Main.tabpane.setComponentAt(3, Main.serverpanel);
                    //Main.tabpane.setComponentAt(3, Main.synchpanel);
                    Main.tabpane.setComponentAt(3, Main.summarypanel);
                    
                    LoginPanel.password_field.setText("");
                    LoginPanel.loginholder.setVisible(false);
                    LoginPanel.lock.setVisible(true);
                    
                    Main.menubar.uploads.setEnabled(true);
                    Main.menubar.myfiles.setEnabled(true);
                    
                    //Main.menubar.synch.setEnabled(true);
                    Main.menubar.summary.setEnabled(true);

                    Main.tabpane.setEnabledAt(1, true);
                    Main.tabpane.setEnabledAt(2, true);
                    Main.tabpane.setEnabledAt(3, true);
                    //Main.tabpane.setEnabledAt(4, true);
                    //Main.tabpane.setEnabledAt(5, true);
        
                    Main.tabpane.setSelectedIndex(3);
                }
                else
                {
                    /*Main.contentPane.remove(0);
                    Main.contentPane.add(Main.loginpanel, BorderLayout.CENTER, 0);
                    Main.loginpanel.setVisible(true);
                    */
                    JOptionPane.showMessageDialog(null, "Wrong Password");
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Sorry, you are not connected to the server,\r\n waiting for internet connection...",
                        "Server Connection Error",JOptionPane.ERROR_MESSAGE);
            Main.loginpanel.loading.setVisible(false);
        }
        
    }

    @Override public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()== LoginPanel.lock)
        {
            Main.menubar.uploads.setEnabled(false);
            Main.menubar.myfiles.setEnabled(false);
            
            //Main.menubar.synch.setEnabled(false);
            Main.menubar.summary.setEnabled(false);

            Main.tabpane.setEnabledAt(1, false);
            Main.tabpane.setEnabledAt(2, false);
            Main.tabpane.setEnabledAt(3, false);
            //Main.tabpane.setEnabledAt(4, false);
            //Main.tabpane.setEnabledAt(5, false);
            
            LoginPanel.lock.setVisible(false);
            LoginPanel.loginholder.setVisible(true);
            
        }
        else if(e.getSource()== Main.menubar.uploads)
        {
            Main.tabpane.setSelectedIndex(1);
            
        }
        else if(e.getSource() == Main.menubar.myfiles)
        {
            Main.tabpane.setSelectedIndex(2);
        }
        
        else if(e.getSource()== Main.menubar.summary)
        {
            Main.tabpane.setSelectedIndex(3);
        }
        else if(e.getSource()== Main.uploadspanel.browse_button)
        {
            Main.uploadspanel.showFileChooser();
        }
        else if(e.getSource()== Main.uploadspanel.upload_button)
        {
            if(Connect.connected)
            {
                Main.uploadspanel.transfer_anim.setVisible(true);
                threads.execute(Main.uploadspanel);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Sorry, you are not connected to the server,\r\n waiting for internet connection...",
                            "Server Connection Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == Main.myfilespanel.restore_btn)
        {
            if(Connect.connected)
            {
                Thread restore = new Restore(Main.myfilespanel.table.getSelectedRow());
                restore.start();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Sorry, you are not connected to the server,\r\n waiting for internet connection...",
                            "Server Connection Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == Main.myfilespanel.remove_btn)
        {
            if(Connect.connected)
            {
                Thread restore = new Remove(Main.myfilespanel.table.getSelectedRow());
                restore.start();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Sorry, you are not connected to the server,\r\n waiting for internet connection...",
                            "Server Connection Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource()== Main.summarypanel.refresh)
        {
            Main.summarypanel = new SummaryPanel();
            Main.tabpane.setComponentAt(3, Main.summarypanel);
        }
        else if(e.getSource()== Main.myfilespanel.refresh_btn)
        {
            Main.myfilespanel = new FilesPanel();
            Main.tabpane.setComponentAt(2, Main.myfilespanel);
        }
        
        
        else
        {
            ;
        }
        if(e.getSource()== Main.menubar.exit)
        {
            System.exit(0);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource() == Main.myfilespanel.table)
        {
            
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
