import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tiki_taka.FriendListFragment
import com.example.tiki_taka.NewMatchingActivity

class FriendListFragmentAdapter(
    fragment: FriendListFragment,
    private val fragments: List<Fragment>
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}