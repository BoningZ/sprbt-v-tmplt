package com.baling.models.sys_menu;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sys_menu")
public class SysMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private SysMenu sysMenu;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 60)
    private String url;

    @NotNull
    private int depth;

    @Size(max = 60)
    private String icon;

    private int orderInSiblings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SysMenu getSysMenu() {
        return sysMenu;
    }

    public void setSysMenu(SysMenu sysMenu) {
        this.sysMenu = sysMenu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getOrderInSiblings() {
        return orderInSiblings;
    }

    public void setOrderInSiblings(int orderInSiblings) {
        this.orderInSiblings = orderInSiblings;
    }
}
