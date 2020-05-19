package com.tor.classify.domain;

public class Train {
    private String method;//提取特征的算法
    private String trainname;
    private String trainpath;
    private String featurepath_arff;
    private String resultpath_txt;
    private String model_infor;
    private String modelname;
    public String getTrainname() { return trainname; }
    public void setTrainname(String trainname) { this.trainname = trainname; }
    public String getMethod(){return method;}
    public void setMethod(String method){this.method=method;}
    public String getTrainpath(){return trainpath;}
    public void setTrainpath(String trainpath){this.trainpath=trainpath;}
    public String getFeaturepath_arff(){return featurepath_arff;}
    public void setFeaturepath_arff(String featurepath_arff){this.featurepath_arff=featurepath_arff;}
    public String getResultpath_txt(){return resultpath_txt;}
    public void setResultpath_txt(String resultpath_txt){this.resultpath_txt=resultpath_txt;}
    public  String getModel_infor(){return model_infor;}
    public void setModel_infor(String model_infor){this.model_infor=model_infor;}
    public String getModelname(){return modelname;}
    public void setModelname(String modelname){this.modelname=modelname;}

}
