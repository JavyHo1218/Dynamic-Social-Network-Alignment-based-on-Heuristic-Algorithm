package FinalModel;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

//integrate new embeddings to last time network
public class integration {
    public static String inputResultSet1FilenameX = "C:\\Users\\puling\\Desktop\\database\\foursquare\\emb\\foursquare.newNodeIONEtop.4-2.num";
    public static String inputResultSet2FilenameX = "C:\\Users\\puling\\Desktop\\database\\foursquare\\embedding\\four.IONEembedding.number5.100_dim.100000";
    public static String inputResultSet1FilenameY = "C:\\Users\\puling\\Desktop\\database\\twitter\\emb\\twitter.newNodeIONEtop.4-2.num";
    public static String inputResultSet2FilenameY = "C:\\Users\\puling\\Desktop\\database\\twitter\\embedding\\tw.IONEembedding.number5.100_dim.100000";


    public static String outputFileX = "C:\\Users\\puling\\Desktop\\database\\foursquare\\emb\\foursquare.newNodeIONEtop.5-2.num";
    public static String outputFileY = "C:\\Users\\puling\\Desktop\\database\\twitter\\emb\\twitter.newNodeIONEtop.5-2.num";

    /*第一次的结果集*/
    public static Map<String, Double[]> vectorOneX = new HashMap<>();
    /*第二次的结果集*/
    public static Map<String, Double[]> vectorTwoX = new HashMap<>();

    public static Map<String, Double[]> vectorOneY = new HashMap<>();
    /*第二次的结果集*/
    public static Map<String, Double[]> vectorTwoY = new HashMap<>();

    public static void readResultSetFile(String filename1, Map<String, Double[]> map) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename1));
        String temp = br.readLine();
        while (temp != null) {
            String[] strings = temp.split("\\s");
            String[] vStr = strings[1].split("\\|");
            Double[] v = new Double[vStr.length];
            int index = 0;
            for (String s : vStr) {
                v[index] = Double.valueOf(s);
                index++;
            }
            map.put(strings[0], v);
            temp = br.readLine();
        }
        br.close();
    }

    public static void integrate(Map<String, Double[]> map1, Map<String, Double[]> map2) {
        for (Entry<String, Double[]> entry : map2.entrySet()) {
            map1.put(entry.getKey(), entry.getValue());
        }
    }

    public static void writeFile(String filename, Map<String, Double[]> map) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(
                filename));
        for (Entry<String, Double[]> entry : map.entrySet()) {
            StringBuilder outputString = new StringBuilder();
            String outputKey = entry.getKey();
            outputString.append(outputKey).append(" ");
            Double[] outputMap = entry.getValue();
            for (int i = 0; i < outputMap.length; i++) {
                outputString.append(outputMap[i]).append("|");
            }

            outputString.append("\n");
            bw.write(String.valueOf(outputString));
        }
        bw.flush();
        bw.close();
    }

   /* public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        readResultSetFile(inputResultSet1FilenameX, vectorOneX);
        readResultSetFile(inputResultSet2FilenameX, vectorTwoX);
        integrate(vectorOneX, vectorTwoX);
        writeFile(outputFileX, vectorOneX);

        readResultSetFile(inputResultSet1FilenameY, vectorOneY);
        readResultSetFile(inputResultSet2FilenameY, vectorTwoY);
        integrate(vectorOneY, vectorTwoY);
        writeFile(outputFileY, vectorOneY);

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }*/

}
