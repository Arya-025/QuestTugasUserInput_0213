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
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(
                            start = dimensionResource(R.dimen.card_padding_horizontal),
                            end = dimensionResource(R.dimen.card_padding_horizontal),
                            top = dimensionResource(R.dimen.card_padding_top),
                            bottom = dimensionResource(R.dimen.card_padding_bottom)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.form_title),
                        color = colorResource(R.color.purple_title),
                        fontSize = sizeSp(R.dimen.title_size),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(dimensionResource(R.dimen.space_small)))
                    Text(
                        text = stringResource(R.string.form_subtitle),
                        color = colorResource(R.color.gray_subtitle),
                        fontSize = sizeSp(R.dimen.subtitle_size),
                        letterSpacing = sizeSp(R.dimen.text_letter_spacing_subtitle),
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(dimensionResource(R.dimen.space_xlarge)))

                    // Nama depan & belakang
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_medium))
                    ) {
                        OutlinedTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            placeholder = { Text(stringResource(R.string.hint_first_name), style = fieldTextStyle) },
                            singleLine = true,
                            shape = fieldShape,
                            colors = fieldColors,
                            textStyle = fieldTextStyle,
                            modifier = Modifier
                                .weight(1f)
                                .height(dimensionResource(R.dimen.field_height))
                        )
                        OutlinedTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            placeholder = { Text(stringResource(R.string.hint_last_name), style = fieldTextStyle) },
                            singleLine = true,
                            shape = fieldShape,
                            colors = fieldColors,
                            textStyle = fieldTextStyle,
                            modifier = Modifier
                                .weight(1f)
                                .height(dimensionResource(R.dimen.field_height))
                        )
                    }

                    Spacer(Modifier.height(dimensionResource(R.dimen.space_large)))

                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text(stringResource(R.string.hint_email), style = fieldTextStyle) },
                        singleLine = true,
                        shape = fieldShape,
                        colors = fieldColors,
                        textStyle = fieldTextStyle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(R.dimen.field_height))
                    )

                    Spacer(Modifier.height(dimensionResource(R.dimen.space_large)))

                    // Tgl lahir & Experience
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_medium))
                    ) {
                        // Tanggal lahir
                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = birthDate,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text(stringResource(R.string.hint_birth_date), style = fieldTextStyle) },
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(android.R.drawable.ic_menu_my_calendar),
                                        contentDescription = stringResource(R.string.cd_pick_date),
                                        tint = colorResource(R.color.gray_icon)
                                    )
                                },
                                singleLine = true,
                                shape = fieldShape,
                                colors = fieldColors,
                                textStyle = fieldTextStyle,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(dimensionResource(R.dimen.field_height))
                            )
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clickable { showDatePicker = true }
                            )
                        }

                        // Experience dropdown
                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = experience,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text(stringResource(R.string.hint_experience), style = fieldTextStyle) },
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(android.R.drawable.arrow_down_float),
                                        contentDescription = stringResource(R.string.cd_pick_experience),
                                        tint = colorResource(R.color.gray_icon)
                                    )
                                },
                                singleLine = true,
                                shape = fieldShape,
                                colors = fieldColors,
                                textStyle = fieldTextStyle,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(dimensionResource(R.dimen.field_height))
                            )
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clickable { expandedExperience = true }
                            )
                            DropdownMenu(
                                expanded = expandedExperience,
                                onDismissRequest = { expandedExperience = false }
                            ) {
                                experienceOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            experience = option
                                            expandedExperience = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(dimensionResource(R.dimen.space_large)))

/** Mengubah dimen (sp) dari resource menjadi TextUnit. */
@Composable

private fun sizeSp(id: Int): TextUnit = dimensionResource(id).value.sp