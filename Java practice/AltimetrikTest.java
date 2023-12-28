package it.sella.practice.service;


public class AltimetrikTest {

    public static void main(String[] args) {
       Integer[] array={9,5,4,3,2,11,6,6,11,9,10};

       int large=array[0];
       int sl=array[0];

       for(int i=1; i<array.length; i++){
           if(large<array[i]){
               large=array[i];
           }

           if(array[i]>sl && array[i]<large){
               sl=array[i];
           }
       }

        System.out.println("largest: "+large);
       System.out.println("second largest: "+sl);

    }


}
