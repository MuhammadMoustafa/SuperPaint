/**
 * @author Benson Pan
 * 
 * @version April 8, 2015
 * 
 * LinkedList Data Structure
 */
// Throws java.lang.CharSequence cannot be resolved error on certain compilers (i.e. no error on JDK 6.0_14 at school)
class LinkedList<T> {
    private int numberOfNodes = 0; 
    private ListNode<T> front = null;
    private ListNode<T> tails = null;
     
    // Returns true if the linked list has no nodes, or false otherwise.
    public boolean isEmpty() {
        return (front == null);
    }
     
    // Deletes all of the nodes in the linked list.
    // Note: ListNode objects will be automatically garbage collected by JVM.
    public void makeEmpty() {
        front = null;
        numberOfNodes = 0;
    }
     
    // Returns the number of nodes in the linked list
    public int size() {
        return numberOfNodes;
    }
    
    // Adds a node to the front of the linked list.
    public void addFront( T element ) {
        front = new ListNode<T>( element, front );
        numberOfNodes++;
        
        if (size() == 1)
            tails = front;
    }
    
    // Returns a reference to the data in the first node, or null if the list is empty.
    public T first() {
        if (isEmpty()) 
            return null;
        
        return front.getData();
    }
    
// Removes a node from the front of the linked list (if there is one).
// Returns a reference to the data in the first node, or null if the list is empty.
    @SuppressWarnings("unchecked")
    public T removeFront() {
        T tempData;
        
        if (isEmpty()) 
            return null;
        
        tempData = front.getData();
        front = front.getNext();
        numberOfNodes--;
        return tempData;
    }
    
// Returns true if the linked list contains a certain element, or false otherwise.
    @SuppressWarnings("unchecked")
    public boolean contains( T key ) {
        ListNode<T> searchNode;
        searchNode = front;
        while ( (searchNode != null) && (!key.equals(searchNode.getData())) ) {
            searchNode = searchNode.getNext();
        }
        return (searchNode != null);
    }
    
// Return String representation of the linked list.
    @SuppressWarnings("unchecked")
    public String toString() {
        ListNode<T> node;
        String linkedList = "FRONT ==> ";
        
        node = front;
        while (node != null) {
            linkedList += (node == front) ? "front" : "";
            linkedList += (node == tails) ? "tails" : "";
            linkedList += "[ " + ((node.getData() == null) ? "" : node.getData()) + " ] ==> ";
            node = node.getNext();
        }
        
        return linkedList + "NULL";
    }
    
// Insert a node in the list with a given key value
    @SuppressWarnings("unchecked")
    public void insert( Comparable key ) {
        ListNode<T> before = null;
        ListNode<T> after = front;
        ListNode<T> newNode;        
        
        // Traverse the list to find the ListNode before and after our insertion point.
        while ((after != null) && (key.compareTo(after.getData()) > 0)) {
            before = after;
            after = after.getNext();
        }
        
        // Create the new node with link pointing to after
        newNode = new ListNode<T>( (T)key, after);
        
        // Adjust front of the list or set before's link to point to new node, as appropriate
        if (before == null) {
            front = newNode;
        }
        else {
            before.setNext(newNode);
        }
        numberOfNodes++;
    }
    
    // Delete a given key value from the list
    public boolean delete( Comparable key ) 
    {
        ListNode<T> before = null;
        ListNode<T> after = front;     
        
        // Traverse the list to find the ListNode and keeps the node before it
        while ((after != null) && (!key.equals(after.getData()))) {
            before = after;
            after = after.getNext();
        }
        
        // delete if found
        if (after != null) {
            before.setNext(after.getNext());
            return true;
        }
        else {
            return false;
        }
    }
    
    // Add an element to the end of the list
    public void addEnd ( T element )
    {
        tails.setNext(new ListNode<T>(element));
        
        if (front == null)
            front = tails.getNext();
        
        tails = tails.getNext();
        numberOfNodes++;
    }
    
    // Remove and element of the end of the list
    public T removeEnd()
    {
        T remove;
        ListNode<T> node = front;
        
        if (isEmpty()) 
            return null;
        // clear list if only one element remains
        if (size() == 1){
            remove = front.getData();
            makeEmpty();
            return remove;
        }
        
        // searches for second last node
        while (node.getNext() != tails){
            node = node.getNext();
        }  
        
        ListNode<T> temp = node.getNext();
        remove = temp.getData();
        tails = node;
        tails.setNext(null);
        numberOfNodes--;
        return remove;
    }
}

