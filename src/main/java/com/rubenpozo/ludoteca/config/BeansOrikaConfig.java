package com.rubenpozo.ludoteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.devonfw.module.beanmapping.common.base.BaseOrikaConfig;
import com.devonfw.module.json.common.base.ObjectMapperFactory;
import com.fasterxml.jackson.databind.Module;
import com.rubenpozo.ludoteca.config.mapper.BeanMapper;
import com.rubenpozo.ludoteca.config.mapper.BeanMapperImpl;

@Configuration
public class BeansOrikaConfig extends BaseOrikaConfig {

    @Override
    @Bean
    public BeanMapper getBeanMapper() {

        return new BeanMapperImpl();
    }

    @Bean
    public Module configureObjectMapper() {

        ObjectMapperFactory objectMapper = new ObjectMapperFactory();
        return objectMapper.getExtensionModule();

    }
}
