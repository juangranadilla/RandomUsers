package com.juangm.randomusers.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juangm.randomusers.R
import kotlinx.android.synthetic.main.activity_users.*

class SettingsFragment : Fragment() {

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
    }

    private fun setBottomAppBar() {
        activity?.favorite_users_button?.let { bottomAppBarButton ->
            if(bottomAppBarButton.isOrWillBeShown)
                bottomAppBarButton.hide()
        }
    }
}
