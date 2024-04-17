package com.el3asas.domain.useCases

import com.el3asas.domain.repos.NewsRepo
import javax.inject.Inject

class GetHomeNewsUseCase @Inject constructor(private val repo: NewsRepo) {

    operator fun invoke(country: String? = null) = repo.getHomeNews(country)

}