package main;

import controller.Controller;
import model.Graph;
import view.View;

public class Main {
    public static void main(String[] args){
        View v = new View(); //Creating View
        Graph g = new Graph(); //Creating Model
        Controller c = new Controller(v, g); //Creating the controller

        c.start(); //Start the application
    }
}
