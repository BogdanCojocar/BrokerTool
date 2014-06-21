package com.brokerTool.service;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {

	public enum Status {
		OK, NOT_OK
	};

	private Status status;
	private String additional_info;

	Response() {
		status = Status.OK;
		additional_info = "";
	}
	
	Response(Status status, String info) {
		this.status = status;
		this.additional_info = info;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getAdditionalInfo() {
		return additional_info;
	}

	public void setAdditionalInfo(String info) {
		this.additional_info = info;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", additional_info="
				+ additional_info + "]";
	}
}
