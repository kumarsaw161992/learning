package it.sella.practice.service;

public class SubListWithOddMul {
    public static void main(String[] args) {
        System.out.println("hellow");
        Integer[] list = {1, 3, 5, 7,8,1};
        Integer subListWithOddMul = getSubLst(list);
        System.out.println(subListWithOddMul);
    }

    private static Integer getSubLst(Integer[] list) {
        int result = 0;
        int i=0;
        while(i<list.length) {
            int oddCount = 0;
            if (list[i] % 2 == 1) {
                for (int j = i; j < list.length; j++) {
                    if (list[j] % 2 == 0) {
                        break;
                    }
                    oddCount++;
                    i++;
                }
            }else{
                i++;
            }
            for (int k = 0; k <= oddCount; k++) {
                result += k;
            }
        }
        return result;
    }
}

