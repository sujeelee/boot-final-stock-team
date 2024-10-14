package kh.st.boot.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.ui.Model;
import kh.st.boot.model.dto.EventDTO;
import kh.st.boot.model.dto.EventPrizeCounterDTO;
import kh.st.boot.model.vo.EventPrizeVO;
import kh.st.boot.model.vo.EventVO;
import kh.st.boot.model.vo.FileVO;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.model.vo.PrizeVO;
import kh.st.boot.service.EventService;
import kh.st.boot.service.MemberService;
import kh.st.boot.service.PointService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    private PointService pointService;

    private MemberService memberService;

    // eventStatus : Opening, Ending, resUser, Hidden
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
    public String eventPostUpdate_Post(EventVO event, MultipartFile file) {
        boolean res = eventService.updateEvent_withFile(event, file);
        if (res) {
            System.out.println("이벤트 업데이트 성공");
        }
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

    @GetMapping("/eventATypeWrite")
    public String eventATypeWrite(Model mo) {
        List<EventVO> eventList = eventService.getEventListByEventForm("Participatory");
        mo.addAttribute("eventList", eventList);
        return "/event/eventATypeWrite";
    }

    @PostMapping("/eventATypeWrite")
    public String eventATypeWrite_post(PrizeVO prize, MultipartFile file) {

        boolean res = eventService.setPrizeToBeUsedFromTheEvent(prize, file);
        if (res) {
            return "redirect:/event/eventATypeWrite";
        } else {
            return "redirect:/event/eventhome/Opening";
        }
    }

    // 이벤트 페이지로 이동 및 구현
    // 출석체크 이벤트 (C event)
    @GetMapping("/calendar_event")
    public String calendar_event(Model mo, Principal principal) {
        // 31칸짜리 배열 생성 (0: 출석 안 함, 1: 출석 완료)
        String storedValue = eventService.getCalenderEventValue(principal.getName());
        String[] parts = storedValue.split(",");
        int[] checkList = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
        mo.addAttribute("checkList", Arrays.toString(checkList));
        return "/eventSeason2024/event202410Cevent";
    }

    @PostMapping("/ajax/calendar_event")
    public @ResponseBody boolean calendar_event_ajax(@RequestParam("mb_id") String mb_id,
            @RequestParam("checkList") int[] checkList) {
        boolean res = eventService.CalenderEvent(mb_id, checkList);
        return res;
    }

    // 참여형 이벤트 (A event)
    @GetMapping("/Aevent/{ev_no}")
    public String Aevent(Model mo, @PathVariable("ev_no") int ev_no, Principal principal) {
        List<PrizeVO> list = eventService.getPrizeListByEv_no(ev_no);
        List<EventPrizeVO> eplist = eventService.getEventPrizeTicketList(ev_no);
        List<EventPrizeCounterDTO> epcDtoList = eventService.getEventPrizeTicketCounter(ev_no);
        mo.addAttribute("epcDtoList", epcDtoList);
        mo.addAttribute("eplist", eplist);
        mo.addAttribute("list", list);
        return "/eventSeason2024/event202410Aevent";
    }

    // 이벤트 참여시 작동
    @PostMapping("/ajax/participate")
    public String ajax_participate_Aevent_post(Model mo, @RequestBody Map<String, Object> PrizeTicket) {
        EventPrizeVO ep = new EventPrizeVO();
        ep.setEv_no((Integer) PrizeTicket.get("ev_no"));
        ep.setPr_no((Integer) PrizeTicket.get("pr_no"));
        ep.setEp_mb_id((String) PrizeTicket.get("mb_id"));
        ep.setEp_prize((String) PrizeTicket.get("pr_link"));

        boolean res = false;

        MemberVO user = memberService.findById(ep.getEp_mb_id());
        // 먼저 포인트가 있는지 확인해야해요
        if (user.getMb_point() >= (Integer) PrizeTicket.get("pr_point")) {
            // 확인한 후 이벤트 프라이즈를 해서 히스토리를 등록해요
            res = eventService.setEventPrizeTicket(ep); // 처음이면 생성, 있으면 + 1해주면 되용
            // usePoint ("누가", "얼마나", "왜")
            res = pointService.usePoint((String) PrizeTicket.get("mb_id"), (Integer) PrizeTicket.get("pr_point"),
                    "spend event");

            mo.addAttribute("res", res);
        } else {
            // 포인트가 없음
            mo.addAttribute("res", res);
        }


        List<EventPrizeVO> eplist = eventService.getEventPrizeTicketList(ep.getEv_no());
        mo.addAttribute("eplist", eplist);

        // sum
        List<EventPrizeCounterDTO> epcDtoList = eventService.getEventPrizeTicketCounter((int) ep.getEv_no());
        mo.addAttribute("epcDtoList", epcDtoList);

        List<PrizeVO> list = eventService.getPrizeListByEv_no((Integer) PrizeTicket.get("ev_no"));
        mo.addAttribute("list", list);
        return "/eventSeason2024/event202410Aevent :: #prize";
    }

    @GetMapping("/prizeList")
    public String prizeList(){
        

        return "/event/eventPrizeList";
    }

}
