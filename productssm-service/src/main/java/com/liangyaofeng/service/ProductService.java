package com.liangyaofeng.service;

import com.liangyaofeng.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductService {

    List<Product> queryProductByName(@Param("name") String name);

    void queryaddProduct(Product product);

    boolean queryupdateProduct(Product product);

    boolean querydeleteByid(long id);

    int querydeleteProductids(List<Long> ids);

    Product queryfindProductById(long id);

    List<Product> querygetProductPager(int pageNO, int size);

    int querygetProductCount();


    //查看分页的信息
    public List<Product> selectPageInfo(int page);
    //查看所有信息的总数
    int getCount();


}
