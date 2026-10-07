package com.example.data.firestore

import com.example.model.AnnouncementItem
import com.example.model.Hotline
import com.example.model.NewsItem
import com.example.model.OfficialPerson
import com.example.model.SocialChannel

data class FirestoreNews(
    val id: String = "",
    val title: String = "",
    val summary: String = "",
    val content: String = "",
    val category: String = "Ümumi",
    val date: String = "",
    val readTime: String = "3 dəq",
    val source: String = "Mingəçevir Bələdiyyəsinin Mətbuat Xidməti",
    val isFeatured: Boolean = false,
    val viewsCount: Int = 0,
    val imageUrl: String? = null,
    val isActive: Boolean = true
) {
    fun toNewsItem(): NewsItem = NewsItem(
        id = id,
        title = title,
        summary = summary,
        content = content,
        category = category,
        date = date,
        readTime = readTime,
        source = source,
        isFeatured = isFeatured,
        viewsCount = viewsCount,
        tags = listOf(category),
        imageUrl = imageUrl
    )

    fun toMap(): Map<String, Any> = buildMap {
        put("id", id)
        put("title", title)
        put("summary", summary)
        put("content", content)
        put("category", category)
        put("date", date)
        put("readTime", readTime)
        put("source", source)
        put("isFeatured", isFeatured)
        put("viewsCount", viewsCount)
        put("isActive", isActive)
        imageUrl?.let { put("imageUrl", it) }
    }
}

data class FirestoreAnnouncement(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val fullText: String = "",
    val date: String = "",
    val deadline: String = "",
    val status: String = "Aktiv",
    val department: String = "Mingəçevir Bələdiyyəsi",
    val category: String = "Ümumi",
    val referenceNumber: String? = null,
    val priority: String = "Normal",
    val type: String = "Bildiriş",
    val isActive: Boolean = true
) {
    fun toAnnouncementItem(): AnnouncementItem = AnnouncementItem(
        id = id,
        title = title,
        description = description,
        fullText = fullText.ifEmpty { description },
        date = date,
        deadline = deadline,
        status = status,
        department = department,
        category = category,
        referenceNumber = referenceNumber
    )

    fun toMap(): Map<String, Any> = buildMap {
        put("id", id)
        put("title", title)
        put("description", description)
        put("fullText", fullText)
        put("date", date)
        put("deadline", deadline)
        put("status", status)
        put("department", department)
        put("category", category)
        put("priority", priority)
        put("type", type)
        put("isActive", isActive)
        referenceNumber?.let { put("referenceNumber", it) }
    }
}

data class FirestoreHotline(
    val id: String = "",
    val title: String = "",
    val number: String = "",
    val description: String? = null,
    val category: String = "Bələdiyyə",
    val isEmergency: Boolean = false,
    val workingHours: String = "24/7",
    val order: Int = 0,
    val isActive: Boolean = true
) {
    fun toHotline(): Hotline = Hotline(
        id = id,
        name = title,
        number = number,
        description = description ?: "",
        category = category,
        isEmergency = isEmergency,
        workingHours = workingHours
    )

    fun toMap(): Map<String, Any> = buildMap {
        put("id", id)
        put("title", title)
        put("number", number)
        put("category", category)
        put("isEmergency", isEmergency)
        put("workingHours", workingHours)
        put("order", order)
        put("isActive", isActive)
        description?.let { put("description", it) }
    }
}

data class FirestoreSocialMedia(
    val id: String = "",
    val title: String = "",
    val platform: String = "facebook",
    val url: String = "",
    val handle: String? = null,
    val description: String = "",
    val order: Int = 0,
    val isActive: Boolean = true
) {
    fun toSocialChannel(): SocialChannel = SocialChannel(
        name = title,
        handle = handle ?: title,
        url = url,
        iconType = platform.lowercase(),
        description = description
    )

    fun toMap(): Map<String, Any> = buildMap {
        put("id", id)
        put("title", title)
        put("platform", platform)
        put("url", url)
        put("description", description)
        put("order", order)
        put("isActive", isActive)
        handle?.let { put("handle", it) }
    }
}

data class FirestoreReception(
    val id: String = "",
    val title: String = "",
    val person: String = "",
    val position: String = "",
    val department: String = "Mingəçevir Bələdiyyəsi",
    val schedule: String = "",
    val location: String? = "Mingəçevir Bələdiyyəsi İnzibati Binası",
    val phone: String = "(024) 274-25-10",
    val email: String = "info@mingecevir-belediyyesi.gov.az",
    val contact: String? = null,
    val order: Int = 0,
    val isActive: Boolean = true
) {
    fun toOfficialPerson(): OfficialPerson = OfficialPerson(
        name = person,
        position = position,
        department = department,
        receptionHours = schedule,
        phone = phone,
        email = email,
        roomNumber = location
    )

    fun toMap(): Map<String, Any> = buildMap {
        put("id", id)
        put("title", title)
        put("person", person)
        put("position", position)
        put("department", department)
        put("schedule", schedule)
        put("phone", phone)
        put("email", email)
        put("order", order)
        put("isActive", isActive)
        location?.let { put("location", it) }
        contact?.let { put("contact", it) }
    }
}

data class FirestoreGeneralInfo(
    val id: String = "main",
    val title: String = "Mingəçevir Bələdiyyəsi",
    val aboutText: String = "Mingəçevir Bələdiyyəsi şəhər sakinlərinin yerli özünüidarəetməsini həyata keçirən nümayəndəli kollegial dövlət təşkilatıdır.",
    val address: String = "Mingəçevir şəhəri, Heydər Əliyev prospekti 24",
    val phone: String = "(024) 274-25-10",
    val email: String = "info@mingecevir-belediyyesi.gov.az",
    val workHours: String = "Bazar ertəsi - Cümə, 09:00 - 18:00",
    val chairman: String? = "Ramin Əhmədov",
    val hotlineShort: String = "164"
) {
    fun toMap(): Map<String, Any> = buildMap {
        put("id", id)
        put("title", title)
        put("aboutText", aboutText)
        put("address", address)
        put("phone", phone)
        put("email", email)
        put("workHours", workHours)
        chairman?.let { put("chairman", it) }
        put("hotlineShort", hotlineShort)
    }
}

data class FirestoreAdminUser(
    val uid: String = "",
    val email: String = "",
    val role: String = "admin",
    val createdAt: String = ""
) {
    fun toMap(): Map<String, Any> = buildMap {
        put("uid", uid)
        put("email", email)
        put("role", role)
        if (createdAt.isNotEmpty()) put("createdAt", createdAt)
    }
}
