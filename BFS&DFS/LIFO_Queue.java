/**
 * Beau Albritton
 * 
 * 
 * lifo Queue Class 
 * 
 *  
 * Program that attempts to implement a proper FIFO Queue data structure. 
 * 
 * Inherits the properties of LinkedList, with an additional two methods that
 * provides LIFO functionality, by adding to the removing the most recently added node to a list.
 * 
 */

class LIFO_Queue extends LinkedList {

    public LIFO_Queue() {
        super();
    }

    //Pushes an element onto the front of the queue. (creating a new head) 
    //This is opposed to the inherited append() which adds a Node to the very end of the queue.
    public void push(int data) {
        Node newNode = new Node(data);
        newNode.setNext(this.getHead());
        this.head = newNode;
    }

    // Removes the most recently added node from the Queue and returns it.
    public int pop() {
        if (this.getHead() == null)
        {
            return -1;
        } 

        int returnData = this.getHead().getData();
        this.head = this.getHead().getNextNode();
        return returnData;
    }
    //Helper function for DFS implementation
    public boolean isEmpty() {
        return this.getHead() == null;
    }
}
