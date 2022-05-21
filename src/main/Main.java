package main;

import controller.Controller;
import model.Model;
import view.View;

import java.io.IOException;

public class Main {

    //Filenames
    private static final String FILE_GRAPH_NAME = "graphS.paed";
    private static final String FILE_DRAMA_NAME = "dagS.paed";
    private static final String FILE_TREE_NAME = "treeS.paed";
    private static final String FILE_R_TREE_NAME = "rtreeS.paed";
    private static final int R_TREE_ORDER = 25;
    private static final String FILE_HASHMAP_NAME = "tablesS.paed";

    public static void main(String[] args){
        try{
            View v = new View(); //Creating View
            Model m = new Model(FILE_GRAPH_NAME, FILE_DRAMA_NAME, FILE_TREE_NAME, FILE_R_TREE_NAME, R_TREE_ORDER, FILE_HASHMAP_NAME); //Creating Model

            Controller c = new Controller(v, m); //Creating the controller

            c.start(); //Start the application
        }catch(IOException e){
            System.err.println("Error while reading datasets. Can't start the application.");
            e.printStackTrace();
        }
    }
}
