package co.id.app.aisa.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import co.id.app.aisa.domain.Permission;

public interface PermissionSearchRepository extends ElasticsearchRepository<Permission, Long>{

}
