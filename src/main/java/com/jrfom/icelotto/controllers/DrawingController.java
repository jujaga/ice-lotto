package com.jrfom.icelotto.controllers;

import com.jrfom.icelotto.service.DrawingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
  public String drawingsIndex() {
    return "drawings";
  }
}