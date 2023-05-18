package com.challenge.memoryapi.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "projectos")
public class Projecto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    @ManyToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "funcionario_id")
    @JsonIgnore
    private Funcionario funcionario;

    @OneToMany(mappedBy = "projecto", cascade = { CascadeType.REMOVE, CascadeType.MERGE })
    private List<Colaborador> colaboradores;

}
