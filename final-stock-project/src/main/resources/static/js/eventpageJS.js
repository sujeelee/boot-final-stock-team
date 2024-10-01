	$(document).ready(function() {
	    $(".tablinks").click(function() {
	        var tabName = $(this).attr("onclick").match(/'(.*?)'/)[1];

	        // 모든 탭 내용을 숨김
	        $(".tabcontent").hide();

	        // 모든 탭 링크의 활성화 상태 제거
	        $(".tablinks").removeClass("active");

	        // 클릭한 탭 내용 표시 및 탭 링크 활성화
	        $("#" + tabName).show();
	        $(this).addClass("active");
	    });

	    // 기본적으로 첫 번째 탭 열기
	    $(".tablinks").first().click();
	});
