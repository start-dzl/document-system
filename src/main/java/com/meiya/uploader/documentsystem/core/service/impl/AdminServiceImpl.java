package com.meiya.uploader.documentsystem.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meiya.uploader.documentsystem.core.entity.Admin;
import com.meiya.uploader.documentsystem.core.mapper.AdminMapper;
import com.meiya.uploader.documentsystem.core.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {


    @Override
    public Optional<Admin> findByUsername(String username) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, username);
        Admin admin = this.baseMapper.selectOne(queryWrapper);
        return Optional.ofNullable(admin);
    }

    @Override
    public List<Admin> findByAll() {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        return this.baseMapper.selectList(queryWrapper);
    }


}