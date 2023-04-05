package com.pedro;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JavaMail {

    private static final String EMAIL = "pedrotesteprojetos@gmail.com";
    private static final String SENHA = "nmpbnktzcntbkgxm";

    public void enviarEmail(boolean envioHtml) {
        try {

            Properties properties = new Properties();
            properties.put("mail.smtp.ssl.trust", "*");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL, SENHA);
                }
            });

            Address[] usuarios = InternetAddress.parse(EMAIL);

            Message corpoEmail = new MimeMessage(session);
            corpoEmail.setFrom(new InternetAddress(EMAIL));
            corpoEmail.setRecipients(Message.RecipientType.TO, usuarios);
            corpoEmail.setSubject("Cabeçalho");

            StringBuilder body = new StringBuilder();
            body.append("<h1> Teste HTML </h1>");

            if (envioHtml) {
                corpoEmail.setContent(body.toString(), "text/html; charset=utf-8");
            } else {
                corpoEmail.setText("Corpo do e-mail enviado com Java");
            }

            Transport.send(corpoEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarEmailAnexo(boolean envioHtml) {
        try {

            Properties properties = new Properties();
            properties.put("mail.smtp.ssl.trust", "*");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL, SENHA);
                }
            });

            Address[] usuarios = InternetAddress.parse(EMAIL);

            Message mensagemEmail = new MimeMessage(session);
            mensagemEmail.setFrom(new InternetAddress(EMAIL));
            mensagemEmail.setRecipients(Message.RecipientType.TO, usuarios);
            mensagemEmail.setSubject("Cabeçalho");

            MimeBodyPart corpoEmail = new MimeBodyPart();

            StringBuilder body = new StringBuilder();
            body.append("<h1> Teste HTML </h1>");

            if (envioHtml) {
                corpoEmail.setContent(body.toString(), "text/html; charset=utf-8");
            } else {
                corpoEmail.setText("Corpo do e-mail enviado com Java");
            }

            List<FileInputStream> anexos = new ArrayList<>();
            anexos.add(simuladorPdf());
            anexos.add(simuladorPdf());
            anexos.add(simuladorPdf());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(corpoEmail);

            int index = 0;
            for (FileInputStream fileInputStream : anexos) {
                MimeBodyPart anexoEmail = new MimeBodyPart();
                anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
                anexoEmail.setFileName("anexoemail" + index + ".pdf");
                multipart.addBodyPart(anexoEmail);
                index++;
            }

            mensagemEmail.setContent(multipart);

            Transport.send(mensagemEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FileInputStream simuladorPdf() throws IOException, DocumentException {
        Document document = new Document();

        File file = new File("fileanexo.pdf");
        file.createNewFile();

        //Escrever o conteúdo do file dentro do document (pdf)
        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();
        document.add(new Paragraph("Conteudo do PDF anexo com Java Mail"));
        document.close();

        return new FileInputStream(file);
    }
}
