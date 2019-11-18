package com.juangm.randomusers.presentation.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.juangm.randomusers.R
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.presentation.ui.common.UserItemInteractions
import kotlinx.android.synthetic.main.activity_users.*
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
        setBottomAppBar()
        setRecyclerView()
        observeUsers()
        usersViewModel.getUsers()
    }

    private fun setBottomAppBar() {
        activity?.favorite_users_button?.let { favoriteUsersButton ->
            favoriteUsersButton.setImageResource(R.drawable.ic_favorite_appbar)

            if(favoriteUsersButton.isOrWillBeHidden) {
                favoriteUsersButton.show()
            }

            favoriteUsersButton.setOnClickListener {
                navigateToFavoriteUsers()
            }
        }

        activity?.bottom_app_bar?.let { bottomAppBar ->
            bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
        }
    }

    private fun setRecyclerView() {
        adapter = UsersAdapter(this)
        users_recycler.layoutManager = LinearLayoutManager(context)
        users_recycler.adapter = adapter
    }

    private fun observeUsers() {
        usersViewModel.users.observe(this, Observer { users ->
            Timber.i("Users value has changed. Submitting changes to adapter. Users: ${users.size}")
            adapter.submitList(users)

            if(users.isNotEmpty())
                users_empty_constraint.visibility = View.GONE
            else
                users_empty_constraint.visibility = View.VISIBLE
        })
    }

    private fun navigateToFavoriteUsers() {
        activity?.favorite_users_button?.isEnabled = false
        Timber.i("Showing favorite users")
        val direction = UsersFragmentDirections.actionUsersFragmentToFavoriteUsersFragment()
        findNavController().navigate(direction)
    }

    override fun navigateToUserDetail(user: User, userImage: ImageView, position: Int) {
        Timber.i("Showing detail for user ${user.id}")
        val direction = UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(user, position)
        val extras = FragmentNavigatorExtras(userImage to userImage.transitionName)
        findNavController().navigate(direction, extras)
    }
}
