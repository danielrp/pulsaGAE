package com.pulsa.util.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.pulsa.persistence.dao.BalanceDAO;
import com.pulsa.persistence.dao.CustomerDAO;
import com.pulsa.persistence.dao.ProductDAO;
import com.pulsa.persistence.model.Balance;
import com.pulsa.persistence.model.Contact;
import com.pulsa.persistence.model.Customer;
import com.pulsa.persistence.model.MultiPartFileUpload;
import com.pulsa.persistence.model.Product;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {

	private String inputFile;
	
	private MultiPartFileUpload upload;
	
	private File file;
	
	

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public MultiPartFileUpload getUpload() {
		return upload;
	}

	public void setUpload(MultiPartFileUpload upload) {
		this.upload = upload;
	}

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	/**
	 * @throws IOException
	 */
	public void readProduct() throws IOException {
		//File inputWorkbook = new File(inputFile);
		//File inputWorkbook = (File) upload.getFile() ;
		InputStream in = upload.getFile().getInputStream();
		File inputWorkbook = file;
		Workbook w;
		try {
			//w = Workbook.getWorkbook(inputWorkbook);
			w = Workbook.getWorkbook(in);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines
			ProductDAO.INSTANCE.removeAll();
			System.out.println(" columns " + sheet.getColumns() + " rows "
					+ sheet.getRows());
			Cell cell ;
			DateCell dCell;
			NumberCell nCell;
			
			String code;
			String provider;
			long cost,price;
			int nominal;
			for (int i = 1; i < sheet.getRows(); i++) {
				int col=0;
				code=sheet.getCell(col++, i).getContents();
				provider=sheet.getCell(col++, i).getContents();
				try{
					nCell= (NumberCell)sheet.getCell(col++, i);
					nominal=(int)nCell.getValue();
				}catch(Exception e){
					nominal=0;
				}
				try{
					nCell= (NumberCell)sheet.getCell(col++, i);
					cost=(long)nCell.getValue();
				}catch(Exception e){
					cost=0;
				}
				try{
					nCell= (NumberCell)sheet.getCell(col++, i);
					price=(long)nCell.getValue();
				}catch(Exception e){
					price=0;
				}
				//System.out.println(urut+" | "+tanggal+" | "+product+" | "+custName+" | "+number+" | "+price+" | "+paid+" | "+debit);
				Product b=new Product();
				b.setCode(code);
				b.setProvider(provider);
				b.setCost(cost);
				b.setNominal(nominal);
				b.setPrice(price);
				//b.setUrut(urut);
				
				ProductDAO.INSTANCE.save(b);
				
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}
	
	public void read() throws IOException {
		//File inputWorkbook = new File(inputFile);
		//File inputWorkbook = (File) upload.getFile() ;
		InputStream in = upload.getFile().getInputStream();
		File inputWorkbook = file;
		Workbook w;
		try {
			//w = Workbook.getWorkbook(inputWorkbook);
			w = Workbook.getWorkbook(in);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines
			BalanceDAO.INSTANCE.removeAll();
			System.out.println(" columns " + sheet.getColumns() + " rows "
					+ sheet.getRows());
			Cell cell ;
			DateCell dCell;
			NumberCell nCell;
			int urut;
			Date tanggal;
			String product;
			String custName;
			String number;
			long price;
			String paid;
			long debit;
			long lastBal=0;
			for (int i = 1; i < sheet.getRows(); i++) {
				int col=0;
				try{
					nCell= (NumberCell)sheet.getCell(col++, i);
					urut=(int)nCell.getValue();
				}catch(Exception e){
					urut=0;
				}
				try{
					dCell= (DateCell)sheet.getCell(col++, i);
					tanggal=dCell.getDate();
				}catch(Exception e){
					tanggal=null;
				}
				product=sheet.getCell(col++, i).getContents();
				custName=sheet.getCell(col++, i).getContents();
				number=sheet.getCell(col++, i).getContents();
				try{
					nCell= (NumberCell)sheet.getCell(col++, i);
					price=(long)nCell.getValue();
				}catch(Exception e){
					price=0;
				}
				cell=sheet.getCell(col++, i);
				if(cell.getContents().equalsIgnoreCase("ok")){
					paid="p";
				}else{
					paid="n";
				}
				try{
					nCell= (NumberCell)sheet.getCell(col++, i);
					debit=(long)nCell.getValue();
				}catch(Exception e){
					debit=0;
				}
				System.out.println(urut+" | "+tanggal+" | "+product+" | "+custName+" | "+number+" | "+price+" | "+paid+" | "+debit);
				Balance b=null;
				if(!product.equalsIgnoreCase("")){
					b=new Balance("Sell",urut,"daniel");
					b.setKredit(-debit);
					b.setDebet(0);
					b.setProfit(price+debit);
					b.setNumber(number);
				}else{
					b=new Balance("Buy",urut,"daniel");
					b.setDebet(debit);
					b.setKredit(0);
					b.setProfit(0);
				}
				b.setPrice(price);
				//b.setUrut(urut);
				b.setCustName(custName);
				b.setInputDate(new Date());
				b.setTransactionDate(tanggal);
				b.setProduct(product);
				b.setStatus("r");
				b.setPaid(paid);
				lastBal=lastBal+b.getDebet()-b.getKredit();
				b.setBalance(lastBal);
				if(urut>0)
					BalanceDAO.INSTANCE.saveBalance(b);
				/*for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					CellType type = cell.getType();
					if (type == CellType.DATE) {
						DateCell dCell=(DateCell)cell;
						System.out.println("I got a Date "+ dCell.getDate());
					}
					if (type == CellType.LABEL) {
						System.out.println("I got a label "+ cell.getContents());
					}

					if (type == CellType.NUMBER) {
						NumberCell nCell=(NumberCell)cell;
						System.out.println("I got a number : "+ nCell.getValue());
						//System.out.println("I got a number : "+ cell.getContents()+" format :"+formatNumber(cell.getContents()));
					}

				}*/
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}
	
	public void readCustomer() throws IOException {
		//File inputWorkbook = new File(inputFile);
		//File inputWorkbook = (File) upload.getFile() ;
		InputStream in = upload.getFile().getInputStream();
		File inputWorkbook = file;
		Workbook w;
		try {
			//w = Workbook.getWorkbook(inputWorkbook);
			w = Workbook.getWorkbook(in);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines
			CustomerDAO.INSTANCE.removeAll();
			System.out.println(" columns " + sheet.getColumns() + " rows "
					+ sheet.getRows());
			Cell cell ;
			DateCell dCell;
			NumberCell nCell;
			
			String nama;
			String desc;
			String prov;
			String number;
			int i=1;
			//for (int i = 1; i < sheet.getRows(); i++) {
			while( i < sheet.getRows()) {
				int col=0;
				nama=sheet.getCell(col++, i).getContents();
				desc=sheet.getCell(col++, i).getContents();
				prov=sheet.getCell(col++, i).getContents();
				number=sheet.getCell(col++, i).getContents();
				
				System.out.println(nama+" | "+desc+" | "+prov+" | "+number);
				Customer b=null;
				if(!nama.equals("")){
					System.out.println("nama==null");
					 b=CustomerDAO.INSTANCE.getCustomerByName(nama);
				}
				if(b==null){ //belom ada bikin baru
					b=new Customer();
					b.setName(nama);
					b.setDesc(desc);
					do{
						Contact c=new Contact();
						c.setDesc(prov);
						c.setNumber(number);
						b.getContacts().add(c);
						i++;
						if(i < sheet.getRows()){
							col=0;
							nama=sheet.getCell(col++, i).getContents();
							desc=sheet.getCell(col++, i).getContents();
							prov=sheet.getCell(col++, i).getContents();
							number=sheet.getCell(col++, i).getContents();
						}else{
							break;
						}
					}while(nama.equals("") && !number.equals(""));
				}else{ // udah ada 
					if(b.isNumberExist(number)){//udah ada gak usah di add lagi
						
					}else{
						Contact c=new Contact();
						c.setDesc(prov);
						c.setNumber(number);
						b.getContacts().add(c);
					}
					
				}
				//b.setPrice(price);
				//b.setUrut(urut);
				System.out.println(b+" = "+b.getId());
				CustomerDAO.INSTANCE.save(b);
				
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ReadExcel test = new ReadExcel();
		test.setInputFile("D:/daniel (8).xls");
		test.read();
	}
	
	public long formatNumber(String s){
		String num="";
		boolean skip=false;
		for(int i=0;i<s.length();i++){
			if(!skip){
				if(s.charAt(i)=='['){
					skip=true;
				}else{
					if(s.charAt(i)!=','){
						num+=s.charAt(i);
					}
				}
			}else{
				if(s.charAt(i)==']'){
					skip=false;
				}
			}
		}
		num= num.trim();
		double d=Double.parseDouble(num);
		long l=(long)d;
		return l;
	}

}