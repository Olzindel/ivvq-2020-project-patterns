package patterns.backend.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patterns.backend.domain.Order;
import patterns.backend.domain.OrderItem;
import patterns.backend.domain.OrderStatus;
import patterns.backend.dto.OrderDto;
import patterns.backend.dto.OrderItemDto;
import patterns.backend.model.OrderForm;
import patterns.backend.services.OrderService;
import patterns.backend.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(value = "/order", produces = "application/json")
public class OrderController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orderDtos = orderService.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrders(@PathVariable("orderId") final Long orderId) {
        OrderDto orderDto = convertToDto(orderService.findOrdersById(orderId));
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<OrderDto> createOrders(@RequestBody OrderForm form) {
        List<OrderItemDto> formDtos = form.getOrders();

        Order order = new Order();
        order.setOrderStatus(OrderStatus.PAID);

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto dto : formDtos) {
            orderItems.add((new OrderItem(dto.getQuantity(), productService.findProductById(dto
                    .getProduct()
                    .getId()), order)));
        }

        order.setOrderItems(orderItems);
        OrderDto orderDto = convertToDto(orderService.create(order));

        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<OrderDto> updateOrder(@RequestBody Order order) {
        OrderDto orderDto = convertToDto(orderService.update(order));
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderDto> deleteOrders(@PathVariable("orderId") final Long orderId) {
        orderService.deleteOrdersById(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private OrderDto convertToDto(Order order) {
        OrderDto orderDTO = modelMapper.map(order, OrderDto.class);
        return orderDTO;
    }

}
