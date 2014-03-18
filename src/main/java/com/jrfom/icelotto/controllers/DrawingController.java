package com.jrfom.icelotto.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.jrfom.gw2.ApiClient;
import com.jrfom.gw2.api.model.items.Item;
import com.jrfom.icelotto.exception.GameItemNotFoundException;
import com.jrfom.icelotto.exception.PrizeTierNotFoundException;
import com.jrfom.icelotto.model.*;
import com.jrfom.icelotto.model.websocket.*;
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

  @MessageMapping("/admin/drawing/item/add")
  @SendTo("/topic/admin/drawing/item/add")
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

  @MessageMapping("/admin/drawing/create")
  @SendTo("/topic/admin/drawing/created")
  public String createDrawing(DrawingCreateMessage drawingCreateMessage) {
    PrizePool smallPool = new PrizePool();
    PrizePool largePool = new PrizePool();
    this.drawingService.create(drawingCreateMessage.getDate(), smallPool, largePool);

    return "created";
  }

  @MessageMapping("/admin/drawing/deposit")
  @SendTo("/topic/admin/drawing/deposit/added")
  public DepositEntryResponse depositEntry(DepositEntryMessage depositEntryMessage) {
    DepositEntryResponse response = new DepositEntryResponse();
    Optional<Drawing> drawingOptional =
      this.drawingService.findById(depositEntryMessage.getDrawingId());

    // TODO: clean this mess up
    if (drawingOptional.isPresent()) {
      User user;
      Drawing drawing = drawingOptional.get();

      Optional<PrizeTier> prizeTierOptional =
        this.prizeTierService.findById(depositEntryMessage.getTierId());
      Optional<User> userOptional =
        this.userService.findByGw2DisplayName(depositEntryMessage.getGw2DisplayName());

      if (!userOptional.isPresent()) {
        user = new User(depositEntryMessage.getGw2DisplayName());
        user = this.userService.save(user);
      } else {
        user = userOptional.get();
      }

      if (prizeTierOptional.isPresent()) {
        PrizeTier prizeTier = prizeTierOptional.get();
        Entry entry = new Entry(user, prizeTier, depositEntryMessage.getAmount());
        drawing.addEntry(entry);
        drawing = this.drawingService.save(drawing);

        response.setSmallPoolTotal(drawing.getSmallPoolTotal());
        response.setLargePoolTotal(drawing.getLargePoolTotal());
      }
    }

    return response;
  }

  @MessageMapping("/admin/drawing/start")
  @SendTo("/topic/drawing/started")
  public StartDrawingResponse startDrawing(StartDrawingMessage startDrawingMessage) {
    StartDrawingResponse result = new StartDrawingResponse();
    Optional<Drawing> drawingOptional =
      this.drawingService.findById(startDrawingMessage.getDrawingId());

    // TODO:
    // 1. calculate pool (money) totals and who is entered in which money pool
    // 2. shuffle all tiers entrants
    // 3. return which tiers have entries, and how many

    if (drawingOptional.isPresent()) {
      result.setDrawingId(drawingOptional.get().getId());
      result.setStarted(true);
    }

    return result;
  }

  @MessageMapping("/admin/drawing/draw/tier")
  @SendTo("/topic/drawing/tier/winner")
  public DrawForTierResponse tierDraw(DrawForTierMessage message) {
    DrawForTierResponse response = new DrawForTierResponse();
    response.setPoolId(message.getPoolId());
    response.setTierId(message.getTierId());

    try {
      SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
      byte[] seed = new byte[4098];
      random.nextBytes(seed);
      random.setSeed(seed);

      Integer result = random.nextInt(10);
      log.debug("Random draw result = `{}`", result);
      response.setItemNumber(result);
    } catch (NoSuchAlgorithmException e) {
      log.error("Could not find random algorithm: `{}`", e.getMessage());
      log.debug(e.toString());
    }

    return response;
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