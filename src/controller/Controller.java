package controller;

import model.Model;
import model.graph.Recommendation;
import model.graph.UserGraph;
import model.graph.interfaces.GraphNode;
import model.utilities.Queue;
import model.utilities.Stack;
import view.View;

import java.io.IOException;

public class Controller {

    private Model model;
    private View view;

    private static final String ACYCLIC_FILE_NAME = "dagXXL.paed";

    public Controller(View v, Model m){
        view = v;
        model = m;
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
                        view.printMessageWithoutLine("Error. The value ranges are from [1, 5]: ");
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
                        view.printMessageWithoutLine("Error. The value ranges are from [A, E]: ");
                        break;
                }
            } while(true); //While the input is not well-formatted
        }
    }

    private void exploreNetwork(){
        Queue<GraphNode> q = model.exploreNetwork();

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

        view.printMessage();
    }

    private void getRecommendation(){
        boolean idValid = false;
        int id;
        do{
            view.printMessageWithoutLine("Entra el teu identificador: ");
            id = view.askForInteger();
            if(model.userExists(id))
                idValid = true;
            else
                view.printMessage("Identificador erroni...");
        } while(!idValid);

        Recommendation[] recommendations = model.getFollowRecommendation(id);

        if(recommendations == null || recommendations.length == 0){
            view.printMessage("No hem trobat comptes que puguis seguir :(");
            return;
        }

        view.printMessage("Potser t'interessa seguir els seguents comptes:");
        view.printMessage();

        for(Recommendation r: recommendations){
            view.printMessage(r.toString());
            view.printMessage();
        }

        view.printMessage();
    }

    private void contextualizeDrama() {
        view.printMessage("Llegint el dataset de drama...");
        Stack<GraphNode> dramaNodesTopo = model.contextualizeDrama(); //Get Drama nodes in topo order

        view.printMessage("Pots posar-te al dia de la polèmica en el següent ordre:");
        while(!dramaNodesTopo.isEmpty()){
            GraphNode n = dramaNodesTopo.remove();
            view.printMessage(n.dramaToString());
            if(!dramaNodesTopo.isEmpty()) view.printMessage("\t↓");
        }

        view.printMessage();
    }

    private void easyNetworking() {
        boolean idValid = false;
        int idUser, idUserToFollow;

        do{
            view.printMessageWithoutLine("Entra el teu identificador: ");
            idUser = view.askForInteger();
            if(model.userExists(idUser))
                idValid = true;
            else
                view.printMessage("Identificador erroni...");
        } while(!idValid);

        idValid = false;
        do{
            view.printMessageWithoutLine("Entra l'identificador de l'altre usuari: ");
            idUserToFollow = view.askForInteger();
            if(idUserToFollow != idUser && model.userExists(idUserToFollow))
                idValid = true;
            else
                view.printMessage("Identificador erroni...");
        } while(!idValid);

        view.printMessage("Trobant la cadena de contactes óptima...\n");

        Stack<GraphNode> path = model.getContactChain(idUser, idUserToFollow);

        if(path == null){
            view.printMessage("No hi ha cap cadena de contactes :(");
            view.printMessage();
            return;
        }

        while(!path.isEmpty()){
            view.printMessage(path.remove().toPrettyString());
            view.printMessage();
        }
    }
}
