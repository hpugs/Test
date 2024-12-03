package com.hpugs.test.bloom_filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @Author：xinge
 * @Date：2024/11/11 16:24
 * @Description：基于guava的布隆过滤器
 */
public class GuavaBloomFilter implements IBloomFilter {

    /**
     * 预期容量
     */
    private static Integer capacity;

    /**
     * 错误比率
     */
    private static double errorRate;

    /**
     * 创建BloomFilter对象，需要传入Funnel对象，预估的元素个数，错误率
     */
    private static BloomFilter bloomFilter;

    public GuavaBloomFilter(Integer capacity, double errorRate) {
        this.capacity = capacity;
        this.errorRate = errorRate;
        this.bloomFilter = BloomFilter.create(Funnels.integerFunnel(), capacity, errorRate);
    }

    @Override
    public String getName() {
        return "GuavaBloomFilter";
    }

    @Override
    public void put(Object obj) {
        bloomFilter.put(obj);
    }

    @Override
    public boolean mightContain(Object obj) {
        return bloomFilter.mightContain(obj);
    }
}
