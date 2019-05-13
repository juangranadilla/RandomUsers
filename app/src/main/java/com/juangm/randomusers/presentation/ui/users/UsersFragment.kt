package com.juangm.randomusers.presentation.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juangm.randomusers.R
import com.juangm.randomusers.domain.models.User
import kotlinx.android.synthetic.main.fragment_users.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class UsersFragment : Fragment(), UserItemInteractions {

    private val usersViewModel by viewModel<UsersViewModel>()
    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        observeUsers()
        usersViewModel.getUsers()
    }

    private fun setRecyclerView() {
        adapter = UsersAdapter(this)
        users_recycler.layoutManager = LinearLayoutManager(context)
        users_recycler.adapter = adapter
    }

    private fun observeUsers() {
        usersViewModel.users.observe(this, Observer { users ->
            Timber.i("Users value has changed. Submitting changes to adapter")
            adapter.submitList(users)
        })
    }

    override fun showUserDetail(user: User) {
        Timber.i("Showing detail for user ${user.id}")
        val direction = UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(user)
        findNavController().navigate(direction)
    }

    override fun addUserToFavorites(user: User, position: Int) {
        Timber.i("Adding user ${user.id} to favorites")
        user.favorite = true
        adapter.notifyItemChanged(position)
        usersViewModel.updateUser(user)
    }

    override fun removeUserFromFavorites(user: User, position: Int) {
        Timber.i("Removing user ${user.id} from favorites")
        user.favorite = false
        adapter.notifyItemChanged(position)
        usersViewModel.updateUser(user)
    }
}
