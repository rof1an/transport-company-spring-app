package com.rflwn.app.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelDtoMapper {
    private final ModelMapper modelMapper;

    public ModelDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <A, B> B toDto(A entity, Class<B> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <A, B> A toModel(B dtoClass, Class<A> entity) {
        return modelMapper.map(dtoClass, entity);
    }

    public <A, B> List<A> modelListToDtoList(List<B> modelList, Class<A> dtoClass) {
        return modelList.stream()
                .map(model -> modelMapper.map(model, dtoClass))
                .collect(Collectors.toList());
    }

    public <A, B> List<B> dtoListToModelList(List<A> dtoList, Class<B> modelList) {
        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, modelList))
                .collect(Collectors.toList());
    }
}
