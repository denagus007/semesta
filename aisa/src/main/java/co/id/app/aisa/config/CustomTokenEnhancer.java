/**
 * @author cipta ageung
 */
package co.id.app.aisa.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import co.id.app.aisa.domain.Authority;
import co.id.app.aisa.domain.Permission;
import co.id.app.aisa.domain.User;
import co.id.app.aisa.service.UserService;

public class CustomTokenEnhancer extends JwtAccessTokenConverter{
	
	@Autowired
	public UserService userService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Optional<User> user = userService.getUserByUserName(authentication.getName());
		Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
		info.put("email", user.get().getEmail());
		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
		Set<GrantedAuthority> authoritiesSet = new HashSet<>(authentication.getAuthorities());
		
		Set<Authority> authoritySet = user.get().getAuthorities();
		List<String> permissionList = new ArrayList<>();
		if(authoritySet != null) {
			for(Authority authority: authoritySet) {
				if(!authority.getPermissions().isEmpty()) {
					for(Permission permission: authority.getPermissions()) {
						permissionList.add(permission.getServiceName());
					}	
				}
			} 
		}
		for (GrantedAuthority authority : authoritiesSet) {
			permissionList.add(authority.getAuthority());
		}
		String[] authorities = 	permissionList.toArray(new String[permissionList.size()]);
		info.put("authorities", authorities);
		customAccessToken.setAdditionalInformation(info);
		return super.enhance(customAccessToken, authentication);
	}
}
