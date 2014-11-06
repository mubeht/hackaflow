package com.hightail.hackaflow.approval;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * see tutorial from:
 * https://blog.openshift.com/day-13-dropwizard-the-awesome-java-rest-server-stack/
 * @author ian.li
 *
 */
public class ApprovalService extends Service<ServiceConfiguration> {

	public static void main(String[] args) throws Exception {
		new ApprovalService().run(new String[] { "server" });
	}

	@Override
	public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
		bootstrap.setName("approval");
	}

	@Override
	public void run(ServiceConfiguration configuration, Environment environment) throws Exception {
		environment.addResource(new ApprovalResource(configuration));
	}
}
