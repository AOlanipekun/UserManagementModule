/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deeservices.hashing;

/**
 *
 * @author user
 */
public class HashPassword {

    public String createpassword(String password) throws
            Exception {
        PasswordAuthentication pssword = new PasswordAuthentication(24);//lenght
        String map = pssword.hash(password);
        if (map.length() == 0) {
            return "Hash not generated";
        } else {
            return map;
        }
    }

    public boolean validatePassword(String password, String confirmpassword) {

        PasswordAuthentication pssword = new PasswordAuthentication(24);//lenght must be a multiple of 8
        boolean map = pssword.authenticate(password, confirmpassword);

        return map;

    }
}
