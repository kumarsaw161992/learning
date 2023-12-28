package it.sella.practice.service;

public class USTTest {

    public static void main(String[] args) {
        USTTest ust=new USTTest();
        Integer array1[]={2,3,6,8,8,0};
        Integer array2[]={1,5,7,10,-1,0,5};

        Integer finalArray[]=new Integer[array1.length+array2.length];

        for(int i=0; i< array1.length+array2.length; i++){
            if(i<array1.length)
                finalArray[i]=array1[i];
            else
                finalArray[i]=array2[i-array1.length];
        }

        for(int i=0; i<finalArray.length-1;i++){ //2 1 3 4 5 6
            for(int j=i+1;j<finalArray.length;j++){
                if(finalArray[i]>finalArray[j]){
                    int temp=finalArray[i];
                    finalArray[i]=finalArray[j];
                    finalArray[j]=temp;
                }
            }
        }

        for(int i:finalArray){
            System.out.print(i +",");
        }
    }
}
