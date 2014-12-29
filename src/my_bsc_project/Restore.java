/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTPClient;
import softedgelib.ServerConnection;

/**
 *
 * @author comapq presario
 */
public class Restore extends Thread {

    long fileId;
    ResultSet result = null;
    String filePath = null;

    public Restore(int rowIndex) {
        fileId = FilesPanel.files_id[rowIndex];
        //JOptionPane.showMessageDialog(null, "ID of the selected row : "+fileId);
        try {
            result = Connect.statement.executeQuery("SELECT * FROM user_files WHERE file_id = " + fileId);
            result.next();
            filePath = result.getString(3);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error querying the DB ::: SQLException :: " + ex.getMessage());
        }

    }

    public void run() {
        if (filePath != null) {
            //start downloading
            //refresh.setEnabled(false);
            //download_btn.setEnabled(false);

            //long size = file.length();
            //long free_space = 0;
            //long used_space = 0;
            FTPClient client = new FTPClient();
            
            FileOutputStream fos = null;
            
            try {
                File file = new File(filePath);
                int proceed = JOptionPane.YES_OPTION;
                if (file.exists()) {
                    proceed = JOptionPane.showConfirmDialog(null, "This file still exists in the original location: \r\n" + file.getAbsolutePath()
                            + "\r\nDo you want to replace it?");
                }

                if (proceed == JOptionPane.YES_OPTION) {
                    //file.getParentFile()
                    //JOptionPane.showMessageDialog(null, getFreeSpace());
                    
                        file.getParentFile().mkdirs();
                        String fileName = filePath.substring(filePath.lastIndexOf('\\') + 1);

                        client.connect(ServerConnection.FTP_SERVER);
                        client.login(ServerConnection.FTP_USERNAME, ServerConnection.FTP_PASSWORD);

                        // Store file to server
                        fos = new FileOutputStream(filePath);
                        client.enterLocalPassiveMode();
                        client.enterLocalActiveMode();
                        client.setAutodetectUTF8(true);
                        client.retrieveFile(LoginPanel.username+"/"+fileName, fos);
                        

                        JOptionPane.showMessageDialog(null, "File successfully Downloaded");

                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error ' ::: IOException : " + ex.toString());
            } 
            finally {
                
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Could not close the output stream ::: IOException : " + ex.getMessage());
                    }
                }
            }
        }
    }
}
