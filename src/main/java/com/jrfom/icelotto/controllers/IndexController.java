package com.jrfom.icelotto.controllers;

import com.google.common.base.Optional;
import com.jrfom.icelotto.model.PrizeItem;
import com.jrfom.icelotto.service.PrizeItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
  private static final Logger log = LoggerFactory.getLogger(IndexController.class);

  @Autowired
  private PrizeItemService prizeItemService;

  @RequestMapping(value = "/")
  public ModelAndView index() {
    log.info("Displaying index.html");
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("index");

    Optional<PrizeItem> result = this.prizeItemService.findById(1l);
    PrizeItem prizeItem = (result.isPresent()) ? result.get() : null;
    modelAndView.addObject("prizeItem", prizeItem);

    return modelAndView;
  }
}