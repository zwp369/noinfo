package com.g7s.ics.demo;

import org.junit.Test;
import org.junit.jupiter.api.Tag;

public class ATest {

    @Test
    public void fun1(){
        System.out.println("fun1");
    }

    @Test
    @Tag("ceshi")
    public void fun2(){
        System.out.println("fun2");
    }

    @Test
    public void fun3(){
        System.out.println("fun3");
    }


    @Test
    public void fun4(){
        System.out.println("fun4");
    }


    @Test
    public void fun5(){
        System.out.println("fun5");
    }
}
