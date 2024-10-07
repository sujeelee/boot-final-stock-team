function showDash(icon) {
    if (!$(icon).hasClass("active")) {
        // 열릴 때 처리
        $(".test").css("display", "block"); // 먼저 display를 block으로 변경
        setTimeout(function() {
            $(".test").css("width", "200px"); // 너비를 200px로 변경 (애니메이션 적용)
        }, 10); // 작은 지연 시간을 두어 자연스러운 애니메이션을 보장

        $(".side_icon").removeClass("active");
        $(icon).addClass("active");
    } else {
        // 닫힐 때 처리
        $(".test").css("width", "0px"); // 너비를 0으로 설정하여 닫히는 애니메이션
        $(".side_icon").removeClass("active");
        $(icon).removeClass("active");

        // 애니메이션이 끝난 후 요소를 숨김
        setTimeout(function() {
            $(".test").css("display", "none"); // 애니메이션 완료 후 숨기기
        }, 250); // 애니메이션 시간과 일치시키기
    }
}
