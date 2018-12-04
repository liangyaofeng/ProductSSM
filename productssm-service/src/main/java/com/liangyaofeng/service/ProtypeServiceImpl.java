package com.liangyaofeng.service;

import com.liangyaofeng.dao.ProTypeDao;
import com.liangyaofeng.entity.Protype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProtypeServiceImpl implements ProtypeService{

    @Autowired
    ProTypeDao proTypeDao;


    @Override
    public List<Protype> querygetAllProtype() {
        List<Protype> list=proTypeDao.getAllProtype();
        return list;
    }

    @Override
    public Protype queryfindProtypeByid(long pid) {
        Protype protype=proTypeDao.findProtypeByid(pid);
        return protype;
    }
}
