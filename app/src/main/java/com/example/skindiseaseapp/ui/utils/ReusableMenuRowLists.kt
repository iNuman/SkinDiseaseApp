package com.oguzdogdu.walliescompose.util

import com.example.skindiseaseapp.navigation.utils.SkinDiseaseAppIcons
import com.example.skindiseaseapp.ui.util.ListItem
import com.example.skindiseaseapp.R

object ReusableMenuRowLists {
    val newList = listOf(
        ListItem.Header(R.string.settings_general_header),
        ListItem.Content(
            0, description = R.string.theme_text, icon = SkinDiseaseAppIcons.DarkMode, arrow = true
        ),

        ListItem.Header(R.string.settings_storage_header),
        ListItem.Content(
            1, R.string.clear_cache_title,icon = SkinDiseaseAppIcons.Cache, arrow = false
        ),
    )
}