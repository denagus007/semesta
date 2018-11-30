/**
 * @author cipta ageung
 */
package co.id.app.aisa.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "m_permission")
@JsonIgnoreProperties(ignoreUnknown = true)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "permission")
public class Permission implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "m_permission_pk_seq", sequenceName = "m_permission_pk_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_permission_pk_seq")
	private Long id;
	
	@Column(name = "service_name", unique=true, nullable=false)
	@Size(max = 50)
	@JsonProperty(value = "serviceName")
	private String serviceName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}