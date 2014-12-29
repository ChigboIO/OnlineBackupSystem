/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTPClient;
import softedgelib.ServerConnection;

/**
 *
 * @author comapq presario
 */
public class Remove extends Thread {

    long fileId;
    ResultSet result = null;
    ResultSet result2 = null;
    String filePath = null;

    public Remove(int rowIndex) {
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
            
            try {
                File file = new File(filePath);
                int delete;
                long used_space;
                if(file.exists())
                    delete = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this backup file? \r\n" + file.getName());
                else
                    delete = JOptionPane.showConfirmDialog(null, "This file is no longer found in the original source location,\r\n"
                            + file.getAbsolutePath() + "\r\nAre you sure you want to remove this backup file?");

                if (delete == JOptionPane.YES_OPTION) {
                    String fileName = filePath.substring(filePath.lastIndexOf('\\') + 1);
                    
                    if(deleteFile(fileName))
                    {
                        Connect.statement.executeUpdate("DELETE FROM user_files WHERE file_id = "+ fileId);
                        
                        result2 = Connect.statement.executeQuery("SELECT * FROM user_space WHERE username = '"+LoginPanel.username +"'");
                        result2.next();
                        used_space = result2.getLong("used_space_BYTE") - result.getLong(4);
                        
                        Connect.statement.executeUpdate("UPDATE user_space SET used_space_BYTE = "+ used_space +" WHERE username = "
                                + "'"+LoginPanel.username +"'");
                        
                        FilesPanel.refresh_btn.doClick();
                        JOptionPane.showMessageDialog(null, "Backup removed successfully!!!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Backup not removed from the server");
                    }
                }
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Could not get value from the result set ::: SQLException : " + ex.getMessage());
            } 
            
        }
    }
    public boolean deleteFile(String file_name)
    {
        boolean deleted = false;
        FTPClient client = new FTPClient();
        //FileInputStream fis = null;

        try {
            client.connect(ServerConnection.FTP_SERVER);
            //if(client.isConnected())
            client.login(ServerConnection.FTP_USERNAME, ServerConnection.FTP_PASSWORD);
            deleted = client.deleteFile(LoginPanel.username+"/"+file_name);
            client.logout();
            client.disconnect();
            Main.myfilespanel.refresh_btn.doClick();
            
        }catch (SocketException ex){
            JOptionPane.showMessageDialog(null, "Could not connect to server ::: SocketException Caught"+ ex.getMessage());
        }catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Could not connect to server ::: IOException Caught "+ ex.getMessage());
        }
        return deleted;
    }
}
