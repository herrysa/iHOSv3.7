package com.huge.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;



public class Program{
	public static void main(String[] args) throws IOException {
		BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream( new FileInputStream( "D:/sz000001.day" ) );

            // 缓冲数组
            byte[] b = new byte[4];
            int len;
            while ( ( len = inBuff.read( b ) ) != -1 ) {
            	String s1 = toHexString1(b[3])+toHexString1(b[2])+toHexString1(b[1])+toHexString1(b[0]);
            	String s2 = toHexString1(b[2]);
            	String s3 = toHexString1(b[1]);
            	String s4 = toHexString1(b[0]);
            	
            	Long long1 = Long.parseLong(s1,16);
            	Long long2 = Long.parseLong(s2,16);
            	Long long3 = Long.parseLong(s3,16);
            	Long long4 = Long.parseLong(s4,16);
            	Long long5 = Long.parseLong(s1,16);

            	 System.out.println(long5);
                 //System.out.println(long2);
                 //System.out.println(long3);
                 //System.out.println(long4);
                //System.out.println();
            }
        }
        finally {
            // 关闭流
            if ( inBuff != null )
                inBuff.close();
        }
	}
	public static String toHexString1(byte b){
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1){
            return "0" + s;
        }else{
            return s;
        }
    }
}
