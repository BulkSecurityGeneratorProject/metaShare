package com.metashare.ex;

public interface IRunner
{
    void Run(Literal node);
	void Run(Variable node);
	void Run(Add node);
	void Run(Sub node);
}
