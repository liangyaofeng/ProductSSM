package com.liangyaofeng.dao;

import com.liangyaofeng.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//@TransactionConfiguration(defaultRollback = true)  //被弃用
@Rollback(true)  //是否回滚
public class ProductDaoTest {

    @Autowired
    ProductDao productDao;

    @Test
    public void testGetAll(){
        List<Product> list=productDao.getAll("百草味东北松子200gx2袋");
        System.out.println(list);
    }

    @Test
    public void testAddProduct(){
        Product product=new Product();
        product.setId(90);
        product.setName("草莓");
        product.setPid(4);
        product.setPrice(34.4);
        product.setPicture("34.jpg");
        productDao.addProduct(product);
    }

    @Test
    public void testUpdateProduct(){
        Product product=new Product();
        product.setId(31);
        product.setName("草莓酱");
        product.setPrice(38.4);
        product.setPicture("34.jpg");
        productDao.updateProduct(product);
    }

    @Test
    public void testDeleteByid(){
        System.out.println(productDao.deleteByid(31));
    }

    @Test
    public void testDeleteProductids(){
        List<Long> list=new ArrayList<Long>();
        list.add(31L);
        list.add(32L);

        System.out.println(productDao.deleteProductids(list));
    }

    @Test
    public void testFindProductById(){
        System.out.println(productDao.findProductById(31));
    }

    @Test
    public void testGetProductPager(){
        System.out.println(productDao.getProductPager(1,5));
    }

    @Test
    public void testGetProductCount(){
        System.out.println(productDao.getProductCount());
    }






}
