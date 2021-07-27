package com.meiya.uploader.documentsystem.core.controller;


import com.meiya.uploader.documentsystem.core.config.property.GlobalProperties;
import com.meiya.uploader.documentsystem.core.entity.Admin;
import com.meiya.uploader.documentsystem.core.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private GlobalProperties globalProperties;

    @PostMapping("/test")
    @ApiOperation("名字信息")
    public List<String> sign() {
        return adminService.findByAll().stream().map(Admin::getUsername).collect(Collectors.toList());
    }

    @PostMapping("/properties")
    @ApiOperation("globalProperties")
    public String globalProperties() {
        return globalProperties.getRemark();
    }

}
