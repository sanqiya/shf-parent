package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = DictService.class)
public class DictServiceImpl implements DictService {

   @Autowired
   private DictDao dictDao;

   @Autowired
   JedisPool jedisPool;

//   数据字典，树形结构，ztree
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
      Jedis jedis = null;
      try {
         String key = "shf:dict:parentId:"+ parentId;
//      1.先从缓存中查询，如果缓存中有数据，直接返回，无需查询数据库
         jedis = jedisPool.getResource();
         String value = jedis.get(key);//存储时将List<Dict>转换为字符串存储的，获取得到的是字符串
         if (!StringUtils.isEmpty(value)){
            Type listType = new TypeReference<List<Dict>>() {}.getType();
            List<Dict> list = JSON.parseObject(value, List.class);
            System.out.println("RedisList:"+list);
            return list;
         }
//      2.如果缓存中没有，则查询数据库，并将数据存放到缓存中，给下次访问数据利用缓存
         List<Dict> list2 = dictDao.findListByParentId(parentId);
         if (list2!=null && list2.size()>0){
            jedis.set(key,JSON.toJSONString(list2));
            System.out.println("dblList:"+list2);
            return list2;
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if (jedis!=null){
            jedis.close();
         }
      }
      return null;
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