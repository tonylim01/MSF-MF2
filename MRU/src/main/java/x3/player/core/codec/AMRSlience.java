package x3.player.core.codec;

public class AMRSlience {

    private static final byte[] AMR_SILENCE_M8 = new byte[] {
            (byte)0x44, (byte)0x11, (byte)0x06, (byte)0x30, (byte)0x33, (byte)0xBE, (byte)0xCE, (byte)0xB3,
            (byte)0xA0, (byte)0xD3, (byte)0x00, (byte)0x00, (byte)0xEB, (byte)0x50, (byte)0x87, (byte)0xB4,
            (byte)0XFF, (byte)0xD6, (byte)0x42, (byte)0x40, (byte)0x18, (byte)0x08, (byte)0x1A, (byte)0xE5,
            (byte)0x02, (byte)0x2A, (byte)0x96, (byte)0x91, (byte)0x29, (byte)0x48, (byte)0x49, (byte)0xCB,
            (byte)0X52, (byte)0x22, (byte)0x89, (byte)0x06, (byte)0x78, (byte)0xC0, (byte)0x28, (byte)0x00,
            (byte)0xB1, (byte)0x18, (byte)0x8B, (byte)0x93, (byte)0x24, (byte)0xC6, (byte)0x58, (byte)0x74,
            (byte)0XAC, (byte)0x19, (byte)0x0D, (byte)0xD7, (byte)0xB0, (byte)0x5B, (byte)0x08, (byte)0x88,
            (byte)0xCB, (byte)0xBA, (byte)0xAF, (byte)0xF2, (byte)0x58
    };

    private static final byte[] AMR_SILENCE_M7 = new byte[] {
            (byte)0x3C, (byte)0x11, (byte)0x06, (byte)0x30, (byte)0x22, (byte)0xAA, (byte)0x8A, (byte)0x93,
            (byte)0xA0, (byte)0xD3, (byte)0xAB, (byte)0x50, (byte)0x87, (byte)0xB4, (byte)0xF7, (byte)0xF6,
            (byte)0xDE, (byte)0x21, (byte)0x1D, (byte)0x98, (byte)0xD2, (byte)0xE5, (byte)0x0A, (byte)0x22,
            (byte)0x96, (byte)0x7A, (byte)0x09, (byte)0x82, (byte)0xD3, (byte)0x44, (byte)0x57, (byte)0x22,
            (byte)0xC9, (byte)0x04, (byte)0x20, (byte)0x80, (byte)0x09, (byte)0x41, (byte)0xDA, (byte)0x08,
            (byte)0x0B, (byte)0xCA, (byte)0xE4, (byte)0xE6, (byte)0x10, (byte)0xBC, (byte)0xAD, (byte)0x80,
            (byte)0x22, (byte)0xC0, (byte)0x99, (byte)0x72, (byte)0x79, (byte)0xAD, (byte)0xCF, (byte)0xCA,
            (byte)0xA3, (byte)0x3A, (byte)0x60
    };

    private static final byte[] AMR_SILENCE_M6 = new byte[] {
            (byte)0x34, (byte)0x11, (byte)0x06, (byte)0x10, (byte)0x33, (byte)0xBE, (byte)0xCE, (byte)0x83,
            (byte)0xA0, (byte)0xD1, (byte)0xA9, (byte)0x50, (byte)0x8F, (byte)0xA4, (byte)0xF8, (byte)0x91,
            (byte)0x3B, (byte)0x3C, (byte)0xD7, (byte)0x20, (byte)0x99, (byte)0x04, (byte)0x43, (byte)0x79,
            (byte)0x51, (byte)0x70, (byte)0x0A, (byte)0xC2, (byte)0x58, (byte)0x22, (byte)0x14, (byte)0x04,
            (byte)0x07, (byte)0x88, (byte)0x66, (byte)0xB9, (byte)0x8D, (byte)0x76, (byte)0xB0, (byte)0xC9,
            (byte)0x16, (byte)0x94, (byte)0xF8, (byte)0x44, (byte)0x85, (byte)0x8C, (byte)0xA1, (byte)0xF0,
            (byte)0x19, (byte)0xCF, (byte)0x00
    };

    private static final byte[] AMR_SILENCE_M5 = new byte[] {
            (byte)0x2C, (byte)0x11, (byte)0x06, (byte)0x10, (byte)0x33, (byte)0xB6, (byte)0xCE, (byte)0x93,
            (byte)0xA0, (byte)0xD3, (byte)0x2B, (byte)0x50, (byte)0x87, (byte)0x84, (byte)0xF9, (byte)0xB7,
            (byte)0x96, (byte)0xAA, (byte)0x1F, (byte)0x4A, (byte)0x92, (byte)0x41, (byte)0x88, (byte)0x2A,
            (byte)0x20, (byte)0x00, (byte)0x19, (byte)0x0C, (byte)0x01, (byte)0x41, (byte)0x46, (byte)0x0C,
            (byte)0xE6, (byte)0xA4, (byte)0x3C, (byte)0xA8, (byte)0x4A, (byte)0x8A, (byte)0x7A, (byte)0x0E,
            (byte)0xC0, (byte)0x83, (byte)0x3D, (byte)0x24, (byte)0x35, (byte)0x79, (byte)0xE8
    };

