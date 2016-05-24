package com.sanjnan.server.security.provider;

import com.sanjnan.server.pojos.SanjnanTokenResponse;
import com.sanjnan.server.security.token.SanjnanTokenPrincipal;
import com.sanjnan.server.service.H2OTokenService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay on 2/3/16.
 */
public class SanjnanTokenAuthenticationProvider implements AuthenticationProvider {

  private final static Logger logger = Logger.getLogger(SanjnanTokenAuthenticationProvider.class);

  @Autowired
  private H2OTokenService tokenService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    SanjnanTokenPrincipal principal = (SanjnanTokenPrincipal)authentication.getPrincipal();
    logger.info("Called authenticated " + principal.toString());
    if (!principal.isValid()) {
      throw new BadCredentialsException("Invalid token");
    }
    SanjnanTokenResponse response = null;
    try {
      response = tokenService.contains(principal.getTenant().get(),
          principal.getRemoteAddr().get(),
          principal.getApplicationId().get(),
          principal.getToken().get(),
          principal.getTokenType()).getResponse();
    } catch (DatatypeConfigurationException e) {
      logger.log(Level.ERROR, "XMLGregorian Calendar conversion error.", e);
    }
    if (response != null) {
      List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
      response.getH2ORoles().forEach(e -> grantedAuthorityList.add(e));
      return new PreAuthenticatedAuthenticationToken(principal, null, grantedAuthorityList);
    }
    throw new BadCredentialsException("Invalid token or token expired");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(PreAuthenticatedAuthenticationToken.class);
  }
}
