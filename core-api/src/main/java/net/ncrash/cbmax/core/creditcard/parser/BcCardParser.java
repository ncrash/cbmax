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
public class BcCardParser implements CreditCardSmsParser {

	private static BcCardParser bcCardParserSingletonObject;
	
	private BcCardParser() { }
	
	public static BcCardParser getBcCardParser() {
		if (bcCardParserSingletonObject == null) {
			bcCardParserSingletonObject = new BcCardParser();
		}
		return bcCardParserSingletonObject;
	}
	
	public List<CreditCardPaymentSms> paymentSmsParse(String mmsContent) {
		List<CreditCardPaymentSms> result = new ArrayList<CreditCardPaymentSms>();
		CreditCardPaymentSms creditCardPaymentSms;

/*
[일시불.승인]
186,000원
우리BC(3*8*)강대권님
04/07 23:17
양은냄비

[일시불.승인]
348,600원
농협BC(1*0*)강대권님
01/24 16:16
(주)테크노에어포트몰

[승인취소]
150,100원
우리BC(3*8*)강대권님
04/23 23:34
(주)인터파크INT

[10개월.승인]
150,100원
우리BC(3*8*)강대권님
04/23 23:33
(주)인터파크INT

[일시불.승인]
21,500원
우리BC(3*8*)강대권님
05/23 14:52
(POINT580점 사용)
피자헛
 */
		Pattern p = Pattern
				.compile("\\[(일시불.승인|([\\d]*)개월.승인|승인취소)\\]\\n([0-9,]*)(원)\\n(.*BC)(\\(\\d\\*\\d\\*\\))(.*님)\\n(\\d*\\/\\d*) (\\d*:\\d*)(\\n\\(.*\\)|)\\n(.*\\b)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();

			creditCardPaymentSms.setMessage(m.group());
			creditCardPaymentSms.setSenderPhoneNumber("01027976877");
			creditCardPaymentSms.setSenderName(m.group(7));
			creditCardPaymentSms.setCardCompanyName(m.group(5));
			creditCardPaymentSms.setCardLastFourNumber(m.group(6));
			creditCardPaymentSms.setPayedWhenDate(m.group(8));
			creditCardPaymentSms.setPayedWhenTime(m.group(9));
			creditCardPaymentSms.setPayedWhere(m.group(11));
			creditCardPaymentSms.setPayedMoney(m.group(3));
			creditCardPaymentSms.setPayedCardType(null);
			creditCardPaymentSms.setUnknownMessage(m.group(10));
			
			if ("일시불.승인".equals(m.group(1))) {
				creditCardPaymentSms.setPayedApproveType("승인");
				creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan("일시불");
			} else if ("승인취소".equals(m.group(1))) {
				creditCardPaymentSms.setPayedApproveType("승인");
				creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan("취소");
			} else if (m.group(2) != null) {
				creditCardPaymentSms.setPayedApproveType("승인");
				creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(2));
			}

			result.add(creditCardPaymentSms);
		}
		
/*
우리BC강대권님 05/01 22:23 13,900원.일시불.11TOP적립예정.G마켓
 */
		p = Pattern
		.compile("(.*BC)(.*님) (\\d{2}/\\d{2}) (\\d{2}:\\d{2}) ([0-9,.]*)(원)\\.(일시불|[\\d]*개월.승인|승인취소)\\.(\\d*)TOP적립예정\\.(.*\\b)");
		m = p.matcher(mmsContent);
		
		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();
			
			creditCardPaymentSms.setMessage(m.group());
			creditCardPaymentSms.setSenderPhoneNumber("01027976877");
			creditCardPaymentSms.setSenderName(m.group(2));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(null);
			creditCardPaymentSms.setPayedWhenDate(m.group(3));
			creditCardPaymentSms.setPayedWhenTime(m.group(4));
			creditCardPaymentSms.setPayedWhere(m.group(9));
			creditCardPaymentSms.setPayedMoney(m.group(5));
			creditCardPaymentSms.setPayedCardType(null);
			creditCardPaymentSms.setPayedApproveType(m.group(7));
			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(7));
			
			result.add(creditCardPaymentSms);
		}

/*
	우리BC(3*8*)강대권님.05/30 15:43.28,200원.일시불.28TOP적립예정.전국고속버스운송
 */
		p = Pattern
		.compile("(.*BC)(\\(\\d\\*\\d\\*\\))(.*님)\\.(\\d{2}/\\d{2}) (\\d{2}:\\d{2})\\.([0-9,.]*)(원)\\.(일시불|[\\d]*개월.승인|승인취소)\\.(\\d*)TOP적립예정\\.(.*\\b)");
		m = p.matcher(mmsContent);
		
		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();
			
			creditCardPaymentSms.setMessage(m.group());
			creditCardPaymentSms.setSenderPhoneNumber("01027976877");
			creditCardPaymentSms.setSenderName(m.group(3));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(m.group(2));
			creditCardPaymentSms.setPayedWhenDate(m.group(4));
			creditCardPaymentSms.setPayedWhenTime(m.group(5));
			creditCardPaymentSms.setPayedWhere(m.group(10));
			creditCardPaymentSms.setPayedMoney(m.group(6));
			creditCardPaymentSms.setPayedCardType(null);
			creditCardPaymentSms.setPayedApproveType(m.group(8));
			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(8));
			
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
		List<CreditCardMonthlyPaymentsSms> result = new ArrayList<CreditCardMonthlyPaymentsSms>();
		CreditCardMonthlyPaymentsSms creditCardMonthlyPaymentsSms;

