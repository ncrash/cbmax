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
public class ShinhanCardParser implements CreditCardSmsParser {

	public List<CreditCardPaymentSms> paymentSmsParse(String mmsContent) {
		List<CreditCardPaymentSms> result = new ArrayList<CreditCardPaymentSms>();
		CreditCardPaymentSms creditCardPaymentSms;

		/*
			신한카드정상승인강대권님        04/13 13:51     200,570원(일시불) （주）인터파크
			신한카드승인취소강대권님        04/13 13:57     200,570원(일시불) （주）인터파크
		 */
		Pattern p = Pattern
				.compile("(신한카드)(정상승인|승인취소)(.*님)\\s*(\\d{2}/\\d{2}) (\\d{2}:\\d{2})\\s*([0-9,]*)(원)\\((일시불)\\) (.*\\b)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();

			creditCardPaymentSms.setSenderName(m.group(3));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(null);
			creditCardPaymentSms.setPayedWhenDate(m.group(4));
			creditCardPaymentSms.setPayedWhenTime(m.group(5));
			creditCardPaymentSms.setPayedWhere(m.group(9));
			creditCardPaymentSms.setPayedMoney(m.group(6));
			creditCardPaymentSms.setPayedCardType(null);
			creditCardPaymentSms.setPayedApproveType(m.group(2));
			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(8));

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
			［신한카드］ 강대권님 04/01결제금액(03/28기준) 26,555원(결제:우리은행)
		 */
		Pattern p = Pattern.compile("［(신한카드)］\\s*(.*님)\\s(\\d{2}/\\d{2})결제금액\\((\\d{2}/\\d{2})기준\\)\\s*([0-9,]*)(원)\\(결제:(.*)\\)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardMonthlyPaymentsSms = new CreditCardMonthlyPaymentsSms();

			creditCardMonthlyPaymentsSms.setSenderPhoneNumber("01027976877");
			creditCardMonthlyPaymentsSms.setSenderName(m.group(2));
			creditCardMonthlyPaymentsSms.setCardCompanyName(m.group(1));
			creditCardMonthlyPaymentsSms.setCardLastFourNumber(null);
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsDate(m.group(3));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsMoney(m.group(5));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsCheckDate(m.group(4));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsBankName(m.group(7));
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
				".*회원님은 신한카드 안심클릭 서비스에 정상 등록되었습니다\\."
		};
		/*
			강대권회원님은 신한카드 안심클릭 서비스에 정상 등록되었습니다.
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
