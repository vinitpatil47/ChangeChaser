package com.barclays.repository;

import org.springframework.data.repository.CrudRepository;

import com.barclays.model.ChangeRequest;

public interface ChangeRequestRepository extends CrudRepository<ChangeRequest, String> {

}
