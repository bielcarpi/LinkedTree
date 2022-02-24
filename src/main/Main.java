package main;

import controller.Controller;
import model.UserGraph;
import view.View;

import java.io.IOException;

public class Main {

    private static final String FILE_NAME = "graphXS.paed";

    public static void main(String[] args){
        try{
            View v = new View(); //Creating View
            UserGraph g = new UserGraph(FILE_NAME); //Creating Model
            Controller c = new Controller(v, g); //Creating the controller

            c.start(); //Start the application
        }catch(IOException e){
            System.err.println("Error. Can't start the application.");
            e.printStackTrace();
        }
    }
}
