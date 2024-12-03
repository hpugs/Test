package com.hpugs.test.bloom_filter;

/**
 * @Author：xinge
 * @Date：2024/11/11 16:54
 * @Description：
 */
public interface IBloomFilter {

    String getName();

    void put(Object obj);

    boolean mightContain(Object obj);

}
