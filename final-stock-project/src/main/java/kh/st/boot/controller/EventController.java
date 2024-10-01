package kh.st.boot.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import kh.st.boot.model.dto.EventDTO;
import kh.st.boot.model.vo.EventVO;
import kh.st.boot.service.EventService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/event")
public class EventController {


    private EventService eventService;

    @GetMapping("/eventhome/{eventStatus}")
    public String eventHome(Model mo, @PathVariable("eventStatus") String eventStatus){
        List<EventDTO> list = eventService.getEventList(eventStatus);
        System.out.println("test");
        mo.addAttribute("list",list);
        return "/event/eventhome";
    }
    
}
