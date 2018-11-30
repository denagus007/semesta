/**
 * @author cipta ageung
 */
package co.id.app.aisa.service;

import co.id.app.aisa.service.dto.EntityAuditEvent;

public interface EntityAuditEventProducer {
	
	public void send(EntityAuditEvent data);
}
