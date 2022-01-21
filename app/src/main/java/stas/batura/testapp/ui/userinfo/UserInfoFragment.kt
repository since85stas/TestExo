package stas.batura.testapp.ui.userinfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.user_info_fragment.view.*
import kotlinx.android.synthetic.main.user_item.view.*
import stas.batura.testapp.R
import stas.batura.testapp.databinding.UserInfoFragmentBinding

private val TAG = "USERINFOFRAGM"

@AndroidEntryPoint
class InfoFragment : Fragment() {

    companion object {
        fun newInstance() = InfoFragment()
    }

    val args: InfoFragmentArgs by navArgs()

    private lateinit var userInfoViewModel: UserInfoViewModel

    private lateinit var bindings: UserInfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        userInfoViewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)

        bindings = DataBindingUtil.inflate(inflater,
            R.layout.user_info_fragment,
            container,
            false)

        bindings.lifecycleOwner = viewLifecycleOwner
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = args.userId

        userInfoViewModel.getUser(userId).observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.avatUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bindings.root.avatar)

            bindings.user = it
            Log.d(TAG, "onViewCreated: $it")
        }
     }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }
}

