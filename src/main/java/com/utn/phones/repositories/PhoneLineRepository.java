package com.utn.phones.repositories;

import com.utn.phones.model.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine,Integer> {
}
