package com.liangyaofeng.service;

import com.liangyaofeng.entity.Protype;

import java.util.List;

public interface ProtypeService {

    List<Protype> querygetAllProtype();

    Protype queryfindProtypeByid(long pid);

}
