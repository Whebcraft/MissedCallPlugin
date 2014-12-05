function greetMe(){
	var name = "Hi!!";
	cordova.exec(missedCallSuccess, missedCallFailure, "MissedCallPlugin", "missedCall", [name]);
}

function missedCallSuccess(data){
	alert("OK: " + data);
}

function missedCallFailure(data){
	alert("FAIL: " + data);
}

greetMe();