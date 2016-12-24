


function changeEmployeeValue(newValue){
	var split = newValue.split(",");
	var email = split[1];
	if(newValue!="Select Other Option to Change Employee"){
		if(newValue!="There are no other avilable employees"){
			document.getElementById("employee").value = split[0];
			document.getElementById("employeeEmail").value=email;
		}else{
		}
	}else{

	}
	

}