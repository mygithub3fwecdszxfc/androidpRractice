package com.example.androidstudydemo.recyclerViewDemo

/**
 * 普通新闻实体类
 * 对应接口返回普通新闻列表item数据
 */
data class News(
    val ga_prefix: String,       // 埋点前缀（统计相关字段）
    val hint: String,            // 提示文字
    val id: Int,                 // 新闻唯一id
    val image: String,          // 新闻大图url
    val image_hue: String,       // 图片主色调（用于界面配色）
    val share_url: String,       // 新闻分享链接
    val thumbnail: String,       // 新闻缩略图url
    val title: String,           // 新闻标题（RecyclerView展示核心字段）
    val url: String              // 新闻详情网页链接
)

/**
 * 头条新闻实体类
 * 对应接口返回顶部轮播/头条新闻数据
 */
data class TopStory(
    val ga_prefix: String,       // 埋点前缀（统计相关字段）
    val hint: String,            // 提示文字
    val id: Int,                 // 头条新闻唯一id
    val image: String,          // 头条大图url
    val image_hue: String,       // 图片主色调
    val image_source: String,    // 图片来源信息
    val share_url: String,       // 分享链接
    val title: String,           // 头条新闻标题
    val url: String              // 新闻详情网页链接
)