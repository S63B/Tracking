//package com.s63b.controllers;
//
//import com.s63b.controllers.Utility.ExtendedRestTestCase;
//import com.s63b.dao.PolDao;
//import com.s63b.domain.Pol;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.util.List;
//
//public class PolControllerTest extends ExtendedRestTestCase {
//
//    public PolControllerTest() {
//        super("http://localhost:8080/");
//    }
//
//    public void setUp() throws Exception {
//        super.setUp();
//
//        new PolDao().removePols("25-GGA-3");
//    }
//
//    public void testPolController() throws Exception {
//        String licensePlate = "25-GGA-3";
//        String lat = "51.4505821";
//        String lng = "5.4686695";
//        long timestamp = System.currentTimeMillis();
//
//        Pol pol = null;
//        Object o = null;
//        try{
//            o = httpRequest("pol?license_plate=" + licensePlate + "&lat=" + lat + "&lng=" + lng + "&timestamp=" + timestamp, Pol.class, RequestMethod.POST);
//            pol = (Pol) o;
//        }catch (ClassCastException e){
//            fail("Error: " + o);
//            return;
//        }
//
//        assertEquals(licensePlate, pol.getLicensePlate());
//        assertEquals(lat, "" + pol.getLat());
//        assertEquals(lng, "" + pol.getLng());
//        assertNotNull(timestamp = pol.getTimestampMillis());
//
//        List<Pol> pols = (List<Pol>) httpRequest("pols?license_plate=" + licensePlate, Pol.class, RequestMethod.GET);
//
//        assertEquals(1, pols.size());
//        assertEquals(licensePlate, pols.get(0).getLicensePlate());
//        assertEquals(lat, "" + pols.get(0).getLat());
//        assertEquals(lng, "" + pols.get(0).getLng());
//        assertEquals(timestamp, pol.getTimestampMillis());
//
//        assertHttpException("pol?license_plate=" + "ASDF" + "&lat=" + lat + "&lng=" + lng, Pol.class, RequestMethod.POST);
//        assertHttpException("pol?license_plate=" + licensePlate + "&lat=" + "ASDF" + "&lng=" + lng, Pol.class, RequestMethod.POST);
//        assertHttpException("pol?license_plate=" + licensePlate + "&lat=" + lat + "&lng=" + "ASDF", Pol.class, RequestMethod.POST);
//
//
//        String lat2 = "61.3525821";
//        String lng2 = "15.4618665";
//        long timestamp2;
//
//        Pol pol2 = (Pol) httpRequest("pol?license_plate=" + licensePlate + "&lat=" + lat2 + "&lng=" + lng2 + "&timestamp=" + System.currentTimeMillis(), Pol.class, RequestMethod.POST);
//
//        assertEquals(licensePlate, pol2.getLicensePlate());
//        assertEquals(lat2, "" + pol2.getLat());
//        assertEquals(lng2, "" + pol2.getLng());
//        assertNotNull(timestamp2 = pol2.getTimestampMillis());
//
//        String testResult = (String) httpRequest("distance?license_plate=" + licensePlate + "&start_date=" + (timestamp - 1) + "&end_date=" + (timestamp2 + 1), Pol.class, RequestMethod.GET);
//
//        assertTrue(1632849 == Long.parseLong(testResult));
//    }
//}