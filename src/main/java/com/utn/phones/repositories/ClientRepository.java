package com.utn.phones.repositories;

import com.utn.phones.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

  public Client findByDNI(String DNI);

  public Client findByUsername(String username);

}
