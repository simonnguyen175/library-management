package controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {
    private static final String path = "src/main/resources/QRImage/";

    public static String getQRCodeFilePath(String ISBN) {
        if (!new File(path+ISBN+".png").exists()) {
            APIController apiController = new APIController();
            generateQRCode(apiController.getPreviewLink(ISBN), ISBN);
        }
        return path + ISBN + ".png";
    }

    public static void generateQRCode(String data, String ISBN) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200, hints);
            Path filePath = FileSystems.getDefault().getPath(path + ISBN + ".png");
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}