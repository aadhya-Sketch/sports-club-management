package com.sportsclub.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.Path;

public class QRCodeGenerator {

    public static String generateUpiQr(double amount, String transactionNote) {
        // Replace with your actual UPI ID for real testing
        String upiId = "sportsclub@upi";
        String payeeName = "Sports Club";

        String upiString = "upi://pay?pa=" + upiId +
                "&pn=" + payeeName.replace(" ", "%20") +
                "&am=" + amount +
                "&cu=INR" +
                "&tn=" + transactionNote.replace(" ", "%20");

        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(upiString, BarcodeFormat.QR_CODE, 300, 300);

            String fileName = "qr_" + System.currentTimeMillis() + ".png";
            Path path = Path.of("qr_codes", fileName);
            path.getParent().toFile().mkdirs(); // create qr_codes folder if missing

            MatrixToImageWriter.writeToPath(matrix, "PNG", path);
            return path.toAbsolutePath().toString();

        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}