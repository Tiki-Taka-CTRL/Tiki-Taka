import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tiki_taka.NewMatchingActivity

class NewMatchingFragmentAdapter(
    activity: NewMatchingActivity,
    private val fragments: List<Fragment>
) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}