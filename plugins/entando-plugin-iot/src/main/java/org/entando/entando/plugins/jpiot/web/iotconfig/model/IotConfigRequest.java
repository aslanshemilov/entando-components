/*
 *
 * <Your licensing text here>
 *
 */
package org.entando.entando.plugins.jpiot.web.iotconfig.model;


import javax.validation.constraints.*;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.entando.entando.web.common.json.JsonDateDeserializer;
import org.entando.entando.web.common.json.JsonDateSerializer;

public class IotConfigRequest {

    @NotNull(message = "iotConfig.id.notBlank")	
	private int id;

	@Size(max = 50, message = "string.size.invalid")
	@NotBlank(message = "iotConfig.name.notBlank")
	private String name;

	@Size(max = 50, message = "string.size.invalid")
	@NotBlank(message = "iotConfig.hostname.notBlank")
	private String hostname;

	private int port;

	@Size(max = 50, message = "string.size.invalid")
	@NotBlank(message = "iotConfig.webapp.notBlank")
	private String webapp;

	private String username;

	private String password;

	private String token;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	public String getWebapp() {
		return webapp;
	}
	public void setWebapp(String webapp) {
		this.webapp = webapp;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}


}
