package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AssertionTest {
    public static void main(String[] args) {
        Assert.assertEquals(5,5,10);

    }
    public static int myCalc(int a, int b) {
        return a+b;
    }

//    public static boolean myValue() {
//
//        return true;
//    }
//
//    @Test
////    public void testCalc() {
////       // Assert.assertEquals(myCalc(5,2), 10);
////       // Assert.assertTrue(myValue());
////        Assert.assertThrows();
////    }
}
