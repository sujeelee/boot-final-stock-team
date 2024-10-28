package kh.st.boot.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import kh.st.boot.model.vo.AdminVO;
import kh.st.boot.service.ConfigService;

@ControllerAdvice
public class GlobalControllerAdvice {
	
    @Autowired
    private ConfigService configService;

    @ModelAttribute("config")
    public AdminVO globalConfig() {
        // configService로 config 테이블 정보를 가져와서 반환
        return configService.getConfig();
    }
}
