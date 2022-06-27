package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
@RequestMapping(value="/houseImage")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseImageController extends BaseController {

   @Reference
   private HouseImageService houseImageService;
   
   private final static String LIST_ACTION = "redirect:/house/";
   private final static String PAGE_UPLOAD_SHOW = "house/upload";

   @RequestMapping("/uploadShow/{houseId}/{type}")
   public String uploadShow(ModelMap model,
                            @PathVariable Long houseId,
                            @PathVariable Long type) {
      model.addAttribute("houseId",houseId);
      model.addAttribute("type",type);
      return PAGE_UPLOAD_SHOW;
   }

   @RequestMapping("/upload/{houseId}/{type}")
   @ResponseBody
   public Result upload(@PathVariable Long houseId,
                        @PathVariable Integer type,
                        @RequestParam(value = "file") MultipartFile[] files)
                        throws Exception {
      if(files != null && files.length > 0) {
         for(MultipartFile file : files) {
            String newFileName =  UUID.randomUUID().toString() ;
            // 上传图片到七牛云
            QiniuUtils.upload2Qiniu(file.getBytes(),newFileName);
            String imgUrl= "http://rdveaqzxo.hn-bkt.clouddn.com/"+ newFileName;
            //七牛云外链域名只有一个月有效期
            HouseImage houseImage = new HouseImage();
            houseImage.setHouseId(houseId);
            houseImage.setType(type);
            houseImage.setImageName(file.getOriginalFilename());
            houseImage.setImageUrl(imgUrl);
            houseImageService.insert(houseImage);
         }
      }
      return Result.ok();
   }

   /**
    * 删除
    * @param id
    * @return
    */
   @RequestMapping("/delete/{houseId}/{id}")
   public String delete(@PathVariable Long houseId, @PathVariable Long id) {
      HouseImage houseImage = houseImageService.getById(id);
      houseImageService.delete(id);//删除前查询数据，否则删除后无法查询
      QiniuUtils.deleteFileFromQiniu(houseImage.getImageUrl());
      return LIST_ACTION + houseId;
   }
}