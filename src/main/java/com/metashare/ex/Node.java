package com.metashare.ex;

public abstract class Node
{
    public  String text;
    protected Node(){};
    protected Node(String text)
    {
	this.text = text;
    }

	public abstract void run(IRunner runner);
}

class Literal extends Node
{
	public double value ;
	public Literal (double val)
    {
        this.value = val;
    }
    @Override
	public  void run(IRunner runner) { runner.Run(this); }
}

abstract class Op extends Node
{
	public Node Left ;
	public Node Right ;
	public Op(Node left, Node right)
{
        this.Left = left;
        this.Right  = right;
    }
}

class Add extends Op
{
	public Add (Node left, Node right){
	    super(left, right);
    }
	@Override
	public void run(IRunner runner) { runner.Run(this); }
}

class Sub extends  Op
{
	public Sub (Node left, Node right)  {
        super(left, right);
    }
	@Override
	public void run(IRunner runner) { runner.Run(this); }
}
