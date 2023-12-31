package controller;

import model.Model;
import model.graph.Recommendation;
import model.graph.interfaces.GraphNode;
import model.rtree.Circle;
import model.rtree.Point;
import model.rtree.interfaces.RTreeElement;
import model.table.Advertisement;
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
                        advertisementOption();
                        continue infiniteLoop;

                    case 5:
                        view.printMessage("\nAturant LinkedTree...\n");
                        return;

                    default:
                        view.printMessageWithoutLine("Error. The value ranges are from [1, 5]: ");
                        break;
                }
            } while (option < 1 || option > 6);
        }
    }

    /**
     * Infinite loop that performs the advertisement (Hash) option
     */
    private void advertisementOption() {
        infiniteLoop:
        while(true){
            view.printAdvertisementMenu();
            do{
                char option = view.askForChar();
                view.askForString();
                switch (option){
                    case 'A':
                        addCompany();
                        continue infiniteLoop;
                    case 'B':
                        removeCompany();
                        continue infiniteLoop;
                    case 'C':
                        consultCompany();
                        continue infiniteLoop;
                    case 'D':
                        showHistogram();
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
     * Method that shows the histogram of the advertisements (HashMap)
     */
    private void showHistogram() {
        view.printMessage();
        view.printMessage("Generant histograma...");
        if(!model.showAdvertisementHistogram())
            view.printMessage("L'histograma ja s'esta visualitzant!");
    }

    /**
     * Method that consults a company advertisement (HashMap)
     */
    private void consultCompany() {
        view.printMessage();
        view.printMessageWithoutLine("Entra el nom de l'empresa a consultar: ");
        String companyName = view.askForString();
        // We mesure the algorithm execution time
        // long timeBefore = System.nanoTime();
        Advertisement ad = model.getAdvertisement(companyName);
        // long timeAfter = System.nanoTime();
        view.printMessage();

        if (ad != null) {
            view.printMessage(ad.toPrettyString());
        } else {
            view.printMessage("L'empresa introduida no existeix! Torna-ho a intentar.");
        }

        // System.out.println("\nTIME: " + (timeAfter-timeBefore)/1000.0 + " us");
    }

    /**
     * Method that removes an advertisement to a company (HashMap)
     */
    private void removeCompany() {
        view.printMessage();
        view.printMessageWithoutLine("Entra el nom de l'empresa a eliminar: ");
        String companyName = view.askForString();
        // We mesure the algorithm execution time
        // long timeBefore = System.nanoTime();
        boolean success = model.removeAdvertisement(companyName);
        // long timeAfter = System.nanoTime();
        view.printMessage();

        if (success) {
            view.printMessage("L'empresa s'ha eliminat correctament del sistema gestor d'anuncis.");
        } else {
            view.printMessage("L'empresa introduida no exiteix! Torna-ho a intentar.");
        }

        // System.out.println("\nTIME: " + (timeAfter-timeBefore)/1000.0 + " us");
    }

    /**
     * Method that adds an advertisement to a company (HashMap)
     */
    private void addCompany() {
        view.printMessage();
        view.printMessageWithoutLine("Entra el nom de l'empresa a afegir: ");
        String companyName = view.askForString();
        view.printMessageWithoutLine("Entra el dia de la setmana en el que està interessada: ");
        String day = view.askForString();
        view.printMessageWithoutLine("Entra el preu que pot pagar, en euros: ");
        int cost = view.askForInteger();
        view.printMessage();

        // We mesure the algorithm execution time
        // long timeBefore = System.nanoTime();
        model.addAdvertisement(companyName, day, cost);
        // long timeAfter = System.nanoTime();
        view.printMessage("L'empresa s'ha afegit correctament al sistema gestor d'anuncis.");

        // System.out.println("\nTIME: " + (timeAfter-timeBefore)/1000.0 + " us");
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
                        view.printMessageWithoutLine("Error. The value ranges are from [A, F]: ");
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

        Circle circle = new Circle(new Point(x, y), radius, hexColor);

        view.printMessage();

        // We mesure the algorithm execution time
        // long timeBefore = System.nanoTime();
        java.util.ArrayList<RTreeElement> magicSearch = model.circleMagicSearch(circle);
        // long timeAfter = System.nanoTime();

        if (magicSearch.size() != 0) {
            view.printMessage("Els cercles propers i semblants a aquest són:\n");
            //Printar els cercles trobats
            for (RTreeElement search : magicSearch) {
                view.printMessage(search.toRangeSearchString());
            }
        } else {
            view.printMessage("No s'ha pogut trobar cap cercle similar i a prop d'aquest, torna-ho a intentar!");
        }

        // System.out.println("\nTIME: " + (timeAfter-timeBefore)/1000.0 + " us");
        // System.out.println("PUNTS TROBATS: " + magicSearch.size());
    }

    /**
     * Method that searches the Circles that are contained in an especific area
     */
    private void areaSearch() {
        view.printMessageWithoutLine("Entra el primer punt del rectangle (X,Y): ");
        view.askForString();
        String stringFirstPoint = view.askForString();
        String[] firstPoint = stringFirstPoint.split(",");
        view.printMessageWithoutLine("Entra el segon punt del rectangle (X,Y): ");
        String stringSecondPoint = view.askForString();
        String[] secondPoint = stringSecondPoint.split(",");
        view.printMessage();

        Point[] points = new Point[2];
        try {
            points[0] = new Point(Float.parseFloat(firstPoint[0]), Float.parseFloat(firstPoint[1]));
            points[1] = new Point(Float.parseFloat(secondPoint[0]), Float.parseFloat(secondPoint[1]));

            // We mesure the algorithm execution time
            // long timeBefore = System.nanoTime();
            java.util.ArrayList<RTreeElement> pointsInArea = model.circleRangeSearch(points);
            // long timeAfter = System.nanoTime();

            if (pointsInArea.size() != 0) {
                view.printMessage("S'han trobat " + pointsInArea.size() + " cercles en aquesta àrea:\n");
                //Printar els cercles trobats
                for (int i = 0; i < pointsInArea.size(); i++) {
                    view.printMessage(pointsInArea.get(i).toRangeSearchString());
                }
            } else {
                view.printMessage("No hi ha cap cercle en aquesta àrea. Torna-ho a intentar!");
            }

            // System.out.println("\nTIME: " + (timeAfter-timeBefore)/1000.0 + " us");
            // System.out.println("PUNTS TROBATS: " + pointsInArea.size());
        } catch (Exception e) {
            view.printMessage("No s'ha pogut fer parse d'aquest número correctament. " +
                    "\nTorna-ho a intentar posant-ho en format enter1.decimal1,enter2.decimal2");
        }
    }

    /**
     * Method that visualize all the wall painting of Circles
     */
    private void visualizePainting() {
        view.printMessage("Generant la visualització del canvas...");
        if(!model.visualizeCircles()) view.printMessage("No s'ha pogut generar la visualització. Tanca la finestra de visualització actual per generar-ne una de nova.");
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
        // We mesure the algorithm execution time
        // long timeBefore = System.nanoTime();
        if (model.removePoint(new Point(x, y))) {
            view.printMessage("El cercle s'ha eliminat correctament del canvas.");
        } else {
            view.printMessage("No hi ha cap cercle en aquest punt, torna-ho a intentar!");
        }

        // long timeAfter = System.nanoTime();
        // System.out.println("\nTIME: " + (timeAfter-timeBefore)/1000.0 + " us");
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

        // We mesure the algorithm execution time
        // long timeBefore = System.nanoTime();
        model.addNewCircle(x, y, radius, hexColor);
        // long timeAfter = System.nanoTime();
        view.printMessage("El cercle s'ha afegit correctament al canvas.");

        // System.out.println("\nTIME: " + (timeAfter-timeBefore)/1000.0 + " us");
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
        long timeBefore = 1, timeAfter = -1;

        if (maxTimestamp < minTimestamp) {
            view.printMessage("El timestamp màxim no pot ser més petit que el mínim.");
        } else {
            timeBefore = System.nanoTime();
            ArrayList<Algorithm> solutionAlgorithms = model.searchByRangeTimestamp(minTimestamp, maxTimestamp);
            timeAfter = System.nanoTime();
            if (solutionAlgorithms.size() != 0) {
                view.printMessage("\nS'han trobat " + solutionAlgorithms.size() + " algorismes en aquest rang...\n");
                for (int i = 0; i < solutionAlgorithms.size(); i++) {
                    view.printMessage(solutionAlgorithms.get(i).toPrettyString());
                }

            } else {
                view.printMessage("No s'ha trobat cap algorisme per aquest rang de timestamps!");
            }
        }

        System.out.println("\nTIME: " + (timeAfter-timeBefore)/1000.0 + " us");
    }

    /**
     * Method that implements the algorithm search by an exact timestamp
     */
    private void timestampSearch() {
        view.printMessageWithoutLine("Timestamp a cercar: ");
        int timestamp = view.askForInteger();
        long timeBefore = System.nanoTime();
        Algorithm solutionAlgorithm = model.searchByTimestamp(timestamp);
        long timeAfter = System.nanoTime();

        view.printMessage();

        if (solutionAlgorithm != null) {
            view.printMessage(solutionAlgorithm.timestampSearchString());
        } else {
            view.printMessage("No s'ha trobat cap algorisme per aquest timestamp!");
        }

        System.out.println("\nTIME: " + (timeAfter-timeBefore)/1000.0 + " us");
    }

    /**
     * Method that removes an algorithm in the system
     */
    private void removeAlgorithm() {
        view.printMessageWithoutLine("Identificador de l'algorisme: ");
        int id = view.askForInteger();

        long timeBefore = System.nanoTime();
        String removedNode = model.removeAlgorithm(id);
        long timeAfter = System.nanoTime();
        if (removedNode != null) {
            view.printMessage("\nL'algorisme de " + removedNode + " s'ha eliminat correctament del feed.");
        } else {
            view.printMessage("\nL'algorisme no s'ha pogut eliminar del feed perquè no existeix!");
        }

        System.out.println("\nTIME: " + (timeAfter-timeBefore)/1000.0 + " us");
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
            long timeBefore = System.nanoTime();
            model.addNewAlgorithm(id, name, language, cost, timestamp);
            long timeAfter = System.nanoTime();
            view.printMessage("\nL'algorisme ha estat correctament afegit al  feed.");

            System.out.println("\nTIME: " + (timeAfter-timeBefore)/1000.0 + " us");
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

        view.printMessage("\nPotser t'interessa seguir els seguents comptes:");
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
        view.printMessage("\nLlegint el dataset de drama...");
        Stack<GraphNode> dramaNodesTopo = model.contextualizeDrama(); //Get Drama nodes in topo order

        view.printMessage("\nPots posar-te al dia de la polèmica en el següent ordre:\n");
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

    /**
     * (Extra) Method that draws in console the AlgorithmTree
     */
    private void drawAlgorithmTree(){
        model.drawAlgorithmTree();
    }
}