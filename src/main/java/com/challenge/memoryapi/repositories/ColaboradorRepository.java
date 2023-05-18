package com.challenge.memoryapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.memoryapi.models.Colaborador;
import com.challenge.memoryapi.models.Projecto;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {
        List<Colaborador> findByProjecto(Projecto projecto);

        @Query(value = "SELECT cb.id, cb.projecto_id, cb.funcionario_id, fc.nome,"
                        + " fc.data_admicao, fc.remuneracao "
                        + " FROM colaboradores cb "
                        + " INNER JOIN funcionarios fc "
                        + " ON  fc.id = cb.funcionario_id "
                        + " WHERE EXTRACT( YEAR FROM data_admicao ) = :ano"
                        + " ORDER BY cb.id ", nativeQuery = true)
        List<Colaborador> findByFuncionarioAnoAdimicao(@Param("ano") Integer ano);

}
