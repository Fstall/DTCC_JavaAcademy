package com.rstech.wordwatch.web.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.rstech.wordwatch.dao.RSUser;

@Service("assembler")
public class Assembler {

  @Transactional(readOnly = true)
  User buildUserFromUserEntity(RSUser userEntity) {

    String username = userEntity.getLOGIN();
    String password = userEntity.getPASSWORD();
    String isDeleted = userEntity.getIS_DELETED();
    String isLocked  = userEntity.getIS_LOCKED();
    boolean enabled = isDeleted == null || isDeleted.equals("N") || isDeleted.equals(""); 
    boolean accountNonExpired = true; // userEntity.isActive();
    boolean credentialsNonExpired = true; // userEntity.isActive();
    boolean accountNonLocked = isLocked == null || isLocked.equals("N") || isLocked.equals("");

    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
/*    for (SecurityRoleEntity role : userEntity.getRoles()) {
      authorities.add(new GrantedAuthorityImpl(role.getRoleName()));
    }
*/
    User user = new User(username, password, enabled,
      accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    return user;
  }
}
