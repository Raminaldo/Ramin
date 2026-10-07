package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import com.example.data.MunicipalDataProvider
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Appeal
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MunicipalUiState
import com.example.viewmodel.MunicipalViewModel

@Composable
fun AppealsScreen(
    uiState: MunicipalUiState,
    viewModel: MunicipalViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedTab = uiState.selectedAppealsTab

    // Dialog for appeal submission success
    uiState.formSubmittedSuccessCode?.let { code ->
        AlertDialog(
            onDismissRequest = { viewModel.dismissSuccessCode() },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF137333),
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text(
                    text = "Müraciətiniz qəbul edildi!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = "Müraciətiniz qeydiyyata alındı və baxılması üçün Mingəçevir Bələdiyyəsinin aidiyyəti şöbəsinə yönləndirildi.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "İZLƏMƏ KODUNUZ:",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = code,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    letterSpacing = 1.2.sp
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Bu kod vasitəsilə müraciətinizin icra vəziyyətini istənilən vaxt yoxlaya bilərsiniz.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("İzləmə Kodu", code))
                        Toast.makeText(context, "İzləmə kodu kopyalandı", Toast.LENGTH_SHORT).show()
                        viewModel.dismissSuccessCode()
                        viewModel.setSelectedAppealsTab(0)
                    }
                ) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Kodu kopyala və tamamla")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissSuccessCode(); viewModel.setSelectedAppealsTab(0) }) {
                    Text("Bağla")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("appeals_screen")
    ) {
        if (uiState.currentScreen !is AppScreen.Main) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.navigateBack() },
                    modifier = Modifier.testTag("appeals_sub_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Geri",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = "Elektron Müraciət",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { viewModel.setSelectedAppealsTab(0) },
                text = { Text("Müraciətlər & İzləmə", fontWeight = FontWeight.SemiBold) },
                modifier = Modifier.testTag("tab_appeals_list")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { viewModel.setSelectedAppealsTab(1) },
                text = { Text("Yeni Müraciət", fontWeight = FontWeight.SemiBold) },
                modifier = Modifier.testTag("tab_appeals_new")
            )
        }

        if (selectedTab == 0) {
            AppealsListAndTrackingView(
                uiState = uiState,
                viewModel = viewModel,
                onNewAppealClick = { viewModel.setSelectedAppealsTab(1) }
            )
        } else {
            NewAppealFormView(
                uiState = uiState,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun AppealsListAndTrackingView(
    uiState: MunicipalUiState,
    viewModel: MunicipalViewModel,
    onNewAppealClick: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Civic info card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "'Vətəndaşların müraciətləri haqqında' AR Qanununa əsasən bütün müraciətlərə 15 iş günü ərzində baxılır və rəsmi cavab verilir.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            lineHeight = 18.sp
                        )
                    )
                }
            }
        }

        // Quick Tracking Box
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Müraciət Statusunu Yoxla",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "İzləmə kodunuzu (MNG-XXXX-XXXX) və ya FİN kodunuzu daxil edin:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = uiState.trackingSearchQuery,
                            onValueChange = { viewModel.setTrackingSearchQuery(it) },
                            placeholder = { Text("MNG-2026-8412") },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("appeal_tracking_search_input")
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { viewModel.trackAppeal() },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("appeal_tracking_search_button")
                        ) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Axtar")
                        }
                    }

                    if (uiState.trackedAppealNotFound) {
                        Text(
                            text = "Bu kod ilə müraciət tapılmadı. Zəhmət olmasa düzgünlüyünü yoxlayın.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    uiState.trackedAppeal?.let { tracked ->
                        Spacer(modifier = Modifier.height(12.dp))
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Axtarış Nəticəsi: ${tracked.trackingCode}",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                    IconButton(
                                        onClick = { viewModel.clearTrackedAppeal() },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Bağla",
                                            modifier = Modifier.size(16.dp),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        color = when (tracked.status) {
                                            "Tamamlandı" -> Color(0xFFE6F4EA)
                                            "İcradadır" -> Color(0xFFFEF3C7)
                                            else -> Color(0xFFE0F2FE)
                                        },
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = "Status: ${tracked.status}",
                                            color = when (tracked.status) {
                                                "Tamamlandı" -> Color(0xFF137333)
                                                "İcradadır" -> Color(0xFFB45309)
                                                else -> Color(0xFF0369A1)
                                            },
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Tarix: ${tracked.date}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = tracked.subject,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = tracked.description,
                                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface),
                                    modifier = Modifier.padding(top = 2.dp)
                                )

                                tracked.response?.let { resp ->
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Surface(
                                        color = Color(0xFFF0FDF4),
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(8.dp)) {
                                            Text(
                                                text = "Rəsmi Cavab:",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF166534)
                                                )
                                            )
                                            Text(
                                                text = resp,
                                                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF14532D))
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Action button to create new appeal
        item {
            Button(
                onClick = onNewAppealClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_start_new_appeal")
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Yeni Elektron Müraciət Göndər")
            }
        }

        item {
            val statusFilters = listOf("Hamısı", "Göndərildi", "İcradadır", "Tamamlandı")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MÜRACİƏTLƏRİN SİYAHISI",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.1.sp
                    )
                )
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(statusFilters) { status ->
                    val isSelected = uiState.appealsFilterStatus == status
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setAppealsFilterStatus(status) },
                        label = { Text(status, fontSize = 12.sp) },
                        shape = RoundedCornerShape(8.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        val filteredAppeals = uiState.appealsList.filter {
            uiState.appealsFilterStatus == "Hamısı" || it.status == uiState.appealsFilterStatus
        }

        if (filteredAppeals.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Bu statusda müraciət tapılmadı.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(filteredAppeals) { appeal ->
                AppealItemCard(appeal = appeal)
            }
        }

        // Citizen guidance & FAQs
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "MÜRACİƏT QAYDALARI VƏ SUALLAR",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.1.sp
                )
            )
        }

        items(MunicipalDataProvider.appealsFaqsList) { faq ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = faq.question,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = faq.answer,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun AppealItemCard(appeal: Appeal) {
    val context = LocalContext.current
    var expanded by remember { androidx.compose.runtime.mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .testTag("appeal_card_${appeal.trackingCode}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = appeal.trackingCode,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("İzləmə Kodu", appeal.trackingCode))
                            Toast.makeText(context, "${appeal.trackingCode} kopyalandı", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Kodu kopyala",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Surface(
                    color = when (appeal.status) {
                        "Tamamlandı" -> Color(0xFFE6F4EA)
                        "İcradadır" -> Color(0xFFFEF3C7)
                        else -> Color(0xFFE0F2FE)
                    },
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = appeal.status,
                        color = when (appeal.status) {
                            "Tamamlandı" -> Color(0xFF137333)
                            "İcradadır" -> Color(0xFFB45309)
                            else -> Color(0xFF0369A1)
                        },
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = appeal.subject,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )

            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sahə: ${appeal.category} • Tarix: ${appeal.date}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = "Vətəndaş: ${appeal.applicantName} (FİN: ${appeal.finCode})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Ünvan: ${appeal.address}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Məzmun:\n${appeal.description}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    appeal.response?.let { resp ->
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            color = Color(0xFFF0FDF4),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "Rəsmi Bələdiyyə Cavabı:",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF166534)
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = resp,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color(0xFF14532D)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewAppealFormView(
    uiState: MunicipalUiState,
    viewModel: MunicipalViewModel
) {
    val categories = listOf(
        "Abadlaşdırma",
        "Yol təmiri",
        "Məişət tullantıları",
        "Küçə işıqlandırılması",
        "Sosial yardım",
        "Digər"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("new_appeal_form_view")
    ) {
        Text(
            text = "Elektron Ərizə və Müraciət Forması",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = "Bütün sahələri diqqətlə doldurun. Müraciətiniz qeydə alınaraq sizə unikal izləmə kodu təqdim olunacaqdır.",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )

        uiState.formError?.let { err ->
            Surface(
                color = MaterialTheme.colorScheme.errorContainer,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = err,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

        // Form Fields
        OutlinedTextField(
            value = uiState.formApplicantName,
            onValueChange = { viewModel.updateFormField(name = it) },
            label = { Text("Ad, Soyad, Ata adı *") },
            placeholder = { Text("Məs: Məmmədov Rəşad Əli oğlu") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("form_input_name"),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = uiState.formFinCode,
                onValueChange = { if (it.length <= 7) viewModel.updateFormField(fin = it) },
                label = { Text("FİN Kod (7 simvol)") },
                placeholder = { Text("5KZ987A") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .testTag("form_input_fin"),
                shape = RoundedCornerShape(10.dp)
            )

            OutlinedTextField(
                value = uiState.formPhone,
                onValueChange = { viewModel.updateFormField(phone = it) },
                label = { Text("Əlaqə Nömrəsi *") },
                placeholder = { Text("+994 50 123 45 67") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .testTag("form_input_phone"),
                shape = RoundedCornerShape(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = uiState.formAddress,
            onValueChange = { viewModel.updateFormField(address = it) },
            label = { Text("Yaşayış Ünvanı (Küçə, bina, mənzil)") },
            placeholder = { Text("Məs: N.Nərimanov küçəsi, bina 12, m. 45") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("form_input_address"),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Müraciətin Sahəsi:",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(categories) { cat ->
                val isSelected = uiState.formCategory == cat
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.updateFormField(category = cat) },
                    label = { Text(cat, fontSize = 12.sp) },
                    shape = RoundedCornerShape(8.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = uiState.formSubject,
            onValueChange = { viewModel.updateFormField(subject = it) },
            label = { Text("Müraciətin Qısa Mövzusu *") },
            placeholder = { Text("Məs: Məhəllədəki işıq dirəyinin bərpası") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("form_input_subject"),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = uiState.formDescription,
            onValueChange = { viewModel.updateFormField(desc = it) },
            label = { Text("Müraciətin Ətraflı Məzmunu *") },
            placeholder = { Text("Xahiş və ya şikayətinizi aydın şəkildə qeyd edin...") },
            minLines = 4,
            maxLines = 8,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("form_input_desc"),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Optional photo/document attachment card
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Foto və ya sənəd əlavəsi",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Problem sahəsinin fotosu (Könüllü)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Könüllü",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { viewModel.submitAppeal() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("form_submit_button"),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Müraciəti Təsdiqlə və Göndər", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}
