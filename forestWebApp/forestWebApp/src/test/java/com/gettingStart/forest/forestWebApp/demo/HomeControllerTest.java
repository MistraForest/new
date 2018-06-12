package com.gettingStart.forest.forestWebApp.demo.web;

import com.gettingStart.forest.forestWebApp.ForestWebAppApplication;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ForestWebAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void runAndInvokeHome(){
        String url = "http://localhost:"+ port +"/";
        String body = new RestTemplate().getForObject(url, String.class);
        assertThat(body,CoreMatchers.equalTo("Hello forest"));
    }
}