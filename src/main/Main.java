package main;

import controller.Controller;
import model.Model;
import view.View;

import java.io.IOException;

public class Main {

    private static final String FILE_GRAPH_NAME = "graphXL.paed";
    private static final String FILE_DRAMA_NAME = "dagXXL.paed";
    private static final String FILE_TREE_NAME = "treeS.paed";

    public static void main(String[] args){
        try{
            View v = new View(); //Creating View
            Model m = new Model(FILE_GRAPH_NAME, FILE_DRAMA_NAME, FILE_TREE_NAME); //Creating Model

            Controller c = new Controller(v, m); //Creating the controller

            c.start(); //Start the application
        }catch(IOException e){
            System.err.println("Error. Can't start the application.");
            e.printStackTrace();
        }
    }
}
