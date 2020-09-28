package com.cesarsosa.mobiletest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cesarsosa.mobiletest.characters.ui.CharacterViewModel
import com.cesarsosa.mobiletest.characters.ui.CharactersViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    abstract fun bindCharactersViewModel(viewModel: CharactersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterViewModel::class)
    abstract fun bindCharacterViewModel(viewModel: CharacterViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
