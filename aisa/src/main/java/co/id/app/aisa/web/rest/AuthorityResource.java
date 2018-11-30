package co.id.app.aisa.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import co.id.app.aisa.domain.Authority;
import co.id.app.aisa.security.AuthoritiesConstants;
import co.id.app.aisa.service.AuthorityService;
import co.id.app.aisa.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * AuthorityResource controller
 */
@RestController
@RequestMapping("/api")
public class AuthorityResource {

    private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);
    
    private final AuthorityService authorityService;
    
    private final HttpServletRequest request;
    
    public AuthorityResource(AuthorityService authorityService, HttpServletRequest request) {
    	this.authorityService = authorityService;
    	this.request = request;
    }
    
    /**
     * REST Endpoint for Create Authority
     * 
     * @param authority
     * @return
     * @throws URISyntaxException
     * 
     */
    @PostMapping("/authority")
    @Timed
    @Secured(value = {AuthoritiesConstants.ADMIN})
    public ResponseEntity<Authority> createAuthorityPost(@RequestBody Authority authority) throws URISyntaxException{
    	log.debug("REST request to save Authority : {}", authority);
    	
    	Authority result = authorityService.save(authority, request.getRemoteUser());
        return ResponseEntity.created(new URI("/api/authority/" + result.getName())).body(result);
    }
    
    /**
     * REST Endpoint for Create Authority
     * 
     * @param authority
     * @return
     * @throws URISyntaxException
     * 
     */
    @PutMapping("/authority")
    @Timed
    @Secured(value = {AuthoritiesConstants.ADMIN})
    public ResponseEntity<Authority> createAuthorityPut(@RequestBody Authority authority) throws URISyntaxException{
    	log.debug("REST request to save Authority : {}", authority);
    	
    	Authority result = authorityService.save(authority,request.getRemoteUser());
        return ResponseEntity.created(new URI("/api/authority/" + result.getName())).body(result);
    }    
    
    /**
     * REST Endpoint for Find One authority
     * 
     * @param name
     * @return
     * 
     */
    @GetMapping("/authority/{name}")
    @Timed
    @Secured(value = {AuthoritiesConstants.ADMIN})
    public ResponseEntity<Authority> findOne(@PathVariable String name){
    	log.debug("REST request to get Authority : {}", name);
    	Optional<Authority> result = authorityService.findOne(name);
    	return ResponseUtil.wrapOrNotFound(result);
    }
    
    /**
     * REST Endpoint to get All Authority
     * 
     * @param pageable
     * @return
     * 
     */
    @GetMapping("/authority")
    @Timed
    @Secured(value = {AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<Authority>> getAllAuthority(Pageable pageable){
    	log.debug("REST request to get All Authority");
    	Page<Authority> result = authorityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, "/api/authority");
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * REST Endpoint for Search Authority at Elastic
     * 
     * @param query
     * @param pageable
     * @return
     */
    
    @GetMapping("/_search/authority")
    @Timed
    @Secured(value = {AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<Authority>> searchAuthority(@RequestParam String query, Pageable pageable){
    	log.debug("REST request to search Authority");
    	Page<Authority> result = authorityService.search(pageable, query);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, "/api/_search/authority");
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * REST Endpoint for delete authority
     * 
     * @param name
     * @return
     */
    @DeleteMapping("/authority/{name}")
    @Timed
    @Secured(value = {AuthoritiesConstants.ADMIN})
    public ResponseEntity<Void> deleteAuthority(@PathVariable String name){
    	log.debug("REST request to delete Authority : {}", name);
    	authorityService.delete(name,request.getRemoteUser());
    	return ResponseEntity.ok().build();
    }

}
