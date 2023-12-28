package it.sella.practice.service;

public class MostOccurenceString {

    public static void main(String[] args) {
        MostOccurenceString ust = new MostOccurenceString();

        System.out.println(ust.solution("helowza"));
    }

    public String solution(String s) {
        int[] occurences=new int[26];
        for(char ch:s.toCharArray()){
            occurences[ch-'a']++;
        }
        char best_char='a';
        int best_res=0;

        for(int i=0;i<26;i++){
            if(occurences[i]>=best_res){
                best_char=(char)((int)'a'+i);
                best_res=occurences[i];
            }
        }
        return Character.toString(best_char);
    }
}

