<%@ page import="java.util.ArrayList"%>
<%@ page import="com.weber.TimeOff"%>
<!DOCTYPE html>
<style>
span.requestoffheader {
	color: green;
	font-size: 24px;
}

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
</style>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Request Off | ScheduleMe</title>
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
                <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
                <a id="logo" class="pull-left" href="index.jsp"></a>
                <div class="nav-collapse collapse pull-right">
                    <ul class="nav">
                        <li class="active"><a href="index.jsp">Home</a></li>
                        <li><a href="<%=request.getContextPath()%>/MyAccount">My Account</a></li>
                        <li><a href="<%=request.getContextPath()%>/MySchedule">My Schedule</a></li>
                        <li><a href="<%=request.getContextPath()%>/RequestOff">RequestOff</a></li>
                        <li><a href="signup.jsp">Registration</a></li>
                        <li class="login">
                            <a data-toggle="modal" href="#loginForm"><i class="icon-lock"></i>  Sign In</a>
                        </li>
                    </ul>        
                </div><!--/.nav-collapse -->
            </div>
        </div>
    </header>
	<!-- /header -->

	<section class="title">
		<div class="container">
			<div class="row-fluid">
				<div class="span6">
					<h1>Request Off</h1>
				</div>
				<div class="span6">
					<ul class="breadcrumb pull-right">
						<li><a href="index.html">Home</a> <span class="divider">/</span></li>
						<li class="active">Request Off</li>
					</ul>
				</div>
			</div>
		</div>
	</section>
	<!-- / .title -->

	<section id="portfolio" class="container main">
		<div>
			
			<h3 align="center">Approved Time Off</h3>

			<%
				// retrieve your list from the request, with casting 
				ArrayList<TimeOff> list = (ArrayList<TimeOff>) request
						.getAttribute("approvedtimesoff");
			%>
			<table align="center" bordercolor="green" border="1" cellpadding="5"
				cellspacing="5">
				<tr>
					<th>Period</th>
					<th>Start Time</th>
					<th>End Time</th>
				</tr>

				<%
					int x = 0;
					// print the information about every category of the list
					for (TimeOff timeoff : list) {
						TimeOff timeoff1 = timeoff;
						x += 1;
				%>

				<tr>
					<td>
						<%
							out.print(x);
						%>
					</td>
					<td>
						<%
							out.print(timeoff1.getStartTime().toString());
						%>
					</td>
					<td>
						<%
							out.print(timeoff1.getEndTime().toString());
						%>
					</td>


				</tr>

				<%
					}
				%>
			</table>
			<br> <br>
		</div>
		<div>
			
			<h3 align="center">Not Approved Time Requested Off</h3>
			
			<%
			// retrieve your list from the request, with casting 
			ArrayList<TimeOff> list2 = (ArrayList<TimeOff>) request
					.getAttribute("notapprovedtimesoff");
%>
			<table align="center" bordercolor="green" border="1" cellpadding="5"
				cellspacing="5">
				<tr>
					<th>Period</th>
					<th>Start Time</th>
					<th>End Time</th>
				</tr>

				<%
					int y = 0;
					// print the information about every category of the list
					for (TimeOff timeoff : list2) {
						TimeOff timeoff1 = timeoff;
						y += 1;
				%>

				<tr>
					<td>
						<%
							out.print(y);
						%>
					</td>

					<td>
						<%
							out.print(timeoff1.getStartTime().toString());
						%>
					</td>
					<td>
						<%
							out.print(timeoff1.getEndTime().toString());
						%>
					</td>


				</tr>

				<%
					}
				%>
			</table>
			<br> <br>
		</div>
		<div>
			<h3 align="center">Request Off Time</h3>
			<form name="f2" method="post" action="RequestOff" id="f2">
				<span class="requestoffheader">Start Time:<span> <select
						style="width: 150px;" name="year">
							<option value="2016">2016</option>
					</select><select name="month" style="width: 150px;">
							<option value="" disabled selected>Month</option>
							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
					</select> <select name="day" style="width: 150px;">
							<option value="" disabled selected>Day</option>

							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
							<option value="31">31</option>
					</select> <select name="hour" style="width: 150px;">
							<option value="" disabled selected>Hour</option>

							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
					</select> <select name="minutes" style="width: 150px;">
							<option value="" disabled selected>Minutes</option>
							<option value="00">00</option>
							<option value="15">15</option>
							<option value="30">30</option>
							<option value="45">45</option>

					</select> <select name="ampm" style="width: 150px;">
							<option value="" disabled selected>AM/PM</option>

							<option value="am">AM</option>
							<option value="pm">PM</option>
					</select><br> <span class="requestoffheader">End Time: </span><select
						style="width: 150px;" name="year2">
							<option value="2016">2016</option>
					</select><select name="month2" style="width: 150px;">
							<option value="" disabled selected>Month</option>
							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
					</select> <select name="day2" style="width: 150px;">
							<option value="" disabled selected>Day</option>

							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
							<option value="31">31</option>
					</select> <select name="hour2" style="width: 150px;">
							<option value="" disabled selected>Hour</option>

							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
					</select> <select name="minutes2" style="width: 150px;">
							<option value="" disabled selected>Minutes</option>
							<option value="00">00</option>
							<option value="15">15</option>
							<option value="30">30</option>
							<option value="45">45</option>

					</select> <select name="ampm2" style="width: 150px;">
							<option value="" disabled selected>AM/PM</option>

							<option value="am">AM</option>
							<option value="pm">PM</option>
					</select><br> </select><br> <input type="submit" value="Request Off">
			</form>
		</div>

	</section>

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
