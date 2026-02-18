document.addEventListener('DOMContentLoaded', function() {

	const tableBody = document.getElementById('recordTable');
	const rows = Array.from(tableBody.querySelectorAll('tr'));

	const pageSizeSelect = document.getElementById('pageSize');
	const prevBtn = document.getElementById('prevPage');
	const nextBtn = document.getElementById('nextPage');
	const pageInfo = document.getElementById('pageInfo');

	let currentPage = 1;
	let pageSize = parseInt(pageSizeSelect.value);

	function renderTable() {
		const start = (currentPage - 1) * pageSize;
		const end = start + pageSize;

		rows.forEach((row, index) => {
			row.style.display = (index >= start && index < end)
				? ''
				: 'none';
		});

		const totalPages = Math.ceil(rows.length / pageSize);
		pageInfo.textContent = `Page ${currentPage} of ${totalPages}`;

		prevBtn.disabled = currentPage === 1;
		nextBtn.disabled = currentPage === totalPages;
	}

	pageSizeSelect.addEventListener('change', function() {
		pageSize = parseInt(this.value);
		currentPage = 1;
		renderTable();
	});

	prevBtn.addEventListener('click', function() {
		if (currentPage > 1) {
			currentPage--;
			renderTable();
		}
	});

	nextBtn.addEventListener('click', function() {
		const totalPages = Math.ceil(rows.length / pageSize);
		if (currentPage < totalPages) {
			currentPage++;
			renderTable();
		}
	});

	// Initial render
	renderTable();
});