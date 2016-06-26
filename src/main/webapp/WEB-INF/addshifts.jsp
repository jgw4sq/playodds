<form name="f2" method="post" action="AddShifts" id="f2">
		Pool: <input type="text" name="pool" placeholder="Pool"><br>
		StartTime: <input type="text" name="year" placeholder="year"><input
			type="text" name="month" placeholder="month(01)"><input
			type="text" name="day" placeholder="day(01)"><input
			type="text" name="hour:minute" placeholder="hour:minute(10:30)"><select name="ampm">
			<option value="am">AM</option>
			<option value="pm">PM</option>
		</select><br> 
		EndTime: <input type="text" name="year2" placeholder="year"><input
			type="text" name="month2" placeholder="month(01)"><input
			type="text" name="day2" placeholder="day(01)"><input
			type="text" name="hour:minute2" placeholder="hour:minute(10:30)"><select name="ampm2">
			<option value="am" >AM</option>
			<option value="pm" >PM</option>
		</select><br> 
		How many guards are needed for this shift? <input type="number" name="numberofguards" placeholder="#ofGuards">
		<input type="submit" value="submit"> 
	</form>