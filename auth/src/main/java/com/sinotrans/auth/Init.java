package com.sinotrans.auth;

import com.google.common.collect.Sets;
import com.sinotrans.auth.domain.Authority;
import com.sinotrans.auth.domain.Role;
import com.sinotrans.auth.domain.User;
import com.sinotrans.auth.repository.AuthorityRepository;
import com.sinotrans.auth.repository.RoleRepository;
import com.sinotrans.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by SuperS on 2017/9/25.
 *
 * @author SuperS
 */
@Component
@AllArgsConstructor
public class Init implements CommandLineRunner {
    private UserService userService;
    private AuthorityRepository authorityRepository;
    private RoleRepository roleRepository;

    @Override
    public void run(String... strings) throws Exception {

        //权限
        Authority authority = new Authority();
        authority.setName("查询");
        authority.setValue("query");
        authorityRepository.save(authority);

        //角色
        Role admin = new Role();
        admin.setName("管理员");
        admin.setValue("ROLE_ADMIN");
        Optional<Authority> byId = authorityRepository.findById(1L);
        if (byId.isPresent()) {
            admin.setAuthorities(Sets.newHashSet(byId.get()));
        }
        roleRepository.save(admin);

        Role role = new Role();
        role.setName("普通用户");
        role.setValue("ROLE_USER");
        roleRepository.save(role);


        //用户
        User fpf = new User();
        fpf.setUsername("fpf");
        fpf.setPassword("fpf");
        fpf.setRoles(Sets.newHashSet(roleRepository.findById(2L).get()));
        userService.createUser(fpf);

        User wl = new User();
        wl.setUsername("wl");
        wl.setPassword("wl");
        wl.setRoles(Sets.newHashSet(roleRepository.findById(3L).get()));
        userService.createUser(wl);


    }
}
