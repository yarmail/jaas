package org.example;

import javax.security.auth.Subject;
import java.security.Principal;

/**
 * самостоятельная реализация класса Principal
 */
public class SimplePrincipal implements Principal {
  private String name;

  public SimplePrincipal(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean implies(Subject subject) {
    return Principal.super.implies(subject);
  }
}