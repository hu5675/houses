package com.mooc.house.web;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HttpClientTests {

    @Autowired
    private HttpClient httpClient;

    @Test
    public void testHttpClient() throws IOException {
        String result = EntityUtils.toString(httpClient.execute(new HttpGet("http://www.baidu.com")).getEntity());
        System.out.println(result);
    }

}

