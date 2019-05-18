package com.metashare.ex.part2;

public class Node {
    String data = null; //链表存储的数据
    Node next = null; //下一个节点

    public Node(String data) {
        this.data = data;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public String getData() {
        return data;
    }
}

class Test {

    public static void getDataByLoop(Node node) {
        while (node != null) {
            System.out.println(node.getData());
            node = node.getNext();
        }
    }

    public static void getDataByRecursion(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.getData());
        getDataByRecursion(node.getNext());
    }

    /*public static void main(String[] args) {
        Node root = new Node("头结点");
        Node node01 = new Node("子节点01");
        Node node02 = new Node("子节点02");
        root.setNext(node01); //将节点连接起来
        node01.setNext(node02);
        System.out.println("使用while循环遍历链表");
        getDataByLoop(root);
        System.out.println("使用递归遍历链表");
        getDataByRecursion(root);
    }*/
}
