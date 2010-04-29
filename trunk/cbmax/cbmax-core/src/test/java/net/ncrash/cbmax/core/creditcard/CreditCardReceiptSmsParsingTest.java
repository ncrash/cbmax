package net.ncrash.cbmax.core.creditcard;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;
import net.ncrash.cbmax.core.dto.CreditCardReceiptSms;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * 정규표현식 개발은 QuickREx eclipse plugin 사용해서 개발
 * Test-Text는 각 카드 타입에 주석으로 달아둔것 사용
 * Regular Expression은 정규표현식을 복사
 * 
 * 작업 시 유의사항
 * 1. Regular Expression은 정규표현식을 복사해서 \\ -> \ 치환
 * 2. 윈도우 환경작업 시 \n -> \r\n 치환 
 */
public class CreditCardReceiptSmsParsingTest {
	static File testFixtureExcelFile;
	Workbook workbook;
	Sheet sheet;

	List<String> receiptSmsList = new ArrayList<String>();
	StringBuffer sb;
	int matchCount;
	
	CreditCardCompany creditCardCompany;
	CreditCardReceiptSms creditCardReceiptSms;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		creditCardCompany = new CreditCardCompany();
		
		matchCount = 0;

		receiptSmsList.add("씨티카드 강대권님 승인내역 03월13일 19:47 이마트구로점 40,410원 일시불");

		sb = new StringBuffer();
		sb.append("[일시불.승인]");
		sb.append("\n").append("186,000원");
		sb.append("\n").append("우리BC(3*8*)강대권님");
		sb.append("\n").append("04/07 23:17");
		sb.append("\n").append("양은냄비");
		receiptSmsList.add(sb.toString());

		sb = new StringBuffer();
		sb.append("[일시불.승인]");
		sb.append("\n").append("348,600원");
		sb.append("\n").append("농협BC(1*0*)강대권님");
		sb.append("\n").append("01/24 16:16");
		sb.append("\n").append("(주)테크노에어포트몰");
		receiptSmsList.add(sb.toString());

		receiptSmsList.add("[KB카드]강대권님 카드가 04월12일 SK텔레콤-자동납부에서 13,080원 사용");

		sb = new StringBuffer();
		sb.append("[KB체크]");
		sb.append("\n").append("강대권님");
		sb.append("\n").append("04월05일12:47");
		sb.append("\n").append("광양불고기");
		sb.append("\n").append("52,000원 사용");
		receiptSmsList.add(sb.toString());

		sb = new StringBuffer();
		sb.append("[KB카드]");
		sb.append("\n").append("강대권님");
		sb.append("\n").append("03월27일17:13");
		sb.append("\n").append("이마트구로점");
		sb.append("\n").append("40,480원 사용");
		receiptSmsList.add(sb.toString());

		receiptSmsList.add("신한카드정상승인강대권님        04/13 13:51     200,570원(일시불) （주）인터파크");
		receiptSmsList.add("신한카드승인취소강대권님        04/13 13:57     200,570원(일시불) （주）인터파크");

		receiptSmsList.add("[외환카드]강대권님     15,720원 승인 택시(서울)한국스 04/08 01:22");

		sb = new StringBuffer();
		sb.append("[현대카드C]");
		sb.append("\n").append("강대권님");
		sb.append("\n").append("12:10");
		sb.append("\n").append("2,400원(일시불)");
		sb.append("\n").append("정상승인");
		sb.append("\n").append("온누리조은약국");
		receiptSmsList.add(sb.toString());

		receiptSmsList.add("롯데카드 강대권님 939,900원 일시불 04/17 10:16 이베이옥션                       ");
		receiptSmsList.add("롯데카드 강대권님 33,800원 일시불 04/21 23:55 (주)이니시스                      ");

		sb = new StringBuffer();
		sb.append("[일시불.승인]");
		sb.append("\n").append("22,500원");
		sb.append("\n").append("우리BC(0*5*)강대권님");
		sb.append("\n").append("04/24 19:27");
		sb.append("\n").append("(주)인터파크INT");
		receiptSmsList.add(sb.toString());

		sb = new StringBuffer();
		sb.append("[승인취소]");
		sb.append("\n").append("22,500원");
		sb.append("\n").append("우리BC(0*5*)강대권님");
		sb.append("\n").append("04/24 19:28");
		sb.append("\n").append("(주)인터파크INT");
		receiptSmsList.add(sb.toString());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	// TODO 씨티카드는 할부개월 및 승인취소 내역 실제 테스트 해야하고 체크카드 작업이 필요
	public void testCityCard() throws Exception {

		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("CITY"));

