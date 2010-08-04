package net.ncrash.cbmax.core.dto;

/**
 * 신용카드 자동납부내역 dto
 * 
 * @author daekwon.kang
 * @since 2010. 4. 29.
 * @see
 */
public class CreditCardAutoPaymentSms {
	private String senderPhoneNumber;
	private String senderName;
	private String cardCompanyName;
	private String cardLastFourNumber;
	private String payedWhenDate;
	private String payedWhere;
	private String payedMoney;

	public CreditCardAutoPaymentSms() {
		super();
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

	public String getPayedWhenDate() {
		return payedWhenDate;
	}

	public void setPayedWhenDate(String payedWhenDate) {
		this.payedWhenDate = payedWhenDate;
	}

	public String getPayedWhere() {
		return payedWhere;
	}

	public void setPayedWhere(String payedWhere) {
		this.payedWhere = payedWhere;
	}

	public String getPayedMoney() {
		return payedMoney;
	}

	public void setPayedMoney(String payedMoney) {
		this.payedMoney = payedMoney;
	}

	@Override
	public String toString() {
		return "CreditCardAutoPaymentSms [cardCompanyName=" + cardCompanyName
				+ ", cardLastFourNumber=" + cardLastFourNumber
				+ ", payedMoney=" + payedMoney + ", payedWhenDate="
				+ payedWhenDate + ", payedWhere=" + payedWhere
				+ ", senderName=" + senderName + ", senderPhoneNumber="
				+ senderPhoneNumber + "]";
	}
}
