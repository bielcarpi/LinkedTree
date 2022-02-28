package controller;

import model.ReadGraph;
import model.Recommendation;
import model.UserGraph;
import model.interfaces.Graph;
import model.interfaces.GraphNode;
import model.utilities.ArrayList;
import model.utilities.Queue;
import model.utilities.Tuple;
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
                    case 2, 3, 4:
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
            view.printMessage("Entra el teu identificador:");
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
        //TODO: Implementar dataset no cíclics per aquesta funcionalitat. Cal fer "git ignore"?
        try {
            view.printMessage("Llegint el dataset de drama...");

            //TODO: S'ha de executar aquesta opció amb els datasets en la mateixa carpeta
            UserGraph graphDrama = new UserGraph("graphXS.paed"); //Read the drama dataset
            GraphNode[] gn = graphDrama.getGraph();

            view.printMessage("Llegint el dataset de drama...");

            /*
            //For debugging purposes
            GraphNode gn = graphDrama.getUser(2);
            System.out.println(gn.toPrettyString());
            */

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void easyNetworking() {
        boolean idValid = false;
        int idUser, idUserToFollow;

        do{
            view.printMessage("Entra el teu identificador:");
            idUser = view.askForInteger();
            if(graph.getUser(idUser) != null)
                idValid = true;
            else
                view.printMessage("Identificador erroni...");
        } while(!idValid);

        idValid = false;
        do{
            view.printMessage("Entra l'identificador de l'altre usuari: ");
            idUserToFollow = view.askForInteger();
            if(graph.getUser(idUserToFollow) != null)
                idValid = true;
            else
                view.printMessage("Identificador erroni...");
        } while(!idValid);

        view.printMessage("Trobant la cadena de contactes óptima...");
    }
}
