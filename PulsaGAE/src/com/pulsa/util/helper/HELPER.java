package com.pulsa.util.helper;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.pulsa.persistence.dao.BalanceDAO;
import com.pulsa.persistence.dao.ProductDAO;
import com.pulsa.persistence.dao.UserDAO;
import com.pulsa.persistence.model.UserInfo;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class HELPER {
	public static String getPaid(String s) {
		if (s.equals("p")) {
			return "Paid";
		} else if (s.equals("n")) {
			return "Not Paid";
		} else {
			return "N/A";
		}
	}

	public static String getPaid2(String s) {
		if (s.equals("p")) {
			return "Ok";
		} else if (s.equals("n")) {
			return "";
		} else {
			return "N/A";
		}
	}

	public static String format(long number) {
		String s = number + "";
		String formatted = "";
		int ctr = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			ctr++;
			if (ctr <= 3) {
				formatted = s.charAt(i) + formatted;
			} else {
				ctr = 0;
				formatted = s.charAt(i) + "," + formatted;
			}
		}
		return formatted;
	}

	public static void sendEmail() {
		try {

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			String msgBody = "...";
			String userSession = SessionHelper.getUsername();
			UserInfo u = UserDAO.INSTANCE.getUser();
			if(u==null) u = new UserInfo();
			try {
				String htmlBody = "<p>Hi "
						+ userSession
						+ ",</p><p>Please find attached file for your back up</p>"
						+ "<p>Best Regards, </p><br/><p>Pulsa Developer</p>"; // ...
				byte[] attachmentData = getAttachment(); // ...

				Multipart mp = new MimeMultipart();

				MimeBodyPart htmlPart = new MimeBodyPart();
				htmlPart.setContent(htmlBody, "text/html");
				mp.addBodyPart(htmlPart);

				MimeBodyPart attachment = new MimeBodyPart();
				attachment.setFileName("pulsa-transaction.xls");
				attachment.setDisposition(Part.ATTACHMENT);
				DataSource src = new ByteArrayDataSource(attachmentData,"application/x-ms-excel");
				DataHandler handler = new DataHandler(src);
				attachment.setDataHandler(handler);
				mp.addBodyPart(attachment);
				attachmentData = getAttachmentProduct(); 
				attachment = new MimeBodyPart();
				attachment.setFileName("pulsa-product.xls");
				attachment.setDisposition(Part.ATTACHMENT);
				src = new ByteArrayDataSource(attachmentData,"application/x-ms-excel");
				handler = new DataHandler(src);
				attachment.setDataHandler(handler);
				mp.addBodyPart(attachment);
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("danielrobertusp@gmail.com",
						"Pulsa"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						u.getEmail(), u.getName()));
				msg.setSubject("Pulsa : your excel back up");
				msg.setText(msgBody);
				msg.setContent(mp);
				Transport.send(msg);
				// if ("1".equals(System.getProperty("debug"))) {
				// System.out.println("email sent");
				// File asd=new File("/sentEmail.eml");
				// System.out.println(asd.getAbsolutePath());
				// msg.writeTo(System.out);
				// }
				// System.out.println("email sent");
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}

		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}
	
	public static byte[] getAttachment(){
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
			WriteExcel we = new WriteExcel();
			// Create the workbook with the output stream of the response
			WritableWorkbook jxlWorkbook = Workbook.createWorkbook(outputStream);
			jxlWorkbook.createSheet("transaction", 0);
			WritableSheet excelSheet = jxlWorkbook.getSheet(0);
			excelSheet = we.addHeader(excelSheet);
			excelSheet = we.addContent(excelSheet, BalanceDAO.INSTANCE.listBalance());

			jxlWorkbook.write();
			jxlWorkbook.close();
			return outputStream.toByteArray();
		
		} catch (Exception ex) {

			ex.printStackTrace();
			return null;
		}
	}
	
	public static byte[] getAttachmentProduct(){
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
			WriteExcel we = new WriteExcel();
			// Create the workbook with the output stream of the response
			WritableWorkbook jxlWorkbook = Workbook.createWorkbook(outputStream);
			jxlWorkbook.createSheet("product", 0);
			WritableSheet excelSheet = jxlWorkbook.getSheet(0);
			excelSheet = we.addHeaderProduct(excelSheet);
			excelSheet = we.addContentProduct(excelSheet, ProductDAO.INSTANCE.listProduct());

			jxlWorkbook.write();
			jxlWorkbook.close();
			return outputStream.toByteArray();
		
		} catch (Exception ex) {

			ex.printStackTrace();
			return null;
		}
	}
}
