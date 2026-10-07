package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MunicipalDataProvider
import com.example.data.firestore.FirestoreAnnouncement
import com.example.data.firestore.FirestoreGeneralInfo
import com.example.data.firestore.FirestoreHotline
import com.example.data.firestore.FirestoreNews
import com.example.data.firestore.FirestoreReception
import com.example.data.firestore.FirestoreSocialMedia
import com.example.data.firestore.MunicipalFirestoreRepository
import com.example.model.AnnouncementItem
import com.example.model.Appeal
import com.example.model.CivicSurvey
import com.example.model.NewsItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

enum class BottomNavTab {
    HOME,
    NEWS,
    APPEALS,
    CONTACT,
    MENU
}

sealed interface AppScreen {
    object Main : AppScreen
    data class NewsDetail(val newsId: String) : AppScreen
    data class AnnouncementDetail(val announcementId: String) : AppScreen
    object NewAppealForm : AppScreen
    data class AppealDetail(val appealId: String) : AppScreen
    object TaxesAndPayments : AppScreen
    object AboutMunicipality : AppScreen
    object Hotlines : AppScreen
    object SocialMedia : AppScreen
    object Documents : AppScreen
    object CivicSurveyScreen : AppScreen
    object AdminLogin : AppScreen
    object AdminDashboard : AppScreen
}

data class UiNotification(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val isRead: Boolean = false
)

data class MunicipalUiState(
    val currentTab: BottomNavTab = BottomNavTab.HOME,
    val currentScreen: AppScreen = AppScreen.Main,
    val screenStack: List<AppScreen> = listOf(AppScreen.Main),
    val newsList: List<NewsItem> = MunicipalDataProvider.newsList,
    val announcementsList: List<AnnouncementItem> = MunicipalDataProvider.announcementsList,
    val appealsList: List<Appeal> = MunicipalDataProvider.initialAppeals,
    val survey: CivicSurvey = MunicipalDataProvider.defaultSurvey,
    val newsSearchQuery: String = "",
    val selectedNewsCategory: String = "Hamısı",
    val trackingSearchQuery: String = "",
    val trackedAppeal: Appeal? = null,
    val trackedAppealNotFound: Boolean = false,
    val showNotificationsDialog: Boolean = false,
    val notifications: List<UiNotification> = listOf(
        UiNotification(
            id = "notif_1",
            title = "Ağacəkmə İməciliyi",
            message = "Mingəçevir şəhərində Kür sahili boyu kütləvi iməcilik aksiyası baş tutacaq.",
            time = "Bu gün, 10:00"
        ),
        UiNotification(
            id = "notif_2",
            title = "Bələdiyyə Şurasının Qərarı",
            message = "2026-cı il abadlıq büdcəsinin layihəsi ictimai dinləməyə təqdim edildi.",
            time = "Dünən, 16:30"
        )
    ),
    // Tax Calculator
    val propertyAreaSqM: String = "85",
    val propertyIsCommercial: Boolean = false,
    val calculatedPropertyTax: Double = 11.0, // (85 - 30) * 0.20 AZN
    val landAreaSot: String = "6",
    val calculatedLandTax: Double = 3.60, // 6 * 0.60 AZN
    // Tab and filter selections
    val selectedNewsTab: Int = 0,
    val selectedAppealsTab: Int = 0,
    val appealsFilterStatus: String = "Hamısı",
    val announcementsFilterStatus: String = "Hamısı",
    val selectedDocumentForView: com.example.model.MunicipalDocument? = null,
    // New Appeal Form State
    val formApplicantName: String = "",
    val formFinCode: String = "",
    val formPhone: String = "",
    val formAddress: String = "",
    val formCategory: String = "Abadlaşdırma",
    val formSubject: String = "",
    val formDescription: String = "",
    val formSubmittedSuccessCode: String? = null,
    val formError: String? = null,
    val isAdminLoggedIn: Boolean = false,
    val adminEmail: String? = null
)

