package model.table;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadTable {
    private static Advertisement[] advertisment;

    public static void read(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Graphs/" + fileName));

        String currentLine = br.readLine();
        int numAdvertisments = Integer.parseInt(currentLine);
        advertisment = new Advertisement[numAdvertisments];

        for (int i = 0; i < numAdvertisments; i++) {
            currentLine = br.readLine();

            String[] advertFields = currentLine.split(";");
            advertisment[i] = new Advertisement(advertFields[0], advertFields[1], Integer.parseInt(advertFields[2]));
        }
    }

    public static Advertisement[] getAdvertisment() {
        return advertisment;
    }
}
