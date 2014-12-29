/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

/**
 *
 * @author comapq presario
 */
public class ForgotPasswordEvent implements ActionListener{
    ExecutorService threads1 = Executors.newCachedThreadPool();
    public void actionPerformed(ActionEvent e){
        if(e.getSource()== Main.loginpanel.forgot_pass_btn)
        {
            int send = JOptionPane.showConfirmDialog(null, "Your Login detail will be sent to your email address \r\n"
                    + "with which you opened your account,\r\n"
                    + " you can then log in to your mail to retrieve your password.\r\n"
                    + "Click \"YES\" to send or \"NO\" to reject.");
            if(send == JOptionPane.OK_OPTION)
                threads1.execute(new ForgotPassword());
        }
    }
}
