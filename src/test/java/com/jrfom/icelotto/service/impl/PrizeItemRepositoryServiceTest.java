package com.jrfom.icelotto.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.icelotto.dto.PrizeItemDto;
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

  private PrizeItemDto prizeItemDto;

  private final Long ITEM_ID = Long.valueOf(2495);
  private final String ITEM_NAME = "Foo";
  private final String ITEM_DESCRIPTION = "Bar";

  @Before
  public void setup() {
    reset(this.prizeItemRepository); // Since we use Spring to autowire it
    this.prizeItemDto = new PrizeItemDto();
    this.prizeItemDto.setId(this.ITEM_ID);
    this.prizeItemDto.setName(this.ITEM_NAME);
    this.prizeItemDto.setDescription(this.ITEM_DESCRIPTION);
  }

  @Test
  public void create() {
    log.info("Running PrizeItemRepositoryService.create() test");

    // Setup the "database"
    PrizeItem persisted = new PrizeItem(this.ITEM_ID, this.ITEM_NAME, this.ITEM_DESCRIPTION);
    when(this.prizeItemRepository.save(any(PrizeItem.class))).thenReturn(persisted);

    Optional<PrizeItem> result = this.prizeItemRepositoryService.create(this.prizeItemDto);
    assertTrue(result.isPresent());

    PrizeItem returned = result.get();

    // Now we get a copy of the argument that was passed to the JPA save method
    ArgumentCaptor<PrizeItem> prizeItemArgumentCaptor = ArgumentCaptor.forClass(PrizeItem.class);
    verify(this.prizeItemRepository, times(1)).save(prizeItemArgumentCaptor.capture());
    verifyNoMoreInteractions(this.prizeItemRepository);

    // And then verify that it matches what we gave the create method
    assertEquals(this.prizeItemDto.getId(), prizeItemArgumentCaptor.getValue().getId());
    assertEquals(this.prizeItemDto.getName(), prizeItemArgumentCaptor.getValue().getName());
    assertEquals(this.prizeItemDto.getDescription(), prizeItemArgumentCaptor.getValue().getDescription());

    assertEquals(persisted, returned);
  }

  @Test(expected = NullPointerException.class)
  public void createWithNullId() {
    log.info("Running PrizeItemRepositoryService.create() with null id test");
    PrizeItem persisted = new PrizeItem(this.ITEM_ID, this.ITEM_NAME, this.ITEM_DESCRIPTION);
    when(this.prizeItemRepository.save(any(PrizeItem.class))).thenReturn(persisted);

    PrizeItemDto newRecord = new PrizeItemDto();

    Optional<PrizeItem> result = this.prizeItemRepositoryService.create(newRecord);
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

    assertEquals(prizeItem, prizeItem);
  }

  @Test
  public void update() throws PrizeItemNotFoundException {
    PrizeItem prizeItem = new PrizeItem(this.ITEM_ID, this.ITEM_NAME, this.ITEM_DESCRIPTION);

    when(this.prizeItemRepository.findOne(this.ITEM_ID)).thenReturn(prizeItem);

    Optional<PrizeItem> result = this.prizeItemRepositoryService.update(this.prizeItemDto);

    verify(this.prizeItemRepository).findOne(this.ITEM_ID);
    verifyNoMoreInteractions(this.prizeItemRepository);

    assertTrue(result.isPresent());
    PrizeItem returned = result.get();

    assertEquals(this.prizeItemDto.getId(), returned.getId());
    assertEquals(this.prizeItemDto.getName(), returned.getName());
    assertEquals(this.prizeItemDto.getDescription(), returned.getDescription());
  }

  @Test(expected = PrizeItemNotFoundException.class)
  public void updateWhenNotFound() throws PrizeItemNotFoundException {
    when(this.prizeItemRepository.findOne(this.ITEM_ID)).thenReturn(null);
    this.prizeItemRepositoryService.update(this.prizeItemDto);

    verify(this.prizeItemRepository).findOne(this.prizeItemDto.getId());
    verifyNoMoreInteractions(this.prizeItemRepository);
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