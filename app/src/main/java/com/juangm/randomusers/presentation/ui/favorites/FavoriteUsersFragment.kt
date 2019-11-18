package com.juangm.randomusers.presentation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.juangm.randomusers.R
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.presentation.ui.common.FavoriteUserItemInteractions
import com.juangm.randomusers.presentation.ui.common.SwipeToDeleteCallback
import com.juangm.randomusers.presentation.ui.common.showSnackbar
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.fragment_favorite_users.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class FavoriteUsersFragment : Fragment(), FavoriteUserItemInteractions {

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
        observeFavoriteUsersList()
        observeUserRemovedFromFavorites()
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
        ItemTouchHelper(SwipeToDeleteCallback(adapter)).attachToRecyclerView(favorite_users_recycler)
    }

    private fun observeFavoriteUsersList() {
        favoriteUsersViewModel.favoriteUsers.observe(viewLifecycleOwner, Observer { favoriteUsers ->
            Timber.i("Favorite users value has changed. Submitting changes to adapter. Users: ${favoriteUsers.size}")
            adapter.submitList(favoriteUsers)

            if(favoriteUsers.isNotEmpty())
                favorite_users_empty_constraint.visibility = View.GONE
            else
                favorite_users_empty_constraint.visibility = View.VISIBLE
        })
    }

    private fun observeUserRemovedFromFavorites() {
        favoriteUsersViewModel.updateUserEvent.observe(viewLifecycleOwner, Observer { updatedUserEvent ->
            updatedUserEvent.getContentIfNotHandled()?.let { user ->
                Timber.i("updated user value has changed. User: ${user.id}")
                if(!user.favorite) {
                    showUserRemovedFromFavoritesSnackbar(user)
                }
            }
        })
    }

    private fun showUserRemovedFromFavoritesSnackbar(recentlyUpdatedUser: User) {
        showSnackbar(
            view,
            R.string.fragment_favorite_users_snackbar_message,
            resources.getColor(R.color.colorSecondary, null),
            R.id.bottom_app_bar,
            R.string.snackbar_undo
        ) {
            favoriteUsersViewModel.addUserToFavorites(recentlyUpdatedUser)
        }
    }

    override fun navigateToUserDetail(user: User, userImage: ImageView, position: Int) {
        Timber.i("Showing detail for favorite user ${user.id}")
        val direction = FavoriteUsersFragmentDirections.actionFavoriteUsersFragmentToUserDetailFragment(user, position)
        val extras = FragmentNavigatorExtras(userImage to userImage.transitionName)
        findNavController().navigate(direction, extras)
    }

    override fun removeUserFromFavorites(user: User) {
        favoriteUsersViewModel.removeUserFromFavorites(user)
    }
}
