package de.riedhammer.radialo.cat.serial;

/**
 * Interface for abstracting serial port operations
 */
public interface SerialPort {

  /**
   * Writes the bytes
   *
   * @return the number of bytes written
   */
  int write(byte[] bytes);

  /**
   * Read the requested number of bytes
   *
   * @param bytes
   * @param numBytes the number of bytes to read
   * @return the number of bytes actually read
   */
  int read(byte[] bytes,
           int numBytes);

}
