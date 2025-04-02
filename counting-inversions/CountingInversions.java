/**
 * Beau Albritton 
 * 
 * CountingInversions.java
 * 
 * This class attempts to implement a recursive inversion counting algorithm in Java, using Merge Sort.
 * 
 * Program receives a filepath input from the user. Assuming the file contains an arbitrary amount of unsorted lists, and that one line contains one list. Separated by whitespace
 * Validation is handled, and printed if any exceptions occur. File is parsed and contents are passed into a nested ArrayList<Integer>
 * 
 * Afterwards, for each unsorted list, the SortAndCount method is called and the results (Inversion count and Sorted List) are written to 'output.txt'
 * 
 */

//Dependencies
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class CountingInversions
{
    /**
     * main -- handles file input & output.
     */
    public static void main(String[] args) {

        //User input
        Scanner userInput = new Scanner(System.in);
        //Declaring variables for file validation
        File readFile;
        Scanner fileIn;

        //Validating file is correct      
        //Using while true loop, breaks after correct input.
        //Not ideal, but was getting an error that readFile,Filein are potentially not initialized
        //when using a while(!validated) where validated was a boolean flag
        while(true){
            System.out.println("Enter an input file to scan. Must include file extension: ");
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
        //Parsed list of nested ArrayList, with each individual list representing one line in the input file
        ArrayList<ArrayList<Integer>> parsedLists = parseFile(fileIn);
        //Try/catch required for FileWriter
        try
        {
            //Writing to output.txt
            FileWriter fileWriter = new FileWriter("output.txt");
            //looping through each individual list in parsedLists
            for(ArrayList<Integer> currentList : parsedLists)
            {
                int[] L  = new int[currentList.size()];

                //copying contents to int[] array (easier indexing in merge-and-count)
                for(int i = 0; i < currentList.size(); ++i)
                {
                    L[i] = currentList.get(i);
                }
                //Calling sortAndCount on new list, assigning it to sorted (SortCountResult object)
                SortCountResult sorted = sortAndCount(L);
                //String formatting for output.txt
                String str = "Inversion count: " + sorted.count + ". Sorted Array: " + arraytoString(sorted.sortedArray) ;
                //Printing file output for clarity
                System.out.println(str);
                //Writing string to file 
                fileWriter.write(str);
            }
            //close the file writer after all lists have been sorted and added to output.txt
                fileWriter.close();
            }
        //Catch and print to terminal     
        catch(IOException e)
        {
            System.out.println("IO Error. Output not saved.");
        }
    }

    /**
     * sortAndCount recursively handles inversion counting and sorting for a given list L
     * 
     * following pseudocode:
     * 
     *  Sort-and-Count(L) {
            if list L has one element
                return 0 and the list L;
            Divide the list into two halves A and B;
            (rA, A) <- Sort-and-Count(A);
            (rB, B) <- Sort-and-Count(B);
            (r, L) <- Merge-and-Count(A, B);
            return r = rA + rB + r and the sorted list L;
        }
     */
    public static SortCountResult sortAndCount(int[] L)
    {
        //one member in L, return 0 and current list L
        if(L.length == 1)
            return new SortCountResult(0, L);

        //generating new lists
        int [] A, B;

        //checking if L.length is uneven. if so, lefthand gets 'shorter end' of the stick. or list in this case :^)
        if (L.length % 2 == 0)
        {
            //same length
            A = new int[L.length/2];
            B = new int[L.length/2];

        }
        else
        {
            //B gets length/2 + 1. e.g., if length of list is 5, then length of A is 2 while B's is 3
            A = new int[L.length/2];
            B = new int[(L.length/2) + 1];
        }   

        //populating these new lists based on 1/2 size of L
        for(int i = 0; i < A.length; ++i)
        {
            A[i] = L[i];
        }
        int j;
        //J is the last index that the prior loop terminates at. So, j = length of A
        for(j = A.length; j < L.length; ++j)
        {
            //Now subtracting A's length for proper indexing
            B[j-A.length] = L[j];
        }


        //Inversion counts from next recursive call
        SortCountResult ra  = sortAndCount(A);
        SortCountResult rb = sortAndCount(B);
        SortCountResult r = mergeAndCount(ra.sortedArray, rb.sortedArray);

        //returning r + ra + rb and sortedArray L 
        return new SortCountResult(ra.count + rb.count + r.count, r.sortedArray);
    }
    
    public static SortCountResult mergeAndCount(int[] A, int[] B)
    {
        //New list to be merged, combined length of A and B
        int[] L = new int[A.length + B.length];
        int inversionCount = 0;

        //looping variables i,j,k. i indexes through A. j indexes through B. k indexes through merged list L
        int i = 0,j = 0, k = 0;
        /*
         * merge sort loop, iterate through two separate lists A and B and merge them into L
         */
        while(i < A.length && j < B.length)
        {
            //Not an inversion, currently in order 
            if(A[i] <= B[j])
            {
                L[k] = A[i];
                //Increment indices 
                ++k; ++i;
            }
            //Inversion!
            else
            {
                L[k] = B[j];
                ++k; ++j;
                //I figure that if these lists from the call prior are 'sorted'
                /*
                 * Here's my logic, if A and B are already sorted from the previous call (recursively)
                 * then if A[i] > B[j] then all members in A after i (i+1 etc) must also be out of order.
                 */
                inversionCount += (A.length - i);

            }
        }

        //Now there is at least one element in each arrays A, B that hasn't been added to new list L
        //Since the loop condition is && (both A and B). Only one loop will run after the first while breaks.
        while (i < A.length)
        {
            L[k] = A[i];
            ++k; ++i;
        }
        while (j < B.length)
        {
            L[k] = B[j];
            ++k; ++j;
        }
    
        //Return this as both (int: inversion count and int[]: sorted list)
        return new SortCountResult(inversionCount, L);
    }

    //Simple array to string for simple printing. Iterates and returns a string through all members in an array
    public static String arraytoString(int[] arr)
    {
        String str = ""; 
        str += "{";
        for(int i = 0; i < arr.length; ++i)
        {
            str += "" + arr[i] + ", ";
        }
        str+="}\n";

        return str;
    }

    /*
     * parseFile() takes a Scanner object representing a file input by the user, and
     * iterates over each line in the file. Each line represents a list. So, extract
     * every integer in the given line and add it to the nested ArrayList<Integer> for
     * later handling in main.
     */
    public static ArrayList<ArrayList<Integer>> parseFile(Scanner fileIn)
    {
        //nested list
        ArrayList<ArrayList<Integer>> list = new ArrayList<>(0);
        //Checking if next line
        while (fileIn.hasNextLine())
        {
            //sub list of nested list to be added after iteration of loop
            ArrayList<Integer> lineList = new ArrayList<>(0);
            //remove unnecessary whitespace otherwise nextInt has errors
            String currentLine = fileIn.nextLine().trim();
            //Scanning the line for integers
            Scanner lineScanner = new Scanner(currentLine);
            
            //Adding each integer found to lineList. This was the best method I found to extract multiple digit integers.
            while (lineScanner.hasNextInt()) {
                lineList.add(lineScanner.nextInt());
            }
            
            // Check if we found any integers in this line (ignore potentially empty lists)
            if (!lineList.isEmpty()) {
                list.add(lineList);
            }
        }
        return list;
    }
}

//wrapper that returns (int, int[]) 
class SortCountResult
{
    int count;
    int[] sortedArray;

    public SortCountResult(int c, int[] l)
    {
        this.count = c;
        this.sortedArray = l;
    }
}
