package com.juangm.randomusers.presentation.ui.userdetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.juangm.randomusers.R
import com.juangm.randomusers.domain.models.User
import com.juangm.randomusers.presentation.ui.common.setUserImage
import com.juangm.randomusers.presentation.ui.common.showSnackbar
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.fragment_user_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class UserDetailFragment : Fragment() {

    private val args: UserDetailFragmentArgs by navArgs()
    private val userDetailViewModel by viewModel<UserDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = args.user
        val position = args.position
        bindUserData(user, position)
        observeFavoriteState()
    }

    private fun bindUserData(user: User, position: Int) {
        user_name.text = getString(R.string.user_name_content, user.name, user.surname)
        user_email.text = user.email
        user_gender.text = user.gender
        user_address.text = getString(R.string.user_address_content, user.street, user.city, user.state)
        user_registered.text = user.registered

        user_image.transitionName = getString(R.string.user_image_transition, position)
        setUserImage(
            user_image,
            user.gender,
            Color.WHITE,
            4f,
            user.largePicture
        )

        setBottomAppBar(user)
    }

    private fun setBottomAppBar(user: User) {
        activity?.favorite_users_button?.let { bottomAppBarButton ->

            setFavoriteFabIcon(user.favorite, bottomAppBarButton)

            if(bottomAppBarButton.isOrWillBeHidden)
                bottomAppBarButton.show()

            bottomAppBarButton.setOnClickListener {
                user.favorite = !user.favorite
                userDetailViewModel.updateUser(user)
            }
        }

        activity?.bottom_app_bar?.let { bottomAppBar ->
            bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
        }
    }

    private fun setFavoriteFabIcon(favorite: Boolean, bottomAppBarButton: FloatingActionButton) {
        if(favorite)
            bottomAppBarButton.setImageResource(R.drawable.ic_favorite_appbar)
        else
            bottomAppBarButton.setImageResource(R.drawable.ic_favorite_appbar_unselected)
    }

    private fun observeFavoriteState() {
        activity?.favorite_users_button?.let { bottomAppBarButton ->
            userDetailViewModel.favorite.observe(this, Observer { favorite ->
                Timber.i("User favorite state has changed to $favorite")
                setFavoriteFabIcon(favorite, bottomAppBarButton)

                if(favorite)
                    showSnackbar(view, R.string.fragment_user_detail_favorite_selected,
                        resources.getColor(R.color.colorSecondary, null), R.id.favorite_users_button)
                else
                    showSnackbar(view, R.string.fragment_user_detail_favorite_unselected,
                        resources.getColor(R.color.colorSecondary, null), R.id.favorite_users_button)
            })
        }
    }
}
