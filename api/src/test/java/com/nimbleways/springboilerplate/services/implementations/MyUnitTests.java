package com.nimbleways.springboilerplate.services.implementations;

import com.nimbleways.springboilerplate.entities.Order;
import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.entities.ProductType;
import com.nimbleways.springboilerplate.repositories.OrderRepository;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.utils.Annotations.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@UnitTest
public class MyUnitTests {

    @Mock
    private NotificationService notificationService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    public void test() {

        // I was focused more on refactoring, than the unitary tests
        // GIVEN

        Long orderId = 123L;
        Product product = new Product(null, 15, 0, ProductType.NORMAL, "RJ45 Cable", null, null);
        Order order = new Order(orderId, Set.of(product));

        Mockito.when(productRepository.save(product)).thenReturn(product);
        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // WHEN
        productService.processOrder(orderId);

        // THEN
        Mockito.verify(notificationService, Mockito.times(1)).sendDelayNotification(product.getLeadTime(), product.getName());
    }
}