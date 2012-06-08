package net.ncrash.cbmax.core.dto;

/**
 * 신용카드 현금서비스 dto
 * 
 * @author daekwon.kang
 * @since 2010. 5. 3.
 * @see
 */
public class CreditCardCashServicesSms extends CreditCardSms {
	private String senderName;
	private String cardCompanyName;
	private String cardLastFourNumber;
	private String serviceWhenDate;
	private String serviceWhenTime;
	private String serviceMoney;

	public CreditCardCashServicesSms(String message) {
		super(message);
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getCardCompanyName() {
		return cardCompanyName;
	}

	public void setCardCompanyName(String cardCompanyName) {
		this.cardCompanyName = cardCompanyName;
	}

	public String getCardLastFourNumber() {
		return cardLastFourNumber;
	}

	public void setCardLastFourNumber(String cardLastFourNumber) {
		this.cardLastFourNumber = cardLastFourNumber;
	}

	public String getServiceWhenDate() {
		return serviceWhenDate;
	}

	public void setServiceWhenDate(String serviceWhenDate) {
		this.serviceWhenDate = serviceWhenDate;
	}

	public String getServiceWhenTime() {
		return serviceWhenTime;
	}

	public void setServiceWhenTime(String serviceWhenTime) {
		this.serviceWhenTime = serviceWhenTime;
	}

	public String getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(String serviceMoney) {
		this.serviceMoney = serviceMoney;
	}

	@Override
	public String toString() {
		return "CreditCardCashServicesSms [cardCompanyName=" + cardCompanyName + ", cardLastFourNumber="
				+ cardLastFourNumber + ", senderName=" + senderName + ", senderPhoneNumber=" + this.getSenderPhoneNumber()
				+ ", serviceMoney=" + serviceMoney + ", serviceWhenDate=" + serviceWhenDate + ", serviceWhenTime="
				+ serviceWhenTime + "]";
	}
}
