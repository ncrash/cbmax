package net.ncrash.cbmax.core.dto;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 신용카드 사용내역 dto
 * 
 * @author daekwon.kang
 * @since 2010. 4. 29.
 * @see
 */
public class CreditCardPaymentSms extends CreditCardSms {
	@JsonProperty
	private String senderName;
	@JsonProperty
	private String cardCompanyName;
	@JsonProperty
	private String cardLastFourNumber;
	/**
	 * 결제일자
	 * 
	 * 05/09, 04월05일
	 */
	@JsonProperty
	private String payedWhenDate;
	@JsonProperty
	private String payedWhenTime;
	@JsonProperty
	private String payedWhere;
	@JsonProperty
	private String payedMoney;
	@JsonProperty
	private String payedCardType;
	@JsonProperty
	private String payedApproveType;
	@JsonProperty
	private String payedLumpSumOrInstallmentPlan;
	@JsonProperty
	private String payedInstallmentMonths;
	@JsonProperty
	private String unknownMessage;

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

	public String getPayedInstallmentMonths() {
		return payedInstallmentMonths;
	}

	public void setPayedInstallmentMonths(String payedInstallmentMonths) {
		this.payedInstallmentMonths = payedInstallmentMonths;
	}

	public String getUnknownMessage() {
		return unknownMessage;
	}

	public void setUnknownMessage(String unknownMessage) {
		this.unknownMessage = unknownMessage;
	}

}
