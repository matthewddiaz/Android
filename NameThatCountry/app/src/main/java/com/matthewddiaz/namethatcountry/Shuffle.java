package com.matthewddiaz.namethatcountry;
import java.util.Random;
/**
 * Created by matthew on 2/5/15.
 */
public class Shuffle {
    private int[] arr;


    public Shuffle(int[] array){
        arr = array;
    }

    public int[] shuffle(){
        Random rnd = new Random();
        int len = arr.length;
        for(int i = 0; i < len;++i){
            int s = i + rnd.nextInt(len-i);
            int temp = arr[s];
            arr[s] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

}
