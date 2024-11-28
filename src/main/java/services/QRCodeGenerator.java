package services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {
    private static QRCodeGenerator instance;

    private QRCodeGenerator() {
    }

    public static QRCodeGenerator getInstance() {
        if (instance == null) {
            instance = new QRCodeGenerator();
        }
        return instance;
    }

    public static Image generateQRCode(String ISBN) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);

        APIController apiController = APIController.getInstance();
        System.out.println("get preview link");
        String data = apiController.getPreviewLink(ISBN);
        System.out.println(data);
        if (data == null || data.isEmpty()) {
            System.out.println("khong co data");
            throw new IllegalArgumentException("Invalid data for QR code generation");
        }

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200, hints);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            return SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (WriterException e) {
            System.err.println("Failed to generate QR Code: " + e.getMessage());
            e.printStackTrace(); // Có thể log thêm nếu cần
        }
        return null; // Return null nếu thất bại
    }
}