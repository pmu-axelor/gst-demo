package com.axelor.gst.web;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.axelor.data.ImportException;
import com.axelor.data.ImportTask;
import com.axelor.data.Listener;
import com.axelor.data.csv.CSVImporter;
import com.axelor.db.EntityHelper;
import com.axelor.db.Model;
import com.axelor.gst.db.Invoice;
import com.axelor.gst.db.Party;
import com.axelor.gst.service.PartyServiceAddressImpl;
import com.axelor.meta.MetaFiles;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validators {

	protected final Logger log = LoggerFactory.getLogger(EntityHelper.getEntityClass(this));

    @Inject
	PartyServiceAddressImpl pas;

	public Object validatePartyAddress(Object bean, Map<String, Object> context) {
		assert bean instanceof Party;

		Party party = (Party) bean;
		pas.validate(party);

		return bean;

	}

	public void createInvoice(Map<String, Object> context) {
		Invoice invoice = new Invoice();
		invoice.setDate(LocalDateTime.now());
		context.put("_invoice", invoice);
	}

	public void importInvoiceLineData(ActionRequest request, ActionResponse response) throws URISyntaxException, IOException {

		/*
		 URL res = getClass().getClassLoader().getResource("data-init/csv-multi-config.xml");
		 System.out.println(res);
		 File file = Paths.get(res.toURI()).toFile();
         String absolutePath = file.getAbsolutePath();
		 System.out.println(absolutePath);  */
		 
		
		

	      String config = "/data-init/csv-get-invoiceLine-data-config.xml";

	    
	      InputStream inputStream = this.getClass().getResourceAsStream(config);
	      File configFile = File.createTempFile("config", ".xml");
	      FileOutputStream fout = new FileOutputStream(configFile);
	      IOUtil.copyCompletely(inputStream, fout);
	     

	     Path path =
	          MetaFiles.getPath((String) ((Map) request.getContext().get("dataFile")).get("filePath"));
	     System.out.println(path);
	      File tempDir = Files.createTempDir();
	      File importFile = new File(tempDir, "invoiceLine.csv");
	      Files.copy(path.toFile(), importFile); 


	  
		/*
		 * InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
		 * "/data-init/csv-multi/invoiceLine.csv"); System.out.println(inputStream);
		 * 
		 * BufferedReader reader = new BufferedReader(new
		 * InputStreamReader(inputStream)); System.out.println(reader.toString());
		 * 
		 * String result = CharStreams.toString(new InputStreamReader( inputStream,
		 * Charsets.UTF_8)); System.out.println(result);
		 */
		
		  
		final List<Model> records = new ArrayList<>();

		CSVImporter importer = new CSVImporter(configFile.getAbsolutePath());
		
		importer.addListener(new Listener() {
			@Override
			public void imported(Model bean) {
				log.info("Bean saved : {}(id={})", bean.getClass().getSimpleName(), bean.getId());
				records.add(bean);
			}

			@Override
			public void imported(Integer total, Integer success) {
				log.info("Record (total): " + total);
				log.info("Record (success): " + success);
			}

			@Override
			public void handle(Model bean, Exception e) {
			}
		});

		importer.run(new ImportTask() {

			@Override
			public void configure() throws IOException {
				input("[gst-invoice]", new File(
						"/home/axelor/parvej/training/axelor-gst-app-demo/axelor-gst-app/modules/axelor-gst/src/main/resources/data-init/csv-multi/invoiceLine.csv"));

			}

			@Override
			public boolean handle(ImportException exception) {
				log.error("Import error : " + exception);
				return false;
			}

			@Override
			public boolean handle(IOException e) {
				log.error("IOException error : " + e);
				return true;
			}
		});

		// System.out.println(records);
		response.setValue("invoiceitemList", records);

	}
	}
	
      


