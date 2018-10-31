package raidsrotationgenerator;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class will find a given raids rotation based off of the bosses
 * that can be seen on the map
 * @author Austin Lee
 */
public class FindRaid<E extends Comparable<E>> {
    //Implementation of a linked list
    private Node<E> head, tail;
    //Number of elements in the linked list
    private int count;
    class Node<E> {
        //Data is the generic type
        E data;
        //Index associated with each node. [1, n]
        int key;
        //Check to confirm that we've visited a node to avoid infinity loop.
        boolean checked;
        //Doubly linked
        Node<E> next;
        Node<E> previous;
        Node(E data, int key) {
            this.data = data;
            this.key = key;
        }
    }
    /**
     * Inserts the items into the linked list. 
     * @param input array of the bosses in a specific order
     */
    public FindRaid(E[] input) {
        insertRotation(input);
    }
    /***
     * Insert these elements into the doubly linked list.
     * @param input array of the generic type.
     */
    private void insertRotation(E[] input) {
        for(int i = 0; i < input.length; i++)
            insertNode(input[i]);
    }
    /***
     * Inserts the data into the doubly linked list
     * @param data information to insert
     */
    private void insertNode(E data) {
        //Linked list is empty
        if(head == null) {
            head = new Node<>(data, count++);
            tail = head;
            head.next = tail;
            tail.next = head;
            head.previous = tail;
            tail.previous = head;
        } else {
            Node<E> temp = new Node<>(data, count++);
            tail.next = temp;
            temp.previous = tail;
            tail = temp;
            tail.next = head;
            head.previous = tail;
        }
    }
    /***
     * Remove (head) each element of the queue if found in the linked list.  
     * Record the index associated with the boss
     * @param bosses input bosses seen in the raid.
     */
    public void searchRaidCW(Queue <E> bosses){
        Queue<Integer> tmp = new LinkedList<>();
        Node<E> current = head;
        current.checked = false;
        current = current.next;
        //Initialize checked to false for every node.
        while(current != head){
            current.checked = false;
            current = current.next;
        }
        current = head;
        //finds the node to start at.
        while(current.next != head && current.data.compareTo(bosses.peek()) != 0){
            current = current.next;
        }
        //Iterates through the linked list looking for the indices of the bosses
        //from bosses
        //Everytime a node is visited, checked = true even if it isn't a match.
        while(!bosses.isEmpty()){
            if(!current.checked){
                if(current.data.compareTo(bosses.peek()) == 0){
                    tmp.add(current.key);
                    String boss = bosses.remove().toString();
                }
            } else return;
            current.checked = true;
            current = current.next;
        }
        System.out.println(buildRaidCW(tmp));
    }
    
        public void searchRaidCCW(Queue <E> bosses){
        Queue<Integer> tmp = new LinkedList<>();
        Node<E> current = head;
        current.checked = false;
        current = current.next;
        while(current != head){
            current.checked = false;
            current = current.next;
        }
        current = head;
        while(current.next != head && current.data.compareTo(bosses.peek()) != 0){
            current = current.next;
        }
        while(!bosses.isEmpty()){
            if(!current.checked){
                if(current.data.compareTo(bosses.peek()) == 0){
                    tmp.add(current.key);
                    String boss = bosses.remove().toString();
                }
            } else return;            
            current.checked = true;
            current = current.previous;
        }
        System.out.println(buildRaidCCW(tmp));
    }
    /***
     * 
     * @param id
     * @return 
     */
    private String buildRaidCW(Queue <Integer> id){
        StringBuilder sb = new StringBuilder();
        ArrayList<E> possibleRaid = new ArrayList<>();
        Node<E> currentNode = head;
        while(currentNode.key != id.peek())
            currentNode = currentNode.next;
        while(!id.isEmpty()){
            sb.append(String.format(" %s", currentNode.data));
            possibleRaid.add(currentNode.data);
            if(currentNode.key == id.peek()){
                int idNumber = id.remove();
            }
            currentNode = currentNode.next;
        }
        System.out.printf("Rotation (%d bosses):", possibleRaid.size());
        return sb.toString();
//        return possibleRaid.toString();
    }
    
    private String buildRaidCCW(Queue <Integer> id){
        StringBuilder sb = new StringBuilder();
        ArrayList<E> possibleRaid = new ArrayList<>();
        Node<E> currentNode = head;
        while(currentNode.key != id.peek())
            currentNode = currentNode.previous;
        while(!id.isEmpty()){
            sb.append(String.format(" %s", currentNode.data));
            possibleRaid.add(currentNode.data);
            if(currentNode.key == id.peek()){
                int idNumber = id.remove();
            }
            currentNode = currentNode.previous;
        }
        System.out.printf("Rotation (%d bosses):", possibleRaid.size());
        return sb.toString();
//        return possibleRaid.toString();
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(head != null){
            sb.append(String.format("%s", head.data));
            Node<E> currentNode = head.next;
            while(currentNode != head){
                sb.append(String.format(" %s", currentNode.data));
                currentNode = currentNode.next;
            }
        }   
        return sb.toString();
    }
}
