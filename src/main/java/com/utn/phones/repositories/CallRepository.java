package com.utn.phones.repositories;

import com.utn.phones.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {



}
