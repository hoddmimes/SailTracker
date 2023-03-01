package com.hoddmimes.sailtracker;

public class Test {
    public static void main(String[] args) {
        Test t = new Test();
        t.test();
    }

    private void test() {
        String tDateTimeUTC = "2023-02-26T16:18:34Z";

        System.out.println(tDateTimeUTC.replace("T", " ").substring(0, tDateTimeUTC.length() - 4));


    }
}