window.currentRecordId = null;



function editFromData(button) {

	document.getElementById("submitButton").hidden = true;
	document.getElementById("updateButton").hidden = false;
	try {

		const recordJson = button.getAttribute("data-record");
		const record = dtoStringToJson(recordJson);
		console.log(record);
		window.currentRecordId = record["childUuid"];
		openModal2("PUT", record["childUuid"]);
	} catch (e) {
		document.getElementById("submitButton").hidden = false;
		document.getElementById("updateButton").hidden = true;
		console.error("Failed to parse data-record:", e);
	}
}



document.addEventListener('DOMContentLoaded', function() {
	const btnClear = document.getElementById('btnClear');

	if (btnClear) {
		btnClear.addEventListener('click', function(e) {
			e.preventDefault();
			window.location.href = '/baptism'; // change to your target URL
		});
	}
});

function updateJson(button, record) {
	// Retrieve sponsors from the button's data-* attribute
	const sponsorsStr = button.dataset.sponsors;

	console.log(sponsorsStr);
	// Optional: convert comma-separated string to array
	const sponsors = sponsorsStr ? sponsorsStr.split(",").map(s => s.trim()) : [];


	console.log(sponsors);
	// Update the record object
	record["sponsors"] = sponsors;

	console.log(record["sponsors"]);

	return record;
}

function printDocument(button) {
	document.getElementById("loadingModal").style.display = "block";

	try {
		const recordJson = button.getAttribute("data-record");
		const record = dtoStringToJson(recordJson);
		fetchChildDocument(record["childUuid"], record["lastName"], record["firstName"]);
	} catch (e) {
		document.getElementById("loadingModal").style.display = "none";
		console.error("Failed to parse data-record:", e);
	}

}

function openModal2(newMethod, childId) {
	console.log("--->" + newMethod);
	console.log(childId);
	// Show modal
	document.getElementById('baptismModal').style.display = 'flex';

	const form = document.getElementById("baptismForm");

	// Reset fields for POST
	if (newMethod === "POST") {
		form.reset();
		return;
	}

	// Fetch data for PUT if childId is provided
	if (newMethod === "PUT" && childId) {
		fetch(`/baptism/${encodeURIComponent(childId)}`)
			.then(response => {
				if (!response.ok) {
					throw new Error(`Failed to fetch record with ID ${childId}`);
				}
				return response.json();
			})
			.then(baptismRecords => {
				populateModalFields(baptismRecords);
			})
			.catch(error => {
				console.error("Error fetching baptism record:", error);
				alert("Failed to load baptism record.");
			});
	}
}

/**
 * Populates modal fields from a baptismRecords object
 * @param {Object} baptismRecords
 */
function populateModalFields(baptismRecords) {
	const fields = [
		"firstName",
		"middleName",
		"lastName",
		"dateOfBirth",
		"placeOfBirth",
		"dateOfBaptism",
		"placeOfBaptism",
		"ageCategory",
		"fatherFirstName",
		"fatherMiddleName",
		"fatherLastName",
		"motherFirstName",
		"motherMiddleName",
		"motherLastName",
		"parentsAddress",
		"sponsors",
		"minister",
		"priestInCharge",
		"bookNo",
		"pageNo",
		"lineNo"
	];

	fields.forEach(field => {
		const el = document.getElementById(field);
		if (el) el.value = baptismRecords[field] || "";
	});
}


function openModal1(newMethod, baptismRecords) {
	document.getElementById('baptismModal').style.display = 'flex';

	const form = document.getElementById("baptismForm");

	// If baptismRecords is a JSON string, convert it
	if (typeof baptismRecords === "string") {
		try {
			baptismRecords = JSON.parse(baptismRecords);
		} catch (e) {
			console.error("Invalid baptismRecords JSON:", baptismRecords);
			baptismRecords = {};
		}
	}

	// Reset fields for POST
	if (newMethod === "POST") {
		document.getElementById("submitButton").hidden = false;
		document.getElementById("updateButton").hidden = true;
		form.reset();
		return;
	}

	// Populate fields for PUT
	if (newMethod === "PUT" && baptismRecords) {
		const fields = [
			"firstName",
			"middleName",
			"lastName",
			"dateOfBirth",
			"placeOfBirth",
			"dateOfBaptism",
			"placeOfBaptism",
			"ageCategory",
			"fatherFirstName",
			"fatherMiddleName",
			"fatherLastName",
			"motherFirstName",
			"motherMiddleName",
			"motherLastName",
			"parentsAddress",
			"sponsors",
			"minister",
			"priestInCharge",
			"bookNo",
			"pageNo",
			"lineNo"
		];

		fields.forEach(field => {
			const el = document.getElementById(field);
			if (el) el.value = baptismRecords[field] || "";
		});

	}
}

function fetchChildDocument(childUuid, lastName, firstname) {
	fetch(`/baptism/download-pdf/${encodeURIComponent(childUuid)}`)
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
			a.download = `${lastName}_${firstname}-${childUuid}.pdf`;
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



function closeModal() {
	document.getElementById('baptismModal').style.display = 'none';
	document.getElementById("submitButton").hidden = false;
	document.getElementById("updateButton").hidden = true;
	clearFormFields();
}

window.onclick = function(event) {
	const modal = document.getElementById('baptismModal');
	if (event.target === modal) {
		closeModal();
	}
}

function clearFormFields() {
	const fields = [
		"firstName",
		"middleName",
		"lastName",
		"dateOfBirth",
		"placeOfBirth",
		"dateOfBaptism",
		"placeOfBaptism",
		"ageCategory",
		"fatherFirstName",
		"fatherMiddleName",
		"fatherLastName",
		"motherFirstName",
		"motherMiddleName",
		"motherLastName",
		"parentsAddress",
		"sponsors",
		"minister",
		"priestInCharge",
		"bookNo",
		"pageNo",
		"lineNo"
	];

	fields.forEach(function(fieldId) {
		const element = document.getElementById(fieldId);

		if (!element) {
			return;
		}

		if (element.tagName === 'SELECT') {
			element.selectedIndex = 0;
		} else if (element.type === 'checkbox' || element.type === 'radio') {
			element.checked = false;
		} else {
			element.value = '';
		}
	});
}

//Update record
document.addEventListener('DOMContentLoaded', function() {

	const fields = [
		"firstName",
		"middleName",
		"lastName",
		"dateOfBirth",
		"placeOfBirth",
		"dateOfBaptism",
		"placeOfBaptism",
		"ageCategory",
		"fatherFirstName",
		"fatherMiddleName",
		"fatherLastName",
		"motherFirstName",
		"motherMiddleName",
		"motherLastName",
		"parentsAddress",
		"sponsors",
		"minister",
		"priestInCharge",
		"bookNo",
		"pageNo",
		"lineNo"
	];

	document.getElementById('updateButton').addEventListener('click', function(e) {
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

		fetch('/baptism/update/' + window.currentRecordId, {
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

function navigate(tab) {
	window.location.href = '/' + tab;
}


