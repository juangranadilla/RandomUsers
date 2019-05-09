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

class UsersFragment : Fragment(), UserClickInterface {

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

    override fun showUserDetail(user: User) {
        val direction = UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(user)
        findNavController().navigate(direction)
    }

    private fun setRecyclerView() {
        adapter = UsersAdapter(this)
        users_recycler.layoutManager = LinearLayoutManager(context)
        users_recycler.adapter = adapter
    }

    private fun observeUsers() {
        usersViewModel.users.observe(this, Observer {
                users -> adapter.submitList(users)
        })
    }
}
