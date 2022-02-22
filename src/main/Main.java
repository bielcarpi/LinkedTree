package main;

import controller.Controller;
import model.Graph;
import view.View;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try{
            View v = new View(); //Creating View
            Graph g = new Graph(); //Creating Model
            Controller c = new Controller(v, g); //Creating the controller

            c.start(); //Start the application
        }catch(IOException e){
            System.err.println("Error. Can't start the application.");
            e.printStackTrace();
        }
    }
}
