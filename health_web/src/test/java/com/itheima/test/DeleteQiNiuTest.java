package com.itheima.test;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 新增套餐-删除垃圾图片
 * @author wangxin
 * @version 1.0
 */
//@ContextConfiguration(locations = "classpath:spring-redis.xml")
//@RunWith(SpringJUnit4ClassRunner.class)
public class DeleteQiNiuTest {

    @Autowired
    private JedisPool jedisPool;

    //@Test
    public void deleteImgs(){
        //获取两个redis集合差集
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String fileName : set) {
            //调用七牛云接口删除图片
            QiniuUtils.deleteFileFromQiniu(fileName);
            System.out.println("****删除七牛云垃圾图片成功*********"+fileName);
            //删除redis中垃圾记录
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            System.out.println("*****删除redis垃圾图片记录成功***************");
        }
    }
}
