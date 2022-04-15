package controller;

import model.Model;
import model.graph.Recommendation;
import model.graph.interfaces.GraphNode;
import model.tree.Algorithm;
import model.utilities.ArrayList;
import model.utilities.Queue;
import model.utilities.Stack;
import view.View;


public class Controller {

    private Model model;
    private View view;

    private static final String ACYCLIC_FILE_NAME = "dagXS.paed";

    public Controller(View v, Model m){
        view = v;
        model = m;
    }

    /**
     * Infinite loop that executes the code
     */
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
                        // Execute Feed option
                        feedOption();
                        continue infiniteLoop;

                    case 3:
                        // Execute Canvas option
                        canvasOption();
                        continue infiniteLoop;

                    case 4:
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

    /**
     * Infinite loop that performs the canvas (R Tree) option
     */
    private void canvasOption() {
        infiniteLoop:
        while(true){
            view.printCanvasMenu();
            do{
                char option = view.askForChar();
                view.printMessage();
                view.printMessage();
                switch (option){
                    case 'A':
                        addCircle();
                        continue infiniteLoop;
                    case 'B':
                        removeCircle();
                        continue infiniteLoop;
                    case 'C':
                        visualizePainting();
                        continue infiniteLoop;
                    case 'D':
                        areaSearch();
                        continue infiniteLoop;
                    case 'E':
                        specialSearch();
                        continue infiniteLoop;
                    case 'F':
                        return;
                    default:
                        view.printMessageWithoutLine("Error. The value ranges are from [A, G]: ");
                        break;
                }
            } while(true); //While the input is not well-formatted
        }
    }

    /**
     * Method that searches the Circles that are similar and close to add a new Circle there
     */
    private void specialSearch() {
        view.printMessageWithoutLine("Entra la coordenada X del centre del cercle a cercar: ");
        float x = view.askForFloat();
        view.printMessageWithoutLine("Entra la coordenada Y del centre del cercle a afegir: ");
        float y = view.askForFloat();
        view.printMessageWithoutLine("Entra el radi del cercle a cercar: ");
        float radius = view.askForFloat();
        view.printMessageWithoutLine("Entra el color del cercle a cercar: ");
        String hexColor = view.askForString();

        view.printMessage();
        if (true/*Cridar a la funcio per fer la Cerca Especial que retorni boolea o string dels cercles*/) {
            view.printMessage("Els cercles propers i semblants a aquest són:");
            //Printar els cercles trobats
        } else {
            view.printMessage("No s'ha pogut trobar cap cercle similar i a prop d'aquest, torna-ho a intentar!");
        }
    }

    /**
     * Method that searches the Circles that are contained in an especific area
     */
    private void areaSearch() {
        view.printMessageWithoutLine("Entra del primer punt del rectangle (X,Y): ");
        String stringFirstPoint = view.askForString();
        String[] firstPoint = stringFirstPoint.split(",");
        view.printMessageWithoutLine("Entra del segon punt del rectangle (X,Y): ");
        String stringSecondPoint = view.askForString();
        String[] secondPoint = stringSecondPoint.split(",");
        view.printMessage();
        // a la funcio passar --> Integer.parseInt(first/secondPoint[0/1])
        if (true/*Cridar a la funcio per a fer la Cerca Per Area que retorni int o String dels cercles*/) {
            view.printMessage("S'han trobat " +  "X"  + " cercles en aquesta àrea:");
            //Printar els cercles trobats
        } else {
            view.printMessage("No hi ha cap cercle en aquesta àrea, torna-ho a intentar!");
        }
    }

    /**
     * Method that visualize all the wall painting of Circles
     */
    private void visualizePainting() {
        /*Cridar directament al swing per visualitzar el canvas*/
        view.printMessage("Generant la visualització del canvas...");
    }

    /**
     * Method that removes a Circle at the painting
     */
    private void removeCircle() {
        view.printMessageWithoutLine("Entra la coordenada X del centre del cercle a eliminar: ");
        float x = view.askForFloat();
        view.printMessageWithoutLine("Entra la coordenada Y del centre del cercle a eliminar: ");
        float y = view.askForFloat();

        view.printMessage();
        if (true/*Cridar a la funcio per Eliminar Cercle que retorni boolea*/) {
            view.printMessage("El cercle s'ha eliminat correctament del canvas.");
        } else {
            view.printMessage("No hi ha cap cercle en aquest punt, torna-ho a intentar!");
        }
    }

    /**
     * Method that adds a Circle at the painting
     */
    private void addCircle() {
        view.printMessageWithoutLine("Entra la coordenada X del centre del cercle a afegir: ");
        float x = view.askForFloat();
        view.printMessageWithoutLine("Entra la coordenada Y del centre del cercle a afegir: ");
        float y = view.askForFloat();
        view.printMessageWithoutLine("Entra el radi del cercle a afegir: ");
        float radius = view.askForFloat();
        view.printMessageWithoutLine("Entra el color del cercle a afegir: ");
        String hexColor = view.askForString();

        view.printMessage();
        if (true/*Cridar a la funcio per afegir Cercle que retorni boolea*/) {
            view.printMessage("El cercle s'ha afegit correctament al canvas.");
        } else {
            view.printMessage("El cercle no s'ha pogut afegir al canvas, torna-ho a intentar!");
        }
    }

