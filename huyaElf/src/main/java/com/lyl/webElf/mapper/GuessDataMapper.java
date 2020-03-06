package com.lyl.webElf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lyl.webElf.domain.GuessData;

public interface GuessDataMapper {
	void insert(GuessData guessData);

	void insertAll(@Param("guessDatas")List<GuessData> guessDatas);
}
