/*
 * Beau Albritton 
 * 
 * Classroom Class
 * 
 * A classroom is an object with three fields, 
 * classNumber: int (representing this classrooms number)
 * lectures: ArrayList of type Lecture. Contains all lecture objects assigned to this class.
 * lastFinish: int. represents the latest finish time found in lectures list.
 * 
 * 
 * This class uses or 'implements' the Comparable interface so that it can be 'Compared' by
 * Java's built in Priority Queue class (this class requries a comparator type). 
 * Overriden 'compareTo' method (from Comparable interface) is documented below.
 * 
 * Also see:
 * https://docs.oracle.com/javase/8/docs/api/java/util/PriorityQueue.html
 * https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html
 * https://www.geeksforgeeks.org/comparator-interface-java/
 */

import java.util.ArrayList;
public class Classroom implements Comparable<Classroom>{
    
    //Declaring members
    int classNumber;
    ArrayList<Lecture> lectures; 
    int lastFinish;

    /*
     * Default constructor, takes in three parameters and assigns them to the appropraite fields, 
     * classNumber, lectures and lastFinish
     */
    public Classroom(int classNumber, ArrayList<Lecture> lectures, int lastFinish)
    {
        this.classNumber = classNumber;
        this.lectures = lectures;
        this.lastFinish = lastFinish;

    }
    /*
     * Overridden compareTo() method from Comparable interface. 
     * Takes a single parameter, 'otherClass' : Classroom,
     * a classroom to compare *this* classroom to.
     * 
     * Compares the two lastFinishes (since this is the 'key') for inserting into Java's Priority Queue 
     * with the Integer classes built in .compare() method
     */
    public int compareTo(Classroom otherClass) {
        
        return Integer.compare(this.lastFinish, otherClass.lastFinish);
    }



}
