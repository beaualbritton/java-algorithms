
import  java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import  java.util.Scanner;
public class CountingInversions
{
    public static void main(String[] args) {

        //int [] L = {1,5,4,8,10,2,6,9,12,11,3,7};
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

        ArrayList<ArrayList<Integer>> parsedLists = parseFile(fileIn);

        try
        {
            FileWriter fileWriter = new FileWriter("output.txt");
            for(ArrayList<Integer> a : parsedLists)
            {
                int[] L  = new int[a.size()];
                for(int i = 0; i < a.size(); ++i)
                {
                    L[i] = a.get(i);
                }
                SortCountResult sorted = sortAndCount(L);

                String str = "Inversion count: " + sorted.count + ". Sorted Array: " + arraytoString(sorted.sortedArray) ;
                System.out.println(str);
                fileWriter.write(str);
            }
                fileWriter.close();
            }
        catch(IOException e)
        {

        }
    }

    public static SortCountResult sortAndCount(int[] L)
    {
        //one member in L, return 0
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

        //populating these new lists
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

        //Just printing so I can trace
        //printArray(A);
        //printArray(B);

        //Inversion counts from next recursive call
        SortCountResult ra  = sortAndCount(A);
        SortCountResult rb = sortAndCount(B);
        SortCountResult r = mergeAndCount(ra.sortedArray, rb.sortedArray);

        //returning r + ra + rb (TODO: RETURN SORTED LIST FROM MERGE&COUNT)
        return new SortCountResult(ra.count+rb.count+r.count,r.sortedArray);
    }
    
    public static SortCountResult mergeAndCount(int[] A, int[] B)
    {
        int[] L = new int[A.length + B.length];
        int inversionCount = 0;
        //inversion count 
        for (int i = 0; i < A.length; ++i)
        {
            int currentNumA = A[i];
            //Comparing if index at A[i] is Greater than B[j]
            for (int j = 0; j < B.length; ++j)
            {
                int currentNumB = B[j];
                //System.out.println(currentNumB);
                if(currentNumA > currentNumB)
                {
                    //System.out.println(currentNumA + " " + currentNumB);
                    ++inversionCount;
                }

            }
        }

        //TODO: implement merge sort x
        //looping variables 
        int i = 0,j = 0, k = 0;
        while(i < A.length && j < B.length)
        {
            if(A[i] <= B[j])
            {
                L[k] = A[i];
                ++k; ++i;
            }
            else
            {
                L[k] = B[j];
                ++k; ++j;
                //TODO: COUNT INVERSIONS MORE EFFICIENTLY 

            }
        }

        //Now there is at least one element in each arrays A, B that hasn't been added to new list L
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
        //printArray(L);

        return new SortCountResult(inversionCount, L);
    }

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

    public static ArrayList<ArrayList<Integer>> parseFile(Scanner fileIn)
    {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>(0);
        while (fileIn.hasNextLine())
        {
            ArrayList<Integer> lineList = new ArrayList<>(0);
            String currentLine = fileIn.nextLine().trim();
            Scanner lineScanner = new Scanner(currentLine);
            
            while (lineScanner.hasNextInt()) {
                lineList.add(lineScanner.nextInt());
            }
            
            // Check if we found any integers in this line
            if (!lineList.isEmpty()) {
                list.add(lineList);
            }
        }
        return list;
    }
}

//Loose data structure that returns (int, int[])
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
