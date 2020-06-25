package com.utn.phones.repositories;

import com.utn.phones.projections.MostCalled;
import com.utn.phones.model.Call;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {

  /***
   * Top 10 destinos mas llamados por el usuario
   * @param userId id client
   * @return Most called cities projection
   */
  @Query(value = "select cities.name as cityName, count(cities.id) as cant\n"
      + "from calls\n"
      + "inner join cities on calls.destiny_city = cities.id\n"
      + "inner join phone_lines on calls.id_origin_line=phone_lines.id\n"
      + "where phone_lines.user_id = ?1\n"
      + "group by cities.id\n"
      + "order by(Cant) desc\n"
      + "LIMIT 10;", nativeQuery = true)
  List<MostCalled> findMostCalledCities(Integer userId);

  /***
   * Consulta de facturas del usuario por rango de fecha
   * @param userId id client
   * @param Start Start date
   * @param End End date
   * @return Calls between dates
   */
  List<Call> findAllByOriginLineClientIdAndDateBetween(Integer userId, LocalDate Start,
      LocalDate End);

  /***
   * Consulta de llamadas por usuario
   * @param userId id client
   * @return calls from this client
   */
  List<Call> findAllByOriginLineClientId(Integer userId);


}
