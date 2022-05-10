package model.table;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadTable {
    private static Advertisment[] advertisment;

    public static void read(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Graphs/" + fileName));

        String currentLine = br.readLine();
        int numAdvertisments = Integer.parseInt(currentLine);
        advertisment = new Advertisment[numAdvertisments];

        for (int i = 0; i < numAdvertisments; i++) {
            currentLine = br.readLine();

            String[] advertFields = currentLine.split(";");
            advertisment[i] = new Advertisment(advertFields[0], advertFields[1], Integer.parseInt(advertFields[2]));
        }
    }

    public static Advertisment[] getAdvertisment() {
        return advertisment;
    }
}
