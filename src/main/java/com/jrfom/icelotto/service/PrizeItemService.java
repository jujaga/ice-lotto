package com.jrfom.icelotto.service;

import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.icelotto.dto.PrizeItemDto;
import com.jrfom.icelotto.exception.PrizeItemNotFoundException;
import com.jrfom.icelotto.model.PrizeItem;

public interface PrizeItemService {
  /**
   * Create a new {@link com.jrfom.icelotto.model.PrizeItem} entry in the
   * database represented by a {@link com.jrfom.icelotto.dto.PrizeItemDto}.
   *
   * @param prizeItem An instance of {@link com.jrfom.icelotto.dto.PrizeItemDto}.
   *
   * @return An instance of {@link com.jrfom.icelotto.model.PrizeItem} wrapped
   * in an {@link com.google.common.base.Optional} or an empty {@code Optional}
   * if there was an error.
   */
  public Optional<PrizeItem> create(PrizeItemDto prizeItem);

  /**
   * Delete a {@link com.jrfom.icelotto.model.PrizeItem} from the database
   * given its identifier. If the item could not be found, an instance of
   * {@link com.jrfom.icelotto.exception.PrizeItemNotFoundException} will be
   * thrown.
   *
   * @param prizeItemId The identifier for the item to delete.
   *
   * @throws PrizeItemNotFoundException
   */
  public void delete(Long prizeItemId) throws PrizeItemNotFoundException;

  /**
   * Get a list of all {@link com.jrfom.icelotto.model.PrizeItem}s in the
   * database.
   *
   * @return An instance of {@link java.util.List} (empty if none were found).
   */
  public List<PrizeItem> findAll();

  /**
   * Retrieve a {@link com.jrfom.icelotto.model.PrizeItem} from the database
   * given its identifier.
   *
   * @param id The identifier of the item.
   *
   * @return An instance of {@link com.jrfom.icelotto.model.PrizeItem} wrapped
   * in an {@link com.google.common.base.Optional} or an empty {@code Optional}.
   */
  public Optional<PrizeItem> findById(Long id);

  /**
   * Updates a {@link com.jrfom.icelotto.model.PrizeItem} record with a given
   * {@link com.jrfom.icelotto.dto.PrizeItemDto}'s details (name and
   * description).
   *
   * @param prizeItem An instance of {@link com.jrfom.icelotto.dto.PrizeItemDto}
   *                  with the new details. Both {@code name} and
   *                  {@code description} properties will be used in the update.
   *
   * @return An instance of {@link com.jrfom.icelotto.model.PrizeItem},
   * representing the new record, wrapped in an {@link com.google.common.base.Optional}
   * or an empty {@code Optional}.
   *
   * @throws PrizeItemNotFoundException
   */
  public Optional<PrizeItem> update(PrizeItemDto prizeItem) throws PrizeItemNotFoundException;
}