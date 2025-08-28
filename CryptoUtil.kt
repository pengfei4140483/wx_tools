import android.util.Base64
import java.nio.charset.StandardCharsets

object CryptoUtil {
    // 加密：明文→反转→补4空字符→Base64
    fun encrypt(plainText: String): String {
        val reversed = plainText.reversed()
        val padded = reversed.toByteArray(StandardCharsets.UTF_8) + byteArrayOf(0x00, 0x00, 0x00, 0x00)
        return Base64.encodeToString(padded, Base64.NO_WRAP)
    }

    // 解密：Base64→解码→去4空字符→反转
    fun decrypt(cipherText: String): String? {
        return try {
            val decoded = Base64.decode(cipherText, Base64.NO_WRAP)
            if (decoded.size < 4) return "密文格式错"
            val valid = decoded.copyOfRange(0, decoded.size - 4)
            String(valid, StandardCharsets.UTF_8).reversed()
        } catch (e: Exception) {
            "解密失败：${e.message}"
        }
    }
}
