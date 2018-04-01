package com.sdsu.edu.cms.authservice.controllers;


import com.sdsu.edu.cms.common.models.response.DataServiceResponse;
import com.sdsu.edu.cms.common.models.user.AuthUser;
import com.sdsu.edu.cms.common.models.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1" , produces = {APPLICATION_JSON_VALUE}, consumes = {APPLICATION_JSON_VALUE})
public class UserServiceController {

    @PostMapping("/users")
    public DataServiceResponse getUsers(Map<String, Object> payLoad){
        if(!payLoad.isEmpty()){
            String id = payLoad.get("id").toString();
        }

        return null;
    }


    @GetMapping("/users/{id}")
    public DataServiceResponse getUserInfoById(@PathVariable String id){

        return null;

    }


    @PutMapping("/users/{id}")
    public DataServiceResponse updateUserInfo(@RequestBody User user, @PathVariable String id){

        return null;
    }

    @PatchMapping("/users/{id}")
    public DataServiceResponse updateUserInfoField(@RequestBody Map<String, Object> payLoad, @PathVariable String id){

        return null;
    }

    @GetMapping("/users/{id}/conferences")
    public DataServiceResponse getUserConferences(@PathVariable String id){
        return null;

    }




}
