package com.atguigu.controller;

import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {



   @Autowired
   private AdminService adminService;

   private final static String PAGE_INDEX = "admin/index";
   private static final String PAGE_CREATE ="admin/create";
   private static final String ACTION_LIST = "redirect:/admin";
   private final static String PAGE_EDIT = "admin/edit";




   @RequestMapping("/delete/{id}")
   public String delete(@PathVariable Long id) {
      adminService.delete(id);
      return ACTION_LIST;
   }

   @RequestMapping("/edit/{id}")
   public String edit(@PathVariable Long id,Map map) {
      Admin admin = adminService.getById(id);
      map.put("admin",admin);
      return PAGE_EDIT;
   }

   @RequestMapping("/update")
   public String update(Admin admin,Map map,HttpServletRequest request) {
      adminService.update(admin);
      return this.successPage("修改成功，关机吧",request);
   }

   @RequestMapping("/save")
   public String save(Admin admin, Map map,HttpServletRequest request) {
      admin.setHeadUrl("http://47.93.148.192:8080/group1/M00/03/F0/rBHu8mHqbpSAU0jVAAAgiJmKg0o148.jpg");
      adminService.insert(admin);
      return this.successPage("添加成功，关机吧",request);
   }

   /**
    * 分页查询
    *    根据查询条件查询
    * @param model
    * @param request
    * @return
    */
   @RequestMapping
   public String index(HttpServletRequest request,Model model) {
      Map<String,Object> filters = getFilters(request);
//      分页对象
      PageInfo<Admin> page = adminService.findPage(filters);

      model.addAttribute("page", page);
      model.addAttribute("filters", filters);
      return PAGE_INDEX;
   }

   @RequestMapping("/create")
   public String create() {
      return PAGE_CREATE;
   }
}