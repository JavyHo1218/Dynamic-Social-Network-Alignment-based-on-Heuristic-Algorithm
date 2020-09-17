package utils;

/**
 * 文件名称
 */
public class FileName {

    /**
     * 文件前缀
     */
    private static String prefix = "D:\\论文\\database\\";

    /**
     * 新增点集
     */
    public String pointFilenameX;
    public String pointFilenameY;
    /**
     * 上一时刻算出来的embedding结果
     */
    public String filenameX;
    public String filenameY;

    /**
     * 输出文件
     */
    public String outputFilenameX;
    public String outputFilenameY;


    /**
     * 新增边集
     */
    public String edgeFileNameY;
    public String edgeFileNameX;

    /**
     * 输出需要更新的局部网络
     */
    public String outputNewEdgeFileY;
    public String outputNewEdgeFileX;
    /**
     * 输出新结点embedding
     */
    public String outputFileX;
    public String outputFileY;

    /**
     * 权重
     */
    public String weightFilenameX;
    public String weightFilenameY;


    /**
     *
     * @param index
     */
    public String anchorFilename;


    public FileName (int index){
        setFileNameThis(index);
        setFileNameLast(index-1);
    }


    /**
     * 设置第几次的文件
     * @param index
     */
    private void setFileNameThis(int index){
        pointFilenameX = prefix+"foursquare\\新增点集\\following-"+index;
        pointFilenameY = prefix+"twitter\\新增点集\\following-"+index;
        edgeFileNameY = prefix+"twitter\\新增边集\\edge"+index+".number";
        edgeFileNameX = prefix+"foursquare\\新增边集\\edge"+index+".number";
        outputNewEdgeFileY = prefix+"twitter\\新增边集\\edge"+index+".IONE.number";
        outputNewEdgeFileX = prefix+"foursquare\\新增边集\\edge"+index+".IONE.number";
        outputFileX = prefix+"foursquare\\emb_train\\8\\foursquare.newNodeIONEtop5."+index;
        outputFileY = prefix+"twitter\\emb_train\\8\\twitter.newNodeIONEtop5."+index;
        weightFilenameY = prefix+"twitter\\weight\\weight"+index+".number";
        weightFilenameX = prefix+"foursquare\\weight\\weight"+index+".number";
        outputFilenameX= prefix+"foursquare\\emb_train\\8\\four.IONEembedding."+index+".num";
        outputFilenameY= prefix+"twitter\\emb_train\\8\\tw.IONEembedding."+index+".num";
        anchorFilename = prefix + "anchor\\8\\anchor"+index+".number";

    }

    /**
     * 设置上一次的文件
     * @param index
     */
    private void setFileNameLast(int index){
        filenameX = prefix+"foursquare\\emb_train\\8\\four.IONEembedding."+index+".num";
        filenameY = prefix+"twitter\\emb_train\\8\\tw.IONEembedding."+index+".num";
    }
}
