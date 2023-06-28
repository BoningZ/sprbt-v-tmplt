package com.baling.repository.sys_menu;

import com.baling.models.sys_menu.TypeMenu;
import com.baling.models.user.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeMenuRepository extends JpaRepository<TypeMenu, Integer> {
    List<TypeMenu> getTypeMenusByUserType(UserType userType);
}
