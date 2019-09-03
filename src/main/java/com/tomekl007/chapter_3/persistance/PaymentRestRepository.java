package com.tomekl007.chapter_3.persistance;

import com.tomekl007.chapter_3.domain.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "payment", path = "payment")
public interface PaymentRestRepository extends PagingAndSortingRepository<Payment, Long> {

    @Query("select t from Payment t where t.accountTo =:accountTo")
    List<Payment> findByAccountTo(@Param("accountTo") String accountTo);

}