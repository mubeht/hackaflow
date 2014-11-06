package com.hightail.hackaflow.dto;

import java.util.Collections;
import java.util.List;

public class ApproverInfo {
	private final String entityId;
	private final List<String> emails;

	public ApproverInfo() {
		super();
		this.entityId = null;
		this.emails = null;
	}
	
	public ApproverInfo(String entityId, List<String> emails) {
		super();
		this.entityId = entityId;
		this.emails = emails;
	}

	public List<String> getEmails() {
		return Collections.unmodifiableList(emails);
	}

	public String getEntityId() {
		return entityId;
	}
	
	
}
