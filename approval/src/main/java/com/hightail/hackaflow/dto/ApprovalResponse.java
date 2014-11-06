package com.hightail.hackaflow.dto;

public class ApprovalResponse {
	private final String approver;
	private final String entityId;
	private final boolean approved;
	private final String comment;

	public ApprovalResponse() {
		super();
		this.entityId = null;
		this.approver = null;
		this.approved = false;
		this.comment = null;
	}

	public ApprovalResponse(String approver, String entityId, boolean approved, String comment) {
		super();
		this.approver = approver;
		this.entityId = entityId;
		this.approved = approved;
		this.comment = comment;
	}

	public String getApprover() {
		return approver;
	}

	public String getEntityId() {
		return entityId;
	}

	public boolean isApproved() {
		return approved;
	}

	public String getComment() {
		return comment;
	}

}
