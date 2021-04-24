<%@page import="com.aklc.ed.model.User"%>
<%
	String loggedin = (String) session.getAttribute("loggedin");
	if (loggedin == null || !loggedin.equals("yes"))
	{
		response.sendRedirect("login.jsp?msg=Session Expired. Please login again");
	}
	else
	{

%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Ashok Kumar Learning Center (AKLC)</title>

<!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/scrolling-nav.css" rel="stylesheet">

</head>

<body id="page-top">

	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top"
		id="mainNav">
		<div class="container">
			<a class="navbar-brand js-scroll-trigger" href="#page-top">ED
				Portal</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="welcome.jsp">Welcome</a></li>
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="encryption.jsp">Encryption</a></li>
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="decryption.jsp">Decryption</a></li>
					<li class="nav-item active"><a class="nav-link js-scroll-trigger"
						href="updateprofile.jsp">Edit Profile</a></li>						
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="changepassword.jsp">Change Password</a></li>						
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="user?req_type=deleteprofile">Delete Profile</a></li>						
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="login.jsp">Logout</a></li>						
				</ul>
			</div>
		</div>
	</nav>


	<section id="about">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 mx-auto">
				
					<%
						String msg = request.getParameter("msg");
					%>
					
					<%
						if (msg != null)
						{
					%>					
					
						<hr/>
						<h3><%=msg %></h3>
						<hr/>
						
					<% } %>
				
				
					<h2>Update Profile</h2>
					<p class="lead">Here you can update your profile information</p>
					
					<%
						User model = (User) session.getAttribute("model");					
					%>
					
					
					<form action='user'>
						<input type=hidden name='req_type' value='updateprofile' />
						<label>Enter your Email ID:</label>
						<input name='email' readonly="readonly" value='<%=model.getEmail() %>' type=text class='form-control' placeholder='Email' />	
						<br/>
						<label>Select your Gender</label>
						<br/>
						
						<% 
							if (model.getGender().equals("Male"))
							{
								%>
						<input type=radio name='gender' value='Male' checked="checked"/> Male &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type=radio name='gender' value='Female' /> Female			
							
								<%
							}
							else
							{
								%>
						<input type=radio name='gender' value='Male' /> Male &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type=radio name='gender' value='Female' checked="checked"/> Female			
								
								<%
							}
						%>
						
						
						
						
						<br/><br/>
						<label>Update your First Name:</label>
						<input name='fname' value='<%=model.getFirstname() %>' type=text class='form-control' placeholder='First name' />	
						<br/>		
						<label>Update your Last Name:</label>
						<input name='lname' value='<%=model.getLastname() %>' type=text class='form-control' placeholder='last name' />	
						<br/>		
						<label>Update your Mobile Number:</label>
						<input name='mobile' value='<%=model.getMobile() %>' type=text class='form-control' placeholder='Mobile Number' />	
						<br/>		
						<label>Update your Address:</label>
						<textarea name='address' class='form-control' placeholder='Address'><%=model.getAddress() %></textarea>	
						
						<br/>	
						<input type=submit value='Update Profile' class='btn btn-primary form-control' />	
					</form>
					

				</div>
			</div>
		</div>
	</section>


	<!-- Footer -->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; ED Portal
				2019</p>
		</div>
		<!-- /.container -->
	</footer>

	<!-- Bootstrap core JavaScript -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Plugin JavaScript -->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom JavaScript for this theme -->
	<script src="js/scrolling-nav.js"></script>

</body>

</html>

<% } %>