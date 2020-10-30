/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};
*/

class Solution {
    public Node flatten(Node head) {
        Node ret = new Node();
        Node current = new Node();
        ret.next = current;
        
        if(head == null) {
            return null;
        }
        
        process(head, current);
        
        return ret.next;
    }
    
    public void process(Node node, Node current) {
        
        if(node != null) {
            current.val = node.val;
        }

        Node prev = current;

        if(node.child != null) {
            current.next = new Node();
            current = current.next;
            current.prev = prev;
            process(node.child, current);
        }

        // ** current node is create prev & next node by recursive method :: call by value (reference value)
        while(current.next != null) {
            current = current.next;
        }

        prev = current;

        if(node.next != null) {
            current.next = new Node();
            current = current.next;
            current.prev = prev;
            process(node.next, current);
        }
    }
}