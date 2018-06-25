package com.mooc.api.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.httpclient.LogbookHttpRequestInterceptor;
import org.zalando.logbook.httpclient.LogbookHttpResponseInterceptor;

import static org.zalando.logbook.Conditions.*;

@Configuration
@ConditionalOnClass(HttpClient.class)
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {


    private final HttpClientProperties properties;

    public HttpClientAutoConfiguration(HttpClientProperties properties){
        this.properties = properties;
    }

    @Autowired
    LogbookHttpRequestInterceptor logbookHttpRequestInterceptor;

    @Autowired
    LogbookHttpResponseInterceptor logbookHttpResponseInterceptor;

    @Bean
    @ConditionalOnMissingBean(HttpClient.class)
    public HttpClient httpClient(){

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(properties.getConnectTimeout())
                .setSocketTimeout(properties.getSocketTimeout()).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
                .setUserAgent(properties.getAgent())
                .setMaxConnPerRoute(properties.getMaxConnPerRoute())
                .setMaxConnTotal(properties.getMaxConnTotal())
                .addInterceptorFirst(logbookHttpResponseInterceptor)
                .addInterceptorFirst(logbookHttpResponseInterceptor)
                .build();
        return  client;
    }
}

