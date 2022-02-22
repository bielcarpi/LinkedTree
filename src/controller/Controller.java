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
                    // Execute Followers Option
                    followersOption();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    view.printMessage("Good bye!");
                    break;
                default:
                    view.printMessage("Error. The value ranges are from [1, 5]");
                    break;
            }
        }while(option < 1 || option > 6);
    }

    public void followersOption(){
        boolean correctOption;

        do{
            char grafOption = view.askForChar();
            correctOption = false;

            switch (grafOption){
                case 'A':
                    //Execute net exploring
                    correctOption = true;
                    break;
                case 'B':
                    correctOption = true;
                    break;
                case 'C':
                    correctOption = true;
                    break;
                case 'D':
                    correctOption = true;
                    break;
                case 'E':
                    correctOption = true;
                    break;
                default:
                    view.printMessage("Error. The value ranges are from [A, E]");
                    break;
            }
        }while(!correctOption);

    }

}
