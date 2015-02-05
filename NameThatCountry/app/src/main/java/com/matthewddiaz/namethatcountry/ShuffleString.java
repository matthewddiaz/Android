package com.matthewddiaz.namethatcountry;
import java.util.Random;
/**
 * Created by matthew on 2/5/15.
 */
public class ShuffleString {
    private String[] arr;


    public ShuffleString(String[] array){
        arr = array;
    }

    public String[] shuffle(){
        Random rnd = new Random();
        int len = arr.length;
        for(int i = 0; i < len;++i){
            int s = i + rnd.nextInt(len-i);
            String temp = arr[s];
            arr[s] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

}
