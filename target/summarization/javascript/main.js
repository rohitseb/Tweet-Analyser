 $(function(){
	 var $orders = $('#orders');
	 var $name = $('#name');
	 var $drink = $('#drink');
	 
	$.ajax({
		type:'GET',
		url:'webapi/messages',
		success:function(data){
			$.each(data,function(i, order){
				$orders.append('<li><br><br>name: '+order.name+', size: '+order.size+'</li><br><br>'),
					$.each(order.tweets,function(j, tw){
						console.log("inside"),
						$orders.append('<li>'+tw.tweet_text+' hashtags - '+tw.hashtags+'</li>')						
					});				
			});
		}
	}); 

	$('#add-order').on('click',function(){
		var order={
			name:$name.val(),
			drink:$drink.val(),
		};
		$.ajax({
			type:'POST',
			url:'https://learnwebcode.github.io/json-example/animals-1.json',
			data:order,
			success: function(){
				$orders.append('<li>name: '+order.name+', species: '+order.species+'</li>');
			}
		});
	});

	
 });