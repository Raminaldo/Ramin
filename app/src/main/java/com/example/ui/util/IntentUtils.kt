package com.example.ui.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object IntentUtils {
    fun safeStartActivity(context: Context, intent: Intent, fallbackToast: String = "Müvafiq tətbiq tapılmadı") {
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, fallbackToast, Toast.LENGTH_SHORT).show()
        }
    }

    fun openBrowser(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Səhifəni açmaq mümkün olmadı", Toast.LENGTH_SHORT).show()
        }
    }

    fun dialPhone(context: Context, phoneNumber: String) {
        try {
            val cleanedNumber = phoneNumber.replace(" ", "").replace("(", "").replace(")", "").replace("-", "")
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanedNumber"))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Zəng xidməti açıla bilmədi ($phoneNumber)", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendEmail(context: Context, emailAddress: String, subject: String? = null) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$emailAddress")
                if (subject != null) putExtra(Intent.EXTRA_SUBJECT, subject)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "E-poçt tətbiqi tapılmadı ($emailAddress)", Toast.LENGTH_SHORT).show()
        }
    }

    fun openMap(context: Context, latitude: Double, longitude: Double, label: String) {
        try {
            val geoUri = Uri.parse("geo:$latitude,$longitude?q=${Uri.encode(label)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            try {
                val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$latitude,$longitude")
                val webIntent = Intent(Intent.ACTION_VIEW, webUri)
                context.startActivity(webIntent)
            } catch (ex: Exception) {
                Toast.makeText(context, "Xəritə açıla bilmədi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun shareText(context: Context, subject: String, text: String, chooserTitle: String = "Paylaş") {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, text)
            }
            context.startActivity(Intent.createChooser(shareIntent, chooserTitle))
        } catch (e: Exception) {
            Toast.makeText(context, "Paylaşmaq mümkün olmadı", Toast.LENGTH_SHORT).show()
        }
    }
}
