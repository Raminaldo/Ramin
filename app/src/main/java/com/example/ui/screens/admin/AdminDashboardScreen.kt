package com.example.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.firestore.FirestoreAnnouncement
import com.example.data.firestore.FirestoreGeneralInfo
import com.example.data.firestore.FirestoreHotline
import com.example.data.firestore.FirestoreNews
import com.example.data.firestore.FirestoreReception
import com.example.data.firestore.FirestoreSocialMedia
import com.example.viewmodel.MunicipalViewModel
import kotlinx.coroutines.launch

enum class AdminSection(val title: String) {
    DASHBOARD("Dashboard"),
    SOCIAL("Sosial Media"),
    HOTLINES("Qaynar Xətt"),
    NEWS("Xəbərlər"),
    RECEPTIONS("Görüşlər"),
    ANNOUNCEMENTS("Elanlar"),
    GENERAL_INFO("Ümumi Məlumatlar")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    viewModel: MunicipalViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSectionIndex by remember { mutableIntStateOf(0) }
    val sections = AdminSection.values()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Dialog states for Add/Edit
    var showNewsDialog by remember { mutableStateOf<FirestoreNews?>(null) }
    var showAnnDialog by remember { mutableStateOf<FirestoreAnnouncement?>(null) }
    var showHotlineDialog by remember { mutableStateOf<FirestoreHotline?>(null) }
    var showSocialDialog by remember { mutableStateOf<FirestoreSocialMedia?>(null) }
    var showReceptionDialog by remember { mutableStateOf<FirestoreReception?>(null) }

