<%@page import="com.hospital"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hospital Details</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script type="text/javascript" src="Components/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="Components/hospital.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-7">
				<h1 class="m-3">Hospital details</h1>
				
				<form id="formHospital" name="formHospital" method="post" action="hospital.jsp">
					
					Hospital name: 
					<input id="hosName" name="hosName" type="text" class="form-control form-control-sm"> 
					
					<br>
					 Hospital address:
					<input id="hosAddress" name="hosAddress" type="text" class="form-control form-control-sm">
					
					<br> 
					Hospital contact no: 
					<input id="hosContactNo" name="hosContactNo" type="text" class="form-control form-control-sm">
					
					 <br> 
					 Hospital capacity: 
					 <input id="hosCapacity" name="hosCapacity" type="text" class="form-control form-control-sm"> 
					 
					 <br> 
					 Hospital units: 
					 <input id="hosUnits" name="hosUnits" type="text" class="form-control form-control-sm"> 
					 
					 <br> 
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary"> 
					<input type="hidden" id="hidHospitalIDSave" name="hidHospitalIDSave" value="">
				
				</form>
				
					<div id="alertSuccess" class="alert alert-success"></div>				
					<div id="alertError" class="alert alert-danger"></div>

			</div>
		</div>
		
		
	
	
	<br>
				<div id = "divHospitalGrid">
							
				<%
					hospital hospitalObj = new hospital();
					out.print(hospitalObj.readHospitals());
				%>
			</div>

</body>
</html>