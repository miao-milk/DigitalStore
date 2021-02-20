package com.mxw.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
    缓存工具类
 */

@Component
public class RedisUtils {

    public static final int EX_FIVE_MINUTES = 5 * 60;
    public static final int EX_TEN_MINUTES = 10 * 60;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * 指定缓存失效时间     * @param key 键     * @param time 时间(秒)     * @return
     */
    public boolean expire(String key, long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } else {
            throw new RuntimeException("超时时间小于0");
        }
    }

    /**
     * 根据key 获取过期时间     * @param key 键 不能为null     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在     * @param key 键     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
    // ============================String=============================

    /**
     * 普通缓存获取     * @param key 键     * @return 值
     */
    public Object get(String key) {
        redisTemplate.isExposeConnection();
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        return key == null ? null : stringObjectValueOperations.get(key);
    }

    /**
     * 普通缓存放入     * @param key 键     * @param value 值     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }


    /**
     * 普通缓存放入并设置时间     * @param key 键     * @param value 值     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     *
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            this.set(key, value);
        }
        return true;
    }

    // ================================Map=================================

    /**
     * HashGet     * @param key 键 不能为null     * @param item 项 不能为null     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值     * @param key 键     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet     * @param key 键     * @param map 对应多个键值     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
        return true;
    }

    /**
     * HashSet 并设置时间     * @param key 键     * @param map 对应多个键值     * @param time 时间(秒)     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建     * @param key 键     * @param item 项     * @param value 值     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
        return true;
    }

    /**
     * 获取变量的长度。
     */
    public Long hsize(String key) {
        Long size = redisTemplate.opsForHash().size(key);
        return size;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建     * @param key 键     * @param item 项     * @param value 值     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     *
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    /**
     * 删除hash表中的值     * @param key 键 不能为null     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值     * @param key 键 不能为null     * @param item 项 不能为null     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回     * @param key 键     * @param item 项     * @param by 要增加几(大于0)
     *
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减     * @param key 键     * @param item 项     * @param by 要减少记(小于0)
     *
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
    // ============================set=============================

    /**
     * 根据key获取Set中的所有值     * @param key 键     * @return
     */
    public Set<Object> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在     * @param key 键     * @param value 值     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存     * @param key 键     * @param values 值 可以是多个     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 将set数据放入缓存     * @param key 键     * @param time 时间(秒)     * @param values 值 可以是多个     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        final Long count = redisTemplate.opsForSet().add(key, values);
        if (time > 0) {
            expire(key, time);
        }
        return count;
    }

    /**
     * 获取set缓存的长度     * @param key 键     * @return
     */
    public long sGetSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的     * @param key 键     * @param values 值 可以是多个     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        final Long count = redisTemplate.opsForSet().remove(key, values);
        return count;
    }
    // ===============================list=================================

    /**
     * 获取list缓存的内容     * @param key 键     * @param start 开始     * @param end 结束  0 到 -1代表所有值
     *
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度     * @param key 键     * @return
     */
    public long lGetListSize(String key) {
        if(StringUtils.isEmpty(key)) return 0;
        if(redisTemplate==null) return 0;
        if(redisTemplate.opsForList()==null) return 0;
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值     * @param key 键     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     *
     * @return
     */
    public Object lGetIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存     * @param key 键     * @param value 值     * @param time 时间(秒)     * @return
     */
    public boolean lSet(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
        return true;
    }

    /**
     * 将list放入缓存     * @param key 键     * @param value 值     * @param time 时间(秒)     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        redisTemplate.opsForList().rightPush(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    /**
     * 将list放入缓存     * @param key 键     * @param value 值     * @param time 时间(秒)     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        redisTemplate.opsForList().rightPushAll(key, value);
        return true;
    }

    /**
     * 将list放入缓存     * @param key 键     * @param value 值     * @param time 时间(秒)     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        redisTemplate.opsForList().rightPushAll(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    /**
     * 根据索引修改list中的某条数据     * @param key 键     * @param index 索引     * @param value 值     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
        return true;
    }

    /**
     * Set : smembers
     */
    public Set<Object> smembers(String key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Set : scard
     */
    public Long scard(String key){
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * Set : sadd
     */
    public Long sadd(String key,Object ...objs){
        return redisTemplate.opsForSet().add(key, objs);
    }


    // ===============================Zset=================================

    public  void zadd(String key, Object obj, double score) {
        if (obj == null) {
            return;
        }
        redisTemplate.opsForZSet().add(key, JSONObject.toJSON(obj), score);
    }
    /**
     * 获取基数等同于zCard
     */
    public long zcard(String key) {
        //返回集合内的成员个数
        Long size = redisTemplate.opsForZSet().size(key);//等同于zCard(key);
        return size;
    }

    /**
     * 正序遍历集合
     * @param key
     * @param start
     * @param end
     * @return
     */
        public Set<Object> zrange(String key, int start, long end) {
        Set<Object> set = redisTemplate.opsForZSet().range(key, start, end);
        return  set;
    }

    /**
     * 删除集合元素
     * @param key
     * @param values
     * @return
     */
    public void zrem(String key, Object values) {
        redisTemplate.opsForZSet().remove(key, JSONObject.toJSON(values));
    }

    /**
     * 获取指定范围的记录，可以做为分页使用
     * @param key
     * @param start
     * @param total
     * @return List
     */
    public List lrange(String key, int start, int total) {
        List listKey = redisTemplate.boundListOps(key).range(start, total);
        return listKey;
    }

    /**
     * 将一个值插入到列表头部
     * @param key
     * @param value
     * @return 记录总数
     */
    public void lpush(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 将一个值插入到列表尾部
     * @param key
     * @param value
     * @return 记录总数
     */
    public void rpush(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     *  反序列化
     */
    public Object unserizlize(byte[] byt) {
        ObjectInputStream oii = null;
        ByteArrayInputStream bis = null;
        bis = new ByteArrayInputStream(byt);
        try {
            oii = new ObjectInputStream(bis);
            Object obj = oii.readObject();
            return obj;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 序列化
     */
    public byte[] serialize(Object obj) {
        ObjectOutputStream obi = null;
        ByteArrayOutputStream bai = null;
        try {
            bai = new ByteArrayOutputStream();
            obi = new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt = bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  JSON序列化
     */
    public String serializeToJSONString(Object obj){
        String s = JSONObject.toJSONString(obj);
        return s;
    }

    /**
     *  JSON反序列化
     */
    public Object unserizlizeToJSONObject(String jsonStr,Class cla){
        Object object = JSONObject.parseObject(jsonStr, cla);
        return object;
    }
}
