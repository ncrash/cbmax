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
public class LotteCardParser implements CreditCardSmsParser {

	public List<CreditCardPaymentSms> paymentSmsParse(String mmsContent) {
		List<CreditCardPaymentSms> result = new ArrayList<CreditCardPaymentSms>();
		CreditCardPaymentSms creditCardPaymentSms;

		/*
			롯데카드 강대권님 939,900원 일시불 04/17 10:16 이베이옥션                       
			롯데카드 강대권님 33,800원 일시불 04/21 23:55 (주)이니시스                      
			롯데카드 강대권님 33,500원 일시불 05/05 22:09 이베이옥션
			롯데카드 강대권님 65,550원 할부(11) 05/09 23:02 (주)인터파크
			롯데카드 강대권님 65,550원 할부(11)취소 05/09 23:02 (주)인터파크
		 */
		Pattern p = Pattern
		.compile("(롯데카드) (.*님) ([0-9,\\.]*)(원) (일시불|할부\\((\\d*)\\))(취소|) (\\d{2}/\\d{2}) (\\d{2}:\\d{2}) (.*\\b)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();

			creditCardPaymentSms.setSenderName(m.group(2));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(null);
			creditCardPaymentSms.setPayedWhenDate(m.group(8));
			creditCardPaymentSms.setPayedWhenTime(m.group(9));
			creditCardPaymentSms.setPayedWhere(m.group(10));
			creditCardPaymentSms.setPayedMoney(m.group(3));
			creditCardPaymentSms.setPayedCardType(null);
			creditCardPaymentSms.setPayedApproveType(m.group(7));
			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(5));
			creditCardPaymentSms.setPayedInstallmentMonths(m.group(6));

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
