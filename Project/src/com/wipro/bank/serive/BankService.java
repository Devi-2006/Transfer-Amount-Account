package com.wipro.bank.serive;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.dao.BankDAO;
import com.wipro.bank.util.InsufficientFundsException;

public class BankService {
	public String checkBalance(String accountNumber) {
		BankDAO bank = new BankDAO();
		if(bank.validateAccount(accountNumber)) {
			float balance=bank.findBalance(accountNumber);
			return "BALANCE IS :"+balance ;
		}else {
			return "ACCOUNT NUMBER IS INVALID";
		}
		
	}
	public String transfer(TransferBean transferBean) {
		BankDAO bank = new BankDAO();
		     if(transferBean!=null) {
		    	 if(bank.validateAccount(transferBean.getFromAccountNumber())&&bank.validateAccount(transferBean.getToAccountNumber())) {
		    		 float senderbalance=bank.findBalance(transferBean.getFromAccountNumber());
		    		 float receiverbalance=bank.findBalance(transferBean.getToAccountNumber());
		    		 try{
		    			 if(senderbalance>=transferBean.getAmount()) {
		    				 senderbalance-=transferBean.getAmount();
		    				 receiverbalance+=transferBean.getAmount();
		    				 bank.updateBalance(transferBean.getFromAccountNumber(), senderbalance);
		    				 bank.updateBalance(transferBean.getToAccountNumber(), receiverbalance);
		    				 bank.transferMoney(transferBean);
		    				 return "SUCCESS";
		    				 
		    			 }else {
		    				 throw new InsufficientFundsException();
		    			 }
		    		 }catch(InsufficientFundsException e) {
		    			 return e.toString();
		    		 }
		    	 }else {
		    		 return "INVALID ACCOUNT";
		    	 }
		     }else {
		    	 return "INVALID";
		     }
	}


}
