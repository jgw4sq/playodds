


function changeEmployeeValue(newValue){
	alert("hi");
	if(!newValue=="Select Other Option to Change Employee"){
		alert("passed first");
		if(!newValue=="There are no other avilable employees"){
			alert("passed second");
			document.getElementById("employee").value = newValue;
		}else{
			alert("failed second");
		}
	}else{

		alert("failed first");
	}
	

}