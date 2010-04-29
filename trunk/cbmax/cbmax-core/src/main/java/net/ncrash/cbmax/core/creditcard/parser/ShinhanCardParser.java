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
 * @since 2010. 4. 29.
 * @see
 */
public class ShinhanCardParser implements CreditCardSmsParser {

	public List<CreditCardPaymentSms> receiptSmsParse(String mmsContent) {
		List<CreditCardPaymentSms> result = new ArrayList<CreditCardPaymentSms>();
		CreditCardPaymentSms creditCardReceiptSms;

		/*
			신한카드정상승인강대권님        04/13 13:51     200,570원(일시불) （주）인터파크
			신한카드승인취소강대권님        04/13 13:57     200,570원(일시불) （주）인터파크
		 */
		Pattern p = Pattern
		.compile("(신한카드)(정상승인|승인취소)(.*님)\\s*(\\d{2}/\\d{2}) (\\d{2}:\\d{2})\\s*([0-9,]*)(원)\\((일시불)\\)(.*\\b)");
		Matcher m = p.matcher(mmsContent);

		while (m.find()) {
			creditCardReceiptSms = new CreditCardPaymentSms();

			creditCardReceiptSms.setSenderName(m.group(3));
			creditCardReceiptSms.setCardCompanyName(m.group(1));
			creditCardReceiptSms.setCardLastFourNumber(null);
			creditCardReceiptSms.setPayedWhenDate(m.group(4));
			creditCardReceiptSms.setPayedWhenTime(m.group(5));
			creditCardReceiptSms.setPayedWhere(m.group(9));
			creditCardReceiptSms.setPayedMoney(m.group(6));
			creditCardReceiptSms.setPayedCardType(null);
			creditCardReceiptSms.setPayedApproveType(m.group(2));
			creditCardReceiptSms.setPayedLumpSumOrInstallmentPlan(m.group(8));

			result.add(creditCardReceiptSms);
		}

		return result;
	}

	public List<CreditCardAutoPaymentSms> autoPaymentSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}
}
