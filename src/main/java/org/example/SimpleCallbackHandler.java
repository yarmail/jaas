package org.example;

import javax.security.auth.callback.*;
import java.io.IOException;

/**
 * Класс реализующий обработку обратных вызовов
 */
public class SimpleCallbackHandler implements CallbackHandler {
  private final String username;
  private final String password;

  public SimpleCallbackHandler(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    for (Callback callback: callbacks) {
      if (callback instanceof NameCallback) {
        ((NameCallback) callback).setName(username);
      } else if (callback instanceof PasswordCallback) {
        ((PasswordCallback) callback).setPassword(password.toCharArray());
      } else {
        throw new UnsupportedCallbackException(callback);
      }
    }
  }
}