class MunicipalViewModel(
    private val repository: MunicipalFirestoreRepository? = try {
        MunicipalFirestoreRepository("ai-studio-android-ramin-757d221e-3144-42cb-a0c8-fc52d2b6ce0e")
    } catch (e: Exception) {
        null
    }
) : androidx.lifecycle.ViewModel() {

    val firestoreNews: StateFlow<List<FirestoreNews>> = repository?.observeNews()
        ?.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        ?: MutableStateFlow(emptyList())

    val firestoreAnnouncements: StateFlow<List<FirestoreAnnouncement>> = repository?.observeAnnouncements()
        ?.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        ?: MutableStateFlow(emptyList())

    val firestoreHotlines: StateFlow<List<FirestoreHotline>> = repository?.observeHotlines()
        ?.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        ?: MutableStateFlow(emptyList())

    val firestoreSocialMedia: StateFlow<List<FirestoreSocialMedia>> = repository?.observeSocialMedia()
        ?.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        ?: MutableStateFlow(emptyList())

    val firestoreReceptions: StateFlow<List<FirestoreReception>> = repository?.observeReceptions()
        ?.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        ?: MutableStateFlow(emptyList())

    val firestoreGeneralInfo: StateFlow<FirestoreGeneralInfo?> = repository?.observeGeneralInfo()
        ?.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
        ?: MutableStateFlow(null)

    private val _uiState = MutableStateFlow(MunicipalUiState())
    val uiState: StateFlow<MunicipalUiState> = _uiState.asStateFlow()

    init {
        repository?.let {
            viewModelScope.launch {
                firestoreNews.collect { list ->
                    if (list.isNotEmpty()) {
                        _uiState.value = _uiState.value.copy(
                            newsList = list.filter { it.isActive }.map { it.toNewsItem() }
                        )
                    }
                }
            }
            viewModelScope.launch {
                firestoreAnnouncements.collect { list ->
                    if (list.isNotEmpty()) {
                        _uiState.value = _uiState.value.copy(
                            announcementsList = list.filter { it.isActive }.map { it.toAnnouncementItem() }
                        )
                    }
                }
            }
        }
    }

    fun setAdminLoggedIn(loggedIn: Boolean, email: String? = null) {
        _uiState.value = _uiState.value.copy(isAdminLoggedIn = loggedIn, adminEmail = email)
    }

    suspend fun saveNews(news: FirestoreNews) = repository?.saveNews(news) ?: Result.success(Unit)
    suspend fun deleteNews(id: String) = repository?.deleteNews(id) ?: Result.success(Unit)
    suspend fun saveAnnouncement(ann: FirestoreAnnouncement) = repository?.saveAnnouncement(ann) ?: Result.success(Unit)
    suspend fun deleteAnnouncement(id: String) = repository?.deleteAnnouncement(id) ?: Result.success(Unit)
    suspend fun saveHotline(hotline: FirestoreHotline) = repository?.saveHotline(hotline) ?: Result.success(Unit)
    suspend fun deleteHotline(id: String) = repository?.deleteHotline(id) ?: Result.success(Unit)
    suspend fun saveSocialMedia(social: FirestoreSocialMedia) = repository?.saveSocialMedia(social) ?: Result.success(Unit)
    suspend fun deleteSocialMedia(id: String) = repository?.deleteSocialMedia(id) ?: Result.success(Unit)
    suspend fun saveReception(reception: FirestoreReception) = repository?.saveReception(reception) ?: Result.success(Unit)
    suspend fun deleteReception(id: String) = repository?.deleteReception(id) ?: Result.success(Unit)
    suspend fun saveGeneralInfo(info: FirestoreGeneralInfo) = repository?.saveGeneralInfo(info) ?: Result.success(Unit)
    suspend fun seedFirestoreInitialData() = repository?.seedInitialData() ?: Result.success(0)

    fun selectTab(tab: BottomNavTab) {
        _uiState.value = _uiState.value.copy(
            currentTab = tab,
            currentScreen = AppScreen.Main,
            screenStack = listOf(AppScreen.Main)
        )
    }

    fun navigateTo(screen: AppScreen) {
        val updatedStack = _uiState.value.screenStack + screen
        val updatedAppealsTab = if (screen is AppScreen.NewAppealForm) 1 else _uiState.value.selectedAppealsTab
        _uiState.value = _uiState.value.copy(
            currentScreen = screen,
            screenStack = updatedStack,
            selectedAppealsTab = updatedAppealsTab
        )
    }

    fun navigateBack(): Boolean {
        val currentStack = _uiState.value.screenStack
        if (currentStack.size > 1) {
            val updatedStack = currentStack.dropLast(1)
            val previousScreen = updatedStack.last()
            _uiState.value = _uiState.value.copy(
                currentScreen = previousScreen,
                screenStack = updatedStack
            )
            return true
        } else if (_uiState.value.currentTab != BottomNavTab.HOME) {
            _uiState.value = _uiState.value.copy(
                currentTab = BottomNavTab.HOME,
                currentScreen = AppScreen.Main,
                screenStack = listOf(AppScreen.Main)
            )
            return true
        }
        return false
    }

    fun setSelectedNewsTab(tabIndex: Int) {
        _uiState.value = _uiState.value.copy(selectedNewsTab = tabIndex)
    }

    fun setSelectedAppealsTab(tabIndex: Int) {
        _uiState.value = _uiState.value.copy(selectedAppealsTab = tabIndex)
    }

    fun setAppealsFilterStatus(status: String) {
        _uiState.value = _uiState.value.copy(appealsFilterStatus = status)
    }

    fun setAnnouncementsFilterStatus(status: String) {
        _uiState.value = _uiState.value.copy(announcementsFilterStatus = status)
    }

    fun setSelectedDocument(doc: com.example.model.MunicipalDocument?) {
        _uiState.value = _uiState.value.copy(selectedDocumentForView = doc)
    }

    fun setNewsSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(newsSearchQuery = query)
    }

    fun setNewsCategory(category: String) {
        _uiState.value = _uiState.value.copy(selectedNewsCategory = category)
    }

    fun setTrackingSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(
            trackingSearchQuery = query,
            trackedAppealNotFound = false
        )
    }

    fun trackAppeal() {
        val query = _uiState.value.trackingSearchQuery.trim()
        if (query.isEmpty()) return

        val found = _uiState.value.appealsList.firstOrNull {
            it.trackingCode.equals(query, ignoreCase = true) ||
                    it.finCode.equals(query, ignoreCase = true)
        }

        _uiState.value = _uiState.value.copy(
            trackedAppeal = found,
            trackedAppealNotFound = found == null
        )
    }

    fun clearTrackedAppeal() {
        _uiState.value = _uiState.value.copy(
            trackedAppeal = null,
            trackedAppealNotFound = false
        )
    }

    // Tax calculation
    fun setPropertyIsCommercial(isCommercial: Boolean) {
        val area = _uiState.value.propertyAreaSqM.toDoubleOrNull() ?: 0.0
        val tax = if (isCommercial) {
            area * 0.40
        } else {
            val taxableArea = (area - 30.0).coerceAtLeast(0.0)
            taxableArea * 0.20
        }
        _uiState.value = _uiState.value.copy(
            propertyIsCommercial = isCommercial,
            calculatedPropertyTax = String.format(Locale.US, "%.2f", tax).toDouble()
        )
    }

    fun updatePropertyArea(areaStr: String) {
        val area = areaStr.toDoubleOrNull() ?: 0.0
        val isCommercial = _uiState.value.propertyIsCommercial
        val tax = if (isCommercial) {
            area * 0.40
        } else {
            val taxableArea = (area - 30.0).coerceAtLeast(0.0)
            taxableArea * 0.20
        }
        _uiState.value = _uiState.value.copy(
            propertyAreaSqM = areaStr,
            calculatedPropertyTax = String.format(Locale.US, "%.2f", tax).toDouble()
        )
    }

    fun updateLandArea(sotStr: String) {
        val sot = sotStr.toDoubleOrNull() ?: 0.0
        // Rate for residential land per sot: 0.60 AZN
        val tax = sot * 0.60
        _uiState.value = _uiState.value.copy(
            landAreaSot = sotStr,
            calculatedLandTax = String.format(Locale.US, "%.2f", tax).toDouble()
        )
    }

    // New Appeal Form handlers
    fun updateFormField(
        name: String? = null,
        fin: String? = null,
        phone: String? = null,
        address: String? = null,
        category: String? = null,
        subject: String? = null,
        desc: String? = null
    ) {
        _uiState.value = _uiState.value.copy(
            formApplicantName = name ?: _uiState.value.formApplicantName,
            formFinCode = fin?.uppercase() ?: _uiState.value.formFinCode,
            formPhone = phone ?: _uiState.value.formPhone,
            formAddress = address ?: _uiState.value.formAddress,
            formCategory = category ?: _uiState.value.formCategory,
            formSubject = subject ?: _uiState.value.formSubject,
            formDescription = desc ?: _uiState.value.formDescription,
            formError = null
        )
    }

    fun submitAppeal(): Boolean {
        val state = _uiState.value
        if (state.formApplicantName.isBlank()) {
            _uiState.value = _uiState.value.copy(formError = "Zəhmət olmasa ad və soyadınızı qeyd edin.")
            return false
        }
        if (state.formPhone.isBlank()) {
            _uiState.value = _uiState.value.copy(formError = "Əlaqə nömrəsi daxil edilməlidir.")
            return false
        }
        if (state.formSubject.isBlank() || state.formDescription.isBlank()) {
            _uiState.value = _uiState.value.copy(formError = "Müraciətin mövzu və məzmununu qeyd edin.")
            return false
        }

        val randomNum = Random.nextInt(1000, 9999)
        val trackingCode = "MNG-2026-$randomNum"
        val currentDate = SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("az")).format(Date())

        val newAppeal = Appeal(
            id = "app_${System.currentTimeMillis()}",
            trackingCode = trackingCode,
            applicantName = state.formApplicantName.trim(),
            finCode = state.formFinCode.trim().ifEmpty { "TƏYİN EDİLMƏYİB" },
            phone = state.formPhone.trim(),
            address = state.formAddress.trim().ifEmpty { "Mingəçevir şəhəri" },
            category = state.formCategory,
            subject = state.formSubject.trim(),
            description = state.formDescription.trim(),
            date = currentDate,
            status = "Göndərildi",
            response = null
        )

        _uiState.value = _uiState.value.copy(
            appealsList = listOf(newAppeal) + state.appealsList,
            formSubmittedSuccessCode = trackingCode,
            formApplicantName = "",
            formFinCode = "",
            formPhone = "",
            formAddress = "",
            formSubject = "",
            formDescription = "",
            formError = null
        )
        return true
    }

    fun dismissSuccessCode() {
        _uiState.value = _uiState.value.copy(formSubmittedSuccessCode = null)
    }

    fun voteInSurvey(optionId: String) {
        val curSurvey = _uiState.value.survey
        if (curSurvey.userVotedOptionId != null) return // Already voted

        val updatedOptions = curSurvey.options.map {
            if (it.id == optionId) it.copy(votes = it.votes + 1) else it
        }

        _uiState.value = _uiState.value.copy(
            survey = curSurvey.copy(
                options = updatedOptions,
                totalVotes = curSurvey.totalVotes + 1,
                userVotedOptionId = optionId
            )
        )
    }

    fun toggleNotificationsDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(showNotificationsDialog = show)
    }
}
