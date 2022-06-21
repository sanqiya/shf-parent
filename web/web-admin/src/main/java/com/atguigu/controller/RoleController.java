package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {



   @Reference
   private RoleService roleService;

   private final static String PAGE_INDEX = "role/index";
   private static final String PAGE_CREATE ="role/create";
   private static final String ACTION_LIST = "redirect:/role";
   private final static String PAGE_EDIT = "role/edit";




   @RequestMapping("/delete/{id}")
   public String delete(@PathVariable Long id) {
      roleService.delete(id);
      return ACTION_LIST;
   }

   @RequestMapping("/edit/{id}")
   public String edit(@PathVariable Long id,Map map) {
      Role role = roleService.getById(id);
      map.put("role",role);
      return PAGE_EDIT;
   }

   @RequestMapping("/update")
   public String update(Role role,Map map,HttpServletRequest request) {
      roleService.update(role);
      return this.successPage("修改成功，关机吧",request);
   }

   @RequestMapping("/save")
   public String save(Role role, Map map,HttpServletRequest request) {
      Integer count = roleService.insert(role);
      return this.successPage("添加成功，关机吧",request);
   }

//   @RequestMapping
//   public String index(ModelMap model) {
//      List<Role> list = roleService.findAll();
//      model.addAttribute("list", list);
//      return PAGE_INDEX;
//   }

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
      PageInfo<Role> page = roleService.findPage(filters);

      model.addAttribute("page", page);
      model.addAttribute("filters", filters);
      return PAGE_INDEX;
   }

   @RequestMapping("/create")
   public String create() {
      return PAGE_CREATE;
   }
}