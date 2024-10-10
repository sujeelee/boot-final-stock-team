package kh.st.boot.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import kh.st.boot.model.vo.FileVO;
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

    @GetMapping("/eventUpdate/{eventStatus}/{ev_no}")
    public String eventPostUpdate_Get(Model mo, @PathVariable("ev_no") int ev_no,
            @PathVariable("eventStatus") String eventStatus) {
        EventVO event = eventService.getEvent(eventStatus, ev_no);
        FileVO file = eventService.getFile(ev_no);

        mo.addAttribute("event", event);
        mo.addAttribute("file", file);
        return "/event/eventUpdate";
    }

    @PostMapping("/eventUpdate")
    public String eventPostUpdate_Post(EventVO event, MultipartFile file){
        
        System.out.println(event);
        System.out.println(file);
        boolean res = eventService.updateEvent_withFile(event, file);
        
        return "redirect:/event/eventhome/Opening";
    }


    @PostMapping("/ajax/updateEventDateAndStatus")
    public @ResponseBody boolean updateEventDateAndStatus() {
        boolean res = eventService.updateEventDateAndStatus();
        return res;
    }

    @PostMapping("/ajax/deleteEventPost")
    public @ResponseBody boolean deleteEventPost(@RequestParam("ev_no") int ev_no) {
        boolean res = eventService.deleteEventPost(ev_no);
        return res;
    }






    // 이벤트 페이지로 이동 및 구현
    @GetMapping("/calendar_event")
    public String calendar_event(Model mo){
        // 31칸짜리 배열 생성 (0: 출석 안 함, 1: 출석 완료)
        int[] checkList = new int[31];
        mo.addAttribute("checkList", Arrays.toString(checkList));
        return "/eventSeason2024/event202410Cevent";
    }
   
    @PostMapping("/ajax/calendar_event")
    public @ResponseBody boolean calendar_event_ajax(@RequestParam("mb_id") String mb_id, @RequestParam("checkList")int[] checkList){
        boolean res = eventService.CalenderEvent(mb_id, checkList);
        System.out.println(mb_id);
        System.out.println(Arrays.toString(checkList));
        return true;
    }

}
