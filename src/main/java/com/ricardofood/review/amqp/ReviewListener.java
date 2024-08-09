package com.ricardofood.review.amqp;


import com.ricardofood.review.amqp.dto.PaymentDto;
import com.ricardofood.review.constants.PaymentQueueName;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReviewListener {

    @RabbitListener(queues = PaymentQueueName.PAYMENT_REVIEW_INFO)
    public void receiveMessage(PaymentDto paymentDto) {
        var message = """
            It is necessary to create a entry for the order with:
            Payment Id: %s
            Client Name: %s
            Amount: R$: %s
            Status: %s
        """.formatted(
                paymentDto.getId(),
                paymentDto.getName(),
                paymentDto.getAmount(),
                paymentDto.getStatus()
        );
        System.out.println("Received message: \n" + message);
    }
}
