$(document).ready(function() {
  $("#btn").on("click", function() {
      $(".sidebar").toggleClass("open");
      menuBtnChange(); // calling the function(optional)
  });
  // following are the code to change sidebar button(optional)
  function menuBtnChange() {
      if ( $(".sidebar").hasClass("open")) {
      $(".sidebar").removeClass("bx-menu").addClass("bx-menu-alt-right"); // replacing the icons class
      } else {
      $(".sidebar").removeClass("bx-menu-alt-right").addClass("bx-menu"); // replacing the icons class
      }}
  });