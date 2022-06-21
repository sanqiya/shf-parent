package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = DictService.class)
public class DictServiceImpl implements DictService {

   @Autowired
   private DictDao dictDao;

   @Override
   public List<Map<String,Object>> findZnodes(Long id) {
      // 返回数据[{ id:2, isParent:true, name:"随意勾选 2"}]
      //根据id获取子节点数据
      //判断该节点是否是父节点

      //获取子节点数据列表
      List<Dict> dictList = dictDao.findListByParentId(id);

      //构建ztree数据
      List<Map<String,Object>> zNodes = new ArrayList<>();
      for(Dict dict : dictList) {
         Integer count = dictDao.countIsParent(dict.getId());
         Map<String,Object> map = new HashMap<>();
         map.put("id", dict.getId());
         map.put("isParent", count > 0 ? true : false);
         map.put("name", dict.getName());
         zNodes.add(map);
      };
      return zNodes;
   }
   @Override
   public List<Dict> findListByParentId(Long parentId) {
      return dictDao.findListByParentId(parentId);
   }

   @Override
   public List<Dict> findListByDictCode(String dictCode) {
      Dict dict = dictDao.getByDictCode(dictCode);
      if(null == dict) return null;
      return this.findListByParentId(dict.getId());
   }

   @Override
   public String getNameById(Long houseTypeId) {
      return dictDao.getNameById(houseTypeId);
   }
}