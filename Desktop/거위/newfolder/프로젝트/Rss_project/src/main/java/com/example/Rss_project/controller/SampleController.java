
package com.example.Rss_project.controller;

import com.example.Rss_project.common.Constants;
import com.example.Rss_project.common.exception.DemoException;
import com.example.Rss_project.data.dto.TemplateDTO;
import com.example.Rss_project.data.entity.TemplateEntity;
import com.example.Rss_project.data.service.TemplateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Rss")
public class SampleController {
    private TemplateService templateService;


    @Autowired
    public SampleController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping(value = "/template/{templateNum}")
    public String getTemplate(@PathVariable String templateNum) {
        long startTime = System.currentTimeMillis();
        TemplateDTO templateDTO = templateService.getTemplate(templateNum);
        String temp=templateDTO.getTemplateName();
        return temp;
    }

    @RequestMapping(value = "/readme")
    public String sample(){
    String temp= "![header](https://capsule-render.vercel.app/api?type=Waving&color=auto&height=300&section=header&text=Readme%20Studio&fontSize=90)\n" +
            "<div align=center><h1>\uD83D\uDCDA  STACKS</h1></div>\n" +
            "<div align=center> \n" +
            "  <img src=\"https://img.shields.io/badge/Spring-007396?style=for-the-badge&logo=Spring&logoColor=white\">\n" +
            "  <br>\n" +
            "  \n" +
            "\u200B\n" +
            "</div>\n" +
            "\u200B\n" +
            "## :one: 소개\n" +
            "  이 프로젝트는 Readme Studio 서비스로  Spring Boot로 제작된 프로젝트입니다.\n" +
            "## :two: 패키지 프레임워크 설치\n" +
            "\u200B\n" +
            "<b>clone 프로젝트 </b>\n" +
            "```xml\n" +
            "gh repo clone https://soominkiminsoo/SurveyForm1.git\n" +
            "```\n" +
            " <b>jar 파일 다운로드</b>\n" +
            "* mysql-connector-j-8.0.31.jar\n" +
            "* mail-1.4.7.jar\n" +
            "* activation.jar\n" +
            "   \n" +
            "\u200B\n" +
            "## :three:DB 구조\n" +
            "C:.<br>\n" +
            "├─dao<br>\n" +
            "│    └─Impl<br>\n" +
            "├─dto<br>\n" +
            "├─entity<br>\n" +
            "├─handler<br>\n" +
            "│    └─Impl<br>\n" +
            "├─repository<br>\n" +
            "└─service<br>\n" +
            "       └─Impl<br>\n" +
            "\u200B\n" +
            "## :four:쿼리 메소드\n" +
            "\u200B\n" +
            "## :five:Contributor\n" +
            "\u200B\n" +
            "<td align=\"center\"><a href=\"https://kentcdodds.com\"><img src=\"https://github.com/geodo2.png\" width=\"100px;\" alt=\"minsoo kim\"/><br /><sub><b>geodo2</b></sub></a><br /><a href=\"#question-kentcdodds\" title=\"Answering Questions\">\uD83D\uDCAC</a> <a href=\"https://github.com/all-contributors/all-contributors/commits?author=geodo2\" title=\"Documentation\">\uD83D\uDCD6</a> <a href=\"https://github.com/all-contributors/all-contributors/pulls?q=is%3Apr+reviewed-by%3Akentcdodds\" title=\"Reviewed Pull Requests\">\uD83D\uDC40</a> <a href=\"#talk-kentcdodds\" title=\"Talks\">\uD83D\uDCE2</a></td>";
        return temp;
    }
}
