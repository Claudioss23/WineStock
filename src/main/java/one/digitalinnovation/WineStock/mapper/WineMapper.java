package one.digitalinnovation.WineStock.mapper;

import one.digitalinnovation.WineStock.dto.WineDTO;
import one.digitalinnovation.WineStock.entity.Wine;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WineMapper {

    WineMapper INSTANCE = Mappers.getMapper(WineMapper.class);

    Wine toModel(WineDTO wineDTO);

    WineDTO toDTO(Wine wine);
}
