package com.rstech.wordwatch.web.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;    
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rstech.wordwatch.business.domain.RSClientManager;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSUser;

@Service("userDetailsService") 
public class UserDetailsServiceImpl implements UserDetailsService {

  private RSUser    theUser;
  private Assembler assembler;

  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException, DataAccessException {
 	  
		RSUserManager userMgr = new RSUserManager();
		RSClientManager mgr = new RSClientManager();
  	  
	  
    UserDetails userDetails = null;
    RSUser      userEntity  = userMgr.getFirstUserByLogin(username);
    if (userEntity == null)
      throw new UsernameNotFoundException("user not found");

    return assembler.buildUserFromUserEntity(userEntity);
  }
}
