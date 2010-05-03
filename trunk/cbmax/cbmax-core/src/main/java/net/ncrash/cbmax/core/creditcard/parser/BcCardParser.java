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

		return result;
	}

	public List<CreditCardAutoPaymentSms> autoPaymentSmsParse(String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CreditCardAutoPaymentSms> monthlyPaymentsSmsParse(
			String mmsContent) {
		// TODO Auto-generated method stub
		return null;
	}
}
