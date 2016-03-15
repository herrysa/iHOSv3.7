package com.huge.ihos.system.reportManager.search.util;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FontUtil {
    public static List GetSystemFont() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();//GraphicsEnvironment是一个抽象类，不能实例化，只能用其中的静态方法获取一个实例 //该类中又获取系统字体的方法；
        String[] str = environment.getAvailableFontFamilyNames();//获取系统字体
        String[] strs = new String[6];
        int j = 0;
        for ( int i = str.length - 1; i >= 0; i-- ) {
            if ( j == 6 ) {
                break;
            }
            strs[j] = str[i];
            j++;
        }
        ArrayList<String> list = new ArrayList<String>( Arrays.asList( strs ) );
        return list;
    }

    public static String GetSystemFontOne() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();//GraphicsEnvironment是一个抽象类，不能实例化，只能用其中的静态方法获取一个实例 //该类中又获取系统字体的方法；
        String[] str = environment.getAvailableFontFamilyNames();//获取系统字体
        return environment.getAvailableFontFamilyNames()[str.length - 1];
    }
}
