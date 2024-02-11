package org.example;

import org.junit.Test;

public class AppTest {

    @Test
    public void testMail() throws Exception{

        //Construção do e-mail em HTML.
        StringBuilder stringBuilderTextMail = new StringBuilder();

        stringBuilderTextMail.append("Hi, <br/><br/>");
        stringBuilderTextMail.append("You are receive this mail write in Java Code! <br/><br/>");
        stringBuilderTextMail.append("The button below is only test. <br/><br/>");

        //CSS.
        stringBuilderTextMail.append("<a target=\"_blank\" href=\"https://www.google.com/\" " +
                "style=\"color:#2525a7; padding: 10px 20px; " +
                "text-align:center; " +
                "text-decoration: none; display: inline-block;" +
                "border-radius: 30px;" +
                "font-size: 15px;" +
                "font-family:courier;" +
                "border: 3px solid green;" +
                "background-color:green;" +
                "\">Test Button<a/>");

        //Dados dos atributos de SendMailObject.
        SendMailObject sendMail = new SendMailObject(
                "matheusvelozo@hotmail.com",
                "JAVA",
                "Sent with Java Mail.",
                stringBuilderTextMail.toString());


        sendMail.sendEmailAttachment(true);
    }

}
