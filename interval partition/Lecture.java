/*
 * Beau Albritton 
 * 
 * Lecture Class
 * 
 * A lecture is an object with these three fields: 
 * startTime: int, start time of the lecture
 * finishTime: int, finish time of the lecture
 * name: String, name of this lecture
 */

public class Lecture
{
    //Declaring members
    String name;
    int startTime;
    int finishTime;

    /*
     * Default constructor
     */
    public Lecture(String name, int start, int finish)
    {
        setStartTime(start);
        setFinishTime(finish);
        setName(name);
    }

    /**
     * Setters, getters below.
     * Boilerplate Below
     */

    public void setStartTime(int s)
    {
        this.startTime = s;
    }


    public void setFinishTime(int f)
    {
        this.finishTime = f;
    }

    public void setName(String n)
    {
        this.name = n;
    }


    public int getStartTime()
    {
        return this.startTime;
    }
    
    public int getFinishTime()
    {
        return this.finishTime;
    }

    /*
     * Finally a toString() for this class 
     */

    public String toString()
    {
        return("Lecture:" + this.name + " Start: " + this.startTime + " Finish: " + this.finishTime);
    }


}