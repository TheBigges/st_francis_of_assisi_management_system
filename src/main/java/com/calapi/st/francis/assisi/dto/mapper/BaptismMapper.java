package com.calapi.st.francis.assisi.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.calapi.st.francis.assisi.model.BaptismRecord;
import com.calapi.st.francis.assisi.model.dto.BaptismRecordDto;

@Mapper
public interface BaptismMapper {
	
	BaptismMapper INSTANCE = Mappers.getMapper(BaptismMapper.class);
	
	BaptismRecord dtoToEntity(BaptismRecordDto baptismRecordDto);
	
	BaptismRecordDto entityToDto(BaptismRecord baptismRecord);
	
	List<BaptismRecordDto> entityToDtoList(List<BaptismRecord> entities);

}
