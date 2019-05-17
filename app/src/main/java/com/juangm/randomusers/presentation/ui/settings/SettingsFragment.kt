package com.juangm.randomusers.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.juangm.randomusers.R
import com.juangm.randomusers.presentation.ui.common.showSnackbar
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class SettingsFragment : Fragment() {

    private val settingsViewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomAppBar()
        setDeleteButtonBehaviour()
        observeUsersDeleted()
    }

    private fun setBottomAppBar() {
        activity?.favorite_users_button?.let { bottomAppBarButton ->
            if(bottomAppBarButton.isOrWillBeShown)
                bottomAppBarButton.hide()
        }
    }

    private fun setDeleteButtonBehaviour() {
        fragment_settings_delete_button.setOnClickListener {
            settingsViewModel.deleteLocalUsers()
        }
    }

    private fun observeUsersDeleted() {
        settingsViewModel.usersDeleted.observe(this, Observer { usersDeleted ->
            if(usersDeleted) {
                Timber.i("usersDeleted = $usersDeleted")
                showSnackbar(view, R.string.fragment_settings_users_deleted_text,
                    resources.getColor(R.color.colorSecondary, null), R.id.bottom_app_bar)
            }
        })
    }
}
