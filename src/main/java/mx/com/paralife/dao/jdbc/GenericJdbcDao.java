/**
 * 
 */
package mx.com.paralife.dao.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import snaq.db.ConnectionPool;

/**
 * @author Eduardo Barcenas
 *
 */
public abstract class GenericJdbcDao {
	
	private static Connection connection;
	
	private static ConnectionPool pool;
	
	static {
		try {
			createConnectionPool();
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*protected Connection getConnection() {
		System.out.println("- getting connection...");
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://192.168.49.2:1433;databaseName=PLM_EmisionCC_V100_Produccion";
		String username = "sa";
		String password = "Admin.Paralife";
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	protected static Connection getConnection() throws SQLException {
		System.out.println("- getting connection...");
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://148.243.71.73:1433;databaseName=paraLife_db_17092014";
		String username = "saparalife";
		String password = "saparalife";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection(url, username, password);
	}
	
	private static void createConnectionPool() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {
		Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		DriverManager.registerDriver(driver);
		String url = "jdbc:sqlserver://148.243.71.73;databaseName=paraLife_db";
		// Note: idleTimeout is specified in seconds.
		pool = new ConnectionPool(
				"local", 
				5, 
				10, 
				15, 
				3600, 
				url,
				"saparalife", 
				"saparalife");
	}
	
	protected static Connection getConnectionPooled() throws SQLException{
		return pool.getConnection(3600000); // in miliseconds
	}
	
	/**
     * Releases the connection.
     *
     * @param connection
     *            - the jdbc connection
     * @throws SQLException
     *             any problem trying to release the resources
     */
    protected void releaseDBResources(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }

    }

    /**
     * Releases the statement.
     *
     * @param statement
     *            - the jdbc statement
     * @throws SQLException
     *             any problem trying to release the resources
     */
    protected static void releaseDBResources(Statement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    /**
     * Releases the result set.
     *
     * @param resultSet
     *            - the jdbc result set
     * @throws SQLException
     *             any problem trying to release the resources
     */
    protected void releaseDBResources(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    /**
     * Releases the connection and the statement.
     *
     * @param connection
     *            - the jdbc connection
     * @param statement
     *            - the jdbc statement
     * @throws SQLException
     *             any problem trying to release the resources
     */
    protected void releaseDBResources(Statement statement, boolean closeConnection)
            throws SQLException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException se) {
            throw se;
        } finally {
            if (closeConnection && connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Releases the connection, the statement and the result set.
     *
     * @param connection
     *            - the jdbc connection
     * @param statement
     *            - the jdbc statement
     * @param resultSet
     *            - the jdbc result set
     * @throws SQLException
     *             any problem trying to release the resources
     */
    protected void releaseDBResources(Statement statement,
            ResultSet resultSet, boolean closeConnection) throws SQLException {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException se) {
            throw se;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se) {
                throw se;
            } finally {
                if (closeConnection && connection != null) {
                    connection.close();
                }
            }
        }
    }
    
	/**
	 * Releases the connection, and a list of statements.
	 * 
	 * @param connection
	 *            - the database connection
	 * @param statements
	 *            - the statements
	 */
    protected void releaseDBResources(Connection connection, List<Statement> statements) {
		int i = 0;
		while (!statements.isEmpty()) {
			// closing statements..
			Statement st = (Statement) statements.remove(i);
			try {
				if (st != null) {
					st.close();
					st = null;
				}
			} catch (SQLException sqle) {
				// placeholder
			}
		}

		// closing the Connection
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (SQLException sqle) {
			// placeholder
		}
	}
    
    public static void releasePool(){
    	System.out.println("releasing pool...");
    	pool.releaseForcibly();
    }
}
