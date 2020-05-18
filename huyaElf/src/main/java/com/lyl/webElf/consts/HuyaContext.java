package com.lyl.webElf.consts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lyl.webElf.base.context.DiggerDriverContext;
import com.lyl.webElf.domain.User;

public class HuyaContext {
	public static Map<String,DiggerDriverContext> digDriverContextMap = new HashMap<>();
	public static List<User> users = new ArrayList<User>();

}
