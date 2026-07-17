package com.example.androidstudydemo.recyclerViewDemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.androidstudydemo.R
import com.example.androidstudydemo.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerBinding
    private lateinit var adapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mockNewsList = listOf(
            News(
                ga_prefix = "",
                hint = "近日多个科技厂商发布全新移动端处理器，功耗与性能实现新突破，手机续航能力有望迎来大幅提升。",
                id = 1,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/20/1080/450",
                title = "新一代移动芯片正式亮相，移动端性能迎来革新",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "城市公共交通持续优化，新增多条便民公交线路，覆盖居民区与商圈，方便市民日常通勤出行。",
                id = 2,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/30/1080/450",
                title = "城市公交路线调整优化，方便居民通勤",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "居家健康小知识：保持室内通风，合理安排作息，适度开展居家运动，可以有效缓解日常疲劳。",
                id = 3,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/40/1080/450",
                title = "几条实用居家养生小建议，建议收藏",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "国内多地开展周末户外徒步活动，不少爱好者走进山野，亲近自然，感受户外运动带来的乐趣。",
                id = 4,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/65/1080/450",
                title = "户外徒步热度上涨，短途轻旅行广受年轻人喜爱",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "图书市场上新大量文学新作，众多经典书籍推出修订版本，适合闲暇时光静下心阅读。",
                id = 5,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/76/1080/450",
                title = "一大批优质新书上市，适合周末安静阅读",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "美食探店：本地新开多家特色小吃门店，主打传统风味小吃，性价比高，吸引大量食客前去打卡。",
                id = 6,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/88/1080/450",
                title = "本地特色小吃新店汇总，吃货不要错过",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "气象部门提醒，未来几天局部区域将迎来持续降雨，外出记得携带雨具，同时注意道路湿滑谨慎驾驶。",
                id = 7,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/91/1080/450",
                title = "降雨天气即将来袭，市民出行提前做好规划",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "各大视频平台持续加码短剧赛道，优质现实主义短剧数量持续增长，成为大众碎片化休闲新选择。",
                id = 8,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/96/1080/450",
                title = "短剧行业持续发展，精品内容不断涌现",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "校园科普活动顺利开展，通过趣味实验激发青少年科学兴趣，助力提升学生动手探索能力。",
                id = 9,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/101/1080/450",
                title = "校园科普活动举办，点亮青少年科学梦想",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "宠物饲养小贴士：定期驱虫、按时接种疫苗，合理搭配饮食，能够更好保障毛孩子身体健康。",
                id = 10,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/106/1080/450",
                title = "新手铲屎官必读，科学饲养宠物小常识",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "多地景区推出淡季优惠政策，门票降价、特色活动上线，适合错峰出游避开人流高峰。",
                id = 11,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/110/1080/450",
                title = "景区淡季福利来袭，错峰出游正当时",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "线上办公工具持续更新，新增协同批注、实时文档同步功能，有效提升远程团队工作沟通效率。",
                id = 12,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/116/1080/450",
                title = "协同办公软件新版本发布，远程办公更便捷",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "全民健身广场全面升级，新增健身器材与休息区域，方便周边居民早晚开展体育锻炼。",
                id = 13,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/122/1080/450",
                title = "社区健身场地改造完成，居民锻炼更加便利",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "国产新能源车型新款正式上市，续航进一步提升，智能驾驶辅助功能全面升级。",
                id = 14,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/128/1080/450",
                title = "新款新能源汽车上市，续航与智能化同步升级",
                url = ""
            ),
            News(
                ga_prefix = "",
                hint = "秋季蔬果大量上市，白菜、柑橘、石榴新鲜实惠，合理搭配膳食，补充多种维生素。",
                id = 15,
                image = "",
                image_hue = "",
                share_url = "",
                thumbnail = "https://picsum.photos/id/133/1080/450",
                title = "时令蔬果集中上市，健康饮食不要错过",
                url = ""
            )
        )

        adapter = NewsAdapter(mockNewsList)
        //决定条目如何摆放（垂直列表 / 横向列表 / 网格）
        //控制条目复用、滚动逻辑。
//        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        //  // RecyclerView绑定适配器，建立列表和数据、条目布局的关联
        binding.recyclerView.adapter = adapter

    }
}