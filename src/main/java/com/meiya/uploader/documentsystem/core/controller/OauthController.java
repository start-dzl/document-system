package com.meiya.uploader.documentsystem.core.controller;


import com.meiya.uploader.documentsystem.core.entity.File;
import com.meiya.uploader.documentsystem.core.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Slf4j
@Validated
@Controller
public class OauthController {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "oauth")
    @ResponseBody
    public void oauth(HttpServletResponse response, String md5) throws UnsupportedEncodingException {
        log.info("======================================");
        File file = fileService.findMd5(md5);
        response.setHeader("Content-Disposition", "inline; filename=\"" + new String(file.getName().getBytes("GBK"), "iso-8859-1") + "\"");
        response.setHeader("X-Accel-Redirect", "/file_server/" + file.getPath());
        response.setHeader("Content-Type", "video/mp4");
    }

}