    val newsList by viewModel.firestoreNews.collectAsStateWithLifecycle()
    val annList by viewModel.firestoreAnnouncements.collectAsStateWithLifecycle()
    val hotlinesList by viewModel.firestoreHotlines.collectAsStateWithLifecycle()
    val socialList by viewModel.firestoreSocialMedia.collectAsStateWithLifecycle()
    val receptionList by viewModel.firestoreReceptions.collectAsStateWithLifecycle()
    val genInfo by viewModel.firestoreGeneralInfo.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Admin İdarəetmə Paneli", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            "Mingəçevir Bələdiyyəsi • Dinamik Məzmun",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                        viewModel.setAdminLoggedIn(false, null)
                        onBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Çıxış", tint = MaterialTheme.colorScheme.error)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            if (selectedSectionIndex != 0 && selectedSectionIndex != 6) {
                FloatingActionButton(
                    onClick = {
                        when (sections[selectedSectionIndex]) {
                            AdminSection.NEWS -> showNewsDialog = FirestoreNews()
                            AdminSection.ANNOUNCEMENTS -> showAnnDialog = FirestoreAnnouncement()
                            AdminSection.HOTLINES -> showHotlineDialog = FirestoreHotline()
                            AdminSection.SOCIAL -> showSocialDialog = FirestoreSocialMedia()
                            AdminSection.RECEPTIONS -> showReceptionDialog = FirestoreReception()
                            else -> {}
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.testTag("admin_fab_add")
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Əlavə et")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Scrollable Section Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedSectionIndex,
                edgePadding = 16.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                sections.forEachIndexed { index, section ->
                    Tab(
                        selected = selectedSectionIndex == index,
                        onClick = { selectedSectionIndex = index },
                        text = { Text(section.title, fontWeight = if (selectedSectionIndex == index) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                when (sections[selectedSectionIndex]) {
                    AdminSection.DASHBOARD -> AdminOverviewDashboard(
                        newsCount = newsList.size,
                        annCount = annList.size,
                        hotlineCount = hotlinesList.size,
                        socialCount = socialList.size,
                        receptionCount = receptionList.size,
                        onSeedData = {
                            scope.launch {
                                val res = viewModel.seedFirestoreInitialData()
                                if (res.isSuccess) {
                                    snackbarHostState.showSnackbar("İlkin məlumatlar bazaya uğurla yükləndi (${res.getOrNull()} element)!")
                                } else {
                                    snackbarHostState.showSnackbar("Xəta: ${res.exceptionOrNull()?.message}")
                                }
                            }
                        }
                    )
                    AdminSection.SOCIAL -> AdminSocialMediaList(
                        items = socialList,
                        onEdit = { showSocialDialog = it },
                        onDelete = {
                            scope.launch {
                                viewModel.deleteSocialMedia(it.id)
                                snackbarHostState.showSnackbar("Sosial media hesabı silindi")
                            }
                        }
                    )
                    AdminSection.HOTLINES -> AdminHotlinesList(
                        items = hotlinesList,
                        onEdit = { showHotlineDialog = it },
                        onDelete = {
                            scope.launch {
                                viewModel.deleteHotline(it.id)
                                snackbarHostState.showSnackbar("Qaynar xətt silindi")
                            }
                        }
                    )
                    AdminSection.NEWS -> AdminNewsList(
                        items = newsList,
                        onEdit = { showNewsDialog = it },
                        onDelete = {
                            scope.launch {
                                viewModel.deleteNews(it.id)
                                snackbarHostState.showSnackbar("Xəbər silindi")
                            }
                        }
                    )
                    AdminSection.RECEPTIONS -> AdminReceptionsList(
                        items = receptionList,
                        onEdit = { showReceptionDialog = it },
                        onDelete = {
                            scope.launch {
                                viewModel.deleteReception(it.id)
                                snackbarHostState.showSnackbar("Qəbul qrafiki silindi")
                            }
                        }
                    )
                    AdminSection.ANNOUNCEMENTS -> AdminAnnouncementsList(
                        items = annList,
                        onEdit = { showAnnDialog = it },
                        onDelete = {
                            scope.launch {
                                viewModel.deleteAnnouncement(it.id)
                                snackbarHostState.showSnackbar("Elan silindi")
                            }
                        }
                    )
                    AdminSection.GENERAL_INFO -> AdminGeneralInfoForm(
                        info = genInfo ?: FirestoreGeneralInfo(),
                        onSave = { updated ->
                            scope.launch {
                                viewModel.saveGeneralInfo(updated)
                                snackbarHostState.showSnackbar("Ümumi bələdiyyə məlumatları yeniləndi!")
                            }
                        }
                    )
                }
            }
        }
    }

    // Dialogs
    showNewsDialog?.let { item ->
        NewsEditorDialog(
            initial = item,
            onDismiss = { showNewsDialog = null },
            onSave = { updated ->
                scope.launch {
                    viewModel.saveNews(updated)
                    snackbarHostState.showSnackbar("Xəbər yadda saxlanıldı!")
                    showNewsDialog = null
                }
            }
        )
    }

    showAnnDialog?.let { item ->
        AnnouncementEditorDialog(
            initial = item,
            onDismiss = { showAnnDialog = null },
            onSave = { updated ->
                scope.launch {
                    viewModel.saveAnnouncement(updated)
                    snackbarHostState.showSnackbar("Elan yadda saxlanıldı!")
                    showAnnDialog = null
                }
            }
        )
    }

    showHotlineDialog?.let { item ->
        HotlineEditorDialog(
            initial = item,
            onDismiss = { showHotlineDialog = null },
            onSave = { updated ->
                scope.launch {
                    viewModel.saveHotline(updated)
                    snackbarHostState.showSnackbar("Qaynar xətt yadda saxlanıldı!")
                    showHotlineDialog = null
                }
            }
        )
    }

    showSocialDialog?.let { item ->
        SocialEditorDialog(
            initial = item,
            onDismiss = { showSocialDialog = null },
            onSave = { updated ->
                scope.launch {
                    viewModel.saveSocialMedia(updated)
                    snackbarHostState.showSnackbar("Sosial media hesabı yadda saxlanıldı!")
                    showSocialDialog = null
                }
            }
        )
    }

    showReceptionDialog?.let { item ->
        ReceptionEditorDialog(
            initial = item,
            onDismiss = { showReceptionDialog = null },
            onSave = { updated ->
                scope.launch {
                    viewModel.saveReception(updated)
                    snackbarHostState.showSnackbar("Görüş qrafiki yadda saxlanıldı!")
                    showReceptionDialog = null
                }
            }
        )
    }
}

@Composable
fun AdminOverviewDashboard(
    newsCount: Int,
    annCount: Int,
    hotlineCount: Int,
    socialCount: Int,
    receptionCount: Int,
    onSeedData: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CloudDone, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Bulud Məlumat Bazası Aktivdir",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "Burada etdiyiniz bütün əlavələr və düzəlişlər birbaşa vətəndaşların telefonundakı tətbiqdə APK yeniləməsi tələb olunmadan əks olunur.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Sistem İcmalı və Saylar", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            DashboardMetricCard(title = "Xəbərlər", count = newsCount, modifier = Modifier.weight(1f))
            DashboardMetricCard(title = "Elanlar", count = annCount, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            DashboardMetricCard(title = "Qaynar Xətlər", count = hotlineCount, modifier = Modifier.weight(1f))
            DashboardMetricCard(title = "Sosial Şəbəkə", count = socialCount, modifier = Modifier.weight(1f))
            DashboardMetricCard(title = "Görüşlər", count = receptionCount, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Baza İlkin Sinxronizasiyası", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "Əgər bulud bazası boşdursa, aşağıdakı düymə ilə tətbiqin mövcud bütün xəbərlərini, qaynar xətlərini və rəsmi məlumatlarını bazaya köçürə bilərsiniz.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = onSeedData,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("İlkin Məlumatları Bazaya Yüklə")
                }
            }
        }
    }
}

@Composable
fun DashboardMetricCard(title: String, count: Int, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$count", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
        }
    }
}

