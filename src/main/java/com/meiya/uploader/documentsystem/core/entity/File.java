package com.meiya.uploader.documentsystem.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * File表存储上传的文件信息
 */
@TableName("File")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File implements Serializable {

    private static final long serialVersionUID = -6956947981866795431L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String md5;
    private String path;
    private Date uploadTime;

    public File(String name, String md5, String path, Date uploadTime) {
        this.name = name;
        this.md5 = md5;
        this.path = path;
        this.uploadTime = uploadTime;
    }
}
