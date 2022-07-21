package ru.manzharovn.whac_a_mole.utils

fun intToTime(time: Int): String {
    val minutes = time / 60
    val seconds = time % 60
    return String.format("%02d:%02d", minutes, seconds);
}