package com.jrfom.icelotto.controllers;

import com.google.common.base.Optional;
import com.jrfom.icelotto.model.Drawing;
import com.jrfom.icelotto.service.DrawingService;
import com.jrfom.icelotto.service.PrizeItemService;
import com.jrfom.icelotto.service.PrizePoolService;
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
  private DrawingService drawingService;

  @Autowired
  private PrizeItemService prizeItemService;

  @Autowired
  private PrizePoolService prizePoolService;

  @RequestMapping(value = "/")
  public ModelAndView index() {
    log.info("Displaying index.html");
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("index");

    Optional<Drawing> result = this.drawingService.nextDrawing();
    if (result.isPresent()) {
      modelAndView.addObject("nextDrawing", result.get());
    }

    return modelAndView;
  }
}