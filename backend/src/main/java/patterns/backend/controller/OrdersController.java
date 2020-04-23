package patterns.backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import patterns.backend.domain.Orders;
import patterns.backend.dto.OrdersDTO;
import patterns.backend.services.OrdersService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrdersController {

    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private OrdersService ordersService;

    @GetMapping()
    public List<OrdersDTO> getOrderss() {
        List<Orders> orders = ordersService.findAll();
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public void getOrders(@PathVariable final long id) {
        ordersService.findOrdersById(id);
    }

    @PutMapping()
    public void addOrders(@RequestBody Orders orders) {
        ordersService.saveOrders(orders);
    }

    @DeleteMapping("{id}")
    public void deleteOrders(@PathVariable final long id) {
        ordersService.deleteOrdersById(id);
    }

    private OrdersDTO convertToDto(Orders orders) {
        OrdersDTO ordersDTO = modelMapper.map(orders, OrdersDTO.class);
        return ordersDTO;
    }

}
