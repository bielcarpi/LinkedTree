package main;

import controller.Controller;
import model.Model;
import view.View;

import java.io.IOException;

public class Main {

    //Filenames
    private static final String FILE_GRAPH_NAME = "graphXL.paed";
    private static final String FILE_DRAMA_NAME = "dagXXL.paed";
    private static final String FILE_TREE_NAME = "treeS.paed";
    private static final String FILE_R_TREE_NAME = "rtreeM.paed";
    private static final int R_TREE_ORDER = 3;

    public static void main(String[] args){
        try{
            View v = new View(); //Creating View
            Model m = new Model(FILE_GRAPH_NAME, FILE_DRAMA_NAME, FILE_TREE_NAME, FILE_R_TREE_NAME, R_TREE_ORDER); //Creating Model

            Controller c = new Controller(v, m); //Creating the controller

            c.start(); //Start the application
        }catch(IOException e){
            System.err.println("Error. Can't start the application.");
            e.printStackTrace();
        }
    }
}
