package net.ncrash.cbmax.core.dto;

/**
 * 신용카드 알수없음 sms dto
 * 
 * @author daekwon.kang
 * @since 2010. 5. 3.
 * @see
 */
public class CreditCardUnmanagedSms extends CreditCardSms {
	private String unmanagedSms;

	public CreditCardUnmanagedSms(String message) {
		super(message);
	}

	public String getUnmanagedSms() {
		return unmanagedSms;
	}

	public void setUnmanagedSms(String unmanagedSms) {
		this.unmanagedSms = unmanagedSms;
	}

	@Override
	public String toString() {
		return "CreditCardUnmanagedSms [senderPhoneNumber=" + this.getSenderPhoneNumber() + ", unmanagedSms=" + unmanagedSms
				+ "]";
	}
}
