package com.metashare.ex;

public class Variable extends Node
{
	public String Name ;
    public Variable (String name)
    {
        this.Name = name;
    }


    @Override
	public void run(IRunner runner)
    {
        runner.Run(this);
    }
}
