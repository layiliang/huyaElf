package com.lyl.webElf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lyl.webElf.domain.LiveItem;

public interface LiveItemMapper {
	void insert(LiveItem liveItem);

	void insertAll(@Param("liveItems")List<LiveItem> liveItems);
}
