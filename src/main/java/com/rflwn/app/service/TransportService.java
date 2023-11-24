package com.rflwn.app.service;

import com.rflwn.app.entity.Transport;
import com.rflwn.app.repostiory.TransportRepository;
import com.rflwn.app.utils.StateNumberCarValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportService {
    private final StateNumberCarValidator stateNumberValidator;
    private final TransportRepository transportRepository;

    public List<Transport> getTransportInfo() {
        return transportRepository.findAll();
    }

    public Transport getTransportInfoById(int id) {
        return transportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException
                        (String.format("Информация о транспорте с id = %d не найдена", id)));
    }

    public List<Transport> addTransport(List<Transport> transportList) {
        ArrayListValuedHashMap<Boolean, Transport> transportListMap = new ArrayListValuedHashMap<>();

        for (Transport obj : transportList) {
            boolean isValid = stateNumberValidator.isValidNumber(obj.getStateNumber());
            transportListMap.put(isValid, obj);
        }

        if (transportListMap.containsKey(true)) {
            return transportRepository.saveAll(transportList);
        } else if (transportListMap.containsKey(false)) {
            List<Transport> allIncorrectModels = transportListMap.get(false);

            List<String> incorrectTransportInfo = allIncorrectModels.stream()
                    .map(Transport::getStateNumber)
                    .toList();

            if (!incorrectTransportInfo.isEmpty()) {
                StringBuilder errorMessage = new StringBuilder();

                if (incorrectTransportInfo.size() == 1) {
                    errorMessage.append("Неверный формат номера добавляемого авто - ")
                            .append(incorrectTransportInfo.get(0));
                } else {
                    errorMessage.append("Неверные форматы номеров добавляемых авто - ");
                    incorrectTransportInfo.forEach(value -> errorMessage.append(value).append(", "));
                    errorMessage.setLength(errorMessage.length() - 2);
                }
                throw new IllegalArgumentException(errorMessage.toString());
            }
        }
        return transportList;
    }

    public Transport changeTransportInfo(Transport transport) {
        if (stateNumberValidator.isValidNumber(transport.getStateNumber())) {
            transportRepository.save(transport);
        } else {
            throw new IllegalArgumentException("Неверный формат изменяемого номера - " + transport.getStateNumber());
        }
        return transport;
    }

    public void deleteTransportFromPark(int id) {
        Transport transportInfoById = getTransportInfoById(id);
        System.out.println(transportInfoById);
        if (transportInfoById != null) {
            transportRepository.deleteById(id);
        }
    }
}


































