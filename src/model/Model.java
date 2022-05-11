package model;

import model.graph.Recommendation;
import model.graph.UserGraph;
import model.graph.interfaces.GraphNode;
import model.rtree.Circle;
import model.rtree.Point;
import model.rtree.RTree;
import model.rtree.interfaces.RTreeElement;
import model.table.Advertisement;
import model.table.AdvertisementHistogram;
import model.table.HashMap;
import model.table.ReadTable;
import model.tree.Algorithm;
import model.tree.AlgorithmTree;
import model.utilities.ArrayList;
import model.utilities.Queue;
import model.utilities.Stack;

import java.io.IOException;

public class Model {

    private final UserGraph userGraph;
    private final UserGraph dramaGraph;
    private final AlgorithmTree algorithmTree;
    private final RTree rTree;
    private final HashMap<String, Advertisement> hashMap;

    public Model(final String graphFileName, final String dramaFileName, final String treeFileName,
                 final String rTreeFileName, final int rTreeOrder, final String hashMapFileName)
            throws IOException {
        userGraph = new UserGraph(graphFileName);
        dramaGraph = new UserGraph(dramaFileName);
        algorithmTree = new AlgorithmTree(treeFileName);
        rTree = new RTree(rTreeOrder, rTreeFileName);
        hashMap = new HashMap<>();

        ReadTable.read(hashMapFileName);
        Advertisement[] ads = ReadTable.getAdvertisment();
        for(Advertisement a: ads) hashMap.put(a.getName(), a);
        System.out.printf("test");
    }

    public Queue<GraphNode> exploreNetwork(){
        return userGraph.getNodesBfs();
    }

    public boolean userExists(int id){
        return userGraph.getUser(id) != null;
    }

    public Recommendation[] getFollowRecommendation(int id){
        return userGraph.getFollowRecommendation(id);
    }

    public Stack<GraphNode> contextualizeDrama(){
        return dramaGraph.getNodesTopo();
    }

    public Stack<GraphNode> getContactChain(int userId, int userIdObjective){
        return userGraph.getDijkstra(userId, userIdObjective);
    }

    public boolean algorithmExists(int id){
        return algorithmTree.nodeExists(id);
    }

    public void addNewAlgorithm(int id, String name, String language, String cost, int timestamp){
        if(algorithmExists(id)) throw new IllegalArgumentException();
        algorithmTree.insert(new Algorithm(id, name, language, cost, timestamp));
    }

    public Algorithm searchByTimestamp(int timestamp) {
        return algorithmTree.getNodeByTimestamp(timestamp);
    }

    public ArrayList<Algorithm> searchByRangeTimestamp(int minTimestamp, int maxTimestamp) {
        return algorithmTree.getRangeNodeByTimestamp(minTimestamp, maxTimestamp);
    }

    public void listAlgorithms() {
        algorithmTree.listAlgorithms();
    }

    public void drawAlgorithmTree() {
        algorithmTree.drawTree();
    }

    public String removeAlgorithm(int id){
        return algorithmTree.remove(id);
    }

    public void addNewCircle(float x, float y, float radius, String hexColor) {
        rTree.insert(new Circle(new Point(x, y), radius, hexColor));
    }

    public boolean removePoint(Point point) {
        return rTree.delete(point);
    }

    public boolean visualizeCircles() {
        return rTree.visualize();
    }

    public java.util.ArrayList<RTreeElement> circleRangeSearch(Point[] pointsRangeSearch) {
        return rTree.makeRangeSearch(pointsRangeSearch);
    }

    public java.util.ArrayList<RTreeElement> circleMagicSearch(Circle circle) {
        return rTree.getPointsBySimilarity(circle);
    }


    public Advertisement getAdvertisement(String key){
        return hashMap.get(key);
    }

    public boolean removeAdvertisement(String key){
        return hashMap.remove(key);
    }

    public void addAdvertisement(String companyName, String weekDay, int cost){
        Advertisement a = new Advertisement(companyName, weekDay, cost);
        hashMap.put(a.getName(), a);
    }

    public boolean showAdvertisementHistogram(){
        return AdvertisementHistogram.start(hashMap);
    }
}
