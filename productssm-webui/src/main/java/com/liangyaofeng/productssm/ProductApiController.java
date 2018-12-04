package com.liangyaofeng.productssm;

import com.liangyaofeng.common.utils.R;
import com.liangyaofeng.entity.Page;
import com.liangyaofeng.entity.Product;
import com.liangyaofeng.service.ProductService;
import com.liangyaofeng.service.ProtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("productapi")
public class ProductApiController {

    @Autowired
    ProductService productService;
    @Autowired
    ProtypeService protypeService;


    @RequestMapping("/queryproduct")
    @ResponseBody
    public R queryProduct(String name){
        return  R.ok(productService.queryProductByName(name));
    }


    @RequestMapping("/deletes")
    @ResponseBody
    public R deletes(@RequestBody List<Long> ids){
        return  R.ok(productService.querydeleteProductids(ids));
    }







    //分页查询
    @RequestMapping("page")
    public @ResponseBody Page<Product> selectPageInfo(HttpServletRequest request, Model model,
                                                      @RequestParam(value = "start",required = true,defaultValue="1") int start){
        List<Product> list= productService.selectPageInfo(start);
        Page<Product> pageInfo = new Page<Product>();
        int count = productService.getCount(); //获取总数
        int endPage = 0;
        //5条数据为一页
        if(productService.getCount()%5==0){
            endPage = productService.getCount()/5;
        }else{
            endPage = productService.getCount()/5 +1 ;
        }
        pageInfo.setPage(start+1);
        pageInfo.setTotalRecord(count);
        pageInfo.setList(list);
        pageInfo.setRows(5);
        pageInfo.setFirstPage(1);
        pageInfo.setEndPage(endPage);
        return pageInfo;
    }



}
