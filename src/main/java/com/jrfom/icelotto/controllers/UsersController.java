package com.jrfom.icelotto.controllers;

import java.util.List;

import com.google.common.base.Optional;
import com.jrfom.icelotto.model.User;
import com.jrfom.icelotto.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller is for the administration of all users. This does not deal
 * with actions performed by an individual on their own account.
 */
@Controller
public class UsersController {
  private static final Logger log = LoggerFactory.getLogger(UsersController.class);

  @Autowired
  private UserService userService;

  @RequestMapping(
    value = "/admin/users",
    method = RequestMethod.GET
  )
  private ModelAndView index() {
    log.debug("Received request for list of users");
    ModelAndView mav = new ModelAndView();
    List<User> usersList = this.userService.findAllOrderByGw2DisplayName();

    mav.setViewName("admin/users");
    mav.addObject("usersList", usersList);

    return mav;
  }

  @RequestMapping(
    value = "/admin/users/{user}/create/key",
    method = RequestMethod.GET
  )
  private String createClaimKey(@PathVariable String user) {
    log.debug("Received request to create claim key for `{}`", user);
    Optional<User> userOptional = this.userService.findByGw2DisplayName(user);

    if (userOptional.isPresent()) {
      User localUser = userOptional.get();
      localUser.setClaimKey(KeyGenerators.string().generateKey());
      this.userService.save(localUser);
    }

    return "redirect:/admin/users";
  }
}