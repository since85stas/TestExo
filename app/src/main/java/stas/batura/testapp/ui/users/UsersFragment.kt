package stas.batura.testapp.ui.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.users_fragment.*
import stas.batura.testapp.R
import stas.batura.testapp.databinding.UsersFragmentBinding

private val TAG = "PaymentsFragment"

@AndroidEntryPoint
class UsersFragment: Fragment() {

    companion object {
        fun newInstance() = UsersFragment()
    }

    private lateinit var usersViewModel: UsersViewModel

//    private val adapter: UsersAdapter = UsersAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        val bindings: UsersFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.users_fragment,
            container,
            false)

        bindings.usersViewModel = usersViewModel

        bindings.lifecycleOwner = viewLifecycleOwner
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UsersAdapter(usersViewModel);
        recycler.adapter = adapter

        usersViewModel.users.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: $it")
            adapter.submitList(it)
        }

        usersViewModel.toastTex.observe(viewLifecycleOwner) {
            showToast(it)
        }

        usersViewModel.userId.observe(viewLifecycleOwner) {
            if (it != null) {
                val action = UsersFragmentDirections.actionNavigationUsersToInfoFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

}