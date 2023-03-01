package com.example.clientservice.util;

public class IDNumberValidator {

    public static boolean validateIDNumber(String idNumber) {
        int month = Integer.parseInt(idNumber.substring(2, 4));
        int date = Integer.parseInt(idNumber.substring(4, 6));
        int citizenship = Integer.parseInt(idNumber.substring(10, 11));
        if (!(month <= 12 && month >= 1)) {
            return false;
        }
        if (!(date <= 31 && date >= 1)) {
            return false;
        }
        if (!(citizenship == 1 || citizenship == 0)) {
            return false;
        }
        long sumOddNumbers = 0;
        long sumEvenNumbers = 0;
        String evenNumberCombined = "";
        for (int i = 0; i < idNumber.length()-1; i++) {
            if (i % 2 == 0) {
                sumOddNumbers += Integer.parseInt(idNumber.substring(i, i + 1));
            } else {
                evenNumberCombined += idNumber.substring(i, i + 1);
            }
        }

        String evenNumberMultiplied= String.valueOf(Long.parseLong(evenNumberCombined)*2);

        for (int i = 0; i < evenNumberMultiplied.length(); i++) {
           sumEvenNumbers += Integer.parseInt(evenNumberMultiplied.substring(i, i + 1));

        }

        long sumOfOddEven = sumOddNumbers+sumEvenNumbers;
        long subtractionTotal= 10-(Integer.parseInt(sumOfOddEven >9?String.valueOf(sumOfOddEven).substring(1,2): String.valueOf(sumOfOddEven)));
        String checkDigit= subtractionTotal>9?String.valueOf(subtractionTotal).substring(1,2):String.valueOf(subtractionTotal);

        return idNumber.substring(idNumber.length()-1).equalsIgnoreCase(checkDigit);
    }
}
