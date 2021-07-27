package com.meiya.uploader.documentsystem.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meiya.uploader.documentsystem.core.entity.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService extends IService<Admin> {
    Optional<Admin> findByUsername(String username);

    List<Admin> findByAll();
}
