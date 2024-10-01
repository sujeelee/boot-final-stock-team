package kh.st.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/event")
public class EventController {


    //이벤트 관련정보는 get 방식으로 전송한 후
    //사진 등등 큰 정보는 ajax로 받아볼까 생각중 입니다.

    @GetMapping("/eventhome")
    public String eventHome(Model mo){

        return "/event/eventhome";
    }
    
    //이벤트에는 현진행중/끝난이벤트/당첨자가 있음
    //ajax로 받아올 예정이라 생각됨
    
}
