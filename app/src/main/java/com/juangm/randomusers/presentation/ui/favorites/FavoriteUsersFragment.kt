package com.juangm.randomusers.presentation.ui.favorites

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
import com.juangm.randomusers.presentation.ui.common.UserItemInteractions
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.fragment_favorite_users.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class FavoriteUsersFragment : Fragment(), UserItemInteractions {

    private val favoriteUsersViewModel by viewModel<FavoriteUsersViewModel>()
    private lateinit var adapter: FavoriteUsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomAppBar()
        setRecyclerView()
        observeUsers()
    }

    override fun onResume() {
        super.onResume()
        favoriteUsersViewModel.getFavoriteUsers()
    }

    private fun setBottomAppBar() {
        activity?.favorite_users_button?.let { bottomAppBarButton ->
            if(bottomAppBarButton.isOrWillBeShown)
                bottomAppBarButton.hide()
        }
    }

    private fun setRecyclerView() {
        adapter = FavoriteUsersAdapter(this)
        favorite_users_recycler.layoutManager = LinearLayoutManager(context)
        favorite_users_recycler.adapter = adapter
    }

    private fun observeUsers() {
        favoriteUsersViewModel.favoriteUsers.observe(this, Observer { favoriteUsers ->
            Timber.i("Favorite users value has changed. Submitting changes to adapter. Users: ${favoriteUsers.size}")
            adapter.submitList(favoriteUsers)

            if(favoriteUsers.isNotEmpty())
                favorite_users_empty_constraint.visibility = View.GONE
            else
                favorite_users_empty_constraint.visibility = View.VISIBLE
        })
    }

    override fun showUserDetail(user: User) {
        Timber.i("Showing detail for favorite user ${user.id}")
        val direction = FavoriteUsersFragmentDirections.actionFavoriteUsersFragmentToUserDetailFragment(user)
        findNavController().navigate(direction)
    }
}
