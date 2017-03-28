//package com.s63b.controllers.Utility;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.type.TypeFactory;
//import com.s63b.domain.Pol;
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//public class ExtendedRestTestCase extends ExtendedTestCase {
//
//    private String basePath;
//
//    public ExtendedRestTestCase(String basePath) {
//        this.basePath = basePath;
//    }
//
//    public void testName() throws Exception {
//        assertTrue(true);
//    }
//
//    public Object httpRequest(String path, Class clazz, RequestMethod requestMethod) throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        HttpUriRequest request = null;
//
//        if (requestMethod == RequestMethod.GET){
//            request = new HttpGet(this.basePath + path);
//        }
//
//        if (requestMethod == RequestMethod.POST){
//            request = new HttpPost(this.basePath + path);
//        }
//
//        if (request == null){
//            return null;
//        }
//
//        HttpResponse response = HttpClientBuilder.create().build().execute(request);
//        String s = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name());
//
//        JSONObject rootObject = new JSONObject(s);
//
//        if (rootObject.has("context")){
//            JSONObject jsonObject = rootObject.getJSONObject("context");
//            Object obj = jsonObject.get("entity");
//
//            if (obj instanceof JSONArray){
//                return objectMapper.readValue(obj.toString(), TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
//            }else if (obj instanceof JSONObject) {
//                return objectMapper.readValue(obj.toString(), clazz);
//            }else if (rootObject.getString("statusInfo").equals("OK")){
//                return obj.toString();
//            }else{
//                return rootObject.getString("statusInfo") + " " + obj;
//            }
//        }else{
//            return rootObject.getString("error") + " " + rootObject.getString("message");
//        }
//    }
//
//    public void assertHttpException(String path, Class clazz, RequestMethod mode){
//        try {
//            String result = httpRequest(path, clazz, mode).toString();
//
//            if (result.startsWith("BAD_REQUEST") || result.startsWith("Bad Request")){
//                assertTrue(true);
//            }else{
//                fail("Expected an exception, didn't get one.");
//            }
//        } catch (Exception e) {
//            System.out.println("Error: " + e);
//            fail("Expected an exception, got an unexpected one.");
//        }
//    }
//
//}