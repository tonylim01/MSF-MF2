package com.uangel.core.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpSocket {

    private static final Logger logger = LoggerFactory.getLogger(UdpServer.class);

    private static final int MAX_BUFFER_SIZE = 4096;

    private DatagramSocket socket;
    private Thread thread = null;
    private byte[] buf = new byte[MAX_BUFFER_SIZE];
    private boolean isQuit = false;
    private InetAddress address;
    private int localPort;
    private int remotePort;

    public UdpSocket(String ipAddress, int remotePort, int localPort) {
        try {
            socket = new DatagramSocket(localPort);
            address = InetAddress.getByName(ipAddress);
            logger.debug("UdpSocket ip {} port {}", socket.getLocalSocketAddress().toString(), localPort);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.remotePort = remotePort;
        this.localPort = localPort;
    }

    private UdpCallback callback = null;

    public void setUdpCallback(UdpCallback callback) {
        this.callback = callback;
    }

    public boolean start() {
        if (thread != null) {
            return false;
        }

        thread = new Thread(new UdpServerRunnable());
        thread.start();

        return true;
    }

    public void stop() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }

        socket.close();
    }

    public boolean send(byte[] buf) {
        if (buf == null || (buf != null && buf.length == 0)) {
            return false;
        }

        boolean result = false;
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, remotePort);
        try {
            socket.send(packet);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    class UdpServerRunnable implements Runnable {
        @Override
        public void run() {

            logger.info("UdpSocket server ({}) start", localPort);
            while (!isQuit) {
                try {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    if (callback != null) {
                        callback.onReceived(packet.getAddress().getAddress(), packet.getPort(), buf, buf.length);
                    }
                } catch (Exception e) {
                    logger.warn("Exception [{}] [{}]", e.getClass(), e.getMessage());
                    if (e.getClass() != IOException.class) {
                        isQuit = true;
                    }
                }
            }
            logger.info("UdpServer server ({}) end", localPort);
        }
    }
}
