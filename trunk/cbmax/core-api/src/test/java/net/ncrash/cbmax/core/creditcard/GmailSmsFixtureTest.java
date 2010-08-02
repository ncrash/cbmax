package net.ncrash.cbmax.core.creditcard;

import static org.junit.Assert.assertEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_ORDER;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import jxl.Sheet;
import jxl.Workbook;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * cbmax maildir 압축파일로 테스트 테스트시 junit4의 temp directory에 압축파일을 해제해서 테스트
 * 
 * @author daekwon.kang
 * @since 2010. 7. 30.
 * @see
 */
public class GmailSmsFixtureTest {
	File gmailArchiveFile;
	File extractTemporaryFolder;
	File extractedDirectory;
	String[] extractedDirectoryItems = new String[] { "gmail-backup.mbox",
			"cur", "new", "tmp" };

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		extractTemporaryFolder = folder.newFolder("temp");
		gmailArchiveFile = new File(getClass().getClassLoader().getResource(
				"gmail-archive.zip").getFile());

		org.apache.cayenne.util.ZipUtil.unzip(gmailArchiveFile,
				extractTemporaryFolder);

		extractedDirectory = new File(extractTemporaryFolder.getPath() + "/"
				+ "gmail-archive");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testZipFileExtract() throws Exception {
		// unzip 후 파일 목록을 비교해서 정상적으로 압축이 풀렸는지 검사
		List<String> destinationFileList = new ArrayList<String>();
		for (File file : extractedDirectory.listFiles()) {
			destinationFileList.add(file.getName());
		}

		assertReflectionEquals(Arrays.asList(extractedDirectoryItems),
				destinationFileList, LENIENT_ORDER);
	}

	@Test
	// 파일 내용에서 신용카드 sms 메일만 읽어들였는지 체크
	public void testMailCheck() throws Exception {
		Session session = Session.getInstance(new Properties());

		String url = "maildir:" + extractedDirectory.getAbsolutePath();
		Store store = session.getStore(new URLName(url));
		store.connect(); // useless with Maildir but included here for consistency
		
		Folder inbox = store.getFolder("inbox");
		inbox.open(Folder.READ_WRITE);
		
		List<String> mailMmsContentList = new ArrayList<String>();
		List<String> excelMmsContentList = new ArrayList<String>();
		
		for (Message m : inbox.getMessages()) {
			
			if (m.isMimeType("text/plain")) {
			} else {
				Multipart mp = (Multipart) m.getContent();
				BodyPart bp = mp.getBodyPart(0);
				if (bp.isMimeType("text/plain")) {
				} else {
					Multipart mp2 = (Multipart) bp.getContent();
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					mp2.getBodyPart(0).writeTo(baos);
					mailMmsContentList.add(baos.toString("euc-kr"));
				}

				m.setFlag(Flags.Flag.DELETED, true);
			}

		}
		inbox.close(true);
		store.close();
		
		File testFixtureExcelFile = new File(getClass().getClassLoader().getResource("maildir_read_test.xls").getFile());

		Workbook workbook = Workbook.getWorkbook(testFixtureExcelFile);
		Sheet sheet = workbook.getSheet(0);
		
		for (int i = 1; i < sheet.getRows(); i++) {
			excelMmsContentList.add(sheet.getCell(1, i).getContents());
		}
		
		assertEquals("excel파일과 mail파일의 처리건수가 동일해야한다.", excelMmsContentList.size(), mailMmsContentList.size());

		assertReflectionEquals("excel 파일에 정의된 내용대로 mail 파일 파싱한 결과가 동일한지 확인", excelMmsContentList, mailMmsContentList, LENIENT_ORDER);
	}
}