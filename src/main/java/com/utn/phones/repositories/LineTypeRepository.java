package com.utn.phones.repositories;

import com.utn.phones.model.LineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineTypeRepository extends JpaRepository<LineType,Integer> {
}
