package net.ncrash.cbmax.core.creditcard;

import java.util.List;

import net.ncrash.cbmax.core.dto.CreditCardAutoPaymentSms;
import net.ncrash.cbmax.core.dto.CreditCardCashServicesSms;
import net.ncrash.cbmax.core.dto.CreditCardMonthlyPaymentsSms;
import net.ncrash.cbmax.core.dto.CreditCardNotificationSms;
import net.ncrash.cbmax.core.dto.CreditCardPaymentSms;
import net.ncrash.cbmax.core.dto.CreditCardUnmanagedSms;

/*
 * 정규표현식 개발은 QuickREx eclipse plugin 사용해서 개발
 * Test-Text는 각 카드 타입에 주석으로 달아둔것 사용
 * Regular Expression은 정규표현식을 복사
 * 
 * 작업 시 유의사항
 * 1. Regular Expression은 정규표현식을 복사해서 \\ -> \ 치환
 * 2. 윈도우 환경작업 시 \n -> \r\n 치환 
 */
/**
 * CreditCardSmsParser Interface
 * 
 * @author Daekwon.Kang
 * @since 2010.04.29
 */
public interface CreditCardSmsParser {

	/**
	 * 신용카드 사용내역 분석
	 * 
	 * @param mmsContent
	 * @return 
	 */
	List<CreditCardPaymentSms> paymentSmsParse(String mmsContent);
	
	/**
	 * 신용카드 자동납부내역 분석
	 * 
	 * @param mmsContent
	 * @return
	 */
	List<CreditCardAutoPaymentSms> autoPaymentSmsParse(String mmsContent);
	
	/**
	 * 신용카드 월결제액 분석
	 * 
	 * @param mmsContent
	 * @return
	 */
	List<CreditCardMonthlyPaymentsSms> monthlyPaymentsSmsParse(String mmsContent);
	
	/**
	 * 신용카드 현금서비스 분석
	 * 
	 * @param mmsContent
	 * @return
	 */
	List<CreditCardCashServicesSms> cashServiceSmsParse(String mmsContent);
	
	/**
	 * 신용카드 알림내역 분석
	 * 
	 * @param mmsContent
	 * @return
	 */
	List<CreditCardNotificationSms> notificationSmsParse(String mmsContent);
	
	/**
	 * 신용카드 알수없음 분석
	 * 
	 * @param mmsContent
	 * @return
	 */
	List<CreditCardUnmanagedSms> unmanagedSmsParse(String mmsContent);
}
