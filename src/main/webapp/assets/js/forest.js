$("#forest").scroll(function() {
  var windowScroll = $("#forest").scrollTop();
  alert($("#forest").scrollTop() + " px");
  $(".forest").children(".layer").each(function() {
    var layer = $(this);
    var yPos = -(windowScroll * layer.data('speed') / 100);
			layer.css({
				"transform" : "translate3d(0px, " + yPos + "px, 0px)"
			});
    console.info("THIS LAYER", layer.data('speed'));
  });
  
});