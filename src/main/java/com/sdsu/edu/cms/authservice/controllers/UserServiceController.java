package com.sdsu.edu.cms.authservice.controllers;


import com.sdsu.edu.cms.authservice.exception.ApiErrorException;
import com.sdsu.edu.cms.authservice.service.UserService;
import com.sdsu.edu.cms.common.models.response.ServiceResponse;
import com.sdsu.edu.cms.common.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1")
public class UserServiceController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/users", produces = {APPLICATION_JSON_VALUE}, consumes = {APPLICATION_JSON_VALUE})
    public ServiceResponse getUsers(Map<String, Object> payLoad){
        if(!payLoad.isEmpty()){
            String id = payLoad.get("id").toString();
        }

        return null;
    }


    @GetMapping("/users/{id}")
    public ServiceResponse getUserInfoById(@PathVariable String id){
        if(id.isEmpty() || id == null) throw new ApiErrorException("User id cant be empty.");
        return userService.getUserById(id);

    }


    @PutMapping(value = "/users/{id}" , produces = {APPLICATION_JSON_VALUE}, consumes = {APPLICATION_JSON_VALUE})
    public ServiceResponse updateUserInfo(@RequestBody User user, @PathVariable String id){
        if(user == null) throw new ApiErrorException("Request body cannot be empty");
        return userService.updateUserInfoById(user, id);

    }

    @PatchMapping("/users/{id}")
    public ServiceResponse updateUserInfoField(@RequestBody Map<String, Object> payLoad, @PathVariable String id){

        return null;
    }

    @GetMapping("/users/{id}/roles")
    public ServiceResponse getUserRolesById(@PathVariable String id){

        return userService.getUserRoles(id);

    }

    @GetMapping("/users/{id}/conferences")
    public ServiceResponse getUserConferences(@PathVariable String id){

        return userService.getUserConferences(id);

    }

    @PostMapping("/users/email")
    public ServiceResponse getUserByEmail(@RequestBody Map<String, String> params){
        return  userService.getUserByEmail(params);
    }

    @PostMapping("/users/roles/{cid}/{uid}/{rid}")
    public ServiceResponse addUserRoles(@PathVariable String cid, @PathVariable String uid, @PathVariable String rid){
        Map<String, String> params = new HashMap<>();
        params.put("cid", cid);
        params.put("uid", uid);
        params.put("rid", rid);
        return userService.addRoles(params);
    }




}
