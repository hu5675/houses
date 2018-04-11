package com.mooc.house.srv.dao;

import com.mooc.house.srv.common.RestResponse;
import com.mooc.house.srv.config.GenericRest;
import com.mooc.house.srv.model.User;
import com.mooc.house.srv.utils.Rests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

  @Autowired
  private GenericRest rest;
  
  @Value("${user.service.name}")
  private String userServiceName;

  public User getAgentDetail(Long agentId) {
   RestResponse<User> response = Rests.exc(() -> {
      String url = Rests.toUrl(userServiceName, "/agency/agentDetail" + "?id=" + agentId);
      ResponseEntity<RestResponse<User>> responseEntity = rest.get(url, new ParameterizedTypeReference<RestResponse<User>>() {});
      return responseEntity.getBody();
    });
   return response.getResult();
  }


  
  
}
