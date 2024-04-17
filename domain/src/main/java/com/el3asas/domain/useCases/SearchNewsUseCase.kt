package com.el3asas.domain.useCases

import com.el3asas.domain.repos.NewsRepo
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(private val repo: NewsRepo) {

    operator fun invoke(search: String) = repo.searchNews(search)

}