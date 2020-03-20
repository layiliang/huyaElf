package com.lyl.webElf.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lyl.webElf.dao.LiveItemDao;
import com.lyl.webElf.domain.LiveItem;
import com.lyl.webElf.mapper.LiveItemMapper;
@Component("liveItemDao")
public class LiveItemDaoImpl implements LiveItemDao {
	@Autowired
	private LiveItemMapper liveItemMapper; 
	@Override
	public void insertList(List<LiveItem> liveItemPerGameList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(LiveItem liveItem) {
		// TODO Auto-generated method stub
		liveItemMapper.insert(liveItem);
	}

}
