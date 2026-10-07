package com.example.model

data class NewsItem(
    val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val category: String,
    val date: String,
    val readTime: String,
    val source: String = "Mingəçevir Bələdiyyəsinin Mətbuat Xidməti",
    val isFeatured: Boolean = false,
    val viewsCount: Int = 142,
    val tags: List<String> = emptyList(),
    val imageUrl: String? = null
)

data class AnnouncementItem(
    val id: String,
    val title: String,
    val description: String,
    val fullText: String,
    val date: String,
    val deadline: String,
    val status: String, // "Aktiv", "Başa çatıb"
    val department: String,
    val category: String = "Ümumi", // "Hərrac", "İctimai Dinləmə", "Müsabiqə", "Bildiriş"
    val referenceNumber: String? = null,
    val attachments: List<String> = emptyList()
)

data class Appeal(
    val id: String,
    val trackingCode: String,
    val applicantName: String,
    val finCode: String,
    val phone: String,
    val address: String,
    val category: String,
    val subject: String,
    val description: String,
    val date: String,
    val status: String, // "Göndərildi", "Baxılır", "İcradadır", "Tamamlandı"
    val response: String? = null,
    val attachmentName: String? = null
)

data class Hotline(
    val id: String,
    val name: String,
    val number: String,
    val description: String,
    val category: String, // "Fövqəladə", "Kommunal", "Bələdiyyə"
    val isEmergency: Boolean = false,
    val workingHours: String = "24/7"
)

data class OfficialPerson(
    val name: String,
    val position: String,
    val department: String,
    val receptionHours: String,
    val phone: String,
    val email: String,
    val roomNumber: String? = null,
    val bio: String? = null
)

data class TaxType(
    val id: String,
    val name: String,
    val description: String,
    val legalBasis: String,
    val calculationGuide: String,
    val paymentPeriod: String = "İldə 2 dəfə (15 avqust və 15 noyabr tarixlərinədək)"
)

data class TaxExemption(
    val id: String,
    val title: String,
    val description: String,
    val legalArticle: String
)

data class MunicipalFaq(
    val id: String,
    val question: String,
    val answer: String,
    val category: String // "Vergilər", "Müraciətlər", "Bələdiyyə", "Qaynar Xətlər"
)

data class SocialChannel(
    val name: String,
    val handle: String,
    val url: String,
    val iconType: String,
    val description: String
)

data class CivicSurvey(
    val id: String,
    val title: String,
    val description: String,
    val options: List<SurveyOption>,
    val totalVotes: Int,
    val userVotedOptionId: String? = null
)

data class SurveyOption(
    val id: String,
    val text: String,
    val votes: Int
)

data class MunicipalDocument(
    val id: String,
    val title: String,
    val category: String,
    val date: String,
    val fileType: String,
    val size: String,
    val description: String? = null
)
