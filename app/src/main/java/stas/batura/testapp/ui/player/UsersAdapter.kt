package stas.batura.testapp.ui.player

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import stas.batura.testapp.data.room.Video
import stas.batura.testapp.databinding.UserItemBinding

class UsersAdapter ( val viewModel: UsersViewModel
): ListAdapter<Video, UsersAdapter.ViewHolder>(TrackDiffCallback()) {

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

        fun bind (video: Video) {
            binding.user = video
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

    class TrackDiffCallback : DiffUtil.ItemCallback<Video> (){

        override fun areItemsTheSame(
            oldItem: Video,
            newItem: Video
        ): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(
            oldItem: Video,
            newItem: Video
        ): Boolean {
            return  oldItem == newItem
        }
    }



}