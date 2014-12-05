function missedCall(){
	cordova.exec(missedCallSuccess, missedCallFailure, "MissedCallPlugin", "missedCall", []);
}

function missedCallSuccess(data){
	alert("OK: " + data);
}

function missedCallFailure(data){
	alert("FAIL: " + data);
}

missedCall();
