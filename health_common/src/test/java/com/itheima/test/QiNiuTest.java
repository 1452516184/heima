package com.itheima.test;

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
 * 七牛云入门案例（新增套餐） 7.2.0（Zone.zone0()）  Region.region0()(高一些版本对象)
 * 上传图片
 * 删除图片
 * @author wangxin
 * @version 1.0
 */
public class QiNiuTest {
    /**
     * 上传图片
     */
    //@Test
    public void uploadFile(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "A_jKJnB1bpEPHn1QdqzPpelrCPU6QfJbJnv-_RR4";
        String secretKey = "CldWf-r2Z6mEkuqQD8zEOVj5U_jIRK-Dcea6T9oB";
        String bucket = "heima-health86";///空间名称
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\admin\\Desktop\\2.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);//返回图片名称
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }


    //@Test
    public void deleteFile(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释

        String accessKey = "A_jKJnB1bpEPHn1QdqzPpelrCPU6QfJbJnv-_RR4";
        String secretKey = "CldWf-r2Z6mEkuqQD8zEOVj5U_jIRK-Dcea6T9oB";
        String bucket = "heima-health86";///空间名称
        String key = "FjH4stdIs39dZDe9SG4uAKk1s7lk";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

}
