function validateForm() {

    var ret = true;
    var name = document.getElementById('name').value;
    if (!name) {
       document.getElementById("nameErr").innerHTML = "Please Enter Your Name";
       ret = false;
   }
   else {
    document.getElementById("nameErr").innerHTML = "";
}

var phone = document.getElementById('phone').value;
if (!validatePhone(phone)) {
   document.getElementById("phoneErr").innerHTML = "Please Enter a Valid Phone Number (Should be within 10 to 13 digits in length)";
   ret = false;
}
else {
    document.getElementById("phoneErr").innerHTML = "";
}	

var email = document.getElementById('email').value;
if (!validateEmail(email)) {
   document.getElementById("emailErr").innerHTML = "Please Enter Valid Email ID";
   ret = false;
}
else {
    document.getElementById("emailErr").innerHTML = "";
}

var message = document.getElementById('message').value;
if (!message) {
   document.getElementById("messageErr").innerHTML = "Please Enter Your Message";
 //  ret = false;
}
else {
    document.getElementById("messageErr").innerHTML = "";
}
    /*if(ret){
        var data = $(this).serializeFormJSON();
        console.log(data);
    }
    else{
        return ret ;
    }*/
    return ret ;
}
 

$(document).ready(function(){
    $("form").submit(function(e){
        e.preventDefault();
        val=validateForm();
        ///alert("Submitted");
        if(val){
            data=$('#formid').serialize();
            var o = {};
            var a = $('#formid').serializeArray();
            $.each(a, function () {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            alert(o);
            $.ajax({
                type: 'post',
                headers: { 'Content-Type': 'application/json' },
                url: 'http://localhost:8080/summarization/webapi/send/print',
                data: o,
                success: function () {
                 alert("Your Message has been sent!");
                }
            });
        }
    });
});


function clearErrors() {
    document.getElementById("nameErr").innerHTML = "";
    document.getElementById("phoneErr").innerHTML = "";
    document.getElementById("emailErr").innerHTML = "";
    document.getElementById("messageErr").innerHTML = "";
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function validatePhone(phone) {
    phone = phone.replace(/[^0-9]/g, '');
	if(phone.length >= 10&&phone.length <= 13) { 
		return true;
	} else {
		return false;
	}
}
