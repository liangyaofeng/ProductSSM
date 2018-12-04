package com.liangyaofeng.dao;


import com.liangyaofeng.entity.Protype;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback(true)  //是否回滚
public class ProtypeTest {

    @Autowired
    ProTypeDao proTypeDao;

    @Test
    public void testGetAllProtype(){
        List<Protype> list=proTypeDao.getAllProtype();
        System.out.println(list);
    }

    @Test
    public void testFindProtypeByid(){
        Protype protype=proTypeDao.findProtypeByid(2);
        System.out.println(protype);
    }


}
