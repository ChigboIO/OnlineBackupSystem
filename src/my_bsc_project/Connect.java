/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import softedgelib.ServerConnection;

/**
 *
 * @author comapq presario
 */
public class Connect extends Thread{
    static Connection connection;
    static Statement statement;
    ResultSet result;
    static boolean connected;
    String serverName;
    String DBPort;
    String mydatabase;
    String DATABASE_URL;
    String USERNAME;
    String PASSWORD;
    public Connect()
    {
        // Create a connection to the database
        
        
        serverName = ServerConnection.SERVER_NAME;
        DBPort = ServerConnection.DB_PORT;
        mydatabase = ServerConnection.DB_NAME;
        DATABASE_URL = "jdbc:mysql://" + serverName +":"+ DBPort + "/" + mydatabase; // a JDBC url
        USERNAME = ServerConnection.DB_USERNAME;
        PASSWORD = ServerConnection.DB_PASSWORD;
         
        
    }
    @Override public void run()
    {
        while(true)
        {
            try {
                // load database driver class
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                //connection.setNetworkTimeout(new exec(), 0);
                
                //if(connection.isValid(0))
                //{
                    connected = true;
                    Main.con_label.setText("Connected");
                    Main.con_label.setForeground(Color.GREEN);
                //}
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "JDBC Driver not found ::: ClassNotFoundException : "+ex.getMessage());
                System.exit(0);
            } catch(SQLException ex)
            {
                connected = false;
                Main.con_label.setText("Not Connected");
                Main.con_label.setForeground(Color.red);
            }
        }
    }
}