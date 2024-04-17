package com.el3asas.domain.useCases

import com.el3asas.domain.models.ArticlesItem
import com.el3asas.domain.repos.NewsRepo
import javax.inject.Inject

class UpdateOrAddArticleToLocaleDbUseCases @Inject constructor(private val repo: NewsRepo) {
    suspend operator fun invoke(vararg articlesItem: ArticlesItem) = repo.updateOrAddNews(*articlesItem)
}