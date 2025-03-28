/*
 * Beau Albritton
 * 
 * Prim's MST class. 
 * 
 * This class implemenets Prim's MST algorithm from pseudocode.
 * Contains several ArrayLists for the set of nodes (S), set of edges (T) and a priority queue (Q).
 * Uses an AdjacencyList data structure to represent the Graph.
 * 
 * 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;
public class PrimMST{

    /*
     * Priority Queue (Q)
     * Set of Nodes (S)
     * Set of Edges (T)
     */
    public static PriorityQueue<Node> Q;
    public static ArrayList<Node> S;
    public static ArrayList<Node[]> T;

    /**
     * Tracking priority of nodes (indexed based)
     * as well as predecessors for later use in Prim's algorithm
     */
    public static ArrayList<Integer>predecessorList;
    public static ArrayList<Integer>priorityList;

    /*
     * Largest value, represents infinity. Displays as '-' in terminal
     */
    static final int INF = 9999;
    /*
     * Character array for printing 
     */
    static final char[] characterConversionArray = {'a','b','c','d','e','f','g','h'};
    

    public static void main(String[] args) {
        //Initializing startNode, graph.
        Node startNode;
        AdjacencyList g = new AdjacencyList(8);
        initializeGraph(g);

        //User input 
        Scanner input = new Scanner(System.in);
        System.out.println("Using Adjacency List Representation.");
        System.out.println("Enter a start node. a-h case sensitive");

        //getting only first character input by user
        char startNodeChar = input.next().charAt(0);
    
        //Converting value for startNodeChar. 'a' represents 97 in javascript, 'b' 98 etc,etc., so 'a' is acting as an offset here
        int startNodeNum = startNodeChar - 'a' + 1;
        
        //Then validating user input
        while(startNodeNum < 1 || startNodeNum > 8)
        {
            System.out.println("Invalid node. Enter a node letter between a-h.");
            startNodeChar = input.next().charAt(0);
            startNodeNum = startNodeChar - 'a' + 1;
        }

        //Traversing graph to find a node that matches startNodeNum
        startNode = null;
        for (LinkedList list : g.getList())
        {  
            Node currentHead = list.getHead();
            while(currentHead != null)
            {
                if(currentHead.getData() == startNodeNum)
                {
                    startNode = currentHead;
                }
                currentHead = currentHead.getNextNode();
            }
        }
        System.out.println("Starting at Node: " + startNode.getData());
        System.out.println("Graph" + g);
        //Run Prim's 
        Prim(g,startNode);

    }

    //Initializing graph with edge weights 
    static void initializeGraph(AdjacencyList graph)
    {
        /*
         * Corresponding letters on the graph and their sourceNode number
         * 
         * a = 1
         * b = 2
         * c = 3
         * d = 4
         * e = 5
         * f = 6
         * g = 7
         * h = 8 
         */

        // node 'a'
        graph.insertEdge(1, 2,9);
        graph.insertEdge(1, 6,14);
        graph.insertEdge(1, 7,15);
        // node 'b'
        graph.insertEdge(2, 3,24);
        //node 'c'
        graph.insertEdge(3, 4,6);   
        graph.insertEdge(3, 5,4);   
        graph.insertEdge(3, 6,18);   
        graph.insertEdge(3, 8,19);     
        // node 'd'
        graph.insertEdge(4, 5,11);   
        graph.insertEdge(4, 8,6); 
        //node 'e'
        graph.insertEdge(5, 6,30);   
        graph.insertEdge(5, 7,20);   
        graph.insertEdge(5, 8,16);
        //node 'f'
        graph.insertEdge(6, 7,5);
        //node 'g' 
        graph.insertEdge(7,8,44);     
    }

    /*
     * Building PriorityQueue and initializing priotiy and predecessor lists
     */
    static void BuildPQ(AdjacencyList graph)
    {
        Q = new PriorityQueue<>(new NodeComparator());
        ArrayList<Node> trackedNodes = new ArrayList<>();
        
        //Avoiding 0 based indexing, so size+1 necessary
        priorityList = new ArrayList<>(graph.getSize() + 1);
        predecessorList = new ArrayList<>(graph.getSize() + 1);

        for (int i = 0; i <= graph.getSize(); i++) {
            priorityList.add(INF);
            predecessorList.add(-1);
        }
        //initialize Q for all nodes in the graph
        for(int i = 0; i < graph.getList().size(); i++)
        {
            LinkedList currentList = graph.getList().get(i);
            Node currentHead = currentList.getHead();


            //traversing linked list
                while(currentHead != null)
                {
                    boolean alreadyAdded = false;
                    
                    for(Node n : trackedNodes)
                    {
                        //Checking if the node data (number) is in the graph list(s)
                        if(currentHead.getData() == n.getData())
                        {
                            alreadyAdded = true;
                        }
                    }
                    //If node not in list
                    if(!alreadyAdded)
                    {
                        Q.add(currentHead);
                        trackedNodes.add(currentHead);
                    }
                    currentHead = currentHead.getNextNode();
                }
        }
    }

    /**
     * Extracts the minimum value from the PriorityQueue (using compare methods in Node class)
     * and returns that node
     */    
    static Node ExtractMin()
    {    
        Node min =  Q.poll();
        //System.out.println(min);
        return min;
    }

    /*
     * Changes key for a node in the priority list by removing then readding the node (after updating the priority list)
     */
    static void ChangeKey(Node n, int key)
    {
        priorityList.set(n.getData(), key);
        if (Q.contains(n)) {
            Q.remove(n);
        }
        Q.add(n);
        
    }
    public static void Prim(AdjacencyList graph, Node startNode)
    {
        //Initializing Q, S and T 
        BuildPQ(graph);
        S = new ArrayList<Node>();
        T = new ArrayList<Node[]>();
        //Seting priority of s to 0
        ChangeKey(startNode, 0);
        Node v;

        //Printing before while
        System.out.println("---------------------------------------");
        printPQ();
        System.out.println("---------------------------------------");
        while(!Q.isEmpty())
        {
            v = ExtractMin();
            S.add(v);
            if(v.getData() != startNode.getData())
            {
                //add edge (predecessor(v),v) to T
                int predData = predecessorList.get(v.getData());
                if (predData != -1) 
                {
                    Node predecessor = new Node(predData);
                    T.add(new Node[]{predecessor, v});
                }
            }
            //Since I implemented my own LinkedList class, navigating through
            //Each edge in the graph requires a *while* loop not a for loop

            //List of edges for a node 'v'
            LinkedList list = graph.getList().get(v.getData()-1);
            Node currentNode = list.getHead();

            while(currentNode!= null)
            {
                boolean nodeInSet = false;

                for(Node n : S)
                {
                    if(n.getData() == currentNode.getData())
                    {
                        nodeInSet = true;
                        break;
                    }
                }
                if(!nodeInSet)
                {
                    int currentEdgeWeight = currentNode.getWeight();
                    if(currentEdgeWeight < priorityList.get(currentNode.getData()))
                    {
                        predecessorList.set(currentNode.getData(), v.getData());
                        ChangeKey(currentNode, currentEdgeWeight);
                    }
                }
                currentNode = currentNode.getNextNode();

            }
                printPQ();
                System.out.println("---------------------------------------");
                
        }

    }
    /*
     * Printing PriorityQueue and sorting by ascending number (with character conversion)
     * Pretty standard string printing. Character conversion is saved in characterConversionArray (accessing each index based on Node number)
     */
    public static void printPQ() {
        ArrayList<Node> sortedList = new ArrayList<>(Q);
        //Using Collections.sort to sort the copied arraylist from Q with Node comparator (implemented in Node.java)
        Collections.sort(sortedList, new NodeComparator()); 

        System.out.print("S = { ");
        for (Node n : S) {
            System.out.print(characterConversionArray[n.getData()-1] + ", ");
            
        }
        System.out.println("}");

        System.out.print("T = { ");
        for(Node[] e : T)
        {
            System.out.print("(" + characterConversionArray[e[0].getData()-1] + ", " + characterConversionArray[e[1].getData()-1] +")");
        }
        System.out.println("}");

        System.out.print("Q = { ");
        for (Node n : sortedList) {
            if(priorityList.get(n.getData()) == INF)
            {
                System.out.print("(" + characterConversionArray[n.getData()-1] + ", " + '-' + ") ");
            }
            else
            {
                System.out.print("(" + characterConversionArray[n.getData()-1] + ", " + priorityList.get(n.getData()) + ") ");
            }
            
        }
        System.out.println("}");
        // System.out.println(priorityList);
        


    }

}
