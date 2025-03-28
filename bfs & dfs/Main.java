/**
 * Beau Albritton
 * 
 * BFS & DFS Graph Traversal Program
 * 
 * Program prompts the user for two inputs,
 * a startNode (int)
 * Representing the beginning node for the appropraite graph traversal.
 * and traversalChoice (String).
 * Representing the choice for either Depth First Search, 
 * or Bread First Search.
 * 
 * After receiving the inputs, program will output the proper visit order
 * for the appropriate graph traversal.
 */

import  java.util.Scanner;


public class Main
{
    public static void main (String[] args)
    {

        //Initializing graph corresponding to Homework 2 instructions
        int graphSize = 8;
        AdjacencyList graph = new AdjacencyList(graphSize);
        //Helper function for better readability. See definition.
        initializeGraph(graph);


        //Getting user input via a Scanner object.
        Scanner userInput = new Scanner(System.in);

        //Prompts
        System.out.println("Hello. Welcome to my graph traversal program.");
        System.out.println("Would you like to run BFS or DFS? Type either BFS or DFS, case insensitive.");
        
        //Getting BFS or DFS choice from user.
        String traversalChoice = userInput.nextLine();

        //Handling incorrect input. If string is not 'DFS' nor 'BFS' then prompt user for reinput.
        while(!(traversalChoice.toUpperCase().equals("DFS")) && !(traversalChoice.toUpperCase().equals("BFS")))
        {
            System.out.println("Invalid option. Enter either 'BFS' or 'DFS'. Case insensitive");
            traversalChoice = userInput.nextLine();
        }

        System.out.println("Enter starting node. Any positive integer. 1-8");
        
        //Getting start node choice from user.
        int startNode = userInput.nextInt();
  
        //Handling incorrect input. If the node is not in the graph, then prompt for reinput.
        while (startNode>graphSize || startNode < 0){
            System.out.println("Invalid node. Enter any positive integer. 1-8");
            startNode = userInput.nextInt();
        }
        //Conditional statements that print the output for either BFS or DFS depending on node
        
        //Prints adjacency list via class methods
        System.out.println("Graph Representation via Adjacency List (toString(): ");
        System.out.println(graph.toString());

        if(traversalChoice.toUpperCase().equals("DFS"))
        {
            System.out.println("Running DFS. Starting with node: " + (startNode) +"\n");
            DFS dfs = new DFS(graphSize);
            //0 based indexing. keep this in mind
            dfs.run(graph.getNodeAtIndex(startNode-1),graph);
        }
        else if (traversalChoice.toUpperCase().equals("BFS"))
        {   
            BFS bfs = new BFS(graphSize);
            System.out.println("Running BFS. Starting with node: " + (startNode) +"\n");
            //0 based indexing. keep this in mind
            bfs.run(graph.getNodeAtIndex(startNode-1),graph);
        }

        
    }


    //Starter graph
    //TODO: implement file input for graph
    public static void initializeGraph(AdjacencyList graph)
    {

        graph.insertEdge(1, 2);
        graph.insertEdge(1, 3);
        
        graph.insertEdge(2, 1);
        graph.insertEdge(2, 3);
        graph.insertEdge(2, 4);
        graph.insertEdge(2, 5);

        graph.insertEdge(3, 1);
        graph.insertEdge(3, 2);
        graph.insertEdge(3, 5);
        graph.insertEdge(3, 7);
        graph.insertEdge(3, 8);

        graph.insertEdge(4, 2);
        graph.insertEdge(4, 5);

        graph.insertEdge(5, 2);
        graph.insertEdge(5, 3);
        graph.insertEdge(5, 4);
        graph.insertEdge(5, 6);

        graph.insertEdge(6, 5);

        graph.insertEdge(7, 3);
        graph.insertEdge(7, 8);

        graph.insertEdge(8, 3);
        graph.insertEdge(8, 7);
    }
} 
