/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import softedgelib.ServerConnection;

/**
 *
 * @author comapq presario
 */
public class ServerPanel extends JPanel implements Runnable{

    /**
     * @param args the command line arguments
     */
    File serverFile = null;
    JTable table;
    JButton refresh;
    JButton download_btn;
    static JFrame f;
    public ServerPanel()
    {
        //this.add(new JLabel("Server folder panel"));
        this.setLayout(null);
        this.setOpaque(false);
        String user = "";//ServerConnection.SERVER_USERNAME;
        String pass = "";//ServerConnection.SERVER_PASSWORD;
        String server = ServerConnection.SERVER_NAME;

        URL url = null;
        String[] header = null;
        Object[][] data = null;
        
        try {
            url = new URL("ftp://backup:backup@127.0.0.1/FTP Backup/Backup");
            
            
                //serverFile = new File(url.toURI());
                serverFile = new File("C:\\FTP Folder\\Backup");
                //JOptionPane.showMessageDialog(this, url.toURI().toString());
            
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(this, "Could not locate the server ::: MalformedURIException :: "+ex.getMessage());
        }
        
        if(serverFile != null && serverFile.exists() && serverFile.isDirectory())
        {
            File files[] = serverFile.listFiles();
            if(files.length > 0)
            {
                header = new String[]{"Name","Size","Last Modified"};
                data = new Object[files.length][3];
                for(int i=0; i< files.length; i++)
                {
                    data[i][0] = files[i].getName();
                    data[i][1] = files[i].length();
                    data[i][2] = new Date(files[i].lastModified());
                }
                
            }
        }
        if(data != null)
        {
            DefaultTableModel model = new DefaultTableModel(data, header);
            table = new JTable(model);
            table.setOpaque(false);
            table.setBackground(null);
            ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);
            table.setRowHeight(30);
            table.setIntercellSpacing(new Dimension(0,0));
            table.setShowHorizontalLines(false);
            table.setShowVerticalLines(false);
            table.setRowSelectionAllowed(true);
            table.setDragEnabled(false);
            //table.setCellSelectionEnabled(false);
            table.setSelectionBackground(Color.CYAN);
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            

            JScrollPane spane = new JScrollPane(table);
            spane.setOpaque(false);
            spane.getViewport().setOpaque(false);
            spane.setViewportBorder(null);
            spane.setBounds(10, 0, 650, 490);
            add(spane);

            refresh = new JButton("Refresh");
            refresh.setBounds(280, 495, 150, 20);
            refresh.addActionListener(Main.eventhandler);
            add(refresh);
            
            download_btn = new JButton("Download");
            download_btn.addActionListener(Main.eventhandler);
            download_btn.setBounds(100,495,150,20);
            add(download_btn);
        }
          
    }
    public void run()
    {
        /*
        refresh.setEnabled(false);
        download_btn.setEnabled(false);
        
        long size = file.length();
        long free_space = 0;
        long used_space = 0;
        
        try {
            //connect = new Connect();
            result = Connect.statement.executeQuery("SELECT * FROM user_space WHERE username = '"+LoginPanel.username +"'");
            if(result.first())
                free_space = result.getLong("total_space_BYTE") - (used_space = result.getLong("used_space_BYTE"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Could not query the DB for space ::: SQLException : "+ex.getMessage());
        }
        
        //JOptionPane.showMessageDialog(browse_panel, "used space = "+used_space+"free space = "+free_space+" file size = "+size);
        if(size < free_space)
        {
            BufferedInputStream is = null;
            BufferedOutputStream os = null;

            String type;
            if(file.isDirectory())
                type = ";type=d";
            else if(file.getName().endsWith("jpg") || file.getName().endsWith("png") || file.getName().endsWith("gif") )
                type = ";type=i";
            else
                type = ";type=a";
            try
            {
                
                URL url = new URL("ftp://backup:backup@127.0.0.1/Backup/"+file.getName()+type);
                
                //JOptionPane.showMessageDialog(this, "The file path of the connection is : "+url.getHost()+"--"+url.getFile());
                
                URLConnection urlc = url.openConnection();

                //JOptionPane.showMessageDialog(this, urlc.getOutputStream());
                os = new BufferedOutputStream(urlc.getOutputStream());
                is = new BufferedInputStream(new FileInputStream(file));

                //os.flush();
                //is.reset();
                
                int done = 0;
                
                
                byte[] byteIn = new byte[1024];
                int read;
                
                // read byte by byte until end of stream
                while (Connect.connected && (read = is.read(byteIn)) != -1)
                {
                   os.write(byteIn, 0, read );
                   done += read;
                   
                   progress.setValue((int)((done/size)*100));
                   //progress.updateUI();
                }
                //updating the database for the used space for the user
                used_space += size;
                
                Connect.statement.executeUpdate("UPDATE user_space SET used_space_BYTE = "+ used_space +" WHERE username = '"+LoginPanel.username +"'");
                
                //adding the source path of uploaded file to the 'file_list' file
                Connect.statement.executeUpdate("INSERT INTO user_files(user, file_path) "
                        + "VALUES('"+ LoginPanel.username +"', '"+ file.getAbsolutePath().replace("\\", "\\\\") +"')");
                
                JOptionPane.showMessageDialog(this, "File successfully uploaded");
                
            }catch(IOException ex)
            {
                JOptionPane.showMessageDialog(this, "Error ' ::: IOException : "+ex.toString());
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Could not update the user_space table ::: SQLException : "+ex.getMessage());
            }
                  
            finally
            {
            if (is != null)
               try
               {
                  is.close();
               }
               catch (IOException ex)
               {
                  JOptionPane.showMessageDialog(this, "Could not close the input stream ::: IOException : "+ex.getMessage());
               }
            if (os != null)
               try
               {
                  os.close();
               }
                catch (IOException ex)
               {
                  JOptionPane.showMessageDialog(this, "Could not close the output stream ::: IOException : "+ex.getMessage());
               }
            }
            
        }
        browse_button.setEnabled(true);
        */
    }
    public static void main(String[] args) {
        // TODO code application logic here
        f = new JFrame("Testing frame");
        f.setLayout(new BorderLayout());
        //f.add(new ToolBar(), BorderLayout.NORTH);
        f.setSize(800,600);
        f.setBackground(Color.yellow);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.setOpacity((float)0.5);
        //this.setIconImage(iconimage);
        f.add(new ServerPanel(), BorderLayout.CENTER);
        //checkUser();
        //this.setJMenuBar(menubar);
        f.setVisible(true);
    }
}

class ImageRenderer extends DefaultTableCellRenderer
{
    
}