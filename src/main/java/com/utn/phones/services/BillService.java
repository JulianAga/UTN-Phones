package com.utn.phones.services;

import com.utn.phones.model.Bill;
import com.utn.phones.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {
    private BillRepository billRepository;

    @Autowired
    public BillService(final BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public List<Bill> findAll() {
        return this.billRepository.findAll();
    }
}
