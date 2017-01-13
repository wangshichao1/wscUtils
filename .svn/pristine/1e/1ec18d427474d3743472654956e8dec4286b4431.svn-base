/*jadclipse*/// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.

package com.wsc.utils;


public class MD5
{

    public MD5()
    {
        m_buf = new long[4];
        m_bits = new long[2];
        m_in = new byte[64];
    }

    public String toDigest(String src)
    {
        byte digest[] = toDigest(src.getBytes());
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < digest.length; i++)
        {
            sb.append(HEX[(digest[i] & 255) / 16]);
            sb.append(HEX[(digest[i] & 255) % 16]);
        }

        return sb.toString();
    }

    public byte[] toDigest(byte src[])
    {
        byte digest[] = new byte[16];
        int len = src.length;
        MD5Init();
        MD5Update(src, len);
        MD5Final(digest);
        return digest;
    }

    private void memset(byte des[], int des_offset, byte dat, int len)
    {
        for(int i = 0; i < len; i++)
            des[des_offset + i] = dat;

    }

    @SuppressWarnings("unused")
	private void memset(long des[], int des_offset, long dat, int len)
    {
        for(int i = 0; i < len; i++)
            des[des_offset + i] = dat;

    }

    private void memcpy(byte des[], int des_offset, byte src[], int src_offset, int len)
    {
        for(int i = 0; i < len; i++)
            des[des_offset + i] = src[src_offset + i];

    }

    private long bp2long(byte src[], int offset_lng)
    {
        long ret = 0L;
        ret = (long)src[offset_lng * 4 + 0] & 255L | (long)(src[offset_lng * 4 + 1] << 8) & 65280L | (long)(src[offset_lng * 4 + 2] << 16) & 16711680L | (long)(src[offset_lng * 4 + 3] << 24) & 4278190080L;
        return ret & 4294967295L;
    }

    private void MD5Init()
    {
        m_buf[0] = 1732584193L;
        m_buf[1] = 4023233417L;
        m_buf[2] = 2562383102L;
        m_buf[3] = 271733878L;
        m_bits[0] = 0L;
        m_bits[1] = 0L;
    }

    private void MD5Update(byte buf[], int len)
    {
        long t = m_bits[0];
        m_bits[0] = t + (long)(len << 3);
        if(m_bits[0] < t)
            m_bits[1]++;
        m_bits[1] = m_bits[1] + (long)(len >> 29);
        t = t >> 3 & 63L;
        if(t != 0L)
        {
            t = 64L - t & 4294967295L;
            if((long)len < t)
            {
                memcpy(m_in, (int)t, buf, 0, len);
                return;
            }
            memcpy(m_in, (int)t, buf, 0, (int)t);
            MD5Transform();
            len = (int)((long)len - t);
        }
        for(; len >= 64; len -= 64)
        {
            memcpy(m_in, 0, buf, (int)t, 64);
            MD5Transform();
            t += 64L;
        }

        memcpy(m_in, 0, buf, (int)t, len);
    }

    private void MD5Final(byte digest[])
    {
        long count = m_bits[0] >> 3 & 63L;
        m_in[(int)count] = -128;
        long p = count + 1L;
        count = 63L - count;
        if(count < 8L)
        {
            memset(m_in, (int)p, (byte)0, (int)count);
            MD5Transform();
            memset(m_in, 0, (byte)0, 56);
        } else
        {
            memset(m_in, (int)p, (byte)0, (int)(count - 8L));
        }
        m_in[56] = (byte)(int)(m_bits[0] & 255L);
        m_in[57] = (byte)(int)(m_bits[0] >> 8 & 255L);
        m_in[58] = (byte)(int)(m_bits[0] >> 16 & 255L);
        m_in[59] = (byte)(int)(m_bits[0] >> 24 & 255L);
        m_in[60] = (byte)(int)(m_bits[1] & 255L);
        m_in[61] = (byte)(int)(m_bits[1] >> 8 & 255L);
        m_in[62] = (byte)(int)(m_bits[1] >> 16 & 255L);
        m_in[63] = (byte)(int)(m_bits[1] >> 24 & 255L);
        MD5Transform();
        for(int i = 0; i < 4; i++)
        {
            digest[i * 4 + 0] = (byte)(int)(m_buf[i] & 255L);
            digest[i * 4 + 1] = (byte)(int)(m_buf[i] >> 8 & 255L);
            digest[i * 4 + 2] = (byte)(int)(m_buf[i] >> 16 & 255L);
            digest[i * 4 + 3] = (byte)(int)(m_buf[i] >> 24 & 255L);
        }

        MD5Init();
    }

    private long F1(long x, long y, long z)
    {
        return (z ^ x & (y ^ z)) & 4294967295L;
    }

    private long F2(long x, long y, long z)
    {
        return F1(z, x, y);
    }

    private long F3(long x, long y, long z)
    {
        return (x ^ y ^ z) & 4294967295L;
    }

    private long F4(long x, long y, long z)
    {
        return (y ^ (x | ~z)) & 4294967295L;
    }

    private long MD5STEP(long w, long f, long x, long y, long z, long data, long s)
    {
        w = w + f + data & 4294967295L;
        w = (w << (int)s | w >> (int)(32L - s)) & 4294967295L;
        w = w + x & 4294967295L;
        return w;
    }

    private void MD5Transform()
    {
        long a = m_buf[0];
        long b = m_buf[1];
        long c = m_buf[2];
        long d = m_buf[3];
        a = MD5STEP(a, F1(b, c, d), b, c, d, bp2long(m_in, 0) + 3614090360L, 7L);
        d = MD5STEP(d, F1(a, b, c), a, b, c, bp2long(m_in, 1) + 3905402710L, 12L);
        c = MD5STEP(c, F1(d, a, b), d, a, b, bp2long(m_in, 2) + 606105819L, 17L);
        b = MD5STEP(b, F1(c, d, a), c, d, a, bp2long(m_in, 3) + 3250441966L, 22L);
        a = MD5STEP(a, F1(b, c, d), b, c, d, bp2long(m_in, 4) + 4118548399L, 7L);
        d = MD5STEP(d, F1(a, b, c), a, b, c, bp2long(m_in, 5) + 1200080426L, 12L);
        c = MD5STEP(c, F1(d, a, b), d, a, b, bp2long(m_in, 6) + 2821735955L, 17L);
        b = MD5STEP(b, F1(c, d, a), c, d, a, bp2long(m_in, 7) + 4249261313L, 22L);
        a = MD5STEP(a, F1(b, c, d), b, c, d, bp2long(m_in, 8) + 1770035416L, 7L);
        d = MD5STEP(d, F1(a, b, c), a, b, c, bp2long(m_in, 9) + 2336552879L, 12L);
        c = MD5STEP(c, F1(d, a, b), d, a, b, bp2long(m_in, 10) + 4294925233L, 17L);
        b = MD5STEP(b, F1(c, d, a), c, d, a, bp2long(m_in, 11) + 2304563134L, 22L);
        a = MD5STEP(a, F1(b, c, d), b, c, d, bp2long(m_in, 12) + 1804603682L, 7L);
        d = MD5STEP(d, F1(a, b, c), a, b, c, bp2long(m_in, 13) + 4254626195L, 12L);
        c = MD5STEP(c, F1(d, a, b), d, a, b, bp2long(m_in, 14) + 2792965006L, 17L);
        b = MD5STEP(b, F1(c, d, a), c, d, a, bp2long(m_in, 15) + 1236535329L, 22L);
        a = MD5STEP(a, F2(b, c, d), b, c, d, bp2long(m_in, 1) + 4129170786L, 5L);
        d = MD5STEP(d, F2(a, b, c), a, b, c, bp2long(m_in, 6) + 3225465664L, 9L);
        c = MD5STEP(c, F2(d, a, b), d, a, b, bp2long(m_in, 11) + 643717713L, 14L);
        b = MD5STEP(b, F2(c, d, a), c, d, a, bp2long(m_in, 0) + 3921069994L, 20L);
        a = MD5STEP(a, F2(b, c, d), b, c, d, bp2long(m_in, 5) + 3593408605L, 5L);
        d = MD5STEP(d, F2(a, b, c), a, b, c, bp2long(m_in, 10) + 38016083L, 9L);
        c = MD5STEP(c, F2(d, a, b), d, a, b, bp2long(m_in, 15) + 3634488961L, 14L);
        b = MD5STEP(b, F2(c, d, a), c, d, a, bp2long(m_in, 4) + 3889429448L, 20L);
        a = MD5STEP(a, F2(b, c, d), b, c, d, bp2long(m_in, 9) + 568446438L, 5L);
        d = MD5STEP(d, F2(a, b, c), a, b, c, bp2long(m_in, 14) + 3275163606L, 9L);
        c = MD5STEP(c, F2(d, a, b), d, a, b, bp2long(m_in, 3) + 4107603335L, 14L);
        b = MD5STEP(b, F2(c, d, a), c, d, a, bp2long(m_in, 8) + 1163531501L, 20L);
        a = MD5STEP(a, F2(b, c, d), b, c, d, bp2long(m_in, 13) + 2850285829L, 5L);
        d = MD5STEP(d, F2(a, b, c), a, b, c, bp2long(m_in, 2) + 4243563512L, 9L);
        c = MD5STEP(c, F2(d, a, b), d, a, b, bp2long(m_in, 7) + 1735328473L, 14L);
        b = MD5STEP(b, F2(c, d, a), c, d, a, bp2long(m_in, 12) + 2368359562L, 20L);
        a = MD5STEP(a, F3(b, c, d), b, c, d, bp2long(m_in, 5) + 4294588738L, 4L);
        d = MD5STEP(d, F3(a, b, c), a, b, c, bp2long(m_in, 8) + 2272392833L, 11L);
        c = MD5STEP(c, F3(d, a, b), d, a, b, bp2long(m_in, 11) + 1839030562L, 16L);
        b = MD5STEP(b, F3(c, d, a), c, d, a, bp2long(m_in, 14) + 4259657740L, 23L);
        a = MD5STEP(a, F3(b, c, d), b, c, d, bp2long(m_in, 1) + 2763975236L, 4L);
        d = MD5STEP(d, F3(a, b, c), a, b, c, bp2long(m_in, 4) + 1272893353L, 11L);
        c = MD5STEP(c, F3(d, a, b), d, a, b, bp2long(m_in, 7) + 4139469664L, 16L);
        b = MD5STEP(b, F3(c, d, a), c, d, a, bp2long(m_in, 10) + 3200236656L, 23L);
        a = MD5STEP(a, F3(b, c, d), b, c, d, bp2long(m_in, 13) + 681279174L, 4L);
        d = MD5STEP(d, F3(a, b, c), a, b, c, bp2long(m_in, 0) + 3936430074L, 11L);
        c = MD5STEP(c, F3(d, a, b), d, a, b, bp2long(m_in, 3) + 3572445317L, 16L);
        b = MD5STEP(b, F3(c, d, a), c, d, a, bp2long(m_in, 6) + 76029189L, 23L);
        a = MD5STEP(a, F3(b, c, d), b, c, d, bp2long(m_in, 9) + 3654602809L, 4L);
        d = MD5STEP(d, F3(a, b, c), a, b, c, bp2long(m_in, 12) + 3873151461L, 11L);
        c = MD5STEP(c, F3(d, a, b), d, a, b, bp2long(m_in, 15) + 530742520L, 16L);
        b = MD5STEP(b, F3(c, d, a), c, d, a, bp2long(m_in, 2) + 3299628645L, 23L);
        a = MD5STEP(a, F4(b, c, d), b, c, d, bp2long(m_in, 0) + 4096336452L, 6L);
        d = MD5STEP(d, F4(a, b, c), a, b, c, bp2long(m_in, 7) + 1126891415L, 10L);
        c = MD5STEP(c, F4(d, a, b), d, a, b, bp2long(m_in, 14) + 2878612391L, 15L);
        b = MD5STEP(b, F4(c, d, a), c, d, a, bp2long(m_in, 5) + 4237533241L, 21L);
        a = MD5STEP(a, F4(b, c, d), b, c, d, bp2long(m_in, 12) + 1700485571L, 6L);
        d = MD5STEP(d, F4(a, b, c), a, b, c, bp2long(m_in, 3) + 2399980690L, 10L);
        c = MD5STEP(c, F4(d, a, b), d, a, b, bp2long(m_in, 10) + 4293915773L, 15L);
        b = MD5STEP(b, F4(c, d, a), c, d, a, bp2long(m_in, 1) + 2240044497L, 21L);
        a = MD5STEP(a, F4(b, c, d), b, c, d, bp2long(m_in, 8) + 1873313359L, 6L);
        d = MD5STEP(d, F4(a, b, c), a, b, c, bp2long(m_in, 15) + 4264355552L, 10L);
        c = MD5STEP(c, F4(d, a, b), d, a, b, bp2long(m_in, 6) + 2734768916L, 15L);
        b = MD5STEP(b, F4(c, d, a), c, d, a, bp2long(m_in, 13) + 1309151649L, 21L);
        a = MD5STEP(a, F4(b, c, d), b, c, d, bp2long(m_in, 4) + 4149444226L, 6L);
        d = MD5STEP(d, F4(a, b, c), a, b, c, bp2long(m_in, 11) + 3174756917L, 10L);
        c = MD5STEP(c, F4(d, a, b), d, a, b, bp2long(m_in, 2) + 718787259L, 15L);
        b = MD5STEP(b, F4(c, d, a), c, d, a, bp2long(m_in, 9) + 3951481745L, 21L);
        m_buf[0] += a;
        m_buf[1] += b;
        m_buf[2] += c;
        m_buf[3] += d;
    }

    private long m_buf[];
    private long m_bits[];
    private byte m_in[];
    private char HEX[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'a', 'b', 'c', 'd', 'e', 'f'
    };
}


/*
	DECOMPILATION REPORT

	Decompiled from: D:\WorkSpace\能力平台对接\ECRM\web\WEB-INF\lib\oper_password.jar
	Total time: 297 ms
	Jad reported messages/errors:
The class file version is 49.0 (only 45.3, 46.0 and 47.0 are supported)
	Exit status: 0
	Caught exceptions:
*/