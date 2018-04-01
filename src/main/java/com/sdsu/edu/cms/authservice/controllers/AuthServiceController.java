package com.sdsu.edu.cms.authservice.controllers;


import com.google.gson.Gson;
import com.sdsu.edu.cms.authservice.proxy.DataServiceProxy;

import com.sdsu.edu.cms.authservice.service.AuthService;
import com.sdsu.edu.cms.common.models.response.DataServiceResponse;
import com.sdsu.edu.cms.common.models.user.AuthUser;


import com.sdsu.edu.cms.common.models.user.User;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1", produces = {APPLICATION_JSON_VALUE}, consumes = {APPLICATION_JSON_VALUE})
public class AuthServiceController {

    @Autowired
    DataServiceProxy dataProxy;
    @Autowired
    DataServiceResponse response;
    @Autowired
    AuthService authService;

    @PostMapping("/auth/login")
    public DataServiceResponse authenticateUser(@RequestBody AuthUser userCreds, HttpServletResponse res){
        String userPwd = userCreds.getPassword();


        try {
             response = dataProxy.queryUserName(userCreds.getEmail());
             AuthUser user = new Gson().fromJson(response.getData().get(0).toString(), AuthUser.class);
             if(authService.authenticate(userPwd, user.getPassword())){
                 res.setStatus(HttpServletResponse.SC_OK);
                 response.setMessage("User Authenticated");
                 response.setData(Arrays.asList(new String[]{user.getId()}));
                 return response;
             }else{

                 res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                 response.setData(null);
                 response.setMessage("Incorrect password");
             }
        }catch(FeignException e){
            if(e.status() == 404){
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setData(null);
                response.setMessage("Invalid Email ID");

            }

            return response;
        }

        return response;


    }

    @PostMapping("/auth/register")
    public DataServiceResponse registerUser(@RequestBody User userDetails, HttpServletResponse res){

        try{
            userDetails.setPassword(authService.hashPwd(userDetails.getPassword()));
            userDetails.setId(UUID.randomUUID().toString());
            response = dataProxy.saveUser(userDetails);
            res.setStatus(HttpServletResponse.SC_CREATED);
            return response;
        }catch (FeignException e){
            if(e.status() == HttpServletResponse.SC_CONFLICT){
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                response.setData(null);
                response.setMessage("User with this email ID already exists.");
                return response;
            }else{
                response.setData(null);
                response.setMessage(e.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return response;
            }

        }

    }

    @GetMapping("/activate/{id}/token/{token}")
    public DataServiceResponse activateUser(@PathVariable("id") String id, @PathVariable("token") String token){

        return null;
    }

}
