package com.meiya.uploader.documentsystem.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meiya.uploader.documentsystem.core.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService extends IService<File> {
    void upload(String name,
                String md5,
                MultipartFile file) throws IOException;

    void uploadWithBlock(String name,
                         String md5,
                         Long size,
                         Integer chunks,
                         Integer chunk,
                         MultipartFile file) throws IOException;

    boolean checkMd5(String md5);

    File findMd5(String md5);
}
