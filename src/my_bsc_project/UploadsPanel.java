/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import org.apache.commons.net.ftp.FTPClient;
import softedgelib.ServerConnection;
/**
 *
 * @author comapq presario
 */
public class UploadsPanel extends JPanel implements Runnable{

    /**
     * @param args the command line arguments
     */
    //Connect connect;
    TransferAnimation transfer_anim;
    JProgressBar progress;
    JPanel browse_panel;
    JTextField filePath_field;
    JButton browse_button;
    JButton upload_button;
    File file;
    ResultSet result;
    static JFrame f;
    //FTPClient client;
    public UploadsPanel()
    {
        //this.add(new JLabel("Uploads panel"));
        setLayout(null);
        setOpaque(false);
        
        transfer_anim = new TransferAnimation();
        transfer_anim.setBounds(100, 0, 500, 100);
        transfer_anim.setVisible(false);
        this.add(transfer_anim);
        
        progress = new JProgressBar(0,100);
        progress.setStringPainted(true);
        progress.setMaximum((int)(100));
        progress.setValue(0);
        
        progress.setBounds(200, 150, 330, 20);
        this.add(progress);
                
        browse_panel = new JPanel();
        browse_panel.setBackground(Color.BLACK);
        
        filePath_field = new JTextField(30);
        filePath_field.setEditable(false);
        //filePath_field.setBorder(null);
        filePath_field.setBackground(Color.BLACK);
        filePath_field.setForeground(Color.WHITE);
        
        browse_button = new JButton("Browse");
        
        browse_panel.add(filePath_field);
        browse_panel.add(browse_button);
        browse_panel.setBounds(150, 200, 430, 40);
        add(browse_panel);
        
        upload_button = new JButton("Upload");
        upload_button.setBounds(300,250,120,40);
        upload_button.setEnabled(false);
        add(upload_button);
        
        browse_button.addActionListener(Main.eventhandler);
        upload_button.addActionListener(Main.eventhandler);
    }
    public void showFileChooser()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int choose = chooser.showOpenDialog(f);

        if(choose != JFileChooser.CANCEL_OPTION)
        {
            file = chooser.getSelectedFile();
            if ( ( file == null ) || ( file.getName().equals( "" ) ) )
             {
                JOptionPane.showMessageDialog( f, "Invalid File Name",
                   "Invalid File Name", JOptionPane.ERROR_MESSAGE );
             }else
            {
                if(file.exists() && file.canRead())
                {
                    filePath_field.setText(file.getAbsolutePath());
                    upload_button.setEnabled(true);
                }

            }
        }
    }
    public void run()
    {
        browse_button.setEnabled(false);
        upload_button.setEnabled(false);
        
        long size = file.length();
        long free_space = 0;
        long used_space = 0;
        
        FTPClient client = new FTPClient();
        FileInputStream fis;
        OutputStream os;
        try {
            //connect = new Connect();
            result = Connect.statement.executeQuery("SELECT * FROM user_space WHERE username = '"+LoginPanel.username +"'");
            if(result.first())
                free_space = result.getLong("total_space_BYTE") - (used_space = result.getLong("used_space_BYTE"));
        
            //JOptionPane.showMessageDialog(browse_panel, "used space = "+used_space+"free space = "+free_space+" file size = "+size);
            if(size < free_space)
            {
                
                String type;
                if(file.isDirectory())
                    type = ";type=d";
                else if(file.getName().endsWith("jpg") || file.getName().endsWith("png") || file.getName().endsWith("gif") )
                    type = ";type=i";
                else
                    type = ";type=a";

                /////////////////////

                

                client.connect(ServerConnection.FTP_SERVER, ServerConnection.FTP_PORT);
                if(client.isConnected())
                {
                    if(client.login(ServerConnection.FTP_USERNAME, ServerConnection.FTP_PASSWORD))
                    {
                        client.setAutodetectUTF8(true);
                        // Create an InputStream of the file to be uploaded
                        fis = new FileInputStream(file);

                        // Store file to server
                        os = client.storeFileStream(LoginPanel.username+"/"+ file.getName());
                        byte[] bytesIn = new byte[1024];
                        int read;
                        
                        progress.setMaximum((int)size);
                        int done = 0;
                        while ((read = fis.read(bytesIn)) != -1) {
                            os.write(bytesIn, 0, read);
                            done += read;
                            progress.setValue( done );
                            Rectangle progressRect = progress.getBounds();
                            //progressRect.x = 0;
                            //progressRect.y = 0;
                            progress.paintImmediately( progressRect );

                        }
                        fis.close();
                        os.close();

                        boolean completed = client.completePendingCommand();
                        if (completed) {
                            used_space += size;
                            Connect.statement.executeUpdate("UPDATE user_space SET used_space_BYTE = "+ used_space +" WHERE username = "
                                    + "'"+LoginPanel.username +"'");

                            Connect.statement.executeUpdate("INSERT INTO user_files(user, file_path, file_size) "
                                    + "VALUES('"+ LoginPanel.username +"', '"+ file.getAbsolutePath().replace("\\", "\\\\") +"', "+ size +")");

                            FilesPanel.refresh_btn.doClick();
                            transfer_anim.setVisible(false);
                            JOptionPane.showMessageDialog(this, "File backed up successfully");

                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this, "Backup Failed");
                        }
                    }else
                    {
                        JOptionPane.showMessageDialog(this, "Error loging in to the FTP server!");
                    }
                }else
                {
                    JOptionPane.showMessageDialog(this, "Error!!! Could not connect to the FTP Server");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Sorry, you do not have enough free storage partition in the server");
            }
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Could not query the DB for space ::: SQLException : "+ex.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "IOException catched ::: "+ex.getMessage());
            } finally {
                try {
                    if (client.isConnected()) {
                        client.logout();
                        client.disconnect();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "IOException catched ::: "+ex.getMessage());
                }
            }
            transfer_anim.setVisible(false);
            browse_button.setEnabled(true);
            filePath_field.setText("");
            progress.setValue(0);
    }
}
