package de.riedhammer.radialo.cat;


import de.riedhammer.radialo.cat.serial.JSerialCommPort;
import de.riedhammer.radialo.cat.serial.SerialPort;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

@Service
public class IC7300 {

    // http://www.plicht.de/ekki/civ/civ-p4.html
    private static final byte[] PREFIX = {(byte) 0xFE, (byte) 0xFE, (byte) 0x94, (byte) 0xE0};
    private static final byte[] SUFFIX = {(byte) 0xFD};
    private static final byte[] WRITE_FREQUENCY = {0x00, 0x00, 0x00, 0x10, 0x21, 0x00};//21mhz
    private static final byte[] READ_FREQUENCY = {(byte) 0x03};


    private SerialPort serialPort;



    public void init() {
        this.serialPort = new JSerialCommPort("COM4", 9600, 8, JSerialCommPort.Parity.NONE, 1);
    }

    public void setFreq() {
        write(WRITE_FREQUENCY);
    }

    public String readFrequency() {
        write(READ_FREQUENCY);
        final String answer = toHex(new String(read(11)));
        return answer.substring(8, 15);
    }


    private void write(byte[] bytes) {
        bytes = (concat(PREFIX, bytes, SUFFIX));
        final int bytesWritten = serialPort.write(bytes);
        if (bytesWritten != bytes.length) throw new RuntimeException("Write error: " + bytesWritten);
    }


    private byte[] read(int length) {
        byte[] readBytes = new byte[length];
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final int bytesRead = serialPort.read(readBytes, readBytes.length);
        if (bytesRead != readBytes.length)
            throw new RuntimeException("Read error. Expected to read " + length + " but only read " + bytesRead +
                    " (" + new String(readBytes) + ") in hex: " + toHex(new String(readBytes)));
        ArrayUtils.reverse(readBytes);
        return readBytes;
    }

    private byte[] concat(byte[] pre, byte[] middle, byte[] post) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(pre);
            outputStream.write(middle);
            outputStream.write(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    private String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }


}
