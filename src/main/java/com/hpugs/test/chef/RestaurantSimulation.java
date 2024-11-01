package com.hpugs.test.chef;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RestaurantSimulation {

    public static void main(String[] args) {
        BlockingQueue<Dish> dishesQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Dish> chefQueue = new LinkedBlockingQueue<>();

        Thread[] customers = new Thread[3];
        for (int i = 0; i < 3; i++) {
            customers[i] = new Thread(new Customer(i + 1, dishesQueue));
            customers[i].start();
        }

        Thread[] waiters = new Thread[5];
        for (int i = 0; i < 5; i++) {
            waiters[i] = new Thread(new Waiter(i + 1, dishesQueue, chefQueue));
            waiters[i].start();
        }

        Thread[] chefs = new Thread[2];
        for (int i = 0; i < 2; i++) {
            chefs[i] = new Thread(new Chef(i + 1, chefQueue));
            chefs[i].start();
        }
    }

}
