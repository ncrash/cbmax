package net.ncrash.cbmax.core.dto;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder(value = { "senderPhoneNumber", "message" })
public class CreditCardSms {
	@JsonProperty
	private String senderPhoneNumber;
	
	@JsonProperty
	private String message;
	
	public CreditCardSms() {
		super();
	}

	public CreditCardSms(String message) {
		super();
		this.message = message;
	}

	public CreditCardSms(String senderPhoneNumber, String message) {
		super();
		this.senderPhoneNumber = senderPhoneNumber;
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
