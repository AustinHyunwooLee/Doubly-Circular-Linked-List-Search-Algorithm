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
    private Node<E> head, tail;
    private int count;
    private E[] input;
    class Node<E> {
        E data;
        int key;
        boolean checked;
        Node<E> next;
        Node<E> previous;
        Node(E data, int key) {
            this.data = data;
            this.key = key;
        }
    }
    public FindRaid() {
    }
    /**
     * 
     * @param input array of the bosses in a specific order
     */
    public FindRaid(E[] input) {
        insertRotation(input);
    }
    
    private void insertRotation(E[] input) {
        for(int i = 0; i < input.length; i++)
            insertNode(input[i]);
    }
    
    private void insertNode(E data) {
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
    
    public void searchRaidCW(Queue <E> bosses){
        Queue<Integer> tmp = new LinkedList<>();
        Node<E> current = head;
        while(current.next != head){
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
    //                System.out.printf("Found %s\n", boss);
                }
            } else {
                System.out.println("Not Found");
                return;
            }
            current.checked = true;
            current = current.next;
        }
        
        System.out.println(buildRaidCW(tmp));
    }
    
        public void searchRaidCCW(Queue <E> bosses){
        Queue<Integer> tmp = new LinkedList<>();
        Node<E> current = head;
        while(current.next != head){
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
    //                System.out.printf("Found %s\n", bosses);
                }
            } else {
                System.out.println("Not Found");
                return;            
            }
            if(current.data.compareTo(bosses.peek()) == 0){
                if(current.checked) {
                    System.out.println("Not Found");
                    return;
                }
                tmp.add(current.key);
                String boss = bosses.remove().toString();
//                System.out.printf("Found %s\n", bosses);
            }
            current.checked = true;
            current = current.previous;
        }
        System.out.println(buildRaidCCW(tmp));
    }
    
    private String buildRaidCW(Queue <Integer> id){
        ArrayList<E> possibleRaid = new ArrayList<>();
        Node<E> currentNode = head;
        while(currentNode.key != id.peek())
            currentNode = currentNode.next;
        while(!id.isEmpty()){
            possibleRaid.add(currentNode.data);
            if(currentNode.key == id.peek()){
                int idNumber = id.remove();
//                System.out.printf("Found %d\n", idNumber);
            }
            currentNode = currentNode.next;
        }
        return possibleRaid.toString();
    }
    
    private String buildRaidCCW(Queue <Integer> id){
        ArrayList<E> possibleRaid = new ArrayList<>();
        Node<E> currentNode = head;
        while(currentNode.key != id.peek())
            currentNode = currentNode.previous;
        while(!id.isEmpty()){
            possibleRaid.add(currentNode.data);
            if(currentNode.key == id.peek()){
                int idNumber = id.remove();
//                System.out.printf("Found %d\n", idNumber);
            }
            currentNode = currentNode.previous;
        }
        return possibleRaid.toString();
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
