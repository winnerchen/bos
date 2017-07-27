package cn.itcast.bos.utils;

import java.util.Arrays;

/**
 * Created by hasee on 2017/7/21.
 */
public class pyTest {
    public static void main(String[] args) {
        String s1 = "中华人民共和国";
        String[] headByString = PinYin4jUtils.getHeadByString(s1);


        System.out.println(Arrays.toString(headByString));
        String s = PinYin4jUtils.hanziToPinyin(s1, ",");
        System.out.println(s);
    }
}
