package net.ncrash.cbmax.core.dto;

import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;

public class CreditCardReceiptSms {
	private String senderPhoneNumber;
	private String senderName;
	private String cardCompanyName;
	private String cardLastFourNumber;
	private String payedWhenDate;
	private String payedWhenTime;
	private String payedWhere;
	private String payedMoney;
	private String payedCardType;
	private String payedApproveType;
	private String payedLumpSumOrInstallmentPlan;

	public CreditCardReceiptSms(String cardType, Matcher m) {
		super();

		this.setSenderPhoneNumber("01027976877");

		if ("lotte".equals(cardType)) {
			this.setSenderName(m.group(2));
			this.setCardCompanyName(m.group(1));
			this.setCardLastFourNumber(null);
			this.setPayedWhenDate(m.group(6));
			this.setPayedWhenTime(m.group(7));
			this.setPayedWhere(m.group(8));
			this.setPayedMoney(m.group(3));
			this.setPayedCardType(m.group(1));
			this.setPayedApproveType(m.group(1));
			this.setPayedLumpSumOrInstallmentPlan(m.group(5));
		} else if ("city".equals(cardType)) {
			this.setSenderName(m.group(2));
			this.setCardCompanyName(m.group(1));
			this.setCardLastFourNumber(null);
			this.setPayedWhenDate(m.group(5));
			this.setPayedWhenTime(m.group(6));
			this.setPayedWhere(m.group(7));
			this.setPayedMoney(m.group(8));
			this.setPayedCardType(null);
			this.setPayedApproveType(m.group(3));
			this.setPayedLumpSumOrInstallmentPlan(m.group(10));
		} else if ("bc".equals(cardType)) {
			this.setSenderName(m.group(6));
			this.setCardCompanyName(m.group(4));
			this.setCardLastFourNumber(m.group(5));
			this.setPayedWhenDate(m.group(7));
			this.setPayedWhenTime(m.group(8));
			this.setPayedWhere(m.group(9));
			this.setPayedMoney(m.group(2));
			this.setPayedCardType(null);
			this.setPayedApproveType(null);
			this.setPayedLumpSumOrInstallmentPlan(null);
		} else if ("kb".equals(cardType)) {
			this.setSenderName(m.group(3));
			this.setCardCompanyName(m.group(1));
			this.setCardLastFourNumber(null);
			this.setPayedWhenDate(m.group(4));
			this.setPayedWhenTime(m.group(5));
			this.setPayedWhere(m.group(6));
			this.setPayedMoney(m.group(7));
			this.setPayedCardType(m.group(2));
			this.setPayedApproveType(null);
			this.setPayedLumpSumOrInstallmentPlan(null);
		} else if ("shinhan".equals(cardType)) {
			this.setSenderName(m.group(3));
			this.setCardCompanyName(m.group(1));
			this.setCardLastFourNumber(null);
			this.setPayedWhenDate(m.group(4));
			this.setPayedWhenTime(m.group(5));
			this.setPayedWhere(m.group(9));
			this.setPayedMoney(m.group(6));
			this.setPayedCardType(null);
			this.setPayedApproveType(m.group(2));
			this.setPayedLumpSumOrInstallmentPlan(m.group(8));
		} else if ("keb".equals(cardType)) {
			this.setSenderName(m.group(2));
			this.setCardCompanyName(m.group(1));
			this.setCardLastFourNumber(null);
			this.setPayedWhenDate(m.group(7));
			this.setPayedWhenTime(m.group(8));
			this.setPayedWhere(m.group(6));
			this.setPayedMoney(m.group(3));
			this.setPayedCardType(null);
			this.setPayedApproveType(m.group(5));
			this.setPayedLumpSumOrInstallmentPlan(null);
		} else if ("hyundai".equals(cardType)) {
			this.setSenderName(m.group(3));
			this.setCardCompanyName(m.group(1));
			this.setCardLastFourNumber(null);
			this.setPayedWhenDate(null);
			this.setPayedWhenTime(m.group(4));
			this.setPayedWhere(m.group(9));
			this.setPayedMoney(m.group(5));
			this.setPayedCardType(m.group(2));
			this.setPayedApproveType(m.group(8));
			this.setPayedLumpSumOrInstallmentPlan(m.group(7));
		}
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

	public void setPayedLumpSumOrInstallmentPlan(String payedLumpSumOrInstallmentPlan) {
		this.payedLumpSumOrInstallmentPlan = payedLumpSumOrInstallmentPlan;
	}

	@Override
	public String toString() {
		return "CreditCardReceiptSms [cardCompanyName=" + cardCompanyName
				+ ", cardLastFourNumber=" + cardLastFourNumber
				+ ", payedApproveType=" + payedApproveType + ", payedCardType="
				+ payedCardType + ", payedLumpSumOrInstallmentPlan="
				+ payedLumpSumOrInstallmentPlan + ", payedMoney=" + payedMoney
				+ ", payedWhenDate=" + payedWhenDate + ", payedWhenTime="
				+ payedWhenTime + ", payedWhere=" + payedWhere
				+ ", senderName=" + senderName + ", senderPhoneNumber="
				+ senderPhoneNumber + "]";
	}
}
