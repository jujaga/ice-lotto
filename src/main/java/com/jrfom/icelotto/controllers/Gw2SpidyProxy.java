package com.jrfom.icelotto.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * Because the gw2spidy.com API doesn't support CORS request :-/
 */
@Controller
public class Gw2SpidyProxy {
  private RestTemplate restClient;

  public Gw2SpidyProxy() {
    this.restClient = new RestTemplate();
  }

  @RequestMapping(
    value = "/spidy/item-search/{term}",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public Object search(@PathVariable String term) {
    return this.restClient.getForObject(
      "http://www.gw2spidy.com/api/v0.9/json/item-search/{term}",
      String.class,
      term
    );
  }
}