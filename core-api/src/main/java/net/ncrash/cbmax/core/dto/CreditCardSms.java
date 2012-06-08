package net.ncrash.cbmax.core.dto;

public class CreditCardSms {
	private String senderPhoneNumber;
	private String message;

	public CreditCardSms(String message) {
		this.message = message;
	}

	public String getSenderPhoneNumber() {
		return senderPhoneNumber;
	}

	public void setSenderPhoneNumber(String senderPhoneNumber) {
		this.senderPhoneNumber = senderPhoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
