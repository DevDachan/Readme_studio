package com.example.Rss_project.test;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;

public class TestLifeCycle {

    @BeforeAll
    static void beforeAll(){
        System.out.println("## BeforeAll Annotation 호출 ##");
        System.out.println();
    }

    @AfterAll
    static void afterAll(){
        System.out.println("## BeforeAll Annotation 호출 ##");
        System.out.println();
    }

    @AfterEach
    void afterEach(){
        System.out.println("## afterEach Annotation 호출 ##");
        System.out.println();
    }

    @Test
    void test1(){
        System.out.println("## Test 1 시작");
        System.out.println();
    }
    @Test
    @DisplayName("Test Case 2!! ")
    void test2(){
        System.out.println("@@ Test 2 시작");
        System.out.println();
    }

    @Test
    @Disabled // 테스트를 실행하지 않게 설정하는 어노테이션
    void test3(){
        System.out.println("Test 3 시작");
        System.out.println();
    }
}
