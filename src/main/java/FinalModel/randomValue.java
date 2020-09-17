package FinalModel;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//init new nodes randomly
public class randomValue {
    public static String inputNewPointFilenameX = "C:\\Users\\puling\\Desktop\\database\\foursquare\\新增点集\\following-5";
    public static String output_filenameX = "C:\\Users\\puling\\Desktop\\database\\foursquare\\embedding\\foursquare.newNode.RandomInittop.5";
    public static String inputNewPointFilenameY = "C:\\Users\\puling\\Desktop\\database\\twitter\\新增点集\\following-5";
    public static String output_filenameY = "C:\\Users\\puling\\Desktop\\database\\twitter\\embedding\\twitter.newNode.RandomInittop.5";

    public static List<String> pointX = new ArrayList<>();
    public static Map<String, Double[]> vectorX = new HashMap<>();
    public static String networkNameX = "_foursquare";

    public static List<String> pointY = new ArrayList<>();
    public static Map<String, Double[]> vectorY = new HashMap<>();
    public static String networkNameY = "_twitter";

    public static void readFile(String filename, List<String> point, String networkName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String temp = br.readLine();
        while (temp != null) {
            point.add(temp + networkName);
            temp = br.readLine();
        }
        br.close();
    }

    public static void RandomEmbedding(List<String> point, Map<String, Double[]> vector) {
        for (String s : point) {
            Double[] emb = new Double[100];
            for (int i = 0; i < 100; i++) {

                emb[i] = (Math.random() - 0.5) / 100;
                if (Double.isInfinite(emb[i])) {
                    System.out.println("init infinite");
                }
            }
            vector.put(s, emb);
        }


    }

    public static void writeFile(String output_filename, Map<String, Double[]> vector) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(
                output_filename));
        for (Map.Entry<String, Double[]> entry : vector.entrySet()) {
            StringBuilder outputString = new StringBuilder();
            String outputKey = entry.getKey();
            outputString.append(outputKey).append(" ");
            Double[] value = entry.getValue();
            for (int i = 0; i < 100; i++) {
                outputString.append(value[i]).append("|");
            }
            outputString.append("\n");
            bw.write(String.valueOf(outputString));
        }


        bw.flush();
        bw.close();
    }

   /* public static void main(String[] args) throws IOException {
        readFile(inputNewPointFilenameX, pointX, networkNameX);
        RandomEmbedding(pointX, vectorX);
        writeFile(output_filenameX, vectorX);

        readFile(inputNewPointFilenameY, pointY, networkNameY);
        RandomEmbedding(pointY, vectorY);
        writeFile(output_filenameY, vectorY);

    }*/
}
