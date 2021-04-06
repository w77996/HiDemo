package com.w77996.字符串;

/**
 * @ClassName Solution
 * @Description 判断回文
 * @author wuhaihui
 * @date 2021/4/6 10:41
 */
public class Palindrome {


    public boolean isPalindrome(String s){
        if(s.equalsIgnoreCase(new StringBuilder(s).reverse().toString())){
            return true;
        }
        return false;
    }

    public boolean isPalindrome2 (String str) {
        // write code here
        int right = str.length()-1;
        int left =0;
        while(left < right){
            if(str.charAt(left) != str.charAt(right)){
                return false;
            }
            left ++;
            right --;
        }
        return true;
    }

}
