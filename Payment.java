package main;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.StringJoiner;

public class Payment {
	
	Merchant merchant;
	String payment_gateway_transaction_reference;
	String transaction_date;
	Consumer consumer;

	
	public Payment(Merchant merchant, String payment_gateway_transaction_reference,
			Consumer consumer) {
		super();
		this.merchant = merchant;
		this.payment_gateway_transaction_reference = payment_gateway_transaction_reference;
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		Date date=new Date();
		this.transaction_date =sdf.format(date);
		this.consumer=consumer;
	}
	
	public String payload_with_sha(){
		StringJoiner joiner = new StringJoiner("|");
		String amount=Integer.toString(this.merchant.amount);
		joiner.add(this.consumer.bank_ifsc_code).add(this.consumer.bank_account_number).add(Integer.toString(this.merchant.amount))
					.add(this.merchant.merchant_transaction_ref)
							.add( this.transaction_date.toString());
		String joinedString = joiner.toString();
		return joinedString;
		
		
	}
	public byte[] payload_to_pg(String msg){
		Base64.Encoder enc=Base64.getEncoder();
		byte[] encoded=enc.encode(msg.getBytes());
		return encoded;
	}
	public byte[] decoder(String encoded_msg){
		Base64.Decoder dec=Base64.getDecoder();
		byte[] decoded=dec.decode(encoded_msg);
		return decoded;
	}
	public byte[] payment(String encoded_msg){
		byte[] x=decoder(encoded_msg);
		String s=new String(x);
		String[] msg=s.split("|");
		String bank_ifsc_code=msg[0];
		String bank_account_number=msg[1];
		int amount=Integer.parseInt(msg[2]);
		String merchant_transaction_ref=msg[3];
		String transaction_date=msg[4];
		if(this.consumer.balance>amount){
			this.consumer.balance=this.consumer.balance-(amount);
			StringJoiner joiner = new StringJoiner("|");
			joiner.add("success").add(Integer.toString(amount))
					.add(merchant_transaction_ref)
							.add((CharSequence)transaction_date)
							.add(this.payment_gateway_transaction_reference);
			String joinedString = joiner.toString();
			return payload_to_pg(joinedString);
		}
		else{
			StringJoiner joiner = new StringJoiner("|");
			joiner.add("failure").add(Integer.toString(amount))
					.add(this.merchant.merchant_transaction_ref)
							.add((CharSequence) this.transaction_date)
							.add(this.payment_gateway_transaction_reference);
			String joinedString = joiner.toString();
			return payload_to_pg(joinedString);
		}
		
	}
}
