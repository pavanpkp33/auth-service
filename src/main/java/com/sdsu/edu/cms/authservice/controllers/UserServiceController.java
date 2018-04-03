package com.sdsu.edu.cms.authservice.controllers;


import com.sdsu.edu.cms.common.models.response.ServiceResponse;
import com.sdsu.edu.cms.common.models.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1" , produces = {APPLICATION_JSON_VALUE}, consumes = {APPLICATION_JSON_VALUE})
public class UserServiceController {

    @PostMapping("/users")
    public ServiceResponse getUsers(Map<String, Object> payLoad){
        if(!payLoad.isEmpty()){
            String id = payLoad.get("id").toString();
        }

        return null;
    }


    @GetMapping("/users/{id}")
    public ServiceResponse getUserInfoById(@PathVariable String id){

        return null;

    }


    @PutMapping("/users/{id}")
    public ServiceResponse updateUserInfo(@RequestBody User user, @PathVariable String id){

        return null;
    }

    @PatchMapping("/users/{id}")
    public ServiceResponse updateUserInfoField(@RequestBody Map<String, Object> payLoad, @PathVariable String id){

        return null;
    }

    @GetMapping("/users/{id}/conferences")
    public ServiceResponse getUserConferences(@PathVariable String id){
        return null;

    }




}
