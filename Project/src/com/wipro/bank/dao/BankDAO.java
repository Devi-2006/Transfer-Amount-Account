package com.wipro.bank.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.util.DBUtil;

public class BankDAO {

	public int generateSequenceNumber() {
		Connection connection=DBUtil.getDBConnection();
		String query="SELECT transacationId_seq.NEXTVAL from dual";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int seqNumber=rs.getInt(1);
			return seqNumber;
			} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	public boolean validateAccount(String accountNumber) {
		 Connection connection=DBUtil.getDBConnection();
		String query="Select * from ACCOUNT_TBL where Account_Number=?";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, accountNumber);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
					return true;
				}	
			return false;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
	}
	public float findBalance(String accountNumber) {
		
		if(validateAccount(accountNumber)) {
			Connection connection=DBUtil.getDBConnection();
			String query ="Select Balance from ACCOUNT_TBL where Account_Number=?";
			try {
				PreparedStatement ps=connection.prepareStatement(query);
				ps.setString(1, accountNumber);
				ResultSet rs=ps.executeQuery();
				rs.next();
				float accountBalance=rs.getFloat(1);
				return accountBalance;
					
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}return -1;
		
	}
	public boolean transferMoney(TransferBean transferBean) {
		transferBean.setTransactionID(generateSequenceNumber());
		Connection connection=DBUtil.getDBConnection();
		String query="INSERT INTO Transfer_TBL VALUES(?,?,?,?,?)";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setInt(1, transferBean.getTransactionID());
			ps.setString(2, transferBean.getFromAccountNumber());
			ps.setString(3, transferBean.getToAccountNumber());
			ps.setDate(4, new Date( transferBean.getDateOfTransaction().getTime()));
			ps.setFloat(5, transferBean.getAmount());
			int res=ps.executeUpdate();
			if(res>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public boolean updateBalance(String accountNumber,float newBalance) {
		if(validateAccount(accountNumber)) {
			Connection connection=DBUtil.getDBConnection();
			String query="Update ACCOUNT_TBL set Balance=? where Account_Number=?";
			try {
				PreparedStatement ps=connection.prepareStatement(query);
				ps.setString(2, accountNumber);
				ps.setFloat(1, newBalance);
				int res=ps.executeUpdate();
				if(res>0)
				return true;
				else 
					return false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return false;
	}
}