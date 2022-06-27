package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.atguigu.vo.LoginVo;
import com.atguigu.vo.RegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名: shf-parent
 * 文件名: UserInfoController
 * 创建者: 曹勇
 * 创建时间:2022/6/25 20:05
 * 描述: TODO
 **/

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    private UserInfoService userInfoService;

    @RequestMapping("/getCode/{phone}")
    public Result getCode(@PathVariable("phone") String phone, HttpServletRequest request){
        String code = "1234";
        request.getSession().setAttribute("CODE",code);
        //往手机上发送校验码，模拟操作
        return Result.ok(code);

    }

    //ajax提交post请求，通过请求体获取json数据，转换为vo对象
    @RequestMapping("/register")
    public Result getCode(@RequestBody RegisterVo registerVo,HttpServletRequest request){
        String code = registerVo.getCode();
        String password = registerVo.getPassword();
        String phone = registerVo.getPhone();
        String nickName = registerVo.getNickName();
        //1.数据校验
        if (StringUtils.isEmpty(code)
                ||StringUtils.isEmpty(phone)
                ||StringUtils.isEmpty(password)
                ||StringUtils.isEmpty(nickName)){
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //2.手机验证码是否有效（校验码五分钟过期）

        //3.手机验证码是否一致
        String sendCode = (String) request.getSession().getAttribute("CODE");
        if (sendCode == null || !code.equals(sendCode) ){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }
        //4.手机号码是否被占用(表中字段unique)
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if (userInfo!=null){
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        //5.封装数据，保存数据（密码加密存储）
        userInfo = new UserInfo();
        BeanUtils.copyProperties(registerVo, userInfo);//类型可以不一样，只要属性名称一样就可以拷贝
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setStatus(1);
        userInfoService.insert(userInfo);

        return Result.ok();

    }
    /**
     * 会员登录
     * @param loginVo
     * @param request
     * @return
     */
    @RequestMapping("login")
    public Result login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        //校验参数
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        //校验账号
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if(null == userInfo) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }

        //校验密码
        //MD5是一种加密算法
        //特点：
        // 1.加密后长度不变，16字节，转换为32个字符存储，每个字符16进制数
        // 2.原文不变，密文不变
        // 3.通过彩虹表进行破解
        if(!MD5.encrypt(password).equals(userInfo.getPassword())) {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }

        //校验是否被禁用
        if(userInfo.getStatus() == 0) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        request.getSession().setAttribute("USER", userInfo);

        Map<String, Object> map = new HashMap<>();
        map.put("phone", userInfo.getPhone());
        map.put("nickName", userInfo.getNickName());
        return Result.ok(map);
    }

    @RequestMapping("logout")
    public Result logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session!=null){
            session.removeAttribute("USER");
        }
        return Result.ok();
    }
}
