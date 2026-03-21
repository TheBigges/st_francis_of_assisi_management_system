window.currentRecordId = null;


function editWeddingData(button) {

	document.getElementById("weddingSubmitButton").hidden = true;
	document.getElementById("weddingUpdateButton").hidden = false;
	try {

		const recordJson = button.getAttribute("data-record");
		const record = dtoStringToJson(recordJson);
		console.log(record);
		window.currentRecordId = record["coupleUuid"];
		openWeddingModalUpdate("PUT", record["coupleUuid"]);
	} catch (e) {
		document.getElementById("weddingSubmitButton").hidden = false;
		document.getElementById("weddingUpdateButton").hidden = true;
		console.error("Failed to parse data-record:", e);
	}
}

function openWeddingModalUpdate(newMethod, weddingId) {
	// Show modal
	document.getElementById('weddingModal').style.display = 'flex';

	// Fetch data for PUT if childId is provided
	if (newMethod === "PUT" && weddingId) {
		fetch(`/wedding/${encodeURIComponent(weddingId)}`)
			.then(response => {
				if (!response.ok) {
					throw new Error(`Failed to fetch record with ID ${weddingId}`);
				}
				return response.json();
			})
			.then(weddingRecords => {
				populateWeddingModalFields(weddingRecords);
			})
			.catch(error => {
				console.error("Error fetching baptism record:", error);
				alert("Failed to load baptism record.");
			});
	}
}

function openWeddingModal(newMethod, weddingRecords) {
	document.getElementById('weddingModal').style.display = 'flex';

	if (typeof weddingRecords === "string") {
		try {
			weddingRecords = JSON.parse(weddingRecords);
		} catch (e) {
			console.error("Invalid baptismRecords JSON:", weddingRecords);
			weddingRecords = {};
		}
	}

	// Reset fields for POST
	if (newMethod === "POST") {
		document.getElementById("weddingSubmitButton").hidden = false;
		document.getElementById("weddingUpdateButton").hidden = true;
		form.reset();
		return;
	}
}

function populateWeddingModalFields(weddingRecord) {

	const fields = [
		"groomFirstName",
		"groomMiddleName",
		"groomLastName",
		"groomDateOfBirth",
		"groomReligion",
		"brideFirstName",
		"brideMiddleName",
		"brideLastName",
		"brideDateOfBirth",
		"brideReligion",
		"dateOfWedding",
		"witnesses",
		"priestInCharge",
		"bookNo",
		"pageNo",
		"lineNo"
	];

	fields.forEach(field => {
		const el = document.getElementById(field);
		if (el) {
			el.value = weddingRecord[field] ?? "";
		}
	});
}


function closeWeddingModal() {
	document.getElementById('weddingModal').style.display = 'none';
	document.getElementById("weddingSubmitButton").hidden = false;
	document.getElementById("weddingUpdateButton").hidden = true;
}

//Update record
document.addEventListener('DOMContentLoaded', function() {

	const fields = [
		"groomFirstName",
		"groomMiddleName",
		"groomLastName",
		"groomDateOfBirth",
		"groomReligion",
		"brideFirstName",
		"brideMiddleName",
		"brideLastName",
		"brideDateOfBirth",
		"brideReligion",
		"dateOfWedding",
		"witnesses",
		"priestInCharge",
		"bookNo",
		"pageNo",
		"lineNo"
	];

	document.getElementById('weddingUpdateButton').addEventListener('click', function(e) {
		e.preventDefault();
		const payload = {};

		fields.forEach(function(fieldId) {
			const element = document.getElementById(fieldId);

			if (!element) {
				return;
			}

			if (element.type === 'checkbox') {
				payload[fieldId] = element.checked;
			} else {
				payload[fieldId] = element.value?.trim() || null;
			}
		});

		fetch('/wedding/update/' + window.currentRecordId, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(payload)
		})
			.then(response => {
				if (!response.ok) {
					throw new Error('Request failed with status ' + response.status);
				}
				return response.json();
			})
			.then(data => {
				console.log('Update successful:', data);
				alert('Record updated successfully.');
				window.location.reload();
			})
			.catch(error => {
				console.error('Error updating record:', error);
				alert('Failed to update record.');
			});
	});
});

function dtoStringToJson(dtoStr) {
	// Remove class name and brackets
	const content = dtoStr
		.replace(/^.*\[/, "")   // remove "ClassName ["
		.replace(/\]$/, "");    // remove trailing "]"

	const result = {};
	const pairs = content.split(", ");

	for (const pair of pairs) {
		const [key, value] = pair.split("=");
		result[key.trim()] = value === undefined ? null : value.trim();
	}

	return result;
}

document.addEventListener('DOMContentLoaded', function() {
	const btnClear = document.getElementById('btnClear');

	if (btnClear) {
		btnClear.addEventListener('click', function(e) {
			e.preventDefault();
			window.location.href = '/wedding'; // change to your target URL
		});
	}
});

function printWeddingDocument(button){
document.getElementById("loadingModal").style.display = "block";

	try {
		const recordJson = button.getAttribute("data-record");
		const record = dtoStringToJson(recordJson);
		fetchCoupleData(record["coupleUuid"], record["groomFirstName"], record["brideFirstName"]);
	} catch (e) {
		document.getElementById("loadingModal").style.display = "none";
		console.error("Failed to parse data-record:", e);
	}	
}

function fetchCoupleData(coupleUuid, groomFirstName, brideLastName){
	fetch(`/wedding/download-pdf/${encodeURIComponent(coupleUuid)}`)
		.then(response => {
			if (!response.ok) {
				throw new Error("PDF download failed");
			}
			return response.blob();
		})
		.then(blob => {
			const url = window.URL.createObjectURL(blob);

			const a = document.createElement("a");
			document.getElementById("loadingModal").style.display = "none";
			a.href = url;
			a.download = `${groomFirstName}_X_${brideLastName}-${coupleUuid}.pdf`;
			document.body.appendChild(a);
			a.click();

			document.body.removeChild(a);
			window.URL.revokeObjectURL(url);
		})
		.catch(error => {
			document.getElementById("loadingModal").style.display = "none";
			console.error("Error downloading PDF:", error);
		});
}
