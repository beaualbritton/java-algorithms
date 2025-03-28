/**
 * Beau Albritton
 * 
 * This program implements the Interval Partition Algorithm,
 * using a min-oriented priority queue.
 * 
 * Program receives an input from the user, for the name of a File
 * containing several Lectures. User input is validated and errors are handled
 * appropriately.
 * 
 * Afterwards, the File is parsed using the parseLecture() method (see below)
 * and all lectures in the File are added.
 * 
 * Afterwards, the ArrayList of Lectures is then passed through to the IntervalPartition() method
 * for proper sorting.
 * 
 */

/**
 * Importing dependencies,
 * data structures (priority queues & arraylist), error handling, 
 * and for comparing (keys in priority queue)
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main{

    public static void main(String[] args)
    {

        //Getting userInput with a new Scanner object
        Scanner userInput = new Scanner(System.in);
        //Declaring variables for file validation
        File readFile;
        Scanner fileIn;

        //Validating file is correct      
        //Using while true loop, breaks after correct input.
        //Not ideal, but was getting an error that readFile,Filein are potentially not initialized
        //when using a while(!validated) where validated was a boolean flag
        while(true){
            System.out.println("Enter a lecture file to scan. Must include file extension: ");
            String fileInput = userInput.nextLine();
            try{
                //Initializing file & reader objects for later parsing
                readFile = new File(fileInput);
                fileIn = new Scanner(readFile);
                break;
            //declaring a 'new File()'' will throw a FileNotFoundException when a filepath is incorrectly input, so catch any potential exception
            }catch(FileNotFoundException error)
            {
                System.out.println("File not found. Check if you have the correct file name & extension");
            }
        } 
        //Setting this ArrayList<Lecture> to the returned List from ParseLectures, 
        //passing in the scanned file as a parameter.
        ArrayList<Lecture> allLectures = ParseLectures(fileIn);        
        
        //Running the IntervalPartition algorithm and printing the amount of allocated rooms
        int d = IntervalPartition(allLectures);
        System.out.println("Depth: " + d);
        
    }
/*
 * This static method implements the IntervalPartition algorithm,
 * and returns the *amount* of allocated classrooms after the algorithm runs.
 * As well as printing the Priority Queue when it finishes.
 */
static public int IntervalPartition(ArrayList<Lecture> lecture)
{
    //Sorting lectures by startTime so that s(1) < s(2) . . . s(n)
    lecture.sort(Comparator.comparingInt(Lecture::getStartTime));

    /*
     * Creating a new PriorityQueue of type Classroom, 
     * since the Classroom class implements & overrides the Comparator interface,
     * the compareTo() method is used to calculate the minimum values (smallest lastFin)
     * in the Priority Queue.
     * 
     */
    PriorityQueue<Classroom> Q = new PriorityQueue<Classroom>();

    //Getting the first item in the set of lectures.
    Lecture firstLectureByStart = lecture.get(0);

    //New list of Lectures (to be passed to the Classroom constructor). Initial capacity of 0 
    ArrayList<Lecture> lectureList = new ArrayList<Lecture>(0);

    //Adding the first lecture to the Lecture list
    lectureList.add(firstLectureByStart);

    //Then creating a new Classroom object with lectureList (assigned only the first lecture)
    //and that lecture's finish Time.
    Classroom cnew = new Classroom(1,lectureList,firstLectureByStart.getFinishTime());

    //Add that to the PriorityQueue<Classroom>
    Q.add(cnew);
    //d=1 according to pseudocode
    int d = 1;
    //Since 0 based indexing starting at 1 instead of 0, since array[1] yields the *second* element
    for (int j = 1; j < lecture.size(); j++)
    {
        //Setting a new classroom to the minimum value in the queue (in the front)
        //Peek allows finding of this value but not the removal
        Classroom c = Q.peek();
        //if c is compatible with j
        if(lecture.get(j).getStartTime() >= c.lastFinish)
        {
            //Removing that value from the front of the queue and reinserting
            //Since Java's Priority Queue doesn't have a built in IncreaseKey value, poll()-ing and then add()-ing
            //A value will reinsert and account for any potential changes (in this case, classroom C has a new lecture inserted, so lastFinish might change)
            Q.poll();
            c.lectures.add(lecture.get(j));
            c.lastFinish = lecture.get(j).getFinishTime();
            Q.add(c);
        }
        else
        {
            //Increase 'depth' or amount of overlapping lectures
            d++;
            //New ArrayList similar to lectureList above, this is just initialized to a
            //and passed into a new classroom constructor
            ArrayList<Lecture> newList = new ArrayList<Lecture>(0);
            //Getting lecture in the sorted list at index j 
            Lecture thisLecture = lecture.get(j);
            
            //Adding that lecture to the list,
            newList.add(thisLecture);
            //That is then passed into this Classoom constructor
            cnew = new Classroom(d, newList, thisLecture.getFinishTime());
            Q.add(cnew);
        }
    }

    /*
     * Iterating through the queue below (for printing) removing 
     * elements from Queue in order, copying to a list and then printing
     * out each Classrooms list of lectures.
     */

    ArrayList<Classroom> allClassrooms = new ArrayList<>();

    // Extract all classrooms from the queue
    while (!Q.isEmpty()) {
        allClassrooms.add(Q.poll());
    }

    // Print all classrooms using simple foreach loop.
    for (Classroom cls : allClassrooms) {
        System.out.println("Classroom " + cls.classNumber + ":");
        for (Lecture lec : cls.lectures) {
            System.out.println("  " + lec.toString());
        }
    }
    //returning depth after printing
    return d;
}

/*
 * This static method is designed to be a helper function for
 * reading in all lectures listed in the file supplied by
 * the user in the while() loop, and returning a list of all of the
 * read lectures.
 */
static public ArrayList<Lecture> ParseLectures(Scanner fileIn){

    //ArrayList to be returned at the end of the method
    ArrayList<Lecture> allLectures = new ArrayList<>();
    //Initializing these variables to represent 'null' or empty (for easier readability when I was bugfixing)
    String name = "";
    int start = -1;
    int finish = -1;

    //Iterating through each *line* in the file, where each line contains one lecture
    //In the format Lecture(name,startTime,finishTime)
    while(fileIn.hasNextLine())
    {
        //Reading all lectures into an ArrayList;
        String currentLine = fileIn.nextLine();

        //'name' is the first character following '('
        //Start time is the first number found s(j) after a delimeter (comma). 
        //Finish time is the second number found f(j) after a delimeter.
        int indexOfDelim = currentLine.indexOf(',');
        
        name =  currentLine.substring(1, indexOfDelim);

        /**
         * Following code might seem a little unintuitive, but I'm trying
         * to slice the String currentLine into three parts, so they can be passed
         * into a Lecture object via constructor.
         * 
         * This is an amalgamation of methods. Forgive me.
         * 
         * substring() is fairly self explanatory
         */

        String afterDelim = currentLine.substring(indexOfDelim+1);
        start = Integer.parseInt(afterDelim.substring(0,afterDelim.indexOf(',')).trim());
        
        afterDelim = afterDelim.substring(afterDelim.indexOf(',')+1);
        finish = Integer.parseInt(afterDelim.substring(0,afterDelim.indexOf(')')).trim());
        
        //create a new lecture and add it to the list
        allLectures.add(new Lecture(name, start, finish));
    }
    //Return the scanned lectures
    return allLectures;
}
}