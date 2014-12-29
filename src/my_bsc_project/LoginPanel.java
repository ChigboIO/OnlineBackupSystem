/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
 *
 * @author comapq presario
 */
public class LoginPanel extends JPanel{
    //Connect connect;
    static JPasswordField password_field = new JPasswordField(20);
    JLabel password_label = new JLabel("Enter Your Password");
    JLabel key_label = new JLabel(new ImageIcon("images/key9.png")); 
    JPanel loading = new JPanel();
    JLabel loading_label = new JLabel(new ImageIcon("images/loadingAnimation.gif"));
    JLabel loading_text = new JLabel("Connecting...");
    JButton forgot_pass_btn;
    
    ResultSet result;
    static String username;
    static JPanel loginholder;
    static JButton lock;
    public LoginPanel()
    {
        setLayout(null);
        setOpaque(false);
        //setOpaque(false);
        //pan.setSize(600, 600);
        lock = new JButton("Lock");
        lock.addActionListener(Main.eventhandler);
        lock.setBounds(220, 20, 200, 30);
        lock.setFocusable(false);
        lock.setVisible(false);
        add(lock);
        
        loginholder = new JPanel();
        loginholder.setLayout(null);
        loginholder.setOpaque(false);
        
        loading_text.setForeground(Color.GRAY);
        loading.add(loading_label);
        loading.add(loading_text);
        loading.setOpaque(false);
        loading.setBounds(60, 0, 250, 40);
        loginholder.add(loading);
        loading.setVisible(false);
        
        password_label.setForeground(Color.BLACK);
        password_label.setFont(new Font("LCD",Font.ITALIC + Font.BOLD,18));// or "Lucida Calligraphy" or "DigifaceWide"
        password_label.setBounds(60,120,200,30);
        loginholder.add(password_label);
        
        
        password_field.setBorder(null);
        password_field.setOpaque(false);
        password_field.addKeyListener(Main.eventhandler);
        password_field.setBounds(33, 177, 287, 20);
        loginholder.add(password_field);
        
        forgot_pass_btn = new JButton("Forgot Pasword");
        forgot_pass_btn.setContentAreaFilled(false);
        forgot_pass_btn.setBorder(null);
        forgot_pass_btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgot_pass_btn.setForeground(Color.BLUE);
        forgot_pass_btn.setFont(new Font("Serif", Font.ITALIC, 14));
        forgot_pass_btn.addActionListener(new ForgotPasswordEvent());
        forgot_pass_btn.setBounds(60, 230, 150, 25);
        loginholder.add(forgot_pass_btn);
        
        key_label.setBounds(5, 100, 420, 170);
        loginholder.add(key_label);
        
        loginholder.setBounds(100, 5, 450, 300);
        add(loginholder);
        
        try {
            //File file = new File("user.dll");
            username = Main.username_file.readUTF();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not read file 'user.dll' ::: IOException : "+ex.getMessage());
        }
    }
    
    
    public boolean doLogin(String user, String password)
    {
        //connect = new Connect();
        
        try 
        {
            result = Connect.statement.executeQuery("SELECT * FROM users WHERE username = '"+user + "' AND password = '" + password + "'");
            
            if(result.first())
                return true;
            else
                return false;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Could not execute your login query ::: SQLException : "+ex.getMessage());
            return false;
        }
        
        //return false;
    }
    public void updateLoadingText(String text)
    {
        this.loading_text.setText(text);
        this.loading_text.repaint();
        this.loading_text.revalidate();
        this.loading.repaint();
        this.loading.revalidate();
        this.repaint();
        this.revalidate();
    }
}
