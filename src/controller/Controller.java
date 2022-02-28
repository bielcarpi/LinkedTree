package controller;

import model.Recommendation;
import model.UserGraph;
import model.interfaces.GraphNode;
import model.utilities.Queue;
import model.utilities.Stack;
import view.View;

import java.io.IOException;

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
                        contextualizeDrama();
                        continue infiniteLoop;
                    case 'D':
                        easyNetworking();
                        continue infiniteLoop;
                    case 'E':
                        return;
                    default:
                        view.printMessage("Error. The value ranges are from [A, E]");
                        break;
                }
            } while(true); //While the input is not well-formatted
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
            view.printMessageWithoutLine("Entra el teu identificador: ");
            id = view.askForInteger();
            if(graph.getUser(id) != null)
                idValid = true;
            else
                view.printMessage("Identificador erroni...");
        } while(!idValid);

        Recommendation[] recommendations = graph.getFollowRecommendation(id);

        if(recommendations == null || recommendations.length == 0){
            view.printMessage("No hem trobat comptes que puguis seguir :(");
            view.printMessage();
            return;
        }

        view.printMessage("Potser t'interessa seguir els seguents comptes:");
        view.printMessage();

        for(Recommendation r: recommendations){
            view.printMessage(r.toString());
            view.printMessage();
        }
    }

    private void contextualizeDrama() {
        try {
            view.printMessage("Llegint el dataset de drama...");
            UserGraph graphDrama = new UserGraph("dagXS.paed"); //Read the drama dataset
            Stack<GraphNode> nodesTopo = graphDrama.getNodesTopo();

            view.printMessage("Pots posar-te al dia de la polèmica en el següent ordre:");
            while(!nodesTopo.isEmpty()){
                GraphNode n = nodesTopo.remove();
                view.printMessage(n.dramaToString());
                if(!nodesTopo.isEmpty()) view.printMessage("\t↓");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void easyNetworking() {
        boolean idValid = false;
        int idUser, idUserToFollow;

        do{
            view.printMessageWithoutLine("Entra el teu identificador: ");
            idUser = view.askForInteger();
            if(graph.getUser(idUser) != null)
                idValid = true;
            else
                view.printMessage("Identificador erroni...");
        } while(!idValid);

        idValid = false;
        do{
            view.printMessageWithoutLine("Entra l'identificador de l'altre usuari: ");
            idUserToFollow = view.askForInteger();
            if(graph.getUser(idUserToFollow) != null)
                idValid = true;
            else
                view.printMessage("Identificador erroni...");
        } while(!idValid);

        view.printMessage("Trobant la cadena de contactes óptima...\n");
        Stack<GraphNode> path = graph.getDijkstra(idUser,idUserToFollow);

        if(path == null){
            view.printMessage("No hi ha cap cadena de contactes :(");
            return;
        }

        while(!path.isEmpty()){
            view.printMessage(path.remove().toPrettyString());
            view.printMessage();
        }
    }
}
