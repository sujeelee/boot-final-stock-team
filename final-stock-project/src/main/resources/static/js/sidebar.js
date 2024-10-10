function showDash(icon) {
    if (!$(icon).hasClass("active")) {
        // 사이드바 열기
        $(".test").css("display", "block");
        
        // 패딩 값 늘리기
        $(".navbar").css("padding", "15px 210px"); // 패딩을 15px 40px로 변경
        
        setTimeout(function() {
            $(".test").css("width", "200px"); // 사이드바 너비 설정
            $(".home-section").css("left", "-170px"); // home 요소를 오른쪽으로 밀기
        }, 10); // 자연스러운 애니메이션을 위한 짧은 지연

        $(".side_icon").removeClass("active");
        $(icon).addClass("active");
    } else {
        // 사이드바 닫기
        $(".test").css("width", "0px"); // 사이드바를 닫기
        
        // 애니메이션 완료 후 home 요소 복원
        setTimeout(function() {
            $(".home-section").css("left", "0px"); // home 요소 원래 위치로 복원
            $(".test").css("display", "none"); // 애니메이션 완료 후 사이드바 숨김
            
            // 원래 패딩 값으로 복원
            $(".navbar").css("padding", "15px 20px"); // 패딩을 원래대로 복원
        }, 250); // 사이드바 애니메이션과 일치시키기 위한 지연

        $(".side_icon").removeClass("active");
        $(icon).removeClass("active");
    }
}
