/**
 * Beau Albritton
 * Main.java for Knapsack Algorithm (post processing approach)
 * with 2D Memoization Table
 * 
 * This main class reads user input for maxWeight (the weight W) and file in "/items.txt" 
 * initializes and populates an ArrayList of Items with their respective indices, weights and values.
 * 
 * It then prints out each iteration of the given Knapsack problem with nice formatting,
 * and returns the optimal items and their sums at the end.
 * 
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Main
{
    //Item ArrayList that will be read into (see Item.java)
    static ArrayList<Item> itemsForKnapsack;
    //Main loop
    public static void main(String[] args)
    {
        //Initiailizing knapsack, getting user input (simple next int, no validation)
        itemsForKnapsack = new ArrayList<>();
        System.out.println("Enter a max weight for the knapsack: ");
        Scanner userinput = new Scanner(System.in);
        int weight = userinput.nextInt();

        //Initializes the items file found in "/items.txt"
        initializeItems();

        //Initializing knapsack with list of read in Item objects and inputted weight
        Knapsack knapsack = new Knapsack(itemsForKnapsack, weight);
        //Running the algorithm (see Knapsack.java). First finding optimal values, then backtracking over it recursively
        knapsack.run();

        //Sum for values
        int sum = 0;
        //Getting the size of optimal items in knapsack and storing it for later use in itemSet and loops
        int numOptimalItems = knapsack.optimalItems.size();
        int[] itemSet = new int[numOptimalItems];

        
        for(int i = 0; i < numOptimalItems; ++i)
        {   //Add optimal item value to sum
            sum+=knapsack.optimalItems.get(i).value;
            //Populate set of lists with corresponding item indices
            itemSet[i] = knapsack.optimalItems.get(i).index;
        }
        //Nice printing
        System.out.println("--- Optimal Items ---\n"+ Arrays.toString(itemSet) + "\nSum of Values: " + sum);

    }
    /**
     * This function reads "items.txt" (in this directory) and extracts
     * each item line by line, first digit corresponding to index, then weight and value respectively
     */
    public static void initializeItems()
    {
        //Filepath
        String filePath = "items.txt";
        //Try catch necessary for File input
        try
        {
            File fileInput = new File(filePath);
            //Scanning that file
            Scanner fileScanner = new Scanner(fileInput);
            //Then looping through all lines
            while(fileScanner.hasNextLine())
            {
                //Getting the current line, then subsequently scanning it
                String currentLine = fileScanner.nextLine().replaceAll(",", " ");
                Scanner lineScanner = new Scanner(currentLine);
                
                //As long as each line has 3 digits separated by a comma delimeter, this will generally work
                //First index corresponds to index, then weight, then value respectively
                int itemValues[] = new int[3];
                //Counter for while loop
                int ct = 0;
                while(lineScanner.hasNextInt())
                {
                    //Item values at current index is the next int on the line itself
                    itemValues[ct] = lineScanner.nextInt();
                    ++ct;
                }
                //Initializing a new Item object that will be added to the knapsack list. With corresponding values
                Item itemToAdd = new Item(itemValues[0],itemValues[1],itemValues[2]);
                itemsForKnapsack.add(itemToAdd);
            }
        }
        //Print error (This shouldn't happen as long as this file is being run in the same directory as the source code (including items.txt))
        catch (FileNotFoundException e)
        {
            System.out.println("!!!!!!!CHECK LINE 95!!!!!!!!!!\n" + e);
        }
    }
}
