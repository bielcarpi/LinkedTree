package controller;

import model.Graph;
import view.View;

public class Controller {

    private Graph graph;
    private View view;

    public Controller(View v, Graph g){
        view = v;
        graph = g;
    }

    public void start(){
        view.printStartMenu();
    }
}
