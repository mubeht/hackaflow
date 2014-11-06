package com.hightail.hackaflow.dto;

public class ApprovalRequest {
	private final String entityId;
	private final String proposal;

	public ApprovalRequest() {
		super();
		this.entityId = null;
		this.proposal = null;
	}
	
	public ApprovalRequest(String entityId, String proposal) {
		super();
		this.entityId = entityId;
		this.proposal = proposal;
	}

	public String getEntityId() {
		return entityId;
	}

	public String getProposal() {
		return proposal;
	}

}
