package com.hpugs.test.bloom_filter;

import java.util.BitSet;

/**
 * bloom filter 布隆过滤器
 */
public class SimpleBloomFilter {

    private static final int DEFAULT_SIZE = 2 << 24;
    private static final int[] seeds = new int[]{7, 11, 13, 31, 37, 41, 43, 47, 53, 59};

    private BitSet bits = new BitSet(DEFAULT_SIZE);
    private SimpleHash[] hashFunctions = new SimpleHash[seeds.length];

    public SimpleBloomFilter() {
        for (int i = 0; i < seeds.length; i++) {
            hashFunctions[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
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
            result = result & bits.get(hash.hash(value));
        }
        return result;
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
