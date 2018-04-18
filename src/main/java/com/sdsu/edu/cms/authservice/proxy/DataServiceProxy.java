package com.sdsu.edu.cms.authservice.proxy;




import com.sdsu.edu.cms.common.models.response.ServiceResponse;
import com.sdsu.edu.cms.common.models.user.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "data-service")
@RibbonClient(name = "data-service")
public interface DataServiceProxy {

    @PostMapping(value ="/api/v1/auth/query", consumes = {APPLICATION_JSON_VALUE})
    @ResponseBody
    ServiceResponse queryUserName(@RequestBody String email);


    @PostMapping(value ="/api/v1/auth/save", consumes = {APPLICATION_JSON_VALUE})
    @ResponseBody
    ServiceResponse saveUser(@RequestBody User user);

    @PostMapping(value = "/api/v1/auth/activate", consumes = {APPLICATION_JSON_VALUE})
    @ResponseBody
    ServiceResponse activateUser(@RequestParam Map<String, String> map);

    @PostMapping("/api/v1/users")
    @ResponseBody
    ServiceResponse findUserById(@RequestParam Map<String,String> map);

    @PostMapping("/api/v1/users/update")
    @ResponseBody
    ServiceResponse updateUser(@RequestBody User user, @RequestParam Map<String, String> map);

    @PostMapping("/api/v1/users/roles")
    ServiceResponse findRoles(@RequestParam Map<String, String > params);

}
