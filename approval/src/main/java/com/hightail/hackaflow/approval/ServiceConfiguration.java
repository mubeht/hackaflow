package com.hightail.hackaflow.approval;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import redis.clients.jedis.Protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;


public class ServiceConfiguration extends Configuration {

	@NotEmpty
	@JsonProperty
	private String redisHost = "localhost";

	@NotNull
	@JsonProperty
	private Integer redisPort = Protocol.DEFAULT_PORT;

	public String getRedisHost() {
		return redisHost;
	}

	public Integer getRedisPort() {
		return redisPort;
	}

	
	
}
