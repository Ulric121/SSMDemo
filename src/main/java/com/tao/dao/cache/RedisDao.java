package com.tao.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.tao.entity.Inventory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * 
 * @author Ulric
 * @version $Id: RedisDao.java, v 1.0 2017年4月6日 上午11:00:22 Ulric Exp $
 */
public class RedisDao {
    private final Logger             logger = LoggerFactory.getLogger(this.getClass());

    //JedisPool类似数据库连接池
    private final JedisPool          jedisPool;

    private RuntimeSchema<Inventory> schema = RuntimeSchema.createFrom(Inventory.class);

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public Inventory getInventory(long inventoryId) {
        //redis操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "inventory:" + inventoryId;

                //redis内部并没有实现内部序列化操作
                //get-->byte[]-->反序列化-->Object(Inventory)
                //采用自定义序列化（开源社区的工具），将对象转化为二进制数组，传递给redis缓存起来
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    //缓存中获取到数据
                    //空对象
                    Inventory inventory = schema.newMessage();
                    //inventory被反序列化，与实现Seriablizable接口相比，压缩空间至原来的1/10到1/5，压缩速度也变快
                    ProtostuffIOUtil.mergeFrom(bytes, inventory, schema);
                    return inventory;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putInventory(Inventory inventory) {
        //set-->Object(Inventory)-->序列化-->byte[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "inventory:" + inventory.getInventoryId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(inventory, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout = 60 * 60;//1小时
                String result = jedis.setex(key.getBytes(), timeout, bytes);//成功返回"ok"
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
