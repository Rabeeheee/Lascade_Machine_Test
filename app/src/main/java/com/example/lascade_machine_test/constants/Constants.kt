package com.example.lascade_machine_test.components.bottom_sheet_components

import androidx.compose.ui.graphics.Color
import com.example.lascade_machine_test.R

object BottomSheetConstants {
    val PRIMARY_BACKGROUND = Color.Black
    val SECONDARY_BACKGROUND = Color(0xFF2B2C2F)
    val DRAG_HANDLE_COLOR = Color(0xFF35383F)
    val PRIMARY_TEXT = Color.White
    val SECONDARY_TEXT = Color.Gray
    val ACCENT_COLOR = Color(0xFFB97FFF)
    val ERROR_COLOR = Color.Red
    val SUCCESS_COLOR = Color.Green

    const val DRAG_HANDLE_WIDTH = 57
    const val DRAG_HANDLE_HEIGHT = 8
    const val BUTTON_HEIGHT = 60
    const val SMALL_BUTTON_WIDTH = 104
    const val ICON_BUTTON_SIZE = 48
    const val ICON_SIZE = 24
    const val CORNER_RADIUS = 30

    const val MAX_DRAG_OFFSET = 400f
    const val DRAG_MULTIPLIER = 0.4f
    const val DRAG_DISMISS_THRESHOLD = 150f
    const val AUTO_DISMISS_DELAY = 2000L

    val SAMPLE_ROUTES = listOf(
        "Kochi - Los Angeles",
        "Dubai trip",
        "Kochi - Los Angeles"
    )

    val SAMPLE_LOCATIONS = listOf(
        "New York" to "New York, United States",
        "London" to "London, United Kingdom",
        "Heathrow LHR" to "London, United Kingdom",
        "Stansted STN" to "London, United Kingdom",
        "Paris CDG" to "Paris, France",
        "Tokyo" to "Tokyo, Japan",
        "Dubai DXB" to "Dubai, United Arab Emirates",
        "Singapore SIN" to "Singapore",
        "Mumbai BOM" to "Mumbai, India",
        "Delhi DEL" to "Delhi, India"
    )

    val MAP_STYLES = listOf(
        "Classic" to R.drawable.map1,
        "Blueprint" to R.drawable.map2,
        "Night" to R.drawable.map4
    )

    object Pages {
        const val DEFAULT = 0
        const val ROUTE_SETTINGS = 1
        const val DELETE_CONFIRMATION = 2
        const val SAVE_ROUTE = 3
        const val SAVE_SUCCESS = 4
        const val LOAD_ROUTE = 5
        const val ADD_LOCATIONS = 6
        const val MAP_STYLE = 7
    }

    object Strings {
        const val ROUTE_NAME = "Kochi - Los Angeles"
        const val ROUTE_POINTS = "2 Points"
        const val CREATE_VIDEO = "Create video"
        const val ROUTE_SETTINGS = "Route settings"
        const val DELETE_ROUTE = "Delete Route"
        const val SAVE_ROUTE = "Save route"
        const val LOAD_ROUTE = "Load route"
        const val ADD_LOCATIONS = "Add locations"
        const val MAP_STYLE = "Map style"
        const val DELETE_CONFIRMATION = "ARE YOU SURE?"
        const val ROUTE_SAVED = "ROUTE SAVED"
        const val ENTER_ROUTE_NAME = "Enter route name"
        const val ENTER_POINT = "Enter Point"
        const val DELETE = "Delete"
        const val SAVE = "Save"
        const val LOAD = "Load"
        const val APPLY = "Apply"
        const val MENU = "Menu"
        const val ADD_LOCATION = "Add Location"
        const val CLOSE = "Close"
        const val BACK = "Back"
        const val FORWARD = "Forward"
        const val SELECTED = "Selected"
        const val LOCATION = "Location"
    }
}