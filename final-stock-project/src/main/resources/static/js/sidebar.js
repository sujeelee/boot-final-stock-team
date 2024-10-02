$(document).ready(function() {
  $("#btn").on("click", function() {
    $(".sidebar").toggleClass("open");
    menuBtnChange(); // calling the function (optional)
    adjustLoginButton(); // 로그인 버튼 위치 조정
  });

  // 로그인 버튼 위치 조정 함수
  function adjustLoginButton() {
    if ($(".sidebar").hasClass("open")) {
      $(".login").css("padding-right", "200px"); // 사이드바 열릴 때 패딩값 줄이기
    } else {
      $(".login").css("padding-right", "0px"); // 사이드바 닫힐 때 원래 패딩으로 돌아가기
    }
  }

  // following are the code to change sidebar button (optional)
  function menuBtnChange() {
    if ($(".sidebar").hasClass("open")) {
      $(".sidebar").removeClass("bx-menu").addClass("bx-menu-alt-right"); // replacing the icons class
    } else {
      $(".sidebar").removeClass("bx-menu-alt-right").addClass("bx-menu"); // replacing the icons class
    }
  }
});

