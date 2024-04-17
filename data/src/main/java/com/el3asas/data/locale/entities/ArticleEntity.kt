package com.el3asas.data.locale.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.el3asas.domain.models.ArticlesItem
import com.el3asas.domain.models.Source

@Entity(indices = [Index(value = ["url"], unique = true)])
data class ArticleEntity(
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String? = null,
    val sourceEntityField: SourceEntityField? = null,
    val title: String? = null,
    val url: String? = null,
    val content: String? = null,
    val isFav: Boolean = false,
    @PrimaryKey
    val id: Int? = null,
) {
    fun mapToItem(): ArticlesItem = run {
        ArticlesItem(
            publishedAt,
            author,
            urlToImage,
            description,
            sourceEntityField?.mapToSource(),
            title,
            url,
            content,
            isFav,
            id
        )
    }

}

data class SourceEntityField(
    val name: String? = null,
    val id: String? = null
) {
    fun mapToSource() = run {
        Source(
            name,
            id
        )
    }
}

fun ArticlesItem.mapToEntity() = run {
    ArticleEntity(
        publishedAt,
        author,
        urlToImage,
        description,
        source?.mapToSourceEntityField(),
        title,
        url,
        content,
        isFav,
        id
    )
}

fun Source.mapToSourceEntityField() = run {
    SourceEntityField(
        name, id
    )
}
