package org.lumenframework.demo.notes;

import java.sql.SQLException;
import java.util.Calendar;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class HelloNotes {

	public static void main(String[] args) throws Exception {
		ConnectionSource connectionSource = getConnection();

		// instantiate the dao
		Dao<Note, Long> noteDao = DaoManager.createDao(connectionSource, Note.class);

		// Once we have configured our database objects, we can use them to
		// persist a Note to the database and query for it from the database by
		// its ID:

		// create an instance of Note
		Note note = new Note();
		note.setTitle("yo " + Calendar.getInstance().getTimeInMillis());
		note.setBody("this is my <em style=\"color: green\">body</em>");

		// persist the note object to the database
		noteDao.create(note);

		// retrieve the note from the database by its id field (name)
		Note note2 = noteDao.queryForId(note.getId());
		System.out.println(note2);

		System.out.println("All Notes : %s " + noteDao.queryForAll());

		// close the connection source
		//connectionSource.close();
	}

	public static ConnectionSource getConnection() throws SQLException {
		if (connectionSource == null) {
			// this uses h2 by default but change to match your database
			String databaseUrl = "jdbc:h2:file:~/lumennote.db";

			// create a connection source to our database
			connectionSource = new JdbcPooledConnectionSource(databaseUrl);
			
			initSchema();
		}

		return connectionSource;
	}
	
	private static void initSchema() throws SQLException {
		// if you need to create the 'note', make it.
		TableUtils.createTableIfNotExists(connectionSource, Note.class);
	}
	
	private static ConnectionSource connectionSource;
}