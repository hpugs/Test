package com.hpugs.test.bloom_filter;

import java.util.BitSet;

/**
 * bloom filter 布隆过滤器
 */
public class SimpleBloomFilter implements IBloomFilter {

    private static final int DEFAULT_SIZE = 2 << 24;
    private static final int[] seeds = new int[]{7, 11, 13, 31, 37, 41, 43, 47, 53, 59};

    private BitSet bits = new BitSet(DEFAULT_SIZE);
    private SimpleHash[] hashFunctions = new SimpleHash[seeds.length];

    public SimpleBloomFilter() {
        for (int i = 0; i < seeds.length; i++) {
            hashFunctions[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }

    public static void main(String[] args) {
        SimpleBloomFilter simpleBloomFilter = new SimpleBloomFilter();
        String abc = "abc";
        simpleBloomFilter.add(abc);
        boolean contains = simpleBloomFilter.contains(abc);
        System.out.println(contains);
    }


    public void add(Object value) {
        for (SimpleHash hash : hashFunctions) {
            bits.set(hash.hash(value), true);
        }
    }

    public boolean contains(Object value) {
        if (value == null) return false;
        boolean result = true;
        for (SimpleHash hash : hashFunctions) {
            result = result && bits.get(hash.hash(value));
        }
        return result;
    }

    @Override
    public String getName() {
        return "SimpleBloomFilter";
    }

    @Override
    public void put(Object obj) {
        add(obj);
    }

    @Override
    public boolean mightContain(Object obj) {
        return contains(obj);
    }

    public static class SimpleHash {
        private int cap;
        private int seed;

        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        public int hash(Object value) {
            int h;
            return (value == null) ? 0 : Math.abs(seed * (cap - 1) & ((h = value.hashCode()) ^ (h >>> 16)));
        }
    }
}
