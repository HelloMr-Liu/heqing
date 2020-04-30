package com.huijianzhu.heqing.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.springframework.util.DigestUtils;

import java.util.concurrent.RunnableFuture;

/**
 * ================================================================
 * 说明：自定义工具类
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/29  10:06            创建
 * =================================================================
 **/
public class MyUtils {


    private MyUtils(){}

    private static String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggS" +
            "jAgEAAoIBAQCELryiFJkPKMLADHr4pXO/dcJ3GT8+kWXZfRMxIIAua6G7/S48xEE" +
            "E03FbInEttxW1riGFu7iRc/gVk/CIdsKTdGWrYYvLVQSnHyakZ+3r3olX9taRk12ph" +
            "rvlEp1/yQ8/5rSaU5kBeC3mAl+VOQvMaYdCGYE+GnOR5XEMJkM85L1uvQ7YtnmqJYR" +
            "QVrOHr0zAd1nkUQ7kFB4qyohHjbuA8GR/MRLhMi+O31OGY78fABBpI1/MtncHNmqLY" +
            "HkpBjg58m6IPUga6kpZfMk/wgGEzRm7xmjPaPzx7ocamKBTaJm3+sSnDLi6Tjko3Yr" +
            "R3ng6TkBt0MBvWir0+8GSXM4hAgMBAAECggEADPNbTfIAsQiIVDKk1bm4JnkOLYcOuD" +
            "2coftsJvMNO/8VoIqEvjVoPWXOBGTSe+0lNmCyLh0fTzjyhIKzHVutYq8Tyv/Kp4hT5" +
            "V/a9F6uY96wzCqGCUtL8PEfdakmPz/eu58J8Njh88/nh1KSNNea/VMVindfP0tuKUw2" +
            "kD8rdUq7PglU1ioT7quNncPOBElU+j5as3Zf4fz4tNncJbrOPq/QlVOMMecVf+/T2TC" +
            "plXY1KaUNXUNXoowT6uJtr+0uy5GHoZfBNjIm70JlSVJ1lypPKPby/SFTOEBi3nLebLZ" +
            "3+MakXnqYjmfo9rg6JKV2D2vIYB5wd/mPUhJ0khfRAQKBgQDfZ+GheHaTOCY16WSzk+" +
            "TSH0V/EKLUyx26wLsl42J7Dbg7GoxgCInEW0MSL5I8lOHWAv7DOnTgv+dAChsOasqK9" +
            "k+F5ylvz3J+WtuPXxSDZ0fhmCGLNCpIbIFlJIRyaON5IFDlPCxxviSFfgZrTqXxeJ4r" +
            "BA6SzEc2UcwnoW8ucQKBgQCXd7Qzd1ZXuUOXOv4g9dmonaIWt7qxNk/d8Hn0aJinX2" +
            "bq32U0wxPZo24szkk0yigC08ZSFUcsOx7n4pP58GTLBDs9qk/Q4Y+X8u9ZEQyViJK4" +
            "03Z1tEUwnGgati3R/pQXQNgJE3HCLkKGjNnhKYfq6aScA/Ckbj8KBaHC0zXSsQKBgQ" +
            "DRjj3FGpwPKCIfe9fr4pRGzPKBZ/d1Jzmxzcs+lPXxe0MOna6C/j9MIlbdniXMEdN" +
            "szc5vDkxH7XpbRZ9vfJiUlMqvTdA+gxZgk5VT/NnfEGgKoosEamAnIzCU0RYzw1Nsc" +
            "PWwsSr3XmYfmcEnstRRltS2gM2ywU8jEsMvgWwq4QKBgAlJS4EERgTm1OAGu+HlcMzH" +
            "rVJbPVT0EX136uIzL3jDfpnTzG6pryPewzh4BUD5S+6wcS5DI7Pwg/qPh6cBOAzCW6" +
            "dyM6aIzbbxK/937h+U2fn+7PpWbQ+H51IL7U88nnO2hAg6avUOxQFezVTiqPScEn2G" +
            "OFC8eZzxr3M0Y85hAoGAXj68DvDtouHQ1kxHKCVK91+ScaaxyWpg/zr55iumlpuq2Tjp" +
            "dJJPBDrK4Usa4bxeXlKe9sogh9w6iWKjgUmDPQC7eXzHCYH72wfpSY+AOj+0QMQfcw0G" +
            "Y+DbKaXvmQzyLJvfq/dM5m/LELvlD99Uh3ByF4gpb2EjR2E7g5zrsOU=";

    private static String PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhC68" +
            "ohSZDyjCwAx6+KVzv3XCdxk/PpFl2X0TMSCALmuhu/0uPMRBBNNxWyJxLbcVta4hhbu4kXP4F" +
            "ZPwiHbCk3Rlq2GLy1UEpx8mpGft696JV/bWkZNdqYa75RKdf8kPP+a0mlOZAXgt5gJflTkLzG" +
            "mHQhmBPhpzkeVxDCZDPOS9br0O2LZ5qiWEUFazh69MwHdZ5FEO5BQeKsqIR427gPBkfzES4TIv" +
            "jt9ThmO/HwAQaSNfzLZ3BzZqi2B5KQY4OfJuiD1IGupKWXzJP8IBhM0Zu8Zoz2j88e6HGpigU2" +
            "iZt/rEpwy4uk45KN2K0d54Ok5AbdDAb1oq9PvBklzOIQIDAQAB";

    private static RSA rsa = new RSA(PRIVATE_KEY,PUBLIC_KEY);



    /**
     *  功能：非对称加密 通讯加密
     *  时间：2020/4/29 15:37
     *  参数：名称            类型            描述
     *       password      String          明文密码
     *
     *  返回：byte[]
     *  描述：明文加密后的字节数组
     */
    public static byte[] encryption(String password){
        byte[] encrypt = rsa.encrypt(password.getBytes(),KeyType.PublicKey);
        return encrypt;
    }

    /**
     *  功能：描述当前方法 通讯加密
     *  时间：2020/4/29 15:41
     *  参数：名称                       类型            描述
     *       encrypt                   byte[]         加密的内容
     *
     *  返回：String
     *  描述：解密加密后的密码返回明文密码
     */
    public static String decryption(byte[] encrypt){
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        return StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
    }


    /**
     *  功能：获取加密后的密码
     *  时间：2020/4/30 10:56
     *  参数：名称            类型            描述
     *       password       String         铭文密码
     *
     *  返回：String
     *  描述：返回一个加密密码
     */
    public static  String getEncryption(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }



    /**
     *  功能：获取一个32长度的唯一id
     *  时间：2020/4/29 10:10
     *
     *  返回：static
     *  描述：一个32长度的唯一id
     */
    public static String get32LengthUUID(){
        return IdUtil.simpleUUID();
    }






}
