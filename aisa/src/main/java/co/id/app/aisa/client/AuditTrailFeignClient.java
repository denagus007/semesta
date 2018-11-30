/**
 * @author cipta ageung
 */
package co.id.app.aisa.client;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import co.id.app.aisa.service.dto.EntityAuditEvent;

@AuthorizedUserFeignClient(name = "${service.feign.audit-trail}", fallback=AuditTrailFeignClientFallback.class)
public interface AuditTrailFeignClient {

	@RequestMapping(method = RequestMethod.POST, value = "/api/audits/entity-audit-events", 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createEntityAuditEvent(@RequestBody EntityAuditEvent entityAuditEvent) throws Exception;
	

}