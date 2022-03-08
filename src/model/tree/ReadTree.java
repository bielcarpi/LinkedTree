package model.tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadTree {

    private static Algorithm[] algorithms;

    public static void read(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Graphs/" + fileName));

        String currentLine = br.readLine();
        int numAlgorithms = Integer.parseInt(currentLine);
        algorithms = new Algorithm[numAlgorithms];

        for (int i = 0; i < numAlgorithms; i++) {
            currentLine = br.readLine();
            // Tractem l'informació dels algorismes
            String[] algorithmFields = currentLine.split(";");
            algorithms[i] = new Algorithm(Integer.parseInt(algorithmFields[0]), algorithmFields[1],
                    algorithmFields[2], algorithmFields[3], Integer.parseInt(algorithmFields[4]));
        }
    }

    public static Algorithm[] getAlgorithms(){
        return algorithms;
    }

}
