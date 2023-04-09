package com.sid.gl.repository;

import com.sid.gl.document.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PersonRepository extends ElasticsearchRepository <Person,String>{


}
