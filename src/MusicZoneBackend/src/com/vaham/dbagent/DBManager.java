package com.vaham.dbagent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Haifa
 * Class for managing the connection pool for database connection.
 */
public class DBManager {

	private Connection conn = null;
	// private Properties queryFile = null;
	// private static final String QUERY_FILE_NAME="query.properties" ;
	private Logger logger = LogManager.getLogger(DBManager.class.getName());

	{
		loadProperties();
	}
	private static Properties queryFile = new Properties();

	// Set config file.
	private static void loadProperties() {
		// get class loader
		ClassLoader loader = DBManager.class.getClassLoader();
		if (null == loader)
			loader = ClassLoader.getSystemClassLoader();

		String propFile = "/config.properties";
		java.net.URL url = loader.getResource(propFile);
		try {
			queryFile.load(url.openStream());
		} catch (Exception e) {
			System.err.println("Can't load configuration file:" + propFile);
		}
	}

	/**
	 * Initializes the connection to the database using the connection pool 
	 * configuration. 
	 */
	public void init() {
		Connection connection = null;
		try {

			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("music_zone");
			connection = ds.getConnection();
			if (connection == null) {
				// System.out.println("The connection failed");
				logger.debug("Failed to get the db connection.");
			}
			conn = connection;

		} catch (SQLException e) {
			// System.out.println("Error: " + e.getMessage()) ;
			logger.debug(e.getMessage());
			logger.error("SQLException", e);
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
			logger.error("NamingException", e);
			e.printStackTrace();
		}

	}

	/**
	 * closes the connection
	 */
	public void finalize() {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.debug(e.getMessage());
				logger.error("SQLException", e);
				e.printStackTrace();
			}
	}

	/**
	 * To start a transaction
	 */
	public void startTransaction() {

		try {
			conn.setAutoCommit(false);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
			logger.error("SQLException", e);
			e.printStackTrace();
		}

	}

	/**
	 * To end a transaction
	 * Commits the database operation to the database 
	 * and rolls back in case of failure.
	 */
	public void endTransaction() {

		try {
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
			logger.error("SQLException", e);
			try {
				if (conn != null) {

					// System.err.print("Transaction is being rolled back");
					logger.debug("Transaction is being rolled back.");
					conn.rollback();
				}
			} catch (SQLException e1) {
				logger.debug(e1.getMessage());
				logger.error("SQLException", e1);
				e1.printStackTrace();
			}
		} finally {
			if (conn != null)
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.debug(e.getMessage());
					logger.error("SQLException", e);
					e.printStackTrace();
				}
		}

	}

	/**
	 * Executes the DML statements like insert, update on the database.
	 * Reads the queries from the properties file.
	 * @param queryid
	 * @param parameterList
	 * @return the number of rows affected
	 * @throws SQLException
	 *             it can be used to delete, update and insert values
	 */
	public int executeSQL(String queryid, List<String> parameterList) {
		PreparedStatement sqlStmt = null;

		int NumberORowsAffected = 0;
		int n = 1;
		Iterator<String> iter = parameterList.iterator();
		try {
			// *************READ from Config file ********//

			String query = queryFile.getProperty(queryid);
			// System.err.println("QUERY : " + query );
			logger.debug("Loaded QUERY from property: " + query);
			// *************Process Query ********//
			sqlStmt = conn.prepareStatement(query);

			// replace ? with parameters
			while (iter.hasNext()) {
				sqlStmt.setString(n, (String) iter.next());
				n++;
			}
			logger.debug("QUERY after set parameter : " + sqlStmt.toString());

			NumberORowsAffected = sqlStmt.executeUpdate();
			// System.err.println("Rows Affected : " + NumberORowsAffected);
			logger.debug("Rows Affected : " + NumberORowsAffected);
		} catch (SQLException sqlexception) {
			logger.debug(sqlexception.getMessage());
			logger.error("SQLException", sqlexception);
			sqlexception.printStackTrace();
		} finally {

			if (sqlStmt != null) {
				try {
					sqlStmt.close();
				} catch (SQLException e) {
					logger.debug(e.getMessage());
					logger.error("SQLException", e);
					e.printStackTrace();
				}
			}

		}
		return NumberORowsAffected;

	}

	/**
	 * Reads the query from the property file and performs the 
	 * select operation on the database.
	 * @param queryid
	 * @param parameterList
	 * @return A list of the fetched data
	 * @throws SQLException
	 *             it can be used only to select data
	 */
	public List<Map<String, Object>> getQueryResult(String queryid,
			List<String> parameterList)

	{
		PreparedStatement sqlStmt = null;
		ResultSet rs = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Iterator<String> iter = parameterList.iterator();
		int n = 1;

		try {
			// *************READ from Config file ********//
			String query = queryFile.getProperty(queryid);
			// System.out.println("QUERY : " + query );
			logger.debug("Loaded QUERY from property : " + query);
			sqlStmt = conn.prepareStatement(query);

			// *************Replace ? with parameters ********//
			while (iter.hasNext()) {
				sqlStmt.setString(n, (String) iter.next());
				n++;
			}
			logger.debug("QUERY after set parameter : " + sqlStmt.toString());

			rs = sqlStmt.executeQuery();
			// *************Writing the output ********//
			if (rs != null) {
				ResultSetMetaData md = rs.getMetaData();
				int columnCount = md.getColumnCount();

				while (rs.next()) {
					Map<String, Object> columns = new LinkedHashMap<String, Object>();

					for (int i = 1; i <= columnCount; i++) {
						columns.put(md.getColumnLabel(i), rs.getObject(i));
					}
					result.add(columns);
				}
			}
		} catch (SQLException sqlexcep) {
			logger.debug(sqlexcep.getMessage());
			logger.error("SQLException", sqlexcep);
			sqlexcep.printStackTrace();
		} finally {

			try {
				if (sqlStmt != null)
					sqlStmt.close();
			} catch (SQLException e) {
				logger.debug(e.getMessage());
				logger.error("SQLException", e);
				e.printStackTrace();
			}
		}
		return result;

	}
}
