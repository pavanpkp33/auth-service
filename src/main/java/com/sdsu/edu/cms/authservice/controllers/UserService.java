package com.sdsu.edu.cms.authservice.controllers;


import com.sdsu.edu.cms.authservice.proxy.DataServiceProxy;
import com.sdsu.edu.cms.authservice.util.Constants;
import com.sdsu.edu.cms.common.models.response.DataServiceResponse;
import com.sdsu.edu.cms.common.models.user.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1", produces = {APPLICATION_JSON_VALUE}, consumes = {APPLICATION_JSON_VALUE})
public class UserService {

    @Autowired
    DataServiceProxy dataProxy;

    @PostMapping("/auth/login")
    public DataServiceResponse authenticateUser(@RequestBody AuthUser userCreds){
        String userPwd = userCreds.getPassword();
        DataServiceResponse response = dataProxy.queryUserName(userCreds.getEmail());
        System.out.println(response);
        return null;

    }

}