// 1. NEWS LIST
@Composable
fun AdminNewsList(
    items: List<FirestoreNews>,
    onEdit: (FirestoreNews) -> Unit,
    onDelete: (FirestoreNews) -> Unit
) {
    if (items.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Xəbər tapılmadı. Yeni xəbər əlavə etmək üçün + düyməsini sıxın.", color = MaterialTheme.colorScheme.outline)
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.title, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text("${item.category} • ${item.date}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(item.summary, style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                        IconButton(onClick = { onEdit(item) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Düzəlt", tint = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { onDelete(item) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Sil", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

// 2. HOTLINES LIST
@Composable
fun AdminHotlinesList(
    items: List<FirestoreHotline>,
    onEdit: (FirestoreHotline) -> Unit,
    onDelete: (FirestoreHotline) -> Unit
) {
    if (items.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Qaynar xətt tapılmadı. + düyməsi ilə əlavə edin.", color = MaterialTheme.colorScheme.outline)
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.title, fontWeight = FontWeight.Bold)
                            Text("Nömrə: ${item.number} • ${item.category}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                            item.description?.let {
                                Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        IconButton(onClick = { onEdit(item) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Düzəlt", tint = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { onDelete(item) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Sil", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

// 3. SOCIAL MEDIA LIST
@Composable
fun AdminSocialMediaList(
    items: List<FirestoreSocialMedia>,
    onEdit: (FirestoreSocialMedia) -> Unit,
    onDelete: (FirestoreSocialMedia) -> Unit
) {
    if (items.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Sosial media hesabı tapılmadı.", color = MaterialTheme.colorScheme.outline)
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.title, fontWeight = FontWeight.Bold)
                            Text("Platforma: ${item.platform} • ${item.url}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { onEdit(item) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Düzəlt", tint = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { onDelete(item) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Sil", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

// 4. ANNOUNCEMENTS LIST
@Composable
fun AdminAnnouncementsList(
    items: List<FirestoreAnnouncement>,
    onEdit: (FirestoreAnnouncement) -> Unit,
    onDelete: (FirestoreAnnouncement) -> Unit
) {
    if (items.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Elan tapılmadı.", color = MaterialTheme.colorScheme.outline)
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.title, fontWeight = FontWeight.Bold)
                            Text("${item.type} • ${item.date}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(item.description, style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                        IconButton(onClick = { onEdit(item) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Düzəlt", tint = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { onDelete(item) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Sil", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

// 5. RECEPTIONS LIST
@Composable
fun AdminReceptionsList(
    items: List<FirestoreReception>,
    onEdit: (FirestoreReception) -> Unit,
    onDelete: (FirestoreReception) -> Unit
) {
    if (items.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Qəbul qrafiki tapılmadı.", color = MaterialTheme.colorScheme.outline)
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(items) { item ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.person, fontWeight = FontWeight.Bold)
                            Text(item.position, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                            Text("Qəbul vaxtı: ${item.schedule}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        IconButton(onClick = { onEdit(item) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Düzəlt", tint = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { onDelete(item) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Sil", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

// 6. GENERAL INFO FORM
@Composable
fun AdminGeneralInfoForm(
    info: FirestoreGeneralInfo,
    onSave: (FirestoreGeneralInfo) -> Unit
) {
    var title by remember(info) { mutableStateOf(info.title) }
    var aboutText by remember(info) { mutableStateOf(info.aboutText) }
    var address by remember(info) { mutableStateOf(info.address) }
    var phone by remember(info) { mutableStateOf(info.phone) }
    var email by remember(info) { mutableStateOf(info.email) }
    var workHours by remember(info) { mutableStateOf(info.workHours) }
    var chairman by remember(info) { mutableStateOf(info.chairman ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Qurumun Adı") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = chairman,
            onValueChange = { chairman = it },
            label = { Text("Bələdiyyə Sədri") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = aboutText,
            onValueChange = { aboutText = it },
            label = { Text("Bələdiyyə Haqqında Mətn") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("İnzibati Ünvan") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Əlaqə Telefonu") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Rəsmi E-poçt") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = workHours,
            onValueChange = { workHours = it },
            label = { Text("İş Rejimi / Saatları") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                onSave(
                    info.copy(
                        title = title,
                        aboutText = aboutText,
                        address = address,
                        phone = phone,
                        email = email,
                        workHours = workHours,
                        chairman = chairman.ifEmpty { null }
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Dəyişiklikləri Yadda Saxla")
        }
    }
}

// EDIT DIALOGS
@Composable
fun NewsEditorDialog(
    initial: FirestoreNews,
    onDismiss: () -> Unit,
    onSave: (FirestoreNews) -> Unit
) {
    var title by remember { mutableStateOf(initial.title) }
    var summary by remember { mutableStateOf(initial.summary) }
    var content by remember { mutableStateOf(initial.content) }
    var category by remember { mutableStateOf(initial.category) }
    var date by remember { mutableStateOf(initial.date.ifEmpty { "07 Oktyabr 2026" }) }
    var imageUrl by remember { mutableStateOf(initial.imageUrl ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initial.id.isEmpty()) "Yeni Xəbər Əlavə Et" else "Xəbəri Redaktə Et") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Başlıq") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Kateqoriya") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Tarix") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = summary, onValueChange = { summary = it }, label = { Text("Qısa Məzmun") }, minLines = 2, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Ətraflı Mətn") }, minLines = 4, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("Şəkil URL (istəyə görə)") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank()) {
                    onSave(initial.copy(
                        title = title.trim(),
                        category = category.trim(),
                        date = date.trim(),
                        summary = summary.trim(),
                        content = content.trim(),
                        imageUrl = imageUrl.trim().ifEmpty { null }
                    ))
                }
            }) { Text("Yadda saxla") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Ləğv et") }
        }
    )
}

@Composable
fun AnnouncementEditorDialog(
    initial: FirestoreAnnouncement,
    onDismiss: () -> Unit,
    onSave: (FirestoreAnnouncement) -> Unit
) {
    var title by remember { mutableStateOf(initial.title) }
    var description by remember { mutableStateOf(initial.description) }
    var type by remember { mutableStateOf(initial.type) }
    var priority by remember { mutableStateOf(initial.priority) }
    var date by remember { mutableStateOf(initial.date.ifEmpty { "07 Oktyabr 2026" }) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initial.id.isEmpty()) "Yeni Elan Əlavə Et" else "Elanı Redaktə Et") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Başlıq") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Elanın Növü") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = priority, onValueChange = { priority = it }, label = { Text("Prioritet (Normal / Yüksək)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Tarix") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Elanın Mətni") }, minLines = 3, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank()) {
                    onSave(initial.copy(
                        title = title.trim(),
                        type = type.trim(),
                        priority = priority.trim(),
                        date = date.trim(),
                        description = description.trim(),
                        fullText = description.trim()
                    ))
                }
            }) { Text("Yadda saxla") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Ləğv et") }
        }
    )
}

@Composable
fun HotlineEditorDialog(
    initial: FirestoreHotline,
    onDismiss: () -> Unit,
    onSave: (FirestoreHotline) -> Unit
) {
    var title by remember { mutableStateOf(initial.title) }
    var number by remember { mutableStateOf(initial.number) }
    var category by remember { mutableStateOf(initial.category) }
    var description by remember { mutableStateOf(initial.description ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initial.id.isEmpty()) "Yeni Qaynar Xətt Əlavə Et" else "Qaynar Xətti Redaktə Et") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Xidmətin Adı") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = number, onValueChange = { number = it }, label = { Text("Nömrə (məs: 164)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Kateqoriya (Bələdiyyə / Fövqəladə / Kommunal)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Qısa Açıqlama") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank() && number.isNotBlank()) {
                    onSave(initial.copy(
                        title = title.trim(),
                        number = number.trim(),
                        category = category.trim(),
                        description = description.trim().ifEmpty { null }
                    ))
                }
            }) { Text("Yadda saxla") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Ləğv et") }
        }
    )
}

@Composable
fun SocialEditorDialog(
    initial: FirestoreSocialMedia,
    onDismiss: () -> Unit,
    onSave: (FirestoreSocialMedia) -> Unit
) {
    var title by remember { mutableStateOf(initial.title) }
    var platform by remember { mutableStateOf(initial.platform) }
    var url by remember { mutableStateOf(initial.url) }
    var handle by remember { mutableStateOf(initial.handle ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initial.id.isEmpty()) "Yeni Sosial Media Hesabı" else "Hesabı Redaktə Et") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Başlıq (məs: Rəsmi Telegram)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = platform, onValueChange = { platform = it }, label = { Text("Platforma (facebook/instagram/telegram/youtube)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("Link URL") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = handle, onValueChange = { handle = it }, label = { Text("İstifadəçi adı / Handle") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank() && url.isNotBlank()) {
                    onSave(initial.copy(
                        title = title.trim(),
                        platform = platform.trim().lowercase(),
                        url = url.trim(),
                        handle = handle.trim().ifEmpty { null }
                    ))
                }
            }) { Text("Yadda saxla") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Ləğv et") }
        }
    )
}

@Composable
fun ReceptionEditorDialog(
    initial: FirestoreReception,
    onDismiss: () -> Unit,
    onSave: (FirestoreReception) -> Unit
) {
    var person by remember { mutableStateOf(initial.person) }
    var position by remember { mutableStateOf(initial.position) }
    var schedule by remember { mutableStateOf(initial.schedule) }
    var phone by remember { mutableStateOf(initial.phone) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initial.id.isEmpty()) "Yeni Görüş / Qəbul Saatı" else "Qəbulu Redaktə Et") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(value = person, onValueChange = { person = it }, label = { Text("Vəzifəli Şəxs") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = position, onValueChange = { position = it }, label = { Text("Vəzifəsi") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = schedule, onValueChange = { schedule = it }, label = { Text("Qəbul Günləri və Saatları") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Telefon") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(onClick = {
                if (person.isNotBlank()) {
                    onSave(initial.copy(
                        person = person.trim(),
                        title = person.trim(),
                        position = position.trim(),
                        schedule = schedule.trim(),
                        phone = phone.trim()
                    ))
                }
            }) { Text("Yadda saxla") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Ləğv et") }
        }
    )
}
