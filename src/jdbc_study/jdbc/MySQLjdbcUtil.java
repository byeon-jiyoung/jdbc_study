package jdbc_study.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.Properties;

public class MySQLjdbcUtil {
	
//	public static void main(String[] args) throws SQLException {
//		Connection con = getConnection();
//		System.out.println(con);
//		System.out.println(con.getCatalog());
//	}
	
	public static Connection getConnection() {
		Connection con = null;
		try(InputStream is =  ClassLoader.getSystemResourceAsStream("db.properties")){ //is가 db.properties를 읽는다
			Properties prop = new Properties(); //properties 파일은 entry 속성에 key 값으로 데이터들을 구분
			prop.load(is);
			
//			for(Entry<Object, Object> e : prop.entrySet()){
//				System.out.printf("%s - %s%n", e.getKey(), e.getValue());
//			}
			
			con = DriverManager.getConnection(prop.getProperty("url"), prop); //데이터베이스에 연결★★
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (SQLException e1) {
			System.err.println("해당 데이터베이스 연결정보가 잘못되었슴 확인요망");
		}
		return con;
	}
}
