package com.tor.classify.classify;

import com.csvreader.CsvReader;
import com.tor.classify.domain.Model;
import com.tor.classify.domain.Train;
import com.tor.classify.util.PropertiesUtil;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.*;
import weka.classifiers.trees.*;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class AlgorithmUtil {
    private Model model=new Model();
    ArffUtil arffUtil = new ArffUtil();
    public Model getModel()
    {
        return this.model;
    }

    /**
     * 将模型选择得到的特征存入文本文件
     *
     * @param features:本模型选择的特征
     * @param feature_txt:保存的txt文件地址。
     * @throws Exception
     */
    public void saveFeatures(String features, String feature_txt) {
        File f = null;
        f = new File(feature_txt);
        FileWriter fw;
        try {
            fw = new FileWriter(f);
            fw.write(features);//将字符串写入到指定的路径下的文件中
            fw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * 读取csv文件中的全部内容
     */
    public ArrayList readCsv(String csvPath) throws IOException {
        ArrayList csvList = new ArrayList<String[]>();
        /*
        String csvFilePath = csvPath;
        CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));//csv是以逗号进行分隔。
        while (reader.readRecord()) {
            csvList.add(reader.getValues());
        }
        reader.close();
        //return csvList;

         */
        DataInputStream EquipmentIn = new DataInputStream(new FileInputStream(new File(csvPath)));
        BufferedReader EquipmentBr = new BufferedReader(new InputStreamReader(EquipmentIn, "GBK"));
        CsvReader csvReaderEquipment = new CsvReader(EquipmentBr);
        // 读表头
        //csvReaderEquipment.readHeaders();
        while (csvReaderEquipment.readRecord()) {
            // 读一整行
            csvList.add(csvReaderEquipment.getRawRecord());
        }
        System.out.println(csvList);
        return csvList;
    }

    /**
     * 读取文本文件中保存的特征
     *
     * @param filePath:存储特征文件的路径
     * @return
     */
    public String readFeature(String filePath) throws IOException {

        StringBuilder result = new StringBuilder();
        FileReader reader = new FileReader(filePath);//构造一个BufferedReader类来读取文件
        BufferedReader br = new BufferedReader(reader);
        String s = null;
        while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
            result.append(s);
        }
        br.close();
        reader.close();
        return result.toString();
    }

    /**
     * 读取保存的模型信息
     *
     * @param infoFilePath:模型信息文件路径
     * @return
     * @throws Exception
     */
    public String readModelInfo(String infoFilePath) throws Exception {
        FileReader fileReader = new FileReader(infoFilePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        String info = stringBuilder.toString();
        info = info.replace(',', ' ').substring(1, info.length() - 2);
        bufferedReader.close();
        fileReader.close();
//		System.out.println(info);
        return info;
    }

    /**
     * 将模型信息写入.model文件，并存入新的一个对象model，以便存入数据库。
     * 将训练好的模型保存
     * @param classifier:分类器
     * @param train：存储着模型信息的对象
     * @throws Exception
     */
    public void saveModel(Classifier classifier, Train train) throws Exception
    {
        String model_inforpath = train.getModel_infor();
        SerializationHelper.write(model_inforpath, classifier);//保存和加载分类器模型参数
        String modelname1 = train.getModelname();
        String result1 = train.getResultpath_txt();
        String train_filename1 = train.getTrainname();
        String feature1  ="C:/Users/96937/IdeaProjects2/model/" + train_filename1 + "Features"+ ".txt";
        model.setModelName(modelname1);
        model.setFeaturePath(feature1);
        model.setModelPath(model_inforpath);
        model.setModelInfo(result1);
    }


    /**
     *将模型的好坏矩阵写入 Infor.txt文件。
     * @param classifier:分类器
     * @param train：训练集
     * @param train :存放着模型数据的对象。
     * @throws Exception
     */
    public void saveModelInfo(Classifier classifier, Instances train1, Train train) throws Exception {
        File f = null;
        String result1 = train.getResultpath_txt();
        f = new File(result1);
        FileWriter fw;
        try {
            fw = new FileWriter(f);
            fw.write(crossValidateModel(classifier, train1).toString());//将模型信息写入文件
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 进行十折交叉验证，评估分类模型
     * @param classifier:分类器
     * @param train：训练集
     * @throws Exception
     */
    private ArrayList<String> crossValidateModel(Classifier classifier, Instances train) throws Exception {
        ArrayList<String> info = new ArrayList<String>();
        Evaluation eval = null;
        for (int i = 0; i < 10; i++) {
            eval = new Evaluation(train);
            eval.crossValidateModel(classifier, train, 10, new Random(i));//实现交叉验证模型,使用递归。
        }
        info.add(eval.toSummaryString());//总结信息
        info.add(eval.toClassDetailsString());//分类详细信息
        info.add(eval.toMatrixString());//分类的混淆矩阵
        return info;
    }

    /**
     * 使用保存的模型对测试集进行分类
     *
     * @param testPath：测试集路径
     * @param featurePath：分类器对应的特征文件路径
     * @param modelPath:分类器路径
     * @return
     * @throws Exception
     */
    public ArrayList<String> useModelclassify(String testname,String testPath, String modelPath, String featurePath) throws Exception {
        System.out.println(featurePath);
        String selectFeatures = readFeature(featurePath);//选中的模型中的feature信息。
        ArrayList<String> result = new ArrayList<String>();
        File file = new File(testPath);
        //转换之后的arff文件路径和名字
        String Featurepath_arff = PropertiesUtil.getFeatureArff()  + testname + "Feature.arff";
        //根据选中的模型中的特征，删除测试集中的多余特征，并将其保存在Featurepath_arff文件中。
        arffUtil.delete(testPath, selectFeatures, Featurepath_arff);
        //根据保存的Feature.arff文件和模型的地址文件进行计算。
        result = doClassify(Featurepath_arff, modelPath);
        return result;
    }

    /**
     * 使用保存的模型对测试集进行分类。
     *
     * @param testfeaturePath_arff；测试文件路径
     * @param modelPath：分类器路径
     * @return
     * @throws Exception
     */
    private ArrayList<String> doClassify(String testfeaturePath_arff, String modelPath) throws Exception {
        ConverterUtils.DataSource sourceTest = null;
        sourceTest = new ConverterUtils.DataSource(testfeaturePath_arff);
        Instances test = sourceTest.getDataSet();
        if (test.classIndex() == -1)
            // 设置关系为最后一列数据
            test.setClassIndex(test.numAttributes() - 1);
        ArrayList<String> result = new ArrayList<String>();
        //加载模型
        FilteredClassifier fc = (FilteredClassifier) weka.core.SerializationHelper.read(modelPath);
        System.out.println("加载的模型为：");
        System.out.println(fc);
        int sum = test.numInstances();
        // 输出预测结果
//		System.out.println(sum);
        for (int i = 0; i < sum; i++) {
            double pred = fc.classifyInstance(test.instance(i));
            //System.out.println(pred);1.0是notor 0.0是tor
            String predicted = test.classAttribute().value((int) pred);
            //System.out.println(predicted);输出的是tor notor
            // 将分类结果加入ArrayList
            result.add(predicted);
        }
        return result;//result 只是很多行数据的最后一列数值，tor or notor。是个 sum*1的数组。
    }
}
