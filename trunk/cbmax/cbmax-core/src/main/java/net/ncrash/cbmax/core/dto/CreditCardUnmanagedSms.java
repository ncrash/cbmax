package net.ncrash.cbmax.core.dto;

/**
 * 신용카드 알수없음 sms dto
 * 
 * @author daekwon.kang
 * @since 2010. 5. 3.
 * @see
 */
public class CreditCardUnmanagedSms {
	private String senderPhoneNumber;
	private String unmanagedSms;

	public CreditCardUnmanagedSms() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSenderPhoneNumber() {
		return senderPhoneNumber;
	}

	public void setSenderPhoneNumber(String senderPhoneNumber) {
		this.senderPhoneNumber = senderPhoneNumber;
	}

	public String getUnmanagedSms() {
		return unmanagedSms;
	}

	public void setUnmanagedSms(String unmanagedSms) {
		this.unmanagedSms = unmanagedSms;
	}

	@Override
	public String toString() {
		return "CreditCardUnmanagedSms [senderPhoneNumber=" + senderPhoneNumber + ", unmanagedSms=" + unmanagedSms
				+ "]";
	}
}
