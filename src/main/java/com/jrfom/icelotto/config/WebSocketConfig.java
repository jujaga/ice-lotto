package com.jrfom.icelotto.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
  @Override
  public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
    stompEndpointRegistry.addEndpoint("/app").withSockJS();
    stompEndpointRegistry.addEndpoint("/admin").withSockJS();
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration channelRegistration) {

  }

  @Override
  public void configureClientOutboundChannel(ChannelRegistration channelRegistration) {

  }

  @Override
  public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
    messageConverters.add(new MappingJackson2MessageConverter());
    return messageConverters.size() > 0;
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
    messageBrokerRegistry.enableSimpleBroker("/topic");
    messageBrokerRegistry.setApplicationDestinationPrefixes("/ws");
  }
}