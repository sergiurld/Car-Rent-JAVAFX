package org.example.faza222.repository;


import org.example.faza222.domain.Client;

public interface ClientRepositoryInterface extends Repository<Long, Client>{

    public Long findClientUniqueCode(String uniqueCode);
}
