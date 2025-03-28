/**
 * Beau Albritton
 * 
 * 
 * FIFO Queue Class 
 * 
 *  
 * Program that attempts to implement a proper FIFO Queue data structure. 
 * 
 * Inherits the properties of LinkedList, with an additional method that
 * provides FIFO functionality, removing the first node in a given linked list 
 * 
 */


class FIFO_Queue extends LinkedList {
    public FIFO_Queue() {
        super();
    }

    // Removes and returns the first node (front of queue,)
    public int pop() {
        //Handling null node
        if (this.getHead() == null){
            return -1;
        }
        int returnData = this.getHead().getData();
        // Move head to the next node
        this.head = this.getHead().getNextNode();  
        return returnData;
    }
}