package com.utn.phones.repositories;

import com.utn.phones.model.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine, Integer> {

  public PhoneLine findByNumber(String number);

  @Modifying
  @Query(value = "update phone_lines set suspended = true where id = ?1", nativeQuery = true)
  public void setSuspendedTrue(Integer id);

}
