package Services;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QRCodeGeneratorTest {

    @BeforeAll
    static void initJFX() {
        // Initialize JavaFX environment
        new JFXPanel();
    }

    @Test
    void testGenerateQRCode() {
        // Arrange
        String validISBN = "9781234567890";

        // Act
        Image qrCodeImage = QRCodeGenerator.generateQRCode(validISBN);

        // Assert
        assertNotNull(qrCodeImage, "QR Code image should not be null for a valid ISBN");
    }
}