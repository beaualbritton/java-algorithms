/**
 * Beau Albritton
 * 
 * BFS Class
 * 
 * This class implements BFS using OOP.
 * 
 * It achieves this using a FIFO Queue (see class definition) to pop
 * members off the front of the queue, as well as a list of discovered
 * nodes. 
 * 
 */

import java.util.ArrayList;
public class BFS {
    //Declaring members of the class 
    FIFO_Queue queue;
    ArrayList<Boolean> discovered;

    //Base constructor. Takes in graph size to properly initialize members.
    public BFS(int graphSize) {

        //See class definition for FIFO Queue implementation
        queue = new FIFO_Queue();   
        //Setting this to an ArrayList of booleans, of n size.
        discovered = new ArrayList<Boolean>(graphSize);

        //Setting discovered[i] to false for all reachable nodes.
        for (int i = 0; i < graphSize; i++) {
            discovered.add(false);
        }
    }

    /**
     * BFS method. Takes in a start node and an AdjacencyList representation of a graph
     * until there are no more undiscovered neighbors in the FIFO Queue.
     */
    public void run(Node startNode, AdjacencyList graph) {

        //Start BFS from the given start node, set that nodes data to a variable.
        int nodeData = startNode.getData();
        //Same as DFS, setting index to nodeData-1 for 0 based indexing. Setting this to a variable
        //for readability.
        int nodeIndex = nodeData -1;
        //Append (add to the end) of the queue 
        queue.append(nodeData);
        // Mark start node 's' as discovered
        discovered.set(nodeIndex, true);  

        //Traversing the queue 
        while (queue.getHead() != null) {
            // Get the next node from the queue (the front)
            int currentValue = queue.pop();
            //0 based index
            int currentIndex = currentValue-1;
            System.out.println("Visited Node: " + currentValue);

            //List of adjacent nodes in the graph passed via parameter.
            //This is due to AdjacencyList's implementation containing an ArrayList of type Linked List 
            LinkedList neighbors = graph.getList().get(currentIndex);

            //Getting the 'head' or first item in the list of neighbors
            Node neighbor = neighbors.getHead();

            //Then add all undiscovered neighbors (at the currentIndex node) to the queue and then visit them in order of the queue.
            while (neighbor != null) {
                int neighborValue = neighbor.getData();
                int neighborIndex = neighborValue - 1;
                if (!discovered.get(neighborIndex)) {
                    //Add undiscovered neighbor to the queue.
                    queue.append(neighborValue);
                    //Discovering each neighbor after adding to the end of the queue
                    discovered.set(neighborIndex, true); 
                }
                //Setting neighbor to the next node it's pointing to.
                neighbor = neighbor.getNextNode();
            }
        }
    }
}