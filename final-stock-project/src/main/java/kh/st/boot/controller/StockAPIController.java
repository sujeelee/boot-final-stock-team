package kh.st.boot.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kh.st.boot.model.vo.StockPriceVO;
import kh.st.boot.model.vo.StockVO;
import kh.st.boot.pagination.Criteria;
import kh.st.boot.pagination.PageMaker;
import kh.st.boot.service.StockService;


@Controller
@RequestMapping("/stock")
public class StockAPIController {
	@Autowired
	StockService stockService;
	private String encodeKey = "tBdrMo4/l4MMdJ3rKe4B4eUHI4TBBzdn8HiGalt8fF7Qx4DIh4qpbGtmlQ8TAdbvlePzRRbwfRhKf38N4eOTRw==";
	
	@GetMapping("")
	public String home(Model model) {
		//https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo?serviceKey=인증키&numOfRows=1&pageNo=1
		return "stock/stockList";
	}
	
	private List<Map<String, Object>> getUrlAPI(String apiUrl){
		StringBuilder result = new StringBuilder();
	    try {
	        // URL 객체 사용
	        @SuppressWarnings("deprecation")
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
	        return jsonArray;

	        // 가공된 데이터를 모델에 추가
	        //model.addAttribute("result", jsonArray);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	@GetMapping("/getListStock")
	public String RestAPI(Criteria cri, Model model, @RequestParam Map<String, String> params) {
		String apiUrl = "http://apis.data.go.kr/1160100/service/"
				+ "GetStocIssuInfoService_V2/getItemBasiInfo_V2?serviceKey="
				+ encodeKey
				+ "&numOfRows=100&resultType=json&pageNo=";
		// 페이지메이커 생성 (페이징 처리를 위한 객체)
		int count = 50;
		PageMaker pm = new PageMaker(10, cri, count);
		model.addAttribute("pm", pm); // 페이지 정보 추가
		
		apiUrl += cri.getPage();
		if(params.get("sfl") != null && params.get("stx") != null) {
			if(params.get("sfl").equals("cmpname")) {
				apiUrl += "&stckIssuCmpyNm=" + params.get("stx").toString();
			} else if(params.get("sfl").equals("crno")) {
				apiUrl += "&crno=" + params.get("stx").toString();
			}
		}
		model.addAttribute("url", apiUrl);
		model.addAttribute("param", params);
		
		List<Map<String, Object>> jsonArray = getUrlAPI(apiUrl);
		model.addAttribute("array", jsonArray);
		return "stock/getStock";
		// JSON 필드와 StockVO 필드 간의 매핑 정의
        /*Map<String, String> field = new HashMap<>();
        field.put("isinCd", "st_code");
        field.put("isinCdNm", "st_name");
        field.put("issuStckCnt", "st_qty");
        field.put("stckIssuCmpyNm", "st_issue");
        field.put("lstgAbolDt", "st_status");
        // jsonArray 값을 확인하기 위한 for문
        for (Map<String, Object> item : jsonArray) {
        	 StockVO stock = new StockVO(); // 새로운 StockVO 객체 생성
            for (Map.Entry<String, Object> entry : item.entrySet()) {
            	//System.out.println(entry.getKey() + " : " + entry.getValue());
            	String jsonKey = entry.getKey();
                Object value = entry.getValue();
                String voFieldName = field.get(jsonKey);
                if (voFieldName != null) {
                	Field fields;
					try {
						fields = StockVO.class.getDeclaredField(voFieldName);
						fields.setAccessible(true); // private 필드 접근
	                    fields.set(stock, value); // 필드에 값 설정
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
            stockService.insertStockCompany(stock);
        }*/
	}
	
	@GetMapping("/Stockprice")
	public String getStockPrice(Model model) {
		List<StockVO> list = stockService.getCompanyList();
		model.addAttribute("result", list);
		for(int i=0; i<list.size();i++) {
			String st_code = list.get(i).getSt_code();
			System.out.println(list.get(i).getSt_code());
			String apiUrl = "http://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo?serviceKey="
					+ encodeKey
					+ "&isinCd=" + st_code
					+ "&resultType=json";
			
			System.out.println(apiUrl);
			
			List<Map<String, Object>> jsonArray = getUrlAPI(apiUrl);
			
			Map<String, String> field = new HashMap<>();
			field.put("basDt", "si_date");
			field.put("clpr", "si_price");
			field.put("isinCd", "st_code");
			field.put("vs", "si_vs");
			field.put("fltRt", "si_fltRt");
			field.put("mrktTotAmt", "si_mrkTotAmt");
			field.put("hipr", "si_hipr");
			field.put("lopr", "si_lopr");
			field.put("trqu", "si_trqu");
			
			StockVO stock = stockService.getCompanyOne(st_code);
			
			 // jsonArray 값을 확인하기 위한 for문
	        for (Map<String, Object> item : jsonArray) {
	        	 StockPriceVO stockPrice = new StockPriceVO(); // 새로운 StockVO 객체 생성
	            for (Map.Entry<String, Object> entry : item.entrySet()) {
	            	String jsonKey = entry.getKey();
	                Object value = entry.getValue();
	                if(stock.getSt_type() == null && jsonKey.contains("mrktCtg")) {
	                	stockService.updateCompanyType(st_code, (String) value);
	                }
	                String voFieldName = field.get(jsonKey);
	                if (voFieldName != null) {
	                	Field fields;
						try {
							fields = StockPriceVO.class.getDeclaredField(voFieldName);
							fields.setAccessible(true); // private 필드 접근
							if(voFieldName == "si_price") {
								value = Integer.parseInt((String) value); 
							}
		                    fields.set(stockPrice, value); // 필드에 값 설정
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	            }
	            System.out.println(stockPrice);
	            stockService.insertPrice(stockPrice);
	        }
	            System.out.println("===========================-");
		}
		
		return "stock/Stockprice"; // 뷰 이름 반환
	}
}
