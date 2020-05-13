package com.utn.phones.repositories;

import com.utn.phones.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<User,Integer> {
}
