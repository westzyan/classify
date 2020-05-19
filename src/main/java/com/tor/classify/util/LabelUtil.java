package com.tor.classify.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;

public class LabelUtil {
    public static String python = PropertiesUtil.getPython();
    public static String makeLabelPy = PropertiesUtil.getMakeLabelPy();

    public static boolean singleCsvLabel(String filePath, String fileType){
        String[] arguments = new String[] {python, makeLabelPy, filePath, fileType};
        try {
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            //java代码中的process.waitFor()返回值为0表示我们调用python脚本成功，
            //返回值为1表示调用python脚本失败，这和我们通常意义上见到的0与1定义正好相反
            int re = process.waitFor();
            System.out.println(re);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /*
     一个目录下的文件打标签
     */
    public static boolean dirCsvLabel(String dirName, String fileType){
        String[] files = new File(dirName).list();
        System.out.println(Arrays.toString(files));
        System.out.println("Found "+files.length+" Files..");
        for (String file : files) {
            String[] arguments = new String[] {python, makeLabelPy, dirName + File.separator+ file, fileType};
            try {
                Process process = Runtime.getRuntime().exec(arguments);
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
                String line = null;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                in.close();
                //java代码中的process.waitFor()返回值为0表示我们调用python脚本成功，
                //返回值为1表示调用python脚本失败，这和我们通常意义上见到的0与1定义正好相反
                int re = process.waitFor();
                System.out.println(re);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
