package com.utn.phones.services;

import com.utn.phones.model.Call;
import com.utn.phones.model.Client;
import com.utn.phones.projections.ClientsMoreThanThree;
import com.utn.phones.repositories.CallRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallService {

  private CallRepository callRepository;

  @Autowired
  public CallService(CallRepository callRepository) {
    this.callRepository = callRepository;
  }

  public List<Call> findAll() {
    return callRepository.findAll();
  }
/*
  public List<ClientsMoreThanThree> findClientsWithMoreThanThree(){
    return this.callRepository.findAll().stream().filter(
        call -> call.getOriginLine().getClient()
    )
  }
*/
}
