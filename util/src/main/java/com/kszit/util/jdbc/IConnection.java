package com.kszit.util.jdbc;

import java.sql.Connection;

public interface IConnection {

	Connection connection() throws Exception;
	
	
	
}
