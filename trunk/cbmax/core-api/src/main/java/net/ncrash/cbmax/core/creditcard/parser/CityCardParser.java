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
public class CityCardParser implements CreditCardSmsParser {

	public List<CreditCardPaymentSms> paymentSmsParse(String mmsContent) {
		List<CreditCardPaymentSms> result = new ArrayList<CreditCardPaymentSms>();
		CreditCardPaymentSms creditCardPaymentSms;

		/*
			씨티카드 강대권님 승인내역 03월13일 19:47 이마트구로점 40,410원 일시불
		 */
		Pattern p = Pattern
		.compile("(씨티카드) (.*님) (승인|취소)(내역) (.*월.*일) ([\\d]*:[\\d]*) (.*) ([0-9,]*)(원) (일시불|.*$)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();

			creditCardPaymentSms.setSenderPhoneNumber("01027976877");
			creditCardPaymentSms.setSenderName(m.group(2));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(null);
			creditCardPaymentSms.setPayedWhenDate(m.group(5));
			creditCardPaymentSms.setPayedWhenTime(m.group(6));
			creditCardPaymentSms.setPayedWhere(m.group(7));
			creditCardPaymentSms.setPayedMoney(m.group(8));
			creditCardPaymentSms.setPayedCardType(null);
			creditCardPaymentSms.setPayedApproveType(m.group(3));
			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(10));

			result.add(creditCardPaymentSms);
		}

		return result;
	}

	public List<CreditCardAutoPaymentSms> autoPaymentSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CreditCardMonthlyPaymentsSms> monthlyPaymentsSmsParse(
			String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CreditCardCashServicesSms> cashServiceSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CreditCardNotificationSms> notificationSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CreditCardUnmanagedSms> unmanagedSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}
}
