package fr.awu.annuaire.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import fr.awu.annuaire.model.Person;

public class PdfExportService {

    // Au pif.
    private static final float MARGIN = 50;
    private static final float TITLE_FONT_SIZE = 20;
    private static final float HEADING_FONT_SIZE = 14;
    private static final float TEXT_FONT_SIZE = 12;
    private static final float LINE_SPACING = 15;

    public void exportEmployeeToPdf(Person person, String outputPath)
            throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(
                    document, page)) {
                float yPosition = page.getMediaBox().getHeight() - MARGIN;

                // Titre
                contentStream.setFont(
                        new PDType1Font(
                                Standard14Fonts.FontName.HELVETICA_BOLD),
                        TITLE_FONT_SIZE);
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN, yPosition);
                Pattern pattern = Pattern.compile("^[aeiouy]",
                        Pattern.CASE_INSENSITIVE);
                contentStream.showText("Information "
                        + (pattern.matcher(person.getFirstName()).find() ? "d'"
                                : "de ")
                        + person.getFirstName());
                contentStream.endText();

                yPosition -= TITLE_FONT_SIZE + 20;

                // Ligne
                contentStream.setLineWidth(1f);
                contentStream.moveTo(MARGIN, yPosition);
                contentStream.lineTo(page.getMediaBox().getWidth() - MARGIN,
                        yPosition);
                contentStream.stroke();

                yPosition -= 20;

                // Les infos à la mano
                yPosition = addSectionTitle(contentStream,
                        "Personal Information", yPosition);
                yPosition = addField(contentStream, "Prénom:",
                        person.getFirstName(), yPosition);
                yPosition = addField(contentStream, "Nom:",
                        person.getLastName(), yPosition);
                yPosition = addField(contentStream, "Rôle:",
                        person.getRole().toString(), yPosition);

                yPosition -= 10;

                yPosition = addSectionTitle(contentStream,
                        "Contact", yPosition);
                yPosition = addField(contentStream, "Email:", person.getEmail(),
                        yPosition);

                String homePhone = person.getHomePhone() != null
                        && !person.getHomePhone().isBlank()
                                ? person.getHomePhone()
                                : "N/A";
                yPosition = addField(contentStream, "Socotel:", homePhone,
                        yPosition);

                String mobilePhone = person.getMobilePhone() != null
                        && !person.getMobilePhone().isBlank()
                                ? person.getMobilePhone()
                                : "N/A";
                yPosition = addField(contentStream, "GSM:",
                        mobilePhone, yPosition);

                yPosition -= 10;

                yPosition = addSectionTitle(contentStream,
                        "Informations Professionnelles",
                        yPosition);
                String serviceName = person.getService() != null
                        ? person.getService().getName()
                        : "N/A";
                yPosition = addField(contentStream, "Service:", serviceName,
                        yPosition);

                String siteName = person.getSite() != null
                        ? person.getSite().getVille()
                        : "N/A";
                yPosition = addField(contentStream, "Site:", siteName,
                        yPosition);

                // Footer
                yPosition = MARGIN + 20;
                contentStream.setFont(
                        new PDType1Font(
                                Standard14Fonts.FontName.HELVETICA_OBLIQUE),
                        10);
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN, yPosition);
                contentStream.showText(
                        "Fait à: " + LocalDateTime.now().format(
                                DateTimeFormatter
                                        .ofPattern("yyyy-MM-dd HH:mm:ss")));
                contentStream.endText();
            }

            document.save(new File(outputPath));
        }
    }

    private float addSectionTitle(PDPageContentStream contentStream,
            String title, float yPosition)
            throws IOException {
        contentStream.setFont(
                new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD),
                HEADING_FONT_SIZE);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN, yPosition);
        contentStream.showText(title);
        contentStream.endText();
        return yPosition - HEADING_FONT_SIZE - 10;
    }

    private float addField(PDPageContentStream contentStream, String label,
            String value, float yPosition)
            throws IOException {
        // Label
        contentStream.setFont(
                new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD),
                TEXT_FONT_SIZE);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN + 20, yPosition);
        contentStream.showText(label);
        contentStream.endText();

        // Valeur
        contentStream.setFont(
                new PDType1Font(Standard14Fonts.FontName.HELVETICA),
                TEXT_FONT_SIZE);
        contentStream.beginText();
        contentStream.newLineAtOffset(MARGIN + 150, yPosition);
        contentStream.showText(value != null ? value : "N/A");
        contentStream.endText();

        return yPosition - LINE_SPACING;
    }

    public String exportEmployeeToPdf(Person person) throws IOException {
        String projectRoot = System.getProperty("user.dir");
        File storageDir = new File(projectRoot, "storage");

        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        String filename = String.format("employee_%s_%s.pdf",
                person.getFirstName().toLowerCase(),
                person.getLastName().toLowerCase());

        String fullPath = new File(storageDir, filename).getAbsolutePath();
        exportEmployeeToPdf(person, fullPath);
        return fullPath;
    }
}
