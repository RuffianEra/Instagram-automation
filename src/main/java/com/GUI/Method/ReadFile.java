package com.GUI.Method;

import com.GUI.Entity.Relevance;
import com.GUI.Entity.URLData;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadFile {
    /** 自动读取用户帐号和密码 */
    public static Map<String, String[]> user = new HashMap<>();
    /** 自动读取评论地址 */
    public static List<String> url = new ArrayList<>();
    public static List<Integer> urls = new ArrayList<>();
    /** 自动读取评论数据 */
    public static List<String> data = new ArrayList<>();
    public static List<Integer> datas = new ArrayList<>();

    /** 用户随机评论集 */
    public static Map<String, Relevance> relevanceMap = new HashMap<>();
    /** 用户固定评论集 */
    public static List<Relevance> relevanceList = new ArrayList<>();

    /**
     * 自动读取文件
     */
    static {
        try{
            BufferedReader mapRead = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\user.txt")));
            String mapString = null;
            while((mapString = mapRead.readLine()) != null) {
                String[] str = mapString.split(",");
                user.put(str[0], str);
            }

            BufferedReader urlRead = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\url.txt")));
            while((mapString = urlRead.readLine()) != null) {
                url.add(mapString);
            }

            BufferedReader dataRead = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\data.txt")));
            while((mapString = dataRead.readLine()) != null) {
                data.add(mapString);
            }
        }
        catch (FileNotFoundException fe) {
            System.out.println(fe.getLocalizedMessage());
        }
        catch (IOException ioe) {
            System.out.println(ioe.getLocalizedMessage());
        }
    }


    /**
     * 1.完全随机获取用户评论集(用户评论地址评论数据随机,每次执行用户只评论一次)
     */
    public static void autoMatching() {
        for(String key:user.keySet()) {
            //relevanceMap.put(key, new Relevance(user.get(key), random(urls, url), random(datas, data)));
        }
    }

    /**
     * 2.固定获取用户评论集(每个用户评论所有评论地址，与评论地址对就的评论数据随机，每次执行每个用户评论N次，N等于评论地址数)
     */
    public static void fixedMatching(){
        for(String key:user.keySet()) {
            List<URLData> list = new ArrayList<>();
            for(String urling:url){
                if(urling.indexOf(user.get(key)[3]) > 0) {
                    list.add(new URLData(urling, random(datas, data)));
                }
            }
            relevanceMap.put(key, new Relevance(user.get(key), list));
        }
    }


    /**
     * 随机获取数据，在获取完所有数据集之前不能重复获取同一数据
     *
     * @param size 已经获取过的数据下标
     * @param set   准备获取的数据集
     * @return  返回获取到的数据
     */
    public static String random(List<Integer> size, List<String> set) {
        if (size.size() == set.size()) {
            size.clear();
        }

        while (true) {
            int number = (int) (Math.random() * set.size());
            if (!size.contains(number)) {
                size.add(number);
                return set.get(number);
            }
        }
    }
}
