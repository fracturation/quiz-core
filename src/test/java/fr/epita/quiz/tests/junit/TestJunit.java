package fr.epita.quiz.tests.junit;

import org.junit.*;

public class TestJunit {

    @BeforeClass
    public static void beforeAll() {
        System.out.println("beforeall");
    }

    @Before
    public void prepareForEach() {
        System.out.println("beforeeach");
    }

    @Test
    public void firstTest() {
        System.out.println("test");
    }

    @After
    public void tearDownForEach() {
        System.out.println("aftereach");
    }

    @AfterClass
    public static void afterAll() {
        System.out.println("afterall");
    }
}
