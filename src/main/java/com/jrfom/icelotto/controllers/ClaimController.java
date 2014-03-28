package com.jrfom.icelotto.controllers;

import com.google.common.base.Optional;
import com.jrfom.icelotto.model.ClaimTicket;
import com.jrfom.icelotto.model.User;
import com.jrfom.icelotto.service.UserService;
import com.jrfom.icelotto.util.Stringer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClaimController {
  private static final Logger log = LoggerFactory.getLogger(ClaimController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @RequestMapping(
    value = "/claim",
    method = RequestMethod.GET
  )
  public String claim() {
    return "claim";
  }

  @RequestMapping(
    value = "/claim",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
  )
  public ModelAndView doClaim(ClaimTicket ticket) {
    log.debug("Processing claim request: `{}`", Stringer.jsonString(ticket));
    ModelAndView mav = new ModelAndView();
    Optional<User> userOptional =
      this.userService.findByGw2DisplayNameAndClaimKey(
        ticket.getGw2DisplayName(), ticket.getClaimKey()
      );

    mav.setViewName("claim");
    if (!userOptional.isPresent()) {
      mav.addObject("claimError", "true");
      mav.addObject("claimErrorMessage", "Could not find matching user.");
    } else {
      mav.addObject("claimSuccess", "true");
      mav.addObject("claimUsername", ticket.getGw2DisplayName());
    }

    return mav;
  }

  @RequestMapping(
    value = "/claim/set/password",
    method = RequestMethod.POST,
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
  )
  public ModelAndView setPassword(
    @RequestParam String password, @RequestParam String passwordVerify, @RequestParam String user)
  {
    log.debug("Setting claim password for `{}`", user);
    ModelAndView mav = new ModelAndView();
    mav.setViewName("claim");
    mav.addObject("setPassSuccess", "false");

    if (!password.equals(passwordVerify)) {
      mav.addObject("setPassErrorMessage", "Passwords do not match.");
    } else if (password.length() < 8) {
      mav.addObject("setPassErrorMessage", "Password is too short. Must be at least 8 characters.");
    } else {
      String encoded = this.passwordEncoder.encode(password);
      Optional<User> userOptional = this.userService.findByGw2DisplayName(user);

      if (userOptional.isPresent()) {
        User dbUser = userOptional.get();
        dbUser.setPassword(encoded);
        dbUser.setEnabled(true);
        this.userService.save(dbUser);

        mav.addObject("setPassSuccess", true);
        mav.addObject("username", dbUser.getGw2DisplayName());
      } else {
        mav.addObject("setPassErrorMessage", "Could not find user.");
      }
    }

    return mav;
  }
}