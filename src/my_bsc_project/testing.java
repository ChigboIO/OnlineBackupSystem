/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;
import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


/**
 *
 * @author comapq presario
 */
public class testing {
    static Connection connection;
    static Statement statement;
    static ResultSet result;
    public static void main(String args[])
    {
        /*
        File file = new File("C:\\Users\\comapq presario\\Documents\\NetBeansProjects\\My_BSc_Project\\test\\folders\\inner");
        //Object date = new Date(file.lastModified());
        //if(file.exists())
        //{
       
            if(file.mkdirs())
            JOptionPane.showMessageDialog(null, file.getName());
        
           
        //}
        */
            try {
                // load database driver class
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                connection = DriverManager.getConnection("jdbc:derby://localhost:1527/science", "science", "science");
                
                    JOptionPane.showMessageDialog(null, "Connected to the Database "+connection.getSchema());
                
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
                //result = statement.executeQuery("create table table2(col1 varchar(10), col2 integer);");
                
                //if(result.first())
                //    System.out.print(result.getString(0)+"  "+result.getString(1));
                //statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "JDBC Driver not found ::: ClassNotFoundException : "+ex.getMessage());
                //System.exit(0);
            } catch(SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Not Connected to the Database ");
            }
    }
    
}
