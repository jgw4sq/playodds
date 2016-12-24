


function changeEmployeeValue(newValue){
	
	if(!newValue==="Select Other Option to Change Employee"){
		if(!newValue==="There are no other avilable employees"){
			document.getElementById("employee").value = newValue;
		}
	}
	

};