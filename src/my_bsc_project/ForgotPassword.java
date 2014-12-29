/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
/**
 *
 * @author comapq presario
 */
public class ForgotPassword implements Runnable{
    private String username;
    private String password = null;
    private String email = null;
    ResultSet result;
    public void run(){
        if(Connect.connected){
            try{
                setUsername(LoginPanel.username);
                String query = "SELECT * FROM users WHERE username = '"+ username +"'";
                result = Connect.statement.executeQuery(query);
                
                if(result.first()){
                    setPassword(result.getString("password"));
                    setEmail(result.getString("email"));
                }
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Could not Query the database ::: SQLException :: "+ ex.getMessage());
            }
            if(password != null){
                sendMail();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Not currently connected to the server");
        }
    }
    
    private void setUsername(String user){
        this.username = user;
    }
    private void setPassword(String pass){
        this.password = pass;
    }
    private void setEmail(String email){
        this.email = email;
    }
    private void sendMail(){

        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", "25");

        Session mailSession = Session.getDefaultInstance(props);
        Message simpleMessage = new MimeMessage(mailSession);

        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
        try {
                fromAddress = new InternetAddress("SetDrive<admin@setdrive.com>");
                toAddress = new InternetAddress(this.email);
        } catch (AddressException ex) {
                JOptionPane.showMessageDialog(null, "Email address not correct ::: AddressException :: "+ ex.getMessage());
        }

        try {
                simpleMessage.setFrom(fromAddress);
                simpleMessage.setRecipient(RecipientType.TO, toAddress);
                simpleMessage.setSubject("Your Password @ SetDrive");
                
                String text = "THis is in responce to your request for your password at SetDrive Backup System"
                        + " Your details are as follows <br>Username : "+this.username+" <br>Password : "+this.password+" <br>"
                        + "You can now login to your application."
                        + "Thank you for using SetDrive";
                simpleMessage.setText(text);

                Transport.send(simpleMessage);
                JOptionPane.showMessageDialog(null, "Your Username and password has been sent to your email \r\nThank you for using SetDrive...");
        } catch (MessagingException ex) {
                JOptionPane.showMessageDialog(null, "Error ocured while sending your mail ::: MessagingException :: "+ ex.getMessage());
        }
	
    }
}
