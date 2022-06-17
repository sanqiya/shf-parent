package com.atguigu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 项目名: shf-parent
 * 文件名: IndexController
 * 创建者: 曹勇
 * 创建时间:2022/6/16 20:49
 * 描述: TODO
 **/
@Controller
public class IndexController {

    private static final String PAGE_FRAME ="frame/index" ;
    private static final String PAGE_MAIN ="frame/main" ;

    @RequestMapping("/")
    public String index(){
        return PAGE_FRAME;
    }

    @RequestMapping("/main")
    public String main(){
        return PAGE_MAIN;
    }
}
