package dyn.ModelWith2OrderNorm;

import FinalModel.Attention;

import java.io.*;
import java.util.*;

//init new noedes used by neighbor nodes
public class InitEmbedding {

    //vectors
    private static Map<String, Double[]> answer = new HashMap<>();
    // new node vectors
    private static List<String> point = new ArrayList<>();
    //neighbor nodes
    private static Map<String, Set<String>> neighbor = new HashMap<>();

    private static Map<String, Double[]> answerTest = new HashMap<>();

    /**
     * init nodes
     *
     */
    private static void initEmbedding(Map<String, Double[]> answer,
                                      List<String> point, Map<String, Set<String>> neighbor, int loopNum) {
        loopNum--;
        if (point.size() == 0) {
            System.out.println("initEmbedding:point is null");
        }
        for (String s : point) {
            if (neighbor.get(s) == null) {
                System.out.println("no neighbor:" + s);
                break;
            }


            int validNum = 0;
            Double[] doubles = new Double[100];
            for (int i = 0; i < doubles.length; i++) {
                doubles[i] = 0.0;
            }

            for (String ns : neighbor.get(s)) {
                if (!point.contains(ns)) {
                    if (answer.get(ns) == null) {
                        System.out.println("answer.get(ns) is null");
                        break;
                    }
                    validNum++;
                    for (int i = 0; i < 100; i++) {
                        try {
                            doubles[i] = (doubles[i] * (validNum - 1) + answer.get(ns)[i]) / validNum;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            answer.put(s, doubles);
            answerTest.put(s, doubles);
        }
        if (loopNum != 0) {
            initEmbedding(answer, point, neighbor, loopNum);
        }
    }

    public static Map<String, Map<String, Double>> resultY = new HashMap<>();


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

    public static void readNewAddPointFile(String filename1, List<String> list, String networkName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename1));
        String temp = br.readLine();
        while (temp != null) {
            list.add(temp + networkName);
            temp = br.readLine();
        }
        br.close();
    }

    public static void readNeighborFile(String filename1, Map<String, Set<String>> map, String networkName, List<String> point) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename1));
        String temp = br.readLine();
        while (temp != null) {
            String[] strings = temp.split("\\s");
            if (point.contains(strings[0] + networkName)) {
                if (map.containsKey(strings[0] + networkName)) {
                    map.get(strings[0] + networkName).add(strings[1] + networkName);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(strings[1] + networkName);
                    map.put(strings[0] + networkName, set);
                }
            }
            if (point.contains(strings[1] + networkName)) {
                if (map.containsKey(strings[1] + networkName)) {
                    map.get(strings[1] + networkName).add(strings[0] + networkName);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(strings[0] + networkName);
                    map.put(strings[1] + networkName, set);
                }
            }
            temp = br.readLine();
        }
        br.close();
    }

    public static void output(String output_filename, Map<String, Double[]> answer) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(
                output_filename));
        for (String uid : answer.keySet()) {
            bw.write(uid + " ");
            Double[] vector = answer.get(uid);
            for (int i = 0; i < vector.length; i++) {
                bw.write(vector[i] + "|");
            }
            bw.write("\n");
        }
        bw.flush();
        bw.close();
    }


    public static void readResultFile(String filename, Map<String, Map<String, Double>> map) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String temp = br.readLine();

        while (temp != null) {
            Map<String, Double> mapvalue = new HashMap<String, Double>();
            String[] strings = temp.split(",");
            String[] vStr = strings[1].split("\t");
            String[] str = new String[vStr.length];
            for (int i = 0; i < vStr.length; i++) {
                str = vStr[i].split(":");

                Double v;
                String s;
                s = str[0];
                v = Double.valueOf(str[1]);

                mapvalue.put(s, v);
            }
            map.put(strings[0], mapvalue);

            temp = br.readLine();
        }
        br.close();
    }

   /* public static void main(String[] args) throws IOException {

        readNewAddPointFile(Attention2.inputNewPointFilenameX, point, Attention2.networkNamex);
        readNeighborFile(Attention2.inputEdgeFileNameX, neighbor, Attention2.networkNamex,point);
        readResultSetFile(Attention2.inputResultSet1FilenameX, answer);
        initEmbedding(answer,point,neighbor,5);
        output("C:\\Users\\puling\\Desktop\\database\\twitter\\embedding\\four.initEmbedding2.number",answerTest);

        readNewAddPointFile(Attention.inputNewPointFilenameY, point, Attention.networkNamey);
        readNeighborFile(Attention.inputEdgeFileNameY, neighbor, Attention.networkNamey, point);
        readResultSetFile(Attention.inputResultSet1FilenameY, answer);
        initEmbedding(answer, point, neighbor, 5);
        output("C:\\Users\\puling\\Desktop\\database\\twitter\\embedding\\tw.initEmbedding2.number", answerTest);
    }*/
}
