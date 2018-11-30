/**
 * @author cipta ageung
 */
package co.id.app.aisa.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import co.id.app.aisa.service.EntityAuditEventProducer;
import co.id.app.aisa.service.dto.EntityAuditEvent;

@Component
public class AuditTrailFeignClientFallback implements AuditTrailFeignClient{
	
	private final Logger log = LoggerFactory.getLogger(AuditTrailFeignClientFallback.class);
	
	@Autowired
	private EntityAuditEventProducer entityAuditEventProducer;

	@Override
	public ResponseEntity<String> createEntityAuditEvent(EntityAuditEvent entityAuditEvent) {
		log.warn("Triggered fallback for createEntityAuditEvent : {}", entityAuditEvent);
		// TODO Auto-generated method stub
		entityAuditEventProducer.send(entityAuditEvent);
		return ResponseEntity.ok().body("Audit Event Submitted by local queue producer");
	}
}