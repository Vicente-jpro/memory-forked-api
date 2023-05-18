package com.challenge.memoryapi.utils;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.challenge.memoryapi.dto.ProjectoDto;

@Component
public class PaginacaoProject {
        public Page<ProjectoDto> getPaginacao(List<ProjectoDto> listaProjectosDto, Pageable pageable) {

                int start = (int) (pageable.getOffset() > listaProjectosDto.size() ? listaProjectosDto.size()
                                : pageable.getOffset());
                int end = (int) ((start + pageable.getPageSize()) > listaProjectosDto.size() ? listaProjectosDto.size()
                                : (start + pageable.getPageSize()));

                Page<ProjectoDto> listaPaginada = new PageImpl<>(listaProjectosDto.subList(start, end), pageable,
                                listaProjectosDto.size());
                return listaPaginada;

        }

}
