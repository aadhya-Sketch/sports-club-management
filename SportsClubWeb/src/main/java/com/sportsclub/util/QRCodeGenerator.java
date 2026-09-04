package com.sportsclub.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class QRCodeGenerator {

    public static String generateUpiQr(double amount, String transactionNote, String destinationFolder) {
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
            Path destFolder = Path.of(destinationFolder);
            Files.createDirectories(destFolder);
            Path fullPath = destFolder.resolve(fileName);

            MatrixToImageWriter.writeToPath(matrix, "PNG", fullPath);
            return fileName;

        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}