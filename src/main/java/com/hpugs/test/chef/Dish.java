package com.hpugs.test.chef;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Dish {

    private final int orderNumber;

    public Dish(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }
}

class Customer implements Runnable {
    private final int customerId;
    private final BlockingQueue<Dish> dishesQueue;
    private final Random random = new Random();

    public Customer(int customerId, BlockingQueue<Dish> dishesQueue) {
        this.customerId = customerId;
        this.dishesQueue = dishesQueue;
    }

    @Override
    public void run() {
        int numDishes = random.nextInt(4) + 2;
        System.out.println("Customer " + customerId + " ordered " + numDishes + " dishes.");
        for (int i = 1; i <= numDishes; i++) {
            try {
                dishesQueue.put(new Dish(i));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        try {
            while (numDishes > 0) {
                Dish dish = dishesQueue.take();
                System.out.println("Customer " + customerId + " received dish " + dish.getOrderNumber());
                numDishes--;
            }
            System.out.println("Customer " + customerId + " leaves satisfied.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Waiter implements Runnable {
    private final int waiterId;
    private final BlockingQueue<Dish> dishesQueue;
    private final BlockingQueue<Dish> chefQueue;

    public Waiter(int waiterId, BlockingQueue<Dish> dishesQueue, BlockingQueue<Dish> chefQueue) {
        this.waiterId = waiterId;
        this.dishesQueue = dishesQueue;
        this.chefQueue = chefQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Dish dish = dishesQueue.take();
                System.out.println("Waiter " + waiterId + " submitted dish order " + dish.getOrderNumber() + " to chef.");
                chefQueue.put(dish);
                Dish cookedDish = chefQueue.take();
                System.out.println("Waiter " + waiterId + " picked up cooked dish " + cookedDish.getOrderNumber());
                dishesQueue.put(cookedDish);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

class Chef implements Runnable {
    private final int chefId;
    private final BlockingQueue<Dish> chefQueue;
    private final Random random = new Random();

    public Chef(int chefId, BlockingQueue<Dish> chefQueue) {
        this.chefId = chefId;
        this.chefQueue = chefQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Dish dish = chefQueue.take();
                int cookingTime = random.nextInt(3) + 1;
                Thread.sleep(cookingTime * 1000);
                System.out.println("Chef " + chefId + " cooked dish " + dish.getOrderNumber() + " in " + cookingTime + " seconds.");
                chefQueue.put(dish);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

