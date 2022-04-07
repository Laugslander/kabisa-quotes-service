package nl.robinlaugs.quotes.dto.mapper;

import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.dto.QuoteDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    QuoteDto mapEntityToDto(Quote entity);

    Quote mapDtoToEntity(QuoteDto dto);

}
