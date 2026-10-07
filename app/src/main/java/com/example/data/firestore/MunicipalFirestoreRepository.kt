package com.example.data.firestore

import android.content.Context
import com.example.R
import com.example.data.MunicipalDataProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class MunicipalFirestoreRepository(private val db: FirebaseFirestore) {

    constructor(databaseId: String) : this(
        FirebaseFirestore.getInstance(databaseId)
    )

    constructor(context: Context) : this(
        context.applicationContext.getString(R.string.firestore_database_id)
    )

    // NEWS
    fun observeNews(): Flow<List<FirestoreNews>> = flow {
        val path = "news"
        emitAll(
            db.collection("news")
                .snapshots()
                .map { snapshot -> snapshot.toObjects(FirestoreNews::class.java) }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                    emit(emptyList())
                }
        )
    }

    suspend fun saveNews(news: FirestoreNews): Result<Unit> = runCatching {
        val docId = news.id.ifEmpty { "news_${System.currentTimeMillis()}" }
        val toSave = news.copy(id = docId)
        db.collection("news").document(docId).set(toSave.toMap()).await()
        Unit
    }.onFailure { if (it is Exception) handleFirestoreError(it, OperationType.WRITE, "news/${news.id}") }

    suspend fun deleteNews(id: String): Result<Unit> = runCatching {
        db.collection("news").document(id).delete().await()
        Unit
    }.onFailure { if (it is Exception) handleFirestoreError(it, OperationType.DELETE, "news/$id") }

    // ANNOUNCEMENTS
    fun observeAnnouncements(): Flow<List<FirestoreAnnouncement>> = flow {
        val path = "announcements"
        emitAll(
            db.collection("announcements")
                .snapshots()
                .map { snapshot -> snapshot.toObjects(FirestoreAnnouncement::class.java) }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                    emit(emptyList())
                }
        )
    }

    suspend fun saveAnnouncement(ann: FirestoreAnnouncement): Result<Unit> = runCatching {
        val docId = ann.id.ifEmpty { "ann_${System.currentTimeMillis()}" }
        val toSave = ann.copy(id = docId)
        db.collection("announcements").document(docId).set(toSave.toMap()).await()
        Unit
    }.onFailure { if (it is Exception) handleFirestoreError(it, OperationType.WRITE, "announcements/${ann.id}") }

    suspend fun deleteAnnouncement(id: String): Result<Unit> = runCatching {
        db.collection("announcements").document(id).delete().await()
        Unit
    }.onFailure { if (it is Exception) handleFirestoreError(it, OperationType.DELETE, "announcements/$id") }

    // HOTLINES
    fun observeHotlines(): Flow<List<FirestoreHotline>> = flow {
        val path = "hotlines"
        emitAll(
            db.collection("hotlines")
                .snapshots()
                .map { snapshot -> snapshot.toObjects(FirestoreHotline::class.java).sortedBy { it.order } }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                    emit(emptyList())
                }
        )
    }

    suspend fun saveHotline(hotline: FirestoreHotline): Result<Unit> = runCatching {
        val docId = hotline.id.ifEmpty { "hotline_${System.currentTimeMillis()}" }
        val toSave = hotline.copy(id = docId)
        db.collection("hotlines").document(docId).set(toSave.toMap()).await()
        Unit
    }.onFailure { if (it is Exception) handleFirestoreError(it, OperationType.WRITE, "hotlines/${hotline.id}") }

    suspend fun deleteHotline(id: String): Result<Unit> = runCatching {
        db.collection("hotlines").document(id).delete().await()
        Unit
    }.onFailure { if (it is Exception) handleFirestoreError(it, OperationType.DELETE, "hotlines/$id") }

    // SOCIAL MEDIA
    fun observeSocialMedia(): Flow<List<FirestoreSocialMedia>> = flow {
        val path = "social_media"
        emitAll(
            db.collection("social_media")
                .snapshots()
                .map { snapshot -> snapshot.toObjects(FirestoreSocialMedia::class.java).sortedBy { it.order } }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                    emit(emptyList())
                }
        )
    }

    suspend fun saveSocialMedia(social: FirestoreSocialMedia): Result<Unit> = runCatching {
        val docId = social.id.ifEmpty { "soc_${System.currentTimeMillis()}" }
        val toSave = social.copy(id = docId)
        db.collection("social_media").document(docId).set(toSave.toMap()).await()
        Unit
    }.onFailure { if (it is Exception) handleFirestoreError(it, OperationType.WRITE, "social_media/${social.id}") }

    suspend fun deleteSocialMedia(id: String): Result<Unit> = runCatching {
        db.collection("social_media").document(id).delete().await()
        Unit
    }.onFailure { if (it is Exception) handleFirestoreError(it, OperationType.DELETE, "social_media/$id") }

    // RECEPTIONS / MEETINGS
    fun observeReceptions(): Flow<List<FirestoreReception>> = flow {
        val path = "receptions"
        emitAll(
            db.collection("receptions")
                .snapshots()
                .map { snapshot -> snapshot.toObjects(FirestoreReception::class.java).sortedBy { it.order } }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.LIST, path)
                    emit(emptyList())
                }
        )
    }

    suspend fun saveReception(reception: FirestoreReception): Result<Unit> = runCatching {
        val docId = reception.id.ifEmpty { "rec_${System.currentTimeMillis()}" }
        val toSave = reception.copy(id = docId)
        db.collection("receptions").document(docId).set(toSave.toMap()).await()
        Unit
    }.onFailure { if (it is Exception) handleFirestoreError(it, OperationType.WRITE, "receptions/${reception.id}") }

    suspend fun deleteReception(id: String): Result<Unit> = runCatching {
        db.collection("receptions").document(id).delete().await()
        Unit
    }.onFailure { if (it is Exception) handleFirestoreError(it, OperationType.DELETE, "receptions/$id") }

    // GENERAL INFO
    fun observeGeneralInfo(): Flow<FirestoreGeneralInfo?> = flow {
        val path = "general_info/main"
        emitAll(
            db.collection("general_info").document("main")
                .snapshots()
                .map { snapshot -> snapshot.toObject(FirestoreGeneralInfo::class.java) }
                .catch { error ->
                    if (error is Exception) handleFirestoreError(error, OperationType.GET, path)
                    emit(null)
                }
        )
    }

    suspend fun saveGeneralInfo(info: FirestoreGeneralInfo): Result<Unit> = runCatching {
        db.collection("general_info").document("main").set(info.toMap()).await()
        Unit
    }.onFailure { if (it is Exception) handleFirestoreError(it, OperationType.WRITE, "general_info/main") }

    // SEED INITIAL DATA (Helper to populate cloud DB with existing municipal content)
    suspend fun seedInitialData(): Result<Int> = runCatching {
        var count = 0
        // Seed News
        MunicipalDataProvider.newsList.forEach { news ->
            val fn = FirestoreNews(
                id = news.id,
                title = news.title,
                summary = news.summary,
                content = news.content,
                category = news.category,
                date = news.date,
                readTime = news.readTime,
                source = news.source,
                isFeatured = news.isFeatured,
                viewsCount = news.viewsCount,
                imageUrl = news.imageUrl,
                isActive = true
            )
            db.collection("news").document(fn.id).set(fn.toMap()).await()
            count++
        }
        // Seed Announcements
        MunicipalDataProvider.announcementsList.forEach { ann ->
            val fa = FirestoreAnnouncement(
                id = ann.id,
                title = ann.title,
                description = ann.description,
                fullText = ann.fullText,
                date = ann.date,
                deadline = ann.deadline,
                status = ann.status,
                department = ann.department,
                category = ann.category,
                referenceNumber = ann.referenceNumber,
                isActive = true
            )
            db.collection("announcements").document(fa.id).set(fa.toMap()).await()
            count++
        }
        // Seed Hotlines
        MunicipalDataProvider.hotlinesList.forEachIndexed { idx, h ->
            val fh = FirestoreHotline(
                id = h.id,
                title = h.name,
                number = h.number,
                description = h.description,
                category = h.category,
                isEmergency = h.isEmergency,
                workingHours = h.workingHours,
                order = idx + 1,
                isActive = true
            )
            db.collection("hotlines").document(fh.id).set(fh.toMap()).await()
            count++
        }
        // Seed Social Media
        MunicipalDataProvider.socialChannels.forEachIndexed { idx, s ->
            val fs = FirestoreSocialMedia(
                id = "soc_${s.iconType}",
                title = s.name,
                platform = s.iconType,
                url = s.url,
                handle = s.handle,
                description = s.description,
                order = idx + 1,
                isActive = true
            )
            db.collection("social_media").document(fs.id).set(fs.toMap()).await()
            count++
        }
        // Seed Receptions
        MunicipalDataProvider.officialsList.forEachIndexed { idx, p ->
            val fr = FirestoreReception(
                id = "rec_${idx + 1}",
                title = p.name,
                person = p.name,
                position = p.position,
                department = p.department,
                schedule = p.receptionHours,
                phone = p.phone,
                email = p.email,
                order = idx + 1,
                isActive = true
            )
            db.collection("receptions").document(fr.id).set(fr.toMap()).await()
            count++
        }
        // Seed General Info
        val genInfo = FirestoreGeneralInfo()
        db.collection("general_info").document("main").set(genInfo.toMap()).await()
        count++

        count
    }

    // ADMIN MANAGEMENT
    suspend fun checkIsAdmin(uid: String, email: String): Boolean = runCatching {
        val trimmedEmail = email.trim().lowercase()
        val isPrimary = trimmedEmail == "ehmedovramin646@gmail.com" || trimmedEmail == "ehmedovramin646@googlemail.com"
        if (isPrimary) {
            // Ensure admin record is seeded in admins/{uid} with matching real Firebase UID
            ensureAdminRecord(uid, trimmedEmail)
            return true
        }

        // Check document at admins/{uid}
        val doc = db.collection("admins").document(uid).get().await()
        if (doc.exists() && doc.getString("role") == "admin") {
            return true
        }

        // Check by email in admins collection
        if (trimmedEmail.isNotEmpty()) {
            val query = db.collection("admins")
                .whereEqualTo("email", trimmedEmail)
                .whereEqualTo("role", "admin")
                .limit(1)
                .get()
                .await()
            if (!query.isEmpty) {
                ensureAdminRecord(uid, trimmedEmail)
                return true
            }
        }

        false
    }.getOrDefault(false)

    suspend fun ensureAdminRecord(uid: String, email: String): Result<Unit> = runCatching {
        val docRef = db.collection("admins").document(uid)
        val doc = docRef.get().await()
        if (!doc.exists()) {
            val now = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US).format(java.util.Date())
            val adminUser = FirestoreAdminUser(
                uid = uid,
                email = email.trim(),
                role = "admin",
                createdAt = now
            )
            docRef.set(adminUser.toMap()).await()
        }
        Unit
    }.onFailure { if (it is Exception) handleFirestoreError(it, OperationType.WRITE, "admins/$uid") }
}
