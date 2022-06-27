package com.atguigu.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * 项目名: shf-parent
 * 文件名: QiniuTest
 * 创建者: 曹勇
 * 创建时间:2022/6/22 16:48
 * 描述: TODO
 **/
public class QiniuTest {
//    @Test
//    public void testUpload(){
//        //构造一个带指定 Region 对象的配置类
//        Configuration cfg = new Configuration(Zone.zone2());
//        //...其他参数参考类注释
//        UploadManager uploadManager = new UploadManager(cfg);
//        //...生成上传凭证，然后准备上传
//        String accessKey = "Y4pXR86rKkpuVvHSihXqef8yGtBN9hTqsgQNrvMm";  //七牛云密钥
//        String secretKey = "z8KEZ0OdklnR5rYzlucw8w29TJ1EBOi1pk4Xm5L1";
//        String bucket = "shfpictures";//七牛云名称空间
//        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
//        String localFilePath = "D:/pictures/1.png";
//        //默认不指定key的情况下，以文件内容的hash值作为文件名
//        String key = "1.png";//上传后的文件名，如果为null会自动创建文件名
//        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket);
//        try {
//            Response response = uploadManager.put(localFilePath, key, upToken);
//            //解析上传成功的结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
//        } catch (QiniuException ex) {
//            Response r = ex.response;
//            System.err.println(r.toString());
//            try {
//                System.err.println(r.bodyString());
//            } catch (QiniuException ex2) {
//                //ignore
//            }
//        }
//    }
//
//    @Test
//    public void testDelete(){
//        //构造一个带指定 Region 对象的配置类
//        Configuration cfg = new Configuration(Zone.zone2());
//        //...其他参数参考类注释
//        String accessKey = "Y4pXR86rKkpuVvHSihXqef8yGtBN9hTqsgQNrvMm";  //七牛云密钥
//        String secretKey = "z8KEZ0OdklnR5rYzlucw8w29TJ1EBOi1pk4Xm5L1";
//        String bucket = "shfpictures";//七牛云名称空间
//        String key = "1.png";//要删除的文件名
//        Auth auth = Auth.create(accessKey, secretKey);
//        BucketManager bucketManager = new BucketManager(auth, cfg);
//        try {
//            bucketManager.delete(bucket, key);
//        } catch (QiniuException ex) {
//            //如果遇到异常，说明删除失败
//            System.err.println(ex.code());
//            System.err.println(ex.response.toString());
//        }
//
//    }
}
