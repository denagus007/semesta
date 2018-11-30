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

import co.id.app.aisa.domain.Permission;
import co.id.app.aisa.security.AuthoritiesConstants;
import co.id.app.aisa.service.PermissionService;
import co.id.app.aisa.web.rest.errors.BadRequestAlertException;
import co.id.app.aisa.web.rest.util.HeaderUtil;
import co.id.app.aisa.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * PermissionResource controller
 */
@RestController
@RequestMapping("/api")
public class PermissionResource {

    private final Logger log = LoggerFactory.getLogger(PermissionResource.class);

    private static final String ENTITY_NAME = "permission";
    
    private final PermissionService permissionService;
    
    private final HttpServletRequest request;

    
    public PermissionResource(PermissionService permissionService, HttpServletRequest request) {
    	this.permissionService = permissionService;
    	this.request = request;
    }
    
    
    /**
    * POST permission
     * @throws URISyntaxException 
    */
    @PostMapping("/permission")
    @Timed
    @Secured(value = {AuthoritiesConstants.ADMIN})
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) throws URISyntaxException {
    	log.debug("REST request to save Permission : {}", permission);
    	if (permission.getId() != null) {
            throw new BadRequestAlertException("A new Permission cannot already have an ID", ENTITY_NAME, "idexists");
        }
    	Permission result = permissionService.save(permission, request.getRemoteUser());
        return ResponseEntity.created(new URI("/api/permission/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
    * GET permission
    */
    @GetMapping("/permission")
    @Timed
    @Secured(value = {AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<Permission>> getAllPermission(Pageable pageable) {
    	log.debug("REST request to get a page of Permission");
        Page<Permission> page = permissionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/permission");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/permission/{id}")
    @Timed
    @Secured(value = {AuthoritiesConstants.ADMIN})
    public ResponseEntity<Permission> getPermission(@PathVariable Long id){
        log.debug("REST request to get Permission : {}", id);
        Optional<Permission> permission = permissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(permission);    	
    }
    
    @GetMapping("/_search/permission")
    @Timed
    @Secured(value = {AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<Permission>> searchPermission(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of permission for query {}", query);
        Page<Permission> page = permissionService.search(pageable, query);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/permission");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
    * DELETE permission
    */
    @DeleteMapping("/permission/{id}")
    @Timed
    @Secured(value = {AuthoritiesConstants.ADMIN})
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
    	log.debug("REST request to delete Permission : {}", id);
    	permissionService.delete(id, request.getRemoteUser());
    	return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
    * PUT permission
     * @throws URISyntaxException 
    */
    @PutMapping("/permission")
    @Timed
    @Secured(value = {AuthoritiesConstants.ADMIN})
    public ResponseEntity<Permission> updatePermission(@RequestBody Permission permission) throws URISyntaxException {
        log.debug("REST request to update Permission : {}", permission);
        if (permission.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Permission result = permissionService.save(permission, request.getRemoteUser());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, permission.getId().toString()))
            .body(result);
    }

}
 