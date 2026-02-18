package com.calapi.st.francis.assisi.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.calapi.st.francis.assisi.model.WeddingRecord;
import com.calapi.st.francis.assisi.model.dto.WeddingRecordDto;

@Mapper(componentModel = "spring")
public interface WeddingMapper {
	
	WeddingMapper INSTANCE = Mappers.getMapper(WeddingMapper.class);
	
	WeddingRecord dtoToEntity(WeddingRecordDto weddingRecordDto);
	
	WeddingRecordDto entityToDto(WeddingRecord weddingRecord);
	
	List<WeddingRecordDto> entityToDtoList(List<WeddingRecord> entities);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "coupleUuid", ignore = true)
	void updateEntityFromDto(WeddingRecordDto dto, @MappingTarget WeddingRecord entity);

}
