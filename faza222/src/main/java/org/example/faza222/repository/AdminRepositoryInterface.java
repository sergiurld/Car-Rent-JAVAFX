package org.example.faza222.repository;


import org.example.faza222.domain.Admin;

public interface AdminRepositoryInterface extends Repository<Long, Admin>{
    public Long findAdminUniqueCode(String uniqueCode);

}
