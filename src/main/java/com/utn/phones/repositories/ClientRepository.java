package com.utn.phones.repositories;

import com.utn.phones.model.Client;
import com.utn.phones.projections.ClientsMoreThanThree;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {


  @Query(value = "select c.name, c.surname, count(ca.id_origin_line) as 'totalCalls' from\n"
      + "        users as c \n"
      + "    inner join\n"
      + "        phone_lines as phone \n"
      + "\t\ton phone.user_id = c.id \n"
      + "    inner join\n"
      + "        calls as ca\n"
      + "\t\ton ca.id_origin_line = phone.id \n"
      + "        group by c.name;", nativeQuery = true)
  public List<ClientsMoreThanThree> getClientsMoreThanThreeCalls();

}
