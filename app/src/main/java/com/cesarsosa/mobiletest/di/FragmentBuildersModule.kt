package com.cesarsosa.mobiletest.di


import com.cesarsosa.mobiletest.characters.ui.CharacterFragment
import com.cesarsosa.mobiletest.characters.ui.CharactersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeCharactersFragment(): CharactersFragment

    @ContributesAndroidInjector
    abstract fun contributeCharacterFragment(): CharacterFragment

}
