package com.jrfom.icelotto.controllers;

import com.google.common.base.Optional;
import com.jrfom.icelotto.model.PrizeItem;
import com.jrfom.icelotto.model.PrizeTier;
import com.jrfom.icelotto.service.PrizeItemService;
import com.jrfom.icelotto.service.PrizeTierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ItemController {
  private static final Logger log = LoggerFactory.getLogger(ItemController.class);

  @Autowired
  private PrizeItemService prizeItemService;

  @Autowired
  private PrizeTierService prizeTierService;

  @RequestMapping(
    value = "/prizeitem/{tierId}/{tierPosition}",
    method = RequestMethod.GET
  )
  public String getPrizeItemInTierAtPosition(
    @PathVariable Long tierId, @PathVariable Integer tierPosition, Model model)
  {
    Optional<PrizeTier> prizeTierOptional = this.prizeTierService.findById(tierId);

    if (prizeTierOptional.isPresent()) {
      PrizeTier prizeTier = prizeTierOptional.get();
      PrizeItem prizeItem = prizeTier.getItemAtPosition(tierPosition);
      model.addAttribute("prizeItem", prizeItem);
    }

    return "fragments/item :: fragment";
  }
}