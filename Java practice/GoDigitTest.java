package it.sella.practice.service;

import java.util.HashMap;
import java.util.Map;

public class GoDigitTest {

    public static String anagram = "";

    public static void main(String[] args) {
        String s1 = "aaaaaabbbbbccccc";
        String s2 = "bbbddddaaa";
        Map<Character, Integer> charCount1 = new HashMap<>();
        Map<Character, Integer> charCount2 = new HashMap<>();

        for (char c : s1.toCharArray()) {
            if (charCount1.get(c) != null) {
                int count = charCount1.get(c);
                charCount1.put(c, ++count);
            } else {
                charCount1.put(c, 1);
            }
        }

        for (char c : s2.toCharArray()) {
            if (charCount2.get(c) != null) {
                int count = charCount2.get(c);
                charCount2.put(c, ++count);
            } else {
                charCount2.put(c, 1);
            }
        }
        System.out.println(charCount1);
        System.out.println(charCount2);
        int count = 0;
        Integer[] arr1 = {1, 0, 0, 2, 4, 0, 0, 1}; //accddddg
        Integer[] arr2 = {1, 0, 0, 2, 4, 0, 0, 1}; //accddddg
        for (int i : arr1) {
            /*if (arr1[i] == arr2[i]) {
                char c = (char) (96 + i);
                for (int j = 1; j <= arr1[i]; j++) {
                    anagram += c;
                }
            }*/
            if (arr1[i] != 0 && arr2[i] != 0) {
               /* char c = (char) (96 + i);
                anagram += c;*/
               if(arr1[i]>arr2[i]) {
                   count += arr1[i] - arr2[i];
               }else{
                   count += arr2[i] - arr1[i];
               }
            } else {
                if (arr1[i] != 0 || arr2[i] != 0)
                    count = count + arr1[i] + arr2[i];
            }
        }
       /* charCount1.forEach((k,v)->{
            if(charCount2.containsKey(k)){
               generateAnagram(k);
            }
        });*/

        System.out.println(anagram);

        int s1Count = s1.length() - anagram.length();
        int s2Count = s2.length() - anagram.length();

        System.out.println("total character to be removed to make an anagram=" + (s1Count + s2Count));

    }

    private static void generateAnagram(Character k) {
        anagram += k;
    }

}

