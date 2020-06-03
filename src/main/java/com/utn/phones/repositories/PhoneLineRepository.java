package com.utn.phones.repositories;

import com.utn.phones.model.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine,Integer> {

    @Query(value = "SELECT * FROM phone_lines AS pl INNER JOIN users AS u ON pl.user_id=u.id WHERE u.surname='Garcia' ", nativeQuery = true)
    List<PhoneLine> getGarciasPhoneLines();

}
