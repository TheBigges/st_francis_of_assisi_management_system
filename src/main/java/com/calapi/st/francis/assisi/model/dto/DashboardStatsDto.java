package com.calapi.st.francis.assisi.model.dto;

public class DashboardStatsDto {

	private long baptismCount;
	private long weddingCount;
	private long confirmationCount;
	private long funeralCount;

	// constructor
	public DashboardStatsDto(long baptismCount, long weddingCount, long confirmationCount, long funeralCount) {
		this.baptismCount = baptismCount;
		this.weddingCount = weddingCount;
		this.confirmationCount = confirmationCount;
		this.funeralCount = funeralCount;
	}

	public long getBaptismCount() {
		return baptismCount;
	}

	public long getWeddingCount() {
		return weddingCount;
	}

	public long getConfirmationCount() {
		return confirmationCount;
	}

	public long getFuneralCount() {
		return funeralCount;
	}

	public synchronized void setBaptismCount(long baptismCount) {
		this.baptismCount = baptismCount;
	}

	public synchronized void setWeddingCount(long weddingCount) {
		this.weddingCount = weddingCount;
	}

	public synchronized void setConfirmationCount(long confirmationCount) {
		this.confirmationCount = confirmationCount;
	}

	public synchronized void setFuneralCount(long funeralCount) {
		this.funeralCount = funeralCount;
	}
	
}
