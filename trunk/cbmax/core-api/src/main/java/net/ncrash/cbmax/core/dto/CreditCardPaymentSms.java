package net.ncrash.cbmax.core.dto;

/**
 * 신용카드 사용내역 dto
 * 
 * @author daekwon.kang
 * @since 2010. 4. 29.
 * @see
 */
public class CreditCardPaymentSms {
	private String senderPhoneNumber;
	private String senderName;
	private String cardCompanyName;
	private String cardLastFourNumber;
	/**
	 * 결제일자
	 * 
	 * 05/09, 04월05일
	 */
	private String payedWhenDate;
	private String payedWhenTime;
	private String payedWhere;
	private String payedMoney;
	private String payedCardType;
	private String payedApproveType;
	private String payedLumpSumOrInstallmentPlan;
	private String payedInstallmentMonths;

	public CreditCardPaymentSms() {
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

	public String getPayedWhenTime() {
		return payedWhenTime;
	}

	public void setPayedWhenTime(String payedWhenTime) {
		this.payedWhenTime = payedWhenTime;
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

	public String getPayedCardType() {
		return payedCardType;
	}

	public void setPayedCardType(String payedCardType) {
		this.payedCardType = payedCardType;
	}

	public String getPayedApproveType() {
		return payedApproveType;
	}

	public void setPayedApproveType(String payedApproveType) {
		this.payedApproveType = payedApproveType;
	}

	public String getPayedLumpSumOrInstallmentPlan() {
		return payedLumpSumOrInstallmentPlan;
	}

	public void setPayedLumpSumOrInstallmentPlan(
			String payedLumpSumOrInstallmentPlan) {
		this.payedLumpSumOrInstallmentPlan = payedLumpSumOrInstallmentPlan;
	}

	public void setPayedInstallmentMonths(String payedInstallmentMonths) {
		this.payedInstallmentMonths = payedInstallmentMonths;
	}

	public String getPayedInstallmentMonths() {
		return payedInstallmentMonths;
	}

	@Override
	public String toString() {
		return "CreditCardPaymentSms [cardCompanyName=" + cardCompanyName
				+ ", cardLastFourNumber=" + cardLastFourNumber
				+ ", payedApproveType=" + payedApproveType + ", payedCardType="
				+ payedCardType + ", payedInstallmentMonths="
				+ payedInstallmentMonths + ", payedLumpSumOrInstallmentPlan="
				+ payedLumpSumOrInstallmentPlan + ", payedMoney=" + payedMoney
				+ ", payedWhenDate=" + payedWhenDate + ", payedWhenTime="
				+ payedWhenTime + ", payedWhere=" + payedWhere
				+ ", senderName=" + senderName + ", senderPhoneNumber="
				+ senderPhoneNumber + "]";
	}
}
