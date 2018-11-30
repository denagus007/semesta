/**
 * @author cipta ageung
 */
package co.id.app.aisa.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.id.app.aisa.client.AuditTrailFeignClient;
import co.id.app.aisa.config.audit.EntityAuditAction;
import co.id.app.aisa.domain.AbstractAuditingEntity;
import co.id.app.aisa.service.EntityAuditEventProducer;
import co.id.app.aisa.service.dto.EntityAuditEvent;

public class BaseServiceImpl implements Serializable{

	private static final long serialVersionUID = 1L;

	private final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	private ValidatorFactory validatorFactory = null;
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
    private AuditTrailFeignClient auditTrailFeignClient;
	
	@Autowired
	private EntityAuditEventProducer entityAuditEventProducer;
    
    @Async
    public void writeAuditEvent(Object target, EntityAuditAction action, Optional<String> user) {
        log.debug("-------------- Post {} audit  --------------", action.value());
        try {
            EntityAuditEvent auditedEntity = prepareAuditEntity(target, action, user);
            if (auditedEntity != null) {
            	auditTrailFeignClient.createEntityAuditEvent(auditedEntity);
            	//entityAuditEventProducer.send(auditedEntity);
            }
        } catch (Exception e) {
            log.error("Exception while persisting audit entity for {} error: {}", target, e);
        }
    }
    
    public EntityAuditEvent prepareAuditEntity(final Object entity, EntityAuditAction action, Optional<String> user) {
        EntityAuditEvent auditedEntity = new EntityAuditEvent();
        Class<?> entityClass = entity.getClass(); // Retrieve entity class with reflection
        auditedEntity.setAction(action.value());
        auditedEntity.setEntityType(entityClass.getName());
        Long entityId = 0L;
        String entityData = "{}";
        log.trace("Getting Entity Id and Content");
        try {
            Field privateLongField = null;
            try{
            	privateLongField = entityClass.getDeclaredField("id");
            } catch (NoSuchFieldException e) {
            	final Field[] fields = FieldUtils.getFieldsWithAnnotation(entityClass, org.springframework.data.annotation.Id.class);
            	if (fields.length!=0) privateLongField = fields[0];
            } finally {
				if (privateLongField !=null) {
					privateLongField.setAccessible(true);
		            try{entityId = (Long) privateLongField.get(entity);}catch(IllegalAccessException | ClassCastException e) {}
		            privateLongField.setAccessible(false);
				}
			}
            ObjectMapper objectMapper = new ObjectMapper();
            entityData = objectMapper.writeValueAsString(entity);
        } catch (IllegalArgumentException | SecurityException |
            IOException e) {
            log.error("Exception while getting entity ID and content {}", e);
            return null;
        }
        auditedEntity.setEntityId(entityId);
        auditedEntity.setEntityValue(entityData);
        final AbstractAuditingEntity abstractAuditEntity;
        if (entity instanceof AbstractAuditingEntity) {
        	abstractAuditEntity = (AbstractAuditingEntity) entity;
        }else {
        	abstractAuditEntity = new AbstractAuditingEntity() {
			};
			if(user.isPresent()) {
				abstractAuditEntity.setLastModifiedBy(user.get());
				abstractAuditEntity.setCreatedBy(user.get());
			}
        }
        Date modifiedDate = new Date();
        if (EntityAuditAction.CREATE.equals(action)) {
            auditedEntity.setModifiedBy(abstractAuditEntity.getCreatedBy());
            auditedEntity.setModifiedDate(modifiedDate);
            auditedEntity.setCommitVersion(1);
        } else {
            auditedEntity.setModifiedBy(abstractAuditEntity.getLastModifiedBy());
            auditedEntity.setModifiedDate(modifiedDate);
        }
        log.trace("Audit Entity --> {} ", auditedEntity.toString());
        return auditedEntity;
    }
    
    public Validator getValidator() {
    	if(validatorFactory == null) {
            validatorFactory = Validation.buildDefaultValidatorFactory();
    	}
        return validatorFactory.getValidator();
    }
    
    public String getMessage(ConstraintViolation<?> constraintViolation) {
    	Locale locale = Locale.getDefault();
    	String[] message = constraintViolation.getMessage().split("\\|");
    	Object[] params = null;
    	if(message.length > 1) {
    		params = new Object[message.length-1];
    		for(int i=0; i<params.length; i++) {
    			params[i] = StringUtils.isNotEmpty(message[i+1])?message[i+1]:"";
    		}
    	}
    	return messageSource.getMessage(message[0], params, locale);
    }
}