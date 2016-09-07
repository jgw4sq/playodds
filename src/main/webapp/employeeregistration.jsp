<%@ page import="com.weber.User" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<style>
input[type=submit] {
	background-color: #4CAF50; /* Green */
	border: none;
	color: white;
	padding: 15px 32px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
}
span.registertablehead{
color:green;
font-size:24px;
	font-weight:bold;

}
</style>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Registration | Nova</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">

<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/sl-slide.css">

<script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="images/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="images/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="images/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="images/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="images/ico/apple-touch-icon-57-precomposed.png">
</head>

<body>

	<!--Header-->
	   	<header class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> <a id="logo" class="pull-left" href="index.jsp"></a>
				<div class="nav-collapse collapse pull-right">
					<ul class="nav">
						<li class="active"><a href="index.jsp">Home</a></li>
						<li><a href="<%=request.getContextPath()%>/MyAccount">My
								Account</a></li>
						<li><a href="<%=request.getContextPath()%>/MySchedule">My
								Schedule</a></li>
						<li><a href="<%=request.getContextPath()%>/RequestOff">RequestOff</a></li>
						<% User user = (User)request.getSession().getAttribute("user");
			if(user!=null){
				if(user.getPosition().equals("Manager")){%>
						<li><a href="<%=request.getContextPath()%>/ScheduleManager">Schedule Manager</a></li>
						<%}} %>
						<li class="login"><a data-toggle="modal" href="#loginForm"><i
								class="icon-lock"></i> Sign In</a></li>
						<li><a href="<%=request.getContextPath()%>/Logout">Logout</a></li>

						<li><a href="signup.jsp">Registration</a></li>

					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</header>
	<!-- /header -->


	<section class="title">
		<div class="container">
			<div class="row-fluid">
				<div class="span6">
					<h1>Registration</h1>
				</div>
				<div class="span6">
					<ul class="breadcrumb pull-right">
						<li><a href="index.html">Home</a> <span class="divider">/</span></li>
						<li><a href="#">Pages</a> <span class="divider">/</span></li>
						<li class="active">Registration</li>
					</ul>
				</div>
			</div>
		</div>
	</section>
	<!-- / .title -->


	<section id="registration-page" class="container">
		<form class="center" name="f2" method="post" action="SignUp"
			id="content">
			<table align="center" cellspacing="5" cellpadding="5">
				<tr>
					<td><span class="registertablehead">Email: </span></td>
					<td><input onchange="validateemail()" type="text" id="email"
						name="email" placeholder="example@gmail.com" required></td>
				</tr>
				<tr>
					<td><span class="registertablehead">Confirm Email: </span></td>
					<td><input onchange="validateemail()" type="text" id="email"
						name="email" placeholder="example@gmail.com" required></td>
				</tr>
				<tr>
					<td><span class="registertablehead" id="namespan">Name: </span></td>
					<td><input id="name" type="text" name="name"
						placeholder="First Last" required></td>
				</tr>
				<tr>
					<td><span class="registertablehead">Age: </span></td>
					<td><input type="number" name="age" placeholder="Age" required>
					</td>
				</tr>
				<tr>
					<td><span class="registertablehead">Main Pool: </span></td>
					<td><select id="poolSelect" name="poolSelect">
							<option value="Glen Allen">Glen Allen
							<option value="Magnolia Ridge">Magnolia Ridge
					</select></td>
				</tr>
				<tr>
					<td><span class="registertablehead">Can you work at other pools?: </span></td>
					<td><select id="poolSelect" name="otherPools">
							<option value="Yes">Yes
							<option value="No">No
					</select></td>
				</tr>
				<tr>
					<td><span class="registertablehead">Position: </span></td>
					<td><select id="mySelect" onchange="myFunction()"
						name="mySelect">
							<option value="Guard">Guard
							<option value="Head Guard">Head Guard
							<option value="Assistant Manager">Assistant Manager
							<option value="Manager">Manager
					</select></td>
				</tr>
				<tr>
					<td><span class="registertablehead">Password: </span></td>
					<td><input onchange="validatepassword()" id="password"
						type="password" name="password" placeholder="Password" required>
					</td>
				</tr>
				<tr>
					<td><span class="registertablehead">Confirm Password: </span></td>
					<td><input id="password2" type="password" name="password2"
						placeholder="Confirm Password" onchange="validatepassword()"
						required>
				</tr>
			</table>
			<input id="submit" type="submit" onClick="check_pass"
				value="Register">
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
						+ ': <input type="text" id="tb_' + '" name="positioncode"/><br>';
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
						+ ': <input type="text" id="tb_' + '" name="positioncode"/><br>';
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
						+ ': <input type="text" id="tb_' + '" name="positioncode"/><br>';
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
	</section>
	<!-- /#registration-page -->

	<!--Bottom-->
	<section id="bottom" class="main">
		<!--Container-->
		<div class="container">

			<!--row-fluids-->
			<div class="row-fluid">

				<!--Contact Form-->
				<div class="span3">
					<h4>ADDRESS</h4>
					<ul class="unstyled address">
						<li><i class="icon-home"></i><strong>Address:</strong> 1032
							Wayback Lane, Wantagh<br>NY 11793</li>
						<li><i class="icon-envelope"></i> <strong>Email: </strong>
							support@email.com</li>
						<li><i class="icon-globe"></i> <strong>Website:</strong>
							www.domain.com</li>
						<li><i class="icon-phone"></i> <strong>Toll Free:</strong>
							631-409-3105</li>
					</ul>
				</div>
				<!--End Contact Form-->

				<!--Important Links-->
				<div id="tweets" class="span3">
					<h4>OUR COMPANY</h4>
					<div>
						<ul class="arrow">
							<li><a href="#">About Us</a></li>
							<li><a href="#">Support</a></li>
							<li><a href="#">Terms of Use</a></li>
							<li><a href="#">Privacy Policy</a></li>
							<li><a href="#">Copyright</a></li>
							<li><a href="#">We are hiring</a></li>
							<li><a href="#">Clients</a></li>
							<li><a href="#">Blog</a></li>
						</ul>
					</div>
				</div>
				<!--Important Links-->

				<!--Archives-->
				<div id="archives" class="span3">
					<h4>ARCHIVES</h4>
					<div>
						<ul class="arrow">
							<li><a href="#">December 2012 (1)</a></li>
							<li><a href="#">November 2012 (5)</a></li>
							<li><a href="#">October 2012 (8)</a></li>
							<li><a href="#">September 2012 (10)</a></li>
							<li><a href="#">August 2012 (29)</a></li>
							<li><a href="#">July 2012 (1)</a></li>
							<li><a href="#">June 2012 (31)</a></li>
						</ul>
					</div>
				</div>
				<!--End Archives-->

				<div class="span3">
					<h4>FLICKR GALLERY</h4>
					<div class="row-fluid first">
						<ul class="thumbnails">
							<li class="span3"><a
								href="http://www.flickr.com/photos/76029035@N02/6829540293/"
								title="01 (254) by Victor1558, on Flickr"><img
									src="http://farm8.staticflickr.com/7003/6829540293_bd99363818_s.jpg"
									width="75" height="75" alt="01 (254)"></a></li>
							<li class="span3"><a
								href="http://www.flickr.com/photos/76029035@N02/6829537417/"
								title="01 (196) by Victor1558, on Flickr"><img
									src="http://farm8.staticflickr.com/7013/6829537417_465d28e1db_s.jpg"
									width="75" height="75" alt="01 (196)"></a></li>
							<li class="span3"><a
								href="http://www.flickr.com/photos/76029035@N02/6829527437/"
								title="01 (65) by Victor1558, on Flickr"><img
									src="http://farm8.staticflickr.com/7021/6829527437_88364c7ec4_s.jpg"
									width="75" height="75" alt="01 (65)"></a></li>
							<li class="span3"><a
								href="http://www.flickr.com/photos/76029035@N02/6829524451/"
								title="01 (6) by Victor1558, on Flickr"><img
									src="http://farm8.staticflickr.com/7148/6829524451_a725793358_s.jpg"
									width="75" height="75" alt="01 (6)"></a></li>
						</ul>
					</div>
					<div class="row-fluid">
						<ul class="thumbnails">
							<li class="span3"><a
								href="http://www.flickr.com/photos/76029035@N02/6829524451/"
								title="01 (6) by Victor1558, on Flickr"><img
									src="http://farm8.staticflickr.com/7148/6829524451_a725793358_s.jpg"
									width="75" height="75" alt="01 (6)"></a></li>
							<li class="span3"><a
								href="http://www.flickr.com/photos/76029035@N02/6829540293/"
								title="01 (254) by Victor1558, on Flickr"><img
									src="http://farm8.staticflickr.com/7003/6829540293_bd99363818_s.jpg"
									width="75" height="75" alt="01 (254)"></a></li>
							<li class="span3"><a
								href="http://www.flickr.com/photos/76029035@N02/6829537417/"
								title="01 (196) by Victor1558, on Flickr"><img
									src="http://farm8.staticflickr.com/7013/6829537417_465d28e1db_s.jpg"
									width="75" height="75" alt="01 (196)"></a></li>
							<li class="span3"><a
								href="http://www.flickr.com/photos/76029035@N02/6829527437/"
								title="01 (65) by Victor1558, on Flickr"><img
									src="http://farm8.staticflickr.com/7021/6829527437_88364c7ec4_s.jpg"
									width="75" height="75" alt="01 (65)"></a></li>
						</ul>
					</div>

				</div>

			</div>
			<!--/row-fluid-->
		</div>
		<!--/container-->

	</section>
	<!--/bottom-->

	<!--Footer-->
	<footer id="footer">
		<div class="container">
			<div class="row-fluid">
				<div class="span5 cp">
					&copy; 2013 <a target="_blank" href="http://shapebootstrap.net/"
						title="Free Twitter Bootstrap WordPress Themes and HTML templates">ShapeBootstrap</a>.
					All Rights Reserved.
				</div>
				<!--/Copyright-->

				<div class="span6">
					<ul class="social pull-right">
						<li><a href="#"><i class="icon-facebook"></i></a></li>
						<li><a href="#"><i class="icon-twitter"></i></a></li>
						<li><a href="#"><i class="icon-pinterest"></i></a></li>
						<li><a href="#"><i class="icon-linkedin"></i></a></li>
						<li><a href="#"><i class="icon-google-plus"></i></a></li>
						<li><a href="#"><i class="icon-youtube"></i></a></li>
						<li><a href="#"><i class="icon-tumblr"></i></a></li>
						<li><a href="#"><i class="icon-dribbble"></i></a></li>
						<li><a href="#"><i class="icon-rss"></i></a></li>
						<li><a href="#"><i class="icon-github-alt"></i></a></li>
						<li><a href="#"><i class="icon-instagram"></i></a></li>
					</ul>
				</div>

				<div class="span1">
					<a id="gototop" class="gototop pull-right" href="#"><i
						class="icon-angle-up"></i></a>
				</div>
				<!--/Goto Top-->
			</div>
		</div>
	</footer>
	<!--/Footer-->

	<!--  Login form -->
	<div class="modal hide fade in" id="loginForm" aria-hidden="false">
		<div class="modal-header">
			<i class="icon-remove" data-dismiss="modal" aria-hidden="true"></i>
			<h4>Login Form</h4>
		</div>
		<!--Modal Body-->
		<div class="modal-body">
			<form class="form-inline" action="index.html" method="post"
				id="form-login">
				<input type="text" class="input-small" placeholder="Email">
				<input type="password" class="input-small" placeholder="Password">
				<label class="checkbox"> <input type="checkbox">
					Remember me
				</label>
				<button type="submit" class="btn btn-primary">Sign in</button>
			</form>
			<a href="#">Forgot your password?</a>
		</div>
		<!--/Modal Body-->
	</div>
	<!--  /Login form -->

	<script src="js/vendor/jquery-1.9.1.min.js"></script>
	<script src="js/vendor/bootstrap.min.js"></script>
	<script src="js/main.js"></script>

</body>
</html>