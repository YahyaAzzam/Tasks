import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

class Stack {
    private static int size = 0;
    private static Node top = null;
    public static class Node{
        private Node next;
        private Object value;
        public void setValue(Object value){this.value = value;}
        public void setNext(Node next){this.next = next;}
        public Object getValue(){return value;}
        public Node getNext(){return next;}
        public void Node(Object value, Node next){
            this.value = value;
            this.next = next;
        }
    }
    public Object pop(){
        if(size==0){
            System.out.print("Error");
            System.exit(0);
        }
        Object x = top.getValue();
        top.setValue(null);
        top = top.getNext();
        size--;
        return x;
    }
    public Object peek(){return top.getValue();}
    public void push(Object element){
        Node x = new Node();
        x.Node(element,top);
        top = x;
        size++;
    }
    public boolean isEmpty(){return size==0;}
    public int size(){return size;}
    public static void printStack() {
        Node printing = top;
        while (printing != null) {
            System.out.print(printing.getValue());
            printing = printing.getNext();
        }
    }
}



public class Evaluator {
    public static void error(){
        System.out.print("Error");
        System.exit(0);
    }
    public String infixToPostfix(String expression){
        Object[] x = new Object[expression.length()];
        Stack stack = new Stack();int size = expression.length();
        char f = expression.charAt(0), g='^';int j = 0,a = 0,l = 0;String ans = new String();
        if(f=='*'||f=='/'||f=='^'){error();}
        for(int i=0;i<expression.length();i++){
            f = expression.charAt(i);
            if(i<expression.length()-1&&f==expression.charAt(i+1)&&f!='-'&&f!='+'&&f!='('&&f!=')'){error();}
            if(f=='-'||f=='+'||f=='*'||f=='/'||f=='^'||f==')'){
                if(i>1){g=expression.charAt(i-2);}
                if(i==expression.length()-1&&f!=')'){error();}
                if((f=='+'||f=='-')&&f==expression.charAt(i+1)){
                    g = i==0?0:expression.charAt(i-1);
                    i++;size--;
                    if(i==1||g=='^'||g=='*'||g=='/'){size--;continue;}
                    f='+';
                }
                else if((f=='+'&&expression.charAt(i+1)=='-')||(f=='-'&&expression.charAt(i+1)=='+')){f='-';i++;size--;}
                if(f=='^'){
                    if(g=='+'||g=='-'||g=='*'||g=='/'){j--;}
                }
                else if(f=='*'||f=='/'){
                    if(g=='+'||g=='-'){j--;}
                }
                if(j==2||f==')'){
                    if(!(stack.isEmpty()||stack.peek().equals('('))){
                        x[a++]=stack.pop();
                        j--;
                    }
                }
                if(f==')'){
                    while(l>0&&!stack.isEmpty()&&!stack.peek().equals('(')){x[a++]=stack.pop();}
                    l--;size--;j=2;
                    if(!stack.isEmpty()){stack.pop();}
                }
                else if(i!=expression.length()-1||f!='+'){stack.push(f);j=1;}
            }
            else if(f=='('){stack.push(f);j=0;l++;size--;}
            else{
                x[a++]=f;
                j++;
            }
        }if(l!=0){error();}
        while(a<size){x[a++]=stack.pop();}
        for(int i=0;i<a;i++){ans = ans + x[i].toString();}
        return ans;
    }
    public int evaluate(String expression){
        Stack stack = new Stack();if(!input.hasNextLine()){error();}
        int a = Integer.parseInt(input.nextLine().replaceAll("a=","")),
        b = Integer.parseInt(input.nextLine().replaceAll("b=","")),
        c = Integer.parseInt(input.nextLine().replaceAll("c=","")), help=0;
        for(int i=0;i<expression.length();i++){
            switch(expression.charAt(i)){
                case 'a':
                    stack.push(a);break;
                case 'b':
                    stack.push(b);break;
                case 'c':
                    stack.push(c);break;
                case '-':
                    if(stack.size()==1){stack.push(-(int)stack.pop());}
                    else{stack.push(-((int)stack.pop()-(int)stack.pop()));}break;
                case '+':
                    stack.push((int)stack.pop()+(int)stack.pop());break;
                case '*':
                    stack.push((int)stack.pop()*(int)stack.pop());break;
                case '/':
                    help = (int) stack.pop();
                    stack.push((int)((int)stack.pop()/help));break;
                case '^':
                    help = (int) stack.pop();
                    stack.push((int)Math.pow((int)stack.pop(),help));break;
            }
        }
        return (int)stack.pop();
    }
    public static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        Evaluator ans = new Evaluator();
        if(!input.hasNextLine()){error();}
        String eq = ans.infixToPostfix(input.nextLine());
        if(eq.length()==0){error();}
        System.out.println(eq);
        System.out.print(ans.evaluate(eq));
    }
}
