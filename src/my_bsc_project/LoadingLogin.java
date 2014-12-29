/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bsc_project;

/**
 *
 * @author comapq presario
 */
public class LoadingLogin implements Runnable{
    
    public void run()
    {
        Main.loginpanel.loading.setVisible(true);
        Main.loginpanel.loading.revalidate();
        System.out.println("Connecting...");
    }
}
