package org.example;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.security.Principal;
import java.util.Map;

public class MyLoginModule implements LoginModule {
  private CallbackHandler callbackHandler;
  private boolean authenticated = false;
  private String username;
  private Subject subject;


  @Override
  public void initialize(Subject subject, CallbackHandler callbackHandler,
                         Map<String, ?> sharedState, Map<String, ?> options) {
    this.subject = subject;
    this.callbackHandler = callbackHandler;
  }

  @Override
  public boolean login() throws LoginException {
    NameCallback nameCallback = new NameCallback("Username");
    PasswordCallback passwordCallback = new PasswordCallback("Password", true);
    try {
      callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});
      username = nameCallback.getName();
      String password = new String(passwordCallback.getPassword());

      if("user".equals(username) && "password".equals(password)) {
        authenticated = true;
        return true;
      } else {
        authenticated = false;
        throw new FailedLoginException("Invalid credentials");
      }
    } catch (Exception e) {
      throw new LoginException("Error during login: " + e.getMessage());
    }
  }

  /**
   * Вызывается после успешной аутентификации пользователя
   * Регистрация аутентифицированного пользователя в системе.
   */
  @Override
  public boolean commit() throws LoginException {
    if (authenticated) {
      Principal principal = new SimplePrincipal(username);
      subject.getPrincipals().add(principal);
      return true;
    } else
      throw new LoginException("Authentication not committed: user not authenticated.");
  }

  @Override
  public boolean abort() throws LoginException {
    authenticated = false;
    return false;
  }

  @Override
  public boolean logout() throws LoginException {
    authenticated = false;
    subject.getPrincipals().clear();
    return true;
  }
}