package com.animal.locator.animalLocator.services.impl;

import com.animal.locator.animalLocator.models.AnimalStatistic;
import com.animal.locator.animalLocator.models.Treatment;
import com.animal.locator.animalLocator.services.PDFCreatorService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Service("pdfCreatorService")
public class PDFCreatorServiceImpl implements PDFCreatorService {

    @Override
    public String createStatisticReport(List<AnimalStatistic> statistics) {
        try {
            String file_name = "Statistics_" + statistics.get(0).getAnimal().getUser().getUsername() + "_" + statistics.get(0).getAnimal().getAnimalName() + ".pdf";
            final String destination = "pdfs/" + file_name;
            Font font_bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 16, BaseColor.BLACK);
            Font font_normal = FontFactory.getFont(FontFactory.TIMES, 16, BaseColor.BLACK);

            final File file = new File(destination);
            file.getParentFile().mkdirs();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(destination));
            document.open();
            final String pdf_title = StringUtils.concat("Statistic report for ", statistics.get(0).getAnimal().getAnimalName());
            final String pdf_title2 = StringUtils.concat("From ", LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")), " to ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));

            final Paragraph paragraphTitle = new Paragraph(pdf_title, font_bold);
            final Paragraph paragraphTitle2 = new Paragraph(pdf_title2, font_normal);

            paragraphTitle.setAlignment(Paragraph.ALIGN_CENTER);
            paragraphTitle.setSpacingAfter(15f);
            paragraphTitle2.setAlignment(Paragraph.ALIGN_CENTER);
            paragraphTitle2.setSpacingAfter(15f);

            document.add(paragraphTitle);
            document.add(paragraphTitle2);

            if (!ObjectUtils.isEmpty(statistics))
            {
                final PdfPTable table = new PdfPTable(3);

                Stream.of("Date", "Moving", "Resting")
                        .forEach(columnTitle -> {
                            PdfPCell header = new PdfPCell();
                            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            header.setFixedHeight(20f);
                            header.setPhrase(new Phrase(columnTitle));
                            table.addCell(header);
                        });

                for (final AnimalStatistic statistic : statistics)
                {
                    Phrase p = new Phrase(statistic.getCreatedTimeString());
                    PdfPCell cell = new PdfPCell(p);
                    cell.setFixedHeight(30f);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                    p = new Phrase(statistic.getRunningHours());
                    cell = new PdfPCell(p);
                    cell.setFixedHeight(30f);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                    p = new Phrase(statistic.getSleepingHours());
                    cell = new PdfPCell(p);
                    cell.setFixedHeight(30f);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);

                }
                document.add(table);
            }
            document.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String createTreatmentReport(List<Treatment> treatments) {
        try {
            String file_name;
            if (!ObjectUtils.isEmpty(treatments)) {
                file_name = "Treatments_" + treatments.get(0).getAnimal().getUser().getUsername() + "_" + treatments.get(0).getAnimal().getAnimalName() + ".pdf";
            } else {
                file_name = "Treatments_" + LocalDateTime.now().toString() + ".pdf";

            }
            final String destination = "pdfs/" + file_name;
            Font font_bold = FontFactory.getFont(FontFactory.TIMES_BOLD, 16, BaseColor.BLACK);
            Font font_normal = FontFactory.getFont(FontFactory.TIMES, 16, BaseColor.BLACK);

            final File file = new File(destination);
            file.getParentFile().mkdirs();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(destination));
            document.open();
            final String pdf_title;
            if(!ObjectUtils.isEmpty(treatments)) {
                pdf_title = StringUtils.concat("Treatments report for ", treatments.get(0).getAnimal().getAnimalName());
            } else {
                pdf_title = "Treatments report";
            }
            final Paragraph paragraphTitle = new Paragraph(pdf_title, font_bold);
            paragraphTitle.setAlignment(Paragraph.ALIGN_CENTER);
            paragraphTitle.setSpacingAfter(15f);
            document.add(paragraphTitle);


            final String pdf_title2 = StringUtils.concat("From ", LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")), " to ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
            final Paragraph paragraphTitle2 = new Paragraph(pdf_title2, font_normal);
            paragraphTitle2.setAlignment(Paragraph.ALIGN_CENTER);
            paragraphTitle2.setSpacingAfter(15f);
            document.add(paragraphTitle2);

            if (!ObjectUtils.isEmpty(treatments)) {
                final PdfPTable table = new PdfPTable(4);

                Stream.of("Date", "Treatment type", "Destription", "Alert time")
                        .forEach(columnTitle -> {
                            PdfPCell header = new PdfPCell();
                            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            header.setFixedHeight(20f);
                            header.setPhrase(new Phrase(columnTitle));
                            table.addCell(header);
                        });

                for (final Treatment treatment : treatments)
                {
                    Phrase p = new Phrase(treatment.getTreatmentDateString());
                    PdfPCell cell = new PdfPCell(p);
                    cell.setFixedHeight(30f);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                    p = new Phrase(treatment.getTreatmentType().getTreatmentType());
                    cell = new PdfPCell(p);
                    cell.setFixedHeight(30f);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                    p = new Phrase(treatment.getTreatmentDescription());
                    cell = new PdfPCell(p);
                    cell.setFixedHeight(30f);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                    if(treatment.getTreatmentAlertTime() == null) {
                        p = new Phrase("-");
                    } else {
                        p = new Phrase(treatment.getTreatmentAlertTimeString());
                    }
                    cell = new PdfPCell(p);
                    cell.setFixedHeight(30f);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);

                }
                document.add(table);
            } else {
                final String pdf_title3 = "No treatments for animal";
                final Paragraph paragraphTitle3 = new Paragraph(pdf_title3, font_normal);
                paragraphTitle3.setAlignment(Paragraph.ALIGN_CENTER);
                paragraphTitle3.setSpacingAfter(15f);
                document.add(paragraphTitle3);
            }
            document.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}