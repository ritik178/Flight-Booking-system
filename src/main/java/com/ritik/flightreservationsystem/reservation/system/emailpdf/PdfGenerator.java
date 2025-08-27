package com.ritik.flightreservationsystem.reservation.system.emailpdf;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.ritik.flightreservationsystem.reservation.system.dto.BookingResponseDto;
import com.ritik.flightreservationsystem.reservation.system.dto.PassengerDTO;
import com.itextpdf.text.BaseColor;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

public class PdfGenerator {

	public static byte[] generateBookingPdf(BookingResponseDto dto) {
	    try {
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        Document document = new Document(PageSize.A4, 36, 36, 36, 36); // cleaner margins
	        PdfWriter.getInstance(document, out);
	        document.open();

	        // -------- Fonts --------
	        Font titleFont    = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
	        Font sectionFont  = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
	        Font labelFont    = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.DARK_GRAY);
	        Font valueFont    = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
	        Font footFont     = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 11, BaseColor.GRAY);

	        // -------- Title --------
	        Paragraph title = new Paragraph("Flight Ticket Confirmation", titleFont);
	        title.setAlignment(Element.ALIGN_CENTER);
	        document.add(title);

	        document.add(Chunk.NEWLINE);
	        document.add(new Paragraph("Dear " + dto.getUserName() + ",", valueFont));
	        document.add(new Paragraph("Thank you for booking with SkyFly Travels. Below are your booking details.", valueFont));
	        document.add(Chunk.NEWLINE);

	        document.add(new LineSeparator());
	        document.add(Chunk.NEWLINE);

	        // -------- Booking Details (2-column table) --------
	        PdfPTable info = new PdfPTable(2);
	        info.setWidthPercentage(100);
	        info.setWidths(new float[]{30f, 70f});

	        addKV(info, "Booking ID",   String.valueOf(dto.getBookingId()), labelFont, valueFont);
	        addKV(info, "Email",        dto.getUserEmail(),                 labelFont, valueFont);
	        addKV(info, "Airline",      dto.getFlightName(),                labelFont, valueFont);
	        addKV(info, "Route",        dto.getSource() + " → " + dto.getDestination(), labelFont, valueFont);

	        String when = (dto.getBookingDate() != null)
	                ? dto.getBookingDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"))
	                : "-";
	        addKV(info, "Booked On",    when,                                labelFont, valueFont);

	        int paxCount = (dto.getPassengers() != null) ? dto.getPassengers().size() : 0;
	        addKV(info, "Passengers",   String.valueOf(paxCount),            labelFont, valueFont);
	        addKV(info, "Total Price",  "₹" + String.format("%.2f", dto.getTotalPrice()), labelFont, valueFont);
	        addKV(info, "Status",       dto.isCancelled() ? "Cancelled" : "Active",       labelFont, valueFont);

	        document.add(info);

	        document.add(Chunk.NEWLINE);
	        document.add(new LineSeparator());
	        document.add(Chunk.NEWLINE);

	        // -------- Passenger Details Table --------
	        if (dto.getPassengers() != null && !dto.getPassengers().isEmpty()) {
	            Paragraph paxHeader = new Paragraph("Passenger Details", sectionFont);
	            paxHeader.setSpacingAfter(8f);
	            document.add(paxHeader);

	            PdfPTable paxTable = new PdfPTable(4); // Name | Age | Gender | ID
	            paxTable.setWidthPercentage(100);
	            paxTable.setSpacingBefore(4f);
	            paxTable.setWidths(new float[]{35f, 10f, 15f, 40f});

	            addHeaderCell(paxTable, "Name");
	            addHeaderCell(paxTable, "Age");
	            addHeaderCell(paxTable, "Gender");
	            addHeaderCell(paxTable, "ID Proof No");

	            for (PassengerDTO p : dto.getPassengers()) {
	                // If your DTO uses getName(), replace getPName() with getName()
	                paxTable.addCell(new PdfPCell(new Phrase(
	                        p.getName() != null ? p.getName() : "-", valueFont)));
	                paxTable.addCell(new PdfPCell(new Phrase(
	                        String.valueOf(p.getAge()), valueFont)));
	                paxTable.addCell(new PdfPCell(new Phrase(
	                        p.getGender() != null ? p.getGender() : "-", valueFont)));
	                paxTable.addCell(new PdfPCell(new Phrase(
	                        p.getIdProofNumber() != null ? p.getIdProofNumber() : "-", valueFont)));
	            }

	            // Optional: zebra rows (light gray) for readability
	            // (iText doesn't have built-in zebra; you can set background per cell if you like.)

	            document.add(paxTable);
	            document.add(Chunk.NEWLINE);
	            document.add(new LineSeparator());
	        }

	        document.add(Chunk.NEWLINE);
	        Paragraph foot = new Paragraph(
	                "We wish you a pleasant journey. For support, contact support@skyflytravels.com", footFont);
	        foot.setAlignment(Element.ALIGN_CENTER);
	        document.add(foot);

	        document.close();
	        return out.toByteArray();
	    } catch (Exception e) {
	        throw new RuntimeException("Error generating PDF", e);
	    }
	}
	
	private static void addKV(PdfPTable table, String key, String value, Font keyFont, Font valueFont) {
	    PdfPCell k = new PdfPCell(new Phrase(key, keyFont));
	    k.setBorder(Rectangle.NO_BORDER);
	    k.setPadding(4f);
	    table.addCell(k);

	    PdfPCell v = new PdfPCell(new Phrase(value != null ? value : "-", valueFont));
	    v.setBorder(Rectangle.NO_BORDER);
	    v.setPadding(4f);
	    table.addCell(v);
	}

	private static void addHeaderCell(PdfPTable table, String text) {
	    Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
	    PdfPCell cell = new PdfPCell(new Phrase(text, headerFont));
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    cell.setBackgroundColor(new BaseColor(60, 90, 150)); // a clean blue
	    cell.setPadding(6f);
	    table.addCell(cell);
	}

}

