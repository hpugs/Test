package com.hpugs.test.bloom_filter;

/**
 * @Author：xinge
 * @Date：2024/11/11 16:51
 * @Description：
 */
public class BloomFilterTest {

    public static void main(String[] args) {
        SimpleBloomFilter simpleBloomFilter = new SimpleBloomFilter();
        test(simpleBloomFilter, 2 << 24);

        GuavaBloomFilter guavaBloomFilter = new GuavaBloomFilter(100, 0.01);
        test(guavaBloomFilter, 100);

        HutoolBloomFilter hutoolBloomFilter = new HutoolBloomFilter(100);
        test(hutoolBloomFilter, 100);
    }

    public static void test(IBloomFilter bloomFilter, int capacity){
        for (int i = 0; i < capacity; i++) {
            bloomFilter.put(i);
        }

        int count = 0;
        for (int i = capacity; i < capacity*2; i++) {
            if(bloomFilter.mightContain(i)){
                count++;
            }
        }
        System.out.println(bloomFilter.getName() + "容错率：" + count);
    }

}
