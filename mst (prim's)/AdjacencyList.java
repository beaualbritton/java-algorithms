/**
 * Beau Albritton
 * 
 * 
 * Adjacency List Class
 * 
 * This class attempts to represent a graph in the style of an 
 * AdjacencyList. 
 * 
 * This class has two members, sizeOf (the corresponding list)
 * and list, an ArrayList of type LinkedList. So at each index of the arraylist, there is a Separate
 * Linked List, representing the edges to a node (at the index of the list)
 * 
 */





import java.util.ArrayList;
public class AdjacencyList 
{
    //Declaring members
    private ArrayList<LinkedList> list;
    private int sizeOf;

    //Constructor. Initializes members,
    //and for the input size of the graph, creates an empty Linked List at each index.
    public AdjacencyList(int size)
    {
        this.sizeOf = size;
        list = new ArrayList<>();

        for(int i = 0; i < this.sizeOf; i++)
        {
            list.add(new LinkedList());
        }
    
    }

    //Inserts an edge into the linked list. Takes in two integers that represent a source node and destination node (to add to the source)
    public void insertEdge(int sourceNode , int destinationNode, int weight)
    {
        //Subtracting by one because of the tricky 0 index 

        sourceNode = sourceNode-1;
        destinationNode = destinationNode-1;
        
        //If either node exists within the bounds of the list (0, n) where n is the input size.
        if ((sourceNode  >= 0 && sourceNode  < this.sizeOf)
        && (destinationNode >= 0 && destinationNode < this.sizeOf))
        {
           //Add that node to the end of the list.
           //Also appends weight
           list.get(sourceNode).append(destinationNode+1,weight); 
           //And append it to the destinationNode (for undirected graph)
           list.get(destinationNode).append(sourceNode+1,weight);
        }

    }
    //Getter for node at a certain index
    public Node getNodeAtIndex(int index) {
        // +1 because index 0 represents node 1
        return new Node(index + 1);  
    }

    //toString for printing and evaluation
    public String toString()
    {
        String returnString = "";
        
        for(int i = 0; i < list.size(); i++)
        {
            LinkedList currentList = list.get(i);
            Node currentHead = currentList.getHead();

            returnString += "Node " + (i+1) + ": ";
            //traversing linked list
            while(currentHead != null)
            {

                returnString += "" + currentHead.getData() + " " + "w=("+currentHead.getWeight()+"), ";
                currentHead = currentHead.getNextNode();
            }
            returnString += "\n";
        }
        return returnString;
    }
    
    public int getSize()
    {
        return this.sizeOf;
    }
    public ArrayList<LinkedList> getList()
    {
        return this.list;
    }
}
