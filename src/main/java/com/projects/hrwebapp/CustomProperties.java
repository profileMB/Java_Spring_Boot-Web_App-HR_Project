package com.projects.hrwebapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix="com.projects.hrwebapp")
public class CustomProperties {
	
	private String apiUrl;

}