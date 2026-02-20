package com.funfair.api.common;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@Service
public class QrCodeService {

    public byte[] generateQrCode(String token) {

        try {

            String qrContent = "FUNFAIR:TICKET:" + token;

            QRCodeWriter writer = new QRCodeWriter();

            BitMatrix matrix =
                    writer.encode(qrContent,
                            BarcodeFormat.QR_CODE,
                            300,
                            300);

            ByteArrayOutputStream stream =
                    new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(
                    matrix,
                    "PNG",
                    stream);

            return stream.toByteArray();

        }
        catch (Exception e) {

            throw new RuntimeException("Failed to generate QR", e);

        }

    }

}
