package com.rubenpozo.ludoteca.config.mapper;

import org.springframework.data.domain.Page;

public interface BeanMapper extends com.devonfw.module.beanmapping.common.api.BeanMapper {

    <T> Page<T> mapPage(Page<?> source, Class<T> targetClass);

}
