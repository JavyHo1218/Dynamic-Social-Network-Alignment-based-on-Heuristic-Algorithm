package utils;

import java.io.*;


public  class Test {
    public static void bbb(FileName fileName) throws IOException {

        String filenameX1=fileName.outputNewEdgeFileX;
        String filenameX2="C:\\Users\\javy\\Desktop\\1\\f5.txt";
        aaa(filenameX1,filenameX2);

        String filenameY1=fileName.outputNewEdgeFileY;
        String filenameY2="C:\\Users\\javy\\Desktop\\1\\t5.txt";
        aaa(filenameY1,filenameY2);


    }
    private static void  aaa(String filename1,String filename2) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename1));
        String temp = br.readLine();
        StringBuilder newFile = new StringBuilder();
        while (temp != null) {
            if (!temp.equals("")){
                newFile.append(temp).append("\n");
            }
            temp = br.readLine();
        }
        br.close();
        BufferedReader br1 = new BufferedReader(new FileReader(filename2));
        String temp1 = br1.readLine();
        while (temp1 != null) {
            if (!temp1.equals("")){
                newFile.append(temp1).append("\n");
            }
            temp1 = br1.readLine();
        }
        br1.close();
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename1));
        bw.write(String.valueOf(newFile));
        bw.flush();
        bw.close();
    }
}
