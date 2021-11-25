package com.archive.sukjulyo.util.interfaces;

import java.util.List;
import java.util.stream.Collectors;

public interface DtoInterface <ENT_T> {

	ENT_T toEntity();

	static <ENT_T, DTO_T extends DtoInterface<ENT_T>> List<ENT_T> toEntityList(List<DTO_T> dtoList) {
		return dtoList.stream()
				.map(DTO_T::toEntity)
				.collect(Collectors.toList());
	}

}
