package com.rflwn.app.controller;

import com.rflwn.app.dto.TicketDto;
import com.rflwn.app.entity.Ticket;
import com.rflwn.app.mapper.ModelDtoMapper;
import com.rflwn.app.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/tickets")
public class TicketController {
    private final ModelDtoMapper modelDtoMapper;
    private final TicketService ticketService;

    @GetMapping
    public List<TicketDto> getAllTickets() {
        List<Ticket> ticketModels = ticketService.getAllTicketsInfo();
        return modelDtoMapper.modelListToDtoList(ticketModels, TicketDto.class);
    }

    @GetMapping("{id}")
    public TicketDto getTicketById(@PathVariable("id") int id) {
        Ticket ticketInfoById = ticketService.getTicketById(id);
        return modelDtoMapper.toDto(ticketInfoById, TicketDto.class);
    }

    @PostMapping
    public List<TicketDto> addNewTickets(@RequestBody List<TicketDto> ticketDtoList) {
        List<Ticket> ticketModels = modelDtoMapper.dtoListToModelList(ticketDtoList, Ticket.class);
        List<Ticket> returnedTicketModels = ticketService.addNewTicketsList(ticketModels);
        return modelDtoMapper.modelListToDtoList(returnedTicketModels, TicketDto.class);
    }

    @PutMapping("{id}")
    public TicketDto changeOneTicketInfo(@PathVariable("id") Ticket ticketFromDb, @RequestBody TicketDto ticketDto) {
        Ticket ticketModel = modelDtoMapper.toModel(ticketDto, Ticket.class);
        Ticket returnedTicket = ticketService.changeOneTicketInfo(ticketFromDb, ticketModel);
        return modelDtoMapper.toDto(returnedTicket, TicketDto.class);
    }

    @PutMapping
    public List<TicketDto> changeTicketsInfo(@RequestBody List<TicketDto> newTicketDtoList) {
        List<Ticket> ticketsModelList = modelDtoMapper.dtoListToModelList(newTicketDtoList, Ticket.class);
        List<Ticket> returnedTickets = ticketService.changeAllTicketsInfo(ticketsModelList);
        return modelDtoMapper.modelListToDtoList(returnedTickets, TicketDto.class);
    }

    @DeleteMapping("{id}")
    public void deleteOneTicket(@PathVariable int id) {
        ticketService.deleteOneTicket(id);
    }

    @DeleteMapping
    public void deleteOneTicket() {
        ticketService.deleteAllTickets();
    }
}
