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
public class KbCardParser implements CreditCardSmsParser {

	public List<CreditCardPaymentSms> paymentSmsParse(String mmsContent) {
		List<CreditCardPaymentSms> result = new ArrayList<CreditCardPaymentSms>();
		CreditCardPaymentSms creditCardPaymentSms;

/*
[KB체크]
강대권님
04월05일12:47
광양불고기
52,000원 사용

[KB카드]
강대권님
03월27일17:13
이마트구로점
40,480원 사용

[KB카드]
강대권님
05월09일22:45
(주)인터파크INT
53,200원
할부:12개월

[KB카드]
강대권님
05월09일22:46
(주)인터파크INT
53,200원
승인취소
 */
		Pattern p = Pattern
				.compile("\\[(KB)(카드|체크)\\]\\n(.*님)\\n(\\d{2}월\\d{2}일)(\\d{2}:\\d{2})\\n(.*\\b)\\n([0-9,]*)(원)( 사용|\\n할부:(\\d*)개월|\\n승인취소)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms(m.group());

			creditCardPaymentSms.setSenderName(m.group(3));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(null);
			creditCardPaymentSms.setPayedWhenDate(m.group(4));
			creditCardPaymentSms.setPayedWhenTime(m.group(5));
			creditCardPaymentSms.setPayedWhere(m.group(6));
			creditCardPaymentSms.setPayedMoney(m.group(7));
			creditCardPaymentSms.setPayedCardType(m.group(2));
			creditCardPaymentSms.setPayedApproveType(null);
			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(9));
			creditCardPaymentSms.setPayedInstallmentMonths(m.group(10));

			result.add(creditCardPaymentSms);
		}

		return result;
	}

	public List<CreditCardAutoPaymentSms> autoPaymentSmsParse(String mmsContent) {
		List<CreditCardAutoPaymentSms> result = new ArrayList<CreditCardAutoPaymentSms>();
		CreditCardAutoPaymentSms creditCardAutoPaymentSms;

/*
[KB카드]강대권님 카드가 03월11일 SK텔레콤-자동납부에서 10,350원 사용
 */
		Pattern p = Pattern.compile("\\[(KB카드)\\](.*님) (카드가) (\\d{2}월\\d{2}일) (.*\\b) ([0-9,\\.]*)(원) (사용)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardAutoPaymentSms = new CreditCardAutoPaymentSms(m.group());

			creditCardAutoPaymentSms.setSenderName(m.group(2));
			creditCardAutoPaymentSms.setCardCompanyName(m.group(1));
			creditCardAutoPaymentSms.setCardLastFourNumber(null);
			creditCardAutoPaymentSms.setPayedWhenDate(m.group(4));
			creditCardAutoPaymentSms.setPayedWhere(m.group(5));
			creditCardAutoPaymentSms.setPayedMoney(m.group(6));

			result.add(creditCardAutoPaymentSms);
		}

		return result;
	}

	public List<CreditCardMonthlyPaymentsSms> monthlyPaymentsSmsParse(String mmsContent) {
		List<CreditCardMonthlyPaymentsSms> result = new ArrayList<CreditCardMonthlyPaymentsSms>();
		CreditCardMonthlyPaymentsSms creditCardMonthlyPaymentsSms;

/*
[KB카드]강대권님04월01일KB카드결제하실금액180,299원감사합니다(03/17기준)
[KB카드]강대권님05월03일KB카드결제하실금액345,960원감사합니다(04/16기준)
 */
		Pattern p = Pattern.compile("\\[(KB카드)\\](.*님)(\\d{2}월\\d{2}일)(KB카드결제하실금액)([0-9,\\.]*)(원)감사합니다\\((\\d{2}/\\d{2})기준\\)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardMonthlyPaymentsSms = new CreditCardMonthlyPaymentsSms(m.group());

			creditCardMonthlyPaymentsSms.setSenderPhoneNumber("01027976877");
			creditCardMonthlyPaymentsSms.setSenderName(m.group(2));
			creditCardMonthlyPaymentsSms.setCardCompanyName(m.group(1));
			creditCardMonthlyPaymentsSms.setCardLastFourNumber(null);
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsDate(m.group(3));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsMoney(m.group(5));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsCheckDate(m.group(7));
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
				"\\[KB카드\\].*님 KB카드의 안전결제\\(ISP\\)가 \\d{2}월\\d{2}일\\d{2}:\\d{2} 등록완료\\.감사합니다"
		};
/*
[KB카드]강대권님 KB카드의 안전결제(ISP)가 03월05일16:51 등록완료.감사합니다
 */
		Pattern p;
		Matcher m;

		for (int i = 0; i < notificationPatterns.length; i++) {
			p = Pattern
			.compile(notificationPatterns[i]);
			m = p.matcher(mmsContent);
			
			while (m.find()) {
				creditCardNotificationSms = new CreditCardNotificationSms(m.group());
				
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
