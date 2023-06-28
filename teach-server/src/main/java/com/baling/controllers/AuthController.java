package com.baling.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.baling.models.user.*;
import com.baling.payload.request.DataRequest;
import com.baling.payload.response.DataResponse;
import com.baling.repository.user.MemberRepository;
import com.baling.repository.user.AdminRepository;
import com.baling.repository.user.UserTypeRepository;
import com.baling.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baling.payload.request.LoginRequest;
import com.baling.payload.response.JwtResponse;
import com.baling.repository.user.UserRepository;
import com.baling.security.jwt.JwtUtils;
import com.baling.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        User user=userRepository.findByUsername(userDetails.getUsername()).get();
        user.setLastLoginTime(new Date());
        user.setLoginCount(user.getLoginCount()+1);
        userRepository.save(user);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles.get(0)));
    }

    @PostMapping("/signup")
    public DataResponse registerUser(@Valid @RequestBody DataRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getString("username"))) {
            return /*ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"))*/
                    CommonMethod.getReturnMessageError("该用户名已被占用！");
        }

        // Create new user's account
        User user = new User(signUpRequest.getString("username"),
                encoder.encode(signUpRequest.getString("password")));

        String strRoles = signUpRequest.getString("role");


        if (strRoles == null) {
            UserType userRole = userTypeRepository.findByName(EUserType.ROLE_MEMBER);
            user.setUserType(userRole);
        } else {
            UserType userRole = userTypeRepository.findByName(EUserType.valueOf(strRoles));
            user.setUserType(userRole);
        }
        switch (user.getUserType().getName()){
            case ROLE_MEMBER:
                if(memberRepository.existsByMid(signUpRequest.getString("mid")))return CommonMethod.getReturnMessageError("该成员编号已被注册！");
                Member member =new Member();
                member.setName(signUpRequest.getString("name"));
                member.setMid(signUpRequest.getString("mid"));
                memberRepository.save(member);
                user.setMember(member);
                break;
            case ROLE_ADMIN:
                if(adminRepository.existsByAid(signUpRequest.getString("aid")))return CommonMethod.getReturnMessageError("该管理员号已被注册！");
                if(!signUpRequest.getString("check").equals("202022300310"))return CommonMethod.getReturnMessageError("校验密码错误！");
                Admin admin =new Admin();
                admin.setName(signUpRequest.getString("name"));
                admin.setAid(signUpRequest.getString("aid"));
                adminRepository.save(admin);
                user.setAdmin(admin);
                break;
        }


        userRepository.save(user);

        return CommonMethod.getReturnMessageOK();
    }


}
