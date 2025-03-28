/**
 * Beau Albritton
 * 
 * 
 * Linked List Class
 * 
 * This class implements a *very minimal* Linked List using OOP.
 * 
 */

public class LinkedList {

    //Head of the linked list
    Node head;

    //Default constructor. Initialize an empty list with no head.
    public LinkedList()
    {
        this.head = null;

    }

    //This method adds a new node with corresponding data to the list.
    public void append(int data, int weight)
    {
        Node nodeToAppend = new Node(data,weight);
    
        
        //If the list is empty, make this node the new head.
        if(this.head == null)
        {
            this.head = nodeToAppend;
        }
        else
        {
            //Else, evaluate the node by traversing the node it points to (until the last node has nothing to point to)
            Node nodeToEvaluate = this.head;
        
            //Traversing list, indexing nodes one at a time
            while(nodeToEvaluate.getNextNode() != null)
            {
                nodeToEvaluate = nodeToEvaluate.getNextNode();
            }
            
            //Setting this node's next node to the new node passed in.
            nodeToEvaluate.setNext(nodeToAppend);
        }
    }

    //Basic getter
    public Node getHead()
    {
        return this.head;
    }
    

}
