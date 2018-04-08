package com.mooc.api.dao;

import com.mooc.api.common.RestResponse;
import com.mooc.api.config.GenericRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    private GenericRest rest;

    public String getUserName(Long id){
        String url = "direct://http://127.0.0.1:8083/getUserName?id=" + id;
        RestResponse<String> restResponse = rest.get(url, new ParameterizedTypeReference<RestResponse<String> >(){}).getBody();
        return restResponse.getResult();
    }
}
