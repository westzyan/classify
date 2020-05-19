package com.tor.classify.service;

import com.tor.classify.dao.ModelDao;
import com.tor.classify.domain.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelService {

    @Autowired
    private ModelDao modelDao;

    /**
     * 获取数据库中最新的模型
     * @return model
     */
    public Model getLastModel(){
        return modelDao.getLastModel();
    }

    public static void main(String[] args) {
        System.out.println(new ModelService().getLastModel());;
    }
}
