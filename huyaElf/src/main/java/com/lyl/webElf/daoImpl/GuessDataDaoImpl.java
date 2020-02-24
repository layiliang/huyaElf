package com.lyl.webElf.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lyl.webElf.dao.GuessDataDao;
import com.lyl.webElf.domain.GuessData;
import com.lyl.webElf.mapper.GuessDataMapper;
@Component("guessDataDao")
public class GuessDataDaoImpl implements GuessDataDao {
	@Autowired
	private GuessDataMapper guessDataMapper; 
	@Override
	public void insertList(List<GuessData> guessDataPerGameList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(GuessData guessData) {
		// TODO Auto-generated method stub
		guessDataMapper.insert(guessData);
	}

}
