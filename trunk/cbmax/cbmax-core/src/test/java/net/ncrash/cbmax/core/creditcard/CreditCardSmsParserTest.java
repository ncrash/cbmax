package net.ncrash.cbmax.core.creditcard;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import net.ncrash.cbmax.core.dto.CreditCardAutoPaymentSms;
import net.ncrash.cbmax.core.dto.CreditCardMonthlyPaymentsSms;
import net.ncrash.cbmax.core.dto.CreditCardPaymentSms;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreditCardSmsParserTest {
	static File testFixtureExcelFile;
	Workbook workbook;
	Sheet sheet;

	List<String> PaymentSmsList = new ArrayList<String>();
	StringBuffer sb;
	int matchCount;
	
	CreditCardCompany creditCardCompany;
	CreditCardPaymentSms creditCardPaymentSms;

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

		PaymentSmsList.add("씨티카드 강대권님 승인내역 03월13일 19:47 이마트구로점 40,410원 일시불");

		sb = new StringBuffer();
		sb.append("[일시불.승인]");
		sb.append("\n").append("186,000원");
		sb.append("\n").append("우리BC(3*8*)강대권님");
		sb.append("\n").append("04/07 23:17");
		sb.append("\n").append("양은냄비");
		PaymentSmsList.add(sb.toString());

		sb = new StringBuffer();
		sb.append("[일시불.승인]");
		sb.append("\n").append("348,600원");
		sb.append("\n").append("농협BC(1*0*)강대권님");
		sb.append("\n").append("01/24 16:16");
		sb.append("\n").append("(주)테크노에어포트몰");
		PaymentSmsList.add(sb.toString());

		PaymentSmsList.add("[KB카드]강대권님 카드가 04월12일 SK텔레콤-자동납부에서 13,080원 사용");

		sb = new StringBuffer();
		sb.append("[KB체크]");
		sb.append("\n").append("강대권님");
		sb.append("\n").append("04월05일12:47");
		sb.append("\n").append("광양불고기");
		sb.append("\n").append("52,000원 사용");
		PaymentSmsList.add(sb.toString());

		sb = new StringBuffer();
		sb.append("[KB카드]");
		sb.append("\n").append("강대권님");
		sb.append("\n").append("03월27일17:13");
		sb.append("\n").append("이마트구로점");
		sb.append("\n").append("40,480원 사용");
		PaymentSmsList.add(sb.toString());

		PaymentSmsList.add("신한카드정상승인강대권님        04/13 13:51     200,570원(일시불) （주）인터파크");
		PaymentSmsList.add("신한카드승인취소강대권님        04/13 13:57     200,570원(일시불) （주）인터파크");

		PaymentSmsList.add("[외환카드]강대권님     15,720원 승인 택시(서울)한국스 04/08 01:22");

		sb = new StringBuffer();
		sb.append("[현대카드C]");
		sb.append("\n").append("강대권님");
		sb.append("\n").append("12:10");
		sb.append("\n").append("2,400원(일시불)");
		sb.append("\n").append("정상승인");
		sb.append("\n").append("온누리조은약국");
		PaymentSmsList.add(sb.toString());

		PaymentSmsList.add("롯데카드 강대권님 939,900원 일시불 04/17 10:16 이베이옥션                       ");
		PaymentSmsList.add("롯데카드 강대권님 33,800원 일시불 04/21 23:55 (주)이니시스                      ");

		sb = new StringBuffer();
		sb.append("[일시불.승인]");
		sb.append("\n").append("22,500원");
		sb.append("\n").append("우리BC(0*5*)강대권님");
		sb.append("\n").append("04/24 19:27");
		sb.append("\n").append("(주)인터파크INT");
		PaymentSmsList.add(sb.toString());

		sb = new StringBuffer();
		sb.append("[승인취소]");
		sb.append("\n").append("22,500원");
		sb.append("\n").append("우리BC(0*5*)강대권님");
		sb.append("\n").append("04/24 19:28");
		sb.append("\n").append("(주)인터파크INT");
		PaymentSmsList.add(sb.toString());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	// TODO 씨티카드는 할부개월 및 승인취소 내역 실제 테스트 해야하고 체크카드 작업이 필요
	public void testCityCard() throws Exception {

		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("CITY"));

		matchCount += getSize(creditCardCompany.getParser());
		
		assertEquals(1, matchCount);
	}
	
	@Test
	// TODO 비씨카드 체크카드 발급 필요
	public void testBcCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("BC"));
		
		matchCount += getSize(creditCardCompany.getParser());
		
		assertEquals(4, matchCount);
	}
	
	@Test
	// TODO KB카드는 할부승인과 신용체크의 취소 필요
	public void testKbCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("KB"));
		
		matchCount += getSize(creditCardCompany.getParser());
		
		assertEquals(2, matchCount);
	}

	@Test
	// TODO 신한카드는 할부승인, 체크카드 승인, 체크카드 승인취소 처리 필요
	public void testShinhanCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("SHINHAN"));
		
		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(2, matchCount);
	}

	@Test
	// TODO 외환카드는 할부승인, 체크카드 승인, 체크카드 승인취소 처리 필요
	public void testYesCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("KEB"));
		
		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(1, matchCount);
	}

	@Test
	// TODO 현대카드는 신용승인, 할부승인, 신용 승인취소, 체크카드 승인취소 처리 필요
	public void testHyundaiCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("HYUNDAI"));
		
		matchCount += getSize(creditCardCompany.getParser());

		assertEquals(1, matchCount);
	}

	@Test
	// TODO 롯데카드는 할부승인, 신용 승인취소, 체크카드 승인, 승인취소 처리 필요
	public void testLotteCard() throws Exception {
		creditCardCompany.setParser(CreditCardSmsParserFactory.getParser("LOTTE"));
		
		matchCount += getSize(creditCardCompany.getParser());
		
		assertEquals(2, matchCount);
	}

	// "cbmax-testcase-fixture.xls" 파일을 내용을 읽어 정규표현식으로 확인이 가능한지 테스트
	@Test
	public void testExcelFixtureMmsContentParsing() throws Exception {
		String mmsContent;
		int excelTotalCount = 0;
		String excelPaymentSmsCount;
		String excelAutoPaymentSmsCount;
		String excelMonthlyPaymentsSmsCount;
		int smsMessageCountTotal = 0;

		String[] cardCompanyIds = {"BC", "CITY", "KB", "SHINHAN", "KEB", "HYUNDAI", "LOTTE", "SAMSUNG"};

		testFixtureExcelFile = new File(getClass().getClassLoader().getResource("cbmax-testcase-fixture.xls").getFile());

		workbook = Workbook.getWorkbook(testFixtureExcelFile);
		sheet = workbook.getSheet(0);

		for (int i = 1; i < sheet.getRows(); i++) {
			mmsContent = sheet.getCell(1, i).getContents();
			excelTotalCount += Integer.parseInt(sheet.getCell(3, i).getContents());
			excelPaymentSmsCount = sheet.getCell(4, i).getContents();
			excelAutoPaymentSmsCount = sheet.getCell(5, i).getContents();
			excelMonthlyPaymentsSmsCount = sheet.getCell(6, i).getContents();

			for (int j = 0; j < cardCompanyIds.length; j++) {
				creditCardCompany.setParser(CreditCardSmsParserFactory.getParser(cardCompanyIds[j]));
				
				List<CreditCardPaymentSms> parsedSmsList = creditCardCompany.getParser().paymentSmsParse(mmsContent);
				if (parsedSmsList != null && parsedSmsList.size() > 0) {
					matchCount += parsedSmsList.size();
					smsMessageCountTotal += Integer.parseInt(excelPaymentSmsCount);
				}
				
				List<CreditCardAutoPaymentSms> parsedAutoPaymentSmsList = creditCardCompany.getParser().autoPaymentSmsParse(mmsContent);
				if (parsedAutoPaymentSmsList != null && parsedAutoPaymentSmsList.size() > 0) {
					matchCount += parsedAutoPaymentSmsList.size();
					smsMessageCountTotal += Integer.parseInt(excelAutoPaymentSmsCount);
				}
				
				List<CreditCardMonthlyPaymentsSms> parsedMonthlyPaymentsSmsList = creditCardCompany.getParser().monthlyPaymentsSmsParse(mmsContent);
				if (parsedMonthlyPaymentsSmsList != null && parsedMonthlyPaymentsSmsList.size() > 0) {
					matchCount += parsedMonthlyPaymentsSmsList.size();
					smsMessageCountTotal += Integer.parseInt(excelMonthlyPaymentsSmsCount);
				}
				
				//TODO 현금서비스를 위한 test 수치조사 구문 완성하기 
//				List<CreditCardMonthlyPaymentsSms> parsedMonthlyPaymentsSmsList = creditCardCompany.getParser().monthlyPaymentsSmsParse(mmsContent);
//				if (parsedMonthlyPaymentsSmsList != null && parsedMonthlyPaymentsSmsList.size() > 0) {
//					matchCount += parsedMonthlyPaymentsSmsList.size();
//					smsMessageCountTotal += Integer.parseInt(excelMonthlyPaymentsSmsCount);
//				}
			}
		}

		assertEquals("excel파일에 기재한 건수와 parsing건수가 같아야 함", smsMessageCountTotal, matchCount);
		assertEquals((excelTotalCount - matchCount) + "건 차이로 total_sms_count와 parsing된 count가 동일 해야함", excelTotalCount, matchCount);
	}
	
	public int getSize(CreditCardSmsParser creditCardSmsParser) {
		for (int i = 0; i < PaymentSmsList.size(); i++) {
			String PaymentSms = PaymentSmsList.get(i);
			
			matchCount += creditCardSmsParser.paymentSmsParse(PaymentSms).size();
		}
		
		return matchCount;
	}
	
//	// PaymentSmsList 내용을 정규표현식으로 모두 찾아내는지 확인하는 테스트
//	@Test
//	public void testCardCompanyReceiptsSmsParser() throws Exception {
//		List<String> PaymentSmsParsers = CreditCardCompany.getPaymentSmsRegexList();
//
//		for (int i = 0; i < PaymentSmsList.size(); i++) {
//			Boolean isFind = false;
//			String PaymentSms = PaymentSmsList.get(i);
//
//			for (int j = 0; j < PaymentSmsParsers.size(); j++) {
//				String PaymentSmsParser = PaymentSmsParsers.get(j);
//
//				Pattern p = Pattern.compile(PaymentSmsParser);
//				Matcher m = p.matcher(PaymentSms);
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
//					Matcher m = p.matcher(PaymentSms);
//
//					while (m.find()) {
//						matchCount++;
//						isFind = true;
//					}
//				}
//			}
//		}
//
//		assertEquals(PaymentSmsList.size(), matchCount);
//	}
//
}
