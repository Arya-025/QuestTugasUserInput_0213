package com.example.tugas_userinput
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiodataScreen() {
    val context = LocalContext.current

    // ---------- State ----------
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var birthDate by rememberSaveable { mutableStateOf("") }
    var experience by rememberSaveable { mutableStateOf("") }
    val selectedSkills = remember { mutableStateListOf<Int>() }

    var showDatePicker by remember { mutableStateOf(false) }
    var expandedExperience by remember { mutableStateOf(false) }

    // ---------- Resources ----------
    val experienceOptions = stringArrayResource(R.array.experience_options)
    val skillOptions = stringArrayResource(R.array.skill_options)
    val successMessage = stringResource(R.string.msg_submit_success)
    val datePattern = stringResource(R.string.date_format)

    val isFormValid = firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            email.isNotBlank() &&
            birthDate.isNotBlank() &&
            experience.isNotBlank() &&
            selectedSkills.isNotEmpty()

    val cardShape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius))
    val fieldShape = RoundedCornerShape(dimensionResource(R.dimen.field_corner_radius))
    val fieldTextStyle = TextStyle(
        fontSize = sizeSp(R.dimen.field_text_size),
        color = colorResource(R.color.gray_hint)
    )
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(R.color.purple_primary),
        unfocusedBorderColor = colorResource(R.color.gray_text_field_border),
        focusedTextColor = colorResource(R.color.text_label),
        unfocusedTextColor = colorResource(R.color.text_label),
        cursorColor = colorResource(R.color.purple_primary)
    )

    // ---------- UI ----------
    Scaffold(
        containerColor = colorResource(R.color.screen_background)
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Layer 1: gambar header + indikator
            HeaderPager(modifier = Modifier.fillMaxSize())

            // Layer 2: Box form yang MENGAPUNG di atas Scaffold
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = dimensionResource(R.dimen.card_bottom_offset))
                    .fillMaxWidth()
                    .shadow(
                        elevation = dimensionResource(R.dimen.card_elevation),
                        shape = cardShape,
                        ambientColor = colorResource(R.color.card_shadow),
                        spotColor = colorResource(R.color.card_shadow)
                    )
                    .clip(cardShape)
                    .background(colorResource(R.color.form_white))
                    .imePadding()
            )