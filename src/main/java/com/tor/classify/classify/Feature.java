package com.tor.classify.classify;

public class Feature {

    ClassifyFeatures classifyFeatures = new ClassifyFeatures();
    AlgorithmUtil algorithmUtil = new AlgorithmUtil();
    ArffUtil arffUtil = new ArffUtil();
    /**
     * 读取保存的模型信息
     * @param trainFilePath:作为训练集的数据路径
     * @param traindelete:保存剩余特征的.arff文件路径
     * @param feature_txt:保存剩余特征的.txt文件路径，以便随后进行展示
     * @param featureSelectAlgorithm：前端选择的提取特征的算法
     * @throws Exception
     */
    public void show_Feature(String trainFilePath,String traindelete, String feature_txt,String featureSelectAlgorithm)
    {
      /*
		 	选择的特征
		 	参数1：csv训练文件路径
		 	参数2：使用的特征选择方法

		 */
        String selectFeatures;
        try {
            //下面是调用算法得到一个训练集的剩下的特征。 字符串。
            selectFeatures = classifyFeatures.getClassifyFeature(trainFilePath,featureSelectAlgorithm);
            //将选择的特征存入文本文件.txt：一个训练集对应一个自己的文件名+Feature.txt文件。存在quic/model中。
            algorithmUtil.saveFeatures(selectFeatures,feature_txt);
            //对csv训练集保留选择的特征，删除多余的特征，并存储为.arff文件。一个训练集对应一个自己的文件名+Feature.arff文件。
            arffUtil.delete(trainFilePath, selectFeatures, traindelete);
            System.out.println("特征提取成功结束！");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
