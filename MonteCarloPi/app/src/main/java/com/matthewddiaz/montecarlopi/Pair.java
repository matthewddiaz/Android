package com.matthewddiaz.montecarlopi;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by matthew on 2/7/15.
 */
public class Pair {
       private double x_coor;
       private double y_coor;

       Pair() {
           x_coor = random_generator();
           y_coor = random_generator();
       }

       public double getX() {
           return x_coor;
       }

       public double getY() {
           return y_coor;
       }

       public double random_generator() {
           Random rnd = new Random();
           return rnd.nextDouble();
       }


        boolean is_inside(){
            return ((x_coor*x_coor + y_coor*y_coor) <= 1.0);
        }

};
