/**
 * Beau Albritton
 * 
 * Node Class
 * 
 * This class implements a minimal Node data structure.
 * 
 */

public class Node {

    //Data to hold
    int data;
    //Points to the next node
    Node nextNode;


    //Default constructor, sets members to null.
    public Node()
    {
        this.data = -1;
        nextNode = null;
    }

    //Overridden constructor with data as a parameter.
    public Node(int data)
    {
        this.data = data;
        nextNode = null;
    }

    //BASIC GETTERS/SETTERS FOR CLASS MEMBERS.

    public void setNext(Node nodeToSet)
    {
        this.nextNode = nodeToSet;
    }

    public void setData(int dataToSet)
    {
        this.data = dataToSet;
        
    }

    public int getData()
    {
        return this.data;
    }

    public Node getNextNode()
    {
        return nextNode;
    }


}
