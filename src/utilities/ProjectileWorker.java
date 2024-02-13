/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;
import javax.swing.SwingWorker;
import java.util.ArrayList;
/**
 *
 * @author Westley
 */
public class ProjectileWorker extends SwingWorker<ArrayList<Projectile>, Object>
{
    private ArrayList<Projectile> pros;
    public ProjectileWorker(ArrayList<Projectile> arr)
    {
        pros = arr;
    }
    public ArrayList<Projectile> doInBackground()
    {
        for(int i = 0 ; i < pros.size() ; i++)
        {
            Projectile p = pros.get(i);
            p.setX((int)p.getX() + p.getXChange());
            p.setY((int)p.getY() + p.getYChange());
            p.setNumTimes(p.getNumTimes()-1);
        }
        return pros;
    }
}
