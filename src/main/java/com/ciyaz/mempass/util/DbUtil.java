package com.ciyaz.mempass.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {
	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取一个H2数据库连接
	 * 
	 * @return 连接对象
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			String url = "jdbc:h2:" + Config.WORK_DIR + "/data/" + Config.AUTH_ID + "/mempass";
			String user = "sa";
			String password = "";
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭资源
	 * 
	 * @param connection 连接
	 */
	public static void closeResource(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭资源
	 * 
	 * @param connection        连接
	 * @param preparedStatement 预编译SQL
	 */
	public static void closeResource(Connection connection, PreparedStatement preparedStatement) {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭资源
	 * 
	 * @param connection 连接
	 * @param statement  SQL
	 */
	public static void closeResource(Connection connection, Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭资源
	 * 
	 * @param connection        连接
	 * @param preparedStatement 预编译SQL
	 * @param resultSet         结果集
	 */
	public static void closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭资源
	 * 
	 * @param connection 连接
	 * @param resultSet  结果集
	 */
	public static void closeResource(Connection connection, ResultSet resultSet) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
