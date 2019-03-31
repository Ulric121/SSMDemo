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
 * @version $Id: RedisDao.java, v 1.0 2017��4��6�� ����11:00:22 Ulric Exp $
 */
public class RedisDao {
    private final Logger             logger = LoggerFactory.getLogger(this.getClass());

    //JedisPool�������ݿ����ӳ�
    private final JedisPool          jedisPool;

    private RuntimeSchema<Inventory> schema = RuntimeSchema.createFrom(Inventory.class);

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public Inventory getInventory(long inventoryId) {
        //redis�����߼�
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "inventory:" + inventoryId;

                //redis�ڲ���û��ʵ���ڲ����л�����
                //get-->byte[]-->�����л�-->Object(Inventory)
                //�����Զ������л�����Դ�����Ĺ��ߣ���������ת��Ϊ���������飬���ݸ�redis��������
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    //�����л�ȡ������
                    //�ն���
                    Inventory inventory = schema.newMessage();
                    //inventory�������л�����ʵ��Seriablizable�ӿ���ȣ�ѹ���ռ���ԭ����1/10��1/5��ѹ���ٶ�Ҳ���
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
        //set-->Object(Inventory)-->���л�-->byte[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "inventory:" + inventory.getInventoryId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(inventory, schema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //��ʱ����
                int timeout = 60 * 60;//1Сʱ
                String result = jedis.setex(key.getBytes(), timeout, bytes);//�ɹ�����"ok"
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
