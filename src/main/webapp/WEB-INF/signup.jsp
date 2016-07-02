<html>
<body>
	<form name="f2" method="post" action="SignUp" id="content">
		Email: <input onchange="validateemail()" type="text" id = "email" name="email" placeholder="example@gmail.com"
			required><br>
			Confirm Email: <input onchange = "validateemail()" id = "email2" type="text" name="email2" placeholder="example@gmail.com"
			required><br> <span id="namespan">Name: </span><input id="name" type="text" name="name"
			placeholder="First Last" required><br> Age: <input
			type="number" name="age" placeholder="Age" required><br>
		Main Pool: <select
			id="poolSelect">
			<option value="Glen Allen">Glen Allen
			<option value="Magnolia Ridge">Magnolia Ridge
		</select><br>
		Can you work at other pools?: <input type="radio" name="otherPools"
			placeholder="yes" required><br> Position: <select
			id="mySelect" onchange="myFunction()">
			<option value="Guard">Guard
			<option value="Head Guard">Head Guard
			<option value="Assistant Manager">Assistant Manager
			<option value="Manager">Manager
		</select><br> <span id="para">Password:</span> <input onchange="validatepassword()" id="password"
			type="password" name="password" placeholder="Password" required><br>
		Confirm Password: <input id="password2" type="password" name="password2"
			placeholder="Confirm Password" onchange="validatepassword()" required><br> <input
			id="submit" type="submit" onClick= "check_pass" value="submit">
	</form>

	<script>
	var intTextBox = 0;
function validateemail(){
			
			var email1 = document.getElementById("email").value;
			var email2 = document.getElementById("email2").value;
			var element =  document.getElementById('emailnotsame');
			if (element== null&&email1!=email2){
				var objNewDiv = document.createElement('div');
				objNewDiv.setAttribute('id', 'emailnotsame');
				objNewDiv.innerHTML =  "<span> Warning! Your emails do not match!</span><br>"
				document.getElementById('content').insertBefore(objNewDiv,
						document.getElementById("namespan"));
				return;
				
			}
			else if (email1==email2){
				document.getElementById('content').removeChild(
						document.getElementById('emailnotsame'));
			}
		}
		
		function validatepassword(){
			
			var password1 = document.getElementById("password").value;
			var password2 = document.getElementById("password2").value;
			var element =  document.getElementById('passwordnotsame');
			if (element== null&&password1!=password2){
				var objNewDiv = document.createElement('div');
				objNewDiv.setAttribute('id', 'passwordnotsame');
				objNewDiv.innerHTML =  "<span>Warning !Your passwords do not match!</span><br>"
				document.getElementById('content').insertBefore(objNewDiv,
						document.getElementById("submit"));
				return;
				
			}
			else if (password1==password2){
				document.getElementById('content').removeChild(
						document.getElementById('passwordnotsame'));
			}
		}
		function myFunction() {
			var x = document.getElementById("mySelect").value;
			if (x == "Manager") {
				intTextBox++;
				var objNewDiv = document.createElement('div');
				objNewDiv.setAttribute('id', 'div_' + intTextBox);
				objNewDiv.innerHTML = 'Manager Code '
						+ ': <input type="text" id="tb_' + '" name="tb_' + intTextBox + '"/><br>';
				document.getElementById('content').insertBefore(objNewDiv,
						document.getElementById("para"));
				return;
			} else {
				if (0 < intTextBox) {
					document.getElementById('content').removeChild(
							document.getElementById('div_' + intTextBox));
					intTextBox--;
				} else {
				}
			}
			if (x == "Assistant Manager") {
				intTextBox++;
				var objNewDiv = document.createElement('div');
				objNewDiv.setAttribute('id', 'div_' + intTextBox);
				objNewDiv.innerHTML = 'Assistant Manager Code '
						+ ': <input type="text" id="tb_' + '" name="tb_' + intTextBox + '"/><br>';
				document.getElementById('content').insertBefore(objNewDiv,
						document.getElementById("para"));
				return;
			} else {
				if (0 < intTextBox) {
					document.getElementById('content').removeChild(
							document.getElementById('div_' + intTextBox));
					intTextBox--;
				} else {
				}
			}
			if (x == "Head Guard") {
				intTextBox++;
				var objNewDiv = document.createElement('div');
				objNewDiv.setAttribute('id', 'div_' + intTextBox);
				objNewDiv.innerHTML = 'Head Guard Code '
						+ ': <input type="text" id="tb_' + '" name="tb_' + intTextBox + '"/><br>';
				document.getElementById('content').insertBefore(objNewDiv,
						document.getElementById("para"));
			} else {
				if (0 < intTextBox) {
					document.getElementById('content').removeChild(
							document.getElementById('div_' + intTextBox));
					intTextBox--;
				} else {
				}
			}

		}
	</script>
</body>


</html>
