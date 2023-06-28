package com.baling.controllers;

import com.baling.models.sys_menu.TypeMenu;
import com.baling.models.user.EUserType;
import com.baling.models.user.User;
import com.baling.payload.request.DataRequest;
import com.baling.payload.response.DataResponse;
import com.baling.repository.sys_menu.TypeMenuRepository;
import com.baling.repository.user.UserRepository;
import com.baling.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class TestController {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TypeMenuRepository typeMenuRepository;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    public String userAccess(HttpSession session) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return "User Content.";
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    public ResponseEntity<?> adminAccess(HttpSession session) {

        LocalDateTime ts = (LocalDateTime)session.getAttribute("ts");

        Map<String, String> res = new HashMap<String, String>();

        if (ts == null) {
            res.put("ts", "");
        } else {
            res.put("ts", ts.toString());
        }
        session.setAttribute("ts",  LocalDateTime.now());
        return ResponseEntity.ok(new DataResponse(
                "ok",
                res
        ));
    }
    @PostMapping("/getMenuList")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    public DataResponse getMenuList(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        User user;
        Optional<User> tmp = userRepository.findByUserId(userId);
        user = tmp.get();
        List mList = new ArrayList();
        Map m;
        List<TypeMenu> menus=typeMenuRepository.getTypeMenusByUserType(user.getUserType());
        for(TypeMenu tm:menus){
            m=new HashMap();
            m.put("name",tm.getSysMenu().getUrl());
            m.put("title",tm.getSysMenu().getName());
            mList.add(m);
        }
        return CommonMethod.getReturnData(mList);
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    public DataResponse changePassword(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        String oldPassword = dataRequest.getString("oldPassword");
        String newPassword = dataRequest.getString("newPassword");
        User u = userRepository.findById(userId).get();
        if(!encoder.matches(oldPassword, u.getPassword())) {
            return CommonMethod.getReturnMessageError("原密码错误！");
        }
        u.setPassword(encoder.encode(newPassword));
        userRepository.save(u);
        return CommonMethod.getReturnMessageOK();
    }

}
