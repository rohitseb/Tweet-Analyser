var labelType, useGradients, nativeTextSupport, animate;
var resultJson;
var twt;
var summry;
var wikisum;
(function() {
  var ua = navigator.userAgent,
      iStuff = ua.match(/iPhone/i) || ua.match(/iPad/i),
      typeOfCanvas = typeof HTMLCanvasElement,
      nativeCanvasSupport = (typeOfCanvas == 'object' || typeOfCanvas == 'function'),
      textSupport = nativeCanvasSupport 
        && (typeof document.createElement('canvas').getContext('2d').fillText == 'function');
  //I'm setting this based on the fact that ExCanvas provides text support for IE
  //and that as of today iPhone/iPad current text support is lame
  labelType = (!nativeCanvasSupport || (textSupport && !iStuff))? 'Native' : 'HTML';
  nativeTextSupport = labelType == 'Native';
  useGradients = nativeCanvasSupport;
  animate = !(iStuff || !nativeCanvasSupport);
})();

/*var Log = {
  elem: false,
  write: function(text){
    if (!this.elem) 
      this.elem = document.getElementById('log');
   // this.elem.innerHTML = text;
    this.elem.style.left = (500 - this.elem.offsetWidth / 2) + 'px';
  }
};
*/
function call_tweets_fn(my_tweets){

	document.getElementById("tweet-tab").style.backgroundColor = "#AAA";
	document.getElementById("summary-tab").style.backgroundColor = "#DDD";

	
	var node = document.getElementById("div-tweet-summary-body");

	while (node.hasChildNodes()) {
		node.removeChild(node.lastChild);
	}
	
	var win_height = $(window).height();
	var div_dim = document.getElementById("div-tweet-summary-body").getBoundingClientRect();
	var new_dim_h = win_height-div_dim.top;
	document.getElementById("div-tweet-summary-body").style.maxHeight=new_dim_h+'px'
	
	var i;
	$.each(my_tweets.tweets,function(j, tx){
			var div = document.createElement("div");
			div.setAttribute("id", "tweets");
			div.setAttribute("title", tx.doc_ID);
			div.setAttribute("onMouseOver", "display_tweettab(this)");
			div.setAttribute("onMouseOut", "disable_tweettab()");
			div.setAttribute("onclick", "click_test()");
			div.innerHTML = tx.tweet_text;
			document.getElementById("div-tweet-summary-body").appendChild(div)
		});
}

function call_tweets(){
	call_tweets_fn(twt);
}

function call_summary(){

	var summry = twt;
	var summary_val = "";
	document.getElementById("tweet-tab").style.backgroundColor = "#DDD";
	document.getElementById("summary-tab").style.backgroundColor = "#AAA";

	var node = document.getElementById("div-tweet-summary-body");

	while (node.hasChildNodes()) {
		node.removeChild(node.lastChild);
	}

	var div = document.createElement("div");
	summary_val += "<h3>Wiki:</h3><br>"+summry.wikiSummary+"<br><h3>Tweet Summary:</h3><br>"+summry.summary;
	div.innerHTML = summary_val;
/*	if(summry.wikiSummary!=""){
		div.innerHTML = "<H2>Wiki:</H2>";
		div.innerHTML = summry.wikiSummary;
	}
*/	document.getElementById("div-tweet-summary-body").appendChild(div)
	
	
}

function reCluster(id)
{
	console.log("Inside Reclus!!! --- "+id);
	
	//createLoadingDiv();
	//$( "#infovis" ).load(window.location.href + " #infovis");
	 $.ajax({  
		 headers: { 
		    //    'Accept': 'application/json',
		        'Content-Type': 'application/json; charset=utf-8' 
		    },
         url:'/summarization/webapi/recluster/cluster/'+id,//http://localhost:8080/summarization/webapi  
         type: "POST",
         data: JSON.stringify(resultJson),
         contentType: "application/json; charset=utf-8",
         dataType: 'JSON',
         
         success:function(data){
        	 deletediv();
 			json=data;
 			console.log(json);
 			resultJson=json;
 			console.log("url------"+window.location.href);
			    //$( "#center-container" ).load(window.location.href + " #center-container", draw(data) );
			    /*$( "#infovis" ).load(window.location.href + " #infovis",function yo(){
			    	draw(json);
			    } );*/
 			//deleteLoadingDiv();
 			
 			creatediv();
 			
 			// window.location.reload();
 			draw(json);
         }
     });  
	
	
}

