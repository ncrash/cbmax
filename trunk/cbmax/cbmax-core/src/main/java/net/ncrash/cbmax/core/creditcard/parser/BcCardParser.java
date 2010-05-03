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
		 */
		Pattern p = Pattern
				.compile("\\[(일시불.승인|[\\d]*개월.승인|승인취소)\\]\\n([0-9,]*)(원)\\n(.*BC)(\\(\\d\\*\\d\\*\\))(.*님)\\n(\\d*\\/\\d*) (\\d*:\\d*)\\n(.*\\b)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();

			creditCardPaymentSms.setSenderPhoneNumber("01027976877");
			creditCardPaymentSms.setSenderName(m.group(6));
			creditCardPaymentSms.setCardCompanyName(m.group(4));
			creditCardPaymentSms.setCardLastFourNumber(m.group(5));
			creditCardPaymentSms.setPayedWhenDate(m.group(7));
			creditCardPaymentSms.setPayedWhenTime(m.group(8));
			creditCardPaymentSms.setPayedWhere(m.group(9));
			creditCardPaymentSms.setPayedMoney(m.group(2));
			creditCardPaymentSms.setPayedCardType(null);
			creditCardPaymentSms.setPayedApproveType(null);
			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(null);

			result.add(creditCardPaymentSms);
		}
		
		//TODO 최근에 변경된 bc카드 사용내역 문자 및 현금서비스 알림 문자
		/*
			[현금서비스.승인]
			10,000원
			농협BC(1*0*)강대권님
			12/24 18:56
			현금서비스
			우리BC강대권님 05/01 22:23 13,900원.일시불.11TOP적립예정.G마켓
		 */
		//TODO G마켓처럼 TOP포인트 적립해주는 가맹점일때 할부처리에 필요한 문자도 필요함
//		p = Pattern
//		.compile("\\[(일시불.승인|[\\d]*개월.승인|승인취소)\\]\\n([0-9,]*)(원)\\n(.*BC)(\\(\\d\\*\\d\\*\\))(.*님)\\n(\\d*\\/\\d*) (\\d*:\\d*)\\n(.*\\b)");
//		m = p.matcher(mmsContent);
//		
//		while (m.find()) {
//			creditCardPaymentSms = new CreditCardPaymentSms();
//			
//			creditCardPaymentSms.setSenderPhoneNumber("01027976877");
//			creditCardPaymentSms.setSenderName(m.group(6));
//			creditCardPaymentSms.setCardCompanyName(m.group(4));
//			creditCardPaymentSms.setCardLastFourNumber(m.group(5));
//			creditCardPaymentSms.setPayedWhenDate(m.group(7));
//			creditCardPaymentSms.setPayedWhenTime(m.group(8));
//			creditCardPaymentSms.setPayedWhere(m.group(9));
//			creditCardPaymentSms.setPayedMoney(m.group(2));
//			creditCardPaymentSms.setPayedCardType(null);
//			creditCardPaymentSms.setPayedApproveType(null);
//			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(null);
//			
//			result.add(creditCardPaymentSms);
//		}

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
			creditCardMonthlyPaymentsSms = new CreditCardMonthlyPaymentsSms();

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
			creditCardMonthlyPaymentsSms = new CreditCardMonthlyPaymentsSms();
			
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
				.compile("\\[(현금서비스.승인||승인취소)\\]\\n([0-9,]*)(원)\\n(.*BC)(\\(\\d\\*\\d\\*\\))(.*님)\\n(\\d*\\/\\d*) (\\d*:\\d*)\\n(.*\\b)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardCashServicesSms = new CreditCardCashServicesSms();

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
		// TODO Auto-generated method stub
		return null;
	}

	public List<CreditCardUnmanagedSms> unmanagedSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}
}
