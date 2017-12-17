$(function() {
  var $elements = $('.animateBlock.notAnimated'); //contains elements of nonAnimated class
  var $window = $(window);
  $window.on('scroll', function(e) {
    $elements.each(function(i, elem) { //loop through each element
      if ($(this).hasClass('animated')) // check if already animated
        return;
      animateMe($(this));
    });
  });
});
 
function animateMe(elem) {
  var winTop = $(window).scrollTop(); // calculate distance from top of window
  var winBottom = winTop + $(window).height();
  var elemTop = $(elem).offset().top; // element distance from top of page
  var elemBottom = elemTop + $(elem).height();
  if ((elemBottom <= winBottom) && (elemTop >= winTop)) {
    // exchange classes if element visible
    $(elem).removeClass('notAnimated').addClass('animated');
  }
}

// Propeller and Banner
 $( document ).ready(function() {
TweenMax.to('.plane-elements', 0.9, { x:2, y:6, rotation:0.5, yoyo:true, repeat:-1 });
TweenMax.to('.propeller', 0.05, { rotationX:'60deg', yoyo:true, repeat:-1 });
TweenMax.to('.banner', 0.7, { y:5, width:895, height: 170, yoyo:true, repeat:-1 });
TweenMax.to('.banner-tip', 0.65, { x:-10, y:5, height:135 , yoyo:true, repeat:-1 });

});