/*
우리BC 강대권님
1/1일 결제금액
175,343원
(12/16일 기준)
농협BC 강대권님
2/1일 결제금액
38,774원
(01/19일 기준)
우리BC 강대권님
2/1일 결제금액
226,761원
(01/16일 기준)
우리BC 강대권님
3/1일 결제금액
153,253원
(02/16일 기준)
농협BC 강대권님
3/1일 결제금액
379,300원
(02/19일 기준)
 */
		Pattern p = Pattern
				.compile("(.*BC) (.*님)\\n(\\d*\\/\\d*일) 결제금액\\n([0-9,]*)(원)\\n\\((\\d{2}/\\d{2}일) 기준\\)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardMonthlyPaymentsSms = new CreditCardMonthlyPaymentsSms(m.group());

			creditCardMonthlyPaymentsSms.setSenderPhoneNumber("01027976877");
			creditCardMonthlyPaymentsSms.setSenderName(m.group(2));
			creditCardMonthlyPaymentsSms.setCardCompanyName(m.group(1));
			creditCardMonthlyPaymentsSms.setCardLastFourNumber(null);
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsDate(m.group(3));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsMoney(m.group(4));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsCheckDate(m.group(6));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsBankName(null);
			creditCardMonthlyPaymentsSms.setRemainedCardPoint(null);
			creditCardMonthlyPaymentsSms.setRemainedCardPointCheckDate(null);

			result.add(creditCardMonthlyPaymentsSms);
		}
		
/*
우리BC강대권님4/1일결제금액300,401원(03/16기준)잔여TOP431(03/24기준)
 */
		p = Pattern
		.compile("(.*BC)(.*님)(\\d*\\/\\d*일)결제금액([0-9,]*)(원)\\((\\d{2}/\\d{2})기준\\)(잔여TOP)([0-9,.]*)\\((\\d{2}/\\d{2})기준\\)");
		m = p.matcher(mmsContent);
		
		while (m.find()) {
			creditCardMonthlyPaymentsSms = new CreditCardMonthlyPaymentsSms(m.group());
			
			creditCardMonthlyPaymentsSms.setSenderPhoneNumber("01027976877");
			creditCardMonthlyPaymentsSms.setSenderName(m.group(2));
			creditCardMonthlyPaymentsSms.setCardCompanyName(m.group(1));
			creditCardMonthlyPaymentsSms.setCardLastFourNumber(null);
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsDate(m.group(3));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsMoney(m.group(4));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsCheckDate(m.group(6));
			creditCardMonthlyPaymentsSms.setMonthlyPaymentsBankName(null);
			creditCardMonthlyPaymentsSms.setRemainedCardPoint(m.group(8));
			creditCardMonthlyPaymentsSms.setRemainedCardPointCheckDate(m.group(9));
			
			result.add(creditCardMonthlyPaymentsSms);
		}

		return result;
	}

	public List<CreditCardCashServicesSms> cashServiceSmsParse(String mmsContent) {
		List<CreditCardCashServicesSms> result = new ArrayList<CreditCardCashServicesSms>();
		CreditCardCashServicesSms creditCardCashServicesSms;

/*
[현금서비스.승인]
10,000원
농협BC(1*0*)강대권님
12/24 18:56
현금서비스
 */
		Pattern p = Pattern
				.compile("\\[(현금서비스.승인)\\]\\n([0-9,]*)(원)\\n(.*BC)(\\(\\d\\*\\d\\*\\))(.*님)\\n(\\d*\\/\\d*) (\\d*:\\d*)\\n(.*\\b)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardCashServicesSms = new CreditCardCashServicesSms(m.group());

			creditCardCashServicesSms.setSenderPhoneNumber("01027976877");
			creditCardCashServicesSms.setSenderName(m.group(6));
			creditCardCashServicesSms.setCardCompanyName(m.group(4));
			creditCardCashServicesSms.setCardLastFourNumber(m.group(5));
			creditCardCashServicesSms.setServiceWhenDate(m.group(7));
			creditCardCashServicesSms.setServiceWhenTime(m.group(8));
			creditCardCashServicesSms.setServiceMoney(m.group(2));

			result.add(creditCardCashServicesSms);
		}
		
		return result;
	}

	public List<CreditCardNotificationSms> notificationSmsParse(String mmsContent) {
		List<CreditCardNotificationSms> result = new ArrayList<CreditCardNotificationSms>();
		CreditCardNotificationSms creditCardNotificationSms;

		String[] notificationPatterns = { 
				".* 고객님 비씨카드 안전결제\\(ISP\\)가 정상적으로 발급되었습니다.",
				".*님 \\d*/\\d*일은 .*BC 결제일입니다\\.\\n.*\\b"
		};
/*
강대권 고객님 비씨카드 안전결제(ISP)가 정상적으로 발급되었습니다.

강대권님 1/4일은 우리BC 결제일입니다.
연말 바쁜 일정에도 건강 잊지 마세요
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
