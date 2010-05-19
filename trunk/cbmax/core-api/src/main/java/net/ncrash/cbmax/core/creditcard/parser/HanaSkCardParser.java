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
 * @since 2010. 5. 10.
 * @see
 */
public class HanaSkCardParser implements CreditCardSmsParser {

	public List<CreditCardPaymentSms> paymentSmsParse(String mmsContent) {
		List<CreditCardPaymentSms> result = new ArrayList<CreditCardPaymentSms>();
		CreditCardPaymentSms creditCardPaymentSms;

		/*
			하나SK카드(1*8*)강대권님 05/09 22:18 일시불/ 56,050원/승인/ (주)인터파크INT
			하나SK카드(1*8*)강대권님 05/09 22:19 일시불/ 56,050원/승인취소/ (주)인터파크INT
			하나SK카드(1*8*)강대권님 05/09 22:21 할부/11개월 149,150원/승인/ (주)인터파크INT
		 */
		Pattern p = Pattern
				.compile("(하나SK카드)(\\(.{4}\\))(.*님) (\\d{2}/\\d{2}) (\\d{2}:\\d{2}) (일시불|할부)/((\\d*)개월|) ([0-9,]*)(원)/(승인|승인취소)/ (.*\\b)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();

			creditCardPaymentSms.setSenderName(m.group(3));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(m.group(2));
			creditCardPaymentSms.setPayedWhenDate(m.group(4));
			creditCardPaymentSms.setPayedWhenTime(m.group(5));
			creditCardPaymentSms.setPayedWhere(m.group(12));
			creditCardPaymentSms.setPayedMoney(m.group(9));
			creditCardPaymentSms.setPayedCardType(null);
			creditCardPaymentSms.setPayedApproveType(m.group(11));
			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(6));
			creditCardPaymentSms.setPayedInstallmentMonths(m.group(8));

			result.add(creditCardPaymentSms);
		}

		/*
			하나카드취소 강대권님 2010년05월11일 -42,700 철도승차권발매
			하나카드취소 강대권님 2010년05월11일 -45,700 철도승차권발매
			하나카드취소 강대권님 2010년05월11일 -42,700 철도승차권발매
			하나카드취소 강대권님 2010년05월11일 -45,700 철도승차권발매
		 */
		p = Pattern
		.compile("(하나카드)(취소) (.*님) (\\d{4}년\\d{2}월\\d{2}일) ([0-9,-]*) (.*\\b)");
		m = p.matcher(mmsContent);
		
		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();
			
			creditCardPaymentSms.setSenderName(m.group(3));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(null);
			creditCardPaymentSms.setPayedWhenDate(m.group(4));
			creditCardPaymentSms.setPayedWhenTime(null);
			creditCardPaymentSms.setPayedWhere(m.group(6));
			creditCardPaymentSms.setPayedMoney(m.group(5));
			creditCardPaymentSms.setPayedCardType(null);
			creditCardPaymentSms.setPayedApproveType(null);
			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(null);
			creditCardPaymentSms.setPayedInstallmentMonths(null);
			
			result.add(creditCardPaymentSms);
		}
		
		return result;
	}

	public List<CreditCardAutoPaymentSms> autoPaymentSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CreditCardMonthlyPaymentsSms> monthlyPaymentsSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CreditCardCashServicesSms> cashServiceSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CreditCardNotificationSms> notificationSmsParse(String mmsContent) {
		List<CreditCardNotificationSms> result = new ArrayList<CreditCardNotificationSms>();
		CreditCardNotificationSms creditCardNotificationSms;

		String[] notificationPatterns = { 
				"\\[하나SK카드\\] 안심클릭 서비스에 가입해 주셔서 감사합니다\\."
		};
		/*
			[하나SK카드] 안심클릭 서비스에 가입해 주셔서 감사합니다.
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
