package FinalModel;

import sun.awt.windows.WPrinterJob;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class supervisedAnchorLearning {
    public static String inputResultSet1FilenameX = "D:\\论文\\database\\foursquare\\emb2\\four.IONEembedding.1.num";
    public static String inputResultSet2FilenameX = "D:\\论文\\database\\foursquare\\emb2\\foursquare.newNodeIONEtop5.2";
    public static String inputEdgeFileNameX = "D:\\论文\\database\\foursquare\\新增边集\\edge2.number";
    public static String inputNewPointFilenameX = "D:\\论文\\database\\foursquare\\新增点集\\following-2";

    public static String inputResultSet1FilenameY = "D:\\论文\\database\\twitter\\emb2\\twitter.INEembeddingtop5.4up.100000";
    public static String inputResultSet2FilenameY = "D:\\论文\\database\\twitter\\emb2\\twitter.newNodeIONEtop5.2";
    public static String inputEdgeFileNameY = "D:\\论文\\database\\twitter\\新增边集\\edge2.number";
    public static String inputNewPointFilenameY = "D:\\论文\\database\\twitter\\新增点集\\following-2";


    static String inputFilenameY = "D:\\论文\\database\\twitter\\weight\\weight2.number";
    static String inputFilenameX = "D:\\论文\\database\\foursquare\\weight\\weight2.number";
    public static String outputResultFilenameX = "D:\\论文\\database\\foursquare\\supervised\\Supervised_f2.number";
    public static String outputResultFilenameY = "D:\\论文\\database\\twitter\\supervised\\Supervised_t2.number";

    public static String networkNameX = "_foursquare";
    public static String networkNameY = "_twitter";

    static Double[] W = new Double[100];
    //static double[]W1= {0.14872274794994975,0.07210941906365492,0.012832246824571134,0.03277857343542416,0.1114952455532834,-0.0883011234514454,-0.10684036312343609,-0.04992224296923818,-0.012559598257886929,-0.03210333674929783,0.02507185395460209,0.010564107558411669,-0.047460829285463954,-0.0014444376988379266,-0.14786330653781515,0.08334578294979816,0.07591991768307504,-0.054730485709719535,0.13930940369443381,-0.034252811124290704,0.05978663230570758,-0.01643907137271951,-0.034956818176224515,-0.26604729230721436,0.22850111351393707,-0.013405054003459463,-0.0067310662366818425,0.049866009642216776,0.010758280594681143,0.04372963530878155,-0.07251808760525161,0.10353193100320922,0.0902635008988943,-0.11639061737220367,-0.18517820096156812,-0.10220933015923982,-0.13239609311822212,0.09302922819564108,-0.15583383638520507,-0.06414667289611706,-0.21894251405727935,0.05864516236485942,0.1286499473937149,0.08235184389376374,-0.15294475969320964,0.06458452494623597,-0.07793756755805374,0.06949283601471533,-0.06424541021273632,0.08448980290826187,0.04663376186072223,0.02064082363415234,0.06839731395359486,-0.04663027317451643,-0.004605640615669274,-0.11568917255676744,0.0171269357175054,0.014725602488810273,0.005245421530062947,-0.04514083717573522,0.09478557973011646,-0.07363588395676378,-0.03345696845518712,-0.016765486052646984,0.06682023178172923,0.04413268639741445,0.0021600850135764698,0.08941423248961927,-0.06679820820553381,0.009072359660553671,0.02400289052729738,-0.19446344463630444,0.04462450031245692,0.14251392138380822,0.08095098090263748,-0.0554136299587663,-0.20602745821503124,-0.09644275990515579,0.03601460626606823,0.0074335713471209534,0.0327048791475477,-0.025924696538317072,-0.08463945891292766,-0.07421847043537143,-0.027230133878693188,-0.10252961182568954,-0.07037590935120201,0.18982862749924614,-0.03148903625398059,0.054794818628345156,-0.04355937094902341,0.08611233430231101,0.01308146334105783,-0.052215868074936396,0.1202157761868215,-0.2384205146037306,0.07685611484078052,-0.13877148674919978,0.16316804094105122,0.07237087463772868};
    static Double[] W2 = {-0.006075090805285055, 0.010143915707726167, 0.015898165041302446, -0.0024293673107685034, 0.004803243689708872, 0.005988327852176812, 0.0028411516880405665, -0.009127445902836408, 0.0034131117348376655, 0.009981353604963258, -0.008586602356797727, 0.00798288252169494, -0.00457277175285172, -0.006719205767745384, -5.527215587433122E-4, -0.0023261749311605986, -0.010592455060456814, -0.005320123401443965, 0.007348063725239936, 0.00910237954089227, -0.002218545206140514, 0.009044172797494629, -0.0022391501678985207, -0.006604668256736957, 6.675000728363024E-5, -0.0019318412947192055, -3.7420517069505896E-4, 0.007121304315142937, 0.0028722669526146266, 0.007517003081909176, -0.00456917397850989, -0.011918740562250538, 0.02147090778521874, 0.011858486699045025, 0.017193235387594697, -0.002062856895398732, -0.004191089942002282, 0.019019787888956342, -0.00928609848564641, -0.01053089532085946, 0.011667648912051319, 0.008702595030239074, 0.004443246880327552, 0.0086393320731873, -0.02031477540102775, 0.002622626893216368, -0.009443826078468887, 0.002549789523985035, -0.006404640612694865, -0.006258428424541827, 0.0065659306425181665, -0.008686211543738399, 0.004152832508850084, -0.02322812931659805, 0.00968995910574523, -0.01211080533045782, -8.999877363505693E-4, 0.01819928493044481, 0.005493792686960031, -0.011772681708437534, -8.950508259778842E-4, 0.006319369197725354, 0.007851714813787625, 0.0012105884361425, 0.007778436946043682, 0.006131175459987425, 0.009019363792498401, 0.0018801633282659767, -0.0034271884952912723, 0.0026621947406904344, 0.011160783263023014, 0.0015816243869710817, -0.008900926456450464, 2.924023783849783E-4, 0.010442894732156012, -0.02072187588874995, 0.001223054592765473, 0.001181440554972946, 0.010645223877681227, 0.0010335370469723361, 0.001094879840107551, 0.014652006286547673, -0.0022225280470895194, 0.004687871542423605, -0.011071260108733787, -0.01627979512875823, -0.008298093687176038, 0.003135111523020602, -0.002536907547851525, -7.367891394128418E-4, 2.302177884889899E-5, 2.757011873362265E-4, -0.001598544062168235, 0.004496509693261394, 0.006555567721312378, -0.0026238975502509087, 0.0018947524251112845, -0.0030181594072190674, 0.0046107678321363, 0.016476517873232212};
    /*第一次的结果集*/
    public static Map<String, Double[]> vectorOneX = new HashMap<>();
    /*第二次的结果集*/
    public static Map<String, Double[]> vectorTwoX = new HashMap<>();
    /*源结点-邻居结点*/
    public static Map<String, Set<String>> neighborX = new HashMap<>();
    /*向量和*/
    public static Map<String, Map<String, Double[]>> addResultX = new HashMap<>();
    /*源节点*/
    public static List<String> newAddEdgeX = new ArrayList<>();
    static List<String[]> edge_weightX = new ArrayList<>();
    public static Map<String, Map<String, Double>> supeprvisedResultX = new HashMap<>();
    public static Map<String, Map<String, Double>> resultX = new HashMap<>();

    public static Map<String, Double[]> vectorOneY = new HashMap<>();
    /*第二次的结果集*/
    public static Map<String, Double[]> vectorTwoY = new HashMap<>();
    /*源结点-邻居结点*/
    public static Map<String, Set<String>> neighborY = new HashMap<>();
    /*向量和*/
    public static Map<String, Map<String, Double[]>> addResultY = new HashMap<>();
    /*源节点*/
    public static List<String> newAddEdgeY = new ArrayList<>();
    static List<String[]> edge_weightY = new ArrayList<>();
    public static Map<String, Map<String, Double>> supeprvisedResultY = new HashMap<>();
    public static Map<String, Map<String, Double>> resultY = new HashMap<>();


    //读文件
    public static void readFile(String filename1, List<String[]> edge_weight) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename1));
        String temp_string = br.readLine();
        while (temp_string != null) {
            String[] array = temp_string.split("\\s+");
            edge_weight.add(array);
            temp_string = br.readLine();
        }
        br.close();
    }

    public static void readResultSetFile(String filename1, Map<String, Double[]> vectorTwo2) throws IOException {
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
            vectorTwo2.put(strings[0], v);
            temp = br.readLine();
        }
        br.close();
    }

    public static void readNewAddEdgeFile(String filename1, List<String> list, String networkName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename1));
        String temp = br.readLine();
        while (temp != null) {
            list.add(temp + networkName);
            temp = br.readLine();
        }
        br.close();
    }

    public static void readNeighborFile(String filename1, Map<String, Set<String>> map, String networkName, List<String> newAddEdge) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename1));
        String temp = br.readLine();
        while (temp != null) {
            String[] strings = temp.split("\\s");
            if (newAddEdge.contains(strings[0] + networkName)) {
                if (map.containsKey(strings[0] + networkName)) {
                    map.get(strings[0] + networkName).add(strings[1] + networkName);
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(strings[1] + networkName);
                    map.put(strings[0] + networkName, set);
                }
            }
            if (newAddEdge.contains(strings[1] + networkName)) {
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


    public static void writeFile(String filename, Map<String, Map<String, Double>> result) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(
                filename));
        for (Entry<String, Map<String, Double>> entry : result.entrySet()) {
            StringBuilder outputString = new StringBuilder();
            String outputKey = entry.getKey();
            outputString.append(outputKey).append(",");
            Map<String, Double> outputMap = entry.getValue();
            for (Map.Entry<String, Double> mapEntry : outputMap.entrySet()) {
                String key = mapEntry.getKey();
                Double value = mapEntry.getValue();
                outputString.append(key).append(":");
	                /*for(int i=0;i<100;i++) {
	                	value1=W2[i]*value[i];
	                }*/
                outputString.append(value).append("\t");
            }
            outputString.append("\n");
            bw.write(String.valueOf(outputString));
        }
        bw.flush();
        bw.close();
    }


    public static void product(Map<String, Set<String>> neighbor, Map<String, Double[]> vectorOne, Map<String, Double[]> vectorTwo, Map<String, Map<String, Double[]>> pointProduct) {
        for (Map.Entry<String, Set<String>> entry : neighbor.entrySet()) {
            String key = entry.getKey();
            Set<String> value = entry.getValue();
            Double[] keyV = vectorTwo.get(key);
            Double[] valueV;
            /*key-v的和*/
            Map<String, Double[]> map = new HashMap<>();
            for (String str : value) {
                if (neighbor.containsKey(str)) {
                    valueV = vectorTwo.get(str);
                } else {
                    valueV = vectorOne.get(str);
                }
                /*和*/
                if (valueV == null) {
                    System.out.println(str.substring(0, 4));
                    continue;
                }
                Double[] vkvv = new Double[100];
                for (int i = 0; i < keyV.length; i++) {
                    vkvv[i] = keyV[i] + valueV[i];
                }

                map.put(str, vkvv);
            }
            pointProduct.put(key, map);
        }
    }

    //监督学习y=Wx,W为100维向量
    public static void supervisedLearning(String networkName, Map<String, Map<String, Double[]>> pointProduct, List<String[]> edge_weight) {

        Double lr = 0.001;//学习率
        int iteration = 10000;

        //W向量初始化
        for (int j = 0; j < 100; j++) {
            W[j] = 0.0;
        }
        for (int i = 1; i < iteration; i++) {
            Double[] w_grad = new Double[100];
            for (int j = 0; j < 100; j++) {
                w_grad[j] = 0.0;
            }

            String[] keys1 = pointProduct.keySet().toArray(new String[0]);
            Random random = new Random();
            String randomkey = keys1[random.nextInt(keys1.length)];

            Map<String, Double[]> strings = pointProduct.get(randomkey);

            String[] keys2 = strings.keySet().toArray(new String[0]);
            Random random2 = new Random();
            String randomkey2 = keys2[random2.nextInt(keys2.length)];

            Double[] x = strings.get(randomkey2);
            int y = -1;
            for (String[] string1 : edge_weight) {
                boolean flag1 = (string1[0] + networkName).equals(String.valueOf(randomkey)) && (string1[1] + networkName).equals(String.valueOf(randomkey2));
                //boolean flag2=(string1[1]+networkName).equals(String.valueOf(randomkey)) && (string1[0]+networkName).equals(String.valueOf(randomkey2));
                if (flag1) {
                    y = Integer.parseInt(string1[2]);
                }
            }
            for (int j = 0; j < 100; j++) {
                w_grad[j] = w_grad[j] - (y - W[j] * x[j]) * x[j];
            }

            for (int j = 0; j < 100; j++) {
                W[j] = W[j] - lr * w_grad[j];
            }


        }
        System.out.print("W:[ ");
        for (double x : W) {
            System.out.print(x + ",");
        }
        System.out.println();

    }

    public static void SuperResult(Map<String, Map<String, Double[]>> pointProduct, Map<String, Map<String, Double>> supeprvisedResult) {
        for (Entry<String, Map<String, Double[]>> entry : pointProduct.entrySet()) {
            Map<String, Double[]> outputMap = entry.getValue();
            String outputKey = entry.getKey();
            Map<String, Double> re = new HashMap<>();
            for (Map.Entry<String, Double[]> mapEntry : outputMap.entrySet()) {
                String key1 = mapEntry.getKey();
                Double[] value = mapEntry.getValue();
                Double value1 = 0.0;
                for (int i = 0; i < 100; i++) {
                    value1 = W2[i] * value[i];
                }

                re.put(key1, value1);

            }
            supeprvisedResult.put(outputKey, re);
        }
    }

    public static void SoftmaxResult(Map<String, Map<String, Double>> supeprvisedResult, Map<String, Map<String, Double>> result) {
        for (Map.Entry<String, Map<String, Double>> entry : supeprvisedResult.entrySet()) {
            double denominator = 0;
            Map<String, Double> value = entry.getValue();
            for (Map.Entry<String, Double> map : value.entrySet()) {
                denominator += Math.exp((map.getValue() * 100));
            }
            double a = 0;

            /*一次结果*/
            Map<String, Double> doubleMap = new HashMap<>();
            for (Map.Entry<String, Double> map : value.entrySet()) {
                double re = Math.exp((map.getValue() * 100)) / denominator;
                a += re;
                doubleMap.put(map.getKey(), re);
            }
            //System.out.println(a);
            result.put(entry.getKey(), doubleMap);
        }
    }


    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        readResultSetFile(inputResultSet1FilenameX, vectorOneX);
        readResultSetFile(inputResultSet2FilenameX, vectorTwoX);
        readNewAddEdgeFile(inputNewPointFilenameX, newAddEdgeX, networkNameX);
        readNeighborFile(inputEdgeFileNameX, neighborX, networkNameX, newAddEdgeX);
        product(neighborX, vectorOneX, vectorTwoX, addResultX);
        readFile(inputFilenameX, edge_weightX);
        //supervisedLearning(networkNameX,addResultX,edge_weightX);
        SuperResult(addResultX, supeprvisedResultX);
       // SoftmaxResult(supeprvisedResultX, resultX);
      //  writeFile(outputResultFilenameX, resultX);

       /* readResultSetFile(inputResultSet1FilenameY, vectorOneY);
        readResultSetFile(inputResultSet2FilenameY, vectorTwoY);
        readNewAddEdgeFile(inputNewPointFilenameY, newAddEdgeY, networkNameY);
        readNeighborFile(inputEdgeFileNameY, neighborY, networkNameY, newAddEdgeY);
        product(neighborY, vectorOneY, vectorTwoY, addResultY);
        readFile(inputFilenameY, edge_weightY);
        SuperResult(addResultY, supeprvisedResultY);
        SoftmaxResult(supeprvisedResultY, resultY);

        writeFile(outputResultFilenameY, resultY);
*/
        long end = System.currentTimeMillis();
        System.out.println("time: "+(end - start) / 1000);

        //检测监督学习效果 算MSE MEA值
		    double mse=0;
	        double mea=0;
	        for(int i=0;i<100;i++) {
                String[] keys1 = addResultX.keySet().toArray(new String[0]);
                Random random = new Random();
                String randomkey = keys1[random.nextInt(keys1.length)];

                Map<String, Double[]> strings = addResultX.get(randomkey);

                String[] keys2 = strings.keySet().toArray(new String[0]);
                Random random2 = new Random();
                String randomkey2 = keys2[random2.nextInt(keys2.length)];

                Double[] x = strings.get(randomkey2);
                double z = 0;
                for (int k = 0; k < 100; k++) {
                    z += W2[k] * x[k];
                }
                System.out.println(randomkey+"-"+randomkey2+":"+z);
  				int y=0;
  				for (String[] string1 :edge_weightX){
  					boolean flag1=(string1[0]+networkNameX).equals(String.valueOf(randomkey)) && (string1[1]+networkNameX).equals(String.valueOf(randomkey2));
  					boolean flag2=(string1[1]+networkNameX).equals(String.valueOf(randomkey)) && (string1[0]+networkNameX).equals(String.valueOf(randomkey2));
  					if(flag1||flag2) {
  						y=Integer.parseInt(string1[2]);
  					}
  				}
  				double l=Math.pow((z-y),2);
  				double l2=Math.abs((z-y));

  				mse=l+mse;
  				mea=l2+mea;

	        }
	        mse=mse/1000;
	        mea=mea/1000;
	       
	       System.out.println("mse:"+mse);
	       System.out.println("mae:"+mea);



            }

}
