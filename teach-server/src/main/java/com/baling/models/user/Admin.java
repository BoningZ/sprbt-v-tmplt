package com.baling.models.user;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "admin",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "aid"),
        })
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//自动编号


    private String aid;//工号


    @Size(max=50)
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer teacherId) {
        this.id = teacherId;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String tid) {
        this.aid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
