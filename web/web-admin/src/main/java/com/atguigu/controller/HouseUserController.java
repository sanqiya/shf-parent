package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/houseUser")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseUserController extends BaseController {

   @Reference
   private HouseUserService houseUserService;

   private final static String LIST_ACTION = "redirect:/house/";
   private final static String PAGE_CREATE = "houseUser/create";
   private final static String PAGE_EDIT = "houseUser/edit";

   
   /** 
    * 进入新增
    * @param model
    * @param houseUser
    * @return
    */
   @RequestMapping("/create")
   public String create(ModelMap model, HouseUser houseUser) {
      model.addAttribute("houseUser",houseUser);
      return PAGE_CREATE;
   }
   
   /**
    * 保存新增
    * @param model
    * @param houseUser
    * @param request
    * @return
    */
   @RequestMapping("/save")
   public String save(ModelMap model, HouseUser houseUser, HttpServletRequest request) {
      //SysUser user = this.currentSysUser(request);
      houseUserService.insert(houseUser);
      return this.successPage(this.MESSAGE_SUCCESS, request);
   }
   
   /** 
    * 编辑
    * @param model
    * @param id
    * @return
    */
   @GetMapping("/edit/{id}")
   public String edit(ModelMap model,@PathVariable Long id) {
      HouseUser houseUser = houseUserService.getById(id);
      model.addAttribute("houseUser",houseUser);
      return PAGE_EDIT;
   }
   
   /** 
    * 保存更新
    * @param model
    * @param id
    * @param houseUser
    * @param request
    * @return
    */
   @RequestMapping(value="/update/{id}")
   public String update(ModelMap model, @PathVariable Long id, HouseUser houseUser, HttpServletRequest request) {
      HouseUser currentHouseUser = houseUserService.getById(id);
      BeanUtils.copyProperties(houseUser, currentHouseUser);
      
      houseUserService.update(currentHouseUser);
      return this.successPage(this.MESSAGE_SUCCESS, request);
   }
   
   /**
    * 删除
    * @param id
    * @return
    */
   @GetMapping("/delete/{houseId}/{id}")
   public String delete(@PathVariable Long houseId, @PathVariable Long id) {
      houseUserService.delete(id);
      return LIST_ACTION + houseId;
   }
   
}