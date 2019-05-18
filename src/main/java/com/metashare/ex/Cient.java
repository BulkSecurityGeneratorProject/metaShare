package com.metashare.ex;


public class Cient
{
   /* public static void main(String[] args) {

        Node expression = new Mul(
            new Add(new Literal(99), new Literal(11)),
            new Div(new Literal(1000), new Sub(new Variable("a"), new Variable ("b"))));
        expression.run(Runner1.Instance);
        expression.run(Runner2.Instance);
    }*/
    private static class Mul extends Node {
        public Mul(Add add, Div div) {
        }
        @Override
        public void run(IRunner runner) {
        }
    }
    private static class Div extends Node{
        public Div(Literal literal, Sub sub) {
        }
        @Override
        public void run(IRunner runner) {

        }
    }
}

