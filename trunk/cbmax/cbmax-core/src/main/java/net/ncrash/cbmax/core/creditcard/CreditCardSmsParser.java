package net.ncrash.cbmax.core.creditcard;

import java.util.List;

import net.ncrash.cbmax.core.dto.CreditCardAutoPaymentSms;
import net.ncrash.cbmax.core.dto.CreditCardReceiptSms;

/*
 * 정규표현식 개발은 QuickREx eclipse plugin 사용해서 개발
 * Test-Text는 각 카드 타입에 주석으로 달아둔것 사용
 * Regular Expression은 정규표현식을 복사
 * 
 * 작업 시 유의사항
 * 1. Regular Expression은 정규표현식을 복사해서 \\ -> \ 치환
 * 2. 윈도우 환경작업 시 \n -> \r\n 치환 
 */
public interface CreditCardSmsParser {

	List<CreditCardReceiptSms> receiptSmsParse(String mmsContent);
	
	List<CreditCardAutoPaymentSms> autoPaymentSmsParse(String mmsContent);
}
