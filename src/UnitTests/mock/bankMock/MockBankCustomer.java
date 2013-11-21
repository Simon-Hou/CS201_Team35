package UnitTests.mock.bankMock;

import interfaces.BankCustomer;
import interfaces.BankTeller;
import UnitTests.mock.LoggedEvent;
import UnitTests.mock.Mock;

public class MockBankCustomer extends Mock implements BankCustomer{

	String name;
	
	public MockBankCustomer(String string) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	@Override
	public void msgHowCanIHelpYou(BankTeller b) {
		
		log.add(new LoggedEvent("Being helped"));
		
	}

	@Override
	public void msgAccountOpenedAnythingElse(int amount, int accountNumber,
			String passWord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDepositCompletedAnythingElse(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsWithdrawalAnythingElse(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgLoanApprovedAnythingElse(int amount, int accountNumber,
			int loanNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsMoneyAnythingElse(int amount) {
		// TODO Auto-generated method stub
		
	}

}
