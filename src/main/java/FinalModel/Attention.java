package FinalModel;

import java.io.*;
import java.util.*;
// get node attention
public class Attention {

    //get top-k neighbor nodes

    public static Map<String, Double[]> vectorOneX = new HashMap<>();
    public static Map<String, Double[]> vectorTwoX = new HashMap<>();
    public static Map<String, Set<String>> neighborX = new HashMap<>();
    public static Map<String, Map<String, Double>> pointProductX = new HashMap<>();
    public static Map<String, Map<String, Double>> resultX = new HashMap<>();
    public static List<String> newAddPointX = new ArrayList<>();

    public static Map<String, Double[]> vectorOneY = new HashMap<>();

    public static Map<String, Double[]> vectorTwoY = new HashMap<>();

    public static Map<String, Set<String>> neighborY = new HashMap<>();

    public static Map<String, Map<String, Double>> pointProductY = new HashMap<>();

    public static Map<String, Map<String, Double>> resultY = new HashMap<>();

    public static List<String> newAddPointY = new ArrayList<>();


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

    public static void readNeighborFile(String filename1, Map<String, Set<String>> map, String networkName, List<String> newAddPoint) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename1));
        String temp = br.readLine();
        while (temp != null && !temp.equals("")) {
            String[] strings = temp.split("\\s");
            if (newAddPoint.contains(strings[0] + networkName)) {
                if (map.containsKey(strings[0] + networkName)) {
                    map.get(strings[0] + networkName).add(strings[1] + networkName);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(strings[1] + networkName);
                    map.put(strings[0] + networkName, set);
                }
            }
            if (newAddPoint.contains(strings[1] + networkName)) {
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


    public static void writeFile(String filename, Map<String, Map<String, Double>> map) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(
                filename));
        for (Map.Entry<String, Map<String, Double>> entry : map.entrySet()) {
            StringBuilder outputString = new StringBuilder();
            String outputKey = entry.getKey();
            outputString.append(outputKey).append(",");
            Map<String, Double> outputMap = entry.getValue();
            for (Map.Entry<String, Double> mapEntry : outputMap.entrySet()) {
                String key = mapEntry.getKey();
                Double value = mapEntry.getValue();
                outputString.append(key).append(":").append(value).append("\t");
            }
            outputString.append("\n");
            bw.write(String.valueOf(outputString));
        }
        bw.flush();
        bw.close();
    }


    public static void product(Map<String, Set<String>> neighbor, Map<String, Double[]> vectorOne, Map<String, Double[]> vectorTwo, Map<String, Map<String, Double>> pointProduct) {
        for (Map.Entry<String, Set<String>> entry : neighbor.entrySet()) {
            String key = entry.getKey();
            Set<String> value = entry.getValue();
            Double[] keyV = vectorTwo.get(key);
            Double[] valueV;

            Map<String, Double> map = new HashMap<>();
            for (String str : value) {
                if (neighbor.containsKey(str)) {
                    valueV = vectorTwo.get(str);
                } else {
                    valueV = vectorOne.get(str);
                }

                if (valueV == null) {
                    System.out.println(str.substring(0, 4));
                    continue;
                }
                double vkvv = 0;
                for (int i = 0; i < keyV.length; i++) {
                    vkvv += keyV[i] * valueV[i];
                }

                map.put(str, vkvv * 100);
            }
            pointProduct.put(key, map);
        }
    }

    public static void attentions(Map<String, Map<String, Double>> pointProduct, Map<String, Map<String, Double>> result) {
        for (Map.Entry<String, Map<String, Double>> entry : pointProduct.entrySet()) {
            double denominator = 0;
            Map<String, Double> value = entry.getValue();
            for (Map.Entry<String, Double> map : value.entrySet()) {
                denominator += Math.exp(map.getValue());
            }
            double a = 0;


            Map<String, Double> doubleMap = new HashMap<>();
            for (Map.Entry<String, Double> map : value.entrySet()) {
                double re = Math.exp(map.getValue()) / denominator;
                a += re;
                doubleMap.put(map.getKey(), re);
            }
            //System.out.println(a);
            result.put(entry.getKey(), doubleMap);
        }
    }

  /*  public static void main(String[] args) throws IOException {

    }*/

    public static void dataHandle(Map<String, Map<String, Double>> result, int k) {
        for (String key : result.keySet()) {
            List<Map.Entry<String, Double>> list = new ArrayList<>(result.get(key).entrySet());
            //降序排序
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            int index = 0;
            List<String> keys = new ArrayList<>();
            for (Map.Entry<String, Double> mapping : list) {
                index++;
                if (k < index) {
                    keys.add(mapping.getKey());
                }
            }
            for (String s : keys) {
                result.get(key).remove(s);
            }
        }
    }

    public static void fileHandle(String inputFileName, String outputFileName,
                                  Map<String, Map<String, Double>> result, String networkName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFileName));
        String temp = br.readLine();
        List<String> lists = new ArrayList<>();
        List<String> listt = new ArrayList<>();
        while (temp != null) {
            String[] strings = temp.split("\\s");
            lists.add(strings[0]);
            listt.add(strings[1]);
            temp = br.readLine();
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));

        for (int i = 0; i < lists.size(); i++) {
            if (result.containsKey(lists.get(i) + networkName)) {
                if (result.get(lists.get(i) + networkName).containsKey(listt.get(i) + networkName)) {
                    bw.write(lists.get(i) + " " + listt.get(i) + "\n");
                }
            }
            if (result.containsKey(listt.get(i) + networkName)) {
                if (result.get(listt.get(i) + networkName).containsKey(lists.get(i) + networkName)) {
                    bw.write(lists.get(i) + " " + listt.get(i) + "\n");
                }
            }
        }
        bw.flush();
        bw.close();

    }
}




