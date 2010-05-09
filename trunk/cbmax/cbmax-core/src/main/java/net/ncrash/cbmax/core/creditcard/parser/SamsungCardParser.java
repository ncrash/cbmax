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
 * @since 2010. 5. 3.
 * @see
 */
public class SamsungCardParser implements CreditCardSmsParser {

	public List<CreditCardPaymentSms> paymentSmsParse(String mmsContent) {
		List<CreditCardPaymentSms> result = new ArrayList<CreditCardPaymentSms>();
		CreditCardPaymentSms creditCardPaymentSms;

		/*
			삼성카드
			04/30 09:58
			1.0% 보너스클럽
			(주)인터파크INT
			63,650원
			삼성카드취소
			04/30 09:59
			(주)인터파크INT
			-63,650원
			일시불
			감사합니다
			삼성카드
			04/30 10:00
			1.0% 보너스클럽
			(주)인터파크INT
			110,200원
			3개월할부
			삼성카드취소
			04/30 10:01
			(주)인터파크INT
			-110,200원
			할부
			감사합니다
		 */
		//TODO 정규표현식에 줄바꿈 처리가 제대로 되지 않아 \n 문자를 모두 없애고 꽁수로 처리함
		Pattern p = Pattern
				.compile("(삼성카드)(취소|)\\n(\\d{2}/\\d{2}) (\\d{2}:\\d{2})\\n([0-9.%]{4} 보너스클럽\\n|)(.*)\\n([0-9,.-]*)(원)(\\n|)((\\d*)개월할부|(일시불)\\n감사합니다|(할부)\\n감사합니다|)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();

			creditCardPaymentSms.setSenderPhoneNumber("01027976877");
			creditCardPaymentSms.setSenderName(null);
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(null);
			creditCardPaymentSms.setPayedWhenDate(m.group(3));
			creditCardPaymentSms.setPayedWhenTime(m.group(4));
			creditCardPaymentSms.setPayedWhere(m.group(6));
			creditCardPaymentSms.setPayedMoney(m.group(7));
			creditCardPaymentSms.setPayedCardType(null);
			creditCardPaymentSms.setPayedApproveType(m.group(2));

			if( "일시불".equals(m.group(12))) {
				creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(12));
			} else if( "할부".equals(m.group(13))) {
					creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(13));
			} else if ( (m.group(10).indexOf("할부") > 0)) {
				creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(11));
			} else {
				creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(null);
			}

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
		List<CreditCardNotificationSms> result = new ArrayList<CreditCardNotificationSms>();
		CreditCardNotificationSms creditCardNotificationSms;

		String[] notificationPatterns = { 
				"\\(삼성카드\\).*님\\, \\d{2}월\\d{2}일 안심클릭 서비스등록이 정상처리되었습니다."
		};
		/*
			(삼성카드)강대권님, 04월24일 안심클릭 서비스등록이 정상처리되었습니다.
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
