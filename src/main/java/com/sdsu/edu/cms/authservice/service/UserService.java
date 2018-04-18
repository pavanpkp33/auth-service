package com.sdsu.edu.cms.authservice.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdsu.edu.cms.authservice.exception.UserNotFoundException;
import com.sdsu.edu.cms.authservice.proxy.DataServiceProxy;
import com.sdsu.edu.cms.common.models.cms.Conference;
import com.sdsu.edu.cms.common.models.response.ServiceResponse;
import com.sdsu.edu.cms.common.models.user.User;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;


@Service
public class UserService {

    @Autowired
    DataServiceProxy dataServiceProxy;


    public ServiceResponse getUserById(String id){
        Map<String, String> mp = new HashMap<>();
        mp.put("id", id);
        try {
            return dataServiceProxy.findUserById(mp);
        }catch (FeignException e){
            if(e.status() == HttpStatus.NOT_FOUND.value()){
                throw new UserNotFoundException("No valid user associated with given ID.");
            }
            throw e;
        }

    }

    public ServiceResponse updateUserInfoById(User user, String id){
        Map<String, String> mp = new HashMap<>();
        mp.put("id",id);
        try{
            return dataServiceProxy.updateUser(user, mp);
        }catch (FeignException e){
            if(e.status() == HttpStatus.NOT_FOUND.value()){
                throw new UserNotFoundException("No valid user associated with given ID.");
            }
            throw e;
        }
    }

    public ServiceResponse getUserRoles(String userId){
        Map<String, String> mp = new HashMap<>();
        mp.put("id", userId);
        ServiceResponse response = dataServiceProxy.findRoles(mp);
        Object res = response.getData().get(0);
        return new ServiceResponse(Arrays.asList(res), "User roles queried.");

    }

    public ServiceResponse getUserConferences(String id) {
        Map<String, String> mp = new HashMap<>();
        mp.put("id", id);

        ServiceResponse response = dataServiceProxy.findConferences(mp);
        String res = (String)response.getData().get(0);
        if(res.equals("-1")) return new ServiceResponse(Arrays.asList(), response.getMessage());
        Type type = new TypeToken<List<Conference>>() {}.getType();
        Gson gson = new Gson();
        List<Conference> result = gson.fromJson(res, type);
        List<Object> finalObj = new ArrayList<>();
        for(Conference s : result){
            finalObj.add(s);
        }
        return new ServiceResponse(finalObj, response.getMessage());
    }
}
