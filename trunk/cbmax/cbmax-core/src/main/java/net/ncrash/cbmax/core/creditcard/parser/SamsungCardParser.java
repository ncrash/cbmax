package net.ncrash.cbmax.core.creditcard.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ncrash.cbmax.core.creditcard.CreditCardSmsParser;
import net.ncrash.cbmax.core.dto.CreditCardAutoPaymentSms;
import net.ncrash.cbmax.core.dto.CreditCardPaymentSms;

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
				.compile("(삼성카드)(취소|)(\\d{2}/\\d{2}) (\\d{2}:\\d{2})([0-9.%]{4} 보너스클럽|)([^0-9]*)([0-9,]*)(원)((\\d*)개월할부|일시불감사합니다|할부감사합니다|)");
		Matcher m = p.matcher(mmsContent.replaceAll("\n", ""));

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
			creditCardPaymentSms.setPayedCardType(m.group(9));
			creditCardPaymentSms.setPayedApproveType(m.group(2));
			
			if(m.groupCount() == 10) {
				creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(10));
			} else {
				creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(null);
			}

			System.out.println(creditCardPaymentSms);
			result.add(creditCardPaymentSms);
		}

		return result;
	}

	public List<CreditCardAutoPaymentSms> autoPaymentSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}
}