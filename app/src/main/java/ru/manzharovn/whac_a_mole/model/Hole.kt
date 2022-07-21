package ru.manzharovn.whac_a_mole.model

data class Hole(val moleStatus: MoleStatus)

enum class MoleStatus {
    None,Show,Whacked
}