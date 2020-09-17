package FinalModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
//generate embeddings for new nodes

public class NewEmbeddding {

    /*public static String inputResultSetFilenameX = Vars.twitter_dir + "foursquare\\embeddings\\foursquare.newNode.RandomInit.4";
     public static String attentionResultX = Vars.twitter_dir+"foursquare\\attention\\Rand_AttentionResult_f4.number";
     public static String supervisedResultX = Vars.twitter_dir+"foursquare\\supervisedLearning\\Rand_Supervised_f4.number";
     public static String outputFileX=Vars.twitter_dir+"foursquare\\embeddings\\foursquare.Top5newNode.4-2";

    public static String inputResultSetFilenameY = Vars.twitter_dir + "twitter\\embeddings\\twitter.newNode.RandomInit.4";
    public static String attentionResultY = Vars.twitter_dir+"twitter\\attention\\Rand_AttentionResult_t4.number";
    public static String supervisedResultY = Vars.twitter_dir+"twitter\\supervisedLearning\\Rand_Supervised_t4.number";
    public static String outputFileY=Vars.twitter_dir+"twitter\\embeddings\\twitter.Top5newNode.4-2";*/
    public static String inputResultSet1FilenameX = "C:\\Users\\puling\\Desktop\\database\\foursquare\\embedding\\foursquare.INEembeddingtop5.4up.100000";
    public static String inputResultSetFilenameX = "C:\\Users\\puling\\Desktop\\database\\foursquare\\embedding\\foursquare.newNode.RandomInittop.5";
    public static String attentionResultX = "C:\\Users\\puling\\Desktop\\database\\foursquare\\attention\\AttentionResulttop5_f5";
    public static String supervisedResultX = "C:\\Users\\puling\\Desktop\\database\\foursquare\\supervised\\Supervisedtop5_f5.number";
    public static String outputFileX = "C:\\Users\\puling\\Desktop\\database\\foursquare\\embedding\\foursquare.newNodetop5.5";

    public static String inputResultSet1FilenameY = "C:\\Users\\puling\\Desktop\\database\\twitter\\embedding\\twitter.INEembeddingtop5.4up.100000";
    public static String inputResultSetFilenameY = "C:\\Users\\puling\\Desktop\\database\\twitter\\embedding\\twitter.newNode.RandomInittop.5";
    public static String attentionResultY = "C:\\Users\\puling\\Desktop\\database\\twitter\\attention\\AttentionResulttop5_t5";
    public static String supervisedResultY = "C:\\Users\\puling\\Desktop\\database\\twitter\\supervised\\Supervisedtop5_t5.number";
    public static String outputFileY = "C:\\Users\\puling\\Desktop\\database\\twitter\\embedding\\twitter.newNodetop5.5";

    public static Map<String, Map<String, Double>> attentionresultX = new HashMap<>();
    public static Map<String, Map<String, Double>> supervisedresultX = new HashMap<>();
    public static Map<String, Double[]> resultX = new HashMap<>();
    public static Map<String, Double[]> embeddingOneX = new HashMap<>();
    public static Map<String, Double[]> embeddingTwoX = new HashMap<>();

    public static Map<String, Map<String, Double>> attentionresultY = new HashMap<>();
    public static Map<String, Map<String, Double>> supervisedresultY = new HashMap<>();
    public static Map<String, Double[]> resultY = new HashMap<>();
    public static Map<String, Double[]> embeddingOneY = new HashMap<>();
    public static Map<String, Double[]> embeddingTwoY = new HashMap<>();

    public static void readEmbeddingFile(String filename, Map<String, Double[]> map) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
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

    public static void newEmb(Map<String, Map<String, Double>> attentionresult, Map<String, Map<String, Double>> supervisedresult,
                              Map<String, Double[]> embeddingOne, Map<String, Double[]> embeddingTwo, Map<String, Double[]> result) {
        for (Map.Entry<String, Map<String, Double>> entry : attentionresult.entrySet()) {
            Double attention;
            Double supervised;
            Double result1;
            Double[] result2 = new Double[100];
            Double[] result3 = new Double[100];
            for (int i = 0; i < 100; i++) {
                result3[i] = 0.0;
            }
            String key = entry.getKey();

            /*attention*/
            Map<String, Double> value1 = entry.getValue();
            /*包含了attention*/
            Map<String, Double> value2 = supervisedresult.get(key);
			 /*if (value1.size()!=value2.size()){
			     System.out.println(key);
			     return;
             }*/

            for (Map.Entry<String, Double> map : value1.entrySet()) {
                String key2 = map.getKey();
                attention = map.getValue();
                supervised = value2.get(key2);
                result1 = (attention + supervised) / 2;
                Double[] embedding = embeddingTwo.get(key2);

                if (!embeddingTwo.containsKey(key2)) {
                    embedding = embeddingOne.get(key2);
                }
                for (int i = 0; i < 100; i++) {
                    try {
                        result2[i] = embedding[i] * result1;
                    } catch (NullPointerException e) {
                        System.out.println(key2);
                    }

                }
                for (int i = 0; i < 100; i++) {
                    try {
                        result3[i] += result2[i];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            result.put(key, result3);
        }
    }

    public static void output(String output_filename, Map<String, Double[]> result) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(
                output_filename));
        for (Map.Entry<String, Double[]> entry : result.entrySet()) {
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

        readEmbeddingFile(inputResultSet1FilenameX, embeddingOneX);
        readEmbeddingFile(inputResultSetFilenameX, embeddingTwoX);
        readResultFile(attentionResultX, attentionresultX);
        readResultFile(supervisedResultX, supervisedresultX);
        newEmb(attentionresultX, supervisedresultX, embeddingOneX, embeddingTwoX, resultX);
        output(outputFileX, resultX);

        readEmbeddingFile(inputResultSet1FilenameY, embeddingOneY);
        readEmbeddingFile(inputResultSetFilenameY, embeddingTwoY);
        readResultFile(attentionResultY, attentionresultY);
        readResultFile(supervisedResultY, supervisedresultY);
        newEmb(attentionresultY, supervisedresultY, embeddingOneY, embeddingTwoY, resultY);
        output(outputFileY, resultY);

    }*/


}
