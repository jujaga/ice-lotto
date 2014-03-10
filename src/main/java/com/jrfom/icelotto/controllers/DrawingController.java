package com.jrfom.icelotto.controllers;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.jrfom.gw2.ApiClient;
import com.jrfom.gw2.api.model.items.Item;
import com.jrfom.icelotto.exception.GameItemNotFoundException;
import com.jrfom.icelotto.exception.PrizeTierNotFoundException;
import com.jrfom.icelotto.model.*;
import com.jrfom.icelotto.model.websocket.DepositEntryMessage;
import com.jrfom.icelotto.model.websocket.DrawingCreateMessage;
import com.jrfom.icelotto.model.websocket.ItemAddMessage;
import com.jrfom.icelotto.model.websocket.ItemAddResponse;
import com.jrfom.icelotto.service.*;
import com.jrfom.icelotto.util.ImageDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
  private PrizePoolService prizePoolService;

  @Autowired
  private GameItemService gameItemService;

  @Autowired
  private UserService userService;

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

  @RequestMapping(
    value = "/drawing/{id}",
    method = RequestMethod.GET,
    produces = MediaType.TEXT_HTML_VALUE
  )
  public ModelAndView editDrawing(@PathVariable Long id) {
    ModelAndView modelAndView = new ModelAndView();
    Optional<Drawing> drawingOptional = this.drawingService.findById(id);

    if (drawingOptional.isPresent()) {
      modelAndView.addObject("drawing", drawingOptional.get());
    }
    modelAndView.addObject("drawingId", id);

    modelAndView.setViewName("drawing");
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
    } catch (NullPointerException e) {
      response = new ItemAddResponse(false, "Item id was null. Try again?");
    }

    return response;
  }

  @MessageMapping("/app/drawing/create")
  @SendTo("/topic/drawing/created")
  public String createDrawing(DrawingCreateMessage drawingCreateMessage) {
    PrizePool smallPool = new PrizePool();
    PrizePool largePool = new PrizePool();
    this.drawingService.create(drawingCreateMessage.getDate(), smallPool, largePool);

    return "created";
  }

  @MessageMapping("/app/drawing/deposit")
  @SendTo("/topic/drawing/deposit/added")
  public Integer depositEntry(DepositEntryMessage depositEntryMessage) {
    Integer result = 0;
    Optional<Drawing> drawingOptional =
      this.drawingService.findById(depositEntryMessage.getDrawingId());

    // TODO: clean this mess up
    if (drawingOptional.isPresent()) {
      User user;
      PrizePool prizePool;
      Drawing drawing = drawingOptional.get();

      Optional<PrizePool> prizePoolOptional =
        this.prizePoolService.findById(depositEntryMessage.getPoolId());
      Optional<User> userOptional =
        this.userService.findByGw2DisplayName(depositEntryMessage.getGw2DisplayName());

      if (!userOptional.isPresent()) {
        user = new User(depositEntryMessage.getGw2DisplayName());
        user = this.userService.save(user);
      } else {
        user = userOptional.get();
      }

      if (prizePoolOptional.isPresent()) {
        prizePool = prizePoolOptional.get();
        Entry entry = new Entry(user, prizePool, depositEntryMessage.getAmount());
        drawing.addEntry(entry);
        drawing = this.drawingService.save(drawing);

        if (prizePool.getId() == drawing.getSmallPool().getId()) {
          result = drawing.getSmallPoolTotal();
        } else {
          result = drawing.getLargePoolTotal();
        }
      }
    } else {
      result = -1;
    }

    return result;
  }

  private GameItem getGameItem(Long itemId) {
    // TODO: this should be in a service
    Preconditions.checkNotNull(itemId);
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