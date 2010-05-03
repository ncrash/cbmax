package net.ncrash.cbmax.core.dto;

/**
 * 신용카드 알림내역 dto
 * 
 * @author daekwon.kang
 * @since 2010. 5. 3.
 * @see
 */
public class CreditCardNotificationSms {
	private String senderPhoneNumber;
	private String notificationSms;

	public CreditCardNotificationSms() {
		super();
	}

	public String getSenderPhoneNumber() {
		return senderPhoneNumber;
	}

	public void setSenderPhoneNumber(String senderPhoneNumber) {
		this.senderPhoneNumber = senderPhoneNumber;
	}

	public String getNotificationSms() {
		return notificationSms;
	}

	public void setNotificationSms(String notificationSms) {
		this.notificationSms = notificationSms;
	}

	@Override
	public String toString() {
		return "CreditCardNotificationSms [notificationSms=" + notificationSms + ", senderPhoneNumber="
				+ senderPhoneNumber + "]";
	}
}
