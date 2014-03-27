package com.jrfom.icelotto.security;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  public AuthSuccessHandler() {
    super();
    this.setDefaultTargetUrl("/index");
    this.setAlwaysUseDefaultTargetUrl(true);
  }
}