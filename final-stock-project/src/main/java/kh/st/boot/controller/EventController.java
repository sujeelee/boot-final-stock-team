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


    //eventStatus : Opening, Ending, resUser
    @GetMapping("/eventhome/{eventStatus}")
    public String eventHome(Model mo, @PathVariable("eventStatus") String eventStatus){
        List<EventDTO> list = eventService.getEventList(eventStatus);
        mo.addAttribute("list",list);
        return "/event/eventhome";
    }
    

    @GetMapping("/eventhome/{eventStatus}/{ev_no}")
    public String eventShow(@PathVariable("eventStatus") String eventStatus, @PathVariable("ev_no") int ev_no){
        // EventVO event = eventService.getEvent();

        return "/event/eventShow";
    }


}
