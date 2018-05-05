package main;

import java.util.Base64;

public class Main {
	public static void main(String[] args) {
		Consumer c=new Consumer("name", "1001", "icici6279", 100000);
		Merchant m=new Merchant("mer01",5000,c);
		Payment pay=new Payment(m,"txn001",c);
		String msg=pay.payload_with_sha();
		System.out.println(msg);
		pay.payload_to_pg(msg);
		byte[] response=pay.payment(msg);	
		System.out.println(new String(response));
		
		/*String s="sdvsdv";
		Base64.Encoder enc=Base64.getEncoder();
		byte[] encoded=enc.encode(s.getBytes());
		System.out.println(encoded);
		Base64.Decoder dec=Base64.getDecoder();
		byte[] decoded=dec.decode(encoded);
		System.out.println(new String(decoded));*/
		
		
		
	}
}
