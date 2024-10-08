package kh.st.boot.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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

    // eventStatus : Opening, Ending, resUser
    @GetMapping("/eventhome/{eventStatus}") // principal
    public String eventHome(Model mo, @PathVariable("eventStatus") String eventStatus) {
        List<EventDTO> list = eventService.getEventList(eventStatus);
        mo.addAttribute("list", list);
        return "/event/eventpage";
    }

    @GetMapping("/eventhome/{eventStatus}/{ev_no}")
    public String eventShow(Model mo, @PathVariable("eventStatus") String eventStatus,
            @PathVariable("ev_no") int ev_no) {
        EventVO event = eventService.getEvent(eventStatus, ev_no);
        mo.addAttribute("event", event);
        return "/event/eventDetail";
    }

    @GetMapping("/write")
    public String eventWrite() {
        return "/event/eventWrite";
    }

    @PostMapping("/write")
    public String eventWrite_Post(EventVO event, MultipartFile file) {
        boolean res = eventService.setEvent(event, file);
        if (event == null && res) {
            return "redirect:/event/write";
        } else {
            return "redirect:/event/eventhome/Opening";
        }

    }

    @PostMapping("/ajax/updateEventDateAndStatus")
    public @ResponseBody boolean updateEventDateAndStatus() {
        boolean res = eventService.updateEventDateAndStatus();
        return res;
    }

    @PostMapping("/ajax/deleteEventPost")
    public @ResponseBody boolean deleteEventPost(@RequestParam("ev_no")int ev_no){
        boolean res = eventService.deleteEventPost(ev_no);
        return res;
    }
}
