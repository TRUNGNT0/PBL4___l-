package server.test;

import server.model.Bean.Account;
import server.model.DAO.AccountDAO;

public class AccountDAOTest {
	public static void main(String[] args) {
		AccountDAO accountDAO = new AccountDAO();
		Account ac = accountDAO.selectById(new Account("Sum", "khongbiet"));
		System.out.println(ac.toString());
	}
}
