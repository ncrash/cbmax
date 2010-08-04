package net.ncrash.cbmax.core.creditcard;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import net.ncrash.cbmax.core.dto.CreditCardAutoPaymentSms;
import net.ncrash.cbmax.core.dto.CreditCardCashServicesSms;
import net.ncrash.cbmax.core.dto.CreditCardMonthlyPaymentsSms;
import net.ncrash.cbmax.core.dto.CreditCardNotificationSms;
import net.ncrash.cbmax.core.dto.CreditCardPaymentSms;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 신용카드사에서 발송하는 SMS(사용내역, 월결제액, ...) parser excel fixture를 통한 test
 * 
 * @author Daekwon.Kang
 * @since 2010.04.26 
 * @see 
 */
public class CreditCardSmsParserExcelFixtureTest {
	static File testFixtureExcelFile;
	Workbook workbook;
	Sheet sheet;

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
	}

	@After
	public void tearDown() throws Exception {
	}

	// "cbmax-testcase-fixture.xls" 파일을 내용을 읽어 정규표현식으로 확인이 가능한지 테스트
	@Test
	public void testExcelFixtureMmsContentParsing() throws Exception {
		String mmsContent;
		int excelTotalCount = 0;
		String excelPaymentSmsCount;
		String excelAutoPaymentSmsCount;
		String excelMonthlyPaymentsSmsCount;
		String excelCashServicesSmsCount;
		String excelNotificationSmsCount;
		String excelUnmanagedSmsCount;
		int smsMessageCountTotal = 0;

		String[] cardCompanyIds = {"BC", "CITY", "KB", "SHINHAN", "KEB", "HYUNDAI", "LOTTE", "SAMSUNG", "HANA_SK"};

		testFixtureExcelFile = new File(getClass().getClassLoader().getResource("cbmax-testcase-fixture.xls").getFile());

		workbook = Workbook.getWorkbook(testFixtureExcelFile);
		sheet = workbook.getSheet(0);

		for (int i = 1; i < sheet.getRows(); i++) {
			mmsContent = sheet.getCell(1, i).getContents();
			excelTotalCount += Integer.parseInt(sheet.getCell(3, i).getContents());
			excelPaymentSmsCount = sheet.getCell(4, i).getContents();
			excelAutoPaymentSmsCount = sheet.getCell(5, i).getContents();
			excelMonthlyPaymentsSmsCount = sheet.getCell(6, i).getContents();
			excelCashServicesSmsCount = sheet.getCell(7, i).getContents();
			excelNotificationSmsCount = sheet.getCell(8, i).getContents();
			excelUnmanagedSmsCount = sheet.getCell(9, i).getContents();

			for (int j = 0; j < cardCompanyIds.length; j++) {
				creditCardCompany.setParser(CreditCardSmsParserFactory.getParser(cardCompanyIds[j]));
				
				List<CreditCardPaymentSms> parsedSmsList = creditCardCompany.getParser().paymentSmsParse(mmsContent);
				if (parsedSmsList != null && parsedSmsList.size() > 0) {
					assertEquals("excel의 count수치와 parse 결과건수가 동일해야함 내용 : \n" + mmsContent, parsedSmsList.size(), Integer.parseInt(excelPaymentSmsCount));
					matchCount += parsedSmsList.size();
					smsMessageCountTotal += Integer.parseInt(excelPaymentSmsCount);
				}
				
				List<CreditCardAutoPaymentSms> parsedAutoPaymentSmsList = creditCardCompany.getParser().autoPaymentSmsParse(mmsContent);
				if (parsedAutoPaymentSmsList != null && parsedAutoPaymentSmsList.size() > 0) {
					assertEquals("excel의 count수치와 parse 결과건수가 동일해야함 내용 : \n" + mmsContent, parsedAutoPaymentSmsList.size(), Integer.parseInt(excelAutoPaymentSmsCount));
					matchCount += parsedAutoPaymentSmsList.size();
					smsMessageCountTotal += Integer.parseInt(excelAutoPaymentSmsCount);
				}
				
				List<CreditCardMonthlyPaymentsSms> parsedMonthlyPaymentsSmsList = creditCardCompany.getParser().monthlyPaymentsSmsParse(mmsContent);
				if (parsedMonthlyPaymentsSmsList != null && parsedMonthlyPaymentsSmsList.size() > 0) {
					assertEquals("excel의 count수치와 parse 결과건수가 동일해야함 내용 : \n" + mmsContent, parsedMonthlyPaymentsSmsList.size(), Integer.parseInt(excelMonthlyPaymentsSmsCount));
					matchCount += parsedMonthlyPaymentsSmsList.size();
					smsMessageCountTotal += Integer.parseInt(excelMonthlyPaymentsSmsCount);
				}
				
				List<CreditCardCashServicesSms> parsedCardCashServicesSmsList = creditCardCompany.getParser().cashServiceSmsParse(mmsContent);
				if (parsedCardCashServicesSmsList != null && parsedCardCashServicesSmsList.size() > 0) {
					assertEquals("excel의 count수치와 parse 결과건수가 동일해야함 내용 : \n" + mmsContent, parsedCardCashServicesSmsList.size(), Integer.parseInt(excelCashServicesSmsCount));
					matchCount += parsedCardCashServicesSmsList.size();
					smsMessageCountTotal += Integer.parseInt(excelCashServicesSmsCount);
				}
				
				List<CreditCardNotificationSms> parsedNotificationSmsList = creditCardCompany.getParser().notificationSmsParse(mmsContent);
				if (parsedNotificationSmsList != null && parsedNotificationSmsList.size() > 0) {
					assertEquals("excel의 count수치와 parse 결과건수가 동일해야함 내용 : \n" + mmsContent, parsedNotificationSmsList.size(), Integer.parseInt(excelNotificationSmsCount));
					matchCount += parsedNotificationSmsList.size();
					smsMessageCountTotal += Integer.parseInt(excelNotificationSmsCount);
				}
				
			}
			//TODO unmanaged_count를 산출하기 위해서는 처리되야할 로직은 위의 5가지 분류를 통해
			//매칭된 내용이 없는 mmsContent 만을 추출해서 counting처리를 해서 unmanagedcount처리
			//excelUnmanagedSmsCount
			matchCount += Integer.parseInt(excelUnmanagedSmsCount);
			smsMessageCountTotal += Integer.parseInt(excelUnmanagedSmsCount);
		}

		assertEquals("excel파일에 기재한 건수와 parsing건수가 같아야 함", smsMessageCountTotal, matchCount);
		assertEquals((excelTotalCount - matchCount) + "건 차이로 total_sms_count와 parsing된 count가 동일 해야함", excelTotalCount, matchCount);
	}
}
