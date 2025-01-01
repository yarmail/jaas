package org.example;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class Main {
  public static void main(String[] args) {
    String configPath = Main.class.getClassLoader().getResource("jaas.config").getPath();
    System.setProperty("java.security.auth.login.config", configPath);

    try {
      CallbackHandler callbackHandler = new SimpleCallbackHandler("user", "password");
      LoginContext lc = new LoginContext("MyLoginModule", callbackHandler);
      lc.login(); //Попытка входа
      System.out.println("Authenticated successfully!");
      lc.logout(); //Выход
      System.out.println("Logged out successfully!");
    } catch (LoginException e) {
      System.out.println("Authentication failed: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }
}