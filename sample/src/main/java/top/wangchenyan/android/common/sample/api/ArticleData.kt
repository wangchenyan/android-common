package top.wangchenyan.android.common.sample.api

import com.google.gson.annotations.SerializedName

data class ArticleList(
    @SerializedName("curPage") val curPage: Int = 0,
    @SerializedName("datas") val datas: List<Article> = listOf(),
    @SerializedName("offset") val offset: Int = 0,
    @SerializedName("over") val over: Boolean = false,
    @SerializedName("pageCount") val pageCount: Int = 0,
    @SerializedName("size") val size: Int = 0,
    @SerializedName("total") val total: Int = 0
)

data class Article(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("apkLink") val apkLink: String = "",
    @SerializedName("audit") val audit: Int = 0,
    @SerializedName("author") private val author: String = "",
    @SerializedName("canEdit") val canEdit: Boolean = false,
    @SerializedName("chapterId") val chapterId: Int = 0,
    @SerializedName("chapterName") val chapterName: String = "",
    @SerializedName("collect") var collect: Boolean = false,
    @SerializedName("courseId") val courseId: Int = 0,
    @SerializedName("desc") val desc: String = "",
    @SerializedName("descMd") val descMd: String = "",
    @SerializedName("envelopePic") val envelopePic: String = "",
    @SerializedName("fresh") val fresh: Boolean = false,
    @SerializedName("host") val host: String = "",
    @SerializedName("link") val link: String = "",
    @SerializedName("niceDate") val niceDate: String = "",
    @SerializedName("niceShareDate") val niceShareDate: String = "",
    @SerializedName("origin") val origin: String = "",
    @SerializedName("prefix") val prefix: String = "",
    @SerializedName("projectLink") val projectLink: String = "",
    @SerializedName("publishTime") val publishTime: Long = 0,
    @SerializedName("realSuperChapterId") val realSuperChapterId: Int = 0,
    @SerializedName("selfVisible") val selfVisible: Int = 0,
    @SerializedName("shareDate") val shareDate: Long = 0,
    @SerializedName("shareUser") val shareUser: String = "",
    @SerializedName("superChapterId") val superChapterId: Int = 0,
    @SerializedName("superChapterName") val superChapterName: String = "",
    @SerializedName("tags") val tags: MutableList<ArticleTag> = mutableListOf(),
    @SerializedName("title") val title: String = "",
    @SerializedName("type") val type: Int = 0,
    @SerializedName("userId") val userId: Int = 0,
    @SerializedName("visible") val visible: Int = 0,
    @SerializedName("zan") val zan: Int = 0,
    @SerializedName("originId") val originId: Long = 0
)

data class ArticleTag(
    @SerializedName("name") val name: String = "",
    @SerializedName("url") val url: String = ""
)