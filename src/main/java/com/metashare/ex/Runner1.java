package com.metashare.ex;

public class Runner1 implements IRunner {

    public static Runner1 Instance = new Runner1();
    @Override
    public void Run(Literal node) {
        System.out.println(node.value);
    }
    @Override
    public void Run(Variable node) {
        System.out.println(node.text);
    }
    @Override
    public void Run(Add node) {
        System.out.println("add + ");
        node.Left.run(this);
        node.Right.run(this);
    }
    @Override
    public void Run(Sub node) {
        System.out.println("add - ");
        node.Left.run(this);
        node.Right.run(this);
    }
}
