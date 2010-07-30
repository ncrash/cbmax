package net.ncrash.cbmax.core.creditcard;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_ORDER;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * cbmax maildir 압축파일로 테스트
 * 테스트시 junit4의 temp directory에 압축파일을 해제해서 테스트
 *  
 * @author daekwon.kang
 * @since 2010. 7. 30.
 * @see
 */
public class GmailSmsFixtureTest {

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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testZipFileExtract() throws Exception {
		File testFixtureExcelFile = new File(getClass().getClassLoader().getResource("gmail-archive.zip").getFile());
		File extractFolder = folder.newFolder("temp");
		String[] fileList = new String[] {"gmail-backup.mbox", "cur", "new", "tmp"};
		List<String> destinationFileList = new ArrayList<String>();
		
		org.apache.cayenne.util.ZipUtil.unzip(testFixtureExcelFile, extractFolder);

		// unzip 후 파일 목록을 비교해서 정상적으로 압축이 풀렸는지 검사
		File destinationFile = new File(extractFolder.getPath() + "/" + "gmail-archive");
		
		for (File file : destinationFile.listFiles()) {
			destinationFileList.add(file.getName());
		}
		
		assertReflectionEquals(Arrays.asList(fileList), destinationFileList, LENIENT_ORDER);
	}
}