		for (int i = 0; i < receiptSmsList.size(); i++) {
			String receiptSms = receiptSmsList.get(i);

			matchCount += creditCardCompany.getParser().receiptSmsParse(receiptSms).size();
		}
		
		assertEquals(1, matchCount);
	}
	
	@Test
	// TODO 비씨카드 체크카드 발급 필요
	public void testBcCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("BC"));
		
		for (int i = 0; i < receiptSmsList.size(); i++) {
			String receiptSms = receiptSmsList.get(i);
			
			matchCount += creditCardCompany.getParser().receiptSmsParse(receiptSms).size();
		}
		
		assertEquals(4, matchCount);
	}

//	@Test
//	// TODO KB카드는 할부승인과 신용체크의 취소 필요
//	public void testKbCard() throws Exception {
//		for (int i = 0; i < receiptSmsList.size(); i++) {
//			String receiptSms = receiptSmsList.get(i);
//
//			/*
//				[KB카드]강대권님 카드가 04월12일 SK텔레콤-자동납부에서 13,080원 사용
//				
//				[KB체크]
//				강대권님
//				04월05일12:47
//				광양불고기
//				52,000원 사용
//				
//				[KB카드]
//				강대권님
//				03월27일17:13
//				이마트구로점
//				40,480원 사용
//			 */
//			Pattern p = Pattern
//					.compile("\\[(KB)(카드|체크)\\]\\n(.*님)\\n(\\d{2}월\\d{2}일)(\\d{2}:\\d{2})\\n(.*\\b)\\n([0-9,]*)(원) (사용)");
//			Matcher m = p.matcher(receiptSms);
//
//			while (m.find()) {
//				matchCount++;
//				
//				creditCardReceiptSms = new CreditCardReceiptSms("kb", m);
//
//				// TODO 구분이 모호한 항목 정리
//				/*
//				 * getPayedApproveType
//				 * getPayedLumpSumOrInstallmentPlan
//				 */
//				assertEquals(m.group(3), creditCardReceiptSms.getSenderName());
//				assertEquals(m.group(1), creditCardReceiptSms.getCardCompanyName());
//				assertEquals(null, creditCardReceiptSms.getCardLastFourNumber());
//				assertEquals(m.group(4), creditCardReceiptSms.getPayedWhenDate());
//				assertEquals(m.group(5), creditCardReceiptSms.getPayedWhenTime());
//				assertEquals(m.group(6), creditCardReceiptSms.getPayedWhere());
//				assertEquals(m.group(7), creditCardReceiptSms.getPayedMoney());
//				assertEquals(m.group(2), creditCardReceiptSms.getPayedCardType());
//				assertEquals(null, creditCardReceiptSms.getPayedApproveType());
//				assertEquals(null, creditCardReceiptSms.getPayedLumpSumOrInstallmentPlan());
//			}
//		}
//		assertEquals(2, matchCount);
//	}
//
//	@Test
//	// TODO 신한카드는 할부승인, 체크카드 승인, 체크카드 승인취소 처리 필요
//	public void testShinhanCard() throws Exception {
//		for (int i = 0; i < receiptSmsList.size(); i++) {
//			String receiptSms = receiptSmsList.get(i);
//
//			/*
//				신한카드정상승인강대권님        04/13 13:51     200,570원(일시불) （주）인터파크
//				신한카드승인취소강대권님        04/13 13:57     200,570원(일시불) （주）인터파크
//			 */
//			Pattern p = Pattern
//					.compile("(신한카드)(정상승인|승인취소)(.*님)\\s*(\\d{2}/\\d{2}) (\\d{2}:\\d{2})\\s*([0-9,]*)(원)\\((일시불)\\)(.*\\b)");
//			Matcher m = p.matcher(receiptSms);
//
//			while (m.find()) {
//				matchCount++;
//
//				creditCardReceiptSms = new CreditCardReceiptSms("shinhan", m);
//
//				// TODO 구분이 모호한 항목 정리
//				/*
//				 * getPayedCardType
//				 */
//				assertEquals(m.group(3), creditCardReceiptSms.getSenderName());
//				assertEquals(m.group(1), creditCardReceiptSms.getCardCompanyName());
//				assertEquals(null, creditCardReceiptSms.getCardLastFourNumber());
//				assertEquals(m.group(4), creditCardReceiptSms.getPayedWhenDate());
//				assertEquals(m.group(5), creditCardReceiptSms.getPayedWhenTime());
//				assertEquals(m.group(9), creditCardReceiptSms.getPayedWhere());
//				assertEquals(m.group(6), creditCardReceiptSms.getPayedMoney());
//				assertEquals(null, creditCardReceiptSms.getPayedCardType());
//				assertEquals(m.group(2), creditCardReceiptSms.getPayedApproveType());
//				assertEquals(m.group(8), creditCardReceiptSms.getPayedLumpSumOrInstallmentPlan());
//			}
//		}
//
//		assertEquals(2, matchCount);
//	}
//
//	@Test
//	// TODO 외환카드는 할부승인, 체크카드 승인, 체크카드 승인취소 처리 필요
//	public void testYesCard() throws Exception {
//		for (int i = 0; i < receiptSmsList.size(); i++) {
//			String receiptSms = receiptSmsList.get(i);
//
//			/*
//				[외환카드]강대권님     15,720원 승인 택시(서울)한국스 04/08 01:22
//			 */
//			Pattern p = Pattern
//					.compile("\\[(외환카드)\\](.*님)\\s*([0-9,]*)(원) (승인) (.*\\b) (\\d{2}/\\d{2}) (\\d{2}:\\d{2})");
//			Matcher m = p.matcher(receiptSms);
//
//			while (m.find()) {
//				matchCount++;
//				
//				creditCardReceiptSms = new CreditCardReceiptSms("keb", m);
//
//				// TODO 구분이 모호한 항목 정리
//				/*
//				 * getPayedCardType
//				 * getPayedLumpSumOrInstallmentPlan
//				 */
//				assertEquals(m.group(2), creditCardReceiptSms.getSenderName());
//				assertEquals(m.group(1), creditCardReceiptSms.getCardCompanyName());
//				assertEquals(null, creditCardReceiptSms.getCardLastFourNumber());
//				assertEquals(m.group(7), creditCardReceiptSms.getPayedWhenDate());
//				assertEquals(m.group(8), creditCardReceiptSms.getPayedWhenTime());
//				assertEquals(m.group(6), creditCardReceiptSms.getPayedWhere());
//				assertEquals(m.group(3), creditCardReceiptSms.getPayedMoney());
//				assertEquals(null, creditCardReceiptSms.getPayedCardType());
//				assertEquals(m.group(5), creditCardReceiptSms.getPayedApproveType());
//				assertEquals(null, creditCardReceiptSms.getPayedLumpSumOrInstallmentPlan());
//			}
//		}
//
//		assertEquals(1, matchCount);
//	}
//
//	@Test
//	// TODO 현대카드는 신용승인, 할부승인, 신용 승인취소, 체크카드 승인취소 처리 필요
//	public void testHyundaiCard() throws Exception {
//		for (int i = 0; i < receiptSmsList.size(); i++) {
//			String receiptSms = receiptSmsList.get(i);
//
//			/*
//				[현대카드C]
//				강대권님
//				12:10
//				2,400원(일시불)
//				정상승인
//				온누리조은약국
//			 */
//			Pattern p = Pattern
//					.compile("\\[(현대카드)(\\w)\\]\\n(.*님)\\n(\\d{2}:\\d{2})\\n([0-9,\\.]*)(원)\\((일시불)\\)\\n(정상승인)\\n(.*\\b)");
//			Matcher m = p.matcher(receiptSms);
//
//			while (m.find()) {
//				matchCount++;
//				
//				creditCardReceiptSms = new CreditCardReceiptSms("hyundai", m);
//
//				// TODO 구분이 모호한 항목 정리
//				/*
//				 * getPayedCardType
//				 * getPayedLumpSumOrInstallmentPlan
//				 */
//				assertEquals(m.group(3), creditCardReceiptSms.getSenderName());
//				assertEquals(m.group(1), creditCardReceiptSms.getCardCompanyName());
//				assertEquals(null, creditCardReceiptSms.getCardLastFourNumber());
//				assertEquals(null, creditCardReceiptSms.getPayedWhenDate());
//				assertEquals(m.group(4), creditCardReceiptSms.getPayedWhenTime());
//				assertEquals(m.group(9), creditCardReceiptSms.getPayedWhere());
//				assertEquals(m.group(5), creditCardReceiptSms.getPayedMoney());
//				assertEquals(m.group(2), creditCardReceiptSms.getPayedCardType());
//				assertEquals(m.group(8), creditCardReceiptSms.getPayedApproveType());
//				assertEquals(m.group(7), creditCardReceiptSms.getPayedLumpSumOrInstallmentPlan());
//			}
//		}
//
//		assertEquals(1, matchCount);
//	}
//
//	@Test
//	// TODO 롯데카드는 할부승인, 신용 승인취소, 체크카드 승인, 승인취소 처리 필요
//	public void testLotteCard() throws Exception {
//		for (int i = 0; i < receiptSmsList.size(); i++) {
//			String receiptSms = receiptSmsList.get(i);
//
//			/*
//				롯데카드 강대권님 939,900원 일시불 04/17 10:16 이베이옥션                       
//				롯데카드 강대권님 33,800원 일시불 04/21 23:55 (주)이니시스                      
//			 */
//			Pattern p = Pattern.compile("(롯데카드) (.*님) ([0-9,\\.]*)(원) (일시불) (\\d{2}/\\d{2}) (\\d{2}:\\d{2}) (.*\\b)");
//			Matcher m = p.matcher(receiptSms);
//
//			while (m.find()) {
//				matchCount++;
//				
//				// TODO 구분이 모호한 항목 정리
//				/*
//				 * getPayedCardType
//				 * getPayedApproveType
//				 */
//				creditCardReceiptSms = new CreditCardReceiptSms("lotte", m);
//				
//				assertEquals(m.group(2), creditCardReceiptSms.getSenderName());
//				assertEquals(m.group(1), creditCardReceiptSms.getCardCompanyName());
//				assertEquals(null, creditCardReceiptSms.getCardLastFourNumber());
//				assertEquals(m.group(6), creditCardReceiptSms.getPayedWhenDate());
//				assertEquals(m.group(7), creditCardReceiptSms.getPayedWhenTime());
//				assertEquals(m.group(8), creditCardReceiptSms.getPayedWhere());
//				assertEquals(m.group(3), creditCardReceiptSms.getPayedMoney());
//				assertEquals(m.group(1), creditCardReceiptSms.getPayedCardType());
//				assertEquals(m.group(1), creditCardReceiptSms.getPayedApproveType());
//				assertEquals(m.group(5), creditCardReceiptSms.getPayedLumpSumOrInstallmentPlan());
//			}
//		}
//
//		assertEquals(2, matchCount);
//	}
//
//	// receiptSmsList 내용을 정규표현식으로 모두 찾아내는지 확인하는 테스트
//	@Test
//	public void testCardCompanyReceiptsSmsParser() throws Exception {
//		List<String> receiptSmsParsers = CreditCardCompany.getReceiptSmsRegexList();
//
//		for (int i = 0; i < receiptSmsList.size(); i++) {
//			Boolean isFind = false;
//			String receiptSms = receiptSmsList.get(i);
//
//			for (int j = 0; j < receiptSmsParsers.size(); j++) {
//				String receiptSmsParser = receiptSmsParsers.get(j);
//
//				Pattern p = Pattern.compile(receiptSmsParser);
//				Matcher m = p.matcher(receiptSms);
//
//				while (m.find()) {
//					matchCount++;
//					isFind = true;
//				}
//			}
//
//			if (isFind == false) {
//				List<String> automaticWithdrawalSmsParsers = CreditCardCompany.getAutomaticWithdrawalSmsRegexList();
//				for (int j = 0; j < automaticWithdrawalSmsParsers.size(); j++) {
//					String automaticWithdrawalSmsParser = automaticWithdrawalSmsParsers.get(j);
//
//					Pattern p = Pattern.compile(automaticWithdrawalSmsParser);
//					Matcher m = p.matcher(receiptSms);
//
//					while (m.find()) {
//						matchCount++;
//						isFind = true;
//					}
//				}
//			}
//		}
//
//		assertEquals(receiptSmsList.size(), matchCount);
//	}
//
	// "cbmax-testcase-fixture.xls" 파일을 내용을 읽어 정규표현식으로 확인이 가능한지 테스트
	@Test
	public void testExcelFixtureMmsContentParsing() throws Exception {
		String mmsContent;
		String smsMessageCount;
		int smsMessageCountTotal = 0;

		String[] cardCompanyIds = {"BC", "CITY"};

		testFixtureExcelFile = new File(getClass().getClassLoader().getResource("cbmax-testcase-fixture.xls").getFile());

		workbook = Workbook.getWorkbook(testFixtureExcelFile);
		sheet = workbook.getSheet(0);

		for (int i = 1; i < sheet.getRows(); i++) {
			mmsContent = sheet.getCell(1, i).getContents();
			smsMessageCount = sheet.getCell(3, i).getContents();

			for (int j = 0; j < cardCompanyIds.length; j++) {
				creditCardCompany.setParser(CreditCardSmsParserFactory.getParser(cardCompanyIds[j]));
				
				List<CreditCardReceiptSms> parsedSmsList = creditCardCompany.getParser().receiptSmsParse(mmsContent);
				
				if (parsedSmsList.size() > 0) {
					matchCount += parsedSmsList.size();
					smsMessageCountTotal += Integer.parseInt(smsMessageCount);
				}
			}
		}

		assertEquals("excel파일에 기재한 건수와 parsing건수가 같아야 함", smsMessageCountTotal, matchCount);
	}
}
