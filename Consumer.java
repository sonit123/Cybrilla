package main;

public class Consumer {
	String name;
	String bank_account_number;
	String bank_ifsc_code;
	int balance;
	public Consumer(String name, String bank_account_number, String bank_ifsc_code, int balance) {
		super();
		this.name = name;
		this.bank_account_number = bank_account_number;
		this.bank_ifsc_code = bank_ifsc_code;
		this.balance = balance;
	}
}
