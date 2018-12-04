package com.liangyaofeng.productssm;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liangyaofeng.dao.ExcelUtil;
import com.liangyaofeng.entity.Product;
import com.liangyaofeng.service.ProductService;
import com.liangyaofeng.service.ProtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("product")
public class ProductController {


    @Autowired
    ProductService productService;
    @Autowired
    ProtypeService protypeService;


    //根据name查询，默认查询所有
    @RequestMapping("productInfo")
    public String productInfo(@RequestParam(required=true,defaultValue="1") Integer pn,
                            @RequestParam(required=false,defaultValue="5") Integer pageSize, Model model, String name){
        //从第一条开始 每页查询五条数据
        PageHelper.startPage(pn, pageSize);
        List<Product> product = productService.queryProductByName(name);
        //将用户信息放入PageInfo对象里
        PageInfo<Product> page = new PageInfo<Product>(product,pageSize);
        model.addAttribute("pageInfo", page);
        model.addAttribute("name",name);
        return "product/allProduct";
    }


//    跳转到添加页面
    @RequestMapping("toAddProduct")
    public String toAddUser(Model model){
        model.addAttribute("protype",protypeService.querygetAllProtype());
        return "product/add";
    }

    //    添加
    @RequestMapping("addProduct")
    public String addProduct(Model model,Product product){
        if(product != null){
            productService.queryaddProduct(product);
        }
        return "redirect:/product/productInfo";
    }


    //    修改
    @RequestMapping("updateProduct")
    public String updateProduct(Model model,Product product){
        if(productService.queryupdateProduct(product)){
            product = productService.queryfindProductById(product.getId());
            model.addAttribute("product", product);
            return "redirect:/product/productInfo";
        }
        return "product/error";
    }


    //    删除
    @RequestMapping("/delProduct")
    public String delProduct(long id,Model model){
        model.addAttribute("product", productService.querydeleteByid(id));
        return "redirect:/product/productInfo";
    }

    //   多删除
    @RequestMapping("/deletes")
    public String deletes(Model model, String name, @RequestParam("ids") List<Long> ids){

        String msg="删除成功！";

        if(productService.querydeleteProductids(ids)<=0){
            msg="删除失败！";
        }

        model.addAttribute("msg",msg);
        model.addAttribute("name",name);

        model.addAttribute("product",productService.queryProductByName(name));
        return "redirect:/product/productInfo";
    }




    //    根据id查询
    @RequestMapping("/getProduct")
    public String getProduct(long id,Model model){
        model.addAttribute("product", productService.queryfindProductById(id));
        model.addAttribute("protype",protypeService.querygetAllProtype());
        return "product/update";
    }



    //跳转到上传图片页面
    @RequestMapping("/upPicture")
    public String upPicture(Model model, long id){
        model.addAttribute("product", productService.queryfindProductById(id));
        model.addAttribute("protype",protypeService.querygetAllProtype());
        return "product/upfile";
    }


