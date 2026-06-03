package com.api.constant;

public enum ServiceLocation {

	SERVICE_LOCATION_A(1),
	SERVICE_LOCATION_B(2),// This location won't work select location 'A'
	SERVICE_LOCATION_C(3);// This location won't work select location 'A'
	
	
	int code;
	
	private ServiceLocation(int code) {
		this.code=code;
	}
	
	public int getCode() {
		return code;
	}
	
	
}
