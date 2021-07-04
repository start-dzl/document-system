package com.meiya.uploader.documentsystem.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meiya.uploader.documentsystem.core.entity.File;

public interface FileMapper extends BaseMapper<File> {
    /**
     * 通过主键获取一行数据
     *
     * @return
     */
    File getById(Long id);


    /**
     * 更新一行数据
     *
     * @param file
     * @return
     */
    int update(File file);

    /**
     * 删除一行数据
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据一个或多个属性获取File
     *
     * @param file
     * @return
     */
    File getByFile(File file);
}
