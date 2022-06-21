package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value="/houseBroker")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseBrokerController extends BaseController {

   @Reference
   private HouseBrokerService houseBrokerService;

   @Reference
   private AdminService adminService;

   private final static String LIST_ACTION = "redirect:/house/";
   
   private final static String PAGE_SHOW = "houseBroker/show";
   private final static String PAGE_CREATE = "houseBroker/create";
   private final static String PAGE_EDIT = "houseBroker/edit";

   
   /** 
    * 显示
    * @param model
    * @param id
    * @return
    */
   @RequestMapping("/{id}")
   public String show(ModelMap model, @PathVariable Long id) {
      HouseBroker houseBroker = houseBrokerService.getById(id);
      model.addAttribute("houseBroker",houseBroker);
      return PAGE_SHOW;
   }

   /** 
    * 进入新增
    * @param model
    * @param houseBroker
    * @return
    */
   @RequestMapping("/create")
   public String create(ModelMap model, HouseBroker houseBroker) {
      List<Admin> adminList = adminService.findAll();
      model.addAttribute("adminList",adminList);
      model.addAttribute("houseBroker",houseBroker);
      return PAGE_CREATE;
   }
   
   /**
    * 保存新增
    * @param houseBroker
    * @param request
    * @return
    */
   @RequestMapping("/save")
   public String save(HouseBroker houseBroker, HttpServletRequest request) {
      Admin admin = adminService.getById(houseBroker.getBrokerId());
      houseBroker.setBrokerName(admin.getName());
      houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
      houseBrokerService.insert(houseBroker);
      
      return this.successPage(this.MESSAGE_SUCCESS, request);
   }
   
   /** 
    * 编辑
    * @param model
    * @param id
    * @return
    */
   @RequestMapping("/edit/{id}")
   public String edit(ModelMap model,@PathVariable Long id) {
      HouseBroker houseBroker = houseBrokerService.getById(id);
      List<Admin> adminList = adminService.findAll();
      model.addAttribute("adminList",adminList);
      model.addAttribute("houseBroker",houseBroker);
      return PAGE_EDIT;
   }
   
   /** 
    * 保存更新
    * @param model
    * @param id
    * @param houseBroker
    * @param request
    * @return
    */
   @RequestMapping(value="/update/{id}")
   public String update(ModelMap model, @PathVariable Long id, HouseBroker houseBroker, HttpServletRequest request) {
      HouseBroker currentHouseBroker = houseBrokerService.getById(id);
      BeanUtils.copyProperties(houseBroker, currentHouseBroker);
      Admin admin = adminService.getById(houseBroker.getBrokerId());
      currentHouseBroker.setBrokerName(admin.getName());
      currentHouseBroker.setBrokerHeadUrl(admin.getHeadUrl());
      houseBrokerService.update(currentHouseBroker);

      return this.successPage(this.MESSAGE_SUCCESS, request);
   }
   
   /**
    * 删除
    * @param id
    * @return
    */
   @RequestMapping("/delete/{houseId}/{id}")
   public String delete(@PathVariable Long houseId, @PathVariable Long id) {
      houseBrokerService.delete(id);
      return LIST_ACTION + houseId;
   }
   
}