function init(query){
    //init data
	deletediv();
	creatediv();
	
	$.ajax({
		type:'GET',
		url:'/summarization/webapi/query/'+query,//http://localhost:8080/summarization
		headers: {
        "Access-Control-Allow-Origin":"*"
    },
		success:function(data){
			json=data;
			console.log(json);
			resultJson=json;
			//deleteLoadingDiv();
			draw(json);
		}
	}); 

}

function creatediv()
{
	var div = document.createElement("div");
	div.setAttribute("id", "infovis");
	document.getElementById("center-container").appendChild(div);
	
}
function deletediv()
{
	var center_div = document.getElementById("center-container");

	while (center_div.hasChildNodes()) {
		center_div.removeChild(center_div.lastChild);
	}
}

/*function createLoadingDiv(){
	var div = document.createElement("div");
	div.setAttribute("id", "loading");
	document.getElementById("infovis").appendChild(div);
}
function deleteLoadingDiv()
{
	var info_div = document.getElementById("infovis");

	while (info_div.hasChildNodes()) {
		info_div.removeChild(info_div.lastChild);
	}
}*/

function draw(json)
{
	console.log("Inside");
/*	$( "#infovis" ).load(window.location.href + "#infovis", function a() {
      $('#infovis').show();
    });*/
	//$( "#infovis" ).load(window.location.href + " #infovis");
	var rgraph = new $jit.RGraph({
		        //Where to append the visualization
		        injectInto: 'infovis',
		        //Optional: create a background canvas that plots
		        //concentric circles.
		      /*  background: {
		          CanvasStyles: {
		            strokeStyle: '#555'
		          }
		        },*/
		        //Add navigation capabilities:
		        //zooming by scrolling and panning.
		        Navigation: {
		          enable: true,
		          panning: true,
		          zooming: 10
		        },
		        //Set Node and Edge styles.
		        Node: {
		        	overridable: true,
		            color: '#ddeeff'
		        },
		        
		        Edge: {
		          color: '#C17878',
		          lineWidth:1.5
		        },
		        
		      //Set polar interpolation.
		        //Default's linear.
		    //    interpolation: 'polar',
		      //Change the transition effect from linear
		        //to elastic.
		       // transition: $jit.Trans.Elastic.easeOut,
		        //Change other animation parameters.
		        duration:2500,
		        fps: 30,
		        //Change father-child distance.
		        levelDistance: 200,
		      //Add events for Dragging and dropping nodes
		      //Add events for Dragging and dropping nodes
		        
		        Events: {
		            enable: true,
		            type: 'Native',
		            onMouseEnter: function(node, eventInfo, e){
		              rgraph.canvas.getElement().style.cursor = 'move';
		            },
		            onMouseLeave: function(node, eventInfo, e){
		              rgraph.canvas.getElement().style.cursor = '';
		            },
		            onDragMove: function(node, eventInfo, e){
		              var pos = eventInfo.getPos();
		              node.pos.setc(pos.x, pos.y);
		              rgraph.plot();
		            },
		            onDragEnd: function(node, eventInfo, e){
		              rgraph.compute('end');
		              rgraph.fx.animate( {
		                modes: [
		                  'linear'
		                ],
		                duration: 700,
		                transition: $jit.Trans.Elastic.easeOut
		              });
		            },
		            //touch events
		            onTouchStart: function(node, eventInfo, e) {
		              //stop the default event
		              $jit.util.event.stop(e);
		            },
		            onTouchMove: function(node, eventInfo, e){
		              //stop the default event
		              $jit.util.event.stop(e);
		              var pos = eventInfo.getPos();
		              node.pos.setc(pos.x, pos.y);
		              rgraph.plot();
		            },
		            onTouchEnd: function(node, eventInfo, e){
		              //stop the default event
		              $jit.util.event.stop(e);
		              rgraph.compute('end');
		              rgraph.fx.animate( {
		                modes: [
		                  'linear'
		                ],
		                duration: 700,
		                transition: $jit.Trans.Elastic.easeOut
		              });
		            },
		            onRightClick: function(node, eventInfo, e){
		                //hide tips and selections
		          	  console.log("RC");
		          	  console.log(node.id+"   "+node.name+" "+node.tweets+" "+node.lololo+" "+node.data);
		                if((typeof node.name!='undefined')&&(node.id!="1"))
		                {
		                   /*console.log(node.id+"   "+node.name+" "+node.tweets+" "+node.test+" "+node.data);
		                   $.each(resultJson,function(j, tw){
		  						//console.log("inside"),
		                  	 	if (tw.name==node.name) {
		                  	 		$.each(tw.tweets,function(j, tx){
		                  	 			console.log(tx.hashtags+" "+tx.tweet_text);
		                  	 		});
		  						}
		  					});*/
		              	  reCluster(node.id);
		  				  console.log(node.id+"   "+node.name+" "+node.tweets+" "+node.lololo+" "+node.data);
		  				  console.log("inside");
		                }
		              },
		              /*onClick: function(node, eventInfo, e){
		                  //hide tips and selections
		              	console.log("LC");
		            	  console.log(node.id+"   "+node.name+" "+node.tweets+" "+node.lololo+" "+node.data);
		                  if((typeof node.name!='undefined'))
		                  {
		                     console.log(node.id+"   "+node.name+" "+node.tweets+" "+node.test+" "+node.data);
		                     $.each(resultJson,function(j, tw){
		    						//console.log("inside"),
		                  	   var count=0;
		                    	 	if (tw.name==node.name) {
		                    	 		$.each(tw.tweets,function(j, tx){
		                    	 			console.log(tx.hashtags+" "+tx.tweet_text);
		                    	 			++count;
		                    	 		});
		                    	 		console.log(count);
		    						}
		    					});
		                  }
		                }*/
		          },
		        
		        
		        
		        
		        

		        /*onBeforeCompute: function(node){
		            Log.write("centering " + node.name + "...");
		            //Add the relation list in the right column.
		            //This list is taken from the data property of each JSON node.
		            $jit.id('inner-details').innerHTML = node.data.relation;
		        },*/
		        
		        //Add the name of the node in the correponding label
		        //and a click handler to move the graph.
		        //This method is called once, on label creation.
		        onCreateLabel: function(domElement, node){
		            domElement.innerHTML = node.name;
		            domElement.onclick = function(){
		                rgraph.onClick(node.id, {
		                    onComplete: function() {
		                    	console.log(node.id+"   "+node.name+" "+node.tweets+" "+node.lololo+" "+node.data);
		                    	$.each(resultJson,function(j, tw){
		    						//console.log("inside"),
		                  	   var count=0;
		                    	 	if (tw.name==node.name) {
		                    	 		twt=tw; 
		                    	 		call_tweets_fn(twt);
		                    	 		/*$.each(tw.tweets,function(j, tx){
		                    	 			console.log(tx.hashtags+" "+tx.tweet_text);
		                    	 			twt=tw
		                    	 			++count;
		                    	 		});*/
		                    	 		console.log(count);
		    						}
		    					});
		                    }
		                });
		            };
		        },
		        //Change some label dom properties.
		        //This method is called each time a label is plotted.
		        onPlaceLabel: function(domElement, node){
		            var style = domElement.style;
		            style.display = '';
		            style.cursor = 'pointer';

		            if (node._depth <= 1) {
		                style.fontSize = "0.8em";
		                style.color = "#000000";
		            
		            } else if(node._depth == 2){
		                style.fontSize = "0.7em";
		                style.color = "#494949";
		            
		            } else {
		            	 style.fontSize = "0.7em";
			                style.color = "#494949";
		            }

		            var left = parseInt(style.left);
		            var w = domElement.offsetWidth;
		            style.left = (left - w / 2) + 'px';
		        }
		    });
			
			
			
			
		    //load JSON data
		    rgraph.loadJSON(json);
		    //trigger small animation
		    rgraph.graph.eachNode(function(n) {
		      var pos = n.getPos();
		      pos.setc(-200, -200);
		    });	
		    rgraph.compute('end');
		    rgraph.fx.animate({
		      modes:['polar'],
		      duration: 2000
		    });
		    //end
		    
		
    
    //init RGraph
}