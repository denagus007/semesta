package co.id.app.aisa.service;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.app.aisa.config.audit.EntityAuditAction;
import co.id.app.aisa.domain.Authority;
import co.id.app.aisa.repository.AuthorityRepository;
import co.id.app.aisa.repository.search.AuthoritySearchRepository;
import co.id.app.aisa.service.impl.BaseServiceImpl;

@Service
@Transactional
public class AuthorityService extends BaseServiceImpl{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2545412063883525741L;

	private final Logger log = LoggerFactory.getLogger(AuthorityService.class);

    private final AuthorityRepository authorityRepository;
    
    private final AuthoritySearchRepository authoritySearchRepository;
    
    public AuthorityService(AuthorityRepository authorityRepository, AuthoritySearchRepository authoritySearchRepository) {
    	this.authorityRepository = authorityRepository;
    	this.authoritySearchRepository = authoritySearchRepository;
    }
    
    public Authority save(Authority authority, String user) {
    	log.debug("Request to save Authority : {}", authority);  
    	Authority result = authorityRepository.save(authority);
    	if (result != null) {
    		authoritySearchRepository.save(result);
    		Optional<String> user_ = Optional.ofNullable(user);
    		writeAuditEvent(authority, EntityAuditAction.CREATE, user_);
    	}
    	return result;
    }
    
    public void delete(String name, String user) {
    	log.debug("Request to delete Authority : {}", name);
		Optional<String> user_ = Optional.ofNullable(user);
		Optional<Authority> authority = findOne(name);
		writeAuditEvent(authority.get(), EntityAuditAction.DELETE, user_);
    	authorityRepository.deleteById(name);
    	authoritySearchRepository.deleteById(name);
    }
    
    @Transactional(readOnly = true)
    public Optional<Authority> findOne(String name){
    	log.debug("Request to findOne Authority : {}", name);
    	return authorityRepository.findById(name);
    }
    
	@SuppressWarnings("deprecation")
	@Transactional(readOnly = true)
    public Page<Authority> findAll(Pageable pageable){
    	log.debug("Request to findAll Authority");
    	Pageable page = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "name");
    	return authorityRepository.findAll(page);
    }
    
    @Transactional(readOnly = true)
    public Page<Authority> search(Pageable pageable, String query){
    	log.debug("Request to search Authority");
    	return authoritySearchRepository.search(queryStringQuery(query), pageable);
    }
}
