package com.meiya.uploader.documentsystem.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.util.Collection;


@TableName("t_admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin{

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;
}