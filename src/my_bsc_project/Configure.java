/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.SocketException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTPClient;
import softedgelib.ServerConnection;


/**
 *
 * @author comapq presario
 */
public class Configure extends JDialog{

    /**
     * @param args the command line arguments
     */
    JPanel dialogPane = new JPanel();
    JPanel content = new JPanel();
    JTextField username = new JTextField(20);
    JPasswordField password = new JPasswordField(20);
    JButton configure_btn = new JButton("Configure");
    ResultSet result;
    Thread thread1;
    public Configure()
    {
        thread1 = new Connect2();
        thread1.start();
        
        this.setSize(400, 500);
        this.setTitle("Sytem Configuration");
        this.setVisible(true);
        this.getContentPane().add(dialogPane);
        this.setLocationRelativeTo(null);
        //this.setModalityType(ModalityType.MODELESS);
        //this.setModal(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogPane.setLayout(null);
        
        content.setLayout(new GridLayout(3,2,5,5));
        content.add(new JLabel("Username :-"));
        content.add(username);
        content.add(new JLabel("Password :-"));
        content.add(password);
        content.add(new JLabel());
        content.add(configure_btn);
        
        content.setBounds(50,150,300,100);
        dialogPane.add(content);
        
        JTextPane message = new JTextPane();
        message.setText("Please enter the Username and Password you created in our official site to configure your software for your use."
                + "If you have not gotten an account, please click the 'Sign Up new account' link. "
                + "Or visit our site @ http://www.setdrive.com/signup to create an account.");
        message.setEditable(false);
        message.setBackground(null);
        
        message.setBounds(20,10,340,100);
        dialogPane.add(message);
        
        JButton signup_link = new JButton("Sign Up New User");
        signup_link.setForeground(Color.BLUE);
        signup_link.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signup_link.setFont(new Font("Arial", Font.ITALIC, 14));
        signup_link.setContentAreaFilled(false);
        signup_link.setBorder(null);
        
        signup_link.setBounds(200,270,150,20);
        dialogPane.add(signup_link);
        signup_link.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                try {
                    Process page = Runtime.getRuntime().exec("cmd /c start http://localhost/setdrive.com/register/index.php");
                    
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Could not execute the new command ::: IOException :: "+ex.getMessage());
                }
            }
        });
        
        configure_btn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                String user = username.getText().toString();
                String pass = String.copyValueOf(password.getPassword());
                
                if(Connect2.connected)
                {
                    if(checkUsername(user, pass))
                    {
                        createServerFolder(user);
                        createFile(user);
                        
                        dispose();
                        new Main();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Error: This username and password was not found in our DB");
                }else
                    JOptionPane.showMessageDialog(null, "Sorry, you are not connected to the server,\r\n waiting for internet connection...",
                        "Server Connection Error",JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    public boolean checkUsername(String user, String password)
    {
        try {
            //Connect connect = new Connect();
            result = Connect2.statement.executeQuery("SELECT * FROM users WHERE username = '"+user + "' AND password = '" + password + "'");
            
            if(result.first())
                return true;
            else
                return false;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Could not query the DB for user name ::: SQLException : "+ex.getMessage());
            return false;
        }
        
    }
    
    public void createFile(String user)
    {
        try {
            RandomAccessFile file = new RandomAccessFile("user.dll","rw");
            file.writeUTF(user);
            file.close();
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not create or write to the file 'user.dll' ::: IOException : "+ex.getMessage());
        }
    }
    
    public void createServerFolder(String user)
    {
        
        FTPClient client = new FTPClient();
        //FileInputStream fis = null;

        try {
            client.connect(ServerConnection.FTP_SERVER);
            
            client.login(ServerConnection.FTP_USERNAME, ServerConnection.FTP_PASSWORD);
            client.makeDirectory(user);
            client.logout();
            client.disconnect();
            
        }catch (SocketException ex){
            JOptionPane.showMessageDialog(null, "Could not connect to server ::: SocketException Caught"+ ex.getMessage());
        }catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Could not connect to server ::: IOException Caught "+ ex.getMessage());
        }
    }
    public static void main(String[] args)
    {
        new Configure();
        
    }
}
