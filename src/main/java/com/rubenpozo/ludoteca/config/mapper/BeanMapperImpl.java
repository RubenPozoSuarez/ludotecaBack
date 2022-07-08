package com.rubenpozo.ludoteca.config.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.devonfw.module.beanmapping.common.impl.orika.BeanMapperImplOrika;

public class BeanMapperImpl extends BeanMapperImplOrika implements BeanMapper {

    public <T> Page<T> mapPage(Page<?> source, Class<T> targetClass) {

        if (source == null) {
            return null;
        }

        List<T> list = mapList(source.getContent(), targetClass);

        return new PageImpl<>(list, source.getPageable(), source.getTotalElements());
    }
}
