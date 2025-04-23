/**
 * Beau Albritton
 * Knapsack.java
 * 
 * This java class file attempts to implement a 'post-processing'
 * solution to the Knapsack problem. This involves optimizing a 2D array (memoized table)
 * and then tracing it recursively to validate optimal path.
 */
import java.util.ArrayList;
import java.util.Arrays;

public class Knapsack
{
    //ArrayList for all items that need to be considered
    public ArrayList<Item> items;
    //Optimal items (for printing at the end of this program).
    public ArrayList<Item> optimalItems;
    //Memoization table 2D array. M[0..n,0..W] in pseudocode
    public int[][] knapsack; 
    //Maxweight field for later use
    int maxWeight;

    /**
     * Default constructor, takes in an initial ist of items,
     * and a maxWeight (passed by user)
     */
    public Knapsack(ArrayList<Item> initItems, int maxWeight)
    {
        this.items = initItems;
        this.maxWeight = maxWeight;
        optimalItems = new ArrayList<>(0);
    }

    //Small function that runs the full post-processing algorithm for the Knapsack problem
    public void run()
    {
        //First find optimal solution
        iterativeOptimal();
        //Then backtrack through it using recursion
        recursiveFind(items.size(), maxWeight);
    }

    /**
     * This method finds the optimal solution for a Knapsack with N items with a max weight W
     * according to the optimal substructure:
     *  OPT(i, w)=
     * {
        if i = 0
            0, 
        if wi> w
            OPT(i-1, w) 
        otherwise
            max(OPT(i-1, w), values[i] + OPT(i-1, w-weights[i]))
        }

     * @return M[N][W] (final item in memo table)
     */
    public int iterativeOptimal()
    {
        //setting maxWeight to mirror pseudocode
        int W = maxWeight;
        int N = items.size();

        //Initializing memoization table for N items with W max weight
        knapsack = new int[N + 1][W + 1];
        
        //initializing base weights per pseudocode
        for(int w = 0; w <= W; ++w)
        {
            knapsack[0][w] = 0;
        }
        //Iterating through each index row by column
        for(int i =1; i <= N; ++i)
        {
            for(int w = 0; w <= W; ++w)
            {
                //Getting the current 'Item' (see Item.java) for later comparison. (i-1) b/c 0 based indexing
                Item itemAtI = items.get(i-1);
                
                //Grabbing these from item and assigning them
                int value = itemAtI.value;
                int weight = itemAtI.weight;
                
                //If the weight for the current item is larger than the weight being considered
                if(weight> w)
                {
                    //Lower the item
                    knapsack[i][w] = knapsack[i-1][w];
                }
                //Otherwise, the weight is <= the index, so it's compatible 
                else
                {
                    //Current index is the max, either the item before it, or the item at M[i-1][w-wi] plus it's value
                    //All this according to pseudocode
                    knapsack[i][w] = Math.max(knapsack[i-1][w], value + knapsack[i-1][w - weight]);   
                }
            }
            //Then print memoization table for each index
            printKnapsack(i);
        }
        //Return the 'deepest' index.
        return knapsack[N][W];
    }

    //Post processing. Backtracling table recursively
    public int recursiveFind(int i, int w)
    {
        //Base case. Before declarations because items.get(0-1) causes an error
        if(i==0)
            return 0;

        //Declaring item and initializing weight and value for comparison
        Item itemAtI = items.get(i-1);

        int weight = itemAtI.weight;
        int value = itemAtI.value;

        //Similar to optimal substructure. Instead, we're checking if weight is LT the index AND
        //If the current item isn't added to the optimalItems, by comparing index and the sum of the
        //Current value plus whatever is at M[i-1][w-weight] (the next item to consider after this item is PUT IN the knapsack (w-weight implies this))
        if (weight <= w && knapsack[i][w] == value + knapsack[i-1][w-weight])
        {
            optimalItems.add(itemAtI);
            recursiveFind(i - 1, w - weight); 
            
        }
        //Not optimal
        else
        {
            recursiveFind(i-1,w);
        }

        return 0;
    }

    //Nice printing method (per line)
    public void printKnapsack(int iteration)
    {
        System.out.println("--- Iteration "+ iteration +" ---");
        for(int i = 0; i < knapsack.length; ++i)
        {
            System.out.println(Arrays.toString(knapsack[i]));
        }
    }
}

