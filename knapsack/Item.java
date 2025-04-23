/*
 * Beau Albritton
 * Item.java
 * 
 * This class is a simple util for Knapsack class. 
 * Wraps three values (index value weight) to correspond to a specific Item object.
 */
public class Item 
{
    public int index, value, weight; 

    public Item(int index, int value, int weight){
        this.index = index;
        this.value = value;
        this.weight = weight;
    }

    public String toString()
    {
        return "i: " + this.index + " w: " + this.weight + " v: "+ this.value;
    }
}