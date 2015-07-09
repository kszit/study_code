package com.kszit.util.jdbc;

import java.sql.ResultSet;

public interface DbOperatorCallBack {
	Object toObj(ResultSet rs) throws Exception;
}
