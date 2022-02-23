package controller;

import model.UserGraph;
import model.interfaces.GraphNode;
import model.utilities.ArrayList;
import model.utilities.Queue;
import model.utilities.Tuple;
import view.View;

public class Controller {

    private UserGraph graph;
    private View view;

    public Controller(View v, UserGraph g){
        view = v;
        graph = g;
    }

    public void start(){
        infiniteLoop:
        while(true) {
            view.printStartMenu();
            int option;
            do {
                option = view.askForInteger();
                switch (option) {
                    case 1:
                        // Execute Followers Option
                        followersOption();
                        continue infiniteLoop;
                    case 2:
                        continue infiniteLoop;
                    case 3:
                        continue infiniteLoop;
                    case 4:
                        continue infiniteLoop;
                    case 5:
                        view.printMessage("Fins aviat!");
                        return;
                    default:
                        view.printMessage("Error. The value ranges are from [1, 5]");
                        break;
                }
            } while (option < 1 || option > 6);
        }
    }

    public void followersOption(){
        infiniteLoop:
        while(true){
            view.printFollowersMenu();
            do{
                char option = view.askForChar();
                switch (option){
                    case 'A':
                        exploreNetwork();
                        continue infiniteLoop;
                    case 'B':
                        getRecommendation();
                        continue infiniteLoop;
                    case 'C':
                        continue infiniteLoop;
                    case 'D':
                        continue infiniteLoop;
                    case 'E':
                        return;
                    default:
                        view.printMessage("Error. The value ranges are from [A, E]");
                        break;
                }
            }while(true); //While the input is not well-formatted
        }
    }

    private void exploreNetwork(){
        Queue<GraphNode> q = graph.getNodesBfs();
        if(!q.isEmpty()){
            view.printMessage();
            view.printMessage("L'usuari que segueix mes comptes es:");
            view.printMessage();
            view.printMessage(q.remove().toPrettyString());
        }

        while(!q.isEmpty()){
            GraphNode gn = q.remove();
            view.printMessage();
            if(gn == null) //If the node is null, it means a change in graph level
                view.printMessage("Aquests son els comptes que segueixen:");
            else
                view.printMessage(gn.toPrettyString());
        }
    }

    private void getRecommendation(){
        boolean idValid = false;
        int id;
        do{
            view.printMessage("Entra el teu identificador:");
            id = view.askForInteger();
            if(graph.getUser(id) != null)
                idValid = true;
            else
                view.printMessage("Identificador erroni...");
        } while(!idValid);

        ArrayList<Tuple<GraphNode, String>> recommendations = graph.getFollowRecommendation(id);

        if(recommendations == null || recommendations.isEmpty()){
            view.printMessage("No hem trobat comptes que puguis seguir :(");
            view.printMessage();
            return;
        }

        view.printMessage("Potser t'interessa seguir els seguents comptes:");
        view.printMessage();

        while(!recommendations.isEmpty()){
            Tuple<GraphNode, String> t = recommendations.get(0);
            view.printMessage(t.getFirst().toPrettyString());
            view.printMessage(t.getLast());
            view.printMessage();

            recommendations.remove(0);
        }
    }
}
