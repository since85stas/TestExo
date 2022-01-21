package stas.batura.testapp.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.user_item.view.*
import stas.batura.testapp.data.room.User
import stas.batura.testapp.databinding.UserItemBinding

class UsersAdapter ( val viewModel: UsersViewModel
): ListAdapter<User, UsersAdapter.ViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder (
        val binding: UserItemBinding
        ) :
        RecyclerView.ViewHolder (binding.root) {

        fun bind (user: User) {
            binding.user = user
            binding.executePendingBindings()
        }

        companion object {
            fun from(
                parent: ViewGroup,
                viewModel: UsersViewModel
                ):
                    ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = UserItemBinding.inflate(layoutInflater,
                    parent,
                    false)
                binding.viewModel = viewModel
                return ViewHolder(binding,)
            }
        }
    }

    class TrackDiffCallback : DiffUtil.ItemCallback<User> (){

        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return  oldItem == newItem
        }
    }



}