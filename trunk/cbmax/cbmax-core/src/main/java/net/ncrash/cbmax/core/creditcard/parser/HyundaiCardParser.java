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
public class HyundaiCardParser implements CreditCardSmsParser {

	public List<CreditCardPaymentSms> paymentSmsParse(String mmsContent) {
		List<CreditCardPaymentSms> result = new ArrayList<CreditCardPaymentSms>();
		CreditCardPaymentSms creditCardPaymentSms;

		/*
			[현대카드C]
			강대권님
			12:10
			2,400원(일시불)
			정상승인
			온누리조은약국
		 */
		Pattern p = Pattern
		.compile("\\[(현대카드)(\\w)\\]\\n(.*님)\\n(\\d{2}:\\d{2})\\n([0-9,\\.]*)(원)\\((일시불)\\)\\n(정상승인)\\n(.*\\b)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();

			creditCardPaymentSms.setSenderName(m.group(3));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(null);
			creditCardPaymentSms.setPayedWhenDate(null);
			creditCardPaymentSms.setPayedWhenTime(m.group(4));
			creditCardPaymentSms.setPayedWhere(m.group(9));
			creditCardPaymentSms.setPayedMoney(m.group(5));
			creditCardPaymentSms.setPayedCardType(m.group(2));
			creditCardPaymentSms.setPayedApproveType(m.group(8));
			creditCardPaymentSms.setPayedLumpSumOrInstallmentPlan(m.group(7));

			result.add(creditCardPaymentSms);
		}
		
		/*
			[현대카드C]-승인
			강대권님
			11:51
			2,000원(일시불)
			훼미리마트Ｗ－ＭＡＬＬ점
		 */
		p = Pattern
		.compile("\\[(현대카드)(\\w)\\](-승인)\\n(.*님)\\n(\\d{2}:\\d{2})\\n([0-9,\\.]*)(원)\\((일시불)\\)\\n(.*\\b)");
		m = p.matcher(mmsContent);
		
		while (m.find()) {
			creditCardPaymentSms = new CreditCardPaymentSms();
			
			creditCardPaymentSms.setSenderName(m.group(4));
			creditCardPaymentSms.setCardCompanyName(m.group(1));
			creditCardPaymentSms.setCardLastFourNumber(null);
			creditCardPaymentSms.setPayedWhenDate(null);
			creditCardPaymentSms.setPayedWhenTime(m.group(5));
			creditCardPaymentSms.setPayedWhere(m.group(9));
			creditCardPaymentSms.setPayedMoney(m.group(6));
			creditCardPaymentSms.setPayedCardType(m.group(2));
			creditCardPaymentSms.setPayedApproveType(m.group(3));
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
