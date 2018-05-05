package main;

public class Merchant {
	String merchant_transaction_ref;
	int amount;
	Consumer consumer_name;
	public Merchant(String merchant_transaction_ref, int amount, Consumer consumer_name) {
		super();
		this.merchant_transaction_ref = merchant_transaction_ref;
		this.amount = amount;
		this.consumer_name=consumer_name;
	}
	
}
