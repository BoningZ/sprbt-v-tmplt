package com.baling.controllers;

import com.baling.models.user.EUserType;
import com.baling.models.user.Member;
import com.baling.models.user.Admin;
import com.baling.models.user.User;
import com.baling.payload.request.DataRequest;
import com.baling.payload.response.DataResponse;
import com.baling.repository.user.MemberRepository;
import com.baling.repository.user.AdminRepository;
import com.baling.repository.user.UserRepository;
import com.baling.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class ProfileController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    MemberRepository memberRepository;

    @PostMapping("/getProfile")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    public DataResponse getProfile(@Valid @RequestBody DataRequest dataRequest){
        Integer userId= CommonMethod.getUserId();
        User user;
        Optional<User> tmp = userRepository.findByUserId(userId);
        user = tmp.get();
        Map m=new HashMap();
        if(user.getUserType().getName()== EUserType.ROLE_ADMIN){
            Admin admin =user.getAdmin();
            m.put("aid", admin.getAid());
            m.put("name", admin.getName());
        }else{
            Member member =user.getMember();
            m.put("mid", member.getMid());
            m.put("name", member.getName());
        }
        return CommonMethod.getReturnData(m);
    }

    @PostMapping("/submitProfile")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    public DataResponse submitProfile(@Valid @RequestBody DataRequest dataRequest){
        Integer userId= CommonMethod.getUserId();
        User user;
        Optional<User> tmp = userRepository.findByUserId(userId);
        user = tmp.get();
        if(user.getUserType().getName()== EUserType.ROLE_ADMIN){
            Admin admin =user.getAdmin();
            admin.setAid(dataRequest.getString("aid"));
            admin.setName(dataRequest.getString("name"));
            adminRepository.save(admin);
        }else{
            Member member =user.getMember();
            member.setMid(dataRequest.getString("mid"));
            member.setName(dataRequest.getString("name"));
            memberRepository.save(member);
        }
        return CommonMethod.getReturnMessageOK();
    }

}

