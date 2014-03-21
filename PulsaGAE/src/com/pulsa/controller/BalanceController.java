package com.pulsa.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.activation.DataHandler;
import javax.activation.DataSource;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pulsa.persistence.dao.CustomerDAO;
import com.pulsa.persistence.dao.BalanceDAO;
import com.pulsa.persistence.dao.ProductDAO;
import com.pulsa.persistence.model.Contact;
import com.pulsa.persistence.model.Customer;
import com.pulsa.persistence.model.Balance;
import com.pulsa.persistence.model.PageForm;
import com.pulsa.persistence.model.Product;
import com.pulsa.persistence.model.Sell;
import com.pulsa.persistence.model.Buy;
import com.pulsa.persistence.model.TransaksiForm;
import com.pulsa.util.helper.HELPER;
import com.pulsa.util.helper.KeyStringConverter;
import com.pulsa.util.helper.ReadExcel;
import com.pulsa.util.helper.SessionHelper;
import com.pulsa.util.helper.WriteExcel;
import com.google.appengine.api.datastore.Key;

@Controller
@RequestMapping("/transaksi/")
public class BalanceController {

	List<Balance> list = null;
	List<Balance> listView = null;
	List<Customer> listCust = null;
	TransaksiForm page = null;
	String paid = "n";
	String search = "";
	long debet = 0;
	long kredit = 0;
	long profit = 0;
	long receivable = 0;
	long payable = 0;

