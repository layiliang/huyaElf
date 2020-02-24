package com.lyl.webElf.dao;

import java.util.List;

import com.lyl.webElf.domain.GuessData;

public interface GuessDataDao {

	void insertList(List<GuessData> guessDataPerGameList);

	void insert(GuessData guessData);

}
