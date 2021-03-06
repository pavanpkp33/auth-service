package com.sdsu.edu.cms.authservice.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sdsu.edu.cms.authservice.proxy.DataServiceProxy;

import com.sdsu.edu.cms.authservice.service.AuthService;
import com.sdsu.edu.cms.common.models.response.ServiceResponse;
import com.sdsu.edu.cms.common.models.user.AuthUser;


import com.sdsu.edu.cms.common.models.user.User;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1")
public class AuthServiceController {

    @Autowired
    DataServiceProxy dataProxy;
    @Autowired
    ServiceResponse response;
    @Autowired
    AuthService authService;

    @PostMapping(value = "/auth/login", produces = {APPLICATION_JSON_VALUE}, consumes = {APPLICATION_JSON_VALUE})
    public ServiceResponse authenticateUser(@RequestBody AuthUser userCreds, HttpServletResponse res){
        String userPwd = userCreds.getPassword();

        try {
             response = dataProxy.queryUserName(userCreds.getEmail());
             String resp = response.getData().get(0).toString().trim();

             AuthUser user = new Gson().fromJson(resp, AuthUser.class);
             if(authService.authenticate(userPwd, user.getPassword())){

                 if(!user.getIsActive().equals("Y")){
                     res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                     response.setMessage("User not activated. Please activate your account using the activation link sent to your email.");
                     response.setData(Arrays.asList(false));
                     return response;
                 }else if(!user.getIsValid().equals("Y")){
                     res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                     response.setData(null);
                     response.setMessage("Account disabled. Please contact support or an administrator");
                     return response;
                 }
                 res.setStatus(HttpServletResponse.SC_OK);
                 response.setMessage("User Authenticated");
                 response.setData(Arrays.asList(user.getId()));
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

    @PostMapping(value = "/auth/register", produces = {APPLICATION_JSON_VALUE}, consumes = {APPLICATION_JSON_VALUE})
    public ServiceResponse registerUser(@RequestBody User userDetails, HttpServletResponse res){

        try{
            String hashedPwd = authService.hashPwd(userDetails.getPassword());
            userDetails.setPassword(hashedPwd);
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
    public ServiceResponse activateUser(@PathVariable("id") String id, @PathVariable("token") String token, HttpServletResponse res){
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("token", token);
        ServiceResponse response = dataProxy.activateUser(map);
        String code = response.getMessage();
        if(code.equals("1")){
            res.setStatus(HttpServletResponse.SC_OK);
            response.setData(Arrays.asList(true));
            response.setMessage("User account activated successfully.");

        }else if(code.equals("-1")){
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setData(Arrays.asList(false));
            response.setMessage("Failed to activate user account");

        }else{
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setData(Arrays.asList(false));
            response.setMessage("Invalid activation token");

        }
        return response;
    }

}
