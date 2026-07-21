import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidstudydemo.databinding.BannerItemBinding
import com.example.androidstudydemo.projectPractice.BannerAdapter
import com.example.androidstudydemo.projectPractice.Book

// 书籍数据实体类，和你项目Book保持一致
data class Book(
    val bookName: String,
    val author: String,
    val imgUrl: String
)

class BannerAdapter(private val bannerList: List<Book>) :
    RecyclerView.Adapter<BannerAdapter.BannerVH>() {

    inner class BannerVH(private val binding: BannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.tvBookName.text = book.bookName
            Glide.with(itemView.context)
                .load(book.imgUrl)
                .into(binding.ivCover)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerVH {
        val binding = BannerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BannerVH(binding)
    }

    override fun onBindViewHolder(holder: BannerVH, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount() = bannerList.size
}