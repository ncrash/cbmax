package net.ncrash.cbmax.core.creditcard.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ncrash.cbmax.core.creditcard.CreditCardSmsParser;
import net.ncrash.cbmax.core.dto.CreditCardAutoPaymentSms;
import net.ncrash.cbmax.core.dto.CreditCardMonthlyPaymentsSms;
import net.ncrash.cbmax.core.dto.CreditCardPaymentSms;

/**
 * 
 * @author daekwon.kang
 * @since 2010. 4. 29.
 * @see
 */
public class KebCardParser implements CreditCardSmsParser {

	public List<CreditCardPaymentSms> paymentSmsParse(String mmsContent) {
		List<CreditCardPaymentSms> result = new ArrayList<CreditCardPaymentSms>();
		CreditCardPaymentSms creditCardPaymentSms;

		/*
			[외환카드]강대권님     15,720원 승인 택시(서울)한국스 04/08 01:22
		 */
		Pattern p = Pattern.compile("\\[(외환카드)\\](.*님)\\s*([0-9,]*)(원) (승인) (.*\\b) (\\d{2}/\\d{2}) (\\d{2}:\\d{2})");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();

			creditCardPaymentSms.setSenderName(m.group(2));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(null);
			creditCardPaymentSms.setPayedWhenDate(m.group(7));
			creditCardPaymentSms.setPayedWhenTime(m.group(8));
			creditCardPaymentSms.setPayedWhere(m.group(6));
			creditCardPaymentSms.setPayedMoney(m.group(3));
			creditCardPaymentSms.setPayedCardType(null);
			creditCardPaymentSms.setPayedApproveType(m.group(5));
			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(null);

			result.add(creditCardPaymentSms);
		}

		return result;
	}

	public List<CreditCardAutoPaymentSms> autoPaymentSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CreditCardMonthlyPaymentsSms> monthlyPaymentsSmsParse(String mmsContent) {
		List<CreditCardMonthlyPaymentsSms> result = new ArrayList<CreditCardMonthlyPaymentsSms>();
		CreditCardMonthlyPaymentsSms creditCardMonthlyPaymentsSms;

		/*
			[외환카드]강대권님 카드대금 384,620원의 결제일은 05/03입니다
		 */
		Pattern p = Pattern
				.compile("\\[(외환카드)\\](.*님) 카드대금 ([0-9,\\.]*)(원)의 결제일은 (\\d{2}/\\d{2})입니다");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardMonthlyPaymentsSms = new CreditCardMonthlyPaymentsSms();

			creditCardMonthlyPaymentsSms.setSenderPhoneNumber("01027976877");
			creditCardMonthlyPaymentsSms.setSenderName(m.group(2));
			creditCardMonthlyPaymentsSms.setCardCompanyName(m.group(1));
			creditCardMonthlyPaymentsSms.setCardLastFourNumber(null);
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsDate(m.group(5));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsMoney(m.group(3));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsCheckDate(null);
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsBankName(null);
			creditCardMonthlyPaymentsSms.setRemainedCardPoint(null);
			creditCardMonthlyPaymentsSms.setRemainedCardPointCheckDate(null);

			result.add(creditCardMonthlyPaymentsSms);
		}

		return result;
	}
}
