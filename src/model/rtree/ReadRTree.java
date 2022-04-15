package model.rtree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadRTree {

    private static Circle[] circles;

    /**
     * Function that allows you to read the dataset containing the information of the R Tree.
     * @param fileName String with the representation of the name of the file.
     * @throws IOException Exception to control.
     */
    public static void read(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Graphs/" + fileName));

        String currentLine = br.readLine();
        int numCircles = Integer.parseInt(currentLine);
        circles = new Circle[numCircles];

        for (int i = 0; i < numCircles; i++) {
            currentLine = br.readLine();
            // Tractem l'informació dels cercles
            String[] circleFields = currentLine.split(";");
            circles[i] = new Circle(Float.parseFloat(circleFields[0]), Float.parseFloat(circleFields[1]),
                    Float.parseFloat(circleFields[2]), circleFields[3]);
            //Posar en el RTree el cercle aqui???
        }
    }

    public static Circle[] getCircles(){
        return circles;
    }

}
