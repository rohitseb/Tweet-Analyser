


	function showAchievement(objID) {
  $('#'+objID+' .circle').removeClass('rotate');
  // Run the animations
  setTimeout(function () {
    $('#'+objID).addClass('expand');
    setTimeout(function () {
      $('#'+objID).addClass('widen');
      setTimeout(function () {
        $('#'+objID+' .copy').addClass('show');
      }, 1000);
    }, 1000);
  }, 1000);
  // Hide the achievement
  setTimeout(function () {
    //hideAchievement();
  }, 4000);
}

function hideAchievement() {
  setTimeout(function () {
    $('#achievement .copy').removeClass('show');
     setTimeout(function () {
      $('#achievement').removeClass('widen');
       $('#achievement .circle').addClass('rotate');
        setTimeout(function () {
          $('#achievement').removeClass('expand');
          $('.refresh').fadeIn(300);
        }, 1000);
     }, 1000);
  }, 3000);
  
  $('.refresh').click(function () {
    showAchievement();
    $(this).fadeOut(300);
  });
}

												$(document).ready(function() {
														$('#achievement').waypoint(function() {
														//alert("Hello! I am an alert box!!");
														showAchievement('achievement');
																
														},
														{ offset: 'bottom-in-view' });
													});
													
												
												$(document).ready(function() {
														$('#poster').waypoint(function() {
														//alert("Hello! I am an alert box!!");
														showAchievement('poster');
																
														},
														{ offset: 'bottom-in-view' });
													});
													
													
													$(document).ready(function() {
														$('#publicity').waypoint(function() {
														//alert("Hello! I am an alert box!!");
														showAchievement('publicity');
																
														},
														{ offset: 'bottom-in-view' });
													});
													
													
													$(document).ready(function() {
														$('#pace').waypoint(function() {
														//alert("Hello! I am an alert box!!");
														showAchievement('pace');
																
														},
														{ offset: 'bottom-in-view' });
													});
													
													
													$(document).ready(function() {
														$('#botomatic').waypoint(function() {
														//alert("Hello! I am an alert box!!");
														showAchievement('botomatic');
																
														},
														{ offset: 'bottom-in-view' });
													});
													


													$(document).ready(function() {
														$('#skillbar').waypoint(function() {
															jQuery('.skillbar').each(function(){
																jQuery(this).find('.skillbar-bar').animate({
																	width:jQuery(this).attr('data-percent')
																},3000);
															});			
														},
														{ offset: 'bottom-in-view' });
													});
													
													$(document).ready(function() {
														$('.gead').waypoint(function() {
														//alert("Hello! I am an alert box!!");
																$('#WorkEx').css('display', 'inline');
																
														},
														{ offset: 'bottom-in-view' });
													});
													$(document).ready(function() {
														$('.abouthead').waypoint(function() {
														//alert("Hello! I am an alert box!!");
																$('#WorkEx2').css('display', 'inline');
																
														},
														{ offset: 'bottom-in-view' });
													});
													
													$(document).ready(function() {
														$('.educationhead').waypoint(function() {
														//alert("Hello! I am an alert box!!");
																$('#edu-head').css('display', 'inline');
																
														},
														{ offset: 'bottom-in-view' });
													});


													$(function() {
														$(window).scroll(function() {
															if ($(this).scrollTop() > 75) {
																$(".progress-bar:hidden").css('visibility', 'visible');
															}
														});
													});

													$('.fa-times').click(
														function() {
															$(this).parent().closest('div').parent().closest('div').parent().closest('div').parent().closest('div').toggle("slow");
														});

													function getRandomIntInclusive(min, max) {
														min = Math.ceil(min);
														max = Math.floor(max);
														return Math.floor(Math.random() * (max - min + 1)) + min;
													}
													document.addEventListener("touchstart", function(){}, true);
													//var tl = new TimelineMax({repeat:-1,repeatDelay:2}).to('#PCont', 0.9, { x:2, y:6, rotationX:'20deg', yoyo:true, repeat:-1 });
													//TweenMax.to('#RCont', 0.9, { x:3, y:5, rotationX:'20deg', yoyo:true, repeat:-1 });
													var t2 = new TimelineMax({repeat:-1,repeatDelay:1})
													.to('#SCont',0.7,{rotation:20})
													.to('#SCont',7,{rotation:0,ease:Elastic.easeOut.config(0.9,0.1)});

													TweenMax.to('#PCont', 0.9, { x:0, y:8, rotationX:'20deg', yoyo:true, repeat:-1 });
													TweenMax.to('#SCont', 0.9, { x:0, y:-8, rotationX:'20deg', yoyo:true, repeat:-1 });
													//TweenMax.to('#ECont', 0.9, { x:5, y:2, rotationY:'10deg', yoyo:true, repeat:-1 });
													//TweenMax.to('#JCont', 0.9, { x:2, y:5, rotationY:'10deg', yoyo:true, repeat:-1 });
													var t3 = new TimelineMax({repeat:-1,repeatDelay:1})
													.to('#OCont',0.2,{rotation:20})
													.to('#OCont',7,{rotation:1,ease:Elastic.easeOut.config(0.9,0.1)});
													
													var t4 = new TimelineMax({repeat:-1,repeatDelay:1})
													.to('#PCont',0.7,{rotation:20})
													.to('#PCont',7,{rotation:0,ease:Elastic.easeOut.config(0.9,0.1)});

													var t5 = new TimelineMax({repeat:-1,repeatDelay:1})
													.to('#CCont',0.2,{rotation:20})
													.to('#CCont',7,{rotation:1,ease:Elastic.easeOut.config(0.9,0.1)});
													
													
													TweenMax.to('#OCont', 0.9, { x:0, y:8, rotationX:'20deg', yoyo:true, repeat:-1 });
													TweenMax.to('#CCont', 0.9, { x:0, y:-8, rotationX:'20deg', yoyo:true, repeat:-1 });

													
												/*	TweenMax.to('#CCont', 0.9, { x:-2, y:-6, rotationX:'20deg', yoyo:true, repeat:-1 });
													TweenMax.to('#TCont', 0.9, { x:-7, y:9, rotationY:'10deg', yoyo:true, repeat:-1 });
													TweenMax.to('#SCont', 0.9, { x:-4, y:-8, rotationX:'20deg', yoyo:true, repeat:-1 });
													TweenMax.to('#Proj', 0.9, { x:2, y:6, rotationX:'20deg', yoyo:true, repeat:-1 });
													TweenMax.to('.Proj1', 0.9, { x:2, y:6, rotationX:'20deg', yoyo:true, repeat:-1 });
													TweenMax.to('.Proj2', 0.9, { x:2, y:6, rotationY:'10deg', yoyo:true, repeat:-1 });*/