package com.jrfom.icelotto.config;

import com.jrfom.icelotto.service.DrawingService;
import com.jrfom.icelotto.service.PrizeItemService;
import com.jrfom.icelotto.service.PrizePoolService;
import com.jrfom.icelotto.service.impl.DrawingRepositoryService;
import com.jrfom.icelotto.service.impl.PrizeItemRepositoryService;
import com.jrfom.icelotto.service.impl.PrizePoolRepositoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceBeans {
  @Bean
  public DrawingService drawingService() {
    return new DrawingRepositoryService();
  }

  @Bean
  public PrizeItemService prizeItemService() {
    return new PrizeItemRepositoryService();
  }

  @Bean
  public PrizePoolService prizePoolService() {
    return new PrizePoolRepositoryService();
  }
}