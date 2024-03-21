package com.estsoft.blogjpa;

import org.junit.jupiter.api.*;

public class JUnitTest {



    @BeforeAll                    // 전체 테스트를 시작하기 전 1회 실행
    public static void beforeAll() {
        System.out.println("@BeforeAll");
    }

    @BeforeEach                    // 각각의 테스트 케이스를 실행하기 전마다 실행
    public void beforeEach() {
        System.out.println("@BeforeEach");
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    @Test
    public void test3() {
        System.out.println("test3");
    }

    @Test
    public void test()
    {
        int a = 1;
        int b = 2;
        int sum = 3;

        //Assertions.assertEquals(sum,a+b);   // JUnit5 사용
        org.assertj.core.api.Assertions.assertThat(a+b).isEqualTo(sum); // AssertJ 사용
    }

    @AfterAll                        // 전체 테스트를 마치고 종료하기 전에 1회 실행
    public static void afterAll() {
        System.out.println("@AfterAll");
    }

    @AfterEach						// 각각의 테스트 케이스를 종료하기 전마다 실행
    public void afterEach() {
        System.out.println("@AfterEach");
    }
}
