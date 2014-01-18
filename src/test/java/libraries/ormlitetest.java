package test.java.libraries;
// http://ormlite.com/
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class ormlitetest {
	
	public static void main(String[] args) throws SQLException {
		// this uses h2 but you can change it to match your database 
		String databaseUrl = "jdbc:sqlite:test.db";
		// create a connection source to our database
		ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
		
		// instantiate the DAO to handle Account with String id
		Dao<Account,String> accountDao = DaoManager.createDao(connectionSource, Account.class); 
		
		// if you need to create the 'accounts' table make this call (dont call it twice though!)
		//TableUtils.createTable(connectionSource, Account.class);
		
		// create an instance of Account
        Account account = new Account();
        account.setName("Jim Coakley");
        account.setPassword("password123");
        
        // persist the account object to the database (dont call it twice though)
        accountDao.createIfNotExists(account);
        // else:
        account.setPassword("password123");
        accountDao.update(account);
        
        // retrieve the account from the database by its id field (name)
        Account account2 = accountDao.queryForId("Jim Coakley");
        System.out.println("Account: " + account2.getPassword());

        account.setPassword("PASSWORD");
        accountDao.update(account);

        // retrieve the account from the database by its id field (name)
        Account account3 = accountDao.queryForId("Jim Coakley");
        System.out.println("Account: " + account3.getPassword());
        
        // close the connection source
        connectionSource.close();
	}
}
