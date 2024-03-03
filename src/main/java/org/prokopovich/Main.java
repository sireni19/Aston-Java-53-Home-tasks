package org.prokopovich;

import org.prokopovich.custom_structures.CustomHashMap;

public class Main {
    public static void main(String[] args) {

        CustomHashMap<String, Integer> map = new CustomHashMap<>();
        map.put("ryan", 1);
        System.out.println(map.capacity());
        map.put("kim", 2);
        map.put("nina", 3);
//        System.out.println(map.getValue("ryan"));
        map.put("dima", 99999);
//        System.out.println(map.getValue("ryan"));
//        System.out.println(map.getValue("nina"));
//        System.out.println(map.size());
        System.out.println("---");
        System.out.println(map.getValue("ryan"));
        System.out.println(map.getValue("kim"));
        System.out.println(map.getValue("nina"));

//        System.out.println(map.size());



    }
}