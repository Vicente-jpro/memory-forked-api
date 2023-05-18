package com.challenge.memoryapi.utils;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.challenge.memoryapi.dto.ColaboradorDto;

@Component
public class PaginacaoColaborador {

        public Page<ColaboradorDto> getPaginacao(List<ColaboradorDto> listaColaboradoresDto, Pageable pageable) {

                int start = (int) (pageable.getOffset() > listaColaboradoresDto.size() ? listaColaboradoresDto.size()
                                : pageable.getOffset());
                int end = (int) ((start + pageable.getPageSize()) > listaColaboradoresDto.size()
                                ? listaColaboradoresDto.size()
                                : (start + pageable.getPageSize()));

                Page<ColaboradorDto> rolesPageList = new PageImpl<>(listaColaboradoresDto.subList(start, end), pageable,
                                listaColaboradoresDto.size());
                return rolesPageList;

        }
}
