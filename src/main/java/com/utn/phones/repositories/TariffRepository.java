package com.utn.phones.repositories;

import com.utn.phones.model.Tariff;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Integer> {

  List<Tariff> findByOriginCityNameAndDestinyCityName(String originCity, String destinyCity);
}
