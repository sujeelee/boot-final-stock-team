package kh.st.boot.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/test")
public class TestController {
	
	private String encodeKey = "tBdrMo4/l4MMdJ3rKe4B4eUHI4TBBzdn8HiGalt8fF7Qx4DIh4qpbGtmlQ8TAdbvlePzRRbwfRhKf38N4eOTRw==";
	private String apiUrl = "http://apis.data.go.kr/1160100/service/"
			+ "GetStockSecuritiesInfoService/getStockPriceInfo?serviceKey="
			+ encodeKey
			+ "&numOfRows=5&pageNo=1&resultType=json";
	
	@GetMapping("")
	public String home(Model model) {
		//https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo?serviceKey=인증키&numOfRows=1&pageNo=1
		return "test/test";
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/rest")
	public String RestAPI(Model model) {
		System.out.println(apiUrl);
		StringBuilder result = new StringBuilder();
	    try {
	        // URL 객체 사용
	        URL url = new URL(apiUrl); // apiUrl은 private 멤버 변수로 선언된 상태
	        
	        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	        urlConnection.setRequestMethod("GET");

	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

	        String returnLine;
	        while ((returnLine = bufferedReader.readLine()) != null) {
	            result.append(returnLine).append("\n");
	        }
	        urlConnection.disconnect();

	        // JSON 문자열을 Java 객체로 변환
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(result.toString());
	        
	        // "response" -> "body" -> "items" -> "item" 배열 추출
	        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

	        // JsonNode를 List로 변환
	        List<Map<String, Object>> jsonArray = objectMapper.convertValue(itemsNode, new TypeReference<List<Map<String, Object>>>(){});
	        // jsonArray 값을 확인하기 위한 for문
	        for (Map<String, Object> item : jsonArray) {
	            System.out.println("Item:");
	            for (Map.Entry<String, Object> entry : item.entrySet()) {
	                System.out.println(entry.getKey() + " : " + entry.getValue());
	            }
	            System.out.println("-------------------------");
	        }

	        // 가공된 데이터를 모델에 추가
	        model.addAttribute("result", jsonArray);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return "test/rest"; // 뷰 이름 반환
	}
}
