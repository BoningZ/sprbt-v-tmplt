package com.baling.models.user;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "member",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "mid"),
        })
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Size(max = 20)
    private String mid;
    @Size(max = 20)
    private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer studentId) {
        this.id = studentId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String sid) {
        this.mid = sid;
    }





}
