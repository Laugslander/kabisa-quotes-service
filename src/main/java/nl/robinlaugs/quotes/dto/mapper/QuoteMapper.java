package nl.robinlaugs.quotes.dto.mapper;

import nl.robinlaugs.quotes.data.model.Quote;
import nl.robinlaugs.quotes.dto.QuoteDto;
import nl.robinlaugs.quotes.dto.StormConsultancyQuoteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    QuoteDto map(Quote entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "votes", ignore = true)
    Quote map(StormConsultancyQuoteDto dto);

}
