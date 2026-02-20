package com.calapi.st.francis.assisi.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.calapi.st.francis.assisi.model.ConfirmationRecord;
import com.calapi.st.francis.assisi.model.dto.ConfirmationRecordDto;

@Mapper(componentModel = "spring")
public interface ConfirmationMapper {
	
	ConfirmationMapper INSTANCE = Mappers.getMapper(ConfirmationMapper.class);
	
	ConfirmationRecord dtoToEntity(ConfirmationRecordDto confirmationRecordDto);
	
	ConfirmationRecordDto entityToDto(ConfirmationRecord confirmationRecord);
	
	List<ConfirmationRecordDto> entityToDtoList(List<ConfirmationRecord> entities);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "recordUuid", ignore = true)
	void updateEntityFromDto(ConfirmationRecordDto dto, @MappingTarget ConfirmationRecord entity);

}
