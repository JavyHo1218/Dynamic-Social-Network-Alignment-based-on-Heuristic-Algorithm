package dyn.ModelWith2OrderNorm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//add weight for anchor node
public class addWeight {
    public static String inputFilename = "D:\\论文\\database\\twitter\\新增边集\\edge2.number";    //edge file
    public static String anchorFilename = "D:\\论文\\database\\anchor\\9\\anchor2.number";        //anchor file
    public static String outputFilename = "D:\\论文\\database\\twitter\\weight\\weight2.number";

    static List<String[]> edge = new ArrayList<>();
    static List<String[]> edge_weight = new ArrayList<>();
    static List<String> anchor = new ArrayList<>();


    public static void readFile(String filename1, String filename2) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename1));
        String temp_string1 = br.readLine();
        while (temp_string1 != null) {
            String[] array = temp_string1.split("\\s+");
            edge.add(array);
            temp_string1 = br.readLine();
        }
        br.close();
        BufferedReader br1 = new BufferedReader(new FileReader(filename2));
        String temp_string = br1.readLine();
        while (temp_string != null) {
            String array1 = temp_string;
            anchor.add(array1);
            temp_string = br1.readLine();
        }
        br1.close();
    }


    public static void writeFile(String filename, List<String[]> list) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(
                filename));
        for (String[] strings : list) {
            bw.write(strings[0] + " " + strings[1] + " " + strings[2] + "\n");
        }
        bw.flush();
        bw.close();
    }

    //添加权重
    public static List<String[]> add(List<String[]> edge, List<String> anchor) {


        for (String[] str : edge) {
            int weight = 0;
            String[] para = new String[3];
            if (anchor.contains(str[0])) {
                weight = weight + 1;
            }
            if (anchor.contains(str[1])) {
                weight = weight + 1;
            }
            para[0] = str[0];
            para[1] = str[1];
            para[2] = String.valueOf(weight);
            edge_weight.add(para);
        }
        return edge_weight;

    }

    public static void main(String[] args) throws IOException {
        readFile(inputFilename, anchorFilename);
        add(edge, anchor);

        writeFile(outputFilename, edge_weight);

    }
}
