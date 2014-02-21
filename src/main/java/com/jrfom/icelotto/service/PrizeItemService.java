package com.jrfom.icelotto.service;

import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.icelotto.exception.PrizeItemNotFoundException;
import com.jrfom.icelotto.model.GameItem;
import com.jrfom.icelotto.model.PrizeItem;

public interface PrizeItemService {
  /**
   * Create a new {@link com.jrfom.icelotto.model.PrizeItem} entry in the
   * database with the specified {@code gameItem}.
   *
   * @param gameItem A {@link com.jrfom.icelotto.model.GameItem} to add as a prize.
   *
   * @return An instance of {@link com.jrfom.icelotto.model.PrizeItem} wrapped
   * in an {@link com.google.common.base.Optional} or an empty {@code Optional}
   * if there was an error.
   */
  Optional<PrizeItem> create(GameItem gameItem);

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
  void delete(Long prizeItemId) throws PrizeItemNotFoundException;

  /**
   * Get a list of all {@link com.jrfom.icelotto.model.PrizeItem}s in the
   * database.
   *
   * @return An instance of {@link java.util.List} (empty if none were found).
   */
  List<PrizeItem> findAll();

  /**
   * Retrieve a {@link com.jrfom.icelotto.model.PrizeItem} from the database
   * given its identifier.
   *
   * @param id The identifier of the item.
   *
   * @return An instance of {@link com.jrfom.icelotto.model.PrizeItem} wrapped
   * in an {@link com.google.common.base.Optional} or an empty {@code Optional}.
   */
  Optional<PrizeItem> findById(Long id);
}