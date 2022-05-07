package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.EUserType;
import org.fatmansoft.teach.models.Student;
import org.fatmansoft.teach.models.Teacher;
import org.fatmansoft.teach.models.User;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.StudentRepository;
import org.fatmansoft.teach.repository.TeacherRepository;
import org.fatmansoft.teach.repository.UserRepository;
import org.fatmansoft.teach.util.CommonMethod;
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
    TeacherRepository teacherRepository;
    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/getProfile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse getProfile(@Valid @RequestBody DataRequest dataRequest){
        Integer userId= CommonMethod.getUserId();
        User user;
        Optional<User> tmp = userRepository.findByUserId(userId);
        user = tmp.get();
        Map m=new HashMap();
        if(user.getUserType().getName()== EUserType.ROLE_ADMIN){
            Teacher teacher=user.getTeacher();
            m.put("tid",teacher.getTid());
            m.put("name",teacher.getName());
        }else{
            Student student=user.getStudent();
            m.put("sid",student.getSid());
            m.put("name",student.getName());
        }
        return CommonMethod.getReturnData(m);
    }

    @PostMapping("/submitProfile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse submitProfile(@Valid @RequestBody DataRequest dataRequest){
        Integer userId= CommonMethod.getUserId();
        User user;
        Optional<User> tmp = userRepository.findByUserId(userId);
        user = tmp.get();
        if(user.getUserType().getName()== EUserType.ROLE_ADMIN){
            Teacher teacher=user.getTeacher();
            teacher.setTid(dataRequest.getString("tid"));
            teacher.setName(dataRequest.getString("name"));
            teacherRepository.save(teacher);
        }else{
            Student student=user.getStudent();
            student.setSid(dataRequest.getString("sid"));
            student.setName(dataRequest.getString("name"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birth=null;
            Calendar cal=Calendar.getInstance();
            try {
                birth=format.parse(dataRequest.getString("birthday"));
                cal.setTime(birth);
                cal.add(Calendar.DATE,1);
                birth=cal.getTime();
            } catch (ParseException e) {e.printStackTrace();}
            studentRepository.save(student);
        }
        return CommonMethod.getReturnMessageOK();
    }

}

