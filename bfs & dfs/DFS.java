/**
 * Beau Albritton
 * 
 * DFS Class
 * 
 * This class implements a recursive DFS using OOP.
 * 
 * It achieves this using a LIFO Queue (see class definition) to pop
 * members off the back of the queue, as well as a list of discovered
 * nodes. 
 * 
 */


import java.util.ArrayList;
public class DFS {

    //Declaring members of the class
    LIFO_Queue queue;
    ArrayList<Boolean> discovered;

    //Base constructor. Takes in graph size to properly initialize members.
    public DFS(int graphSize)
    {
        //See class definition for LIFO Queue implementation
        queue = new LIFO_Queue(); 
        //Setting this to an ArrayList of booleans, of n size.
        discovered = new ArrayList<>(graphSize);

        //Setting discovered[i] to false for all reachable nodes.
        for (int i = 0; i < graphSize; i++) {
            discovered.add(false);
        }

    }

    /**
     * Recursive DFS method. Takes in a start node and an Adjacency List representation of a graph, runs 
     * until there are no more members in the LIFO Queue. 
     */
    
    public void run(Node node, AdjacencyList graph) {
        int nodeData = node.getData();
        // Converting to 0 based index and storing it in a new var
        //so that get() method calls on the queue aren't confusing to read 
        int nodeIndex = nodeData - 1;  
    
        //Setting discovered[node] true
        discovered.set(nodeIndex, true);

        //Visit the node
        System.out.println("Visited Node: " + nodeData);
    
        //List of adjacent nodes in the graph passed via parameter.
        //This is due to AdjacencyList's implementation containing an ArrayList of type Linked List 
        LinkedList neighbors = graph.getList().get(nodeIndex);
        //Getting the 'head' or first item in the list of neighbors
        Node neighbor = neighbors.getHead();
        
        //Traversing all neighbors for the current node, stopping at the node with no neighbor.
        while (neighbor != null) {
            int neighborValue = neighbor.getData();
            // Again, converting to 0 based index for readability's sake.
            int neighborIndex = neighborValue - 1;
            //If not discovered then push it onto the queue
            if (!discovered.get(neighborIndex)) {
                //Storing original node number. Reverting 0 based index for recursive call.
                //Pushing (adding to the front) of the queue
                queue.push(neighborValue);  
            }
            //Next neighbor in the list
            neighbor = neighbor.getNextNode();
        }
        

        //For the length of the queue, pop the last node added to the queue (deepest/furthest neighbor in adjacency list)
        //and invoke a recursive DFS call with the popped node passed as the parameter.
        while (!queue.isEmpty()) {
            int nextNode = queue.pop();
            if (!discovered.get(nextNode - 1)) {
                run(new Node(nextNode), graph);
            }
        }
    }

}
