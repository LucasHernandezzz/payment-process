package com.payments.cafts.domain.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validators {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    public static boolean isValidCreditCard(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }

        String cleanedCardNumber = cardNumber.replaceAll("\\s+", "");
        if (!cleanedCardNumber.matches("\\d+")) {
            return false; // Must contain only digits
        }

        int nDigits = cleanedCardNumber.length();
        int nSum = 0;
        boolean isSecond = false;

        for (int i = nDigits - 1; i >= 0; i--) {
            int d = cleanedCardNumber.charAt(i) - '0';
            if (isSecond) {
                d = d * 2;
            }
            nSum += d / 10;
            nSum += d % 10;
            isSecond = !isSecond;
        }

        return (nSum % 10 == 0);
    }
    public static boolean isValidPixKey(String pixKey, String pixKeyType) {
        if (pixKey == null || pixKey.isEmpty() || pixKeyType == null) {
            return false;
        }

        switch (pixKeyType.toUpperCase()) {
            case "CPF":
                return isValidCpf(pixKey);
            case "EMAIL":
                return isValidEmail(pixKey);
            case "PHONE":
                return isValidPhone(pixKey);
            default:
                return false;
        }
    }

    private static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private static boolean isValidPhone(String phone) {
        if (phone == null) {
            return false;
        }

        // Simple validation for a Brazilian phone number format (e.g., 11987654321)
        String cleanedPhone = phone.replaceAll("[^0-9]", "");
        return cleanedPhone.length() >= 10 && cleanedPhone.length() <= 11;
    }

    private static boolean isValidCpf(String cpf) {
        if (cpf == null) {
            return false;
        }

        String cleanedCpf = cpf.replaceAll("[^0-9]", "");
        if (cleanedCpf.length() != 11 || cleanedCpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int[] weights1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
            int sum1 = 0;
            for (int i = 0; i < 9; i++) {
                sum1 += (cleanedCpf.charAt(i) - '0') * weights1[i];
            }
            int remainder1 = sum1 % 11;
            int digit1 = (remainder1 < 2) ? 0 : 11 - remainder1;
            if ((cleanedCpf.charAt(9) - '0') != digit1) {
                return false;
            }

            int[] weights2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
            int sum2 = 0;
            for (int i = 0; i < 10; i++) {
                sum2 += (cleanedCpf.charAt(i) - '0') * weights2[i];
            }
            int remainder2 = sum2 % 11;
            int digit2 = (remainder2 < 2) ? 0 : 11 - remainder2;

            return (cleanedCpf.charAt(10) - '0') == digit2;
        } catch (Exception e) {
            return false;
        }
    }
}
