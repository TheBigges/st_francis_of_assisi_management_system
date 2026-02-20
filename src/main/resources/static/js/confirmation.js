window.currentRecordId = null;

function navigate(tab) {
	window.location.href = '/' + tab;
}

function editConfirmation(button) {

	document.getElementById("confirmationSubmitButton").hidden = true;
	document.getElementById("confirmationUpdateButton").hidden = false;
	try {

		const recordJson = button.getAttribute("data-record");
		const record = dtoStringToJson(recordJson);
		console.log(record);
		window.currentRecordId = record["recordUuid"];
		opeConfirmationModalUpdate("PUT", record["recordUuid"]);
	} catch (e) {
		document.getElementById("confirmationSubmitButton").hidden = false;
		document.getElementById("confirmationUpdateButton").hidden = true;
		console.error("Failed to parse data-record:", e);
	}
}

function opeConfirmationModalUpdate(newMethod, weddingId) {
	// Show modal
	document.getElementById('confirmationModal').style.display = 'flex';

	// Fetch data for PUT if childId is provided
	if (newMethod === "PUT" && weddingId) {
		fetch(`/confirmation/${encodeURIComponent(weddingId)}`)
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

function openConfirmationModal(newMethod, weddingRecords) {
	document.getElementById('confirmationModal').style.display = 'flex';

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
		document.getElementById("confirmationSubmitButton").hidden = false;
		document.getElementById("confirmationUpdateButton").hidden = true;
		form.reset();
		return;
	}
}

function populateWeddingModalFields(weddingRecord) {

	const fields = [
		"firstName",
		"middleName",
		"lastName",
		"baptismDate",
		"confirmationDate",
		"fathersFullName",
		"mothersFullName",
		"sponsor",
		"presider",
		"churchLocation",
		"placeOfConfirmation",
		"dioces",
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


function closeConfirmationModal() {
	document.getElementById('confirmationModal').style.display = 'none';
	document.getElementById("confirmationSubmitButton").hidden = false;
	document.getElementById("confirmationUpdateButton").hidden = true;
}

//Update record
document.addEventListener('DOMContentLoaded', function() {

	const fields = [
		"firstName",
		"middleName",
		"lastName",
		"baptismDate",
		"confirmationDate",
		"fathersFullName",
		"mothersFullName",
		"sponsor",
		"presider",
		"churchLocation",
		"placeOfConfirmation",
		"dioces",
		"priestInCharge",
		"bookNo",
		"pageNo",
		"lineNo"
	];

	document.getElementById('confirmationUpdateButton').addEventListener('click', function(e) {
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

		fetch('/confirmation/update/' + window.currentRecordId, {
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
			window.location.href = '/confirmation'; // change to your target URL
		});
	}
});

function printConfirmationDocument(button) {
	document.getElementById("loadingModal").style.display = "block";

	try {
		const recordJson = button.getAttribute("data-record");
		const record = dtoStringToJson(recordJson);
		fetchConfirmationData(record["recordUuid"], record["firstName"], record["lastNmae"]);
	} catch (e) {
		document.getElementById("loadingModal").style.display = "none";
		console.error("Failed to parse data-record:", e);
	}
}

function fetchConfirmationData(recordUuid, firstName, lastName) {
	fetch(`/confirmation/download-pdf/${encodeURIComponent(recordUuid)}`)
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
			a.download = `${firstName}_X_${lastName}-${recordUuid}.pdf`;
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
