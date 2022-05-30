package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CourtCase;
import com.mycompany.myapp.service.dto.CourtCaseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourtCase} and its DTO {@link CourtCaseDTO}.
 */
@Mapper(componentModel = "spring")
public interface CourtCaseMapper extends EntityMapper<CourtCaseDTO, CourtCase> {}
