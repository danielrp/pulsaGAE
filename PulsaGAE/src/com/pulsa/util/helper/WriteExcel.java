package com.pulsa.util.helper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import java.util.ArrayList;
import java.util.List;
import com.pulsa.persistence.model.Balance;
import com.pulsa.persistence.model.Contact;
import com.pulsa.persistence.model.Customer;
import com.pulsa.persistence.model.Product;

public class WriteExcel {

	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String inputFile;

	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void write() throws IOException, WriteException {
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Report", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		createLabel(excelSheet);
		createContent(excelSheet);

		workbook.write();
		workbook.close();
	}

	private void createLabel(WritableSheet sheet) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(
				WritableFont.TIMES, 10, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

		// Write a few headers
		addCaption(sheet, 0, 0, "Header 1");
		addCaption(sheet, 1, 0, "This is another header");

	}

	public WritableSheet addHeader(WritableSheet sheet) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(
				WritableFont.TIMES, 10, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		// cv.setFormat(times);
		// cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

		String[] header = { "No", "Date", "Product", "Customer", "Number",
				"Price", "Paid", "Debet/Kredit", "Balance", "Profit" };
		// Write a few headers
		WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		//cellFont.setColour(Colour.BLUE);
		
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		cellFormat.setBackground(Colour.AQUA);
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		Label label;
		for (int i = 0; i < header.length; i++) {

			label = new Label(i, 0, header[i],cellFormat);
			sheet.addCell(label);
			// addCaption(sheet, i, 0, header[i]);
			// cell=sheet.getColumnView(i);
			// cell.setAutosize(true);
		//	sheet.setColumnView(i, cv);
		}
		sheet.getSettings().setVerticalFreeze(1);
		return sheet;
	}
	
	public WritableSheet addHeaderProduct(WritableSheet sheet) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(
				WritableFont.TIMES, 12, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		//CellView cv = new CellView();
		// cv.setFormat(times);
		// cv.setFormat(timesBoldUnderline);
		//cv.setAutosize(true);

		String[] header = { "Product Code", "Provider", "Nominal", "Buy Price", "Sell Price",
				"Profit"};
		// Write a few headers
		
		WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		//cellFont.setColour(Colour.BLUE);
		
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		cellFormat.setBackground(Colour.AQUA);
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		Label label;
		for (int i = 0; i < header.length; i++) {

			label = new Label(i, 0, header[i],cellFormat);
			sheet.addCell(label);
			System.out.println(label.getString()+" "+label.getCellFormat().getBackgroundColour());
			// addCaption(sheet, i, 0, header[i]);
			// cell=sheet.getColumnView(i);
			// cell.setAutosize(true);
			//sheet.setColumnView(i, cv);
		}
		sheet.getSettings().setVerticalFreeze(1);
		
		return sheet;
	}
	
