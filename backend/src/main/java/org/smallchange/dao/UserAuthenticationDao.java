package org.smallchange.dao;

import org.smallchange.functionality.UserDetail;
import org.smallchange.model.User;

public interface UserAuthenticationDao {
    public UserDetail loginAuthenticationService(String email, String password);
    public UserDetail registrationAuthenticationService(String name,String email,String password);
}
