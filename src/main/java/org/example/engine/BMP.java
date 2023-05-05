package org.example.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class BMP {
    File file;
    int w;
    int h;
    byte[] bmpImage;
    int nullEnd;

    public BMP(int w, int h, String path) {
        this.file = new File(path);
        this.w = w;
        this.h = h;
        this.nullEnd = 4 - ((w * 3) % 4);
        if (this.nullEnd == 4) {
            this.nullEnd = 0;
        }
        int bitmapSize = (h * (w * 3 + this.nullEnd));
        this.bmpImage = new byte[54 + bitmapSize];
        this.prepareBaseHeader();
    }

    public void save() {
        this.writeByte(bmpImage);
    }

    static byte[] ColorHexStringToByteArray(String s) {
        return new byte[]{(byte) Integer.parseInt(s.substring(0, 2), 16), (byte) Integer.parseInt(s.substring(2, 4), 16), (byte) Integer.parseInt(s.substring(4), 16)};
    }

    public void writeByte(byte[] bytes) {
        try {

            OutputStream os = new FileOutputStream(this.file);
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public void prepareBaseHeader() {
        // BM signature
        this.bmpImage[0] = 0x42;
        this.bmpImage[1] = 0x4d;

        // write size
        writeInt4Bytes(this.bmpImage, this.bmpImage.length, 0x2);

        // write offset
        writeInt4Bytes(this.bmpImage, 54, 0xa);

        // dib header size
        writeInt4Bytes(this.bmpImage, 40, 0xe);
        // width
        writeInt4Bytes(this.bmpImage, this.w, 0x12);
        // height
        writeInt4Bytes(this.bmpImage, this.h, 0x16);

        // a bit of trash
        this.bmpImage[0x1a] = 0x1;
        this.bmpImage[0x1c] = 0x18;
//        writeInt2Bytes(image, 1, 0x1a);
//        writeInt2Bytes(image, 24, 0x1c);

        // bitmap size
//        writeInt4Bytes(image, image.length-54, 0x22);

        // a bit of another trash
//        writeInt4Bytes(image, 2835, 0x26);
//        writeInt4Bytes(image, 2835, 0x2a);

    }

    static void writeInt4Bytes(byte[] array, int integer, int index) {
        int i = 0;
        byte[] arr = Int4ToBytes(integer);
        for (byte s : arr) {
            array[index + i] = s;
            i += 1;
        }
    }

    static byte[] Int4ToBytes(int n) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(n).array();
    }

    public void fillImage(Color color) {
        this.fillRect(0, 0, this.w, this.h, color);
    }

    public void fillRect(int startXPixel, int startYPixel, int rectW, int rectH, Color color) {
        for (int y = startYPixel; startYPixel + rectH > y; y++) {
            if (y >= h) {
                continue;
            }
            for (int x = startXPixel; startXPixel + rectW > x; x++) {
                if (x >= w) {
                    continue;
                }
                this.fillPixel(x, y, color);
            }
        }

    }

    public void fillPixel(int x, int y, Color color) {
        int startIndex = (this.w * 3 + nullEnd) * (this.h - y - 1) + (x * 3);
        this.bmpImage[54 + startIndex] += (byte) (int) color.getB();
        this.bmpImage[54 + startIndex + 1] += (byte) (int) color.getG();
        this.bmpImage[54 + startIndex + 2] += (byte) (int) color.getR();
    }
}
