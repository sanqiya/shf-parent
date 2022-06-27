package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController   //相当于@Controller和ResponseBody的组合
@RequestMapping(value="/house")
@CrossOrigin
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private UserFollowService userFollowService;


    @RequestMapping("/info/{id}")
    public Result info(@PathVariable Long id, HttpServletRequest request) {
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseBroker> houseBrokerList = houseBrokerService.findListByHouseId(id);
        List<HouseImage> houseImage1List = houseImageService.findList(id, 1);
        //登录后判断当前用户是否已经关注房源，将isFollow值变成动态
        boolean isFollow=false;
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        if (userInfo!=null){
            Long userInfoId = userInfo.getId();
            isFollow = userFollowService.isFollow(userInfoId,id);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        //关注业务后续补充，当前默认返回未关注
        map.put("isFollow",false);
        return Result.ok(map);
    }

    /**
     * 房源列表
     * @return
     * 异步请求处理
     */
    @RequestMapping(value = "/list/{pageNum}/{pageSize}")
    public Result findListPage(@RequestBody HouseQueryVo houseQueryVo,
                               @PathVariable Integer pageNum,
                               @PathVariable Integer pageSize) {
        PageInfo<HouseVo> pageInfo = houseService
                                  .findListPage(pageNum, pageSize, houseQueryVo);
        return Result.ok(pageInfo);
    }

}