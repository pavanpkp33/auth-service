package com.sdsu.edu.cms.authservice.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class AuthService {


    public boolean authenticate(String userPwd, String dbPwd){
        if(userPwd.isEmpty() || dbPwd.isEmpty()) return false;
       return BCrypt.checkpw(userPwd, dbPwd);
    }

    public String hashPwd(String userPwd){
        if(userPwd.isEmpty()) return null;

        return BCrypt.hashpw(userPwd, BCrypt.gensalt());
    }


}
