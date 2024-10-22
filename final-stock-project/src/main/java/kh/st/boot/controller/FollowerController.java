package kh.st.boot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/follower")
public class FollowerController {

    ///api/follower/add

    @PostMapping("/add")
    public Map<String, Object> addFoller(){
        Map<String, Object> result = new HashMap<String, Object>();
        



        return result;
    }


}
