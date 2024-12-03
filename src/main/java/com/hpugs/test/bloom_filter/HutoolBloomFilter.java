package com.hpugs.test.bloom_filter;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author：xinge
 * @Date：2024/11/11 16:48
 * @Description：hutool的布隆过滤器
 */
public class HutoolBloomFilter implements IBloomFilter {

    private static int capacity;

    private static BitMapBloomFilter bloomFilter;

    public HutoolBloomFilter(int capacity) {
        this.capacity = capacity;
        this.bloomFilter = new BitMapBloomFilter(capacity);

    }

    @Override
    public String getName() {
        return "HutoolBloomFilter";
    }

    @Override
    public void put(Object obj) {
        bloomFilter.add(JSONObject.toJSONString(obj));
    }

    @Override
    public boolean mightContain(Object obj) {
        return bloomFilter.contains(JSONObject.toJSONString(obj));
    }
}
