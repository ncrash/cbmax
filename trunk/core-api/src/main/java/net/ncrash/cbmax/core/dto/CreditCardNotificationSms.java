package net.ncrash.cbmax.core.dto;

/**
 * 신용카드 알림내역 dto
 * 
 * @author daekwon.kang
 * @since 2010. 5. 3.
 * @see
 */
public class CreditCardNotificationSms extends CreditCardSms {
	private String notificationSms;

	public CreditCardNotificationSms(String message) {
		super(message);
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
				+ this.getSenderPhoneNumber() + "]";
	}
}
