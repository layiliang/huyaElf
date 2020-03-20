package com.lyl.webElf.dao;

import java.util.List;

import com.lyl.webElf.domain.LiveItem;

public interface LiveItemDao {

	void insertList(List<LiveItem> liveItemList);

	void insert(LiveItem liveItem);

}
