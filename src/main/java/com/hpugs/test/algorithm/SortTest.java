package com.hpugs.test.algorithm;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.Random;

/**
 * 排序算法：https://baijiahao.baidu.com/s?id=1759602341234685264&wfr=spider&for=pc
 */
public class SortTest {

    public static void main(String[] args) {
//        // java提供的排序方法
//        javaDefaultSort(buildRandomArray(10));
//
//        // 冒泡排序
//        bubblingSort(buildRandomArray(10));
//
//        // 插入排序
//        insertSort(buildRandomArray(10));
//
//        // 选择排序
//        selectSort(buildRandomArray(10));
//
//        // 快速排序
//        quickSort(buildRandomArray(10));

//        // 二分查找
//        int[] array = buildRandomArray(10);
//        Random random = new Random();
//        binarySearch(array, array[random.nextInt(10)], 0, array.length);
    }

    /**
     * 构造随机数组
     *
     * @param n 数组大小
     * @return
     */
    private static int[] buildRandomArray(int n) {
        int[] arrs = new int[n];
        Random random = new Random();

        for (int i = n - 1; i >= 0; i--) {
            arrs[i] = random.nextInt(100);
        }
        System.out.println("初始数组：" + JSONObject.toJSONString(arrs));
        return arrs;
    }

    private static void javaDefaultSort(int[] arrs) {
        System.out.println("Arrays.sort排序：begin");
        Arrays.sort(arrs);
        System.out.println("排序结果：" + JSONObject.toJSONString(arrs));
        System.out.println("Arrays.sort排序：end");
    }

    /**
     * 冒泡排序
     * 思路：左边大于右边交换一趟排下来最大的在右边
     *
     * @param arrs 待排序数组
     */
    private static void bubblingSort(int[] arrs) {
        System.out.println("冒泡排序：begin");
        for (int i = 0; i < arrs.length; i++) {
            for (int j = 0; j < arrs.length - i - 1; j++) {
                if (arrs[j] > arrs[j + 1]) {
                    int n = arrs[j + 1];
                    arrs[j + 1] = arrs[j];
                    arrs[j] = n;
                }
            }
        }
        System.out.println("排序结果：" + JSONObject.toJSONString(arrs));
        System.out.println("冒泡排序：end");
    }

    /**
     * 插入排序
     * 思路：在待排序的元素中，假设前n-1个元素已有序，现将第n个元素插入到前面已经排好的序列中，使得前n个元素有序。
     * 按照此法对所有元素进行插入，直到整个序列有序。
     * 但我们并不能确定待排元素中究竟哪一部分是有序的，所以我们一开始只能认为第一个元素是有序的，
     * 依次将其后面的元素插入到这个有序序列中来，直到整个序列有序为止。
     *
     * @param arrs 待排序数组
     */
    private static void insertSort(int[] arrs) {
        System.out.println("插入排序：begin");
        for (int i = 1; i < arrs.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arrs[j] > arrs[j + 1]) {
                    int n = arrs[j + 1];
                    arrs[j + 1] = arrs[j];
                    arrs[j] = n;
                } else {
                    break;
                }
            }
        }
        System.out.println("排序结果：" + JSONObject.toJSONString(arrs));
        System.out.println("插入排序：end");
    }

    /**
     * 选择排序
     * 思路：每次从待排序列中选出一个最小值，然后放在序列的起始位置，直到全部待排数据排完即可。
     *
     * @param arrs 待排序数组
     */
    private static void selectSort(int[] arrs) {
        System.out.println("选择排序：begin");
        for (int i = 0; i < arrs.length; i++) {
            int small = arrs[i];
            for (int j = i + 1; j < arrs.length; j++) {
                if (small > arrs[j]) {
                    int n = arrs[j];
                    arrs[j] = small;
                    small = n;
                }
            }
            arrs[i] = small;
        }
        System.out.println("排序结果：" + JSONObject.toJSONString(arrs));
        System.out.println("选择排序：end");
    }

    /**
     * 快速排序
     *
     * @param arrs 待排序数组
     */
    private static void quickSort(int[] arrs) {
        System.out.println("快速排序：begin");
        quickSort(arrs, 0, arrs.length - 1);
        System.out.println("排序结果：" + JSONObject.toJSONString(arrs));
        System.out.println("快速排序：end");
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // 获取分区后的枢纽位置
            int pivotIndex = partition(arr, low, high);

            // 分别对枢纽左右两边的子数组进行递归排序
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        // 选择数组的最后一个元素作为枢纽值
        int pivot = arr[high];
        int i = (low - 1);

        // 遍历数组，将小于枢纽值的元素放到左边，大于枢纽值的元素放到右边
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;

                // 交换 arr[i] 和 arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // 将枢纽元素放到正确的位置
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        // 返回枢纽位置
        return i + 1;
    }

    /**
     * 二分查找
     *
     * @param array
     * @param n
     */
    private static void binarySearch(int[] array, int n, int left, int right) {
        Arrays.sort(array);

        int index = (left + right) / 2;
        if (array[index] == n) {
            System.out.println("数组：" + JSONObject.toJSONString(array));
            System.out.println("查询数字：" + n);
            System.out.println("数字位置：" + (index + 1));
        } else if (array[index] > n) {
            binarySearch(array, n, left, index);
        } else {
            binarySearch(array, n, index, right);
        }
    }

}
