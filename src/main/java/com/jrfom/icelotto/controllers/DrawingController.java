package com.jrfom.icelotto.controllers;

import java.util.List;

import com.jrfom.icelotto.model.Drawing;
import com.jrfom.icelotto.service.DrawingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DrawingController {
  private static final Logger log = LoggerFactory.getLogger(DrawingController.class);

  @Autowired
  private DrawingService drawingService;

  @RequestMapping(
    value = "/drawings",
    method = RequestMethod.GET,
    produces = MediaType.TEXT_HTML_VALUE
  )
  public ModelAndView drawingsIndex() {
    ModelAndView modelAndView = new ModelAndView();
    List<Drawing> drawingList = this.drawingService.findAll();

    modelAndView.setViewName("drawings");
    modelAndView.addObject("drawingsList", drawingList);

    return modelAndView;
  }
}