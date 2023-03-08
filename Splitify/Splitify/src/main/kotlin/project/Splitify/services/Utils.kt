package project.Splitify.services

import org.springframework.stereotype.Component
import project.Splitify.domain.UserCreation
import project.Splitify.domain.UserOutput
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Component
class Utils {

    private val SECRET_KEY = System.getenv("SECRET_KEY")


    fun encrypt(plainText: String): String {

        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")

        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(SECRET_KEY.toByteArray(), "AES"))

        val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(encryptedText: String): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(SECRET_KEY.toByteArray(), "AES"))
        val encryptedBytes = Base64.getDecoder().decode(encryptedText)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    fun encryptUserCreation(userCreation: UserCreation) : UserCreation {
        return userCreation.copy(
            name = encrypt(userCreation.name),
            email = encrypt(userCreation.email),
            phone = encrypt(userCreation.phone),
            password = encrypt(userCreation.password)
        )
    }

    fun decryptUserOutput(userOutput : UserOutput) : UserOutput{
        return userOutput.copy(
            name = decrypt(userOutput.name),
            email = decrypt(userOutput.email),
            phone = decrypt(userOutput.phone),
        )
    }


    fun isPasswordSafe(password : String) : Boolean{
        val regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}\$")
        return regex.matches(password)
    }

}