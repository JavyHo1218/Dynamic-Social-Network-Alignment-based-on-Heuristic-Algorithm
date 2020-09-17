package Main;

import FinalModel.*;
import utils.FileName;
import utils.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HDyNA {

    public static String networkNameX = "_foursquare";
    public static List<String> pointX = new ArrayList<>();                //新增点集
    public static Map<String, Double[]> vectorOneX = new HashMap<>();  //之前网络的embedding
    public static Map<String, Double[]> vectorTwoX = new HashMap<>();  //新增节点随机初始化的embedding
    public static Map<String, Set<String>> neighborX = new HashMap<>(); //邻居节点集
    public static Map<String, Map<String, Double>> pointProductX = new HashMap<>();  //点积
    public static Map<String, Map<String, Double>> attnResultX = new HashMap<>();   //attn结果
    public static Map<String, Map<String, Double[]>> addResultX = new HashMap<>();  //向量和
    public static Map<String, Map<String, Double>> resultX = new HashMap<>();      //监督学习值
    public static Map<String, Map<String, Double>> supeprvisedResultX = new HashMap<>(); //softmax后监督学习值
    static List<String[]> edge_weightX = new ArrayList<>();                        //边权值
    public static Map<String, Double[]> newEmbeddingX = new HashMap<>();          //新增节点结果集


    public static String networkNameY = "_twitter";
    public static List<String> pointY = new ArrayList<>();
    public static Map<String, Double[]> vectorOneY = new HashMap<>();
    public static Map<String, Double[]> vectorTwoY = new HashMap<>();
    public static Map<String, Set<String>> neighborY = new HashMap<>(); //邻居节点集
    public static Map<String, Map<String, Double>> pointProductY = new HashMap<>();  //点积
    public static Map<String, Map<String, Double>> attnResultY = new HashMap<>();   //attn结果
    public static Map<String, Map<String, Double[]>> addResultY = new HashMap<>();
    public static Map<String, Map<String, Double>> resultY = new HashMap<>();
    public static Map<String, Map<String, Double>> supeprvisedResultY = new HashMap<>(); //监督学习值
    static List<String[]> edge_weightY = new ArrayList<>();
    public static Map<String, Double[]> newEmbeddingY = new HashMap<>();

    public static Set<String> set;
    /**
     * 初始化参数
     * @param fileName
     */
    public static void initParameter(FileName fileName,IONE2 test) throws IOException {

        randomValue.readFile(fileName.pointFilenameX, pointX, networkNameX);
        randomValue.RandomEmbedding(pointX, vectorTwoX);
        randomValue.readFile(fileName.pointFilenameY, pointY, networkNameY);
        randomValue.RandomEmbedding(pointY, vectorTwoY);

        // System.out.println("-------------attention---------");
        Attention.readResultSetFile(fileName.filenameX, vectorOneX);
        Attention.readNeighborFile(fileName.edgeFileNameX, neighborX, networkNameX, pointX);
        Attention.product(neighborX, vectorOneX, vectorTwoX, pointProductX);

        supervisedAnchorLearning.readFile(fileName.weightFilenameX, edge_weightX);
        supervisedAnchorLearning.product(neighborX, vectorOneX, vectorTwoX, addResultX);
        supervisedAnchorLearning.SuperResult(addResultX, resultX);
        supervisedAnchorLearning.SoftmaxResult(resultX, supeprvisedResultX);

        Attention.attentions(pointProductX, attnResultX);

      /*  supeprvisedResultX=Trans(edge_weightX,networkNameX,pointX);*/

        test.resultMap(test.resultx,attnResultX,supeprvisedResultX);
        Attention.dataHandle(test.resultx, 5);
        Attention.fileHandle(fileName.edgeFileNameX, fileName.outputNewEdgeFileX, test.resultx, networkNameX);




        Attention.readResultSetFile(fileName.filenameY, vectorOneY);
        Attention.readNeighborFile(fileName.edgeFileNameY, neighborY, networkNameY, pointY);
        Attention.product(neighborY, vectorOneY, vectorTwoY, pointProductY);

        supervisedAnchorLearning.readFile(fileName.weightFilenameY, edge_weightY);
        supervisedAnchorLearning.product(neighborY, vectorOneY, vectorTwoY, addResultY);
        supervisedAnchorLearning.SuperResult(addResultY, resultY);
        supervisedAnchorLearning.SoftmaxResult(resultY, supeprvisedResultY);

        Attention.attentions(pointProductY, attnResultY);

     /*  supeprvisedResultY=Trans(edge_weightY,networkNameY,pointY);*/

        test.resultMap(test.resulty,attnResultY,supeprvisedResultY);
        Attention.dataHandle(test.resulty, 5);
        Attention.fileHandle(fileName.edgeFileNameY, fileName.outputNewEdgeFileY, test.resulty, networkNameY);

       // Test.bbb(fileName);

        //  System.out.println("------------new embedding------------");
        NewEmbeddding.newEmb(attnResultX, supeprvisedResultX, vectorOneX, vectorTwoX, newEmbeddingX);
        NewEmbeddding.newEmb(attnResultY, supeprvisedResultY, vectorOneY, vectorTwoY, newEmbeddingY);
        NewEmbeddding.output(fileName.outputFileX, newEmbeddingX);
        NewEmbeddding.output(fileName.outputFileY, newEmbeddingY);

       set = readFile(fileName.outputNewEdgeFileX,fileName.outputNewEdgeFileY,fileName.anchorFilename);
      // set = readFile(fileName.edgeFileNameX, fileName.edgeFileNameY, fileName.anchorFilename);
        System.out.println("anchor:"+set.size()+set);
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();         //计时
        //System.out.println("--------------random init vector-------------");
        FileName fileName = new FileName(5);
        IONE2 test = new IONE2();
        initParameter(fileName,test);

        test.resultMap(test.resultx,attnResultX,supeprvisedResultX);
        test.resultMap(test.resulty,attnResultY,supeprvisedResultY);
        //t2时刻边
        HashMap<String, String> lastfm_anchor2 = test.getNetworkAnchors(set, networkNameX, networkNameY);
        HashMap<String, String> myspace_anchor2 = test.getNetworkAnchors(set, networkNameY, networkNameX);
       // HashMap<String, String> lastfm_anchor2 = test.getNetworkAnchors(5, networkNameX, networkNameY);
       // HashMap<String, String> myspace_anchor2 = test.getNetworkAnchors(5, networkNameY, networkNameX);

        Map<String, IONE2Update> train2 = test.Train(5, 100000, "",
                100, lastfm_anchor2, myspace_anchor2,fileName);

        // int index = 0;
        train2.get("x");//  index++;
        // System.out.println(index);
        //System.out.println(train2.get("x").answer.size());

        integration.integrate(vectorOneX, mapToMap(train2.get("x").answer));
        integration.writeFile(fileName.outputFilenameX, vectorOneX);
        integration.integrate(vectorOneY, mapToMap(train2.get("y").answer));
        integration.writeFile(fileName.outputFilenameY, vectorOneY);


        long end = System.currentTimeMillis();
        System.out.println("time: "+(end - start) / 1000);
    }

    private static Map<String, Double[]> mapToMap(Map<String, double[]> mapD) {
        Map<String, Double[]> map = new HashMap<>();
        for (String key : mapD.keySet()) {
            Double[] doubles = new Double[mapD.get(key).length];
            int index = 0;
            for (double d : mapD.get(key)) {
                doubles[index] = d;
                index++;
            }
            map.put(key, doubles);
        }
        integration.integrate(vectorOneX, map);
        return map;
    }

    public static Set<String> readFile(String filename1, String filename2, String filename3) throws IOException {
        List<String[]> listX = new ArrayList<>();
        List<String[]> listY = new ArrayList<>();
        List<String> result1 = new ArrayList<>();
        List<String> result2 = new ArrayList<>();
        Set<String> result = new HashSet<>();
        List<String> anchor = new ArrayList<>();
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

        for (String[] str : listX) {
            if (anchor.contains(str[0])) {
                result1.add(str[0]);
            }
            if (anchor.contains(str[1])) {
                result1.add(str[1]);
            }
        }
        for (String[] str1 : listY) {
            if (anchor.contains(str1[0])) {
                result2.add(str1[0]);
            }
            if (anchor.contains(str1[1])) {
                result2.add(str1[1]);
            }
        }

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
        return result;
    }

    public static Map<String, Map<String, Double>> Trans(List<String[]> edge_weight,String networkName,List<String> point){
        Map<String, Map<String, Double>> result = new HashMap<>();
        for(String[] Strings:edge_weight){
            if (point.contains(Strings[0]+networkName)) {
                if (result.containsKey(Strings[0] + networkName)) {
                    result.get(Strings[0] + networkName).put(Strings[1] + networkName, Double.valueOf(Strings[2]));
                } else {
                    Map<String, Double> map = new HashMap<>();
                    map.put(Strings[1] + networkName, Double.valueOf(Strings[2]));
                    result.put(Strings[0] + networkName, map);
                }
            }
            if (point.contains(Strings[1]+networkName)) {
                if (result.containsKey(Strings[1] + networkName)) {
                    result.get(Strings[1] + networkName).put(Strings[0] + networkName, Double.valueOf(Strings[2]));
                } else {
                    Map<String, Double> map = new HashMap<>();
                    map.put(Strings[0] + networkName, Double.valueOf(Strings[2]));
                    result.put(Strings[1] + networkName, map);
                }
            }

        }
        return result;

    }
}
