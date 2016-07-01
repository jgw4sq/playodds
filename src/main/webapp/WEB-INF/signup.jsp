<html>
<body>
	<form name="f2" method="post" action="SignUp" id="content">
		Email: <input type="text" name="email" placeholder="example@gmail.com"><br>
		Name: <input type="text" name="name" placeholder="First Last"><br>
		Age: <input type="number" name="age" placeholder="Age"><br>
		Main Pool: <input type="text" name="pool" placeholder="Pool"><br>
		Can you work at other pools?: <input type="radio" name="otherPools"
			placeholder="yes"><br> Position: <select id="mySelect"
			onchange="myFunction()">
			<option value="Guard">Guard
			<option value="Head Guard">Head Guard
			<option value="Assistant Manager">Assistant Manager
			<option value="Manager">Manager
		</select><br> <span id="para">Password:</span> <input id="password" type="password" name="password"
			placeholder="Password"><br> Confirm Password: <input
			type="password" name="password2" placeholder="Confirm Password"><br>

		<input type="submit" value="submit">
	</form>

	<script>
		var intTextBox = 0;
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
			}
			else {
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
			}
			else {
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
			}
			else {
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
