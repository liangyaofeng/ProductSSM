package com.liangyaofeng.dao;

import com.liangyaofeng.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    List<Product> getAll(@Param("name") String name);

    void addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteByid(long id);

    int deleteProductids(List<Long> ids);

    Product findProductById(long id);

    List<Product> getProductPager(@Param("skip") int skip,@Param("size") int size);

    int getProductCount();


}
