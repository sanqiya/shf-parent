package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/community")
@SuppressWarnings({"unchecked", "rawtypes"})
public class CommunityController extends BaseController {

	@Reference
	private CommunityService communityService;

	@Reference
	private DictService dictService;
	
	private final static String LIST_ACTION = "redirect:/community";
	private final static String PAGE_INDEX = "community/index";
	private final static String PAGE_CREATE = "community/create";
	private final static String PAGE_EDIT = "community/edit";
	
	/** 
	 * 列表
	 * @param model
	 * @param request
	 * @return
	 */
    @RequestMapping
	public String index(ModelMap model, HttpServletRequest request) {
		Map<String,Object> filters = getFilters(request);
		//页面filters.areaId报错，兼容处理
		if(!filters.containsKey("areaId")) {
			filters.put("areaId", "");
		}
		if(!filters.containsKey("plateId")) {
			filters.put("plateId", "");
		}
		PageInfo<Community> page = communityService.findPage(filters);

		List<Dict> areaList = dictService.findListByDictCode("beijing");
		model.addAttribute("areaList",areaList);
		model.addAttribute("page", page);
		model.addAttribute("filters", filters);
		return PAGE_INDEX;
	}

	/** 
	 * 进入新增
	 * @param model
	 * @param
	 * @return
	 */
	@RequestMapping("/create")
	public String create(ModelMap model) {
		List<Dict> areaList = dictService.findListByDictCode("beijing");
		model.addAttribute("areaList",areaList);
		return PAGE_CREATE;
	}
	
	/**
	 * 保存新增
	 * @param
	 * @param community
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	public String save(Community community, HttpServletRequest request) {
		communityService.insert(community);
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
		Community community = communityService.getById(id);
		List<Dict> areaList = dictService.findListByDictCode("beijing");
		model.addAttribute("community",community);
		model.addAttribute("areaList",areaList);
		return PAGE_EDIT;
	}
	
	/** 
	 * 保存更新
	 * @param
	 * @param
	 * @param community
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update")
	public String update(Community community, HttpServletRequest request) {	
        communityService.update(community);
		return this.successPage(this.MESSAGE_SUCCESS, request);
	}
	
	/**
	 * 删除
	 * @param
	 * @param id
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		communityService.delete(id);
		return LIST_ACTION;
	}
	
}