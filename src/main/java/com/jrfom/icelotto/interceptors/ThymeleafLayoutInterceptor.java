package com.jrfom.icelotto.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Preconditions;
import com.jrfom.icelotto.annotations.Layout;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>Provides an {@link org.springframework.web.servlet.handler.HandlerInterceptorAdapter}
 * that inspects the handler for the presence of the {@link com.jrfom.icelotto.annotations.Layout}
 * annotation. If present, it changes the view name to that of the layout
 * specified in the annotation and sets a model attribute to the name of the
 * "view" (that was returned by the method). Thus, Thymeleaf will use the
 * layout as the main template and the template can use the view name to load
 * in the sub-template. For example:</p>
 *
 * <pre>
 * {@code
 * \@Layout("layouts/alternate")
 * \@RequestMapping("/user/login")
 * public String login() {
 *   return "loginView";
 * }
 * }
 * </pre>
 *
 * <p>In this example, Tymeleaf would load the "layouts/alternate" as the view
 * template. This template would have an include like this:</p>
 *
 * {@code <div th:replace="${view} :: content"></div>}
 *
 * <p>The {@code ${view}} in this case would be replaced with "loginView".</p>
 */
public class ThymeleafLayoutInterceptor extends HandlerInterceptorAdapter {
  private static final String DEFAULT_LAYOUT = "layouts/default";
  private static final String DEFAULT_VIEW_ATTRIBUTE_NAME = "view";

  private String defaultLayout = DEFAULT_LAYOUT;
  private String viewAttributeName = DEFAULT_VIEW_ATTRIBUTE_NAME;

  public void setDefaultLayout(String defaultLayout) {
    Preconditions.checkArgument(defaultLayout.length() > 0);
    this.defaultLayout = defaultLayout;
  }

  public void setViewAttributeName(String viewAttributeName) {
    Preconditions.checkArgument(viewAttributeName.length() > 0);
    this.viewAttributeName = viewAttributeName;
  }

  @Override
  public void postHandle(
    HttpServletRequest request,
    HttpServletResponse response,
    Object handler,
    ModelAndView modelAndView)
    throws Exception
  {
    if (modelAndView == null || !modelAndView.hasView()) {
      return;
    }

    String originalViewName = modelAndView.getViewName();
    if (this.isRedirectOrForward(originalViewName)) {
      return;
    }

    String layoutName = this.getLayoutName(handler);
    modelAndView.setViewName(layoutName);
    modelAndView.addObject(this.viewAttributeName, originalViewName);
  }

  private boolean isRedirectOrForward(String viewName) {
    return viewName.startsWith("redirect:") || viewName.startsWith("forward:");
  }

  private String getLayoutName(Object handler) {
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    Layout layout = this.getMethodOrTypeAnnotation(handlerMethod);
    return (layout == null) ? this.defaultLayout : layout.value();
  }

  private Layout getMethodOrTypeAnnotation(HandlerMethod handlerMethod) {
    Layout layout = handlerMethod.getMethodAnnotation(Layout.class);

    if (layout == null) {
      layout = handlerMethod.getBeanType().getAnnotation(Layout.class);
    }

    return layout;
  }
}