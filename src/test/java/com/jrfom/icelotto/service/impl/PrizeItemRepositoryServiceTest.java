package com.jrfom.icelotto.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.icelotto.exception.PrizeItemNotFoundException;
import com.jrfom.icelotto.model.PrizeItem;
import com.jrfom.icelotto.repository.PrizeItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
  ContextConfig.class
})
public class PrizeItemRepositoryServiceTest {
  private static final Logger log = LoggerFactory.getLogger(PrizeItemRepositoryServiceTest.class);

  @Autowired
  private PrizeItemRepository prizeItemRepository;

  @Autowired
  private PrizeItemRepositoryService prizeItemRepositoryService;

  private PrizeItem prizeItem;

  private final Long ITEM_ID = Long.valueOf(2495);
  private final String ITEM_NAME = "Foo";
  private final String ITEM_DESCRIPTION = "Bar";

  @Before
  public void setup() {
    reset(this.prizeItemRepository); // Since we use Spring to autowire it
    this.prizeItem = new PrizeItem();
    this.prizeItem.setId(this.ITEM_ID);
    this.prizeItem.setName(this.ITEM_NAME);
    this.prizeItem.setDescription(this.ITEM_DESCRIPTION);
  }

  @Test
  public void create() {
    log.info("Running PrizeItemRepositoryService.create() test");

    // Setup the "database"
    PrizeItem persisted = new PrizeItem(this.ITEM_ID, this.ITEM_NAME, this.ITEM_DESCRIPTION);
    when(this.prizeItemRepository.save(any(PrizeItem.class))).thenReturn(persisted);

    Optional<PrizeItem> result = this.prizeItemRepositoryService.create(
      this.ITEM_ID,
      this.ITEM_NAME,
      this.ITEM_DESCRIPTION
    );
    assertTrue(result.isPresent());

    PrizeItem returned = result.get();

    // Now we get a copy of the argument that was passed to the JPA save method
    ArgumentCaptor<PrizeItem> prizeItemArgumentCaptor = ArgumentCaptor.forClass(PrizeItem.class);
    verify(this.prizeItemRepository, times(1)).save(prizeItemArgumentCaptor.capture());
    verifyNoMoreInteractions(this.prizeItemRepository);

    // And then verify that it matches what we gave the create method
    assertEquals(this.prizeItem.getId(), prizeItemArgumentCaptor.getValue().getId());
    assertEquals(this.prizeItem.getName(), prizeItemArgumentCaptor.getValue().getName());
    assertEquals(this.prizeItem.getDescription(), prizeItemArgumentCaptor.getValue().getDescription());

    assertEquals(persisted, returned);
  }

  @Test(expected = NullPointerException.class)
  public void createWithNullId() {
    log.info("Running PrizeItemRepositoryService.create() with null id test");
    PrizeItem persisted = new PrizeItem(this.ITEM_ID, this.ITEM_NAME, this.ITEM_DESCRIPTION);
    when(this.prizeItemRepository.save(any(PrizeItem.class))).thenReturn(persisted);

    Optional<PrizeItem> result = this.prizeItemRepositoryService.create(
      null,
      this.ITEM_NAME,
      this.ITEM_DESCRIPTION
    );
    assertFalse(result.isPresent());
  }

  @Test
  public void delete() throws PrizeItemNotFoundException {
    log.info("Running PrizeItemRepositoryService.delete() success test");
    PrizeItem deleted = new PrizeItem(this.ITEM_ID, this.ITEM_NAME, this.ITEM_DESCRIPTION);
    when(this.prizeItemRepository.findOne(this.ITEM_ID)).thenReturn(deleted);

    this.prizeItemRepositoryService.delete(this.ITEM_ID);
    verify(this.prizeItemRepository).findOne(this.ITEM_ID);
    verify(this.prizeItemRepository).delete(this.ITEM_ID);
    verifyNoMoreInteractions(this.prizeItemRepository);
  }

  @Test(expected = PrizeItemNotFoundException.class)
  public void deletePrizeItemNotFound() throws PrizeItemNotFoundException {
    when(this.prizeItemRepository.findOne(this.ITEM_ID)).thenReturn(null);

    this.prizeItemRepositoryService.delete(this.ITEM_ID);

    verify(this.prizeItemRepository).findOne(this.ITEM_ID);
    verifyNoMoreInteractions(this.prizeItemRepository);
  }

  @Test
  public void findAll() {
    List<PrizeItem> prizeItems = new ArrayList<>();
    when(this.prizeItemRepository.findAll()).thenReturn(prizeItems);

    List<PrizeItem> returned = this.prizeItemRepositoryService.findAll();

    verify(this.prizeItemRepository).findAll();
    verifyNoMoreInteractions(this.prizeItemRepository);

    assertEquals(prizeItems, returned);
  }

  @Test
  public void findById() {
    PrizeItem prizeItem = new PrizeItem(this.ITEM_ID, this.ITEM_NAME, this.ITEM_DESCRIPTION);
    when(this.prizeItemRepository.findOne(this.ITEM_ID)).thenReturn(prizeItem);

    Optional<PrizeItem> result = this.prizeItemRepositoryService.findById(this.ITEM_ID);

    verify(this.prizeItemRepository).findOne(this.ITEM_ID);
    verifyNoMoreInteractions(this.prizeItemRepository);

    assertTrue(result.isPresent());
    PrizeItem returned = result.get();

    assertEquals(prizeItem, returned);
  }
}

@Configuration
class ContextConfig {
  @Bean
  public PrizeItemRepository prizeItemRepository() {
    PrizeItemRepository repository = mock(PrizeItemRepository.class);
    return repository;
  }

  @Bean
  public PrizeItemRepositoryService prizeItemRepositoryService() {
    return new PrizeItemRepositoryService();
  }
}