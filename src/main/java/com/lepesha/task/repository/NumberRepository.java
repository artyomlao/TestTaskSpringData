package com.lepesha.task.repository;

import com.lepesha.task.model.Number;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberRepository extends CrudRepository<Number, String> {
}
