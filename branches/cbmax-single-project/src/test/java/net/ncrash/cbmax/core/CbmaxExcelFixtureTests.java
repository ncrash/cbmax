package net.ncrash.cbmax.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;

import net.ncrash.cbmax.core.receiptMessageResolver;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for the {@link receiptMessageResolver} class.
 *
 * <p>cbmax-testcase-fixture.xls test case
 *
 * @author Daekwon Kang
 * @since 2010. 4. 7.
 * @see
 */
public class CbmaxExcelFixtureTests {
	static File testFixtureExcelFile;
	Workbook workbook;
	Sheet sheet;

	String smsContent;
	String mmsContent;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testFixtureExcelFile = new File(getClass().getClassLoader().getResource("cbmax-testcase-fixture.xls").getFile());

		workbook = Workbook.getWorkbook(testFixtureExcelFile);
		sheet = workbook.getSheet(0);
	}

	@After
	public void tearDown() throws Exception {
		workbook.close();
	}

	@Test
	public void excelFileLoad() throws Exception {
		assertTrue(sheet.getRows() > 0);

		mmsContent = sheet.getCell(1, 1).getContents();
		smsContent = sheet.getCell(2, 1).getContents();

		assertTrue(mmsContent.length() > 0);
		assertTrue(smsContent.length() > 0);
	}

	@Test
	public void convertMmsToSms() throws Exception {
		for (int i = 1; i < sheet.getRows(); i++) {
			mmsContent = sheet.getCell(1, i).getContents();
			smsContent = sheet.getCell(2, i).getContents();

			assertEquals("mms�� sms�� convert�� ����� excel�� ����� sms ����� �����ؾ� �Ѵ�.", smsContent, receiptMessageResolver
					.convertOriginalSms(mmsContent));
		}
	}

	@Test
	public void testjavacode() throws Exception {
		// �ܼ��� javacode �׽�Ʈ�ϱ� ���� ����� �� test method
		
		String abcd = "����Ǵ� 4/1���� �츮BC �������Դϴ�. ȯ���� ���� �����ϼ��� ";
		System.out.println(abcd.getBytes().length);
	}
}