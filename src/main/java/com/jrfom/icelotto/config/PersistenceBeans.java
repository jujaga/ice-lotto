package com.jrfom.icelotto.config;

import com.jrfom.icelotto.service.PrizeItemService;
import com.jrfom.icelotto.service.impl.PrizeItemRepositoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceBeans {
  @Bean
  public PrizeItemService prizeItemService() {
    return new PrizeItemRepositoryService();
  }
}