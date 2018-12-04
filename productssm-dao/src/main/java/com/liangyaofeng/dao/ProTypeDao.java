package com.liangyaofeng.dao;

import com.liangyaofeng.entity.Protype;

import java.util.List;

public interface ProTypeDao {

    List<Protype> getAllProtype();

    Protype findProtypeByid(long pid);

}
