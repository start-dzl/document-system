package com.meiya.uploader.documentsystem.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meiya.uploader.documentsystem.core.config.UploadConfig;
import com.meiya.uploader.documentsystem.core.entity.File;
import com.meiya.uploader.documentsystem.core.mapper.FileMapper;
import com.meiya.uploader.documentsystem.core.service.FileService;
import com.meiya.uploader.documentsystem.core.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

import static com.meiya.uploader.documentsystem.core.utils.FileUtils.generateFileName;
import static com.meiya.uploader.documentsystem.core.utils.UploadUtils.*;

/**
 * 文件上传服务
 */
@Slf4j
@Service
@Transactional
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {


    /**
     * 上传文件
     *
     * @param md5
     * @param file
     */
    @Override
    public void upload(String name,
                       String md5,
                       MultipartFile file) throws IOException {
        String end = name.substring(name.lastIndexOf('.'));
        String fileName = generateFileName();
        String path = UploadConfig.path + fileName + end;
        FileUtils.write(path, file.getInputStream());
        save(new File(name, md5, fileName + end, new Date()));
    }

    /**
     * 分块上传文件
     *
     * @param md5
     * @param size
     * @param chunks
     * @param chunk
     * @param file
     * @throws IOException
     */
    @Override
    public void uploadWithBlock(String name,
                                String md5,
                                Long size,
                                Integer chunks,
                                Integer chunk,
                                MultipartFile file) throws IOException {
        String fileName = getFileName(md5, chunks);
        String end = name.substring(name.lastIndexOf('.'));
        FileUtils.writeWithBlok(UploadConfig.path + fileName + end, size, file.getInputStream(), file.getSize(), chunks, chunk);
        addChunk(md5, chunk);
        if (isUploaded(md5)) {
            removeKey(md5);
            String path = fileName + end;
            save(new File(name, md5, path, new Date()));
        }
    }

    /**
     * 检查Md5判断文件是否已上传
     *
     * @param md5
     * @return
     */
    @Override
    public boolean checkMd5(String md5) {
        File file = new File();
        file.setMd5(md5);
        return this.baseMapper.getByFile(file) == null;
    }

    @Override
    public File findMd5(String md5) {
        File file = new File();
        file.setMd5(md5);
        return this.baseMapper.getByFile(file);
    }

}
