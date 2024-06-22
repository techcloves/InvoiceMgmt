package com.inv.controller;



	import org.apache.poi.xwpf.usermodel.XWPFDocument;
	import org.springframework.web.bind.annotation.*;
	import org.springframework.web.multipart.MultipartFile;
	import org.apache.poi.xwpf.converter.pdf.PdfConverter;
	import org.apache.poi.xwpf.converter.pdf.PdfOptions;

	/*	import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
		import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;*/

/*	import fr.opensagres.odfdom.converter.pdf.PdfConverter;
		import fr.opensagres.odfdom.converter.pdf.PdfOptions;*/

	import org.springframework.core.io.InputStreamResource;
	import org.springframework.http.MediaType;
	import org.springframework.http.ResponseEntity;

	import java.io.*;
	
	@CrossOrigin
	@RestController
	@RequestMapping("/doc")   


	public class DocumentController {

		
		@PostMapping(value = "/convertDocToPDF", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	    public ResponseEntity<InputStreamResource> convertDocxToPdf(@RequestParam("file") MultipartFile file) throws IOException {
			
	        // Convert MultipartFile to XWPFDocument
	        XWPFDocument document = new XWPFDocument(file.getInputStream());

	        // Use Apache PDFBox to create the PDF
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	       
	        PdfOptions options = PdfOptions.create();
	        PdfConverter.getInstance().convert(document, out, options);
	        ByteArrayInputStream inStream = new ByteArrayInputStream(out.toByteArray());

	        // Return the PDF as a stream
	        InputStreamResource resource = new InputStreamResource(inStream);
	        return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(resource);
	    }
	        
	       /* XWPFDocument document = new XWPFDocument(file.getInputStream());
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        PdfOptions options = PdfOptions.create();
	     
	        PdfConverter.getInstance().convert(document, out, options);
	        ByteArrayInputStream inStream = new ByteArrayInputStream(out.toByteArray());

	        // Return the PDF as a stream
	        InputStreamResource resource = new InputStreamResource(inStream);
	        return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(resource);
		}*/
	        
	}

	
	

