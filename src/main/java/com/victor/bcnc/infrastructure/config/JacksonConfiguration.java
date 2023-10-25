package com.victor.bcnc.infrastructure.config;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class JacksonConfiguration {

	/**
	 * JSON object mapper configuration
	 * 
	 * @return Jackson2ObjectMapperBuilderCustomizer
	 */
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

		return builder -> {

			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));

			builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
		};
	}
}
