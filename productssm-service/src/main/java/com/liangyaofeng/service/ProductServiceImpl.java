package com.liangyaofeng.service;

import com.liangyaofeng.dao.ProductDao;
import com.liangyaofeng.entity.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductDao productDao;

//    根据name查询所有
    @Override
    public List<Product> queryProductByName(String name) {
        if (StringUtils.isEmpty(name)){
            name=null;
        }
        return productDao.getAll(name);
    }

//    添加
    @Override
    public void queryaddProduct(Product product) {
        productDao.addProduct(product);
    }

//    修改
    @Override
    public boolean queryupdateProduct(Product product) {
        return productDao.updateProduct(product);
    }

//    删除
    @Override
    public boolean querydeleteByid(long id){
        return productDao.deleteByid(id);
    }

//    多删除
    @Override
    public int querydeleteProductids(List<Long> ids) {
        if(ids==null||ids.size()<=0) {
            return 0;
        }
        return productDao.deleteProductids(ids);
    }

//    根据id查询
    @Override
    public Product queryfindProductById(long id) {
        Product product=productDao.findProductById(id);
        return product ;
    }


    @Override
    public List<Product> querygetProductPager(int pageNO, int size) {
        int skip=(pageNO-1)*size;
        return productDao.getProductPager(skip, size);
    }


    @Override
    public int querygetProductCount() {
        return productDao.getProductCount();
    }






    public List<Product> selectPageInfo(int page) {
        int rows = 5;  //一页显示8条数据
        int start = page*rows;   //这里表示数据库从第几条数据开始查询（limit从下标0开始）
        return productDao.getProductPager(start,rows);
    }
    public int getCount() {
        return productDao.getProductCount();
    }


}
