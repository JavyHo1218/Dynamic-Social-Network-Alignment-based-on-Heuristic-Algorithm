package FinalModel;

import dyn.ModelWith2OrderNorm.BasicUnit;
import utils.FileName;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IONE2 {

    //network x as the foursquare, network y as the twitter

    int foldtrain = 9;

	/*public IONE(int foldtrain)
	{
		this.foldtrain=foldtrain;
	}*/

    public HashMap<String, String> getNetworkAnchors(Set<String> set, String postfix_1, String postfix_2) throws IOException {
    // public HashMap<String, String> getNetworkAnchors(int index, String postfix_1, String postfix_2) throws IOException {

        HashMap<String, String> answer_map = new HashMap<String, String>();
        // String file_name = "D:\\论文代码\\数据集\\twitter\\锚定节点集\\groundtruth.9.foldtrain.train.number";
        /*读第几次的锚定结点*/
        //String file_name=Vars.twitter_dir+"twitter_foursquare_groundtruth/newAnchor"+index+".number";
        /*String file_name="D:\\论文\\database\\anchor\\7\\anchor"+index+".IONE.number";
        BufferedReader br = BasicUnit.readData(file_name);
        String temp_string = br.readLine();
        while (temp_string != null) {
            answer_map.put(temp_string + postfix_1, temp_string + postfix_2);
            temp_string = br.readLine();
        }
*/

       for(String s :set){
            answer_map.put(s + postfix_1, s + postfix_2);
        }

        return answer_map;

    }


    public Map<String, IONE2Update> Train(int index, int total_iter, String fileInterop,
                                          int dim,
                                          HashMap<String, String> lastfm_anchor,
                                          HashMap<String, String> myspace_anchor,
                                          FileName fileName) throws IOException {
        String emb_file1x = fileName.filenameX;
        String emb_file2x = fileName.outputFileX;

        String emb_file1y = fileName.filenameY;
        String emb_file2y = fileName.outputFileY;

        //String networkx_file = fileName.edgeFileNameX;
        String networkx_file = fileName.outputNewEdgeFileX;
        String ouput_filename_networkx ="D:\\论文\\database\\foursquare\\emb_train\\9\\four.IONE2embedding"+ fileInterop + index;

        //String networky_file = fileName.edgeFileNameY;
        String networky_file =fileName.outputNewEdgeFileY;
        String ouput_filename_networky =
                                 "D:\\论文\\database\\twitter\\emb_train\\9\\tw.IONE2embedding" + fileInterop + index;

        IONE2Update twoOrder_x =
                new IONE2Update(dim, networkx_file, "foursquare", emb_file1x, emb_file2x,resultx);
        twoOrder_x.init();

        IONE2Update twoOrder_y =
                new IONE2Update(dim, networky_file, "twitter", emb_file1y, emb_file2y,resulty);
        twoOrder_y.init();

//        HashMap<String, String> lastfm_anchor = getNetworkAnchors("_foursquare", "_twitter");
//        HashMap<String, String> myspace_anchor = getNetworkAnchors("_twitter", "_foursquare");
        System.out.println(lastfm_anchor.size());
        System.out.println(myspace_anchor.size());

        for (int i = 0; i < total_iter; i++) {
            //TwoOrder.Train(i, total_iter);
            twoOrder_x.Train(i, total_iter, twoOrder_x.answer,
                    twoOrder_x.answer_context_input,
                    twoOrder_x.answer_context_output,
                    lastfm_anchor);
            twoOrder_y.Train(i, total_iter, twoOrder_x.answer,
                    twoOrder_x.answer_context_input,
                    twoOrder_x.answer_context_output,
                    myspace_anchor);
            if ((i + 1) % 100000 == 0) {
                twoOrder_x.output(ouput_filename_networkx + "." + dim + "_dim" + "." + (i + 1));
                twoOrder_y.output(ouput_filename_networky + "." + dim + "_dim" + "." + (i + 1));
            }
        }
        Map<String, IONE2Update> result = new HashMap<>();
        result.put("x", twoOrder_x);
        result.put("y", twoOrder_y);
        return result;
        //TwoOrder.output(output_file_name);
    }

    public Map<String, Map<String, Double>> resultx = new HashMap<>();
    public Map<String, Map<String, Double>> resulty = new HashMap<>();


    public void resultMap(Map<String, Map<String, Double>> result,
                                 Map<String, Map<String, Double>> attention,
                                 Map<String, Map<String, Double>> supervise) {
        for (Map.Entry<String, Map<String, Double>> entry : attention.entrySet()) {
            String mapKey = entry.getKey();
            Map<String, Double> mapValue = entry.getValue();
            Map<String, Double> map = new HashMap<>();
            for (Map.Entry<String, Double> entry1 : mapValue.entrySet()) {
                String entry1Key = entry1.getKey();
                Double entry1Value = entry1.getValue();
                entry1Value += supervise.get(mapKey).get(entry1Key);
                map.put(entry1Key, entry1Value);
            }
            result.put(mapKey, map);
        }
    }

}