	@InitBinder
	protected void initBinder(HttpServletRequest req,
			ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Key.class, new KeyStringConverter());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(
			ModelMap model,
			@RequestParam(value = "p", defaultValue = "0", required = false) String p,
			@RequestParam(value = "pay", defaultValue = "a", required = false) String pay,
			@RequestParam(value = "search", defaultValue = "", required = false) String s) {

		if (pay != null)
			paid = pay;
		search = s;

		// if(list==null){
		list = BalanceDAO.INSTANCE.listBalance();
		listCust = CustomerDAO.INSTANCE.listCustomers();
		filterList();
		getPage();
		int pg = 0;
		if (p != null)
			pg = Integer.parseInt(p);
		if (pg != 0) {
			page.setCurrent(pg);
		}
		// System.out.println("List View : "+listView.size()+" listview asd "+page.getCurrent());
		// System.out.println("page= "+page+" size "+page.getSize()+" p "+p+" current "+page.getCurrent()+" begin "+page.getBegin()+" end "+page.getEnd());
		// }
		/*
		 * try{ ReadExcel test = new ReadExcel();
		 * test.setInputFile("D:/eclipse/pulsaGAE/PulsaGAE/war/daniel (8).xls");
		 * test.read(); }catch(Exception e){ e.printStackTrace(); }
		 */

		Balance sell = new Balance("Sell", getUrut());// new Sell(getUrut());

		model.addAttribute("listBalance", listView);
		model.addAttribute("listCust", listCust);
		model.addAttribute("sellForm", sell);
		// model.addAttribute("buyForm", new Buy(getUrut()));
		model.addAttribute("buyForm", new Balance("Buy", getUrut()));
		// System.out.println("model "+model);
		// System.out.println("sell "+sell+" "+sell.getId());
		model.addAttribute("balanceForm", new Balance());
		model.addAttribute("customerForm", new Customer());
		model.addAttribute("page", page);
		return "balance";

	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(HttpServletResponse response) {
		try {
			response.setContentType("application/x-download");

			response.setHeader("Content-Disposition",
					"attachment; filename=pulsa-trasaction.xls");
			WriteExcel we = new WriteExcel();
			// Create the workbook with the output stream of the response
			WritableWorkbook jxlWorkbook = Workbook.createWorkbook(response
					.getOutputStream());
			jxlWorkbook.createSheet("Trasaction", 0);
			WritableSheet excelSheet = jxlWorkbook.getSheet(0);
			excelSheet = we.addHeader(excelSheet);
			excelSheet = we.addContent(excelSheet, list);
			jxlWorkbook.write();
			jxlWorkbook.close();
			// Finally close the stream
			response.getOutputStream().close();
			response.flushBuffer();
		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public String email(ModelMap model) {
		HELPER.sendEmail();
		filterList();
		getPage();
		model.addAttribute("listCust", listCust);
		model.addAttribute("listBalance", listView);
		model.addAttribute("sellForm", new Balance("Sell", getUrut()));
		model.addAttribute("buyForm", new Balance("Buy", getUrut()));
		model.addAttribute("page", page);
		// return "balance";
		return "balance";
	}

	public byte[] getAttachment() {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			WriteExcel we = new WriteExcel();
			// Create the workbook with the output stream of the response
			WritableWorkbook jxlWorkbook = Workbook
					.createWorkbook(outputStream);
			jxlWorkbook.createSheet("Report", 0);
			WritableSheet excelSheet = jxlWorkbook.getSheet(0);
			excelSheet = we.addHeader(excelSheet);
			excelSheet = we.addContent(excelSheet, list);

			jxlWorkbook.write();
			jxlWorkbook.close();
			return outputStream.toByteArray();
			// Finally close the stream
			// response.getOutputStream().close();
			// response.flushBuffer();
		} catch (Exception ex) {

			ex.printStackTrace();
			return null;
		}
	}

	public void getPage() {
		summary();
		int p = 1;
		boolean flag = false;
		if (page != null) {
			p = page.getCurrent();
			flag = true;
		}
		page = new TransaksiForm(listView.size(), 15);
		if (flag) {
			page.setCurrent(p);
		}
		// System.out.println("view size : "+listView.size()+" cur "+page.getCurrent()+" tot "+page.getTotal());
		page.setPaid(paid);
		page.setBalance(getLastBalance());
		page.setDebet(debet);
		page.setKredit(kredit);
		page.setProfit(profit);
		page.setReceivable(receivable);
		page.setPayable(payable);
		page.setSearch(search);
	}

	@RequestMapping(value = "/sell", method = RequestMethod.POST)
	public String sell(ModelMap model, Balance form) {
		// Customer
		// custInserted=CustomerDAO.INSTANCE.add(form.getName(),form.getDesc());
		form.setProfit(form.getPrice() - form.getKredit());
		form.setBalance(getLastBalance(form.getUrut()) + form.getDebet()
				- form.getKredit());
		Balance sellInserted = BalanceDAO.INSTANCE.saveBalance(form);
		if (sellInserted != null) {
			//tambahin nomor kalo belom ada
			Product product = ProductDAO.INSTANCE.getProductByCode(form.getProduct());
			//System.out.println("product price ="+product.getPrice()+" --- "+form.getPrice() );
			if(product!=null){//produk udah ada di cek aja kalo harga berubah langsung save
				if(product.getCost()!=form.getKredit()){
					System.out.println("price tidak sama");
					product.setCost(form.getKredit());
					ProductDAO.INSTANCE.save(product);
				}
			}else{//product lom ada di save aja
				product= new Product();
				product.setCode(form.getProduct());
				product.setPrice(form.getPrice());
				product.setCost(form.getKredit());
				product.setUsername(SessionHelper.getUsername());
				ProductDAO.INSTANCE.save(product);
			}
			Customer cust=CustomerDAO.INSTANCE.getCustomerByName(form.getCustName());
			if(cust!=null){//customer udah ada.. cek dulu udah ada belom no nya
				if(!cust.isNumberExist(form.getNumber())){//kalo no nya belom ada ditambahin 
					Contact contact=new Contact();
					contact.setNumber(form.getNumber());
					if(product!=null){
						contact.setDesc(product.getProvider());
						contact.setProvider(product.getProvider());
					}
					cust.getContacts().add(contact);
					CustomerDAO.INSTANCE.save(cust);
				}
			}else{
				cust=new Customer();
				cust.setName(form.getCustName());
				cust.setDesc("autosave");
				Contact contact=new Contact();
				contact.setNumber(form.getNumber());
				if(product!=null){
					contact.setDesc(product.getProvider());
					contact.setProvider(product.getProvider());
				}
				cust.getContacts().add(contact);
				CustomerDAO.INSTANCE.save(cust);
				
			}
			
			//tambahin di list yang buat di view;
			if (list != null) {
				boolean exist = false;
				// System.out.println(" " + list.size());
				List<Balance> list2 = new ArrayList();
				boolean update = false;
				for (int i = 0; i < list.size(); i++) {
					Balance compare = list.get(i);
					if (compare.getId().getId() == sellInserted.getId().getId()) {
						list2.add(sellInserted);
						exist = true;
						update = true;
					} else {
						if (update) {
							long saldo = BalanceDAO.INSTANCE.getLastBalance(
									list2, compare.getUrut())
									+ compare.getDebet() - compare.getKredit();
							if (compare.getBalance() != saldo) {
								compare.setBalance(saldo);
								BalanceDAO.INSTANCE.saveBalance(compare);
							}
							// System.out.println(compare.getType() + " " +
							// compare.getBalance());
							/*
							 * if (compare.getType().equals("Sell")) { Sell s =
							 * (Sell) list.get(i); s.setBalance(saldo);
							 * BalanceDAO.INSTANCE.saveBalance(s); } else { Buy
							 * b = (Buy) list.get(i); b.setBalance(saldo);
							 * BalanceDAO.INSTANCE.saveBuy(b); }
							 */

						}
						list2.add(compare);
					}
				}
				if (!exist) {
					list2.add(sellInserted);
				}
				list = list2;
			}
		}
		// List<Customer> list=CustomerDAO.INSTANCE.listCustomers();
		filterList();
		getPage();
		model.addAttribute("listCust", listCust);
		model.addAttribute("listBalance", listView);
		model.addAttribute("sellForm", new Balance("Sell", getUrut()));
		model.addAttribute("buyForm", new Balance("Buy", getUrut()));
		model.addAttribute("page", page);
		return "balance";
	}

	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public String buy(ModelMap model, Balance form) {
		// Customer
		// custInserted=CustomerDAO.INSTANCE.add(form.getName(),form.getDesc());
		// form.setProfit(form.getPrice()-form.getKredit());
		form.setProfit(form.getDebet() - form.getPrice());
		form.setCustName("Top Up Saldo");
		form.setKredit(0);
		form.setBalance(getLastBalance(form.getUrut()) + form.getDebet()
				- form.getKredit());
		Balance buyInserted = BalanceDAO.INSTANCE.saveBalance(form);
		if (buyInserted != null) {
			if (list != null) {
				boolean exist = false;
				// System.out.println(" " + list.size());
				List<Balance> list2 = new ArrayList();
				boolean update = false;
				for (int i = 0; i < list.size(); i++) {
					Balance compare = list.get(i);
					if (compare.getId().getId() == buyInserted.getId().getId()) {
						list2.add(buyInserted);
						exist = true;
						update = true;
					} else {
						if (update) {
							long saldo = BalanceDAO.INSTANCE.getLastBalance(
									list2, compare.getUrut())
									+ compare.getDebet() - compare.getKredit();
							if (compare.getBalance() != saldo) {
								compare.setBalance(saldo);
								BalanceDAO.INSTANCE.saveBalance(compare);
							}
							// System.out.println(compare.getType() + " " +
							// compare.getBalance());
						}
						list2.add(compare);
					}
				}
				if (!exist) {
					list2.add(buyInserted);
				}
				list = list2;
			}
		}
		// List<Customer> list=CustomerDAO.INSTANCE.listCustomers();
		filterList();
		getPage();
		model.addAttribute("listCust", listCust);
		model.addAttribute("listBalance", listView);
		model.addAttribute("sellForm", new Balance("Sell", getUrut()));
		model.addAttribute("buyForm", new Balance("Buy", getUrut()));
		model.addAttribute("page", page);
		return "balance";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(ModelMap model, long id) {

		// boolean deleted=CustomerDAO.INSTANCE.remove(id);
		// System.out.println("deleted "+deleted);
		Balance s = BalanceDAO.INSTANCE.getBalance(id);
		Balance b = BalanceDAO.INSTANCE.getBalance(id);
		if (s.getType().equals("Buy"))
			s = new Balance("Sell", getUrut());
		if (b.getType().equals("Sell"))
			b = new Balance("Buy", getUrut());
		filterList();
		getPage();
		// Balance c = BalanceDAO.INSTANCE.getSell(id);
		model.addAttribute("listCust", listCust);
		model.addAttribute("listBalance", listView);
		model.addAttribute("sellForm", s);
		model.addAttribute("buyForm", b);
		model.addAttribute("page", page);

		return "balance";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(ModelMap model) {

		model.addAttribute("listBalance", listView);

		return "balanceprint";
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String remove(ModelMap model, long id) {
		boolean deleted = BalanceDAO.INSTANCE.removeBalance(id);

		if (deleted) {

			List<Balance> list2 = new ArrayList();
			long bal = 0;
			boolean flag = false;
			int urut = 0;
			for (int i = 0; i < list.size(); i++) {
				Balance c = list.get(i);

				// System.out.println(id+"="+c.getId()+"?"+(c.getId()==id));
				if (c.getId().getId() == id) {
					bal = c.getBalance() - c.getDebet() + c.getKredit();
					urut = c.getUrut();
					flag = true;
					// System.out.println("list size before removed : "+list.size()+" id="+id);
					// list.remove(i);
					// System.out.println("list size after removed : "+list.size()+"");
					// break;
				} else {
					if (flag) {
						bal = bal + c.getDebet() - c.getKredit();
						c.setBalance(bal);
						c.setUrut(urut++);
						BalanceDAO.INSTANCE.saveBalance(c);
					}
					list2.add(c);
				}
			}
			list = list2;
			// delete dari list;
		}
		filterList();
		getPage();
		// Balance c = BalanceDAO.INSTANCE.getSell(id);
		model.addAttribute("listCust", listCust);
		model.addAttribute("listBalance", listView);
		model.addAttribute("sellForm", new Balance("Sell", getUrut()));
		model.addAttribute("buyForm", new Balance("Buy", getUrut()));
		model.addAttribute("page", page);
		return "balance";
	}

	public int getUrut() {
		return BalanceDAO.INSTANCE.getLastNumber(list) + 1;
	}

	public long getLastBalance() {
		return BalanceDAO.INSTANCE.getLastBalance(list);
	}

	public void summary() {
		debet = 0;
		kredit = 0;
		profit = 0;
		receivable = 0;
		payable = 0;
		for (int i = 0; i < list.size(); i++) {
			Balance it = list.get(i);
			debet += it.getDebet();
			kredit += it.getKredit();
			profit += it.getProfit();
			if (it.isSell()) {
				if (!it.isPaidOff()) {
					receivable += it.getPrice();
				}
			} else {
				if (!it.isPaidOff()) {
					payable += it.getPrice();
				}
			}
		}
	}

	public long getLastBalance(int urut) {
		return BalanceDAO.INSTANCE.getLastBalance(list, urut);
	}

	public void filterList() {
		if (paid == null)
			paid = "n";
		listView = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (!paid.equals("a")) {
				if (list.get(i).getPaid().equals(paid)) {
					listView.add(list.get(i));
				}
			} else {
				listView.add(list.get(i));
			}
		}
		if (!search.equals("")) {
			List<Balance> listtemp = new ArrayList();
			// search by name
			for (int i = 0; i < listView.size(); i++) {
				if (listView.get(i).getCustName().toLowerCase()
						.contains(search.toLowerCase())) {
					// if (list.get(i).getPaid().equals(paid)) {
					listtemp.add(listView.get(i));
					// }
				} // else {
					// listView.add(listView.get(i));
				// }
			}
			listView = listtemp;
		}
	}

}

/*
 * @RequestMapping(value = "/sell", method = RequestMethod.POST) public String
 * sell(ModelMap model, Sell form) { // Customer //
 * custInserted=CustomerDAO.INSTANCE.add(form.getName(),form.getDesc());
 * form.setProfit(form.getPrice() - form.getKredit());
 * form.setBalance(getLastBalance(form.getUrut()) + form.getDebet() -
 * form.getKredit()); Sell sellInserted = BalanceDAO.INSTANCE.saveSell(form); if
 * (sellInserted != null) { if (list != null) { boolean exist = false;
 * System.out.println(" " + list.size()); List<Balance> list2 = new ArrayList();
 * boolean update = false; for (int i = 0; i < list.size(); i++) { Balance
 * compare = list.get(i); if (compare.getId().getId() ==
 * sellInserted.getId().getId()) { list2.add(sellInserted); exist = true; update
 * = true; } else { if (update) { long
 * saldo=BalanceDAO.INSTANCE.getLastBalance(list2, compare.getUrut()) +
 * compare.getDebet() - compare.getKredit(); compare.setBalance(saldo);
 * System.out.println(compare.getType() + " " + compare.getBalance());
 * if(compare.getType().equals("Sell")){ Sell s=(Sell)list.get(i);
 * s.setBalance(saldo); BalanceDAO.INSTANCE.saveSell(s); }else{ Buy
 * b=(Buy)list.get(i); b.setBalance(saldo); BalanceDAO.INSTANCE.saveBuy(b); } }
 * list2.add(compare); } } if (!exist) { list2.add(sellInserted); } list =
 * list2; } } // List<Customer> list=CustomerDAO.INSTANCE.listCustomers();
 * filterList(); getPage(); model.addAttribute("listCust", listCust);
 * model.addAttribute("listBalance", listView); model.addAttribute("sellForm",
 * new Sell(getUrut())); model.addAttribute("buyForm", new Buy(getUrut()));
 * model.addAttribute("page", page); return "balance"; }
 * 
 * @RequestMapping(value = "/buy", method = RequestMethod.POST) public String
 * buy(ModelMap model, Buy form) { // Customer //
 * custInserted=CustomerDAO.INSTANCE.add(form.getName(),form.getDesc()); //
 * form.setProfit(form.getPrice()-form.getKredit()); form.setKredit(0);
 * form.setBalance(getLastBalance(form.getUrut()) + form.getDebet() -
 * form.getKredit()); Buy buyInserted = BalanceDAO.INSTANCE.saveBuy(form); if
 * (buyInserted != null) { if (list != null) { boolean exist = false;
 * System.out.println(" " + list.size()); List<Balance> list2 = new ArrayList();
 * boolean update = false; for (int i = 0; i < list.size(); i++) { Balance
 * compare = list.get(i); if (compare.getId().getId() ==
 * buyInserted.getId().getId()) { list2.add(buyInserted); exist = true; update =
 * true; } else { if (update) { compare.setBalance(BalanceDAO.INSTANCE
 * .getLastBalance(list2, compare.getUrut()) + compare.getDebet() -
 * compare.getKredit()); System.out.println(compare.getType() + " " +
 * compare.getBalance()); } list2.add(compare); } } if (!exist) {
 * list2.add(buyInserted); } list = list2; } } // List<Customer>
 * list=CustomerDAO.INSTANCE.listCustomers(); filterList(); getPage();
 * model.addAttribute("listCust", listCust); model.addAttribute("listBalance",
 * listView); model.addAttribute("sellForm", new Sell(getUrut()));
 * model.addAttribute("buyForm", new Buy(getUrut())); model.addAttribute("page",
 * page); return "balance"; }
 */
