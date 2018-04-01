package com.sdsu.edu.cms.authservice.proxy;




import com.sdsu.edu.cms.common.models.response.DataServiceResponse;
import com.sdsu.edu.cms.common.models.user.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "data-service")
@RibbonClient(name = "data-service")
public interface DataServiceProxy {

    @PostMapping(value ="/api/v1/auth/query", consumes = {APPLICATION_JSON_VALUE})
    @ResponseBody
    DataServiceResponse queryUserName(@RequestBody String email);


    @PostMapping(value ="/api/v1/auth/save", consumes = {APPLICATION_JSON_VALUE})
    @ResponseBody
    DataServiceResponse saveUser(@RequestBody User user);
}
