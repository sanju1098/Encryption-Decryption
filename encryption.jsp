<%@page import="com.aklc.ed.model.EncryptionHistory"%>
<%@page import="java.util.List"%>
<%@page import="com.aklc.ed.model.User"%>
<%@page import="com.aklc.ed.dao.EncryptionHistoryDAO"%>
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
						href="#about">Welcome</a></li>
					<li class="nav-item active"><a class="nav-link js-scroll-trigger"
						href="encryption.jsp">Encryption</a></li>
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="decryption.jsp">Decryption</a></li>
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="updateprofile.jsp">Edit Profile</a></li>						
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="changepassword.jsp">Change Password</a></li>						
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="user?req_type=deleteprofile">Delete Profile</a></li>						
					<li class="nav-item"><a class="nav-link js-scroll-trigger"
						href="user?req_type=logout">Logout</a></li>						
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
				
				
					<h2>Encryption Operation</h2>
					<hr/>
					
					<h3>Encryption of Plain Text</h3>
					<hr/>
					
					<%
						String encText = request.getParameter("encText");
						if (encText != null)
						{
							%>
								<p>Encrypted Text is: </p>
								<h4><%=encText %></h4>
							<%
						}
					%>
					
					
					<form action='encrypt' >
						<input type=hidden name='req_type' value='plaintext' />
						<label>Enter the text to be encrypted</label>
						<input type=text name='text' class='form-control' placeholder="Plain Text" />
						<br/>
						<input type=submit value='Encrypt' class='btn btn-primary' />
					</form>
					<hr/>
					
					<h3>Encryption of Text File</h3>
					<hr/>
					<form action='encrypt?req_type=textfile' method="post" enctype="multipart/form-data">
						<label>Chose the file to be encrypted</label>
						<input name='file' type=file class='form-control' placeholder="Text File" />
						<br/>
						<input type=submit value='Encrypt' class='btn btn-primary' />
					</form>
					<hr/>
					
					<h3>History</h3>
					<hr/>
					
					<%
						EncryptionHistoryDAO eDao = new EncryptionHistoryDAO();
						User uModel = (User) session.getAttribute("model");
						try 
						{
							List<EncryptionHistory> history = eDao.read(uModel.getEmail());
							if (history != null && history.size() > 0)
							{
								%>
									<table class='table'>
										<tr>
											<th>Email</th>
											<th>Type</th>
											<th>Entry Time</th>
										</tr>
										
										<% 
											for (EncryptionHistory eh: history)
											{
												%>
														<tr>
															<td> <%=eh.getEmail() %></td>
															<td> <%=eh.getEncryption_type() %></td>
															<td> <%=eh.getEntry_time()	 %></td>
															
														</tr>
												<%
											}
										
										%>										
										
									</table>								
								<%								
							}
							else
							{
								%>
									<h4>No History Found</h4>
								<%
							}
							
						} 
						catch (Exception e)
						{
							%>
								
								<h4>Exception while trying to fetch the Encryption History</h4>
							
							<%
						}
						
					%>
					
					
				
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

