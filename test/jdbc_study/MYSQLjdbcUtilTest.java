package jdbc_study;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import jdbc_study.jdbc.MySQLjdbcUtil;

public class MYSQLjdbcUtilTest {
	
	static final Logger log = LogManager.getLogger();
	
	@Test
	public void testConnection() throws SQLException {
		Connection con = MySQLjdbcUtil.getConnection();
		log.trace(String.format("Connected to database %s successfully.", con.getCatalog()));
		Assert.assertNotNull(con); //con이 null이면 접속 안된거고, null이면 녹색이 뜬다
	}

}
