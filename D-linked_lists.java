import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class DoubleLinkedList {
	private static boolean started = false;
    private static Node head = null;
    private static int size = 0;
    private static Node tail = null;

    public void setStarted(){
        started = true;
    }

    public static class Node {
        private Node previous;
        private Object value;
        private Node next;

        public Node(Node previous,Object value, Node next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }

        public Node getPrevious(){
            return previous;
        }

        public Object getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    public void add(int index, Object element) {
        if (index >= size || index < 0)
            System.out.print("Error");
        else {
            Node n = new Node(null, element, null);
            Node before = head;
            if (index == 0) {
                n.setNext(head);
                head.setPrevious(n);
                head = n;
            } else {
                for (int i = 1; i < index; i++)
                    before = before.getNext();
                n.setNext(before.getNext());
                before.getNext().setPrevious(n);
                before.setNext(n);
            }
            size++;
            printList();
        }
    }

    public void add(Object element) {
        if (size == 0) {
            head = new Node(null, element, null);
            tail = head;
        } else {
            Node n = new Node(tail, element, null);
            tail.setNext(n);
            tail = n;
            if (size == 1) {
                head.setNext(n);
            }
        }
        size++;
        if(started)
            printList();
    }

    public Object get(int index) {
        if(index<0||index>=size){
            System.out.print("Error");
            return null;
        }
        Node wanted = head;
        if(index<size/2){
            for (int i = 1; i <= index; i++) {
                wanted = wanted.getNext();
            }
        }
        else{
            wanted = tail;
            for (int i = size-1; i > index; i--) {
                wanted = wanted.getPrevious();
            }
        }
        System.out.print(wanted.getValue());
        return wanted.getValue();
    }

    public void set(int index, Object element) {
        if (index >= size || index < 0)
            System.out.print("Error");
        else if (index == 0){
            head.setValue(element);
            printList();
        }
        else {
            Node after = head.getNext();
            if(index<size/2){
                for (int i = 1; i < index; i++) {
                    after = after.getNext();
                }
            }
            else{
                after = tail;
                for (int i = size-1; i > index; i--) {
                    after = after.getPrevious();
                }
            }
            after.setValue(element);
            printList();
        }
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            head.setValue(null);
            head = head.getNext();
        }
        size = 0;
        printList();
    }

    public boolean isEmpty() {
        String empty = String.valueOf(size==0);
        System.out.print(empty.substring(0, 1).toUpperCase() + empty.substring(1));
        return size==0;
    }

    public void remove(int index) {
        if (index >= size || index < 0)
            System.out.print("Error");
        else if (index == 0) {
            if (head == tail)
                tail = null;
            head = head.next;
            head.getPrevious().setNext(null);
            head.setPrevious(null);
            printList();
            size--;
        }else if(index==size-1){
            tail = tail.getPrevious();
            tail.getNext().setPrevious(null);
            tail.setNext(null);
            printList();
            size--;
        }else{
            Node before = head, removal = head.getNext();
            if(index<size/2){
                for (int i = 1; i < index; i++) {
                    before = removal;
                    removal = removal.getNext();
                }
            }else{
                before = tail.getPrevious();removal = tail;
                for (int i = size-1; i > index; i--) {
                    removal = before;
                    before = before.getPrevious();
                }
            }
            before.setNext(removal.getNext());
            before.getNext().setPrevious(before);
            removal.setNext(null);
            removal.setPrevious(null);
            removal.setValue(null);
            printList();
            size--;
        }
    }

    public int size() {
        System.out.print(size);
        return size;
    }

    public ILinkedList sublist(int fromIndex, int toIndex){
        if(fromIndex<0||fromIndex>toIndex||toIndex>=size){
            System.out.print("Error");
            return null;
        }
        started = false;
        DoubleLinkedList x = new DoubleLinkedList();
        Node h = head;
        for(int i=0;i<fromIndex;i++) {
            h = h.getNext();
        }
        for(int i=fromIndex;i<=toIndex;i++) {
            x.add(h.getValue());
            h = h.getNext();
        }
        printList(fromIndex,toIndex);
        started = true;
        return x;
    }

    public boolean contains(Object o) {
        Node search = head;
        while (search != null) {
            if (search.getValue().equals(o)){
                System.out.print("True");
                return true;
            }
            search = search.getNext();
        }
        System.out.print("False");
        return false;
    }

    public static void printList() {
        System.out.print("[");
        Node printing = head;
        while (printing != null) {
            System.out.print(printing.getValue());
            if (printing != tail)
                System.out.print(", ");
            printing = printing.getNext();
        }
        System.out.print("]");
    }

    public static void printList(int first, int last){
        System.out.print("[");
        Node printing = head;
        for(int i=0;i<first;i++)
            printing = printing.getNext();
        for(int i=first;i<=last;i++){
            System.out.print(printing.getValue());
            if(i != last){
                System.out.print(", ");
                printing = printing.getNext();
            }
        }
        System.out.print("]");
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);DoubleLinkedList one = new DoubleLinkedList();int index;
        String[] n = input.nextLine().replaceAll("\\[|\\]","").split(", ");
        if(!(n.length==1 && n[0].isEmpty())){
            for(int i=0;i<n.length;i++){
                one.add(n[i]);
                n[i] = null;
            }
        }
        started = true;
        String order = input.nextLine();
        switch(order){
            case "add":
                one.add(input.nextLine());
                break;
            case "addToIndex":
                index = input.nextInt();
                one.add(index,input.next());
                break;
            case "get":one.get(input.nextInt());
                break;
            case "set":
                index = input.nextInt();
                one.set(index,input.next());
                break;
            case "clear":
                one.clear();
                break;
            case "isEmpty":
                one.isEmpty();
                break;
            case "remove":
                one.remove(input.nextInt());
                break;
            case "size":
                one.size();
                break;
            case "sublist":
                one.sublist(input.nextInt(),input.nextInt());
                break;
            case "contains":
                one.contains(input.nextLine());
                break;
            default:
                System.out.print("Error");
        }
    }
}
