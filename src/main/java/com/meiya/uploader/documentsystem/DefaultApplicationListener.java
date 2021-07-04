package com.meiya.uploader.documentsystem;

import com.meiya.uploader.documentsystem.core.config.property.AdminProperties;
import com.meiya.uploader.documentsystem.core.entity.Admin;
import com.meiya.uploader.documentsystem.core.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AdminProperties pro;
    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String username = pro.getUsername();
        Optional<Admin> optional = adminService.findByUsername(username);
        if (!optional.isPresent()) {
            Admin admin = Admin.builder()
                    .username(username)
                    .password(passwordEncoder.encode(pro.getPassword()))
                    .build();
            adminService.save(admin);
            logger.info("Create Default Admin: " + username);
            logger.info("Default Password: " + pro.getPassword());
        } else {
            Admin admin = optional.get();
            admin.setPassword(passwordEncoder.encode(pro.getPassword()));
            adminService.updateById(admin);
            logger.info("Updata Default Admin: " + username);
            logger.info("Default Password: " + pro.getPassword());
        }
    }
}
