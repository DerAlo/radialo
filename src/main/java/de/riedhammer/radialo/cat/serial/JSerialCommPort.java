package de.riedhammer.radialo.cat.serial;


import static com.fazecast.jSerialComm.SerialPort.TIMEOUT_READ_BLOCKING;

/**
 * Implementation of {@link SerialPort} using com.fazecast.jSerialComm
 */
public class JSerialCommPort implements SerialPort {

    private final com.fazecast.jSerialComm.SerialPort commPort;

    public JSerialCommPort(String serialPort,
                           int baudRate,
                           int dataBits,
                           Parity parity,
                           int stopBits) {
        commPort = com.fazecast.jSerialComm.SerialPort.getCommPort(serialPort);
        commPort.setComPortParameters(baudRate, dataBits, stopBits, 0);
        commPort.setComPortTimeouts(TIMEOUT_READ_BLOCKING, 1, 2);
        commPort.openPort();
        if (!commPort.isOpen()) {
            System.err.println("Couldn't open " + serialPort);
            System.exit(1);
        }
        System.out.println("Comm port opened");
    }

    @Override
    public int write(byte[] bytes) {
        return commPort.writeBytes(bytes, bytes.length);
    }

    @Override
    public int read(byte[] bytes,
                    int numBytes) {
        if (bytes.length < numBytes) throw new RuntimeException("bytes is smaller than numBytes");
        return commPort.readBytes(bytes, numBytes);
    }

    public enum Parity {
        NONE,
        ODD,
        EVEN,
        MARK,
        SPACE
    }

}
