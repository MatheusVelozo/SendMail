package org.example;
//Javamail
//bndm qnqu vlvu jhwy

import com.itextpdf.text.Document;
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

public class SendMailObject {

    private String userName = "java.23.test.mail@gmail.com";
    private String password = "***";


    private String recipientList = "";
    private String senderName = "";
    private String subject = "";
    private String textMail= "";

    public SendMailObject(String recipientList, String senderName, String subject, String textMail) {
        this.recipientList = recipientList;
        this.senderName = senderName;
        this.subject = subject;
        this.textMail = textMail;
    }

    public void sendEmail(boolean sendHtml) throws Exception {
        /*OLHAR AS CONFIG SMTP DO EMAIL*/
                //PROPRIEDADES DE CONEXÃO.
                Properties properties = new Properties();
                properties.put("mail.smtp.ssl.trust", "*");
                properties.put("mail.smtp.auth", "true"); //autorização.
                properties.put("mail.smtp.starttls", "true"); //autenticação.
                properties.put("mail.smtp.host", "smtp.gmail.com"); //servidor gmail.
                properties.put("mail.smtp.port", "465"); //porta do servidor.
                properties.put("mail.smtp.socketFactory.port", "465"); //porta a ser conectada pelo socket.
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //classe socket de conexão ao SMTP.

                //CONFIGURAÇÃO COM SERVIDOR.
                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });

                //LISTA DE E-MAILS DESTINATÁRIOS.
                Address[] toUser = InternetAddress.parse(recipientList);

                //E-MAIL.
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(userName, senderName));//Remetente.
                message.setRecipients(Message.RecipientType.TO, toUser);//destinatário.
                message.setSubject(subject);//Assunto.

                if (sendHtml) {
                    message.setContent(textMail, "text/html; charset=utf-8");
                } else {
                    message.setText(textMail);//Corpo do email.
                }

                Transport.send(message);
            }

    //Método de enviar email com anexo.
    public void sendEmailAttachment(boolean sendHtml) throws Exception {
        /*OLHAR AS CONFIG SMTP DO EMAIL*/
        //PROPRIEDADES DE CONEXÃO.
        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.auth", "true"); //autorização.
        properties.put("mail.smtp.starttls", "true"); //autenticação.
        properties.put("mail.smtp.host", "smtp.gmail.com"); //servidor gmail.
        properties.put("mail.smtp.port", "465"); //porta do servidor.
        properties.put("mail.smtp.socketFactory.port", "465"); //porta a ser conectada pelo socket.
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //classe socket de conexão ao SMTP.

        //CONFIGURAÇÃO COM SERVIDOR.
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        //LISTA DE E-MAILS DESTINATÁRIOS.
        Address[] toUser = InternetAddress.parse(recipientList);

        //E-MAIL.
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userName, senderName));//Remetente.
        message.setRecipients(Message.RecipientType.TO, toUser);//destinatário.
        message.setSubject(subject);//Assunto.

        //Parte 1 do email com texto e descrição.
        MimeBodyPart bodyMail = new MimeBodyPart();

        if (sendHtml) {
            bodyMail.setContent(textMail, "text/html; charset=utf-8");
        } else {
            bodyMail.setText(textMail);//Corpo do email.
        }

        List<FileInputStream> archives = new ArrayList<FileInputStream>();
        archives.add(PdfSimulator());
        archives.add(PdfSimulator());
        archives.add(PdfSimulator());
        archives.add(PdfSimulator());
        archives.add(PdfSimulator());

        //Junta as duas partes do email.
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyMail);

        int index = 0;
        for (FileInputStream fileInputStream : archives) {

            //Parte 2 do email com anexos.
            MimeBodyPart attachmentMail = new MimeBodyPart();
            //No 1 parâmetro do byteArray pode ser passado um arquivo do BD, por exemplo.
            attachmentMail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
            attachmentMail.setFileName("AttachmentMail"+index+".pdf");

            multipart.addBodyPart(attachmentMail);

            index ++;
        }

        message.setContent(multipart);


        Transport.send(message);
    }

    //Método para simular o PDF ou qualquer outro arquivo que possa ser enviado axenado via mail.
    private FileInputStream PdfSimulator() throws Exception{
        Document document = new Document();
        File file = new File("Anexo.pdf");

        file.createNewFile();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        document.add(new Paragraph("Conteúdo do PDF anexado com Java Mail."));
        document.close();

        return new FileInputStream(file);
        }
    }
