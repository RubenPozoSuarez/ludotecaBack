package com.rubenpozo.ludoteca.loan;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rubenpozo.ludoteca.loan.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("select l from Loan l where (:game is null or l.game.id = :game) and (:client is null or l.client.id = :client)"
            + " and (:date is null or (l.startDate<=:date and l.repaymentDate>=:date))")
    List<Loan> find(@Param("game") Long gameId, @Param("client") Long clientId, @Param("date") LocalDate date);

    @Query("select l from Loan l where :game is l.game.id")
    List<Loan> findLoansByGame(@Param("game") Long gameId);

    @Query("select count(l) from Loan l where l.client.id is :client and (l.repaymentDate between :dateInitial and :dateEnd)")
    Long customerGamesByDate(@Param("client") Long clientId, @Param("dateInitial") LocalDate dateInitial,
            @Param("dateEnd") LocalDate dateEnd);
}
