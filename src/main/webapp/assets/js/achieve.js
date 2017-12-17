function showAchievement() {
  $('.achex .circle').removeClass('rotate');
  // Run the animations
  setTimeout(function () {
    $('.achex').addClass('expand');
    setTimeout(function () {
      $('.achex').addClass('widen');
      setTimeout(function () {
        $('.achex .copy').addClass('show');
      }, 1000);
    }, 1000);
  }, 1000);
  // Hide the achievement
  setTimeout(function () {
    hideAchievement();
  }, 4000);
}


showAchievement();