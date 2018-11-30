package co.id.app.aisa.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.URISyntaxException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.app.aisa.config.audit.EntityAuditAction;
import co.id.app.aisa.domain.Permission;
import co.id.app.aisa.repository.PermissionRepository;
import co.id.app.aisa.repository.search.PermissionSearchRepository;
import co.id.app.aisa.service.impl.BaseServiceImpl;

@Service
@Transactional
public class PermissionService extends BaseServiceImpl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1458964516649177883L;

	private final Logger log = LoggerFactory.getLogger(PermissionService.class);
    
    private final PermissionRepository permissionRepository;
    
    private final PermissionSearchRepository permissionSearchRepository;
    
    public PermissionService(PermissionRepository permissionRepository, PermissionSearchRepository permissionSearchRepository) {
    	this.permissionRepository = permissionRepository;
    	this.permissionSearchRepository = permissionSearchRepository;
    }
    
    public Permission save(Permission permission, String user) throws URISyntaxException{
    	log.debug("Request to save Permission : {}, user : {}", permission, user);
    	boolean isCreate = true;
    	if (permission.getId()!=null) isCreate = false;
    	Permission result = permissionRepository.save(permission);
    	if (result != null) {
    		permissionSearchRepository.save(result);
    		Optional<String> user_ = Optional.ofNullable(user);
    		writeAuditEvent(result, (isCreate?EntityAuditAction.CREATE:EntityAuditAction.UPDATE), user_);
    	}
    	return result;
    }
    
    public void delete(Long id, String user) {
    	log.debug("Request to delete Permission : {}", id);
    	Optional<Permission> deleteObject  = findOne(id);
    	Optional<String> user_ = Optional.ofNullable(user);
    	writeAuditEvent(deleteObject.get(), EntityAuditAction.DELETE, user_);
    	permissionRepository.deleteById(id);
    	permissionSearchRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public Optional<Permission> findOne(Long id){
    	log.debug("Request to get Permission : {}", id);
    	return permissionRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public Page<Permission> findAll(Pageable pageable){
    	log.debug("Request to get All Permission");
    	return permissionRepository.findAll(pageable);
    }
    
    @Transactional(readOnly = true)
    public Page<Permission> search(Pageable pageable, String query){
    	log.debug("Request to search Permission");
    	return permissionSearchRepository.search(queryStringQuery(query), pageable);
    }
}