    /**
     * Infinite loop that performs the feed (Binary Tree) option
     */
    private void feedOption() {
        infiniteLoop:
        while(true){
            view.printFeedMenu();
            do{
                char option = view.askForChar();
                view.printMessage();
                switch (option){
                    case 'A':
                        addAlgorithm();
                        continue infiniteLoop;
                    case 'B':
                        removeAlgorithm();
                        continue infiniteLoop;
                    case 'C':
                        listAlgorithms();
                        continue infiniteLoop;
                    case 'D':
                        timestampSearch();
                        continue infiniteLoop;
                    case 'E':
                        rangeTimestampSearch();
                        continue infiniteLoop;
                    case 'F':
                        drawAlgorithmTree();
                        continue infiniteLoop;
                    case 'G':
                        return;
                    default:
                        view.printMessageWithoutLine("Error. The value ranges are from [A, G]: ");
                        break;
                }
            } while(true); //While the input is not well-formatted
        }
    }

    /**
     * Method that list all the algorithms in the system
     */
    private void listAlgorithms() {
        model.listAlgorithms();
    }

    /**
     * Method that implements the algorithm range search by timestamp
     */
    private void rangeTimestampSearch() {
        view.printMessageWithoutLine("Timestamp mínim a cercar: ");
        int minTimestamp = view.askForInteger();
        view.printMessageWithoutLine("Timestamp màxim a cercar: ");
        int maxTimestamp = view.askForInteger();
        view.printMessage();
        if (maxTimestamp < minTimestamp) {
            view.printMessage("El timestamp màxim no pot ser més petit que el mínim.");
        } else {
            ArrayList<Algorithm> solutionAlgorithms = model.searchByRangeTimestamp(minTimestamp, maxTimestamp);

            if (solutionAlgorithms.size() != 0) {
                for (int i = 0; i < solutionAlgorithms.size(); i++) {
                    view.printMessage(solutionAlgorithms.get(i).timestampSearchString());
                }

            } else {
                view.printMessage("No s'ha trobat cap algorisme per aquest rang de timestamps!");
            }
        }
    }

    /**
     * Method that implements the algorithm search by an exact timestamp
     */
    private void timestampSearch() {
        view.printMessageWithoutLine("Timestamp a cercar: ");
        int timestamp = view.askForInteger();
        Algorithm solutionAlgorithm = model.searchByTimestamp(timestamp);
        view.printMessage();

        if (solutionAlgorithm != null) {
            view.printMessage(solutionAlgorithm.timestampSearchString());
        } else {
            view.printMessage("No s'ha trobat cap algorisme per aquest timestamp!");
        }
    }

    /**
     * Method that removes an algorithm in the system
     */
    private void removeAlgorithm() {
        view.printMessageWithoutLine("\nIdentificador de l'algorisme: ");
        int id = view.askForInteger();

        String removedNode = model.removeAlgorithm(id);
        if (removedNode != null) {
            view.printMessage("L'algorisme de " + removedNode + " s'ha eliminat correctament!");
        } else {
            view.printMessage("L'algorisme no s'ha pogut eliminar del feed perquè no existeix!");
        }
    }

    /**
     * Method that adds an algorithm in the system
     */
    private void addAlgorithm() {
        view.printMessageWithoutLine("Identificador de l'algorisme: ");
        int id = view.askForInteger();
        view.printMessageWithoutLine("Nom de l'algorisme: ");
        String name = view.askForString();
        view.printMessageWithoutLine("Llenguatge de l'algorisme: ");
        String language = view.askForString();
        view.printMessageWithoutLine("Cost de l'algorisme: ");
        String cost = view.askForString();
        view.printMessageWithoutLine("Timestamp de l'algorisme: ");
        int timestamp = view.askForInteger();

        if(model.algorithmExists(id)) {
            view.printMessage("\nL'algorisme no s'ha pogut afegir al feed perquè ja existeix!");
        } else {
            model.addNewAlgorithm(id, name, language, cost, timestamp);
            view.printMessage("\nL'algorisme ha estat correctament afegit al  feed.");
        }
    }

    /**
     * Infinite loop that performs the followers option
     */
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

    /**
     * Method that explores all the followers network
     */
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

    /**
     * Method that makes followers recomendations
     */
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

    /**
     * Method that shows the right way followers to contextualize drama
     */
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

    /**
     * Method that finds the optimum contact network of 2 users in order to expand their social circle
     */
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

    private void drawAlgorithmTree(){
        model.drawAlgorithmTree();
    }
}
