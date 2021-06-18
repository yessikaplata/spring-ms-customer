package co.com.pragma.customer.servicecustomer;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileToBase64StringConversionTest {
	private String inputFilePath = "D:/imgs/test_image1.png";
	private String outputFilePath = "D:/imgs/test_image_copy1.png";
	
	
	@Test
	public void fileToBase64StringConversion() throws IOException {
		File inputFile = new File(inputFilePath);

		byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		System.out.println(encodedString);

		// create output file
		File outputFile = new File(outputFilePath);

		// decode the string and write to file
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		FileUtils.writeByteArrayToFile(outputFile, decodedBytes);

		Assertions.assertTrue(FileUtils.contentEquals(inputFile, outputFile));
	}
}