    private static final byte[] AMR_SILENCE_M4 = new byte[] {
            (byte)0x24, (byte)0x11, (byte)0x06, (byte)0x10, (byte)0x22, (byte)0xA2, (byte)0x8A, (byte)0x83,
            (byte)0xA0, (byte)0xD3, (byte)0x2D, (byte)0x50, (byte)0x87, (byte)0x84, (byte)0xEB, (byte)0x19,
            (byte)0x3B, (byte)0x90, (byte)0x90, (byte)0x00, (byte)0x05, (byte)0x67, (byte)0x60, (byte)0x04,
            (byte)0x12, (byte)0x55, (byte)0x04, (byte)0x40, (byte)0x01, (byte)0x95, (byte)0x3A, (byte)0x96,
            (byte)0x21, (byte)0x60, (byte)0x61, (byte)0x85, (byte)0x5B, (byte)0x5D, (byte)0x28, (byte)0x00,
            (byte)0xF0
    };

    private static final byte[] AMR_SILENCE_M3 = new byte[] {
            (byte)0x1C, (byte)0x11, (byte)0x06, (byte)0x00, (byte)0x33, (byte)0xB6, (byte)0xCE, (byte)0xA3,
            (byte)0xA0, (byte)0xD3, (byte)0xE9, (byte)0xD8, (byte)0x8F, (byte)0x94, (byte)0xE8, (byte)0x63,
            (byte)0x33, (byte)0x11, (byte)0x18, (byte)0x26, (byte)0x07, (byte)0x28, (byte)0x00, (byte)0x22,
            (byte)0x00, (byte)0x11, (byte)0xC2, (byte)0x44, (byte)0x50, (byte)0x4D, (byte)0x41, (byte)0x15,
            (byte)0x6F, (byte)0xC0, (byte)0x44, (byte)0x25, (byte)0x58
    };

    private static final byte[] AMR_SILENCE_M2 = new byte[] {
            (byte)0x14, (byte)0x11, (byte)0x06, (byte)0x20, (byte)0x22, (byte)0xAE, (byte)0x8A, (byte)0xB3,
            (byte)0xA0, (byte)0xD1, (byte)0x2D, (byte)0x58, (byte)0x8F, (byte)0x84, (byte)0xF0, (byte)0x47,
            (byte)0xB8, (byte)0x02, (byte)0xC0, (byte)0xA2, (byte)0x48, (byte)0x42, (byte)0x86, (byte)0xF2,
            (byte)0x40, (byte)0x14, (byte)0x15, (byte)0x50, (byte)0x49, (byte)0x54, (byte)0x43, (byte)0x48,
            (byte)0xD8
    };

    private static final byte[] AMR_SILENCE_M1 = new byte[] {
            (byte)0x0C, (byte)0x10, (byte)0x00, (byte)0x0F, (byte)0x00, (byte)0x0E, (byte)0xB3, (byte)0xA0,
            (byte)0xD7, (byte)0xA8, (byte)0x50, (byte)0x97, (byte)0x94, (byte)0x85, (byte)0x7A, (byte)0x86,
            (byte)0x86, (byte)0xC0, (byte)0x85, (byte)0x82, (byte)0x17, (byte)0x02, (byte)0x17, (byte)0x00
    };

    private static final byte[] AMR_SILENCE_M0 = new byte[] {
            (byte)0x04, (byte)0x10, (byte)0x21, (byte)0x00, (byte)0x39, (byte)0x1D, (byte)0x37, (byte)0xD4,
            (byte)0x91, (byte)0x74, (byte)0x7C, (byte)0xC2, (byte)0x78, (byte)0xE8, (byte)0xE0, (byte)0x88,
            (byte)0xE2, (byte)0xE0
    };


    private static final int[] AMR_SILENCE_SIZE = new int[] {
            18, 24, 33, 37, 41, 47, 51, 59, 61
    };

    public static int getPayloadSize(int mode) {
        return (mode < AMR_SILENCE_SIZE.length) ? AMR_SILENCE_SIZE[mode] : 0;
    }

    public static int copySilenceBuffer(int mode, byte[] buf, int offset) {
        byte[] silenceBuf = null;
        switch (mode) {
            case 0: silenceBuf = AMR_SILENCE_M0; break;
            case 1: silenceBuf = AMR_SILENCE_M1; break;
            case 2: silenceBuf = AMR_SILENCE_M2; break;
            case 3: silenceBuf = AMR_SILENCE_M3; break;
            case 4: silenceBuf = AMR_SILENCE_M4; break;
            case 5: silenceBuf = AMR_SILENCE_M5; break;
            case 6: silenceBuf = AMR_SILENCE_M6; break;
            case 7: silenceBuf = AMR_SILENCE_M7; break;
            case 8: silenceBuf = AMR_SILENCE_M8; break;
            default:
                return 0;
        }

        int size = getPayloadSize(mode);

        if (size == 0) {
            return 0;
        }

        System.arraycopy(silenceBuf, 0, buf, offset, size);

        return size;
    }

}
