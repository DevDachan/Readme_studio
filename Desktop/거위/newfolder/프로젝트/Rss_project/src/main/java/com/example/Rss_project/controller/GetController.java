package com.example.Rss_project.controller;





import com.example.Rss_project.dto.MemberDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/get-api")

// http://localhost:8080/api/v1/get-api

public class GetController {


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello(){
        return "Helclo Woeeerld";
    }

    @GetMapping(value="/name")
    public String getName(){
        return "12345";
    }

    @GetMapping(value="/variable1/{variable}")
    public String getVariable(@PathVariable("variable") String var){
        return var;
    }
    @GetMapping(value="/variable2/{variable}")
    public String getVariable2(@PathVariable  String variable){
        return variable;
    }

    @GetMapping(value="/hello1")
    public String getRequestParam1(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String organization
    ){
        return name + " " + email + " " + organization;
    }

    @GetMapping(value="hello2")
    public String getRequestParam2(@RequestParam Map<String, String> param){
        StringBuilder sb = new StringBuilder();
        param.entrySet().forEach(map -> {
            sb.append(map.getKey() +" : "+ map.getValue() + "\n");
        });
        return sb.toString();
    }

    @GetMapping(value="hello3")
    public String getRequestParam3(MemberDTO memberDTO){
        return memberDTO.toString();
    }

}