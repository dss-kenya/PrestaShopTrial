package com.example.prestashopdemo;

import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;

/**
 * Created by user on 14/07/2015.
 */
public class CustomAuthenticator extends Authenticator{
    protected PasswordAuthentication getPasswordAuthentication() {
        String prompt = getRequestingPrompt();
        String hostname = getRequestingHost();
        InetAddress ipaddr = getRequestingSite();
        int port = getRequestingPort();

        String username = "YTANJPUUB5SJTD3E6XKYWQDA9CZT";
        String password = "";

        // Return the information (a data holder that is used by Authenticator)
        return new PasswordAuthentication(username, password.toCharArray());
    }
}
