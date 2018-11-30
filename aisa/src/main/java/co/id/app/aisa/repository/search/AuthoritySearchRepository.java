package co.id.app.aisa.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import co.id.app.aisa.domain.Authority;

public interface AuthoritySearchRepository extends ElasticsearchRepository<Authority, String>{

}
