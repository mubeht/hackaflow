package com.hightail.hackaflow.approval;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.hightail.hackaflow.dto.ApprovalRequest;
import com.hightail.hackaflow.dto.ApprovalResponse;
import com.hightail.hackaflow.dto.ApproverInfo;

@Path("/approval")
@Produces("application/json")
@Consumes("application/json")
public class ApprovalResource {

	public ApprovalResource() {
		//
	}

	/**
	 * create an association between the entity id and approver emails
	 * @param approvers
	 * @return
	 */
	@POST
	@Path("/entity")
	public Response setApprovers(ApproverInfo approvers) {
		
		log("associating %s with %s", approvers.getEntityId(), Arrays.toString(approvers.getEmails().toArray()));
		URI uri;
		try {
			uri = new URI("/approval/entity/"+approvers.getEntityId());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		return Response.ok().contentLocation(uri).build();
	}
	
	/**
	 * retrieve the approver email information for a given entity id
	 * @param id
	 * @return
	 */
	@GET
	@Path("/entity/{id}")
	public Response getApprovers(@PathParam("id") String id) {
		ApproverInfo ai = new ApproverInfo(id, Collections.EMPTY_LIST);
		log("retrieved approvers for entity %s", id, Arrays.toString(ai.getEmails().toArray()));
		return Response.ok(ai).build();
	}
	
	/*
	 * submit an approval request for entity id
	 */
	@POST
	@Path("/request")
	public Response submit(ApprovalRequest request) {
		UUID requestId = UUID.randomUUID();
		URI uri;
		try {
			uri = new URI("/request/" + requestId);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		log("submitted approval request for entity %s: proposal [%s]", request.getEntityId(), request.getProposal());
		return Response.ok().contentLocation(uri).build();
	}

	@POST
	@Path("/request/{id}")
	public Response approve(@PathParam("id") String id, ApprovalResponse response) {
		UUID responseId = UUID.randomUUID();
		URI uri;
		try {
			uri = new URI("/request/"+ id + "/" + responseId);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		log("received approval for entity %s, request id %s, approver %s, approved %s, comment [%s]", response.getEntityId(), id, response.getApprover(), response.isApproved(), response.getComment() );
		return Response.ok().build();
	}

	@GET
	@Path("request/{id}")
	public Response status(@PathParam("id") String id) {
		return Response.ok(String.format("approval status for %s: pending!", id)).build();
	}

	private void log(Object obj) {
		System.out.println(obj);
	}

	private void log(String format, Object... args) {
		System.out.println(String.format(format, args));
	}
}
