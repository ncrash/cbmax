package net.ncrash.cbmax.core.dto;

/**
 * 신용카드 월결제액 dto
 * 
 * @author daekwon.kang
 * @since 2010. 5. 3.
 * @see
 */
public class CreditCardMonthlyPaymentsSms {
	private String senderPhoneNumber;
	private String senderName;
	private String cardCompanyName;
	private String cardLastFourNumber;
	private String monthlyPaymentsDate;
	private String monthlyPaymentsMoney;
	private String monthlyPaymentsCheckDate;
	private String monthlyPaymentsBankName;
	private String remainedCardPoint;
	private String remainedCardPointCheckDate;

	public CreditCardMonthlyPaymentsSms() {
		super();
	}

	public String getMonthlyPaymentsBankName() {
		return monthlyPaymentsBankName;
	}

	public void setMonthlyPaymentsBankName(String monthlyPaymentsBankName) {
		this.monthlyPaymentsBankName = monthlyPaymentsBankName;
	}

	public String getSenderPhoneNumber() {
		return senderPhoneNumber;
	}

	public void setSenderPhoneNumber(String senderPhoneNumber) {
		this.senderPhoneNumber = senderPhoneNumber;
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

	public String getMonthlyPaymentsDate() {
		return monthlyPaymentsDate;
	}

	public void setMonthlyPaymentsDate(String monthlyPaymentsDate) {
		this.monthlyPaymentsDate = monthlyPaymentsDate;
	}

	public String getMonthlyPaymentsMoney() {
		return monthlyPaymentsMoney;
	}

	public void setMonthlyPaymentsMoney(String monthlyPaymentsMoney) {
		this.monthlyPaymentsMoney = monthlyPaymentsMoney;
	}

	public String getMonthlyPaymentsCheckDate() {
		return monthlyPaymentsCheckDate;
	}

	public void setMonthlyPaymentsCheckDate(String monthlyPaymentsCheckDate) {
		this.monthlyPaymentsCheckDate = monthlyPaymentsCheckDate;
	}

	public String getRemainedCardPoint() {
		return remainedCardPoint;
	}

	public void setRemainedCardPoint(String remainedCardPoint) {
		this.remainedCardPoint = remainedCardPoint;
	}

	public String getRemainedCardPointCheckDate() {
		return remainedCardPointCheckDate;
	}

	public void setRemainedCardPointCheckDate(String remainedCardPointCheckDate) {
		this.remainedCardPointCheckDate = remainedCardPointCheckDate;
	}

	@Override
	public String toString() {
		return "CreditCardMonthlyPaymentsSms [cardCompanyName=" + cardCompanyName + ", cardLastFourNumber="
				+ cardLastFourNumber + ", monthlyPaymentsBankName=" + monthlyPaymentsBankName
				+ ", monthlyPaymentsCheckDate=" + monthlyPaymentsCheckDate + ", monthlyPaymentsDate="
				+ monthlyPaymentsDate + ", monthlyPaymentsMoney=" + monthlyPaymentsMoney + ", remainedCardPoint="
				+ remainedCardPoint + ", remainedCardPointCheckDate=" + remainedCardPointCheckDate + ", senderName="
				+ senderName + ", senderPhoneNumber=" + senderPhoneNumber + "]";
	}
}
