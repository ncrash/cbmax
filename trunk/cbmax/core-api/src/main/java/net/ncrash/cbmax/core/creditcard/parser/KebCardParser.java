package net.ncrash.cbmax.core.creditcard.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ncrash.cbmax.core.creditcard.CreditCardSmsParser;
import net.ncrash.cbmax.core.dto.CreditCardAutoPaymentSms;
import net.ncrash.cbmax.core.dto.CreditCardCashServicesSms;
import net.ncrash.cbmax.core.dto.CreditCardMonthlyPaymentsSms;
import net.ncrash.cbmax.core.dto.CreditCardNotificationSms;
import net.ncrash.cbmax.core.dto.CreditCardPaymentSms;
import net.ncrash.cbmax.core.dto.CreditCardUnmanagedSms;

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
			[외환카드]강대권님 65,550원 할부 11개월 승인 인터파크(쇼핑몰) 05/09 22:53
			[외환카드]강대권님 65,550원 승인취소 인터파크(쇼핑몰) 05/09 22:54
		 */
		Pattern p = Pattern.compile("\\[(외환카드)\\](.*님)\\s*([0-9,]*)(원) (승인|할부 (\\d*)개월 승인|승인취소) (.*) (\\d{2}/\\d{2}) (\\d{2}:\\d{2})");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();

			creditCardPaymentSms.setSenderName(m.group(2));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(null);
			creditCardPaymentSms.setPayedWhenDate(m.group(8));
			creditCardPaymentSms.setPayedWhenTime(m.group(9));
			creditCardPaymentSms.setPayedWhere(m.group(7));
			creditCardPaymentSms.setPayedMoney(m.group(3));
			creditCardPaymentSms.setPayedCardType(null);
			
			if("승인".equals(m.group(5)) || "승인취소".equals(m.group(5))) {
				creditCardPaymentSms.setPayedApproveType(m.group(5));
				creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(null);
				creditCardPaymentSms.setPayedInstallmentMonths(m.group(6));
			} else if (m.group(5) != null && m.group(5).indexOf("할부") > -1 && m.group(5).indexOf("승인") > -1) {
				creditCardPaymentSms.setPayedApproveType("승인");
				creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan("할부 11개월");
				creditCardPaymentSms.setPayedInstallmentMonths(m.group(6));
			}

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

	public List<CreditCardCashServicesSms> cashServiceSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CreditCardNotificationSms> notificationSmsParse(String mmsContent) {
		List<CreditCardNotificationSms> result = new ArrayList<CreditCardNotificationSms>();
		CreditCardNotificationSms creditCardNotificationSms;

		String[] notificationPatterns = { 
				"\\[외환카드\\].*님 \\d{2}/\\d{2} 카드대금 [0-9,]*원 결제\\(완납\\)\\. 감사합니다."
		};
		/*
			[외환카드]강대권님 05/01 카드대금 384,620원 결제(완납). 감사합니다.
		 */
		Pattern p;
		Matcher m;

		for (int i = 0; i < notificationPatterns.length; i++) {
			p = Pattern
			.compile(notificationPatterns[i]);
			m = p.matcher(mmsContent);
			
			while (m.find()) {
				creditCardNotificationSms = new CreditCardNotificationSms();
				
				creditCardNotificationSms.setSenderPhoneNumber("01027976877");
				creditCardNotificationSms.setNotificationSms(m.group());
				
				result.add(creditCardNotificationSms);
			}
		}
		
		return result;
	}

	public List<CreditCardUnmanagedSms> unmanagedSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}
}
