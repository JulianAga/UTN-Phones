package com.utn.phones.repositories;

import com.utn.phones.model.Bill;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

  List<Bill> findAllByClientIdAndDateBetween(Integer client, LocalDate Start,
      LocalDate End);
}
