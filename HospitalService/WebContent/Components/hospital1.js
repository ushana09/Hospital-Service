$(document).ready(function(){
	
	if($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
}); 

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateHospitalForm();
	if(status != true)
		{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
		}
	
// If valid------------------------
	
	//$("#formHospital").submit
	var type = ($("#hidHospitalIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "hospitalsAPI",
		type : type,
		data : $("#formHospital").serialize(),
		dataType : "text",
		complete : function(response, status) 
		{
			onHospitalsSaveComplet(response.responseText, status);
		}
	});
});
	
	function onHospitalsSaveComplet(response, status) {
		if (status == "success") 
		{
			var resultSet = JSON.parse(response);
			
			if (resultSet.status.trim() == "success") 
			{
				$("#alertSuccess").text("Successfully saved.");
				$("#alertSuccess").show();
				
				$("#divHospitalGrid").html(resultSet.data);
				
			} else if (resultSet.status.trim() == "error") {
				
				$("#alertError").text(resultSet.data);
				$("#alertError").show();
			}
		} else if (status == "error") {
			$("#alertError").text("Error while saving.");
			$("#alertError").show();
		} else {
			$("#alertError").text("Unknown error while saving..");
			$("#alertError").show();
		}
		$("#hidHospitalIDSave").val("");
		$("#formHospital")[0].reset();
	}
	



//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
		{
			$("#hidHospitalIDSave").val($(this).closest("tr").find('#hidHospitalIDUpdate').val());
			$("#hosName").val($(this).closest("tr").find('td:eq(0)').text());
			$("#hosAddress").val($(this).closest("tr").find('td:eq(1)').text());
			$("#hosContactNo").val($(this).closest("tr").find('td:eq(2)').text());
			$("#hosCapacity").val($(this).closest("tr").find('td:eq(3)').text());
			$("#hosUnits").val($(this).closest("tr").find('td:eq(4)').text());
		});


//remove
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "hospitalsAPI",
		type : "DELETE",
		data : "hosId=" + $(this).data("hosId"),
		dataType : "text",
		complete : function(response, status) 
		{
			onHospitalDeleteComplete(response.responseText, status);
		}
	});
});


function onHospitalDeleteComplete(response, status) {
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") 
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			
			$("#divItemsGrid").html(resultSet.data);
		
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

//CLIENTMODEL=========================================================================
function validateHospitalForm() {
	// NAME
	if ($("#hosName").val().trim() == "") {
		return "Insert Hospital Name.";
	}
	// ADDRESS
	if ($("#hosAddress").val().trim() == "") {
		return "Insert Hospital Address.";
	}
	// CONTACT NUMBER-------------------------------
	var tmpContact = $("#hosContactNo").val().trim();
	if (!$.isNumeric(tmpContact)) {
		return "Insert Hospital Contact Number.";
	}
	// DESCRIPTION------------------------
	if ($("#hosCapacity").val().trim() == "") {
		return "Insert Hospital Capacity.";
	}
	if ($("#hosUnits").val().trim() == "") {
		return "Insert Hospital Units.";
	}

	
	return true;
}


