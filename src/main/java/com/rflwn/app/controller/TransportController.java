package com.rflwn.app.controller;

import com.rflwn.app.dto.TransportDto;
import com.rflwn.app.entity.Transport;
import com.rflwn.app.mapper.ModelDtoMapper;
import com.rflwn.app.service.TransportService;
import com.rflwn.app.utils.StateNumberCarValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/transports")
public class TransportController {
    private final ModelDtoMapper modelDtoMapper;
    private final TransportService transportService;

    @GetMapping
    public List<TransportDto> getAllTransportsInfo() {
        List<Transport> transportsInfo = transportService.getTransportInfo();
        return modelDtoMapper.modelListToDtoList(transportsInfo, TransportDto.class);
    }

    @GetMapping("{id}")
    public TransportDto getTransportInfoById(@PathVariable("id") int id) {
        Transport transportInfoModel = transportService.getTransportInfoById(id);
        return modelDtoMapper.toDto(transportInfoModel, TransportDto.class);
    }

    @PostMapping
    public List<TransportDto> addNewTransport(@RequestBody List<TransportDto> transportDto) {
        List<Transport> transportModels = modelDtoMapper.dtoListToModelList(transportDto, Transport.class);
        List<Transport> addedTransports = transportService.addTransport(transportModels);
        return modelDtoMapper.modelListToDtoList(addedTransports, TransportDto.class);
    }

    @PutMapping("{id}")
    public Transport changeTransportInfo(@PathVariable("id") Transport transportFromDb,
                                         @RequestBody TransportDto transportDto) {
        BeanUtils.copyProperties(transportDto, transportFromDb, "id");
        return transportService.changeTransportInfo(transportFromDb);
    }

    @DeleteMapping("{id}")
    public void deleteTransport(@PathVariable("id") int id) {
        transportService.deleteTransportFromPark(id);
    }
}
