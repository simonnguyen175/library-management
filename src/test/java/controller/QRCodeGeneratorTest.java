package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QRCodeGeneratorTest {

    @Test
    void getQRCodeFilePath() {
        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
        String result = qrCodeGenerator.getQRCodeFilePath("6041107093");
        System.out.println(result);
    }

}