package kg.hackathon.template.prefs

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

abstract class EncryptedPreferences(context: Context, fileName: String) {

    private val masterKey = MasterKey.Builder(context).setKeyScheme(MASTER_KEY_SCHEME).build()

    protected val sharedPreferences = EncryptedSharedPreferences
        .create(context.applicationContext, fileName, masterKey, KEY_SCHEME, VALUE_SCHEME)

    fun clear() {
        sharedPreferences.edit { clear() }
    }

    companion object {
        private val MASTER_KEY_SCHEME = MasterKey.KeyScheme.AES256_GCM
        private val KEY_SCHEME = EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
        private val VALUE_SCHEME = EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    }

}