    //上传图片+编辑修改
    @RequestMapping("/upPictureSave/{id}")
    public String upPictureSave(Model model,Product pro, @PathVariable long id, MultipartFile picFile, HttpServletRequest request){
        Product product=productService.queryfindProductById(id);
        //如果选择了文件
        if(picFile!=null){
            //如果文件大小不为0
            if(picFile.getSize()>0){
                //获得上传位置
                String path=request.getServletContext().getRealPath("/images");
                //生成文件名
                String filename=UUID.randomUUID().toString()+picFile.getOriginalFilename().substring(picFile.getOriginalFilename().lastIndexOf("."));
                File tempFile=new File(path, filename);
                try {
                    //保存文件
                    picFile.transferTo(tempFile);
                    //更新数据
                    product.setPicture(filename);
                    productService.queryupdateProduct(product);
                    //修改
                    if(productService.queryupdateProduct(pro)){
                        pro = productService.queryfindProductById(pro.getId());
                        model.addAttribute("product", pro);
                        return "redirect:/product/productInfo";
                    }

                    //转向列表页
                    return "redirect:/product/productInfo";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        model.addAttribute("product", product);
        return "product/upfile";
    }





    //导入
    @RequestMapping(value = "/leads",method = RequestMethod.POST)
    @ResponseBody
    public String leads(MultipartFile files, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //导入数据
        List<Product> productArrayList=new ArrayList<>();
        //      文件存放路径
        System.out.println("aaa");
        String str=request.getServletContext().getRealPath("files");
        File file=new File(str);
        //判断文件是否存在
        if(!file.exists()){
            //不存在则创建一个文件夹
            file.mkdir();
        }


        File file1=new File(str,files.getOriginalFilename());
            //把内存的文件写入磁盘
            files.transferTo(file1);

        //System.out.println(str+"\\"+files.getOriginalFilename());
        // new ExcelUtil().impExcel(str+"\\"+files.getOriginalFilename(),goodsliuArrayList);
        List<Product> productList=new ExcelUtil().impExcel(str+"\\"+files.getOriginalFilename(),productArrayList);
        System.out.println(productList);
        System.out.println(productList);
        System.out.println(productArrayList);
        Product product=new Product();
        //return R.ok(goodsliuService.updategoods(goodsliu));
        for(Product item:productList){
            product.setId(item.getId());
            product.setName(item.getName());
            product.setPid(item.getPid());
            product.setPrice(item.getPrice());
            product.setPicture(item.getPicture());
            productService.queryaddProduct(product);
        }
        //response.sendRedirect("/GoodsList.html");
        //request.getRequestDispatcher("GoodsList.html").forward(request, response);
        return "redirect:/product/productInfo";
    }





    //21.下载附件，导出Excel,xls
    @RequestMapping("/pushxls")
    @ResponseBody
    public void pushxls(HttpServletResponse response) throws IOException {
        //POI
        //response.setContentType("text/html;charset=utf-8");
        //response.setCharacterEncoding("utf-8");
        //问题：下载xls问题用excel打开乱码，用notepad++等工具转成UTF-8格式(带BOM)可以正常打开。
        //解决：严格来说这并不是xls文件的问题，而是Excel处理文件编码方式问题，Excel默认并不是以UTF-8来打开文件，所以在xls开头加入BOM，告诉Excel文件使用utf-8的编码方式。
        response.setHeader("Content-Type","application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=product.xls");
        PrintWriter out = response.getWriter();
        //加上bom头,解决excel打开乱码问题
        byte[] bomStrByteArr = new byte[] { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };
        String bomStr = new String(bomStrByteArr, "UTF-8");
        out.write(bomStr);

        StringBuffer str=new StringBuffer("");
        str.append("<table border=1 width=100%>");
        str.append("<tr><th>编号</th><th>名称</th><th>类型</th><th>价格</th><th>图片</th></tr>");

        List<Product> list=productService.queryProductByName("");


        for (Product product:list) {
            str.append("<tr><td>"+product.getId()+"</td><td>"+product.getName()+"</td><td>"+product.getProtype().getTname()+"</td><td>"+product.getPrice()+"</td><td>"+product.getPicture()+"</td></tr>");
        }

        str.append("</table>");
        out.write(str.toString());
    }

    //22.下载附件，导出Excel,csv
    @RequestMapping("/pushcsv")
    @ResponseBody
    public void pushcsv(HttpServletResponse response) throws IOException {

        //POI
        //response.setContentType("text/html;charset=utf-8");
        //response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type","application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=product.csv");
        PrintWriter out = response.getWriter();
        //加上bom头,解决excel打开乱码问题
        byte[] bomStrByteArr = new byte[] { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };
        String bomStr = new String(bomStrByteArr, "UTF-8");
        out.write(bomStr);

        List<Product> list=productService.queryProductByName("");


        StringBuffer str=new StringBuffer("");
        str.append("编号,名称,类型,价格,图片\r\n");
        for (Product product:list) {
            str.append(product.getId()+","+product.getName()+","+product.getProtype().getTname()+","+product.getPrice()+","+product.getPicture()+"\r\n");
        }
        response.getWriter().write(str.toString());
    }




}
