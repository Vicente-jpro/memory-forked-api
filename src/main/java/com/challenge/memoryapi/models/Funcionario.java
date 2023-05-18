package com.challenge.memoryapi.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.challenge.memoryapi.Enums.StatusFuncionario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 30)
    private String nome;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "data_admicao")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAdmicao;

    @Column(name = "remuneracao", precision = 20, scale = 2)
    private BigDecimal remuneracao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_funcionario")
    private StatusFuncionario funcao;

    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.MERGE })
    @JsonIgnore
    private List<Projecto> projectos;

    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.MERGE })
    @JsonIgnore
    private List<Colaborador> colaboradores;
}
