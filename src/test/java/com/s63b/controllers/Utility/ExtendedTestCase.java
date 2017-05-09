//package com.s63b.controllers.Utility;
//
//import junit.framework.TestCase;
//
//public class ExtendedTestCase extends TestCase {
//
//    public void testName() throws Exception {
//        assertTrue(true);
//    }
//
//    public void assertException(Runnable runnable){
//        try{
//            runnable.run();
//            fail("Expected an exception, didn't get one.");
//        }catch(Exception ex){
//            assertTrue(true);
//        }
//    }
//
//    public void assertNoException(Runnable runnable){
//        try{
//            runnable.run();
//            assertTrue(true);
//        }catch(Exception ex){
//            fail("Didn't expect an exception, got one.\r\n" + ex.getMessage());
//        }
//    }
//
//}
