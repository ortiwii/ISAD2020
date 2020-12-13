package ehu.isad.controller.db;

import ehu.isad.Services.SystemConection;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Properties;


public class DBKudeatzaile {

	Connection conn = null;

	private void conOpen(String dbpath) {
		try {
			this.datubaseInizializazioa();
			Class.forName("org.sqlite.JDBC").newInstance();
			String url = "jdbc:sqlite:"+ dbpath ;
			DriverManager.registerDriver(new org.sqlite.JDBC());
			conn = DriverManager.getConnection(url);

			System.out.println("Database connection established, "+dbpath);
		} catch (Exception e) {
			System.err.println("Cannot connect to database server " + e);
		}
	}



	private void conClose() {

		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		System.out.println("Database connection terminated");

	}

	private ResultSet query(Statement s, String query) {

		ResultSet rs = null;

		try {
			rs = s.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	// singleton patroia
	private static DBKudeatzaile instantzia = new DBKudeatzaile();

	private DBKudeatzaile()  {

		Properties properties = null;
		InputStream in = null;

		try {
			in = this.getClass().getResourceAsStream("/setup.properties");
			properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.conOpen(System.getProperty("user.home")+properties.getProperty("dbpath"));

	}

	public static DBKudeatzaile getInstantzia() {
		return instantzia;
	}

	public ResultSet execSQL(String query) {
		int count = 0;
		Statement s = null;
		ResultSet rs = null;

		try {
			s = (Statement) conn.createStatement();
			if (query.toLowerCase().indexOf("select") == 0) {
				// select agindu bat
				rs = this.query(s, query);

			} else {
				// update, delete, create agindu bat
				count = s.executeUpdate(query);
				System.out.println(count + " rows affected");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}
	private void datubaseInizializazioa (){

		String home = "";
		home = System.getProperty("user.home");

		File directorio = new File(home + "/.whatwebfx");
		try {
			if (!directorio.exists()) {
				if (directorio.mkdirs()) {
					// Si el archivo no existe es creado
					File file = new File(home + "/.whatwebfx/whatweb.sqlite");
					if (!file.exists()) {
						file.createNewFile();
					}
					FileUtils.copyURLToFile( new URL("http://elbarto.bar:3000/whatweb.sqlite"), file );
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}