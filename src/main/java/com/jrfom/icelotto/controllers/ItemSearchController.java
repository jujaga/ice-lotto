package com.jrfom.icelotto.controllers;

import com.jrfom.gw2.util.ChatLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class ItemSearchController {
  private static final Logger log = LoggerFactory.getLogger(ItemSearchController.class);

  private RestTemplate restClient;

  public ItemSearchController() {
    this.restClient = new RestTemplate();
  }

  @RequestMapping(
    value = "/spidy/item-search/{term}",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseBody
  public Object search(@PathVariable String term) {
    log.debug("Received gw2spidy search request for: `{}`", term);
    Object result;
    boolean isChatLink = (term.startsWith("[&") && term.endsWith("]"));

    if (isChatLink) {
      log.debug("Search term is a chat link");
      ChatLink chatLink = new ChatLink(term);
      result = this.restClient.getForObject(
        "http://www.gw2spidy.com/api/v0.9/json/item/{id}",
        String.class,
        chatLink.getId()
      );
    } else {
      log.debug("Search term is a name");
      result = this.restClient.getForObject(
        "http://www.gw2spidy.com/api/v0.9/json/item-search/{term}",
        String.class,
        term
      );
    }

    return result;
  }
}