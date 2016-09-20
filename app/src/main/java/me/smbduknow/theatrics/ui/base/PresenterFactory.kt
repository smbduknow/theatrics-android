package me.smbduknow.theatrics.ui.base;

interface PresenterFactory<out P : MvpPresenter<*>> {
        fun create(): P
}