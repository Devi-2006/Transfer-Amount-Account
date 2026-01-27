package com.wipro.bank.main;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.serive.BankService;

public class BankMain {
	public static void main(String[] args) {
		BankService bankService =new BankService();
		 System.out.println("SENDER's OLD "+bankService.checkBalance("717821"));
		 System.out.println("RECEIVER's OLD "+bankService.checkBalance("717263"));
		 TransferBean transferBean = new TransferBean();
	     transferBean.setFromAccountNumber("717821");   
	     transferBean.setToAccountNumber("717263");     
	     transferBean.setAmount(200);
	     System.out.println("The Amount transfered is :"+transferBean.getAmount());
	     transferBean.setDateOfTransaction(new java.util.Date());
	     System.out.println(bankService.transfer(transferBean));
	     if(bankService.transfer(transferBean).equals("SUCCESS")) {
	     System.out.println("SENDER's NEW"+bankService.checkBalance("717821"));
	     System.out.println("RECEIVER's NEW"+bankService.checkBalance("717263"));}
	}
}
