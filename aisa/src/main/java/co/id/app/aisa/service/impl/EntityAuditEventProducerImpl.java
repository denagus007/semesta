/**
 * @author cipta ageung
 */
package co.id.app.aisa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import co.id.app.aisa.service.EntityAuditEventProducer;
import co.id.app.aisa.service.dto.EntityAuditEvent;

@Service
public class EntityAuditEventProducerImpl implements EntityAuditEventProducer{

private static final Logger log = LoggerFactory.getLogger(EntityAuditEventProducerImpl.class);

	@Autowired
	private KafkaTemplate<String, EntityAuditEvent> kafkaTemplate;
	
	@Value("${audit.kafka.topic}")
	String kafkaTopic = "audit-trail-event";
	
	public void send(EntityAuditEvent data) {
	    log.debug("sending data='{}'", data);
	    kafkaTemplate.send(kafkaTopic, data);
	}
}
