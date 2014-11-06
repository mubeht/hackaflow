package com.hightail.hackaflow.approval;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPool;

import com.hightail.hackaflow.dto.ApprovalRequest;
import com.hightail.hackaflow.dto.ApprovalResponse;
import com.hightail.hackaflow.dto.ApproverInfo;


@Path("/approval")
@Produces("application/json")
@Consumes("application/json")
public class ApprovalResource {
    private final static Logger LOGGER = LoggerFactory.getLogger(ApprovalResource.class);
    private static final String ENTITY_APPROVERS_ASSOCIATION = "ENTITY_APPROVERS_ASSOCIATION";

    private final ServiceConfiguration configuration;
	private final JedisDao jedisDao;
	
	public ApprovalResource(ServiceConfiguration configuration) {
		this.configuration = configuration;
		JedisPool jedisPool = new JedisPool(configuration.getRedisHost(), configuration.getRedisPort());
		this.jedisDao = new JedisDao(jedisPool); 
	    log("Connection to redis server {}:{}", configuration.getRedisHost(), configuration.getRedisPort());
	    log("Server is running: {}", jedisPool.getResource().ping());
	}

	/**
	 * create an association between the entity id and approver emails
	 * @param approvers
	 * @return
	 */
	@POST
	@Path("/entity")
	public Response saveApprovers(ApproverInfo approvers) {
		jedisDao.save(ENTITY_APPROVERS_ASSOCIATION, approvers.getEntityId(), approvers.getEmails());        
		log("associating {} with {}", approvers.getEntityId(), Arrays.toString(approvers.getEmails().toArray()));
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
		List approvers = jedisDao.get(ENTITY_APPROVERS_ASSOCIATION, id, List.class);
		ApproverInfo ai = new ApproverInfo(id, approvers);
		log("retrieved approvers for entity {}", id, Arrays.toString(ai.getEmails().toArray()));
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
		log("submitted approval request for entity {}: proposal [{}]", request.getEntityId(), request.getProposal());
		return Response.ok().contentLocation(uri).build();
	}

	@POST
	@Path("/request/{id}")
	public Response approve(@PathParam("id") String id, ApprovalResponse response) {
		log("received approval for entity {}, request id {}, approver {}, approved {}, comment [{}]", response.getEntityId(), id, response.getApprover(), response.isApproved(), response.getComment() );
		return Response.ok().build();
	}

	@GET
	@Path("request/{id}")
	public Response status(@PathParam("id") String id) {
		return Response.ok(String.format("approval status for {}: pending!", id)).build();
	}

	private void log(String format, Object... args) {
		LOGGER.info(format, args);
	}
}
