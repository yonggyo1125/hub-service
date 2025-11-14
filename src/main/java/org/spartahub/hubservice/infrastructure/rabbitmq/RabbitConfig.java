package org.spartahub.hubservice.infrastructure.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
//@EnableConfigurationProperties(RabbitProductProperties.class)
public class RabbitConfig {
//    private final RabbitProductProperties productProperties;
//
//    @Bean
//    public TopicExchange productExchange() {
//        return new TopicExchange(productProperties.getExchange(), true, false);
//    }
//
//    @Bean
//    public Queue productQueue() {
//        return QueueBuilder.durable(productProperties.getQueue()).build();
//    }
//
//    @Bean
//    public Binding productBinding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(productProperties.getRoutingKey());
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
}
