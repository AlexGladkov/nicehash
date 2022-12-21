package com.dev.nicehash.enums

/**
 * Created by agladkov on 17.06.18.
 * Contains themes for application, combined with dark and light
 */
enum class Theme(val value: Int, val color: Int) {
    DarkOrange(0, 2), LightOrange(1, 2), DarkBlue(2, 0), LightBlue(3, 0), DarkGreen(4, 1), LightGreen(5, 1),
    DarkRed(6, 3), LightRed(7, 3), DarkPurple(8, 4), LightPurple(9, 4)
}