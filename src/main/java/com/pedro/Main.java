package com.pedro;

public class Main {

    public static void main(String[] args) {
        JavaMail javaMail = new JavaMail();
        //javaMail.enviarEmail(false);
        //javaMail.enviarEmail(true);
        javaMail.enviarEmailAnexo(true);
    }

}