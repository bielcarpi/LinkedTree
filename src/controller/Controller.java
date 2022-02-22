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
        int option;
        do{
            option = view.askForInteger();
            switch (option){
                case 1:
                    // Execute Grafs Option
                    char grafOption = view.askForChar();

                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break:
                case 5:
                    break;
                default:
                    view.printMessage("Error. The value ranges are from [1, 5]");
                    break;
            }
        }while(option < 1 || option > 6);
    }
}
