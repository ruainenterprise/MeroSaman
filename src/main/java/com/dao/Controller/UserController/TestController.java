package com.dao.Controller.UserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/testMe")
public class TestController {


 
    @GetMapping()
    public 	String getTreeById() {
        return "welcome.ftlh" ;
    }
  

}