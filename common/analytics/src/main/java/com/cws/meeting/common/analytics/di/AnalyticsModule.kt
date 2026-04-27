package com.cws.meeting.common.analytics.di

import com.cws.meeting.common.analytics.AnalyticsHelper
import com.cws.meeting.common.analytics.CompositeAnalyticsHelper
import com.cws.meeting.common.analytics.FileAnalyticsHelper
import com.cws.meeting.common.analytics.LogcatAnalyticsHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AnalyticsModule {
    @Provides
    @Singleton
    fun provideAnalyticsHelper(
        logcat: LogcatAnalyticsHelper,
        file: FileAnalyticsHelper,
    ): AnalyticsHelper = CompositeAnalyticsHelper(listOf(logcat, file))
}
