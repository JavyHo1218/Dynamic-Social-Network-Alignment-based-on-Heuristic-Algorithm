package dyn.ModelWith2OrderNorm;

import utils.FileName;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//get anchor node for local network

public class GetAnchor {

    public static String FilenameX = "D:\\论文\\database\\foursquare\\新增边集\\edge2.IONE.number";
    public static String FilenameY = "D:\\论文\\database\\twitter\\新增边集\\edge2.IONE.number";

    public static List<String[]> listX = new ArrayList<>();
    public static List<String[]> listY = new ArrayList<>();
    public static List<String> result1 = new ArrayList<>();
    public static List<String> result2 = new ArrayList<>();

    public static Set<String> result = new HashSet<>();

    static List<String> anchor = new ArrayList<>();

    public static String anchorFilename = "D:\\论文\\database\\anchor\\9\\anchor2.number";
    public static String outputFilename = "D:\\论文\\database\\anchor\\9\\anchor2.IONE.number";

    //���ļ�
    public static void readFile(String filename1, String filename2, String filename3) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename1));
        String temp_string1 = br.readLine();
        while (temp_string1 != null) {
            String[] array = temp_string1.split(" ");
            listX.add(array);
            temp_string1 = br.readLine();
        }
        br.close();
        BufferedReader br2 = new BufferedReader(new FileReader(filename2));
        String temp_string2 = br2.readLine();
        while (temp_string2 != null) {
            String[] array2 = temp_string2.split(" ");
            listY.add(array2);
            temp_string2 = br2.readLine();
        }
        br2.close();

        BufferedReader br1 = new BufferedReader(new FileReader(filename3));
        String temp_string = br1.readLine();
        while (temp_string != null) {
            String array1 = temp_string;
            anchor.add(array1);
            temp_string = br1.readLine();
        }
        br1.close();
    }

    public static void Find(List<String[]> edge1, List<String[]> edge2, List<String> anchor) {


        for (String[] str : edge1) {

            if (anchor.contains(str[0])) {
                result1.add(str[0]);
            }
            if (anchor.contains(str[1])) {
                result1.add(str[1]);
            }

        }


        for (String[] str1 : edge2) {

            if (anchor.contains(str1[0])) {
                result2.add(str1[0]);
            }
            if (anchor.contains(str1[1])) {
                result2.add(str1[1]);
            }

        }


    }

    public static void writeFile(String filename, Set<String> result3) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(
                filename));
        for (String s : result3) {
            bw.write(s + "\n");

        }

        bw.close();

    }

    public static void main(String[] args) throws IOException {

        readFile(FilenameX, FilenameY, anchorFilename);
        Find(listX, listY, anchor);
        for (String s : result1) {
            if (result.equals(s)) {
                continue;
            }
            for (String ss : result2) {
                if (s.equals(ss)) {
                    result.add(s);
                }
            }
        }


        writeFile(outputFilename, result);
    }

}