	public WritableSheet addHeaderCustomer(WritableSheet sheet) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);
		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(
				WritableFont.TIMES, 12, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		String[] header = { "Nama", "Description", "Provider", "Number"};
		// Write a few headers
		
		WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		//cellFont.setColour(Colour.BLUE);
		
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		cellFormat.setBackground(Colour.AQUA);
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		Label label;
		for (int i = 0; i < header.length; i++) {

			label = new Label(i, 0, header[i],cellFormat);
			sheet.addCell(label);
			System.out.println(label.getString()+" "+label.getCellFormat().getBackgroundColour());
		}
		sheet.getSettings().setVerticalFreeze(1);
		
		return sheet;
	}

	public WritableSheet addContent(WritableSheet sheet, List<Balance> balance)
			throws WriteException, RowsExceededException {
		// Write a few number
		Number number;
		Label label;
		DateTime dateCell;
		SimpleDateFormat format = new SimpleDateFormat("d-M-yyyy");
		DateFormat customDateFormat = new DateFormat ("dd-MMM-yyyy"); 
		WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat); 
		NumberFormat sep = new NumberFormat("#,##0"); 
		WritableCellFormat numFormat = new WritableCellFormat(sep); 
		for (int i = 0; i < balance.size(); i++) {
			Balance b = balance.get(i);
			int column = 0;
			number = new Number(column++, i + 1, b.getUrut());
			sheet.addCell(number);
			dateCell = new DateTime(column++, i + 1, b.getTransactionDate(), dateFormat); 
			//label = new Label(column++, i + 1, format.format(b
				//	.getTransactionDate()));
			sheet.addCell(dateCell);
			label = new Label(column++, i + 1, b.getProduct());
			sheet.addCell(label);
			if (b.getType().equals("Buy")) {
				label = new Label(column++, i + 1, "Top Up Saldo");
				sheet.addCell(label);
			} else {
				label = new Label(column++, i + 1, b.getCustName());
				sheet.addCell(label);
			}
			label = new Label(column++, i + 1, b.getNumber());
			sheet.addCell(label);
			number = new Number(column++, i + 1, b.getPrice(),numFormat);
			sheet.addCell(number);
			label = new Label(column++, i + 1, HELPER.getPaid2(b.getPaid()));
			sheet.addCell(label);
			number = new Number(column++, i + 1, b.getDebet() - b.getKredit(),numFormat);
			sheet.addCell(number);
			number = new Number(column++, i + 1, b.getBalance(),numFormat);
			sheet.addCell(number);
			number = new Number(column++, i + 1, b.getProfit(),numFormat);
			sheet.addCell(number);
		}
		
		int i =0;
		//no
		//CellView cv = sheet.getColumnView(i);
		//cv.setSize(4);	
		sheet.setColumnView(i++, 4);
		//date
		//cv = sheet.getColumnView(i);
		//cv.setSize(12);	
		sheet.setColumnView(i++, 12);
		//product
		//cv = sheet.getColumnView(i);
		//		cv.setSize(8);	
		sheet.setColumnView(i++, 8);
		//customer
		//cv = sheet.getColumnView(i);
		//cv.setSize(20);	
		sheet.setColumnView(i++, 20);
		//number
		//cv = sheet.getColumnView(i);
		//cv.setSize(15);	
		sheet.setColumnView(i++, 15);
		//price
		//cv = sheet.getColumnView(i);
		//cv.setSize(10);	
		sheet.setColumnView(i++, 10);
		//paid
		//cv = sheet.getColumnView(i);
		//cv.setSize(4);	
		sheet.setColumnView(i++, 5);
		//debet
		//cv = sheet.getColumnView(i);
		//cv.setSize(15);	
		sheet.setColumnView(i++, 12);
		//balance
		//cv = sheet.getColumnView(i);
		//cv.setSize(15);	
		sheet.setColumnView(i++, 10);
		//profit
		//cv = sheet.getColumnView(i);
		//cv.setSize(15);	
		sheet.setColumnView(i++, 10);
		
		//System.out.println("ASD");
		
		return sheet;
	}

	public WritableSheet addContentProduct(WritableSheet sheet,
			List<Product> products) throws WriteException,
			RowsExceededException {
		// Write a few number
		Number number;
		Label label;
		SimpleDateFormat format = new SimpleDateFormat("d-M-yyyy");
		for (int i = 0; i < products.size(); i++) {
			Product b = products.get(i);
			int column = 0;

			label = new Label(column++, i + 1, b.getCode());
			sheet.addCell(label);
			label = new Label(column++, i + 1, b.getProvider());
			sheet.addCell(label);
			number = new Number(column++, i + 1, b.getNominal());
			sheet.addCell(number);
			number = new Number(column++, i + 1, b.getCost());
			sheet.addCell(number);
			number = new Number(column++, i + 1, b.getPrice());
			sheet.addCell(number);
			number = new Number(column++, i + 1, b.getPrice()-b.getCost());
			sheet.addCell(number);
			
			
		}
		return sheet;
	}
	
	public WritableSheet addContentCustomer(WritableSheet sheet,
			List<Customer> customers) throws WriteException,
			RowsExceededException {
		// Write a few number
		Number number;
		Label label;
		SimpleDateFormat format = new SimpleDateFormat("d-M-yyyy");
		int row=1;
		for (int i = 0; i < customers.size(); i++) {
			Customer b = customers.get(i);
			
			
			List<Contact> contacts= b.getContacts();
			for (int j = 0; j < contacts.size(); j++) {
				int column = 0;
				Contact c = contacts.get(j);
				if(j==0){
					label = new Label(column++, row, b.getName());
					sheet.addCell(label);
					label = new Label(column++, row, b.getDesc());
					sheet.addCell(label);
				}else{
					column+=2;
				}
				label = new Label(column++,row, c.getDesc());
				sheet.addCell(label);
				label = new Label(column++, row, c.getNumber());
				sheet.addCell(label);
				row++;
				//number = new Number(column++,row, b.getPrice());
				//sheet.addCell(number);
				//number = new Number(column++,row++, b.getPrice()-b.getCost());
				//sheet.addCell(number);
			}
			
			
		}
		return sheet;
	}

	private void createContent(WritableSheet sheet) throws WriteException,
			RowsExceededException {
		// Write a few number
		for (int i = 1; i < 10; i++) {
			// First column
			addNumber(sheet, 0, i, i + 10);
			// Second column
			addNumber(sheet, 1, i, i * i);
		}
		// Lets calculate the sum of it
		StringBuffer buf = new StringBuffer();
		buf.append("SUM(A2:A10)");
		Formula f = new Formula(0, 10, buf.toString());
		sheet.addCell(f);
		buf = new StringBuffer();
		buf.append("SUM(B2:B10)");
		f = new Formula(1, 10, buf.toString());
		sheet.addCell(f);

		// Now a bit of text
		for (int i = 12; i < 20; i++) {
			// First column
			addLabel(sheet, 0, i, "Boring text " + i);
			// Second column
			addLabel(sheet, 1, i, "Another text");
		}
	}

	private void addCaption(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}

	private void addNumber(WritableSheet sheet, int column, int row,
			Integer integer) throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, integer, times);
		sheet.addCell(number);
	}

	private void addLabel(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}

	public static void main(String[] args) throws WriteException, IOException {
		WriteExcel test = new WriteExcel();
		test.setOutputFile("d:/lars.xls");
		test.write();
		System.out
				.println("Please check the result file under c:/temp/lars.xls ");
	}
}