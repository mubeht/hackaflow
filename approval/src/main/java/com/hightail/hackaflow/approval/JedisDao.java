package com.hightail.hackaflow.approval;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPool;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JedisDao {

	private final JedisPool jedisPool;
	private static final ObjectMapper JSON_OBJECTMAPPER = new ObjectMapper();
    private final static Logger LOGGER = LoggerFactory.getLogger(JedisDao.class);

	public JedisDao(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public void save(String schema, String name, Object value) {
		try {
			String key = schema + "|" + name;
			final String json = JSON_OBJECTMAPPER.writeValueAsString(value);
			jedisPool.getResource().set(key, json);
			LOGGER.info("saved [{}, {}]", key, json);
		} catch (final Exception e) {
            LOGGER.error("Error saving to redis. {}", e.getMessage());

		}
	}

	public <T> T get(String schema, String name, Class<T> clazz ) {
		try {
			String key = schema + "|" + name;
			String json = jedisPool.getResource().get(key);
			T value = JSON_OBJECTMAPPER.readValue(json, clazz);
			LOGGER.info("retrieved [{}, {}]", key, value);
			return value;
		} catch (final Exception e) {
            LOGGER.error("Error saving to redis. {}", e.getMessage());
            throw new RuntimeException(e);
		}
	}

	
}
