package com.bookstore.order_service.service;

import com.bookstore.order_service.dataModel.Order;
import com.bookstore.order_service.dataModel.OrderStatus;
import com.bookstore.order_service.dto.Book;
import com.bookstore.order_service.dto.Customer;
import com.bookstore.order_service.exceptions.InsufficientStockException;
import com.bookstore.order_service.exceptions.OrderNotFoundException;
import com.bookstore.order_service.feignClients.BookServiceClient;
import com.bookstore.order_service.feignClients.CustomerServiceClient;
import com.bookstore.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{


    private final OrderRepository orderRepository;
    private final BookServiceClient bookServiceClient;
    private final CustomerServiceClient customerServiceClient;

    public OrderServiceImpl(OrderRepository orderRepository, BookServiceClient bookServiceClient, CustomerServiceClient customerServiceClient) {
        this.orderRepository = orderRepository;
        this.bookServiceClient = bookServiceClient;
        this.customerServiceClient = customerServiceClient;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
    }

    public Order createOrder(Order order) {
        validateOrder(order);
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));

        validateOrder(orderDetails);

        existingOrder.setCustomerId(orderDetails.getCustomerId());
        existingOrder.setBookId(orderDetails.getBookId());
        existingOrder.setQuantity(orderDetails.getQuantity());
        existingOrder.setStatus(orderDetails.getStatus());

        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
        orderRepository.delete(order);
    }

    private void validateOrder(Order order) {
        // Validate customer
        Customer customer = customerServiceClient.getCustomerById(order.getCustomerId());
        if (customer == null) {
            throw new OrderNotFoundException("Customer not found with id " + order.getCustomerId());
        }

        // Validate book and stock
        Book book = bookServiceClient.getBookById(order.getBookId());
        if (book == null) {
            throw new OrderNotFoundException("Book not found with id " + order.getBookId());
        }

        if (book.getStock() < order.getQuantity()) {
            throw new InsufficientStockException("Not enough stock available for book id " + order.getBookId());
        }
    }
}
