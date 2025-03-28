/**
 * Beau Albritton
 * 
 * 
 * Node Class
 * 
 * This class implements a minimal Node data structure.
 * 
 */
import java.util.Comparator;
//Implementing Node with Comparable interface for Java's PriorityQueue library
//SEE: https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
public class Node implements Comparable<Node>{

    //Data to hold
    int data;
    //Weight to hold
    int weight;
    //Points to the next node
    Node nextNode;


    //Default constructor, sets members to null.
    public Node()
    {
        this.data = -1;
        this.weight = -1;
        nextNode = null;
    }

    //Overridden constructor with data and weight as a parameter.
    public Node(int data, int weight)
    {
        this.data = data;
        this.weight = weight;
        nextNode = null;
    }
    //Another constructor, just for data
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
    public void setWeight(int weightToSet)
    {
        this.weight = weightToSet;
        
    }

    public int getWeight()
    {
        return this.weight;
    }

    public Node getNextNode()
    {
        return nextNode;
    }

    @Override
    public int compareTo(Node other)
    {
        // Compare based on priority values (not the cleanest code but this was the only thing i could reason)
        // Without having messy code (this would have had to have been stored in the PrimMST.java file with every other method)
        return Integer.compare(
            PrimMST.priorityList.get(this.getData()), 
            PrimMST.priorityList.get(other.getData())
        );
    }

    public String toString()
    {
        return ""+ this.data + " ";
    }
    
    //Overriden equals method, used in PQ's contains() in ChangeKey()
    @Override
    public boolean equals(Object o) 
    {
        //type casting from object to node
        Node node = (Node) o;
        return this.getData() == node.getData();
    }
    
    /*
     * Documentation for hashCode: https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode--
     * After scowering StackOverflow and other resources it seemed that whenever an equals() method was
     * overriden in a comparable class, he hashCode method would be overriden as well, by convention.
     */
    @Override
    public int hashCode()
    {
        return Integer.hashCode(this.getData());
    }


}

/*
* This is a 'helper' class that is passed through to the Priority Queue Q
* This overrides the basic Java Priority Queue logic and allows for 
* comparison with custom data (when extracting min or removing nodes from queue) 
* 
* SEE: https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html
*/
class NodeComparator implements Comparator<Node>
{
    @Override
    public int compare(Node a, Node b)
    {
        // Compare based on priority values
        return Integer.compare(
            PrimMST.priorityList.get(a.getData()), 
            PrimMST.priorityList.get(b.getData())
        );
    }
}


