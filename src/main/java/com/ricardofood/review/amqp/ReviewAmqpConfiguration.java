package com.ricardofood.review.amqp;

import com.ricardofood.review.constants.PaymentExchangeName;
import com.ricardofood.review.constants.PaymentQueueName;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewAmqpConfiguration {

    @Bean
    public Jackson2JsonMessageConverter createJackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue reviewInfoQueue() {
        return  QueueBuilder
                .durable(
                        PaymentQueueName.PAYMENT_REVIEW_INFO
                )
                .deadLetterExchange(PaymentExchangeName.PAYMENT_EXCHANGE_DLQ)
                .build();
    }

    @Bean
    public Queue reviewInfoQueueDLQ() {
        return  QueueBuilder
                .durable(
                        PaymentQueueName.PAYMENT_REVIEW_INFO_DLQ
                ).build();
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange(PaymentExchangeName.PAYMENT_EXCHANGE)
                .build();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder
                .fanoutExchange(PaymentExchangeName.PAYMENT_EXCHANGE_DLQ)
                .build();
    }

    @Bean
    public Binding OrderPaymentBinding() {
        return BindingBuilder
                .bind(reviewInfoQueue())
                .to(fanoutExchange());
    }

    @Bean
    public Binding OrderPaymentBindingDLQ() {
        return BindingBuilder
                .bind(reviewInfoQueueDLQ())
                .to(deadLetterExchange());
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> startRabbitAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
