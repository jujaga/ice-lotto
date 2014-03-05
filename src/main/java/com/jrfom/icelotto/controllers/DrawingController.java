package com.jrfom.icelotto.controllers;

import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.gw2.ApiClient;
import com.jrfom.gw2.api.model.items.Item;
import com.jrfom.icelotto.exception.GameItemNotFoundException;
import com.jrfom.icelotto.exception.PrizeTierNotFoundException;
import com.jrfom.icelotto.model.Drawing;
import com.jrfom.icelotto.model.GameItem;
import com.jrfom.icelotto.model.ItemRarity;
import com.jrfom.icelotto.model.websocket.ItemAddMessage;
import com.jrfom.icelotto.model.websocket.ItemAddResponse;
import com.jrfom.icelotto.service.DrawingService;
import com.jrfom.icelotto.service.GameItemService;
import com.jrfom.icelotto.service.PrizeTierService;
import com.jrfom.icelotto.util.ImageDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DrawingController {
  private static final Logger log = LoggerFactory.getLogger(DrawingController.class);

  @Autowired
  private ApiClient apiClient;

  @Autowired
  private ImageDownloader imageDownloader;

  @Autowired
  private DrawingService drawingService;

  @Autowired
  private PrizeTierService prizeTierService;

  @Autowired
  private GameItemService gameItemService;

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

  @MessageMapping("/app/drawing/item/add")
  @SendTo("/topic/drawing/item/add")
  public ItemAddResponse addItem(ItemAddMessage itemAddMessage) {
    ItemAddResponse response = new ItemAddResponse();

    try {
      GameItem gameItem = this.getGameItem(itemAddMessage.getItemId());

      if (gameItem == null) {
        throw new GameItemNotFoundException(
          "Item is in valid. Item id: " + itemAddMessage.getItemId()
        );
      }

      this.prizeTierService.addItem(
        gameItem,
        itemAddMessage.getTierId(),
        itemAddMessage.getTierPosition(),
        itemAddMessage.getCount()
      );
    } catch (PrizeTierNotFoundException | GameItemNotFoundException e) {
      response = new ItemAddResponse(false, e.getMessage());
    }

    return response;
  }

  private GameItem getGameItem(Long itemId) {
    // TODO: this should be in a service
    GameItem gameItem = null;
    Optional<GameItem> gameItemOptional =
        this.gameItemService.findById(itemId);

    if (gameItemOptional.isPresent()) {
      gameItem = gameItemOptional.get();
    } else {
      Optional<Item> itemOptional = this.apiClient.getItemDetails(itemId.intValue());
      if (itemOptional.isPresent()) {
        Item item = itemOptional.get();
        gameItem = new GameItem(
          item.getItemId().longValue(),
          item.getName(),
          item.getDescription()
        );
        gameItem.setMinLevel(item.getLevel());
        gameItem.setRarity(ItemRarity.valueOf(item.getRarity()));

        String url = String.format(
          "https://render.guildwars2.com/file/%s/%s.png",
          item.getIconFileSignature(),
          item.getIconFileId()
        );
        this.imageDownloader.downloadImageAtUrlAs(url, "icons/" + item.getItemId());
        gameItem.setImageUrl(url);
      }

      this.gameItemService.save(gameItem);
    }

    return gameItem;
  }
}